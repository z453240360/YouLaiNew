package com.bm.chengshiyoutian.youlaiwang.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.DianPuShouYeRCAdapter;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinRCAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuShouYeBean;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBeanNum;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinFenLeiBena;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinYouBianBean;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinZuoBianBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.ShoppingCar.ShoppingAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ShoppingCarBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.Utils.MyRes.DIANPU_ID;


public class KuaiSuXiaDanActivity extends AppCompatActivity implements View.OnClickListener {

    //一级父类ID
    String p1ID;
    //二级父类ID
    String p2ID;
    //三级父类Id
    String p3ID;
    //页码
    int page = 1;
    private LinearLayout ll;
    private TextView tv_name;
    private LinearLayout ll1;
    private TextView tv1;
    private TextView tv2;

    private LinearLayout ll_bg,ll_shoucang;
    private ImageView sign,iv_shoucang;
    private TextView store_name,company_license_num;

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;

    //价格是升还是降
    private boolean tag;
    private String tag1;
    private Intent intent;

    private RecyclerView rc;
    private ImageView paixu;
    public static ProgressBar pb;
    private Context context;
    private TabLayout tabs;
    //lv2每次获得的数据源
    private List<ShangPinYouBianBean.DataBeanX.DataBean> data_lv2 = new ArrayList<>();

    private String head;
    private ImageView iv_finish;
    private TextView tv_shangpinliebiao;
    private TextView tv_jilu;
    private TextView iv_gouwuche;
    private ImageView iv_dianhua;
    private XRecyclerView xr;



    private ShoppingAdapter adapter3;
    private List<ShoppingCarBean.DataBeanX.DataBean> allData = new ArrayList<>();
    private String token;
    private String areaId;
    private LinearLayoutManager manager;



    private SharedPreferences sp;
    private LinearLayout ll_sou,isHasData;
    private ImageView iv_emessage;
    private RelativeLayout miv_gouwuche1;
    private List<ShangPinZuoBianBean.DataBean> data;
    private DianPuShouYeBean dianPuShouYeBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_kuaisuxiadan);


        context = this;
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        String string = sp.getString(MyRes.MY_TOKEN, "1");
        head = string;
        token = string;
        initView();


        //商品详情
        manager = new LinearLayoutManager(this);
        adapter3 = new ShoppingAdapter(this, allData);
        xr.setLayoutManager(manager);
        xr.setAdapter(adapter3);

        //商品列表设置加载更多
        xr.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                allData.clear();
                page = 1;
                adapter3.notifyDataSetChanged();
                getrefreshData();
                xr.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                getrefreshData();
                xr.loadMoreComplete();
            }
        });


        //商品列表的点击跳转详情事件
        adapter3.setClicked(new ShoppingAdapter.OnItemClicked() {
            @Override
            public void getDetial(int goods_id) {
                Intent intent = new Intent(KuaiSuXiaDanActivity.this, ShangPinXiangQingActivity.class);
                intent.putExtra("token", goods_id+"");
                startActivityForResult(intent, 100);//请求吗100
            }

            @Override
            public void addClicked() {
                getCarNum();
            }

            @Override
            public void reduceClicker() {
                getCarNum();
            }
        });


        getPData();

        getShouYeDatas();
    }

    private void getPData() {
        String stringExtra = sp.getString(MyRes.DIANPU_ID, "");
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/store/" + stringExtra + "/class");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                String s = (String) response.get();
                try {
                    ShangPinFenLeiBena shangPinFenLeiBena = GsonUtils.getInstance().fromJson(s, ShangPinFenLeiBena.class);
                    List<ShangPinFenLeiBena.DataBean> data = shangPinFenLeiBena.getData();


                    for (int i = 0; i < data.size(); i++) {
                        tabs.addTab(tabs.newTab().setText(data.get(i).getGc_name()).setTag(data.get(i).getGc_id() + ""));

                    }
                    //一级父ID
                    p1ID = data.get(0).getGc_id() + "";
                    //二级父ID
                    p2ID = data.get(0).getGoods_class_two().get(0).getGc_id() + "";
                    getData(p1ID);

                    tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            page = 1;
                            p1ID = (String) tab.getTag();
                            p2ID = "";
                            p3ID = "";
                            getData(p1ID);
                            allData.clear();
                            adapter3.notifyDataSetChanged();
                        }


                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });


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

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        getCarNum();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> backData = (List<ShangPinXQ1DaiPingLun.DataBean.SpecBean>) data.getSerializableExtra("goodsName");
                adapter3.refish(backData);
            }
        }
    }

    private void getCarNum() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/total");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    int cartNum = GsonUtils.getInstance().fromJson((String) response.get(), GouWuCheBeanNum.class).getData().getCartNum();
                    iv_gouwuche.setText(cartNum + "");
                } catch (JsonSyntaxException e) {
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        page = 1;
        if(data != null){
            data.clear();
        }
        data_lv2.clear();
        allData.clear();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        page = 1;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {

        if (message.equals("jia")) {
            getCarNum();
        }
        if (message.equals("jian")) {

            getCarNum();
        }
    }

    //二级分类
    private void getData(String id) {
        String stringExtra = sp.getString(MyRes.DIANPU_ID, "");
        final Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/store/" + stringExtra + "/classTwo");
        stringRequest.add("pid", id);   stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinZuoBianBean.class).getData();
                    p2ID = data.get(0).getGc_id() + "";
                    getrefreshData();
                    tv_name.setText(data.get(0).getGc_name());
                    // 创建一个线性布局管理器
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    // 设置布局管理器
                    rc.setLayoutManager(layoutManager);
                    layoutManager.setOrientation(OrientationHelper.VERTICAL);

                    // 创建Adapter，并指定数据集
                    final ShangPinRCAdapter adapter = new ShangPinRCAdapter(context, data);
                    data.get(0).tag = true;

                    // 设置Adapter
                    rc.setAdapter(adapter);

                    adapter.setOnItemClickListener(new ShangPinRCAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            page = 1;
                            tv_name.setText(data.get(position).getGc_name());
                            for (int j = 0; j < data.size(); j++) {
                                data.get(j).tag = false;
                            }

                            allData.clear();
                            adapter3.notifyDataSetChanged();
                            data.get(position).tag = true;
                            adapter.notifyDataSetChanged();
                            //当点击的时候  获得右边的数据
                            p2ID = data.get(position).getGc_id() + "";
                            getrefreshData();

                        }
                    });

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


    }

    private void initView() {
        store_name = (TextView) findViewById(R.id.store_name);
        company_license_num = (TextView) findViewById(R.id.company_license_num);
        xr = (XRecyclerView)findViewById(R.id.xr);


        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setOnClickListener(this);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setOnClickListener(this);
        sign = (ImageView) findViewById(R.id.sign);
        sign.setOnClickListener(this);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv1.setOnClickListener(this);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv2.setOnClickListener(this);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv3.setOnClickListener(this);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv4.setOnClickListener(this);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv5.setOnClickListener(this);





        iv_shoucang = (ImageView) findViewById(R.id.iv_shoucang);
        iv_shoucang.setOnClickListener(this);

        ll_shoucang = (LinearLayout) findViewById(R.id.ll_shoucang);
        ll_shoucang.setOnClickListener(this);

        ll_bg = (LinearLayout) findViewById(R.id.ll_bg);
        ll_bg.setOnClickListener(this);
        sign = (ImageView) findViewById(R.id.sign);
        sign.setOnClickListener(this);
        rc = (RecyclerView) findViewById(R.id.rc);
        paixu = (ImageView) findViewById(R.id.paixu);
        pb = (ProgressBar) findViewById(R.id.pb);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll = (LinearLayout) findViewById(R.id.ll);
        isHasData = (LinearLayout) findViewById(R.id.isHasData);



        ViewGroup.LayoutParams layoutParams = ll.getLayoutParams();
        tv_name = (TextView) findViewById(R.id.tv_name);
        ll.setLayoutParams(layoutParams);
        ll1.setLayoutParams(layoutParams);



        tabs = (TabLayout) findViewById(R.id.tab);

        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        tv_shangpinliebiao = (TextView) findViewById(R.id.tv_shangpinliebiao);
        tv_shangpinliebiao.setOnClickListener(this);
        tv_jilu = (TextView) findViewById(R.id.tv_jilu);
        tv_jilu.setOnClickListener(this);
        iv_gouwuche = (TextView) findViewById(R.id.iv_gouwuche);
        miv_gouwuche1 = (RelativeLayout) findViewById(R.id.iv_gouwuche1);
        miv_gouwuche1.setOnClickListener(this);
        iv_dianhua = (ImageView) findViewById(R.id.iv_dianhua);
        iv_dianhua.setOnClickListener(this);
        ll_sou = (LinearLayout) findViewById(R.id.ll_sou);
        ll_sou.setOnClickListener(this);
        iv_emessage = (ImageView) findViewById(R.id.iv_emessage);
        iv_emessage.setOnClickListener(this);
    }

    //获取首页的banner,和底部的基本信息
    private void getShouYeDatas() {
        try {
            String stringExtra = getIntent().getStringExtra(DIANPU_ID);
            Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/store/" + stringExtra + "/index"/*,RequestMethod.POST*/);
            String string = sp.getString(MyRes.MY_TOKEN, "");
            stringRequest.addHeader("Authorization", string);
            CallServer.getInstance().add(1, stringRequest, new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {
                }
                @Override
                public void onSucceed(int what, Response<String> response) {
                    try {
                        dianPuShouYeBean = GsonUtils.getInstance().fromJson(response.get(), DianPuShouYeBean.class);
                        tag1 = dianPuShouYeBean.getData().getStore().getIs_favorites() + "";
                        //    banner1 = dianPuShouYeBean.getData().getBanner();

                        ArrayList<String> goods_images = new ArrayList<String>();

                        //轮播图数据
                        final List<DianPuShouYeBean.DataBean.BannerBean> banners = dianPuShouYeBean.getData().getBanner();
                        for (int x = 0; x < banners.size(); x++) {
                            String banner_imgsrc = banners.get(x).getBanner_imgsrc();
                            goods_images.add(banner_imgsrc);
                        }


                        iv_gouwuche.setText(dianPuShouYeBean.getData().getCart_num() + "");

                        int evaluate_total = dianPuShouYeBean.getData().getStore().getEvaluate_total();
                        switch (evaluate_total) {
                            case 1:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.INVISIBLE);
                                iv3.setVisibility(View.INVISIBLE);
                                iv4.setVisibility(View.INVISIBLE);
                                iv5.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.INVISIBLE);
                                iv4.setVisibility(View.INVISIBLE);
                                iv5.setVisibility(View.INVISIBLE);
                                break;
                            case 3:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                iv4.setVisibility(View.INVISIBLE);
                                iv5.setVisibility(View.INVISIBLE);
                                break;
                            case 4:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                iv4.setVisibility(View.VISIBLE);
                                iv5.setVisibility(View.INVISIBLE);
                                break;
                            case 5:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                iv4.setVisibility(View.VISIBLE);
                                iv5.setVisibility(View.VISIBLE);
                                break;
                        }
                        if ((dianPuShouYeBean.getData().getStore().getIs_favorites() + "").equals("1")) {
                            iv_shoucang.setImageResource(R.drawable.shoucang_004);

                        } else {

                            iv_shoucang.setImageResource(R.drawable.shouye_shoucang);

                        }

                        try {
                            Glide.with(KuaiSuXiaDanActivity.this).load(dianPuShouYeBean.getData().getStore().getSign()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Drawable drawable = new BitmapDrawable(resource);

                                    ll_bg.setBackgroundDrawable(drawable);
                                }
                            });
                            Glide.with(KuaiSuXiaDanActivity.this)
                                    .load(dianPuShouYeBean.getData().getStore().getStore_logo())
                                    .into(KuaiSuXiaDanActivity.this.sign);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        List<DianPuShouYeBean.DataBean.NewGoodsBean> newGoods = dianPuShouYeBean.getData().getNewGoods();
                        // 创建Adapter，并指定数据集
                        DianPuShouYeRCAdapter adapter = new DianPuShouYeRCAdapter(getWindowManager().getDefaultDisplay().getWidth(), KuaiSuXiaDanActivity.this, newGoods);
                        // 设置Adapter
                        rc.setAdapter(adapter);


                        company_license_num.setText("已认证" + dianPuShouYeBean.getData().getStore().getCompany_license_num() + "证");

                        store_name.setText(dianPuShouYeBean.getData().getStore().getStore_name());


                    } catch (JsonSyntaxException e) {
                        ShowToast.showToast("数据异常");
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    ShowToast.showToast("联网失败");
                }

                @Override
                public void onFinish(int what) {

                }
            });
        } catch (Exception e) {
            ShowToast.showToast("店铺数据异常");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:
                finish();
                break;
            case R.id.ll_sou:
                Intent intent2 = new Intent(this, SearchActivity.class);
                startActivity(intent2);
                break;

            case R.id.iv_emessage:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(this, Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);

                } else {
                    intent = new Intent(this, XiTongTiXingActivity.class);
                    startActivity(intent);
                }


                break;
            case R.id.tv1:
                page = 1;
                tv1.setTextColor(Color.rgb(118,201,51));
                tv2.setTextColor(0xff333333);

                allData.clear();
                adapter3.notifyDataSetChanged();
                p3ID = 3 + "";
                getrefreshData();

                break;

            case R.id.tv2:
                page = 1;
                p3ID = 5 + "";
                tv2.setTextColor(Color.rgb(118,201,51));

                allData.clear();
                adapter3.notifyDataSetChanged();
                tv1.setTextColor(0xff333333);
                getrefreshData();


                break;
            case R.id.tv_shangpinliebiao:

                Intent intent1 = new Intent(this, ShangPinLieBiaoAcitivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_jilu:

                if (sp.getString(MyRes.MY_TOKEN, "1").equals("kong")||sp.getString(MyRes.MY_TOKEN, "1").equals("")) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(KuaiSuXiaDanActivity.this, Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    Intent intent6 = new Intent(this, GouMaiJiLuActivity.class);
                    startActivity(intent6);
                }

                break;
            case R.id.iv_gouwuche1:

                if (sp.getString(MyRes.MY_TOKEN, "1").equals("kong")) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(KuaiSuXiaDanActivity.this
                            , Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, GouWuCheActivity.class);
                    startActivity(intent);

                }

                break;
            case R.id.iv_dianhua:
                Intent intent4 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + sp.getString(MyRes.PHONE, "")));

                startActivity(intent4);
                break;



            case R.id.sign:
                String DIANPU_ID1 = getIntent().getStringExtra(DIANPU_ID);

                intent = new Intent(this, DianPuXiangQingActivity.class);
                intent.putExtra("data", "已认证" + dianPuShouYeBean.getData().getStore().getCompany_license_num() + "证");
                intent.putExtra(DIANPU_ID, DIANPU_ID1);
                startActivity(intent);
                break;

            case R.id.ll_shoucang:
                if (dianPuShouYeBean != null) {

                    if (tag1.equals("2")) {
                        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/store", RequestMethod.POST);
                        if (sp.getString(MyRes.MY_TOKEN, "1").equals("1")) {
                            ShowToast.showToast("请登录");
                            intent = new Intent(KuaiSuXiaDanActivity.this
                                    , Login_ddActivity.class);
                            intent.putExtra("tag", "two");
                            startActivity(intent);
                        } else {
                            stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, "1"));
                            stringRequest.add("type", 2);
                            stringRequest.add("foreign_id", dianPuShouYeBean.getData().getStore().getStore_id());
                            CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
                                @Override
                                public void onStart(int what) {

                                }

                                @Override
                                public void onSucceed(int what, Response response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject((String) response.get());
                                        String msg = jsonObject.getString("msg");
                                        ShowToast.showToast(msg);
                                        String code = jsonObject.getString("code");

                                        if (code.equals("200")) {
                                            iv_shoucang.setImageResource(R.drawable.shoucang_004);
                                            tag1 = "1";

                                        }
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

                    } else {
                        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/destroy", RequestMethod.POST);

                        if (sp.getString(MyRes.MY_TOKEN, "1").equals("1")) {
                            ShowToast.showToast("请登录");
                            intent = new Intent(KuaiSuXiaDanActivity.this
                                    , Login_ddActivity.class);
                            intent.putExtra("tag", "two");
                            startActivity(intent);
                        } else {
                            stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, "1"));
                            stringRequest.add("type", 2);
                            stringRequest.add("_method", "delete");
                            stringRequest.add("foreign_ids", dianPuShouYeBean.getData().getStore().getStore_id());


                            CallServer.getInstance().add(3213, stringRequest, new OnResponseListener() {
                                @Override
                                public void onStart(int what) {

                                }

                                @Override
                                public void onSucceed(int what, Response response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject((String) response.get());
                                        String msg = jsonObject.getString("msg");
                                        ShowToast.showToast(msg);
                                        String code = jsonObject.getString("code");


                                        if (code.equals("200")) {

                                            iv_shoucang.setImageResource(R.drawable.shouye_shoucang);
                                            tag1 = "2";

                                        }
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


                    }


                }


                break;
        }
    }

    private void getrefreshData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("classOneId", p1ID);
        stringRequest.add("classId", p2ID);
        stringRequest.add("state", p3ID);
        stringRequest.add("page", page);
        stringRequest.add("storeId",sp.getString(MyRes.DIANPU_ID,""));
        stringRequest.add("storeId", getIntent().getStringExtra(MyRes.DIANPU_ID));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response response) {
                if (response.responseCode() != 200) {
                    ShowToast.showToast("服务器异常：" + response.responseCode());
                    return;
                }
                Gson gson = new Gson();
                ShoppingCarBean shoppingCarBean = gson.fromJson(response.get().toString(), ShoppingCarBean.class);
                int code = shoppingCarBean.getCode();
                if (code != 200) {
                    Toast.makeText(KuaiSuXiaDanActivity.this, "" + shoppingCarBean.getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<ShoppingCarBean.DataBeanX.DataBean> data = shoppingCarBean.getData().getData();

                if (page == 1) {
                    if (data.size() == 0) {
                        isHasData.setVisibility(View.VISIBLE);
                        xr.setVisibility(View.GONE);
                        return;
                    } else {
                        isHasData.setVisibility(View.GONE);
                        xr.setVisibility(View.VISIBLE);
                    }
                } else {
                    isHasData.setVisibility(View.GONE);
                    xr.setVisibility(View.VISIBLE);
                    if (data.size() == 0) {
                        ShowToast.showToast("已经显示全部");
                        return;
                    }
                }

                allData.addAll(data);
                adapter3.notifyDataSetChanged();
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
}