package com.mcrealtime.wallpaperapp;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        WebView mywebview = (WebView) findViewById(R.id.webView);

        mywebview.loadUrl("https://sites.google.com/view/company-privacypolicy");
    }
}