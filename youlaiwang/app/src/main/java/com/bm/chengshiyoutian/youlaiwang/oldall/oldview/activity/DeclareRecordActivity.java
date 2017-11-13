package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectBefore;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.MyFragmentPagerAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.fragment.DeclareFragment;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.fragment.ReceivedFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 交油记录
 * Created by jayen on 16/1/13.
 */
public class DeclareRecordActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_type1)
    TextView tvType1;
    @Bind(R.id.tv_type2)
    TextView tvType2;
    @Bind(R.id.ll_type)
    LinearLayout llType;
    @Bind(R.id.vPager)
    ViewPager vPager;

    private ArrayList<Fragment> fragmentList;

    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_declare_record);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ivLeft.setOnClickListener(this);
        title.setText("交油记录");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.home);
        ivRight.setOnClickListener(this);
        tvType1.setOnClickListener(this);
        tvType2.setOnClickListener(this);
        initViewPager();

    }

    private void initViewPager() {
        title.setText("交油记录");
        fragmentList = new ArrayList<Fragment>();
        Fragment declareFragment = new DeclareFragment();
        Fragment receivedFragment = new ReceivedFragment();
        fragmentList.add(receivedFragment);
        fragmentList.add(declareFragment);
        vPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        vPager.setCurrentItem(0);
        vPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.iv_right:
                startActivity(new Intent(this, XiangQingActivity.class));
                finish();
                break;

            case R.id.tv_type1:
                vPager.setCurrentItem(0);

                break;
            case R.id.tv_type2:
                vPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            llType.setBackgroundResource(R.mipmap.type1);
            tvType1.setTextColor(Color.WHITE);
            tvType2.setTextColor(getResources().getColor(R.color.black));
        } else  if (position == 1) {
            llType.setBackgroundResource(R.mipmap.type2);
            tvType1.setTextColor(getResources().getColor(R.color.black));
            tvType2.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
