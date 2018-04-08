package com.example.mybrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by john_hyland on 4/7/18.
 */

public class MyAdapter extends FragmentStatePagerAdapter {

    private ArrayList<WebViewFragment> myAL;

    public MyAdapter(FragmentManager fm, ArrayList<WebViewFragment> al) {
        super(fm);
        myAL = al;
    }

    @Override
    public int getCount() {
        return myAL.size();
    }

    //return fragment associated with specific position
    @Override
    public Fragment getItem(int position) {
        return myAL.get(position);
    }

}
