package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by youj on 2016/1/7.
 */
public class MyRelativeLayout extends RelativeLayout {

    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);//不要拦截事件
                //用户手指按下    停止切换
                break;
            case MotionEvent.ACTION_MOVE:

                getParent().requestDisallowInterceptTouchEvent(true);//拦截事件
                break;
            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }


        return super.dispatchTouchEvent(ev);
    }
}
