package com.maliros.giftcard.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

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
        setTitle(null);
        btnOpenPopup = (Button) findViewById(R.id.btn_update);
        lLayout = new GridLayoutManager(DisplayCardsActivity.this, 2);

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
        startActivity(intent);
    }

    public void updateBalanceCard(View view) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        Button btnSave = (Button) popupView.findViewById(R.id.btn_save);
        Button btnDismiss = (Button) popupView.findViewById(R.id.btn_cancel);
       /* Spinner popupSpinner = (Spinner) popupView.findViewById(R.id.popupspinner);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(DisplayCardsActivity.this,
                        android.R.layout.simple_spinner_item, Company);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        popupSpinner.setAdapter(adapter);
        popupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                typeIndex = position + 1;
                typeAppend = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/
        btnDismiss.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(btnOpenPopup, 250, 500);
        btnSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                Intent intent = new Intent(v.getContext(), AddCardActivity.class);
                intent.putExtra(CardEntry.CARD_TYPE_ID,id);
                Log.d("Display**", CardEntry.CARD_TYPE_ID);
                startActivity(intent);

            }
        });

    }

}
