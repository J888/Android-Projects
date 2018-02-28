package edu.temple.modifiedcolorselection;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by John Hyland on 2/14/18.
 * CIS 3515 Lab 4
 */


public class customAdapter extends ArrayAdapter {

    LayoutInflater inflater;
    Context context;
    int mResource;
    Object[] labels, colors;


    public customAdapter(Context context, int resource, Object[] labels, Object[] colors){
        super(context, resource, (String[]) colors);
        inflater = LayoutInflater.from(context);
        this.labels = labels;
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


    //get the view
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        return createItemView(position, view, viewGroup);
    }

    private View createItemView(int position, View view, ViewGroup viewGroup) {

        final View colorView = inflater.inflate(mResource, viewGroup, false);

        TextView colorText = colorView.findViewById(R.id.gridViewItem);

        //set the label for the item
        String label = (String)labels[position];
        colorText.setText(label);

        //set the color for the item
        String chosenColor = (String)colors[position];
        colorText.setBackgroundColor(Color.parseColor(chosenColor));



        return colorView;

    }

}
