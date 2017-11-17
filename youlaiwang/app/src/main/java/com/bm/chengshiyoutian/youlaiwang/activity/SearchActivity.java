package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinYouBianBean;
import com.bm.chengshiyoutian.youlaiwang.bean.SouSuoJiLuBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.ShoppingCar.ShoppingAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.HistoryActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ShoppingCarBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jaeger.library.StatusBarUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.bm.chengshiyoutian.youlaiwang.R.id.ll;
import static com.bm.chengshiyoutian.youlaiwang.R.id.ll_kong;
import static com.bm.chengshiyoutian.youlaiwang.R.id.lv2;


/**
 * Created by sld on 2017/5/2.
 */

public class SearchActivity extends AppCompatActivity {


    @Bind(R.id.xr)
    XRecyclerView xr;
    private EditText et_search;
    private ImageView iv_clear;
    String Authorization;
    private TextView tv;
    private GridView gv;
    private TextView tv_finish;
    private SharedPreferences sharedPreferences;
    private int page = 1;
    private int total = 0;
    private String area_id;
    private LinearLayout layout_kong;

    private long firstTime = 0;
    private String goodsName = "";

    private ShoppingAdapter adapter3;
    private List<ShoppingCarBean.DataBeanX.DataBean> allData = new ArrayList<>();
    private String token;
    private String areaId;
    private LinearLayoutManager manager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();

        //商品详情
        manager = new LinearLayoutManager(this);
        adapter3 = new ShoppingAdapter(this, allData);
        xr.setLayoutManager(manager);
        xr.setAdapter(adapter3);

        layout_kong = (LinearLayout) findViewById(R.id.ll_kong);
        sharedPreferences = getSharedPreferences(MyRes.CONFIG, 0);
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255, 168, 0), 0);
        //获得搜索的历史记录

        Authorization = sharedPreferences.getString(MyRes.MY_TOKEN, "");
        area_id = sharedPreferences.getString(MyRes.area_id, "");
        getData();


        //商品列表设置加载更多
        xr.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                allData.clear();
                page = 1;
                adapter3.notifyDataSetChanged();
                getSouSuoData(et_search.getText().toString().trim());
                xr.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                getSouSuoData(et_search.getText().toString().trim());
                xr.loadMoreComplete();
            }
        });


        //商品列表的点击跳转详情事件
        adapter3.setClicked(new ShoppingAdapter.OnItemClicked() {
            @Override
            public void getDetial(int goods_id) {
                Intent intent = new Intent(SearchActivity.this, ShangPinXiangQingActivity.class);
                intent.putExtra("token", goods_id + "");
                startActivityForResult(intent, 100);//请求吗100
            }

            @Override
            public void addClicked() {
            }

            @Override
            public void reduceClicker() {

            }
        });

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
                layout_kong.setVisibility(View.GONE);
                xr.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {
                if (response.responseCode() != 200) {
                    ShowToast.showToast("服务器异常：" + response.responseCode());
                    return;
                }


                Gson gson = new Gson();
                try {

                    ShoppingCarBean shoppingCarBean = gson.fromJson(response.get().toString(), ShoppingCarBean.class);
                    int code = shoppingCarBean.getCode();
                    if (code != 200) {
                        Toast.makeText(SearchActivity.this, "" + shoppingCarBean.getMsg(), Toast.LENGTH_SHORT).show();
//                    dialog.cancel();
                    }

                    List<ShoppingCarBean.DataBeanX.DataBean> data = shoppingCarBean.getData().getData();

                    if (page == 1) {
                        if (data.size() == 0) {
                            layout_kong.setVisibility(View.VISIBLE);
                            return;
                        } else {
                        }
                    } else {
                        if (data.size() == 0) {
                            ShowToast.showToast("已经显示全部");
                            return;
                        }
                    }

                    allData.addAll(data);
                    adapter3.notifyDataSetChanged();

                } catch (JsonSyntaxException e) {
                    ShowToast.showToast("数据解析异常");
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
        tv = (TextView) findViewById(R.id.tv);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                allData.clear();

                adapter3.notifyDataSetChanged();
                gv.setVisibility(View.VISIBLE);
                layout_kong.setVisibility(View.GONE);
                xr.setVisibility(View.GONE);
                et_search.setText("");
                getData();
            }
        });
        gv = (GridView) findViewById(R.id.gv);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allData.clear();
                adapter3.notifyDataSetChanged();
                String sh_name = data_jilu.get(position).getSh_name();
                et_search.setText(sh_name);
                page = 1;
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


    }

    private void submit() {
        String search = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            Toast.makeText(this, "search不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}

