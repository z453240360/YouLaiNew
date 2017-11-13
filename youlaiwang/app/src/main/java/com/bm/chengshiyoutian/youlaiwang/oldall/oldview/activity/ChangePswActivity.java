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

import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * 修改密码
 * Created by youj on 2016/1/9.
 */
public class ChangePswActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.et_old_psw)
    EditText etOldPsw;
    @Bind(R.id.ed_psw)
    EditText edPsw;
    @Bind(R.id.ed_ensure_psw)
    EditText edEnsurePsw;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.bt_ensure)
    Button btEnsure;
    @Bind(R.id.tv_v_psw)
    TextView tvVPsw;
    @Bind(R.id.tv_v_npsw)
    TextView tvVNpsw;
    @Bind(R.id.tv_v_nspsw)
    TextView tvVNspsw;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_change_psw);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        title.setText(getString(R.string.change_psw));
        ivLeft.setOnClickListener(this);
        btEnsure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.bt_ensure:
                changePsw();
                break;
            default:
                break;
        }
    }

    /**
     * 点击确定 修改密码
     */
    private void changePsw() {
        String oldPsw = etOldPsw.getText().toString().trim();//老密码
        String newPsw = edPsw.getText().toString().trim();
        String ensurePsw = edEnsurePsw.getText().toString().trim();
        if (TextUtils.isEmpty(oldPsw) || oldPsw.length() < 6 || oldPsw.length() > 16) {
            tvVPsw.setVisibility(View.VISIBLE);
            return;
        } else {
            tvVPsw.setVisibility(View.INVISIBLE);
        }
        if (TextUtils.isEmpty(newPsw) || newPsw.length() < 6 || newPsw.length() > 16) {
            tvVNpsw.setVisibility(View.VISIBLE);
            return;
        } else {
            tvVNpsw.setVisibility(View.INVISIBLE);
        }
        if (!newPsw.equals(ensurePsw)) {
            tvVNspsw.setVisibility(View.VISIBLE);
            return;
        } else {
            tvVNspsw.setVisibility(View.INVISIBLE);
        }

        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("RestID", MyApplication.getInstance().getUser().id);
        params.put("oldpwd", oldPsw);
        params.put("newPwd", newPsw);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "UpdatePwd", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    String msg = jsonObject.optString("msg");
                    if (jsonObject.optInt("status") == 0) {
                        tvState.setVisibility(View.VISIBLE);
                        tvState.setText(msg);
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(ChangePswActivity.this, LoginActivtiy.class));
                                finish();
                            }
                        }).start();

                    } else {
                        tvState.setVisibility(View.VISIBLE);
                        tvState.setText(msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
