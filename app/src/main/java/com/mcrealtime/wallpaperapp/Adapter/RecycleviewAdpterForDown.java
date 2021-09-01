package com.mcrealtime.wallpaperapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mcrealtime.wallpaperapp.AdUtills.AdManagerFB;
import com.mcrealtime.wallpaperapp.R;
import com.mcrealtime.wallpaperapp.WallSetActivity;
import com.mcrealtime.wallpaperapp.utils.AssetUtils;

import java.util.List;

public class RecycleviewAdpterForDown extends RecyclerView.Adapter<RecycleviewAdpterForDown.RecycleviewHolderForDown>{

    Context context;
    List<String> list;


    public RecycleviewAdpterForDown(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecycleviewHolderForDown onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);

        View view=inflater.inflate(R.layout.image_item_layout,parent,false);
        return new RecycleviewHolderForDown(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecycleviewHolderForDown holder, int position) {

        Glide.with(context).load(AssetUtils.loadBitmapFromAssets(context,list.get(position))).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                return false;
            }

        }).into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdManagerFB.getInstance().displayInterstitialAd((Activity) context, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        context.startActivity(new Intent(context, WallSetActivity.class).putExtra("id",position));

                    }
                });



            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleviewHolderForDown extends RecyclerView.ViewHolder {

        ImageView imageView;
        public RecycleviewHolderForDown(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);

        }
    }

}
