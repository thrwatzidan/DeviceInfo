package com.dev.thrwat_zidan.deviceinfo;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    public CustomFilter(ArrayList<Model> filterList,MyAdapter adapter ) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    MyAdapter adapter;
    ArrayList<Model> filterList;

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //check containt validaty
        if (constraint != null && constraint.length() > 0) {
            //chang to upper case
            constraint = constraint.toString().toUpperCase();
            //store our filterd Models

            ArrayList<Model> filterModels = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {

                //check
                if (filterList.get(i).getName().toUpperCase().contains(constraint)){

                //add model to filtered models
                filterModels.add(filterList.get(i));
            }
        }
        results.count = filterModels.size();
        results.values = filterModels;
    }
    else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return null;


}

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.models = (ArrayList<Model>) results.values;
        //refresh
        adapter.notifyDataSetChanged();


    }
}
