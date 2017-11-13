package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/19.
 */

public class DianPuShouYeCuXiaoBean {

    /**
     * code : 200
     * data : {"current_page":1,"data":[{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_id":7334,"goods_name":"324","goods_price":"0.00","ratio":"//瓶"},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_id":7342,"goods_name":"商品名称12","goods_price":"10.00","ratio":"/瓶/1瓶"},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/f6106056d4a0742a3816735e71787e16.jpg","goods_id":7343,"goods_name":"商品名称11","goods_price":"10.00","ratio":"/瓶/1瓶"},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_id":7351,"goods_name":"fasdgas","goods_price":"4.00","ratio":"/瓶/1瓶"},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/087585f9a334e0961211e720dfe81863.jpg","goods_id":7354,"goods_name":"名称1","goods_price":"6.00","ratio":"/瓶/0.1111111111111111箱"}],"total":5}
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
         * data : [{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_id":7334,"goods_name":"324","goods_price":"0.00","ratio":"//瓶"},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_id":7342,"goods_name":"商品名称12","goods_price":"10.00","ratio":"/瓶/1瓶"},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/f6106056d4a0742a3816735e71787e16.jpg","goods_id":7343,"goods_name":"商品名称11","goods_price":"10.00","ratio":"/瓶/1瓶"},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_id":7351,"goods_name":"fasdgas","goods_price":"4.00","ratio":"/瓶/1瓶"},{"goods_cover":"http://opq5wk7p7.bkt.clouddn.com/087585f9a334e0961211e720dfe81863.jpg","goods_id":7354,"goods_name":"名称1","goods_price":"6.00","ratio":"/瓶/0.1111111111111111箱"}]
         * total : 5
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
             * goods_cover : http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg
             * goods_id : 7334
             * goods_name : 324
             * goods_price : 0.00
             * ratio : //瓶
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
