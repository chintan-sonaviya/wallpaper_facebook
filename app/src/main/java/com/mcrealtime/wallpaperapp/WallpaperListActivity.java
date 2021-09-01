package com.mcrealtime.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mcrealtime.wallpaperapp.AdUtills.AdManagerFB;
import com.mcrealtime.wallpaperapp.Adapter.RecycleviewAdpterForDown;
import com.mcrealtime.wallpaperapp.utils.AssetUtils;

public class WallpaperListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    LinearLayout admobBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_list);

        admobBanner = findViewById(R.id.admobBanner);
        AdManagerFB.getInstance().loadFbBannerWithLoader(WallpaperListActivity.this, admobBanner);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));


        RecycleviewAdpterForDown adpter=new RecycleviewAdpterForDown(this, AssetUtils.lstWall());
        recyclerView.setAdapter(adpter);

        findViewById(R.id.backact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        getINternet();
    }


    public void getINternet()
    {
        if (!isOnline())
        {
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
        final Dialog dialog = new Dialog(WallpaperListActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.app_dialog);
        dialog.setCancelable(false);
        admobBanner.setVisibility(View.INVISIBLE);
        RelativeLayout txt_yes = (RelativeLayout) dialog.findViewById(R.id.yes);


        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline())
                {

                    admobBanner.setVisibility(View.INVISIBLE);

                    Toast.makeText(WallpaperListActivity.this, "Please Connect Internet \nConnection", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    admobBanner.setVisibility(View.VISIBLE);
                    getINternet();
                    dialog.dismiss();


                }
            }
        });
        dialog.show();
    }

}