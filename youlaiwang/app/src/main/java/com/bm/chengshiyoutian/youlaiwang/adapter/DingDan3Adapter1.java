package com.bm.chengshiyoutian.youlaiwang.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.DianPuShouYeActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.DingDanXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.KuaiSuXiaDanActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ZhiFuFangShiActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.CodeBean;
import com.bm.chengshiyoutian.youlaiwang.bean.DingDan1Bean;
import com.bm.chengshiyoutian.youlaiwang.view.MylistView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.bm.chengshiyoutian.youlaiwang.R.id.tv;


/**
 * Created by sld on 2017/5/26.
 */

public class DingDan3Adapter1 extends BaseAdapter {
    String Authorizatio;
    SharedPreferences sp;
    private OkHttpClient mOkHttpClient;

    public DingDan3Adapter1(String authorizatio, List<DingDan1Bean.DataBeanX.DataBean> datas, Context context, Activity mActivity) {
        Authorizatio = authorizatio;
        this.datas = datas;
        this.mContext = context;
        sp = context.getSharedPreferences(MyRes.CONFIG, 0);
        this.mActivity = mActivity;
    }

    List<DingDan1Bean.DataBeanX.DataBean> datas;

    public void setDatas(List<DingDan1Bean.DataBeanX.DataBean> datas) {
        this.datas = datas;
    }

    private Context mContext;
    private Activity mActivity;

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


        final int x = position;
        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_dingdan3, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.tv_xiangqing = (TextView) view.findViewById(R.id.tv_xiangqing);
            viewHolder.money = (TextView) view.findViewById(R.id.money);

            viewHolder.tv_money = (TextView) view.findViewById(R.id.tv_money);
            viewHolder.tv_num = (TextView) view.findViewById(R.id.tv_num);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.tv_ok = (TextView) view.findViewById(R.id.tv_ok);
            viewHolder.lv = (MylistView) view.findViewById(R.id.lv);
            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        int is_payment = datas.get(position).getIs_payment();
        if (is_payment == 1) {//未付款
            viewHolder.tv_ok.setText("付款");
            viewHolder.tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, ZhiFuFangShiActivity.class);
                    mIntent.putExtra("orderId", datas.get(position).getOrder_id() + "");
                    String money = viewHolder.tv_money.getText().toString();
                    mIntent.putExtra("money", money);
                    mContext.startActivity(mIntent);
                }
            });


        } else if (is_payment == 2) {//已付款
            viewHolder.tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOkHttpClient = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("orderId", datas.get(position).getOrder_id() + "")
                            .add("_method", "patch")
                            .build();
                    Request request = new Request.Builder()

                            .url(MyRes.BASE_URL + "api/order/receipt")
                            .addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""))
                            .post(formBody)
                            .build();
                    Call call = mOkHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, okhttp3.Response response) throws IOException {
                            String json = response.body().string().toString();
                            if (json != null) {
                                final CodeBean codeBean = GsonUtils.getInstance().fromJson(json, CodeBean.class);
                                if (codeBean.code == 200) {
                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            datas.remove(position);
                                            notifyDataSetChanged();
                                            ShowToast.showToast(codeBean.msg);
                                        }
                                    });

                                } else {
                                    ShowToast.showToast(codeBean.msg);
                                }
                            }

                        }

                    });

                }
            });
        }


        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, KuaiSuXiaDanActivity.class);
                sp.edit().putString(MyRes.DIANPU_ID, datas.get(position).getShop_id() + "").commit();
                intent.putExtra(MyRes.DIANPU_ID, datas.get(position).getShop_id() + "");
                mContext.startActivity(intent);
            }
        });

        viewHolder.name.setText(datas.get(position).getShop_name());
        viewHolder.tv_num.setText("共" + datas.get(position).getNum() + "件商品");
        viewHolder.tv_money.setText(datas.get(position).getOrder_amount() + "");
        viewHolder.money.setText(datas.get(position).getFormat_order_state());
        List<DingDan1Bean.DataBeanX.DataBean.OrderGoodsBean> order_goods = datas.get(position).getOrder_goods();
        DingDan1Adapter2 dingDan1Adapter2 = new DingDan1Adapter2(order_goods, mContext, Authorizatio);
        viewHolder.lv.setAdapter(dingDan1Adapter2);
        final int y = position;
        viewHolder.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, DingDanXiangQingActivity.class);
                intent.putExtra("id", datas.get(y).getOrder_id() + "");
                mContext.startActivity(intent);
            }
        });

        viewHolder.tv_xiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DingDanXiangQingActivity.class);
                intent.putExtra("id", datas.get(y).getOrder_id() + "");
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    public class ViewHolder {

      public TextView name, tv_num, tv_money, tv_ok, money,tv_xiangqing;
        MylistView lv;


    }


}
