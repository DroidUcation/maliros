package com.maliros.giftcard.dbhelpers;

import android.net.Uri;

/**
 * Created by user on 05/05/2016.
 */
public class GCDatabaseContract {

    /**
     * A list of possible paths that will be appended to the base URI for each of the different
     * tables.
     */
    public static final String PATH_STORE = "store";
    public static final String PATH_CARD_TYPE = "cardType";
    public static final String PATH_STORE_CARD_TYPE = "storeCardType";
    public static final String PATH_CARD = "card";
    public static final String PATH_USER = "user";
    public static final String PATH_CARD_JOIN_CARD_TYPE = "cardJoinCardType";

    // tables aliases
    public static final String CARD_ALIAS = "c";
    public static final String CARD_TYPE_ALIAS = "ct";
    public static final String STORE_ALIAS = "s";
    public static final String STORE_CARD_TYPE_ALIAS = "sct";

    // base authority(provider path)
    public static final String CONTENT_AUTHORITY = "com.maliros.giftcard.contentproviders.GiftCardProvider";

    // base uri
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // uri of joined tables
    public static final Uri CONTENT_URI_CARD_JOIN_CARD_TYPE = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CARD_JOIN_CARD_TYPE).build();

}

