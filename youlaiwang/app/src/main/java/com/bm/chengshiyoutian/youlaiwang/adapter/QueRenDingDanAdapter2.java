package com.bm.chengshiyoutian.youlaiwang.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.QueRenDingDanBena1;

import java.text.DecimalFormat;
import java.util.List;



/**
 * Created by sld on 2017/6/1.
 */

public class QueRenDingDanAdapter2 extends BaseAdapter {
    Context context;
    List<QueRenDingDanBena1.DataBean.GoodsDataBean.GoodsBean> goods_two;

    public QueRenDingDanAdapter2(Context context, List<QueRenDingDanBena1.DataBean.GoodsDataBean.GoodsBean> goods_two) {
        this.context = context;
        this.goods_two = goods_two;
    }

    @Override
    public int getCount() {
        return goods_two.size();

    }

    @Override
    public Object getItem(int position) {
        return goods_two.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_querendingdan2, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_pir = (TextView) convertView.findViewById(R.id.tv_pir);
            viewHolder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);
            viewHolder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(goods_two.get(position).getGoods_name());
        DecimalFormat df   = new DecimalFormat("######0.00");

        viewHolder.tv_pir.setText(goods_two.get(position).getPrice() + "");

        viewHolder.tv_danwei.setText(goods_two.get(position).getSpec_text());

        viewHolder.tv_count.setText(goods_two.get(position).getGoods_num()+"");

        return convertView;
    }

    class ViewHolder {

        public TextView tv_name, tv_pir, tv_danwei, tv_count;

    }
}


