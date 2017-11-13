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
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.RestaurantAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
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
 * 搜索餐厅
 * Created by youj on 2016/1/30.
 */
public class SearchActivity extends Activity implements PullToRefreshBase.OnRefreshListener2<ListView>, View.OnClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv)
    PullToRefreshListView lv;
    private ProgressDialog progressDialog;
    private String cityName;
    private String keyWord;
    private RestaurantAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);
        ButterKnife.bind(this);
        init();
    }


    private int pageIndex = 1;
    private String pageSize = Constants.PAGE_SIZE;
    private int pageCount = 1;

    private ArrayList<RestaurantBean> restaurantList = new ArrayList<>();
    private ArrayList<RestaurantBean> tempList = new ArrayList<>();

    private void init() {
        title.setText(getString(R.string.near_mess));
        ivLeft.setOnClickListener(this);
        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        keyWord = intent.getStringExtra("keyWord");
        progressDialog = new ProgressDialog(this);
        getData(1);
        adapter = new RestaurantAdapter(this, restaurantList, R.layout.item_restaurant);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
    }

    private void getData(int type) {
        if (!progressDialog.isShowing() && type == 1) {
            progressDialog.show();
        }

        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        params.put("CityName", cityName);
        params.put("keyWords", keyWord);
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetNearListByKeyWords", params, config, this);

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
                    JSONObject object = new JSONObject(entity.getContentAsString());
                    pageCount = object.optInt("recordCount");
                    if (!(pageIndex < pageCount)) {
                        lv.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
                    } else {
                        lv.setMode(PullToRefreshBase.Mode.BOTH);
                    }
                    if (object.optInt("status") == 0) {
                        JSONArray jsonArray = object.optJSONObject("data").optJSONArray("ds");
                        int length = jsonArray.length();
                        tempList.clear();
                        for (int i = 0; i < length; i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            String id = jsonObject.optString("id");
                            String retaurantName = jsonObject.optString("title");
                            String img_url = jsonObject.optString("img_url");
                            String retaurantAddress = jsonObject.optString("seo_title");
                            String phone = jsonObject.optString("seo_keywords"); //电话
                            String mobile = jsonObject.optString("tags");//手机
                            String internal = jsonObject.optString("score");
                            String xing = jsonObject.optString("xing");//星级
                            String caigou = jsonObject.optString("caigou");//采购
                            String jiaoyou = jsonObject.optString("jiaoyou");//交油
                            String cclj = jsonObject.optString("cclj");//餐厨垃圾
                            String bi = jsonObject.optString("bi");
                            String distance = jsonObject.optString("distance");//距离
                            RestaurantBean bean = new RestaurantBean(img_url, retaurantName, xing, retaurantAddress,
                                    phone, mobile, "菜系", internal, id, caigou,
                                    jiaoyou, cclj, bi, distance);
                            tempList.add(bean);
                        }
                        restaurantList.addAll(tempList);
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

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        restaurantList.clear();
        pageIndex = 1;
        getData(2);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = pageIndex + 1;
        getData(2);
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
        RestaurantBean bean = adapter.getItem(position-1);
        Intent intent = new Intent(this,ResturantDetaisActivity.class);
        intent.putExtra("id",bean.id);
        startActivity(intent);
    }
}
