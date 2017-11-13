package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinFenLeiBena;
import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by Administrator on 2017/5/22.
 */

public class ShangPinLieBiaoGVAdapter extends BaseAdapter {
    Context context;

    public ShangPinLieBiaoGVAdapter(Context context, List<ShangPinFenLeiBena.DataBean.GoodsClassTwoBean> goods_class_two) {
        this.context = context;
        this.goods_class_two = goods_class_two;
    }

    List<ShangPinFenLeiBena.DataBean.GoodsClassTwoBean> goods_class_two;


    public int getCount() {
        return goods_class_two.size();
    }

    @Override
    public Object getItem(int position) {
        return goods_class_two.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shangpinliebiao_gv, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.tv_name.setText(goods_class_two.get(position).getGc_name());
        Glide.with(context)
                .load(goods_class_two.get(position).getImage())
                .into(viewHolder.imageView);
        return convertView;
    }

    class ViewHolder {
        public TextView tv_name;
        ImageView imageView;

    }

}
