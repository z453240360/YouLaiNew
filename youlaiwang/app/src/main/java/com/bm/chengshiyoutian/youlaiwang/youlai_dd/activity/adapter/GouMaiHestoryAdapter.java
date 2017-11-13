package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinYouBianBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.BuyRecordBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.SevenBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.PicLoader;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view.AddView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bm.chengshiyoutian.youlaiwang.R.drawable.on;
import static com.bm.chengshiyoutian.youlaiwang.R.id.add_number;
import static com.bm.chengshiyoutian.youlaiwang.R.id.mTxt_danwei;
import static com.bm.chengshiyoutian.youlaiwang.R.id.mTxt_money;
import static com.bm.chengshiyoutian.youlaiwang.R.id.mTxt_title;
import static com.bm.chengshiyoutian.youlaiwang.R.id.mTxt_today;
import static com.bm.chengshiyoutian.youlaiwang.R.id.main_title;
import static com.bm.chengshiyoutian.youlaiwang.R.id.rl1;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_queDing;

/**
 * Created by Administrator on 2017/10/10.
 */

public class GouMaiHestoryAdapter extends RecyclerView.Adapter<GouMaiHestoryAdapter.MyViewHolder_dd> {

    private Context context;
    private List<BuyRecordBean.DataBeanX.DataBean> mDates = new ArrayList<>();
    private LayoutInflater mInflater;
    private Map<Integer, Integer> map = new HashMap<>();
    private CarAdapter adapter;


    public interface OnItemClickListener {
        void onItemClick(int pos, View view);

        void onAddClick(int pos, View view);

        void onReduceClick(int pos, View view);

        void onTextNumberClick(int pos, int number);
    }

    private OnItemClickListener myListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.myListener = listener;
    }


    public GouMaiHestoryAdapter(Context context, List<BuyRecordBean.DataBeanX.DataBean> mDates) {
        this.context = context;
        this.mDates = mDates;
        this.mInflater = LayoutInflater.from(context);

        initMap();
    }

    public void initMap() {
        if (mDates != null) {
            for (int i = 0; i < mDates.size(); i++) {
                map.put(i, 0);
            }
        }
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
        int goods_id = mDates.get(position).getGoods_id();
        String goods_cover = mDates.get(position).getGoods_cover();
        String goods_price = mDates.get(position).getGoods_price();
        String goods_spec_remark = mDates.get(position).getGoods_spec_remark();
        String ratio = mDates.get(position).getRatio();

        holder.mTxt_title.setText(mDates.get(position).getGoods_name());
        holder.mTxt_money.setText(goods_price.toString());
        holder.mTxt_danwei.setText(ratio);

        Picasso.with(context)
                .load(goods_cover)
                .into(holder.mImg_goods);

        if (map.get(position) == null) {
            map.put(position, 0);
        }

        holder.mTxt_number.setText(map.get(position)+"");

        //数量增加
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer integer = map.get(position);
                int integer1 = integer+1;
                map.put(position, integer1);
                holder.mTxt_number.setText(map.get(position) + "");

                myListener.onAddClick(position, holder.itemView);
            }
        });

        //数量减少
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            private int integer1;

            @Override
            public void onClick(View view) {
                Integer integer = map.get(position);
                if (integer > 0) {
                    integer1 = integer-1;
                } else if (integer <= 0) {
                    integer1 = 0;
                }

                map.put(position, integer1);
                holder.mTxt_number.setText(map.get(position) + "");

                myListener.onReduceClick(position, holder.itemView);
            }
        });


        //点击跳出弹框
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
                et_num.setText(map.get(position) + "");

                tv_quXiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                //手动输入数量
                tv_queDing.setOnClickListener(new View.OnClickListener() {

                    private int i;

                    @Override
                    public void onClick(View view) {
                        String trim = et_num.getText().toString().trim();

                        if (trim.equals("")) {
                            dialog.cancel();
                            return;
                        } else if (trim == null) {
                            dialog.cancel();
                            return;
                        }


                        try {
                            i = Integer.parseInt(trim);
                        } catch (NumberFormatException e) {
                            i = 0;
                        }

                        if (i < 1) {
                            map.put(position, 0);
                            holder.mTxt_number.setText(map.get(position));
                            myListener.onTextNumberClick(position, map.get(position));
                        } else {
                            map.put(position, i);
                            holder.mTxt_number.setText(map.get(position)+"");
                            myListener.onTextNumberClick(position, map.get(position));
                        }

                        dialog.cancel();
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onItemClick(position,holder.itemView);
            }
        });


        LinearLayoutManager manager = new LinearLayoutManager(context);




    }


    public class MyViewHolder_dd extends RecyclerView.ViewHolder {

        private ImageView mImg_goods, add, reduce;
        private TextView mTxt_new, mBtn_guige, mTxt_title, mTxt_hot, mTxt_money, mTxt_danwei, mTxt_number;
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
            mTxt_danwei = (TextView) itemView.findViewById(R.id.mTxt_danwei);
            mTxt_number = (TextView) itemView.findViewById(R.id.mTxt_number);

            ry_specd = (RecyclerView) itemView.findViewById(R.id.ry_specd);

        }
    }
}
