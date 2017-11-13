package com.bm.chengshiyoutian.youlaiwang.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.activity.DianPuShouYeActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.KuaiSuXiaDanActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBean;
import com.bm.chengshiyoutian.youlaiwang.view.MylistView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.DataForma_dd;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bm.chengshiyoutian.youlaiwang.R.id.min_pay_money;


/**
 * Created by sld on 2017/5/26.
 */

public class GouWuCheAdapter1 extends BaseAdapter {
    String Authorizatio;
    SharedPreferences sp;
    List<GouWuCheBean.DataBeanX.DataBean> datas;
    private Map<Integer, Integer> mapLocastCost = new HashMap<>();


    public GouWuCheAdapter1(String authorizatio, List<GouWuCheBean.DataBeanX.DataBean> datas, Context mContext) {
        Authorizatio = authorizatio;
        this.datas = datas;
        this.mContext = mContext;
        sp = mContext.getSharedPreferences(MyRes.CONFIG, 0);
    }


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

        double total = 0;
        final int x = position;
        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_gouwuche_title, null);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.money = (TextView) view.findViewById(R.id.money);
            viewHolder.check = (ImageView) view.findViewById(R.id.check);
            viewHolder.lv = (MylistView) view.findViewById(R.id.lv);
            viewHolder.minPay = (TextView) view.findViewById(R.id.money_minpay);

            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, KuaiSuXiaDanActivity.class);
                intent.putExtra(MyRes.DIANPU_ID, datas.get(position).getStore_id() + "");
                intent.putExtra("token", sp.getString(MyRes.TOKEN, ""));
                sp.edit().putString(MyRes.DIANPU_ID, datas.get(position).getStore_id() + "").commit();

                mContext.startActivity(intent);

            }
        });

        if (datas.get(position).tag) {
            viewHolder.check.setImageResource(R.mipmap.shezhi_xuanzhhong);
        } else {
            viewHolder.check.setImageResource(R.mipmap.shezhi_weixuanzhong);
        }

        viewHolder.name.setText(datas.get(position).getStore_name());
        viewHolder.minPay.setText("满:¥" + datas.get(position).getMin_pay_money() + "免邮费");

        final List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> goods = datas.get(position).getGoods();
        final GouWuCheAdapter2 gouWuCheAdapter2 = new GouWuCheAdapter2(goods, mContext, Authorizatio);
        viewHolder.lv.setAdapter(gouWuCheAdapter2);

        gouWuCheAdapter2.setOnCheckListener(new GouWuCheAdapter2.OnCheckListener() {
            @Override
            public void onCheck(int pos) {
                goods.get(pos).tag = !goods.get(pos).tag;
                gouWuCheAdapter2.notifyDataSetChanged();
                for (int i = 0; i < goods.size(); i++) {
                    if (!goods.get(i).tag) {
                        datas.get(x).tag = false;
                        String json = "选中";
                        EventBus.getDefault().post(json);
                        notifyDataSetChanged();
                        break;
                    } else {
                        datas.get(x).tag = true;
                        notifyDataSetChanged();
                        String json = "选中";
                        EventBus.getDefault().post(json);
                    }
                }
            }
        });




        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double money = 0;
                String ids = null;
                datas.get(x).tag = !datas.get(x).tag;

                for (int i = 0; i < goods.size(); i++) {
                    goods.get(i).tag = datas.get(x).tag;
                    if (goods.get(i).tag) {
                        money = money + goods.get(i).getPrice() * goods.get(i).getGoods_num();
                        ids = ids + "," + goods.get(i).getGoods_id();
                    }
                }

                SharedPreferences sharedPreferences = mContext.getSharedPreferences(MyRes.CONFIG, 0);
                sharedPreferences.edit().putString("money", money + "").commit();
                sharedPreferences.edit().putString(MyRes.GoodsId, ids + "").commit();
                EventBus.getDefault().post("money");
                notifyDataSetChanged();
            }
        });


        for (int i = 0; i < goods.size(); i++) {
            total += goods.get(i).getPrice() * goods.get(i).getGoods_num();
        }


        if (gouWuCheAdapter2.getTotalP() >= datas.get(position).getMin_pay_money()) {
            viewHolder.money.setText("免邮费");
            viewHolder.money.setTextColor(Color.parseColor("#0bb04a"));
        } else {
            viewHolder.money.setText("邮费:¥ " + datas.get(position).getLogistics_cost());
            viewHolder.money.setTextColor(Color.parseColor("#555555"));
        }

        return view;

    }

    //获取列表的总价格，待返回给主界面
    public String getTotalPrice() {
        double price = 0;

        for (int i = 0; i < datas.size(); i++) {
            for (int j = 0; j < datas.get(i).getGoods().size(); j++) {
                if (datas.get(i).getGoods().get(j).tag) {
                    double childTotalPrice = datas.get(i).getGoods().get(j).getGoods_num() * datas.get(i).getGoods().get(j).getPrice();
                    price = price + childTotalPrice;
                }
            }
        }

        String doubleByDouble = DataForma_dd.getDoubleByDouble(price);

        return doubleByDouble;
    }

    public class ViewHolder {
        public TextView name, money, minPay;
        MylistView lv;
        ImageView check;
    }


    private CallBack back;

    public GouWuCheAdapter1(CallBack back) {
        this.back = back;
    }

    public interface CallBack{
        void getTotaPrice(double s);
    }

}
