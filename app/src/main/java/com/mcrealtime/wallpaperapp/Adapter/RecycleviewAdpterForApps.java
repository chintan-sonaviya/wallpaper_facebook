package com.mcrealtime.wallpaperapp.Adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kikt.view.CustomRatingBar;
import com.mcrealtime.wallpaperapp.AdUtills.AdManagerFB;
import com.mcrealtime.wallpaperapp.R;
import com.mcrealtime.wallpaperapp.model.Apps;

import java.util.List;

public class RecycleviewAdpterForApps extends RecyclerView.Adapter<RecycleviewAdpterForApps.AppsviewHolder> {


    private Context context;
    private List<Apps> list;
    private int lay = 0;

    public RecycleviewAdpterForApps(Context context, List<Apps> list, int lay) {
        this.context = context;
        this.list = list;
        this.lay = lay;
    }

    @NonNull
    @Override
    public AppsviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (lay == 1) {
            View view = inflater.inflate(R.layout.apps_horiz, parent, false);
            return new AppsviewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.apps_vertical, parent, false);
            return new AppsviewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AppsviewHolder holder, int position) {

        holder.icon.setImageResource(list.get(position).getIcon());
        holder.title.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lay != 1) {

                    AdManagerFB.getInstance().displayInterstitialAd((Activity) context, new AdManagerFB.AdCallBack() {
                        @Override
                        public void onAdDismiss(String str) {
                            Intent goToMarket = new Intent("android.intent.action.VIEW", Uri.parse(list.get(position).getUrl()));
                            try {
                                context.startActivity(goToMarket);
                            } catch (ActivityNotFoundException e) {
                                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(list.get(position).getUrl())));
                            }
                        }
                    });
                }

            }
        });

        if (lay==1) {
            holder.install.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent goToMarket = new Intent("android.intent.action.VIEW", Uri.parse(list.get(position).getUrl()));
                    try {
                        context.startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(list.get(position).getUrl())));
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class AppsviewHolder extends RecyclerView.ViewHolder {

        TextView title;
        CustomRatingBar rating;
        ImageView icon;
        Button install;


        public AppsviewHolder(@NonNull View itemView) {
            super(itemView);

            if (lay == 1) {
                title = itemView.findViewById(R.id.title);
                icon = itemView.findViewById(R.id.icon);
                rating = itemView.findViewById(R.id.rating);
                install = itemView.findViewById(R.id.install);

            } else {
                title = itemView.findViewById(R.id.title);
                icon = itemView.findViewById(R.id.icon);

            }


        }
    }
}
