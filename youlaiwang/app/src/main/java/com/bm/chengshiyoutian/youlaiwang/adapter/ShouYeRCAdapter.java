package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.GlideToCicle;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.ShouYeBean;
import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by sld on 2017/5/18.
 */

public class ShouYeRCAdapter extends RecyclerView.Adapter<ShouYeRCAdapter.MyViewHolder> {

    private Intent intent;

    public ShouYeRCAdapter(Context context, List<ShouYeBean.DataBean.DataRecommendBean> dataRecommend) {

        this.context = context;
        this.dataRecommend = dataRecommend;
    }


    Context context;
    List<ShouYeBean.DataBean.DataRecommendBean> dataRecommend;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_shouye_xinpin_rc, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();

        holder.tv2.setText("¥ "+dataRecommend.get(position).getGoods_price());
        holder.tv1.setText(dataRecommend.get(position).getGoods_name());
        holder.tv3.setText(dataRecommend.get(position).getRatio());



        Glide.with(context)
                .load(dataRecommend.get(position).getGoods_cover()).transform(new GlideToCicle(context,20)).centerCrop()  .into( holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = dataRecommend.get(position).getGoods_id() + "";
                Intent intent = new Intent(context, ShangPinXiangQingActivity.class);
                intent.putExtra("token",s);
                context.startActivity(intent);
            }
        });
       SharedPreferences sp = context.getSharedPreferences(MyRes.CONFIG, 0);
        final String head = sp.getString(MyRes.MY_TOKEN, "kong");


    }


    @Override
    public int getItemCount() {
        if(dataRecommend.size()>4){
            return 4;
        }
        return dataRecommend.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,gouwuche;
        TextView tv1,tv2,tv3;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image1);
            gouwuche = (ImageView) itemView.findViewById(R.id.gouwuche);
            tv1=(TextView) itemView.findViewById(R.id.tv1);
            tv2=(TextView) itemView.findViewById(R.id.tv2);
            tv3=(TextView) itemView.findViewById(R.id.tv3);
        }
    }
}
