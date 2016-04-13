package com.maliros.fivethings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GoatsFactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goats_fact_activity);
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
