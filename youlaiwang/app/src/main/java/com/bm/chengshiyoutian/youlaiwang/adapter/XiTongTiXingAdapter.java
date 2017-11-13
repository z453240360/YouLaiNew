package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.XiTongTiXingBean;

import java.util.List;




/**
 * Created by MingweiLi on 2017/4/5.
 */

public class XiTongTiXingAdapter extends BaseAdapter {
    List<XiTongTiXingBean.DataBeanX.DataBean> data;
    // 定义Context

    int is_read = 1;
    private Context mContext;

    public XiTongTiXingAdapter(List<XiTongTiXingBean.DataBeanX.DataBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

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
            view = inflater.inflate(R.layout.item_xitongtixing, null);

            viewHolder = new ViewHolder();

            viewHolder.textView1= (TextView) view.findViewById(R.id.tv1);
            viewHolder.textView2= (TextView) view.findViewById(R.id.tv2);
            viewHolder.textView3= (TextView) view.findViewById(R.id.tv3);
            viewHolder.iv_tixing = (ImageView) view.findViewById(R.id.iv_tixing);
            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }
       viewHolder.textView1.setText(data.get(position).getTitle());
        viewHolder.textView2.setText(data.get(position).getContent());
        viewHolder.textView3.setText(data.get(position).getFormat_addtime());
        is_read = data.get(position).getIs_read();
        if(is_read == 2){
            viewHolder.iv_tixing.setBackgroundResource(R.drawable.yidu);
        }else {
            viewHolder.iv_tixing.setBackgroundResource(R.drawable.weidu);
        }
        return view;

    }

    class ViewHolder {
        TextView textView1,textView2,textView3;
        ImageView iv_tixing;

    }
}
