package com.bm.chengshiyoutian.youlaiwang.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader1 extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {


        Glide.with(context).load(path).fitCenter().into(imageView);


    }

}