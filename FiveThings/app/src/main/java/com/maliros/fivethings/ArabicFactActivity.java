package com.maliros.fivethings;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maliros.fivethings.contentproviders.FactsProvider;
import com.maliros.fivethings.dbhelpers.FactsDBContract;
import com.maliros.fivethings.enums.FactName;

import static com.maliros.fivethings.dbhelpers.FactsDBContract.IMAGE_ID;
import static com.maliros.fivethings.dbhelpers.FactsDBContract.TEXT;

public class ArabicFactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arabic_fact_activity);

        // get fact
        String[] mProjection = {FactsDBContract.TEXT, FactsDBContract.IMAGE_ID};
        String selection = FactsDBContract.FACT_NAME + " = ?";
        String[] selectionArgs = new String[]{FactName.ARABIC.getId()};
        Cursor cursor = getContentResolver().query(FactsProvider.CONTENT_URI, mProjection, selection, selectionArgs, null);
        cursor.moveToFirst();
        // fact text
        TextView textView =(TextView) findViewById(R.id.arabic_fact_text_view);
        textView.setText(cursor.getString(cursor.getColumnIndex(TEXT)));
        // fact image
        ImageView imageView =(ImageView) findViewById(R.id.arabic_image);
        int imageId = cursor.getInt(cursor.getColumnIndex(IMAGE_ID));
        imageView.setImageResource(imageId);
    }

    public void displayNextFact(View view) {
        // display third fact activity: fruit fact
        Intent intent = new Intent(this, FruitFactActivity.class);
        this.startActivity(intent);
    }

    public void displayPreviousFact(View view) {
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
