package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
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
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sld on 2017/5/25.
 */

public class ShangPinGuiGeXiangQingAdapter1 extends BaseAdapter {
    private int x = 0;
    private Context context;
    private String goods_id;
    private List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> goods_spec;
    private String head;
    boolean isHeJi;
    private SharedPreferences sp;
    private Map<Integer, String> map = new HashMap<Integer, String>();
    private Intent intent;

    public void setHeJi(boolean heJi) {
        isHeJi = heJi;
    }

    public ShangPinGuiGeXiangQingAdapter1(Context context, String goods_id, List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> goods_spec, String head) {
        this.context = context;
        this.goods_id = goods_id;
        this.goods_spec = goods_spec;
        this.head = head;
        sp = context.getSharedPreferences("dd", Context.MODE_PRIVATE);
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
        final int[] x = {0};
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_guige2, null);

            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
            viewHolder.tv4 = (TextView) convertView.findViewById(R.id.tv4);
            viewHolder.iv1 = (ImageView) convertView.findViewById(R.id.iv1);
            viewHolder.iv2 = (ImageView) convertView.findViewById(R.id.iv2);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        double v = Double.parseDouble(goods_spec.get(position).getSpec_promo_price());

        for (int i = 0; i < goods_spec.size(); i++) {
            goods_spec.get(i).setGoods_id(goods_id);
        }

        if (v <= 0) {
            viewHolder.tv1.setVisibility(View.GONE);
            viewHolder.tv.setText("¥ " + goods_spec.get(position).getPrice() + " ");
            viewHolder.tv.setTextColor(context.getResources().getColor(R.color.dongdong));


        } else {
            viewHolder.tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tv.setText("¥ " + goods_spec.get(position).getPrice() + " ");
            viewHolder.tv1.setText("¥ " + goods_spec.get(position).getSpec_promo_price() + " ");

        }

        viewHolder.tv1.setText("¥ " + goods_spec.get(position).getSpec_promo_price() + " ");
        viewHolder.tv2.setText(goods_spec.get(position).getRatio());
        viewHolder.tv4.setText(goods_spec.get(position).getCart_goods_num() + "");
        x[0] = goods_spec.get(position).getCart_goods_num();


        viewHolder.iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("kong".equals(head)) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(context, Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    context.startActivity(intent);

                } else {
                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                    stringRequest.addHeader("Authorization", head);
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

                                String code = jsonObject.getString("code");
                                if ("200".equals(code)) {
                                    EventBus.getDefault().post("jia1");
                                    x[0]++;
                                    viewHolder.tv4.setText(x[0] + "");//显示数量
                                    goods_spec.get(position).setCart_goods_num(x[0]);

                                        if (myListener != null) {
                                            myListener.getMap(position,goods_spec);
                                        }
                                }
                                if (myListener != null) {
                                    myListener.getTextNumber(x[0] + "");
                                    ShowToast.showToast(msg + "");
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
        });


        viewHolder.iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x[0] < 1) {
                    ShowToast.showToast("已经最少");
                } else {
                    if ("kong".equals(head)) {
                        ShowToast.showToast("请登录");
                        intent = new Intent(context, Login_ddActivity.class);
                        intent.putExtra("tag", "two");
                        context.startActivity(intent);

                    } else {
                        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                        stringRequest.addHeader("Authorization", head);
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
                                    if ("200".equals(code)) {
                                        EventBus.getDefault().post("jia1");
                                        x[0]--;
                                        if (x[0] < 1) {
                                            ShowToast.showToast("已经最少");
                                            goods_spec.get(position).setCart_goods_num(x[0]);
                                            if (myListener != null) {
                                                myListener.getMap(position,goods_spec);
                                            }
                                        } else {
                                            goods_spec.get(position).setCart_goods_num(x[0]);
                                            viewHolder.tv4.setText(x[0] + "");
                                            if (myListener != null) {
                                                myListener.getMap(position,goods_spec);
                                            }
                                        }
                                    }

                                    if (myListener != null) {
                                        myListener.getTextNumber(x[0] + "");
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
                }
            }
        });
        return convertView;
    }

    private OnTextChanged myListener;

    public OnTextChanged getMyListener() {
        return myListener;
    }

    public interface OnTextChanged {
        void getTextNumber(String s);
        void getMap(int postion,List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> goods_spec);
    }

    public void setMyListener(OnTextChanged myListener) {
        this.myListener = myListener;
    }

    class ViewHolder {

        public TextView tv1, tv2, tv3, tv4, tv;
        ImageView iv1, iv2;

    }


}
