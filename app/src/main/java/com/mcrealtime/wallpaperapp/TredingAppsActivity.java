package com.mcrealtime.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mcrealtime.wallpaperapp.AdUtills.AdManagerFB;
import com.mcrealtime.wallpaperapp.Adapter.RecycleviewAdpterForApps;
import com.mcrealtime.wallpaperapp.model.Apps;

import java.util.ArrayList;
import java.util.List;

public class TredingAppsActivity extends AppCompatActivity {
    RecycleviewAdpterForApps forApps1, forApps2;
    RecyclerView recyclerviewhori, recyclerviewvertical;
    List<Apps> appshori = new ArrayList<>();
    List<Apps> appsverti = new ArrayList<>();
    LinearLayout admobBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treding_apps);
        admobBanner = findViewById(R.id.admobBanner);
        AdManagerFB.getInstance().loadFbBannerWithLoader(TredingAppsActivity.this, admobBanner);
        recyclerviewhori = findViewById(R.id.recyclerviewhori);
        recyclerviewvertical = findViewById(R.id.recyclerviewvertical);
        recyclerviewhori.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewvertical.setLayoutManager(new GridLayoutManager(this, 3));
        //add your item like that..
        appshori.add(new Apps("https://play.google.com/store/apps/details?id=com.playit.videoplayer", "PLAYit", R.drawable.app7));
        appshori.add(new Apps("https://play.google.com/store/apps/details?id=com.sharekaro.app", "Share Karo", R.drawable.app8));
        appshori.add(new Apps("https://play.google.com/store/apps/details?id=com.instagram.android", "Instagram", R.drawable.app9));
        appshori.add(new Apps("https://play.google.com/store/apps/details?id=com.snapchat.android", "Snapchat", R.drawable.app10));
        findViewById(R.id.backact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //add your item like that..
        appsverti.add(new Apps("https://play.google.com/store/apps/details?id=in.mohalla.video", "Moj Kro", R.drawable.app1));
        appsverti.add(new Apps("https://play.google.com/store/apps/details?id=com.vpnmasterx.free", "VPN Master", R.drawable.app2));
        appsverti.add(new Apps("https://play.google.com/store/apps/details?id=com.whatsapp", "WhatsApp Messenger", R.drawable.app3));
        appsverti.add(new Apps("https://play.google.com/store/apps/details?id=com.phonepe.app", "PhonePe", R.drawable.app4));
        appsverti.add(new Apps("https://play.google.com/store/apps/details?id=com.meesho.supply", "Meesho", R.drawable.app5));
        appsverti.add(new Apps("https://play.google.com/store/apps/details?id=com.facebook.katana", "Facebook", R.drawable.app6));
        forApps1 = new RecycleviewAdpterForApps(this, appsverti, 0);
        recyclerviewvertical.setAdapter(forApps1);
        forApps2 = new RecycleviewAdpterForApps(this, appshori, 1);
        recyclerviewhori.setAdapter(forApps2);
        getINternet();
    }

    public void getINternet() {
        if (!isOnline()) {
            InternetDialog();
        }
    }

    public Boolean isOnline() {
        try {
            return Boolean.valueOf(Runtime.getRuntime().exec("ping -c 1 www.google.com").waitFor() == 0);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.valueOf(false);
        }
    }

    public void InternetDialog() {
        final Dialog dialog = new Dialog(TredingAppsActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.app_dialog);
        dialog.setCancelable(false);
        admobBanner.setVisibility(View.INVISIBLE);
        RelativeLayout txt_yes = (RelativeLayout) dialog.findViewById(R.id.yes);
        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    admobBanner.setVisibility(View.INVISIBLE);
                    Toast.makeText(TredingAppsActivity.this, "Please Connect Internet \nConnection", Toast.LENGTH_SHORT).show();
                } else {
                    admobBanner.setVisibility(View.VISIBLE);
                    getINternet();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}