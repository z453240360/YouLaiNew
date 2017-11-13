package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 引导页
 * Created by youj on 2016/1/8.
 */
public class LeaderPagerActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.vPager)
    ViewPager vPager;
    @Bind(R.id.bt_start)
    TextView btStart;
    private ArrayList<View> views;
    private MyAdapter adapter;
    private int[] imgs = new int[]{
           R.mipmap.p1,
           R.mipmap.p2,
//           R.mipmap.p3_1,
            R.mipmap.p3_1
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_leader);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        btStart.setOnClickListener(this);
        views = new ArrayList<>();
        for(int resid:imgs){
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(resid);
            views.add(iv);
        }
        adapter = new MyAdapter();
        vPager.setAdapter(adapter);
        vPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_start:
                startActivity(new Intent(this,XiangQingActivity.class));
                finish();
                break;

            default:
                 break;
        }
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            vPager.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = views.get(position);
            vPager.removeView(view);
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        //控制按钮的显示
        if(arg0 == adapter.getCount() - 1){
            //显示
            btStart.setVisibility(View.VISIBLE);
        }else{
            //隐藏
            btStart.setVisibility(View.GONE);
        }
    }
}
