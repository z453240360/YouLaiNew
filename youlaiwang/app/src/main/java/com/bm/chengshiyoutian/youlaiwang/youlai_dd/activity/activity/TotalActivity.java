package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.fragment.WoDeFragment2;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.ShoppingCar.ShoppingFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment.DingDanFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment.FirstFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment.ShoppingCarFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TotalActivity extends AppCompatActivity {

    @Bind(R.id.mRg_main)
    RadioGroup mRgMain;
    @Bind(R.id.frame_fragment)
    FrameLayout frameFragment;

    private ArrayList<Fragment> list = new ArrayList<>();
    private FragmentManager managers;
    private Fragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorState.setWindowStatusBarColorBlue(this, Color.BLUE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_total);

        ButterKnife.bind(this);

        initFragment();
        initRadioButton();
    }

    private void initFragment() {
        managers = getSupportFragmentManager();

        FirstFragment firstFragment = new FirstFragment();
        ShoppingFragment shoppingFragment = new ShoppingFragment();

        ShoppingCarFragment shoppingCarFragment =new ShoppingCarFragment();

        DingDanFragment dingDanFragment = new DingDanFragment();

        WoDeFragment2 woDeFragment2 = new WoDeFragment2();


        list.add(firstFragment);
        list.add(shoppingFragment);
//        list.add(shoppingCarFragment);
        list.add(dingDanFragment);
        list.add(woDeFragment2);

        managers.beginTransaction().add(R.id.frame_fragment, list.get(0)).commit();
        lastFragment = list.get(0);
    }



    private void initRadioButton() {
        mRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton select = (RadioButton) findViewById(i);
                int index = Integer.parseInt(select.getTag().toString());
                if (list.get(index).isAdded()) {
                    managers.beginTransaction().show(list.get(index)).commit();
                } else {
                    managers.beginTransaction().add(R.id.frame_fragment, list.get(index)).commit();
                }
                managers.beginTransaction().hide(lastFragment).commit();
                lastFragment = list.get(index);
            }
        });

    }
}
