package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinZuoBianBean;

import java.util.List;



/**
 * Created by sld on 2017/5/18.
 */

public class ShangPinRCAdapter extends RecyclerView.Adapter<ShangPinRCAdapter.MyViewHolder> {

    private OnItemClickListener mOnItemClickListener = null;
    Context context;
    List<ShangPinZuoBianBean.DataBean> data;

    public ShangPinRCAdapter(Context context, List<ShangPinZuoBianBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_shangpinsousuo, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv1.setText(data.get(position).getGc_name());
        holder.itemView.setTag(position);
        if (data.get(position).tag) {
            holder.view.setBackgroundColor(0xffffffff);
            holder.ll2.setVisibility(View.VISIBLE);
            holder.tv1.setTextColor(Color.rgb(118,201,51));
        } else {

            holder.view.setBackgroundColor(0xfff2f2f2);
            holder.ll2.setVisibility(View.GONE);
            holder.tv1.setTextColor(0xff444444);
        }


    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tv1;
        LinearLayout ll1, ll2;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv1 = (TextView) itemView.findViewById(R.id.title);
            ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);
            ll2 = (LinearLayout) itemView.findViewById(R.id.ll2);


        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
