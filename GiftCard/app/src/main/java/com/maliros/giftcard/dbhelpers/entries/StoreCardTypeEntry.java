package com.maliros.giftcard.dbhelpers.entries;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.maliros.giftcard.dbhelpers.GCDatabaseContract.BASE_CONTENT_URI;
import static com.maliros.giftcard.dbhelpers.GCDatabaseContract.PATH_STORE_CARD_TYPE;

/**
 * Created by user on 31/05/2016.
 * This table is -many to many- of store and cardType tables
 */
public class StoreCardTypeEntry implements BaseColumns {
    public static final String STORE_CARD_TYPE_TBL = "storeCardType";
    public static final String CARD_TYPE_KEY = "cardTypeKey";
    public static final String STORE_KEY = "storeKey";

    // Content URI represents the base location for the table
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_STORE_CARD_TYPE).build();

    // These are special type prefixes that specify if a URI returns a list or a specific item
    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + PATH_STORE_CARD_TYPE;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_STORE_CARD_TYPE;

    // Define a function to build a URI to find a specific store by it's identifier
    public static Uri buildStoreUri(long id){
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }


}

