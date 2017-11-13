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

import org.greenrobot.eventbus.EventBus;

import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class ShangPinShouCangAdapter extends BaseAdapter {

    List<ShangPinBean.DataBeanX.DataBean> data;
    Context context;


    public ShangPinShouCangAdapter(List<ShangPinBean.DataBeanX.DataBean> data, Context context) {
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
//        final ViewHolder viewHolder;
//        if (convertView == null) {
//            convertView = View.inflate(context, R.layout.item_shangpinshoucang, null);
//            viewHolder = new ViewHolder();
//            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
//            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
//            viewHolder.tv4 = (TextView) convertView.findViewById(R.id.tv4);
//            viewHolder.check = (ImageView) convertView.findViewById(R.id.check);
//            viewHolder.image = (RelativeLayout) convertView.findViewById(R.id.image);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        View view = View.inflate(context, R.layout.item_shangpinshoucang, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView tv3 = (TextView) view.findViewById(R.id.tv3);
        TextView tv4 = (TextView) view.findViewById(R.id.tv4);
        final ImageView check = (ImageView) view.findViewById(R.id.check);
        final RelativeLayout image = (RelativeLayout) view.findViewById(R.id.image);

        if (data.get(position).tag1) {
            check.setVisibility(View.VISIBLE);

        } else {
            check.setVisibility(View.GONE);
        }

        if (data.get(position).tag2) {

            check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
        } else {

            check.setImageResource(R.drawable.xuanzhong_02);
        }

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).tag2 = !data.get(position).tag2;
                EventBus.getDefault().post("shoucang");
                if (data.get(position).tag2) {
                    check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);

                } else {

                    check.setImageResource(R.drawable.xuanzhong_02);
                }
            }
        });


        title.setText(data.get(position).getGoods_name());
        tv3.setText("¥ "+data.get(position).getGoods_price()+"");
        tv4.setText(data.get(position).getRatio());
        Glide.with(context).load(data.get(position).getGoods_cover()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);

                image.setBackgroundDrawable(drawable);
            }
        });


        int width = image.getLayoutParams().width;
        Log.d("width11", width + "");
        Log.d("width11", image.getLayoutParams().height + "");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        view.setLayoutParams(layoutParams);

        return view;
    }

    class ViewHolder {
        public TextView title, tv3, tv4;
        public RelativeLayout image;
        ImageView check;
    }


}