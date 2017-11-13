package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by sld on 2017/5/12.
 */

public class FenLeiGVAdapter extends BaseAdapter {

    public FenLeiGVAdapter(ArrayList<String> ints, ArrayList<String> datas, ArrayList<Boolean> tags, Context context) {
        this.ints = ints;
        this.datas = datas;
        this.tags=tags;
        this.context = context;
    }
    ArrayList<Boolean> tags;
    ArrayList<String> ints ;
    ArrayList<String> datas;
    Context context;


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
            convertView = View.inflate(context, R.layout.item_fen_lei, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

       viewHolder.title.setText(datas.get(position));
        if (tags.get(position)){
            viewHolder.ll.setBackgroundResource(R.drawable.fenleibg2);
            viewHolder.title.setTextColor(Color.rgb(118,201,51));

        }else {
            viewHolder.ll.setBackgroundResource(R.drawable.fenleibg1);
            viewHolder.title.setTextColor(0xff222222);
        }
        Glide.with(context).load(ints.get(position)).into(viewHolder.iv);
        return convertView;
    }

    class ViewHolder {
        public TextView title;
        ImageView iv;
        LinearLayout ll;
    }


}