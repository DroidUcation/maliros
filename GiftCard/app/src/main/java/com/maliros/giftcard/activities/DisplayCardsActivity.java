package com.maliros.giftcard.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.Spinner;


import com.maliros.giftcard.R;
import com.maliros.giftcard.recyclerviewgridview.ItemObject;
import com.maliros.giftcard.recyclerviewgridview.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class DisplayCardsActivity extends BaseActivity {
    String[] Company = {"Apple","Genpack","Microsoft","HP","HCL","Ericsson"};
    private int typeIndex = 1;
    private String typeAppend = "";
    private GridLayoutManager lLayout;
    Button btnOpenPopup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);
        setTitle(null);
        btnOpenPopup = (Button) findViewById(R.id.btn_update);
        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(DisplayCardsActivity.this, 2);

        // 1. get a reference to recyclerView
        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        // 2. set layoutManger
        rView.setLayoutManager(lLayout);
        // 3. create an adapter
        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(DisplayCardsActivity.this, rowListItem);
        // 4. set adapter
        rView.setAdapter(rcAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            // Calls Custom Searchable Activity
            Intent intent = new Intent(this, SearchCardsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<ItemObject> getAllItemList() {

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("200", "Gift Card 1", R.drawable.gift_card1));
        allItems.add(new ItemObject("300", "Gift Card 2", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 3", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 4", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 5", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 6", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 7", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 8", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 9", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 10", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 11", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 12", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 13", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 14", R.drawable.gift_card1));
        allItems.add(new ItemObject("100", "Gift Card 15", R.drawable.gift_card1));
        allItems.add(new ItemObject("150", "Gift Card 16", R.drawable.gift_card1));

        return allItems;
    }

    public void  addCard(View view){
        Intent intent = new Intent(this,AddCardActivity.class);
        startActivity(intent);
    }
    public void  updateBalanceCard(View view){
        LayoutInflater layoutInflater =
                (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        Button btnSave = (Button)popupView.findViewById(R.id.btn_save);
        Button btnDismiss = (Button)popupView.findViewById(R.id.btn_cancel);
        Spinner popupSpinner = (Spinner)popupView.findViewById(R.id.popupspinner);
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
        });
        btnDismiss.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }});
        popupWindow.showAsDropDown(btnOpenPopup, 250,500);
        btnSave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                Intent intent = new Intent(v.getContext(),UpdateBalanceActivity.class);
                startActivity(intent);
            }});

    }

}
