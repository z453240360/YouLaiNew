package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ShoppingCarBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.callback.IAddCarBack;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.CatUtils;

import java.util.ArrayList;
import java.util.List;


public class DingDanAdapter extends RecyclerView.Adapter<DingDanAdapter.MyViewHolder> {
    private final String token;
    private List<ShoppingCarBean.DataBeanX.DataBean.SpecBean> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private SharedPreferences sp;

    public DingDanAdapter(Context context, List<ShoppingCarBean.DataBeanX.DataBean.SpecBean> datas) {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mContext = context;
        sp = context.getSharedPreferences(MyRes.CONFIG,0);
        token = sp.getString(MyRes.MY_TOKEN, "");
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    //初始化布局,创建ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_dingdan, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final int spec_id = mDatas.get(position).getSpec_id();
        int cart_goods_num = mDatas.get(position).getCart_goods_num();
        final int goods_id = mDatas.get(position).getGoods_id();
        String spec_promo_price = mDatas.get(position).getSpec_promo_price();
        String price = mDatas.get(position).getPrice();

        double promoPrice = Double.parseDouble(spec_promo_price);
        double realPrice = Double.parseDouble(price);


        //促销价格大于0，且小于原有价格，显示促销价格和原价，否则只显示原有价格
        if (realPrice>=promoPrice&&promoPrice>0){
            holder.tv_name.setText("¥"+promoPrice+"");
            holder.tv_name2.setText(""+realPrice);
        }else {
            holder.tv_name.setText("¥"+realPrice+"");
            holder.tv_name2.setText("");
        }

        holder.tv_name3.setText(mDatas.get(position).getRatio()+"");



        holder.tv_test.setText(cart_goods_num+"");

        //购物车数量增加
        holder.mImg_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mImg_add.setClickable(false);

                if (token.equals("")|| token.equals("kong")){
                    holder.mImg_add.setClickable(true);
                    mContext.startActivity(new Intent(mContext, Login_ddActivity.class));
                    return;
                }


                final Integer integer = mDatas.get(position).getCart_goods_num();
                CatUtils.addCar(1, goods_id, spec_id, 1, sp.getString(MyRes.MY_TOKEN,""),new IAddCarBack() {
                    @Override
                    public void successed(String code) {
                        int num = integer + 1;
                        mDatas.get(position).setCart_goods_num(num);
                        if (callBackData != null) {
                            callBackData.getList(mDatas);
                            callBackData.addClicked(position);
                        }

                        holder.tv_test.setText(mDatas.get(position).getCart_goods_num()+"");
                        holder.mImg_add.setClickable(true);
                    }

                    @Override
                    public void failed(String s) {
                        holder.mImg_add.setClickable(true);
                    }
                });
            }
        });

        //数量减少
        holder.mImg_reduce.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                holder.mImg_reduce.setClickable(false);

                if (token.equals("")|| token.equals("kong")){
                    holder.mImg_add.setClickable(true);
                    mContext.startActivity(new Intent(mContext, Login_ddActivity.class));
                    return;
                }

                final Integer integer = mDatas.get(position).getCart_goods_num();
                if (integer==0){
                    return;
                }

                CatUtils.addCar(1, goods_id, spec_id, 2, sp.getString(MyRes.MY_TOKEN,""),new IAddCarBack() {
                    @Override
                    public void successed(String code) {
                        int num = integer - 1;

                        if (num <= 0) {
                            mDatas.get(position).setCart_goods_num(0);
                        } else {
                            mDatas.get(position).setCart_goods_num(num);
                        }
                        if (callBackData != null) {
                            callBackData.getList(mDatas);
                            callBackData.reduceClicked(position);
                        }
                        holder.mImg_reduce.setClickable(true);
                        holder.tv_test.setText(mDatas.get(position).getCart_goods_num() + "");
                    }

                    @Override
                    public void failed(String s) {
                        holder.mImg_reduce.setClickable(true);
                    }
                });

            }
        });
    }


    public void deleteItem(int postion){
        notifyItemRemoved(postion);
    }

    public OnCallBackData callBackData;

    public void setCallBackData(OnCallBackData callBackData) {
        this.callBackData = callBackData;
    }

    public interface OnCallBackData {
        void getList(List<ShoppingCarBean.DataBeanX.DataBean.SpecBean> mDatas);
        void addClicked(int postion);
        void reduceClicked(int postion);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_test,tv_name,tv_name2,tv_name3;
        private ImageView mImg_add, mImg_reduce;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_test = (TextView) itemView.findViewById(R.id.mTxt_test);
            tv_name = (TextView) itemView.findViewById(R.id.mTxt_name);
            tv_name2 = (TextView) itemView.findViewById(R.id.mTxt_name2);
            tv_name2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
            tv_name3 = (TextView) itemView.findViewById(R.id.mTxt_name3);

            mImg_add = (ImageView) itemView.findViewById(R.id.mImg_add);
            mImg_reduce = (ImageView) itemView.findViewById(R.id.mImg_reduce);
        }
    }

}
