package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.alipaydemo.PayResult;
import com.bm.chengshiyoutian.youlaiwang.bean.Bean11;
import com.bm.chengshiyoutian.youlaiwang.bean.ZhiFuBaoBean;
import com.jaeger.library.StatusBarUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class ZhiFuFangShiActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar tb_toolbar;
    private TextView tv_jine;
    private LinearLayout ll_zhifubao,ll_weixinpay;
    private String orderSns ="";
    private String money = "";
    private String orderId = "";
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_fu_fang_shi);
        initView();

        StatusBarUtil.setColorForSwipeBack(this, Color.parseColor("#ffa800"), 0);

        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent mIntent = getIntent();
        orderId = mIntent.getStringExtra("orderId");

//        orderSns = mIntent.getStringExtra("orderSns");
        money = mIntent.getStringExtra("money");
        tv_jine.setText("¥" + money);
    }

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        tv_jine = (TextView) findViewById(R.id.tv_jine);
        ll_zhifubao = (LinearLayout) findViewById(R.id.ll_zhifubao);
        ll_weixinpay = (LinearLayout) findViewById(R.id.ll_weixinpay);
        ll_zhifubao.setOnClickListener(this);
        ll_weixinpay.setOnClickListener(this);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
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
        switch (v.getId()){
            case R.id.ll_zhifubao:
                FuKuan("1");
                break;
            case R.id.ll_weixinpay:
                FuKuan("2");
                break;
        }
    }

    private void FuKuan(final String type) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/mode");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("orderId", orderId);

        CallServer.getInstance().add(23, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
            }
            @Override
            public void onSucceed(int what, Response response) {
                String orderSns = GsonUtils.getInstance().fromJson((String) response.get(), Bean11.class).getData().getOrderSns();

                Request<String> stringRequest1 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/launchPay", RequestMethod.POST);
                stringRequest1.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                stringRequest1.add("_method", "patch");
                stringRequest1.add("type", type);
                stringRequest1.add("orderSns", orderSns);
                CallServer.getInstance().add(123, stringRequest1, new OnResponseListener() {
                    @Override
                    public void onStart(int what) {
                    }
                    @Override
                    public void onSucceed(int what, Response response) {
                        if(type.equals("1")){
                            String data = GsonUtils.getInstance().fromJson((String) response.get(), ZhiFuBaoBean.class).getData();
                            //调支付宝支付
                            alipay(data);
                        } else if (type.equals("2")) {
                            //调微信支付
                            try {
                                JSONObject object = new JSONObject(response.get().toString());
                                if (object.getInt("code") == 200) {
                                    JSONObject object1 = object.getJSONObject("data");
                                    req.appId = object1.getString("appid");
                                    req.partnerId = object1.getString("partnerid");
                                    req.prepayId = object1.getString("prepayid");
                                    req.packageValue = object1.getString("package");
                                    req.nonceStr = object1.getString("noncestr");
                                    req.timeStamp = object1.getString("timestamp");
                                    req.sign = object1.getString("sign");
                                    sendPayReq();
//                                    wodeInstance.finish();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
            @Override
            public void onFailed(int what, Response response) {
            }
            @Override
            public void onFinish(int what) {
            }
        });


    }


    private PayReq req = new PayReq();
    private final String APP_ID = "wxd5c25b7a2a3864c3";
    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    /*
         * 调起微信支付
         */
    private void sendPayReq() {

        msgApi.registerApp(APP_ID);
        msgApi.sendReq(req);
        Log.i(">>>>>", req.partnerId);
    }


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(ZhiFuFangShiActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ZhiFuFangShiActivity.this, WoDeDingDanActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(ZhiFuFangShiActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(ZhiFuFangShiActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ZhiFuFangShiActivity.this, WoDeDingDanActivity.class);
                            finish();
                            startActivity(intent);

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(ZhiFuFangShiActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

    };

    //去支付
    public void alipay(final String data) {

        // 完整的符合支付宝参数规范的订单信息

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(ZhiFuFangShiActivity.this);
                // 调用支付接口，获取支付结果
                try {
                    String result = alipay.pay(data);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (ActivityNotFoundException exception) {
                    ShowToast.showToast("请先安装支付宝");
                }

            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
