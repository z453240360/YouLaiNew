package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class DianPuShouCangActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private Toolbar tb_toolbar;

    private PullToRefreshListView lv;
    private TextView _tv_num;
    private TextView tv_delete;
    private LinearLayout ll_shoucang;
    SharedPreferences sp;
    int page = 1;
    int total = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianpu_shoucang);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        getDatas();
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    String foreign_ids = null;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        int x = 0;
        foreign_ids = null;

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

    DianPuShouCangAdapter dianPuShouCangAdapter;
    List<DianPuBena.DataBeanX.DataBean> data;
    List<DianPuBena.DataBeanX.DataBean> allData = new ArrayList<>();

    private void getDatas() {


        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/shop");

        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("page", page);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    data = GsonUtils.getInstance().fromJson((String) response.get(), DianPuBena.class).getData().getData();
                    total = GsonUtils.getInstance().fromJson((String) response.get(), DianPuBena.class).getData().getTotal();
                    allData.addAll(data);
                    if(dianPuShouCangAdapter == null){
                        dianPuShouCangAdapter = new DianPuShouCangAdapter(allData, DianPuShouCangActivity.this);
                        lv.setAdapter(dianPuShouCangAdapter);
                    }else {
                        dianPuShouCangAdapter.notifyDataSetChanged();
                    }

                    lv.onRefreshComplete();

                    if (allData.size() > 0) {
                        if (!tag_ll){
                            for (int i = 0; i < allData.size(); i++) {
                                allData.get(i).tag1 = true;
                                allData.get(i).tag2 = false;

                            }
                            dianPuShouCangAdapter.notifyDataSetChanged();
                            tag = "2";

                        }else {
                            for (int i = 0; i < allData.size(); i++) {
                                allData.get(i).tag1 = false;
                                allData.get(i).tag2 = false;

                            }
                            dianPuShouCangAdapter.notifyDataSetChanged();
                            tag = "1";
                        }

                    }

                    _tv_num.setText(0 + "");
                    ids = null;

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (!tag_ll) {

                                allData.get(position-1).tag2 = !allData.get(position-1).tag2;
                                dianPuShouCangAdapter.notifyDataSetChanged();
                                int x = 0;
                                for (int i = 0; i < allData.size(); i++) {
                                    if (allData.get(i).tag1 == true && allData.get(i).tag2) {
                                        x++;

                                    }

                                }
                                _tv_num.setText(x + "");

                            }else {
                                Intent intent = new Intent(DianPuShouCangActivity.this, KuaiSuXiaDanActivity.class);
                                SharedPreferences sp = getSharedPreferences(MyRes.CONFIG, 0);
                                sp.edit().putString(MyRes.DIANPU_ID, allData.get(position-1).getStore_id() + "");
                                intent.putExtra(MyRes.DIANPU_ID, allData.get(position-1).getStore_id() + "");
                                startActivity(intent);
                            }

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

        _tv_num = (TextView) findViewById(R.id._tv_num);
        _tv_num.setOnClickListener(this);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(this);
        ll_shoucang = (LinearLayout) findViewById(R.id.ll_shoucang);
        ll_shoucang.setOnClickListener(this);
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
                            ShowToast.showToast("已经到底了！");
                            lv.onRefreshComplete();
                        }
                    },500);


                }

            }
        });
    }

    boolean tag_ll = true;
    String tag = "1";
    String ids = null;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv:
                if (tag_ll) {
                    ll_shoucang.setVisibility(View.VISIBLE);
                    tv.setText("取消");

                } else {

                    ll_shoucang.setVisibility(View.GONE);
                    tv.setText("编辑");


                }
                tag_ll = !tag_ll;

                if (allData.size() > 0) {
                    if (tag.equals("1")) {

                        for (int i = 0; i < allData.size(); i++) {
                            allData.get(i).tag1 = true;
                            allData.get(i).tag2 = false;

                        }
                        dianPuShouCangAdapter.notifyDataSetChanged();
                        tag = "2";
                    } else {

                        for (int i = 0; i < allData.size(); i++) {
                            allData.get(i).tag1 = false;
                            allData.get(i).tag2 = false;

                        }
                        dianPuShouCangAdapter.notifyDataSetChanged();
                        tag = "1";
                    }

                }


                break;
            case R.id.tv_delete:

                ids = null;
                for (int i = 0; i < allData.size(); i++) {
                    if (allData.get(i).tag1 == true && allData.get(i).tag2 == true) {

                        ids = ids + "," + allData.get(i).getStore_id();
                    }

                }

                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/destroy", RequestMethod.POST);
                String string = sp.getString(MyRes.MY_TOKEN, "1");
                if (string.equals("1")) {
                    ShowToast.showToast("请登录");

                } else {
                    stringRequest.addHeader("Authorization", string);
                    stringRequest.add("type", 2);
                    stringRequest.add("_method", "delete");
                    stringRequest.add("foreign_ids", ids);


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
                                    page = 1;
                                    allData.clear();
                                    getDatas();
                                    _tv_num.setText("0");
                                    tag_ll=true;
                                    tv.setText("编辑");
                                    ll_shoucang.setVisibility(View.GONE);
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
                    break;

                }
        }
    }
}