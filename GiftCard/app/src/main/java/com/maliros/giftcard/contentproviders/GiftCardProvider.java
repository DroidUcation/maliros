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
import com.maliros.giftcard.dbhelpers.entries.CardTypeEntry;
import com.maliros.giftcard.dbhelpers.entries.StoreCardTypeEntry;
import com.maliros.giftcard.dbhelpers.entries.StoreEntry;
import com.maliros.giftcard.entities.Store;

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

    private SQLiteDatabase sqLiteDatabase;
    private GCDatabaseHelper gcDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        Log.d("**********in on create", "********");
        Context context = getContext();
        gcDbHelper = new GCDatabaseHelper(context);
        sqLiteDatabase = gcDbHelper.getWritableDatabase();
        gcDbHelper.onUpgrade(sqLiteDatabase, 1, 2); // call on upgrade for recreating DB TODO: impl a better solution
        return (sqLiteDatabase != null);
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
                return StoreCardTypeEntry.STORE_CARD_TYPE_TBL;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    /**
     * insert specific store
     * @param store
     * @return
     */
    public long insertStore(Uri uri, Store store){
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
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        rows = db.delete(tableName, selection, selectionArgs);

        // Because null could delete all rows:
        if (selection == null || rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = gcDbHelper.getWritableDatabase();
        int rows; // Number of rows effected
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
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        rows = db.update(tableName, values, selection, selectionArgs);

        // Because null could delete all rows:
        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }
}
