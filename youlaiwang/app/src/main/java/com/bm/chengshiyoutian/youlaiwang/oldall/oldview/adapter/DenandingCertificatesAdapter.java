package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.DemandingCertificatesBeans;

import java.util.ArrayList;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/11 13:45
 *
 * @Description: 历史记录的适配器
 */
public class DenandingCertificatesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<DemandingCertificatesBeans.DemandingCertificates> message;

    public DenandingCertificatesAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(ArrayList<DemandingCertificatesBeans.DemandingCertificates> message) {
        this.message = message;
    }

    @Override
    public int getCount() {
        if (message != null) {
            if (message.size() >= 10) {
                return 10;
            } else {
                return message.size();
            }
        }
        return 0;
    }

    @Override
    public DemandingCertificatesBeans.DemandingCertificates getItem(int position) {
        return message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pop, null);
            //convertView.setBackgroundColor(mContext.getResources().getColor(R.color.green_bg));
        }
        TextView tv_title = UniversalViewHolder.get(convertView, R.id.tv_title);
        DemandingCertificatesBeans.DemandingCertificates demandingCertificates = message.get(position);
        tv_title.setText(demandingCertificates.name);
        tv_title.setTextColor(Color.BLACK);
        tv_title.setPadding(DPtoPX(10, mContext), DPtoPX(10, mContext), DPtoPX(10, mContext), DPtoPX(10, mContext));
        tv_title.setSingleLine();
        return convertView;
    }

    /**
     * 将dp类型的尺寸转换成px类型的尺寸
     *
     * @param size
     * @param context
     * @return
     */
    public int DPtoPX(int size, Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return (int) ((float) size * metrics.density + 0.5);
    }
}
