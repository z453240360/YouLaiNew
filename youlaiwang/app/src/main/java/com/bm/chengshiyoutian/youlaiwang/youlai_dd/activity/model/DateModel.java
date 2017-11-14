package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model;


import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.MainActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.QiYeRenZhengActivity;
import com.bm.chengshiyoutian.youlaiwang.adapter.DingDan2Adapter1;
import com.bm.chengshiyoutian.youlaiwang.adapter.QiYeRCAdapter;
import com.bm.chengshiyoutian.youlaiwang.adapter.QiYeRCAdapter_ddNew;
import com.bm.chengshiyoutian.youlaiwang.bean.DingDan1Bean;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBeanNum;
import com.bm.chengshiyoutian.youlaiwang.bean.QiYeRenZhengBean;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPingXIangQIng1BuDaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.GouwuActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.EXpandAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.CarBean_new;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.R.id.list;
import static com.bm.chengshiyoutian.youlaiwang.R.id.isAllSelect;
import static com.bm.chengshiyoutian.youlaiwang.R.id.iv;
import static com.bm.chengshiyoutian.youlaiwang.R.id.iv_shoucang;
import static com.bm.chengshiyoutian.youlaiwang.R.id.lv;
import static com.bm.chengshiyoutian.youlaiwang.R.id.rc;
import static com.bm.chengshiyoutian.youlaiwang.R.id.rc_zhengJian;
import static com.bm.chengshiyoutian.youlaiwang.R.id.sign;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_count;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_money;
import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;

/**
 * Created by Administrator on 2017/8/3.
 * 网络请求信息管理类
 */

public class DateModel {

    //返回IService对象
    public IService getCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASRURL)
                .build();
        IService iService = retrofit.create(IService.class);
        return iService;
    }

    //登陆接口
    public void getLogin(final ICallBack callBack, String mobile, String password) {
        IService call = getCall();
        Call<ResponseBody> login = call.login(mobile, password);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                if (code != 200) {
                    callBack.netState("网络异常");
                    return;
                }
                String s = null;
                try {
                    s = response.body().string();
                    if (null == s || ("").equals(s)) {
                        callBack.netState("服务器异常");
                        return;
                    } else {
                        callBack.succesed(s);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed("请求网络失败");
            }
        });

    }

    //注册
    public void getRegister(String phone, final ICallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASRURL)
                .build();
        IService service = retrofit.create(IService.class);

        Call<ResponseBody> regiest = service.regiest(phone);
        regiest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callBack.succesed(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed("网络请求失败" + t.toString());
            }
        });


    }

    //注册2
    public void getBanner(final ICallBack callBack, String areaId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASRURL)
                .build();
        IService service = retrofit.create(IService.class);

        Call<ResponseBody> regiest = service.getBanner(areaId);
        regiest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callBack.succesed(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed("网络请求失败" + t.toString());
            }
        });


    }


    //购物车
    public void getCur_bur(final ICallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASRURL)
                .build();
        IService service = retrofit.create(IService.class);

        Call<ResponseBody> regiest = service.getCur();
        regiest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callBack.succesed(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed("网络请求失败" + t.toString());
            }
        });

    }

    //首页促销商品
    public void getPromo(String areaId, final ICallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASRURL)
                .build();
        IService service = retrofit.create(IService.class);

        Call<ResponseBody> regiest = service.getPromo(areaId);
        regiest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    callBack.succesed(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed("网络请求失败");
            }
        });

    }

    //---------------------------------------------------------------------------
    //GET请求    咨询+公告 +常见问题
    //---------------------------------------------------------------------------

    //咨询--公告列表
    public void getCounselMinute(String page, String classId, final ICallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASRURL)
                .build();
        IService service = retrofit.create(IService.class);

        Call<ResponseBody> regiest = service.counselList(page, classId);
        regiest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callBack.succesed(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed("网络请求失败");
            }
        });

    }

    //商品--商品详情
    public void product_message(String classId, final ICallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASRURL)
                .build();
        IService service = retrofit.create(IService.class);

        Call<ResponseBody> regiest = service.product_message(classId);
        regiest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callBack.succesed(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed("网络请求失败");
            }
        });

    }

    // 商品--商品分类
    public void product_kind(String pid, final ICallBack callBack) {
        IService iService = getCall();
        //IService对应的方法
        Call<ResponseBody> call = iService.product_kind(pid);
        //同步请求网络
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() != 200) {
                    callBack.netState("服务器异常");
                    return;
                }

                String responseBody = null;

                try {
                    responseBody = response.body().string();
                    if (null == responseBody) {
                        callBack.netState("数据为空");
                        return;
                    }
                    callBack.succesed(responseBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed("网络错误");
            }
        });

    }

    // 商品--商品列表
    public void product_list(String classOneId,
                             String classId,
                             String state,
                             String orderBy,
                             String lng,
                             String lat,
                             String storeId, final ICallBack callBack) {
        IService iService = getCall();
        //IService对应的方法
        Call<ResponseBody> call = iService.product_list(classOneId, classId, state, orderBy, lng, lat, storeId);
        //同步请求网络
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() != 200) {
                    callBack.netState("服务器异常");
                    return;
                }

                String responseBody = null;

                try {
                    responseBody = response.body().string();
                    if (null == responseBody) {
                        callBack.netState("数据为空");
                        return;
                    }
                    callBack.succesed(responseBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed(t.toString());
            }
        });

    }

    public void getNews(int id, final ICallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASRURL)
                .build();
        IService iService = retrofit.create(IService.class);
        Call<ResponseBody> call = iService.getNewsDetil(Constant.BASRURL + "api/article/" + id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() != 200) {
                    callBack.netState("服务器异常");
                    return;
                }

                String responseBody = null;

                try {
                    responseBody = response.body().string();
                    if (null == responseBody) {
                        callBack.netState("数据为空");
                        return;
                    }
                    callBack.succesed(responseBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failed("网络错误");
            }
        });

    }


    //访问网络，获取购物车数据
    public void getShoppingCarDate(String token, final ICallBack callBack) {

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart");
        stringRequest.addHeader("Authorization", token);

        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.get().toString() == null) {
                    callBack.failed("服务器异常");
                    return;
                }
                int responseCode = response.responseCode();
                if (responseCode != 200) {
                    ShowToast.showToast("服务器异常：" + responseCode);
                    return;
                }

                String s = response.get().toString();
                callBack.succesed(s);
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callBack.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //获取购物车数据
    public void getCarData(String token, final ICallBack callBack) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart");
        stringRequest.addHeader("Authorization", token);
        NoHttp.newRequestQueue().add(1, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response<String> response) {
                if (response.get().toString() == null) {
                    callBack.failed("服务器异常");
                    return;
                }
                int responseCode = response.responseCode();
                if (responseCode != 200) {
                    ShowToast.showToast("服务器异常：" + responseCode);
                    return;
                }

                String s = response.get().toString();
                callBack.succesed(s);

            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response<String> response) {
                callBack.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

    //移除购物车数据
    public void deleteCarData(String list, String token, final ICallBack callBack) {
        Request<String> stringRequest1 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/destroy", RequestMethod.POST);
        stringRequest1.addHeader("Authorization", token);
        stringRequest1.add("cart_id", list);
        stringRequest1.add("_method", "delete");
        CallServer.getInstance().add(13, stringRequest1, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.get().toString() == null) {
                    callBack.failed("服务器异常");
                    return;
                }
                int responseCode = response.responseCode();
                if (responseCode != 200) {
                    ShowToast.showToast("服务器异常：" + responseCode);
                    return;
                }

                String s = response.get().toString();
                callBack.succesed(s);
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callBack.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }


    //同步购物车
    public void addCar(final int num, String goodId, String specId, int type, final ICallBack callBack) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("goodsId", goodId);//商品编号
        stringRequest.add("specId", specId);//规格
        stringRequest.add("type", type);

        CallServer.getInstance().add(12, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                String result = response.get().toString();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        String msg = object.getString("msg");
                        callBack.succesed(msg);
                    } else {
                        callBack.failed("服务器异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.failed("服务器获取数据异常");
                }
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callBack.failed("网络联接失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //购物车  去支付
    public void goToPay(String token, String cardsID, final ICallBack callBack) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/confirm");
        stringRequest.addHeader("Authorization", token);
        stringRequest.add("cartIds", cardsID);
        NoHttp.newRequestQueue().add(1333, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                String result = response.get().toString();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        String msg = object.getString("msg");
                        callBack.succesed(msg);
                    } else {
                        callBack.failed("服务器异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.failed("服务器获取数据异常");
                }
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callBack.failed("网络联接失败");
            }

            @Override
            public void onFinish(int what) {
            }
        });


    }


    //获取一级分类的标签（蔬菜水果，肉禽蛋类......）
    public void getClassOneData(String token, final ICallBack callback) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/class");
        stringRequest.addHeader("Authorization", token);
        NoHttp.newRequestQueue().add(1, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.responseCode()!=200)
                {
                    callback.failed("服务器异常："+response.responseCode());
                    return;
                }
                callback.succesed(response.get().toString());
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callback.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    //二级综合商品分类列表（传值为”“ 时显示全部二级分类列表）
    public void getClassTwoData(String token,String pid,String area_id,final ICallBack callback) {
        final Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/class/two");
        stringRequest.add("pid", pid);
        stringRequest.add("areaId", area_id);
        NoHttp.newRequestQueue().add(1, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.responseCode()!=200)
                {
                    callback.failed("服务器异常："+response.responseCode());
                    return;
                }
                callback.succesed(response.get().toString());
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callback.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //获取详细的商品列表
    public void getYouBianData(String token,
                               int classId,
                               String classOneId,
                               String state,
                               String orderBy,
                               String storeId,
                               int page,
                               String lng,
                               String lat,
                               String areaId,
                               final ICallBack callback) {

        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
        request.addHeader("Authorization", token);
        if(!(""+classId).equals("")){
            request.add("classId", classId);//二级分类
        }
        if (!(""+page).equals("")){
            request.add("page", page);//页码
        }
        if (!(""+classOneId).equals("")){
            request.add("classOneId", classOneId);//一级分类
        }
        if (!(""+areaId).equals("")){
            request.add("areaId", areaId);//区域ID
        }

        if (!(""+state).equals("")){
            request.add("state", state);//排序类型
        }
        if (!(""+orderBy).equals("")){
            request.add("orderBy", orderBy);//倒序还是反序
        }
        if (!(""+lng).equals("")){
            request.add("lng", lng);//经度
        }
        if (!(""+lat).equals("")){
            request.add("lat", lat);//维度
        }
        if (!(""+storeId).equals("")){
            request.add("storeId", storeId);//商店ID
        }


        NoHttp.newRequestQueue().add(1, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response<String> response) {
                if (response.responseCode()!=200)
                {
                    callback.failed("服务器异常："+response.responseCode());
                    return;
                }
                callback.succesed(response.get().toString());
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response<String> response) {
                callback.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    //获取商品详情
    public void getGoodsDetial(String token, String goodsId, final ICallBack callBack){
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/" + goodsId);
        stringRequest.addHeader("Authorization", token);
        NoHttp.newRequestQueue().add(1, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.responseCode()!=200)
                {
                    callBack.failed("服务器异常："+response.responseCode());
                    return;
                }
                callBack.succesed(response.get().toString());
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callBack.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    //获取购物车数量
    public void getCarNum(String token, final ICallBack callBack) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/total");
        stringRequest.addHeader("Authorization", token);
        NoHttp.newRequestQueue().add(1, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.responseCode()!=200)
                {
                    callBack.failed("服务器异常："+response.responseCode());
                    return;
                }
                callBack.succesed(response.get().toString());
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callBack.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    //商品收藏
    public void shouCang(String token,String goodsID,final ICallBack callBack){
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/store", RequestMethod.POST);
        stringRequest.addHeader("Authorization", token);
        stringRequest.add("type", 1);
        stringRequest.add("foreign_id", goodsID);
        CallServer.getInstance().add(233, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {


            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.responseCode()!=200)
                {
                    callBack.failed("服务器异常："+response.responseCode());
                    return;
                }
                callBack.succesed(response.get().toString());

            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callBack.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {


            }
        });
    }


    //获取订单状态
    public void getOrder(String token,int page,int state,final ICallBack callBack) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/order");
        stringRequest.add("state", state);
        stringRequest.add("page", page);
        stringRequest.addHeader("Authorization", token);
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.responseCode()!=200)
                {
                    callBack.failed("服务器异常："+response.responseCode());
                    return;
                }
                callBack.succesed(response.get().toString());


            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callBack.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    //获取企业认证的信息
    public void getResMessage(String token,final  ICallBack callBack) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/company");
        stringRequest.addHeader("Authorization", token);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.responseCode()!=200)
                {
                    callBack.failed("服务器异常："+response.responseCode());
                    return;
                }
                callBack.succesed(response.get().toString());
            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                callBack.failed("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }








}

