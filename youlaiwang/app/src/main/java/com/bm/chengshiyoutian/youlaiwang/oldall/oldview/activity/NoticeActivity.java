package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.NoticeAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.NoticeBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
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
 * 公告
 * Created by jayen on 16/1/12.
 */
public class NoticeActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv)
    PullToRefreshListView lv;
    private NoticeAdapter noticeAdapter;

    private ArrayList<NoticeBean> noticeList = new ArrayList<NoticeBean>();
    private ArrayList<NoticeBean> temList = new ArrayList<NoticeBean>();
    private ProgressDialog progressDialog;
    private int pageIndex = 1;
    private String pageSize = Constants.PAGE_SIZE;
    private int pageCount = 1;
    private boolean isRefreshDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notice);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        title.setText(R.string.notice);
        ivLeft.setOnClickListener(this);
        setData(1);
        clearUnreadStatus(MyApplication.getInstance().getUser().id, "3");
        noticeAdapter = new NoticeAdapter(this, noticeList, R.layout.item_notice);
        lv.setAdapter(noticeAdapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
    }

    private void setData(int i) {
        if (i == 1 && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize);
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.ADSERVER, "GetAdList", params, config, this);
    }

    /**
     * 清楚未读消息
     *
     * @param userid
     * @param type:消息类型(1 系统消息，2政府消息,3公告)
     */
    private void clearUnreadStatus(String userid, String type) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("userid", userid);
        params.put("type", type);
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(10000);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "ClearUnreadStatus", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        lv.onRefreshComplete();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        int key = entity.getKey();
        switch (key) {
            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());

                    if (jsonObject.optInt("status") == 0) {
                        pageCount = jsonObject.getInt("recordCount"); //页数
                        if (!(pageIndex < pageCount)) {
                            lv.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
                        } else {

                            lv.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        temList.clear();
                        JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("ds");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            String id = object.optString("id");
                            String title = object.optString("title");
                            String content = object.optString("content");
                            String time = object.optString("add_time");
                            temList.add(new NoticeBean(id, content, title, time));
                        }
                        noticeList.addAll(temList);
                        noticeAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1://调接口表示公告已读
                clearNews(entity.getContentAsString());
                break;
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

    private void clearNews(String contentAsString) {
        if (!MyUtils.isEmpty(contentAsString)) {
            try {
                JSONObject jsonObject = new JSONObject(contentAsString);
                String status = jsonObject.optString("status");
                if ("0".equals(status)) {
                    //调用成功
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NoticeBean bean = noticeAdapter.getItem(position - 1);
        Intent intent = new Intent(this, NoticeDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("notice", bean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        noticeList.clear();
        noticeAdapter.notifyDataSetChanged();
        pageIndex = 1;
        setData(2);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = pageIndex + 1;
        setData(2);
    }
}
