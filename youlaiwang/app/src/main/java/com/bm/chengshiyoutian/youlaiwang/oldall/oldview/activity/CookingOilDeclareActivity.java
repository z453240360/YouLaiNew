package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 食用油采购
 * Created by jayen on 16/1/12.
 */
public class CookingOilDeclareActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_restaurant_name)
    TextView tvRestaurantName;
    @Bind(R.id.tv_restaurant_address)
    TextView tvRestaurantAddress;
    /**
     * 采购数量
     */
    @Bind(R.id.et_litre_num)
    EditText etLitreNum;
    /**
     * 提交
     */
    @Bind(R.id.bt_submit)
    Button btSubmit;
    /**
     * 提示
     */
    @Bind(R.id.tv_hint)
    TextView tvHint;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cooking_oil_declare);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        MyUtils.setPricePoint(etLitreNum);
        dialog = new ProgressDialog(this);
        title.setText("食用油采购");
        ivRight.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.edit);
        btSubmit.setOnClickListener(this);
        setData();
    }

    /**
     * 给控件设置数据
     */
    private void setData() {
        RestaurantBean bean = MyApplication.getInstance().getUser();
        tvRestaurantName.setText(bean.restaurantName);
        tvRestaurantAddress.setText(bean.address);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.iv_right:
                startActivity(new Intent(this, ProcurementRecordActivity.class));
                break;

            case R.id.bt_submit:
                addOil();
                break;

            default:
                break;
        }
    }

    private void addOil() {
        String addOilNum = etLitreNum.getText().toString().trim();
        if (TextUtils.isEmpty(addOilNum)) {
            addOilNum = "0";
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
        LinkedHashMap<String,String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("ShopId", MyApplication.getInstance().getUser().id);
        params.put("Record", addOilNum);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL,"AddOilInfo",params,config,this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());
            if (0 == jsonObject.optInt("status")) {
                tvHint.setText("记录成功!");
                new Thread(new Runnable(){

                    public void run(){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(CookingOilDeclareActivity.this,ProcurementRecordActivity.class));
                    }

                }).start();

            } else {
                tvHint.setText("提交失败!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        MyToastUtils.show(this,getString(R.string.intnet_err));
    }
}
