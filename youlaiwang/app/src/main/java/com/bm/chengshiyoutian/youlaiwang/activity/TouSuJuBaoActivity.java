package com.bm.chengshiyoutian.youlaiwang.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.TouSuJuBaoBean;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;



/**
 * Created by sld on 2017/5/11.
 */

public class TouSuJuBaoActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;
    private ImageView iv;
    private ListView lv;
    private Button btn1;
    private EditText et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tousujubao);
        initView();


        StatusBarUtil.setColorForSwipeBack(this, Color.parseColor("#ffa800"), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }



    private void getDatas() {
        String token = getIntent().getStringExtra("token");
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/report/store", RequestMethod.POST);
        stringRequest.addHeader("Authorization", "Bearer "+token);
        stringRequest.add("content",et.getText().toString());
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                Log.d("sld", (String) response.get());
                TouSuJuBaoBean touSuJuBaoBean = GsonUtils.getInstance().fromJson((String) response.get(), TouSuJuBaoBean.class);
                if (touSuJuBaoBean.getCode()==200){
                    ShowToast.showToast(touSuJuBaoBean.getMsg());
                    finish();


                }else {
                    ShowToast.showToast("服务器异常");

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
        btn1 = (Button) findViewById(R.id.btn1);
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        btn1.setOnClickListener(this);
        et = (EditText) findViewById(R.id.et);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                getDatas();
                break;
        }
    }

    private void submit() {
        // validate
        String etString = et.getText().toString().trim();
        if (TextUtils.isEmpty(etString)) {
            Toast.makeText(this, "etString不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
