package com.mcrealtime.wallpaperapp.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class RecycleviewAdpterForDownloaded extends RecyclerView.Adapter<RecycleviewAdpterForDownloaded.RecycleviewHolderForCategory> {

    Context context;
    List<String> list;


    public RecycleviewAdpterForDownloaded(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecycleviewHolderForCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);

        View view=inflater.inflate(R.layout.image_down_item_layout,parent,false);
        return new RecycleviewHolderForCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleviewHolderForCategory holder, int position) {

        Glide.with(context).load(list.get(position)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                return false;
            }

        }).into(holder.imageView);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.newcustom_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
                dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        try {
                            deleteFile((String) list.get(position), position);

                        } catch (ArrayIndexOutOfBoundsException unused) {
                        }
                    }
                });

                dialog.show();


            }
        });

        holder.gallaryset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeFile(list.get(position));
                WallpaperManager manager = WallpaperManager.getInstance(context);
                try {
                    manager.setBitmap(bitmap);
                    Toast.makeText(context, "Wallpaper set success!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdManagerFB.getInstance().displayInterstitialAd((Activity) context, new AdManagerFB.AdCallBack() {
                    @Override
                    public void onAdDismiss(String str) {
                        try {
                            int item=Integer.valueOf(new File(list.get(position)).getName().replace("wall","").replace(".jpg",""));
                            context.startActivity(new Intent(context, WallSetActivity.class).putExtra("id",item));

                        }
                        catch (Exception e)
                        {

                        }

                    }
                });




            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleviewHolderForCategory extends RecyclerView.ViewHolder {

        ImageView imageView,gallaryset,delete;

        public RecycleviewHolderForCategory(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            gallaryset=itemView.findViewById(R.id.gallaryset);
            delete=itemView.findViewById(R.id.delete);

        }
    }


    public void deleteFile(String str, int i) {
        File file = new File(str);
        if (!file.exists()) {
            return;
        }
        if (file.delete()) {
            list.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, list.size());
            notifyDataSetChanged();

            Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("file Deleted :");
            sb.append(str);
            printStream.println(sb.toString());
            return;
        }
        Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        PrintStream printStream2 = System.out;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("file not Deleted :");
        sb2.append(str);
        printStream2.println(sb2.toString());
    }

}
