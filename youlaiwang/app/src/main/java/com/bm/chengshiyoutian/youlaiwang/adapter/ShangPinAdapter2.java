package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.MainActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinYouBianBean;
import com.bm.chengshiyoutian.youlaiwang.view.MylistView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bumptech.glide.Glide;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_count;


/**
 * Created by sld on 2017/5/12.
 */

public class ShangPinAdapter2 extends BaseAdapter {
    //是否展开规格的标记
    private boolean tag;
    private List<ShangPinYouBianBean.DataBeanX.DataBean> data1;
    private Context context;
    private SharedPreferences sp;
    private Intent intent;
    private ProgressDialog progressDialog;


    public void setData1(List<ShangPinYouBianBean.DataBeanX.DataBean> data1) {
        this.data1 = data1;
    }

    public ShangPinAdapter2(List<ShangPinYouBianBean.DataBeanX.DataBean> data1, Context context, String head) {
        this.data1 = data1;
        this.context = context;
        sp = context.getSharedPreferences(MyRes.CONFIG, 0);

    }

    @Override
    public int getCount() {
        return data1.size();
    }

    @Override
    public Object getItem(int position) {
        return data1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.item_shangpin, null);
        try {
            final int[] x = {data1.get(position).getSpec().getCart_goods_num()};

            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView jiage = (TextView) view.findViewById(R.id.jiage);
            final TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
            TextView danwei = (TextView) view.findViewById(R.id.danwei);
            TextView cuxiao = (TextView) view.findViewById(R.id.cuxiao);
            TextView xin = (TextView) view.findViewById(R.id.xin);
            TextView tv_xuanquguige = (TextView) view.findViewById(R.id.tv_xuanquguige);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            final ImageView iv_jian = (ImageView) view.findViewById(R.id.iv_jian);
            ImageView iv_jia = (ImageView) view.findViewById(R.id.iv_jia);
            LinearLayout ll_xuanze = (LinearLayout) view.findViewById(R.id.ll_xuanzhe);
            final LinearLayout ll_dd = (LinearLayout) view.findViewById(R.id.ll_dd);
            MylistView lv = (MylistView) view.findViewById(R.id.lv);


            if ("1".equals(data1.get(position).getGoods_is_new() + "")) {
                xin.setVisibility(View.VISIBLE);

            } else {
                xin.setVisibility(View.GONE);
            }
            if ("1".equals(data1.get(position).getGoods_is_promo() + "")) {
                cuxiao.setVisibility(View.VISIBLE);
            } else {
                cuxiao.setVisibility(View.GONE);
            }

            tv_name.setText(data1.get(position).getGoods_name());
            String cuxiaojia = data1.get(position).getSpec().getSpec_promo_price();
            double cu = Double.parseDouble(cuxiaojia);

            if (cu > 0) {
                jiage.setText("¥" + data1.get(position).getSpec().getSpec_promo_price());
                jiage.setTextColor(jiage.getResources().getColor(R.color.cuxiaojia));
            } else {
                jiage.setText("¥" + data1.get(position).getSpec().getPrice());
            }

            danwei.setText(data1.get(position).getSpec().getRatio());


            lv.setAdapter(new ShangPinGuiGeAdapter(context,
                    data1.get(position).getGoods_id() + "",
                    data1.get(position).getGoods_spec(),
                    sp.getString(MyRes.MY_TOKEN, "kong")));

            if (data1.get(position).tag) {
                tv_xuanquguige.setText("收   回");
                lv.setVisibility(View.VISIBLE);
            } else {
                lv.setVisibility(View.GONE);
                tv_xuanquguige.setText("选取规格");
            }

            if (data1.get(position).getGoods_spec().size() == 0) {
                ll_xuanze.setVisibility(View.GONE);
            } else {
                ll_xuanze.setVisibility(View.VISIBLE);
            }

            Glide.with(context)
                    .load(data1.get(position).getGoods_cover())
                    .into(imageView);

            //图片点击事件，跳转到商品详情页面
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClicked!=null){
                        onClicked.getIntent(position,data1.get(position).getGoods_id() + "");
                    }
                }
            });

            //选取规格
            tv_xuanquguige.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tag = !tag;
                    data1.get(position).tag = !data1.get(position).tag;
                    notifyDataSetChanged();
                }
            });


            //增加商品,并且同步数据
            iv_jia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_jian.setVisibility(View.VISIBLE);
                    tv_count.setVisibility(View.VISIBLE);
                    if (sp.getString(MyRes.MY_TOKEN, "kong").equals("kong")) {
                        ShowToast.showToast("请登录");
                        intent = new Intent(context, Login_ddActivity.class);
                        intent.putExtra("tag", "two");
                        context.startActivity(intent);

                    } else {
                        try {
                            dialog("");
                            Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                            stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                            stringRequest.add("goodsId", data1.get(position).getGoods_id() + "");
                            stringRequest.add("specId", data1.get(position).getSpec().getSpec_id());
                            stringRequest.add("type", 1);
                            CallServer.getInstance().add(1232, stringRequest, new OnResponseListener() {
                                @Override
                                public void onStart(int what) {
                                }

                                @Override
                                public void onSucceed(int what, Response response) {
                                    progressDialog.cancel();
                                    try {
                                        JSONObject jsonObject = new JSONObject((String) response.get());
                                        String msg = jsonObject.getString("msg");
                                        String code = jsonObject.getString("code");
                                        if ("200".equals(code)) {
                                            data1.get(position).getSpec().setCart_goods_num(++x[0]);
                                            tv_count.setText(data1.get(position).getSpec().getCart_goods_num() + "");
                                            EventBus.getDefault().post("jia");
                                            MainActivity.getData1();
                                        }
                                        ShowToast.showToast(msg);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailed(int what, Response response) {
                                    progressDialog.cancel();
                                }

                                @Override
                                public void onFinish(int what) {
                                    progressDialog.cancel();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            ShowToast.showToast("点击速度不要太快哦！");
                        }


                    }

                }
            });

            //减少商品
            iv_jian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (x[0] < 1) {
                        ShowToast.showToast("已经最少");
                    } else {
                        if (sp.getString(MyRes.MY_TOKEN, "kong").equals("kong")) {
                            ShowToast.showToast("请登录");
                            intent = new Intent(context, Login_ddActivity.class);
                            intent.putExtra("tag", "two");
                            context.startActivity(intent);
                        } else {
                            try {
                                dialog("");
                                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                                stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                                stringRequest.add("goodsId", data1.get(position).getGoods_id());
                                stringRequest.add("specId", data1.get(position).getSpec().getSpec_id() + "");
                                stringRequest.add("type", 2);
                                CallServer.getInstance().add(100, stringRequest, new OnResponseListener() {
                                    @Override
                                    public void onStart(int what) {
                                    }

                                    @Override
                                    public void onSucceed(int what, Response response) {
                                        progressDialog.cancel();
                                        try {
                                            JSONObject jsonObject = new JSONObject((String) response.get());
                                            String msg = jsonObject.getString("msg");
                                            String code = jsonObject.getString("code");
                                            if ("200".equals(code)) {
                                                data1.get(position).getSpec().setCart_goods_num(--x[0]);
                                                tv_count.setText(data1.get(position).getSpec().getCart_goods_num() + "");

                                                if (x[0] == 0) {
                                                    iv_jian.setVisibility(View.VISIBLE);
                                                    ll_dd.setBackgroundColor(Color.WHITE);
                                                    tv_count.setVisibility(View.VISIBLE);
                                                } else {
                                                }
                                                EventBus.getDefault().post("jian");
                                                MainActivity.getData1();
                                            }

                                            if (msg.equals("删除购物车成功")) {
                                                iv_jian.setVisibility(View.VISIBLE);
                                                ll_dd.setBackgroundColor(Color.WHITE);
                                                tv_count.setVisibility(View.VISIBLE);
                                            }
//                                            notifyDataSetInvalidated();
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
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                ShowToast.showToast("点击速度不要太快哦！");
                            }

                        }
                    }
                }
            });

            if (data1.get(position).getSpec().getCart_goods_num() > 0) {
                tv_count.setText(data1.get(position).getSpec().getCart_goods_num() + "");
                iv_jian.setVisibility(View.VISIBLE);
                tv_count.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    class ViewHolder {
        MylistView lv;
        public TextView tv_name, jiage, danwei, tv_xuanquguige, tv_count, cuxiao, xin;
        ImageView imageView, iv_jian, iv_jia;
        LinearLayout ll_xuanze, ll_dd;


        public ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            jiage = (TextView) view.findViewById(R.id.jiage);
            tv_count = (TextView) view.findViewById(R.id.tv_count);
            danwei = (TextView) view.findViewById(R.id.danwei);
            cuxiao = (TextView) view.findViewById(R.id.cuxiao);
            xin = (TextView) view.findViewById(R.id.xin);
            tv_xuanquguige = (TextView) view.findViewById(R.id.tv_xuanquguige);

            imageView = (ImageView) view.findViewById(R.id.iv);
            iv_jian = (ImageView) view.findViewById(R.id.iv_jian);
            iv_jia = (ImageView) view.findViewById(R.id.iv_jia);

            ll_xuanze = (LinearLayout) view.findViewById(R.id.ll_xuanzhe);
            ll_dd = (LinearLayout) view.findViewById(R.id.ll_dd);
            lv = (MylistView) view.findViewById(R.id.lv);
        }


    }

    //跳转商品详情，获取改变后的数据
    public void refish(List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> backData) {
        if (backData == null) {
            return;
        }

        int goods_id = Integer.parseInt(backData.get(0).getGoods_id());
        for (int i = 0; i < data1.size(); i++) {
            if (data1.get(i).getGoods_id() == goods_id) {
                for (int j = 0; j < backData.size(); j++) {
                    int spec_id = backData.get(j).getSpec_id();
                    int cart_goods_num = backData.get(j).getCart_goods_num();
                    if (j == 0) {
                        data1.get(i).getSpec().setCart_goods_num(cart_goods_num);
                    } else {
                        List<ShangPinYouBianBean.DataBeanX.DataBean.GoodsSpecBean> goods_spec = data1.get(i).getGoods_spec();

                        for (int k = 0; k < goods_spec.size(); k++) {
                            if (goods_spec.get(k).getSpec_id() == spec_id) {
                                goods_spec.get(k).setCart_goods_num(cart_goods_num);
                            }
                        }
                    }
                }
            }
        }

        notifyDataSetChanged();

    }


    //进度条
    private void dialog(String mm) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    private OnClicked onClicked;

    public void setOnClicked(OnClicked onClicked) {
        this.onClicked = onClicked;
    }

    public interface OnClicked {
        void getIntent(int pos, String goodsId);
    }


}