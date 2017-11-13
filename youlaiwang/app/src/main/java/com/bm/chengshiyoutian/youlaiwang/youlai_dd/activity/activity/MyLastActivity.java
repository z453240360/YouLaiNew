package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.CarAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.Car_dd_Bean;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;

public class MyLastActivity extends AppCompatActivity implements AMapLocationListener {

    @Bind(R.id.rv)
    XRecyclerView rv;
    @Bind(R.id.test)
    Button test;

    private String lng;//经度
    private String lat;//维度
    private int classOneId;//一级分类ID
    private int classId;//二级分类ID
    private int page = 1;//页码
    private String state;//商品显示类型
    private String orderBy;//排序方式
    private String storeId;//店铺的id

    private int TYPE_ADD = 1;//购物车添加商品
    private int TYPE_REDUCE = 2;//购物车减少商品


    private CarAdapter adapter;
    private LinearLayoutManager manager;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private List<Car_dd_Bean.DataBeanX.DataBean> allData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_last);
        ButterKnife.bind(this);


        classId = 2834;
        classOneId = 3;
        state = "5";
        orderBy = "";
        storeId = "";
        lng = "";
        lat = "";
        init();
        getGVData();//一级列表
        getRCData("");//二级列表，传空显示所有二级分类
//        getYouBianData(classId, classOneId, state, state, orderBy, page, lng, lat);//获取商品列表
        getYouBianData(2837, 2, "", "", "", 1, "", "  ");


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.refesh();
            }
        });
    }

    private void init() {

        lng = sp.getString(MyRes.JINGDU, "经度");
        lat = sp.getString(MyRes.WEIDU, "纬度");

        manager = new LinearLayoutManager(this);
        adapter = new CarAdapter(this, allData);

        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        rv.setRefreshProgressStyle(ProgressStyle.BallBeat);
        rv.setLoadingMoreProgressStyle(ProgressStyle.LineScalePulseOutRapid);
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                allData.clear();
                adapter.notifyDataSetChanged();
                page = 1;
                getYouBianData(2837, 2, "", "", "", 1, "", "  ");
                rv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                adapter.notifyDataSetChanged();
                getYouBianData(2837, 2, "", "", "", page, "", "  ");
                rv.loadMoreComplete();
            }
        });

        adapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {//查看商品详情
//                Intent intent = new Intent(MyLastActivity.this, ShangPinXiangQingActivity.class);
//                intent.putExtra("token", allData.get(pos).getGoods_id());
//                startActivity(intent);
            }

            @Override
            public void onAddClicked(int pos, int spec, int number, String numbers) {//商品添加
                addCar(number, -1, TYPE_ADD, pos, allData.get(pos).getSpec().getSpec_id(), numbers);
            }

            @Override
            public void onReduceClicked(int pos, int spec, int number, String numbers) {//商品减少
                addCar(number, -1, TYPE_REDUCE, pos, allData.get(pos).getSpec().getSpec_id(), numbers);
            }

            @Override
            public void onSecondAddClicked(int pos, int pos2, int spec, int number) {//多规格商品添加

            }

            @Override
            public void onSecondReduceClicked(int pos, int pos2, int spec, int number) {//多规格商品减少

            }

            @Override
            public void onNumberchanged(int pos, int spec, int number) {//商品数量增加

            }

            @Override
            public void onSecondChanged(int pos, int pos2, int spec, int number) {//多规格商品数量改变

            }
        });
    }


    //获取一级分类的标签（蔬菜水果，肉禽蛋类......）
    public void getGVData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/class");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                Log.i("dd", "onSucceed: " + response.get().toString());
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
            }
        });

    }

    //获取二级商品分类列表
    public void getRCData(String pid) {
        final Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/class/two");
        stringRequest.add("pid", pid);
        stringRequest.add("areaId", sp.getString(MyRes.area_id, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                Log.i("dd", "onSucceed: " + response.get().toString());
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //获取详细的商品列表
    public void getYouBianData(int classId, int classOneId, String state, String orderBy, String storeId, int page, String lng, String lat) {

        Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods");
        request.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        request.add("classId", classId);//二级分类
        request.add("page", page);//页码
        request.add("classOneId", classOneId);//一级分类
        request.add("areaId", sp.getString(MyRes.area_id, ""));//区域ID
        request.add("state", state);//排序类型
        request.add("orderBy", orderBy);//倒序还是反序
        request.add("lng", lng);//经度
        request.add("lat", lat);//维度
        request.add("storeId", storeId);//商店ID
        NoHttp.newRequestQueue().add(1, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i("dd", "onSucceed: " + response.get().toString());
                if (response.responseCode() != 200) {
                    ShowToast.showToast("服务器异常");
                    return;
                }
                Gson gson = new Gson();
                Car_dd_Bean car_dd_bean = gson.fromJson(response.get().toString(), Car_dd_Bean.class);
                if (car_dd_bean.getCode() != 200) {
                    ShowToast.showToast(car_dd_bean.getMsg());
                    return;
                }
                List<Car_dd_Bean.DataBeanX.DataBean> data = car_dd_bean.getData().getData();
                allData.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

    //同步购物车
    private void addCar(final int num, final int pos2, int type, final int postion, int spec_id, final String numbers) {

        final int i = Integer.parseInt(numbers);
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("goodsId", allData.get(postion).getGoods_id() + "");//商品编号
        stringRequest.add("specId", allData.get(postion).getSpec().getSpec_id());//规格
        stringRequest.add("type", type);
        stringRequest.add("goodsNum", Math.abs(num));
        CallServer.getInstance().add(12, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response response) {
                String result = response.get().toString();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        String msg = object.getString("msg");
                        if (pos2 < 0) {
                            allData.get(postion).getSpec().setCart_goods_num(i + num);
                            adapter.notifyDataSetChanged();
                        } else {
                            allData.get(pos2).getGoods_spec().get(pos2).setCart_goods_num(i + num);
                            adapter.notifyItemChanged(postion);
                        }

                        ShowToast.showToast(msg);
                    } else {
                        ShowToast.showToast(object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }





    //获取定位地址
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
                Log.i("dd", "onSucceed: " + response.get().toString());
            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    //定位
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        sp.edit().putString(MyRes.WEIDU, aMapLocation.getLatitude() + "").commit();
        sp.edit().putString(MyRes.JINGDU, aMapLocation.getLongitude() + "").commit();
        getDiZhi();
    }
}
