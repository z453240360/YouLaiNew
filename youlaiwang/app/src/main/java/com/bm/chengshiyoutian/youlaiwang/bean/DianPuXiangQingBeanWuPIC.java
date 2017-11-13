package com.bm.chengshiyoutian.youlaiwang.bean;

/**
 * Created by sld on 2017/6/16.
 */

public class DianPuXiangQingBeanWuPIC {


    /**
     * code : 200
     * data : {"company_license":"","create_time":"2017-06-08","detail_sign":"http://oqv8tlktu.bkt.clouddn.com/c86ce63e069eacfdae320afe69b3b615.jpg","is_favorites":2,"pca_info":"","store_id":51,"store_linkman":"QQ：3157878 方总","store_logo":"http://oqv8tlktu.bkt.clouddn.com/24aafa94718fb8686043d1b3a3a0b233.jpg","store_name":"帝龙国际红酒直供中心","store_phone":"13357001818","store_synopsis":"各类国际知名品牌红酒，批发零售，欢迎选购"}
     * msg :
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
         * company_license :
         * create_time : 2017-06-08
         * detail_sign : http://oqv8tlktu.bkt.clouddn.com/c86ce63e069eacfdae320afe69b3b615.jpg
         * is_favorites : 2
         * pca_info :
         * store_id : 51
         * store_linkman : QQ：3157878 方总
         * store_logo : http://oqv8tlktu.bkt.clouddn.com/24aafa94718fb8686043d1b3a3a0b233.jpg
         * store_name : 帝龙国际红酒直供中心
         * store_phone : 13357001818
         * store_synopsis : 各类国际知名品牌红酒，批发零售，欢迎选购
         */

        private String company_license;
        private String create_time;
        private String detail_sign;
        private int is_favorites;
        private String pca_info;
        private int store_id;
        private String store_linkman;
        private String store_logo;
        private String store_name;
        private String store_phone;
        private String store_synopsis;

        public String getCompany_license() {
            return company_license;
        }

        public void setCompany_license(String company_license) {
            this.company_license = company_license;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDetail_sign() {
            return detail_sign;
        }

        public void setDetail_sign(String detail_sign) {
            this.detail_sign = detail_sign;
        }

        public int getIs_favorites() {
            return is_favorites;
        }

        public void setIs_favorites(int is_favorites) {
            this.is_favorites = is_favorites;
        }

        public String getPca_info() {
            return pca_info;
        }

        public void setPca_info(String pca_info) {
            this.pca_info = pca_info;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_linkman() {
            return store_linkman;
        }

        public void setStore_linkman(String store_linkman) {
            this.store_linkman = store_linkman;
        }

        public String getStore_logo() {
            return store_logo;
        }

        public void setStore_logo(String store_logo) {
            this.store_logo = store_logo;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(String store_phone) {
            this.store_phone = store_phone;
        }

        public String getStore_synopsis() {
            return store_synopsis;
        }

        public void setStore_synopsis(String store_synopsis) {
            this.store_synopsis = store_synopsis;
        }
    }
}
