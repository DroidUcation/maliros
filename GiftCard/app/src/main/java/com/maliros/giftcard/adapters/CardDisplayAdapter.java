package com.maliros.giftcard.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maliros.giftcard.R;
import com.maliros.giftcard.adapters.viewholders.CardDisplayViewHolder;
import com.maliros.giftcard.dbhelpers.entries.CardEntry;
import com.maliros.giftcard.dbhelpers.entries.CardTypeEntry;
import com.maliros.giftcard.recyclerviewgridview.CursorRecyclerViewAdapter;
import com.squareup.picasso.Picasso;


/**
 * Created by user on 25/06/2016.
 */
public class CardDisplayAdapter extends CursorRecyclerViewAdapter<CardDisplayViewHolder> {

    public CardDisplayAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(CardDisplayViewHolder viewHolder, Cursor cursor) {
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.nameTxtView.setText(cursor.getString(cursor.getColumnIndex(CardTypeEntry.NAME)));
        viewHolder.balanceTxtView.setText(cursor.getString(cursor.getColumnIndex(CardEntry.BALANCE)));
        Picasso.with(mContext)
                .load(cursor.getInt(cursor.getColumnIndex(CardTypeEntry.IMAGE)))
//                .placeholder(R.drawable.ic_action_new)
                .into(viewHolder.cardTypeImage);
//        viewHolder.cardTypeImage.setImageResource(cursor.getInt(cursor.getColumnIndex(CardTypeEntry.IMAGE)));
    }

    @Override
    public CardDisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_list, null);

        // create ViewHolder
        CardDisplayViewHolder viewHolder = new CardDisplayViewHolder(itemLayoutView);
        return viewHolder;
    }

}


