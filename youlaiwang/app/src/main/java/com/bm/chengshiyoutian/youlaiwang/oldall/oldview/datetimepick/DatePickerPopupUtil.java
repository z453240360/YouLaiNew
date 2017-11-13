package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.datetimepick;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.datepicker.OnWheelScrollListener;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.datepicker.WheelView;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.datepicker.viewadapter.NumericWheelAdapter;


/**
 * Created by dur on 2015/12/25.
 */
public class DatePickerPopupUtil implements View.OnClickListener {
    public PopupWindow popupWindow;
    //用户所选择的当前的生日
    public String birthday;
    //年月日滚轮
    private WheelView year;
    private WheelView month;
    private WheelView day;
    // 初始时间设置
    private int mYear = 2016;
    private int mMonth = 0;
    private int mDay = 1;
    //生日弹框中取消按钮
    private TextView tvBirthCancle;
    //生日弹框中完成按钮
    private TextView tvBirthComplete;
    //生日弹框点击背景取消弹框
    private View viewCancle;
    private Context context;
    // 年份 月份 天数都可以添加监听
    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            // 拼接生日
            // ===============================================
            birthday = new StringBuilder()
                    .append((year.getCurrentItem() + 2016))
                    .append("年")
                    .append((month.getCurrentItem() + 1) < 10 ? ""
                            + (month.getCurrentItem() + 1) : (month
                            .getCurrentItem() + 1))
                    .append("月")
                    .append(((day.getCurrentItem() + 1) < 10) ? ""
                            + (day.getCurrentItem() + 1) : (day
                            .getCurrentItem() + 1)).append("日").toString();

            initDay(year.getCurrentItem() + 2016, (month.getCurrentItem() + 1) < 10 ? +(month.getCurrentItem() + 1) : (month.getCurrentItem() + 1));
        }
    };
    private DateCallBck callBck;

    public DatePickerPopupUtil(Context context, DateCallBck callBck) {
        this.context = context;
        this.callBck = callBck;
    }

    public void setCurrent(int year, int month, int day) {
        if (year < 2016) {
            this.mYear = 2016;
        }
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.birthday = year + "年" + (month + 1) + "月" + day + "日";
    }

    public PopupWindow getDatePicker() {
        if (popupWindow == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.pop_birthday, null);
            popupWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.update();
            //=============================================================
            tvBirthCancle = (TextView) view.findViewById(R.id.tvBirthCancle);
            tvBirthComplete = (TextView) view.findViewById(R.id.tvBirthComplete);
            viewCancle = view.findViewById(R.id.viewCancle);
            //=============================================================
            tvBirthCancle.setOnClickListener(this);
            tvBirthComplete.setOnClickListener(this);
            viewCancle.setOnClickListener(this);
            //=============================================================
            int curYear = mYear;
            int curMonth = mMonth + 1;
            int curDate = mDay;
            year = (WheelView) view.findViewById(R.id.year);
            NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(
                    context, 2016, 2100);// 设置收尾时间点
            numericWheelAdapter1.setLabel("年");
            year.setViewAdapter(numericWheelAdapter1);
            year.setCyclic(true);// 是否可循环滑动
            year.addScrollingListener(scrollListener);
            // ===============================================
            month = (WheelView) view.findViewById(R.id.month);
            NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
                    context, 1, 12, "%1d");
            numericWheelAdapter2.setLabel("月");
            month.setViewAdapter(numericWheelAdapter2);
            month.setCyclic(true);
            month.addScrollingListener(scrollListener);
            // ===============================================
            day = (WheelView) view.findViewById(R.id.day);
            initDay(curYear, curMonth);
            day.setCyclic(true);
            day.addScrollingListener(scrollListener);
            // ===============================================
            /** 设置显示的行数 **/
            year.setVisibleItems(7);
            month.setVisibleItems(7);
            day.setVisibleItems(7);
            /** 设置当前初始时间 **/
            year.setCurrentItem(curYear - 2016);
            month.setCurrentItem(curMonth - 1);
            day.setCurrentItem(curDate - 1);
        }
        return popupWindow;
    }

    private void initDay(int arg1, int arg2) {// 根据年份和月份获取日的信息
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context,
                1, getDay(arg1, arg2), "%1d");
        numericWheelAdapter.setLabel("日");
        day.setViewAdapter(numericWheelAdapter);
    }

    // ===========获得每个月的天数==============
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //生日弹框取消
            case R.id.tvBirthCancle:

                popupWindow.dismiss();
                break;
            //生日弹框取消
            case R.id.viewCancle:

                popupWindow.dismiss();
                break;
            //生日弹框完成
            case R.id.tvBirthComplete:
                callBck.complete();
                popupWindow.dismiss();
                break;

        }
    }

    public interface DateCallBck {
        void complete();

    }
}
