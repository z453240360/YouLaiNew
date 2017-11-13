package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.TouchContinuousChangeNumView;

import java.util.ArrayList;




/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/24 16:06
 *
 * @Description: 原料票据适配器
 */
public class BillDataAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<RepositoryBillBean> repositoryBillBeans;

    public BillDataAdapter(Context context) {
        this.mContext = context;
    }

    public ArrayList<RepositoryBillBean> getData() {
        return repositoryBillBeans;
    }

    public void setData(ArrayList<RepositoryBillBean> repositoryBillBeans) {
        this.repositoryBillBeans = repositoryBillBeans;
    }

    @Override
    public int getCount() {
        return repositoryBillBeans != null ? repositoryBillBeans.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.item_bill_data, null);
        TextView tv_product_name = UniversalViewHolder.get(convertView, R.id.tv_product_name);
        TextView tv_product_unit = UniversalViewHolder.get(convertView, R.id.tv_product_unit);
        TouchContinuousChangeNumView cv_product_num = UniversalViewHolder.get(convertView, R.id.cv_product_num);
        if (repositoryBillBeans != null) {
            final RepositoryBillBean repositoryBillBean = repositoryBillBeans.get(position);
            tv_product_name.setText(repositoryBillBean.productName);
            tv_product_unit.setText(repositoryBillBean.productNnit);
            cv_product_num.setDataNum(repositoryBillBean);
            cv_product_num.setContext(mContext);
            cv_product_num.setChangeProductNumInterface(new TouchContinuousChangeNumView.ChangeProductNumInterface() {
                @Override
                public void changeNum(TextView mTv_bill_data) {
                    String data = mTv_bill_data.getText().toString();
                    repositoryBillBean.productNum = data;
                    Log.i("BillDataAdapter输入内容", data);
                }
            });
        }
        return convertView;
    }
}
