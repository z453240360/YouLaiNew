package com.bm.chengshiyoutian.youlaiwang.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.activity.DianPuShouYeActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.DingDanXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.DingDan1Bean;
import com.bm.chengshiyoutian.youlaiwang.view.MylistView;

import java.util.List;


/**
 * Created by sld on 2017/5/26.
 */

public class DingDan2Adapter1 extends BaseAdapter {
    String Authorizatio;

    public DingDan2Adapter1(String authorizatio, List<DingDan1Bean.DataBeanX.DataBean> datas, Context mContext) {
        Authorizatio = authorizatio;
        this.datas = datas;
        this.mContext = mContext;
    }

    List<DingDan1Bean.DataBeanX.DataBean> datas;

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


        final int x = position;
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_dingdan2, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.tv_xiangqing = (TextView) view.findViewById(R.id.tv_xiangqing);
            viewHolder.tv_money = (TextView) view.findViewById(R.id.tv_money);
            viewHolder.tv_num = (TextView) view.findViewById(R.id.tv_num);
            viewHolder.name = (TextView) view.findViewById(R.id.name);

            viewHolder.lv = (MylistView) view.findViewById(R.id.lv);
            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DianPuShouYeActivity.class);
                intent.putExtra(MyRes.DIANPU_ID, datas.get(position).getShop_id() + "");
                mContext.startActivity(intent);
            }
        });

        viewHolder.name.setText(datas.get(position).getShop_name());
        viewHolder.tv_num.setText("共" + datas.get(position).getNum() + "件商品");
        viewHolder.tv_money.setText(datas.get(position).getOrder_amount() + "");
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
        public TextView name, tv_num, tv_money, tv_xiangqing;
        MylistView lv;


    }


}
