package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pc.ioc.event.EventBus;
import com.android.pc.ioc.inject.InjectBefore;
import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.RepositoryBillAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.EventBusBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.BaseInterface;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GlobalData;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyListView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/5/25 14:58
 * @Description 仓库台账入库
 */
public class RepositoryBillRuKuActivity extends Activity implements BaseInterface, View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right_two)
    TextView tv_right_two;

    @Bind(R.id.lv_mingxi_data)
    MyListView lv_mingxi_data;
    @Bind(R.id.tv_add_data)
    TextView tv_add_data;

    @Bind(R.id.ll_baozhiqixian)
    LinearLayout ll_baozhiqixian;
    @Bind(R.id.bt_submit)
    Button bt_submit;

    private ArrayList<RepositoryBillBean> mRepositoryBillBeans;
    private RepositoryBillAdapter mRepositoryBillAdapter;
    private Intent mIntent;

    private String selectType;

    private EventBus mMEventBus;
    private ArrayList<RepositoryBillBean> mRepositoryBillBeansOfTime;//选择保质期限后的集合
    private ArrayList<RepositoryBillBean> mProductMingXi;

    private ProgressDialog progressDialog;

    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_repository_bill_ru_ku);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        init();
        mMEventBus = EventBus.getDefault();//注册eventbus
        mMEventBus.register(this);
    }

    @Override
    public void init() {
        progressDialog = new ProgressDialog(this);
        getIntentData();
        //获取保存的入库信息
        mProductMingXi = MyUtils.getSaveData(getApplicationContext(), "productMingXi");
        initView();
        initData();
        setListener();
    }

    @Override
    public void initView() {
        title.setText(getString(R.string.production_one));
        tv_right_two.setVisibility(ImageView.VISIBLE);
        tv_right_two.setText("提交");
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectType = intent.getStringExtra("selectType");
        }
    }

    @Override
    public void initData() {
        mRepositoryBillAdapter = new RepositoryBillAdapter(RepositoryBillRuKuActivity.this);
        lv_mingxi_data.setAdapter(mRepositoryBillAdapter);
        mRepositoryBillBeans = new ArrayList<>();
        if (mProductMingXi == null || mProductMingXi.size() == 0) {
            for (int i = 0; i < 3; i++) {
                mRepositoryBillBeans.add(new RepositoryBillBean());
            }
        } else {
            mRepositoryBillBeans.addAll(mProductMingXi);
            for (int i = mProductMingXi.size(); i < 3; i++) {
                mRepositoryBillBeans.add(new RepositoryBillBean());
            }
        }
        mRepositoryBillAdapter.setData(mRepositoryBillBeans);
        mRepositoryBillAdapter.notifyDataSetChanged();
    }

    @Override
    public void setListener() {
        ivLeft.setOnClickListener(this);
        tv_add_data.setOnClickListener(this);
        ll_baozhiqixian.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        tv_right_two.setOnClickListener(this);
    }

    /**
     * 入库 提交
     */
    private void addInstockRecord(String typeid, String bill, String jsondetails) {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("typeid", typeid);
        params.put("restaurantid", MyApplication.getInstance().getUser().id);
        params.put("bill", bill);
        params.put("jsondetails", jsondetails);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "AddInstockRecord", params, config, this);
    }

    @InjectHttpOk
    @Override
    public void ok(ResponseEntity entity) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        String contentAsString = entity.getContentAsString();
        int key = entity.getKey();
        switch (key) {
            case 0://提交
                try {
                    JSONObject jsonObject = new JSONObject(contentAsString);
                    String msg = jsonObject.optString("msg");
                    String status = jsonObject.optString("status");
                    MyToastUtils.show(getApplicationContext(), msg);
                    if ("0".equals(status)) {
                        //MyToastUtils.show(getApplicationContext(), getString(R.string.ku_chun_updata_success));
                        Intent intent = new Intent(getApplicationContext(), SuoZhengSuoPiaoRecordActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        MyUtils.setTextViewClick(true, tv_right_two, bt_submit);
                        MyToastUtils.show(getApplicationContext(), msg);
                    }
                } catch (JSONException e) {
                    MyUtils.setTextViewClick(true, tv_right_two, bt_submit);
                    e.printStackTrace();
                }
                break;
        }
    }

    @InjectHttpErr
    @Override
    public void err(ResponseEntity entity) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        MyUtils.setTextViewClick(true, tv_right_two, bt_submit);
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_data:
                mRepositoryBillBeans.add(new RepositoryBillBean());
                mRepositoryBillAdapter.setData(mRepositoryBillBeans);
                mRepositoryBillAdapter.notifyDataSetChanged();
                lv_mingxi_data.setSelection(mRepositoryBillBeans.size() - 1);
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_baozhiqixian://保质期限
                submitOrSelectBaoZhiQiXian(0);
                break;
            case R.id.tv_right_two:
            case R.id.bt_submit://提交
                MyUtils.setTextViewClick(false, tv_right_two, bt_submit);
                submitOrSelectBaoZhiQiXian(1);
                break;
        }
    }

    /**
     * 选择保质期限或 提交
     *
     * @param type 0 选择保质期限 ；1 提交
     */
    private void submitOrSelectBaoZhiQiXian(int type) {
        ArrayList<RepositoryBillBean> billBeans = mRepositoryBillAdapter.getData();
        if (billBeans != null) {
            ArrayList<RepositoryBillBean> repositoryBillBeans = MyUtils.selectHasNum(billBeans);
            if (repositoryBillBeans != null && repositoryBillBeans.size() > 0) {
                ArrayList<RepositoryBillBean> billBeanOfTime = MyUtils.getBillBeanOfTime(repositoryBillBeans, mRepositoryBillBeansOfTime);
                if (type == 0) {
                    mIntent = new Intent(getApplicationContext(), QualityGuaranteePeriodActivity.class);
                    mIntent.putParcelableArrayListExtra("billBeans", billBeanOfTime);
                    startActivity(mIntent);
                } else if (type == 1) {
                    Gson gson = new Gson();
                    String toJson = gson.toJson(billBeanOfTime);
                    GlobalData.cacheData(getApplicationContext(), "productMingXi", toJson);
                    addInstockRecord(selectType, "", toJson);
                }
            } else {
                MyToastUtils.show(getApplicationContext(), getString(R.string.wanshan_product_ming_xi));
            }
        }
    }

    public void onEventMainThread(EventBusBean mEventBusBean) {
        if (mEventBusBean == null) {
            return;
        }
        if ("QualityGuaranteePeriodActivity".equals(mEventBusBean.getActivityName())) {
            mRepositoryBillBeansOfTime = mEventBusBean.getRepositoryBillBeans();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMEventBus != null) {
            mMEventBus.unregister(this);
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MyUtils.setTextViewClick(true, tv_right_two, bt_submit);
        }
        return super.onKeyDown(keyCode, event);
    }
}
