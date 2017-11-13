package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/23.
 */

public class JuBaoJiLuBean {


    /**
     * data : {"total":4,"current_page":1,"data":[{"report_id":1,"report_content":"内容1","report_time":1490236157,"format_report_time":"2017-03-23"},{"report_id":2,"report_content":"内容2","report_time":1490236157,"format_report_time":"2017-03-23"},{"report_id":3,"report_content":"这里是","report_time":1495260706,"format_report_time":"2017-05-20"},{"report_id":4,"report_content":"这里是1","report_time":1495260763,"format_report_time":"2017-05-20"}]}
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
         * total : 4
         * current_page : 1
         * data : [{"report_id":1,"report_content":"内容1","report_time":1490236157,"format_report_time":"2017-03-23"},{"report_id":2,"report_content":"内容2","report_time":1490236157,"format_report_time":"2017-03-23"},{"report_id":3,"report_content":"这里是","report_time":1495260706,"format_report_time":"2017-05-20"},{"report_id":4,"report_content":"这里是1","report_time":1495260763,"format_report_time":"2017-05-20"}]
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
             * report_id : 1
             * report_content : 内容1
             * report_time : 1490236157
             * format_report_time : 2017-03-23
             */

            private int report_id;
            private String report_content;
            private int report_time;
            private String format_report_time;

            public int getReport_id() {
                return report_id;
            }

            public void setReport_id(int report_id) {
                this.report_id = report_id;
            }

            public String getReport_content() {
                return report_content;
            }

            public void setReport_content(String report_content) {
                this.report_content = report_content;
            }

            public int getReport_time() {
                return report_time;
            }

            public void setReport_time(int report_time) {
                this.report_time = report_time;
            }

            public String getFormat_report_time() {
                return format_report_time;
            }

            public void setFormat_report_time(String format_report_time) {
                this.format_report_time = format_report_time;
            }
        }
    }
}
