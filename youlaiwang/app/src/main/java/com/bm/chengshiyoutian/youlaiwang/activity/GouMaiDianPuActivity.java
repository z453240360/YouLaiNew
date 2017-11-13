package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.DianPuShouCangAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuBena;
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
 * Created by sld on 2017/5/12.
 */

public class GouMaiDianPuActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private Toolbar tb_toolbar;

    private PullToRefreshListView lv;
    int page = 1;
    int total = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianpu_goumaiguode);
        getDatas();
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb_toolbar.setNavigationIcon(R.mipmap.gouwuche_fanhui);

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

    List<DianPuBena.DataBeanX.DataBean> allData = new ArrayList<>();
    DianPuShouCangAdapter dianPuShouCangAdapter;
    private void getDatas() {


        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/buyRecord/shop");
        String token = getIntent().getStringExtra("token");
        stringRequest.addHeader("Authorization", "Bearer " + token);
        stringRequest.add("page", page);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    final List<DianPuBena.DataBeanX.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), DianPuBena.class).getData().getData();
                    total = GsonUtils.getInstance().fromJson((String) response.get(), DianPuBena.class).getData().getTotal();
                    allData.addAll(data);
                    if(dianPuShouCangAdapter == null){
                        dianPuShouCangAdapter = new DianPuShouCangAdapter(allData, GouMaiDianPuActivity.this);
                        lv.setAdapter(dianPuShouCangAdapter);
                    }else {
                        dianPuShouCangAdapter.notifyDataSetChanged();
                    }
                    lv.onRefreshComplete();
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(GouMaiDianPuActivity.this, KuaiSuXiaDanActivity.class);
                            intent.putExtra(MyRes.DIANPU_ID, allData.get(position-1).getStore_id() + "");
                            startActivity(intent);
                        }
                    });
                } catch (JsonSyntaxException e) {
                    ShowToast.showToast("数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
                lv.onRefreshComplete();
                if(page > 1){
                    page = page - 1;
                }
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(this);
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);

        lv = (PullToRefreshListView) findViewById(R.id.lv);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                allData.clear();
                getDatas();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = page + 1;
                if(allData.size() < total){
                    getDatas();
                }else {
                    lv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lv.onRefreshComplete();
                            ShowToast.showToast("已经到底了！");
                        }
                    },500);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv:
                Toast.makeText(this, "编辑", Toast.LENGTH_SHORT).show();

                break;

        }
    }
}