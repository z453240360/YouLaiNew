package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import java.io.Serializable;

/**
 * Created by youj on 2016/1/12.
 */
public class NoticeBean implements Serializable {
    public String title;

    public String content;

    public String time;

    public String id;

    public NoticeBean(String id, String content, String title, String time) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.time = time;
    }

}
