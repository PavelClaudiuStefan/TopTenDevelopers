package com.pavelclaudiustefan.shadowapps.toptendevelopers.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pavelclaudiustefan.shadowapps.toptendevelopers.data.UserContract.UserEntry;

/**
 * Created by Claudiu on 23-Mar-18.
 */

public class UserProvider extends ContentProvider {

    public static final String LOG_TAG = UserProvider.class.getSimpleName();

    private static final int USERS = 100;
    private static final int USER_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS, USERS);

        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS + "/#", USER_ID);
    }

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                cursor = database.query(
                        UserEntry.TABLE_NAME,
                        projection, selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case USER_ID:
                selection = UserEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(
                        UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return insertUser(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertUser(Uri uri, ContentValues values) {
        String name = values.getAsString(UserEntry.NAME);
        if (name == null) {
            throw new IllegalArgumentException("User requires a name");
        }

        String location = values.getAsString(UserEntry.LOCATION);
        if (location == null) {
            throw new IllegalArgumentException("User requires a location");
        }

        String profileImageUrl = values.getAsString(UserEntry.PROFILE_IMAGE_URL);
        if (profileImageUrl == null) {
            throw new IllegalArgumentException("User requires a profile image url");
        }

        Integer goldBadgesCount = values.getAsInteger(UserEntry.GOLD_BADGES_COUNT);
        if (goldBadgesCount == null) {
            throw new IllegalArgumentException("User requires gold badges count");
        }

        Integer silverBadgesCount = values.getAsInteger(UserEntry.SILVER_BADGES_COUNT);
        if (silverBadgesCount == null) {
            throw new IllegalArgumentException("User requires silver badges count");
        }

        Integer bronzeBadgesCount = values.getAsInteger(UserEntry.BRONZE_BADGES_COUNT);
        if (bronzeBadgesCount == null) {
            throw new IllegalArgumentException("User requires bronze badges count");
        }


        // Get writeable database
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Insert the new movie with the given values
        long id = database.insert(UserEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the movie content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return updateUsers(uri, contentValues, selection, selectionArgs);
            case USER_ID:
                selection = UserEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateUsers(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateUsers(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String name = values.getAsString(UserEntry.NAME);
        if (name == null) {
            throw new IllegalArgumentException("User requires a name");
        }

        String location = values.getAsString(UserEntry.LOCATION);
        if (location == null) {
            throw new IllegalArgumentException("User requires a location");
        }

        String profileImageUrl = values.getAsString(UserEntry.PROFILE_IMAGE_URL);
        if (profileImageUrl == null) {
            throw new IllegalArgumentException("User requires a profile image url");
        }

        Integer goldBadgesCount = values.getAsInteger(UserEntry.GOLD_BADGES_COUNT);
        if (goldBadgesCount == null) {
            throw new IllegalArgumentException("User requires gold badges count");
        }

        Integer silverBadgesCount = values.getAsInteger(UserEntry.SILVER_BADGES_COUNT);
        if (silverBadgesCount == null) {
            throw new IllegalArgumentException("User requires silver badges count");
        }

        Integer bronzeBadgesCount = values.getAsInteger(UserEntry.BRONZE_BADGES_COUNT);
        if (bronzeBadgesCount == null) {
            throw new IllegalArgumentException("User requires bronze badges count");
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(UserEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_ID:
                // Delete a single row given by the ID in the URI
                selection = UserEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return UserEntry.CONTENT_LIST_TYPE;
            case USER_ID:
                return UserEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
