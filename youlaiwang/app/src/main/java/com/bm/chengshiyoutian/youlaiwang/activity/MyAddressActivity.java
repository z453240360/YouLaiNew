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
import android.widget.Button;
import android.widget.ListView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.AddressAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.WoDeShouHuoDiZhiBena;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.activity.QueRenDingDanActivity.queInstance;


/**
 * Created by sld on 2017/5/11.
 */

public class MyAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;
    private ListView lv;

    private Button btn_add;
    SharedPreferences sp;
    private boolean fromDingDan = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        Intent mmIntent = getIntent();
        fromDingDan = mmIntent.getBooleanExtra("fromDingDan",false);
    }

    String token;

    @Override
    protected void onStart() {
        super.onStart();
        getDatas();
    }

    private void getDatas() {

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/address");

        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    final List<WoDeShouHuoDiZhiBena.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), WoDeShouHuoDiZhiBena.class).getData();
                    lv.setAdapter(new AddressAdapter(data, token, MyAddressActivity.this));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(queInstance != null){
                                queInstance.finish();
                            }
                            if(fromDingDan){
                                Intent intent = new Intent(MyAddressActivity.this, QueRenDingDanActivity.class);
                                intent.putExtra("name", data.get(position).getName());
                                intent.putExtra("add", data.get(position).getCityinfo()+data.get(position).getAreainfo());
                                intent.putExtra("MyID", data.get(position).getAddress_id()+"");
                                intent.putExtra("phone",data.get(position).getMobile()+"");

                                startActivity(intent);
                                finish();
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
        lv = (ListView) findViewById(R.id.lv);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                intent = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                intent.putExtra("token",sp.getString(MyRes.TOKEN,"") );
                startActivity(intent);
                break;
        }
    }
}
