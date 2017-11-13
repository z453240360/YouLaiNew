package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

/**
 * Created by Administrator on 2017/11/8.
 */

public class CarNumberBean {

    /**
     * data : {"cartNum":"23"}
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
         * cartNum : 23
         */

        private String cartNum;

        public String getCartNum() {
            return cartNum;
        }

        public void setCartNum(String cartNum) {
            this.cartNum = cartNum;
        }
    }
}
