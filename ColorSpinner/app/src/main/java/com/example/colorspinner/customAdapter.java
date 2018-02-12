package com.example.colorspinner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by john_hyland on 2/11/18.
 */

public class customAdapter extends ArrayAdapter{

    LayoutInflater inflater;
    Context context;
    int mResource;
    Object[] colors;


    public customAdapter(Context context, int resource, Object[] colors){
        super(context, resource, (String[]) colors);
        inflater = LayoutInflater.from(context);
        this.colors = colors;
        this.context = context;
        mResource = resource;

    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int i) {
        return colors[i];
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        return createItemView(position, view, parent);
    }

    //get the view
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        return createItemView(position, view, viewGroup);
    }

    private View createItemView(int position, View view, ViewGroup viewGroup) {

        final View colorView = inflater.inflate(mResource, viewGroup, false);

        TextView colorText = colorView.findViewById(R.id.spinnerItem);

        String chosenColor = (String)colors[position];
        colorText.setBackgroundColor(Color.parseColor(chosenColor));
        colorText.setText(chosenColor);

        return colorView;

    }
}
