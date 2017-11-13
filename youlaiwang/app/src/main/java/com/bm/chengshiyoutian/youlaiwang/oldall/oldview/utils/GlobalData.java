package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GlobalData {
    private static SharedPreferences sp;
    private static Editor ed;

    /**
     * @return void
     * @author 杨杰
     * @Description 缓存全局数据
     * @date 2014-12-9 下午4:23:41
     */
    public static void cacheData(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences("youlaiwang", Context.MODE_PRIVATE);
        }
        if (ed == null) {
            ed = sp.edit();
        }
        ed.putString(key, value);
        ed.commit();
    }


    /**
     * @return String
     * @author 杨杰
     * @Description 根据对于的key值获取对于的全局的值
     * @date 2014-12-9 下午4:30:31
     */
    public static String getData(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences("youlaiwang", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }
}
