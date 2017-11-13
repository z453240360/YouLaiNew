package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_All;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiFaHuo;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiFuKuan;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiPingJia;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiShouHuo;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_YiQuXiao;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.id.list;

public class MyOrderActivity extends AppCompatActivity {

    @Bind(R.id.toolbars_longhu)
    Toolbar toolbarsLonghu;
    @Bind(R.id.strip)
    PagerTabStrip strip;
    @Bind(R.id.pager)
    ViewPager pager;

    private ArrayList<Fragment> list = new ArrayList<Fragment>();

    private String[] titles = {"全部","待付款","待发货","待收货","待评价","已取消"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);

        initToolBar();
        initData();


        strip.setTabIndicatorColor(Color.GREEN);
        strip.setTextColor(Color.BLACK);
        strip.setBackgroundColor(Color.WHITE);
        strip.setTextSpacing(400);



        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    private void initToolBar() {
        setSupportActionBar(toolbarsLonghu);
        toolbarsLonghu.setNavigationIcon(R.mipmap.gouwuche_fanhui);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initData() {
        list.add(new Fragment_All());
        list.add(new Fragment_DaiFuKuan());
        list.add(new Fragment_DaiFaHuo());
        list.add(new Fragment_DaiShouHuo());
        list.add(new Fragment_DaiPingJia());
        list.add(new Fragment_YiQuXiao());
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
