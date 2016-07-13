package com.maliros.giftcard.adapters.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maliros.giftcard.R;
import com.maliros.giftcard.activities.AddCardActivity;
import com.maliros.giftcard.dbhelpers.entries.CardEntry;

/**
 * Created by user on 25/06/2016.
 */
public class CardDisplayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView nameTxtView;
    public TextView numberCvvTxtView;
    public TextView balanceTxtView;
    public ImageView cardTypeImage;
    public int cardId;

    public CardDisplayViewHolder(View itemView) {
        super(itemView);
        nameTxtView = (TextView) itemView.findViewById(R.id.card_name);
        numberCvvTxtView = (TextView) itemView.findViewById(R.id.card_number_cvv);
        balanceTxtView = (TextView) itemView.findViewById(R.id.balance);
        cardTypeImage = (ImageView) itemView.findViewById(R.id.photo);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent itemIntent = new Intent(view.getContext(), AddCardActivity.class);
        itemIntent.putExtra(CardEntry._ID, cardId);
        view.getContext().startActivity(itemIntent);
    }
}
