package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.ProcurementRecordBean;

import java.util.ArrayList;



/**
 * Created by jayen on 16/1/13.
 */
public class ProcurementRecordAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProcurementRecordBean> list;

    public ProcurementRecordAdapter(Context context, ArrayList<ProcurementRecordBean> list) {
        this.list = list;
        this.context =context;
    }

    public ProcurementRecordAdapter(Context context) {
        this.context =context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 :list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position-1);
    }

    public void setDate(ArrayList<ProcurementRecordBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holder = null;
        if (convertView == null) {
            holder = new HolderView();
            convertView = LinearLayout.inflate(context, R.layout.item_procurement_record, null);
            holder.oil_num = (TextView) convertView.findViewById(R.id.tv_oil_num);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder  = (HolderView) convertView.getTag();
        }

        ProcurementRecordBean bean = list.get(position);
        holder.oil_num.setText(bean.oil_num + context.getResources().getString(R.string.L));
        holder.time.setText(bean.time);
        return convertView;
    }

    class HolderView {
        public TextView time;
        public TextView oil_num;
    }
}
