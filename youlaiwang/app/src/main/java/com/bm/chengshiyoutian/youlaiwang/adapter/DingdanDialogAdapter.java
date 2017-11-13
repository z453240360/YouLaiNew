package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.FaQiZhiFuBean;
import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by P on 2017/7/5.
 */

public class DingdanDialogAdapter extends RecyclerView.Adapter<DingdanDialogAdapter.MyViewHolder> {
    List<FaQiZhiFuBean.DataBean.StoresBean> stores;
    Context ctx;
    public DingdanDialogAdapter(List<FaQiZhiFuBean.DataBean.StoresBean> stores, Context mContext) {
        this.ctx = mContext;
        this.stores = stores;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(ctx, R.layout.dialog_itemquerendingdan, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FaQiZhiFuBean.DataBean.StoresBean storesBean = stores.get(position);
        holder.mPop_name.setText(storesBean.getStore_name());
        if (TextUtils.isEmpty(storesBean.getStore_logo())) {
            Glide.with(ctx).load(R.mipmap.loading_empty_img).into(holder.mPop_img);
        } else {
            Glide.with(ctx).load(storesBean.getStore_logo()).error(R.mipmap.loading_fail_img).placeholder(R.mipmap.loading_on_img).into(holder.mPop_img);
        }

        holder.mPop_callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new AlertDialog.Builder(ctx)
                        .create();
                dialog.setView(LayoutInflater.from(ctx).inflate(R.layout.item_call_phone_dialog, null));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置背景透明
                dialog.show();

                TextView tv_quXiao = (TextView) dialog.findViewById(R.id.tv_quXiao);
                TextView tv_queDing = (TextView) dialog.findViewById(R.id.tv_queDing);
                TextView tv_dianhua = (TextView) dialog.findViewById(R.id.tv_dianhua);
                tv_dianhua.setText(storesBean.getStore_phone());
                tv_quXiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                tv_queDing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Uri uri = Uri.parse("tel:" + storesBean.getStore_phone());
                            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                            ctx.startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return stores==null?0:stores.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout mPop_callphone;
        public TextView mPop_name;
        public ImageView mPop_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View itemView) {
            mPop_callphone = (LinearLayout) itemView.findViewById(R.id.pop_callphone);
            mPop_name = (TextView) itemView.findViewById(R.id.pop_name);
            mPop_img = (ImageView) itemView.findViewById(R.id.pop_img);

        }
    }
}
