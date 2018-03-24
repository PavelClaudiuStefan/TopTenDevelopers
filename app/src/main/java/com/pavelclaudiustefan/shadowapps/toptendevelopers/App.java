package com.pavelclaudiustefan.shadowapps.toptendevelopers;

import android.app.Application;

import com.pavelclaudiustefan.shadowapps.toptendevelopers.models.User;

import java.util.ArrayList;

/**
 * Created by Claudiu on 23-Mar-18.
 */

public class App extends Application {

    private static App instance = null;

    private ArrayList<User> users = null;
    private long ramCacheExpireTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public long getRamCacheExpireTime() {
        return ramCacheExpireTime;
    }

    public void setRamCacheExpireTime(long ramCacheExpireTime) {
        this.ramCacheExpireTime = ramCacheExpireTime;
    }

    private static void checkInstance() {
        if(instance == null) {
            throw new RuntimeException("App instance is null");
        }
    }

    public static App getInstance() {
        checkInstance();
        return instance;
    }
}
