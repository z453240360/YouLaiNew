package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sld on 2017/5/18.
 */

public class PingJiaRCAdapter extends RecyclerView.Adapter<PingJiaRCAdapter.MyViewHolder> {

    private OnItemClickListener mOnItemClickListener = null;
    Context context;
    List<String> company_license;

    public PingJiaRCAdapter(Context context, List<String> company_license) {
        this.context = context;
        this.company_license = company_license;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_qiyerenzheng, null);
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

        holder.itemView.setTag(position);

        try {
            Glide.with(context)
                    .load(company_license.get(position)).centerCrop()
                    .into(holder.imageView);
        } catch (Exception e) {
            Glide.with(context)
                    .load(R.drawable.pingjia).centerCrop()
                    .into(holder.imageView);
        }


    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return company_license.size()+1;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = (ImageView) view.findViewById(R.id.iv);


        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
