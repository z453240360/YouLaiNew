package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.ProcurementRecordAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.ProcurementRecordBean;
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
 * 采购记录
 * Created by jayen on 16/1/13.
 */
public class ProcurementRecordActivity extends Activity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.lv)
    PullToRefreshListView lv;
    private ProcurementRecordAdapter adapter;
    private ArrayList<ProcurementRecordBean> recordList = new ArrayList<ProcurementRecordBean>();
    private ArrayList<ProcurementRecordBean> tempList;
    private ProgressDialog dialog;

    private int pageIndex = 1;
    private String pageSize = Constants.PAGE_SIZE;
    private int pageCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_procurement_record);
        ButterKnife.bind(this);

        init();

    }

    private void init() {
        dialog = new ProgressDialog(this);
        title.setText("采购记录");
        ivLeft.setOnClickListener(this);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.home);
        ivRight.setOnClickListener(this);
        loadOilTotal();
        loadListData(1);
        adapter = new ProcurementRecordAdapter(this, recordList);
        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
    }

    /**
     * 获取采购总量
     */
    private void loadOilTotal() {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("_id", MyApplication.getInstance().getUser().id);
        params.put("States", "1");
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetOilTotal", params, config, this);

    }

    /**
     * 获取采购记录列表
     */
    private void loadListData(int type) {
        if (type == 1 && dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("_id", MyApplication.getInstance().getUser().id);
        params.put("States", "1");
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize);
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetOilList", params, config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        lv.onRefreshComplete();
        switch (entity.getKey()) {
            case 0:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.optInt("status")) {
                        pageCount = jsonObject.getInt("recordCount");
                        if (!(pageIndex < pageCount)) {
                            lv.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
                        } else {
                            lv.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("ds");
                        int length = jsonArray.length();
                        tempList = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            String oilNum = object.optString("Record");
                            String time = object.optString("AddTime");
                            tempList.add(new ProcurementRecordBean(oilNum, time));
                        }
                        recordList.addAll(tempList);
                        adapter.notifyDataSetChanged();

                    } else {
                        MyToastUtils.show(this, "数据异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 1:

                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.optInt("status")) {
                        JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").optJSONObject(0);
                        String record = object.optString("Record");
                        tvNum.setText("null".equals(record) ? "0" : record);
                    } else {
                        MyToastUtils.show(this, "数据异常");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            default:
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

            case R.id.iv_right:
                startActivity(new Intent(this, XiangQingActivity.class));
                finish();
                break;

            default:
                break;
        }
    }


    @InjectHttpErr
    private void err(ResponseEntity entity) {
        lv.onRefreshComplete();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        recordList.clear();
        adapter.notifyDataSetChanged();
        pageIndex = 1;
        loadListData(2);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = pageIndex + 1;
        loadListData(2);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
