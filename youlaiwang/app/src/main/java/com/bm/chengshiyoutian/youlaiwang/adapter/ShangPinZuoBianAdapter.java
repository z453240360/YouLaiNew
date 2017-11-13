package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinZuoBianBean;

import java.util.List;



/**
 * Created by sld on 2017/6/5.
 */

public class ShangPinZuoBianAdapter extends BaseAdapter {
    Context context;
     List<ShangPinZuoBianBean.DataBean> data;

    public ShangPinZuoBianAdapter(Context context, List<ShangPinZuoBianBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override

    public int getCount() {
        return data.size();
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
        if (convertView==null){
            convertView=View.inflate(context, R.layout.item_shangpinsousuo, null);

        }
        return convertView;
    }
}
