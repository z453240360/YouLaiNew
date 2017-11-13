package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.Car_dd_Bean;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {


    private Context context;
    private List<Car_dd_Bean.DataBeanX.DataBean.GoodsSpecBean> goods_spec=new ArrayList<>();
    @Override
    public int getCount() {
        return goods_spec.size();
    }

    @Override
    public Object getItem(int i) {
        return goods_spec.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }


    class MyHolder{
        ImageView add,reduce;
        TextView txtNumber;

    }
}
