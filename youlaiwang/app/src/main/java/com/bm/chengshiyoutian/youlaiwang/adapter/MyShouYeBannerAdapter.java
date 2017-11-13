package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.chengshiyoutian.youlaiwang.bean.ShouYeBean;
import com.bumptech.glide.Glide;

import java.util.List;



/**
 * Created by Administrator on 2016/11/3.
 */
public class MyShouYeBannerAdapter extends PagerAdapter {
    List<ShouYeBean.DataBean.BannerBean> banner;
    private Context _context;

    public MyShouYeBannerAdapter(List<ShouYeBean.DataBean.BannerBean> banner, Context _context) {
        this.banner = banner;
        this._context = _context;
    }

    @Override
    public int getCount() {
        return banner.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view =new ImageView(_context);
        view.setScaleType(ImageView.ScaleType.FIT_XY);

        Glide.with(_context).load(banner.get(position).getBanner_cover()).into(view);
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}