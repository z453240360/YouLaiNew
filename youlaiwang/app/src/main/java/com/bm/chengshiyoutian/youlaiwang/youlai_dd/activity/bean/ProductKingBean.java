package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ProductKingBean {

    /**
     * data : [{"gc_id":2848,"gc_name":"米面杂粮"},{"gc_id":2849,"gc_name":"食用油"},{"gc_id":2850,"gc_name":"调味酱料"},{"gc_id":2851,"gc_name":"干货酱菜"},{"gc_id":2865,"gc_name":"果干零食"},{"gc_id":2891,"gc_name":"罐头"},{"gc_id":2903,"gc_name":"药膳补品"},{"gc_id":2883,"gc_name":"冷冻猪肉"},{"gc_id":2884,"gc_name":"冷冻牛羊肉"},{"gc_id":2894,"gc_name":"冷冻禽类"},{"gc_id":2895,"gc_name":"冷冻面点"},{"gc_id":2896,"gc_name":"冷冻蔬菜"},{"gc_id":2897,"gc_name":"冷冻调理包"},{"gc_id":2898,"gc_name":"冰鲜肉类"},{"gc_id":2899,"gc_name":"冷藏熟食"},{"gc_id":2836,"gc_name":"猪肉类"},{"gc_id":2838,"gc_name":"肉制品"},{"gc_id":2835,"gc_name":"牛羊类"},{"gc_id":2837,"gc_name":"禽类"},{"gc_id":2839,"gc_name":"蛋类"},{"gc_id":2909,"gc_name":"酱油醋酒"},{"gc_id":2904,"gc_name":"蚝油、汁类"},{"gc_id":2866,"gc_name":"冰鲜水产"},{"gc_id":2862,"gc_name":"冲调"},{"gc_id":2824,"gc_name":"水果组合"},{"gc_id":2823,"gc_name":"进口水果"},{"gc_id":2821,"gc_name":"国产水果"},{"gc_id":2820,"gc_name":"组合蔬菜"},{"gc_id":2900,"gc_name":"餐厅用具"},{"gc_id":2901,"gc_name":"清洁用品"},{"gc_id":2902,"gc_name":"配件"},{"gc_id":2887,"gc_name":"进口商品"},{"gc_id":2906,"gc_name":"鸡精味精"},{"gc_id":2907,"gc_name":"酱类"},{"gc_id":2863,"gc_name":"乳品饮料"},{"gc_id":2893,"gc_name":"咖啡"},{"gc_id":2864,"gc_name":"茶"},{"gc_id":2867,"gc_name":"虾蟹贝类"},{"gc_id":2868,"gc_name":"刺身即食"},{"gc_id":2870,"gc_name":"中式面点"},{"gc_id":2871,"gc_name":"荤素卤制"},{"gc_id":2853,"gc_name":"新鲜活杀"},{"gc_id":2873,"gc_name":"豆制品"},{"gc_id":2819,"gc_name":"时令蔬菜"},{"gc_id":2860,"gc_name":"酒水"},{"gc_id":2885,"gc_name":"烹饪用具"},{"gc_id":2886,"gc_name":"厨房电器"},{"gc_id":2890,"gc_name":"新鲜蔬菜"},{"gc_id":2861,"gc_name":"饮料"},{"gc_id":2905,"gc_name":"盐、糖类"},{"gc_id":2908,"gc_name":"香料类"},{"gc_id":2872,"gc_name":"烘培类"}]
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
         * gc_id : 2848
         * gc_name : 米面杂粮
         */

        private int gc_id;
        private String gc_name;

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
    }
}
