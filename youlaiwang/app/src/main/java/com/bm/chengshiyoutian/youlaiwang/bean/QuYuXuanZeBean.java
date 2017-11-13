package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/17.
 */

public class QuYuXuanZeBean {

    /**
     * data : [{"area_id":110100,"area_name":"北京市","area_parent_id":110000,"area_sort":0,"area_show":1,"area_hot":1},{"area_id":310100,"area_name":"上海市","area_parent_id":310000,"area_sort":0,"area_show":1,"area_hot":1}]
     * msg :
     * code : 200
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area_id : 110100
         * area_name : 北京市
         * area_parent_id : 110000
         * area_sort : 0
         * area_show : 1
         * area_hot : 1
         */

        private int area_id;
        private String area_name;
        private int area_parent_id;
        private int area_sort;
        private int area_show;
        private int area_hot;
        public boolean tag = false;

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public int getArea_parent_id() {
            return area_parent_id;
        }

        public void setArea_parent_id(int area_parent_id) {
            this.area_parent_id = area_parent_id;
        }

        public int getArea_sort() {
            return area_sort;
        }

        public void setArea_sort(int area_sort) {
            this.area_sort = area_sort;
        }

        public int getArea_show() {
            return area_show;
        }

        public void setArea_show(int area_show) {
            this.area_show = area_show;
        }

        public int getArea_hot() {
            return area_hot;
        }

        public void setArea_hot(int area_hot) {
            this.area_hot = area_hot;
        }
    }
}
