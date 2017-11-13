package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/24.
 */

public class QiYeRenZhengBean {
    /**
     * code : 200
     * data : {"class_names":"1","company_license":["http://oqv8tlktu.bkt.clouddn.com/FjWshSVI25BQgVg9x42N9T1E_37k","http://oqv8tlktu.bkt.clouddn.com/FjWshSVI25BQgVg9x42N9T1E_37k","http://oqv8tlktu.bkt.clouddn.com/Fso1lVJjNOg3P5zUM8XEugNyFmEm"],"company_linkman":"5","company_mobile":"1","company_name":"1"}
     * msg : 更新昵称成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public static class DataBean {
        /**
         * class_names : 1
         * company_license : ["http://oqv8tlktu.bkt.clouddn.com/FjWshSVI25BQgVg9x42N9T1E_37k","http://oqv8tlktu.bkt.clouddn.com/FjWshSVI25BQgVg9x42N9T1E_37k","http://oqv8tlktu.bkt.clouddn.com/Fso1lVJjNOg3P5zUM8XEugNyFmEm"]
         * company_linkman : 5
         * company_mobile : 1
         * company_name : 1
         */


        private String company_name;
        private String company_linkman;
        private String company_mobile;
        private String class_names;
        private List<String> company_license;
        private String food_license_number;
        private String food_license_period;
        private String company_license_number;
        private String company_license_period;
        private String province_id;
        private String city_id;
        private String area_id;
        private String company_phone;
        private String sign;
        private String company_areainfo;
        private String company_healthy_period;

        public String getCompany_healthy_number() {
            return company_healthy_number;
        }

        public void setCompany_healthy_number(String company_healthy_number) {
            this.company_healthy_number = company_healthy_number;
        }

        public String getCompany_healthy_period() {
            return company_healthy_period;
        }

        public void setCompany_healthy_period(String company_healthy_period) {
            this.company_healthy_period = company_healthy_period;
        }

        private String company_healthy_number;

        public String getFood_license_number() {
            return food_license_number;
        }

        public void setFood_license_number(String food_license_number) {
            this.food_license_number = food_license_number;
        }

        public String getFood_license_period() {
            return food_license_period;
        }

        public void setFood_license_period(String food_license_period) {
            this.food_license_period = food_license_period;
        }

        public String getCompany_license_number() {
            return company_license_number;
        }

        public void setCompany_license_number(String company_license_number) {
            this.company_license_number = company_license_number;
        }

        public String getCompany_license_period() {
            return company_license_period;
        }

        public void setCompany_license_period(String company_license_period) {
            this.company_license_period = company_license_period;
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

        public String getCompany_phone() {
            return company_phone;
        }

        public void setCompany_phone(String company_phone) {
            this.company_phone = company_phone;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getCompany_areainfo() {
            return company_areainfo;
        }

        public void setCompany_areainfo(String company_areainfo) {
            this.company_areainfo = company_areainfo;
        }

        public String getClass_names() {
            return class_names;
        }

        public void setClass_names(String class_names) {
            this.class_names = class_names;
        }

        public String getCompany_linkman() {
            return company_linkman;
        }

        public void setCompany_linkman(String company_linkman) {
            this.company_linkman = company_linkman;
        }

        public String getCompany_mobile() {
            return company_mobile;
        }

        public void setCompany_mobile(String company_mobile) {
            this.company_mobile = company_mobile;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public List<String> getCompany_license() {
            return company_license;
        }

        public void setCompany_license(List<String> company_license) {
            this.company_license = company_license;
        }
    }
}
