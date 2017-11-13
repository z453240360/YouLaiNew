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
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.Car_dd_Bean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/10.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder_dd> {

    private Context context;
    private List<Car_dd_Bean.DataBeanX.DataBean> mDates = new ArrayList<>();
    private LayoutInflater mInflater;
    private List<Car_dd_Bean.DataBeanX.DataBean.GoodsSpecBean> goods_spec=new ArrayList<>();
    private Map<Integer,Boolean> map=new HashMap<>();


    public interface OnItemClickListener {
        void onItemClick(int pos, View view);
        void onAddClicked(int pos,int spec,int number,String numbers);
        void onReduceClicked(int pos,int spec,int number,String numbers);
        void onSecondAddClicked(int pos,int pos2,int spec,int number);
        void onSecondReduceClicked(int pos,int pos2,int spec,int number);
        void onNumberchanged(int pos,int spec,int number);
        void onSecondChanged(int pos,int pos2,int spec,int number);
    }

    private OnItemClickListener myListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.myListener = listener;
    }

    public CarAdapter(Context context, List<Car_dd_Bean.DataBeanX.DataBean> mDates) {
        this.context = context;
        this.mDates = mDates;
        this.mInflater = LayoutInflater.from(context);
        initMap();
    }

    public void initMap() {
        if (mDates!=null){
            for (int i = 0; i < mDates.size(); i++) {
                map.put(i,false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDates == null ? 0 : mDates.size();
    }

    @Override
    public MyViewHolder_dd onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_dd_car, parent, false);
        MyViewHolder_dd myViewHolder = new MyViewHolder_dd(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder_dd holder, final int position) {

        Car_dd_Bean.DataBeanX.DataBean dataBean = mDates.get(position);
        String goods_cover = dataBean.getGoods_cover();
        int goods_id = dataBean.getGoods_id();
        int goods_is_new = dataBean.getGoods_is_new();
        int goods_is_promo = dataBean.getGoods_is_promo();
        String goods_name = dataBean.getGoods_name();
        String goods_price = dataBean.getGoods_price();




        final List<Car_dd_Bean.DataBeanX.DataBean.GoodsSpecBean> goods_spec = dataBean.getGoods_spec();

        /**
         * 默认显示商品的信息
         */

        Car_dd_Bean.DataBeanX.DataBean.SpecBean spec = dataBean.getSpec();
        int cart_goods_num = spec.getCart_goods_num();
        int goodsNum = spec.getGoodsNum();
        String price = spec.getPrice();
        String ratio = spec.getRatio();
        int spec_id = spec.getSpec_id();
        String spec_promo_price = spec.getSpec_promo_price();
        double v = Double.parseDouble(spec_promo_price);


        if (map.get(position)==null){
            map.put(position,false);
        }




        holder.mTxt_title.setText(mDates.get(position).getGoods_name());

        holder.mTxt_danwei.setText(ratio);

        holder.mTxt_number.setText(cart_goods_num+"");

        holder.mBtn_guige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map.get(position)){
                    holder.ry_specd.setVisibility(View.GONE);
                    Drawable top = context.getResources().getDrawable(R.mipmap.sousuo_xuanguige);
                    top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
                    holder.mBtn_guige.setCompoundDrawables(null,null,top,null);
                    map.put(position,false);
                }else {
                    holder.ry_specd.setVisibility(View.VISIBLE);
                    Drawable top = context.getResources().getDrawable(R.mipmap.sousuo_shouqi);
                    top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
                    holder.mBtn_guige.setCompoundDrawables(null,null,top,null);
                    map.put(position,true);
                }
            }
        });

        Picasso.with(context).load(goods_cover).into(holder.mImg_goods);

        if (goods_is_new==1){
            holder.mTxt_new.setVisibility(View.VISIBLE);
            holder.mTxt_new.setText("新");
        }else {
            holder.mTxt_new.setVisibility(View.INVISIBLE);
//            holder.mTxt_new.setText("");
        }

        if (goods_is_promo==1){
            holder.mTxt_hot.setVisibility(View.VISIBLE);
            holder.mTxt_hot.setText("促");
        }else {
            holder.mTxt_hot.setVisibility(View.INVISIBLE);
//            holder.mTxt_hot.setText("");
        }

        if (goods_spec.size()>0){
            holder.mBtn_guige.setVisibility(View.VISIBLE);
        }else {
            holder.mBtn_guige.setVisibility(View.INVISIBLE);
        }

        if (v-0>0){
            holder.mTxt_money.setText("￥"+spec_promo_price+"");
            holder.mTxt_money2.setText(price);
        }else {
            holder.mTxt_money.setText("￥"+price+"");
            holder.mTxt_money2.setText("");
        }

        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onReduceClicked( position,mDates.get(position).getSpec().getSpec_id(),-1,holder.mTxt_number.getText().toString());
            }
        });


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myListener!=null){
                    myListener.onAddClicked(position,mDates.get(position).getSpec().getSpec_id(),1,holder.mTxt_number.getText().toString());
                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myListener!=null){
                    myListener.onItemClick(position,holder.itemView);
                }
            }
        });


        if (goods_spec.size()>0){
            LinearLayoutManager manager2=new LinearLayoutManager(context);
            CarAdapter2 adapter2 = new CarAdapter2(context,goods_spec);
            holder.ry_specd.setLayoutManager(manager2);
            holder.ry_specd.setAdapter(adapter2);


            //多规格列表回调事件
            adapter2.setOnItemClickListener(new CarAdapter2.OnItemClickListener() {
                @Override
                public void onItemClick(int pos, View view) {

                }

                @Override
                public void onAddClicked(int pos, int spec_id) {
                    ShowToast.showToast("位置"+pos+"商品ID"+spec_id);
                }

                @Override
                public void onReduceClicked(int pos, int spec_id) {
                    ShowToast.showToast("位置"+pos+"商品ID"+spec_id);
                }

                @Override
                public void onTextNumBerChanged(int pos, int spec_id, int number) {
                    ShowToast.showToast("位置"+pos+"商品ID"+spec_id+"数量"+number);
                }

            });
        }

    }


    public void refesh(){
        mDates.get(5).getSpec().setCart_goods_num(100);
        notifyDataSetChanged();
    }


    public class MyViewHolder_dd extends RecyclerView.ViewHolder {

        private ImageView mImg_goods, add, reduce;
        private TextView mTxt_new, mBtn_guige, mTxt_title, mTxt_hot, mTxt_money,mTxt_money2, mTxt_danwei, mTxt_number;
        private RecyclerView ry_specd;

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
            ry_specd = (RecyclerView) itemView.findViewById(R.id.ry_specd);

        }
    }
}
