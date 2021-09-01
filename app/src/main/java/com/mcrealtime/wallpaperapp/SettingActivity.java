package com.mcrealtime.wallpaperapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.mcrealtime.wallpaperapp.AdUtills.AdManagerFB;


public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.backact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.line_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    InternetDialog();
                    return;
                }
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });
        findViewById(R.id.line_privacy_policy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    InternetDialog();
                    return;
                }
                startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));
            }
        });
        findViewById(R.id.line_more_apps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline())
                {
                    InternetDialog();
                    return;
                }

                AdManagerFB.getInstance().displayInterstitialAd(SettingActivity.this, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        startActivity(new Intent(getApplicationContext(),TredingAppsActivity.class));

                    }
                });

            }
        });
        findViewById(R.id.line_invite_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    InternetDialog();
                    return;
                }
                final Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", "Hey check out my app and share your friends : https://play.google.com/store/apps/details?id=" + getPackageName());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });
        findViewById(R.id.line_rate_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    InternetDialog();
                    return;
                }
                final StringBuilder d2 = new StringBuilder("http://play.google.com/store/apps/details?id=");
                d2.append(getPackageName());
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(d2.toString())));
            }
        });
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
        final Dialog dialog = new Dialog(SettingActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.app_dialog);
        dialog.setCancelable(false);
        RelativeLayout txt_yes = (RelativeLayout) dialog.findViewById(R.id.yes);
        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    Toast.makeText(SettingActivity.this, "Please Connect Internet \nConnection", Toast.LENGTH_SHORT).show();
                } else {
                    getINternet();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}