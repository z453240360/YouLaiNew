package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;


/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class PointDialog extends Dialog {

    private Context context;
    private String title;
    private String message;
    private ClickListenerInterface clickListenerInterface;


    public PointDialog(Context context, String title,
                       String message) {
        super(context, R.style.Dialog);
        this.context = context;
        this.title = title;
        this.message = message;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_message = (TextView) findViewById(R.id.message);
        TextView confirm = (TextView) findViewById(R.id.confirm);
        TextView cancel = (TextView) findViewById(R.id.cancel);
        tv_title.setText(title);
        tv_message.setText(message);
        confirm.setOnClickListener(new clickListener());
        cancel.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.confirm:
                    clickListenerInterface.doConfirm();
                    break;
                case R.id.cancel:
                    clickListenerInterface.doCancel();
                    break;
            }
        }

    }

    public interface ClickListenerInterface {

        void doConfirm();

        void doCancel();
    }
}
