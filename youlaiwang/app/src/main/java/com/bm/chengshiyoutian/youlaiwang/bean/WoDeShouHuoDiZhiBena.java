package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/23.
 */

public class WoDeShouHuoDiZhiBena {


    /**
     * data : [{"address_id":65,"name":"付云","mobile":"18530911214","cityinfo":"上海市上海市虹口区","areainfo":"凌云路街道","is_default":1},{"address_id":62,"name":"黎明3","mobile":"15683424932","cityinfo":"北京北京崇文区","areainfo":"规范讲的是","is_default":2},{"address_id":12,"name":"黎明1","mobile":"15683424932","cityinfo":"北京北京崇文区","areainfo":"规范讲的是","is_default":2}]
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
         * address_id : 65
         * name : 付云
         * mobile : 18530911214
         * cityinfo : 上海市上海市虹口区
         * areainfo : 凌云路街道
         * is_default : 1
         */

        private int address_id;
        private String name;
        private String mobile;
        private String cityinfo;
        private String areainfo;
        private int is_default;

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCityinfo() {
            return cityinfo;
        }

        public void setCityinfo(String cityinfo) {
            this.cityinfo = cityinfo;
        }

        public String getAreainfo() {
            return areainfo;
        }

        public void setAreainfo(String areainfo) {
            this.areainfo = areainfo;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }
}
