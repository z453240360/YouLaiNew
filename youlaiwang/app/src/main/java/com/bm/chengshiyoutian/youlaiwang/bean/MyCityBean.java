package com.bm.chengshiyoutian.youlaiwang.bean;

import java.util.List;

/**
 * Created by sld on 2017/5/23.
 */

public class MyCityBean {


    /**
     * k : 110000
     * n : [{"k":"110100","n":[{"k":"110101","v":"东城区"},{"k":"110102","v":"西城区"},{"k":"110103","v":"崇文区"},{"k":"110104","v":"宣武区"},{"k":"110105","v":"朝阳区"},{"k":"110106","v":"丰台区"},{"k":"110107","v":"石景山区"},{"k":"110108","v":"海淀区"},{"k":"110109","v":"门头沟区"},{"k":"110111","v":"房山区"},{"k":"110112","v":"通州区"},{"k":"110113","v":"顺义区"},{"k":"110114","v":"昌平区"},{"k":"110115","v":"大兴区"},{"k":"110116","v":"怀柔区"},{"k":"110117","v":"平谷区"},{"k":"110228","v":"密云县"},{"k":"110229","v":"延庆县"}],"v":"北京市"}]
     * v : 北京市
     */

    private String k;
    private String v;
    private List<NBeanX> n;

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public List<NBeanX> getN() {
        return n;
    }

    public void setN(List<NBeanX> n) {
        this.n = n;
    }

    public static class NBeanX {
        /**
         * k : 110100
         * n : [{"k":"110101","v":"东城区"},{"k":"110102","v":"西城区"},{"k":"110103","v":"崇文区"},{"k":"110104","v":"宣武区"},{"k":"110105","v":"朝阳区"},{"k":"110106","v":"丰台区"},{"k":"110107","v":"石景山区"},{"k":"110108","v":"海淀区"},{"k":"110109","v":"门头沟区"},{"k":"110111","v":"房山区"},{"k":"110112","v":"通州区"},{"k":"110113","v":"顺义区"},{"k":"110114","v":"昌平区"},{"k":"110115","v":"大兴区"},{"k":"110116","v":"怀柔区"},{"k":"110117","v":"平谷区"},{"k":"110228","v":"密云县"},{"k":"110229","v":"延庆县"}]
         * v : 北京市
         */

        private String k;
        private String v;
        private List<NBean> n;

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public List<NBean> getN() {
            return n;
        }

        public void setN(List<NBean> n) {
            this.n = n;
        }

        public static class NBean {
            /**
             * k : 110101
             * v : 东城区
             */

            private String k;
            private String v;

            public String getK() {
                return k;
            }

            public void setK(String k) {
                this.k = k;
            }

            public String getV() {
                return v;
            }

            public void setV(String v) {
                this.v = v;
            }
        }
    }
}
