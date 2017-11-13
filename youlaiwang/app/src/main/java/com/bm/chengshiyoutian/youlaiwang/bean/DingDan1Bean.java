package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/6/12.
 */

public class DingDan1Bean {


    /**
     * code : 200
     * data : {"current_page":1,"data":[{"format_order_state":"待付款","num":7,"order_amount":"302.00","order_goods":[{"goods_attr_text":"/盒/8盒","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/99181540edc6d55c82f5df2c5df93b50.jpg","goods_id":7343,"goods_name":"瑞鲜生番茄 700g/盒","goods_num":3,"goods_price":"90.00","order_id":1},{"goods_attr_text":"/盒/1盒","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/80aedf8d8dc36b5b1e8d21d1231bce92.jpg","goods_id":7341,"goods_name":"生土豆300g/盒","goods_num":4,"goods_price":"8.00","order_id":1}],"order_id":1,"order_state":1,"shop_id":7,"shop_name":"水果蔬菜店"}],"total":1}
     * msg :
     */

    private int code;
    private DataBeanX data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"format_order_state":"待付款","num":7,"order_amount":"302.00","order_goods":[{"goods_attr_text":"/盒/8盒","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/99181540edc6d55c82f5df2c5df93b50.jpg","goods_id":7343,"goods_name":"瑞鲜生番茄 700g/盒","goods_num":3,"goods_price":"90.00","order_id":1},{"goods_attr_text":"/盒/1盒","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/80aedf8d8dc36b5b1e8d21d1231bce92.jpg","goods_id":7341,"goods_name":"生土豆300g/盒","goods_num":4,"goods_price":"8.00","order_id":1}],"order_id":1,"order_state":1,"shop_id":7,"shop_name":"水果蔬菜店"}]
         * total : 1
         */

        private int current_page;
        private int total;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * format_order_state : 待付款
             * num : 7
             * order_amount : 302.00
             * order_goods : [{"goods_attr_text":"/盒/8盒","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/99181540edc6d55c82f5df2c5df93b50.jpg","goods_id":7343,"goods_name":"瑞鲜生番茄 700g/盒","goods_num":3,"goods_price":"90.00","order_id":1},{"goods_attr_text":"/盒/1盒","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/80aedf8d8dc36b5b1e8d21d1231bce92.jpg","goods_id":7341,"goods_name":"生土豆300g/盒","goods_num":4,"goods_price":"8.00","order_id":1}]
             * order_id : 1
             * order_state : 1
             * shop_id : 7
             * shop_name : 水果蔬菜店
             */

            private String format_order_state;
            private int num;
            private String order_amount;
            private int order_id;
            private int order_state;
            private int shop_id;
            private String shop_name;
            private List<OrderGoodsBean> order_goods;
            private int is_payment;

            public int getIs_payment() {
                return is_payment;
            }

            public void setIs_payment(int is_payment) {
                this.is_payment = is_payment;
            }

            public String getFormat_order_state() {
                return format_order_state;
            }

            public void setFormat_order_state(String format_order_state) {
                this.format_order_state = format_order_state;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
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

            public int getOrder_state() {
                return order_state;
            }

            public void setOrder_state(int order_state) {
                this.order_state = order_state;
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

            public List<OrderGoodsBean> getOrder_goods() {
                return order_goods;
            }

            public void setOrder_goods(List<OrderGoodsBean> order_goods) {
                this.order_goods = order_goods;
            }

            public static class OrderGoodsBean {
                /**
                 * goods_attr_text : /盒/8盒
                 * goods_cover : http://opq5wk7p7.bkt.clouddn.com/99181540edc6d55c82f5df2c5df93b50.jpg
                 * goods_id : 7343
                 * goods_name : 瑞鲜生番茄 700g/盒
                 * goods_num : 3
                 * goods_price : 90.00
                 * order_id : 1
                 */

                private String goods_attr_text;
                private String goods_cover;
                private int goods_id;
                private String goods_name;
                private int goods_num;
                private double goods_price;
                private int order_id;

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

                public double getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(double goods_price) {
                    this.goods_price = goods_price;
                }

                public int getOrder_id() {
                    return order_id;
                }

                public void setOrder_id(int order_id) {
                    this.order_id = order_id;
                }
            }
        }
    }
}
