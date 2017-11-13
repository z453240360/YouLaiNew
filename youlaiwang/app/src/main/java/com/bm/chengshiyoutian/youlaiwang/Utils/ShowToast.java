package com.bm.chengshiyoutian.youlaiwang.Utils;

import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;


/**
 * Toast工具类
 * Created by Cloud_android on 2017/4/7.
 */

public class ShowToast {
        private static Toast toast;

        public static void showToast(String text){
            if(toast==null){
                toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
            }else {
                toast.setText(text);//如果不为空，则直接改变当前toast的文本
            }
            toast.show();
    }
}
