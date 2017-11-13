package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bm.chengshiyoutian.youlaiwang.R;


public class UpDataDialogDialog extends Dialog implements View.OnClickListener {
    private ListView message;
    private OnClickListener mOnClickListener, mCancelLictener;
    private Button sure, cancel;

    public UpDataDialogDialog(Context context) {
        super(context, R.style.dialog);

    }

    public Button getCancel() {
        return cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_update);
        init();
    }

    private void init() {
        //title = (TextView) findViewById(R.id.title);
        message = (ListView) findViewById(R.id.message);
        sure = (Button) findViewById(R.id.sure);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
       // title.setText("提示");
    }

    public void setActionMessage(String sureMessage, String cancelMessage) {
        sure.setText(sureMessage);
        cancel.setText(cancelMessage);
    }

    public void showCancel() {
        cancel.setVisibility(View.VISIBLE);
        findViewById(R.id.fg).setVisibility(View.VISIBLE);
    }

    public void hideCancel() {
        findViewById(R.id.cancel).setVisibility(View.GONE);
        findViewById(R.id.fg).setVisibility(View.GONE);
    }



    public void setMessage(String message) {
        if (this.message != null) {
            String[] split = message.split("\r\n");
            this.message.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.dialogitem_text,split));

        }

    }

    public void setButtonSure(String text) {
        if (this.sure != null)
            this.sure.setText(text);
    }

    public void setButtonCancel(String text) {
        if (this.cancel != null)
            this.cancel.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v);
                }
                break;

            case R.id.cancel:
                if (mCancelLictener != null) {
                    mCancelLictener.onClick(v);
                }
                break;
        }
        dismiss();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public void setOnCanceClicklListener(OnClickListener listener) {
        this.mCancelLictener = listener;
    }

    public interface OnClickListener {
        void onClick(View v);
    }
}