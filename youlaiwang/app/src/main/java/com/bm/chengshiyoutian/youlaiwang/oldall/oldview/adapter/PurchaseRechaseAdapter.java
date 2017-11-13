package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.ProcurementRecordNewBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/4/20 15:58
 *
 * @Description: 采购记录
 */
public class PurchaseRechaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProcurementRecordNewBean> procurementRecordNewBeans;
    private ImageLoader imageload;

    public PurchaseRechaseAdapter(Context context) {
        this.context = context;
        this.imageload = ImageLoader.getInstance();
    }

    public void setData(ArrayList<ProcurementRecordNewBean> procurementRecordNewBeans) {
        this.procurementRecordNewBeans = procurementRecordNewBeans;
    }

    @Override
    public int getCount() {
        return procurementRecordNewBeans != null ? procurementRecordNewBeans.size() : 0;
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
            convertView = View.inflate(context, R.layout.item_purchase_record, null);
        }
        TextView tv_time = UniversalViewHolder.get(convertView, R.id.tv_time);
        View view_line = UniversalViewHolder.get(convertView, R.id.view_line);
        TextView tv_type = UniversalViewHolder.get(convertView, R.id.tv_type);
        TextView tv_name = UniversalViewHolder.get(convertView, R.id.tv_name);
        TextView tv_supplier_name = UniversalViewHolder.get(convertView, R.id.tv_supplier_name);
        ImageView iv_store_photo = UniversalViewHolder.get(convertView, R.id.iv_store_photo);
        View view_line_shuxian = UniversalViewHolder.get(convertView, R.id.view_line_shuxian);
        LinearLayout ll_biaoji = UniversalViewHolder.get(convertView, R.id.ll_biaoji);
        TextView tv_time_year = UniversalViewHolder.get(convertView, R.id.tv_time_year);

        ProcurementRecordNewBean procurementRecordNewBean = procurementRecordNewBeans.get(position);

        if (TextUtils.isEmpty(procurementRecordNewBean.goodsTypeId)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_type.getLayoutParams();
            layoutParams.weight = 1;
            tv_type.setLayoutParams(layoutParams);
            view_line_shuxian.setVisibility(View.GONE);
            tv_type.setText("票据-暂无明细");
            tv_name.setText("");
        } else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_type.getLayoutParams();
            layoutParams.weight = 1;
            tv_type.setLayoutParams(layoutParams);
            view_line_shuxian.setVisibility(View.VISIBLE);
            tv_type.setText(procurementRecordNewBean.goodsTypeId);
            tv_name.setText(procurementRecordNewBean.number + "" + procurementRecordNewBean.unit);
        }
        tv_supplier_name.setText(procurementRecordNewBean.supplierName);
        //imageload.displayImage(procurementRecordNewBean.licenseImg, iv_store_photo, App.getInstance().getOptions());
        if (procurementRecordNewBean.billImageLists != null && procurementRecordNewBean.billImageLists.size() > 0) {
            iv_store_photo.setVisibility(View.VISIBLE);
            imageload.displayImage(procurementRecordNewBean.billImageLists.get(0), iv_store_photo, MyApplication.getInstance().getOptions());
        } else {
            iv_store_photo.setVisibility(View.GONE);
        }
        tv_time.setText(procurementRecordNewBean.time);

        tv_time.setVisibility(View.VISIBLE);
        view_line.setVisibility(View.GONE);

        tv_time_year.setText(procurementRecordNewBean.year);
        ll_biaoji.setVisibility(View.VISIBLE);

        if (position > 0) {
            ProcurementRecordNewBean procurementRecordNewBean1 = procurementRecordNewBeans.get(position);
            ProcurementRecordNewBean procurementRecordNewBean2 = procurementRecordNewBeans.get(position - 1);
            if (procurementRecordNewBean1.time.equals(procurementRecordNewBean2.time)) {
                tv_time.setVisibility(View.GONE);
                view_line.setVisibility(View.VISIBLE);
            }
            if (!MyUtils.isEmpty(procurementRecordNewBean1.year)) {
                if ((procurementRecordNewBean1.year).equals(procurementRecordNewBean2.year)) {
                    ll_biaoji.setVisibility(View.GONE);
                } else {
                    ll_biaoji.setVisibility(View.VISIBLE);
                }
            }
        }
        return convertView;
    }
}
