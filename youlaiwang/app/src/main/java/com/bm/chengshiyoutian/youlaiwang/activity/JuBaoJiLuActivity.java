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
import android.widget.ImageView;
import android.widget.ListView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.JuBaoJiLuAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.JuBaoJiLuBean;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;



/**
 * Created by sld on 2017/5/11.
 */

public class JuBaoJiLuActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;
    private ImageView iv;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jubaojilu);

        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0); StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        tb_toolbar.setNavigationIcon(R.mipmap.gouwuche_fanhui);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    String token;

    @Override
    protected void onStart() {
        super.onStart();
        getDatas();
    }

    private void getDatas() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/report");
        token = getIntent().getStringExtra("token");

        stringRequest.addHeader("Authorization", "Bearer " + token);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {


            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    List<JuBaoJiLuBean.DataBeanX.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), JuBaoJiLuBean.class).getData().getData();
                    if (data.size() == 0) {
                        ShowToast.showToast("暂无数据");

                    } else {

                        lv.setAdapter(new JuBaoJiLuAdapter(data, JuBaoJiLuActivity.this));
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

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv:
                Intent intent = new Intent(this, TouSuJuBaoActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
                break;


        }
    }
}
