package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/24.
 */

public class ShangPinZuoBianBean {


    /**
     * code : 200
     * data : [{"gc_id":2839,"gc_name":"T恤"},{"gc_id":2845,"gc_name":"打底裤"},{"gc_id":2835,"gc_name":"羽绒服"},{"gc_id":2836,"gc_name":"卫衣"},{"gc_id":2837,"gc_name":"风衣"},{"gc_id":2838,"gc_name":"外套"},{"gc_id":2844,"gc_name":"牛仔裤"},{"gc_id":2840,"gc_name":"衬衫"},{"gc_id":2841,"gc_name":"毛衣"},{"gc_id":2842,"gc_name":"礼服"},{"gc_id":2846,"gc_name":"其他"},{"gc_id":2848,"gc_name":"床上用品"},{"gc_id":2843,"gc_name":"旗袍"},{"gc_id":2850,"gc_name":"枕头"},{"gc_id":2851,"gc_name":"毛毯"},{"gc_id":2849,"gc_name":"保暖被芯"},{"gc_id":2872,"gc_name":"卫衣"},{"gc_id":2870,"gc_name":"羽绒服"},{"gc_id":2868,"gc_name":"手提包"},{"gc_id":2867,"gc_name":"钱包"},{"gc_id":2866,"gc_name":"双肩包"},{"gc_id":2871,"gc_name":"棉服"},{"gc_id":2888,"gc_name":"进口商品2"},{"gc_id":2887,"gc_name":"进口商品1"},{"gc_id":2886,"gc_name":"厅厨2"},{"gc_id":2885,"gc_name":"厅厨1"},{"gc_id":2884,"gc_name":"冷冻2"},{"gc_id":2883,"gc_name":"冷冻1"},{"gc_id":2878,"gc_name":"西装"},{"gc_id":2877,"gc_name":"风衣"},{"gc_id":2876,"gc_name":"衬衣"},{"gc_id":2875,"gc_name":"T恤"},{"gc_id":2874,"gc_name":"外套"},{"gc_id":2873,"gc_name":"毛衣"},{"gc_id":2865,"gc_name":"床褥"},{"gc_id":2864,"gc_name":"拖鞋"},{"gc_id":2863,"gc_name":"小白鞋"},{"gc_id":2820,"gc_name":"礼服"},{"gc_id":2821,"gc_name":"衬衫"},{"gc_id":2823,"gc_name":"羽绒服"},{"gc_id":2824,"gc_name":"风衣"},{"gc_id":2826,"gc_name":"卫衣"},{"gc_id":2828,"gc_name":"T恤"},{"gc_id":2829,"gc_name":"暖心毛衣"},{"gc_id":2830,"gc_name":"保暖内衣"},{"gc_id":2831,"gc_name":"西裤"},{"gc_id":2832,"gc_name":"羊毛衫"},{"gc_id":2833,"gc_name":"其他"},{"gc_id":2827,"gc_name":"外套"},{"gc_id":2853,"gc_name":"时尚女包"},{"gc_id":2855,"gc_name":"西装"},{"gc_id":2860,"gc_name":"单鞋"},{"gc_id":2861,"gc_name":"休闲鞋"},{"gc_id":2862,"gc_name":"运动鞋"},{"gc_id":2819,"gc_name":"西装"}]
     * msg :
     */

    private int code;
    private String msg;
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
        /**
         * gc_id : 2839
         * gc_name : T恤
         */
        public  boolean tag=false;
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
