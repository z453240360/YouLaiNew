package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.SevenBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view.AddView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/10.
 */

public class LastWeekAdapter extends RecyclerView.Adapter<LastWeekAdapter.MyViewHolder_dd> {

    private Context context;
    private List<SevenBean.DataBeanX.DataBean.GoodsBean> mDates = new ArrayList<>();
    private LayoutInflater mInflater;
    private Map<Integer, Boolean> map = new HashMap<>();//选中
    private Map<Integer, Integer> mapNumber = new HashMap<>();//商品数量
    private Map<Integer, Double> doubleMap = new HashMap<>();//钱

    public interface OnItemClickListener {
        void onItemClick(int pos, View view);

        void onCheckBoxChanged(int pos, boolean b);

        void onGetTotalPrice(String s);
    }

    private OnItemClickListener myListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.myListener = listener;
    }

    public LastWeekAdapter(Context context, List<SevenBean.DataBeanX.DataBean.GoodsBean> mDates) {
        this.context = context;
        this.mDates = mDates;
        this.mInflater = LayoutInflater.from(context);
        initMap();
    }

    public void initMap() {
        if (mDates != null) {
            for (int i = 0; i < mDates.size(); i++) {
                map.put(i, false);
                mapNumber.put(i, mDates.get(i).getGoods_num());
                doubleMap.put(i, mDates.get(i).getPrice());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDates == null ? 0 : mDates.size();
    }

    @Override
    public MyViewHolder_dd onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_lastweek, parent, false);
        MyViewHolder_dd myViewHolder = new MyViewHolder_dd(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder_dd holder, final int position) {

        //获取当前条目数据
        String goods_name = mDates.get(position).getGoods_name();
        double price = mDates.get(position).getPrice();
        int goods_num = mDates.get(position).getGoods_num();
        final int spec_id = mDates.get(position).getSpec_id();
        String spec_text = mDates.get(position).getSpec_text();
        int goods_state = mDates.get(position).getGoods_state();


        //设置对应的数据
        holder.mTxt_title.setText(goods_name + "");
        holder.mTxt_danwei.setText(spec_text);
        holder.mTxt_money.setText("￥" + price + "元");


        if (mapNumber.get(position) == null) {
            mapNumber.put(position, mDates.get(position).getGoods_num());
        }

        //判断是否已经下架
        if (goods_state != 1) {
            map.put(position, true);
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.mTxt_today.setText("已经下架");
            holder.mTxt_money.setVisibility(View.INVISIBLE);
            holder.mTxt_danwei.setVisibility(View.INVISIBLE);
            holder.add_number.setVisibility(View.INVISIBLE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.mTxt_today.setText("价格：");
            holder.mTxt_money.setVisibility(View.VISIBLE);
            holder.mTxt_danwei.setVisibility(View.VISIBLE);
            holder.add_number.setVisibility(View.VISIBLE);
        }


        //设置多选框的选中监听事件，
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                map.put(position, b);
                getMoney(position);
                double totalMoney = getTotalMoney();
                if (myListener != null) {
                    myListener.onGetTotalPrice(totalMoney + "");
                }

                //如果条目被全部选中，
                if (isAllSelect()) {
                    myListener.onCheckBoxChanged(position, true);
                } else {
                    myListener.onCheckBoxChanged(position, false);
                }

            }
        });


        //点击数量增加
        holder.add_number.getImg_add().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer integer = mapNumber.get(position);
                int pN = integer + 1;
                mapNumber.put(position, pN);
                holder.add_number.setmTxt_number(mapNumber.get(position), 100000, 0);
                getMoney(position);
                double totalMoney = getTotalMoney();
                if (myListener != null) {
                    myListener.onGetTotalPrice(totalMoney + "");
                }
            }
        });

        //点击数量减少
        holder.add_number.getImg_jian().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer integer = mapNumber.get(position);
                if (integer < 1) {
                    holder.add_number.setmTxt_number(0, 100000, 0);
                    map.put(position, false);
                    holder.checkBox.setChecked(map.get(position));
                    getMoney(position);
                    double totalMoney = getTotalMoney();
                    if (myListener != null) {
                        myListener.onGetTotalPrice(totalMoney + "");
                    }
                } else {
                    int pN = integer - 1;
                    mapNumber.put(position, pN);
                    holder.add_number.setmTxt_number(mapNumber.get(position), 100000, 0);
                    getMoney(position);
                    double totalMoney = getTotalMoney();

                    if (myListener != null) {
                        myListener.onGetTotalPrice(totalMoney + "");
                    }
                }
            }
        });


        //点击手动输入数量，跳出弹框
        holder.add_number.getmTxt_number().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setView(LayoutInflater.from(context).inflate(R.layout.item_goumai, null));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                final EditText et_num = (EditText) dialog.findViewById(R.id.et_num);
                TextView tv_quXiao = (TextView) dialog.findViewById(R.id.tv_quXiao);
                TextView tv_queDing = (TextView) dialog.findViewById(R.id.tv_queDing);
                et_num.setText(mapNumber.get(position) + "");

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
                            mapNumber.put(position, 0);
                            map.put(position, false);
                            holder.checkBox.setChecked(map.get(position));
                            getMoney(position);
                            double totalMoney = getTotalMoney();
                            if (myListener != null) {
                                myListener.onGetTotalPrice(totalMoney + "");
                            }
                        } else {
                            mapNumber.put(position, i);
                        }

                        holder.add_number.setmTxt_number(mapNumber.get(position), 100000, 0);

                        getMoney(position);
                        double totalMoney = getTotalMoney();

                        if (myListener != null) {
                            myListener.onGetTotalPrice(totalMoney + "");
                        }

                        dialog.cancel();
                    }
                });

            }
        });

        holder.add_number.setmTxt_number(mapNumber.get(position), 100000, 0);

        if (map.get(position) == null) {
            map.put(position, false);
        }

        //设置多选框是否选中
        holder.checkBox.setChecked(map.get(position));

        //点击查看详情
        holder.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDates.get(position).getGoods_state() == 1) {
                    if (myListener != null) {
                        myListener.onItemClick(position, holder.itemView);
                    }
                }

            }
        });

    }

    //获取当前条目的金额
    private void getMoney(int position) {
        if (map.get(position)) {

            if (mDates.get(position).getGoods_state() != 1) {
                doubleMap.put(position, 0.00);
            } else {

                doubleMap.put(position, mapNumber.get(position) * mDates.get(position).getPrice());
            }

        } else {
            doubleMap.put(position, 0.0);
        }
    }


    //设置是否全选
    public void isSelectAll(boolean b) {
        if (mDates != null) {
            for (int i = 0; i < mDates.size(); i++) {
                if (map.get(i) == null) {
                    map.put(i, b);
                }

                map.put(i, b);
                if (mapNumber.get(i) == null) {
                    if (mDates.get(i).getGoods_state() != 1) {
                        mapNumber.put(i, 0);
                    } else {
                        mapNumber.put(i, mDates.get(i).getGoods_num());
                    }
                }
                getMoney(i);
            }
        }

        double totalMoney = getTotalMoney();
        myListener.onGetTotalPrice(totalMoney + "");
        notifyDataSetChanged();
    }


    //获取总金额
    public double getTotalMoney() {
        double mone = 0.0;

        if (mDates != null) {
            for (int i = 0; i < mDates.size(); i++) {
                if (map.get(i) == null) {
                    map.put(i, false);
                }


                if (mapNumber.get(i) == null) {
                    if (mDates.get(i).getGoods_state() != 1) {
                        mapNumber.put(i, 0);
                    } else {
                        mapNumber.put(i, mDates.get(i).getGoods_num());
                    }

                }


                if (doubleMap.get(i) == null) {
                    doubleMap.put(i, 0.0);
                }


                if (map.get(i)) {
                    mone = doubleMap.get(i) + mone;
                }

            }


        } else {
            mone = 0.00;
        }
        return mone;
    }

    //判断是否为全选
    public boolean isAllSelect() {

        if (mDates != null) {
            for (int i = 0; i < mDates.size(); i++) {
                if (map.get(i) == null) {
                    map.put(i, false);
                }

                if (!map.get(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    //返回选中商品的拼接字符串
    public String getSelectedData() {

        String carData = "";
        StringBuffer buffer = new StringBuffer();
        if (mDates != null) {
            for (int i = 0; i < mDates.size(); i++) {
                if (map.get(i) == null) {

                    if (mDates.get(i).getGoods_state() != 1) {
                        map.put(i, false);
                    }
                    map.put(i, false);
                }


                if (mapNumber.get(i) == null) {
                    if (mDates.get(i).getGoods_state() != 1) {
                        mapNumber.put(i, 0);
                    } else {
                        mapNumber.put(i, mDates.get(i).getGoods_num());
                    }

                }

                if (doubleMap.get(i) == null) {
                    doubleMap.put(i, 0.0);
                }


                if (map.get(i)) {
                    if (mDates.get(i).getGoods_state() == 1) {
                        carData = mDates.get(i).getGoods_id() + "," + mDates.get(i).getSpec_id() + "," + mapNumber.get(i) + ";";
                        buffer.append(carData);
                    }
                }
            }
        }

        return buffer.toString();
    }


    //重置
    public void reSet() {
        if (map != null) {
            map.clear();
        }

        if (map != null) {
            mapNumber.clear();
        }
    }

    public class MyViewHolder_dd extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView mTxt_title, mTxt_today, mTxt_money, mTxt_danwei;
        private AddView add_number;
        private View itemView;
        private RelativeLayout rl1;

        public MyViewHolder_dd(View itemView) {
            super(itemView);
            this.itemView = itemView;
            checkBox = (CheckBox) itemView.findViewById(R.id.cb);
            mTxt_title = (TextView) itemView.findViewById(R.id.mTxt_title);
            mTxt_money = (TextView) itemView.findViewById(R.id.mTxt_money);
            mTxt_danwei = (TextView) itemView.findViewById(R.id.mTxt_danwei);
            mTxt_today = (TextView) itemView.findViewById(R.id.mTxt_today);
            add_number = (AddView) itemView.findViewById(R.id.add_number);
            rl1 = (RelativeLayout) itemView.findViewById(R.id.rl1);
        }
    }
}
