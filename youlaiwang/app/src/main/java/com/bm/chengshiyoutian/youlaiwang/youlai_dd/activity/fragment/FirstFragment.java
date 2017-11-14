package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.FashionActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.KuaiSuXiaDanActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.QuYuActivityNew;
import com.bm.chengshiyoutian.youlaiwang.activity.SearchActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.XiTongTiXingActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.DingWeiBean;
import com.bm.chengshiyoutian.youlaiwang.bean.RenZhenBean;
import com.bm.chengshiyoutian.youlaiwang.fragment.ShouYeFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.NativeActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.BuyRecodeActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.HistoryActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.GouwuActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.HistoryActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.LastWeekActivity_dd;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.MyLastActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.MyOrderActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.TotalActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.dd_MainActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.ZhuYeAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.ZhuYe_CuXiaoAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.ZhuYe_DongTaiAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ShouYe_BannerBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ZhuYe_CuXiaoBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ZuiXinDongTaiBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.PicLoader;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bm.chengshiyoutian.youlaiwang.R.id.city_name;
import static com.bm.chengshiyoutian.youlaiwang.R.id.paixu;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv1;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv2;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv3;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv4;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_area;
import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;

/**
 * Created by Administrator on 2017/9/18.
 */

public class FirstFragment extends Fragment implements IMainView, AMapLocationListener {
    @Bind(R.id.banner_total)
    Banner bannerTotal;
    @Bind(R.id.mBtn_castomlist)
    Button mBtnCastomlist;
    @Bind(R.id.mBtn_buyRecordlist)
    Button mBtnBuyRecordlist;
    @Bind(R.id.mBtn_coupon)
    Button mBtnCoupon;
    @Bind(R.id.mBtn_buyByPhone)
    Button mBtnBuyByPhone;
    @Bind(R.id.mBtn_plushuiyuan)
    Button mBtnPlushuiyuan;
    @Bind(R.id.ll_total)
    LinearLayout llTotal;
    @Bind(R.id.img_zhengfugonggao)
    ImageView imgZhengfugonggao;
    @Bind(R.id.rl_zhengfu)
    RelativeLayout rlZhengfu;
    @Bind(R.id.img_tuijianyoujiang)
    ImageView imgTuijianyoujiang;
    @Bind(R.id.meiweixinpin)
    ImageView meiweixinpin;
    @Bind(R.id.ll_imgs)
    LinearLayout llImgs;
    @Bind(R.id.xinpin)
    TextView xinpin;
    @Bind(R.id.banner_total2)
    RecyclerView bannerTotal2;
    @Bind(R.id.presell)
    TextView presell;
    @Bind(R.id.mRecyclerView_presell)
    RecyclerView mRecyclerViewPresell;
    @Bind(R.id.mBtn_gengduo1)
    Button mBtnGengduo1;
    @Bind(R.id.rl11)
    RelativeLayout rl11;
    @Bind(R.id.mTxt_govement)
    TextView mTxtGovement;

    @Bind(R.id.ll_quyu)
    LinearLayout llQuyu;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.iv_dian)
    TextView ivDian;
    @Bind(R.id.rexiao)
    ImageView rexiao;
    @Bind(R.id.mBtn_gengduo2)
    Button mBtnGengduo2;
    @Bind(R.id.rl2)
    RelativeLayout rl2;
    @Bind(R.id.iv_emessage)
    ImageView ivEmessage;
    @Bind(R.id.ll_yanse)
    LinearLayout llYanse;
    @Bind(R.id.rl1)
    RelativeLayout rl1;
    @Bind(R.id.city_name)
    TextView cityName;

    private String area_name;
    private String TAG = "dd";

    private GridLayoutManager manager;
    private GridLayoutManager gridLayoutManager;

    private ZhuYeAdapter adapters;
    private ZhuYe_CuXiaoAdapter cuXiaoAdapter;

    private Present present;
    private ShouYe_BannerBean bean;
    private Intent intent;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;


    private List<String> imgUrl = new ArrayList<>();
    private List<ShouYe_BannerBean.DataBean.BannerBean> firstBanner = new ArrayList<>();
    private List<ZhuYe_CuXiaoBean.DataBeanX.DataBean> cuXiaoList = new ArrayList<>();
    private List<ShouYe_BannerBean.DataBean.DataRecommendBean> dataRecommend = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();


        String string = sp.getString("weizhi", "");
        if (string.equals("")) {

        } else {
            imgUrl.clear();
            dataRecommend.clear();
            cuXiaoList.clear();
            firstBanner.clear();
            getLoction();//定位
            getDatas();//获取消息条目
            String kong = sp.getString(MyRes.city2, "");
            cityName.setText(kong + "");
            present.getBanner(sp.getString(MyRes.area_id, ""));//首页轮播
            present.getPromo(sp.getString(MyRes.area_id, ""));//促销商品信息
            present.counselMinute("1", "2");
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ColorState.setWindowStatusBarColorBlue2(getActivity(), Color.parseColor("#ffffff"));
        View view = inflater.inflate(R.layout.fragment_zhuye_dd, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTxtGovement.setSelected(true);
        getLoction();//定位
        getDatas();//获取消息条目
        String kong = sp.getString(MyRes.city2, "");
        cityName.setText(kong + "");
        intent = new Intent();
        present = new Present(this);
        present.getBanner(sp.getString(MyRes.area_id, ""));//首页轮播
        present.getPromo(sp.getString(MyRes.area_id, ""));//促销商品信息
        present.counselMinute("1", "2");
    }

    //首页轮播和新品数据
    @Override
    public void getCode(String s) {
        Gson gson = new Gson();
        bean = gson.fromJson(s, ShouYe_BannerBean.class);
        final ShouYe_BannerBean.DataBean data = bean.getData();

        dataRecommend = data.getDataRecommend();
        adapters = new ZhuYeAdapter(dataRecommend, getActivity());
        manager = new GridLayoutManager(getActivity(), 2, GridLayout.VERTICAL, false);

        bannerTotal2.setLayoutManager(manager);
        bannerTotal2.setAdapter(adapters);

        adapters.setOnItemClickListener(new ZhuYeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {

                int goods_id = data.getDataRecommend().get(pos).getGoods_id();
                Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                intent.putExtra("token", goods_id + "");
                startActivity(intent);

            }

            @Override
            public void onCarClicked() {
                mListener.getCarNum("s");
            }
        });


        //首页轮播图片，只有一张
        firstBanner = data.getBanner();

        for (int i = 0; i < firstBanner.size(); i++) {
            ShouYe_BannerBean.DataBean.BannerBean bannerBean = firstBanner.get(i);
            String banner_cover = bannerBean.getBanner_cover();
            imgUrl.add(banner_cover);
        }

        bannerTotal.setImageLoader(new PicLoader());
        bannerTotal.setImages(imgUrl);
        bannerTotal.setDelayTime(4000);
        bannerTotal.start();


        bannerTotal.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if ((firstBanner.get(position).getBanner_open_type()+"").equals("")){
                    return;
                }


                if (firstBanner != null && firstBanner.size() > 0) {
                    if (firstBanner.get(position).getBanner_open_type() == 1) {//商品
                        Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                        intent.putExtra("token", firstBanner.get(position).getBanner_key_id() + "");
                        startActivity(intent);
                    } else if (firstBanner.get(position).getBanner_open_type() == 2) {//店铺
                        Intent intent = new Intent(getActivity(), KuaiSuXiaDanActivity.class);
                        intent.putExtra(MyRes.DIANPU_ID, firstBanner.get(position).getBanner_key_id() + "");
                        sp.edit().putString(MyRes.DIANPU_ID,firstBanner.get(position).getBanner_key_id() + "").commit();
                        startActivity(intent);
                    }
                }
            }
        });

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showFaliure(String s) {

    }

    //
    @Override
    public void getLogin(String s) {
        Gson g = new Gson();
        ZuiXinDongTaiBean zuiXinDongTaiBean = g.fromJson(s, ZuiXinDongTaiBean.class);
        int code = zuiXinDongTaiBean.getCode();
        if (code != 200) {
            mTxtGovement.setText(zuiXinDongTaiBean.getMsg());
            return;
        }

        StringBuffer buffer = new StringBuffer();
        ZuiXinDongTaiBean.DataBeanX data = zuiXinDongTaiBean.getData();
        List<ZuiXinDongTaiBean.DataBeanX.DataBean> data1 = data.getData();
        for (int i = 0; i < data1.size(); i++) {
            buffer.append(data1.get(i).getArticle_title());
        }
        mTxtGovement.setText(buffer.toString());
    }

    //优惠商品数据
    @Override
    public void getUpDate(String s) {
        Gson gson = new Gson();
        ZhuYe_CuXiaoBean zhuYe_cuXiaoBean = gson.fromJson(s, ZhuYe_CuXiaoBean.class);
        ZhuYe_CuXiaoBean.DataBeanX data = zhuYe_cuXiaoBean.getData();

        cuXiaoList = data.getData();
        cuXiaoAdapter = new ZhuYe_CuXiaoAdapter(cuXiaoList, getActivity());
        mRecyclerViewPresell.setAdapter(cuXiaoAdapter);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayout.VERTICAL, false);
        mRecyclerViewPresell.setLayoutManager(gridLayoutManager);
        cuXiaoAdapter.setOnItemClickListener(new ZhuYe_DongTaiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {

                /**
                 * 点击促销商品，跳转到商品详情页面
                 */

                ZhuYe_CuXiaoBean.DataBeanX.DataBean dataBean = cuXiaoList.get(pos);
                int goods_id = dataBean.getGoods_id();
                Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                intent.putExtra("token", goods_id + "");
                startActivity(intent);
            }

            @Override
            public void onCarClicked() {
                mListener.getCarNum("s");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_quyu,R.id.rl11,R.id.rl2, R.id.iv_emessage, R.id.ll_search, R.id.mBtn_castomlist, R.id.mBtn_buyRecordlist, R.id.mBtn_coupon, R.id.mBtn_buyByPhone, R.id.mBtn_plushuiyuan, R.id.img_zhengfugonggao, R.id.mTxt_govement, R.id.img_tuijianyoujiang, R.id.meiweixinpin, R.id.rexiao, R.id.mBtn_gengduo2, R.id.mBtn_gengduo1})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            //区域选择
            case R.id.ll_quyu:
                startActivity(new Intent(getActivity(), QuYuActivityNew.class));
                break;

            //搜索
            case R.id.ll_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;

            //查看消息
            case R.id.iv_emessage:
                isLogin(XiTongTiXingActivity.class);
                break;


            //智能采购
            case R.id.mBtn_castomlist:
                isLogin(LastWeekActivity_dd.class);
                break;

            //本地供应商
            case R.id.mBtn_buyRecordlist:
                startActivity(new Intent(getActivity(), NativeActivity.class));
                break;
            //食尚咨询
            case R.id.mBtn_coupon:
                startActivity(new Intent(getActivity(), FashionActivity.class));
                break;
            //索证索票
            case R.id.mBtn_buyByPhone:

                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(getActivity(), Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    RenZhen();
                }

                break;
            //PLUS会员
            case R.id.mBtn_plushuiyuan:
                startActivity(new Intent(getActivity(), BuyRecodeActivity.class));
//                startActivity(new Intent(getActivity(), GouwuActivity.class));
                break;
            //政府公告
            case R.id.img_zhengfugonggao:
                startActivity(new Intent(getActivity(), FashionActivity.class));
                break;
            //政府公告
            case R.id.mTxt_govement:
                startActivity(new Intent(getActivity(), FashionActivity.class));
                break;
            //推荐有奖
            case R.id.img_tuijianyoujiang:

                break;

            //美味新品
            case R.id.meiweixinpin:
                mListener.getString("gengduo1");
                break;
            //金牌热销
            case R.id.rexiao:
                mListener.getString("gengduo2");
                break;
            //更多新品
            case R.id.mBtn_gengduo1:
                mListener.getString("gengduo1");
                break;

            case R.id.rl11:
                mListener.getString("gengduo1");
                break;

            //更多优惠
            case R.id.mBtn_gengduo2:
                mListener.getString("gengduo2");
                break;

            case R.id.rl2:
                mListener.getString("gengduo2");
                break;
        }
    }


    private void isLogin(Class c) {
        if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
            ShowToast.showToast("请登录");
            intent.setClass(getActivity(), Login_ddActivity.class);
            startActivity(intent);

        } else {
            startActivity(new Intent(getActivity(), c));
        }
    }

    private void RenZhen() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/users/isAuth", RequestMethod.GET);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                if (response.get() != null) {
                    try {
                        RenZhenBean renZhenBean = GsonUtils.getInstance().fromJson((String) response.get(), RenZhenBean.class);
                        if (renZhenBean != null) {
                            if (renZhenBean.data.isAuth == 1) {//进去
                                intent = new Intent(getActivity(), XiangQingActivity.class);
                                intent.putExtra("wenID", 42 + "");
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("提示信息").setIcon(android.R.drawable.ic_dialog_alert);
                                builder.setMessage("此功能仅向认证企业用户开放");
                                builder.create().show();//创建并展示对话框
//                                ShowToast.showToast("此功能仅向认证企业用户开放");
                            }
                        }
                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onFailed(int what, Response response) {
                Log.e("shouye", "失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            ColorState.setWindowStatusBarColorBlue2(getActivity(),Color.parseColor("#ffffff"));
        }
    }

    /**
     * 定位初始化
     */
    private void getLoction() {

        locationClient = new AMapLocationClient(getActivity());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        locationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        //启动定位
        locationClient.startLocation();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        sp.edit().putString(MyRes.WEIDU, aMapLocation.getLatitude() + "").commit();
        sp.edit().putString(MyRes.JINGDU, aMapLocation.getLongitude() + "").commit();
        getDiZhi();
    }

    private void getDiZhi() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/getAreaId");
        stringRequest.add("lng", sp.getString(MyRes.JINGDU, ""));
        stringRequest.add("lat", sp.getString(MyRes.WEIDU, ""));
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject((String) response.get());
                    String code = jsonObject.getString("code");
                    if ("200".equals(code)) {
                        DingWeiBean dingWeiBean = GsonUtils.getInstance().fromJson((String) response.get(), DingWeiBean.class);
                        if (dingWeiBean != null) {
                            if (dingWeiBean.getData() != null) {
                                if (dingWeiBean.getData().getArea_name() != null) {
                                    area_name = dingWeiBean.getData().getArea_name();
                                } else {
                                    area_name = "";
                                }

                                String kong = sp.getString(MyRes.city2, "kong");

                                if (kong.equals("kong")) {
                                    cityName.setText(area_name);
                                    sp.edit().putString(MyRes.city2, area_name).commit();
                                    sp.edit().putString(MyRes.area_id, GsonUtils.getInstance().fromJson((String) response.get(), DingWeiBean.class).getData().getArea_id() + "").commit();

                                } else {
                                    cityName.setText(kong);
                                }
                            }

                        } else {
                            area_name = "";
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    private void getDatas() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/message/num");

        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));

        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                String result = response.get().toString();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        JSONObject object1 = object.getJSONObject("data");
                        int num = object1.getInt("num");
                        if (num > 0) {
                            ivDian.setVisibility(View.VISIBLE);
                            ivDian.setText(num + "");
                        } else {
                            ivDian.setVisibility(View.GONE);
                        }

                    }

                } catch (Exception e) {
                  /*  ShowToast.showToast("数据异常");*/
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private onClickListener mListener;

    public void setmListener(FirstFragment.onClickListener mListener) {
        this.mListener = mListener;
    }



    public interface onClickListener {
        void getString(String s);
        void getCarNum(String s);
    }
}
