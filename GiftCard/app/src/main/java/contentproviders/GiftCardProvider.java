package contentproviders;

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

import com.maliros.giftcard.dbhelpers.DatabaseHelper;

import static com.maliros.giftcard.dbhelpers.FactsDBContract.FACTS_TBL;


/**
 * Created by user on 04/05/2016.
 */
public class GiftCardProvider extends ContentProvider{

    private static final String PROVIDER_NAME = "com.maliros.fivethings.contentproviders.FactsProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/facts");
    private static final int FACTS = 1;
    private static final int FACT_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();

    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper dbHelper;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "facts", FACTS);
        uriMatcher.addURI(PROVIDER_NAME, "facts/#", FACT_ID);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DatabaseHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        //dbHelper.onUpgrade(sqLiteDatabase, 1, 2); // call on upgrade for recreating DB
        return (sqLiteDatabase != null);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(FACTS_TBL);
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case FACTS:
                return "vnd.android.cursor.dir/vnd.com.maliros.fivethings.contentproviders.FactsProvider.facts";
            case FACT_ID:
                return "vnd.android.cursor.item/vnd.com.maliros.fivethings.contentproviders.FactsProvider.facts";

        }
        return "";    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = sqLiteDatabase.insert(FACTS_TBL, "", values);
        Uri _uri = ContentUris.withAppendedId(uri, rowID);
        getContext().getContentResolver().notifyChange(_uri, null);
        return _uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return sqLiteDatabase.delete(FACTS_TBL,selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
