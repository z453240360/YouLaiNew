package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ZhenDianPuXiangQingBean {
    /**
     * data : {"store_id":30,"store_name":"浙江餐道网络科技有限公司","store_linkman":"菜菜","store_phone":"15058232031","store_logo":"http://oqv8tlktu.bkt.clouddn.com/af5da0780f45b0843cc15abc8de51f4c.jpg","detail_sign":"http://oqv8tlktu.bkt.clouddn.com/f4dbe37b73b243819368c3fdc7282dbd.jpg","company_license":["http://oqv8tlktu.bkt.clouddn.com/f542522376957a2277306687e0c5d7b9.jpg"],"store_synopsis":"我们多年来提供新鲜蔬菜水果","pca_info":"浙江省-衢州市-柯城区","create_time":"2017-06-04","is_favorites":2}
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
         * store_id : 30
         * store_name : 浙江餐道网络科技有限公司
         * store_linkman : 菜菜
         * store_phone : 15058232031
         * store_logo : http://oqv8tlktu.bkt.clouddn.com/af5da0780f45b0843cc15abc8de51f4c.jpg
         * detail_sign : http://oqv8tlktu.bkt.clouddn.com/f4dbe37b73b243819368c3fdc7282dbd.jpg
         * company_license : ["http://oqv8tlktu.bkt.clouddn.com/f542522376957a2277306687e0c5d7b9.jpg"]
         * store_synopsis : 我们多年来提供新鲜蔬菜水果
         * pca_info : 浙江省-衢州市-柯城区
         * create_time : 2017-06-04
         * is_favorites : 2
         */

        private int store_id;
        private String store_name;
        private String store_linkman;
        private String store_phone;
        private String store_logo;
        private String detail_sign;
        private String store_synopsis;
        private String pca_info;
        private String create_time;
        private int is_favorites;
        private List<String> company_license;

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_linkman() {
            return store_linkman;
        }

        public void setStore_linkman(String store_linkman) {
            this.store_linkman = store_linkman;
        }

        public String getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(String store_phone) {
            this.store_phone = store_phone;
        }

        public String getStore_logo() {
            return store_logo;
        }

        public void setStore_logo(String store_logo) {
            this.store_logo = store_logo;
        }

        public String getDetail_sign() {
            return detail_sign;
        }

        public void setDetail_sign(String detail_sign) {
            this.detail_sign = detail_sign;
        }

        public String getStore_synopsis() {
            return store_synopsis;
        }

        public void setStore_synopsis(String store_synopsis) {
            this.store_synopsis = store_synopsis;
        }

        public String getPca_info() {
            return pca_info;
        }

        public void setPca_info(String pca_info) {
            this.pca_info = pca_info;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_favorites() {
            return is_favorites;
        }

        public void setIs_favorites(int is_favorites) {
            this.is_favorites = is_favorites;
        }

        public List<String> getCompany_license() {
            return company_license;
        }

        public void setCompany_license(List<String> company_license) {
            this.company_license = company_license;
        }
    }
}
