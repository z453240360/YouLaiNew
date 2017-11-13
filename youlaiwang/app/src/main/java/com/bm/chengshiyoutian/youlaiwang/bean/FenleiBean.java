package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by 禽兽强 on 2017/7/20.
 */

public class FenleiBean {
    /**
     * code : 200
     * data : [{"gc_id":6,"gc_name":"调味品","image":"http://oqv8tlktu.bkt.clouddn.com/840f667f32dd18ae8f332819fd44f1a9.png?imageView2/1/w/20/h/20"},
     * {"gc_id":7,"gc_name":"冷冻冷藏","image":"http://oqv8tlktu.bkt.clouddn.com/2cf287f2c90cf4d0886addf1af6f5d01.jpg?imageView2/1/w/20/h/20"},
     * {"gc_id":4,"gc_name":"水产海鲜","image":"http://oqv8tlktu.bkt.clouddn.com/6e01708218e3839c843df12576e33de1.jpg?imageView2/1/w/20/h/20"},
     * {"gc_id":5,"gc_name":"酒水饮料","image":"http://oqv8tlktu.bkt.clouddn.com/614d6104a7ba1818e35e92ad8f55229c.jpg?imageView2/1/w/20/h/20"},
     * {"gc_id":2,"gc_name":"肉禽蛋类","image":"http://oqv8tlktu.bkt.clouddn.com/66ba8f7ce4fbf42d6692dbbf3fd45d68.jpg?imageView2/1/w/20/h/20"},
     * {"gc_id":1,"gc_name":"蔬菜水果","image":"http://oqv8tlktu.bkt.clouddn.com/fe3845b27649701e4c65fb371babe296.jpg?imageView2/1/w/20/h/20"},
     * {"gc_id":3,"gc_name":"粮油副食","image":"http://oqv8tlktu.bkt.clouddn.com/ea85250c2a129b7c5ca24be2d3e3df02.jpg?imageView2/1/w/20/h/20"},
     * {"gc_id":8,"gc_name":"厅厨用品","image":"http://oqv8tlktu.bkt.clouddn.com/6b63a576ad556a8ba361bad74cb9a115.jpg?imageView2/1/w/20/h/20"},
     * {"gc_id":9,"gc_name":"进口商品","image":"http://oqv8tlktu.bkt.clouddn.com/34ebb6929eb1e6549e1b5a795f45ed3b.jpg?imageView2/1/w/20/h/20"}]
     * msg :
     */

    private int code;
    private String msg;
    /**
     * gc_id : 6
     * gc_name : 调味品
     * image : http://oqv8tlktu.bkt.clouddn.com/840f667f32dd18ae8f332819fd44f1a9.png?imageView2/1/w/20/h/20
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
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
