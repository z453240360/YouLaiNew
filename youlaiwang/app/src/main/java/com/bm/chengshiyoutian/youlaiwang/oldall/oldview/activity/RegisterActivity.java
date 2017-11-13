package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 注册
 * Created by jayen on 16/1/12.
 */
public class RegisterActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.et_id)
    EditText etId;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.bt_getCode)
    Button btGetCode;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.et_psw_ensure)
    EditText etPswEnsure;
    @Bind(R.id.bt_register_next)
    Button btRegisterNext;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.cb_agree_agreement)
    CheckBox cbAgreeAgreement;
    @Bind(R.id.tv_verify_phone)
    TextView tvVerifyPhone;
    @Bind(R.id.ll_agree_agreement)
    LinearLayout llAgreeAgreement;
    @Bind(R.id.tv_verify_psw)
    TextView tvVerifyPsw;
    @Bind(R.id.tv_verify_psw_again)
    TextView tvVerifyPswAgain;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_agreement)
    TextView tvAgreement;
    private MyAsyncTask myAsyncTask;
    private String psw;
    private ProgressDialog progressDialog;
    private String phoneNum;
    /**
     * 后台发送的验证码
     */
    private String sendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("注册");
        cbAgreeAgreement.setPadding(10, 0, 0, 0);
        tvAgreement.setText("《" + getString(R.string.app_name) + "注册协议》");
        ivLeft.setOnClickListener(this);
        btGetCode.setOnClickListener(this);
        btRegisterNext.setOnClickListener(this);
        cbAgreeAgreement.setOnCheckedChangeListener(this);
        tvAgreement.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.bt_getCode: //点击发送验证码
                verifyPhone();
                break;

            case R.id.bt_register_next:
                registerNext();
                break;

            case R.id.tv_agreement:
                Intent intent = new Intent(this, HelpActivity.class);
                intent.putExtra(Constants.TYPE, Constants.REGISTRATION_PROTOCOL);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 获取手机号码是否注册过
     */
    private void verifyPhone() {
        phoneNum = etId.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() < 11) {
            tvVerifyPhone.setVisibility(View.VISIBLE);
            tvVerifyPhone.setText("请输入正确的手机号码");
            return;
        } else {
            tvVerifyPhone.setVisibility(View.INVISIBLE);
        }

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        //先从后台判断该手机号是否已经注册过
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("phone", phoneNum);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "ExistsTel", params, config, this);
    }


    private void sendCode() {
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("mobile", phoneNum);
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "send_verify_sms_code", params, config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0: // 验证手机号
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        //该手机号码没有注册 就发送验证码
                        sendCode();
                    } else {
                        tvVerifyPhone.setVisibility(View.VISIBLE);
                        tvVerifyPhone.setText(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (jsonObject.optInt("status") == 0) {
                        sendCode = jsonObject.optString("data");
                    } else {
                        myAsyncTask.isStop = true;
                    }
                    MyToastUtils.show(this, jsonObject.optString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }

    }


    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        myAsyncTask.isStop = true;
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }


    /**
     * 点击下一步
     */
    private void registerNext() {
        String phoneNum = etId.getText().toString().trim();
        psw = etPsw.getText().toString().trim();
        String pswEnsure = etPswEnsure.getText().toString().trim();
        String code = etCode.getText().toString().trim();

        if (TextUtils.isEmpty(code)) {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText("请输入验证码");
            return;
        } else {
            tvStatus.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(psw) || psw.length() < 6 || psw.length() > 16) { //密码格式不正确
            tvVerifyPsw.setVisibility(View.VISIBLE);
            return;
        } else {
            tvVerifyPsw.setVisibility(View.INVISIBLE);
        }

        if (!psw.equals(pswEnsure)) { //两次密码不一致
            tvVerifyPswAgain.setVisibility(View.VISIBLE);
            return;
        } else {
            tvVerifyPswAgain.setVisibility(View.INVISIBLE);
        }

        if (!code.equals(sendCode)) {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText("验证码不正确");
            return;
        } else {
            tvStatus.setVisibility(View.INVISIBLE);
        }
        Intent intent = new Intent(this, RegisterNextActivity.class);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("psw", psw);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            btRegisterNext.setEnabled(false);
            btRegisterNext.setBackgroundDrawable(getResources().getDrawable(R.mipmap.button_unonclic));
        } else {
            btRegisterNext.setEnabled(true);
            btRegisterNext.setBackgroundDrawable(getResources().getDrawable(R.mipmap.button));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (myAsyncTask != null) {
            myAsyncTask = null;
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

    private class MyAsyncTask extends AsyncTask<Void, String, Void> {

        boolean isStop;

        @Override
        protected void onPreExecute() {
            btGetCode.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 59; i >= 0; i--) {
                try {
                    if (isStop)
                        break;
                    String str = String.format("%1$s秒", "" + i);
                    publishProgress(str);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            btGetCode.setText(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
//            getCode = "";
            btGetCode.setEnabled(true);
            btGetCode.setText("获取验证码");
        }
    }
}
