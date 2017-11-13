package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;

import java.util.ArrayList;


/**
 * Created by MingweiLi on 2017/4/5.
 */

public class DingDanDaiFuKuanAdapter extends BaseAdapter {
    ArrayList<String> datas;
    // 定义Context


    private Context mContext;

    public DingDanDaiFuKuanAdapter(ArrayList<String> datas, Activity mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return datas.size();
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
    public View getView(int position, View convertView, ViewGroup parent)

    {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_daifukuan, null);
            viewHolder = new ViewHolder();
            viewHolder.tv1 = (TextView) view.findViewById(R.id.tv1);
            viewHolder.tv2 = (TextView) view.findViewById(R.id.tv2);
            viewHolder.tv3 = (TextView) view.findViewById(R.id.tv3);
            viewHolder.tv4 = (TextView) view.findViewById(R.id.tv4);
            viewHolder.tv5 = (TextView) view.findViewById(R.id.tv5);
            viewHolder.tv6 = (TextView) view.findViewById(R.id.tv6);
            viewHolder.btn1 = (Button) view.findViewById(R.id.btn1);
            viewHolder.btn2 = (Button) view.findViewById(R.id.btn2);
            viewHolder.iv = (ImageView) view.findViewById(R.id.iv);
            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }
        // viewHolder.textView1.setText("2016-10-10  12:30:30");
        // viewHolder.textView2.setText("注册");
        // viewHolder.textView3.setText("+5");
        return view;

    }

    public class ViewHolder {

        public TextView tv1, tv2, tv3, tv4, tv5, tv6;
        Button btn1, btn2;
        ImageView iv;

    }
}
