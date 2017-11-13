package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.fragment.GovernmentFragment;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.fragment.SystemFragment;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/7/21 15:57
 * @Description 消息 包含 系统消息 和 政府消息
 */
public class NewsActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rl_xitong_news)
    RelativeLayout rl_xitong_news;
    @Bind(R.id.tv_xitong_news)
    TextView tv_xitong_news;
    @Bind(R.id.view_xitong_news)
    View view_xitong_news;
    @Bind(R.id.rl_zhengfu_news)
    RelativeLayout rl_zhengfu_news;
    @Bind(R.id.tv_zhengfu_news)
    TextView tv_zhengfu_news;
    @Bind(R.id.view_zhengfu_news)
    View view_zhengfu_news;
    @Bind(R.id.vp_news)
    ViewPager vp_news;

    @Bind(R.id.iv_sys_tip)
    ImageView iv_sys_tip;
    @Bind(R.id.iv_gov_tip)
    ImageView iv_gov_tip;

    private ArrayList<Fragment> mFragments;

    private int type = 0;
    private int mExistNewGovInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        title.setText("消息");
        getIntentData();
        ivLeft.setOnClickListener(this);
        rl_xitong_news.setOnClickListener(this);
        rl_zhengfu_news.setOnClickListener(this);
        vp_news.addOnPageChangeListener(this);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            int mExistNewSysInfo = intent.getIntExtra("mExistNewSysInfo", 0);
            mExistNewGovInfo = intent.getIntExtra("mExistNewGovInfo", 0);
            if (1 == mExistNewSysInfo) {
                type = 0;
                iv_sys_tip.setVisibility(View.VISIBLE);
                clearUnreadStatus(MyApplication.getInstance().getUser().id, "1");
                iv_sys_tip.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv_sys_tip.setVisibility(View.GONE);
                    }
                }, 1500);
            } else {
                iv_sys_tip.setVisibility(View.GONE);
            }
            if (1 == mExistNewGovInfo) {
                iv_gov_tip.setVisibility(View.VISIBLE);
            } else {
                iv_gov_tip.setVisibility(View.GONE);
            }
        }
    }

    private void initData() {
        SystemFragment systemFragment = new SystemFragment();
        GovernmentFragment governmentFragment = new GovernmentFragment();
        mFragments = new ArrayList<>();
        mFragments.add(systemFragment);
        mFragments.add(governmentFragment);

        NewsAdapter newsAdapter = new NewsAdapter(getSupportFragmentManager());
        vp_news.setAdapter(newsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.rl_xitong_news://系统消息
                type = 0;
                vp_news.setCurrentItem(0);
                break;
            case R.id.rl_zhengfu_news://政府消息
                type = 1;
                vp_news.setCurrentItem(1);
                break;
        }
    }

    private void selectXiTongNews() {
        tv_xitong_news.setTextColor(getResources().getColor(R.color.yello));
        view_xitong_news.setBackgroundColor(getResources().getColor(R.color.yello));
        tv_zhengfu_news.setTextColor(getResources().getColor(R.color.white));
        view_zhengfu_news.setBackgroundColor(getResources().getColor(R.color.bill_color));
    }

    private void selectZhengFuNews() {
        tv_xitong_news.setTextColor(getResources().getColor(R.color.white));
        view_xitong_news.setBackgroundColor(getResources().getColor(R.color.bill_color));
        tv_zhengfu_news.setTextColor(getResources().getColor(R.color.yello));
        view_zhengfu_news.setBackgroundColor(getResources().getColor(R.color.yello));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        type = position;
        switch (position) {
            case 0:
                selectXiTongNews();
                break;
            case 1:
                if (mExistNewGovInfo == 1) {
                    clearUnreadStatus(MyApplication.getInstance().getUser().id, "2");
                }
                selectZhengFuNews();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 清楚未读消息
     *
     * @param userid
     * @param type:消息类型(1 系统消息，2政府消息,3公告)
     */
    private void clearUnreadStatus(String userid, String type) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("userid", userid);
        params.put("type", type);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(10000);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "ClearUnreadStatus", params, config, this);
    }

    @InjectHttpOk
    public void ok(ResponseEntity entity) {
        String contentAsString = entity.getContentAsString();
        int key = entity.getKey();
        switch (key) {
            case 0://处理消息
                clearNews(contentAsString);
                break;
        }
    }

    @InjectHttpErr
    public void err(ResponseEntity entity) {
    }

    private void clearNews(String contentAsString) {
        if (!MyUtils.isEmpty(contentAsString)) {
            try {
                JSONObject jsonObject = new JSONObject(contentAsString);
                String status = jsonObject.optString("status");
                if ("0".equals(status)) {
                    //调用成功
                    if (type == 0) {
                        iv_sys_tip.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iv_sys_tip.setVisibility(View.GONE);
                            }
                        }, 1000);
                    } else {
                        mExistNewGovInfo = 0;
                        iv_gov_tip.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iv_gov_tip.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    private class NewsAdapter extends FragmentPagerAdapter {

        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
