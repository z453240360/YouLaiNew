package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class FenLeiAdapter extends RecyclerView.Adapter<FenLeiAdapter.MyHolder>{
    private List<String> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;


    public FenLeiAdapter(List<String> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_fenlei, parent, false);
        MyHolder myViewHolder = new MyHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.onItemClick(position,holder.itemView);
                }
            }
        });
        holder.fenlei.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class MyHolder extends XRecyclerView.ViewHolder{

        TextView fenlei;
        public MyHolder(View itemView) {
            super(itemView);
            fenlei = (TextView) itemView.findViewById(R.id.mTxt_fenlei);
        }
    }

    /**
     * 点击事件的接口回调
     *
     */

    public interface OnItemClickListener {
        void onItemClick(int pos, View view);
    }

    private ZhuYe_DongTaiAdapter.OnItemClickListener mListener;

    public void setOnItemClickListener(ZhuYe_DongTaiAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

}
