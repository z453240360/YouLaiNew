package com.bm.chengshiyoutian.youlaiwang.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.SharedPUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.QuYuActivityNew;
import com.bm.chengshiyoutian.youlaiwang.activity.SearchActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.XiTongTiXingActivity;
import com.bm.chengshiyoutian.youlaiwang.adapter.FenLeiGVAdapter;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinAdapter2;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinRCAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.DingWeiBean;
import com.bm.chengshiyoutian.youlaiwang.bean.FenleiBean;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinYouBianBean;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinZuoBianBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.constant.Constant;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jauker.widget.BadgeView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static cn.jpush.android.api.b.e;
import static cn.jpush.android.api.b.k;
import static com.bm.chengshiyoutian.youlaiwang.R.dimen.s;
import static com.bm.chengshiyoutian.youlaiwang.R.id.city_name;
import static com.bm.chengshiyoutian.youlaiwang.R.id.lv;
import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;
import static com.nostra13.universalimageloader.core.ImageLoader.TAG;


public class ShangPinFragment extends Fragment implements View.OnClickListener, AMapLocationListener {
    //页码数
    private int page = 1;
    //一级父类ID
    private String p1ID = "";
    //二级父类ID
    private String p2ID = "";
    //三级父类Id
    private String p3ID = "";
    private String orderBy = "";//排序

    //价格是升还是降
    private boolean tag;
    private boolean bb = false;
    private String area_name = "";
    private String token, head;
    private String 经度 = "";
    private String 纬度 = "";
    private int flag;


    private SharedPreferences sp;
    private Context context;

    private BadgeView badgeView1;
    private PullToRefreshListView lv2;
    private LinearLayout ll1;
    private TextView tv1,tv2,tv3,tv4,mCity_name,mViewById;
    private GridView gv;
    private RecyclerView rc;
    private ImageView paixu,iv_emessage;
    private ProgressBar pb;
    private LinearLayout ll_sou,mLl_quyu,ll_kongGouWuChe;


    //具体商品显示的适配器
    private ShangPinAdapter2 shangPinAdapter2;
    private FenLeiGVAdapter fenLeiGVAdapter;

    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;

    private ArrayList<String> strings;
    private ArrayList<String> ints;
    private ArrayList<Boolean> tags;

    //总的数据源
    private List<ShangPinYouBianBean.DataBeanX.DataBean> allData = new ArrayList<>();
    private List<ShangPinYouBianBean.DataBeanX.DataBean> data1;

    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (bb) {
                        tags.add(msg.getData().getInt("position") - 1, false);
                    } else {
                        tags.add(msg.getData().getInt("position") - 1, true);
                    }
                    fenLeiGVAdapter.notifyDataSetChanged();
                    break;
            }
        }

    };


    @Override
    public void onStart() {
        super.onStart();
        String kong = sp.getString(MyRes.city2, "");
        mCity_name.setText(kong + "");
    }

//
//    @Override
//    public void onResume() {
//        super.onResume();
//        tv1.setTextColor(0xff333333);
//        tv2.setTextColor(0xff333333);
//        tv3.setTextColor(0xff333333);
//        tv4.setTextColor(0xff333333);
//        paixu.setImageResource(R.mipmap.diangpu_xiangxia);
//        tag = false;
//        p3ID = null;
//        经度 = "";
//        纬度 = "";
//        page = 1;
//        p1ID = "";
//        p2ID = "";
//        p3ID = "";
//        orderBy = "";
//        onFirst();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ColorState.setWindowStatusBarColorBlue(getActivity(), Color.BLUE);
        View view = inflater.inflate(R.layout.fragment_shangpin, container, false);

        mViewById = (TextView) view.findViewById(R.id.iv_dian);
        initView(view);
        onFirst();

        if (shangPinAdapter2 != null) {
            shangPinAdapter2.setOnClicked(new ShangPinAdapter2.OnClicked() {
                @Override
                public void getIntent(int pos, String goodsId) {
                    Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                    intent.putExtra("token", goodsId);
                    intent.putExtra("postion", pos);
                    startActivityForResult(intent, 100);
                }
            });
        }
        return view;
    }

    //初始化控件
    private void initView(View view) {
        ll_kongGouWuChe = (LinearLayout) view.findViewById(R.id.ll_kongGouWuChe);
        mLl_quyu = (LinearLayout) view.findViewById(R.id.ll_quyu);
        mLl_quyu.setOnClickListener(this);
        mCity_name = (TextView) view.findViewById(city_name);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv1.setOnClickListener(this);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv2.setOnClickListener(this);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv3.setOnClickListener(this);
        tv4 = (TextView) view.findViewById(R.id.tv4);
        tv4.setOnClickListener(this);
        gv = (GridView) view.findViewById(R.id.gv);

        rc = (RecyclerView) view.findViewById(R.id.rc);
        paixu = (ImageView) view.findViewById(R.id.paixu);
        pb = (ProgressBar) view.findViewById(R.id.pb);
        ll1 = (LinearLayout) view.findViewById(R.id.ll1);
        lv2 = (PullToRefreshListView) view.findViewById(R.id.lv2);
        lv2.setMode(PullToRefreshBase.Mode.BOTH);


        lv2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                allData.clear();
//                new GetDataTask().execute();
                getdata5();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
//                new GetDataTask().execute();
                getdata5();
            }
        });


        ll_sou = (LinearLayout) view.findViewById(R.id.ll_sou);
        ll_sou.setOnClickListener(this);
        iv_emessage = (ImageView) view.findViewById(R.id.iv_emessage);
        iv_emessage.setOnClickListener(this);
    }

    public void onFirst() {
        page = 1;
        allData.clear();
        context = getActivity();
        token = getActivity().getIntent().getStringExtra("token");
        sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/index");
        stringRequest.addHeader("Authorization", "Bearer " + token);
        head = "Bearer " + token;

        if (shangPinAdapter2 != null) {
            shangPinAdapter2 = null;
        }

        badgeView1 = new BadgeView(getActivity());
        getLoction();
        getGVData();
        getRCData(0 + "");


        String string = sp.getString("myDdJump", "null");
        if (string.equals("gengduo1")) {
            ByNew();
            sp.edit().putString("myDdJump", "null").commit();
        } else if (string.equals("gengduo2")) {
            ByCuXiao();
            sp.edit().putString("myDdJump", "null").commit();
        }

        getDatas();
    }

    @Override
    public void onPause() {
        super.onPause();
        p1ID = "";
//       onFirst();
    }

    //商品页面消息
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

                } catch (JsonSyntaxException e) {
                } catch (JSONException e) {
                    e.printStackTrace();
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

    //获取一级分类的标签（蔬菜水果，肉禽蛋类......）
    public void getGVData() {
        strings = new ArrayList<>();//名称集合
        ints = new ArrayList<>();//图标集合         /* ints.add(R.drawable.shucai_shuiguo);*/
        tags = new ArrayList<>();//是否选中集合     /*  tags.add(false)*/
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/class");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                String httpsss = response.toString();
                String json = (String) response.get();
                strings.clear();
                ints.clear();
                tags.clear();

                final FenleiBean fenleiBean = GsonUtils.getInstance().fromJson(json, FenleiBean.class);
                for (int i = 0; i < fenleiBean.getData().size(); i++) {
                    strings.add(fenleiBean.getData().get(i).getGc_name());
                    ints.add(fenleiBean.getData().get(i).getImage());
                    tags.add(false);
                }

                fenLeiGVAdapter = new FenLeiGVAdapter(ints, strings, tags, getActivity());
                gv.setAdapter(fenLeiGVAdapter);


                //一级分类标签的点击事件
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        allData.clear();
                        p1ID = fenleiBean.getData().get(position).getGc_id() + "";
                        bb = tags.get(position);
                        if (bb) {
                            getRCData("");
                            p1ID = "";
                        } else {
                            getRCData(p1ID);
                        }
                        shangPinAdapter2 = null;
                        page = 1;
                        tags.clear();


                        new Thread(

                        ) {
                            @Override
                            public void run() {
                                for (int i = 0; i < 9; i++) {

                                    tags.add(i, false);
                                }

                                Message message = new Message();
                                message.what = 1;
                                Bundle bundle = new Bundle(position);
                                bundle.putInt("position", position + 1);
                                message.setData(bundle);
                                myHandler.sendMessage(message);
                            }
                        }.start();


                    }
                });
            }

            @Override
            public void onFailed(int what, Response response) {
                lv2.onRefreshComplete();
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    //二级综合商品分类列表（传值为”“ 时显示全部二级分类列表）
    public void getRCData(String pid) {
        final Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/class/two");
        stringRequest.add("pid", pid);
        stringRequest.add("areaId", sp.getString(MyRes.area_id, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    final List<ShangPinZuoBianBean.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinZuoBianBean.class).getData();
                    // tv_name.setText(data.get(0).getGc_name());
                    // 创建一个线性布局管理器
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    // 设置布局管理器
                    rc.setLayoutManager(layoutManager);
                    layoutManager.setOrientation(OrientationHelper.VERTICAL);
                    // 创建Adapter，并指定数据集
                    final ShangPinRCAdapter adapter = new ShangPinRCAdapter(context, data);
                    // 设置Adapter
                    data.get(0).tag = true;
                    rc.setAdapter(adapter);
                    allData.clear();
                    getYouBianData(p2ID = data.get(0).getGc_id() + "");
                    adapter.setOnItemClickListener(new ShangPinRCAdapter.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            for (int j = 0; j < data.size(); j++) {
                                data.get(j).tag = false;
                            }

                            page = 1;
                            shangPinAdapter2 = null;
                            data.get(position).tag = true;
                            flag = position;
                            adapter.notifyDataSetChanged();
                            allData.clear();


                            //当点击的时候  获得右边的数据

                            getYouBianData(p2ID = data.get(position).getGc_id() + "");

                            tv1.setTextColor(0xff333333);
                            tv2.setTextColor(0xff333333);
                            tv3.setTextColor(0xff333333);
                            tv4.setTextColor(0xff333333);
                            paixu.setImageResource(R.mipmap.diangpu_xiangxia);
                            tag = false;
                            p3ID = null;
                        }


                    });
                    SharedPUtils.storeString(getActivity(), "DDpostion", p2ID);
                } catch (JsonSyntaxException e) {

                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                pb.setVisibility(View.GONE);
            }
        });
    }

    //获取详细的商品列表
    public void getYouBianData(String s) {
        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
        request.add("classId", s);
        request.add("page", page);
        request.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        request.add("areaId", sp.getString(MyRes.area_id, ""));

        NoHttp.newRequestQueue().add(1, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    data1 = GsonUtils.getInstance().fromJson(response.get(), ShangPinYouBianBean.class).getData().getData();

                    if (page > 1) {
                        if (data1.size() == 0) {
                            lv2.onRefreshComplete();
                            ShowToast.showToast("已经显示全部数据");
                            return;
                        }
                    }

                    allData.addAll(data1);

                    if (shangPinAdapter2 == null) {
                        if (allData.size() > 0) {
                            ll_kongGouWuChe.setVisibility(View.GONE);
                            lv2.setVisibility(View.VISIBLE);
                            shangPinAdapter2 = new ShangPinAdapter2(allData, context, sp.getString(MyRes.MY_TOKEN, ""));

                            if (shangPinAdapter2 != null) {
                                shangPinAdapter2.setOnClicked(new ShangPinAdapter2.OnClicked() {
                                    @Override
                                    public void getIntent(int pos, String goodsId) {
                                        Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                                        intent.putExtra("token", goodsId);
                                        startActivityForResult(intent, 100);
                                    }
                                });
                            }
                            lv2.setAdapter(shangPinAdapter2);
                        } else {
                            ll_kongGouWuChe.setVisibility(View.VISIBLE);
                            lv2.setVisibility(View.GONE);

                        }

                    } else {
                        shangPinAdapter2.notifyDataSetChanged();
                        lv2.onRefreshComplete();
                    }


                } catch (JsonSyntaxException e) {
                    lv2.onRefreshComplete();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                lv2.onRefreshComplete();
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                pb.setVisibility(View.GONE);
            }
        });
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            if ("1".equals(p3ID)) {
                page++;
                getData1();
            }
            if ("2".equals(p3ID)) {
                page++;
                getData2();
            }
            if ("3".equals(p3ID)) {
                page++;
                getdata3();
            }
            if ("4".equals(p3ID)) {
                page++;
                getdata4();
            }
            if (p3ID == null) {
                page++;
                getYouBianData(p2ID);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            lv2.onRefreshComplete();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //跳转定位页面
            case R.id.ll_quyu:
                Intent intents = new Intent(getActivity(), QuYuActivityNew.class);
                startActivity(intents);
                break;

            //按照价格排序查询商品
            case R.id.tv1:
                ByPrice();
                break;


            //系统消息
            case R.id.iv_emessage:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    Intent intent = new Intent(context, Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(context, XiTongTiXingActivity.class);
                    startActivity(intent);
                }
                break;

            //新品排序
            case R.id.tv2:
                ByNew();
                break;


            //促销排序
            case R.id.tv3:
                ByCuXiao();

                break;
            //附近
            case R.id.tv4:
                ByNear();
                break;

            //搜索
            case R.id.ll_sou:
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    //搜索附近
    public void ByNear() {
        allData.clear();
        tv4.setTextColor(Color.rgb(118, 201, 51));
        tv2.setTextColor(0xff333333);
        shangPinAdapter2 = null;
        page = 1;
        经度 = "";
        纬度 = "";
        tv3.setTextColor(0xff333333);
        tv1.setTextColor(0xff333333);
        p3ID = 4 + "";

        getdata4();
    }

    //促销商品
    public void ByCuXiao() {
        page = 1;
        经度 = "";
        纬度 = "";
        allData.clear();
        tv3.setTextColor(Color.rgb(118, 201, 51));
        tv2.setTextColor(0xff333333);
        shangPinAdapter2 = null;
        tv1.setTextColor(0xff333333);
        tv4.setTextColor(0xff333333);
        p3ID = 3 + "";
        getdata3();
    }

    //新品
    public void ByNew() {
        page = 1;
        p3ID = 2 + "";
        经度 = "";
        纬度 = "";
        allData.clear();
        shangPinAdapter2 = null;
        tv2.setTextColor(Color.rgb(118, 201, 51));
        tv1.setTextColor(0xff333333);
        tv3.setTextColor(0xff333333);
        tv4.setTextColor(0xff333333);
        getData2();
    }

    //价格排序
    public void ByPrice() {
        shangPinAdapter2 = null;
        p3ID = 1 + "";
        tv1.setTextColor(Color.rgb(118, 201, 51));
        tv2.setTextColor(0xff333333);
        tv3.setTextColor(0xff333333);
        tv4.setTextColor(0xff333333);
        page = 1;
        经度 = "";
        纬度 = "";
        if (tag) {
            paixu.setImageResource(R.mipmap.diangpu_xiangxia);
            tag = false;
        } else {
            paixu.setImageResource(R.mipmap.diangpu_xiangshang);
            tag = true;
        }
        allData.clear();
        getData1();
    }

    /**
     * 获取数据源
     */

    //附近商品数据
    private void getdata4() {

        经度 = sp.getString(MyRes.JINGDU, "经度");
        纬度 = sp.getString(MyRes.WEIDU, "纬度");
        orderBy = "";
        Request<String> stringRequest4 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
        stringRequest4.add("classId", p2ID);
        stringRequest4.add("state", p3ID);
        stringRequest4.add("lng", 经度);
        stringRequest4.add("lat", 纬度);
        stringRequest4.add("page", page);
        stringRequest4.add("areaId", sp.getString(MyRes.area_id, ""));
        CallServer.getInstance().add(21, stringRequest4, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    List<ShangPinYouBianBean.DataBeanX.DataBean> data1 = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinYouBianBean.class).getData().getData();
                    if (page > 1) {
                        if (data1.size() == 0) {
                            lv2.onRefreshComplete();
                            ShowToast.showToast("已经显示全部数据");
                            return;
                        }
                    }
                    allData.addAll(data1);
                    if (shangPinAdapter2 == null) {
                        shangPinAdapter2 = new ShangPinAdapter2(allData, context, sp.getString(MyRes.MY_TOKEN, ""));
                        lv2.setAdapter(shangPinAdapter2);

                        if (shangPinAdapter2 != null) {
                            shangPinAdapter2.setOnClicked(new ShangPinAdapter2.OnClicked() {
                                @Override
                                public void getIntent(int pos, String goodsId) {
                                    Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                                    intent.putExtra("token", goodsId);
                                    startActivityForResult(intent, 100);
                                }
                            });
                        }

                    } else {
                        shangPinAdapter2.notifyDataSetChanged();
                        lv2.onRefreshComplete();
                    }
                } catch (JsonSyntaxException e) {
                    lv2.onRefreshComplete();
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                lv2.onRefreshComplete();
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                pb.setVisibility(View.GONE);
            }
        });

    }

    //促销数据
    private void getdata3() {
        经度 = "";
        纬度 = "";
        orderBy = "";
        Request<String> stringRequest3 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
        stringRequest3.add("classId", p2ID);
        stringRequest3.add("state", p3ID);
        stringRequest3.add("page", page);
        stringRequest3.add("areaId", sp.getString(MyRes.area_id, ""));
        CallServer.getInstance().add(1, stringRequest3, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    List<ShangPinYouBianBean.DataBeanX.DataBean> data1 = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinYouBianBean.class).getData().getData();
                    if (page > 1) {
                        if (data1.size() == 0) {
                            lv2.onRefreshComplete();
                            ShowToast.showToast("已经显示全部数据");
                            return;
                        }
                    }
                    allData.addAll(data1);
                    if (shangPinAdapter2 == null) {
                        shangPinAdapter2 = new ShangPinAdapter2(allData, context, sp.getString(MyRes.MY_TOKEN, ""));
                        lv2.setAdapter(shangPinAdapter2);

                        if (shangPinAdapter2 != null) {
                            shangPinAdapter2.setOnClicked(new ShangPinAdapter2.OnClicked() {
                                @Override
                                public void getIntent(int pos, String goodsId) {
                                    Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                                    intent.putExtra("token", goodsId);
                                    startActivityForResult(intent, 100);
                                }
                            });
                        }
                    } else {
                        shangPinAdapter2.notifyDataSetChanged();
                        lv2.onRefreshComplete();
                    }
                } catch (JsonSyntaxException e) {
                    lv2.onRefreshComplete();
                    ShowToast.showToast("数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                lv2.onRefreshComplete();
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                pb.setVisibility(View.GONE);
            }
        });
    }

    //新品数据
    private void getData2() {
        经度 = "";
        纬度 = "";
        orderBy = "";
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
        stringRequest.add("classId", p2ID);
        stringRequest.add("page", page);
        stringRequest.add("state", p3ID);
        stringRequest.add("areaId", sp.getString(MyRes.area_id, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {

            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    List<ShangPinYouBianBean.DataBeanX.DataBean> data1 = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinYouBianBean.class).getData().getData();
                    if (page > 1) {
                        if (data1.size() == 0) {
                            lv2.onRefreshComplete();
                            ShowToast.showToast("已经显示全部数据");
                            return;
                        }
                    }
                    allData.addAll(data1);
                    if (shangPinAdapter2 == null) {
                        shangPinAdapter2 = new ShangPinAdapter2(allData, context, sp.getString(MyRes.MY_TOKEN, ""));
                        lv2.setAdapter(shangPinAdapter2);

                        if (shangPinAdapter2 != null) {
                            shangPinAdapter2.setOnClicked(new ShangPinAdapter2.OnClicked() {
                                @Override
                                public void getIntent(int pos, String goodsId) {
                                    Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                                    intent.putExtra("token", goodsId);
                                    startActivityForResult(intent, 100);
                                }
                            });
                        }
                    } else {

                        shangPinAdapter2.notifyDataSetChanged();
                        lv2.onRefreshComplete();
                    }
                } catch (JsonSyntaxException e) {
                    lv2.onRefreshComplete();
                    ShowToast.showToast("数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                lv2.onRefreshComplete();
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                pb.setVisibility(View.GONE);
                lv2.onRefreshComplete();
            }
        });

    }

    //价格排序
    private void getData1() {

        经度 = "";
        纬度 = "";
        if (tag) {
            orderBy = "desc";
            Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
            stringRequest.add("classId", p2ID);
            stringRequest.add("state", p3ID);
            stringRequest.add("page", page);
            stringRequest.add("orderBy", "desc");
            stringRequest.add("areaId", sp.getString(MyRes.area_id, ""));
            CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
                @Override
                public void onStart(int what) {
                    pb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSucceed(int what, Response response) {
                    try {
                        List<ShangPinYouBianBean.DataBeanX.DataBean> data1 = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinYouBianBean.class).getData().getData();
                        if (page > 1) {
                            if (data1.size() == 0) {
                                lv2.onRefreshComplete();
                                ShowToast.showToast("已经显示全部数据");
                                return;
                            }
                        }
                        allData.addAll(data1);
                        if (shangPinAdapter2 == null) {
                            shangPinAdapter2 = new ShangPinAdapter2(allData, context, sp.getString(MyRes.MY_TOKEN, ""));
                            lv2.setAdapter(shangPinAdapter2);

                            if (shangPinAdapter2 != null) {
                                shangPinAdapter2.setOnClicked(new ShangPinAdapter2.OnClicked() {
                                    @Override
                                    public void getIntent(int pos, String goodsId) {
                                        Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                                        intent.putExtra("token", goodsId);
                                        startActivityForResult(intent, 100);
                                    }
                                });
                            }

                        } else {

                            shangPinAdapter2.notifyDataSetChanged();
                            lv2.onRefreshComplete();

                        }
                    } catch (JsonSyntaxException e) {
                        ShowToast.showToast("数据异常");
                    }
                }

                @Override
                public void onFailed(int what, Response response) {
                    ShowToast.showToast("联网失败");
                }

                @Override
                public void onFinish(int what) {
                    pb.setVisibility(View.GONE);
                }
            });


        } else {
            orderBy = "asc";
            Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
            stringRequest.add("classId", p2ID);
            stringRequest.add("state", p3ID);
            stringRequest.add("page", page);
            stringRequest.add("orderBy", "asc");
            stringRequest.add("areaId", sp.getString(MyRes.area_id, ""));
            CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
                @Override
                public void onStart(int what) {
                    pb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSucceed(int what, Response response) {
                    try {
                        List<ShangPinYouBianBean.DataBeanX.DataBean> data1 = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinYouBianBean.class).getData().getData();
                        if (data1.size() == 0) {
                            ShowToast.showToast("已显示全部数据");
                            lv2.onRefreshComplete();
                            return;

                        }
                        allData.addAll(data1);
                        if (shangPinAdapter2 == null) {
                            shangPinAdapter2 = new ShangPinAdapter2(allData, context, sp.getString(MyRes.MY_TOKEN, ""));
                            lv2.setAdapter(shangPinAdapter2);

                            if (shangPinAdapter2 != null) {
                                shangPinAdapter2.setOnClicked(new ShangPinAdapter2.OnClicked() {
                                    @Override
                                    public void getIntent(int pos, String goodsId) {
                                        Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                                        intent.putExtra("token", goodsId);
                                        startActivityForResult(intent, 100);
                                    }
                                });
                            }
                        } else {

                            shangPinAdapter2.notifyDataSetChanged();
                            lv2.onRefreshComplete();

                        }
                    } catch (JsonSyntaxException e) {
                        ShowToast.showToast("数据异常");
                        lv2.onRefreshComplete();
                    }
                }

                @Override
                public void onFailed(int what, Response response) {
                    ShowToast.showToast("联网失败");
                }

                @Override
                public void onFinish(int what) {
                    pb.setVisibility(View.GONE);
                }
            });

        }

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
                                    mCity_name.setText(area_name);
                                    sp.edit().putString(MyRes.city2, area_name).commit();
                                    sp.edit().putString(MyRes.area_id, GsonUtils.getInstance().fromJson((String) response.get(), DingWeiBean.class).getData().getArea_id() + "").commit();

                                } else {
                                    mCity_name.setText(kong);

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            ColorState.setWindowStatusBarColorBlue(getActivity(), Color.parseColor("#ffffff"));
            String string = sp.getString("myDdJump", "null");
            if (string.equals("gengduo1")) {
                ByNew();
            } else if (string.equals("gengduo2")) {
                ByCuXiao();
            }

            sp.edit().putString("myDdJump", "null").commit();
        }
    }

    //促销数据
    private void getdata5() {
        Request<String> stringRequest3 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
        stringRequest3.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest3.add("classId", p2ID);
        stringRequest3.add("classOneId", p1ID);
        stringRequest3.add("state", p3ID);
        stringRequest3.add("page", page);
        stringRequest3.add("lng", 经度);
        stringRequest3.add("lat", 纬度);
        stringRequest3.add("orderBy", orderBy);
        stringRequest3.add("areaId", sp.getString(MyRes.area_id, ""));
        CallServer.getInstance().add(1, stringRequest3, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    List<ShangPinYouBianBean.DataBeanX.DataBean> data1 = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinYouBianBean.class).getData().getData();
                    if (page > 1) {
                        if (data1.size() == 0) {
                            ShowToast.showToast("已显示全部数据");
                            lv2.onRefreshComplete();
                            return;
                        }
                    }
                    allData.addAll(data1);
                    if (shangPinAdapter2 == null) {
                        shangPinAdapter2 = new ShangPinAdapter2(allData, context, sp.getString(MyRes.MY_TOKEN, ""));
                        lv2.setAdapter(shangPinAdapter2);

                        if (shangPinAdapter2 != null) {
                            shangPinAdapter2.setOnClicked(new ShangPinAdapter2.OnClicked() {
                                @Override
                                public void getIntent(int pos, String goodsId) {
                                    Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                                    intent.putExtra("token", goodsId);
                                    startActivityForResult(intent, 100);
                                }
                            });
                        }
                    } else {
                        shangPinAdapter2.notifyDataSetChanged();
                        lv2.onRefreshComplete();
                    }
                } catch (JsonSyntaxException e) {
                    ShowToast.showToast("数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
                lv2.onRefreshComplete();
            }

            @Override
            public void onFinish(int what) {
                lv2.onRefreshComplete();
                pb.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){

            if (requestCode==100)
            {
                List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> backData = (List<ShangPinXQ1DaiPingLun.DataBean.SpecBean>)data.getSerializableExtra("goodsName");
                if (backData==null){
                    return;
                }
                ShangPinXQ1DaiPingLun.DataBean.SpecBean specBean = backData.get(0);
                String goods_id = specBean.getGoods_id();
                shangPinAdapter2.refish(backData);
            }

        }


    }
}