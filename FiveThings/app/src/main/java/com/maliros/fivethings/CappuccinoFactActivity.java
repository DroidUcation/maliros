package com.maliros.fivethings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CappuccinoFactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cappuccino_fact_activity);
    }

    public void displayNextFact(View view) {
        // display fifth fact activity: sport fact
        Intent intent = new Intent(this, SportFactActivity.class);
        this.startActivity(intent);
    }

    public void displayPreviousFact(View view) {
        // display third fact activity: fruit fact
        Intent intent = new Intent(this, FruitFactActivity.class);
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
