package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.bean.QuYuXuanZeBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class QuYuNewAdapter2 extends BaseAdapter {

    SharedPreferences sp;

    public QuYuNewAdapter2(List<QuYuXuanZeBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
        sp = context.getSharedPreferences(MyRes.CONFIG, 0);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_quyu_listview2, null);
            viewHolder = new ViewHolder();
            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_check);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv1.setText(data.get(position).getArea_name());
        if (data.get(position).tag) {
            viewHolder.tv1.setTextColor(Color.rgb(118,201,51));
            viewHolder.imageView.setVisibility(View.VISIBLE);

        } else {
            viewHolder.imageView.setVisibility(View.INVISIBLE);
            viewHolder.tv1.setTextColor(0xff333333);

        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* sp.edit().putString(MyRes.city2, data.get(position).getArea_name()).commit();
                sp.edit().putString(MyRes.area_id, data.get(position).getArea_id() + "").commit();

                EventBus.getDefault().post("queding");*/
            }
        });
        if (SPUtil.getString(context, Constants.AREAID2) != null && SPUtil.getString(context, Constants.AREAID2).equals(data.get(position).getArea_id() + "")) {
            viewHolder.tv1.setTextColor(Color.rgb(118,201,51));
            viewHolder.imageView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        public TextView tv1;
        ImageView imageView;


    }


}