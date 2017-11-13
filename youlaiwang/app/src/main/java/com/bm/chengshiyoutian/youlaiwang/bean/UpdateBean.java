package com.bm.chengshiyoutian.youlaiwang.bean;

/**
 * Created by P on 2017/7/10.
 */

public class UpdateBean {

    /**
     * code : 200
     * data : {"dataAndroid":{"av_content":"1","av_id":2,"av_type":1,"av_url":"/download/1.apk","av_version":"1","format_type":"安卓"},"dataIos":{"av_content":"","av_id":0,"av_type":0,"av_url":"","av_version":0}}
     * msg :
     */

    public int code;
    public DataBean data;
    public String msg;



    public static class DataBean {
        /**
         * dataAndroid : {"av_content":"1","av_id":2,"av_type":1,"av_url":"/download/1.apk","av_version":"1","format_type":"安卓"}
         * dataIos : {"av_content":"","av_id":0,"av_type":0,"av_url":"","av_version":0}
         */

        public DataAndroidBean dataAndroid;
        public DataIosBean dataIos;



        public static class DataAndroidBean {
            /**
             * av_content : 1
             * av_id : 2
             * av_type : 1
             * av_url : /download/1.apk
             * av_version : 1
             * format_type : 安卓
             */

            private String av_content;
            private int av_id;
            private int av_type;
            private String av_url;
            private String av_version;
            private String format_type;

            public String getAv_content() {
                return av_content;
            }

            public void setAv_content(String av_content) {
                this.av_content = av_content;
            }

            public int getAv_id() {
                return av_id;
            }

            public void setAv_id(int av_id) {
                this.av_id = av_id;
            }

            public int getAv_type() {
                return av_type;
            }

            public void setAv_type(int av_type) {
                this.av_type = av_type;
            }

            public String getAv_url() {
                return av_url;
            }

            public void setAv_url(String av_url) {
                this.av_url = av_url;
            }

            public String getAv_version() {
                return av_version;
            }

            public void setAv_version(String av_version) {
                this.av_version = av_version;
            }

            public String getFormat_type() {
                return format_type;
            }

            public void setFormat_type(String format_type) {
                this.format_type = format_type;
            }
        }

        public static class DataIosBean {
            /**
             * av_content :
             * av_id : 0
             * av_type : 0
             * av_url :
             * av_version : 0
             */

            public String av_content;
            public int av_id;
            public int av_type;
            public String av_url;
            public int av_version;

           
        }
    }
}
