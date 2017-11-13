package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.CuXiaoBean1;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class CuXiaoAdapternew extends BaseAdapter {

    List<CuXiaoBean1.DataBeanX.DataBean> data;
    Context context;


    public CuXiaoAdapternew(List<CuXiaoBean1.DataBeanX.DataBean> data, Context context) {
        this.data = data;
        this.context = context;

    }

    @Override
    public int getCount() {
        if (null != data) {

            if (data.size()>4)
            {
                return 4;
            }
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
            convertView = View.inflate(context, R.layout.item_cuxiaonew, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
            viewHolder.tv4 = (TextView) convertView.findViewById(R.id.tv4);
            viewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(data.get(position).goods_name);
       viewHolder.tv3.setText("¥"+data.get(position).goods_price);
        viewHolder.tv4.setText(data.get(position).ratio);
        Uri uri = Uri.parse(data.get(position).goods_cover);
        viewHolder.image.setImageURI(uri);
        return convertView;
    }

    class ViewHolder {
        public TextView title, tv3, tv4;
        public SimpleDraweeView image;
    }


}