package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtil {
	/**
	 * 获取首选项
	 * 
	 * @param context
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences("config1", Context.MODE_PRIVATE);
	}

	/**
	 * 获取String值
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context, String key) {
		SharedPreferences sp = getSharedPreferences(context);
		String result = sp.getString(key, null);
		return result;
	}

	/**
	 * 获取boolean值
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key) {
		SharedPreferences sp = getSharedPreferences(context);
		boolean result = sp.getBoolean(key, false);
		return result;
	}

	/**
	 * 获取int值
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getInt(Context context, String key) {
		SharedPreferences sp = getSharedPreferences(context);
		int result = sp.getInt(key, 0);
		return result;
	}

	/**
	 * 存储数据,仅限于String,Integer,Boolean
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void put(Context context, String key, Object value) {
		SharedPreferences sp = getSharedPreferences(context);
		Editor editor = sp.edit();
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		}
		editor.commit();
	}
}
