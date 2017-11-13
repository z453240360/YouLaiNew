package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;

import cn.jpush.android.api.JPushInterface;


/**
 * 开始页
 * Created by youj on 2016/1/8.
 */
public class StartActivity extends Activity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_start);

        //CacheImageService.startsevice(this);
        init();
    }

    private void init() {
        final boolean isFirst = SPUtil.getBoolean(this, Constants.FIRST_OPEN);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFirst) {
                    toGuide();
                } else {
                    toHome();
                }
            }
        }, 3000);

        SPUtil.put(this, Constants.FIRST_OPEN, true);
    }

    /**
     * 去引导页
     */
    private void toGuide() {
        startActivity(new Intent(StartActivity.this, LeaderPagerActivity.class));
        finish();
    }

    /**
     * 去主页
     */
    private void toHome() {

        //if (SPUtil.getBoolean(this, Constants.IS_LOGIN)) {
        if (!TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
            JPushInterface.setAlias(getApplicationContext(), MyApplication.getInstance().getUser().id, null);
        }

        startActivity(new Intent(StartActivity.this,XiangQingActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

}
