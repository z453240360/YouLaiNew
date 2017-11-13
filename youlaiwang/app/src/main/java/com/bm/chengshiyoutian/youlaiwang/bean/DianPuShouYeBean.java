package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/19.
 */

public class DianPuShouYeBean {
    /**
     * code : 200
     * data : {"banner":[{"banner_foreign_id":10,"banner_id":13129,"banner_imgsrc":"http://oqv8tlktu.bkt.clouddn.com/58bcc0571ba4a416308533b90fe7df25.jpg","banner_jump_type":1}],"cart_num":0,"newGoods":[{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/3eb0b5a585f1dca44bcb9744d0f93067.jpg?imageView2/1/w/200/h/200","goods_id":7427,"goods_name":"2500安井墨鱼丸","goods_price":"60.00","ratio":"/包/1包"},{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/164c3378b90c882a80599bccbe2f14fd.JPG?imageView2/1/w/200/h/200","goods_id":7683,"goods_name":"康姐地皮菜","goods_price":"5.00","ratio":"/包/1包"},{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/189fa6dca91633de01d6efce2e1d2f6d.jpg?imageView2/1/w/200/h/200","goods_id":7939,"goods_name":"四季豆干","goods_price":"32.00","ratio":"/斤/1斤"}],"store":{"company_license_num":1,"delivery_radius":"5","evaluate_total":5,"is_favorites":2,"min_pay_money":500,"sign":"http://oqv8tlktu.bkt.clouddn.com/ab6f17e455ffeae5812df6d59c277cb7.jpg","store_id":30,"store_logo":"http://oqv8tlktu.bkt.clouddn.com/b454217e8283b42162890dd1a6da1fa5.jpg","store_name":"浙江餐道网络科技有限公司","store_phone":"15058232031"}}
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
         * banner : [{"banner_foreign_id":10,"banner_id":13129,"banner_imgsrc":"http://oqv8tlktu.bkt.clouddn.com/58bcc0571ba4a416308533b90fe7df25.jpg","banner_jump_type":1}]
         * cart_num : 0
         * newGoods : [{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/3eb0b5a585f1dca44bcb9744d0f93067.jpg?imageView2/1/w/200/h/200","goods_id":7427,"goods_name":"2500安井墨鱼丸","goods_price":"60.00","ratio":"/包/1包"},{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/164c3378b90c882a80599bccbe2f14fd.JPG?imageView2/1/w/200/h/200","goods_id":7683,"goods_name":"康姐地皮菜","goods_price":"5.00","ratio":"/包/1包"},{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/189fa6dca91633de01d6efce2e1d2f6d.jpg?imageView2/1/w/200/h/200","goods_id":7939,"goods_name":"四季豆干","goods_price":"32.00","ratio":"/斤/1斤"}]
         * store : {"company_license_num":1,"delivery_radius":"5","evaluate_total":5,"is_favorites":2,"min_pay_money":500,"sign":"http://oqv8tlktu.bkt.clouddn.com/ab6f17e455ffeae5812df6d59c277cb7.jpg","store_id":30,"store_logo":"http://oqv8tlktu.bkt.clouddn.com/b454217e8283b42162890dd1a6da1fa5.jpg","store_name":"浙江餐道网络科技有限公司","store_phone":"15058232031"}
         */

        private int cart_num;
        private StoreBean store;
        private List<BannerBean> banner;
        private List<NewGoodsBean> newGoods;

        public int getCart_num() {
            return cart_num;
        }

        public void setCart_num(int cart_num) {
            this.cart_num = cart_num;
        }

        public StoreBean getStore() {
            return store;
        }

        public void setStore(StoreBean store) {
            this.store = store;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<NewGoodsBean> getNewGoods() {
            return newGoods;
        }

        public void setNewGoods(List<NewGoodsBean> newGoods) {
            this.newGoods = newGoods;
        }

        public static class StoreBean {
            /**
             * company_license_num : 1
             * delivery_radius : 5
             * evaluate_total : 5
             * is_favorites : 2
             * min_pay_money : 500
             * sign : http://oqv8tlktu.bkt.clouddn.com/ab6f17e455ffeae5812df6d59c277cb7.jpg
             * store_id : 30
             * store_logo : http://oqv8tlktu.bkt.clouddn.com/b454217e8283b42162890dd1a6da1fa5.jpg
             * store_name : 浙江餐道网络科技有限公司
             * store_phone : 15058232031
             */

            private int company_license_num;
            private String delivery_radius;
            private int evaluate_total;
            private int is_favorites;
            private int min_pay_money;
            private String sign;
            private int store_id;
            private String store_logo;
            private String store_name;
            private String store_phone;

            public int getCompany_license_num() {
                return company_license_num;
            }

            public void setCompany_license_num(int company_license_num) {
                this.company_license_num = company_license_num;
            }

            public String getDelivery_radius() {
                return delivery_radius;
            }

            public void setDelivery_radius(String delivery_radius) {
                this.delivery_radius = delivery_radius;
            }

            public int getEvaluate_total() {
                return evaluate_total;
            }

            public void setEvaluate_total(int evaluate_total) {
                this.evaluate_total = evaluate_total;
            }

            public int getIs_favorites() {
                return is_favorites;
            }

            public void setIs_favorites(int is_favorites) {
                this.is_favorites = is_favorites;
            }

            public int getMin_pay_money() {
                return min_pay_money;
            }

            public void setMin_pay_money(int min_pay_money) {
                this.min_pay_money = min_pay_money;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getStore_logo() {
                return store_logo;
            }

            public void setStore_logo(String store_logo) {
                this.store_logo = store_logo;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getStore_phone() {
                return store_phone;
            }

            public void setStore_phone(String store_phone) {
                this.store_phone = store_phone;
            }
        }

        public static class BannerBean {
            /**
             * banner_foreign_id : 10
             * banner_id : 13129
             * banner_imgsrc : http://oqv8tlktu.bkt.clouddn.com/58bcc0571ba4a416308533b90fe7df25.jpg
             * banner_jump_type : 1
             */

            private int banner_foreign_id;
            private int banner_id;
            private String banner_imgsrc;
            private int banner_jump_type;

            public int getBanner_foreign_id() {
                return banner_foreign_id;
            }

            public void setBanner_foreign_id(int banner_foreign_id) {
                this.banner_foreign_id = banner_foreign_id;
            }

            public int getBanner_id() {
                return banner_id;
            }

            public void setBanner_id(int banner_id) {
                this.banner_id = banner_id;
            }

            public String getBanner_imgsrc() {
                return banner_imgsrc;
            }

            public void setBanner_imgsrc(String banner_imgsrc) {
                this.banner_imgsrc = banner_imgsrc;
            }

            public int getBanner_jump_type() {
                return banner_jump_type;
            }

            public void setBanner_jump_type(int banner_jump_type) {
                this.banner_jump_type = banner_jump_type;
            }
        }

        public static class NewGoodsBean {
            /**
             * goods_cover : http://oqv8tlktu.bkt.clouddn.com/3eb0b5a585f1dca44bcb9744d0f93067.jpg?imageView2/1/w/200/h/200
             * goods_id : 7427
             * goods_name : 2500安井墨鱼丸
             * goods_price : 60.00
             * ratio : /包/1包
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
