package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/26.
 */

public class Bean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * data : 105
         */

        private int data;

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }
}
