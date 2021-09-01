package com.mcrealtime.wallpaperapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AssetUtils {

    public static List<String> lstWall() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("Wallpaper/wall1.jpg");
        arrayList.add("Wallpaper/wall2.jpg");
        arrayList.add("Wallpaper/wall3.jpg");
        arrayList.add("Wallpaper/wall4.jpg");
        arrayList.add("Wallpaper/wall5.jpg");
        arrayList.add("Wallpaper/wall6.jpg");
        arrayList.add("Wallpaper/wall7.jpg");
        arrayList.add("Wallpaper/wall8.jpg");
        arrayList.add("Wallpaper/wall9.jpg");
        arrayList.add("Wallpaper/wall10.jpg");
        arrayList.add("Wallpaper/wall11.jpg");
        arrayList.add("Wallpaper/wall12.jpg");
        arrayList.add("Wallpaper/wall13.jpg");
        arrayList.add("Wallpaper/wall14.jpg");
        arrayList.add("Wallpaper/wall15.jpg");
        arrayList.add("Wallpaper/wall16.jpg");
        arrayList.add("Wallpaper/wall17.jpg");
        arrayList.add("Wallpaper/wall18.jpg");
        arrayList.add("Wallpaper/wall19.jpg");
        arrayList.add("Wallpaper/wall20.jpg");
        arrayList.add("Wallpaper/wall21.jpg");
        arrayList.add("Wallpaper/wall22.jpg");
        arrayList.add("Wallpaper/wall23.jpg");
        arrayList.add("Wallpaper/wall24.jpg");
        arrayList.add("Wallpaper/wall25.jpg");
        arrayList.add("Wallpaper/wall26.jpg");
        arrayList.add("Wallpaper/wall27.jpg");
        arrayList.add("Wallpaper/wall28.jpg");
        arrayList.add("Wallpaper/wall29.jpg");
        arrayList.add("Wallpaper/wall30.jpg");
        return arrayList;
    }


    public static Bitmap loadBitmapFromAssets(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            Bitmap decodeStream = BitmapFactory.decodeStream(open);
            open.close();
            return decodeStream;
        } catch (Exception unused) {
            return null;
        }
    }

}
