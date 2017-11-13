package com.bm.chengshiyoutian.youlaiwang.bean;

/**
 * Created by sld on 2017/5/22.
 */

public class WoDeBean {
    /**
     * data : {"avatar":"http://oqv8tlktu.bkt.clouddn.com/FmLGEHWDH-PeFeDuLneIdcziGW7t","user_nicename":"呵呵哒","collect_goods":5,"collect_store":3,"bug_goods":2,"bug_store":1,"pay_order_num":19,"deliver_num":4,"receipt_num":0,"comment_num":0,"siteMobile":"4007167186"}
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
         * avatar : http://oqv8tlktu.bkt.clouddn.com/FmLGEHWDH-PeFeDuLneIdcziGW7t
         * user_nicename : 呵呵哒
         * collect_goods : 5
         * collect_store : 3
         * bug_goods : 2
         * bug_store : 1
         * pay_order_num : 19
         * deliver_num : 4
         * receipt_num : 0
         * comment_num : 0
         * siteMobile : 4007167186
         */

        private String avatar;
        private String user_nicename;
        private int collect_goods;
        private int collect_store;
        private int bug_goods;
        private int bug_store;
        private int pay_order_num;
        private int deliver_num;
        private int receipt_num;
        private int comment_num;
        private String siteMobile;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUser_nicename() {
            return user_nicename;
        }

        public void setUser_nicename(String user_nicename) {
            this.user_nicename = user_nicename;
        }

        public int getCollect_goods() {
            return collect_goods;
        }

        public void setCollect_goods(int collect_goods) {
            this.collect_goods = collect_goods;
        }

        public int getCollect_store() {
            return collect_store;
        }

        public void setCollect_store(int collect_store) {
            this.collect_store = collect_store;
        }

        public int getBug_goods() {
            return bug_goods;
        }

        public void setBug_goods(int bug_goods) {
            this.bug_goods = bug_goods;
        }

        public int getBug_store() {
            return bug_store;
        }

        public void setBug_store(int bug_store) {
            this.bug_store = bug_store;
        }

        public int getPay_order_num() {
            return pay_order_num;
        }

        public void setPay_order_num(int pay_order_num) {
            this.pay_order_num = pay_order_num;
        }

        public int getDeliver_num() {
            return deliver_num;
        }

        public void setDeliver_num(int deliver_num) {
            this.deliver_num = deliver_num;
        }

        public int getReceipt_num() {
            return receipt_num;
        }

        public void setReceipt_num(int receipt_num) {
            this.receipt_num = receipt_num;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public String getSiteMobile() {
            return siteMobile;
        }

        public void setSiteMobile(String siteMobile) {
            this.siteMobile = siteMobile;
        }
    }
}
