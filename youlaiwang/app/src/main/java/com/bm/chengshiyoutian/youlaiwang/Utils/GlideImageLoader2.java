package com.bm.chengshiyoutian.youlaiwang.Utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader2 extends ImageLoader {

    public GlideImageLoader2() {

    }

    public GlideImageLoader2(Activity context) {

    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        try {
            Glide.with(context).load(path).fitCenter().into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }



    }

}