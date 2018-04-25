package com.example.myportfolio;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by john_hyland on 4/17/18.
 */

public class DetailsFragment extends Fragment {

    TextView companyName, stockPrice;
    ImageView chartImage;
    View v;
    String symbol;
    private final int UPDATE_FREQUENCY = 5;

    private static Timer timer = new Timer();

    public DetailsFragment() {
        //required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_details, container, false);
       updateDetails(this.getArguments().getString("Symbol"));
       return v;
    }

    //called if the fragment is already there and details need to be updated
    public void updateDetails(String symbol){
        companyName = v.findViewById(R.id.companyName);
        stockPrice = v.findViewById(R.id.stockPrice);
        chartImage = v.findViewById(R.id.chartImage);
        File mFolder = new File(getContext().getFilesDir().toString());
        StockDetail sd = new StockDetail();
        sd.readStock(mFolder, symbol);
        companyName.setText(sd.getCompanyName());
        stockPrice.setText("$"+sd.getPriceAsString());
        Picasso.with(getContext()).load("https://www.google.com/finance/chart?q="+symbol+"&p=1d")
            .into(chartImage);
    }

    public void updatePrice(double price){
        stockPrice = v.findViewById(R.id.stockPrice);
        stockPrice.setText(Double.toString(price));
    }

    public String getSymbol(){
        return symbol;
    }
}
