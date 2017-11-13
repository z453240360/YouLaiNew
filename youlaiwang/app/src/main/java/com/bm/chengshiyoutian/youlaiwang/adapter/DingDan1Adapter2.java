package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.DingDan1Bean;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;



/**
 * Created by sld on 2017/5/26.
 */

public class DingDan1Adapter2 extends BaseAdapter {

    List<DingDan1Bean.DataBeanX.DataBean.OrderGoodsBean> order_goods;
    String Authorizatio;
    Context mContext;

    public DingDan1Adapter2(List<DingDan1Bean.DataBeanX.DataBean.OrderGoodsBean> order_goods, Context mContext, String authorizatio) {
        this.order_goods = order_goods;
        this.mContext = mContext;
        Authorizatio = authorizatio;
    }

    @Override
    public int getCount() {
        return order_goods.size();
    }

    @Override
    public Object getItem(int position) {
        return order_goods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)

    {
        View view;
        final int[] x = {order_goods.get(position).getGoods_num()};
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_dingdan_content, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.danwei = (TextView) view.findViewById(R.id.danwei);
            viewHolder.jiage = (TextView) view.findViewById(R.id.jiage);
            viewHolder.tv_zongjine = (TextView) view.findViewById(R.id.tv_zongjine);

            viewHolder.tv_count = (TextView) view.findViewById(R.id.tv_count);
            viewHolder.iv = (ImageView) view.findViewById(R.id.iv);

            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }

        viewHolder.tv_count.setText("x" + order_goods.get(position).getGoods_num() + "");
        viewHolder.tv_name.setText(order_goods.get(position).getGoods_name() + "");
        viewHolder.danwei.setText(order_goods.get(position).getGoods_attr_text() + "");
        DecimalFormat    df   = new DecimalFormat("######0.00");

        viewHolder.jiage.setText("¥" + df.format(order_goods.get(position).getGoods_price()) + "");
        int goods_num = order_goods.get(position).getGoods_num();
        double v1 = order_goods.get(position).getGoods_price() * goods_num;
        //double v = Double.parseDouble();

        String format = df.format(v1);

        viewHolder.tv_zongjine.setText("¥" +  df.format(v1) + "");
        Glide.with(mContext)
                .load(order_goods.get(position).getGoods_cover()).centerCrop()
                .into(viewHolder.iv);


        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShangPinXiangQingActivity.class);
                intent.putExtra("token", order_goods.get(position).getGoods_id() + "");
                mContext.startActivity(intent);
            }
        });
        viewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, ShangPinXiangQingActivity.class);
                intent.putExtra("token", order_goods.get(position).getGoods_id() + "");
                mContext.startActivity(intent);
            }
        });
        return view;

    }

    public class ViewHolder {

        public TextView tv_name, danwei, jiage, tv_zongjine, tv_count;

        ImageView iv;

    }
}
