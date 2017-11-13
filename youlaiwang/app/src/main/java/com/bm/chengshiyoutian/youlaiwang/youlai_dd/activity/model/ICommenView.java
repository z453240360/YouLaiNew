package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface ICommenView {
    void getFirstData(String s);
    void showLoading();
    void cancelLoading();
    void showFaliure(String s);
    void getSecond(String s);
}
