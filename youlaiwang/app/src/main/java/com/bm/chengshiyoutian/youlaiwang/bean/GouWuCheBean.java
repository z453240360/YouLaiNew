package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.R.id.min_pay_money;

/**
 * Created by sld on 2017/5/26.
 */

public class GouWuCheBean {
    /**
     * data : {"total":1,"current_page":1,"data":
     * [{"store_id":7,"store_name":"水果蔬菜店","logistics_cost":33,"goods":[{"cart_id":104,"goods_name":"瑞鲜生番茄 700g/盒","goods_id":7343,"spec_id":206,"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/99181540edc6d55c82f5df2c5df93b50.jpg","price":"90.00","goods_num":3,"spec_text":"/盒/8盒"},{"cart_id":105,"goods_name":"生土豆300g/盒","goods_id":7341,"spec_id":209,"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/80aedf8d8dc36b5b1e8d21d1231bce92.jpg","price":"8.00","goods_num":4,"spec_text":"/盒/1盒"},{"cart_id":106,"goods_name":"瑞鲜生姜150g/盒","goods_id":7342,"spec_id":207,"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/f718b30e9b446c1a434e5920542063a0.jpg","price":"10.00","goods_num":2,"spec_text":"/盒/1盒"}]}]}
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
         * total : 1
         * current_page : 1
         * data : [{"store_id":7,"store_name":"水果蔬菜店","logistics_cost":33,"goods":[{"cart_id":104,"goods_name":"瑞鲜生番茄 700g/盒","goods_id":7343,"spec_id":206,"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/99181540edc6d55c82f5df2c5df93b50.jpg","price":"90.00","goods_num":3,"spec_text":"/盒/8盒"},{"cart_id":105,"goods_name":"生土豆300g/盒","goods_id":7341,"spec_id":209,"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/80aedf8d8dc36b5b1e8d21d1231bce92.jpg","price":"8.00","goods_num":4,"spec_text":"/盒/1盒"},{"cart_id":106,"goods_name":"瑞鲜生姜150g/盒","goods_id":7342,"spec_id":207,"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/f718b30e9b446c1a434e5920542063a0.jpg","price":"10.00","goods_num":2,"spec_text":"/盒/1盒"}]}]
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
             * store_name : 水果蔬菜店
             * logistics_cost : 33
             * goods : [{"cart_id":104,"goods_name":"瑞鲜生番茄 700g/盒","goods_id":7343,"spec_id":206,"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/99181540edc6d55c82f5df2c5df93b50.jpg","price":"90.00","goods_num":3,"spec_text":"/盒/8盒"},{"cart_id":105,"goods_name":"生土豆300g/盒","goods_id":7341,"spec_id":209,"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/80aedf8d8dc36b5b1e8d21d1231bce92.jpg","price":"8.00","goods_num":4,"spec_text":"/盒/1盒"},{"cart_id":106,"goods_name":"瑞鲜生姜150g/盒","goods_id":7342,"spec_id":207,"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/f718b30e9b446c1a434e5920542063a0.jpg","price":"10.00","goods_num":2,"spec_text":"/盒/1盒"}]
             */
            public  boolean tag=true;
            private int store_id;
            private String store_name;
            private int logistics_cost;
            private List<GoodsBean> goods;
            private int min_pay_money;

            public int getMin_pay_money() {
                return min_pay_money;
            }

            public void setMin_pay_money(int min_pay_money) {
                this.min_pay_money = min_pay_money;
            }

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

            public int getLogistics_cost() {
                return logistics_cost;
            }

            public void setLogistics_cost(int logistics_cost) {
                this.logistics_cost = logistics_cost;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * cart_id : 104
                 * goods_name : 瑞鲜生番茄 700g/盒
                 * goods_id : 7343
                 * spec_id : 206
                 * goods_cover : http://opq5wk7p7.bkt.clouddn.com/99181540edc6d55c82f5df2c5df93b50.jpg
                 * price : 90.00
                 * goods_num : 3
                 * spec_text : /盒/8盒
                 */
              public   boolean tag = true;
                private int cart_id;
                private String goods_name;
                private int goods_id;
                private int spec_id;
                private String goods_cover;
                private double price;
                private int goods_num;
                private String spec_text;

                public int getCart_id() {
                    return cart_id;
                }

                public void setCart_id(int cart_id) {
                    this.cart_id = cart_id;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public String getGoods_cover() {
                    return goods_cover;
                }

                public void setGoods_cover(String goods_cover) {
                    this.goods_cover = goods_cover;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public int getGoods_num() {
                    return goods_num;
                }

                public void setGoods_num(int goods_num) {
                    this.goods_num = goods_num;
                }

                public String getSpec_text() {
                    return spec_text;
                }

                public void setSpec_text(String spec_text) {
                    this.spec_text = spec_text;
                }
            }
        }
    }
}
