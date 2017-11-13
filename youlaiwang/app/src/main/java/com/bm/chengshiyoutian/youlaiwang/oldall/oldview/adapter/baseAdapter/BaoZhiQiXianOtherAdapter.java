package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.UniversalViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.DialogSelectTimeInterface;

import java.util.List;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/24 18:39
 *
 * @Description: 保质期限适配器
 */
public class BaoZhiQiXianOtherAdapter extends BaseAdapter {

    private Context mContext;
    private DialogSelectTimeInterface mDialogSelectTime;
    private List<RepositoryBillBean> repositoryBillBeans;

    public BaoZhiQiXianOtherAdapter(Context context, DialogSelectTimeInterface dialogSelectTime) {
        mContext = context;
        mDialogSelectTime = dialogSelectTime;
    }

    public void setData(List<RepositoryBillBean> repositoryBillBeans) {
        this.repositoryBillBeans = repositoryBillBeans;
    }

    @Override
    public int getCount() {
//        if (repositoryBillBeans == null) {
//            return 5;
//        } else {
            return repositoryBillBeans != null ? repositoryBillBeans.size() : 0;
//        }
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
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_baozhi_qixian, null);
        }
        TextView tv_add_baozhi_qixian = UniversalViewHolder.get(convertView, R.id.tv_add_baozhi_qixian);
        TextView tv_shengchan_pihao = UniversalViewHolder.get(convertView, R.id.tv_shengchan_pihao);
        TextView tv_product_name = UniversalViewHolder.get(convertView, R.id.tv_product_name);
        TextView tv_product_num = UniversalViewHolder.get(convertView, R.id.tv_product_num);
        TextView tv_product_unit = UniversalViewHolder.get(convertView, R.id.tv_product_unit);

        if (repositoryBillBeans != null) {
            RepositoryBillBean repositoryBillBean = repositoryBillBeans.get(position);
            tv_product_name.setText(repositoryBillBean.productName);
            tv_product_num.setText(repositoryBillBean.productNum);
            tv_product_unit.setText(repositoryBillBean.productNnit);
            if (!TextUtils.isEmpty(repositoryBillBean.productTime)) {
                tv_add_baozhi_qixian.setText(repositoryBillBean.productTime);
                tv_add_baozhi_qixian.setTextColor(Color.WHITE);
            } else {
                tv_add_baozhi_qixian.setText(Html.fromHtml("<u>" + mContext.getString(R.string.add_baozhiqixian) + "</u>"));
                tv_add_baozhi_qixian.setTextColor(mContext.getResources().getColor(R.color.yello));
            }
            if (!TextUtils.isEmpty(repositoryBillBean.productPiHao)) {
                tv_shengchan_pihao.setText(repositoryBillBean.productPiHao);
                tv_shengchan_pihao.setTextColor(Color.WHITE);
            } else {
                tv_shengchan_pihao.setText(Html.fromHtml("<u>" + mContext.getString(R.string.add_shenchanpihao) + "</u>"));
                tv_shengchan_pihao.setTextColor(mContext.getResources().getColor(R.color.yello));
            }
        }
        setListener(tv_add_baozhi_qixian, tv_shengchan_pihao, position);
        return convertView;
    }

    private void setListener(final TextView tv_add_baozhi_qixian, final TextView tv_shengchan_pihao, final int position) {
        tv_add_baozhi_qixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogSelectTime != null) {
                    mDialogSelectTime.selectOtherTime(0, tv_add_baozhi_qixian, position);
                }
            }
        });
        //生产批号
        tv_shengchan_pihao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogSelectTime != null) {
                    mDialogSelectTime.selectOtherTime(1, tv_shengchan_pihao, position);
                }
            }
        });
    }
}
