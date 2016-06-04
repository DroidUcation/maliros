package com.maliros.giftcard.recyclerviewgridview;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maliros.giftcard.R;
import com.maliros.giftcard.activities.AddCardActivity;

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


   // @OnClick(R.id.text_fade_fast)
    @Override
    public void onClick(View view) {
        Intent itemIntent = new Intent(view.getContext(), AddCardActivity.class);
      /*  itemIntent.putExtra(
                AddCardActivity.EXTRA_TRANSITION, AddCardActivity.TRANSITION_FADE_FAST);*/

       //startActivityWithOptions(itemIntent);
        view.getContext().startActivity(itemIntent);
    }
/*
    private void startActivityWithOptions(Intent itemIntent) {
        ActivityOptions transitionActivity =
                ActivityOptions.makeSceneTransitionAnimation(DisplayCardsActivity.class);
        startActivity(intent, AddCardActivity.toBundle());
    }*/


}