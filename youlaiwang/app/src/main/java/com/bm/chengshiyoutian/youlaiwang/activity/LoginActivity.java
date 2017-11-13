package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.fragment.DengLuFragment;
import com.bm.chengshiyoutian.youlaiwang.fragment.ZhuCeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by sld on 2017/5/10.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.ll_zhaohui)
    LinearLayout llZhaohui;
    private TextView tv1;
    private TextView tv2;
    private LinearLayout ll, ll_test;
    private Fragment fragment1, fragment2;
    private int screenWidth;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.BLACK);


        }


        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，如：800p）


        initView();
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        String aBoolean = sp.getString(MyRes.MY_TOKEN, "kong");
        if (aBoolean.equals("kong")) {

        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //设置点击空白处，自动隐藏键盘
        llZhaohui.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        ll_test = (LinearLayout) findViewById(R.id.ll_test);
        ll = (LinearLayout) findViewById(R.id.ll);
        setDefaultFragment();
        ll_test.getLayoutParams().height = (int) (screenWidth * 0.68);
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fragment2 = ZhuCeFragment.newInstance("");
        transaction.replace(R.id.ll, fragment2);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * 登陆
             *
             */
            case R.id.tv1:
                tv1.setTextColor(getResources().getColor(R.color.text));
                tv2.setTextColor(getResources().getColor(R.color.text_hei));
                FragmentManager fm1 = getSupportFragmentManager();
                FragmentTransaction transaction1 = fm1.beginTransaction();
                fragment1 = DengLuFragment.newInstance("");
                transaction1.replace(R.id.ll, fragment1);
                transaction1.commit();
                break;

            /**
             * 商户注册？
             *
             */
            case R.id.tv2:
                tv1.setTextColor(getResources().getColor(R.color.text_hei));
                tv2.setTextColor(getResources().getColor(R.color.text));
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction transaction2 = fm2.beginTransaction();
                fragment2 = ZhuCeFragment.newInstance("");
                transaction2.replace(R.id.ll, fragment2);
                transaction2.commit();
                break;

        }

    }
}
