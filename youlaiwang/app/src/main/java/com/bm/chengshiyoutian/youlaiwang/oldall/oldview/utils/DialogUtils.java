package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.DialogActionInterface;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/25 09:56
 *
 * @Description: 对话框工具类
 */
public class DialogUtils {

    private AlertDialog alertDialog;

    /**
     * @param context
     * @param activity
     */
    public void showDialogOrSure(String message, Context context, final Activity activity, final DialogActionInterface dialogAction) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context).create();
        }
        View inflate = View.inflate(context, R.layout.dialog_sure, null);
        alertDialog.show();
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        TextView tv_tip_message = (TextView) inflate.findViewById(R.id.tv_tip_message);
        LinearLayout ll_action = (LinearLayout) inflate.findViewById(R.id.ll_action);
        TextView tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        TextView tv_sure = (TextView) inflate.findViewById(R.id.tv_sure);
        tv_tip_message.setText(message);
        alertDialog.getWindow().setLayout(width * 4 / 5, LinearLayout.LayoutParams.WRAP_CONTENT);// 弹框的宽，高
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setContentView(inflate);
        // 按钮监听
        tv_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialogAction != null) {
                    dialogAction.sureAction();
                }
                alertDialog.cancel();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogAction != null) {
                    dialogAction.cancelAction();
                }
                alertDialog.cancel();
            }
        });
    }
}
