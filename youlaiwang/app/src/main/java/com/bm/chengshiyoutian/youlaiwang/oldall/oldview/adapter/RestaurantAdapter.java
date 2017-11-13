package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;



/**
 * Created by youj on 2016/1/14.
 */
public class RestaurantAdapter extends CommonAdapter<RestaurantBean> {

    private List<RestaurantBean> list;


    public RestaurantAdapter(Context context, List<RestaurantBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.list = mDatas;
    }

    @Override
    public void convert(ViewHolder helper, RestaurantBean item, int position) {
        ImageView photo = helper.getView(R.id.photo);
        TextView tv_title = helper.getView(R.id.tv_title);
        RatingBar ratingbar = helper.getView(R.id.ratingbar);
        TextView tv_address = helper.getView(R.id.tv_address);
        TextView tv_phone = helper.getView(R.id.tv_phone);
        TextView tv_distance = helper.getView(R.id.tv_distance);

        RestaurantBean bean = list.get(position);
        tv_address.setText(bean.address);
        tv_title.setText(bean.restaurantName);
        ImageLoader.getInstance().displayImage(bean.photoUrl, photo, MyApplication.getInstance().getOptions());
        tv_phone.setText(bean.restaurantPhone);
        tv_distance.setText(bean.distance+"km");
        String grade = "null".equals(bean.grade)?"0":bean.grade;
        ratingbar.setRating(Float.parseFloat(grade));
    }
}
