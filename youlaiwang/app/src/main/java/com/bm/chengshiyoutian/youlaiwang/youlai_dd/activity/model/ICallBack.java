package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model;

/**
 * Created by Administrator on 2017/8/3.
 * 网络请求回调，成功和失败
 */

public interface ICallBack {
    void succesed(String s);
    void failed(String s);
    void netState(String s);
}
