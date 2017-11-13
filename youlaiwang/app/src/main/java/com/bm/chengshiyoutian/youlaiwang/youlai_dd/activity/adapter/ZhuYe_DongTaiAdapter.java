package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ZuiXinDongTaiBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class ZhuYe_DongTaiAdapter extends RecyclerView.Adapter<ZhuYe_DongTaiAdapter.MyHolder> {
    private List<ZuiXinDongTaiBean.DataBeanX.DataBean> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;


    public ZhuYe_DongTaiAdapter(List<ZuiXinDongTaiBean.DataBeanX.DataBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_dongtai, parent, false);
        MyHolder myViewHolder = new MyHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position, holder.itemView);
                }
            }
        });

        ZuiXinDongTaiBean.DataBeanX.DataBean dataBean = mDatas.get(position);
        //获取图片地址
        String goods_cover = dataBean.getArticle_cover();

        Picasso.with(mContext)
                .load(goods_cover)
                .placeholder(R.mipmap.zhanweitu)
                .error(R.mipmap.zhanweitu)
                .into(holder.mImg);

        holder.title.setText(dataBean.getArticle_title());//标题
        holder.time.setText(dataBean.getArticle_time());//日期
        holder.seeNumber.setText(dataBean.getArticle_see_num() + " 人看过");//查看人数
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class MyHolder extends XRecyclerView.ViewHolder {

        ImageView mImg;
        TextView title, time, seeNumber;

        public MyHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.iv_show);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            time = (TextView) itemView.findViewById(R.id.time);
            seeNumber = (TextView) itemView.findViewById(R.id.seeNumber);
        }
    }

    /**
     * 点击事件的接口回调
     */

    public interface OnItemClickListener {
        void onItemClick(int pos, View view);
        void onCarClicked();
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}
