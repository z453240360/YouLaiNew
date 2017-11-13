package com.bm.chengshiyoutian.youlaiwang.bean;

/**
 * Created by sld on 2017/5/17.
 */

public class ZhuCeBean {


    /**
     * code : 200
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjU5NSwiaXNzIjoiaHR0cDovL3lvdWxhaXdhbmcuZXhjZWxzaW9yYnJhbmQuY24vYXBpL3VzZXIvcmVnaXN0ZXIiLCJpYXQiOjE0OTc5NTA1NjksImV4cCI6MTQ5ODAzNDU2OSwibmJmIjoxNDk3OTUwNTY5LCJqdGkiOiJET2JxeWd0blRKMDNqUHZrIn0.-fs-Q6_SGWtAvGI0RqKfompeNcou-Qyve0La1c5FN-I"}
     * msg :
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public static class DataBean {
        /**
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjU5NSwiaXNzIjoiaHR0cDovL3lvdWxhaXdhbmcuZXhjZWxzaW9yYnJhbmQuY24vYXBpL3VzZXIvcmVnaXN0ZXIiLCJpYXQiOjE0OTc5NTA1NjksImV4cCI6MTQ5ODAzNDU2OSwibmJmIjoxNDk3OTUwNTY5LCJqdGkiOiJET2JxeWd0blRKMDNqUHZrIn0.-fs-Q6_SGWtAvGI0RqKfompeNcou-Qyve0La1c5FN-I
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
