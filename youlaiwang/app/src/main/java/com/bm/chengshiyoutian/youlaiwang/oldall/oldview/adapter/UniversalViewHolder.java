package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @author 高骅
 * @Description 通用ViewHolder
 * @date 2015-1-26 下午1:36:36
 */
public class UniversalViewHolder {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
