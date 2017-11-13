package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.ShoppingCar;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ClassTwoBean;

import java.util.ArrayList;
import java.util.List;

public class ClassTwoAdapter extends RecyclerView.Adapter<ClassTwoAdapter.MyViewHolder> {
    private List<ClassTwoBean.DataBean> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public ClassTwoAdapter(Context context, List<ClassTwoBean.DataBean> datas) {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mContext = context;
    }




    //初始化
    public void init(){
        if (mDatas==null){
            return;
        }
        for (int i = 0; i < mDatas.size(); i++) {
            if (i==0){
                mDatas.get(i).setSelected(true);
            }else {
                mDatas.get(i).setSelected(false);
            }
        }
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    //初始化布局,创建ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.class_two, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.classTwo.setText(mDatas.get(position).getGc_name());


        if (mDatas.get(position).isSelected()){
            holder.line.setVisibility(View.VISIBLE);
            holder.classTwo.setTextColor(Color.parseColor("#74ca31"));
            holder.classTwo.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            holder.line.setVisibility(View.INVISIBLE);
            holder.classTwo.setTextColor(Color.parseColor("#000000"));
            holder.classTwo.setBackgroundColor(Color.parseColor("#008a8a8a"));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked!=null){
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).setSelected(false);
                    }

                    mDatas.get(position).setSelected(true);

                    clicked.onItemClicked(position,mDatas.get(position).getGc_id());

                    notifyDataSetChanged();
                }
            }
        });

    }


    //获取选中项目的商品详情
    public int  getPostion(){

        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isSelected()){
                return i;
            }
        }
        return 0;
    }

    private OnClicked clicked;

    public void setClicked(OnClicked clicked) {
        this.clicked = clicked;
    }

    public interface OnClicked{
        void onItemClicked(int postion, int gc_id);
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView classTwo,line;

        public MyViewHolder(View itemView) {
            super(itemView);

            classTwo = (TextView) itemView.findViewById(R.id.classTwo);
            line = (TextView) itemView.findViewById(R.id.line);
        }
    }

}
