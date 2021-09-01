package com.mcrealtime.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.ads.AdSettings;
import com.mcrealtime.wallpaperapp.AdUtills.AdManagerFB;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        AdSettings.setTestMode(true);

        AdManagerFB.getInstance().loadInterstitialAds(this);



        if(!isOnline())
        {
            InternetDialog();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isOnline())
                {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();

                }
                else
                {
                    InternetDialog();
                }
            }
        },3000);
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
        final Dialog dialog = new Dialog(SplashActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.app_dialog);
        dialog.setCancelable(false);

        RelativeLayout txt_yes = (RelativeLayout) dialog.findViewById(R.id.yes);
      //  TextView txt = (TextView) dialog.findViewById(R.id.txt);

      //  txt.setText("Internet is not working.\n"+"Start your Internet and restart app.");


        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline())
                {
                   /* finish();
                    finishAffinity();
                    dialog.dismiss();
*/

                    Toast.makeText(SplashActivity.this, "Please Connect Internet \nConnection", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();

                }
            }
        });
        dialog.show();
    }


}