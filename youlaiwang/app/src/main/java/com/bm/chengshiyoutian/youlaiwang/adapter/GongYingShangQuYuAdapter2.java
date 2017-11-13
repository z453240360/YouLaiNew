package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.GongYingShangQuYuBean;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class GongYingShangQuYuAdapter2 extends BaseAdapter {


    public GongYingShangQuYuAdapter2(List<GongYingShangQuYuBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    List<GongYingShangQuYuBean.DataBean> data;
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
            convertView = View.inflate(context, R.layout.item_dianjiaquyu2, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            viewHolder.iv_check = (ImageView) convertView.findViewById(R.id.iv_check);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(data.get(position).getArea_name());
        if (data.get(position).tag ) {
            viewHolder.title.setTextColor(Color.rgb(118,201,51));
            viewHolder.ll.setBackgroundColor(0xffffffff);
            viewHolder.iv_check.setVisibility(View.VISIBLE);
        } else {
            viewHolder.title.setTextColor(0xff8c8c8c);
            viewHolder.ll.setBackgroundColor(0xfff5f5f5);
        }

        return convertView;
    }

    class ViewHolder {
        public TextView title;
        LinearLayout ll;
        ImageView iv_check;

    }


}