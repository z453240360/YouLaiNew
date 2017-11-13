package com.bm.chengshiyoutian.youlaiwang.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.DialogUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.DianPuShouYeActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.DingDanXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.KuaiSuXiaDanActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.PingJiaActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ZhiFuFangShiActivity;
import com.bm.chengshiyoutian.youlaiwang.alipaydemo.PayResult;
import com.bm.chengshiyoutian.youlaiwang.bean.CodeBean;
import com.bm.chengshiyoutian.youlaiwang.bean.DingDanAllbean;
import com.bm.chengshiyoutian.youlaiwang.view.MylistView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_money;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_xiangqing;


/**
 * Created by sld on 2017/5/26.
 */

public class DingDanAllAdapter extends BaseAdapter {
    String Authorizatio;
    SharedPreferences sp;
    int x;


    public DingDanAllAdapter(String authorizatio, List<DingDanAllbean.DataBeanX.DataBean> datas, Context mContext) {
        Authorizatio = authorizatio;
        this.datas = datas;
        this.mContext = mContext;
        sp = mContext.getSharedPreferences(MyRes.CONFIG, 0);
    }

    List<DingDanAllbean.DataBeanX.DataBean> datas;

    private Context mContext;


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_dingdan1, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);

            viewHolder.tv_xiangqing = (TextView) view.findViewById(R.id.tv_xiangqing);

            viewHolder.money = (TextView) view.findViewById(R.id.money);
            viewHolder.tv_money = (TextView) view.findViewById(tv_money);
            viewHolder.tv_num = (TextView) view.findViewById(R.id.tv_num);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
            viewHolder.tv_pay = (TextView) view.findViewById(R.id.tv_pay);
            viewHolder.ll = (LinearLayout) view.findViewById(R.id.ll);
            viewHolder.lv = (MylistView) view.findViewById(R.id.lv);
            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }
        int order_state = datas.get(position).order_state;
        int is_payment = datas.get(position).is_payment;
        switch (order_state) {
            case 1:
                //viewHolder.tv_money.setText("待付款");
                viewHolder.tv_cancel.setVisibility(View.VISIBLE);
                viewHolder.tv_cancel.setText("取消订单");
                if (is_payment == 1) {
                    viewHolder.tv_pay.setVisibility(View.VISIBLE);
                    viewHolder.tv_pay.setText("付款");
                }

                break;
            case 2:
                //viewHolder.tv_money.setText("待发货");
                viewHolder.tv_pay.setVisibility(View.INVISIBLE);
                viewHolder.tv_cancel.setVisibility(View.INVISIBLE);
                if (is_payment == 1) {
                    viewHolder.tv_pay.setVisibility(View.VISIBLE);
                    viewHolder.tv_pay.setText("付款");
                }
                break;
            case 3:
                //viewHolder.tv_money.setText("已发货");
                viewHolder.tv_pay.setVisibility(View.VISIBLE);
                viewHolder.tv_cancel.setVisibility(View.GONE);
                if (is_payment == 1) {
                    viewHolder.tv_pay.setText("付款");
                } else {
                    viewHolder.tv_pay.setText("确认收货");
                }
                break;
            case 4:

                // viewHolder.tv_money.setText("已收货");
                viewHolder.tv_pay.setVisibility(View.GONE);
                viewHolder.tv_cancel.setVisibility(View.VISIBLE);
                viewHolder.tv_cancel.setText("评价");
                viewHolder.tv_cancel.setBackgroundResource(R.drawable.tv_bg_green_line);
                viewHolder.tv_cancel.setTextColor(Color.rgb(118, 201, 51));

                if (is_payment == 1) {
                    viewHolder.tv_pay.setVisibility(View.VISIBLE);
                    viewHolder.tv_pay.setText("付款");
                }
                break;
            case 5:
                // viewHolder.tv_money.setText("已完成");
                viewHolder.tv_pay.setVisibility(View.INVISIBLE);
                viewHolder.tv_cancel.setVisibility(View.INVISIBLE);
                if (is_payment == 1) {
                    viewHolder.tv_pay.setVisibility(View.VISIBLE);
                    viewHolder.tv_pay.setText("付款");
                }
                break;
            case 10:
                //viewHolder.tv_money.setText("已取消");
                viewHolder.tv_pay.setVisibility(View.INVISIBLE);
                viewHolder.tv_cancel.setVisibility(View.INVISIBLE);

                break;
            case 6:
                //viewHolder.tv_money.setText("已退货退款");
                viewHolder.tv_pay.setVisibility(View.INVISIBLE);
                viewHolder.tv_cancel.setVisibility(View.INVISIBLE);
                if (is_payment == 1) {
                    viewHolder.tv_pay.setVisibility(View.VISIBLE);
                    viewHolder.tv_pay.setText("付款");
                }
                break;

        }
        String format_order_state = datas.get(position).format_order_state;
        if (format_order_state.equals("待付款")) {
            viewHolder.money.setTextColor(Color.RED);
        } else {
            viewHolder.money.setTextColor(Color.BLACK);
        }


        viewHolder.money.setText(format_order_state);


        viewHolder.tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = viewHolder.tv_pay.getText().toString();
                if (s.equals("付款")) {
                    x = position;
                    Intent mIntent = new Intent(mContext, ZhiFuFangShiActivity.class);
                    mIntent.putExtra("orderId", datas.get(position).order_id + "");
                    String money = viewHolder.tv_money.getText().toString();
                    mIntent.putExtra("money", money);
                    mContext.startActivity(mIntent);

//                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/mode");
//                    stringRequest.add("orderId", datas.get(position).order_id);
//                    stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
//                    CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
//                        @Override
//                        public void onStart(int what) {
//                        }
//                        @Override
//                        public void onSucceed(int what, Response response) {
//                            String orderSns = Gsonutils.getInstance().fromJson((String) response.get(), Bean11.class).getData().getOrderSns();
//                            Intent mIntent = new Intent(mContext, ZhiFuFangShiActivity.class);
//                            mIntent.putExtra("orderSns", orderSns);
//                            String money = viewHolder.tv_money.getText().toString();
//                            mIntent.putExtra("money",money);
//                            mContext.startActivity(mIntent);
//                        }
//                        @Override
//                        public void onFailed(int what, Response response) {
//                        }
//                        @Override
//                        public void onFinish(int what) {
//                        }
//                    });


                } else if (s.equals("确认收货")) {
                    x = position;
                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/order/receipt");
                    stringRequest.add("orderId", datas.get(position).order_id);
                    stringRequest.add("_method", "patch");
                    stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                    CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response response) {
                            String json = (String) response.get();
                            if (json != null) {
                                CodeBean codeBean = GsonUtils.getInstance().fromJson(json, CodeBean.class);
                                if (codeBean.code == 200) {
                                    datas.remove(position);
                                    notifyDataSetChanged();
                                    ShowToast.showToast(codeBean.msg);
                                } else {
                                    ShowToast.showToast(codeBean.msg);
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
        });
        viewHolder.tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = viewHolder.tv_cancel.getText().toString();
                if (s.equals("取消订单")) {
                    View views = View.inflate(mContext, R.layout.dialog_shanchu, null);
                    TextView viewById = (TextView) views.findViewById(R.id.dialog_message);
                    viewById.setText("是否取消此订单");
                    final AlertDialog alertDialog = DialogUtils.ShowDialog2(views, mContext);
                    views.findViewById(R.id.dialog_delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/order/cancel", RequestMethod.POST);
                            stringRequest.add("_method", "patch");
                            stringRequest.add("orderId", datas.get(position).order_id);
                            stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                            CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
                                @Override
                                public void onStart(int what) {

                                }

                                @Override
                                public void onSucceed(int what, Response response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject((String) response.get());
                                        Log.e("tag", (String) response.get());
                                        String code = jsonObject.getString("code");
                                        String msg = jsonObject.getString("msg");
                                        if ("200".equals(code)) {

                                            datas.remove(position);
                                            notifyDataSetChanged();
                                            alertDialog.dismiss();
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
                    });

                    views.findViewById(R.id.dialog_dismiss).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                } else if (s.equals("评价")) {
                    Intent intent = new Intent(mContext, PingJiaActivity.class);
                    intent.putExtra("id", datas.get(position).order_id + "");
                    Log.e("xxxxx", datas.get(position).order_id + "");
                    mContext.startActivity(intent);
                }


            }
        });
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, KuaiSuXiaDanActivity.class);
                intent.putExtra(MyRes.DIANPU_ID, datas.get(position).shop_id + "");
                mContext.startActivity(intent);
            }
        });
        viewHolder.name.setText(datas.get(position).shop_name);
        viewHolder.tv_num.setText("共" + datas.get(position).num + "件商品");
        viewHolder.tv_money.setText(datas.get(position).order_amount + "");
        List<DingDanAllbean.DataBeanX.DataBean.OrderGoodsBean> order_goods = datas.get(position).order_goods;
        DingDan1Adapter2_All dingDan1Adapter2 = new DingDan1Adapter2_All(order_goods, mContext, Authorizatio);

//        viewHolder.lv.setClickable(false);
//        viewHolder.lv.setPressed(false);
//        viewHolder.lv.setFocusable(false);
//        viewHolder.lv.setFocusable(false);

        viewHolder.lv.setAdapter(dingDan1Adapter2);
        final int y = position;
        viewHolder.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, DingDanXiangQingActivity.class);
                intent.putExtra("id", datas.get(y).order_id + "");
                mContext.startActivity(intent);
            }
        });

        viewHolder.tv_xiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DingDanXiangQingActivity.class);
                intent.putExtra("id", datas.get(y).order_id + "");
                mContext.startActivity(intent);
            }
        });


        return view;

    }

    public class ViewHolder {

        public TextView name, tv_num, tv_money, tv_cancel, tv_pay, money, tv_xiangqing;
        MylistView lv;
        LinearLayout ll;


    }


    //去支付
    public void alipay(final String data) {


        // 完整的符合支付宝参数规范的订单信息

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(data);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();

                        datas.remove(x);
                        notifyDataSetChanged();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(mContext, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

    };
}
