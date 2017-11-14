package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model;


import android.view.View;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.GouWuCheAdapter1;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBean;
import com.google.gson.JsonSyntaxException;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.text.DecimalFormat;

import static android.R.attr.data;
import static com.alipay.sdk.cons.GlobalConstants.s;
import static com.bm.chengshiyoutian.youlaiwang.R.id.ll_kongGouWuChe;
import static com.bm.chengshiyoutian.youlaiwang.R.id.lv;
import static com.bm.chengshiyoutian.youlaiwang.Utils.MyRes.area_id;
import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;

/**
 * Created by Administrator on 2017/8/3.
 * 网络请求逻辑层
 */

public class Present {

    private DateModel dateModel;
    private IMainView iMainView;

    public Present(IMainView mIMainView) {
        dateModel = new DateModel();
        this.iMainView = mIMainView;
    }

    public void getLogin(String mobile,String password){
        iMainView.showLoading();
        dateModel.getLogin(new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getCode(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        }, mobile, password);
    }

    //手机注册
    public void getRegist(String mobile){
        iMainView.showLoading();
        dateModel.getRegister( mobile,new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getCode(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        });
    }

    //获取首页轮播
    public void getBanner(String areaId){
        iMainView.showLoading();
        dateModel.getBanner(new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getCode(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        },areaId);
    }

    //获取购物车列表
    public void getCar(){
        iMainView.showLoading();
        dateModel.getCur_bur(new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getCode(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        });
    }

    //首页促销商品
    public void getPromo(String areaId){
        iMainView.showLoading();
        dateModel.getPromo(areaId,new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getUpDate(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        });
    }

    //获取公告
    public void counselMinute(String page,String classId){
        iMainView.showLoading();
        dateModel.getCounselMinute(page,classId,new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getLogin(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        });
    }

    //商品--商品详情
    public void product_message(String id){
        iMainView.showLoading();
        dateModel.product_message(id,new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getUpDate(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        });
    }

    //商品--商品分类
    public void product_kind(String pid){
        iMainView.showLoading();
        dateModel.product_kind(pid,new ICallBack() {
            @Override
            public void succesed(String s) {
                if(null==s){
                    return;
                }
                iMainView.getUpDate(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                if(null==s){
                    return;
                }
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                if(null==s){
                    return;
                }
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        });
    }

    //商品--商品列表
    public void product_list(String classOneId,
                             String classId,
                             String state,
                             String orderBy,
                             String lng,
                             String lat,
                             String storeId){
        iMainView.showLoading();
        dateModel.product_list(classOneId,classId,state,orderBy,lng,lat,storeId,new ICallBack() {
            @Override
            public void succesed(String s) {
                if(null==s){
                    return;
                }
                iMainView.getUpDate(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                if(null==s){
                    return;
                }
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                if(null==s){
                    return;
                }
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        });
    }

    //获取新闻详情
    public void getNews(int id){
        iMainView.showLoading();
        dateModel.getNews(id,new ICallBack() {
            @Override
            public void succesed(String s) {
                if(null==s){
                    return;
                }
                iMainView.getUpDate(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                if(null==s){
                    return;
                }
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {
                if(null==s){
                    return;
                }
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }
        });
    }

    //获取购物车数据
    public void getCatData(String token){
        iMainView.showLoading();
        dateModel.getShoppingCarDate(token, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getLogin(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {

            }
        });
    }

    //获取购物车数据
    public void getCarData(String token){
        iMainView.showLoading();
        dateModel.getCarData(token, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getLogin(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {

            }
        });
    }

    //删除购物车数据
    public void deleteCarData(String token,String list){
        iMainView.showLoading();
        dateModel.deleteCarData(list,token, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getUpDate(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {

            }
        });

    }

    //购物车数据____去支付
    public void goToPay(String token,String cadtID){
        iMainView.showLoading();
        dateModel.goToPay(token,cadtID, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getCode(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {

            }
        });

    }

    //添加或者减少购物车数量
    public void changeCarData(final int num, String goodId, String specId, int type){
        iMainView.showLoading();
        dateModel.addCar(num, goodId, specId, type, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getUpDate(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.showFaliure(s);
                iMainView.cancelLoading();
            }

            @Override
            public void netState(String s) {

            }
        });

    }


    //获取一级分类列表
    public void getClassOneData(String token) {
        iMainView.showLoading();
        dateModel.getClassOneData(token, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getCode(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.cancelLoading();
                iMainView.showFaliure(s);
            }

            @Override
            public void netState(String s) {

            }
        });
    }

    //获取二级分类列表
    public void getClassTwoData(String token,String pid,String area_id) {
        iMainView.showLoading();
        dateModel.getClassTwoData(token,pid,area_id, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getLogin(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.cancelLoading();
                iMainView.showFaliure(s);
            }

            @Override
            public void netState(String s) {

            }

        });
    }

    //获取产品详情
    public void getYouBianData(String token, int classId, String classOneId, String state, String orderBy, String storeId, int page, String lng, String lat,String areaID) {
        iMainView.showLoading();
        dateModel.getYouBianData(token, classId, classOneId, state, orderBy, storeId, page, lng, lat,areaID, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getUpDate(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.cancelLoading();
                iMainView.showFaliure(s);
            }

            @Override
            public void netState(String s) {

            }
        });
    }


    //获取商品详情
    public void getGoodsDetial(String token, String goodsId) {

        iMainView.showLoading();
        dateModel.getGoodsDetial(token,goodsId, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getLogin(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.cancelLoading();
                iMainView.showFaliure(s);
            }

            @Override
            public void netState(String s) {

            }

        });
    }


    //获取购物车商品的数量
    public void getCarNumber(String token) {

        iMainView.showLoading();
        dateModel.getCarNum(token, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getUpDate(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.cancelLoading();
                iMainView.showFaliure(s);
            }

            @Override
            public void netState(String s) {

            }

        });
    }

    //获取购物车商品的数量
    public void shouCang(String token,String goodsID) {

        iMainView.showLoading();
        dateModel.shouCang(token,goodsID, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getCode(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.cancelLoading();
                iMainView.showFaliure(s);
            }

            @Override
            public void netState(String s) {

            }

        });
    }

    //获取订单状态
    public void getOrder(String token,int page,int state){
        iMainView.showLoading();
        dateModel.getOrder(token,page,state, new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getCode(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.cancelLoading();
                iMainView.showFaliure(s);
            }

            @Override
            public void netState(String s) {

            }

        });
    }


    //获取订单状态
    public void getResMessage(String token){
        iMainView.showLoading();
        dateModel.getResMessage(token,new ICallBack() {
            @Override
            public void succesed(String s) {
                iMainView.getCode(s);
                iMainView.cancelLoading();
            }

            @Override
            public void failed(String s) {
                iMainView.cancelLoading();
                iMainView.showFaliure(s);
            }

            @Override
            public void netState(String s) {

            }

        });
    }






}
