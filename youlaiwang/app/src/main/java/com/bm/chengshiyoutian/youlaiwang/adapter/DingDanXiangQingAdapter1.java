package com.bm.chengshiyoutian.youlaiwang.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.activity.DianPuShouYeActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.DingDanXiangQingBean;
import com.bm.chengshiyoutian.youlaiwang.view.MylistView;


/**
 * Created by sld on 2017/5/26.
 */

public class DingDanXiangQingAdapter1 extends BaseAdapter {

    SharedPreferences sp;


    public DingDanXiangQingAdapter1(DingDanXiangQingBean.DataBean data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
        sp = mContext.getSharedPreferences(MyRes.CONFIG, 0);

    }

    DingDanXiangQingBean.DataBean data;

    private Context mContext;


    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        final int x = position;
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_dingdanxiangxing_title, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);

            viewHolder.lv = (MylistView) view.findViewById(R.id.lv);
            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, DianPuShouYeActivity.class);
                intent.putExtra(MyRes.DIANPU_ID, data.getShop_id() + "");

                sp.edit().putString(MyRes.DIANPU_ID, data.getShop_id() + "").commit();

                // Log.d("dian", dianPuXiangQingBean.getData().getData().get(position).getStore_id() + "xxxxxxxxxxxxxxxxxx");
                mContext.startActivity(intent);

            }
        });


        viewHolder.name.setText(data.getShop_name());


        final DingDanXiangQingAdapter2 gouWuCheAdapter2 = new DingDanXiangQingAdapter2(data.getGoods(), mContext, sp.getString(MyRes.CONFIG, ""));
        viewHolder.lv.setAdapter(gouWuCheAdapter2);


        return view;

    }

    public class ViewHolder {

        public TextView name;
        MylistView lv;


    }


}
