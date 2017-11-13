package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class VideoBean {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }


    public static List<VideoBean> getList(JSONArray array){
        List<VideoBean> list = new ArrayList<VideoBean>();
        JSONObject obj = null;
        VideoBean bean =  null;

        for(int i = 0,count = array.length();i<count;i++){
            bean = new VideoBean();
            obj = array.optJSONObject(i);
           bean.setName(obj.optString("zhaiyao"));
            bean.setUrl(obj.optString("link_url"));

            list.add(bean);
        }

        return list;
    }
}
