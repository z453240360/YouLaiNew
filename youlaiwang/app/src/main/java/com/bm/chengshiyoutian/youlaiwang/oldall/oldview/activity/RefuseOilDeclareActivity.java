package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
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
 * 地沟油申报
 * Created by jayen on 16/1/13.
 */
public class RefuseOilDeclareActivity extends Activity implements View.OnClickListener {

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
    @Bind(R.id.et_litre)
    EditText etLitre;
    @Bind(R.id.et_junk_num)
    EditText etJunkNum;
    @Bind(R.id.bt_submit)
    Button btSubmit;
    @Bind(R.id.tv_type)
    TextView tvType;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_refuse_oil_declare);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        MyUtils.setPricePoint(etLitre);
        title.setText("地沟油申报");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.edit);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        dialog = new ProgressDialog(this);
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
                startActivity(new Intent(this, DeclareRecordActivity.class));
                break;

            case R.id.bt_submit: //点击申报
                submit();
                break;

            default:
                break;
        }
    }

    private void submit() {
        String litreNum = etLitre.getText().toString().trim(); //采购数量
        String junkNum = etJunkNum.getText().toString().trim(); //垃圾数量
        if (TextUtils.isEmpty(litreNum) || TextUtils.isEmpty(junkNum)) {
            tvType.setText("数据不能为空！");
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("ShopId", MyApplication.getInstance().getUser().id);
        params.put("Record", litreNum);
        params.put("Cclj", junkNum);
        config.setTimeout(Constants.TIMEOUT);
        config.setKey(0);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "Declare", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.optInt("status")) {
                        tvType.setText("申报成功！");
                        getScore();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(RefuseOilDeclareActivity.this, XiangQingActivity.class));
                                finish();
                            }

                        }).start();
                    } else {
                        tvType.setText("申报失败！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (jsonObject.getInt("status") == 0) {
                        JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").getJSONObject(0);
                        String score = object.optString("score");
                        MyApplication app = MyApplication.getInstance();
                        RestaurantBean user = app.getUser();
                        user.integral = score;
                        app.setUser(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 获取积分
     */
    private void getScore() {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("_id", MyApplication.getInstance().getUser().id);
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetRestaurantScore", params, config, this);
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
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
