package com.maliros.giftcard.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maliros.giftcard.R;
import com.maliros.giftcard.recyclerviewgridview.ItemObject;
import com.maliros.giftcard.recyclerviewgridview.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class DisplayCardsActivity extends AppCompatActivity {

    private GridLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);
        setTitle(null);


        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(DisplayCardsActivity.this, 2);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(DisplayCardsActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("200","Gift Card 1", R.drawable.gift_card1));
        allItems.add(new ItemObject("300","Gift Card 2", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 3", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 4", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 5", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 6", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 7", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 8", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 9", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 10", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 11", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 12", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 13", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 14", R.drawable.gift_card1));
        allItems.add(new ItemObject("100","Gift Card 15", R.drawable.gift_card1));
        allItems.add(new ItemObject("150","Gift Card 16", R.drawable.gift_card1));

        return allItems;
    }
}
