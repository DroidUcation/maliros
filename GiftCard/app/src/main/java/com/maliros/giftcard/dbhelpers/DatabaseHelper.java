package com.maliros.giftcard.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.maliros.giftcard.dbhelpers.FactsDBContract.*;

/**
 * Created by user on 18/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_TABLE = "facts.db";
    private static final int    DATABASE_VERSION = 1;
    private static final String SQL_DROP_FACTS_TABLE =
            "DROP TABLE IF EXISTS " + FACTS_TBL;

    private static final String SQL_CREATE_TABLE_CARD_TYPE = "CREATE TABLE "
            + FACTS_TBL + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TEXT
            + " TEXT," + FACT_NAME + " TEXT," + IMAGE_ID + " INTEGER " + ")";

    private static final String SQL_CREATE_TABLE_SHOP = "CREATE TABLE "
            + FACTS_TBL + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TEXT
            + " TEXT," + FACT_NAME + " TEXT," + IMAGE_ID + " INTEGER " + ")";

    private static final String SQL_CREATE_TABLE_CARD_TYPE_SHOP = "CREATE TABLE "
            + FACTS_TBL + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TEXT
            + " TEXT," + FACT_NAME + " TEXT," + IMAGE_ID + " INTEGER " + ")";


    public DatabaseHelper(Context context) {
        super(context, FACTS_TBL, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create db tables
        db.execSQL(SQL_CREATE_TABLE_CARD_TYPE);
        db.execSQL(SQL_CREATE_TABLE_SHOP);
        db.execSQL(SQL_CREATE_TABLE_CARD_TYPE_SHOP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop and recreate DB
        db.execSQL(SQL_DROP_FACTS_TABLE);
        onCreate(db);
    }

    /*public Fact getData(FactName factName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + FACTS_TBL + " WHERE "
                + FACT_NAME + " = '" + factName.getId() +"'";

        Cursor c = db.rawQuery(selectQuery, null);

        Fact fact = null;
        if (c != null){
            c.moveToFirst();
            fact = new Fact();
            fact.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            fact.setText(c.getString(c.getColumnIndex(TEXT)));
        }

        return fact;
    }*/

    public long insertFact(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        // insert row
        long id = db.insert(FACTS_TBL, null, values);

        return id;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
