package com.it.insidetowns.theinsidetowns.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.it.insidetowns.theinsidetowns.R;

public class WebViewShow extends AppCompatActivity {
    WebView Website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_show);

        Website = findViewById(R.id.Website);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(WebViewShow.this);
        String wv = sharedPrefs.getString("Website", "");

        Website.getSettings().setLoadsImagesAutomatically(true);
        Website.getSettings().setJavaScriptEnabled(true);
        Website.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        WebSettings webSettings = Website.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        Website.setWebViewClient(new WebViewClient());


        try
        {
            Website.loadUrl("http://"+wv);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }


}
