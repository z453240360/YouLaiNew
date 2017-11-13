package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ProDetialBean {

    /**
     * data : {"goods_id":8568,"goods_name":"黄小米  500克 农家粗粮五谷杂粮米面粮油","store_id":58,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/74ca10fd5e6654cc74121441d520046d.jpg","goods_subtitle":"粒粒饱满 粗加工更美味 老人小孩都爱吃","goods_images":["http://oqv8tlktu.bkt.clouddn.com/74ca10fd5e6654cc74121441d520046d.jpg"],"evaluation_count":4,"is_favorites":1,"store":{"store_phone":"17740857397"},"cart_num":"25","cart_price":355.8,"spec":[{"spec_id":13697,"price":"15.00","spec_promo_price":"10.00","ratio":"/袋/500g","cart_goods_num":12},{"spec_id":13698,"price":"129.80","spec_promo_price":"120.00","ratio":"/箱/0袋","cart_goods_num":1}],"comment":{"comment_content":"好评","comment_addtime":"2017-10-25","avatar":"http://oqv8tlktu.bkt.clouddn.com/avatar.jpg","user_nicename":"小丸子"}}
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
         * goods_id : 8568
         * goods_name : 黄小米  500克 农家粗粮五谷杂粮米面粮油
         * store_id : 58
         * goods_cover : http://oqv8tlktu.bkt.clouddn.com/74ca10fd5e6654cc74121441d520046d.jpg
         * goods_subtitle : 粒粒饱满 粗加工更美味 老人小孩都爱吃
         * goods_images : ["http://oqv8tlktu.bkt.clouddn.com/74ca10fd5e6654cc74121441d520046d.jpg"]
         * evaluation_count : 4
         * is_favorites : 1
         * store : {"store_phone":"17740857397"}
         * cart_num : 25
         * cart_price : 355.8
         * spec : [{"spec_id":13697,"price":"15.00","spec_promo_price":"10.00","ratio":"/袋/500g","cart_goods_num":12},{"spec_id":13698,"price":"129.80","spec_promo_price":"120.00","ratio":"/箱/0袋","cart_goods_num":1}]
         * comment : {"comment_content":"好评","comment_addtime":"2017-10-25","avatar":"http://oqv8tlktu.bkt.clouddn.com/avatar.jpg","user_nicename":"小丸子"}
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
        private CommentBean comment;
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

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
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
             * store_phone : 17740857397
             */

            private String store_phone;

            public String getStore_phone() {
                return store_phone;
            }

            public void setStore_phone(String store_phone) {
                this.store_phone = store_phone;
            }
        }

        public static class CommentBean {
            /**
             * comment_content : 好评
             * comment_addtime : 2017-10-25
             * avatar : http://oqv8tlktu.bkt.clouddn.com/avatar.jpg
             * user_nicename : 小丸子
             */

            private String comment_content;
            private String comment_addtime;
            private String avatar;
            private String user_nicename;

            public String getComment_content() {
                return comment_content;
            }

            public void setComment_content(String comment_content) {
                this.comment_content = comment_content;
            }

            public String getComment_addtime() {
                return comment_addtime;
            }

            public void setComment_addtime(String comment_addtime) {
                this.comment_addtime = comment_addtime;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUser_nicename() {
                return user_nicename;
            }

            public void setUser_nicename(String user_nicename) {
                this.user_nicename = user_nicename;
            }
        }

        public static class SpecBean {
            /**
             * spec_id : 13697
             * price : 15.00
             * spec_promo_price : 10.00
             * ratio : /袋/500g
             * cart_goods_num : 12
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
