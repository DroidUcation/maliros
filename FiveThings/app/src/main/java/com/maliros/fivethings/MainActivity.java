package com.maliros.fivethings;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.maliros.fivethings.contentproviders.FactsProvider;
import com.maliros.fivethings.enums.FactName;
import com.maliros.fivethings.enums.ImageId;

import static com.maliros.fivethings.dbhelpers.FactsDBContract.FACT_NAME;
import static com.maliros.fivethings.dbhelpers.FactsDBContract.IMAGE_ID;
import static com.maliros.fivethings.dbhelpers.FactsDBContract.TEXT;


public class MainActivity extends AppCompatActivity {

    private boolean dataInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize facts table
        if(!dataInserted){
            insertFactsToDB();
            dataInserted = true; // insert data only once.TODO: need to save in Bundle savedInstanceState
        }
    }

    private void insertFactsToDB(){
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

    public void displayFacts(View view) {
        // display first fact activity: goats fact
        Intent intent = new Intent(this, GoatsFactActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
