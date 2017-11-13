package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dongdong on 2017/9/29.
 */

public class MyDataView extends FrameLayout {

    private TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, mTxt_yesterday;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7;

    public RadioButton getRb1() {
        return rb1;
    }

    public RadioButton getRb2() {
        return rb2;
    }

    public RadioButton getRb3() {
        return rb3;
    }

    public RadioButton getRb4() {
        return rb4;
    }

    public RadioButton getRb5() {
        return rb5;
    }

    public RadioButton getRb6() {
        return rb6;
    }

    public RadioButton getRb7() {
        return rb7;
    }

    private List<TextView> txtList = new ArrayList<>();
    private List<RadioButton> rbList = new ArrayList<>();

    public MyDataView(Context context) {
        this(context, null);
    }

    public TextView getmTxt_yesterday() {
        return mTxt_yesterday;
    }

    public MyDataView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_duiying, null);
        addView(view);
        txt1 = (TextView) view.findViewById(R.id.week1);
        txt2 = (TextView) view.findViewById(R.id.week2);
        txt3 = (TextView) view.findViewById(R.id.week3);
        txt4 = (TextView) view.findViewById(R.id.week4);
        txt5 = (TextView) view.findViewById(R.id.week5);
        txt6 = (TextView) view.findViewById(R.id.week6);
        txt7 = (TextView) view.findViewById(R.id.week7);

        txtList.add(txt7);
        txtList.add(txt6);
        txtList.add(txt5);
        txtList.add(txt4);
        txtList.add(txt3);
        txtList.add(txt2);
        txtList.add(txt1);

        mTxt_yesterday = (TextView) view.findViewById(R.id.mTxt_yesterday);
        mTxt_yesterday.setText("");


        rb1 = ((RadioButton) view.findViewById(R.id.rb1));
        rb2 = ((RadioButton) view.findViewById(R.id.rb2));
        rb3 = ((RadioButton) view.findViewById(R.id.rb3));
        rb4 = ((RadioButton) view.findViewById(R.id.rb4));
        rb5 = ((RadioButton) view.findViewById(R.id.rb5));
        rb6 = ((RadioButton) view.findViewById(R.id.rb6));
        rb7 = ((RadioButton) view.findViewById(R.id.rb7));
        rbList.add(rb7);
        rbList.add(rb6);
        rbList.add(rb5);
        rbList.add(rb4);
        rbList.add(rb3);
        rbList.add(rb2);
        rbList.add(rb1);

    }

    //设置是否可见
    public void setWeeks(List<String> list){
        if (list!=null){
            for (int i = 0; i < list.size(); i++) {
                txtList.get(i).setText(getWeek(list.get(i)));
                txtList.get(i).setVisibility(VISIBLE);
                rbList.get(i).setVisibility(VISIBLE);
                rbList.get(i).setText(getDay(list.get(i))+"");
            }
        }
        mTxt_yesterday.setText("为什么看不见啊");
    }

    //根据日期获取星期几
    public String getWeek(String data){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(data);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int i = calendar.get(Calendar.DAY_OF_WEEK);

            switch (i){
                case 1:
                    return "日";
                case 2:
                    return "一";
                case 3:
                    return "二";
                case 4:
                    return "三";
                case 5:
                    return "四";
                case 6:
                    return "五";
                case 7:
                    return "六";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    //根据日期获取是几号
    public int getDay(String data){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(data);


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int i = calendar.get(Calendar.DAY_OF_MONTH);
            return i;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getData(String data){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(data);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int i = calendar.get(Calendar.DAY_OF_MONTH);//获取日期
            int i1 = calendar.get(Calendar.MONTH)+1;//获取月份
            int i2 = calendar.get(Calendar.YEAR);//获取年
            String week = getWeek(data);
            return i2+"年"+i1+"月"+i+"日 周"+week;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return "";
    }




}
