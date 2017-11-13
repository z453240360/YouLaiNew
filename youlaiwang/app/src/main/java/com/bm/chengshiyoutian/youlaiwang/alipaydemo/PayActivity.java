package com.bm.chengshiyoutian.youlaiwang.alipaydemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bm.chengshiyoutian.youlaiwang.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PayActivity extends AppCompatActivity {
    //商户PID
    public static String PARTNER = "2088702317991090";
    //商户收款账号
    public static String SELLER = "15238638194";
    //商户私钥，pkcs8格式
    public static String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMBsZ+dYz2cUL2x8" +
            "fiw0AjXOeFghPbnxTzGCIFRsvvylF/Uz/yDgogwCutyxmRXlgbYgnNzjKlrAkjQs"+
            "poP5qKIlEjW16BLln7tatYJnXfpD9zUy1Ntb4emR89Hl0ZwKbRRe7xJ/D75Bn8yX"+
            "vkk7mljD31l2iTWnVA3HJdgLx5IvAgMBAAECgYAPpAAiFt7s4wAj0k6zKxsO4pR3"+
            "nWQWDB5CyO/1zM0NXiH/ZVWUh43EaihkLe9ij0Am3KnDbpePvt1jbkmuqolhWIs5"+
            "g2wuXfX0QAwATlta+EYd7MdMK7s1bbBwrjD46v+cF2EvKme0l5PiQ4ipdiw2ZjSB"+
            "qSaxs0ZL9aWNHbwewQJBAN5zbZYu79R0WDQ1ssmFJmAvCUHssL2YSvc33tpoEDym"+
            "mjA0cKDhUrEhJKbDzNdXmNO3RIgmAYHrFkJLwhDACGECQQDdcadFFAXjvXh82IHk"+
            "H2hnZ/Or1pR8lNI4/g7jKDs2qJTazuLcXIHNHrGUqhYupQwKWinmiYMnSM4xTd3i"+
            "XWSPAkBTML2x5ZnBTUVTOSXEoWvpmo2OvvKV5OdcLuPDsBkqZW/SSTo5oCppnah0"+
            "SVNI0TSRML+LXxM/P+iSIHzSAM7hAkBtjRp5XCyFvGhtKxcnajpWXeD4tBGFiIJv"+
            "25A15ONWaRQrI4qsBb/ZYEPuDi8nKFPKk0yDVqhhuOz5C1Kn+BgVAkEAwDVnMX1h"+
            "LU94O6sztwzPmwyFwCdIJ9FcnZwYvSd0FR3xFBp4j31huM8UWmICAJbDGz4ERdjk"+
            "cY3sPSDEEu2oiw==";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, "支付成功",Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(PayActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo() {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";
        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + "2012122316088112" + "\"";
        // 商品名称
        orderInfo += "&subject=" + "\"" + "超哥店铺" + "\"";
        // 商品详情
        orderInfo += "&body=" + "\"" + "质量上乘" + "\"";
        // 商品金额
        orderInfo += "&total_fee=" + "\"" + "1000" + "\"";
        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://dl.bufan.hk/index.php/Portal/Alipay/index" + "\"";
        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";
        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";
        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";
        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";
        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";
        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        return orderInfo;
    }
    //去支付
    public void alipay(View v) {
        // 订单
        String orderInfo = getOrderInfo();
        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     */
    public void check(View v) {
        Runnable checkRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(PayActivity.this);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();
                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };
        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }



    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
