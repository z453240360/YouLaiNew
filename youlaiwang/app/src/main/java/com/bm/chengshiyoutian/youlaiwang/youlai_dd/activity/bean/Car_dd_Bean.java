package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */

public class Car_dd_Bean {

    /**
     * data : {"total":285,
     *          "current_page":1,
     *          "data":[{"goods_id":8568,"store_id":58,"goods_name":"黄小米  500克 农家粗粮五谷杂粮米面粮油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/74ca10fd5e6654cc74121441d520046d.jpg?imageView2/1/w/200/h/200","goods_price":"15.00","goods_is_new":1,"goods_is_promo":2,"goods_spec":[{"spec_id":13698,"price":"129.80","spec_promo_price":"0.00","ratio":"/箱/0袋","cart_goods_num":0}],"spec":{"spec_id":13697,"price":"15.00","spec_promo_price":"0.00","ratio":"/袋/500g","cart_goods_num":0}},{"goods_id":8576,"store_id":58,"goods_name":"汉源红花椒50g 特麻 四川特产麻椒粒 过筛散装 大红袍足干","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/053817651fd8e3bfe107ec62be0de928.jpg?imageView2/1/w/200/h/200","goods_price":"10.00","goods_is_new":2,"goods_is_promo":1,"goods_spec":[],"spec":{"spec_id":11884,"price":"12.00","spec_promo_price":"10.00","ratio":"/包/50g","cart_goods_num":0}},{"goods_id":8577,"store_id":58,"goods_name":"黑豆500克 农家粗粮五谷杂粮","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/ea5a593273494c68f60a16c2bb0ada46.jpg?imageView2/1/w/200/h/200","goods_price":"6.80","goods_is_new":1,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":12082,"price":"6.80","spec_promo_price":"0.00","ratio":"/包/500g","cart_goods_num":0}},{"goods_id":8578,"store_id":58,"goods_name":"红豆  500克 农家粗粮五谷杂粮米面粮油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/8a15939806e839d3000c915781930fff.jpg?imageView2/1/w/200/h/200","goods_price":"7.90","goods_is_new":2,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11938,"price":"7.90","spec_promo_price":"0.00","ratio":"/包/500g","cart_goods_num":0}},{"goods_id":8579,"store_id":58,"goods_name":"绿豆  500克 农家粗粮五谷杂粮米面粮油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/ad06a858077db4357416fc6d2f422b81.jpg?imageView2/1/w/200/h/200","goods_price":"8.80","goods_is_new":2,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11940,"price":"8.80","spec_promo_price":"0.00","ratio":"/包/500g","cart_goods_num":0}},{"goods_id":8580,"store_id":58,"goods_name":"花生 500克 农家粗粮五谷杂粮米面粮油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/705168e29b7ee55b33647ebf50d94436.jpg?imageView2/1/w/200/h/200","goods_price":"8.90","goods_is_new":2,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":12502,"price":"8.90","spec_promo_price":"0.00","ratio":"/包/500g","cart_goods_num":0}},{"goods_id":8581,"store_id":58,"goods_name":"四川辣椒","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/3a29de90f85c9145f5ced66154aa6ec3.jpg?imageView2/1/w/200/h/200","goods_price":"15.90","goods_is_new":1,"goods_is_promo":1,"goods_spec":[],"spec":{"spec_id":13536,"price":"15.90","spec_promo_price":"0.00","ratio":"/包/100g","cart_goods_num":0}},{"goods_id":8583,"store_id":58,"goods_name":"红大米  1千克  农家粗粮五谷杂粮米面粮油 ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/0aa436eb4585641541e667aa399fd9da.jpg?imageView2/1/w/200/h/200","goods_price":"38.00","goods_is_new":1,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11946,"price":"38.00","spec_promo_price":"0.00","ratio":"/千克/1","cart_goods_num":0}},{"goods_id":8584,"store_id":58,"goods_name":"黄豆  500克  农家粗粮五谷杂粮米面粮油 ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/82a84bd20dcbdf5df9ea0c9943864081.jpg?imageView2/1/w/200/h/200","goods_price":"12.98","goods_is_new":1,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11948,"price":"12.98","spec_promo_price":"0.00","ratio":"/斤/500g","cart_goods_num":0}},{"goods_id":8585,"store_id":58,"goods_name":"薏米  500克  农家粗粮五谷杂粮米面粮油 ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/ac2b78421a47f9c56dc3825083b3c5cd.jpg?imageView2/1/w/200/h/200","goods_price":"19.80","goods_is_new":1,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11949,"price":"19.80","spec_promo_price":"0.00","ratio":"/千克/500g","cart_goods_num":0}}]}
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
         * total : 285
         * current_page : 1
         * data : [{"goods_id":8568,"store_id":58,"goods_name":"黄小米  500克 农家粗粮五谷杂粮米面粮油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/74ca10fd5e6654cc74121441d520046d.jpg?imageView2/1/w/200/h/200","goods_price":"15.00","goods_is_new":1,"goods_is_promo":2,"goods_spec":[{"spec_id":13698,"price":"129.80","spec_promo_price":"0.00","ratio":"/箱/0袋","cart_goods_num":0}],"spec":{"spec_id":13697,"price":"15.00","spec_promo_price":"0.00","ratio":"/袋/500g","cart_goods_num":0}},{"goods_id":8576,"store_id":58,"goods_name":"汉源红花椒50g 特麻 四川特产麻椒粒 过筛散装 大红袍足干","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/053817651fd8e3bfe107ec62be0de928.jpg?imageView2/1/w/200/h/200","goods_price":"10.00","goods_is_new":2,"goods_is_promo":1,"goods_spec":[],"spec":{"spec_id":11884,"price":"12.00","spec_promo_price":"10.00","ratio":"/包/50g","cart_goods_num":0}},{"goods_id":8577,"store_id":58,"goods_name":"黑豆500克 农家粗粮五谷杂粮","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/ea5a593273494c68f60a16c2bb0ada46.jpg?imageView2/1/w/200/h/200","goods_price":"6.80","goods_is_new":1,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":12082,"price":"6.80","spec_promo_price":"0.00","ratio":"/包/500g","cart_goods_num":0}},{"goods_id":8578,"store_id":58,"goods_name":"红豆  500克 农家粗粮五谷杂粮米面粮油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/8a15939806e839d3000c915781930fff.jpg?imageView2/1/w/200/h/200","goods_price":"7.90","goods_is_new":2,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11938,"price":"7.90","spec_promo_price":"0.00","ratio":"/包/500g","cart_goods_num":0}},{"goods_id":8579,"store_id":58,"goods_name":"绿豆  500克 农家粗粮五谷杂粮米面粮油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/ad06a858077db4357416fc6d2f422b81.jpg?imageView2/1/w/200/h/200","goods_price":"8.80","goods_is_new":2,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11940,"price":"8.80","spec_promo_price":"0.00","ratio":"/包/500g","cart_goods_num":0}},{"goods_id":8580,"store_id":58,"goods_name":"花生 500克 农家粗粮五谷杂粮米面粮油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/705168e29b7ee55b33647ebf50d94436.jpg?imageView2/1/w/200/h/200","goods_price":"8.90","goods_is_new":2,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":12502,"price":"8.90","spec_promo_price":"0.00","ratio":"/包/500g","cart_goods_num":0}},{"goods_id":8581,"store_id":58,"goods_name":"四川辣椒","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/3a29de90f85c9145f5ced66154aa6ec3.jpg?imageView2/1/w/200/h/200","goods_price":"15.90","goods_is_new":1,"goods_is_promo":1,"goods_spec":[],"spec":{"spec_id":13536,"price":"15.90","spec_promo_price":"0.00","ratio":"/包/100g","cart_goods_num":0}},{"goods_id":8583,"store_id":58,"goods_name":"红大米  1千克  农家粗粮五谷杂粮米面粮油 ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/0aa436eb4585641541e667aa399fd9da.jpg?imageView2/1/w/200/h/200","goods_price":"38.00","goods_is_new":1,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11946,"price":"38.00","spec_promo_price":"0.00","ratio":"/千克/1","cart_goods_num":0}},{"goods_id":8584,"store_id":58,"goods_name":"黄豆  500克  农家粗粮五谷杂粮米面粮油 ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/82a84bd20dcbdf5df9ea0c9943864081.jpg?imageView2/1/w/200/h/200","goods_price":"12.98","goods_is_new":1,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11948,"price":"12.98","spec_promo_price":"0.00","ratio":"/斤/500g","cart_goods_num":0}},{"goods_id":8585,"store_id":58,"goods_name":"薏米  500克  农家粗粮五谷杂粮米面粮油 ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/ac2b78421a47f9c56dc3825083b3c5cd.jpg?imageView2/1/w/200/h/200","goods_price":"19.80","goods_is_new":1,"goods_is_promo":2,"goods_spec":[],"spec":{"spec_id":11949,"price":"19.80","spec_promo_price":"0.00","ratio":"/千克/500g","cart_goods_num":0}}]
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
             * goods_id : 8568
             * store_id : 58
             * goods_name : 黄小米  500克 农家粗粮五谷杂粮米面粮油
             * goods_cover : http://oqv8tlktu.bkt.clouddn.com/74ca10fd5e6654cc74121441d520046d.jpg?imageView2/1/w/200/h/200
             * goods_price : 15.00
             * goods_is_new : 1
             * goods_is_promo : 2
             * goods_spec : [{"spec_id":13698,"price":"129.80","spec_promo_price":"0.00","ratio":"/箱/0袋","cart_goods_num":0}]
             * spec : {"spec_id":13697,"price":"15.00","spec_promo_price":"0.00","ratio":"/袋/500g","cart_goods_num":0}
             */

            private int goods_id;
            private int store_id;
            private String goods_name;
            private String goods_cover;
            private String goods_price;
            private int goods_is_new;
            private int goods_is_promo;
            private SpecBean spec;
            private List<GoodsSpecBean> goods_spec;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
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

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public int getGoods_is_new() {
                return goods_is_new;
            }

            public void setGoods_is_new(int goods_is_new) {
                this.goods_is_new = goods_is_new;
            }

            public int getGoods_is_promo() {
                return goods_is_promo;
            }

            public void setGoods_is_promo(int goods_is_promo) {
                this.goods_is_promo = goods_is_promo;
            }

            public SpecBean getSpec() {
                return spec;
            }

            public void setSpec(SpecBean spec) {
                this.spec = spec;
            }

            public List<GoodsSpecBean> getGoods_spec() {
                return goods_spec;
            }

            public void setGoods_spec(List<GoodsSpecBean> goods_spec) {
                this.goods_spec = goods_spec;
            }

            public static class SpecBean {
                /**
                 * spec_id : 13697
                 * price : 15.00
                 * spec_promo_price : 0.00
                 * ratio : /袋/500g
                 * cart_goods_num : 0
                 */

                private int spec_id;
                private String price;
                private String spec_promo_price;
                private String ratio;
                private int cart_goods_num;

                public int getGoodsNum() {
                    return goodsNum;
                }

                public void setGoodsNum(int goodsNum) {
                    this.goodsNum = goodsNum;
                }

                private int goodsNum;

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getSpec_promo_price() {
                    return spec_promo_price;
                }

                public void setSpec_promo_price(String spec_promo_price) {
                    this.spec_promo_price = spec_promo_price;
                }

                public String getRatio() {
                    return ratio;
                }

                public void setRatio(String ratio) {
                    this.ratio = ratio;
                }

                public int getCart_goods_num() {
                    return cart_goods_num;
                }

                public void setCart_goods_num(int cart_goods_num) {
                    this.cart_goods_num = cart_goods_num;
                }
            }

            public static class GoodsSpecBean {
                /**
                 * spec_id : 13698
                 * price : 129.80
                 * spec_promo_price : 0.00
                 * ratio : /箱/0袋
                 * cart_goods_num : 0
                 */

                private int spec_id;
                private String price;
                private String spec_promo_price;
                private String ratio;
                private int cart_goods_num;

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getSpec_promo_price() {
                    return spec_promo_price;
                }

                public void setSpec_promo_price(String spec_promo_price) {
                    this.spec_promo_price = spec_promo_price;
                }

                public String getRatio() {
                    return ratio;
                }

                public void setRatio(String ratio) {
                    this.ratio = ratio;
                }

                public int getCart_goods_num() {
                    return cart_goods_num;
                }

                public void setCart_goods_num(int cart_goods_num) {
                    this.cart_goods_num = cart_goods_num;
                }
            }
        }
    }
}
