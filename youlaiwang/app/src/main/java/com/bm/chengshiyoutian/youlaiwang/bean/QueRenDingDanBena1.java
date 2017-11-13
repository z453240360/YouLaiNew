package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/6/1.
 */

public class QueRenDingDanBena1 {


    /**
     * code : 200
     * data : {"address":{"address_id":114,"areainfo":"浙江省杭州市市辖区电话","mobile":"15000505884","name":"都好都"},"goodsData":[{"goods":[{"cart_id":1433,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/45d310cec3bb176a2ac20a7f80db1606.jpg?imageView2/1/w/200/h/200","goods_name":"地瓜粉","goods_num":1,"grade_price_eight":"0.00","grade_price_five":"4.00","grade_price_four":"4.00","grade_price_nine":"0.00","grade_price_one":"5.50","grade_price_seven":"0.00","grade_price_six":"4.00","grade_price_ten":"0.00","grade_price_three":"3.50","grade_price_two":"3.50","price":"5.50","spec_text":"/斤/1斤","store_id":30}],"logistics_cost":10,"min_pay_money":500,"store_id":30,"store_name":"浙江餐道网络科技有限公司","store_num":1,"store_price":15.5}],"money":15.5}
     * msg :
     */

    private int code;
    /**
     * address : {"address_id":114,"areainfo":"浙江省杭州市市辖区电话","mobile":"15000505884","name":"都好都"}
     * goodsData : [{"goods":[{"cart_id":1433,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/45d310cec3bb176a2ac20a7f80db1606.jpg?imageView2/1/w/200/h/200","goods_name":"地瓜粉","goods_num":1,"grade_price_eight":"0.00","grade_price_five":"4.00","grade_price_four":"4.00","grade_price_nine":"0.00","grade_price_one":"5.50","grade_price_seven":"0.00","grade_price_six":"4.00","grade_price_ten":"0.00","grade_price_three":"3.50","grade_price_two":"3.50","price":"5.50","spec_text":"/斤/1斤","store_id":30}],"logistics_cost":10,"min_pay_money":500,"store_id":30,"store_name":"浙江餐道网络科技有限公司","store_num":1,"store_price":15.5}]
     * money : 15.5
     */

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
         * address_id : 114
         * areainfo : 浙江省杭州市市辖区电话
         * mobile : 15000505884
         * name : 都好都
         */

        private AddressBean address;
        private double money;
        /**
         * goods : [{"cart_id":1433,"goods_cover":"http://oqv8tlktu.bkt.clouddn.com/45d310cec3bb176a2ac20a7f80db1606.jpg?imageView2/1/w/200/h/200","goods_name":"地瓜粉","goods_num":1,"grade_price_eight":"0.00","grade_price_five":"4.00","grade_price_four":"4.00","grade_price_nine":"0.00","grade_price_one":"5.50","grade_price_seven":"0.00","grade_price_six":"4.00","grade_price_ten":"0.00","grade_price_three":"3.50","grade_price_two":"3.50","price":"5.50","spec_text":"/斤/1斤","store_id":30}]
         * logistics_cost : 10
         * min_pay_money : 500
         * store_id : 30
         * store_name : 浙江餐道网络科技有限公司
         * store_num : 1
         * store_price : 15.5
         */

        private List<GoodsDataBean> goodsData;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public List<GoodsDataBean> getGoodsData() {
            return goodsData;
        }

        public void setGoodsData(List<GoodsDataBean> goodsData) {
            this.goodsData = goodsData;
        }

        public static class AddressBean {
            private int address_id;
            private String areainfo;
            private String mobile;
            private String name;

            public int getAddress_id() {
                return address_id;
            }

            public void setAddress_id(int address_id) {
                this.address_id = address_id;
            }

            public String getAreainfo() {
                return areainfo;
            }

            public void setAreainfo(String areainfo) {
                this.areainfo = areainfo;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class GoodsDataBean {
            private int logistics_cost;
            private double min_pay_money;
            private int store_id;
            private String store_name;
            private int store_num;
            private double store_price;
            /**
             * cart_id : 1433
             * goods_cover : http://oqv8tlktu.bkt.clouddn.com/45d310cec3bb176a2ac20a7f80db1606.jpg?imageView2/1/w/200/h/200
             * goods_name : 地瓜粉
             * goods_num : 1
             * grade_price_eight : 0.00
             * grade_price_five : 4.00
             * grade_price_four : 4.00
             * grade_price_nine : 0.00
             * grade_price_one : 5.50
             * grade_price_seven : 0.00
             * grade_price_six : 4.00
             * grade_price_ten : 0.00
             * grade_price_three : 3.50
             * grade_price_two : 3.50
             * price : 5.50
             * spec_text : /斤/1斤
             * store_id : 30
             */

            private List<GoodsBean> goods;

            public int getLogistics_cost() {
                return logistics_cost;
            }

            public void setLogistics_cost(int logistics_cost) {
                this.logistics_cost = logistics_cost;
            }

            public double getMin_pay_money() {
                return min_pay_money;
            }

            public void setMin_pay_money(double min_pay_money) {
                this.min_pay_money = min_pay_money;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public int getStore_num() {
                return store_num;
            }

            public void setStore_num(int store_num) {
                this.store_num = store_num;
            }

            public double getStore_price() {
                return store_price;
            }

            public void setStore_price(int store_price) {
                this.store_price = store_price;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                private int cart_id;
                private int store_id;
                private String goods_name;
                private String goods_cover;
                private int goods_state;
                private int goods_num;
                private String spec_text;
                private String grade_price_eight;
                private String grade_price_five;
                private String grade_price_four;
                private String grade_price_nine;
                private String grade_price_one;
                private String grade_price_seven;
                private String grade_price_six;
                private String grade_price_ten;
                private String grade_price_three;
                private String grade_price_two;
                private String price;


                public int getGoods_state() {
                    return goods_state;
                }

                public void setGoods_state(int goods_state) {
                    this.goods_state = goods_state;
                }

                public int getCart_id() {
                    return cart_id;
                }

                public void setCart_id(int cart_id) {
                    this.cart_id = cart_id;
                }

                public String getGoods_cover() {
                    return goods_cover;
                }

                public void setGoods_cover(String goods_cover) {
                    this.goods_cover = goods_cover;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public int getGoods_num() {
                    return goods_num;
                }

                public void setGoods_num(int goods_num) {
                    this.goods_num = goods_num;
                }

                public String getGrade_price_eight() {
                    return grade_price_eight;
                }

                public void setGrade_price_eight(String grade_price_eight) {
                    this.grade_price_eight = grade_price_eight;
                }

                public String getGrade_price_five() {
                    return grade_price_five;
                }

                public void setGrade_price_five(String grade_price_five) {
                    this.grade_price_five = grade_price_five;
                }

                public String getGrade_price_four() {
                    return grade_price_four;
                }

                public void setGrade_price_four(String grade_price_four) {
                    this.grade_price_four = grade_price_four;
                }

                public String getGrade_price_nine() {
                    return grade_price_nine;
                }

                public void setGrade_price_nine(String grade_price_nine) {
                    this.grade_price_nine = grade_price_nine;
                }

                public String getGrade_price_one() {
                    return grade_price_one;
                }

                public void setGrade_price_one(String grade_price_one) {
                    this.grade_price_one = grade_price_one;
                }

                public String getGrade_price_seven() {
                    return grade_price_seven;
                }

                public void setGrade_price_seven(String grade_price_seven) {
                    this.grade_price_seven = grade_price_seven;
                }

                public String getGrade_price_six() {
                    return grade_price_six;
                }

                public void setGrade_price_six(String grade_price_six) {
                    this.grade_price_six = grade_price_six;
                }

                public String getGrade_price_ten() {
                    return grade_price_ten;
                }

                public void setGrade_price_ten(String grade_price_ten) {
                    this.grade_price_ten = grade_price_ten;
                }

                public String getGrade_price_three() {
                    return grade_price_three;
                }

                public void setGrade_price_three(String grade_price_three) {
                    this.grade_price_three = grade_price_three;
                }

                public String getGrade_price_two() {
                    return grade_price_two;
                }

                public void setGrade_price_two(String grade_price_two) {
                    this.grade_price_two = grade_price_two;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getSpec_text() {
                    return spec_text;
                }

                public void setSpec_text(String spec_text) {
                    this.spec_text = spec_text;
                }

                public int getStore_id() {
                    return store_id;
                }

                public void setStore_id(int store_id) {
                    this.store_id = store_id;
                }
            }
        }
    }
}
