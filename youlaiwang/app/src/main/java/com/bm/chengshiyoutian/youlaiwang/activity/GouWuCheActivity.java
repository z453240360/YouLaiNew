package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.GouWuCheAdapter1;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBean;
import com.bm.chengshiyoutian.youlaiwang.fragment.ShangPinFragment;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;


/**
 * Created by sld on 2017/6/7.
 */

public class GouWuCheActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar tb_toolbar;
    private ProgressBar pb;
    private ListView lv;
    private ImageView iv_check;
    private TextView tv_money;
    private TextView tv_delete;
    private TextView tv_pay;
    private LinearLayout ll_kongGouWuChe,ll_gouWuChe;
    private TextView tv_xuanShangPin;
    public static GouWuCheActivity gouInstance;
    private SharedPreferences sp;
    private String Authorization;
    private List<GouWuCheBean.DataBeanX.DataBean> data;
    private GouWuCheAdapter1 gouWuCheAdapter1;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gouwuche);
        gouInstance = this;
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        sp = getSharedPreferences(MyRes.CONFIG, 0);

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
        ll_kongGouWuChe = (LinearLayout) findViewById(R.id.ll_kongGouWuChe);
        ll_gouWuChe = (LinearLayout) findViewById(R.id.ll_gouWuChe);
        tv_xuanShangPin = (TextView) findViewById(R.id.tv_xuanShangPin);
        tv_xuanShangPin.setOnClickListener(this);

        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        tb_toolbar.setOnClickListener(this);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv);

        iv_check = (ImageView) findViewById(R.id.iv_check);
        iv_check.setOnClickListener(this);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_money.setOnClickListener(this);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(this);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_pay.setOnClickListener(this);
        iv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag) {
                    iv_check.setImageResource(R.drawable.xuanzhong_02);
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).tag = false;
                        List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> goods = data.get(i).getGoods();
                        for (int j = 0; j < goods.size(); j++) {
                            goods.get(j).tag = false;
                            //  x = data.get(i).getGoods().get(j).getPrice() * data.get(i).getGoods().get(j).getGoods_num() + x;
                        }
                    }
                    tv_money.setText("¥" + "0.00");
                    tag = !tag;
                    gouWuCheAdapter1 = new GouWuCheAdapter1(Authorization, data, GouWuCheActivity.this);

                    lv.setAdapter(gouWuCheAdapter1);

                } else {
                    x = 0;
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).tag = true;
                        List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> goods = data.get(i).getGoods();
                        for (int j = 0; j < goods.size(); j++) {
                            goods.get(j).tag = true;
                            x = data.get(i).getGoods().get(j).getPrice() * data.get(i).getGoods().get(j).getGoods_num() + x;
                        }
                    }
                    DecimalFormat    df   = new DecimalFormat("######0.00");
                    tv_money.setText("¥" + df.format(x) + "");
                    tag = !tag;

                    gouWuCheAdapter1 = new GouWuCheAdapter1(Authorization, data, GouWuCheActivity.this);
                    iv_check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);

                    lv.setAdapter(gouWuCheAdapter1);
                }
            }
        });
    }




    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
            ShowToast.showToast("请登录");
            intent = new Intent(GouWuCheActivity.this,Login_ddActivity.class);
            intent.putExtra("tag", "two");
            startActivity(intent);
        } else {
            getData1();
        }
        getData1();
    }

    //仅第一次进入调用
    private void getData1() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart");
        String token = sp.getString(MyRes.TOKEN, "");
        Authorization = "Bearer " + token;
        stringRequest.addHeader("Authorization", "Bearer " + token);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    Log.d("onSucceed", (String) response.get());
                    data = GsonUtils.getInstance().fromJson((String) response.get(), GouWuCheBean.class).getData().getData();
                    //   data.addAll(data);
                    //   data.addAll(data);
                    gouWuCheAdapter1 = new GouWuCheAdapter1(Authorization, data, GouWuCheActivity.this);
                    x = 0;
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).tag = true;
                        List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> goods = data.get(i).getGoods();
                        for (int j = 0; j < goods.size(); j++) {
                            goods.get(j).tag = true;
                            x = data.get(i).getGoods().get(j).getPrice() * data.get(i).getGoods().get(j).getGoods_num() + x;
                        }
                    }
                    DecimalFormat    df   = new DecimalFormat("######0.00");
                    tv_money.setText("¥" + df.format(x) + "");
                    if(data.size() > 0){
                        lv.setAdapter(gouWuCheAdapter1);
                        ll_kongGouWuChe.setVisibility(View.GONE);
                    }else {
                        ll_kongGouWuChe.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.GONE);
                    }


                } catch (JsonSyntaxException e) {
//                    ShowToast.showToast("数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                pb.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    public void getData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart");
        String token = sp.getString(MyRes.TOKEN, "");
        Authorization = "Bearer " + token;
        stringRequest.addHeader("Authorization", "Bearer " + token);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    Log.d("onSucceed", (String) response.get());
                    data = GsonUtils.getInstance().fromJson((String) response.get(), GouWuCheBean.class).getData().getData();
                    //   data.addAll(data);
                    //   data.addAll(data);
                    gouWuCheAdapter1 = new GouWuCheAdapter1(Authorization, data, GouWuCheActivity.this);
                    if(data.size() > 0){
                        lv.setAdapter(gouWuCheAdapter1);
                        ll_kongGouWuChe.setVisibility(View.GONE);
                    }else {
                        ll_kongGouWuChe.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.GONE);
                    }

                    x = 0;
                    for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < data.get(i).getGoods().size(); j++) {
                            if (data.get(i).getGoods().get(j).tag) {
                                x = data.get(i).getGoods().get(j).getPrice() * data.get(i).getGoods().get(j).getGoods_num() + x;

                            }
                        }

                    }
                    iv_check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
                    DecimalFormat    df   = new DecimalFormat("######0.00");
                    tv_money.setText("¥" + df.format(x) + "");

                } catch (JsonSyntaxException e) {
//                    ShowToast.showToast("数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                pb.setVisibility(View.GONE);
            }
        });
    }


    double x;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {

        if (message.equals("选中")) {
            sp.edit().putString("购物车","bianhua").commit();
            x = 0;
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < data.get(i).getGoods().size(); j++) {
                    if (data.get(i).getGoods().get(j).tag) {
                        x = data.get(i).getGoods().get(j).getPrice() * data.get(i).getGoods().get(j).getGoods_num() + x;

                    }
                }

            }
            iv_check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
            for (int i = 0; i < data.size(); i++) {
                Log.e("tag", "13123");

                if (data.get(i).tag == false) {


                    iv_check.setImageResource(R.drawable.xuanzhong_02);
                    break;
                }
            }
            // getData();
            DecimalFormat    df   = new DecimalFormat("######0.00");
            tv_money.setText("¥" + df.format(x) + "");

        }
        if (message.equals("变化")) {
            sp.edit().putString("购物车","bianhua").commit();
            //TODO 注销了
           /* tv_money.setText("");
            getData();*/


            x = 0;
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < data.get(i).getGoods().size(); j++) {
                    if (data.get(i).getGoods().get(j).tag) {
                        x = data.get(i).getGoods().get(j).getPrice() * data.get(i).getGoods().get(j).getGoods_num() + x;

                    }
                }

            }
            iv_check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
            for (int i = 0; i < data.size(); i++) {
                Log.e("tag", "13123");

                if (data.get(i).tag == false) {


                    iv_check.setImageResource(R.drawable.xuanzhong_02);
                    break;
                }
            }
            // getData();
            DecimalFormat    df   = new DecimalFormat("######0.00");
            tv_money.setText("¥" + df.format(x) + "");


        }

        if (message.equals("money")) {
            sp.edit().putString("购物车","bianhua").commit();
            try {
                double money = Double.parseDouble(sp.getString("money", ""));
                DecimalFormat    df   = new DecimalFormat("######0.00");
                tv_money.setText("¥" + df.format(money) + "");
            } catch (Exception ex) {

            }

            kong = sp.getString(MyRes.GoodsId, "kong");
            iv_check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
            for (int i = 0; i < data.size(); i++) {
                Log.e("tag", "13123");

                if (data.get(i).tag == false) {


                    iv_check.setImageResource(R.drawable.xuanzhong_02);
                    break;
                }
            }

        }
    }

    String kong;


    //是否全选
    boolean tag = true;
    ArrayList<String> cardIds = new ArrayList<>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay:
                cardIds.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() { //　新建一个线程，并新建一个Message的对象，是用Handler的对象发送这个Message

                        if(data.size() > 0){
                            for (int i = 0; i < data.size(); i++) {

                                for (int j = 0; j < data.get(i).getGoods().size(); j++) {
                                    if (data.get(i).getGoods().get(j).tag) {
                                        int cart_id = data.get(i).getGoods().get(j).getCart_id();
                                        cardIds.add(cart_id + "");

                                    }

                                }

                            }
                        }


//                        String[] array = (String[])cardIds.toArray(new String[cardIds.size()]);
//
//                        for (int i = 0; i <array.length ; i++) {
//                            Log.d("cardIds",array[i]);
//                        }
                        // String[] x={"123","123","123"};
                        Message msg = new Message();
                        msg.what = 1; // 用户自定义的一个值，用于标识不同类型的消息
                        hd.sendMessage(msg); // 发送消息
                    }
                }).start();

                break;
            case R.id.tv_delete:
                cardIds.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() { //　新建一个线程，并新建一个Message的对象，是用Handler的对象发送这个Message
                        for (int i = 0; i < data.size(); i++) {

                            for (int j = 0; j < data.get(i).getGoods().size(); j++) {
                                if (data.get(i).getGoods().get(j).tag) {
                                    int cart_id = data.get(i).getGoods().get(j).getCart_id();
                                    cardIds.add(cart_id + "");
                                }
                            }

                        }

                        Message msg = new Message();
                        msg.what = 2; // 用户自定义的一个值，用于标识不同类型的消息
                        hd.sendMessage(msg); // 发送消息
                    }
                }).start();
                break;
            case R.id.tv_xuanShangPin:
                Intent mIntent = new Intent(GouWuCheActivity.this,MainActivity.class);
                mIntent.putExtra("from","gouwuche");
                startActivity(mIntent);
                break;

        }
    }

    private Handler hd = new MyHandler();

    // 定义一个内部类继承自Handler，并且覆盖handleMessage方法用于处理子线程传过来的消息
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
//订单
                case 1: // 接受到消息之后，对UI控件进行修改
                    String new_CarrId = ",";

                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/confirm");
                    stringRequest.addHeader("Authorization", Authorization);
                    for (int i = 0; i < cardIds.size(); i++) {
                        new_CarrId = new_CarrId + cardIds.get(i) + ",";
                    }

                    if (",".equals(new_CarrId)) {
                        ShowToast.showToast("请选择商品");

                    } else {

                        stringRequest.add("cartIds", new_CarrId);
                        Log.d("ding", new_CarrId + "ding");
                        CallServer.getInstance().add(1333, stringRequest, new OnResponseListener() {
                            @Override
                            public void onStart(int what) {
                                pb.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onSucceed(int what, Response response) {
                                Log.d("cartIds", (String) response.get());
                                Intent intent = new Intent(GouWuCheActivity.this, QueRenDingDanActivity.class);
                                intent.putExtra("data", (String) response.get());
                                sp.edit().putString(MyRes.cartIds, (String) response.get()).commit();
                                intent.putExtra("Authorization", Authorization);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailed(int what, Response response) {

                            }

                            @Override
                            public void onFinish(int what) {
                                pb.setVisibility(View.GONE);
                            }
                        });


                    }


                    break;
                case 2:
                    String new_CarrId1 = ",";
                    Request<String> stringRequest1 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/destroy", RequestMethod.POST);
                    for (int i = 0; i < cardIds.size(); i++) {
                        new_CarrId1 = new_CarrId1 + cardIds.get(i) + ",";
                    }

                    if ("1".equals(new_CarrId1)) {

                        ShowToast.showToast("请选择商品");
                    } else {

                        stringRequest1.addHeader("Authorization", Authorization);
                        stringRequest1.add("cart_id", new_CarrId1);
                        stringRequest1.add("_method", "delete");

                        CallServer.getInstance().add(13, stringRequest1, new OnResponseListener() {
                            @Override
                            public void onStart(int what) {
                                pb.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onSucceed(int what, Response response) {
                                Log.d("delete", (String) response.get());
                                getData();
                                tv_money.setText("");
                            }

                            @Override
                            public void onFailed(int what, Response response) {

                            }

                            @Override
                            public void onFinish(int what) {
                                pb.setVisibility(View.GONE);
                            }
                        });


                    }


                    break;
                default:
                    break;
            }
        }
    }

}
