package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.SouSuoJiLuBean;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class SouSuoJiLuAdapter extends BaseAdapter {


    List<SouSuoJiLuBean.DataBean> data_jilu;

    Context context;

    public SouSuoJiLuAdapter(  List<SouSuoJiLuBean.DataBean> data_jilu, Context context) {
        this.data_jilu = data_jilu;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (null != data_jilu) {
            return data_jilu.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return data_jilu.get(position);
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
       viewHolder.title.setText(data_jilu.get(position).getSh_name());

        return convertView;
    }

    class ViewHolder {
        public TextView title;

    }


}