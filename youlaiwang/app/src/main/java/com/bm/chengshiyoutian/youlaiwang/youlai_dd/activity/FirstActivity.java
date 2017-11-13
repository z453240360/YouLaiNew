package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FirstActivity extends BaseActivity {

    @Bind(R.id.mTxt_jinjian_Activity)
    TextView mTxtJinjianActivity;
    @Bind(R.id.toolbars)
    Toolbar toolbar;
    @Bind(R.id.activity_first)
    RelativeLayout activityFirst;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.gouwuche_fanhui);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}
