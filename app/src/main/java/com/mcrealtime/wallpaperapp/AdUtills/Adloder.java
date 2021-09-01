package com.mcrealtime.wallpaperapp.AdUtills;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import androidx.cardview.widget.CardView;

import com.mcrealtime.wallpaperapp.R;


public class Adloder {
    private static Adloder mInstance;
    Dialog dialog;

    public static Adloder getInstance() {
        if (mInstance == null) {
            mInstance = new Adloder();
        }
        return mInstance;
    }

    public void showLoader(Activity activity) {
        dialog = new Dialog(activity);
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        CardView cardView = (CardView) layoutInflater.inflate(R.layout.loader_dialog, null);
        dialog.setContentView(cardView);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void dismissLoader() {
        dialog.dismiss();
    }
}


