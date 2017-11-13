package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.GouMaiHestoryAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.BuyRecordBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;

public class GouMaiHestoryActivity extends AppCompatActivity {


    @Bind(R.id.toolbars_longhu)
    Toolbar toolbarsLonghu;
    private int page = 1;
    private XRecyclerView rv;
    private List<BuyRecordBean.DataBeanX.DataBean> allData = new ArrayList<>();
    private LinearLayoutManager manager;
    private GouMaiHestoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorState.setWindowStatusBarColorBlue(this, Color.BLUE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gou_mai_hestory);
        ButterKnife.bind(this);
        initToobar();
        rv = (XRecyclerView) findViewById(R.id.xr);
        manager = new LinearLayoutManager(this);
        adapter = new GouMaiHestoryAdapter(this, allData);

        getDatas(page);



        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);

        rv.setRefreshProgressStyle(ProgressStyle.BallBeat);
        rv.setLoadingMoreProgressStyle(ProgressStyle.LineScalePulseOutRapid);

        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                allData.clear();
                adapter.notifyDataSetChanged();
                page=1;
                getDatas(page);
                rv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                adapter.notifyDataSetChanged();
                getDatas(page);
                rv.loadMoreComplete();
            }
        });


        adapter.setOnItemClickListener(new GouMaiHestoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                ShowToast.showToast("" + pos);
                Intent intent = new Intent(GouMaiHestoryActivity.this, ShangPinXiangQingActivity.class);
                intent.putExtra("token",allData.get(pos).getGoods_id()+"");
                intent.putExtra("postion_dd",pos);
                startActivityForResult(intent,100);
            }

            @Override
            public void onAddClick(int pos, View view) {
                addCar(1,1,pos);
            }

            @Override
            public void onReduceClick(int pos, View view) {
                addCar(1,2,pos);
            }

            @Override
            public void onTextNumberClick(int pos, int number) {
                addCar(number, 1, pos);
            }
        });
    }

    //购买商品的记录
    private void getDatas(int page) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/buyRecord/goods");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("page", page);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {

            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                if (response.responseCode() != 200) {
                    ShowToast.showToast("服务器异常：" + response.responseCode());
                    return;
                }

                Gson gson = new Gson();
                BuyRecordBean buyRecordBean = gson.fromJson(response.get().toString(), BuyRecordBean.class);
                int code = buyRecordBean.getCode();
                if (code != 200) {
                    ShowToast.showToast(buyRecordBean.getMsg());
                    return;
                }

                BuyRecordBean.DataBeanX data = buyRecordBean.getData();
                List<BuyRecordBean.DataBeanX.DataBean> data1 = data.getData();
                if (data1.size() == 0) {
                    ShowToast.showToast("没有数据啦");
                    return;
                }

                allData.addAll(data1);
                adapter.notifyDataSetChanged();
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

    //手动输入数量
    private void addCar(int num, int type, int postion) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("goodsId", allData.get(postion).getGoods_id() + "");//商品编号
        stringRequest.add("specId", allData.get(postion).getRatio());//规格
        stringRequest.add("type", type);
        stringRequest.add("goodsNum", num);
        CallServer.getInstance().add(12, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response response) {
                String result = response.get().toString();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        String msg = object.getString("msg");
                        ShowToast.showToast(msg);
                    } else {
                        ShowToast.showToast(object.getString("msg"));
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

    private void initToobar() {
        //设置自定义标题栏

        setSupportActionBar(toolbarsLonghu);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbarsLonghu.setNavigationIcon(R.mipmap.gouwuche_fanhui);
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

    //意图接收从商品详情页面传过来的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){

            allData.get(5).setGoods_name(data.getStringExtra("goodsName"));
            adapter.notifyDataSetChanged();
        }
    }
}
