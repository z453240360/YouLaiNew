package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;



/**团购
 * Created by youj on 2016/2/4.
 */
public class GroupBuyActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.web)
    WebView web;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_group_buy);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText(getResources().getString(R.string.app_name));
        progressDialog = new ProgressDialog(this);
        ivLeft.setOnClickListener(this);
        getData();
    }

    private void getData() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        InternetConfig config = new InternetConfig();
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.SETTING_URL, "tuangou", config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());
            int status = jsonObject.optInt("status");
            if (status == 0) {
                JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").optJSONObject(0);
                String link_url = object.optString("link_url");
                loadWeb(link_url);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadWeb(String link_url) {
        progressDialog.show();
        web.loadUrl(link_url);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });
        WebSettings settings = web.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
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
