package com.maliros.giftcard.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maliros.giftcard.R;
import com.maliros.giftcard.dbhelpers.GCDatabaseContract;
import com.maliros.giftcard.dbhelpers.entries.CardEntry;
import com.maliros.giftcard.dbhelpers.entries.CardTypeEntry;
import com.maliros.giftcard.dbhelpers.entries.StoreEntry;
import com.maliros.giftcard.recyclerviewgridview.CursorRecyclerViewAdapter;
import com.maliros.giftcard.style.DividerItemDecoration;

public class SearchCardsResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards_result);

        // 1. get a reference to recyclerView 
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 3. create an adapter 
        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(this, getAllItemList());
        // 4. set adapter
        recyclerView.setAdapter(searchResultAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // set item decoration
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.spinner_bg,
                true, true));
    }

    private Cursor getAllItemList() {
        String[] projection = {CardTypeEntry.NAME, CardEntry.BALANCE};
        String selection = StoreEntry.FULL_NAME_ALIAS + " = ?";
        Log.d("**", getIntent().getExtras().getString(StoreEntry.NAME));
        String[] selectionArgs = {getIntent().getExtras().getString(StoreEntry.NAME)};
        return getContentResolver().query(GCDatabaseContract.CONTENT_URI_CARD_JOIN_CARD_TYPE_STORE, projection, selection, selectionArgs, null);
    }

    class SearchResultAdapter extends CursorRecyclerViewAdapter<ViewHolder> {

        public SearchResultAdapter(Context context, Cursor cursor) {
            super(context, cursor);
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            // create a new view
            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_search_result_row, null);

            // create ViewHolder

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

            // - get data from your itemsData at this position
            // - replace the contents of the view with that itemsData
            viewHolder.nameViewTitle.setText(cursor.getString(cursor.getColumnIndex(CardTypeEntry.NAME)));
            viewHolder.balanceViewTitle.setText(cursor.getString(cursor.getColumnIndex(CardEntry.BALANCE)));
        }
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameViewTitle;
        public TextView balanceViewTitle;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            nameViewTitle = (TextView) itemLayoutView.findViewById(R.id.result_name_text);
            balanceViewTitle = (TextView) itemLayoutView.findViewById(R.id.result_balance_text);
        }
    }

}
