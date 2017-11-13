package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/26.
 */

public class LieBiaoBean {
    /**
     * data : {"total":3,"current_page":1,"data":[{"article_id":35,"article_title":"咨询列表3","article_see_num":1,"article_cover":"http://opq5wk7p7.bkt.clouddn.com/","article_time":"2017年05月19日"},{"article_id":34,"article_title":"咨询列表2","article_see_num":1,"article_cover":"http://opq5wk7p7.bkt.clouddn.com/","article_time":"2017年05月19日"},{"article_id":32,"article_title":"咨询列表1","article_see_num":0,"article_cover":"http://opq5wk7p7.bkt.clouddn.com/","article_time":"2017年05月19日"}]}
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
         * total : 3
         * current_page : 1
         * data : [{"article_id":35,"article_title":"咨询列表3","article_see_num":1,"article_cover":"http://opq5wk7p7.bkt.clouddn.com/","article_time":"2017年05月19日"},{"article_id":34,"article_title":"咨询列表2","article_see_num":1,"article_cover":"http://opq5wk7p7.bkt.clouddn.com/","article_time":"2017年05月19日"},{"article_id":32,"article_title":"咨询列表1","article_see_num":0,"article_cover":"http://opq5wk7p7.bkt.clouddn.com/","article_time":"2017年05月19日"}]
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
             * article_id : 35
             * article_title : 咨询列表3
             * article_see_num : 1
             * article_cover : http://opq5wk7p7.bkt.clouddn.com/
             * article_time : 2017年05月19日
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
