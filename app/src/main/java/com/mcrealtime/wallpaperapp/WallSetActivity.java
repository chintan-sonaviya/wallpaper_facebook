package com.mcrealtime.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.ads.NativeAdLayout;
import com.mcrealtime.wallpaperapp.AdUtills.AdManagerFB;
import com.mcrealtime.wallpaperapp.utils.AssetUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WallSetActivity extends AppCompatActivity {

    int value=0;
    ImageView wallpap;
    static File path;
    NativeAdLayout admobNativeLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_set);

        if (!isOnline())
        {
            InternetDialog();
        }


        admobNativeLarge = findViewById(R.id.admobnative);
        AdManagerFB.getInstance().loadFbAdNativeWithLoader(WallSetActivity.this, admobNativeLarge);


        value=getIntent().getIntExtra("id",0);

        if (checkfile())
        {
            findViewById(R.id.downloadimg).setVisibility(View.INVISIBLE);
        }
        else
        {

        }

        wallpap= findViewById(R.id.wallpap);
        wallpap.setImageBitmap(AssetUtils.loadBitmapFromAssets(getApplicationContext(),AssetUtils.lstWall().get(value)));

        findViewById(R.id.backact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        findViewById(R.id.downloadimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isOnline())
                {
                    InternetDialog();
                    return;
                }


                AdManagerFB.getInstance().displayInterstitialAd(WallSetActivity.this, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        File dir=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Nature Wallpaer");
                        if (!dir.exists())
                        {
                            dir.mkdirs();
                        }
                        File path=new File(dir,"wall"+value+".jpg");
                        savebitmap(path);

                    }
                });
            }
        });


        findViewById(R.id.setwall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isOnline())
                {
                    InternetDialog();
                    return;
                }

                AdManagerFB.getInstance().displayInterstitialAd(WallSetActivity.this, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        setWallpaperd(AssetUtils.loadBitmapFromAssets(getApplicationContext(),AssetUtils.lstWall().get(value)));

                    }
                });



            }
        });
        findViewById(R.id.sharewall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isOnline())
                {
                    InternetDialog();
                    return;
                }

                if (!checkfile())
                {
                    savebitmap(path);
                }

                AdManagerFB.getInstance().displayInterstitialAd(WallSetActivity.this, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        File file = new File(path.getAbsolutePath());
                        WallSetActivity playActivity = WallSetActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append(WallSetActivity.this.getPackageName());
                        sb.append(".provider");
                        Uri uriForFile = FileProvider.getUriForFile(playActivity, sb.toString(), file);
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.STREAM", uriForFile);
                        intent.setType("image/");
                        WallSetActivity.this.startActivity(intent);

                    }
                });


            }
        });







    }


   public void savebitmap(File path)
    {
        Bitmap bmp=null;
        try (FileOutputStream out = new FileOutputStream(path)) {

            bmp= AssetUtils.loadBitmapFromAssets(getApplicationContext(),AssetUtils.lstWall().get(value));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            Toast.makeText(this, "Download success...", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

            File pictureFile = path;
            if (pictureFile == null)
            {
                Toast.makeText(this, "Error creating media file, check storage permissions", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Error creating media file, check storage permissions: ");// e.getMessage());
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                bmp.compress(Bitmap.CompressFormat.PNG, 98, fos);
                fos.close();
                return;
            }
            catch (FileNotFoundException e1) {
                Log.d("TAG", "File not found: " + e1.getMessage());

            }
            catch (IOException e2) {
                Log.d("TAG", "Error accessing file: " + e2.getMessage());

            }

            Toast.makeText(this, "Download faild...", Toast.LENGTH_SHORT).show();

        }

    }

    public void setWallpaperd(Bitmap bmp) {
        Bitmap bitmap = bmp;
        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
        try {
            manager.setBitmap(bitmap);
            Toast.makeText(this, "Wallpaper set success!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }


    boolean checkfile()
    {

        File dir=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Nature Wallpaer");
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        path=new File(dir,"wall"+value+".jpg");

        return path.exists();
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
        final Dialog dialog = new Dialog(WallSetActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.app_dialog);
        dialog.setCancelable(false);
        admobNativeLarge.setVisibility(View.GONE);
        RelativeLayout txt_yes = (RelativeLayout) dialog.findViewById(R.id.yes);
        //  TextView txt = (TextView) dialog.findViewById(R.id.txt);

        //  txt.setText("Internet is not working.\n"+"Start your Internet and restart app.");


        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline())
                {

                    admobNativeLarge.setVisibility(View.GONE);
                    Toast.makeText(WallSetActivity.this, "Please Connect Internet \nConnection", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    admobNativeLarge.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }




}

