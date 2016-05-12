package com.maliros.fivethings.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;

import com.maliros.fivethings.R;
import com.maliros.fivethings.contentproviders.FactsProvider;
import com.maliros.fivethings.enums.FactName;
import com.maliros.fivethings.enums.ImageId;

import static com.maliros.fivethings.dbhelpers.FactsDBContract.FACT_NAME;
import static com.maliros.fivethings.dbhelpers.FactsDBContract.IMAGE_ID;
import static com.maliros.fivethings.dbhelpers.FactsDBContract.TEXT;


/**
 * Created by ליברמן on 09/05/2016.
 */
public class DBService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public DBService() {
        super("DBService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Fill data with new facts
        fillTheDB();
    }

    private void fillTheDB() {
        getContentResolver().insert(FactsProvider.CONTENT_URI, createFactContentValues(FactName.ARABIC, getString(R.string.arabic_fact_data), ImageId.ARABIC));
        getContentResolver().insert(FactsProvider.CONTENT_URI, createFactContentValues(FactName.GOATS, getString(R.string.goats_fact_data), ImageId.GOATS));
        getContentResolver().insert(FactsProvider.CONTENT_URI, createFactContentValues(FactName.CAPPUCCINO, getString(R.string.cappuccino_fact_data), ImageId.CAPPUCCINO));
        getContentResolver().insert(FactsProvider.CONTENT_URI, createFactContentValues(FactName.SPORT, getString(R.string.sport_fact_data), ImageId.SPORT));
        getContentResolver().insert(FactsProvider.CONTENT_URI, createFactContentValues(FactName.FRUIT, getString(R.string.fruit_fact_data), ImageId.FRUIT));
    }

    private ContentValues createFactContentValues(FactName factName, String factText, ImageId imageId){
        ContentValues values = new ContentValues();
        values.put(FACT_NAME, factName.getId());
        values.put(TEXT, factText);
        values.put(IMAGE_ID, imageId.getId());
        return values;
    }

}