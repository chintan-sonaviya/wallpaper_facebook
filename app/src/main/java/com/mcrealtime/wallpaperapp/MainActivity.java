package com.mcrealtime.wallpaperapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAdLayout;
import com.mcrealtime.wallpaperapp.AdUtills.AdManagerFB;

public class MainActivity extends AppCompatActivity {

    LinearLayout admobBanner;
    NativeAdLayout admobNativeLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AdSettings.setTestMode(true);

        getStoragePermission();

        AdManagerFB.getInstance().displayInterstitialAd(MainActivity.this, new AdManagerFB.AdCallBack() {
            @Override
            public void onAdDismiss(String str) {

            }
        });
        admobBanner = findViewById(R.id.admobBanner);
        AdManagerFB.getInstance().loadFbBannerWithLoader(MainActivity.this, admobBanner);
        admobNativeLarge = findViewById(R.id.admobnative);
        AdManagerFB.getInstance().loadFbAdNativeWithLoader(MainActivity.this, admobNativeLarge);
        findViewById(R.id.wallpaperlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    InternetDialog();
                    return;
                }

                AdManagerFB.getInstance().displayInterstitialAd(MainActivity.this, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        startActivity(new Intent(getApplicationContext(), WallpaperListActivity.class));

                    }
                });


            }
        });
        findViewById(R.id.downloaded).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    InternetDialog();
                    return;
                }
                AdManagerFB.getInstance().displayInterstitialAd(MainActivity.this, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        startActivity(new Intent(getApplicationContext(), DownloadActivity.class));

                    }
                });
            }
        });

        findViewById(R.id.tredingapps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    InternetDialog();
                    return;
                }

                AdManagerFB.getInstance().displayInterstitialAd(MainActivity.this, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        startActivity(new Intent(getApplicationContext(), TredingAppsActivity.class));

                    }
                });
            }
        });

        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    InternetDialog();
                    return;
                }
                AdManagerFB.getInstance().displayInterstitialAd(MainActivity.this, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                    }
                });
            }
        });

        findViewById(R.id.rate).setOnClickListener(new View.OnClickListener() {
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
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.app_dialog);
        dialog.setCancelable(false);
        admobBanner.setVisibility(View.INVISIBLE);
        admobNativeLarge.setVisibility(View.INVISIBLE);

        RelativeLayout txt_yes = (RelativeLayout) dialog.findViewById(R.id.yes);

        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {

                    admobBanner.setVisibility(View.INVISIBLE);
                    admobNativeLarge.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Please Connect Internet \nConnection", Toast.LENGTH_SHORT).show();
                } else {
                    admobBanner.setVisibility(View.VISIBLE);
                    admobNativeLarge.setVisibility(View.VISIBLE);
                    getINternet();
                    dialog.dismiss();


                }
            }
        });
        dialog.show();
    }

    protected void getStoragePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //  Toast.makeText(MainActivity.this, "Permission is Already Granted!", Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Storage Permission Needed")
                        .setMessage("Storage Permission is Needed to Access External Files")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }

        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Toast.makeText(MainActivity.this, "Permission is Already Granted!", Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Storage Permission Needed")
                        .setMessage("Storage Permission is Needed to Access External Files")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Storage Permission Needed")
                            .setMessage("Storage Permission is Compulsory for this Feature \r\nGrant Now?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this, "Grant Permission from Permission Tab", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();

                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}