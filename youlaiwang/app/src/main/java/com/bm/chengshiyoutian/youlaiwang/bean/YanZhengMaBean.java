package com.bm.chengshiyoutian.youlaiwang.bean;

/**
 * Created by sld on 2017/5/17.
 */

public class YanZhengMaBean {

    /**
     * data : {"token":"fd2011ec8012a80d8271d2f761899ed5"}
     * msg : 短信发送成功
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
         * token : fd2011ec8012a80d8271d2f761899ed5
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
