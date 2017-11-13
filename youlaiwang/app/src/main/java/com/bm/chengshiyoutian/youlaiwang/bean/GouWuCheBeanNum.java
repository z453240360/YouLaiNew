package com.bm.chengshiyoutian.youlaiwang.bean;

/**
 * Created by sld on 2017/6/16.
 */

public class GouWuCheBeanNum {


    /**
     * code : 200
     * data : {"cartNum":2}
     * msg : 删除购物车成功
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
         * cartNum : 2
         */

        private int cartNum;

        public int getCartNum() {
            return cartNum;
        }

        public void setCartNum(int cartNum) {
            this.cartNum = cartNum;
        }
    }
}
