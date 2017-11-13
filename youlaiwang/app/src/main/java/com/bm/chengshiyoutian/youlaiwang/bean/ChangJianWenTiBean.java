package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/23.
 */

public class ChangJianWenTiBean {

    /**
     * data : {"total":3,"current_page":1,"data":[{"article_id":41,"article_title":"问题3","article_content":"问题详情3"},{"article_id":40,"article_title":"问题2","article_content":"问题详情2"},{"article_id":39,"article_title":"问题1","article_content":"问题详情1"}]}
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
         * data : [{"article_id":41,"article_title":"问题3","article_content":"问题详情3"},{"article_id":40,"article_title":"问题2","article_content":"问题详情2"},{"article_id":39,"article_title":"问题1","article_content":"问题详情1"}]
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
             * article_id : 41
             * article_title : 问题3
             * article_content : 问题详情3
             */

            private int article_id;
            private String article_title;
            private String article_content;

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

            public String getArticle_content() {
                return article_content;
            }

            public void setArticle_content(String article_content) {
                this.article_content = article_content;
            }
        }
    }
}
