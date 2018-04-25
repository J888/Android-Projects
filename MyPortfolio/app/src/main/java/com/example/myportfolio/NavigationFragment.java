package com.example.myportfolio;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {

    View v;
    ListView myListView;
    TextView placeholder;
    CustomAdapter myCustomAdapter = null;

    //Dataset used by adapter
    ArrayList<StockDetail> stockDetailArrayList = new ArrayList<>();

    OnStockSelectedListener mCallback;

    public interface OnStockSelectedListener {
        void onStockSelected(String symbol);
    }


    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("new-data-update-adapter"));
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            StockDetail mySD = new StockDetail(intent.getStringExtra("Symbol"),
                    intent.getStringExtra("Company-name"), intent.getDoubleExtra("Price", 0));
            updateDataSet(mySD);
        }
    };




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_navigation, container, false);

        myCustomAdapter = new CustomAdapter(this.getContext(), R.layout.list_view_item, stockDetailArrayList);
        myListView = v.findViewById(R.id.myListView);
        myListView.setAdapter(myCustomAdapter);

        File path = new File(getContext().getFilesDir().toString());
        File[] filesList = path.listFiles();
            FileInputStream fis = null;
            ObjectInputStream in = null;
            for (File f:
                    filesList) {
                try {
                    fis = new FileInputStream(f);
                    in = new ObjectInputStream(fis);
                    StockDetail sd = (StockDetail) in.readObject();
                    Log.i("SD2price", sd.getPriceAsString());
                    Log.i("SD2CompanyName", sd.getCompanyName());
                    Log.i("SD2Symbol", sd.getSymbol());
                    in.close();
                    stockDetailArrayList.add(sd); // add to ArrayList
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.onStockSelected(stockDetailArrayList.get(position).getSymbol());
            }
        });

    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    public void updateDataSet(StockDetail newSD){
        stockDetailArrayList.add(newSD);
        myCustomAdapter.notifyDataSetChanged();
    }

    public void clearDataSet(){
        stockDetailArrayList.clear();
        myCustomAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Make sure the parent activity implemented callback interface, throw exception
        // if it did not
        try {
            mCallback = (OnStockSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "must implement OnColorSelectedListener");
        }
    }


}
