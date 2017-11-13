package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.CarBean_new;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.callback.IAddCarBack;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.CatUtils;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.DataForma_dd;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/31.
 */

public class EXpandAdapter extends BaseExpandableListAdapter {

    private List<CarBean_new.DataBeanX.DataBean> mDatas = new ArrayList<>();
    private Context mContext;
    private SharedPreferences sp;
    private String token = "";

    public EXpandAdapter(List<CarBean_new.DataBeanX.DataBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        sp = mContext.getSharedPreferences(MyRes.TOKEN, 0);

        String string = sp.getString(MyRes.TOKEN, "");

        token = "Bearer " + string;
    }

    @Override
    public int getGroupCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mDatas.get(i).getGoods() == null ? 0 : mDatas.get(i).getGoods().size();
    }

    @Override
    public Object getGroup(int i) {
        return mDatas.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mDatas.get(i).getGoods().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i + i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    //获取父条目的显示
    @Override
    public View getGroupView(final int postion, boolean b, View view, ViewGroup viewGroup) {
        final GroupHolder groupHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.expand_1, viewGroup, false);
            groupHolder = new GroupHolder(view);
            view.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) view.getTag();
        }

        groupHolder.shopName.setText(mDatas.get(postion).getStore_name());//设置商店名字
        groupHolder.minPay.setText("满" + mDatas.get(postion).getMin_pay_money() + "元免邮费");//设置邮费满减

        //商店词条的点击事件
        groupHolder.shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBackData.getGroupListClicked(postion);
            }
        });


        //本条目选中商品的总价格
        double dongdong = getDongdong(postion);

        //设置邮费情况
        if (dongdong >= mDatas.get(postion).getMin_pay_money()) {
            groupHolder.postMoney.setText("免邮费");
            groupHolder.postMoney.setTextColor(Color.parseColor("#76c933"));
        } else {
            groupHolder.postMoney.setText("邮费：" + mDatas.get(postion).getLogistics_cost() + "元");
            groupHolder.postMoney.setTextColor(Color.parseColor("#555555"));
        }


        //监听选择框的选中事件
        groupHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDatas.get(postion).isFlag()) {
                    mDatas.get(postion).setSelect(false);
                    groupHolder.checkBox.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
                    mDatas.get(postion).setFlag(false);
                    setAllChildSelect(postion, false);

                } else {
                    mDatas.get(postion).setSelect(true);
                    groupHolder.checkBox.setImageResource(R.mipmap.shezhi_weixuanzhong);
                    mDatas.get(postion).setFlag(true);
                    setAllChildSelect(postion, true);
                }

                if (isGroupAllSelected()) {
                    if (callBackData != null) {
                        callBackData.getAllSelected(postion, true);
                    }
                } else {
                    if (callBackData != null) {
                        callBackData.getAllSelected(postion, false);
                    }
                }
            }
        });


        if (mDatas.get(postion).isSelect()) {
            groupHolder.checkBox.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
        } else {
            groupHolder.checkBox.setImageResource(R.mipmap.shezhi_weixuanzhong);
        }

        if (isGroupAllSelected()) {
            if (callBackData != null) {
                callBackData.getAllSelected(postion, true);
            }
        } else {
            if (callBackData != null) {
                callBackData.getAllSelected(postion, false);
            }
        }

        return view;
    }


    //获取到条目的总资费
    private double getDongdong(int postion) {
        double dongdong = 0;
        for (int i = 0; i < mDatas.get(postion).getGoods().size(); i++) {
            if (mDatas.get(postion).getGoods().get(i).isSelect()) {
                String price = mDatas.get(postion).getGoods().get(i).getPrice();
                double v = Double.parseDouble(price);
                double v1 = mDatas.get(postion).getGoods().get(i).getGoods_num() * v;
                dongdong = dongdong + v1;
            }
        }
        notifyDataSetChanged();
        return dongdong;
    }

    //获取每个条目的显示
    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        final ChildHolder childHolder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.expand_2, viewGroup, false);
            childHolder = new ChildHolder(view);
            view.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) view.getTag();
        }

        double v = Double.parseDouble(mDatas.get(i).getGoods().get(i1).getPrice());
        int goods_num = mDatas.get(i).getGoods().get(i1).getGoods_num();

        mDatas.get(i).getGoods().get(i1).setChildTotalPrice(goods_num * v);//设置条目的价格
        childHolder.name.setText(mDatas.get(i).getGoods().get(i1).getGoods_name());//商品的名字
        childHolder.jiage.setText("¥ " + mDatas.get(i).getGoods().get(i1).getPrice() + "");//设置价格
        childHolder.danwei.setText(mDatas.get(i).getGoods().get(i1).getSpec_text() + "");


        //设置图片
        Glide.with(mContext).load(mDatas.get(i).getGoods().get(i1).getGoods_cover()).into(childHolder.mImgCover);

        //多选框监听事件
        childHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                mDatas.get(i).getGoods().get(i1).setSelect(b);//设置选中状态

                if (b) {
                    double v = Double.parseDouble(mDatas.get(i).getGoods().get(i1).getPrice());//价格
                    int goods_num = mDatas.get(i).getGoods().get(i1).getGoods_num();//数量
                    mDatas.get(i).getGoods().get(i1).setChildTotalPrice(goods_num * v);//子条目的总价格
                    childHolder.money.setText(mDatas.get(i).getGoods().get(i1).getGoods_num() * v + "");//设置子条目的价格
                } else {
                    mDatas.get(i).getGoods().get(i1).setChildTotalPrice(0);
                }

                if (callBackData != null) {
                    callBackData.getTotalPrice(getTotalPrice());
                }

                if (isChildAllSelected(i)) {
                    mDatas.get(i).setFlag(true);
                    mDatas.get(i).setSelect(true);
                } else {
                    mDatas.get(i).setFlag(false);
                    mDatas.get(i).setSelect(false);
                }
                getDongdong(i);
            }
        });


        //数量增加
        childHolder.mImg_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childHolder.mImg_add.setClickable(false);

                int goods_id = mDatas.get(i).getGoods().get(i1).getGoods_id();
                int spec_id = mDatas.get(i).getGoods().get(i1).getSpec_id();

                CatUtils.addCar(1, goods_id, spec_id, 1, token, new IAddCarBack() {
                    @Override
                    public void successed(String code) {
                        String price = mDatas.get(i).getGoods().get(i1).getPrice();
                        double v = Double.parseDouble(price);
                        int goods_num = mDatas.get(i).getGoods().get(i1).getGoods_num();
                        mDatas.get(i).getGoods().get(i1).setGoods_num(goods_num + 1);//点击减少，变更商品数量
                        childHolder.tv_test.setText(mDatas.get(i).getGoods().get(i1).getGoods_num() + "");//设置商品数量
                        childHolder.money.setText(mDatas.get(i).getGoods().get(i1).getGoods_num() * v + "");
                        mDatas.get(i).getGoods().get(i1).setChildTotalPrice(mDatas.get(i).getGoods().get(i1).getGoods_num());
                        if (callBackData != null) {
                            callBackData.getChildAdd(i, i1);//商品数量减少回调
                            callBackData.getTotalPrice(getTotalPrice());//返回列表总价格
                        }

                        getDongdong(i);//获取本商店的总价格
                        mDatas.get(i).setTotalPrice(getChildTotalPrice(i));
                        childHolder.mImg_add.setClickable(true);
                    }

                    @Override
                    public void failed(String s) {
                        childHolder.mImg_add.setClickable(true);
                        Toast.makeText(mContext, "加入购物车失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //数量减少
        childHolder.mImg_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                childHolder.mImg_reduce.setClickable(false);

                int goods_id = mDatas.get(i).getGoods().get(i1).getGoods_id();
                int spec_id = mDatas.get(i).getGoods().get(i1).getSpec_id();

                CatUtils.addCar(1, goods_id, spec_id, 2, token, new IAddCarBack() {
                    @Override
                    public void successed(String code) {
                        double v = Double.parseDouble(mDatas.get(i).getGoods().get(i1).getPrice());
                        int goods_num = mDatas.get(i).getGoods().get(i1).getGoods_num();
                        if (goods_num <= 0) {
                            return;
                        }

                        mDatas.get(i).getGoods().get(i1).setGoods_num(goods_num - 1);//点击减少，变更商品数量
                        childHolder.tv_test.setText(mDatas.get(i).getGoods().get(i1).getGoods_num() + "");//设置商品数量
                        callBackData.getChildReduce(i, i1);//商品数量减少回调
                        callBackData.getTotalPrice(getTotalPrice());//返回列表总价格
                        childHolder.money.setText(mDatas.get(i).getGoods().get(i1).getGoods_num() * v + "");//子条目的价格

                        mDatas.get(i).setTotalPrice(getChildTotalPrice(i));
                        getDongdong(i);//获取本商店的总价格
                        childHolder.mImg_reduce.setClickable(true);
                    }

                    @Override
                    public void failed(String s) {
                        childHolder.mImg_reduce.setClickable(true);
                        Toast.makeText(mContext, "加入购物车失败", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        //设置多选框的选中与否
        childHolder.checkBox.setChecked(mDatas.get(i).getGoods().get(i1).isSelect());
        childHolder.money.setText("小计：" + DataForma_dd.getDoubleByDouble(mDatas.get(i).getGoods().get(i1).getChildTotalPrice()) + "元");


        //设置子列表的数量
        childHolder.tv_test.setText(mDatas.get(i).getGoods().get(i1).getGoods_num() + "");
        mDatas.get(i).setTotalPrice(getChildTotalPrice(i));
        return view;
    }


    //获取当前单个子条目的选中的条目的价格
    public double getChildTotalPrice(int i) {
        double totalPrice = 0;

        for (int j = 0; j < mDatas.get(i).getGoods().size(); j++) {
            if (mDatas.get(i).getGoods().get(j).isSelect()) {
                double childTotalPrice = Double.parseDouble(mDatas.get(i).getGoods().get(j).getPrice());
                totalPrice = totalPrice + childTotalPrice;
            }
        }
        return totalPrice;
    }

    //获取列表的总价格，待返回给主界面
    public double getTotalPrice() {
        double price = 0;

        for (int i = 0; i < mDatas.size(); i++) {
            for (int j = 0; j < mDatas.get(i).getGoods().size(); j++) {
                if (mDatas.get(i).getGoods().get(j).isSelect()) {
                    double childTotalPrice = mDatas.get(i).getGoods().get(j).getGoods_num() * Double.parseDouble(mDatas.get(i).getGoods().get(j).getPrice());
                    price = price + childTotalPrice;
                }
            }
        }
        return price;
    }


    //判断Group是否为全部选中
    public boolean isGroupAllSelected() {
        for (int i = 0; i < mDatas.size(); i++) {
            if (!mDatas.get(i).isSelect()) {
                return false;
            }
        }
        return true;
    }


    //判断子child是否被全部被选中
    public boolean isChildAllSelected(int postion) {
        for (int j = 0; j < mDatas.get(postion).getGoods().size(); j++) {
            if (!mDatas.get(postion).getGoods().get(j).isSelect()) {
                return false;
            }
        }
        notifyDataSetChanged();
        return true;
    }

    //设置Group全部选中
    public void setAllSelected(boolean b) {
        for (int i = 0; i < mDatas.size(); i++) {
            mDatas.get(i).setSelect(b);
            setAllChildSelect(i, b);
        }
        notifyDataSetChanged();
    }


    //设置子列表是否全选
    public void setAllChildSelect(int postion, boolean b) {
        for (int i = 0; i < mDatas.get(postion).getGoods().size(); i++) {
            mDatas.get(postion).getGoods().get(i).setSelect(b);
        }
        notifyDataSetChanged();
    }

    //获取选中条目的购物车编号id
    public String getCarId() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isSelect()) {

                for (int j = 0; j < mDatas.get(i).getGoods().size(); j++) {
                    if (mDatas.get(i).getGoods().get(j).isSelect()) {
                        int cart_id = mDatas.get(i).getGoods().get(j).getCart_id();

                        if (i != mDatas.size()) {
                            buffer.append(cart_id + ",");
                        } else {
                            buffer.append(cart_id);
                        }
                    }
                }
            }
        }
        return buffer.toString();
    }

    //接口回调
    public OnCallBackData callBackData;

    public void setCallBackData(OnCallBackData callBackData) {
        this.callBackData = callBackData;
    }

    public interface OnCallBackData {
        void getGroupListClicked(int postion);

        void getChildClicked(int groupPos, int childPos);

        void getChildAdd(int groupPos, int childPos);

        void getChildReduce(int groupPos, int childPos);

        void getChildedTotalPrice(int groupPos, int childPos, String price);

        void getTotalPrice(double s);

        void getAllSelected(int postion, boolean b);
    }

    //holder容器
    class GroupHolder {
        private ImageView checkBox;
        private TextView postMoney, minPay, shopName;

        public GroupHolder(View itemView) {
            shopName = (TextView) itemView.findViewById(R.id.shopName);
            postMoney = (TextView) itemView.findViewById(R.id.postMoney);
            minPay = (TextView) itemView.findViewById(R.id.minPay);
            checkBox = (ImageView) itemView.findViewById(R.id.isSelectedAll);
        }
    }

    class ChildHolder {
        private TextView tv_test, money, name, jiage, danwei;
        private ImageView mImg_add, mImg_reduce, mImgCover;
        private CheckBox checkBox;

        public ChildHolder(View itemView) {
            name = (TextView) itemView.findViewById(R.id.name);
            money = (TextView) itemView.findViewById(R.id.money);

            jiage = (TextView) itemView.findViewById(R.id.jiage);
            danwei = (TextView) itemView.findViewById(R.id.danwei);

            tv_test = (TextView) itemView.findViewById(R.id.mTxt_test);
            mImg_add = (ImageView) itemView.findViewById(R.id.mImg_add);
            mImgCover = (ImageView) itemView.findViewById(R.id.mImg_cover);

            mImg_reduce = (ImageView) itemView.findViewById(R.id.mImg_reduce);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }
}
