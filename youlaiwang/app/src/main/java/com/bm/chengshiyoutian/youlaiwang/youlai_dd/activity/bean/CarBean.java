package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class CarBean {

    /**
     * data : {"total":2,"current_page":1,"data":[{"store_id":217,"store_name":"义乌由来网代采购","logistics_cost":10,"goods":[{"cart_id":26042,"goods_name":"冷鲜鸭脖（即将上线）","goods_id":13110,"spec_id":13949,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/90d414ec27b79a974b57f9ac0ec737ea.jpg?imageView2/1/w/200/h/200","goods_num":1,"spec_text":"/KG/5kg","spec_price":"999.00","spec_promo_price":"0.00","price":"999.00"}],"min_pay_money":50},{"store_id":58,"store_name":"吾趣优选","logistics_cost":10,"goods":[{"cart_id":26038,"goods_name":"汉源红花椒50g 特麻 四川特产麻椒粒 过筛散装 大红袍足干","goods_id":8576,"spec_id":11884,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/053817651fd8e3bfe107ec62be0de928.jpg?imageView2/1/w/200/h/200","goods_num":7,"spec_text":"/包/50g","spec_price":"20.00","spec_promo_price":"15.00","price":"15.00"},{"cart_id":26039,"goods_name":"红豆  500克 农家粗粮五谷杂粮米面粮油","goods_id":8578,"spec_id":11938,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/8a15939806e839d3000c915781930fff.jpg?imageView2/1/w/200/h/200","goods_num":1,"spec_text":"/包/500g","spec_price":"7.90","spec_promo_price":"0.00","price":"7.90"}],"min_pay_money":100}]}
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
         * data : [{"store_id":217,"store_name":"义乌由来网代采购","logistics_cost":10,"goods":[{"cart_id":26042,"goods_name":"冷鲜鸭脖（即将上线）","goods_id":13110,"spec_id":13949,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/90d414ec27b79a974b57f9ac0ec737ea.jpg?imageView2/1/w/200/h/200","goods_num":1,"spec_text":"/KG/5kg","spec_price":"999.00","spec_promo_price":"0.00","price":"999.00"}],"min_pay_money":50},{"store_id":58,"store_name":"吾趣优选","logistics_cost":10,"goods":[{"cart_id":26038,"goods_name":"汉源红花椒50g 特麻 四川特产麻椒粒 过筛散装 大红袍足干","goods_id":8576,"spec_id":11884,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/053817651fd8e3bfe107ec62be0de928.jpg?imageView2/1/w/200/h/200","goods_num":7,"spec_text":"/包/50g","spec_price":"20.00","spec_promo_price":"15.00","price":"15.00"},{"cart_id":26039,"goods_name":"红豆  500克 农家粗粮五谷杂粮米面粮油","goods_id":8578,"spec_id":11938,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/8a15939806e839d3000c915781930fff.jpg?imageView2/1/w/200/h/200","goods_num":1,"spec_text":"/包/500g","spec_price":"7.90","spec_promo_price":"0.00","price":"7.90"}],"min_pay_money":100}]
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
             * store_id : 217
             * store_name : 义乌由来网代采购
             * logistics_cost : 10
             * goods : [{"cart_id":26042,"goods_name":"冷鲜鸭脖（即将上线）","goods_id":13110,"spec_id":13949,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/90d414ec27b79a974b57f9ac0ec737ea.jpg?imageView2/1/w/200/h/200","goods_num":1,"spec_text":"/KG/5kg","spec_price":"999.00","spec_promo_price":"0.00","price":"999.00"}]
             * min_pay_money : 50
             */

            private int store_id;
            private String store_name;
            private int logistics_cost;
            private int min_pay_money;
            private List<GoodsBean> goods;
            private boolean isSelect=true;
            private double TotalPrice =0;
            private boolean flag=true;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public double getTotalPrice() {
                return TotalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                TotalPrice = totalPrice;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
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

            public int getMin_pay_money() {
                return min_pay_money;
            }

            public void setMin_pay_money(int min_pay_money) {
                this.min_pay_money = min_pay_money;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * cart_id : 26042
                 * goods_name : 冷鲜鸭脖（即将上线）
                 * goods_id : 13110
                 * spec_id : 13949
                 * goods_cover : http://oqv8tlktu.bkt.clouddn.com/90d414ec27b79a974b57f9ac0ec737ea.jpg?imageView2/1/w/200/h/200
                 * goods_num : 1
                 * spec_text : /KG/5kg
                 * spec_price : 999.00
                 * spec_promo_price : 0.00
                 * price : 999.00
                 */

                private int cart_id;
                private String goods_name;
                private int goods_id;
                private int spec_id;
                private String goods_cover;
                private int goods_num;
                private String spec_text;
                private String spec_price;
                private String spec_promo_price;
                private String price;
                private boolean isSelect=true;
                private double childTotalPrice = 0;

                public double getChildTotalPrice() {
                    return childTotalPrice;
                }

                public void setChildTotalPrice(double childTotalPrice) {
                    this.childTotalPrice = childTotalPrice;
                }

                public boolean isSelect() {
                    return isSelect;
                }

                public void setSelect(boolean select) {
                    isSelect = select;
                }

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

                public String getSpec_price() {
                    return spec_price;
                }

                public void setSpec_price(String spec_price) {
                    this.spec_price = spec_price;
                }

                public String getSpec_promo_price() {
                    return spec_promo_price;
                }

                public void setSpec_promo_price(String spec_promo_price) {
                    this.spec_promo_price = spec_promo_price;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }
            }
        }
    }
}
