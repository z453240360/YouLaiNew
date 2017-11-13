package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/23.
 */

public class XiTongTiXingBean {
    /**
     * data : {"total":3,"current_page":1,"data":[{"id":156,"title":"购买商品","content":"2017-03-19购买商品，春节大酬宾！好衣服5折销售，大家快来买呀！一个字，买，买，买！","addtime":1495199783,"format_addtime":"2017-05-19"},{"id":155,"title":"购买商品","content":"2017-03-17购买商品，结婚礼服\u2014休伯特416","addtime":1495199783,"format_addtime":"2017-05-19"},{"id":154,"title":"购买商品","content":"2017-03-17购买商品，结婚礼服\u2014休伯特416","addtime":1495199783,"format_addtime":"2017-05-19"}]}
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
         * data : [{"id":156,"title":"购买商品","content":"2017-03-19购买商品，春节大酬宾！好衣服5折销售，大家快来买呀！一个字，买，买，买！","addtime":1495199783,"format_addtime":"2017-05-19"},{"id":155,"title":"购买商品","content":"2017-03-17购买商品，结婚礼服\u2014休伯特416","addtime":1495199783,"format_addtime":"2017-05-19"},{"id":154,"title":"购买商品","content":"2017-03-17购买商品，结婚礼服\u2014休伯特416","addtime":1495199783,"format_addtime":"2017-05-19"}]
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
             * id : 156
             * title : 购买商品
             * content : 2017-03-19购买商品，春节大酬宾！好衣服5折销售，大家快来买呀！一个字，买，买，买！
             * addtime : 1495199783
             * format_addtime : 2017-05-19
             */

            private int id;
            private String title;
            private String content;
            private int addtime;
            private String format_addtime;
            private int is_read;

            public int getIs_read() {
                return is_read;
            }

            public void setIs_read(int is_read) {
                this.is_read = is_read;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getAddtime() {
                return addtime;
            }

            public void setAddtime(int addtime) {
                this.addtime = addtime;
            }

            public String getFormat_addtime() {
                return format_addtime;
            }

            public void setFormat_addtime(String format_addtime) {
                this.format_addtime = format_addtime;
            }
        }
    }
}
