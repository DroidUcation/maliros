package com.maliros.giftcard.activities;

import android.content.Context;
import android.content.Intent;
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

    TextView balanceSumTv;
    private Double balanceSum = 0d;

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


//        balanceSumTv.setVisibility(balanceSum != 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private Cursor getAllItemList() {
        String[] projection = {CardTypeEntry.NAME, CardEntry.BALANCE, CardEntry.CARD_NUMBER, CardEntry.CVV, CardEntry._ID};
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
            StringBuilder numberAndCvv = new StringBuilder(cursor.getString(cursor.getColumnIndex(CardEntry.CARD_NUMBER)))
                    .append(" / ")
                    .append(cursor.getString(cursor.getColumnIndex(CardEntry.CVV)));
            viewHolder.numberCvvViewTitle.setText(numberAndCvv);
            String balance = cursor.getString(cursor.getColumnIndex(CardEntry.BALANCE));
            viewHolder.balanceViewTitle.setText(balance + "$");
            viewHolder.cardId = cursor.getInt(cursor.getColumnIndex(CardEntry._ID));

            balanceSum += Double.valueOf(balance);
            updateTotalBalanceSum();
        }

        private void updateTotalBalanceSum(){
            balanceSumTv = (TextView) findViewById(R.id.result_balance_sum);
            balanceSumTv.setText("Total:   " + balanceSum.toString() + "$");
        }
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameViewTitle;
        public TextView numberCvvViewTitle;
        public TextView balanceViewTitle;
        public int cardId;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            nameViewTitle = (TextView) itemLayoutView.findViewById(R.id.result_name_text);
            numberCvvViewTitle = (TextView) itemLayoutView.findViewById(R.id.result_number_cvv);
            balanceViewTitle = (TextView) itemLayoutView.findViewById(R.id.result_balance_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent itemIntent = new Intent(view.getContext(), AddCardActivity.class);
            itemIntent.putExtra(CardEntry._ID, cardId);
            view.getContext().startActivity(itemIntent);
        }
    }

}
