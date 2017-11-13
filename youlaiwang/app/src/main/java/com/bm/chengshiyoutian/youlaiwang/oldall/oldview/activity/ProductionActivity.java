package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/5/20 18:14
 * @Description 生产 二级界面
 */
public class ProductionActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.tv_provider)
    TextView tv_provider;
    @Bind(R.id.tv_provider_tip)
    TextView tv_provider_tip;
    @Bind(R.id.tv_account)
    TextView tv_account;
    @Bind(R.id.tv_account_tip)
    TextView tv_account_tip;
    @Bind(R.id.tv_no_hege)
    TextView tv_no_hege;
    @Bind(R.id.tv_no_hege_tip)
    TextView tv_no_hege_tip;

    @Bind(R.id.ll_production)
    LinearLayout ll_production;
    @Bind(R.id.ll_storage)
    LinearLayout ll_storage;
    @Bind(R.id.ll_disqualification)
    LinearLayout ll_disqualification;

    private String selectType;//标记选择的是生产 流通  餐饮;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_production);
        ButterKnife.bind(this);
        setListener();
        getIntentData();
        init();
    }

    private void init() {
        showDataBySelectType();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectType = intent.getStringExtra("selectType");
        }
    }

    private void showDataBySelectType() {
        if ("1".equals(selectType)) {
            //生产
            title.setText("生产");
            tv_no_hege.setText(getString(R.string.pasttimeproduct));
        } else if ("2".equals(selectType)) {
            //流通
            title.setText("流通");
            tv_provider.setText(getString(R.string.gongyingshang_two));
            tv_provider_tip.setText("提示:供应商品流入票证信息");
            tv_account.setText(getString(R.string.repositorybillsell_two));
            tv_account_tip.setText("提示:商品流出票证信息");
            tv_no_hege.setText(getString(R.string.pasttimeproduct));
            tv_no_hege_tip.setText("提示:检验不合格/临期产品处理信息");
        }
    }

    private void setListener() {
        ivLeft.setOnClickListener(this);
        ll_production.setOnClickListener(this);
        ll_storage.setOnClickListener(this);
        ll_disqualification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_production:
                //供应商
//                mIntent = new Intent(getApplicationContext(), DemandingCertificatesThreeActivity.class);
                mIntent = new Intent(getApplicationContext(), SupplierOrLedgerActivity.class);
//                mIntent.putExtra("selectType", selectType);
                startActivity(mIntent);
                break;
            case R.id.ll_storage:
                if ("1".equals(selectType)) {
                    //仓库台账
                    mIntent = new Intent(getApplicationContext(), RepositoryBillActivity.class);
                    mIntent.putExtra("selectType", selectType);
                    startActivity(mIntent);
                } else if ("2".equals(selectType)) {
                    //采购商户

                    mIntent = new Intent(getApplicationContext(), RepositoryBillShipmentActivity.class);
                    mIntent.putExtra("selectType", selectType);
                    startActivity(mIntent);
                }
                break;
            case R.id.ll_disqualification:
                //不合格&临期产品
                mIntent = new Intent(getApplicationContext(), PastTimeProductActivity.class);
                mIntent.putExtra("selectType", selectType);
                startActivity(mIntent);
                break;
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
