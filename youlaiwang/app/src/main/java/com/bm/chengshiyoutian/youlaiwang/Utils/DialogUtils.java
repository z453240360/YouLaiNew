package com.bm.chengshiyoutian.youlaiwang.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.bm.chengshiyoutian.youlaiwang.R;


/**
 * Created by 禽兽强 on 2017/1/24.
 */

public class DialogUtils {

    /**
     * 弹出一个自定义对话框
     * @param view
     */
    public static AlertDialog ShowDialog(View view, Context context) {
        AlertDialog builder = new AlertDialog.Builder(context).create();
        builder.setCanceledOnTouchOutside(false);
        builder.show();
        builder.getWindow().setGravity(Gravity.CENTER);

        builder.getWindow().setContentView(view);
        builder.getWindow().setLayout((int) (context.getResources().getDimension(R.dimen.dp320)),
                ViewGroup.LayoutParams.WRAP_CONTENT);

        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return builder;
    }


    /**
     * 弹出一个自定义对话框
     * @param view
     */
    public static AlertDialog ShowDialog2(View view, Context context) {
        AlertDialog builder = new AlertDialog.Builder(context).create();
        builder.setCanceledOnTouchOutside(false);
        builder.show();
        builder.getWindow().setGravity(Gravity.CENTER);
        builder.getWindow().setContentView(view);
        builder.getWindow().setLayout((int) (context.getResources().getDimension(R.dimen.dp345)),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.getWindow().setBackgroundDrawableResource(R.drawable.dialogbg);

        return builder;
    }



}
