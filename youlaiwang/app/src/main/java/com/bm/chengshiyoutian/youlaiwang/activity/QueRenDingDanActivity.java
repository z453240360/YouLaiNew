package com.bm.chengshiyoutian.youlaiwang.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.DialogUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.DingdanDialogAdapter;
import com.bm.chengshiyoutian.youlaiwang.adapter.QueRenDingDanAdapter1;
import com.bm.chengshiyoutian.youlaiwang.alipaydemo.PayResult;
import com.bm.chengshiyoutian.youlaiwang.bean.FaQiZhiFuBean;
import com.bm.chengshiyoutian.youlaiwang.bean.FaQiZhiFuBean1;
import com.bm.chengshiyoutian.youlaiwang.bean.QueRenDingDanBena1;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.R.id.pop_lv;
import static com.bm.chengshiyoutian.youlaiwang.R.id.start;
import static com.bm.chengshiyoutian.youlaiwang.R.string.code;
import static com.bm.chengshiyoutian.youlaiwang.adapter.QueRenDingDanAdapter1.inputContainer;


/**
 * Created by sld on 2017/6/1.
 */

public class QueRenDingDanActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;
    QueRenDingDanBena1.DataBean data;
    private ListView lv;
    private TextView tv_go;
    private TextView tv_user_name;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_pay1;
    private TextView tv_pay2, tv_payWeiXin;
    private TextView tv_pay_money;
    private String cartIds, addressId;
    private TextView tv_money;
    private SharedPreferences sp;
    private List<QueRenDingDanBena1.DataBean.GoodsDataBean> goods_one;
    private QueRenDingDanAdapter1 adapter1;
    private StringBuffer bufferId;
    public static QueRenDingDanActivity queInstance;
    private String sss = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querendingdan1);
        queInstance = this;
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255, 168, 0), 0);
        sss = sp.getString(MyRes.cartIds, "");
        if (!"".equals(sss)) {

            try {
                data = GsonUtils.getInstance().fromJson(sss, QueRenDingDanBena1.class).getData();
                List<QueRenDingDanBena1.DataBean.GoodsDataBean> goods1 = data.getGoodsData();
                for (int i = 0; i < goods1.size(); i++) {

                    List<QueRenDingDanBena1.DataBean.GoodsDataBean.GoodsBean> goods2 = goods1.get(i).getGoods();
                }

                addressId = data.getAddress().getAddress_id() + "";

                JSONObject object = new JSONObject(sss);
                if (object.getInt("code") == 200) {
                    JSONObject object1 = object.getJSONObject("data");
                    JSONArray array = object1.getJSONArray("goodsData");
                    if (array.length() > 0) {
                        bufferId = new StringBuffer();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object2 = array.getJSONObject(i);
                            JSONArray array1 = object2.getJSONArray("goods");
                            if (array1.length() > 0) {

                                for (int a = 0; a < array1.length(); a++) {
                                    JSONObject object3 = array1.getJSONObject(a);
                                    String cart_id = object3.getString("cart_id");
                                    bufferId.append("," + cart_id);
                                }

                            }
                        }
                        cartIds = bufferId.toString();
                        cartIds = cartIds.substring(cartIds.indexOf(",") + 1, cartIds.length());
                    }
                }

            } catch (Exception e) {
                ShowToast.showToast("数据异常");
            }
        }

        initView();

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


    @Override
    protected void onStart() {
        super.onStart();
        String name = getIntent().getStringExtra("name");
        String add = getIntent().getStringExtra("add");

        String MyID = getIntent().getStringExtra("MyID");
        String phone = getIntent().getStringExtra("phone");

        if (name != null && add != null && MyID != null && phone != null) {
            addressId = MyID;
            tv_address.setText(add);
            tv_user_name.setText(name);
            tv_phone.setText(phone + "");
        }

    }

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        tv_go = (TextView) findViewById(R.id.tv_go);
        tv_go.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv);


        View head = View.inflate(this, R.layout.lv_head, null);
        tv_user_name = (TextView) head.findViewById(R.id.tv_user_name);
        tv_phone = (TextView) head.findViewById(R.id.tv_phone);
        tv_address = (TextView) head.findViewById(R.id.tv_address);
        lv.addHeaderView(head);

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QueRenDingDanActivity.this, MyAddressActivity.class);
                intent.putExtra("fromDingDan", true);
                startActivity(intent);

            }
        });

        View foot = View.inflate(this, R.layout.lv_foot, null);
        tv_pay1 = (TextView) foot.findViewById(R.id.tv_pay1);
        tv_pay1.setOnClickListener(this);
        tv_pay2 = (TextView) foot.findViewById(R.id.tv_pay2);
        tv_pay2.setOnClickListener(this);
        tv_payWeiXin = (TextView) foot.findViewById(R.id.tv_payWeiXin);
        tv_payWeiXin.setOnClickListener(this);
        tv_pay_money = (TextView) foot.findViewById(R.id.tv_pay_money);
        tv_pay_money.setOnClickListener(this);
        lv.addFooterView(foot);


        tv_address.setText(data.getAddress().getAreainfo());
        tv_phone.setText(data.getAddress().getMobile() + "");
        tv_user_name.setText(data.getAddress().getName());
        DecimalFormat df = new DecimalFormat("######0.00");
        tv_pay_money.setText(df.format(data.getMoney()) + "");
        goods_one = data.getGoodsData();
        adapter1 = new QueRenDingDanAdapter1(this, goods_one);
        lv.setAdapter(adapter1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        tv_money = (TextView) findViewById(R.id.tv_money);

        tv_money.setText(df.format(data.getMoney()) + "");
    }

    int x = 1;
    String type = "1"; //1=支付宝  2=微信支付
    //    List<String> remark = new ArrayList<>();
    StringBuffer buffer;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_go:
                buffer = new StringBuffer();

                if (inputContainer.size() > 0) {
                    for (Integer in : inputContainer.keySet()) {
                        String str = (String) inputContainer.get(in);
                        if ("".equals(str)) {
                            buffer.append(" " + "|||");
                        } else {
                            buffer.append(str + "|||");
                        }
                    }
                }

                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/submit", RequestMethod.POST);
                stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                stringRequest.add("cartIds", cartIds);
                stringRequest.add("addressId", addressId);
                stringRequest.add("isDistance", 1);
                stringRequest.add("paymentType", x);
                stringRequest.add("remark", buffer.toString());

                CallServer.getInstance().add(11, stringRequest, new OnResponseListener() {
                    @Override
                    public void onStart(int what) {
                    }

                    @Override
                    public void onSucceed(int what, Response response) {

                        try {
                            JSONObject jsonObject = new JSONObject((String) response.get());
                            String code = jsonObject.getString("code");
                            JSONObject data = jsonObject.getJSONObject("data");
                            try {
                                int isCanceled = data.getInt("is_canceled");

                                if (isCanceled==999){
                                    showDialog();
                                }
                                return;
                            } catch (JSONException e) {

                            }

                            if (code.equals("200")) {
                                FaQiZhiFuBean faQiZhiFuBean = GsonUtils.getInstance().fromJson((String) response.get(), FaQiZhiFuBean.class);
                                if (faQiZhiFuBean.getData().getStores() != null && faQiZhiFuBean.getData().getStores().size() > 0) {
                                    showit(faQiZhiFuBean.getData().getStores());
                                } else {
                                    Request<String> stringRequest1 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/launchPay", RequestMethod.POST);
                                    stringRequest1.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                                    stringRequest1.add("_method", "patch");
                                    stringRequest1.add("type", type);
                                    stringRequest1.add("orderSns", faQiZhiFuBean.getData().getOrderSns());
                                    CallServer.getInstance().add(11, stringRequest1, new OnResponseListener() {
                                        @Override
                                        public void onStart(int what) {
                                        }

                                        @Override
                                        public void onSucceed(int what, Response response) {
                                            if (x == 2) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject((String) response.get());
                                                    String msg = jsonObject.getString("msg");
                                                    String code = jsonObject.getString("code");
                                                    if ("200".equals(code)) {
                                                        ShowToast.showToast("下单成功");
                                                        Intent intent = new Intent(QueRenDingDanActivity.this, WoDeDingDanActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                if (type.equals("1")) {
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
                                                            finish();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
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

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                break;
            case R.id.tv_pay1:
                x = 1;
                type = "1";
                tv_pay1.setBackgroundResource(R.drawable.zhifufangshi_xuanzhong);
                tv_pay2.setBackgroundResource(R.drawable.zhifuweixuan);
                tv_payWeiXin.setBackgroundResource(R.drawable.zhifuweixuan);
                break;
            case R.id.tv_pay2:
                x = 2;
                tv_pay2.setBackgroundResource(R.drawable.zhifufangshi_xuanzhong);
                tv_pay1.setBackgroundResource(R.drawable.zhifuweixuan);
                tv_payWeiXin.setBackgroundResource(R.drawable.zhifuweixuan);
                break;
            case R.id.tv_payWeiXin:
                x = 1;
                type = "2";
                tv_payWeiXin.setBackgroundResource(R.drawable.zhifufangshi_xuanzhong);
                tv_pay1.setBackgroundResource(R.drawable.zhifuweixuan);
                tv_pay2.setBackgroundResource(R.drawable.zhifuweixuan);
                break;

        }
    }

    //超出距离弹框
    private void showit(List<FaQiZhiFuBean.DataBean.StoresBean> stores) {
        View view = View.inflate(this, R.layout.dialog_querendingdan1, null);
        final AlertDialog alertDialog = DialogUtils.ShowDialog(view, this);

        RecyclerView pop_lv = (RecyclerView) view.findViewById(R.id.pop_lv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pop_lv.setLayoutManager(linearLayoutManager);
        DingdanDialogAdapter dingdanDialogAdapter = new DingdanDialogAdapter(stores, this);
        pop_lv.setAdapter(dingdanDialogAdapter);


        //返回购物车
        view.findViewById(R.id.pop_gotogouwuche).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }

                finish();
            }
        });


        //继续下单
        view.findViewById(R.id.pop_xiadan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/submit", RequestMethod.POST);

                stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                stringRequest.add("cartIds", cartIds);
                stringRequest.add("addressId", addressId);
                stringRequest.add("isDistance", 2);
                stringRequest.add("paymentType", x);
                stringRequest.add("remark", buffer.toString());
                CallServer.getInstance().add(11, stringRequest, new OnResponseListener() {
                    @Override
                    public void onStart(int what) {
                    }

                    @Override
                    public void onSucceed(int what, Response response) {
                        try {
                            final JSONObject jsonObject = new JSONObject((String) response.get());
                            String code = jsonObject.getString("code");
                            if (code.equals("200")) {
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }

                                FaQiZhiFuBean1 faQiZhiFuBean = GsonUtils.getInstance().fromJson((String) response.get(), FaQiZhiFuBean1.class);
                                if (faQiZhiFuBean.getData().getStores() != null && faQiZhiFuBean.getData().getStores().size() > 0) {
                                } else {
                                    Request<String> stringRequest1 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/launchPay", RequestMethod.POST);
                                    stringRequest1.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                                    stringRequest1.add("_method", "patch");
                                    stringRequest1.add("type", type);
                                    stringRequest1.add("orderSns", faQiZhiFuBean.getData().getOrderSns());
                                    CallServer.getInstance().add(11, stringRequest1, new OnResponseListener() {
                                        @Override
                                        public void onStart(int what) {

                                        }

                                        @Override
                                        public void onSucceed(int what, Response response) {
                                            if (x == 2) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject((String) response.get());
                                                    String msg = jsonObject.getString("msg");
                                                    String code = jsonObject.getString("code");
                                                    if ("200".equals(code)) {
                                                        ShowToast.showToast("下单成功");
                                                        finish();
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                if (type.equals("1")) {
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
                                                            finish();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
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
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            String s = response.get().toString();
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
        });
    }


    //有未支付订单情况弹框
    private void showDialog() {
        View view2 = View.inflate(this, R.layout.dialog_querendingdan11, null);
        final AlertDialog alertDialog = DialogUtils.ShowDialog(view2, this);

        //弹框取消
        view2.findViewById(R.id.pop_xiadan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });


        //去支付 pop_xiadan
        view2.findViewById(R.id.pop_gotogouwuche).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QueRenDingDanActivity.this,WoDeDingDanActivity.class);
                intent.putExtra("current",1);
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                startActivity(intent);
            }
        });
    }







    private PayReq req = new PayReq();
    private final String APP_ID = "wxd5c25b7a2a3864c3";
    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, APP_ID, true);

    /*
         * 调起微信支付
         */
    private void sendPayReq() {

        msgApi.registerApp(APP_ID);
        msgApi.sendReq(req);
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
                        Toast.makeText(QueRenDingDanActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(QueRenDingDanActivity.this, WoDeDingDanActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(QueRenDingDanActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(QueRenDingDanActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(QueRenDingDanActivity.this, WoDeDingDanActivity.class);
                            finish();
                            startActivity(intent);

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(QueRenDingDanActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
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
                PayTask alipay = new PayTask(QueRenDingDanActivity.this);
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
