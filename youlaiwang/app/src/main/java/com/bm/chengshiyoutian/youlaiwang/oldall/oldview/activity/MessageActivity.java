package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.NoticeAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.NoticeBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * 消息
 * Created by youj on 2016/1/9.
 */
public class MessageActivity extends Activity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv)
    PullToRefreshListView lv;
    private ProgressDialog progressDialog;
    private NoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_message);
        ButterKnife.bind(this);
        init();
    }


    private int pageIndex = 1;
    private String pageSize = Constants.PAGE_SIZE;
    private int pageCount = 1;

    private ArrayList<NoticeBean> messageList = new ArrayList<>();
    private ArrayList<NoticeBean> tempList = new ArrayList<>();


    private void init() {
        progressDialog = new ProgressDialog(this);
        title.setText(getString(R.string.message));
        setData(1);
        ivLeft.setOnClickListener(this);
        adapter = new NoticeAdapter(this, messageList, R.layout.item_notice);
        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                startActivity(new Intent(this, SettingActivity.class));
                finish();
                break;

            default:
                break;
        }
    }


    private void setData(int i) {
        if (i == 1 && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize);
        params.put("strWhere", "");
        params.put("filedOrder", "");
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.MESSAGE, "GetNoticeList", params, config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        lv.onRefreshComplete();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());

            if (jsonObject.optInt("status") == 0) {
                pageCount = jsonObject.getInt("recordCount"); //页数
                if (!(pageIndex < pageCount)) {
                    lv.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
                } else {

                    lv.setMode(PullToRefreshBase.Mode.BOTH);
                }
                tempList.clear();
                JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("ds");
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject object = jsonArray.optJSONObject(i);
                    String id = object.optString("id");
                    String content = object.optString("content");
                    String time = object.optString("add_time");
                    int category_id = object.optInt("category_id");
                    if (category_id == 0) {
                        tempList.add(new NoticeBean(id, content, "申报提醒", time));
                    } else if(category_id == 1) {
                        tempList.add(new NoticeBean(id, content, "系统提醒", time));
                    }
                }
                messageList.addAll(tempList);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        lv.onRefreshComplete();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        messageList.clear();
        adapter.notifyDataSetChanged();
        pageIndex = 1;
        setData(2);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = pageIndex + 1;
        setData(2);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            startActivity(new Intent(this, SettingActivity.class));
            finish();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
}
