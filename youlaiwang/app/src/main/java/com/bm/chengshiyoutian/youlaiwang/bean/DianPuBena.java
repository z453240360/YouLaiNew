package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/23.
 */

public class DianPuBena {

    /**
     * data : {"total":2,"current_page":1,"data":[{"store_id":7,"store_name":"原创设计店铺","store_logo":"http://opq5wk7p7.bkt.clouddn.com/9c7824ecc35ff6ec1c7a7d467e0a0063.jpg","evaluate_total":5,"company_license_num":1,"class_names":"蔬菜水果,肉禽蛋类,粮油副食","delivery_radius":"111M","min_pay_money":22},{"store_id":15,"store_name":"店铺名称","store_logo":"http://opq5wk7p7.bkt.clouddn.com/","evaluate_total":5,"company_license_num":1,"class_names":"","delivery_radius":"M","min_pay_money":0}]}
     * msg :
     * code : 200
     */

    private DataBeanX data;
    private String msg;
    private int code;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
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

    public static class DataBeanX {
        /**
         * total : 2
         * current_page : 1
         * data : [{"store_id":7,"store_name":"原创设计店铺","store_logo":"http://opq5wk7p7.bkt.clouddn.com/9c7824ecc35ff6ec1c7a7d467e0a0063.jpg","evaluate_total":5,"company_license_num":1,"class_names":"蔬菜水果,肉禽蛋类,粮油副食","delivery_radius":"111M","min_pay_money":22},{"store_id":15,"store_name":"店铺名称","store_logo":"http://opq5wk7p7.bkt.clouddn.com/","evaluate_total":5,"company_license_num":1,"class_names":"","delivery_radius":"M","min_pay_money":0}]
         */

        private int total;
        private int current_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * store_id : 7
             * store_name : 原创设计店铺
             * store_logo : http://opq5wk7p7.bkt.clouddn.com/9c7824ecc35ff6ec1c7a7d467e0a0063.jpg
             * evaluate_total : 5
             * company_license_num : 1
             * class_names : 蔬菜水果,肉禽蛋类,粮油副食
             * delivery_radius : 111M
             * min_pay_money : 22
             */

            public  boolean tag1;
            public  boolean tag2;
            private int store_id;
            private String store_name;
            private String store_logo;
            private int evaluate_total;
            private int company_license_num;
            private String class_names;
            private String delivery_radius;
            private int min_pay_money;

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

            public String getStore_logo() {
                return store_logo;
            }

            public void setStore_logo(String store_logo) {
                this.store_logo = store_logo;
            }

            public int getEvaluate_total() {
                return evaluate_total;
            }

            public void setEvaluate_total(int evaluate_total) {
                this.evaluate_total = evaluate_total;
            }

            public int getCompany_license_num() {
                return company_license_num;
            }

            public void setCompany_license_num(int company_license_num) {
                this.company_license_num = company_license_num;
            }

            public String getClass_names() {
                return class_names;
            }

            public void setClass_names(String class_names) {
                this.class_names = class_names;
            }

            public String getDelivery_radius() {
                return delivery_radius;
            }

            public void setDelivery_radius(String delivery_radius) {
                this.delivery_radius = delivery_radius;
            }

            public int getMin_pay_money() {
                return min_pay_money;
            }

            public void setMin_pay_money(int min_pay_money) {
                this.min_pay_money = min_pay_money;
            }
        }
    }
}
