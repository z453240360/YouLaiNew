package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.SystemNewsAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.NewsBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.BaseInterface;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/7/21 16:34
 * @Description 系统消息
 */
public class SystemFragment extends Fragment implements BaseInterface {

    public List<NewsBean.NewsBeanMessage> newsBeanMessage = new ArrayList<>();
    private View mView;
    private PullToRefreshListView mLv_system_news;
    private int pageIndexInt = 1;
    private ProgressDialog progressDialog;
    private int mRecordCount = 0;//获取消息的总数
    private SystemNewsAdapter mSystemNewsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frg_system, container, false);
        init();
        return mView;
    }

    @Override
    public void init() {
        initView();
        initData();
        progressDialog = new ProgressDialog(getActivity());
        getInfoList(MyApplication.getInstance().getUser().id, "1", String.valueOf(pageIndexInt));
    }

    /**
     * @param userid
     * @param type:消息类型(1 系统消息，2政府消息)
     * @param pageIndex
     */
    private void getInfoList(String userid, String type, String pageIndex) {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("userid", userid);
        params.put("type", type);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", Constants.PAGE_SIZE);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(10000);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetInfoList", params, config, this);
    }

    @Override
    public void initView() {
        mLv_system_news = (PullToRefreshListView) mView.findViewById(R.id.lv_system_news);
        mLv_system_news.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
    }

    @Override
    public void initData() {
        mSystemNewsAdapter = new SystemNewsAdapter(getActivity());
        mLv_system_news.setAdapter(mSystemNewsAdapter);
        setListener();
    }

    @Override
    public void setListener() {
        mLv_system_news.setOnRefreshListener((new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mLv_system_news.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndexInt++;
                getInfoList(MyApplication.getInstance().getUser().id, "1", String.valueOf(pageIndexInt));
            }
        }));
    }

    @InjectHttpOk
    @Override
    public void ok(ResponseEntity entity) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        mLv_system_news.onRefreshComplete();
        String contentAsString = entity.getContentAsString();
        int key = entity.getKey();
        switch (key) {
            case 0://处理消息
                actionNews(contentAsString);
                break;
        }
    }

    @InjectHttpErr
    @Override
    public void err(ResponseEntity entity) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        mLv_system_news.onRefreshComplete();
    }

    //处理消息
    private void actionNews(String contentAsString) {
        if (!MyUtils.isEmpty(contentAsString)) {
            try {
                JSONObject jsonObject = new JSONObject(contentAsString);
                String status = jsonObject.optString("status");
                if ("0".equals(status)) {
                    String data = jsonObject.optString("data");
                    mRecordCount = jsonObject.optInt("recordCount");
                    if (pageIndexInt * Integer.parseInt(Constants.PAGE_SIZE) > mRecordCount) {
                        mLv_system_news.setMode(PullToRefreshBase.Mode.DISABLED);
                    } else {
                        mLv_system_news.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                    }
                    if (!MyUtils.isEmpty(data)) {
                        Gson gson = new Gson();
                        NewsBean newsBean = gson.fromJson(data, NewsBean.class);
                        if (newsBean != null) {
                            newsBeanMessage.addAll(newsBean.ds);
                            mSystemNewsAdapter.setData(newsBeanMessage);
                            mSystemNewsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
