package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/6/5.
 */

public class FaQiZhiFuBean {


    /**
     * code : 200
     * data : {"money":0,"orderSns":"","stores":[{"store_logo":"","store_name":"四季食品","store_phone":"15058232031"},{"store_logo":"4f45674c4c0e0776835ff899f9d39478.png","store_name":"原创设计店铺","store_phone":"13127987897"}]}
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
         * money : 0
         * orderSns :
         * stores : [{"store_logo":"","store_name":"四季食品","store_phone":"15058232031"},{"store_logo":"4f45674c4c0e0776835ff899f9d39478.png","store_name":"原创设计店铺","store_phone":"13127987897"}]
         */

        private int money;
        private String orderSns;
        private List<StoresBean> stores;

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getOrderSns() {
            return orderSns;
        }

        public void setOrderSns(String orderSns) {
            this.orderSns = orderSns;
        }

        public List<StoresBean> getStores() {
            return stores;
        }

        public void setStores(List<StoresBean> stores) {
            this.stores = stores;
        }

        public static class StoresBean {
            /**
             * store_logo :
             * store_name : 四季食品
             * store_phone : 15058232031
             */

            private String store_logo;
            private String store_name;
            private String store_phone;

            public String getStore_logo() {
                return store_logo;
            }

            public void setStore_logo(String store_logo) {
                this.store_logo = store_logo;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getStore_phone() {
                return store_phone;
            }

            public void setStore_phone(String store_phone) {
                this.store_phone = store_phone;
            }
        }
    }
}
