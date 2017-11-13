package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 * 首页轮播对象
 *
 */

public class ShouYe_BannerBean {

    /**
     * data : {"banner":[{"banner_cover":"http://oqv8tlktu.bkt.clouddn.com/34b0b73b0a774a9955d90d15ab448140.jpg","banner_open_type":2,"banner_key_id":217}],"dataRecommend":[{"goods_id":8384,"goods_name":"750G四季排骨饭","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/f8c668200b598d659edc510912e9a365.jpg?imageView2/1/w/200/h/200","goods_price":"18.00","goods_spec_remark":"750克","ratio":"/个/750克"},{"goods_id":12118,"goods_name":"带皮带骨羊肉","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/5d33f27bd5ea27b90e324bba7b791636.jpg?imageView2/1/w/200/h/200","goods_price":"20.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12084,"goods_name":"小南瓜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/be8f4ef7879229dd572f1548eafb6b70.jpg?imageView2/1/w/200/h/200","goods_price":"2.90","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12305,"goods_name":"全精肉","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/0062ad70e787dbed7d085eafbb2b996a.jpg?imageView2/1/w/200/h/200","goods_price":"13.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12303,"goods_name":"小排","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/d48601e7b6086f21414ebaeec7fdc00b.jpg?imageView2/1/w/200/h/200","goods_price":"10.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12250,"goods_name":"秋葵","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/d782a79d27e703406d70bc880d045bb5.jpg?imageView2/1/w/200/h/200","goods_price":"5.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12136,"goods_name":"老姜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/327139b4f7310647a68a7b87343ec017.jpg?imageView2/1/w/200/h/200","goods_price":"5.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12116,"goods_name":"红薯杆","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/bd13a6e09793de3d207aa4f9565f4329.jpg?imageView2/1/w/200/h/200","goods_price":"2.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12095,"goods_name":"毛豆","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/9f35d490cfc1a1572f4ee8943936a87a.jpg?imageView2/1/w/200/h/200","goods_price":"2.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12088,"goods_name":"包菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/5ffd699c6dca15ae8e8f9cc9709b15c8.jpg?imageView2/1/w/200/h/200","goods_price":"1.20","goods_spec_remark":"500g","ratio":"/斤/500g"}],"ads":{"ads_cover":"http://oqv8tlktu.bkt.clouddn.com/c85155009cf073c792d9c343a79f5429.png","ads_key_id":3,"ads_open_type":1}}
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
         * banner : [{"banner_cover":"http://oqv8tlktu.bkt.clouddn.com/34b0b73b0a774a9955d90d15ab448140.jpg","banner_open_type":2,"banner_key_id":217}]
         * dataRecommend : [{"goods_id":8384,"goods_name":"750G四季排骨饭","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/f8c668200b598d659edc510912e9a365.jpg?imageView2/1/w/200/h/200","goods_price":"18.00","goods_spec_remark":"750克","ratio":"/个/750克"},{"goods_id":12118,"goods_name":"带皮带骨羊肉","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/5d33f27bd5ea27b90e324bba7b791636.jpg?imageView2/1/w/200/h/200","goods_price":"20.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12084,"goods_name":"小南瓜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/be8f4ef7879229dd572f1548eafb6b70.jpg?imageView2/1/w/200/h/200","goods_price":"2.90","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12305,"goods_name":"全精肉","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/0062ad70e787dbed7d085eafbb2b996a.jpg?imageView2/1/w/200/h/200","goods_price":"13.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12303,"goods_name":"小排","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/d48601e7b6086f21414ebaeec7fdc00b.jpg?imageView2/1/w/200/h/200","goods_price":"10.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12250,"goods_name":"秋葵","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/d782a79d27e703406d70bc880d045bb5.jpg?imageView2/1/w/200/h/200","goods_price":"5.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12136,"goods_name":"老姜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/327139b4f7310647a68a7b87343ec017.jpg?imageView2/1/w/200/h/200","goods_price":"5.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12116,"goods_name":"红薯杆","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/bd13a6e09793de3d207aa4f9565f4329.jpg?imageView2/1/w/200/h/200","goods_price":"2.50","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12095,"goods_name":"毛豆","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/9f35d490cfc1a1572f4ee8943936a87a.jpg?imageView2/1/w/200/h/200","goods_price":"2.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12088,"goods_name":"包菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/5ffd699c6dca15ae8e8f9cc9709b15c8.jpg?imageView2/1/w/200/h/200","goods_price":"1.20","goods_spec_remark":"500g","ratio":"/斤/500g"}]
         * ads : {"ads_cover":"http://oqv8tlktu.bkt.clouddn.com/c85155009cf073c792d9c343a79f5429.png","ads_key_id":3,"ads_open_type":1}
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
             * ads_cover : http://oqv8tlktu.bkt.clouddn.com/c85155009cf073c792d9c343a79f5429.png
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
             * banner_cover : http://oqv8tlktu.bkt.clouddn.com/34b0b73b0a774a9955d90d15ab448140.jpg
             * banner_open_type : 2
             * banner_key_id : 217
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
             * goods_id : 8384
             * goods_name : 750G四季排骨饭
             * goods_cover : http://oqv8tlktu.bkt.clouddn.com/f8c668200b598d659edc510912e9a365.jpg?imageView2/1/w/200/h/200
             * goods_price : 18.00
             * goods_spec_remark : 750克
             * ratio : /个/750克
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
