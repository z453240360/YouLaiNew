package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;


public class MyReciverEditText extends EditText {

Context context;
    public MyReciverEditText(Context context) {
        super(context);
    }

    public MyReciverEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
        final Intent intent = new Intent();

        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                       setText(s);
                        setSelection(s.length());

                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    setText(s);
                    setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        setText(s.subSequence(1, 2));
                        setSelection(1);

                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        setKeyListener(new NumberKeyListener() {
            // 0无键盘 1英文键盘 2模拟键盘 3数字键盘
            @Override
            public int getInputType() {

                return 3;
            }
            // 返回允许输入的字符
            @Override
            protected char[] getAcceptedChars() {

                char[] c = {'0','1','2','3','4','5','6','7','8','9','.'};
                return c;
            }
        });
    }

}

