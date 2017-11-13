package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.GlideCircleTransform;
import com.bm.chengshiyoutian.youlaiwang.bean.XiangXing3Bean;
import com.bm.chengshiyoutian.youlaiwang.view.MyGridView;
import com.bumptech.glide.Glide;

import java.util.List;




/**
 * Created by MingweiLi on 2017/4/5.
 */

public class PingJiaAdapter extends BaseAdapter {
    List<XiangXing3Bean.DataBeanX.DataBean> data;

    public PingJiaAdapter(List<XiangXing3Bean.DataBeanX.DataBean> data, Context mContext) {
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
    public View getView(final int position, View convertView, ViewGroup parent)

    {

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            convertView = inflater.inflate(R.layout.item_pingjia_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.gv = (MyGridView) convertView.findViewById(R.id.gv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_content.setText(data.get(position).getComment_content());
        viewHolder.tv_name.setText(data.get(position).getUser_nicename());
        viewHolder.tv_time.setText(data.get(position).getComment_addtime());
        Glide.with(mContext)
                .load(data.get(position).getAvatar()).transform(new GlideCircleTransform(mContext)).into(viewHolder.iv);
        List<String> comment_images = data.get(position).getComment_images();
        viewHolder.gv.setAdapter(new PingJiaGVAdapter(comment_images, mContext));
        return convertView;

    }

    public class ViewHolder {
        ImageView iv;
        TextView tv_content, tv_time, tv_name;
        MyGridView gv;
    }
}
