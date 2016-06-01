package com.maliros.giftcard.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.maliros.giftcard.dbhelpers.entries.CardTypeEntry;
import com.maliros.giftcard.dbhelpers.entries.StoreCardTypeEntry;
import com.maliros.giftcard.dbhelpers.entries.StoreEntry;

/**
 * Created by user on 31/05/2016.
 */
public class GCDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "giftCard.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_STORE_TBL_SQL = "CREATE TABLE " + StoreEntry.STORE_TBL + " ( " +
            StoreEntry.KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            StoreEntry.NAME + " TEXT," +
            StoreEntry.IS_CHAIN_STORE + " SMALLINT )";

    private static final String SQL_CREATE_CARD_TYPE_TBL_SQL = "CREATE TABLE " + CardTypeEntry.CARD_TYPE_TBL + " ( " +
            CardTypeEntry.KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CardTypeEntry.NAME + " TEXT," +
            CardTypeEntry.FOR_SPECIFIC_STORE + " SMALLINT )";

    private static final String SQL_CREATE_STORE_CARD_TYPE_TBL_SQL = "CREATE TABLE " + StoreCardTypeEntry.STORE_CARD_TYPE_TBL + " ( " +
            StoreCardTypeEntry.STORE_KEY + " INTEGER , " +
            StoreCardTypeEntry.CARD_TYPE_KEY + " INTEGER )" ;

    private static final String SQL_DROP_STORE_TABLE =
            "DROP TABLE IF EXISTS " + StoreEntry.STORE_TBL;

    private static final String SQL_DROP_CARD_TYPE_TABLE =
            "DROP TABLE IF EXISTS " + CardTypeEntry.CARD_TYPE_TBL;

    private static final String SQL_DROP_STORE_CARD_TYPE_TABLE =
            "DROP TABLE IF EXISTS " + StoreCardTypeEntry.STORE_CARD_TYPE_TBL;

    public GCDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Constructor that init fixed db name and version
     *
     * @param context
     */
    public GCDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("**********in db helper", "********");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL(SQL_CREATE_STORE_TBL_SQL);
        db.execSQL(SQL_CREATE_CARD_TYPE_TBL_SQL);
        db.execSQL(SQL_CREATE_STORE_CARD_TYPE_TBL_SQL);
        // init data
        insertStoresData(db);
        insertCardTypesData(db);
        insertStoresCardTypesData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_STORE_TABLE);
        db.execSQL(SQL_DROP_CARD_TYPE_TABLE);
        db.execSQL(SQL_DROP_STORE_CARD_TYPE_TABLE);
        onCreate(db);
    }

    private void insertStoresData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(StoreEntry.NAME, "ZARA");
        values.put(StoreEntry.IS_CHAIN_STORE, 1);

        // insert row
        db.insert(StoreEntry.STORE_TBL, null, values);
    }

    private void insertCardTypesData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(CardTypeEntry.NAME, "Dream Card");
        values.put(CardTypeEntry.FOR_SPECIFIC_STORE, 0);
        db.insert(CardTypeEntry.CARD_TYPE_TBL, null, values);

        // another row
        values.clear();
        values.put(CardTypeEntry.NAME, "Gift Card- Isracard");
        values.put(CardTypeEntry.FOR_SPECIFIC_STORE, 1);
        db.insert(CardTypeEntry.CARD_TYPE_TBL, null, values);
    }


    private void insertStoresCardTypesData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(StoreCardTypeEntry.STORE_KEY, 1);
        values.put(StoreCardTypeEntry.CARD_TYPE_KEY, 1);

        // insert row
        db.insert(StoreCardTypeEntry.STORE_CARD_TYPE_TBL, null, values);
    }
}
