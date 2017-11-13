package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/6/6.
 */

public class XiangQingShangPinBean {


    /**
     * code : 200
     * data : {"goods_content":"精制级「美玫牌」糕點粉採用特級進口小麥碾制而成，為精制粉心粉。具低灰份、顏色自然白皙、麥香味特濃、吸水力強等顯著優點。此外，「美玫牌」糕點粉非常適合制作港式饅頭、高檔中式包點、月餅、蛋糕等，制成品組織細膩、鬆軟可口、體積大、富彈性，而且天然健康，品質卓著，為中國國家免檢產品。人民大會堂、釣魚台國賓館、中國航天局訓練基地、星級酒店均選用「美玫牌」頂級糕點粉。 ","goods_detail_images":[]}
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
         * goods_content : 精制级「美玫牌」糕點粉採用特級進口小麥碾制而成，為精制粉心粉。具低灰份、顏色自然白皙、麥香味特濃、吸水力強等顯著優點。此外，「美玫牌」糕點粉非常適合制作港式饅頭、高檔中式包點、月餅、蛋糕等，制成品組織細膩、鬆軟可口、體積大、富彈性，而且天然健康，品質卓著，為中國國家免檢產品。人民大會堂、釣魚台國賓館、中國航天局訓練基地、星級酒店均選用「美玫牌」頂級糕點粉。
         * goods_detail_images : []
         */

        private String goods_content;
        private List<?> goods_detail_images;

        public String getGoods_content() {
            return goods_content;
        }

        public void setGoods_content(String goods_content) {
            this.goods_content = goods_content;
        }

        public List<?> getGoods_detail_images() {
            return goods_detail_images;
        }

        public void setGoods_detail_images(List<?> goods_detail_images) {
            this.goods_detail_images = goods_detail_images;
        }
    }
}
