package com.maliros.giftcard.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.maliros.giftcard.R;
import com.maliros.giftcard.entities.CardType;
import com.maliros.giftcard.recyclerviewgridview.ItemObject2;

import java.util.ArrayList;
import java.util.List;

public class UpdateBalanceActivity extends AppCompatActivity implements View.OnClickListener {
    private String typeAppend = "";
    TextView tvTypeOfCard, tvCount;
    private EditText etCountUsed;
    private Spinner typesSpinner;
    private Button add;
    ArrayAdapter<ItemObject2> adapter;
    public static boolean isFirstFocus = false;
    private int typeIndex = 1;
    ArrayAdapter<CardType> typesAdapter;
    RecyclerView recyclerView;
    PlusButtonAdapter plusButtonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_balance);
         //display first item without listView
        typesSpinner = (Spinner) findViewById(R.id.type_spinner);
      //  tvTypeOfCard = (TextView) findViewById(R.id.tv_type_of_card);
        etCountUsed = (EditText) findViewById(R.id.et_count_used);
        tvCount = (TextView) findViewById(R.id.tv_count);
        etCountUsed.setOnClickListener(this);

      /*  String[] projection = new String[]{CardTypeEntry.NAME, CardTypeEntry.KEY};
        Cursor cursor = getContentResolver().query(CardTypeEntry.CONTENT_URI, projection, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //typeSpinnerElements.add(cursor.getString(cursor.getColumnIndex(CardTypeEntry.NAME)));
                AddCardActivity.typeSpinnerElements.add(new CardType(false, cursor.getInt(cursor.getColumnIndex(CardTypeEntry.KEY)), cursor.getString(cursor.getColumnIndex(CardTypeEntry.NAME))));
            } while (cursor.moveToNext());
        }*/
        typesAdapter = new ArrayAdapter<CardType>(this,
                android.R.layout.simple_spinner_item, AddCardActivity.typeSpinnerElements);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(typesAdapter);
        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                typeIndex = position + 1;
                typeAppend = parent.getItemAtPosition(position).toString();
               // tvTypeOfCard.setText(typeAppend);
                Log.d("**********typeAppend : ", typeAppend);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


/*

        adapter = new ArrayAdapter<ItemObject2>(this,
                android.R.layout.simple_list_item_1);
*/

       /* AddValue = (ListView) findViewById(R.id.ListView);
        AddValue.setAdapter(adapter);*/

        add = (Button) findViewById(R.id.add);
        Log.d("michal 103","???????");
      //  List<ItemObject2> rowListItem = getAllItemList();
        Log.d("michal 104","???????");
     //   plusButtonAdapter = new PlusButtonAdapter(this, rowListItem);
        Log.d("michal 105","???????");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(UpdateBalanceActivity.this));
        plusButtonAdapter = new PlusButtonAdapter(UpdateBalanceActivity.this, getAllItemList(), getTypesSpinner());//getTypesSpinner());
        // 4. set adapter
        recyclerView.setAdapter(plusButtonAdapter);

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("michal 106","???????");
                // add item to list
                final Dialog dialog = new Dialog(UpdateBalanceActivity.this);

                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Custom Alert Dialog");
/*
                final EditText editText = (EditText) dialog.findViewById(R.id.editText);
                Button btnSave          = (Button) dialog.findViewById(R.id.save);
                Button btnCancel        = (Button) dialog.findViewById(R.id.cancel);
                dialog.show();
                plusButtonAdapter.addItem(new ItemObject2(null, null, null));*/
                // notify change
                recyclerView.getAdapter().notifyItemChanged(5);//TODO : COUNT OF CARD'S USER
            }
        });

    }

    // final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.search_box);
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == etCountUsed && !isFirstFocus) {
            etCountUsed.setText("");
            Log.d("Michal", " if( v == etCountUsed)");
            isFirstFocus = true;
        }
    }

    class PlusButtonAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
        private List<ItemObject2> itemList;
        private Context context;
        private Spinner spinner;
        public PlusButtonAdapter(Context context, List<ItemObject2> itemList,Spinner spinner) {

            this.itemList = itemList;
            this.context = context;
            this.spinner= spinner;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_update_row_list, null);
            // create RecyclerViewHolders
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {
            // - get data from your itemsData at this position
            // - replace the contents of the view with that itemsData
            //holder.typesSpinner.setAdapter(spinner);
            holder.etCountUsed.setText((itemList.get(position).getCountUsed()));
            holder.tvTypeOfCard.setText((itemList.get(position).getTypeOfCard()));
//            holder.tvCount.setText((itemList.get(position).getTvCountUsed()));
            holder.typesSpinner = spinner;
        }

        public void addItem(ItemObject2 itemObject){
            itemList.add(itemObject);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return this.itemList.size();
        }
    }

    // inner class to hold a reference to each item of RecyclerView
    public class RecyclerViewHolders extends RecyclerView.ViewHolder {


        public Spinner typesSpinner;
        public TextView tvTypeOfCard;
        public EditText etCountUsed;
        public TextView tvCount;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            typesSpinner = (Spinner) itemView.findViewById(R.id.type_spinner);
            tvTypeOfCard = (TextView) itemView.findViewById(R.id.tv_type_of_card);
            etCountUsed = (EditText) itemView.findViewById(R.id.et_count_used);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);

        }
    }
    private List<ItemObject2> getAllItemList() {

        List<ItemObject2> allItems = new ArrayList<ItemObject2>();
      /*  Log.d("michal:: tvTypeOfCard",tvTypeOfCard.getText().toString());
        Log.d("michal:: etCountUsed",etCountUsed.getText().toString());*/
//        allItems.add(new ItemObject2("Card", "Count used",etCountUsed.getText().toString()));

        return allItems;
    }

    public Spinner getTypesSpinner() {

      /*  String[] projection = new String[]{CardTypeEntry.NAME, CardTypeEntry.KEY};
        Cursor cursor = getContentResolver().query(CardTypeEntry.CONTENT_URI, projection, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //typeSpinnerElements.add(cursor.getString(cursor.getColumnIndex(CardTypeEntry.NAME)));
                AddCardActivity.typeSpinnerElements.add(new CardType(false, cursor.getInt(cursor.getColumnIndex(CardTypeEntry.KEY)), cursor.getString(cursor.getColumnIndex(CardTypeEntry.NAME))));
            } while (cursor.moveToNext());
        }*/
        typesAdapter = new ArrayAdapter<CardType>(this,
                android.R.layout.simple_spinner_item, AddCardActivity.typeSpinnerElements);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(typesAdapter);
        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                typeIndex = position + 1;
                typeAppend = parent.getItemAtPosition(position).toString();
               // tvTypeOfCard.setText(typeAppend);
                Log.d("**********typeAppend : ", typeAppend);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        return typesSpinner;
    }
}



