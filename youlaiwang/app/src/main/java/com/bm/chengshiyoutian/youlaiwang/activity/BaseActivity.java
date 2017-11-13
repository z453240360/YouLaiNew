package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;


public class BaseActivity extends AppCompatActivity {
 public SharedPreferences sp;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        sp=getSharedPreferences(MyRes.CONFIG,0);
    }


}