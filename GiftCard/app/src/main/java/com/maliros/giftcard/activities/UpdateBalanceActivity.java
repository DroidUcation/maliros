package com.maliros.giftcard.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.maliros.giftcard.R;

public class UpdateBalanceActivity extends AppCompatActivity  {

    TextView tvTypeOfCard, tvCount;

    private Button add;

    ArrayAdapter<String> adapter;
    private ListView AddValue;

    private ListView listView;
    RecyclerView recyclerView;
   // PlusButtonAdapter rcAdapter;
    //PlusButtonAdapter plusButtonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_balance);

//       tvTypeOfCard = (TextView) findViewById(R.id.tv_type_of_card);

        tvCount = (TextView) findViewById(R.id.tv_count);





       adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);


        AddValue = (ListView) findViewById(R.id.ListView);
        AddValue.setAdapter(adapter);

        add = (Button) findViewById(R.id.add);
//        LinearLayoutManager lLayout;
//        List<ItemObject2> rowListItem = getAllItemList();
//        lLayout = new LinearLayoutManager(UpdateBalanceActivity.this);
//
//        // 1. get a reference to recyclerView
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        // 2. set layoutManger
//        recyclerView.setLayoutManager(lLayout);
//        // 3. create an adapter
//         rcAdapter = new PlusButtonAdapter(UpdateBalanceActivity.this, rowListItem);
//        // 4. set adapter
//        recyclerView.setAdapter(rcAdapter);
//        // 5. set item animator to DefaultAnimator
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("michal 106","???????");
              /*  String val = edittext.getText().toString();
                list.add(val);*/
                //((ArrayAdapter<Object>) listView.getAdapter()).notifyDataSetChanged();
                       // rcAdapter.addItem((new ItemObject2("DREM CARD",50)));
                        //recyclerView.getAdapter().notifyDataSetChanged();//TODO : COUNT OF CARD'S USER
                    }});


           }
/*    private List<ItemObject> getAllItemList() {

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("200", "Gift Card 1", R.drawable.gift_card1));


        return allItems;
    }*/
    }


/*

    class PlusButtonAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
        private List<ItemObject2> itemList;
        private Context context;

        public PlusButtonAdapter(Context context, List<ItemObject2> itemList) {

            this.itemList = itemList;
            this.context = context;

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

           holder.tvTypeOfCard.setText((itemList.get(position).getTypeOfCard()));
           holder.tvCount.setText((itemList.get(position).getTvCountUsed()));

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

        public TextView tvTypeOfCard;
        public TextView tvCount;

        public RecyclerViewHolders(View itemView) {
            super(itemView);

            tvTypeOfCard = (TextView) itemView.findViewById(R.id.tv_type_of_card);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);

        }
    }*/







