package com.example.myportfolio;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity
        implements NavigationFragment.OnStockSelectedListener {

    Boolean twoPanes;
    FragmentManager fm;
    FragmentTransaction transaction;
    ArrayList<String> history = new ArrayList<>();
    String currentSelectedStockSymbol;
    private final String CURRENT_SELECTED_KEY = "current";
    TextView noStocksPlaceholderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("get-rid-of-placeholder-text"));
        checkForStocks();
//        forceChange("NFLX", 0.23);
//        forceChange("GOOGL", 0.23);

        twoPanes = findViewById(R.id.fragContainer2)!=null;

        transaction = getSupportFragmentManager().beginTransaction()
        .add(R.id.fragContainer, new NavigationFragment());
        transaction.commit();

        handleIntent(getIntent()); // for search
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(noStocksPlaceholderText!=null){
                noStocksPlaceholderText.setVisibility(View.GONE);
            }
        }
    };

    // forces a price change to prove that the continuous update service actually works
    public void forceChange(String symbol, double price){
        StockDetail newStock = new StockDetail();
        newStock.readStock(getFilesDir(),symbol);
        newStock.setPrice(price);
        File mFolder = new File(getFilesDir().toString());
        File mFile = new File(mFolder.getAbsolutePath()+"/"+newStock.getSymbol()+".ser");
        newStock.writeStockToFile(mFolder, mFile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Bind to MyStockUpdateService
        Intent myIntent = new Intent(this, MyStockUpdateService.class);
        Boolean b = bindService(myIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unBind
        unbindService(mConnection);
        mBound=false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_search, menu);

        // Get SearchView, set searchable config
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);  // expand widget by default
        return true;
    }

    MyStockUpdateService mService;
    Boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            // Now that we are bound to MyStockUpdateService,
            // cast the IBinder and get LocalService instance
            MyStockUpdateService.LocalBinder binder = (MyStockUpdateService.LocalBinder) service;
            mService = binder.getService();
            mBound=true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            mBound=false;
        }
    };



    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if(history.contains(query)){
                Toast.makeText(getApplicationContext(), "You already added that stock!",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                history.add(query);
                //start the service to go fetch the JSON
                mService.startStockFetchThread("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol="+query);
            }
        }
    }


    //interface with NavigationFragment
    @Override
    public void onStockSelected(String symbol) {
        currentSelectedStockSymbol=symbol;
        DetailsFragment df = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("Symbol", symbol);
        if(twoPanes){ // 2 PANES
            transaction = getSupportFragmentManager().beginTransaction();
            df.setArguments(args);
            transaction.replace(R.id.fragContainer2, df);
            transaction.commit();
        }
        else { // 1 PANE, replace nav frag with details
            transaction = getSupportFragmentManager().beginTransaction();
            df.setArguments(args);
            transaction.replace(R.id.fragContainer, df);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(currentSelectedStockSymbol!=null){
            outState.putString(CURRENT_SELECTED_KEY, currentSelectedStockSymbol);
        }
        if(outState!=null && !twoPanes){
            super.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void checkForStocks(){
        noStocksPlaceholderText = findViewById(R.id.noStocksPlaceholder);
        int count = 0;
        File path = new File(getApplicationContext().getFilesDir().toString());
        File[] filesList = path.listFiles();
        for (File f:
                filesList) {
            if(f.getName().endsWith(".ser") ){
                Log.i("file:",  f.getName());
                count++;
            }
        }
        if(count==0){
            noStocksPlaceholderText.setVisibility(View.VISIBLE);
        }
        else{
            noStocksPlaceholderText.setVisibility(View.GONE);
        }
    }
}
