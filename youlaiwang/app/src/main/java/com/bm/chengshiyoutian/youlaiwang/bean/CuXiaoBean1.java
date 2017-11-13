package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/23.
 */

public class CuXiaoBean1 {


    /**
     * code : 200
     * data : {"current_page":1,"data":[{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/189fa6dca91633de01d6efce2e1d2f6d.jpg?imageView2/1/w/200/h/200","goods_id":7939,"goods_name":"四季豆干","goods_price":"32.00","goods_spec_remark":"","ratio":"/斤/1斤"},{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/6874c7ef27c14daad185538738de7548.jpg?imageView2/1/w/200/h/200","goods_id":8379,"goods_name":"竹筒鸡24","goods_price":"33.00","goods_spec_remark":"","ratio":"/个/1个"},{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/03efe5cfea45d3eed7bf445035812f75.jpg?imageView2/1/w/200/h/200","goods_id":8343,"goods_name":"50G安琪泡打粉","goods_price":"1.50","goods_spec_remark":"50g","ratio":"/包/50g"}],"total":3}
     * msg :
     */

    public int code;
    public DataBeanX data;
    public String msg;

   

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/189fa6dca91633de01d6efce2e1d2f6d.jpg?imageView2/1/w/200/h/200",
         * "goods_id":7939,
         * "goods_name":"四季豆干",
         * "goods_price":"32.00","goods_spec_remark":"",
         * "ratio":"/斤/1斤"},
         * {"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/6874c7ef27c14daad185538738de7548.jpg?imageView2/1/w/200/h/200",
         * "goods_id":8379,
         * "goods_name":"竹筒鸡24",
         * "goods_price":"33.00",
         * "goods_spec_remark":"",
         * "ratio":"/个/1个"},
         * {"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/03efe5cfea45d3eed7bf445035812f75.jpg?imageView2/1/w/200/h/200","goods_id":8343,"goods_name":"50G安琪泡打粉","goods_price":"1.50","goods_spec_remark":"50g","ratio":"/包/50g"}]
         * total : 3
         */

        public int current_page;
        public int total;
        public List<DataBean> data;

       

        public static class DataBean {
            /**
             * goods_cover : http://oqv8tlktu.bkt.clouddn.com/189fa6dca91633de01d6efce2e1d2f6d.jpg?imageView2/1/w/200/h/200
             * goods_id : 7939
             * goods_name : 四季豆干
             * goods_price : 32.00
             * goods_spec_remark :
             * ratio : /斤/1斤
             */

            public String goods_cover;
            public int goods_id;
            public String goods_name;
            public String goods_price;
            public String goods_spec_remark;
            public String ratio;

           
        }
    }
}
