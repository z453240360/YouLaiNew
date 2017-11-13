package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;




/**
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @author wujm
 * @Description 查看大图
 * @date 2015-3-12 下午4:29:41
 */
public class BigImageJiLuDialog extends Dialog implements ViewPager.OnPageChangeListener {

    private Context context;
    private ViewPager vp_big_image;
    private LinearLayout ll_point;
    private RelativeLayout ll_whole_dialog_bigImage;
    private List<String> comment_pics;//评论图片
    private int selectIndex;//那张图片选中了
    private BigImageAdapter bigImageAdapter;
    private ImageLoader imageLoader;

    public BigImageJiLuDialog(Context context, List<String> comment_pics, int selectIndex, ImageLoader imageLoader) {
        super(context, R.style.Theme_MyDialog);// 使用样式
        this.context = context;
        this.comment_pics = comment_pics;
        this.selectIndex = selectIndex;
        this.imageLoader = imageLoader;
        setContentView(R.layout.dialog_big_image);
        findViewById();// 初始控件
        setContent();
        setProperty();// 设置对话框显示属性
        //屏蔽返回键
        this.setOnKeyListener(new OnKeyListener() {
                                  @Override
                                  public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                                      //默认返回 false
                                      return i == KeyEvent.KEYCODE_BACK;
                                  }
                              }

        );
    }

    /**
     * 初始控件
     */
    private void findViewById() {
        ImageView iv_left = (ImageView) findViewById(R.id.iv_left);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        vp_big_image = (ViewPager) findViewById(R.id.vp_big_image);
        ll_point = (LinearLayout) findViewById(R.id.ll_point);
        /*ll_whole_dialog_bigImage = (RelativeLayout) findViewById(R.id.ll_whole_dialog_bigImage);
        ll_whole_dialog_bigImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });*/
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 设置显示内容
     */
    public void setContent() {
        bigImageAdapter = new BigImageAdapter();
        vp_big_image.setAdapter(bigImageAdapter);
        if (comment_pics != null) {
            bigImageAdapter.setPoint(comment_pics.size());
        }
        vp_big_image.setOnPageChangeListener(this);
        vp_big_image.setCurrentItem(selectIndex);
    }

    /**
     * 设置事件
     *
     * @param
     */
    public void setOnClickListener(final int selectindex) {
    }

    /**
     * 设置对话框窗体属性
     */
    private void setProperty() {
        //setCanceledOnTouchOutside(false);// 对话框以外无法取消
        setCancelable(true);
        int h = this.context.getResources().getDisplayMetrics().heightPixels;
        int w = this.context.getResources().getDisplayMetrics().widthPixels;
        if (h < w) {
            w = h;
        }
        Window window = getWindow();//得到对话框的窗口．
        WindowManager.LayoutParams lp = window.getAttributes();
        // 中间
        lp.x = 0;// 这两句设置了对话框的位置．0为中间
        // 宽度
        lp.width = w;// 对话框的宽 占屏幕
        // 高
        // lp.height = lp.WRAP_CONTENT;// 对话框的高包裹内容
        lp.height = h;
        // 透明度
        // wl.alpha = 0.9f;// 这句设置了对话框的透明度
        // 布局相对位置
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int count = ll_point.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = ll_point.getChildAt(i);
            if (i == position) {
                view.setEnabled(false);
            } else {
                view.setEnabled(true);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class BigImageAdapter extends PagerAdapter {

        private int mChildCount = 0;

        @Override
        public int getCount() {
            /*if (comment_pics != null) {
                if (comment_pics.size() >= 4) {
                    return 4;
                } else {
                    return comment_pics.size();
                }
            }
            return 0;*/
            if (comment_pics != null) {
                return comment_pics.size();
            }
            return 0;
        }

        public void setPoint(int pointNum) {
            // 添加点
            /*if (pointNum >= 4) {
                pointNum = 4;
            }*/
            ll_point.removeAllViews();
            if (pointNum > 1) {
                for (int i = 0; i < pointNum; i++) {
                    View view = new View(context);
                    view.setId(i);// 给小点设置id
                    view.setTag(i);// 给tag
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(14, 14);
                    params.rightMargin = 10;
                    view.setLayoutParams(params);// 小圆点布局参数
                    view.setBackgroundResource(R.drawable.point_bg);
                    ll_point.addView(view);
                }
                // 第一个点不可用
                ll_point.getChildAt(0).setEnabled(false);
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /*@Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }*/

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //imageView.setBackground(context.getResources().getDrawable(R.drawable.cake_banner));
            //imageLoader.displayImage(comment_pics.get(position), imageView, App.getInstance().getWallOptions());
            imageLoader.displayImage(comment_pics.get(position), imageView, MyApplication.getInstance().getOptions());
            container.addView(imageView);
           /* imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });*/
            return imageView;
        }
    }
}