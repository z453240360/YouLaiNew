package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.XiTongTiXingAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.XiTongTiXingBean;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by sld on 2017/5/11.
 */

public class XiTongTiXingActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;

    private PullToRefreshListView lv;
     SharedPreferences sp;
    int page = 1;
    List<XiTongTiXingBean.DataBeanX.DataBean> allData = new ArrayList<>();
    XiTongTiXingAdapter tiXingAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xitongtixing);
        sp=getSharedPreferences(MyRes.CONFIG,0);
        getDatas();
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    ArrayList<String> datas;

    private void getDatas() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/message");

        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN,""));
        stringRequest.add("page", page);

        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                Log.e("系统消息", (String) response.get());
                try {

                    List<XiTongTiXingBean.DataBeanX.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), XiTongTiXingBean.class).getData().getData();

                    allData.addAll(data);
                    tiXingAdapter = new XiTongTiXingAdapter(allData, XiTongTiXingActivity.this);
                    lv.setAdapter(tiXingAdapter);
                    lv.onRefreshComplete();
                } catch (JsonSyntaxException e) {
                    ShowToast.showToast("数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
                lv.onRefreshComplete();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);

        lv = (PullToRefreshListView) findViewById(R.id.lv);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                allData.clear();
                getDatas();
//                new XiTongTiXingActivity.GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                page++;
                getDatas();
//                new XiTongTiXingActivity.GetDataTask().execute();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv:
                break;

        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getDatas();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            lv.onRefreshComplete();
        }
    }

}
