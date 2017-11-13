package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.BillDataAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.BaseInterface;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
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
 *         create at 2016/5/25 17:21
 * @Description 仓库台账  仓库库存
 */
public class RepositoryBillSaveActivity extends AppCompatActivity implements BaseInterface, View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.lv_save_message)
    ListView lv_save_message;
    @Bind(R.id.bt_submit)
    Button bt_submit;

    private String selectType;

    private ProgressDialog progressDialog;
    private BillDataAdapter mBillDataAdapter;
    private ArrayList<RepositoryBillBean> repositoryBillBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_repository_bill_save);
        ButterKnife.bind(this);
        init();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectType = intent.getStringExtra("selectType");
        }
    }

    @Override
    public void init() {
        progressDialog = new ProgressDialog(this);
        getIntentData();
        initView();
        initData();
        setListener();
    }

    @Override
    public void initView() {
        title.setText("仓库库存");
    }

    @Override
    public void initData() {
        mBillDataAdapter = new BillDataAdapter(RepositoryBillSaveActivity.this);
        lv_save_message.setAdapter(mBillDataAdapter);
        getStorageInfo(selectType);
    }

    @Override
    public void setListener() {
        ivLeft.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    /**
     * 获取库存的信息
     */
    private void getStorageInfo(String typeid) {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("typeid", typeid);
        params.put("restaurantid", MyApplication.getInstance().getUser().id);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetStorageInfo", params, config, this);
    }

    /**
     * 更改库存的信息
     */
    private void updateStorageInfo(String typeid, String jsondetails) {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("typeid", typeid);
        params.put("restaurantid", MyApplication.getInstance().getUser().id);
        params.put("jsondetails", jsondetails);
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "UpdateStorageInfo", params, config, this);
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
            case 0://获取库存信息
                getKuChunData(contentAsString);
                break;
            case 1://更新库存信息
                MyUtils.setTextViewClick(true, bt_submit);
                updataKUChunData(contentAsString);
                break;
        }
    }

    @InjectHttpErr
    @Override
    public void err(ResponseEntity entity) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        MyUtils.setTextViewClick(true, bt_submit);
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    //解析库存信息
    private void getKuChunData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            String status = jsonObject.optString("status");
            if ("0".equals(status)) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                if (jsonArray != null && jsonArray.length() > 0) {
                    repositoryBillBeans = new ArrayList<>();
                    int length = jsonArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        repositoryBillBeans.add(new RepositoryBillBean(object.optString("GoodsName"), object.optString("Count"), object.optString("Unit")));
                    }
                    mBillDataAdapter.setData(repositoryBillBeans);
                    mBillDataAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updataKUChunData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            String msg = jsonObject.optString("msg");
            MyToastUtils.show(getApplicationContext(), msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.bt_submit://提交
                MyUtils.setTextViewClick(false, bt_submit);
                sumbit();
                break;
        }
    }

    private void sumbit() {
        ArrayList<RepositoryBillBean> mBillDataAdapterData = mBillDataAdapter.getData();
        if (mBillDataAdapterData != null && mBillDataAdapterData.size() > 0) {
            Gson gson = new Gson();
            String toJson = gson.toJson(mBillDataAdapterData);
            updateStorageInfo(selectType, toJson);
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
            MyUtils.setTextViewClick(true, bt_submit);
        }
        return super.onKeyDown(keyCode, event);
    }
}
