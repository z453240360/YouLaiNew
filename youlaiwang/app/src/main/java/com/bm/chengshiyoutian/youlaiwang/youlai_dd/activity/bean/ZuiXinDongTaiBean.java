package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 *
 * 最新动态列表
 *
 *
 */

public class ZuiXinDongTaiBean {

    /**
     * data : {"total":6,"current_page":1,"data":[{"article_id":55,"article_title":"浙江省食品药品监督管理局朱志泉局长一行莅临义乌由来网考察调研","article_see_num":25,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/fe4359977de3a142ed84e0dad923bfd0.jpg","article_time":"2017年08月09日"},{"article_id":54,"article_title":"由来网一期先锋创业实习班齐聚义乌双创基地培训 ","article_see_num":33,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/d4d51632275baf02f27d066ac39d1233.jpg","article_time":"2017年07月20日"},{"article_id":53,"article_title":"义乌高新产业园管委会王国成主任一行参观由来网并表示全力支持项目工作 ","article_see_num":10,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/9bdc68d1300028f36979768d83916cea.jpg","article_time":"2017年07月20日"},{"article_id":52,"article_title":"湖南耒阳、怀化合作伙伴莅临义乌双创基地参观培训","article_see_num":35,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/0675c5aec7ffc05cd45cfd0a0b935a1e.jpeg","article_time":"2017年07月12日"},{"article_id":51,"article_title":"首批中国烹饪协会团体标准在京正式发布 ","article_see_num":52,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/b3a8461264e74a7df7e1c42e31651425.jpg","article_time":"2017年07月12日"},{"article_id":47,"article_title":"让癌症不再嚣张 漂洋过海来治\u201c你\u201d！","article_see_num":20,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/afbe08c13c24840a97049d9e8edb0886.jpg","article_time":"2017年06月16日"}]}
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
         * total : 6
         * current_page : 1
         * data : [{"article_id":55,"article_title":"浙江省食品药品监督管理局朱志泉局长一行莅临义乌由来网考察调研","article_see_num":25,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/fe4359977de3a142ed84e0dad923bfd0.jpg","article_time":"2017年08月09日"},{"article_id":54,"article_title":"由来网一期先锋创业实习班齐聚义乌双创基地培训 ","article_see_num":33,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/d4d51632275baf02f27d066ac39d1233.jpg","article_time":"2017年07月20日"},{"article_id":53,"article_title":"义乌高新产业园管委会王国成主任一行参观由来网并表示全力支持项目工作 ","article_see_num":10,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/9bdc68d1300028f36979768d83916cea.jpg","article_time":"2017年07月20日"},{"article_id":52,"article_title":"湖南耒阳、怀化合作伙伴莅临义乌双创基地参观培训","article_see_num":35,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/0675c5aec7ffc05cd45cfd0a0b935a1e.jpeg","article_time":"2017年07月12日"},{"article_id":51,"article_title":"首批中国烹饪协会团体标准在京正式发布 ","article_see_num":52,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/b3a8461264e74a7df7e1c42e31651425.jpg","article_time":"2017年07月12日"},{"article_id":47,"article_title":"让癌症不再嚣张 漂洋过海来治\u201c你\u201d！","article_see_num":20,"article_cover":"http://oqv8tlktu.bkt.clouddn.com/afbe08c13c24840a97049d9e8edb0886.jpg","article_time":"2017年06月16日"}]
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
             * article_id : 55
             * article_title : 浙江省食品药品监督管理局朱志泉局长一行莅临义乌由来网考察调研
             * article_see_num : 25
             * article_cover : http://oqv8tlktu.bkt.clouddn.com/fe4359977de3a142ed84e0dad923bfd0.jpg
             * article_time : 2017年08月09日
             */

            private int article_id;
            private String article_title;
            private int article_see_num;
            private String article_cover;
            private String article_time;

            public int getArticle_id() {
                return article_id;
            }

            public void setArticle_id(int article_id) {
                this.article_id = article_id;
            }

            public String getArticle_title() {
                return article_title;
            }

            public void setArticle_title(String article_title) {
                this.article_title = article_title;
            }

            public int getArticle_see_num() {
                return article_see_num;
            }

            public void setArticle_see_num(int article_see_num) {
                this.article_see_num = article_see_num;
            }

            public String getArticle_cover() {
                return article_cover;
            }

            public void setArticle_cover(String article_cover) {
                this.article_cover = article_cover;
            }

            public String getArticle_time() {
                return article_time;
            }

            public void setArticle_time(String article_time) {
                this.article_time = article_time;
            }
        }
    }
}
