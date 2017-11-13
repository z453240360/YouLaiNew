package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;


/**
 * @author youj
 * 自定义dialog
 */
public class ProgressDialog extends Dialog {
    private TextView tv_loadingmsg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog_view);
        tv_loadingmsg = (TextView) findViewById(R.id.tv_loadingmsg);
        tv_loadingmsg.setText("正在加载中...");
        setCanceledOnTouchOutside(false);
    }

    public ProgressDialog(Context context) {
        super(context, R.style.ProgressDialog);
    }



}
