package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

/**
 * Created by youj on 2016/1/21.
 */
public class PoPBean {

    public String id;


    public String channel_id;

    public String title;

    public PoPBean(String id, String channel_id, String title) {
        this.id = id;
        this.channel_id = channel_id;
        this.title = title;
    }

    public PoPBean(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
