package com.bm.chengshiyoutian.youlaiwang.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GlideImageLoader2;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.DianPuChuXiaoAdapter;
import com.bm.chengshiyoutian.youlaiwang.adapter.DianPuShouYeRCAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuShouYeBean;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuShouYeCuXiaoBean;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBeanNum;
import com.bm.chengshiyoutian.youlaiwang.view.HeaderGridView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.Utils.MyRes.DIANPU_ID;
import static com.bm.chengshiyoutian.youlaiwang.app.MyApplication.getContext;


/**
 * Created by sld on 2017/5/19.
 */

public class DianPuShouYeActivity extends Activity implements View.OnClickListener {
    private HeaderGridView gv;


    private ImageView sign;
    private TextView store_name;
    private TextView company_license_num;

    //商品列表
    TextView tv_shangpinliebiao;
    private RecyclerView rc;
    private TextView tv_xiadan;
    private LinearLayout ll_bg;
    private LinearLayout ll;
    private LinearLayout myll;
    private TextView iv_gouwuche;
    private ImageView iv_dianhua;
    private LinearLayout ll_shoucang;
    private ImageView iv_shoucang;
    SharedPreferences sp;
    private ImageView iv_finish;
    private TextView tv_jilu;
    private LinearLayout ll_sou;
    private TextView tv_1;
    private TextView tv_2;
    private LinearLayout ll1;
    private LinearLayout ll2;

    private ImageView iv_emessage;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private LinearLayout ll_tag1;
    private LinearLayout ll_tag2;
    private RelativeLayout ll_go;
    Activity context;
    private LinearLayout ll_yanse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_dianpushouye);
        context = this;
        initView();
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        sp.edit().putString(DIANPU_ID, getIntent().getStringExtra(DIANPU_ID)).commit();




        boolean b = MyUtils.checkDeviceHasNavigationBar(this);
        if (b) {
            int virtualBarHeigh = MyUtils.getVirtualBarHeigh(this);
            ViewGroup.LayoutParams layoutParams = myll.getLayoutParams();
            layoutParams.height = virtualBarHeigh;
            myll.setLayoutParams(layoutParams);


        }


        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
        allData.clear();
        page = 1;
        dianPuChuXiaoAdapter = null;
        getGVDatas();


    }

    DianPuShouYeBean dianPuShouYeBean;


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
                        tag = dianPuShouYeBean.getData().getStore().getIs_favorites() + "";
                        //    banner1 = dianPuShouYeBean.getData().getBanner();

                        ArrayList<String> goods_images = new ArrayList<String>();

                        //轮播图数据
                        final List<DianPuShouYeBean.DataBean.BannerBean> banners = dianPuShouYeBean.getData().getBanner();
                        for (int x = 0; x < banners.size(); x++) {
                            String banner_imgsrc = banners.get(x).getBanner_imgsrc();
                            goods_images.add(banner_imgsrc);
                        }

                        //设置图片加载器
                        DianPuShouYeActivity.this.banner.setImageLoader(new GlideImageLoader2());
                        //设置图片集合
                        DianPuShouYeActivity.this.banner.setImages(goods_images);
                        //banner设置方法全部调用完毕时最后调用

                        DianPuShouYeActivity.this.banner.start();

                        banner.setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {
                                if(banners != null && banners.size() > 0){
                                    int key_id = banners.get(position).getBanner_foreign_id();
                                    if(key_id == 0){

                                    }else {
                                        if (banners.get(position).getBanner_jump_type() == 1) {//商品
                                            Intent intent = new Intent(getContext(), ShangPinXiangQingActivity.class);
                                            intent.putExtra("token", banners.get(position).getBanner_foreign_id() + "");
                                            startActivity(intent);
                                        } else if (banners.get(position).getBanner_jump_type() == 2) {//店铺
                                            Intent intent = new Intent(getContext(), DianPuShouYeActivity.class);
                                            intent.putExtra(MyRes.DIANPU_ID, banners.get(position).getBanner_foreign_id() + "");
                                            startActivity(intent);
                                        }
                                    }

                                }
                            }
                        });

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
                            Glide.with(DianPuShouYeActivity.this).load(dianPuShouYeBean.getData().getStore().getSign()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Drawable drawable = new BitmapDrawable(resource);

                                    ll_bg.setBackgroundDrawable(drawable);
                                }
                            });
                            Glide.with(DianPuShouYeActivity.this)
                                    .load(dianPuShouYeBean.getData().getStore().getStore_logo())
                                    .into(DianPuShouYeActivity.this.sign);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        List<DianPuShouYeBean.DataBean.NewGoodsBean> newGoods = dianPuShouYeBean.getData().getNewGoods();
                        // 创建Adapter，并指定数据集
                        DianPuShouYeRCAdapter adapter = new DianPuShouYeRCAdapter(getWindowManager().getDefaultDisplay().getWidth(), DianPuShouYeActivity.this, newGoods);
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

    Banner banner;

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            page++;
            getGVDatas();
            //    getData1();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            gv.onRefreshComplete();
        }
    }

    int page = 1;
    DianPuChuXiaoAdapter dianPuChuXiaoAdapter;

    private void getGVDatas() {
        String stringExtra = getIntent().getStringExtra(DIANPU_ID);
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/store/" + stringExtra + "/promo");
        stringRequest.add("page", page);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.e("店铺促销商品", response.get());
                try {
                    DianPuShouYeCuXiaoBean dianPuShouYeCuXiaoBean = GsonUtils.getInstance().fromJson(response.get(), DianPuShouYeCuXiaoBean.class);
                    final List<DianPuShouYeCuXiaoBean.DataBeanX.DataBean> data = dianPuShouYeCuXiaoBean.getData().getData();

                    allData.addAll(data);
                    if (dianPuChuXiaoAdapter == null) {
                        dianPuChuXiaoAdapter = new DianPuChuXiaoAdapter(allData, getContext(), getWindowManager().getDefaultDisplay().getWidth());
                        gv.setAdapter(dianPuChuXiaoAdapter);
                    } else {
                        dianPuChuXiaoAdapter.notifyDataSetChanged();
                    }


                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            if (position > 3) {
                                Intent intent = new Intent(DianPuShouYeActivity.this, ShangPinXiangQingActivity.class);

                                intent.putExtra("token", allData.get(position - 4).getGoods_id() + "");
                                startActivity(intent);
                            } else {


                            }
                        }
                    });
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


    }

    private void initView() {
        gv = (HeaderGridView) findViewById(R.id.gv);
        View head1 = View.inflate(DianPuShouYeActivity.this, R.layout.dianpushouye_head1, null);
        View head2 = View.inflate(DianPuShouYeActivity.this, R.layout.dianpushouye_head2, null);

        gv.addHeaderView(head1);
        gv.addHeaderView(head2);

        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount > 4) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        Window window = context.getWindow();
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.rgb(255,168,0));
                        window.setNavigationBarColor(Color.BLACK);
                    }
                    ll_yanse.setBackgroundColor(Color.rgb(255,168,0));
                } else {
                    ll_yanse.setBackgroundColor(0x00000000);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        Window window = context.getWindow();
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(0x00000000);
                        window.setNavigationBarColor(Color.BLACK);
                    }


                }
            }
        });
        gv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        gv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                ShowToast.showToast("1");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                new GetDataTask().execute();
            }
        });

        tv_shangpinliebiao = (TextView) findViewById(R.id.tv_shangpinliebiao);
        tv_shangpinliebiao.setOnClickListener(this);
        sign = (ImageView) head1.findViewById(R.id.sign);
        sign.setOnClickListener(this);
        store_name = (TextView) head1.findViewById(R.id.store_name);
        banner = (Banner) head1.findViewById(R.id.banner);
        company_license_num = (TextView) head1.findViewById(R.id.company_license_num);

        rc = (RecyclerView) head2.findViewById(R.id.rc);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // 设置布局管理器
        rc.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置Adapter
        // 创建数据集


        tv_xiadan = (TextView) findViewById(R.id.tv_xiadan);
        tv_xiadan.setOnClickListener(this);
        ll_bg = (LinearLayout) head1.findViewById(R.id.ll_bg);
        ll_bg.setOnClickListener(this);
        ll = (LinearLayout) head1.findViewById(R.id.ll);

        myll = (LinearLayout) findViewById(R.id.myll);
        myll.setOnClickListener(this);
        iv_gouwuche = (TextView) findViewById(R.id.iv_gouwuche);
        iv_gouwuche.setOnClickListener(this);
        iv_dianhua = (ImageView) findViewById(R.id.iv_dianhua);
        iv_dianhua.setOnClickListener(this);
        ll_shoucang = (LinearLayout) head1.findViewById(R.id.ll_shoucang);
        ll_shoucang.setOnClickListener(this);
        iv_shoucang = (ImageView) head1.findViewById(R.id.iv_shoucang);
        iv_shoucang.setOnClickListener(this);
        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        tv_jilu = (TextView) findViewById(R.id.tv_jilu);
        tv_jilu.setOnClickListener(this);
        ll_sou = (LinearLayout) findViewById(R.id.ll_sou);
        ll_sou.setOnClickListener(this);
        tv_1 = (TextView) head1.findViewById(R.id.tv_1);
        tv_1.setOnClickListener(this);
        tv_2 = (TextView) head1.findViewById(R.id.tv_2);
        tv_2.setOnClickListener(this);
        ll1 = (LinearLayout) head1.findViewById(R.id.ll1);
        ll1.setOnClickListener(this);
        ll2 = (LinearLayout) head2.findViewById(R.id.ll2);
        ll2.setOnClickListener(this);

        iv_emessage = (ImageView) findViewById(R.id.iv_emessage);
        iv_emessage.setOnClickListener(this);
        iv1 = (ImageView) head1.findViewById(R.id.iv1);
        iv1.setOnClickListener(this);
        iv2 = (ImageView) head1.findViewById(R.id.iv2);
        iv2.setOnClickListener(this);
        iv3 = (ImageView) head1.findViewById(R.id.iv3);
        iv3.setOnClickListener(this);
        iv4 = (ImageView) head1.findViewById(R.id.iv4);
        iv4.setOnClickListener(this);
        iv5 = (ImageView) head1.findViewById(R.id.iv5);
        iv5.setOnClickListener(this);
        ll_tag1 = (LinearLayout) head1.findViewById(R.id.ll_tag1);
        ll_tag1.setOnClickListener(this);
        ll_tag2 = (LinearLayout) head1.findViewById(R.id.ll_tag2);
        ll_tag2.setOnClickListener(this);
        ll_go = (RelativeLayout) findViewById(R.id.ll_go);
        ll_go.setOnClickListener(this);
        ll_yanse = (LinearLayout) findViewById(R.id.ll_yanse);
        ll_yanse.setOnClickListener(this);
    }

    int screenWidth, screenHeigh;
    List<DianPuShouYeCuXiaoBean.DataBeanX.DataBean> allData = new ArrayList<>();


    @Override
    public void onStart() {
        super.onStart();

        getShouYeDatas();

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
    public void onStop() {
        super.onStop();


    }


    Intent intent;

    String tag;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_shangpinliebiao:
                intent = new Intent(this, ShangPinLieBiaoAcitivity.class);
                startActivity(intent);
                break;
            case R.id.iv_finish:


                finish();
                break;
            case R.id.iv_emessage:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(getContext()
                            , Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);

                } else {
                    intent = new Intent(getContext(), XiTongTiXingActivity.class);

                    startActivity(intent);
                }


                break;
            case R.id.tv_1:
                ll_tag1.setVisibility(View.VISIBLE);
                ll_tag2.setVisibility(View.INVISIBLE);

                gv.getRefreshableView().setSelection(2);
                break;
            case R.id.tv_2:


                ll_tag1.setVisibility(View.INVISIBLE);
                ll_tag2.setVisibility(View.VISIBLE);
                gv.getRefreshableView().setSelection(4);

                break;
            case R.id.ll_sou:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);

                break;
            case R.id.sign:
                String DIANPU_ID1 = getIntent().getStringExtra(DIANPU_ID);

                intent = new Intent(this, DianPuXiangQingActivity.class);
                intent.putExtra("data", "已认证" + dianPuShouYeBean.getData().getStore().getCompany_license_num() + "证");
                intent.putExtra(DIANPU_ID, DIANPU_ID1);
                startActivity(intent);
                break;

            case R.id.ll_go:
                if (sp.getString(MyRes.MY_TOKEN, "1").equals("kong")) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(DianPuShouYeActivity.this
                            , Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    intent = new Intent(this, GouWuCheActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.tv_xiadan://快速下单

                intent = new Intent(this, KuaiSuXiaDanActivity.class);


                startActivity(intent);
                break;
            case R.id.tv_jilu:
                if (sp.getString(MyRes.MY_TOKEN, "").equals("kong")) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(DianPuShouYeActivity.this
                            , Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    intent = new Intent(this, GouMaiJiLuActivity.class);
                    startActivity(intent);
                }


                break;
            case R.id.iv_dianhua:

                if (dianPuShouYeBean != null) {

                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dianPuShouYeBean.getData().getStore().getStore_phone()));
                    sp.edit().putString(MyRes.PHONE, dianPuShouYeBean.getData().getStore().getStore_phone() + "").commit();
                    startActivity(intent);


                }
                break;
            case R.id.ll_shoucang:
                if (dianPuShouYeBean != null) {

                    if (tag.equals("2")) {
                        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/store", RequestMethod.POST);
                        //  String string = ;
                        if (sp.getString(MyRes.MY_TOKEN, "1").equals("1")) {
                            ShowToast.showToast("请登录");
                            intent = new Intent(DianPuShouYeActivity.this
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
                                            tag = "1";

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
                            intent = new Intent(DianPuShouYeActivity.this
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
                                            tag = "2";

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
}
