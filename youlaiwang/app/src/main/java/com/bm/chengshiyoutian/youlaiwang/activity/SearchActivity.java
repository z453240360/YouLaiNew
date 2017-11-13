package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinAdapter2;
import com.bm.chengshiyoutian.youlaiwang.adapter.SouSuoJiLuAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinYouBianBean;
import com.bm.chengshiyoutian.youlaiwang.bean.SouSuoJiLuBean;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sld on 2017/5/2.
 */

public class SearchActivity extends AppCompatActivity {


    private EditText et_search;
    private ImageView iv_clear;
    private PullToRefreshListView lv2;
    String Authorization;
    private TextView tv;
    private GridView gv;
    private TextView tv_finish;
    SharedPreferences sharedPreferences;
    private int page = 1;
    private int total = 0;
    private String area_id;
    private LinearLayout layout_kong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        layout_kong = (LinearLayout) findViewById(R.id.ll_kong);
        sharedPreferences = getSharedPreferences(MyRes.CONFIG, 0);
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255, 168, 0), 0);
        //获得搜索的历史记录

        Authorization = sharedPreferences.getString(MyRes.MY_TOKEN, "");
        area_id = sharedPreferences.getString(MyRes.area_id, "");
        getData();

    }

    private long firstTime = 0;
    String goodsName = "";

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                //如果两次按键时间间隔大于2秒，搜索
                goodsName = et_search.getText().toString().trim();
                getSouSuoData(goodsName);
                firstTime = secondTime;//更新firstTime
                return true;
            } else {                                                    //两次按键小于2秒时，无反应

            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    List<ShangPinYouBianBean.DataBeanX.DataBean> datas = new ArrayList<>();
    ShangPinAdapter2 shangPinAdapter2 = null;


    private void getSouSuoData(String key) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/search");
        stringRequest.addHeader("Authorization", Authorization);
        stringRequest.add("goodsName", key);
        stringRequest.add("page", page);
        stringRequest.add("areaId", area_id);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                tv.setVisibility(View.GONE);
                gv.setVisibility(View.GONE);
                lv2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    List<ShangPinYouBianBean.DataBeanX.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinYouBianBean.class).getData().getData();
                    total = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinYouBianBean.class).getData().getTotal();
                    datas.addAll(data);

                    if (datas.size() == 0) {
                        layout_kong.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        layout_kong.setVisibility(View.GONE);
                    }


                    lv2.setVisibility(View.VISIBLE);

                    if (shangPinAdapter2 == null) {
                        shangPinAdapter2 = new ShangPinAdapter2(datas, SearchActivity.this, Authorization);
                        lv2.setAdapter(shangPinAdapter2);
                        shangPinAdapter2.setOnClicked(new ShangPinAdapter2.OnClicked() {
                            @Override
                            public void getIntent(int pos, String goodsId) {
                                Intent intent = new Intent(SearchActivity.this, ShangPinXiangQingActivity.class);
                                intent.putExtra("token", datas.get(pos).getGoods_id() + "");
                                startActivity(intent);
                            }
                        });


                    } else {
                        shangPinAdapter2.setData1(datas);
                        shangPinAdapter2.notifyDataSetChanged();
                    }

                    lv2.onRefreshComplete();

                } catch (Exception e) {
                    ShowToast.showToast("数据异常");
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
                lv2.onRefreshComplete();
                if (page > 1) {
                    page = page - 1;
                }
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    List<SouSuoJiLuBean.DataBean> data_jilu;

    private void getData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/search/history");
        stringRequest.addHeader("Authorization", Authorization);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                gv.setVisibility(View.GONE);
                Log.d("sld", (String) response.get());
                try {
                    data_jilu = GsonUtils.getInstance().fromJson((String) response.get(), SouSuoJiLuBean.class).getData();
                    if (data_jilu.size() == 0) {
                        tv.setVisibility(View.GONE);


                    } else {
                        tv.setVisibility(View.VISIBLE);
                        gv.setVisibility(View.VISIBLE);
                        gv.setAdapter(new SouSuoJiLuAdapter(data_jilu, SearchActivity.this));


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

            }
        });


    }

    private void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        lv2 = (PullToRefreshListView) findViewById(R.id.lv2);
        lv2.setVisibility(View.GONE);
        tv = (TextView) findViewById(R.id.tv);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv2.setVisibility(View.GONE);
                datas.clear();
                page = 1;
                et_search.setText("");
                getData();
            }
        });
        gv = (GridView) findViewById(R.id.gv);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sh_name = data_jilu.get(position).getSh_name();
                et_search.setText(sh_name);
                page = 1;
                datas.clear();
                goodsName = sh_name;
                getSouSuoData(sh_name);
            }
        });
        tv_finish = (TextView) findViewById(R.id.tv_finish);
        tv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        lv2.setMode(PullToRefreshBase.Mode.BOTH);
        lv2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                datas.clear();
                getSouSuoData(goodsName);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = page + 1;
                if (datas.size() < total) {
                    getSouSuoData(goodsName);
                } else if (datas.size() == total) {
                    lv2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page = page - 1;
                            lv2.onRefreshComplete();
                            ShowToast.showToast("已经到底了");
                        }
                    }, 500);
                }

            }
        });

    }

    private void submit() {
        // validate
        String search = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            Toast.makeText(this, "search不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}

