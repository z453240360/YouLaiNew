package com.bm.chengshiyoutian.youlaiwang.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CommonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.FragmentFactory;
import com.jaeger.library.StatusBarUtil;



/**
 * Created by sld on 2017/5/11.
 */

public class ShangPinSouSuoActivity extends AppCompatActivity {

    private TabLayout tab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangpinsousuo);
        initView();
        tab.setupWithViewPager(viewPager);
        StatusBarUtil.setColorForSwipeBack(this, 0xff0bb04a, 0);

    }


    private void initView() {

        tab = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new BasePagerAdapter(getSupportFragmentManager()));

    }


    class BasePagerAdapter extends FragmentPagerAdapter {
        String[] titles;

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
            this.titles = CommonUtils.getStringArray(R.array.shangpin_title);
        }


        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createShangPinFragment(position);
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
