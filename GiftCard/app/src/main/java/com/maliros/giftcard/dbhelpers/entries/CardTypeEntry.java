package com.maliros.giftcard.dbhelpers.entries;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.maliros.giftcard.dbhelpers.GCDatabaseContract;

import static com.maliros.giftcard.dbhelpers.GCDatabaseContract.BASE_CONTENT_URI;
import static com.maliros.giftcard.dbhelpers.GCDatabaseContract.PATH_CARD_TYPE;

/**
 * Created by user on 31/05/2016.
 */
public class CardTypeEntry implements BaseColumns {
    public static final String CARD_TYPE_TBL = "cardType";
    public static final String NAME = "name";
    public static final String FOR_SPECIFIC_STORE = "forSpecificStore";

    // aliases
    public static final String FULL_NAME_ALIAS = GCDatabaseContract.CARD_TYPE_ALIAS + "." + NAME;

    // Content URI represents the base location for the table
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_CARD_TYPE).build();

    // These are special type prefixes that specify if a URI returns a list or a specific item
    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + PATH_CARD_TYPE;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_CARD_TYPE;

    // Define a function to build a URI to find a specific card_type by it's identifier
    public static Uri buildStoreUri(long id){
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }


}

