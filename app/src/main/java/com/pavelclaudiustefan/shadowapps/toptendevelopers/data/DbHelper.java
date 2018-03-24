package com.pavelclaudiustefan.shadowapps.toptendevelopers.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pavelclaudiustefan.shadowapps.toptendevelopers.data.UserContract.UserEntry;

/**
 * Created by Claudiu on 23-Mar-18.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";

    private static final int DATABASE_VERSION = 1;

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the users table
        String sqlCreateUsersTable =  "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.NAME + " TEXT NOT NULL, "
                + UserEntry.LOCATION + " TEXT NOT NULL,"
                + UserEntry.PROFILE_IMAGE_URL + " TEXT NOT NULL, "
                + UserEntry.GOLD_BADGES_COUNT + " INTEGER NOT NULL, "
                + UserEntry.SILVER_BADGES_COUNT + " INTEGER NOT NULL, "
                + UserEntry.BRONZE_BADGES_COUNT + " INTEGER NOT NULL);";

        db.execSQL(sqlCreateUsersTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }
}
