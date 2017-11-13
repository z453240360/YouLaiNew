package com.bm.chengshiyoutian.youlaiwang.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.DianPuShouYeActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.KuaiSuXiaDanActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.LoginActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.QuYuActivityNew;
import com.bm.chengshiyoutian.youlaiwang.activity.SearchActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.XiTongTiXingActivity;
import com.bm.chengshiyoutian.youlaiwang.adapter.FenLeiGVAdapter;
import com.bm.chengshiyoutian.youlaiwang.adapter.GongYingShangLVAdapter;
import com.bm.chengshiyoutian.youlaiwang.adapter.GongYingShangQuYuAdapter1;
import com.bm.chengshiyoutian.youlaiwang.adapter.GongYingShangQuYuAdapter2;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuXiangQingBean;
import com.bm.chengshiyoutian.youlaiwang.bean.DingWeiBean;
import com.bm.chengshiyoutian.youlaiwang.bean.FenleiBean;
import com.bm.chengshiyoutian.youlaiwang.bean.GongYingShangQuYuBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jauker.widget.BadgeView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.R.id.city_name;
import static com.bm.chengshiyoutian.youlaiwang.Utils.MyRes.DIANPU_ID;


public class GongYingShangFragment extends Fragment implements View.OnClickListener, AMapLocationListener {


    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private GridView gv;
    private PullToRefreshListView lv;
    //一级父ID
    String P1ID = "";
    //二级父ID
    String P2ID = "";
    private ProgressBar pb;
    private LinearLayout ll;
    private ImageView iv_emessage;
    private ProgressDialog mProgressDialog;
    private LinearLayout mLl_quyu;
    private TextView mCity_name;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private BadgeView badgeView1;
    private TextView mViewById;
    private View mView;
    private ArrayList<String> strings;
    private ArrayList<String> ints;
    private ArrayList<Boolean> tags;
    private SharedPreferences sp;
    private boolean bb = false;
    //区域选择的左边的listview
    private ListView lv1;
    //区域选择的右边边的listview
    private ListView lv2;
    private PopupWindow popupWindow;

    private String area_name;
    private String CityID;
    private List<DianPuXiangQingBean.DataBeanX.DataBean> data;
    private List<DianPuXiangQingBean.DataBeanX.DataBean> alldata = new ArrayList<>();
    private GongYingShangLVAdapter gongYingShangLVAdapter;
    private FenLeiGVAdapter fenLeiGVAdapter;
    private int page = 1;
    private String weidu;
    private String jingdu;
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

    public static GongYingShangFragment newInstance(String param1) {
        GongYingShangFragment fragment = new GongYingShangFragment();
        return fragment;
    }

    public GongYingShangFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_gongyingshang, container, false);
        mViewById = (TextView) mView.findViewById(R.id.iv_dian);
        sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);
        getLoction();
        page = 1;
        initView(mView);
        badgeView1 = new BadgeView(getActivity());
        if (alldata != null) {
            alldata.clear();
        }

        if (gongYingShangLVAdapter != null) {
            gongYingShangLVAdapter = null;
        }
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDatas();
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

                    //  badgeView1.setTextSize(getContext().getResources().getDimension(R.dimen.sp4));
                } catch (JsonSyntaxException e) {
                    /*ShowToast.showToast("数据异常");*/
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

    @Override
    public void onPause() {
        super.onPause();
        P1ID = "";
    }

    private void initData(View view) {

        gv = (GridView) view.findViewById(R.id.gv);
        strings = new ArrayList<>();
        ints = new ArrayList<>();
        tags = new ArrayList<>();


        //店铺详情数据
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/class");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));

        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                if (mProgressDialog != null) {
                    mProgressDialog.show();
                } else {

                }
            }

            @Override
            public void onSucceed(int what, Response response) {
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

                fenLeiGVAdapter = new
                        FenLeiGVAdapter(ints, strings, tags, getActivity());
                gv.setAdapter(fenLeiGVAdapter);

                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        alldata.clear();
                        P1ID = fenleiBean.getData().get(position).getGc_id() + "";

                        bb = tags.get(position);
                        if (bb) {
                            getDianPuDatas("", 1 + "", 0 + "", 0 + "", 1 + "", sp.getString(MyRes.area_id, ""));
                            P1ID = "";
                        } else {
                            getDianPuDatas(P1ID + "", 1 + "", 0 + "", 0 + "", 1 + "", sp.getString(MyRes.area_id, ""));
                        }

                        tv1.setTextColor(Color.rgb(118, 201, 51));
                        tv2.setTextColor(0xff222222);
                        tv3.setTextColor(0xff222222);
                        tv4.setTextColor(0xff222222);

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
                                //    myHandler.sendEmptyMessage(1);
                                myHandler.sendMessage(message);
                            }
                        }.start();


                    }
                });
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                } else {

                }
                //pb.setVisibility(View.GONE);
            }
        });

        /**
         * @param classId 一级父类
         * @param state   2级父类
         * @param lat     维度
         * @param lng     经度
         * @param page    页码
         */   //所在城市ID

        getDianPuDatas("", "", sp.getString(MyRes.WEIDU, ""), sp.getString(MyRes.JINGDU, ""), 1 + "", sp.getString(MyRes.area_id, ""));

    }

    private void getQuYuDatas() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/area", RequestMethod.GET);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                if (mProgressDialog.isShowing()) {

                } else {
                    mProgressDialog.show();
                }
            }

            @Override
            public void onSucceed(int what, Response response) {
                SharedPreferences sp = getContext().getSharedPreferences(MyRes.CONFIG, 0);
                final String s1 = sp.getString(MyRes.city1, "");
                final String s2 = sp.getString(MyRes.city2, "");
                String json = (String) response.get();
                try {
                    GongYingShangQuYuBean gongYingShangQuYuBean = GsonUtils.getInstance().fromJson(json, GongYingShangQuYuBean.class);
                    final List<GongYingShangQuYuBean.DataBean> data = gongYingShangQuYuBean.getData();
                    final GongYingShangQuYuAdapter1 gongYingShangQuYuAdapter1 = new GongYingShangQuYuAdapter1(data, getActivity());

                    lv1.setAdapter(gongYingShangQuYuAdapter1);
                    int num1 = 0;
                    for (int a = 0; a < data.size(); a++) {
                        if (data.get(a).getArea_name().equals(s1)) {
                            lv1.setSelection(a);
                            data.get(a).tag = true;
                            num1 = a;
                        } else {
                            if (s1.equals("")) {
                                num1 = 0;
                            }
                        }
                    }

                    Request<String> stringRequest1 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/area", RequestMethod.GET);
                    stringRequest1.add("parentId", data.get(num1).getArea_id());
                    CallServer.getInstance().add(11, stringRequest1, new OnResponseListener() {
                        @Override
                        public void onStart(int what) {
                            if (mProgressDialog.isShowing()) {

                            } else {
                                mProgressDialog.show();
                            }
                        }

                        @Override
                        public void onSucceed(int what, Response response) {
                            String json = (String) response.get();
                            final List<GongYingShangQuYuBean.DataBean> data1 = GsonUtils.getInstance().fromJson((String) response.get(), GongYingShangQuYuBean.class).getData();
                            lv2.setAdapter(new GongYingShangQuYuAdapter2(data1, getActivity()));
                            for (int a = 0; a < data1.size(); a++) {
                                if (data1.get(a).getArea_name().equals(s2)) {
                                    lv2.setSelection(a);
                                    data1.get(a).tag = true;
                                }
                            }
                            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    CityID = data1.get(position).getArea_id() + "";
                                    alldata.clear();
                                    getDianPuDatas(P1ID + "", P2ID + "", 0 + "", 0 + "", 1 + "", CityID + "");
                                    popupWindow.dismiss();
                                }
                            });
                        }

                        @Override
                        public void onFailed(int what, Response response) {
                            ShowToast.showToast("联网失败");
                        }

                        @Override
                        public void onFinish(int what) {
                            if (mProgressDialog != null) {
                                mProgressDialog.dismiss();
                            } else {

                            }
                            // pb.setVisibility(View.GONE);
                        }
                    });
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int i = 0; i < data.size(); i++) {
                                data.get(i).tag = false;

                            }
                            data.get(position).tag = true;
                            gongYingShangQuYuAdapter1.notifyDataSetChanged();
                            data.get(position).getArea_id();
                            Request<String> stringRequest1 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/area");
                            stringRequest1.add("parentId", data.get(position).getArea_id());
                            CallServer.getInstance().add(11, stringRequest1, new OnResponseListener() {
                                @Override
                                public void onStart(int what) {
                                    // pb.setVisibility(View.VISIBLE);
                                    mProgressDialog.show();
                                }

                                @Override
                                public void onSucceed(int what, Response response) {
                                    final List<GongYingShangQuYuBean.DataBean> data1 = GsonUtils.getInstance().fromJson((String) response.get(), GongYingShangQuYuBean.class).getData();
                                    lv2.setAdapter(new GongYingShangQuYuAdapter2(data1, getActivity()));
                                    lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            CityID = data1.get(position).getArea_id() + "";
                                            getDianPuDatas(P1ID + "", 3 + "", 0 + "", 0 + "", 1 + "", CityID + "");
                                            popupWindow.dismiss();
                                        }
                                    });
                                }

                                @Override
                                public void onFailed(int what, Response response) {
                                    ShowToast.showToast("联网失败");
                                }

                                @Override
                                public void onFinish(int what) {
                                    if (mProgressDialog != null) {
                                        mProgressDialog.dismiss();
                                    }

                                }
                            });
                        }
                    });

                } catch (JsonSyntaxException e) {
                    ShowToast.showToast("区域数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 所在城市ID
     *
     * @param classId 一级父类
     * @param state   2级父类
     * @param lat     维度
     * @param lng     经度
     * @param page    页码
     */

    private void getDianPuDatas(String classId, String state, String lat, String lng, String page, String cityid) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/store", RequestMethod.GET);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("classId", classId);
        stringRequest.add("state", state);
        stringRequest.add("areaId", sp.getString(MyRes.area_id, ""));
        stringRequest.add("lat", lat);
        stringRequest.add("lng", lng);
        stringRequest.add("page", page);
        stringRequest.add("cityId", cityid);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                if (mProgressDialog != null) {
                    mProgressDialog.show();
                } else {

                }
                // pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                String json = (String) response.get();
                try {

                    data = GsonUtils.getInstance().fromJson(json, DianPuXiangQingBean.class).getData().getData();
                    alldata.clear();
                    alldata.addAll(data);
                    //   ShowToast.showToast(alldata.size() + "");
                    if (gongYingShangLVAdapter == null) {
                        gongYingShangLVAdapter = new GongYingShangLVAdapter(alldata, getActivity());
                        lv.setAdapter(gongYingShangLVAdapter);

                    } else {
                        gongYingShangLVAdapter.notifyDataSetChanged();
                    }

                } catch (JsonSyntaxException e) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getInt("code") == 300) {
                            String msg = jsonObject.getString("msg");
                            ShowToast.showToast(msg);
                        }

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
//                    ShowToast.showToast("店铺数据异常");

                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                } else {

                }
                //pb.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 所在城市ID
     *
     * @param classId 一级父类
     * @param state   2级父类
     * @param lat     维度
     * @param lng     经度
     * @param page    页码
     */
    private void getDianPuDatas1(String classId, String state, String lat, String lng, final String page, String cityid) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/store");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("classId", classId);
        stringRequest.add("state", state);
        stringRequest.add("areaId", sp.getString(MyRes.area_id, ""));
        stringRequest.add("lat", lat);
        stringRequest.add("lng", lng);
        stringRequest.add("page", page);
        stringRequest.add("cityId", cityid);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                if (mProgressDialog != null) {
                    mProgressDialog.show();
                } else {

                }
            }

            @Override
            public void onSucceed(int what, Response response) {
                String json = (String) response.get();
                try {

                    data.clear();
                    data = GsonUtils.getInstance().fromJson(json, DianPuXiangQingBean.class).getData().getData();
                    if (data.size()==0){
                        ShowToast.showToast("没有更多数据啦");
                        return;
                    }
                    alldata.addAll(data);
                    if (gongYingShangLVAdapter == null) {
                        gongYingShangLVAdapter = new GongYingShangLVAdapter(alldata, getActivity());
                        lv.setAdapter(gongYingShangLVAdapter);
                    } else {

                        gongYingShangLVAdapter.notifyDataSetChanged();
                    }


                } catch (JsonSyntaxException e) {
                    ShowToast.showToast("店铺数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                } else {

                }
            }
        });

    }

    private void initView(View view) {

        mLl_quyu = (LinearLayout) view.findViewById(R.id.ll_quyu);
        mLl_quyu.setOnClickListener(this);
        mCity_name = (TextView) view.findViewById(R.id.city_name);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv1.setTextColor(0xff0bb04a);
        tv1.setOnClickListener(this);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv2.setOnClickListener(this);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv3.setOnClickListener(this);
        tv4 = (TextView) view.findViewById(R.id.tv4);
        tv4.setOnClickListener(this);

        lv = (PullToRefreshListView) view.findViewById(R.id.lv);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                alldata.clear();
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                new GetDataTask().execute();
            }
        });


        //跳转到供应商首页面
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), KuaiSuXiaDanActivity.class);
                intent.putExtra(MyRes.DIANPU_ID, alldata.get(position - 1).getStore_id() + "");
                intent.putExtra("token", getActivity().getIntent().getStringExtra("token"));
                sp.edit().putString(DIANPU_ID, alldata.get(position - 1).getStore_id() + "").commit();
                startActivity(intent);
            }
        });
        pb = (ProgressBar) view.findViewById(R.id.pb);
        mProgressDialog = new ProgressDialog(getActivity());
        pb.setOnClickListener(this);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        ll.setOnClickListener(this);
        iv_emessage = (ImageView) view.findViewById(R.id.iv_emessage);
        iv_emessage.setOnClickListener(this);
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getDianPuDatas1(P1ID + "", P2ID + "", weidu + "", jingdu + "", page + "", sp.getString(MyRes.area_id, ""));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            lv.onRefreshComplete();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_quyu:
                Intent intents = new Intent(getActivity(), QuYuActivityNew.class);
                startActivity(intents);
                break;
            case R.id.tv1://推荐
                page = 1;
                P2ID = 1 + "";
                alldata.clear();
                tv1.setTextColor(Color.rgb(118, 201, 51));
                tv2.setTextColor(0xff222222);
                tv3.setTextColor(0xff222222);
                tv4.setTextColor(0xff222222);
                getDianPuDatas(P1ID + "", P2ID + "", 0 + "", 0 + "", 1 + "", sp.getString(MyRes.area_id, ""));

                break;
            case R.id.ll:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv2://附近
                page = 1;
                alldata.clear();
                P2ID = 2 + "";
                weidu = sp.getString(MyRes.WEIDU, "0");
                jingdu = sp.getString(MyRes.JINGDU, "0");
                getDianPuDatas(P1ID + "", 2 + "", weidu + "", jingdu + "", 1 + "", sp.getString(MyRes.area_id, ""));
                tv2.setTextColor(Color.rgb(118, 201, 51));
                tv1.setTextColor(0xff222222);
                tv3.setTextColor(0xff222222);
                tv4.setTextColor(0xff222222);
                break;
            case R.id.iv_emessage:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(getActivity(), Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), XiTongTiXingActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.tv3://区域
                page = 1;
                P2ID = 3 + "";
                tv3.setTextColor(Color.rgb(118, 201, 51));
                tv1.setTextColor(0xff222222);
                tv2.setTextColor(0xff222222);
                tv4.setTextColor(0xff222222);

                View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popuwindow_quyu, null);

                lv1 = (ListView) contentView.findViewById(R.id.lv1);
                lv2 = (ListView) contentView.findViewById(R.id.lv2);

                popupWindow = new PopupWindow(getActivity());
                popupWindow.setBackgroundDrawable(new ColorDrawable(0));
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();
                popupWindow.setContentView(contentView);
                popupWindow.setWidth(width);
                popupWindow.setHeight(height * 3 / 5);
                popupWindow.setFocusable(true);
                // 实例化一个ColorDrawable颜色为半透明
                ColorDrawable dw = new ColorDrawable(0xb0000000);
                // 设置弹出窗体的背景
                popupWindow.setBackgroundDrawable(dw);
                WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
                //当弹出Popupwindow时，背景变半透明
                attributes.alpha = 0.7f;

                popupWindow.showAsDropDown(v);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                        params.alpha = 1f;
                        getActivity().getWindow().setAttributes(params);
                    }
                });

                getQuYuDatas();
                break;
            case R.id.tv4://常用
                page = 1;
                P2ID = 4 + "";
                tv4.setTextColor(Color.rgb(118, 201, 51));
                tv1.setTextColor(0xff222222);
                tv2.setTextColor(0xff222222);
                tv3.setTextColor(0xff222222);
                alldata.clear();
                getDianPuDatas(P1ID + "", P2ID + "", 0 + "", 0 + "", 1 + "", sp.getString(MyRes.area_id, ""));
                break;
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

    @Override
    public void onStart() {
        super.onStart();
        String kong = sp.getString(MyRes.city2, "");
        mCity_name.setText(kong + "");
        initData(mView);
    }
}