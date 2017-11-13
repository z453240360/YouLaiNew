package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class ShangPinYiGouMaiAdapter extends BaseAdapter {

    List<ShangPinBean.DataBeanX.DataBean> data;
    Context context;


    public ShangPinYiGouMaiAdapter(List<ShangPinBean.DataBeanX.DataBean> data, Context context) {
        this.data = data;
        this.context = context;

    }

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
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shangpinshoucang, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
            viewHolder.tv4 = (TextView) convertView.findViewById(R.id.tv4);
            viewHolder.check = (ImageView) convertView.findViewById(R.id.check);
            viewHolder.image = (RelativeLayout) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (data.get(position).tag1) {
            viewHolder.check.setVisibility(View.VISIBLE);

        } else {
            viewHolder.check.setVisibility(View.GONE);

        }

                if (data.get(position).tag2) {
                    viewHolder.check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);

                } else {

                    viewHolder.check.setImageResource(R.drawable.xuanzhong_02);
                }



        viewHolder.title.setText(data.get(position).getGoods_name());
        viewHolder.tv3.setText(data.get(position).getGoods_price()+"");
        viewHolder.tv4.setText(data.get(position).getRatio());
        Glide.with(context).load(data.get(position).getGoods_cover()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);


                viewHolder.image.setBackgroundDrawable(drawable);
            }
        });


        int width = viewHolder.image.getLayoutParams().width;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        convertView.setLayoutParams(layoutParams);
        return convertView;
    }

    class ViewHolder {
        public TextView title, tv3, tv4;
        public RelativeLayout image;
        ImageView check;
    }


}