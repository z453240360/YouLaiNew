package com.bm.chengshiyoutian.youlaiwang.bean;
import java.util.List;

/**
 * Created by sld on 2017/6/1.
 */

public class QueRenDingDanBena {


    /**
     * data : {"address":{"address_id":12,"name":"黎明","mobile":"15683424932","areainfo":"北京北京崇文区规范讲的是"},"goods":[{"store_id":7,"store_name":"原创设计店铺","logistics_cost":33,"min_pay_money":22,"goods":[{"cart_id":94,"store_id":7,"goods_name":"fasdgas","goods_cover":"09ba0de8f3e039c032a8e61f47f48434.png","goods_num":1,"spec_text":"/瓶/1瓶","price":"4.00"},{"cart_id":95,"store_id":7,"goods_name":"名称1","goods_cover":"ef600f56b6b1ba88e09748a3a0150cc7.png","goods_num":2,"spec_text":"/瓶/1瓶","price":"6.00"}],"store_num":3,"store_price":16}],"money":16}
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
         * address : {"address_id":12,"name":"黎明","mobile":"15683424932","areainfo":"北京北京崇文区规范讲的是"}
         * goods : [{"store_id":7,"store_name":"原创设计店铺","logistics_cost":33,"min_pay_money":22,"goods":[{"cart_id":94,"store_id":7,"goods_name":"fasdgas","goods_cover":"09ba0de8f3e039c032a8e61f47f48434.png","goods_num":1,"spec_text":"/瓶/1瓶","price":"4.00"},{"cart_id":95,"store_id":7,"goods_name":"名称1","goods_cover":"ef600f56b6b1ba88e09748a3a0150cc7.png","goods_num":2,"spec_text":"/瓶/1瓶","price":"6.00"}],"store_num":3,"store_price":16}]
         * money : 16
         */

        private AddressBean address;
        private int money;
        private List<GoodsBeanX> goods;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public List<GoodsBeanX> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBeanX> goods) {
            this.goods = goods;
        }

        public static class AddressBean {
            /**
             * address_id : 12
             * name : 黎明
             * mobile : 15683424932
             * areainfo : 北京北京崇文区规范讲的是
             */

            private int address_id;
            private String name;
            private String mobile;
            private String areainfo;

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

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getAreainfo() {
                return areainfo;
            }

            public void setAreainfo(String areainfo) {
                this.areainfo = areainfo;
            }
        }

        public static class GoodsBeanX {
            /**
             * store_id : 7
             * store_name : 原创设计店铺
             * logistics_cost : 33
             * min_pay_money : 22
             * goods : [{"cart_id":94,"store_id":7,"goods_name":"fasdgas","goods_cover":"09ba0de8f3e039c032a8e61f47f48434.png","goods_num":1,"spec_text":"/瓶/1瓶","price":"4.00"},{"cart_id":95,"store_id":7,"goods_name":"名称1","goods_cover":"ef600f56b6b1ba88e09748a3a0150cc7.png","goods_num":2,"spec_text":"/瓶/1瓶","price":"6.00"}]
             * store_num : 3
             * store_price : 16
             */

            private int store_id;
            private String store_name;
            private int logistics_cost;
            private int min_pay_money;
            private int store_num;
            private int store_price;
            private List<GoodsBean> goods;

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

            public int getStore_num() {
                return store_num;
            }

            public void setStore_num(int store_num) {
                this.store_num = store_num;
            }

            public int getStore_price() {
                return store_price;
            }

            public void setStore_price(int store_price) {
                this.store_price = store_price;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * cart_id : 94
                 * store_id : 7
                 * goods_name : fasdgas
                 * goods_cover : 09ba0de8f3e039c032a8e61f47f48434.png
                 * goods_num : 1
                 * spec_text : /瓶/1瓶
                 * price : 4.00
                 */

                private int cart_id;
                private int store_id;
                private String goods_name;
                private String goods_cover;
                private int goods_num;
                private String spec_text;
                private String price;

                public int getCart_id() {
                    return cart_id;
                }

                public void setCart_id(int cart_id) {
                    this.cart_id = cart_id;
                }

                public int getStore_id() {
                    return store_id;
                }

                public void setStore_id(int store_id) {
                    this.store_id = store_id;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
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
