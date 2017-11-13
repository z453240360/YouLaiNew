package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 */

public class XiangXing3Bean {


    /**
     * data : {"total":1,"current_page":1,"data":[{"comment_content":"123456","comment_addtime":"2017-06-24","comment_images":["http://oqv8tlktu.bkt.clouddn.com/Fryv_YZvqwtSdFHuw5xtTI0xnmNs"],"avatar":"http://oqv8tlktu.bkt.clouddn.com/FmNjuck32CzxBJ65mZmvzlACq96G","user_nicename":"程磊"}]}
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
         * total : 1
         * current_page : 1
         * data : [{"comment_content":"123456","comment_addtime":"2017-06-24","comment_images":["http://oqv8tlktu.bkt.clouddn.com/Fryv_YZvqwtSdFHuw5xtTI0xnmNs"],"avatar":"http://oqv8tlktu.bkt.clouddn.com/FmNjuck32CzxBJ65mZmvzlACq96G","user_nicename":"程磊"}]
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
             * comment_content : 123456
             * comment_addtime : 2017-06-24
             * comment_images : ["http://oqv8tlktu.bkt.clouddn.com/Fryv_YZvqwtSdFHuw5xtTI0xnmNs"]
             * avatar : http://oqv8tlktu.bkt.clouddn.com/FmNjuck32CzxBJ65mZmvzlACq96G
             * user_nicename : 程磊
             */

            private String comment_content;
            private String comment_addtime;
            private String avatar;
            private String user_nicename;
            private List<String> comment_images;

            public String getComment_content() {
                return comment_content;
            }

            public void setComment_content(String comment_content) {
                this.comment_content = comment_content;
            }

            public String getComment_addtime() {
                return comment_addtime;
            }

            public void setComment_addtime(String comment_addtime) {
                this.comment_addtime = comment_addtime;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUser_nicename() {
                return user_nicename;
            }

            public void setUser_nicename(String user_nicename) {
                this.user_nicename = user_nicename;
            }

            public List<String> getComment_images() {
                return comment_images;
            }

            public void setComment_images(List<String> comment_images) {
                this.comment_images = comment_images;
            }
        }
    }
}
