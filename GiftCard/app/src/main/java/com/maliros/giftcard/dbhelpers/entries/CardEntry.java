package com.maliros.giftcard.dbhelpers.entries;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.maliros.giftcard.dbhelpers.GCDatabaseContract;

import static com.maliros.giftcard.dbhelpers.GCDatabaseContract.BASE_CONTENT_URI;
import static com.maliros.giftcard.dbhelpers.GCDatabaseContract.PATH_CARD;

/**
 * Created by user on 31/05/2016.
 * User has instances of cards
 */
public class CardEntry implements BaseColumns {
    public static final String CARD_TBL = "card";
    public static final String CARD_TYPE_ID = "cardTypeId";
    public static final String IS_FOR_UNIQUE_STORE = "isForUniqueStore";
    public static final String UNIQUE_STORE_NAME = "uniqueStoreName";
    public static final String BALANCE = "balance";
    public static final String EXPIRATION_DATE = "expirationDate";
    public static final String USER_ID = "userId";

    // aliases
    public static final String FULL_BALANCE_ALIAS = GCDatabaseContract.CARD_ALIAS + "." + BALANCE;
    public static final String FULL_ID_ALIAS = GCDatabaseContract.CARD_ALIAS + "." + _ID;

    // Content URI represents the base location for the table
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_CARD).build();

    // These are special type prefixes that specify if a URI returns a list or a specific item
    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + PATH_CARD;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_CARD;

    // Define a function to build a URI to find a specific card by it's identifier
    public static Uri buildStoreUri(long id){
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }


}






