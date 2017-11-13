package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.constant;

/**
 * Created by Administrator on 2017/8/17.
 */

public interface Constant {

    //---------------------------------------------------------------------------
    //POST请求    登陆、注册、找回密码
    //---------------------------------------------------------------------------

    //由来往基本地址
//    String BASRURL = "http://app.youlainet.com/";

    String BASRURL = "http://you.hanchengvip.com/";//测试环境

    //登陆
    String Login = "api/user/login";

    //注册 - 发送验证码
    String Register = "api/sms/register";

    //注册 - 必须有推荐人手机号
    String RegisterByPhone = "api/user/register";

    //找回密码
    String findPassword = "api/api/sms/findPwd";

    //找回密码- 发送验证码
    String findPasswordByPhone = "api/sms/findPwd";

    //---------------------------------------------------------------------------
    //GET请求    首页
    //---------------------------------------------------------------------------

    //区域选择
    String hotArea = "api/area/hot";

    //首页轮播
    String fitstBanner = "api/index";

    //首页--促销商品
    String promo = "api/index/promo";

    //首页--根据经纬度获取城市信息
    String getAreaId = "api/getAreaId";

    //首页--获取城市信息
    String cityArea = "api/area/cityArea";

    //首页--获取版本信息
    String version = "api/version";


    //---------------------------------------------------------------------------
    //GET请求    供应商
    //---------------------------------------------------------------------------
    // 供应商列表
    String store = "api/store";

    // 供应商首页
    String storeindex= "api/store/7/index";

    // 供应商详情
    String gongyingshangXiangqing= "api/store/7";

    //供应商-商品分类
    String kingProduct="api/store/7/class";

    //区域选择 api/area
    String area="api/store/7/class";

    //供应商-促销商品
    String promotionProduct="api/store/7/promo";


    //供应商-商品列表
    String produceList="store/7/class/2824";

    //收藏商品 和收藏店铺
    String collectShop="store/7/class/2824";      //post

    //供应商 - 购买记录
    String buyRecord="api/store/{id}/buyList";



    //历史搜索记录
    String searchRcoord = "api/search/history";

    //搜索结果页面
    String searchResult="api/goods/search";

    //供应商 - 商品二级分类
    String supportKind="api/store/{storeId}/classTwo";



    //---------------------------------------------------------------------------
    //GET请求    咨询+公告 +常见问题
    //---------------------------------------------------------------------------


    //咨询+公告列表
    String  counselList="api/article";

    //咨询+公告详情
    String  counselMinute="api/article/1";

    //常见问题列表
    String question = "api/article/question";

    //---------------------------------------------------------------------------
    //GET请求    我的个人中心
    //---------------------------------------------------------------------------

    //我的 - 首页
    String myFirstPage = "api/user/index";

    //收藏 - 商品列表
    String myProductList="api/favorites/goods";

    //收藏 - 店铺列表
    String myShopList="api/favorites/shop";

    //收藏 - 删除
    String myDelete="api/favorites/destroy";      //post

    //购买商品
    String myBuyProduct="api/buyRecord/goods";

    //购买店铺
    String myBuyShop="api/buyRecord/shop";

    //投诉举报 - 列表
    String report="api/report";


    // 投诉举报—— 提交
    String supportReport ="api/report/store";      //post

    // 系统提示
    String remindFromIntent="api/message";

    //订单列表
    String orderList="api/order";

    //订单详情
    String order_minte = "api/order/{id}";

    //付款 - 选择支付方式页面
    String payPage = "api/pay/mode";

    //评价
    String review="api/order/comments";      //post


    //订单—— 取消
    String cancel_order="api/order/cancel";      //post

    //订单- 确认收货
    String conformOrder="api/order/receipt";      //post


    //购买过的商品— 添加购物车、
    String buyedProduct="api/cart/goods/buyRecord";      //post



    //---------------------------------------------------------------------------
    //GET请求    我的设置
    //---------------------------------------------------------------------------

    //昵称修改
    String changeName="api/user/update";

    //收货地址-列表
    String acceptAddress="api/user/address";

    // 收货地址-新增
    String addAddress="api/user/address/store";      //post

    //收货地址- 删除
    String deleteAddress="api/user/address/destroy";      //post

    //收货地址-设为默认
    String sureAddress="api/user/address/default";      //post



    //收货地址-更新
    String update="api/user/address/update";      //post

    //收货地址-详情
    String addressminte="api/user/address/{id}";

    //企业认证 - 获取信息
    String getCompeneyMessage="api/user/company";

    //企业认证 - 更新
    String updateCompeneyMessage="api/user/company/update";      //post

    // 密码修改
    String changePassword="api/user/update/password";      //post

    //头像更新
    String update_picture="api/user/update/avatar";      //post

    //---------------------------------------------------------------------------
    //GET请求    商品
    //---------------------------------------------------------------------------

    //商品详情
    String product_message="api/goods/{id}";

    //商品评价列表
    String productMessage_list="api/goods/{id}/comment";

    //商品列表
    String product_list="api/goods";


    //商品分类
    String product_kind="api/class/two";

    //商品详情 - 详情内容+ 图片
    String product_detail="api/goods/{id}/detail";


    //---------------------------------------------------------------------------
    //GET请求    购物车
    //---------------------------------------------------------------------------

    //购物车列表
    String car_buy="api/cart";

    //购物车 - 删除
    String car_delete="api/cart/destroy";      //post

    //购物车添加
    String car_add="api/cart/store";      //post

    //去结算 - 确认订单页
    String car_finish="api/pay/confirm";

    //提交订单
    String order_support="api/pay/submit";      //post

    //发起支付
    String order_pay="api/pay/launchPay";

    //购物车数量
    String car_total="api/cart/total";


}
