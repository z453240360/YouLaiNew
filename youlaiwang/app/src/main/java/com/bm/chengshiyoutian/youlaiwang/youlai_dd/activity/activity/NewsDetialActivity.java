package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.NewsBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.constant.Constant;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetialActivity extends AppCompatActivity implements IMainView {

    @Bind(R.id.mTxt_longhu_web)
    TextView mTxtLonghuWeb;
    @Bind(R.id.toolbars_longhu)
    Toolbar toolbarsLonghu;
    @Bind(R.id.mTxt_title)
    TextView mTxtTitle;
    @Bind(R.id.txt_time)
    TextView txtTime;
    @Bind(R.id.txt_seeNum)
    TextView txtSeeNum;
    @Bind(R.id.wb)
    WebView wb;

    private Intent intent;
    private int newsId;
    private Present present;
    private ProgressDialog dialog;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorState.setWindowStatusBarColorBlue(this, Color.BLUE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detial);
        ButterKnife.bind(this);
        initToolBar();
        init();


    }

    private void init() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        //设置WebView的一些常用属性
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持Javascript代码
        webSettings.setDefaultTextEncodingName("utf-8"); //设置默认编码集 utf-8

        //自适应手机屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //请求网络数据
        present = new Present(this);
        intent = getIntent();
        newsId = intent.getIntExtra("newsId", 0);
        title = intent.getStringExtra("title");
        mTxtLonghuWeb.setText(title);
        present.getNews(newsId);
    }

    private void initToolBar() {
        setSupportActionBar(toolbarsLonghu);
        toolbarsLonghu.setNavigationIcon(R.mipmap.gouwuche_fanhui);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
    public void getCode(String s) {

    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void cancelLoading() {
        dialog.dismiss();
    }

    @Override
    public void showFaliure(String s) {
        ShowToast.showToast(s);
    }

    @Override
    public void getLogin(String s) {

    }

    @Override
    public void getUpDate(String s) {
        Log.i("dd", "getUpDate: " + s);
        getDetials(s);
    }

    private void getDetials(String s) {
        Gson gson = new Gson();
        NewsBean newsBean = gson.fromJson(s, NewsBean.class);
        int code = newsBean.getCode();
        if (code != 200) {
            ShowToast.showToast(newsBean.getMsg());
            return;
        }
        String article_title = newsBean.getData().getArticle_title();
        String article_content = newsBean.getData().getArticle_content();
        String article_time = newsBean.getData().getArticle_time();
        int article_see_num = newsBean.getData().getArticle_see_num();


        mTxtTitle.setText(article_title);
        txtSeeNum.setText(article_see_num + "");
        txtTime.setText(article_time);
//        wb.loadDataWithBaseURL(null, article_content, "text/html", "utf-8", null);
        loadUrl();
    }


    private void loadUrl() {
        wb.loadUrl(Constant.BASRURL+"wap/article/"+newsId);
        //辅助 WebView 处理各种通知、请求事件等
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true; //注意这里改为true
            }
        });
    }
}
