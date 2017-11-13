package com.bm.chengshiyoutian.youlaiwang.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.YanZhengMaBean;
import com.bm.chengshiyoutian.youlaiwang.bean.ZhaoHui_Bean;
import com.bm.chengshiyoutian.youlaiwang.bean.ZhuCeBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by sld on 2017/5/10.
 */

public class ZhaoHuiMIMaActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;
    private EditText et1;
    private EditText et2;
    private Button btn2;
    private EditText et3;
    private EditText et4;
    private Button btn1;
    private TimeCount time;
    private YanZhengMaBean yanZhengMaBean;
    private LinearLayout ll_zhaohui;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhaohuimima);
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //设置点击空白处，自动隐藏键盘
        ll_zhaohui.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        et1 = (EditText) findViewById(R.id.et1);
        et1.setOnClickListener(this);
        et2 = (EditText) findViewById(R.id.et2);
        et2.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        et3 = (EditText) findViewById(R.id.et3);
        et3.setOnClickListener(this);
        et4 = (EditText) findViewById(R.id.et4);
        et4.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        ll_zhaohui = (LinearLayout) findViewById(R.id.zhaohui);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn2:
                time = new TimeCount(60000, 1000);
                time.start();// 开始计时
                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/sms/findPwd", RequestMethod.POST);
                stringRequest.add("mobile", et1.getText().toString());
                CallServer.getInstance().add(1, stringRequest, new OnResponseListener<String>() {
                    //15655451139
                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        try {
                            yanZhengMaBean = GsonUtils.getInstance().fromJson(response.get(), YanZhengMaBean.class);
                            ShowToast.showToast(yanZhengMaBean.getMsg());

                        } catch (JsonSyntaxException e) {
                            Toast.makeText(ZhaoHuiMIMaActivity.this, "验证码获取失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {
                        Toast.makeText(ZhaoHuiMIMaActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish(int what) {

                    }
                });
                break;
            case R.id.btn1:
                if (submit()) {
                    Request<String> stringRequest1 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/findPwd", RequestMethod.POST);
                    stringRequest1.add("mobile", et1.getText().toString().trim());
                    stringRequest1.add("code", et2.getText().toString().trim());
                    stringRequest1.add("password", et3.getText().toString().trim());
                    stringRequest1.add("token", yanZhengMaBean.getData().getToken());
                    CallServer.getInstance().add(1, stringRequest1, new OnResponseListener<String>() {

                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response<String> response) {
                            Log.e("jpush", response.get());
                            Gson gson = new Gson();
                            ZhaoHui_Bean zhuCeBean = gson.fromJson(response.get(), ZhaoHui_Bean.class);
//                            ZhuCeBean zhuCeBean = Gsonutils.getInstance().fromJson(response.get(), ZhuCeBean.class);
                            ShowToast.showToast(zhuCeBean.getMsg());
                            finish();
                        }

                        @Override
                        public void onFailed(int what, Response<String> response) {
                            Toast.makeText(ZhaoHuiMIMaActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinish(int what) {

                        }
                    });

                } else {
                    Toast.makeText(this, "请资料填写完整", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    private boolean submit() {
        // validate
        String et1String = et1.getText().toString().trim();
        if (TextUtils.isEmpty(et1String)) {

            return false;
        }

        String et2String = et2.getText().toString().trim();
        if (TextUtils.isEmpty(et2String)) {

            return false;
        }

        String et3String = et3.getText().toString().trim();
        if (TextUtils.isEmpty(et3String)) {

            return false;
        }

        String et4String = et4.getText().toString().trim();
        if (TextUtils.isEmpty(et4String)) {

            return false;
        }

        // TODO validate success, do something

        return true;
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btn2.setText("获取验证码");
            btn2.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btn2.setClickable(false);//防止重复点击
            btn2.setText(millisUntilFinished / 1000 + "s");
        }
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
}
