package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuBena;
import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class DianPuShouCangAdapter extends BaseAdapter {

    List<DianPuBena.DataBeanX.DataBean> data;
    Context context;

    public DianPuShouCangAdapter(List<DianPuBena.DataBeanX.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (null != data) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_dianpushoucang, null);
            viewHolder = new ViewHolder();
            viewHolder.store_name = (TextView) convertView.findViewById(R.id.store_name);
            viewHolder.class_names = (TextView) convertView.findViewById(R.id.class_names);
            viewHolder.delivery_radius = (TextView) convertView.findViewById(R.id.delivery_radius);
            viewHolder.min_pay_money = (TextView) convertView.findViewById(R.id.min_pay_money);
            viewHolder.company_license_num = (TextView) convertView.findViewById(R.id.company_license_num);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.store_logo);
            viewHolder.check = (ImageView) convertView.findViewById(R.id.check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.store_name.setText(data.get(position).getStore_name() + "");
        viewHolder.class_names.setText(data.get(position).getClass_names() + "");
        viewHolder.delivery_radius.setText(data.get(position).getDelivery_radius() + "");
        viewHolder.min_pay_money.setText(data.get(position).getMin_pay_money() + "");
        viewHolder.company_license_num.setText("已认证" + data.get(position).getCompany_license_num() + "证V");

        if (data.get(position).tag1) {
            viewHolder.check.setVisibility(View.VISIBLE);

        } else {
            viewHolder.check.setVisibility(View.GONE);

        }

        if (data.get(position).tag2) {
            viewHolder.check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);

        } else {
            viewHolder.check.setImageResource(R.drawable.xuanzhong_02);

        }

        Glide.with(context).load(data.get(position).getStore_logo()).into(viewHolder.image);
        return convertView;
    }

    class ViewHolder {
        public TextView store_name, class_names, delivery_radius, min_pay_money, company_license_num;
        public ImageView image, check;
    }


}