package com.maliros.giftcard.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maliros.giftcard.R;

/**
 * Created by user on 25/06/2016.
 */
public class CardDisplayViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTxtView;
    public TextView balanceTxtView;
    public ImageView cardTypeImage;

    public CardDisplayViewHolder(View itemView) {
        super(itemView);
        nameTxtView = (TextView) itemView.findViewById(R.id.name);
        balanceTxtView = (TextView) itemView.findViewById(R.id.balance);
        cardTypeImage = (ImageView) itemView.findViewById(R.id.photo);
    }
}
