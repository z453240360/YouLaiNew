package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.bm.chengshiyoutian.youlaiwang.R;

/**
 * Created by Administrator on 2017/10/23.
 */

public class Dialog_Company {


    private static Dialog_Company instance = null;


    private Dialog_Company() {
    }

    /* 1:懒汉式，静态工程方法，创建实例 */
    public static Dialog_Company getInstance() {
        if (instance == null) {
            instance = new Dialog_Company();
        }
        return instance;
    }

    public static void getDialog(Context context) {
        //弹出框以及对应的控件功能
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_company, null);
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(view);
        dialog.setCancelable(false);
    }


}
