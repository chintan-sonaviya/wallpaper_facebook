package com.mcrealtime.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mcrealtime.wallpaperapp.AdUtills.AdManagerFB;
import com.mcrealtime.wallpaperapp.Adapter.RecycleviewAdpterForDownloaded;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<String> list=new ArrayList<>();
    public static ArrayList<String> photos = new ArrayList();
    private ArrayList<String> photosTemp = new ArrayList();
    TextView tvError;

    LinearLayout admobBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);



        admobBanner = findViewById(R.id.admobBanner);
        AdManagerFB.getInstance().loadFbBannerWithLoader(DownloadActivity.this, admobBanner);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        tvError=findViewById(R.id.tvError);

        getDataFromDir();


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
        final Dialog dialog = new Dialog(DownloadActivity.this);
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

                    Toast.makeText(DownloadActivity.this, "Please Connect Internet \nConnection", Toast.LENGTH_SHORT).show();
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


    public void getDataFromDir() {
        photos.clear();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +File.separator + "Nature Wallpaer");
        if (file.isDirectory() && file.exists()) {
            File[] listFile;
            listFile = file.listFiles();
            Arrays.sort(listFile, new Comparator() {
                public int compare(Object obj, Object obj2) {
                    File file = (File) obj;
                    File file2 = (File) obj2;
                    if (file.lastModified() > file2.lastModified()) {
                        return -1;
                    }
                    return file.lastModified() < file2.lastModified() ? 1 : 0;
                }
            });
            int i = 0;
            photosTemp.clear();
            while (true) {
                File[] fileArr = listFile;
                if (i >= fileArr.length) {
                    break;
                }
                if (fileArr[i].getName().contains("temp")) {
                    // this.listFile[i].delete();
                } else {
                    if (fileArr[i].getName().endsWith(".jpg"))
                    {
                        this.photosTemp.add(listFile[i].getAbsolutePath());
                    }
                }
                i++;
            }
            photos.clear();
            for (int i2 = 0; i2 < this.photosTemp.size(); i2++) {
                photos.add(this.photosTemp.get(i2));
            }
        }
        else
        {
            tvError.setVisibility(View.VISIBLE);


        }
        RecycleviewAdpterForDownloaded adpter=new RecycleviewAdpterForDownloaded(this, photos);
        recyclerView.setAdapter(adpter);
        adpter.notifyDataSetChanged();
        if (photos.size() == 0) {
            this.tvError.setVisibility(View.VISIBLE);
        } else {
            this.tvError.setVisibility(View.GONE);
        }
    }

}