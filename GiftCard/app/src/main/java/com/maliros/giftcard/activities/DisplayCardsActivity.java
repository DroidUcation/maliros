package com.maliros.giftcard.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.maliros.giftcard.R;
import com.maliros.giftcard.adapters.CardDisplayAdapter;
import com.maliros.giftcard.dbhelpers.GCDatabaseContract;
import com.maliros.giftcard.dbhelpers.entries.CardEntry;

public class DisplayCardsActivity extends BaseActivity {
    String[] Company = {"Apple", "Genpack", "Microsoft", "HP", "HCL", "Ericsson"};
    private int typeIndex = 1;
    private String typeAppend = "";
    private GridLayoutManager lLayout;
    Button btnOpenPopup;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);
//        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/mytruetypefont.ttf");
//        myTextView.setTypeface(typeFace);

        SpannableString s = new SpannableString("My Cards");
        s.setSpan(new TypefaceSpan("fonts/Bombing.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        setTitle(R.string.gift_cared);

        btnOpenPopup = (Button) findViewById(R.id.btn_update_balance);
        int columnsNumber = 1;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnsNumber = 2;
        }
        lLayout = new GridLayoutManager(DisplayCardsActivity.this, columnsNumber);
        // 1. get a reference to recyclerView
        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        // 2. set layoutManger
        rView.setLayoutManager(lLayout);
        // 3. create an adapter
        CardDisplayAdapter cardDisplayAdapter = new CardDisplayAdapter(this, getCardsCursor());
        // 4. set adapter
        rView.setAdapter(cardDisplayAdapter);
    }

    private Cursor getCardsCursor() {
        return getContentResolver().query(GCDatabaseContract.CONTENT_URI_CARD_JOIN_CARD_TYPE, null, null, null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        id = item.getItemId();

        if (id == R.id.action_search) {
            // Calls Custom Searchable Activity
            Intent intent = new Intent(this, SearchCardsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addCard(View view) {
        Intent intent = new Intent(this, AddCardActivity.class);
        intent.putExtra(CardEntry._ID, -1);
        startActivity(intent);
    }
}
