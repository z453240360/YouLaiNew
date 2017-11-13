package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/6/5.
 */

public class FaQiZhiFuBean1 {


    /**
     * money : 15.5
     * orderSns : 647820174670720182404658
     * stores : []
     */

    private DataBean data;
    /**
     * data : {"money":15.5,"orderSns":"647820174670720182404658","stores":[]}
     * msg : 提交订单成功
     * code : 200
     */

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
        private double money;
        private String orderSns;
        private List<?> stores;

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
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
