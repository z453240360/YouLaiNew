package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/22.
 */

public class ShangPinBean {
    /**
     * code : 200
     * data : {"current_page":1,"data":[{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/ef4b3553478adb82db2cd4f7960f3a2d.png","goods_id":7341,"goods_name":"商品名称1666","goods_price":"8.00","ratio":"/瓶/0.1箱","record_id":3,"spec_id":120},{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/4f344189e12958f9fb5cd8e74b275feb.png","goods_id":7342,"goods_name":"商品名称12","goods_price":"10.00","ratio":"/瓶/1瓶","record_id":1,"spec_id":117}],"total":2}
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
         * data : [{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/ef4b3553478adb82db2cd4f7960f3a2d.png","goods_id":7341,"goods_name":"商品名称1666","goods_price":"8.00","ratio":"/瓶/0.1箱","record_id":3,"spec_id":120},{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/4f344189e12958f9fb5cd8e74b275feb.png","goods_id":7342,"goods_name":"商品名称12","goods_price":"10.00","ratio":"/瓶/1瓶","record_id":1,"spec_id":117}]
         * total : 2
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
             * goods_cover : http://oqv8tlktu.bkt.clouddn.com/ef4b3553478adb82db2cd4f7960f3a2d.png
             * goods_id : 7341
             * goods_name : 商品名称1666
             * goods_price : 8.00
             * ratio : /瓶/0.1箱
             * record_id : 3
             * spec_id : 120
             */
            public boolean tag1, tag2;
            private String goods_cover;
            private int goods_id;
            private String goods_name;
            private double goods_price;
            private String ratio;
            private int record_id;
            private String spec_id;


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

            public double getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
            }

            public String getRatio() {
                return ratio;
            }

            public void setRatio(String ratio) {
                this.ratio = ratio;
            }

            public int getRecord_id() {
                return record_id;
            }

            public void setRecord_id(int record_id) {
                this.record_id = record_id;
            }

            public String getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(String spec_id) {
                this.spec_id = spec_id;
            }
        }
    }
}
