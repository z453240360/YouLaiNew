package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.ShoppingCar.ShoppingAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ShoppingCarBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.bm.chengshiyoutian.youlaiwang.R.id.xr;


public class BuyRecodeActivity extends AppCompatActivity {


    @Bind(R.id.toolbars_longhu1)
    Toolbar toolbarsLonghu;
    @Bind(R.id.xr1)
    XRecyclerView xr1;


    private int page =1;

    private SharedPreferences sp;

    private ShoppingAdapter adapter3;
    private List<ShoppingCarBean.DataBeanX.DataBean> allData = new ArrayList<>();
    private String token;
    private String areaId;
    private LinearLayoutManager manager;

    private ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorState.setWindowStatusBarColorBlue(this, Color.BLUE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_buy_recode);
        ButterKnife.bind(this);
        initToobar();

        sp = getSharedPreferences(MyRes.CONFIG, 0);
        token = sp.getString(MyRes.MY_TOKEN, "");
        areaId = sp.getString(MyRes.area_id, "");

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        initToobar();


        //商品详情
        manager = new LinearLayoutManager(this);
        adapter3 = new ShoppingAdapter(this, allData);
        xr1.setLayoutManager(manager);
        xr1.setAdapter(adapter3);


        getDatas(page,token);

        //商品列表设置加载更多
        xr1.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                allData.clear();
                page = 1;
                adapter3.notifyDataSetChanged();
                getDatas(page,token);
                xr1.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                getDatas(page,token);
                xr1.loadMoreComplete();
            }
        });


        //商品列表的点击跳转详情事件
        adapter3.setClicked(new ShoppingAdapter.OnItemClicked() {
            @Override
            public void getDetial(int goods_id) {
                Intent intent = new Intent(BuyRecodeActivity.this, ShangPinXiangQingActivity.class);
                intent.putExtra("token", goods_id+"");
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

    private void initToobar() {
        //设置自定义标题栏
        setSupportActionBar(toolbarsLonghu);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbarsLonghu.setNavigationIcon(R.mipmap.gouwuche_fanhui);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //购买商品的记录
    private void getDatas(final int page,String token) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/goods");
        stringRequest.addHeader("Authorization", token);
        stringRequest.add("page", page);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {

            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                dialog.cancel();
                if (response.responseCode() != 200) {
                    ShowToast.showToast("服务器异常：" + response.responseCode());
                    return;
                }
                Gson gson = new Gson();
                ShoppingCarBean shoppingCarBean = gson.fromJson(response.get().toString(), ShoppingCarBean.class);
                int code = shoppingCarBean.getCode();
                if (code != 200) {
                    Toast.makeText(BuyRecodeActivity.this, "" + shoppingCarBean.getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<ShoppingCarBean.DataBeanX.DataBean> data = shoppingCarBean.getData().getData();

                if (page == 1) {
                    if (data.size() == 0) {
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
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
                dialog.cancel();
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
}
