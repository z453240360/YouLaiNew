package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuXiangQingBean;
import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class GongYingShangLVAdapter extends BaseAdapter {


    public GongYingShangLVAdapter(List<DianPuXiangQingBean.DataBeanX.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    List<DianPuXiangQingBean.DataBeanX.DataBean> data;
    Context context;


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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_dianpushoucang, null);
            viewHolder = new ViewHolder();
            viewHolder.store_name = (TextView) convertView.findViewById(R.id.store_name);
            viewHolder.class_names = (TextView) convertView.findViewById(R.id.class_names);
            viewHolder.delivery_radius = (TextView) convertView.findViewById(R.id.delivery_radius);
            viewHolder.min_pay_money = (TextView) convertView.findViewById(R.id.min_pay_money);
            viewHolder.company_license_num = (TextView) convertView.findViewById(R.id.company_license_num);
            viewHolder.tv_juli = (TextView) convertView.findViewById(R.id.tv_juli);
            viewHolder.store_logo = (ImageView) convertView.findViewById(R.id.store_logo);

            viewHolder.iv1 = (ImageView) convertView.findViewById(R.id.iv1);
            viewHolder.iv2 = (ImageView) convertView.findViewById(R.id.iv2);
            viewHolder.iv3 = (ImageView) convertView.findViewById(R.id.iv3);
            viewHolder.iv4 = (ImageView) convertView.findViewById(R.id.iv4);
            viewHolder.iv5 = (ImageView) convertView.findViewById(R.id.iv5);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.store_name.setText(data.get(position).getStore_name());
        viewHolder.class_names.setText(data.get(position).getClass_names());
        viewHolder.delivery_radius.setText(data.get(position).getDelivery_radius());
        viewHolder.min_pay_money.setText(data.get(position).getMin_pay_money() + "");
        viewHolder.company_license_num.setText("已认证" + data.get(position).getCompany_license_num() + "证V");
        if (data.get(position).getDistance().equals("0")) {

            viewHolder.tv_juli.setText("");
        } else {
            viewHolder.tv_juli.setText(data.get(position).getDistance());

        }

        int evaluate_total = data.get(position).getEvaluate_total();
        switch (evaluate_total) {
            case 1:
                viewHolder.iv1.setVisibility(View.VISIBLE);
                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);
                viewHolder.iv5.setVisibility(View.INVISIBLE);
                break;
            case 2:
                viewHolder.iv1.setVisibility(View.VISIBLE);
                viewHolder.iv2.setVisibility(View.VISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);
                viewHolder.iv5.setVisibility(View.INVISIBLE);
                break;
            case 3:
                viewHolder.iv1.setVisibility(View.VISIBLE);
                viewHolder.iv2.setVisibility(View.VISIBLE);
                viewHolder.iv3.setVisibility(View.VISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);
                viewHolder.iv5.setVisibility(View.INVISIBLE);
                break;
            case 4:
                viewHolder.iv1.setVisibility(View.VISIBLE);
                viewHolder.iv2.setVisibility(View.VISIBLE);
                viewHolder.iv3.setVisibility(View.VISIBLE);
                viewHolder.iv4.setVisibility(View.VISIBLE);
                viewHolder.iv5.setVisibility(View.INVISIBLE);
                break;
            case 5:
                viewHolder.iv1.setVisibility(View.VISIBLE);
                viewHolder.iv2.setVisibility(View.VISIBLE);
                viewHolder.iv3.setVisibility(View.VISIBLE);
                viewHolder.iv4.setVisibility(View.VISIBLE);
                viewHolder.iv5.setVisibility(View.VISIBLE);
                break;

        }
        Glide.with(context)
                .load(data.get(position).getStore_logo())
                .into(viewHolder.store_logo);
        return convertView;
    }

    class ViewHolder {
        TextView store_name, class_names, delivery_radius, min_pay_money, company_license_num, tv_juli;
        ImageView store_logo, iv1, iv2, iv3, iv4, iv5;
    }
}