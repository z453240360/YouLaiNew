package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.QuYuXuanZeBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class QuYuNewAdapter1 extends BaseAdapter {


    public QuYuNewAdapter1(List<QuYuXuanZeBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    List<QuYuXuanZeBean.DataBean> data;
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
            convertView = View.inflate(context, R.layout.item_quyu_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_check);
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv1.setText(data.get(position).getArea_name());
        if (data.get(position).tag) {
            viewHolder.tv1.setTextColor(Color.parseColor("#76c933"));
            viewHolder.ll.setBackgroundColor(0xffffffff);
        } else {
            viewHolder.tv1.setTextColor(0xff333333);
            viewHolder.ll.setBackgroundColor(0xfff1f1f1);
        }

        if (SPUtil.getString(context, Constants.AREAID)!=null&&SPUtil.getString(context, Constants.AREAID).equals(data.get(position).getArea_id()+"")) {
            viewHolder.tv1.setTextColor(Color.parseColor("#76c933"));
            viewHolder.ll.setBackgroundColor(0xffffffff);
        }

        return convertView;
    }

    class ViewHolder {
        public TextView tv1;
        ImageView imageView;

        LinearLayout ll;
    }


}