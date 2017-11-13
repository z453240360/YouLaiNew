package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 *
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Description 土丝工具类
 * @author wujm
 * @CreateDate 2015-5-6 下午5:02:30
 * @ModifiedBy 修改人中文名或拼音缩写
 * @ModifiedDate 修改日期格式YYYY-MM-DD
 * @WhyModified 改原因描述
 */
public class MyToastUtils {

	/**
	 * 土丝显示间隔
	 */
	private static long intervalTime = 2000;
	/**
	 * 上一次显示时间
	 */
	private static long lastTime;

	/**
	 * 土丝提示
	 *
	 * @param context
	 * @param text
	 */
	public static void show(Context context, String text) {
		long nowTime = System.currentTimeMillis();
		if (nowTime - lastTime < intervalTime) {
			return;
		}
		lastTime = nowTime;
		if (context != null) {
			Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	/**
	 * 土丝提示
	 *
	 * @param context
	 * @param id
	 */
	public static void show(Context context, int id) {
		long nowTime = System.currentTimeMillis();
		if (nowTime - lastTime < intervalTime) {
			return;
		}
		lastTime = nowTime;
		Toast.makeText(context, context.getResources().getString(id),
				Toast.LENGTH_SHORT).show();
	}

}
