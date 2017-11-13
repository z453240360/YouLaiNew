package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
 * 转换比例
 * Created by youj on 2016/1/25.
 */
public class ConversionRatioActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_dq_cg_yl)
    TextView tvDqCgYl;
    @Bind(R.id.tv_dq_jy_yl)
    TextView tvDqJyYl;
    @Bind(R.id.tv_dq_zh_bl)
    TextView tvDqZhBl;
    @Bind(R.id.tv_zcg_yl)
    TextView tvZcgYl;
    @Bind(R.id.tv_zjyl)
    TextView tvZjyl;
    @Bind(R.id.tv_ls_zh_bl)
    TextView tvLsZhBl;
    @Bind(R.id.ll_have_data)
    LinearLayout llHaveData;
    @Bind(R.id.tv_cookingOilDeclare)
    Button tvCookingOilDeclare;
    @Bind(R.id.tv_efuseOilDeclare)
    Button tvEfuseOilDeclare;
    @Bind(R.id.ll_no_data)
    LinearLayout llNoData;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_conversion_ratio);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        title.setText(getString(R.string.conversion_ratio));
        ivLeft.setOnClickListener(this);
        tvCookingOilDeclare.setOnClickListener(this);
        tvEfuseOilDeclare.setOnClickListener(this);
        getdata();
    }

    private void getdata() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("_id", MyApplication.getInstance().getUser().id);
        InternetConfig config = new InternetConfig();
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetRestaurantDetail", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());
            if (jsonObject.optInt("status") == 0) {
                JSONObject object = jsonObject.optJSONArray("data").optJSONObject(0);
                String zxcg = object.optString("zxcg"); //当前采购
                String cgzl = object.optString("cgzl");//采购总量
                String zxjy = object.optString("zxjy");//当前交油量
                String jyzl = object.optString("jyzl");//交油总量
                String dqzhbl = object.optString("dqzhbl");//当前转换比例
                String lszhbl = object.optString("lszhbl");//历史转换比例
                if (!("0".equals(zxcg) && "0".equals(zxjy))) {
                    //如果当前采购和当前交油都为空 则显示无数据的图片 否者显示转换的信息
                    llNoData.setVisibility(View.GONE);
                    llHaveData.setVisibility(View.VISIBLE);

                    tvDqCgYl.setText(zxcg); //当前采购油量
                    tvDqJyYl.setText(zxjy); //当前交油油量
                    tvZcgYl.setText(cgzl); //采购总量
                    tvZjyl.setText(jyzl);//交油总量
                    tvDqZhBl.setText(dqzhbl.replace("%",""));//当前转换比例
                    tvLsZhBl.setText(lszhbl); //历史转换比例
                } else {
                    llNoData.setVisibility(View.VISIBLE);
                    llHaveData.setVisibility(View.GONE);
                }
            } else {
                llNoData.setVisibility(View.VISIBLE);
                String msg = jsonObject.optString("msg");
                MyToastUtils.show(this,msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.tv_cookingOilDeclare: //食用油申报
                startActivity(new Intent(this, CookingOilDeclareActivity.class));
                finish();
                break;

            case R.id.tv_efuseOilDeclare: //废弃油申报
                startActivity(new Intent(this, RefuseOilDeclareActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        llNoData.setVisibility(View.VISIBLE);
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }
}
