package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuShouYeBean;
import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by sld on 2017/5/18.
 */

public class DianPuShouYeRCAdapter extends RecyclerView.Adapter<DianPuShouYeRCAdapter.MyViewHolder> {

    public DianPuShouYeRCAdapter(int x, Context context, List<DianPuShouYeBean.DataBean.NewGoodsBean> newGoods) {
        this.x = x;
        this.context = context;
        this.newGoods = newGoods;
    }

    int x;
    Context context;
    List<DianPuShouYeBean.DataBean.NewGoodsBean> newGoods;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cuxiao, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.tv3.setText("¥"+newGoods.get(position).getGoods_price());
        holder.tv1.setText(newGoods.get(position).getGoods_name());
        holder.tv4.setText(newGoods.get(position).getRatio());

//        layoutParams.height = i / 3;
//
//        layoutParams.width = i / 3;
//
//        holder.imageView.setLayoutParams(layoutParams);
        Glide.with(context)
                .load(newGoods.get(position).getGoods_cover()) .centerCrop ().into( holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShangPinXiangQingActivity.class);
                intent.putExtra("token",newGoods.get(position).getGoods_id()+"");
                Log.d("goodsid", newGoods.get(position).getGoods_id()+"");
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return newGoods.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv1,tv4,tv3;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            tv1=(TextView) itemView.findViewById(R.id.title);
            tv3=(TextView) itemView.findViewById(R.id.tv3);
            tv4=(TextView) itemView.findViewById(R.id.tv4);
        }
    }
}
