package com.bm.chengshiyoutian.youlaiwang.activity;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.fragment.LunTanFragment;
import com.bm.chengshiyoutian.youlaiwang.fragment.ShouYeFragment;
import com.jaeger.library.StatusBarUtil;




public class HomeActivity extends AppCompatActivity {

    private ShouYeFragment locationFragment1;
    private LunTanFragment locationFragment2;
    private LunTanFragment locationFragment3;
    private LunTanFragment locationFragment4;
    private LunTanFragment locationFragment5;

    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        initView();
        StatusBarUtil.setColorForSwipeBack(this, 0xff0bb04a, 0);
        setDefaultFragment();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        locationFragment1 = ShouYeFragment.newInstance("");
        transaction.replace(R.id.tb, locationFragment1);
        transaction.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();

//        images= (ArrayList<TImage>) getIntent().getSerializableExtra("images");
//        if (images!=null&&images.size()>0){
//            Toast.makeText(this,"1231",Toast.LENGTH_SHORT).show();
//            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
//            locationFragment5 = locationFragment5.newInstance("");
//            transaction.replace(R.id.tb, locationFragment5);
//            transaction.commit();
//
//
//        }
    }


    private void initView() {
        rg = (RadioGroup) findViewById(R.id.rg);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        rb5 = (RadioButton) findViewById(R.id.rb5);
        rg.check(R.id.rb1);
        rb1.setTextColor(0xff94d9ff);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                FragmentManager fm = getSupportFragmentManager();
                //开启事务
                FragmentTransaction transaction = fm.beginTransaction();
                switch (checkedId) {
                    case R.id.rb1:
                        rb1.setTextColor(0xff94d9ff);
                        rb2.setTextColor(0xff8c8c8c);
                        rb3.setTextColor(0xff8c8c8c);
                        rb4.setTextColor(0xff8c8c8c);
                        rb5.setTextColor(0xff8c8c8c);
                        if (locationFragment1 == null) {
                            locationFragment1 = ShouYeFragment.newInstance("位置");
                        }
                        transaction.replace(R.id.tb, locationFragment1);
                        break;
                    case R.id.rb2:
                        rb2.setTextColor(0xff94d9ff);
                        rb1.setTextColor(0xff8c8c8c);
                        rb3.setTextColor(0xff8c8c8c);
                        rb4.setTextColor(0xff8c8c8c);
                        rb5.setTextColor(0xff8c8c8c);
                        if (locationFragment2 == null) {
                            locationFragment2 = LunTanFragment.newInstance("论坛");
                        }
                        transaction.replace(R.id.tb, locationFragment2);
                        break;
                    case R.id.rb3:
                        rb3.setTextColor(0xff94d9ff);
                        rb2.setTextColor(0xff8c8c8c);
                        rb1.setTextColor(0xff8c8c8c);
                        rb4.setTextColor(0xff8c8c8c);
                        rb5.setTextColor(0xff8c8c8c);
                        if (locationFragment3 == null) {
                            locationFragment3 = LunTanFragment.newInstance("视频");
                        }
                        transaction.replace(R.id.tb, locationFragment3);
                        break;
                    case R.id.rb4:
                        rb4.setTextColor(0xff94d9ff);
                        rb2.setTextColor(0xff8c8c8c);
                        rb3.setTextColor(0xff8c8c8c);
                        rb1.setTextColor(0xff8c8c8c);
                        rb5.setTextColor(0xff8c8c8c);
                        if (locationFragment4 == null) {
                            locationFragment4 = LunTanFragment.newInstance("签到");
                        }
                        transaction.replace(R.id.tb, locationFragment4);
                        break;
                    case R.id.rb5:
                        rb5.setTextColor(0xff94d9ff);
                        rb2.setTextColor(0xff8c8c8c);
                        rb3.setTextColor(0xff8c8c8c);
                        rb4.setTextColor(0xff8c8c8c);
                        rb1.setTextColor(0xff8c8c8c);
                        if (locationFragment5 == null) {
                            locationFragment5 = LunTanFragment.newInstance("");
                        }
                        transaction.replace(R.id.tb, locationFragment5);
                        break;
                    default:
                        break;
                }
                // 事务提交
                transaction.commit();
            }
        });


    }

    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                   finish();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }



    //  @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//            /*隐藏软键盘*/
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (inputMethodManager.isActive()) {
//                inputMethodManager.hideSoftInputFromWindow(PayActivity.this.getCurrentFocus().getWindowToken(), 0);
//                Log.d("123","2222222222222222");
//            }else {
//
//
//                Log.d("123","11111111111");
//
//            }
//
//
//           Intent intent = new Intent(this, SearchActivity.class);
//           intent.putExtra("key", locationFragment1.getSearch());
//           startActivity(intent);
//            return false;
//        }
//        return super.dispatchKeyEvent(event);
//    }
}