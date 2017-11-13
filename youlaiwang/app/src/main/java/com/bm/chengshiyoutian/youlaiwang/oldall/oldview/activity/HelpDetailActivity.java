package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HelpDetailActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_content)
    WebView tvContent;
    private String type;
    private String keyWords;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_detail);
        ButterKnife.bind(this);
        ivLeft.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        type = getIntent().getStringExtra("Type");
        if ("1".equals(type)){
            keyWords = "解答";
            title.setText("常见问题解答");
        }else if("2".equals(type)){
            title.setText("注册协议");
            keyWords = "协议";
        }else if("3".equals(type)){
            title.setText("软件使用说明");
            keyWords = "软件使用";
        }
        setInfo();

    }

    private void setInfo() {
        dialog.show();

        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("keyWords", keyWords);
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.SETTING_URL, "GetProtocol", params, config, this);

    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {

        switch (entity.getKey()) {
            case 0:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.optInt("status")) {

                        JSONObject data = jsonObject.optJSONObject("data");
                        JSONArray dsarray = data.optJSONArray("ds");
//                        final String link_url= "";

                        for (int i = 0; i < dsarray.length(); i++) {
                            final String content = dsarray.optJSONObject(i).optString("content");
                            tvContent.getSettings().setJavaScriptEnabled(true);
                            tvContent.loadDataWithBaseURL("",content,"text/html","utf-8",null);
                        }


                    } else {
                        MyToastUtils.show(this, "数据异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
