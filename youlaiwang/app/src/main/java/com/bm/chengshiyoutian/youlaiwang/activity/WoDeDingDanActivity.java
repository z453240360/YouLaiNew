package com.bm.chengshiyoutian.youlaiwang.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CommonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.FragmentFactory;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_All;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiFaHuo;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiFuKuan;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiPingJia;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiShouHuo;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_YiQuXiao;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;


/**
 * Created by sld on 2017/5/11.
 */

public class WoDeDingDanActivity extends AppCompatActivity {
    private ImageView iv;
    private Toolbar tb_toolbar;
    private TabLayout tab;
    private ViewPager viewPager;
    public static WoDeDingDanActivity wodeInstance;
    private ArrayList<Fragment> list = new ArrayList<Fragment>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodedingdan);
        wodeInstance = this;
        initData();
        initView();

        tab.setupWithViewPager(viewPager);
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);

        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb_toolbar.setNavigationIcon(R.mipmap.gouwuche_fanhui);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        list.add(new Fragment_All());
        list.add(new Fragment_DaiFuKuan());
        list.add(new Fragment_DaiFaHuo());
        list.add(new Fragment_DaiShouHuo());
        list.add(new Fragment_DaiPingJia());
        list.add(new Fragment_YiQuXiao());
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        tab = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new BasePagerAdapter(getSupportFragmentManager()));
        int current = getIntent().getIntExtra("current", 0);
        viewPager.setCurrentItem(current,false);
    }


    class BasePagerAdapter extends FragmentPagerAdapter {
        String[] titles;

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
            this.titles = CommonUtils.getStringArray(R.array.ding_dan_titles);
        }


        @Override
        public Fragment getItem(int position) {
//            return list.get(position);
            return FragmentFactory.createDingDanFragment(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
