package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sld on 2017/5/12.
 */

public class PingJiaGVAdapter extends BaseAdapter {
    List<String> comment_images;

    Context context;

    public PingJiaGVAdapter(List<String> comment_images, Context context) {
        this.comment_images = comment_images;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (null != comment_images) {
            return comment_images.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return comment_images.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_iv, null);
            viewHolder = new ViewHolder();

            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Glide.with(context).load(comment_images.get(position)).into(viewHolder.iv);
        return convertView;
    }

    class ViewHolder {

        ImageView iv;


    }


}