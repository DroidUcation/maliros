package com.maliros.giftcard.dbhelpers;

import android.net.Uri;

/**
 * Created by user on 05/05/2016.
 */
public class GCDatabaseContract {

    // base authority(provider path)
    public static final String CONTENT_AUTHORITY = "com.maliros.giftcard.contentproviders.GiftCardProvider";
    // base uri
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * A list of possible paths that will be appended to the base URI for each of the different
     * tables.
     */
    public static final String PATH_STORE = "store";
    public static final String PATH_CARD_TYPE = "cardType";

}
