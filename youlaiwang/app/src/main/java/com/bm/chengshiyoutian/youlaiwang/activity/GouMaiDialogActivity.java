package com.bm.chengshiyoutian.youlaiwang.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;


public class GouMaiDialogActivity extends Activity implements View.OnClickListener{

    private TextView tv_quXiao,tv_queDing;
    private EditText et_num;
    String Authorizatio = "";
    String goodsId = "";
    String specId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gou_mai_dialog);
        initView();

        Intent mIntent = getIntent();
        Authorizatio = mIntent.getStringExtra("Authorization");
        goodsId = mIntent.getStringExtra("goodsId");
        specId = mIntent.getStringExtra("specId");
    }

    private void initView() {
        tv_quXiao = (TextView) findViewById(R.id.tv_quXiao);
        tv_queDing = (TextView) findViewById(R.id.tv_queDing);
        et_num = (EditText) findViewById(R.id.et_num);
        tv_quXiao.setOnClickListener(this);
        tv_queDing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_quXiao:
                finish();
                break;
            case R.id.tv_queDing:
                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                String token = sp.getString(MyRes.TOKEN, "");
                stringRequest.addHeader("Authorization", "Bearer " + token);
                stringRequest.add("goodsId", goodsId);
                stringRequest.add("specId", specId);
                stringRequest.add("type", 3);
                stringRequest.add("goodsNum", et_num.getText().toString().trim());
                CallServer.getInstance().add(12, stringRequest, new OnResponseListener() {
                    @Override
                    public void onStart(int what) {
                    }
                    @Override
                    public void onSucceed(int what, Response response) {
                        String result = response.get().toString();
                        try {
                            JSONObject object = new JSONObject(result);
                            if(object.getInt("code") == 200){
                                String num = et_num.getText().toString().trim();
                                SharedPreferences sp = getSharedPreferences("buy",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("num",num);
                                editor.commit();
                                finish();
                            }else {
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
                finish();
                break;
        }
    }
}
