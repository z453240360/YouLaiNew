package com.bm.chengshiyoutian.youlaiwang.bean;

/**
 * Created by sld on 2017/5/17.
 */

public class DengLuBean1 {
    /**
     * data : {
     *
     * "token":"ia"}
     * msg :
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
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjU0MCwiaXNzIjoiaHR0cDpcL1wvMTE1LjI5LjI0Ni44ODo4ODA5XC9hcGlcL3VzZXJcL2xvZ2luIiwiaWF0IjoxNDk1MDA1ODIyLCJleHAiOjE0OTUwMDk0MjIsIm5iZiI6MTQ5NTAwNTgyMiwianRpIjoiSVkyOER0NjZFaGRRWEtBbyJ9.U1zs1FrU6IjKnvBHT47T3n8gF2elRJVZcYWE9kvAB6w
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
