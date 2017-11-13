package com.bm.chengshiyoutian.youlaiwang.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.XiuGaiMingZiBean;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;




/**
 * Created by sld on 2017/4/27.
 */

public class ChangeUserNameActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tb_toolbar;
    private EditText et_new_name;
    private ImageView iv_clear;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_name);
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

    private void initView() {

        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        et_new_name = (EditText) findViewById(R.id.et_new_name);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);

        iv_clear.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (TextUtils.isEmpty(et_new_name.getText().toString().trim())) {
                    Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                } else {

                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/update", RequestMethod.POST);
                    String token = getIntent().getStringExtra("token");
                    stringRequest.addHeader("Authorization", "Bearer " + token);
                    stringRequest.add("_method", "patch");
                    stringRequest.add("user_nicename", et_new_name.getText().toString());
                    CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response response) {
                            try {
                                int code = GsonUtils.getInstance().fromJson((String) response.get(), XiuGaiMingZiBean.class).getCode();
                                if (code == 200) {
                                    ShowToast.showToast("修改成功");
                                    finish();

                                } else {
                                    ShowToast.showToast("修改失败");

                                }

                            } catch (JsonSyntaxException e) {
                                ShowToast.showToast("数据异常");
                            }

                        }

                        @Override
                        public void onFailed(int what, Response response) {
                            ShowToast.showToast("联网失败");
                        }

                        @Override
                        public void onFinish(int what) {

                        }
                    });

                }
                break;
            case R.id.iv_clear:
                et_new_name.setText("");
                break;

        }
    }
}
