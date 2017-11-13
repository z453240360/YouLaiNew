package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

/**
 * Created by Administrator on 2015/8/28 0028.
 */
public class BaseBannerBean {
    public String id;

    public String url;

    public String content;

    public BaseBannerBean(String id, String url, String content) {
        this.id = id;
        this.url = url;
        this.content = content;
    }

    @Override
    public String toString() {
        return "BaseBannerBean{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
