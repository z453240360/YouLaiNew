package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/6/12.
 */

public class DingDanAllbean {


    /**
     * code : 200
     * data : {"current_page":1,"data":[{"format_order_state":"待付款","format_shop_state":"待审核","num":2,"order_amount":"10.00","order_goods":[{"goods_attr_text":"/件/15个","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200","goods_id":8397,"goods_name":"湘荣宝塔肉","goods_num":2,"goods_price":"0.00","order_id":500}],"order_id":500,"order_state":1,"shop_id":30,"shop_name":"浙江餐道网络科技有限公司"},{"format_order_state":"待付款","format_shop_state":"待审核","num":1,"order_amount":"10.00","order_goods":[{"goods_attr_text":"/斤/1斤","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/189fa6dca91633de01d6efce2e1d2f6d.jpg?imageView2/1/w/200/h/200","goods_id":7939,"goods_name":"四季豆干","goods_num":1,"goods_price":"0.00","order_id":499}],"order_id":499,"order_state":1,"shop_id":30,"shop_name":"浙江餐道网络科技有限公司"},{"format_order_state":"已取消","format_shop_state":"待审核","num":2,"order_amount":"10.00","order_goods":[{"goods_attr_text":"/个/1个","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200","goods_id":8397,"goods_name":"湘荣宝塔肉","goods_num":1,"goods_price":"0.00","order_id":498},{"goods_attr_text":"/件/15个","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200","goods_id":8397,"goods_name":"湘荣宝塔肉","goods_num":1,"goods_price":"0.00","order_id":498}],"order_id":498,"order_state":10,"shop_id":30,"shop_name":"浙江餐道网络科技有限公司"},{"format_order_state":"已取消","format_shop_state":"待审核","num":2,"order_amount":"10.00","order_goods":[{"goods_attr_text":"/件/15个","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200","goods_id":8397,"goods_name":"湘荣宝塔肉","goods_num":2,"goods_price":"0.00","order_id":497}],"order_id":497,"order_state":10,"shop_id":30,"shop_name":"浙江餐道网络科技有限公司"}],"total":4}
     * msg : 
     */

    public int code;
    public DataBeanX data;
    public String msg;

    

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"format_order_state":"待付款","format_shop_state":"待审核","num":2,"order_amount":"10.00","order_goods":[{"goods_attr_text":"/件/15个","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200","goods_id":8397,"goods_name":"湘荣宝塔肉","goods_num":2,"goods_price":"0.00","order_id":500}],"order_id":500,"order_state":1,"shop_id":30,"shop_name":"浙江餐道网络科技有限公司"},{"format_order_state":"待付款","format_shop_state":"待审核","num":1,"order_amount":"10.00","order_goods":[{"goods_attr_text":"/斤/1斤","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/189fa6dca91633de01d6efce2e1d2f6d.jpg?imageView2/1/w/200/h/200","goods_id":7939,"goods_name":"四季豆干","goods_num":1,"goods_price":"0.00","order_id":499}],"order_id":499,"order_state":1,"shop_id":30,"shop_name":"浙江餐道网络科技有限公司"},{"format_order_state":"已取消","format_shop_state":"待审核","num":2,"order_amount":"10.00","order_goods":[{"goods_attr_text":"/个/1个","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200","goods_id":8397,"goods_name":"湘荣宝塔肉","goods_num":1,"goods_price":"0.00","order_id":498},{"goods_attr_text":"/件/15个","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200","goods_id":8397,"goods_name":"湘荣宝塔肉","goods_num":1,"goods_price":"0.00","order_id":498}],"order_id":498,"order_state":10,"shop_id":30,"shop_name":"浙江餐道网络科技有限公司"},{"format_order_state":"已取消","format_shop_state":"待审核","num":2,"order_amount":"10.00","order_goods":[{"goods_attr_text":"/件/15个","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200","goods_id":8397,"goods_name":"湘荣宝塔肉","goods_num":2,"goods_price":"0.00","order_id":497}],"order_id":497,"order_state":10,"shop_id":30,"shop_name":"浙江餐道网络科技有限公司"}]
         * total : 4
         */

        public int current_page;
        public int total;
        public List<DataBean> data;

       

        public static class DataBean {
            /**
             * format_order_state : 待付款
             * format_shop_state : 待审核
             * num : 2
             * order_amount : 10.00
             * order_goods : [{"goods_attr_text":"/件/15个","goods_cover":"http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200","goods_id":8397,"goods_name":"湘荣宝塔肉","goods_num":2,"goods_price":"0.00","order_id":500}]
             * order_id : 500
             * order_state : 1
             * shop_id : 30
             * shop_name : 浙江餐道网络科技有限公司
             */

            public String format_order_state;
            public String format_shop_state;
            public int num;
            public String order_amount;
            public int order_id;
            public int order_state;
            public int shop_id;
            public String shop_name;
            public List<OrderGoodsBean> order_goods;
            public int is_payment;
           

            public static class OrderGoodsBean {
                /**
                 * goods_attr_text : /件/15个
                 * goods_cover : http://oqv8tlktu.bkt.clouddn.com/40fbab247fbde42289eef62aadfb1e02.jpg?imageView2/1/w/200/h/200
                 * goods_id : 8397
                 * goods_name : 湘荣宝塔肉
                 * goods_num : 2
                 * goods_price : 0.00
                 * order_id : 500
                 */

                public String goods_attr_text;
                public String goods_cover;
                public int goods_id;
                public String goods_name;
                public int goods_num;
                public double goods_price;
                public int order_id;

               
            }
        }
    }
}
