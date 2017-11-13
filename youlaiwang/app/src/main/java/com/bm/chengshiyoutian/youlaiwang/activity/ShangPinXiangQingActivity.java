package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBeanNum;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPingXIangQIng1BuDaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.fragment.FragmentShangPin1XiangQing;
import com.bm.chengshiyoutian.youlaiwang.fragment.FragmentShangPin2XiangQing;
import com.bm.chengshiyoutian.youlaiwang.fragment.FragmentShangPin3XiangQing;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.HEAD;

import static android.R.id.list;


/**
 * Created by sld on 2017/5/25.
 */

public class ShangPinXiangQingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_shangpin, tv_xiangqing, tv_pingjia, tv_money, tv_count;
    private ImageView iv_fenxiang, iv_shoucang, iv_finish;
    private LinearLayout ll_dianpu, ll_dianhua, ll_shoucang, ll_fragment, llgouwuche;

    private FragmentShangPin1XiangQing fragmentShangPin1;
    private FragmentShangPin2XiangQing fragmentShangPin2;
    private FragmentShangPin3XiangQing fragmentShangPin3;

    private String tag;
    //请求头
    private String head;
    private int cart_num;
    private double Cart_price;
    private SharedPreferences sp;
    private Intent intent;
    private Intent ddongIntent;

    //店铺电话
    private String num;
    //店铺id
    private String StoreId;
    public static boolean isHeJi = false;
    private int pos_dd;
    private List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> goods_spec_d = new ArrayList<>();
    private int postion_ddd;

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getGouWuce();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        if (message.equals("jia1")) {
            getGouWuce();
        }

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangpinxiangqing);

        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255, 168, 0), 0);

        sp = getSharedPreferences(MyRes.CONFIG, 0);
        head = sp.getString(MyRes.MY_TOKEN, "");
        initView();
        tv_shangpin.setTextColor(0xffecff14);
        ddongIntent = new Intent();

        //token 不是token  是商品id
        String token = getIntent().getStringExtra("token");

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/" + token);
        stringRequest.addHeader("Authorization", head);
        CallServer.getInstance().add(12, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    ShangPinXQ1DaiPingLun.DataBean data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinXQ1DaiPingLun.class).getData();
                    String s = data.getIs_favorites() + "";
                    tag = s;
                    cart_num = data.getCart_num();
                    Cart_price = data.getCart_price();
                    DecimalFormat df = new DecimalFormat("######0.00");

                    num = data.getStore().getStore_phone() + "";
                    StoreId = data.getStore_id() + "";
                    tv_money.setText(df.format(Cart_price));
                    tv_count.setText(cart_num + "");
                    if ("1".equals(tag)) {
                        iv_shoucang.setImageResource(R.drawable.shoucang_004);

                    } else {
                        iv_shoucang.setImageResource(R.drawable.shoucang_003);

                    }
                } catch (JsonSyntaxException e) {
                    try {
                        ShangPingXIangQIng1BuDaiPingLun.DataBean data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPingXIangQIng1BuDaiPingLun.class).getData();
                        String s = data.getIs_favorites() + "";
                        tag = s;
                        cart_num = (int) data.getCart_num();
                        Cart_price = data.getCart_price();
                        StoreId = data.getStore_id() + "";
                        num = data.getStore().getStore_phone();
                        tv_money.setText(Cart_price + "");
                        tv_count.setText(cart_num + "");
                        if ("1".equals(tag)) {
                            iv_shoucang.setImageResource(R.drawable.shoucang_004);
                        } else {
                            iv_shoucang.setImageResource(R.drawable.shoucang_003);
                        }
                    } catch (Exception ex) {
                        ShowToast.showToast("数据异常");
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

        initListenrt();
        setDefaultFragment();
    }

    //刷新购物车数据
    private void getGouWuce() {
        head = sp.getString(MyRes.MY_TOKEN, "kong");
        String token = getIntent().getStringExtra("token");
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/" + token);
        stringRequest.addHeader("Authorization", head);
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {

                    ShangPinXQ1DaiPingLun.DataBean data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinXQ1DaiPingLun.class).getData();
                    String s = data.getIs_favorites() + "";
                    tag = s;
                    try {
                        cart_num = data.getCart_num();
                        tv_count.setText(cart_num + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getCarNum();

                    Cart_price = data.getCart_price();
                    DecimalFormat df = new DecimalFormat("######0.00");
                    tv_money.setText(df.format(Cart_price));


                } catch (JsonSyntaxException e) {
                    ShangPingXIangQIng1BuDaiPingLun.DataBean data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPingXIangQIng1BuDaiPingLun.class).getData();
                    String s = data.getIs_favorites() + "";
                    tag = s;
                    try {
                        cart_num = (int) data.getCart_num();
                        tv_count.setText(cart_num + "");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    Cart_price = data.getCart_price();
                    DecimalFormat df = new DecimalFormat("######0.00");
                    tv_money.setText(df.format(Cart_price));

                    getCarNum();

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

    //获取购物车数量
    private void getCarNum() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/total");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    int cartNum = GsonUtils.getInstance().fromJson((String) response.get(), GouWuCheBeanNum.class).getData().getCartNum();
                    tv_count.setText(cartNum + "");
                } catch (JsonSyntaxException e) {
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

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fragmentShangPin1 = FragmentShangPin1XiangQing.newInstance("");

        transaction.replace(R.id.ll_fragment, fragmentShangPin1);
        transaction.commit();


        //切换到评价
        fragmentShangPin1.setChange(new FragmentShangPin1XiangQing.onChange() {
            @Override
            public void getJump() {
                tv_pingjia.setTextColor(0xffecff14);
                tv_shangpin.setTextColor(0xffffffff);
                tv_xiangqing.setTextColor(0xffffffff);
                FragmentManager fm3 = getSupportFragmentManager();

                //开启事务
                FragmentTransaction transaction3 = fm3.beginTransaction();
                fragmentShangPin3 = FragmentShangPin3XiangQing.newInstance("");
                transaction3.replace(R.id.ll_fragment, fragmentShangPin3);
                transaction3.commit();
            }

            //获取变更后的数据
            @Override
            public void getData(int postion, List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> goods_spec) {
                goods_spec_d = goods_spec;
                ddongIntent.putExtra("goodsName", (Serializable) (goods_spec_d));
            }
        });
    }


    private void initView() {

        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        tv_shangpin = (TextView) findViewById(R.id.tv_shangpin);
        tv_xiangqing = (TextView) findViewById(R.id.tv_xiangqing);
        tv_pingjia = (TextView) findViewById(R.id.tv_pingjia);
        iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        ll_fragment = (LinearLayout) findViewById(R.id.ll_fragment);

        ll_dianhua = (LinearLayout) findViewById(R.id.ll_dianhua);
        ll_dianhua.setOnClickListener(this);
        ll_dianpu = (LinearLayout) findViewById(R.id.ll_dianpu);
        ll_dianpu.setOnClickListener(this);
        ll_shoucang = (LinearLayout) findViewById(R.id.ll_shoucang);
        ll_shoucang.setOnClickListener(this);
        iv_shoucang = (ImageView) findViewById(R.id.iv_shoucang);
        iv_shoucang.setOnClickListener(this);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_money.setOnClickListener(this);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_count.setOnClickListener(this);
        llgouwuche = (LinearLayout) findViewById(R.id.llgouwuche);
        llgouwuche.setOnClickListener(this);

        ddongIntent = new Intent();
        ddongIntent.putExtra("goodsName", (Serializable) (goods_spec_d));
        ddongIntent.putExtra("postion", -1);
        goods_spec_d = new ArrayList<>();


    }

    private void initListenrt() {
        iv_finish.setOnClickListener(this);
        tv_shangpin.setOnClickListener(this);
        tv_xiangqing.setOnClickListener(this);
        tv_pingjia.setOnClickListener(this);
        iv_fenxiang.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:


                Serializable goodsName = ddongIntent.getSerializableExtra("goodsName");
                setResult(RESULT_OK, ddongIntent);
                finish();
                break;
            case R.id.llgouwuche://购物车
                head = sp.getString(MyRes.MY_TOKEN, "kong");
                if ("kong".equals(head)) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(this, Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);

                } else {
                    isHeJi = true;
                    intent = new Intent(this, GouWuCheActivity.class);
                    startActivity(intent);

                }
                break;

            //切换到商品
            case R.id.tv_shangpin:
                tv_shangpin.setTextColor(0xffecff14);
                tv_xiangqing.setTextColor(0xffffffff);
                tv_pingjia.setTextColor(0xffffffff);
                FragmentManager fm1 = getSupportFragmentManager();
                //开启事务
                FragmentTransaction transaction1 = fm1.beginTransaction();
                fragmentShangPin1 = FragmentShangPin1XiangQing.newInstance("");
                transaction1.replace(R.id.ll_fragment, fragmentShangPin1);
                transaction1.commit();
                break;
            //切换到详情
            case R.id.tv_xiangqing:
                tv_xiangqing.setTextColor(0xffecff14);
                tv_shangpin.setTextColor(0xffffffff);
                tv_pingjia.setTextColor(0xffffffff);
                FragmentManager fm2 = getSupportFragmentManager();
                //开启事务
                FragmentTransaction transaction2 = fm2.beginTransaction();
                fragmentShangPin2 = FragmentShangPin2XiangQing.newInstance("");
                transaction2.replace(R.id.ll_fragment, fragmentShangPin2);
                transaction2.commit();

                break;
            //切换的评价
            case R.id.tv_pingjia:
                tv_pingjia.setTextColor(0xffecff14);
                tv_shangpin.setTextColor(0xffffffff);
                tv_xiangqing.setTextColor(0xffffffff);
                FragmentManager fm3 = getSupportFragmentManager();
                //开启事务
                FragmentTransaction transaction3 = fm3.beginTransaction();
                fragmentShangPin3 = FragmentShangPin3XiangQing.newInstance("");
                transaction3.replace(R.id.ll_fragment, fragmentShangPin3);
                transaction3.commit();
                break;
            case R.id.iv_fenxiang:

                break;
            case R.id.ll_dianhua:

                if (num != null) {

                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
                    startActivity(intent);
                }

                break;
            case R.id.ll_dianpu:
                intent = new Intent(this, KuaiSuXiaDanActivity.class);

                intent.putExtra(MyRes.DIANPU_ID, StoreId);
                sp.edit().putString(MyRes.DIANPU_ID, StoreId).commit();


                startActivity(intent);

                break;
            case R.id.ll_shoucang:
                head = sp.getString(MyRes.MY_TOKEN, "kong");
                if ("2".equals(tag)) {

                    if ("kong".equals(head)) {
                        ShowToast.showToast("请登录");
                        intent = new Intent(this, Login_ddActivity.class);
                        intent.putExtra("tag", "two");
                        startActivity(intent);

                    } else {
                        iv_shoucang.setImageResource(R.drawable.shoucang_004);
                        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/store", RequestMethod.POST);
                        stringRequest.addHeader("Authorization", head);
                        stringRequest.add("type", 1);
                        stringRequest.add("foreign_id", getIntent().getStringExtra("token"));
                        CallServer.getInstance().add(233, stringRequest, new OnResponseListener() {
                            @Override
                            public void onStart(int what) {


                            }

                            @Override
                            public void onSucceed(int what, Response response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject((String) response.get());
                                    String msg = jsonObject.getString("msg");
                                    String code = jsonObject.getString("code");
                                    if (code.equals("200")) {
                                        iv_shoucang.setImageResource(R.drawable.shoucang_004);
                                        ShowToast.showToast(msg);
                                        tag = 1 + "";
                                    } else {
                                        ShowToast.showToast(msg);
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


                } else {
                    if ("kong".equals(head)) {
                        ShowToast.showToast("请登录");
                        intent = new Intent(this
                                , Login_ddActivity.class);
                        intent.putExtra("tag", "two");
                        startActivity(intent);

                    } else {
                        iv_shoucang.setImageResource(R.drawable.shoucang_003);
                        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/destroy", RequestMethod.POST);
                        stringRequest.addHeader("Authorization", head);

                        stringRequest.add("type", 1);
                        stringRequest.add("_method", "delete");
                        stringRequest.add("foreign_ids", getIntent().getStringExtra("token"));
                        CallServer.getInstance().add(13412, stringRequest, new OnResponseListener() {
                            @Override
                            public void onStart(int what) {

                                ShowToast.showToast("删除");
                            }

                            @Override
                            public void onSucceed(int what, Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject((String) response.get());
                                    String msg = jsonObject.getString("msg");
                                    String code = jsonObject.getString("code");
                                    if (code.equals("200")) {
                                        iv_shoucang.setImageResource(R.drawable.shoucang_003);
                                        ShowToast.showToast(msg);
                                        tag = 2 + "";
                                    } else {
                                        ShowToast.showToast(msg);

                                    }


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


                }


                break;


        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Serializable goodsName = ddongIntent.getSerializableExtra("goodsName");
            setResult(RESULT_OK, ddongIntent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
