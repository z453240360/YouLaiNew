package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model;



import android.net.Uri;

import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.constant.Constant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/8/3.
 * 地址请求接口类
 */

public interface IService {

    //登陆
    @FormUrlEncoded
    @POST(Constant.Login)
    Call<ResponseBody> login(@Field("mobile") String mobile, @Field("password") String password);

    //手机注册
    @FormUrlEncoded
    @POST(Constant.Register)
    Call<ResponseBody> regiest(@Field("mobile") String mobile);


    //首页Banner
    @GET(Constant.fitstBanner)
    Call<ResponseBody> getBanner(@Query("areaId")String s);



    //首页Banner
    @GET(Constant.car_buy)
    Call<ResponseBody> getCur();

    //首页--促销商品
    @GET(Constant.promo)
    Call<ResponseBody> getPromo(@Query("areaId")String s);

    //---------------------------------------------------------------------------
    //GET请求    咨询+公告 +常见问题
    //---------------------------------------------------------------------------


    //咨询--公告列表
    @GET(Constant.counselList)
    Call<ResponseBody> counselList(@Query("page") String page,
                                   @Query("classId") String classId);


    //---------------------------------------------------------------------------
    //GET请求    商品
    //------------------------------------------------------

    //商品--商品详情
    @GET(Constant.product_message)
    Call<ResponseBody> product_message(@Path("id") String id);

    //商品--商品分类
    @GET(Constant.product_kind)
    Call<ResponseBody> product_kind(@Query("pid") String pid);

    //商品--商品列表
    @GET(Constant.product_list)
    Call<ResponseBody> product_list(@Query("classOneId") String classOneId,
                                    @Query("classId") String classId,
                                    @Query("state") String state,
                                    @Query("orderBy") String orderBy,
                                    @Query("lng") String lng,
                                    @Query("lat") String lat,
                                    @Query("storeId") String storeId);

    @GET
    Call<ResponseBody> getNewsDetil(@Url String uri);


}
