package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;




/**
 * Created by sld on 2017/4/27.
 */

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;


    private EditText et_old_password;
    private EditText et_one_new_password;
    private EditText et_two_new_password;
    private ImageView iv_clear;
    private Button btn;
    SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        et_old_password = (EditText) findViewById(R.id.et_old_password);
        et_one_new_password = (EditText) findViewById(R.id.et_one_new_password);
        et_two_new_password = (EditText) findViewById(R.id.et_two_new_password);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                String password1 = et_old_password.getText().toString().trim();
                String password2 = et_one_new_password.getText().toString().trim();
                String password3 = et_two_new_password.getText().toString().trim();
                if (TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2) || TextUtils.isEmpty(password3)) {

                    Toast.makeText(this, "请数据填写完整", Toast.LENGTH_SHORT).show();
                } else if (!password2.equals(password3)) {
                    Toast.makeText(this, "两次填写密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/update/password", RequestMethod.POST);
                    stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                    stringRequest.add("_method","patch");
                    stringRequest.add("password",et_old_password.getText().toString().trim());
                    stringRequest.add("new_password",et_one_new_password.getText().toString().trim());
                    stringRequest.add("confirm_password",et_two_new_password.getText().toString().trim());
                    CallServer.getInstance().add(13123, stringRequest, new OnResponseListener() {
                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject((String) response.get());
                                String code = jsonObject.getString("code");
                                String msg = jsonObject.getString("msg");
                                if ("200".equals(code)) {
                                    ShowToast.showToast("修改成功");
                                    finish();

                                }else {


                            }
                            ShowToast.showToast(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailed(int what, Response response) {
                        }

                        @Override
                        public void onFinish(int what) {

                        }
                    });
                }


                break;
            case R.id.iv_clear:
                et_old_password.setText("");
                break;


        }
    }


}
