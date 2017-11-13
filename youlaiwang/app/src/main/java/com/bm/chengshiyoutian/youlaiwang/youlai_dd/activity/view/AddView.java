package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;

/**
 * Created by Administrator on 2017/10/10.
 */

public class AddView extends FrameLayout {

    private ImageView img_add, img_jian;
    private TextView mTxt_number;
    private int max = 10000;
    private int min = 0;

    public ImageView getImg_add() {
        return img_add;
    }

    public ImageView getImg_jian() {
        return img_jian;
    }

    public TextView getmTxt_number() {
        return mTxt_number;
    }

    public AddView(Context context) {
        this(context, null);
    }

    public AddView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_add, null);
        addView(view);
        img_add = (ImageView) view.findViewById(R.id.img_jia);
        img_jian = (ImageView) view.findViewById(R.id.img_jian);
        mTxt_number = (TextView) view.findViewById(R.id.number);


        img_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addNumber(max,min);
            }
        });

        img_jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                reduceNumber(max,min);
            }
        });
    }


    public void setmTxt_number(int number, int max, int min) {
        if (number <= 0) {
            mTxt_number.setText("0");
        } else if (number < max && number > min) {
            mTxt_number.setText(number + "");
        } else if (number > max) {
            mTxt_number.setText(max + "");
        }
    }

    //加法
    public void addNumber(int max, int min){

        String s = mTxt_number.getText().toString().trim();
        int i = Integer.parseInt(s)+1;
        if (i>=max){
            mTxt_number.setText(max + "");
        }else if (i<min&&min>=0){
            mTxt_number.setText(min+"");
        }else {
            mTxt_number.setText(i + "");
        }
    }


    //减法
    public void reduceNumber(int max, int min){
        String s = mTxt_number.getText().toString().trim();
        int i = Integer.parseInt(s)-1;
        if(i>=0){
            if (i>=max){
                mTxt_number.setText(max + "");
            }else if (i<min&&min>=0){
                mTxt_number.setText(min+"");
            }else {
                mTxt_number.setText(i + "");
            }
        }else {
            mTxt_number.setText("0");
        }

    }


}
