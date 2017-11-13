package com.bm.chengshiyoutian.youlaiwang.bean;

/**
 * Created by sld on 2017/6/5.
 */

public class ZhiFuBaoBean {

    /**
     * data : partner="2088221246503395"&seller_id="2088221246503395"&out_trade_no="1496631137"&subject="1"&body="地瓜粉"&total_fee="16"&notify_url="http://youlaiwang.excelsiorbrand.cn/pay/notify/alipay"&service="mobile.securitypay.pay"&payment_type="1"&_input_charset="utf-8"&it_b_pay="50m"&passback_params="{"orderSns":"426320173930605105217655"}"&sign="iqf2J5DcObLuSEctDKLH386%2FJtRzju55WUFte0kOpNqyelMoayA8nbeYStqcT3vjedkbP1%2BHQhdlZaxJsP6%2FH79VlFBgX9YUVrlbPQUmHVHg%2FmjoSryQrebumNheNVuKozNEm64M2sIy36v7tBTsVtUw9gbcuL%2Btpbl9wNK45ntnzB0bWx8DypW%2Fqvb0mMBJL%2BZ2w1BlSmBr1E6TuYNeXV1Hc2LPgghT1gF%2Fa8VjAyFjaoIzA5k3BniMYeIxEh50lYMaqANvJO7%2B4sYtBs2HE27B4e2pD6ZYXHFr0VmP2ZKV7wfVAcIVgUZk2MdlzNDo%2FYr%2B7upA%2BtXo0rxRnpUb6w%3D%3D"&sign_type="RSA"
     * msg : 支付申请成功
     * code : 200
     */

    private String data;
    private String msg;
    private int code;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
}
