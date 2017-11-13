package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.adapter.QuYuNewAdapter1;
import com.bm.chengshiyoutian.youlaiwang.adapter.QuYuNewAdapter2;
import com.bm.chengshiyoutian.youlaiwang.bean.DingWeiBean;
import com.bm.chengshiyoutian.youlaiwang.bean.QuYuXuanZeBean;
import com.bm.chengshiyoutian.youlaiwang.fragment.ShangPinFragment;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.R.attr.fragment;


/**
 * Created by sld on 2017/6/19.
 */

public class QuYuActivityNew extends AppCompatActivity implements AMapLocationListener {
    private Toolbar tb_toolbar;
    private SharedPreferences sp;
    private TextView tv;
    private ListView lv1;
    private ListView lv2;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        if (message.equals("queding")) {
            finish();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acyivity_quyu);
        getLoction();
        initView();
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        sp.edit().putString("weizhi","gaibian").commit();
        String string = sp.getString("weizhi", "wancheng");
        tv.setText(sp.getString(MyRes.city2, ""));
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initChildData();
        getData();

    }

    private void initChildData() {
        getChildData(SPUtil.getString(this,Constants.AREAID)==null?"":SPUtil.getString(this, Constants.AREAID));
    }


    /**
     * 定位初始化
     */
    private void getLoction() {

        locationClient = new AMapLocationClient(this);
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        locationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        //启动定位
        locationClient.startLocation();

    }

    private void getData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/area/hot");
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                String s1= sp.getString(MyRes.city1,"");
                final List<QuYuXuanZeBean.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), QuYuXuanZeBean.class).getData();
                final QuYuNewAdapter1 quYuNewAdapter = new QuYuNewAdapter1(data, QuYuActivityNew.this);
                lv1.setAdapter(quYuNewAdapter);
                int num1 = 0;
                for(int a=0;a<data.size();a++){
                    if(data.get(a).getArea_name().equals(s1)){
                        lv1.setSelection(a);
                        data.get(a).tag = true;
                        num1 = a;
                        getChildData(data.get(a).getArea_id() + "");
                    }else {
                        if(s1.equals("")){
                            data.get(0).tag = true;
                            getChildData(data.get(0).getArea_id() + "");
                        }

                    }
                }

                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        getChildData(data.get(position).getArea_id() + "");
                        SPUtil.put(QuYuActivityNew.this, Constants.AREAID,data.get(position).getArea_id() + "");

                        for (int i = 0; i < data.size(); i++) {

                            data.get(i).tag = false;

                        }
                        data.get(position).tag = true;
                        quYuNewAdapter.notifyDataSetChanged();

                        sp.edit().putString("weizhi","gaibian").commit();
                        sp.edit().putString(MyRes.city1, data.get(position).getArea_name()).commit();
                        sp.getString("weizhi","");
                    }


                });

            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void getChildData(String pid) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/area");
        stringRequest.add("parentId", pid);
        CallServer.getInstance().add(13, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
            }
            @Override
            public void onSucceed(int what, Response response) {
                final List<QuYuXuanZeBean.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), QuYuXuanZeBean.class).getData();
                final QuYuNewAdapter2 quYuNewAdapter = new QuYuNewAdapter2(data, QuYuActivityNew.this);
                lv2.setAdapter(quYuNewAdapter);
                lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        for (int i = 0; i < data.size(); i++) {

                            data.get(i).tag = false;

                        }
                        sp.edit().putString(MyRes.city2, data.get(position).getArea_name()).commit();
                        sp.edit().putString(MyRes.area_id, data.get(position).getArea_id() + "").commit();
                        SPUtil.put(QuYuActivityNew.this,Constants.AREAID2,data.get(position).getArea_id() + "");
                        EventBus.getDefault().post("queding");
                        data.get(position).tag = true;
                        quYuNewAdapter.notifyDataSetChanged();
                    }

                });
            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

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

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        tv = (TextView) findViewById(R.id.tv);
        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 = (ListView) findViewById(R.id.lv2);

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        sp.edit().putString(MyRes.WEIDU, aMapLocation.getLatitude() + "").commit();
        sp.edit().putString(MyRes.JINGDU, aMapLocation.getLongitude() + "").commit();

        String city = aMapLocation.getCity();
        tv.setText(city);

        getDiZhi();
    }


    String area_name;

    private void getDiZhi() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/getAreaId");
        stringRequest.add("lng", sp.getString(MyRes.JINGDU, ""));
        stringRequest.add("lat", sp.getString(MyRes.WEIDU, ""));
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject((String) response.get());
                    String code = jsonObject.getString("code");
                    if ("200".equals(code)) {
                        final DingWeiBean dingWeiBean = GsonUtils.getInstance().fromJson((String) response.get(), DingWeiBean.class);
                        if (dingWeiBean != null) {
                            if (dingWeiBean.getData() != null) {
                                if (dingWeiBean.getData().getArea_name() != null) {
                                    area_name = dingWeiBean.getData().getArea_name();
                                    tv.setText(area_name);
                                } else {
                                    area_name = "";
                                }
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sp.edit().putString(MyRes.city2, dingWeiBean.getData().getArea_name()).commit();
                                        sp.edit().putString(MyRes.area_id, dingWeiBean.getData().getArea_id() + "").commit();

                                        EventBus.getDefault().post("queding");
                                    }
                                });
                                //
                                String kong = sp.getString(MyRes.city2, "kong");

                                if (kong.equals("kong")) {
                                    tv.setText(area_name);
                                    sp.edit().putString(MyRes.city2, area_name).commit();
                                    sp.edit().putString(MyRes.area_id, GsonUtils.getInstance().fromJson((String) response.get(), DingWeiBean.class).getData().getArea_id() + "").commit();

                                } else {
                                    // tv.setText(kong);
                                    tv.setText(area_name);
                                }
                            }


                        } else {
                            area_name = "";
                        }
                        //String area_name = Gsonutils.getInstance().fromJson((String) response.get(), DingWeiBean.class).getData().getArea_name();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
