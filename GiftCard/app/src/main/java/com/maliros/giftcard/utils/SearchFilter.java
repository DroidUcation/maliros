/*
package com.maliros.giftcard.utils;

import android.util.Log;
import android.widget.Filter;


import com.maliros.giftcard.recyclerviewgridview.ItemObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by user on 07/06/2016.
 *//*

public class SearchFilter extends Filter {

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        List<ItemObject> list = new ArrayList<ItemObject>(values);
        FilterResults result = new FilterResults();
        String substr = constraint.toString().toLowerCase();
        // if no constraint is given, return the whole list
        if (substr == null || substr.length() == 0) {
            result.values = list;
            result.count = list.size();
        } else {
            // iterate over the list of venues and find if the venue matches the constraint. if it does, add to the result list
            final ArrayList<ItemObject> retList = new ArrayList<ItemObject>();
            for (ItemObject itemObject : list) {
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
        searchAdapter.clear();
        if (results.count > 0) {
            for (ItemObject o : (ArrayList<ItemObject>) results.values) {
                searchAdapter.add(o);
            }
        }
    }

}*/
