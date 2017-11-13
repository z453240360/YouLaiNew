package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.TouchContinuousChangeNumView;

import java.util.ArrayList;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/25 15:41
 *
 * @Description:
 */
public class RepositoryBillAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<RepositoryBillBean> mRepositoryBillBeans;

    public RepositoryBillAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mRepositoryBillBeans != null ? mRepositoryBillBeans.size() : 0;
    }

    public ArrayList<RepositoryBillBean> getData() {
        return mRepositoryBillBeans;
    }

    public void setData(ArrayList<RepositoryBillBean> repositoryBillBeans) {
        this.mRepositoryBillBeans = repositoryBillBeans;
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
        convertView = View.inflate(mContext, R.layout.item_repository_bill, null);
        EditText et_product_name = UniversalViewHolder.get(convertView, R.id.et_product_name);
        EditText et_product_unit = UniversalViewHolder.get(convertView, R.id.et_product_unit);
        TouchContinuousChangeNumView cv_product_num = UniversalViewHolder.get(convertView, R.id.cv_product_num);

        final RepositoryBillBean repositoryBillBean = mRepositoryBillBeans.get(position);

        et_product_name.setText(repositoryBillBean.productName);
        et_product_unit.setText(repositoryBillBean.productNnit);
        cv_product_num.setDataNum(repositoryBillBean);
        cv_product_num.setContext(mContext);
        setTextChangeListener(et_product_name, et_product_unit, repositoryBillBean);
        cv_product_num.setChangeProductNumInterface(new TouchContinuousChangeNumView.ChangeProductNumInterface() {
            @Override
            public void changeNum(TextView mTv_bill_data) {
                String data = mTv_bill_data.getText().toString();
                repositoryBillBean.productNum = data;
            }
        });
        return convertView;
    }

    private void setTextChangeListener(EditText et_product_name, EditText et_product_unit, final RepositoryBillBean repositoryBillBean) {
        et_product_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                repositoryBillBean.productName = s.toString();
            }
        });
        et_product_unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                repositoryBillBean.productNnit = s.toString();
                repositoryBillBean.productAllNnit = s.toString();


            }
        });
    }
}
