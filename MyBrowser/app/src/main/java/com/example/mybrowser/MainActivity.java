package com.example.mybrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements  WebViewFragment.OnUrlChangedListener{

    ArrayList<WebViewFragment> fragsList;
    MyAdapter mAdapter;
    ViewPager mPager;
    int currentTab;
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragsList = new ArrayList<>();
        //fragsList.add(new WebViewFragment());
        fragsList.add(WebViewFragment.getInstance(""));

        //Set up toolbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Instantiate ViewPager and PagerAdapter
        mPager = findViewById(R.id.viewPager);
        mAdapter = new MyAdapter(getSupportFragmentManager(), fragsList);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0);


        address = findViewById(R.id.editText);

        findViewById(R.id.goButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = address.getText().toString();

                if(url.contains(".")==false){
                  url = "https://www.google.com/search?q="+url;
                } else if(url.startsWith("http")==false){
                    url = "https://" + url;
                }
                address.setText(url);
                fragsList.get(currentTab).setURL(url);
            }
        });


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                String savedURL = fragsList.get(position).getMyURL();
                if(savedURL!=null) {
                    address.setText(savedURL);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle b;
        switch (item.getItemId()) {
            case R.id.action_new:

                fragsList.add(WebViewFragment.getInstance(""));
                currentTab = fragsList.size()-1;
                mAdapter.notifyDataSetChanged();
                mPager.setCurrentItem(currentTab);
                address.setText("");
                return true;

            case R.id.action_previous:
                if(currentTab > 0) {
                    currentTab--;
                    mPager.setCurrentItem(currentTab);
                }
                else if(currentTab == 0){
                    //loop back around to last tab
                    currentTab = fragsList.size()-1;
                    mPager.setCurrentItem(currentTab);
                }
                return true;

            case R.id.action_next:
                if(currentTab < fragsList.size()-1){
                    currentTab++;
                    mPager.setCurrentItem(currentTab);
                }
                else if(currentTab == fragsList.size()-1) {
                    //loop back around to the first fragment
                    currentTab = 0;
                    mPager.setCurrentItem(currentTab);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.appbar_buttons, menu );
        return true;
    }

    // If the user has clicked on a link within the WebView fragment,
    // update the address bar
    @Override
    public void onUrlChanged(String url) {
        address.setText(url);
    }


}
