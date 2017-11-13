package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces;

import android.widget.TextView;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/25 09:59
 *
 * @Description: 对话框选择保质期限
 */
public interface DialogSelectTimeInterface {
    /**
     * @param type 0:保质期限 ； 1：生产批号
     */
    void selectTime(int type, TextView textView, int position);
    void selectOtherTime(int type, TextView textView, int position);
}
