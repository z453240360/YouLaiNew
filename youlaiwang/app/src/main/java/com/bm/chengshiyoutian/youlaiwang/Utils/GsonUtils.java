package com.bm.chengshiyoutian.youlaiwang.Utils;

import com.google.gson.Gson;

/**
 *  gson单例
 * Created by Cloud_android on 2017/4/14.
 */

public class GsonUtils {

    public static Gson getInstance(){
        Gson gson = null;
        if (null==gson){
            gson = new Gson();
        }
        return gson;
    }
}
