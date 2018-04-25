package com.example.myportfolio;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Timer;
import java.util.TimerTask;

public class MyStockUpdateService extends Service {

    private IBinder mBinder = new LocalBinder(); //Binder for clients
    private String TAG = "MyStockUpdateService";
    private static Timer timer = new Timer();
    private final int UPDATE_FREQUENCY = 60;
    StockDetail retrievedOnSearch;

    public MyStockUpdateService() { }

    @Override
    public void onCreate() {
        timer.scheduleAtFixedRate(new mainTask(), 0, UPDATE_FREQUENCY*1000);
    }

    private class mainTask extends TimerTask {
        public void run() {
            updatedStocks();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    public class LocalBinder extends Binder {
        MyStockUpdateService getService() {
            // Return this instance of MyStockUpdateService so clients can call public methods
            return MyStockUpdateService.this;
        }
    }

    public void startStockFetchThread(final String myUrl) {
        Thread stockFetcherThread = new Thread(new Runnable() {
            @Override
            public void run() {
                getStockFromJSON(myUrl, false);
            }
        });
        stockFetcherThread.start();
    }

    // called every 60 seconds to update stock price on file
    public void updatedStocks() {
        Double oldPrice, newPrice;
        File path = new File(getApplicationContext().getFilesDir().toString());
        File[] filesList = path.listFiles();
        FileInputStream fis;
        ObjectInputStream in;
        StockDetail oldStockDetail = new StockDetail();
        if(filesList.length>0) {
            for (File f :
                    filesList) {
                if (f.getName().endsWith(".ser")) {
                    try {
                        fis = new FileInputStream(f);
                        in = new ObjectInputStream(fis);
                        oldStockDetail = (StockDetail) in.readObject();
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String name = f.getName();
                    int pos = name.lastIndexOf(".");
                    if (pos > 0) {
                        name = name.substring(0, pos);
                    }
                    getStockFromJSON("http://dev.markitondemand.com/MODApis/Api/v2/" +
                                    "Quote/json/?symbol="+name, true);
                    newPrice = retrievedOnSearch.getPrice();
                    oldPrice= oldStockDetail.getPrice();
                    if (!oldPrice.equals(newPrice)) {
                        Log.i("myApp", "Updated a stock");
                        Log.i("myApp-oldPrice", oldPrice.toString());
                        Log.i("myApp-newPrice", newPrice.toString());
                        oldStockDetail.setPrice(newPrice); //update the StockDetail object
                        saveSerializedStockDetailObject(oldStockDetail, true);  //save it
                    }
                }
            }
        }
    }

    public double toDouble(String price){ return Double.parseDouble(price); }

    public void getStockFromJSON(String url, final Boolean isUpdate){
        retrievedOnSearch = new StockDetail();
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            retrievedOnSearch.setPrice(toDouble(response.getString("LastPrice")));
                            retrievedOnSearch.setCompanyName(response.getString("Name"));
                            retrievedOnSearch.setSymbol(response.getString("Symbol"));
                            Log.i("SDprice", retrievedOnSearch.getPriceAsString());
                            Log.i("SDCompanyName", retrievedOnSearch.getCompanyName());
                            Log.i("SDSymbol", retrievedOnSearch.getSymbol());
                            saveSerializedStockDetailObject(retrievedOnSearch, isUpdate);
                            //tell main activity that it can get rid of the fragment's placeholder text
                            Intent intent = new Intent("get-rid-of-placeholder-text");
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        Volley.newRequestQueue(this).add(jsonRequest);
    }

    // saves serialized StockDetail objects to the file system
    public void saveSerializedStockDetailObject(StockDetail stockDetailObject, Boolean isUpdate){
        File mFolder = new File(getFilesDir().toString());
        File mFile = new File(getFilesDir().getAbsolutePath()+"/"+stockDetailObject.getSymbol()+".ser");
        stockDetailObject.writeStockToFile(mFolder, mFile);
        if(!isUpdate) { // send a message to update the adapter
            Intent intent = new Intent("new-data-update-adapter");
            intent.putExtra("Company-name", stockDetailObject.getCompanyName());
            intent.putExtra("Symbol", stockDetailObject.getSymbol());
            intent.putExtra("Price", stockDetailObject.getPrice());
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }
}
