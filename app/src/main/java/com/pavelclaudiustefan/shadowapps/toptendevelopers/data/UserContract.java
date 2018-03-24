package com.pavelclaudiustefan.shadowapps.toptendevelopers.data;

/**
 * Created by Claudiu on 23-Mar-18.
 */

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class UserContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private UserContract() {}

    public static final String CONTENT_AUTHORITY = "com.pavelclaudiustefan.shadowapps.toptendevelopers";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USERS = "users";

    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static final String TABLE_NAME = "users";

        public static final String _ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String PROFILE_IMAGE_URL = "profile_image_url";
        public static final String LOCATION = "location";
        public static final String GOLD_BADGES_COUNT = "gold_badges_count";
        public static final String SILVER_BADGES_COUNT = "silver_badges_count";
        public static final String BRONZE_BADGES_COUNT = "bronze_badges_count";

    }

}

