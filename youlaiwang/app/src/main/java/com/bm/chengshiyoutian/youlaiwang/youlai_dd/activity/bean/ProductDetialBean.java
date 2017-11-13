package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongdong on 2017/11/5.
 */

public class ProductDetialBean implements Serializable {


    /**
     * data : {"goods_id":14571,"goods_name":"童子鸡（特级）（6.7元/斤）","store_id":217,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/8ac443346a141e33ecfc8ca5a3b78334.jpg","goods_subtitle":"","goods_images":["http://oqv8tlktu.bkt.clouddn.com/8ac443346a141e33ecfc8ca5a3b78334.jpg"],"evaluation_count":0,"is_favorites":2,"store":{"store_phone":"0579-89970906"},"cart_num":"39","cart_price":1623.8,"spec":[{"spec_id":14866,"price":"20.00","spec_promo_price":"0.00","ratio":"/只/3斤","cart_goods_num":0}],"comment":""}
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
         * goods_id : 14571
         * goods_name : 童子鸡（特级）（6.7元/斤）
         * store_id : 217
         * goods_cover : http://oqv8tlktu.bkt.clouddn.com/8ac443346a141e33ecfc8ca5a3b78334.jpg
         * goods_subtitle :
         * goods_images : ["http://oqv8tlktu.bkt.clouddn.com/8ac443346a141e33ecfc8ca5a3b78334.jpg"]
         * evaluation_count : 0
         * is_favorites : 2
         * store : {"store_phone":"0579-89970906"}
         * cart_num : 39
         * cart_price : 1623.8
         * spec : [{"spec_id":14866,"price":"20.00","spec_promo_price":"0.00","ratio":"/只/3斤","cart_goods_num":0}]
         * comment :
         */

        private int goods_id;
        private String goods_name;
        private int store_id;
        private String goods_cover;
        private String goods_subtitle;
        private int evaluation_count;
        private int is_favorites;
        private StoreBean store;
        private String cart_num;
        private double cart_price;
        private String comment;
        private List<String> goods_images;
        private List<SpecBean> spec;

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

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getGoods_cover() {
            return goods_cover;
        }

        public void setGoods_cover(String goods_cover) {
            this.goods_cover = goods_cover;
        }

        public String getGoods_subtitle() {
            return goods_subtitle;
        }

        public void setGoods_subtitle(String goods_subtitle) {
            this.goods_subtitle = goods_subtitle;
        }

        public int getEvaluation_count() {
            return evaluation_count;
        }

        public void setEvaluation_count(int evaluation_count) {
            this.evaluation_count = evaluation_count;
        }

        public int getIs_favorites() {
            return is_favorites;
        }

        public void setIs_favorites(int is_favorites) {
            this.is_favorites = is_favorites;
        }

        public StoreBean getStore() {
            return store;
        }

        public void setStore(StoreBean store) {
            this.store = store;
        }

        public String getCart_num() {
            return cart_num;
        }

        public void setCart_num(String cart_num) {
            this.cart_num = cart_num;
        }

        public double getCart_price() {
            return cart_price;
        }

        public void setCart_price(double cart_price) {
            this.cart_price = cart_price;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public List<String> getGoods_images() {
            return goods_images;
        }

        public void setGoods_images(List<String> goods_images) {
            this.goods_images = goods_images;
        }

        public List<SpecBean> getSpec() {
            return spec;
        }

        public void setSpec(List<SpecBean> spec) {
            this.spec = spec;
        }

        public static class StoreBean {
            /**
             * store_phone : 0579-89970906
             */

            private String store_phone;

            public String getStore_phone() {
                return store_phone;
            }

            public void setStore_phone(String store_phone) {
                this.store_phone = store_phone;
            }
        }

        public static class SpecBean implements Serializable {
            /**
             * spec_id : 14866
             * price : 20.00
             * spec_promo_price : 0.00
             * ratio : /只/3斤
             * cart_goods_num : 0
             */

            private int spec_id;
            private String price;
            private String spec_promo_price;
            private String ratio;
            private int cart_goods_num;
            private int goods_id;

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
