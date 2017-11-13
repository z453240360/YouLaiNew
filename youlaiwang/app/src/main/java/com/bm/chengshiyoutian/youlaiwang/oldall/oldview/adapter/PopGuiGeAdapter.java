package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;


/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class PopGuiGeAdapter extends BaseAdapter {
    private Context context ;
    private String[] data ;
    public PopGuiGeAdapter(Context context, String[] data){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pop_guige, null);
        TextView tv_guige_pop = (TextView) view.findViewById(R.id.tv_guige_pop);
        tv_guige_pop.setText(data[position]);
        return view;
    }
}
