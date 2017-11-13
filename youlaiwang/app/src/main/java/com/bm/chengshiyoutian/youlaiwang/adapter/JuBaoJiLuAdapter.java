package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.JuBaoJiLuBean;

import java.util.List;




/**
 * Created by MingweiLi on 2017/4/5.
 */

public class JuBaoJiLuAdapter extends BaseAdapter {
    List<JuBaoJiLuBean.DataBeanX.DataBean> data;
    // 定义Context


    public JuBaoJiLuAdapter(List<JuBaoJiLuBean.DataBeanX.DataBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    private Context mContext;



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
    public View getView(int position, View convertView, ViewGroup parent)

    {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_jubaojilu, null);

            viewHolder = new ViewHolder();

            viewHolder.textView1= (TextView) view.findViewById(R.id.tv1);
            viewHolder.textView2= (TextView) view.findViewById(R.id.tv2);

            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }
        viewHolder.textView2.setText(data.get(position).getFormat_report_time());
        viewHolder.textView1.setText(data.get(position).getReport_content());
       // viewHolder.textView3.setText("+5");
        return view;

    }

    class ViewHolder {
        TextView textView1,textView2;

    }
}
