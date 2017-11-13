package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.GouWuCheActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.LastWeekAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.CarBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.SevenBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view.MyDataView;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LastWeekActivity_dd extends AppCompatActivity {

    @Bind(R.id.tb_toolbarss)
    Toolbar tbToolbarss;
    @Bind(R.id.myDataView)
    MyDataView myDataView;
    @Bind(R.id.activity_last_week_dd)
    RelativeLayout activityLastWeekDd;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.cb)
    ImageView cb;
    @Bind(R.id.money_totle)
    TextView moneyTotle;
    @Bind(R.id.mBtn_totle)
    Button mBtnTotle;
    @Bind(R.id.ttxt)
    TextView ttxt;
    private String token;
    private String Authorization;
    private List<String> dataList = new ArrayList<>();
    private SharedPreferences sp;
    private LastWeekAdapter adapter;
    private LinearLayoutManager manager;
    private boolean flag ;
    private int tag = 1;

    private List<SevenBean.DataBeanX.DataBean> data = new ArrayList<>();
    private List<SevenBean.DataBeanX.DataBean.GoodsBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_last_week_dd);
        StatusBarUtil.setColorForSwipeBack(this, Color.parseColor("#ffa800"), 0);
        ButterKnife.bind(this);
        initToken();
        initView();

        getLastWeek("2017-9-9");
        initRbClick();

        flag = adapter.isAllSelect();

        cb.setImageResource(R.drawable.xuanzhong_02);

        //全选按钮，全部选中、或者全不选
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag){
                    adapter.isSelectAll(true);
                    cb.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
                    flag=false;
                }else{
                    adapter.isSelectAll(false);
                    cb.setImageResource(R.mipmap.shezhi_weixuanzhong);
                    flag=true;
                }
            }
        });


        //设置适配器回调事件
        adapter.setOnItemClickListener(new LastWeekAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int pos, View view) {
                Intent intent = new Intent(LastWeekActivity_dd.this, ShangPinXiangQingActivity.class);
                intent.putExtra("token", mData.get(pos).getGoods_id() + "");
                startActivity(intent);
            }

            @Override
            public void onCheckBoxChanged(int pos,boolean b) {
                if (b){
                    cb.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
                    flag=false;
                }else{
                    cb.setImageResource(R.mipmap.shezhi_weixuanzhong);
                    flag=true;
                }
            }

            @Override
            public void onGetTotalPrice(String s) {
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(Double.parseDouble(s));
                moneyTotle.setText("￥ " + format);
            }
        });



        mBtnTotle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedData = adapter.getSelectedData();
                addCar(selectedData);
            }
        });

    }


    private void initToken() {
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        token = sp.getString(MyRes.TOKEN, "");
        Authorization = "Bearer " + token;
        if (token.equals("")||token.equals("kong")){
            ShowToast.showToast("请登陆");
            startActivity(new Intent(this, Login_ddActivity.class));
            return;
        }
    }

    private void initView() {
        setSupportActionBar(tbToolbarss);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        adapter = new LastWeekAdapter(this, mData);
        manager = new LinearLayoutManager(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);
    }

    private void initRbClick() {

        if (dataList == null) {
            return;
        }

        myDataView.getRb1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.reSet();
                getLastWeek(dataList.get(6), 6);


            }
        });

        myDataView.getRb2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.reSet();
                getLastWeek(dataList.get(5), 5);


            }
        });


        myDataView.getRb3().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.reSet();
                getLastWeek(dataList.get(4), 4);

            }
        });


        myDataView.getRb4().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.reSet();
                getLastWeek(dataList.get(3), 3);

            }
        });


        myDataView.getRb5().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.reSet();
                getLastWeek(dataList.get(2), 2);

            }
        });

        myDataView.getRb6().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.reSet();
                getLastWeek(dataList.get(1), 1);

            }
        });

        myDataView.getRb7().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.reSet();
                getLastWeek(dataList.get(0), 0);

            }
        });
    }


    //初始获取上周七天和第一天的数据
    public void getLastWeek(String weekDay) {


        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/weeklist/list/" + weekDay);
        String token = sp.getString(MyRes.TOKEN, "");
        Authorization = "Bearer " + token;
        stringRequest.addHeader("Authorization", "Bearer " + token);
        CallServer.getInstance().add(2000, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }
            //18656385918程磊

            @Override
            public void onSucceed(int what, Response response) {

                int responseCode = response.responseCode();
                if (responseCode!=200){
                    ShowToast.showToast("服务器异常："+responseCode);
                    return;
                }


                String weekDays = response.get().toString();
                Gson gson = new Gson();
                SevenBean sevenBean = gson.fromJson(weekDays, SevenBean.class);
                SevenBean.DataBeanX.DateBean date = sevenBean.getData().getDate();

                String one = date.getOne();
                String two = date.getTwo();
                String three = date.getThree();
                String four = date.getFour();
                String five = date.getFive();
                String six = date.getSix();
                String seven = date.getSeven();

                dataList.clear();
                dataList.add(seven);
                dataList.add(six);
                dataList.add(five);
                dataList.add(four);
                dataList.add(three);
                dataList.add(two);
                dataList.add(one);

                myDataView.setWeeks(dataList);

                //设置点击的日期
                String data1 = myDataView.getData(one);
                myDataView.getmTxt_yesterday().setText(data1);


                mData.clear();
                adapter.notifyDataSetChanged();
                //获取列表数据
                data = sevenBean.getData().getData();
                for (int i = 0; i < data.size(); i++) {
                    List<SevenBean.DataBeanX.DataBean.GoodsBean> goods = data.get(i).getGoods();
                    mData.addAll(goods);
                }

                isShowTxt(mData);
                adapter.notifyDataSetChanged();

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

    //获取选中当天的数据
    public void getLastWeek(String weekDay, final int pos) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/weeklist/list/" + weekDay);
        String token = sp.getString(MyRes.TOKEN, "");
        Authorization = "Bearer " + token;
        stringRequest.addHeader("Authorization", "Bearer " + token);
        CallServer.getInstance().add(2000, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }
            //18656385918程磊

            @Override
            public void onSucceed(int what, Response response) {

                int responseCode = response.responseCode();
                if (responseCode!=200){
                    ShowToast.showToast("服务器异常："+responseCode);
                    return;
                }

                mData.clear();

                String weekDays = response.get().toString();
                Gson gson = new Gson();
                SevenBean sevenBean = gson.fromJson(weekDays, SevenBean.class);
                SevenBean.DataBeanX.DateBean date = sevenBean.getData().getDate();

                //设置点击的日期
                String data1 = myDataView.getData(dataList.get(pos));
                myDataView.getmTxt_yesterday().setText(data1);


                //获取列表数据
                data = sevenBean.getData().getData();
                for (int i = 0; i < data.size(); i++) {
                    List<SevenBean.DataBeanX.DataBean.GoodsBean> goods = data.get(i).getGoods();
                    mData.addAll(goods);
                }
                isShowTxt(mData);
                adapter.notifyDataSetChanged();


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

    //添加购物车
    public void addCar(String s) {

        if (s.equals("")){
            ShowToast.showToast("您没有选任何商品哦");
            return;
        }
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/weeklist/buy");
        String token = sp.getString(MyRes.TOKEN, "");
        Authorization = "Bearer " + token;
        stringRequest.addHeader("Authorization", "Bearer " + token);
        stringRequest.add("goods", s);

        CallServer.getInstance().add(2001, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                int responseCode = response.responseCode();
                if (responseCode!=200){
                    ShowToast.showToast("服务器异常："+responseCode);
                    return;
                }

                String s1 = response.get().toString();

                Gson gson = new Gson();
                CarBean carBean = gson.fromJson(s1, CarBean.class);
                int code = carBean.getCode();
                if (code!=200){
                    return;
                }

                startActivity(new Intent(LastWeekActivity_dd.this, GouWuCheActivity.class));
                ShowToast.showToast(carBean.getMsg()+"");
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

    //是否显示空是的状态
    public void isShowTxt(List<SevenBean.DataBeanX.DataBean.GoodsBean> mData) {
        if (mData.size() == 0) {
            ttxt.setVisibility(View.VISIBLE);
        } else {
            ttxt.setVisibility(View.INVISIBLE);
        }

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
}
