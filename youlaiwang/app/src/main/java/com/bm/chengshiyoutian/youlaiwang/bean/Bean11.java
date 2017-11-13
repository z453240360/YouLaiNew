package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/6/14.
 */

public class Bean11 {


    /**
     * code : 200
     * data : {"money":"268.00","orderSns":"296420177580525104548268","stores":[]}
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
         * money : 268.00
         * orderSns : 296420177580525104548268
         * stores : []
         */

        private String money;
        private String orderSns;
        private List<?> stores;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getOrderSns() {
            return orderSns;
        }

        public void setOrderSns(String orderSns) {
            this.orderSns = orderSns;
        }

        public List<?> getStores() {
            return stores;
        }

        public void setStores(List<?> stores) {
            this.stores = stores;
        }
    }
}
