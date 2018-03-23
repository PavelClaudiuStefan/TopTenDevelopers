package com.pavelclaudiustefan.shadowapps.toptendevelopers.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.pavelclaudiustefan.shadowapps.toptendevelopers.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Claudiu on 23-Mar-18.
 */

public class UsersListLoader extends AsyncTaskLoader<List<User>> {

    String url;

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
        // TODO
        ArrayList<User> users = User.getUsers();
        if (users == null) {
            throw new RuntimeException("temporary users list is null");
        }

        return User.getUsers();
    }
}
