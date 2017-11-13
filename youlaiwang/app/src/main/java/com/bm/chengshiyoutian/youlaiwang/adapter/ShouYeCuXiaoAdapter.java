package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinBean;
import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class ShouYeCuXiaoAdapter extends BaseAdapter {


    List<ShangPinBean.DataBeanX.DataBean> data;

    public ShouYeCuXiaoAdapter(List<ShangPinBean.DataBeanX.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

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
            convertView = View.inflate(context, R.layout.item_weizhi, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(data.get(position).getGoods_name());
        viewHolder.tv2.setText(data.get(position).getGoods_price()+"");
        viewHolder.tv3.setText(data.get(position).getRatio());
        Glide.with(context).load(data.get(position).getGoods_cover()).into(viewHolder.image);
        return convertView;
    }

    class ViewHolder {
        public TextView title, tv2, tv3;
        public ImageView image;
    }


}