package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.QuYuXuanZeBean;

import java.util.ArrayList;



/**
 * Created by sld on 2017/5/12.
 */

public class QuYuXuanZeAdapter extends BaseAdapter {


    ArrayList<QuYuXuanZeBean.DataBean> datas;
    Context context;

    public QuYuXuanZeAdapter(ArrayList<QuYuXuanZeBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (null != datas) {
            return datas.size();
        } else {
            return 0;
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_quyu, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       viewHolder.title.setText(datas.get(position).getArea_name());

        return convertView;
    }

    class ViewHolder {
        public TextView title;

    }


}