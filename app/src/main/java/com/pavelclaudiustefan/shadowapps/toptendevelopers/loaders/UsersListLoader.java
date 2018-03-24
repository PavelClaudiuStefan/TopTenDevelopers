package com.pavelclaudiustefan.shadowapps.toptendevelopers.loaders;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;

import com.pavelclaudiustefan.shadowapps.toptendevelopers.App;
import com.pavelclaudiustefan.shadowapps.toptendevelopers.data.UsersHttpRequest;
import com.pavelclaudiustefan.shadowapps.toptendevelopers.data.UserContract.UserEntry;
import com.pavelclaudiustefan.shadowapps.toptendevelopers.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Claudiu on 23-Mar-18.
 */

public class UsersListLoader extends AsyncTaskLoader<List<User>>{

    private String url;

    public UsersListLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<User> loadInBackground() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        App app = App.getInstance();
        ArrayList<User> users = app.getUsers();
        long ramCacheExpireTime = app.getRamCacheExpireTime();
        long currentTime = System.currentTimeMillis();

        if (users != null && ramCacheExpireTime > currentTime) {
            // Return users stored in ram
            return users;
        }

        long diskCacheExpireTime = prefs.getLong("disk_cache_expire_time", 0);
        if (diskCacheExpireTime > currentTime) {
            // Return users stored on disk
            return getUsersFromDisk();
        } else {
            users = getUsersFromHttp();
            if (users != null) {
                saveData(app, users, prefs);

                //Return users from http
                return users;
            } else {
                // Error getting users from http, return what is stored on disk as a last measure
                return getUsersFromDisk();
            }
        }
    }

    private ArrayList<User> getUsersFromDisk() {
        ArrayList<User> users = new ArrayList<>();

        String[] projection = {
                UserEntry.NAME,
                UserEntry.LOCATION,
                UserEntry.PROFILE_IMAGE_URL,
                UserEntry.GOLD_BADGES_COUNT,
                UserEntry.SILVER_BADGES_COUNT,
                UserEntry.BRONZE_BADGES_COUNT,

        };
        Cursor cursor = getContext().getContentResolver().query(UserEntry.CONTENT_URI, projection, null, null, null);
        if (cursor == null || cursor.getCount() < 1) {
            return null;
        }
        try {
            while(cursor.moveToNext()) {
                int nameColumnIndex = cursor.getColumnIndex(UserEntry.NAME);
                int locationColumnIndex = cursor.getColumnIndex(UserEntry.LOCATION);
                int profileImageUrlColumnIndex = cursor.getColumnIndex(UserEntry.PROFILE_IMAGE_URL);
                int goldBadgesCountColumnIndex = cursor.getColumnIndex(UserEntry.GOLD_BADGES_COUNT);
                int silverBadgesCountColumnIndex = cursor.getColumnIndex(UserEntry.SILVER_BADGES_COUNT);
                int bronzeBadgesCountColumnIndex = cursor.getColumnIndex(UserEntry.BRONZE_BADGES_COUNT);

                String name = cursor.getString(nameColumnIndex);
                String location = cursor.getString(locationColumnIndex);
                String profileImageUrl = cursor.getString(profileImageUrlColumnIndex);
                int goldBadgesCount = cursor.getInt(goldBadgesCountColumnIndex);
                int silverBadgesCount = cursor.getInt(silverBadgesCountColumnIndex);
                int bronzeBadgesCount = cursor.getInt(bronzeBadgesCountColumnIndex);

                User user = new User(name, profileImageUrl, location);
                user.setBadgesCount(goldBadgesCount, silverBadgesCount, bronzeBadgesCount);
                users.add(user);
            }
        } finally {
            cursor.close();
        }

        return users;
    }

    private void saveUsersOnDisk(ArrayList<User> users) {
        //Remove previous data, if any
        removeUsers();

        for (User user : users) {
            addUser(user);
        }
    }

    private void removeUsers() {
        getContext().getContentResolver().delete(UserEntry.CONTENT_URI, null, null);
    }

    private void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserEntry.NAME, user.getName());
        values.put(UserEntry.LOCATION, user.getLocation());
        values.put(UserEntry.PROFILE_IMAGE_URL, user.getProfileImageUrl());
        values.put(UserEntry.GOLD_BADGES_COUNT, user.getGoldBadgesCount());
        values.put(UserEntry.SILVER_BADGES_COUNT, user.getSilverBadgesCount());
        values.put(UserEntry.BRONZE_BADGES_COUNT, user.getBronzeBadgesCount());

        getContext().getContentResolver().insert(UserEntry.CONTENT_URI, values);
    }

    private ArrayList<User> getUsersFromHttp() {
        return UsersHttpRequest.getUsers(url);
    }

    private void saveData(App app, ArrayList<User> users, SharedPreferences prefs) {
        long currentTime = System.currentTimeMillis();
        long tenMinutesInMilliseconds = 600000L;
        long thirtyMinutesInMilliseconds = 1800000L;

        //Save users in ram
        app.setUsers(users);
        app.setRamCacheExpireTime(currentTime + tenMinutesInMilliseconds);

        //Save users on disk
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putLong("disk_cache_expire_time", currentTime + thirtyMinutesInMilliseconds);
        prefsEditor.apply();
        saveUsersOnDisk(users);
    }
}
