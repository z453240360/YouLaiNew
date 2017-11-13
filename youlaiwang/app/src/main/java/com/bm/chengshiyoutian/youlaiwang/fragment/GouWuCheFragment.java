package com.bm.chengshiyoutian.youlaiwang.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.SharedPUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.AddAddressActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.MainActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.QueRenDingDanActivity;
import com.bm.chengshiyoutian.youlaiwang.adapter.GouWuCheAdapter1;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBean;
import com.bm.chengshiyoutian.youlaiwang.bean.QueRenDingDanBena1;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.google.gson.JsonSyntaxException;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;


public class GouWuCheFragment extends Fragment implements View.OnClickListener {


    private ProgressBar pb;
    private ListView lv;
    private TextView tv_delete;
    private TextView tv_pay;
    private TextView tv_money;
    private ImageView iv_check;
    private LinearLayout ll_kongGouWuChe,ll_gouWuChe;
    private TextView tv_xuanShangPin;
    private ShangPinFragment locationFragment3;
    private Context context;
    private SharedPreferences sp;
    private String Authorization;
    private List<GouWuCheBean.DataBeanX.DataBean> data;
    private GouWuCheAdapter1 gouWuCheAdapter1;
    private Handler hd = new MyHandler();
    private double x;
    private String kong;
    private Intent intent;
    private boolean tag = true;//是否全选
    private ArrayList<String> cardIds = new ArrayList<>();


    public static GouWuCheFragment newInstance(String param1) {
        GouWuCheFragment fragment = new GouWuCheFragment();
        return fragment;
    }

    public GouWuCheFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getData1();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ColorState.setWindowStatusBarColorBlue(getActivity(),Color.BLUE);
        View view = inflater.inflate(R.layout.fragment_gouwuche, container, false);
        sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);
        context = getActivity();
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                    data = GsonUtils.getInstance().fromJson((String) response.get(), GouWuCheBean.class).getData().getData();
                    gouWuCheAdapter1 = new GouWuCheAdapter1(Authorization, data, context);
                    gouWuCheAdapter1.notifyDataSetChanged();
                    x = 0;
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).tag = true;
                        List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> goods = data.get(i).getGoods();
                        for (int j = 0; j < goods.size(); j++) {
                            goods.get(j).tag = true;
                            x = data.get(i).getGoods().get(j).getPrice() * data.get(i).getGoods().get(j).getGoods_num() + x;
                        }
                    }

                    DecimalFormat df = new DecimalFormat("######0.00");
                    tv_money.setText("¥" + df.format(x) + "");

                    if(data.size() > 0){
                        lv.setAdapter(gouWuCheAdapter1);
                        lv.setVisibility(View.VISIBLE);
                        ll_kongGouWuChe.setVisibility(View.GONE);
                    }else {
                        ll_kongGouWuChe.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.GONE);
                    }

                } catch (JsonSyntaxException e) {
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
                    data = GsonUtils.getInstance().fromJson((String) response.get(), GouWuCheBean.class).getData().getData();
                    gouWuCheAdapter1 = new GouWuCheAdapter1(Authorization, data, context);
                    if(data.size() > 0){
                        lv.setAdapter(gouWuCheAdapter1);
                        lv.setVisibility(View.VISIBLE);
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
                    DecimalFormat format = new DecimalFormat("#0.00");
                    String text = format.format(x);
                    tv_money.setText("¥" + text + "");
                    iv_check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
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
                pb.setVisibility(View.GONE);
            }
        });
    }

    private void initView(View view) {
        pb = (ProgressBar) view.findViewById(R.id.pb);
        lv = (ListView) view.findViewById(R.id.lv);
        ll_kongGouWuChe = (LinearLayout) view.findViewById(R.id.ll_kongGouWuChe);
        ll_gouWuChe = (LinearLayout) view.findViewById(R.id.ll_gouWuChe);
        tv_xuanShangPin = (TextView) view.findViewById(R.id.tv_xuanShangPin);
        tv_xuanShangPin.setOnClickListener(this);
        tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(this);
        tv_pay = (TextView) view.findViewById(R.id.tv_pay);
        tv_pay.setOnClickListener(this);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_money.setOnClickListener(this);
        iv_check = (ImageView) view.findViewById(R.id.iv_check);
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
                        }
                    }
                    tv_money.setText("¥" + "0.00");
                    tag = !tag;
                    gouWuCheAdapter1 = new GouWuCheAdapter1(Authorization, data, context);


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

                    DecimalFormat format = new DecimalFormat("#0.00");
                    String text = format.format(x);
                    tv_money.setText("¥" + text + "");
                    tag = !tag;

                    gouWuCheAdapter1 = new GouWuCheAdapter1(Authorization, data, context);
                    iv_check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
                    lv.setAdapter(gouWuCheAdapter1);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay:
                sp.edit().putString("购物车","bianhua").commit();
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
                        msg.what = 1; // 用户自定义的一个值，用于标识不同类型的消息
                        hd.sendMessage(msg); // 发送消息
                    }
                }).start();
                SharedPUtils.storeBoolean(getActivity(),"isChanged",true);
                break;

            case R.id.tv_delete:
                sp.edit().putString("购物车","bianhua").commit();
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
                SharedPUtils.storeBoolean(getActivity(),"isChanged",true);

                break;




            // TODO 跳转到主页面，并且让商品显示
            case R.id.tv_xuanShangPin:
                sp.edit().putString("购物车","bianhua").commit();
                changes.getChange("shoppingCar");
                break;
        }
    }

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
                        CallServer.getInstance().add(1333, stringRequest, new OnResponseListener() {
                            @Override
                            public void onStart(int what) {
                                pb.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onSucceed(int what, Response response) {
                                try {
                                    QueRenDingDanBena1.DataBean.AddressBean address = GsonUtils.getInstance().fromJson((String) response.get(), QueRenDingDanBena1.class).getData().getAddress();

                                    if (address.getAreainfo().equals("")) {
                                        ShowToast.showToast("收货地址为空.请添加收货地址");
                                        Intent intent1 = new Intent(getActivity(), AddAddressActivity.class);
                                        startActivity(intent1);
                                    } else {
                                        sp.edit().putString(MyRes.cartIds, (String) response.get()).commit();
                                        Intent intent = new Intent(getActivity(), QueRenDingDanActivity.class);
                                        intent.putExtra("data", (String) response.get());
                                        intent.putExtra("Authorization", Authorization);
                                        context.startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    try {
                                        JSONObject jsonObject = new JSONObject((String) response.get());
                                        if (300 == jsonObject.getInt("code")) {
                                            ShowToast.showToast("收货地址为空.请添加收货地址");
                                            Intent intent1 = new Intent(getActivity(), AddAddressActivity.class);
                                            startActivity(intent1);
                                        }
                                    } catch (JSONException e1) {
                                        Log.e("jsons", "数据异常");
                                    }
                                }

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
                                getData();
                                tv_money.setText("¥" + "0.00");
                                MainActivity.getData1();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {

        if (message.equals("选中")) {

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
                if (data.get(i).tag == false) {
                    iv_check.setImageResource(R.drawable.xuanzhong_02);
                    break;
                }
            }
            DecimalFormat format = new DecimalFormat("#0.00");
            tv_money.setText("¥" + gouWuCheAdapter1.getTotalPrice() + "");

            sp.edit().putString("购物车","bianhua").commit();
        }

        if (message.equals("变化")) {
            tv_money.setText("¥" + gouWuCheAdapter1.getTotalPrice() + "");
            sp.edit().putString("购物车","bianhua").commit();
            getData();

        }

        if (message.equals("money")) {


            tv_money.setText("¥" + gouWuCheAdapter1.getTotalPrice() + "");
            kong = sp.getString(MyRes.GoodsId, "kong");
            iv_check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).tag == false) {
                    iv_check.setImageResource(R.drawable.xuanzhong_02);
                    break;
                }
            }
            sp.edit().putString("购物车","bianhua").commit();
        }
    }

    //接口回调
    private GetChange changes;

    public void setChange(GetChange change) {
        this.changes = change;
    }

    public interface GetChange{
        void getChange(String s);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            ColorState.setWindowStatusBarColorBlue(getActivity(),Color.parseColor("#ffffff"));
        }
    }
}