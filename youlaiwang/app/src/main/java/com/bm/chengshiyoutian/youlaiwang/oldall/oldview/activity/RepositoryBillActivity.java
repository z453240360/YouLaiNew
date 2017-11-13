package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/5/25 14:34
 * @Description 仓库台账
 */
public class RepositoryBillActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ll_production_ru_ku)
    LinearLayout ll_production_ru_ku;
    @Bind(R.id.ll_production_sell)
    LinearLayout ll_production_sell;
    @Bind(R.id.ll_product_save)
    LinearLayout ll_product_save;

    private String selectType;
    private Intent mIntent;
    private String mRecordCount;//当前库存条目

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_repository_bill);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStorageInfo(selectType);
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
            selectType = intent.getStringExtra("selectType");
        }
    }

    private void initView() {
        title.setText("仓库台账");
    }

    private void initData() {

    }

    private void setListener() {
        ivLeft.setOnClickListener(this);
        ll_production_ru_ku.setOnClickListener(this);
        ll_production_sell.setOnClickListener(this);
        ll_product_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_production_ru_ku://生产商品入库
                mIntent = new Intent(getApplicationContext(), RepositoryBillRuKuActivity.class);
                mIntent.putExtra("selectType", selectType);
                startActivity(mIntent);
                break;
            case R.id.ll_production_sell://生产商品出库（销售）
                if (!TextUtils.isEmpty(mRecordCount) && !"0".equals(mRecordCount)) {
                    mIntent = new Intent(getApplicationContext(), RepositoryBillSellActivity.class);
                    mIntent.putExtra("selectType", selectType);
                    startActivity(mIntent);
                } else {
                    MyToastUtils.show(getApplicationContext(), getString(R.string.add_ku_chun));
                }
                break;
            case R.id.ll_product_save://生产商品库存
                if (!TextUtils.isEmpty(mRecordCount) && !"0".equals(mRecordCount)) {
                    mIntent = new Intent(getApplicationContext(), RepositoryBillSaveActivity.class);
                    mIntent.putExtra("selectType", selectType);
                    startActivity(mIntent);
                    break;
                } else {
                    MyToastUtils.show(getApplicationContext(), getString(R.string.add_ku_chun));
                }
        }
    }

    /**
     * 获取库存的信息
     */
    private void getStorageInfo(String typeid) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("typeid", typeid);
        params.put("restaurantid", MyApplication.getInstance().getUser().id);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetStorageInfo", params, config, this);
    }

    @InjectHttpOk
    public void ok(ResponseEntity entity) {
        String contentAsString = entity.getContentAsString();
        int key = entity.getKey();
        switch (key) {
            case 0://获取库存信息
                getKuChunData(contentAsString);
                break;
        }
    }

    @InjectHttpErr
    public void err(ResponseEntity entity) {
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    //解析库存信息
    private void getKuChunData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            String status = jsonObject.optString("status");
            if ("0".equals(status)) {
                mRecordCount = jsonObject.optString("recordCount");
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
}
