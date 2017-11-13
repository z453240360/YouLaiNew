package com.bm.chengshiyoutian.youlaiwang.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {




    public Context mContext;

    public GlideImageLoader(Context context) {
        this.mContext = context;
    }


    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        try {
            Glide.with(mContext).load(path).centerCrop().into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}