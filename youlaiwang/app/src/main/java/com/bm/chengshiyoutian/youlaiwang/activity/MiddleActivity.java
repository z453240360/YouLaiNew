package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bm.chengshiyoutian.youlaiwang.R;

public class MiddleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);

        startActivity(new Intent(this,MainActivity.class));

    }
}
