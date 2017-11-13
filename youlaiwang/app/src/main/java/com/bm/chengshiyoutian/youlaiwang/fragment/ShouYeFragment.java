package com.bm.chengshiyoutian.youlaiwang.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GlideImageLoader2;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.DianPuShouYeActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.FashionActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.GongGaoActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.GouMaiShangPinActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.QuYuActivityNew;
import com.bm.chengshiyoutian.youlaiwang.activity.SearchActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.XiTongTiXingActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.adapter.CuXiaoAdapternew;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShouYeRCAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.CuXiaoBean1;
import com.bm.chengshiyoutian.youlaiwang.bean.DingWeiBean;
import com.bm.chengshiyoutian.youlaiwang.bean.RenZhenBean;
import com.bm.chengshiyoutian.youlaiwang.bean.ShouYeBean;
import com.bm.chengshiyoutian.youlaiwang.view.HeaderGridView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.NativeActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.LastWeekActivity_dd;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.MyLastActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.NewsDetialActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.TotalActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ZuiXinDongTaiBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.google.gson.Gson;
import com.jauker.widget.BadgeView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static com.bm.chengshiyoutian.youlaiwang.R.drawable.on;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_shishang;


public class ShouYeFragment extends Fragment implements View.OnClickListener, AMapLocationListener,IMainView {

    private HeaderGridView gv;
    private LinearLayout ll_search,ll_quyu,ll_yanse;
    private RecyclerView rc;
    private static Banner banner;
    private TextView city_name,tv_caigou,tv_gonggao,tv_gonggao2,tv_suopiao,tv_gongyingshang,tv_zixun,tv_you,mViewById;

    private ImageView iv_emessage,mShouye_guanggao,img_meiwei,img_jinpai;
    private BadgeView badgeView1;
    private int page = 1;
    private CuXiaoAdapternew cuXiaoAdapternew;
    private List<CuXiaoBean1.DataBeanX.DataBean> allData = new ArrayList<>();
    private SharedPreferences sp;
    private Activity context;
    //获取自动定位的城市
    private String area_name;
    //dingwei
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private Intent intent;

    private List<ShouYeBean.DataBean.BannerBean> banners;
    private List<ShouYeBean.DataBean.DataRecommendBean> dataRecommend;
    private ArrayList<String> strings;
    private ArrayList<Integer> ints;
    private TextView tv_shishang;
    private Present present;
    private Button mBtn_gengduo1,mBtn_gengduo2;
    private TextView tv_huiyuan;


    public ShouYeFragment() {

    }

    public static ShouYeFragment newInstance(String param1) {
        ShouYeFragment fragment = new ShouYeFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        String kong = sp.getString(MyRes.city2, "");
        city_name.setText(kong + "");
        allData.clear();
        page = 1;
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDatas();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shouye, container, false);
        context = (Activity) getActivity();
        sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);

        mViewById = (TextView) view.findViewById(R.id.iv_dian);
        present=new Present(this);
        present.getNews(1);
        initView(view);
        badgeView1 = new BadgeView(getActivity());
        page = 1;
        cuXiaoAdapternew = null;
        sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);

        //自动定位的经纬度
        getLoction();

        allData.clear();
        //得到除了促销之外的数据
        //initData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView(View view) {

        gv = (HeaderGridView) view.findViewById(R.id.gv);
        View head = View.inflate(context, R.layout.shouye_head, null);//--------------------
        gv.addHeaderView(head);
//        gv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        /**
         * 停止使用刷新功能
         */


//        gv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
//                ShowToast.showToast("1");
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
//                new GetDataTask().execute();
//            }
//        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
//        mShouye_guanggao = (ImageView) head.findViewById(R.id.shouye_guanggao);
        ll_search = (LinearLayout) view.findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        ll_quyu = (LinearLayout) view.findViewById(R.id.ll_quyu);
        ll_quyu.setOnClickListener(this);
        rc = (RecyclerView) head.findViewById(R.id.rc);

        banner = (Banner) head.findViewById(R.id.banner);

        ll_yanse = (LinearLayout) view.findViewById(R.id.ll_yanse);
        ll_yanse.setOnClickListener(this);
        city_name = (TextView) view.findViewById(R.id.city_name);
        tv_caigou = (TextView) head.findViewById(R.id.tv_caigou);
        tv_caigou.setOnClickListener(this);
        tv_gonggao = (TextView) head.findViewById(R.id.tv_gonggao);
        tv_gonggao.setOnClickListener(this);

        tv_gonggao2 = (TextView) head.findViewById(R.id.tv_gonggao2);
        tv_gonggao2.setOnClickListener(this);
        tv_gonggao2.setSelected(true);

        present.counselMinute("1","2");

        tv_suopiao = (TextView) head.findViewById(R.id.tv_suopiao);
        tv_suopiao.setOnClickListener(this);
        tv_gongyingshang = (TextView) head.findViewById(R.id.tv_gongyingshang);
        tv_gongyingshang.setOnClickListener(this);
        tv_zixun = (TextView) head.findViewById(R.id.tv_zixun);
        tv_zixun.setOnClickListener(this);
        tv_you = (TextView) head.findViewById(R.id.tv_you);
        tv_you.setOnClickListener(this);

        iv_emessage = (ImageView) view.findViewById(R.id.iv_emessage);
        iv_emessage.setOnClickListener(this);

        tv_shishang = (TextView) head.findViewById(R.id.tv_shishang);
        tv_shishang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FashionActivity.class));
            }
        });


        img_meiwei = (ImageView) head.findViewById(R.id.meiweixinpin);
        img_jinpai = (ImageView) head.findViewById(R.id.jinpairexiao);
        tv_huiyuan = (TextView) head.findViewById(R.id.tv_huiyuan);
        tv_huiyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),TotalActivity.class));
            }
        });

        img_jinpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.getString("gengduo2");
            }
        });

        img_meiwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.getString("gengduo1");
            }
        });


        mBtn_gengduo1 = (Button) head.findViewById(R.id.mBtn_gengduo1);
        mBtn_gengduo2 = (Button) head.findViewById(R.id.mBtn_gengduo2);

        //点击按钮，选中Fragment3
        mBtn_gengduo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.getString("gengduo1");
            }
        });

        mBtn_gengduo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.getString("gengduo2");
            }
        });
    }

    private onClickListener mListener;

    public void setmListener(onClickListener mListener) {
        this.mListener = mListener;
    }

    public interface onClickListener{
        void getString(String s);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_quyu://获取定位信息
                intent = new Intent(getActivity(), QuYuActivityNew.class);
                startActivity(intent);
                break;

            case R.id.iv_emessage://点击查看消息
                isLogin(XiTongTiXingActivity.class);
                break;

            case R.id.tv_caigou://智能采购

                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(getActivity(), Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);

                } else {
                    intent = new Intent(getActivity(), LastWeekActivity_dd.class);
                    startActivity(intent);
                }

                break;


            /**
             *
             * 公告栏：首页需要点击跳转公告页面（新增功能）
             *
             */
            case R.id.tv_gonggao:
                startActivity(new Intent(getActivity(), FashionActivity.class));
                break;
            case R.id.tv_gonggao2:

//                EventBus.getDefault().post("tv_gonggao");
                startActivity(new Intent(getActivity(), FashionActivity.class));
                break;


            case R.id.tv_suopiao:

                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(getActivity(), Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    RenZhen();
                }

                break;

            /**
             * 本地供应商对应按钮，需要新开一个页面
             *
             *
             *
             */
            case R.id.tv_gongyingshang:
                startActivity(new Intent(getActivity(), NativeActivity.class));
                break;
            case R.id.tv_zixun:
                EventBus.getDefault().post("tv_zixun");
                break;


            case R.id.tv_you:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(getActivity(),Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);

                } else {
                    RenZhen1();
                }

                break;

        }
    }

    private void isLogin(Class c) {
        if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
            ShowToast.showToast("请登录");
            intent = new Intent(getActivity(), Login_ddActivity.class);
            intent.putExtra("tag", "two");
            startActivity(intent);

        } else {
            intent = new Intent(getActivity(), XiTongTiXingActivity.class);
            startActivity(intent);
        }
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
                            mViewById.setVisibility(View.VISIBLE);
                            mViewById.setText(num + "");
                        } else {
                            mViewById.setVisibility(View.GONE);
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

    private void getData1() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/index/promo");
        stringRequest.add("page", page);
        stringRequest.add("areaId", sp.getString(MyRes.area_id, ""));
        CallServer.getInstance().add(11, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                final List<CuXiaoBean1.DataBeanX.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), CuXiaoBean1.class).data.data;
                allData.addAll(data);
                if (cuXiaoAdapternew == null) {
                    cuXiaoAdapternew = new CuXiaoAdapternew(allData, context);
                    gv.setAdapter(cuXiaoAdapternew);
                } else {
                    cuXiaoAdapternew.notifyDataSetChanged();
                }


                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (position > 1) {
                            Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                            intent.putExtra("token", allData.get(position - 2).goods_id + "");
                            startActivity(intent);

                        } else {


                        }

                    }
                });

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


    protected void initData() {

        strings = new ArrayList<>();
        strings.add("智能采购");
        strings.add("政府公告");
        strings.add("索证索票");
        strings.add("本体供应商");
        strings.add("行业咨讯");
        strings.add("废弃油");

        ints = new ArrayList<>();
        ints.add(R.drawable.zhineng_caigou);
        ints.add(R.drawable.zhengfu_gonggao);
        ints.add(R.drawable.suozheng_suopiao);
        ints.add(R.drawable.bendi_gongyingshang);
        ints.add(R.drawable.shishang_zixun);
        ints.add(R.drawable.shishang_zixun);

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/index");
        stringRequest.add("areaId", sp.getString(MyRes.area_id, ""));

        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                List<String> goods_images = new ArrayList<String>();
                ShouYeBean.DataBean data = GsonUtils.getInstance().fromJson((String) response.get(), ShouYeBean.class).getData();
                //轮播图数据
                banners = data.getBanner();
                for (int x = 0; x < banners.size(); x++) {
                    goods_images.add(banners.get(x).getBanner_cover());

                }

//                Glide.with(context).load(data.getAds().getAds_cover()).error(R.drawable.shouye_guanggao).placeholder(R.mipmap.loading_on_img).into(mShouye_guanggao);
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader2(getActivity()));
                //设置图片集合
                banner.setImages(goods_images);
                //banner设置方法全部调用完毕时最后调用

                banner.start();
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if (banners != null && banners.size() > 0) {
                            int key_id = banners.get(position).getBanner_key_id();
                            if (key_id == 0) {

                            } else {
                                if (banners.get(position).getBanner_open_type() == 1) {//商品
                                    Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                                    intent.putExtra("token", banners.get(position).getBanner_key_id() + "");
                                    startActivity(intent);
                                } else if (banners.get(position).getBanner_open_type() == 2) {//店铺
                                    Intent intent = new Intent(getActivity(), DianPuShouYeActivity.class);
                                    intent.putExtra(MyRes.DIANPU_ID, banners.get(position).getBanner_key_id() + "");
                                    startActivity(intent);
                                }
                            }

                        }
                    }
                });
                //新品的数据
                dataRecommend = data.getDataRecommend();


                // 设置布局管理器
                rc.setLayoutManager(new GridLayoutManager(getActivity(), 2,GridLayout.VERTICAL, false));


                // 创建Adapter，并指定数据集
                ShouYeRCAdapter adapter = new ShouYeRCAdapter(getActivity(), dataRecommend);
                // 设置Adapter
                rc.setAdapter(adapter);

                getData1();
            }

            @Override
            public void onFailed(int what, Response response) {
                Log.d("shouye", "失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });


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
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void RenZhen1() {
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
                                intent.putExtra("wenID", 43 + "");
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
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void getCode(String s) {

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

    @Override
    public void getLogin(String s) {
        Gson g = new Gson();
        ZuiXinDongTaiBean zuiXinDongTaiBean = g.fromJson(s, ZuiXinDongTaiBean.class);
        int code = zuiXinDongTaiBean.getCode();
        if(code!=200){
            tv_gonggao2.setText(zuiXinDongTaiBean.getMsg());
            return;
        }

        StringBuffer buffer = new StringBuffer();
        ZuiXinDongTaiBean.DataBeanX data = zuiXinDongTaiBean.getData();
        List<ZuiXinDongTaiBean.DataBeanX.DataBean> data1 = data.getData();
        for (int i = 0; i < data1.size(); i++) {
            buffer.append(data1.get(i).getArticle_title());
        }
        tv_gonggao2.setText(buffer.toString());
    }

    @Override
    public void getUpDate(String s) {

    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            page++;
            getData1();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            gv.onRefreshComplete();
        }
    }

    /**
     * 定位初始化
     */
    private void getLoction() {

        locationClient = new AMapLocationClient(context);
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

                                //
                                String kong = sp.getString(MyRes.city2, "kong");

                                if (kong.equals("kong")) {
                                    city_name.setText(area_name);
                                    sp.edit().putString(MyRes.city2, area_name).commit();
                                    sp.edit().putString(MyRes.area_id, GsonUtils.getInstance().fromJson((String) response.get(), DingWeiBean.class).getData().getArea_id() + "").commit();

                                } else {
                                    city_name.setText(kong);
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}