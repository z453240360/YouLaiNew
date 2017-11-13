package com.bm.chengshiyoutian.youlaiwang.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by sld on 2017/6/13.
 */

public class PicActivity extends AppCompatActivity {
    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popu);
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        Glide.with(this)
                .load(getIntent().getStringExtra("url")).centerCrop().into(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
