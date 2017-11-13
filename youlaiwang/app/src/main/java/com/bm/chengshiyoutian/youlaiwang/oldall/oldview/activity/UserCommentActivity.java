package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.CommentAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.CommentBean;
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
 * 顾客评论
 * Created by youj on 2016/1/18.
 */
public class UserCommentActivity extends Activity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv)
    PullToRefreshListView lv;
    private CommentAdapter adapter;
    private ArrayList<CommentBean> commenlist = new ArrayList<>();
    private String id;
    private ArrayList<CommentBean> tempList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_comment);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        id = getIntent().getStringExtra("id");
        if (null == id) {
            id = MyApplication.getInstance().getUser().id;
        }
        title.setText("用户评论");
        ivLeft.setOnClickListener(this);
        getCommentList(1);
        adapter = new CommentAdapter(this,commenlist,R.layout.item_comment);
        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
    }



    private int pageIndex = 1;
    private String pageSize = Constants.PAGE_SIZE;
    private int pageCount = 1;

    /**
     * 获取餐厅评论列表
     *
     */
    private void getCommentList(int type) {
        if (!progressDialog.isShowing() && type ==1) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        params.put("id", id);
        params.put("pageIndex", pageIndex+"");
        params.put("pageSize", pageSize);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetCommentList", params, config, this);
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this,getString(R.string.intnet_err));
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        lv.onRefreshComplete();
        switch (entity.getKey()) {
            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("ds");
                        pageCount = jsonObject.getInt("recordCount"); //页数
                        if (!(pageIndex < pageCount)) {
                            lv.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
                        } else {
                            lv.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        tempList.clear();
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            String content = object.optString("content");
                            String time = object.optString("add_time");
                            tempList.add(new CommentBean(content, time));
                        }
                        commenlist.addAll(tempList);
                        adapter.notifyDataSetChanged();
                    } else {
                        MyToastUtils.show(this, "数据获取失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
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
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        commenlist.clear();
        pageIndex = 1;
        getCommentList(2);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = pageIndex+1;
        getCommentList(2);
    }
}
