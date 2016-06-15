package com.maliros.giftcard.contentproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.maliros.giftcard.dbhelpers.GCDatabaseContract;
import com.maliros.giftcard.dbhelpers.GCDatabaseHelper;
import com.maliros.giftcard.dbhelpers.entries.CardEntry;
import com.maliros.giftcard.dbhelpers.entries.CardTypeEntry;
import com.maliros.giftcard.dbhelpers.entries.StoreCardTypeEntry;
import com.maliros.giftcard.dbhelpers.entries.StoreEntry;
import com.maliros.giftcard.dbhelpers.entries.UserEntry;
import com.maliros.giftcard.entities.Store;

import java.util.HashMap;
import java.util.Map;

import static com.maliros.giftcard.dbhelpers.entries.StoreEntry.IS_CHAIN_STORE;
import static com.maliros.giftcard.dbhelpers.entries.StoreEntry.NAME;
import static com.maliros.giftcard.dbhelpers.entries.StoreEntry.STORE_TBL;

/**
 * Created by user on 31/05/2016.
 */
public class GiftCardProvider extends ContentProvider {

    private static final int STORE = 1;
    private static final int STORE_ID = 2;
    private static final int CARD_TYPE = 3;
    private static final int CARD_TYPE_ID = 4;
    private static final int STORE_CARD_TYPE = 5;
    private static final int USER = 6;
    private static final int USER_ID = 7;
    private static final int CARD = 8;
    private static final int CARD_ID = 9;
    private static final int CARD_AND_CARD_TYPE = 10;

    private SQLiteDatabase sqLiteDatabase;
    private GCDatabaseHelper gcDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final Map<String, String> storeNameCardProjectionsMap = buildStoreCardProjMap();

    @Override
    public boolean onCreate() {
        Log.d("**********in on create", "********");
        Context context = getContext();
        gcDbHelper = new GCDatabaseHelper(context);
        sqLiteDatabase = gcDbHelper.getWritableDatabase();
        gcDbHelper.onUpgrade(sqLiteDatabase, 1, 2); // call on upgrade for recreating DB TODO: impl a better solution
        return (sqLiteDatabase != null);
    }

    private static Map<String,String> buildStoreCardProjMap() {
        Map<String, String> projectionsMap = new HashMap<>();
        projectionsMap.put(StoreEntry.NAME, StoreEntry.FULL_NAME_ALIAS);
        projectionsMap.put(CardTypeEntry.NAME, CardTypeEntry.FULL_NAME_ALIAS);
        projectionsMap.put(CardEntry.BALANCE, CardEntry.FULL_BALANCE_ALIAS);
        return projectionsMap;
    }

    /**
     * Builds a UriMatcher that is used to determine witch database request is being made.
     */
    public static UriMatcher buildUriMatcher() {
        String content = GCDatabaseContract.CONTENT_AUTHORITY;
        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, GCDatabaseContract.PATH_STORE, STORE);
        matcher.addURI(content, GCDatabaseContract.PATH_STORE + "/#", STORE_ID);
        matcher.addURI(content, GCDatabaseContract.PATH_CARD_TYPE, CARD_TYPE);
        matcher.addURI(content, GCDatabaseContract.PATH_CARD_TYPE + "/#", CARD_TYPE_ID);
        matcher.addURI(content, GCDatabaseContract.PATH_STORE_CARD_TYPE, STORE_CARD_TYPE);
        matcher.addURI(content, GCDatabaseContract.PATH_USER, USER);
        matcher.addURI(content, GCDatabaseContract.PATH_USER + "/#", USER_ID);
        matcher.addURI(content, GCDatabaseContract.PATH_CARD, CARD);
        matcher.addURI(content, GCDatabaseContract.PATH_CARD + "/#", CARD_ID);
        matcher.addURI(content, GCDatabaseContract.PATH_CARD_JOIN_CARD_TYPE, CARD_AND_CARD_TYPE);
        return matcher;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        long _id;
        switch (sUriMatcher.match(uri)) {
            case STORE:
                sqLiteQueryBuilder.setTables(StoreEntry.STORE_TBL);
                break;
            case STORE_ID:
                sqLiteQueryBuilder.setTables(StoreEntry.STORE_TBL);
                _id = ContentUris.parseId(uri);
                selection = StoreEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(_id)};
            case CARD_TYPE:
                sqLiteQueryBuilder.setTables(CardTypeEntry.CARD_TYPE_TBL);
                break;
            case CARD_TYPE_ID:
                sqLiteQueryBuilder.setTables(CardTypeEntry.CARD_TYPE_TBL);
                _id = ContentUris.parseId(uri);
                selection = CardTypeEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(_id)};
                break;
            case STORE_CARD_TYPE:
                sqLiteQueryBuilder.setTables(StoreCardTypeEntry.STORE_CARD_TYPE_TBL);
                break;
            case USER_ID:
                _id = ContentUris.parseId(uri);
                selection = UserEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(_id)};
            case USER:
                sqLiteQueryBuilder.setTables(UserEntry.USER_TBL);
                break;
            case CARD_ID:
                _id = ContentUris.parseId(uri);
                selection = CardEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(_id)};
            case CARD:
                sqLiteQueryBuilder.setTables(CardEntry.CARD_TBL);
                break;
            case CARD_AND_CARD_TYPE: // join card, cardType, store and cardTypeStore tables
                sqLiteQueryBuilder.setTables(CardEntry.CARD_TBL  + " as c INNER JOIN " + CardTypeEntry.CARD_TYPE_TBL + " as ct"
                        + " ON c." + CardEntry.CARD_TYPE_KEY + " = ct." + CardTypeEntry.KEY
                        + " INNER JOIN " + StoreCardTypeEntry.STORE_CARD_TYPE_TBL + " as sct"
                        + " ON sct." + StoreCardTypeEntry.CARD_TYPE_KEY + " = ct." + CardTypeEntry.KEY
                        + " INNER JOIN " + StoreEntry.STORE_TBL + " as s"
                        + " ON s." + StoreEntry.KEY + " = sct." + StoreCardTypeEntry.STORE_KEY);
                sqLiteQueryBuilder.setProjectionMap(storeNameCardProjectionsMap);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case STORE:
                return StoreEntry.CONTENT_TYPE;
            case STORE_ID:
                return StoreEntry.CONTENT_ITEM_TYPE;
            case CARD_TYPE:
                return CardTypeEntry.CONTENT_TYPE;
            case CARD_TYPE_ID:
                return CardTypeEntry.CONTENT_ITEM_TYPE;
            case STORE_CARD_TYPE:
                return StoreCardTypeEntry.CONTENT_TYPE;
            case USER:
                return UserEntry.CONTENT_TYPE;
            case USER_ID:
                return UserEntry.CONTENT_ITEM_TYPE;
            case CARD:
                return CardEntry.CONTENT_TYPE;
            case CARD_ID:
                return CardEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = gcDbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        long row = db.insert(tableName, "", values);
        // If record is added successfully
        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(uri, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            Log.d("**successful insert!!! ", tableName);
            return newUri;
        }
        Log.d("*****B-A-D insert!!! ", tableName);
        return null;
    }

    /**
     * insert specific store
     *
     * @param store
     * @return
     */
    public long insertStore(Uri uri, Store store) {
        final SQLiteDatabase db = gcDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, store.getName());
        values.put(IS_CHAIN_STORE, store.isChainStore());
        getContext().getContentResolver().notifyChange(uri, null);
        return db.insert(STORE_TBL, null, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = gcDbHelper.getWritableDatabase();
        int rows; // Number of rows effected
        String tableName = getTableName(uri);
        rows = db.delete(tableName, selection, selectionArgs);

        // Because null could delete all rows:
        if (selection == null || rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    private String getTableName(Uri uri) {
        String tableName;
        switch (sUriMatcher.match(uri)) {
            case STORE:
            case STORE_ID:
                tableName = StoreEntry.STORE_TBL;
                break;
            case CARD_TYPE:
            case CARD_TYPE_ID:
                tableName = CardTypeEntry.CARD_TYPE_TBL;
                break;
            case STORE_CARD_TYPE:
                tableName = StoreCardTypeEntry.STORE_CARD_TYPE_TBL;
                break;
            case USER:
            case USER_ID:
                tableName = UserEntry.USER_TBL;
                break;
            case CARD:
            case CARD_ID:
                tableName = CardEntry.CARD_TBL;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return tableName;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = gcDbHelper.getWritableDatabase();
        int rows; // Number of rows effected
        String tableName = getTableName(uri);
        rows = db.update(tableName, values, selection, selectionArgs);

        // Because null could delete all rows:
        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;

    }
}

