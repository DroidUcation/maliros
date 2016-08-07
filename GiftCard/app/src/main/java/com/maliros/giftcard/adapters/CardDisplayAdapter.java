package com.maliros.giftcard.adapters;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
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

    private Context context;

    public CardDisplayAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final CardDisplayViewHolder viewHolder, final Cursor cursor) {

        final StringBuilder numberAndCvv = new StringBuilder(cursor.getString(cursor.getColumnIndex(CardEntry.CARD_NUMBER)))
                .append(" / ")
                .append(cursor.getString(cursor.getColumnIndex(CardEntry.CVV)));
        viewHolder.numberCvvTxtView.setText(numberAndCvv);
        viewHolder.nameTxtView.setText(cursor.getString(cursor.getColumnIndex(CardTypeEntry.NAME)));
        String balance = cursor.getString(cursor.getColumnIndex(CardEntry.BALANCE));
        viewHolder.balanceTxtView.setText(balance + "$");
        viewHolder.cardId = cursor.getInt(cursor.getColumnIndex(CardEntry._ID));
        if (balance.equalsIgnoreCase("0")) {
            viewHolder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            viewHolder.deleteButton.setVisibility(View.INVISIBLE);
        }
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addYesNoDeleteDialog(viewHolder.cardId, viewHolder.getAdapterPosition(), cursor);
            }
        });
        Picasso.with(mContext)
                .load(cursor.getInt(cursor.getColumnIndex(CardTypeEntry.IMAGE)))
                .into(viewHolder.cardTypeImage);
//        viewHolder.cardTypeImage.setImageResource(cursor.getInt(cursor.getColumnIndex(CardTypeEntry.IMAGE)));
    }

    private void addYesNoDeleteDialog(final int cardId, final int position, final Cursor cursor) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setMessage("Are you sure You want to delete this card?")
                .setTitle("Delete Card");
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Uri url = ContentUris.withAppendedId(CardEntry.CONTENT_URI, cardId);
                        Log.d("uri=", url.toString());
                        context.getContentResolver().delete(url, null, null);
                        CardDisplayAdapter.this.notifyItemChanged(position);
                        cursor.requery();
                        notifyDataSetChanged();
                        CardDisplayAdapter.this.notifyItemRemoved(position);
                        CardDisplayAdapter.this.notifyItemRangeChanged(position, getItemCount());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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


