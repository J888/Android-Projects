package com.example.myportfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.ObjectInput;
import java.util.ArrayList;

/**
 * Created by john_hyland on 4/16/18.
 */

public class CustomAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    int mResource;
    ArrayList<StockDetail> stockDetails;

    public CustomAdapter(Context context, int resource, ArrayList<StockDetail> stockDetails) {

        inflater = LayoutInflater.from(context);
        this.stockDetails = stockDetails;
        this.context = context;
        mResource = resource;

    }

    @Override
    public int getCount(){
        return stockDetails.size();
    }

    @Override
    public Object getItem(int position){
        return stockDetails.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        return createItemView(position, view, viewGroup);
    }


    private View createItemView(int position, View view, ViewGroup viewGroup) {

        final View stockView = inflater.inflate(mResource, viewGroup, false);

        TextView stockText = stockView.findViewById(R.id.listViewItem);

        //set the label for the stock item
        stockText.setText((String)stockDetails.get(position).getSymbol());

        return stockView;

    }

}
