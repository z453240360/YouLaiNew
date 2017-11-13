package com.bm.chengshiyoutian.youlaiwang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sld on 2017/5/25.
 */

public class ShangPinXQ1DaiPingLun {
    /**
     * code : 200
     * data :
     * {"cart_num":0,
     * "cart_price":0,
     * "comment":{"avatar":"http://oqv8tlktu.bkt.clouddn.com/Fur5caTE4rgNf2bbKSy29SN6o65q",
     *            "comment_addtime":"2017-06-05",
     *            "comment_content":"",
     *            "user_nicename":"海贼王啊"
     *            },
     * "evaluation_count":0,
     * "goods_cover":"http://oqv8tlktu.bkt.clouddn.com/752e91c50595e24c0816f0e85e90dcc7.jpg",
     * "goods_id":8398,
     * "goods_images":["http://oqv8tlktu.bkt.clouddn.com/97c2cad0008d4540550be3f77cd2a444.jpg",
     *               "http://oqv8tlktu.bkt.clouddn.com/ba8f0c1ac32833298c8af980a7d6ea39.jpg"],
     * "goods_name":"南美野生海浦虾",
     * "goods_subtitle":"",
     * "is_favorites":2,
     *
     * "spec":[{
     *            "price":"60.00",
     *            "ratio":"/斤/1斤",
     *            "spec_id":2062,
     *            "spec_promo_price":"0.00"},
     *            {"price":"600.00","ratio":"/件/10斤","spec_id":2063,"spec_promo_price":"0.00"}
     *         ],
     * "store":{"store_phone":"15058232031"},
     * "store_id":30}
     * msg :
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public static class DataBean {
        /**
         * cart_num : 0
         * cart_price : 0
         * comment : {"avatar":"http://oqv8tlktu.bkt.clouddn.com/Fur5caTE4rgNf2bbKSy29SN6o65q","comment_addtime":"2017-06-05","comment_content":"","user_nicename":"海贼王啊"}
         * evaluation_count : 0
         * goods_cover : http://oqv8tlktu.bkt.clouddn.com/752e91c50595e24c0816f0e85e90dcc7.jpg
         * goods_id : 8398
         * goods_images : ["http://oqv8tlktu.bkt.clouddn.com/97c2cad0008d4540550be3f77cd2a444.jpg","http://oqv8tlktu.bkt.clouddn.com/ba8f0c1ac32833298c8af980a7d6ea39.jpg"]
         * goods_name : 南美野生海浦虾
         * goods_subtitle :
         * is_favorites : 2
         * spec : [{"price":"60.00","ratio":"/斤/1斤","spec_id":2062,"spec_promo_price":"0.00"},{"price":"600.00","ratio":"/件/10斤","spec_id":2063,"spec_promo_price":"0.00"}]
         * store : {"store_phone":"15058232031"}
         * store_id : 30
         */

        private int cart_num;
        private double cart_price;
        private CommentBean comment;
        private int evaluation_count;
        private String goods_cover;
        private int goods_id;
        private String goods_name;
        private String goods_subtitle;
        private int is_favorites;
        private StoreBean store;
        private int store_id;
        private List<String> goods_images;
        private List<SpecBean> spec;

        public int getCart_num() {
            return cart_num;
        }

        public void setCart_num(int cart_num) {
            this.cart_num = cart_num;
        }

        public double getCart_price() {
            return cart_price;
        }

        public void setCart_price(double cart_price) {
            this.cart_price = cart_price;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public int getEvaluation_count() {
            return evaluation_count;
        }

        public void setEvaluation_count(int evaluation_count) {
            this.evaluation_count = evaluation_count;
        }

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

        public String getGoods_subtitle() {
            return goods_subtitle;
        }

        public void setGoods_subtitle(String goods_subtitle) {
            this.goods_subtitle = goods_subtitle;
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

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
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

        public static class CommentBean {
            /**
             * avatar : http://oqv8tlktu.bkt.clouddn.com/Fur5caTE4rgNf2bbKSy29SN6o65q
             * comment_addtime : 2017-06-05
             * comment_content :
             * user_nicename : 海贼王啊
             */

            private String avatar;
            private String comment_addtime;
            private String comment_content;
            private String user_nicename;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getComment_addtime() {
                return comment_addtime;
            }

            public void setComment_addtime(String comment_addtime) {
                this.comment_addtime = comment_addtime;
            }

            public String getComment_content() {
                return comment_content;
            }

            public void setComment_content(String comment_content) {
                this.comment_content = comment_content;
            }

            public String getUser_nicename() {
                return user_nicename;
            }

            public void setUser_nicename(String user_nicename) {
                this.user_nicename = user_nicename;
            }
        }

        public static class StoreBean {
            /**
             * store_phone : 15058232031
             */

            private String store_phone;

            public String getStore_phone() {
                return store_phone;
            }

            public void setStore_phone(String store_phone) {
                this.store_phone = store_phone;
            }
        }

        public static class SpecBean implements Serializable{
            /**
             * price : 60.00
             * ratio : /斤/1斤
             * spec_id : 2062
             * spec_promo_price : 0.00
             */

            private String price;
            private String ratio;
            private int spec_id;
            private String spec_promo_price;
            private int cart_goods_num;



            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            private String goods_id;

            public int getCart_goods_num() {
                return cart_goods_num;
            }

            public void setCart_goods_num(int cart_goods_num) {
                this.cart_goods_num = cart_goods_num;
            }

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
