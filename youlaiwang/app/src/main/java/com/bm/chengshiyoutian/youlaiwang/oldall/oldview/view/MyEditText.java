package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;


public class MyEditText extends EditText {

Context context;
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
//        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
                        setText(s.subSequence(0, 1));
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
    }

}

