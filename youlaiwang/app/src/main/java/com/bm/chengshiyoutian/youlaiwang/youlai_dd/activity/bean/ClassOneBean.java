package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class ClassOneBean {

    /**
     * data : [{"gc_id":1,"gc_name":"蔬菜水果","image":"http://oqv8tlktu.bkt.clouddn.com/ec53946be74508261af8c910995000b5.png?imageView2/1/w/20/h/20"},{"gc_id":2,"gc_name":"肉禽蛋类","image":"http://oqv8tlktu.bkt.clouddn.com/3dc1996255189f746c753306580eb374.png?imageView2/1/w/20/h/20"},{"gc_id":3,"gc_name":"粮油副食","image":"http://oqv8tlktu.bkt.clouddn.com/4846f3335a0a6ae2ba0837efc8af5594.png?imageView2/1/w/20/h/20"},{"gc_id":4,"gc_name":"水产海鲜","image":"http://oqv8tlktu.bkt.clouddn.com/3a19d39108ecadd4905f98ed89df3865.png?imageView2/1/w/20/h/20"},{"gc_id":5,"gc_name":"酒水饮料","image":"http://oqv8tlktu.bkt.clouddn.com/e129d80e55ef3600efb8c03e99c5162d.png?imageView2/1/w/20/h/20"},{"gc_id":6,"gc_name":"调味品","image":"http://oqv8tlktu.bkt.clouddn.com/7e4b4956b51ac67ee93396ef82dfa2b8.png?imageView2/1/w/20/h/20"},{"gc_id":7,"gc_name":"冷冻冷藏","image":"http://oqv8tlktu.bkt.clouddn.com/67b41108b5972158f4da2f755b32df5d.png?imageView2/1/w/20/h/20"},{"gc_id":8,"gc_name":"厅厨用品","image":"http://oqv8tlktu.bkt.clouddn.com/0f094294477a71e6f4f9aeb8d4ae7be7.png?imageView2/1/w/20/h/20"},{"gc_id":9,"gc_name":"进口商品","image":"http://oqv8tlktu.bkt.clouddn.com/e03c1e83703c156a74913aedc60a7829.png?imageView2/1/w/20/h/20"}]
     * msg :
     * code : 200
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * gc_id : 1
         * gc_name : 蔬菜水果
         * image : http://oqv8tlktu.bkt.clouddn.com/ec53946be74508261af8c910995000b5.png?imageView2/1/w/20/h/20
         */

        private int gc_id;
        private String gc_name;
        private String image;

        public int getGc_id() {
            return gc_id;
        }

        public void setGc_id(int gc_id) {
            this.gc_id = gc_id;
        }

        public String getGc_name() {
            return gc_name;
        }

        public void setGc_name(String gc_name) {
            this.gc_name = gc_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
