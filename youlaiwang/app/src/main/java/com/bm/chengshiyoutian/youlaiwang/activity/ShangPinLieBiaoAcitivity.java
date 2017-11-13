package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinLieBiaoLVAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBeanNum;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinFenLeiBena;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/5/22.
 */

public class ShangPinLieBiaoAcitivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_finish;
    SharedPreferences sp;
    private TextView tv_xiadan;
    private TextView tv_jilu;
    private TextView iv_gouwuche;
    private ImageView iv_dianhua;
    private LinearLayout ll_sou;
    private ImageView iv_emessage;
    private RelativeLayout ll_go;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangpinliebiao);
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        initView();

        getDatas();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/total");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                try {
                    int cartNum = GsonUtils.getInstance().fromJson((String) response.get(), GouWuCheBeanNum.class).getData().getCartNum();
                    iv_gouwuche.setText(cartNum + "");
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void getDatas() {
        String stringExtra = sp.getString(MyRes.DIANPU_ID, "");
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/store/" + stringExtra + "/class");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                String s = (String) response.get();
                try {
                    ShangPinFenLeiBena shangPinFenLeiBena = GsonUtils.getInstance().fromJson(s, ShangPinFenLeiBena.class);
                    List<ShangPinFenLeiBena.DataBean> data = shangPinFenLeiBena.getData();
                    lv.setAdapter(new ShangPinLieBiaoLVAdapter(data, ShangPinLieBiaoAcitivity.this, sp.getString(MyRes.DIANPU_ID, "")));
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

    ListView lv;
    Context context;
    ArrayList<String> data;

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);


        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        tv_xiadan = (TextView) findViewById(R.id.tv_xiadan);
        tv_xiadan.setOnClickListener(this);
        tv_jilu = (TextView) findViewById(R.id.tv_jilu);
        tv_jilu.setOnClickListener(this);
        iv_gouwuche = (TextView) findViewById(R.id.iv_gouwuche);
        iv_gouwuche.setOnClickListener(this);
        iv_dianhua = (ImageView) findViewById(R.id.iv_dianhua);
        iv_dianhua.setOnClickListener(this);
        ll_sou = (LinearLayout) findViewById(R.id.ll_sou);
        ll_sou.setOnClickListener(this);
        iv_emessage = (ImageView) findViewById(R.id.iv_emessage);
        iv_emessage.setOnClickListener(this);
        ll_go = (RelativeLayout) findViewById(R.id.ll_go);
        ll_go.setOnClickListener(this);
    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:

                finish();
                break;
            case R.id.iv_emessage:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(this, Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);

                } else {
                    intent = new Intent(this, XiTongTiXingActivity.class);

                    startActivity(intent);
                }


                break;
            case R.id.tv_xiadan:
                intent = new Intent(this, KuaiSuXiaDanActivity.class);
                String stringExtra = sp.getString(MyRes.DIANPU_ID, "");
                intent.putExtra("token",stringExtra);
                startActivity(intent);
                break;
            case R.id.tv_jilu:

                if (sp.getString(MyRes.MY_TOKEN, "1").equals("kong")) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(ShangPinLieBiaoAcitivity.this, Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    intent = new Intent(this, GouMaiJiLuActivity.class);
                    startActivity(intent);
                }


                break;
            case R.id.ll_go:

                if (sp.getString(MyRes.MY_TOKEN, "1").equals("kong")) {
                    ShowToast.showToast("请登录");
                    intent = new Intent(ShangPinLieBiaoAcitivity.this, Login_ddActivity.class);
                    intent.putExtra("tag", "two");
                    startActivity(intent);
                } else {
                    intent = new Intent(ShangPinLieBiaoAcitivity.this, GouWuCheActivity.class);
                    intent.putExtra("num", "4");

                    startActivity(intent);
                }

                break;
            case R.id.iv_dianhua:

                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + sp.getString(MyRes.PHONE, "")));

                startActivity(intent);
                break;
            case R.id.ll_sou:

                intent = new Intent(this, SearchActivity.class);

                startActivity(intent);
                break;

        }
    }
}
