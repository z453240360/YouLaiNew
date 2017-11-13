package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ShangPinLieBiaoBena {


    /**
     * code : 200
     * data : {"current_page":1,"data":[{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/fb511e20a21609f3a28ba4c4c39d32d6.jpg","goods_id":7341,"goods_name":"商品名称13","goods_price":"8.00","ratio":"/瓶/0.1箱"}],"total":1}
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
         * data : [{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/fb511e20a21609f3a28ba4c4c39d32d6.jpg","goods_id":7341,"goods_name":"商品名称13","goods_price":"8.00","ratio":"/瓶/0.1箱"}]
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
             * goods_cover : http://opq5wk7p7.bkt.clouddn.com/fb511e20a21609f3a28ba4c4c39d32d6.jpg
             * goods_id : 7341
             * goods_name : 商品名称13
             * goods_price : 8.00
             * ratio : /瓶/0.1箱
             */

            private String goods_cover;
            private int goods_id;
            private String goods_name;
            private String goods_price;
            private String ratio;

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

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getRatio() {
                return ratio;
            }

            public void setRatio(String ratio) {
                this.ratio = ratio;
            }
        }
    }
}
