package com.example.mybrowser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by john_hyland on 4/7/18.
 */

public class WebViewFragment extends Fragment {

    private String myURL;
    OnUrlChangedListener mCallback;
    WebView myWebView;

    public interface OnUrlChangedListener {
        void onUrlChanged(String url);

    }

    // Required empty constructor
    public WebViewFragment() {
    }

    public static WebViewFragment getInstance(String url){
        WebViewFragment webViewFragment = new WebViewFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("url", url);
//        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    /* Retrieve this instance's URL from Bundle */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            myURL = getArguments().getString("URL");
            Toast.makeText(getActivity(), "Got this URL onCreate: "+myURL, Toast.LENGTH_SHORT).show();
        }

        if(savedInstanceState!=null){
            myURL = savedInstanceState.getString("URL");
            Toast.makeText(getActivity(), "Got this URL onCreate: "+myURL, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webview, container, false);
        myWebView = v.findViewById(R.id.myWebView);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                myURL = url;
            }

            // if a new page starts loading, send the url to the parent activity
            // so it can update the address in the address bar
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mCallback.onUrlChanged(url);
            }
        });

        myWebView.getSettings().setJavaScriptEnabled(true); // Enable Javascript

        if(savedInstanceState != null) {
            myURL = savedInstanceState.getString("URL");
            myWebView.loadUrl(myURL);
        }

        return v;
    }


    public void setURL(String url){
        myURL=url;
        myWebView.loadUrl(myURL);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("URL", myURL);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback = (OnUrlChangedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ " must implement onUrlChangedListener");
        }
    }


    public String getMyURL () {
        return myURL;
    }


}
