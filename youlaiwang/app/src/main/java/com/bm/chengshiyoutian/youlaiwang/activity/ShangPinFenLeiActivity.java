package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinFeiLeiAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBeanNum;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinLieBiaoBena;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by Administrator on 2017/5/22.
 */

public class ShangPinFenLeiActivity extends AppCompatActivity implements View.OnClickListener {


    private PullToRefreshGridView gv;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private ProgressBar pb;
    private ImageView iv_finish;

    //页码数
    int page = 1;
    //类型
    int x = 0;
    private LinearLayout ll_sou;
    private ImageView iv_emessage;
    private TextView tv_xiadan;
    private TextView tv_shangpinliebiao;
    private TextView tv_jilu;
    private TextView iv_gouwuche;
    private ImageView iv_dianhua;
    SharedPreferences sp;
    private RelativeLayout ll_go;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shangpinfenlei);
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        allDatas = new ArrayList<>();
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        initView();

    }

    private void initView() {
        String data1 = getIntent().getStringExtra("data");

        List<ShangPinLieBiaoBena.DataBeanX.DataBean> datas1 = GsonUtils.getInstance().fromJson(data1, ShangPinLieBiaoBena.class).getData().getData();
        allDatas.addAll(datas1);

        gv = (PullToRefreshGridView) findViewById(R.id.gv);
        ShangPinFeiLeiAdapter shangPinFeiLeiAdapter1 = new ShangPinFeiLeiAdapter(allDatas, ShangPinFenLeiActivity.this, getWindowManager().getDefaultDisplay().getWidth());


        gv.setAdapter(shangPinFeiLeiAdapter1);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ShangPinFenLeiActivity.this, ShangPinXiangQingActivity.class);
                String s = allDatas.get(position).getGoods_id() + "";
                intent.putExtra("token", s);
                startActivity(intent);

            }
        });
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setOnClickListener(this);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setOnClickListener(this);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv3.setOnClickListener(this);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setOnClickListener(this);
        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);

        gv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

                page++;
                new GetDataTask().execute();
            }
        });

        ll_sou = (LinearLayout) findViewById(R.id.ll_sou);
        ll_sou.setOnClickListener(this);
        iv_emessage = (ImageView) findViewById(R.id.iv_emessage);
        iv_emessage.setOnClickListener(this);
        tv_xiadan = (TextView) findViewById(R.id.tv_xiadan);
        tv_xiadan.setOnClickListener(this);
        tv_shangpinliebiao = (TextView) findViewById(R.id.tv_shangpinliebiao);
        tv_shangpinliebiao.setOnClickListener(this);
        tv_jilu = (TextView) findViewById(R.id.tv_jilu);
        tv_jilu.setOnClickListener(this);
        iv_gouwuche = (TextView) findViewById(R.id.iv_gouwuche);
        iv_gouwuche.setOnClickListener(this);
        iv_dianhua = (ImageView) findViewById(R.id.iv_dianhua);
        iv_dianhua.setOnClickListener(this);
        ll_go = (RelativeLayout) findViewById(R.id.ll_go);
        ll_go.setOnClickListener(this);
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getData(page, x);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            gv.onRefreshComplete();
        }
    }


    //?ie=utf-8&f=8&rsv_bp=0
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:

                finish();
                break;

            case R.id.ll_sou:
                Intent intent = new Intent(this, SearchActivity.class);


                startActivity(intent);
                break;
            case R.id.tv1:
                page = 1;
                x = 1;
                allDatas.clear();
                tv1.setTextColor(0xff0bb04a);
                tv2.setTextColor(0xff8c8c8c);
                tv3.setTextColor(0xff8c8c8c);
                getData(page, x);
                break;
            case R.id.tv2:
                x = 2;
                allDatas.clear();
                tv2.setTextColor(0xff0bb04a);
                tv1.setTextColor(0xff8c8c8c);
                tv3.setTextColor(0xff8c8c8c);
                page = 1;
                getData(page, x);
                break;
            case R.id.tv3:
                allDatas.clear();
                tv3.setTextColor(0xff0bb04a);
                tv1.setTextColor(0xff8c8c8c);
                tv2.setTextColor(0xff8c8c8c);
                x = 3;
                page = 1;
                getData(page, x);
                break;
            case R.id.iv_emessage:

                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(this
                            , LoginActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);

                } else {
                    intent = new Intent(this, XiTongTiXingActivity.class);

                    startActivity(intent);
                }


                break;
            case R.id.tv_xiadan:
                intent = new Intent(this, KuaiSuXiaDanActivity.class);

                startActivity(intent);
                finish();
                break;
            case R.id.tv_jilu:

                if (sp.getString(MyRes.MY_TOKEN, "1").equals("kong")) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(ShangPinFenLeiActivity.this
                            , LoginActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    intent = new Intent(this, GouMaiJiLuActivity.class);
                    startActivity(intent);
                    finish();
                }


                break;
            case R.id.ll_go:


                if (sp.getString(MyRes.MY_TOKEN, "1").equals("kong")) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(ShangPinFenLeiActivity.this
                            , LoginActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    intent = new Intent(ShangPinFenLeiActivity.this, GouWuCheActivity.class);
                    intent.putExtra("num", "4");

                    startActivity(intent);
                }

                break;
            case R.id.iv_dianhua:

                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + sp.getString(MyRes.PHONE, "")));

                startActivity(intent);
                break;

        }
    }

    List<ShangPinLieBiaoBena.DataBeanX.DataBean> datas;
    ShangPinFeiLeiAdapter shangPinFeiLeiAdapter = null;
    List<ShangPinLieBiaoBena.DataBeanX.DataBean> allDatas;

    private void getData(int page, int state) {
        String url3 = getIntent().getStringExtra("url");
        Request<String> stringRequest = NoHttp.createStringRequest(url3 + "?page=" + page + "&state=" + state);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
                gv.setVisibility(View.GONE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                datas = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinLieBiaoBena.class).getData().getData();
                allDatas.addAll(datas);
                if (shangPinFeiLeiAdapter == null) {
                    shangPinFeiLeiAdapter = new ShangPinFeiLeiAdapter(allDatas, ShangPinFenLeiActivity.this, getWindowManager().getDefaultDisplay().getWidth());
                    gv.setAdapter(shangPinFeiLeiAdapter);


                } else {
                    shangPinFeiLeiAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                pb.setVisibility(View.GONE);
                gv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getCarNum();


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
}
