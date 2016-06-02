package com.maliros.giftcard.dbhelpers.entries;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.maliros.giftcard.dbhelpers.GCDatabaseContract.BASE_CONTENT_URI;
import static com.maliros.giftcard.dbhelpers.GCDatabaseContract.PATH_USER;

/**
 * Created by user on 31/05/2016.
 */
public class UserEntry implements BaseColumns {
    public static final String USER_TBL = "user";
    public static final String KEY = "key";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "last_name";
    public static final String PASSWORD = "passWord";
    public static final String EMAIL = "email";

    // Content URI represents the base location for the table
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

    // These are special type prefixes that specify if a URI returns a list or a specific item
    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + PATH_USER;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_USER;

    // Define a function to build a URI to find a specific user by it's identifier
    public static Uri buildStoreUri(long id){
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }


}






