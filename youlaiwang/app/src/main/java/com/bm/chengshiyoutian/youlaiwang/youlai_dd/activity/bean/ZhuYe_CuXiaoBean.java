package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 * 首页促销商品对象
 *
 */

public class ZhuYe_CuXiaoBean {


    /**
     * data : {"total":25,"current_page":1,"data":[{"goods_id":8379,"goods_name":"竹筒鸡24","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/972e41d3e92a779eafb448492a426694.jpg?imageView2/1/w/200/h/200","goods_price":"33.00","goods_spec_remark":"","ratio":"/个/1个"},{"goods_id":12253,"goods_name":"小葱","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/87db306eaf55ab2ad2680ff50c038edf.jpg?imageView2/1/w/200/h/200","goods_price":"4.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12251,"goods_name":"芦笋","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/6b43bbcaba7e66e52dd8394f7c2bb49f.jpg?imageView2/1/w/200/h/200","goods_price":"7.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12249,"goods_name":"上海青","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/f91e8149907b04fc06253a81eb574e7c.jpg?imageView2/1/w/200/h/200","goods_price":"3.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12133,"goods_name":"韭菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/89e3b99a8c15e4c39e2ae23563a68983.jpg?imageView2/1/w/200/h/200","goods_price":"2.80","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12114,"goods_name":"洋葱","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/e5e361a49f03728f946c2b47fbaf8d18.jpg?imageView2/1/w/200/h/200","goods_price":"1.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12105,"goods_name":"白冬瓜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/37fd1f1327a08744e666d0f4620e0969.jpg?imageView2/1/w/200/h/200","goods_price":"0.80","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12091,"goods_name":"尖椒","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/8ae4526d305e0483206d49b20acb1df5.jpg?imageView2/1/w/200/h/200","goods_price":"2.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12079,"goods_name":"西红柿","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/2cc562514b5723b6041786f4715fbb72.jpg?imageView2/1/w/200/h/200","goods_price":"2.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12248,"goods_name":"油麦菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/f92c9b77e941b3c521b358f7f2f07e3c.jpg?imageView2/1/w/200/h/200","goods_price":"3.50","goods_spec_remark":"500g","ratio":"/斤/500g"}]}
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
         * total : 25
         * current_page : 1
         * data : [{"goods_id":8379,"goods_name":"竹筒鸡24","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/972e41d3e92a779eafb448492a426694.jpg?imageView2/1/w/200/h/200","goods_price":"33.00","goods_spec_remark":"","ratio":"/个/1个"},{"goods_id":12253,"goods_name":"小葱","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/87db306eaf55ab2ad2680ff50c038edf.jpg?imageView2/1/w/200/h/200","goods_price":"4.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12251,"goods_name":"芦笋","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/6b43bbcaba7e66e52dd8394f7c2bb49f.jpg?imageView2/1/w/200/h/200","goods_price":"7.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12249,"goods_name":"上海青","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/f91e8149907b04fc06253a81eb574e7c.jpg?imageView2/1/w/200/h/200","goods_price":"3.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12133,"goods_name":"韭菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/89e3b99a8c15e4c39e2ae23563a68983.jpg?imageView2/1/w/200/h/200","goods_price":"2.80","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12114,"goods_name":"洋葱","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/e5e361a49f03728f946c2b47fbaf8d18.jpg?imageView2/1/w/200/h/200","goods_price":"1.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12105,"goods_name":"白冬瓜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/37fd1f1327a08744e666d0f4620e0969.jpg?imageView2/1/w/200/h/200","goods_price":"0.80","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12091,"goods_name":"尖椒","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/8ae4526d305e0483206d49b20acb1df5.jpg?imageView2/1/w/200/h/200","goods_price":"2.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12079,"goods_name":"西红柿","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/2cc562514b5723b6041786f4715fbb72.jpg?imageView2/1/w/200/h/200","goods_price":"2.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12248,"goods_name":"油麦菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/f92c9b77e941b3c521b358f7f2f07e3c.jpg?imageView2/1/w/200/h/200","goods_price":"3.50","goods_spec_remark":"500g","ratio":"/斤/500g"}]
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
             * goods_id : 8379
             * goods_name : 竹筒鸡24
             * goods_cover : http://oqv8tlktu.bkt.clouddn.com/972e41d3e92a779eafb448492a426694.jpg?imageView2/1/w/200/h/200
             * goods_price : 33.00
             * goods_spec_remark :
             * ratio : /个/1个
             */

            private int goods_id;
            private String goods_name;
            private String goods_cover;
            private String goods_price;
            private String goods_spec_remark;
            private String ratio;
            private String store_name;



            private String goods_measure_id;


            public String getGoods_measure_id() {
                return goods_measure_id;
            }

            public void setGoods_measure_id(String goods_measure_id) {
                this.goods_measure_id = goods_measure_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
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

            public String getGoods_spec_remark() {
                return goods_spec_remark;
            }

            public void setGoods_spec_remark(String goods_spec_remark) {
                this.goods_spec_remark = goods_spec_remark;
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
