package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/6/2.
 */

public class SouSuoJieGuoBean {


    /**
     * code : 200
     * data : {"current_page":1,"data":[{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/ef4b3553478adb82db2cd4f7960f3a2d.png","goods_id":7341,"goods_is_new":1,"goods_is_promo":2,"goods_name":"商品名称13","goods_price":"8.00","goods_spec":[{"price":"99.00","ratio":"/箱/10箱","spec_id":120,"spec_promo_price":"98.00"}],"spec":{"price":"9.00","ratio":"/瓶/1箱","spec_id":119,"spec_promo_price":"8.00"}},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4f344189e12958f9fb5cd8e74b275feb.png","goods_id":7342,"goods_is_new":1,"goods_is_promo":1,"goods_name":"商品名称12","goods_price":"10.00","goods_spec":[{"price":"100.00","ratio":"/箱/10瓶","spec_id":118,"spec_promo_price":"90.00"}],"spec":{"price":"11.00","ratio":"/瓶/1瓶","spec_id":117,"spec_promo_price":"10.00"}},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/a616aca092499f67076259478da1bf1b.png","goods_id":7343,"goods_is_new":1,"goods_is_promo":1,"goods_name":"商品名称11","goods_price":"10.00","goods_spec":[{"price":"100.00","ratio":"/箱/8瓶","spec_id":116,"spec_promo_price":"90.00"}],"spec":{"price":"11.00","ratio":"/瓶/1瓶","spec_id":115,"spec_promo_price":"10.00"}}],"total":3}
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
         * data : [{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/ef4b3553478adb82db2cd4f7960f3a2d.png","goods_id":7341,"goods_is_new":1,"goods_is_promo":2,"goods_name":"商品名称13","goods_price":"8.00","goods_spec":[{"price":"99.00","ratio":"/箱/10箱","spec_id":120,"spec_promo_price":"98.00"}],"spec":{"price":"9.00","ratio":"/瓶/1箱","spec_id":119,"spec_promo_price":"8.00"}},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4f344189e12958f9fb5cd8e74b275feb.png","goods_id":7342,"goods_is_new":1,"goods_is_promo":1,"goods_name":"商品名称12","goods_price":"10.00","goods_spec":[{"price":"100.00","ratio":"/箱/10瓶","spec_id":118,"spec_promo_price":"90.00"}],"spec":{"price":"11.00","ratio":"/瓶/1瓶","spec_id":117,"spec_promo_price":"10.00"}},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/a616aca092499f67076259478da1bf1b.png","goods_id":7343,"goods_is_new":1,"goods_is_promo":1,"goods_name":"商品名称11","goods_price":"10.00","goods_spec":[{"price":"100.00","ratio":"/箱/8瓶","spec_id":116,"spec_promo_price":"90.00"}],"spec":{"price":"11.00","ratio":"/瓶/1瓶","spec_id":115,"spec_promo_price":"10.00"}}]
         * total : 3
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
             * goods_cover : http://opq5wk7p7.bkt.clouddn.com/ef4b3553478adb82db2cd4f7960f3a2d.png
             * goods_id : 7341
             * goods_is_new : 1
             * goods_is_promo : 2
             * goods_name : 商品名称13
             * goods_price : 8.00
             * goods_spec : [{"price":"99.00","ratio":"/箱/10箱","spec_id":120,"spec_promo_price":"98.00"}]
             * spec : {"price":"9.00","ratio":"/瓶/1箱","spec_id":119,"spec_promo_price":"8.00"}
             */

            private String goods_cover;
            private int goods_id;
            private int goods_is_new;
            private int goods_is_promo;
            private String goods_name;
            private String goods_price;
            private SpecBean spec;
            private List<GoodsSpecBean> goods_spec;

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

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
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
                 * price : 9.00
                 * ratio : /瓶/1箱
                 * spec_id : 119
                 * spec_promo_price : 8.00
                 */

                private String price;
                private String ratio;
                private int spec_id;
                private String spec_promo_price;

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getRatio() {
                    return ratio;
                }

                public void setRatio(String ratio) {
                    this.ratio = ratio;
                }

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public String getSpec_promo_price() {
                    return spec_promo_price;
                }

                public void setSpec_promo_price(String spec_promo_price) {
                    this.spec_promo_price = spec_promo_price;
                }
            }

            public static class GoodsSpecBean {
                /**
                 * price : 99.00
                 * ratio : /箱/10箱
                 * spec_id : 120
                 * spec_promo_price : 98.00
                 */

                private String price;
                private String ratio;
                private int spec_id;
                private String spec_promo_price;

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getRatio() {
                    return ratio;
                }

                public void setRatio(String ratio) {
                    this.ratio = ratio;
                }

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public String getSpec_promo_price() {
                    return spec_promo_price;
                }

                public void setSpec_promo_price(String spec_promo_price) {
                    this.spec_promo_price = spec_promo_price;
                }
            }
        }
    }
}
