package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Copyright © 2015 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/2/23 13:55
 *
 * @Description: 极光推送
 */
public class JPushAlias {

    private final int MSG_SET_ALIAS = 1;
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("JPushAlias", "设置别名成功");
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };
    private Context context;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("TAG", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(context,
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i("TAG", "Unhandled msg - " + msg.what);
            }
        }
    };

    //设置别名
    public void setAlias(Context context, String userId) {
        this.context = context;
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, userId));
    }
}
