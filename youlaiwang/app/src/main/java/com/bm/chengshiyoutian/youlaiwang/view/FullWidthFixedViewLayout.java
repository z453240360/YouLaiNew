package com.bm.chengshiyoutian.youlaiwang.view;

import android.content.Context;
import android.widget.FrameLayout;

public class FullWidthFixedViewLayout extends FrameLayout {
    public HeaderGridView hd;

        public FullWidthFixedViewLayout(Context context, HeaderGridView headerGridView) {
            super(context);
            hd = headerGridView;
        }  
  
        @Override  
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
            int targetWidth = hd.getMeasuredWidth()
                    - hd.getPaddingLeft()
                    - hd.getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(targetWidth,  
                    MeasureSpec.getMode(widthMeasureSpec));  
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        }  
    }  