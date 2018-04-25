package com.example.myportfolio;

import android.app.Application;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Timer;

/**
 * Created by john_hyland on 4/16/18.
 */

public class StockDetail implements Serializable {

    String companyName;
    String symbol;
    double price;

    public StockDetail(){

    }


    public StockDetail(String symbol, String companyName, double price){
        this.companyName=companyName;
        this.symbol = symbol;
        this.price=price;
    }

    public String getCompanyName(){ return companyName; }
    public void setCompanyName(String companyName){ this.companyName=companyName;}
    public String getSymbol(){ return symbol; }
    public void setSymbol(String symbol){ this.symbol=symbol;}
    public double getPrice() {return price; }
    public String getPriceAsString() {return Double.toString(price); }
    public void setPrice(double price) {this.price = price;};

    //writes serialized StockDetail object to file
    public void writeStockToFile(File directory, File file){
        FileOutputStream fos;
        ObjectOutputStream out;
        if(!directory.exists()){
            directory.mkdir();
        }
        if (!file.exists()) {
            try{
                file.createNewFile();
            } catch (IOException e) {
                Log.e("MyPortfolio", ".ser file create error", e);
            }
        }
        try {
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(this);
        } catch ( Exception e) {
            e.printStackTrace();
        }


    }

    public void readStock(File directory, String symbol){
        StockDetail returnStock;
        FileInputStream fis;
        ObjectInputStream in;
        try{
            fis = new FileInputStream(directory+"/"+symbol+".ser");
            in = new ObjectInputStream(fis);
            returnStock = (StockDetail) in.readObject();
            in.close();
            this.symbol = returnStock.getSymbol();
            this.companyName = returnStock.getCompanyName();
            this.price = returnStock.getPrice();
        } catch (Exception e){
            Log.e("myApp", "file read exception", e);
        }


    }


}
