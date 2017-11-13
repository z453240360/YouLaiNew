package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.SuoZhengSuoPiaoRecordActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.SuoZhenSuoPiao;

import java.util.List;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/4/20 15:58
 *
 * @Description: 采购记录
 */
public class PurchaseRechaseAdapter1 extends BaseAdapter {

    private Context context;
private List<SuoZhenSuoPiao.DataBean> data;
    public List<SuoZhenSuoPiao.DataBean.DatePurchaseListBean> datePurchaseList;
    public PurchaseRechaseAdapter1(Context context) {
        this.context = context;
    }

    public void setData(List<SuoZhenSuoPiao.DataBean> data) {
        this.data = data;
        List<SuoZhenSuoPiao.DataBean.DatePurchaseListBean> datePurchaseList = data.get(0).getDatePurchaseList();
        this.datePurchaseList = datePurchaseList;
    }

    @Override
    public int getCount() {
        if (data != null&&data.size() > 0) {
            return data.get(0).getDatePurchaseList().size();
        } else {
            return 0;
        }
    }

    @Override
    public SuoZhenSuoPiao.DataBean.DatePurchaseListBean getItem(int position) {
        return data.get(0).getDatePurchaseList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_purchase_record1, null);
        }
        TextView tv_time = UniversalViewHolder.get(convertView, R.id.tv_time);
        View view_line = UniversalViewHolder.get(convertView, R.id.view_line);
        LinearLayout ll_biaoji = UniversalViewHolder.get(convertView, R.id.ll_biaoji);
        TextView tv_time_year = UniversalViewHolder.get(convertView, R.id.tv_time_year);
        Button btn1 = UniversalViewHolder.get(convertView, R.id.btn1);
        tv_time_year.setText(data.get(0).getYear());

        if (position > 0) {
            ll_biaoji.setVisibility(View.GONE);
        } else {
            ll_biaoji.setVisibility(View.VISIBLE);
        }
        //设置时间
        tv_time.setText(datePurchaseList.get(position).getDate());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuoZhenSuoPiao.DataBean.DatePurchaseListBean datePurchaseListBean = datePurchaseList.get(position);
                String s = GsonUtils.getInstance().toJson(datePurchaseListBean);
                Intent intent = new Intent(context, SuoZhengSuoPiaoRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("json", s);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
