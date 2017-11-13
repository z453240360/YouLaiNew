package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.bm.chengshiyoutian.youlaiwang.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 日期时间选择控件
 */
public abstract class DateTimePickDialogUtil implements OnDateChangedListener,
        OnTimeChangedListener {
    public DatePicker datePicker;
    private AlertDialog ad;
    private String dateTime;
    private Dialog dialog;

    /**
     * 日期时间弹出选择框构造函数
     */
    public DateTimePickDialogUtil(Context context, TextView inputDate) {
        setDialog(context, inputDate);

    }

    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
    }

    // 日期对话框
    public void setDialog(final Context context, final TextView inputDate) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_datetime,
                null);
        dialog = new Dialog(context, R.style.dialog_time);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        datePicker = (DatePicker) view.findViewById(R.id.datepicker);
        datePicker
                .setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);//设置不让点击输入
        TextView quxiao = (TextView) view.findViewById(R.id.tv_qx_dialog);
        TextView queren = (TextView) view.findViewById(R.id.tv_qr_dialog);
        dialog.show();// 显示对话框

        // 取消按钮的操作
        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 隐藏取消对话框
                dialog.dismiss();
                dialog.cancel();

            }
        });
        // 确认对话框
        queren.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int state = setQrClick();
                if (0 == state) {
                    // 获得日历实例
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(datePicker.getYear(), datePicker.getMonth(),
                            datePicker.getDayOfMonth());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    dateTime = sdf.format(calendar.getTime());
                    inputDate.setText(dateTime);
                } else {
                    //ToastUtil.showToast(context, "修改失败，请检查网络");
                }
                // 隐藏消失对话框
                dialog.dismiss();
                dialog.cancel();
            }
        });
    }

    //点击确认按钮
    protected abstract int setQrClick();

    //获取选择的日期
    public String getPicker() {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateTime = sdf.format(calendar.getTime());
        return dateTime;
    }


    //-------------
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
    }


}
