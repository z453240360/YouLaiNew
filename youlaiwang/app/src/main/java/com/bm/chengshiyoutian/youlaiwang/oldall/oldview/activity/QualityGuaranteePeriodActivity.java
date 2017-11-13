package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pc.ioc.event.EventBus;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.BaoZhiQiXianAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.EventBusBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.datetimepick.DatePickerPopupUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.DialogActionInterface;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.DialogSelectTimeInterface;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.DialogUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/5/24 18:07
 * @Description 保质期限
 */
public class QualityGuaranteePeriodActivity extends AppCompatActivity implements View.OnClickListener, DialogSelectTimeInterface, DatePickerPopupUtil.DateCallBck {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.lv_qixian)
    ListView lv_qixian;

    @Bind(R.id.bt_done)
    Button bt_done;

    @Bind(R.id.ll_dialog_tip)
    LinearLayout ll_dialog_tip;

    @Bind(R.id.tv_product_name)
    TextView tv_product_name;
    @Bind(R.id.tv_product_num)
    TextView tv_product_num;
    @Bind(R.id.tv_product_unit)
    TextView tv_product_unit;

    private TextView mTv_action_message;
    private TextView mTv_select_time;
    private TextView mTv_shengchan_pihao;
    private TextView mTv_sure_time;
    private EditText mEt_select_time;
    private TextView textViewAction;//当前点击的保质期限或生产批号
    private int selectPosition;//当前选择的条目的下标（保质期限或生产批号）

    private DatePickerPopupUtil datePickerPopupUtil;
    private ArrayList<RepositoryBillBean> mBillBeans;
    private ArrayList<RepositoryBillBean> mBillBeansBefore;//开始传入到该界面的集合（在弹出保存对话框如果点击否了之后，只清楚本次填写的内容，就将该集合返回）
    private EventBus mMEventBus;
    private EventBusBean mEventBusBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_quality_guarantee_period);
        ButterKnife.bind(this);
        init();
        mMEventBus = EventBus.getDefault();
    }

    private void init() {
        getIntentData();
        initView();
        initData();
        setListener();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            mBillBeans = intent.getParcelableArrayListExtra("billBeans");
            if (mBillBeans != null) {
                mBillBeansBefore = new ArrayList<>();
                for (RepositoryBillBean repositoryBillBean : mBillBeans) {
                    RepositoryBillBean repositoryBillBeanTemp = new RepositoryBillBean(repositoryBillBean.productName, repositoryBillBean.productNum,
                            repositoryBillBean.productNnit, repositoryBillBean.productTime, repositoryBillBean.productPiHao);
                    repositoryBillBeanTemp.productTempNum = repositoryBillBean.productTempNum;
                    mBillBeansBefore.add(repositoryBillBeanTemp);
                }
            }
        }
    }

    private void initView() {
        title.setText("保质期限");
        mTv_action_message = (TextView) findViewById(R.id.tv_action_message);
        mTv_select_time = (TextView) findViewById(R.id.tv_select_time);
        mEt_select_time = (EditText) findViewById(R.id.et_select_time);
        mTv_shengchan_pihao = (TextView) findViewById(R.id.tv_shengchan_pihao);
        mTv_sure_time = (TextView) findViewById(R.id.tv_sure_time);
    }

    private void initData() {
        datePickerPopupUtil = new DatePickerPopupUtil(getApplicationContext(), QualityGuaranteePeriodActivity.this);
        BaoZhiQiXianAdapter baoZhiQiXianAdapter = new BaoZhiQiXianAdapter(this, this);
        lv_qixian.setAdapter(baoZhiQiXianAdapter);
        baoZhiQiXianAdapter.setData(mBillBeans);
        baoZhiQiXianAdapter.notifyDataSetChanged();
    }

    public void setListener() {
        ivLeft.setOnClickListener(this);
        mTv_select_time.setOnClickListener(this);
        ll_dialog_tip.setOnClickListener(this);
        mTv_sure_time.setOnClickListener(this);
        bt_done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                ll_dialog_tip.setVisibility(View.GONE);
                showSaveTip();
                break;
            case R.id.ll_dialog_tip:
                ll_dialog_tip.setVisibility(View.GONE);
                break;
            case R.id.tv_select_time://选择保质期限
                Calendar c = Calendar.getInstance();
                datePickerPopupUtil.setCurrent(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerPopupUtil.getDatePicker().showAtLocation(
                        mTv_select_time,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_sure_time://选择时间确定或输入生产批号确定
                if (View.VISIBLE == mTv_select_time.getVisibility()) {
                    if (!"请填写保质期限".equals(mTv_select_time.getText())) {
                        textViewAction.setText(mTv_select_time.getText());
                        textViewAction.setTextColor(Color.WHITE);
                        if (mBillBeans != null) {
                            mBillBeans.get(selectPosition).productTime = mTv_select_time.getText().toString();
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(mEt_select_time.getText().toString()) && !"".equals(mEt_select_time.getText().toString().trim())) {
                        textViewAction.setText(mEt_select_time.getText());
                        textViewAction.setTextColor(Color.WHITE);
                        if (mBillBeans != null) {
                            mBillBeans.get(selectPosition).productPiHao = mEt_select_time.getText().toString();
                        }
                    }
                }
                ll_dialog_tip.setVisibility(View.GONE);
                break;
            case R.id.bt_done://完成
                sendEventBus(mBillBeans);
                finish();
                break;
        }
    }

    private void showSaveTip() {
        DialogUtils dialogUtils = new DialogUtils();
        dialogUtils.showDialogOrSure(getString(R.string.save_data), QualityGuaranteePeriodActivity.this, QualityGuaranteePeriodActivity.this, new DialogActionInterface() {
            @Override
            public void sureAction() {
                sendEventBus(mBillBeans);
                finish();
            }

            @Override
            public void cancelAction() {
                if (mBillBeansBefore != null) {
                    sendEventBus(mBillBeansBefore);
                }
                finish();
            }
        });
    }

    //显示保质期限和生产批号的对话框
    public void showDialogTipAddTime() {
        ll_dialog_tip.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showSaveTip();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void sendEventBus(ArrayList<RepositoryBillBean> mBillBeans) {
        if (mEventBusBean == null) {
            mEventBusBean = new EventBusBean();
        }
        mEventBusBean.setActivityName("QualityGuaranteePeriodActivity");
        mEventBusBean.setRepositoryBillBeans(mBillBeans);
        mMEventBus.post(mEventBusBean);

    }

    //显示不同的对话框
    @Override
    public void selectTime(int type, TextView textView, int position) {
        textViewAction = textView;
        selectPosition = position;
        showDialogTipAddTime();
        if (type == 0) {
            //保质期限
            shoWDialogText("保质期限", "请填写保质期限", mBillBeans.get(position).productPiHao);
            tv_product_name.setText(mBillBeans.get(position).productName);
            tv_product_num.setText(mBillBeans.get(position).productNum);
            tv_product_unit.setText(mBillBeans.get(position).productNnit);
            mTv_select_time.setVisibility(View.VISIBLE);
            mEt_select_time.setVisibility(View.GONE);
        } else if (type == 1) {
            //生产批号
            shoWDialogText("生产批号", "请填写生产批号", mBillBeans.get(position).productTime);
            tv_product_name.setText(mBillBeans.get(position).productName);
            tv_product_num.setText(mBillBeans.get(position).productNum);
            tv_product_unit.setText(mBillBeans.get(position).productNnit);
            mTv_select_time.setVisibility(View.GONE);
            mEt_select_time.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void selectOtherTime(int type, TextView textView, int position) {

    }

    private void shoWDialogText(String actionMessage, String selectTime, String shengchanPihao) {
        mTv_action_message.setText(actionMessage);
        mTv_shengchan_pihao.setText(shengchanPihao);
    }

    @Override
    public void complete() {
        String time = datePickerPopupUtil.birthday;
        if (time != null) {
            mTv_select_time.setText(MyUtils.getDate(time, "yyyy年MM月dd日", "yyyy-MM-dd"));
            mTv_select_time.setTextColor(Color.BLACK);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMEventBus.unregister(this);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
