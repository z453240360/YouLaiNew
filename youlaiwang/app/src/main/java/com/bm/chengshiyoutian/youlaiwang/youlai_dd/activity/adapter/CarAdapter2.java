package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.Car_dd_Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/10.
 */

public class CarAdapter2 extends RecyclerView.Adapter<CarAdapter2.MyViewHolder_dd> {

    private Context context;
    private List<Car_dd_Bean.DataBeanX.DataBean.GoodsSpecBean> mDates = new ArrayList<>();
    private LayoutInflater mInflater;
    private Map<Integer,Integer> map=new HashMap<>();

    public interface OnItemClickListener {
        void onItemClick(int pos, View view);
        void onAddClicked(int pos,int spec_id);
        void onReduceClicked(int pos,int spec_id);
        void onTextNumBerChanged(int pos,int spec_id,int number);
    }

    private OnItemClickListener myListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.myListener = listener;
    }

    public CarAdapter2(Context context, List<Car_dd_Bean.DataBeanX.DataBean.GoodsSpecBean> mDates) {
        this.context = context;
        this.mDates = mDates;
        this.mInflater = LayoutInflater.from(context);
        initMap();
    }

    public void initMap() {
        if (mDates!=null){
            for (int i = 0; i < mDates.size(); i++) {
                map.put(i,mDates.get(i).getCart_goods_num());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDates == null ? 0 : mDates.size();
    }

    @Override
    public MyViewHolder_dd onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_dd_car2, parent, false);
        MyViewHolder_dd myViewHolder = new MyViewHolder_dd(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder_dd holder, final int position) {

        Car_dd_Bean.DataBeanX.DataBean.GoodsSpecBean goodsSpecBean = mDates.get(position);

        String spec_promo_price = goodsSpecBean.getSpec_promo_price();//促销价格
        final int cart_goods_num = goodsSpecBean.getCart_goods_num();//购物车数据
        String price = goodsSpecBean.getPrice();
        String ratio = goodsSpecBean.getRatio();
        int spec_id = goodsSpecBean.getSpec_id();



        if (map.get(position)==null){
            map.put(position,0);
        }


        double v = Double.parseDouble(spec_promo_price);
        if (v-0>0){
            holder.mTxt_money.setText("￥"+spec_promo_price);
            holder.mTxt_money2.setText(price);
        }else {
            holder.mTxt_money.setText("￥"+price);
            holder.mTxt_money2.setText("");
        }

        holder.mTxt_number.setText(cart_goods_num+"");

        holder.mTxt_danwei.setText(ratio);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myListener!=null) {

                    Integer integer = map.get(position);
                    map.put(position,integer+1);

                    holder.mTxt_number.setText(map.get(position));

                    myListener.onAddClicked(position,mDates.get(position).getSpec_id());
                }
            }
        });

        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myListener!=null) {

                    Integer integer = map.get(position);
                    if (integer>=1) {
                        map.put(position, integer - 1);
                    }else {
                        map.put(position,0);
                    }

                    holder.mTxt_number.setText(map.get(position));
                    myListener.onReduceClicked(position,mDates.get(position).getSpec_id());
                }
            }
        });

        holder.mTxt_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setView(LayoutInflater.from(context).inflate(R.layout.item_goumai, null));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                final EditText et_num = (EditText) dialog.findViewById(R.id.et_num);
                TextView tv_quXiao = (TextView) dialog.findViewById(R.id.tv_quXiao);
                TextView tv_queDing = (TextView) dialog.findViewById(R.id.tv_queDing);

                et_num.setText(cart_goods_num + "");

                tv_quXiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                //手动输入数量
                tv_queDing.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        String trim = et_num.getText().toString().trim();
                        if (trim.equals("")){
                            dialog.cancel();
                            return;
                        }else if (trim==null){
                            dialog.cancel();
                            return;
                        }

                        int i = Integer.parseInt(trim);

                        myListener.onTextNumBerChanged(position,mDates.get(position).getSpec_id(),i);
                        dialog.cancel();
                    }
                });
            }
        });


    }



    public class MyViewHolder_dd extends RecyclerView.ViewHolder {

        private ImageView add, reduce;
        private TextView  mTxt_money,mTxt_money2, mTxt_danwei, mTxt_number;

        public MyViewHolder_dd(View itemView) {
            super(itemView);

            add = (ImageView) itemView.findViewById(R.id.add);
            reduce = (ImageView) itemView.findViewById(R.id.reduce);

            mTxt_money = (TextView) itemView.findViewById(R.id.mTxt_money);
            mTxt_money2 = (TextView) itemView.findViewById(R.id.mTxt_money2);
            mTxt_danwei = (TextView) itemView.findViewById(R.id.mTxt_danwei);
            mTxt_number = (TextView) itemView.findViewById(R.id.mTxt_number);
            mTxt_money2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

        }
    }
}
