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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinAdapter2;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBeanNum;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinYouBianBean;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by sld on 2017/6/8.
 */

public class GouMaiJiLuActivity extends AppCompatActivity implements View.OnClickListener {
    private PullToRefreshListView lv2;
    SharedPreferences sp;
    private ImageView iv_finish;
    private TextView tv_xiadan;
    private TextView tv_shangpinliebiao;
    private TextView iv_gouwuche;
    private ImageView iv_dianhua;
    private ImageView iv_emessage;
    private RelativeLayout ll_go;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goumaijilu);
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        initView();
        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {

//dgf
        if (message.equals("jia")) {

            getCarNum();

        }
        if (message.equals("jian")) {

            getCarNum();
        }
    }

    ShangPinAdapter2 shangPinAdapter2;
    int page = 1;
    List<ShangPinYouBianBean.DataBeanX.DataBean> allData = new ArrayList<>();

    private void getData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/store/" + sp.getString(MyRes.DIANPU_ID, "") + "/buyList");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, "0"));
        stringRequest.add("page", page);
        CallServer.getInstance().add(213, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                Log.d("sss", (String) response.get());

                List<ShangPinYouBianBean.DataBeanX.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinYouBianBean.class).getData().getData();
                allData.addAll(data);

                if (shangPinAdapter2 == null) {

                    shangPinAdapter2 = new ShangPinAdapter2(allData, GouMaiJiLuActivity.this, sp.getString(MyRes.MY_TOKEN, ""));
                    lv2.setAdapter(shangPinAdapter2);
                } else {
                    shangPinAdapter2.notifyDataSetChanged();


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

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getData();
            //   getDianPuDatas(P1ID + "", P2ID + "", weidu + "", jingdu + "", page + "", CityID + "");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            lv2.onRefreshComplete();
        }
    }

    private void initView() {
        lv2 = (PullToRefreshListView) findViewById(R.id.lv2);
        lv2.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                page++;
                new GetDataTask().execute();
            }
        });

        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        tv_xiadan = (TextView) findViewById(R.id.tv_xiadan);
        tv_xiadan.setOnClickListener(this);
        tv_shangpinliebiao = (TextView) findViewById(R.id.tv_shangpinliebiao);
        tv_shangpinliebiao.setOnClickListener(this);
        iv_gouwuche = (TextView) findViewById(R.id.iv_gouwuche);
        iv_dianhua = (ImageView) findViewById(R.id.iv_dianhua);
        iv_dianhua.setOnClickListener(this);
        iv_emessage = (ImageView) findViewById(R.id.iv_emessage);
        iv_emessage.setOnClickListener(this);
        ll_go = (RelativeLayout) findViewById(R.id.ll_go);
        ll_go.setOnClickListener(this);
    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:
                finish();
                break;
            case R.id.iv_emessage:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);

                } else {
                    intent = new Intent(this, XiTongTiXingActivity.class);

                    startActivity(intent);
                }


                break;
            case R.id.ll_go:
                intent = new Intent(this, GouWuCheActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_dianhua:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + sp.getString(MyRes.PHONE, "")));

                startActivity(intent);
                break;
            case R.id.tv_shangpinliebiao:

                intent = new Intent(this, ShangPinLieBiaoAcitivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_xiadan:
                intent = new Intent(this, KuaiSuXiaDanActivity.class);

                startActivity(intent);
                finish();
                break;
        }
    }
}
