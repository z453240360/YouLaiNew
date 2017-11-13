package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * 帮助
 * Created by youj on 2016/1/9.
 */
public class HelpActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_content)
    WebView tvContent;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_help);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ivLeft.setOnClickListener(this);
        String type = getIntent().getStringExtra("type");
        if (Constants.HELP.equals(type)) {
            title.setText(getString(R.string.help));
            getContent(0);
        } else if (Constants.TERMS.equals(type)) {
            title.setText("服务条款");
            getContent(1);
        } else if (Constants.NOTICE_FOR_USE.equals(type)) {
            title.setText("使用须知");
            getContent(2);
        } else if (Constants.REGISTRATION_PROTOCOL.equals(type)) {
            title.setText("注册协议");
            getContent(3);
        }else if (Constants.AD.equals(type)) {
            title.setText("广告详情");
          String content =  getIntent().getStringExtra("content");
//            tvContent.setText(content);
            tvContent.getSettings().setJavaScriptEnabled(true);
            tvContent.loadDataWithBaseURL("",content,"text/html","utf-8",null);
        }
    }

    private void getContent(int i) {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        String str = "";
        if (i == 0) {
            str = "帮助";
        } else if (i == 1) {
            str = "条款";
        } else if (i == 2) {
            str = "须知";
        } else if (i == 3) {
            str = "协议";
        }
        params.put("keyWords", str);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.SETTING_URL, "GetProtocol", params, config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0:
                try {
                    JSONObject object = new JSONObject(entity.getContentAsString());
                    JSONObject jsonObject = object.optJSONObject("data").optJSONArray("ds").optJSONObject(0);
//                     tvContent.setText(jsonObject.optString("content"));
                    tvContent.getSettings().setJavaScriptEnabled(true);
                    tvContent.loadDataWithBaseURL("",jsonObject.optString("content"),"text/html","utf-8",null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this,getString(R.string.intnet_err));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            default:
                break;
        }
    }
}
