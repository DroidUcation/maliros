package com.maliros.giftcard.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.TextView;

import com.maliros.giftcard.R;
import com.maliros.giftcard.dbhelpers.entries.StoreEntry;
import com.maliros.giftcard.recyclerviewgridview.ItemObject;

import java.util.ArrayList;
import java.util.List;

public class SearchCardsActivity extends AppCompatActivity {

    private List<ItemObject> itemObjects;
    private ArrayAdapter searchArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards);

        // adapter for search component
        itemObjects = getStores();
        searchArrayAdapter = getArrayAdapter();
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.search_box);
        autoCompleteTextView.setAdapter(searchArrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                Intent intent = new Intent(arg1.getContext(), SearchCardsResultActivity.class);
                Log.d("**", autoCompleteTextView.getText().toString());
                intent.putExtra(StoreEntry.NAME, autoCompleteTextView.getText().toString());
                startActivity(intent);
            }
        });
    }

    private List<ItemObject> getStores() {
        List<ItemObject> itemObjects = new ArrayList<>();
        Cursor cursor = getContentResolver().query(StoreEntry.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            Log.d("store ",cursor.getString(cursor.getColumnIndex(StoreEntry.NAME)));
            itemObjects.add(new ItemObject("0", cursor.getString(cursor.getColumnIndex(StoreEntry.NAME)), 0));
        }
        return itemObjects;
    }

    private ArrayAdapter getArrayAdapter() {
        ArrayAdapter<ItemObject> searchAdapter = new ArrayAdapter<ItemObject>(this, R.layout.activity_search_cards, R.id.gift_card_name_text) {
            private Filter filter;

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.card_search_row_details, parent, false);
                }

                TextView cardNameText = (TextView) convertView
                        .findViewById(R.id.gift_card_name_text);

                final ItemObject itemObject = this.getItem(position);
                convertView.setTag(itemObject);
                cardNameText.setText(itemObject.getName());

                return convertView;
            }

            @Override
            public Filter getFilter() {
                if (filter == null) {
                    filter = new SearchFilter();
                }
                return filter;
            }
        };

        return searchAdapter;
    }


    class SearchFilter extends Filter {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ItemObject) resultValue).getName();
        }

        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            List<ItemObject> list = itemObjects;
            FilterResults result = new FilterResults();
            String substr = constraint != null ? constraint.toString().toLowerCase() : null;
            // if no constraint is given, return the whole list
            if (substr == null || substr.length() == 0) {
                result.values = list;
                result.count = list.size();
                Log.d("all", "results");
            } else {
                Log.d("filter: ", constraint.toString());
                // iterate over the list of venues and find if the venue matches the constraint. if it does, add to the result list
                final ArrayList<ItemObject> retList = new ArrayList();
                for (ItemObject itemObject : list) {
                    Log.d("name ", itemObject.getName());
                    if (itemObject.getName().toLowerCase().contains(constraint)) {
                        retList.add(itemObject);
                    }
                }
                result.values = retList;
                result.count = retList.size();
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // we clear the adapter and then pupulate it with the new results
            searchArrayAdapter.clear();
            Log.d("res count", String.valueOf(results.count));
            if (results.count > 0) {
                for (ItemObject o : (ArrayList<ItemObject>) results.values) {
                    searchArrayAdapter.add(o);
                }
            }
        }

    }


}
