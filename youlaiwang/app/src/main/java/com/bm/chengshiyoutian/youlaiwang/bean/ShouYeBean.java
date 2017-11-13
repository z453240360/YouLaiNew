package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/23.
 */

public class ShouYeBean {

    /**
     * data : {"banner":[{"banner_cover":"http://opq5wk7p7.bkt.clouddn.com/1432e33fa8f644eaca43628a59a9d0d3.png","banner_open_type":1,"banner_key_id":1},{"banner_cover":"http://opq5wk7p7.bkt.clouddn.com/edf55723d557c7d00b3b6dfa744e215b.png","banner_open_type":1,"banner_key_id":9},{"banner_cover":"http://opq5wk7p7.bkt.clouddn.com/09f5bffd4b40915bf5dbbcbbae11e162.png","banner_open_type":1,"banner_key_id":9}],"dataRecommend":[{"goods_id":7343,"goods_name":"商品名称11","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/f6106056d4a0742a3816735e71787e16.jpg","goods_price":"10.00","ratio":"/瓶/2瓶"},{"goods_id":7341,"goods_name":"商品名称13","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/http://opq5wk7p7.bkt.clouddn.com/f6106056d4a0742a3816735e71787e16.jpg","goods_price":"8.00","ratio":"//"},{"goods_id":7342,"goods_name":"商品名称12","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_price":"10.00","ratio":"/瓶/1瓶"},{"goods_id":7341,"goods_name":"商品名称13","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/http://opq5wk7p7.bkt.clouddn.com/f6106056d4a0742a3816735e71787e16.jpg","goods_price":"8.00","ratio":"//"},{"goods_id":7334,"goods_name":"324","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_price":"0.00","ratio":"//瓶"},{"goods_id":7354,"goods_name":"名称1","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/http://opq5wk7p7.bkt.clouddn.com/087585f9a334e0961211e720dfe81863.jpg","goods_price":"6.00","ratio":"//"},{"goods_id":7354,"goods_name":"名称1","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/http://opq5wk7p7.bkt.clouddn.com/087585f9a334e0961211e720dfe81863.jpg","goods_price":"6.00","ratio":"//"}],"ads":{"ads_cover":"http://opq5wk7p7.bkt.clouddn.com/fef903158eb13cd7566a2686b5abb42f.png","ads_key_id":3,"ads_open_type":1}}
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
         * banner : [{"banner_cover":"http://opq5wk7p7.bkt.clouddn.com/1432e33fa8f644eaca43628a59a9d0d3.png","banner_open_type":1,"banner_key_id":1},{"banner_cover":"http://opq5wk7p7.bkt.clouddn.com/edf55723d557c7d00b3b6dfa744e215b.png","banner_open_type":1,"banner_key_id":9},{"banner_cover":"http://opq5wk7p7.bkt.clouddn.com/09f5bffd4b40915bf5dbbcbbae11e162.png","banner_open_type":1,"banner_key_id":9}]
         * dataRecommend : [{"goods_id":7343,"goods_name":"商品名称11","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/f6106056d4a0742a3816735e71787e16.jpg","goods_price":"10.00","ratio":"/瓶/2瓶"},{"goods_id":7341,"goods_name":"商品名称13","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/http://opq5wk7p7.bkt.clouddn.com/f6106056d4a0742a3816735e71787e16.jpg","goods_price":"8.00","ratio":"//"},{"goods_id":7342,"goods_name":"商品名称12","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_price":"10.00","ratio":"/瓶/1瓶"},{"goods_id":7341,"goods_name":"商品名称13","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/http://opq5wk7p7.bkt.clouddn.com/f6106056d4a0742a3816735e71787e16.jpg","goods_price":"8.00","ratio":"//"},{"goods_id":7334,"goods_name":"324","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/4dbb899a35af16e846f96aff9ed28cea.jpg","goods_price":"0.00","ratio":"//瓶"},{"goods_id":7354,"goods_name":"名称1","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/http://opq5wk7p7.bkt.clouddn.com/087585f9a334e0961211e720dfe81863.jpg","goods_price":"6.00","ratio":"//"},{"goods_id":7354,"goods_name":"名称1","goods_cover":"http://opq5wk7p7.bkt.clouddn.com/http://opq5wk7p7.bkt.clouddn.com/087585f9a334e0961211e720dfe81863.jpg","goods_price":"6.00","ratio":"//"}]
         * ads : {"ads_cover":"http://opq5wk7p7.bkt.clouddn.com/fef903158eb13cd7566a2686b5abb42f.png","ads_key_id":3,"ads_open_type":1}
         */

        private AdsBean ads;
        private List<BannerBean> banner;
        private List<DataRecommendBean> dataRecommend;

        public AdsBean getAds() {
            return ads;
        }

        public void setAds(AdsBean ads) {
            this.ads = ads;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<DataRecommendBean> getDataRecommend() {
            return dataRecommend;
        }

        public void setDataRecommend(List<DataRecommendBean> dataRecommend) {
            this.dataRecommend = dataRecommend;
        }

        public static class AdsBean {
            /**
             * ads_cover : http://opq5wk7p7.bkt.clouddn.com/fef903158eb13cd7566a2686b5abb42f.png
             * ads_key_id : 3
             * ads_open_type : 1
             */

            private String ads_cover;
            private int ads_key_id;
            private int ads_open_type;

            public String getAds_cover() {
                return ads_cover;
            }

            public void setAds_cover(String ads_cover) {
                this.ads_cover = ads_cover;
            }

            public int getAds_key_id() {
                return ads_key_id;
            }

            public void setAds_key_id(int ads_key_id) {
                this.ads_key_id = ads_key_id;
            }

            public int getAds_open_type() {
                return ads_open_type;
            }

            public void setAds_open_type(int ads_open_type) {
                this.ads_open_type = ads_open_type;
            }
        }

        public static class BannerBean {
            /**
             * banner_cover : http://opq5wk7p7.bkt.clouddn.com/1432e33fa8f644eaca43628a59a9d0d3.png
             * banner_open_type : 1
             * banner_key_id : 1
             */

            private String banner_cover;
            private int banner_open_type;
            private int banner_key_id;

            public String getBanner_cover() {
                return banner_cover;
            }

            public void setBanner_cover(String banner_cover) {
                this.banner_cover = banner_cover;
            }

            public int getBanner_open_type() {
                return banner_open_type;
            }

            public void setBanner_open_type(int banner_open_type) {
                this.banner_open_type = banner_open_type;
            }

            public int getBanner_key_id() {
                return banner_key_id;
            }

            public void setBanner_key_id(int banner_key_id) {
                this.banner_key_id = banner_key_id;
            }
        }

        public static class DataRecommendBean {
            /**
             * goods_id : 7343
             * goods_name : 商品名称11
             * goods_cover : http://opq5wk7p7.bkt.clouddn.com/f6106056d4a0742a3816735e71787e16.jpg
             * goods_price : 10.00
             * ratio : /瓶/2瓶
             */

            private int goods_id;
            private String goods_name;
            private String goods_cover;
            private String goods_price;
            private String ratio;

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

            public String getRatio() {
                return ratio;
            }

            public void setRatio(String ratio) {
                this.ratio = ratio;
            }
        }
    }
}
