package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bumptech.glide.Glide;


/**
 * Created by Administrator on 2017/10/23.
 */

public class MyLagerImageView extends FrameLayout {
    private PhotoView largeImg;
    public MyLagerImageView(Context context) {
        this(context,null);
    }

    public MyLagerImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyLagerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_img, null);
        addView(view);
        largeImg = (PhotoView) view.findViewById(R.id.view_large);
        largeImg.enable();


    }

    public void setLargeImg(Context context,String s){
        Glide.with(context)
                .load(s)
                .into(largeImg);
    }
}
