package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class MyItemPagerAdapter extends PagerAdapter {
    private List<Integer> list = new ArrayList<>();
    private Context _context;
    public MyItemPagerAdapter(Context context , List<Integer> list) {
        this.list = list;
        this._context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view =new ImageView(_context);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setImageResource(list.get(position));
       // Glide.with(_context).load(list.get(position)).into(view);
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}