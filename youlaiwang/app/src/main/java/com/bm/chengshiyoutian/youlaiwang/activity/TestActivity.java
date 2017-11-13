package com.bm.chengshiyoutian.youlaiwang.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bm.chengshiyoutian.youlaiwang.R;


/**
 * Created by sld on 2017/5/16.
 */

public class TestActivity extends AppCompatActivity {
    private TabLayout tab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tab.addTab(tab.newTab().setText("水产海鲜 1"));
        tab.addTab(tab.newTab().setText("水产海鲜 2"));
        tab.addTab(tab.newTab().setText("水产海鲜 3"));
        tab.addTab(tab.newTab().setText("水产海鲜 4"));
        tab.addTab(tab.newTab().setText("水产海鲜 5"));
        tab.addTab(tab.newTab().setText("水产海鲜 6"));
        tab.addTab(tab.newTab().setText("水产海鲜 7"));
        tab.addTab(tab.newTab().setText("水产海鲜 8"));
        tab.addTab(tab.newTab().setText("水产海鲜 9"));


    }
}
