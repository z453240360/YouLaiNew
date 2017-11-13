package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.Car_dd_Bean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/10.
 */

public class ShoppingCarAdapter extends RecyclerView.Adapter<ShoppingCarAdapter.MyViewHolder_dd> {

    private Context context;
    private List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> mDates = new ArrayList<>();
    private LayoutInflater mInflater;


    public interface OnItemClickListener {
        void onItemClick(int pos, View view);
        void isSeclectAll(boolean s);
        void add(int pos,String price);
        void reduce(int pos,String price);
        void textNumber(int pos,String price);
    }

    private OnItemClickListener myListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.myListener = listener;
    }

    public ShoppingCarAdapter(Context context, List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> mDates) {
        this.context = context;
        this.mDates = mDates;
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public int getItemCount() {
        return mDates == null ? 0 : mDates.size();
    }

    @Override
    public MyViewHolder_dd onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_shop_car, parent, false);
        MyViewHolder_dd myViewHolder = new MyViewHolder_dd(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder_dd holder, final int position) {


    }



    public class MyViewHolder_dd extends RecyclerView.ViewHolder {

        private ImageView mImg_goods, add, reduce;

        private TextView mTxt_new, mBtn_guige, mTxt_title, mTxt_hot, mTxt_money,mTxt_money2, mTxt_danwei, mTxt_number;


        public MyViewHolder_dd(View itemView) {
            super(itemView);

            mImg_goods = (ImageView) itemView.findViewById(R.id.mImg_goods);
            add = (ImageView) itemView.findViewById(R.id.add);
            reduce = (ImageView) itemView.findViewById(R.id.reduce);
            mTxt_new = (TextView) itemView.findViewById(R.id.mTxt_new);
            mBtn_guige = (TextView) itemView.findViewById(R.id.mBtn_guige);
            mTxt_title = (TextView) itemView.findViewById(R.id.mTxt_title);
            mTxt_hot = (TextView) itemView.findViewById(R.id.mTxt_hot);
            mTxt_money = (TextView) itemView.findViewById(R.id.mTxt_money);
            mTxt_money2 = (TextView) itemView.findViewById(R.id.mTxt_money2);
            mTxt_money2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
            mTxt_danwei = (TextView) itemView.findViewById(R.id.mTxt_danwei);
            mTxt_number = (TextView) itemView.findViewById(R.id.mTxt_number);

        }
    }
}
