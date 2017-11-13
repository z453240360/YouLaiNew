package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.FuWenBenBean;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;




/**
 * Created by sld on 2017/5/2.
 */

public class XIangQingActivity1 extends AppCompatActivity {

    private Toolbar tb_toolbar;

    private WebView wb;
    private TextView tv_title;
    SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing1);
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sp=getSharedPreferences(MyRes.CONFIG,0);
        Log.d("1234", getIntent().getStringExtra("wenID"));
        getData();
    }

    private void getData() {

        final String wenID = getIntent().getStringExtra("wenID");

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/article/" + wenID);

        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN,""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                Log.d("sss", (String) response.get());
                FuWenBenBean.DataBean data = GsonUtils.getInstance().fromJson((String) response.get(), FuWenBenBean.class).getData();
                String article_content=data.getArticle_content();
                //wb.loadDataWithBaseURL(null, article_content, "text/html", "UTF-8", null);
                wb.loadUrl(MyRes.BASE_URL+"wap/article/"+wenID);
                tv_title.setText(data.getArticle_title());
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


    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);

        wb = (WebView) findViewById(R.id.wb);

        tv_title = (TextView) findViewById(R.id.tv_title);

    }
}

