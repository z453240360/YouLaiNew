package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils;

import android.widget.TextView;

import com.google.gson.Gson;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2017/11/8.
 */

public class Gsonutils {
    public static Object getBean(String s, Class T){
        Gson gson = new Gson();


        Object o = gson.fromJson(s, T);

        return o;
    }
}
