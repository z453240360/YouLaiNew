package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.FrameLayout;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.fragment.GongYingShangFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NativeActivity extends AppCompatActivity {

    @Bind(R.id.native_frame)
    FrameLayout nativeFrame;
    private FragmentManager ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorState.setWindowStatusBarColorBlue(this, Color.BLUE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_native);
        ButterKnife.bind(this);

        ma = getSupportFragmentManager();
        ma.beginTransaction().add(R.id.native_frame,new GongYingShangFragment()).commit();
    }
}
