package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.ShoppingCar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ShoppingCarBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.callback.IAddCarBack;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.CatUtils;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view.ShoppingCarChildView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.R.drawable.sta;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.MyViewHolder> {
    private List<ShoppingCarBean.DataBeanX.DataBean> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private SharedPreferences sp;
    private String token;

    public ShoppingAdapter(Context context, List<ShoppingCarBean.DataBeanX.DataBean> datas) {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mContext = context;
        sp = context.getSharedPreferences(MyRes.CONFIG, 0);
        token = sp.getString(MyRes.MY_TOKEN, "");
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    //初始化布局,创建ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.test_item3, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final int goods_id = mDatas.get(position).getGoods_id();
        final int spec_id = mDatas.get(position).getSpec().getSpec_id();

        holder.mTxt_title.setText(mDatas.get(position).getGoods_name());//商品名称
        holder.mTxt_danwei.setText(mDatas.get(position).getSpec().getRatio());

        holder.mTxt_number.setText(mDatas.get(position).getSpec().getCart_goods_num() + "");//商品数量
        //商品图片
        Glide.with(mContext).load(mDatas.get(position).getGoods_cover()).into(holder.mImg_goods);

        List<ShoppingCarBean.DataBeanX.DataBean.SpecBean> goods_spec = mDatas.get(position).getGoods_spec();


        //选规格按钮是否可见
        if (goods_spec.size() == 0) {
            holder.mBtn_guige.setVisibility(View.INVISIBLE);
        } else {
            holder.mBtn_guige.setVisibility(View.VISIBLE);

            for (int i = 0; i < goods_spec.size(); i++) {
                goods_spec.get(i).setGoods_id(goods_id);
            }
        }

        //判断是否是新品
        int goods_is_new = mDatas.get(position).getGoods_is_new();
        if (goods_is_new == 1) {
            holder.mTxt_new.setText("新");
        } else {
            holder.mTxt_new.setText("");
        }


        //判断是否是促销品
        int goods_is_promo = mDatas.get(position).getGoods_is_promo();
        if (goods_is_promo == 1) {
            holder.mTxt_hot.setText("促");
        } else {
            holder.mTxt_hot.setText("");
        }

        //判断是不是促销价格
        if (Double.parseDouble(mDatas.get(position).getSpec().getSpec_promo_price()) > 0) {
            holder.mTxt_money.setText("¥" + mDatas.get(position).getSpec().getSpec_promo_price());//商品价格
            holder.mTxt_money2.setText(mDatas.get(position).getSpec().getPrice() + "");
        } else {
            holder.mTxt_money.setText("¥" + mDatas.get(position).getGoods_price());//商品价格
            holder.mTxt_money2.setText("");
        }


        //设置子列表的数据
        holder.list.setDatas(goods_spec, position);


        //设置规格箭头指向
        if (mDatas.get(position).isShow()) {
            holder.list.setVisibility(View.VISIBLE);
            Drawable top = mContext.getResources().getDrawable(R.mipmap.sousuo_xuanguige);

            top.setBounds(0, 0, 20, 20);
            holder.mBtn_guige.setCompoundDrawables(null, null, top, null);
        } else {
            Drawable top = mContext.getResources().getDrawable(R.mipmap.sousuo_shouqi);
            top.setBounds(0, 0, 20, 20);
            holder.mBtn_guige.setCompoundDrawables(null, null, top, null);
            holder.list.setVisibility(View.GONE);
        }


        //规格选择按钮，打开还是收起
        holder.mBtn_guige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDatas.get(position).isShow()) {
                    holder.list.setVisibility(View.VISIBLE);
                    mDatas.get(position).setShow(false);
                    notifyDataSetChanged();
                } else {
                    holder.list.setVisibility(View.INVISIBLE);
                    mDatas.get(position).setShow(true);
                    notifyDataSetChanged();
                }
            }
        });


        //购物车数量增加
        holder.add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (sp.getString(MyRes.TOKEN, "").equals("") || sp.getString(MyRes.TOKEN, "").equals("kong")) {

                    mContext.startActivity(new Intent(mContext, Login_ddActivity.class));
                    return;
                }


                holder.add.setClickable(false);
                final Integer integer = mDatas.get(position).getSpec().getCart_goods_num();
                CatUtils.addCar(1, goods_id, spec_id, 1, sp.getString(MyRes.MY_TOKEN, ""), new IAddCarBack() {
                    @Override
                    public void successed(String code) {
                        int num = integer + 1;
                        mDatas.get(position).getSpec().setCart_goods_num(num);
                        holder.mTxt_number.setText(mDatas.get(position).getSpec().getCart_goods_num() + "");
                        holder.add.setClickable(true);
                        if (clicked != null) {
                            clicked.addClicked();
                        }
                    }

                    @Override
                    public void failed(String s) {
                        holder.add.setClickable(true);
                    }
                });
            }
        });


        //数量减少
        holder.reduce.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (sp.getString(MyRes.TOKEN, "").equals("") || sp.getString(MyRes.TOKEN, "").equals("kong")) {
                    mContext.startActivity(new Intent(mContext, Login_ddActivity.class));
                    return;
                }
                holder.reduce.setClickable(false);
                final Integer integer = mDatas.get(position).getSpec().getCart_goods_num();
                if (integer == 0) {
                    return;
                }

                CatUtils.addCar(1, goods_id, spec_id, 2, sp.getString(MyRes.MY_TOKEN, ""), new IAddCarBack() {
                    @Override
                    public void successed(String code) {
                        int num = integer - 1;
                        if (num <= 0) {
                            mDatas.get(position).getSpec().setCart_goods_num(0);
                        } else {
                            mDatas.get(position).getSpec().setCart_goods_num(num);
                        }

                        holder.reduce.setClickable(true);
                        holder.mTxt_number.setText(mDatas.get(position).getSpec().getCart_goods_num() + "");
                        if (clicked != null) {
                            clicked.addClicked();
                        }
                    }

                    @Override
                    public void failed(String s) {
                        holder.reduce.setClickable(true);
                    }
                });
            }
        });


        //数据增加
        holder.list.setOnAddClicked(new ShoppingCarChildView.OnAddClicked() {
            @Override
            public void addClicked(int chiledPostion) {

            }
        });


        //数据减少
        holder.list.setReduceClicked(new ShoppingCarChildView.OnReduceClicked() {
            @Override
            public void reduceClicked(int chiledPostion) {

            }
        });

        //点击跳转到商品详情页面
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked != null) {
                    clicked.getDetial(goods_id);
                }
            }
        });

    }


    private OnItemClicked clicked;

    public void setClicked(OnItemClicked clicked) {
        this.clicked = clicked;
    }

    public interface OnItemClicked {
        void getDetial(int goods_id);

        void addClicked();

        void reduceClicker();
    }

    //跳转商品详情，获取改变后的数据
    public void refish(List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> backData) {
        if (backData == null) {
            return;
        }

        int goods_id = Integer.parseInt(backData.get(0).getGoods_id());
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getGoods_id() == goods_id) {
                for (int j = 0; j < backData.size(); j++) {
                    int spec_id = backData.get(j).getSpec_id();
                    int cart_goods_num = backData.get(j).getCart_goods_num();
                    if (j == 0) {
                        mDatas.get(i).getSpec().setCart_goods_num(cart_goods_num);
                    } else {
                        List<ShoppingCarBean.DataBeanX.DataBean.SpecBean> goods_spec = mDatas.get(i).getGoods_spec();

                        for (int k = 0; k < goods_spec.size(); k++) {
                            if (goods_spec.get(k).getSpec_id() == spec_id) {
                                goods_spec.get(k).setCart_goods_num(cart_goods_num);
                            }
                        }
                    }
                }
            }
        }

        notifyDataSetChanged();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ShoppingCarChildView list;

        private ImageView mImg_goods, add, reduce;
        private TextView mTxt_new, mBtn_guige, mTxt_title, mTxt_hot, mTxt_money, mTxt_money2, mTxt_danwei, mTxt_number;

        public MyViewHolder(View itemView) {
            super(itemView);
            list = (ShoppingCarChildView) itemView.findViewById(R.id.sssss);

            mImg_goods = (ImageView) itemView.findViewById(R.id.mImg_goods);
            add = (ImageView) itemView.findViewById(R.id.add);
            reduce = (ImageView) itemView.findViewById(R.id.reduce);
            mTxt_new = (TextView) itemView.findViewById(R.id.mTxt_new);
            mBtn_guige = (TextView) itemView.findViewById(R.id.mBtn_guige);
            mTxt_title = (TextView) itemView.findViewById(R.id.mTxt_title);
            mTxt_hot = (TextView) itemView.findViewById(R.id.mTxt_hot);
            mTxt_money = (TextView) itemView.findViewById(R.id.mTxt_money);
            mTxt_money2 = (TextView) itemView.findViewById(R.id.mTxt_money2);
            mTxt_money2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            mTxt_danwei = (TextView) itemView.findViewById(R.id.mTxt_danwei);
            mTxt_number = (TextView) itemView.findViewById(R.id.mTxt_number);
        }
    }

}
