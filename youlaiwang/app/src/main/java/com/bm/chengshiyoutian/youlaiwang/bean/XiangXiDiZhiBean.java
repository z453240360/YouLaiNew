package com.bm.chengshiyoutian.youlaiwang.bean;

/**
 * Created by sld on 2017/5/23.
 */

public class XiangXiDiZhiBean {
    /**
     * data : {"address_id":12,"name":"黎明","province_id":"0","city_id":"0","area_id":"0","cityinfo":"北京北京崇文区","areainfo":"规范讲的是","mobile":"15683424932","is_default":2}
     * msg :
     * code : 200
     */

    private DataBean data;
    private String msg;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * address_id : 12
         * name : 黎明
         * province_id : 0
         * city_id : 0
         * area_id : 0
         * cityinfo : 北京北京崇文区
         * areainfo : 规范讲的是
         * mobile : 15683424932
         * is_default : 2
         */

        private int address_id;
        private String name;
        private String province_id;
        private String city_id;
        private String area_id;
        private String cityinfo;
        private String areainfo;
        private String mobile;
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

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }
}
