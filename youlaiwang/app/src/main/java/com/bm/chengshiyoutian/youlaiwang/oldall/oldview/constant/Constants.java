package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youj on 2016/1/8.
 */
public class Constants {
    /**
     * 是否第一次打开应用
     */
    public static final String FIRST_OPEN = "first_open";
    /**
     * 超时时间
     */
    public static final int TIMEOUT = 5000;

    /**
     * 超时时间
     */
    public static final int TIMEOUTLONG = 30000;

    /**
     * 公司服务器ip
     */
    //public static final String BASE_URL = "http://103.21.118.204:8088";
//    public static final String BASE_URL = "http://112.64.173.178:20082";
//    public static final String BASE_URL = "http://172.51.97.89:8088";
//    public static final String BASE_URL = "http://172.51.97.89:8088";
    /**
     * 公司服务器ip
     */
    //public static final String BASE_URL = "http://10.58.174.192:84";


    //new
    public static final String BASE_URL = "http://govadmin.youlainet.com";

    /**
     * 客户服务器ip
     */
    //public static final String BASE_URL = "http://123.56.74.162:88";


    /**
     * 餐厅
     */
    public static final String RESTAURANT_URL = BASE_URL + "/Interface/RestaurantService.asmx";


    /**
     * 索证索票
     */
    public static final String SUOZHEN_SUOPIAP = BASE_URL + "/Interface/RestaurantService.asmx";
    /**
     * 公告
     */
    public static final String ADSERVER = BASE_URL + "/Interface/AdServer.asmx";

    /**
     * 设置
     */
    public static final String SETTING_URL = BASE_URL + "/Interface/Expand.asmx";

    /**
     * 轮播图
     */
    public static final String BANNERSERVER_URL = BASE_URL + "/Interface/BannerServer.asmx";
    /**
     * 轮播图
     */
    public static final String FEEDBACK = BASE_URL + "/Interface/feedback.asmx";

    /**
     * 消息
     */
    public static final String MESSAGE = BASE_URL + "/Interface/Notice.asmx";

    /**
     * 是否记住密码
     */
    public static final String REMEMBER_PSW = "remember_psw";
    /**
     * 记住的密码
     */
    public static final String PASSWORD = "psw";
    /**
     * 记住的手机号
     */
    public static final String PHONENUM = "phoneNum";
    /**
     * 判断是否登录
     */
    public static final String IS_LOGIN = "is_login";
    public static final String HELP = "help";
    /**
     * 条款
     */
    public static final String TERMS = "terms";
    /**
     * 使用须知
     */
    public static final String NOTICE_FOR_USE = "notice_for_use";
    /**
     * 注册协议
     */
    public static final String REGISTRATION_PROTOCOL = "registration_protocol";

    /**
     * 推送的信息内容
     */
    public static final String NOTICE_MESSAGE = "notice_message";

    public static final String AD = "ad";

    /**
     *
     */
    public static final String PAGE_SIZE = "15";


    /**
     * 可以根据关键字获取数据 参考关键字为'条款','帮助','须知','协议'
     */
    public static String TYPE = "type";

    //0是新增供应商，1是编辑供应商
    public static String SUPPLIERTYPE="0";

    //0是注册或编辑的营业执照，1是企业门头照，2是供应商营业执照
    public static String BIMPTYPE="0";
    public static String AREAID="56456452412";
    public static String AREAID2="5646546541564856";


    public static class AccountDetaiTools{//台账明细
             //票据集合
             public static List<Bitmap> piaojuList = new ArrayList<Bitmap>();

             //1是生产，2是流通，3是餐饮
             public static int  typeid = 0;

             //供应商ID
             public static int supplierid = 0;

             //是否为流通的出货台账 1是,2不是。
             public static int isCirculatesShipment = 0;


        }

    public static class RepositoryShipTools{//流通的出货台账数据
        //采购商名称
        public static String procurementName = "";

        //联系人
        public static String procurementPersion = "";

        //联系电话
        public static String procurementPhone = "";
    }
}
