package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment.GarvementFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment.NewsFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FashionActivity extends AppCompatActivity {

    @Bind(R.id.toolbars_longhu)
    Toolbar toolbarsLonghu;
    @Bind(R.id.mRb_longhu_geren)
    RadioButton mRbLonghuGeren;
    @Bind(R.id.mRb_longhu_tuandui)
    RadioButton mRbLonghuTuandui;
    @Bind(R.id.mRg_longhu)
    RadioGroup mRgLonghu;
    @Bind(R.id.frame_longhu)
    FrameLayout frameLonghu;


    private ArrayList<Fragment> list = new ArrayList<>();
    private FragmentManager managers;
    private Fragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorState.setWindowStatusBarColorBlue(this, Color.BLUE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fashion);
        ButterKnife.bind(this);

        initToolBar();
        initFragment();
        initRadioButton();
    }

    private void initToolBar() {
        setSupportActionBar(toolbarsLonghu);
        toolbarsLonghu.setNavigationIcon(R.mipmap.gouwuche_fanhui);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    private void initFragment() {
        managers = getSupportFragmentManager();
        GarvementFragment garvementFragment = new GarvementFragment();
        NewsFragment newsFragment = new NewsFragment();
        list.add(garvementFragment);
        list.add(newsFragment);
        managers.beginTransaction().add(R.id.frame_longhu, list.get(0)).commit();
        lastFragment = list.get(0);
    }

    private void initRadioButton() {
        mRgLonghu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton select = (RadioButton) findViewById(i);
                int index = Integer.parseInt(select.getTag().toString());

                if (list.get(index).isAdded()) {
                    managers.beginTransaction().show(list.get(index)).commit();
                } else {
                    managers.beginTransaction().add(R.id.frame_longhu, list.get(index)).commit();
                }

                managers.beginTransaction().hide(lastFragment).commit();
                lastFragment = list.get(index);
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
