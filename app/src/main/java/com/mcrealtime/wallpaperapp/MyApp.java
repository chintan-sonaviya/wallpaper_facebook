package com.mcrealtime.wallpaperapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



import java.util.ArrayList;

public class MyApp extends Application {

    private static MyApp ourInstance;

    public SharedPreferences preferences;

    public static final String MyPREFERENCES = "MyAdsPrefs";

    public static MyApp getInstance() {
        return ourInstance;
    }

    private static final String PREF_APP_DETAILS = "app_details", PREF_ADS_DETAILS = "ads_details";



    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        MultiDex.install(this);
        preferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        MyApp.getInstance().preferences.edit().putBoolean("isFbFirstAd", true).apply();


        AudienceNetworkAds.initialize(this);

    }

    public static Context getAppContext() {
        return ourInstance;
    }






}