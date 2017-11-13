package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuShouYeCuXiaoBean;
import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class DianPuChuXiaoAdapter extends BaseAdapter {

    List<DianPuShouYeCuXiaoBean.DataBeanX.DataBean> datas;
    Context context;
    int width;

    public DianPuChuXiaoAdapter(List<DianPuShouYeCuXiaoBean.DataBeanX.DataBean> datas, Context context, int width) {
        this.datas = datas;
        this.context = context;
        this.width = width;
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
            convertView = View.inflate(context, R.layout.item_cuxiao1, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
            viewHolder.tv4 = (TextView) convertView.findViewById(R.id.tv4);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(datas.get(position).getGoods_name());
        viewHolder.tv3.setText("¥"+datas.get(position).getGoods_price());
        viewHolder.tv4.setText(datas.get(position).getRatio());


      //  ViewGroup.LayoutParams layoutParams = viewHolder.image.getLayoutParams();
       // int i = width - CommonUtils.dip2px(context, 8);

       // layoutParams.height = i / 2;

    //    viewHolder.image.setLayoutParams(layoutParams);
        Glide.with(context)
                .load(datas.get(position).getGoods_cover()) .fitCenter() .into( viewHolder.image);

        return convertView;
    }

    class ViewHolder {
        public TextView title,tv3,tv4;
        public ImageView image;
    }


}