package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinAdapter2;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinShouCangAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinBean;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinYouBianBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.BuyRecodeActivity;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.bm.chengshiyoutian.youlaiwang.R.id.tb_toolbar;
import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;


/**
 * Created by sld on 2017/5/12.
 */

public class ShangPinShouCangActivity extends AppCompatActivity {
    @Bind(R.id.tb_toolbar)
    Toolbar tbToolbar;
    @Bind(R.id.lv2)
    PullToRefreshListView lv2;
    private int page = 0;
    private SharedPreferences sp;

    String tag = "1";
    List<ShangPinBean.DataBeanX.DataBean> data;
    private List<ShangPinYouBianBean.DataBeanX.DataBean> data1=new ArrayList<>();
    private List<ShangPinYouBianBean.DataBeanX.DataBean> allData = new ArrayList<>();
    private ShangPinAdapter2 shangPinAdapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangpin_shoucang);
        ButterKnife.bind(this);
        sp = getSharedPreferences(MyRes.CONFIG, 0);


        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255, 168, 0), 0);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tbToolbar.setNavigationIcon(R.mipmap.gouwuche_fanhui);

        getDatas(page);

        lv2.setMode(PullToRefreshBase.Mode.BOTH);
        lv2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 0;
                allData.clear();
                new ShangPinShouCangActivity.GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=0;
                new ShangPinShouCangActivity.GetDataTask().execute();
            }
        });
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            page++;
            getDatas(page);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            lv2.onRefreshComplete();
        }
    }

    private void getDatas(int page) {

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/favorites/goods");
        stringRequest.addHeader("Authorization",sp.getString(MyRes.MY_TOKEN, ""));
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

                try {
                    data1 = GsonUtils.getInstance().fromJson(response.get().toString(), ShangPinYouBianBean.class).getData().getData();
                    allData.addAll(data1);

                    if (shangPinAdapter2 == null) {
                        if (allData.size() > 0) {
                            shangPinAdapter2 = new ShangPinAdapter2(allData, ShangPinShouCangActivity.this, sp.getString(MyRes.MY_TOKEN, ""));
                            lv2.setAdapter(shangPinAdapter2);
                        } else {

                        }

                    } else {
                        shangPinAdapter2.notifyDataSetChanged();
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
