package com.bm.chengshiyoutian.youlaiwang.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.constant.Constant.store;

/**
 * Created by Administrator on 2017/9/19.
 */

public class SharedPUtils {

    public static void storeString(Context context, String name, String val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("dd_json", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(name, val).commit();
    }

    public static String getVal(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("dd_json", Context.MODE_PRIVATE);
        String val = sharedPreferences.getString(name, "");
        return val;
    }

    public static void storeBoolean(Context context,String name,boolean s){
        SharedPreferences sharedPreferences = context.getSharedPreferences("dd_json", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(name, s).commit();
    }

    public static boolean getBoolean(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("dd_json", Context.MODE_PRIVATE);
        boolean val = sharedPreferences.getBoolean(name, false);
        return val;
    }
}
