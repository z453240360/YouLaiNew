package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class BuyRecordBean {

    /**
     * data : {"total":221,"current_page":1,"data":[{"goods_id":8583,"goods_name":"红大米  1千克  农家粗粮五谷杂粮米面粮油 ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/0aa436eb4585641541e667aa399fd9da.jpg?imageView2/1/w/200/h/200","goods_price":"38.00","goods_spec_remark":"1","ratio":"/千克/1"},{"goods_id":12445,"goods_name":"虾皮","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/dc0ae836cfe3d300ebe95619055604ed.jpg?imageView2/1/w/200/h/200","goods_price":"30.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12433,"goods_name":"榨菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/733faa5419362fd9f7cc028284e4b357.jpg?imageView2/1/w/200/h/200","goods_price":"2.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12427,"goods_name":"雪菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/759d8bdd434eab9c15939f194ca67a95.jpg?imageView2/1/w/200/h/200","goods_price":"2.60","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12419,"goods_name":"咸菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/6eef322e286de2270fd7d836d428ae9c.jpg?imageView2/1/w/200/h/200","goods_price":"1.80","goods_spec_remark":"500克","ratio":"/斤/500克"},{"goods_id":12418,"goods_name":"酸豆角","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/cdcee1c488546e8120c26e69f37ae1bc.jpg?imageView2/1/w/200/h/200","goods_price":"2.50","goods_spec_remark":"500克","ratio":"/斤/500克"},{"goods_id":8592,"goods_name":"伞塔牌 厨房炒菜调料调味品套装50g*7","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/d5104fd66330372c7cfb3912eb3542de.jpg?imageView2/1/w/200/h/200","goods_price":"25.00","goods_spec_remark":"1","ratio":"/包/1"},{"goods_id":8591,"goods_name":"海天蒸鱼豉油1.75L ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/191e17e0522017ca1989f4d4149b9dcb.jpg?imageView2/1/w/200/h/200","goods_price":"40.00","goods_spec_remark":"1","ratio":"/瓶/1"},{"goods_id":8590,"goods_name":"海天味极鲜酱油1.9L 酿造酱油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/0255af332b271a1870fe9788812c6182.jpg?imageView2/1/w/200/h/200","goods_price":"24.90","goods_spec_remark":"1.9L","ratio":"/瓶/1.9L"},{"goods_id":8589,"goods_name":"王守义 十三香 调味品 香料 佐料 40g","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/77b599a1da7e98dda295be51565824b7.jpg?imageView2/1/w/200/h/200","goods_price":"5.00","goods_spec_remark":"1","ratio":"/盒/1"}]}
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
         * total : 221
         * current_page : 1
         * data : [{"goods_id":8583,"goods_name":"红大米  1千克  农家粗粮五谷杂粮米面粮油 ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/0aa436eb4585641541e667aa399fd9da.jpg?imageView2/1/w/200/h/200","goods_price":"38.00","goods_spec_remark":"1","ratio":"/千克/1"},{"goods_id":12445,"goods_name":"虾皮","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/dc0ae836cfe3d300ebe95619055604ed.jpg?imageView2/1/w/200/h/200","goods_price":"30.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12433,"goods_name":"榨菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/733faa5419362fd9f7cc028284e4b357.jpg?imageView2/1/w/200/h/200","goods_price":"2.00","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12427,"goods_name":"雪菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/759d8bdd434eab9c15939f194ca67a95.jpg?imageView2/1/w/200/h/200","goods_price":"2.60","goods_spec_remark":"500g","ratio":"/斤/500g"},{"goods_id":12419,"goods_name":"咸菜","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/6eef322e286de2270fd7d836d428ae9c.jpg?imageView2/1/w/200/h/200","goods_price":"1.80","goods_spec_remark":"500克","ratio":"/斤/500克"},{"goods_id":12418,"goods_name":"酸豆角","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/cdcee1c488546e8120c26e69f37ae1bc.jpg?imageView2/1/w/200/h/200","goods_price":"2.50","goods_spec_remark":"500克","ratio":"/斤/500克"},{"goods_id":8592,"goods_name":"伞塔牌 厨房炒菜调料调味品套装50g*7","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/d5104fd66330372c7cfb3912eb3542de.jpg?imageView2/1/w/200/h/200","goods_price":"25.00","goods_spec_remark":"1","ratio":"/包/1"},{"goods_id":8591,"goods_name":"海天蒸鱼豉油1.75L ","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/191e17e0522017ca1989f4d4149b9dcb.jpg?imageView2/1/w/200/h/200","goods_price":"40.00","goods_spec_remark":"1","ratio":"/瓶/1"},{"goods_id":8590,"goods_name":"海天味极鲜酱油1.9L 酿造酱油","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/0255af332b271a1870fe9788812c6182.jpg?imageView2/1/w/200/h/200","goods_price":"24.90","goods_spec_remark":"1.9L","ratio":"/瓶/1.9L"},{"goods_id":8589,"goods_name":"王守义 十三香 调味品 香料 佐料 40g","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/77b599a1da7e98dda295be51565824b7.jpg?imageView2/1/w/200/h/200","goods_price":"5.00","goods_spec_remark":"1","ratio":"/盒/1"}]
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
             * goods_id : 8583
             * goods_name : 红大米  1千克  农家粗粮五谷杂粮米面粮油
             * goods_cover : http://oqv8tlktu.bkt.clouddn.com/0aa436eb4585641541e667aa399fd9da.jpg?imageView2/1/w/200/h/200
             * goods_price : 38.00
             * goods_spec_remark : 1
             * ratio : /千克/1
             */

            private int goods_id;
            private String goods_name;
            private String goods_cover;
            private String goods_price;
            private String goods_spec_remark;
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
