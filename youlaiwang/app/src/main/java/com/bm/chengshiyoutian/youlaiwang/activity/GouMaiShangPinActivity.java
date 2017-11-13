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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinYiGouMaiAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinBean;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by sld on 2017/5/12.
 */

public class GouMaiShangPinActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private Toolbar tb_toolbar;
    private PullToRefreshGridView gv;
    private TextView tv_num;
    private TextView _tv_money;
    private TextView tv_gouwuche;
    private LinearLayout ll_shoucang;
    private SharedPreferences sp;
    private int page = 1;
    private int total = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangpin_goumaiguode);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        getDatas();
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    String Ids;
    String specIds;
    List<ShangPinBean.DataBeanX.DataBean> data = new ArrayList<>();
    List<ShangPinBean.DataBeanX.DataBean> allData = new ArrayList<>();



    //购买商品的记录
    private void getDatas() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/buyRecord/goods");

        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("page",page);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
            }
            @Override
            public void onSucceed(int what, Response response) {
                try {
                    data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinBean.class).getData().getData();
                    total = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinBean.class).getData().getTotal();
                    allData.addAll(data);

                    if(shangPinYiGouMaiAdapter == null){
                        shangPinYiGouMaiAdapter = new ShangPinYiGouMaiAdapter(allData, GouMaiShangPinActivity.this);
                        gv.setAdapter(shangPinYiGouMaiAdapter);
                    }else {
                        shangPinYiGouMaiAdapter.notifyDataSetChanged();
                    }

                    gv.onRefreshComplete();
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int x = 0;
                            Ids = "";
                            specIds = "";
                            double y = 0;
                            if (tag_ll) {

                                allData.get(position).tag2 = !allData.get(position).tag2;
                                shangPinYiGouMaiAdapter.notifyDataSetChanged();
                                for (int i = 0; i < allData.size(); i++) {
                                    if (allData.get(i).tag2) {
                                        x++;
                                        Ids = Ids + "," + allData.get(i).getGoods_id();
                                        specIds = specIds + "," + allData.get(i).getSpec_id();
                                        y = y + allData.get(i).getGoods_price();
                                    }
                                }
                                tv_num.setText(x + "");
                                _tv_money.setText(y + "");

                            } else {
                                Intent intent = new Intent(GouMaiShangPinActivity.this, ShangPinXiangQingActivity.class);
                                intent.putExtra("token", allData.get(position).getGoods_id() + "");
                                startActivity(intent);
                            }
                        }
                    });

                } catch (JsonSyntaxException e) {
                    gv.onRefreshComplete();
                    String string = response.get().toString();
                    try {
                        JSONObject object = new JSONObject(string);
                        if(object.getInt("code") == 300){
                            String msg = object.getString("msg");
                            if(!msg.equals("")){
                                ShowToast.showToast(msg);
                            }

                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
                gv.onRefreshComplete();
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
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        gv = (PullToRefreshGridView) findViewById(R.id.gv);
        gv.setMode(PullToRefreshBase.Mode.BOTH);
        tv.setOnClickListener(this);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_num.setOnClickListener(this);
        _tv_money = (TextView) findViewById(R.id._tv_money);
        _tv_money.setOnClickListener(this);
        tv_gouwuche = (TextView) findViewById(R.id.tv_gouwuche);
        tv_gouwuche.setOnClickListener(this);
        ll_shoucang = (LinearLayout) findViewById(R.id.ll_shoucang);
        ll_shoucang.setOnClickListener(this);


        gv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                page = 1;
                allData.clear();
                getDatas();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;

                if(allData.size() > 0){
//                    gv.getRefreshableView().smoothScrollToPosition(allData.size()-1);
                    gv.getRefreshableView().setSelection(allData.size()-1);
//                    gv.getRefreshableView().smoothScrollToPositionFromTop(allData.size()-1,0);
                }
                if(allData.size() < total){
                    getDatas();
                }else {
                    gv.onRefreshComplete();
                    ShowToast.showToast("已经到底了！");
                }


            }
        });

    }


    //底部是否显示的标记
    boolean tag_ll = false;
    //标记是否显示
    boolean tag1;
    //标记是否选中
    boolean tag2;
    ShangPinYiGouMaiAdapter shangPinYiGouMaiAdapter;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv:
                tag_ll = !tag_ll;
                if (tag_ll) {

                    ll_shoucang.setVisibility(View.VISIBLE);
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).tag1 = true;
                    }
                    shangPinYiGouMaiAdapter.notifyDataSetChanged();
                    tv.setText("取消");
                } else {
                    ll_shoucang.setVisibility(View.GONE);
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).tag1 = false;
                    }
                    shangPinYiGouMaiAdapter.notifyDataSetChanged();
                    tv.setText("多选");
                }

                break;

            case R.id.tv_gouwuche:

                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/goods/buyRecord", RequestMethod.POST);
                stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                stringRequest.add("specId", specIds);
                stringRequest.add("goodsId", Ids);
                CallServer.getInstance().add(32, stringRequest, new OnResponseListener() {
                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response response) {

                        try {
                            JSONObject jsonObject = new JSONObject((String) response.get());
                            String msg = jsonObject.getString("msg");
                            String code = jsonObject.getString("code");
                            if ("200".equals(code)) {
                                Intent intent = new Intent(GouMaiShangPinActivity.this, GouWuCheActivity.class);
                                startActivity(intent);
                            }
                            ShowToast.showToast(msg);
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
