package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.DingDanXiangQingBean;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;




/**
 * Created by sld on 2017/5/26.
 */

public class DingDanXiangQingAdapter2 extends BaseAdapter {

    List<DingDanXiangQingBean.DataBean.GoodsBean> goods;
    private Context mContext;
    String Authorizatio;

    public DingDanXiangQingAdapter2(List<DingDanXiangQingBean.DataBean.GoodsBean> goods, Context mContext, String authorizatio) {
        this.goods = goods;
        this.mContext = mContext;
        Authorizatio = authorizatio;
    }

    @Override
    public int getCount() {
        return goods.size();
    }

    @Override
    public Object getItem(int position) {
        return goods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)

    {
        View view;
        final int[] x = {goods.get(position).getGoods_num()};
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_dingdanxiangqing_content, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.danwei = (TextView) view.findViewById(R.id.danwei);
            viewHolder.jiage = (TextView) view.findViewById(R.id.jiage);
            viewHolder.tv_count = (TextView) view.findViewById(R.id.tv_count);


            viewHolder.tv_xiaozongjia = (TextView) view.findViewById(R.id.tv_xiaozongjia);

            viewHolder.tv_cha = (TextView) view.findViewById(R.id.tv_cha);

            viewHolder.iv = (ImageView) view.findViewById(R.id.iv);

            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }


        viewHolder.tv_name.setText(goods.get(position).getGoods_name() + "");
        viewHolder.danwei.setText(goods.get(position).getGoods_attr_text() + "");
        DecimalFormat format = new DecimalFormat("#0.00");
        viewHolder.jiage.setText("¥ " + format.format(goods.get(position).getGoods_pay_price()) + "");
        viewHolder.tv_count.setText("x" + goods.get(position).getGoods_num() + "");
        Glide.with(mContext)
                .load(goods.get(position).getGoods_cover()).centerCrop()
                .into(viewHolder.iv);

        viewHolder.tv_cha.setText("x"+goods.get(position).getGoods_num() + "");
        double goods_pay_price = goods.get(position).getGoods_pay_price();
        viewHolder.tv_xiaozongjia.setText("¥ "+format.format(goods.get(position).getGoods_num() * goods_pay_price) + "");
//        Log.e("价格价格价格价格价格价格",goods.get(position).getGoods_pay_price() + "-" + goods.get(position).getGoods_num() * goods_pay_price);
        viewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ShangPinXiangQingActivity.class);
                intent.putExtra("token", goods.get(position).getGoods_id() + "");
                mContext.startActivity(intent);
            }
        });

        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ShangPinXiangQingActivity.class);
                intent.putExtra("token", goods.get(position).getGoods_id() + "");
                mContext.startActivity(intent);
            }
        });


        return view;

    }

    public class ViewHolder {

        public TextView tv_name, danwei, jiage, tv_count, tv_xiaozongjia, tv_cha;

        ImageView iv;

    }
}
