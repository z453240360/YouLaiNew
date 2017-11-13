package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/6/20.
 */

public class DingDanXiangQingBean {


    /**
     * code : 200
     * data : {"create_time":"2017-06-20 09:48:50","format_order_state":"待付款","format_shop_state":"待审核","goods":[{"goods_attr_text":"/千克/1千克","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/37f695f3e9d50eaa4e4f1b7f51ddbb6b.jpg?imageView2/1/w/200/h/200","goods_id":8568,"goods_name":"黄小米  500克 农家粗粮五谷杂粮米面粮油","goods_num":3,"goods_pay_price":"1000.00"}],"goods_amount":"3000.00","order_amount":"3020.00","order_id":370,"order_sn":"597120179920620094850452","order_state":1,"payment_time":"1970-01-01 08:00:00","payment_type":"在线支付","reciver_info":"上海市上海市黄浦区？？？。","reciver_mobile":"444","reciver_name":"。。。","send_goods":"1970-01-01 08:00:00","shipping_fee":"20.00","shop_id":58,"shop_name":"由来网系统测试"}
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
         * create_time : 2017-06-20 09:48:50
         * format_order_state : 待付款
         * format_shop_state : 待审核
         * goods : [{"goods_attr_text":"/千克/1千克","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/37f695f3e9d50eaa4e4f1b7f51ddbb6b.jpg?imageView2/1/w/200/h/200","goods_id":8568,"goods_name":"黄小米  500克 农家粗粮五谷杂粮米面粮油","goods_num":3,"goods_pay_price":"1000.00"}]
         * goods_amount : 3000.00
         * order_amount : 3020.00
         * order_id : 370
         * order_sn : 597120179920620094850452
         * order_state : 1
         * payment_time : 1970-01-01 08:00:00
         * payment_type : 在线支付
         * reciver_info : 上海市上海市黄浦区？？？。
         * reciver_mobile : 444
         * reciver_name : 。。。
         * send_goods : 1970-01-01 08:00:00
         * shipping_fee : 20.00
         * shop_id : 58
         * shop_name : 由来网系统测试
         */

        private String create_time;
        private String format_order_state;
        private String format_shop_state;
        private String goods_amount;
        private String order_amount;
        private int order_id;
        private String order_sn;
        private int order_state;
        private String payment_time;
        private String payment_type;
        private String reciver_info;
        private String reciver_mobile;
        private String reciver_name;
        private String send_goods;
        private String shipping_fee;
        private int shop_id;
        private String shop_name;
        private List<GoodsBean> goods;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getFormat_order_state() {
            return format_order_state;
        }

        public void setFormat_order_state(String format_order_state) {
            this.format_order_state = format_order_state;
        }

        public String getFormat_shop_state() {
            return format_shop_state;
        }

        public void setFormat_shop_state(String format_shop_state) {
            this.format_shop_state = format_shop_state;
        }

        public String getGoods_amount() {
            return goods_amount;
        }

        public void setGoods_amount(String goods_amount) {
            this.goods_amount = goods_amount;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getOrder_state() {
            return order_state;
        }

        public void setOrder_state(int order_state) {
            this.order_state = order_state;
        }

        public String getPayment_time() {
            return payment_time;
        }

        public void setPayment_time(String payment_time) {
            this.payment_time = payment_time;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
        }

        public String getReciver_info() {
            return reciver_info;
        }

        public void setReciver_info(String reciver_info) {
            this.reciver_info = reciver_info;
        }

        public String getReciver_mobile() {
            return reciver_mobile;
        }

        public void setReciver_mobile(String reciver_mobile) {
            this.reciver_mobile = reciver_mobile;
        }

        public String getReciver_name() {
            return reciver_name;
        }

        public void setReciver_name(String reciver_name) {
            this.reciver_name = reciver_name;
        }

        public String getSend_goods() {
            return send_goods;
        }

        public void setSend_goods(String send_goods) {
            this.send_goods = send_goods;
        }

        public String getShipping_fee() {
            return shipping_fee;
        }

        public void setShipping_fee(String shipping_fee) {
            this.shipping_fee = shipping_fee;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * goods_attr_text : /千克/1千克
             * goods_cover : http://oqv8tlktu.bkt.clouddn.com/37f695f3e9d50eaa4e4f1b7f51ddbb6b.jpg?imageView2/1/w/200/h/200
             * goods_id : 8568
             * goods_name : 黄小米  500克 农家粗粮五谷杂粮米面粮油
             * goods_num : 3
             * goods_pay_price : 1000.00
             */

            private String goods_attr_text;
            private String goods_cover;
            private int goods_id;
            private String goods_name;
            private int goods_num;
            private double goods_pay_price;

            public String getGoods_attr_text() {
                return goods_attr_text;
            }

            public void setGoods_attr_text(String goods_attr_text) {
                this.goods_attr_text = goods_attr_text;
            }

            public String getGoods_cover() {
                return goods_cover;
            }

            public void setGoods_cover(String goods_cover) {
                this.goods_cover = goods_cover;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public double getGoods_pay_price() {
                return goods_pay_price;
            }

            public void setGoods_pay_price(double goods_pay_price) {
                this.goods_pay_price = goods_pay_price;
            }
        }
    }
}
