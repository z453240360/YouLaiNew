package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.LieBiaoBean;
import com.bumptech.glide.Glide;

import java.util.List;




/**
 * Created by MingweiLi on 2017/4/5.
 */

public class WoDeLVAdapter extends BaseAdapter {
    List<LieBiaoBean.DataBeanX.DataBean> datas;
    // 定义Context


    private Context mContext;

    public WoDeLVAdapter(List<LieBiaoBean.DataBeanX.DataBean> datas, Context mContext) {
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
            view = inflater.inflate(R.layout.item_wode, null);

            viewHolder = new ViewHolder();

            viewHolder.textView1= (TextView) view.findViewById(R.id.tv_content);
            viewHolder.textView2= (TextView) view.findViewById(R.id.tv_time);
            viewHolder.textView3= (TextView) view.findViewById(R.id.tv_count);
            viewHolder.iv= (ImageView) view.findViewById(R.id.iv);
            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }
       viewHolder.textView1.setText(datas.get(position).getArticle_title());
       viewHolder.textView2.setText(datas.get(position).getArticle_time());
       viewHolder.textView3.setText(datas.get(position).getArticle_see_num()+"");
        Glide.with(mContext)
                .load(datas.get(position).getArticle_cover()).fitCenter()
                .into(viewHolder.iv);
        return view;

    }

    class ViewHolder {
        TextView textView1,textView2,textView3;
        ImageView  iv;
    }
}
