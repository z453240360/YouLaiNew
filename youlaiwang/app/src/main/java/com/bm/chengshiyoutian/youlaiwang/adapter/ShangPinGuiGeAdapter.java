package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.LoginActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.MainActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinYouBianBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;



/**
 * Created by sld on 2017/5/25.
 */

public class ShangPinGuiGeAdapter extends BaseAdapter {
    SharedPreferences sp;
    Context context;
    String goods_id;
    List<ShangPinYouBianBean.DataBeanX.DataBean.GoodsSpecBean> goods_spec;
    String head;
    Intent intent;
    ProgressDialog progressDialog;
    public ShangPinGuiGeAdapter(Context context, String goods_id, List<ShangPinYouBianBean.DataBeanX.DataBean.GoodsSpecBean> goods_spec, String head) {
        this.context = context;
        this.goods_id = goods_id;
        this.goods_spec = goods_spec;
        sp = context.getSharedPreferences(MyRes.CONFIG, 0);
        this.head = sp.getString(MyRes.MY_TOKEN, "kong");
    }

    @Override
    public int getCount() {
        return goods_spec.size();
    }

    @Override
    public Object getItem(int position) {
        return goods_spec.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int[] x = {goods_spec.get(position).getCart_goods_num()};

        View view = View.inflate(context, R.layout.item_guige, null);



        TextView tv1 = (TextView) view.findViewById(R.id.tv1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv2);
        TextView tv3 = (TextView) view.findViewById(R.id.tv3);
        final TextView tv4 = (TextView) view.findViewById(R.id.tv4);
        ImageView iv1 = (ImageView) view.findViewById(R.id.iv1);
        ImageView iv2 = (ImageView) view.findViewById(R.id.iv2);

        String cuxiao = goods_spec.get(position).getSpec_promo_price();//促销价格
        double cu = Double.parseDouble(cuxiao);
        if(cu > 0){
            DecimalFormat df = new DecimalFormat("#0.00");
            tv1.setText("¥" + goods_spec.get(position).getSpec_promo_price());
        }else {
            tv1.setText(
                    "¥" + goods_spec.get(position).getPrice());
        }

        tv2.setText(goods_spec.get(position).getRatio());

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (head.equals("kong")) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(context,Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    context.startActivity(intent);
                } else {
                    dialog("");
                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                stringRequest.addHeader("Authorization", head=sp.getString(MyRes.MY_TOKEN,""));
                stringRequest.add("goodsId", goods_id + "");
                stringRequest.add("specId", goods_spec.get(position).getSpec_id());
                stringRequest.add("type", 1);
                CallServer.getInstance().add(1232, stringRequest, new OnResponseListener() {
                    @Override
                    public void onStart(int what) {
                    }
                    @Override
                    public void onSucceed(int what, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject((String) response.get());
                            String msg = jsonObject.getString("msg");
                            ShowToast.showToast(msg);
                            Log.d("msg",(String) response.get());
                            String code = jsonObject.getString("code");
                            if ("200".equals(code)){
                                goods_spec.get(position).setCart_goods_num(++x[0]);
                                tv4.setText(goods_spec.get(position).getCart_goods_num() + "");
                                EventBus.getDefault().post("jia");
                                MainActivity.getData1();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailed(int what, Response response) {
                        ShowToast.showToast("联网失败");
                        progressDialog.cancel();
                    }

                    @Override
                    public void onFinish(int what) {
                        progressDialog.cancel();
                    }
                });}

            }
        });


        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x[0] < 1) {
                    ShowToast.showToast("已经最少");
                } else {

                    if (head.equals("kong")) {
                        ShowToast.showToast("请登录");
                        intent = new Intent(context, Login_ddActivity.class);
                        intent.putExtra("tag", "two");
                        context.startActivity(intent);

                    } else {
                        dialog("");
                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                    stringRequest.addHeader("Authorization", head=sp.getString(MyRes.MY_TOKEN,""));
                    stringRequest.add("goodsId", goods_id + "");
                    stringRequest.add("specId", goods_spec.get(position).getSpec_id());
                    stringRequest.add("type", 2);
                    CallServer.getInstance().add(1232, stringRequest, new OnResponseListener() {
                        @Override
                        public void onStart(int what) {
                        }
                        @Override
                        public void onSucceed(int what, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject((String) response.get());
                                String msg = jsonObject.getString("msg");
                                String code = jsonObject.getString("code");
                                if ("200".equals(code)){
                                    goods_spec.get(position).setCart_goods_num(--x[0]);
                                    tv4.setText(goods_spec.get(position).getCart_goods_num() + "");
                                    EventBus.getDefault().post("jian");
                                    MainActivity.getData1();
                                }
                                ShowToast.showToast(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailed(int what, Response response) {
                            ShowToast.showToast("联网失败");
                            progressDialog.cancel();
                        }

                        @Override
                        public void onFinish(int what) {
                            progressDialog.cancel();
                        }
                    });}
                }
            }
        });

        tv4.setText(goods_spec.get(position).getCart_goods_num() + "");

        return view;
    }

    class ViewHolder {

        public TextView tv1, tv2, tv3, tv4;
        ImageView iv1, iv2;

    }

    //进度条
    private void dialog(String mm) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
