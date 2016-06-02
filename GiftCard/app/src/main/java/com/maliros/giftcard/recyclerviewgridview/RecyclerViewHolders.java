package com.maliros.giftcard.recyclerviewgridview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maliros.giftcard.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView name;
    public TextView balance;
    public ImageView photo;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        name = (TextView)itemView.findViewById(R.id.name);
        balance = (TextView)itemView.findViewById(R.id.balance);
        photo = (ImageView)itemView.findViewById(R.id.photo);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }

}