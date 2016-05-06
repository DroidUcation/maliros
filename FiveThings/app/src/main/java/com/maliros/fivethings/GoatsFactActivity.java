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

public class GoatsFactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goats_fact_activity);

        // get fact
        String[] mProjection = {FactsDBContract.TEXT, FactsDBContract.IMAGE_ID};
        String selection = FactsDBContract.FACT_NAME + " = ?";
        String[] selectionArgs = new String[]{FactName.GOATS.getId()};
        Cursor cursor = getContentResolver().query(FactsProvider.CONTENT_URI, mProjection, selection, selectionArgs, null);
        cursor.moveToFirst();
        // fact text
        TextView textView =(TextView) findViewById(R.id.goats_fact_text_view);
        textView.setText(cursor.getString(cursor.getColumnIndex(TEXT)));
        // fact image
        ImageView imageView =(ImageView) findViewById(R.id.goat_ethiopia_image);
        int imageId = cursor.getInt(cursor.getColumnIndex(IMAGE_ID));
        imageView.setImageResource(imageId);
    }

    public void displayNextFact(View view) {
        // display second fact activity: arabic fact
        Intent intent = new Intent(this, ArabicFactActivity.class);
        this.startActivity(intent);
    }

    public void displayStartPage(View view) {
        // display start page activity
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }


}
