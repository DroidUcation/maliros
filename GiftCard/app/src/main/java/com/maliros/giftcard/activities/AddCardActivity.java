package com.maliros.giftcard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.maliros.giftcard.R;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Intent intent = new Intent(this, DisplayCardsActivity.class);
        this.startActivity(intent);
    }
}
