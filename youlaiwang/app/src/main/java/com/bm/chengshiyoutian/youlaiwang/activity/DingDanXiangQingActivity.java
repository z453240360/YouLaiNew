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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.DingDanXiangQingAdapter1;
import com.bm.chengshiyoutian.youlaiwang.bean.DingDanXiangQingBean;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;



/**
 * Created by sld on 2017/6/19.
 */

public class DingDanXiangQingActivity extends AppCompatActivity {
    SharedPreferences sp;
    private Toolbar tb_toolbar;
    private ListView lv;
    private TextView tv_dingdanhao;
    private TextView tv_dingdanzhuangtai;
    private TextView tv_ren;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_shangpinjine;
    private TextView tv_yunfei;
    private TextView tv_zongjine;
    private TextView tv_fangshi;
    private TextView tv_chuangjian;
    private TextView tv_fukuan;
    private TextView tv_fahuo;
    private TextView tv_chengjiao;
    private LinearLayout ll_fuKuanTime,ll_fanHuoTime;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dingdanxiangqing);
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        Request<String> id = NoHttp.createStringRequest(MyRes.BASE_URL + "api/order/" + getIntent().getStringExtra("id"));
        id.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));

        CallServer.getInstance().add(123, id, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                data = GsonUtils.getInstance().fromJson((String) response.get(), DingDanXiangQingBean.class).getData();
                //订单状态 10:已取消;1(默认):未付款;2:已付款;3:已发货;4:已收货;5:已完成;6:已退货退款
                int order_state = data.getOrder_state();
                tv_fangshi.setText(data.getPayment_type() + "");
                tv_shangpinjine.setText("¥"+data.getGoods_amount() + "");
                tv_yunfei.setText("¥"+data.getShipping_fee() + "");
                tv_chuangjian.setText(data.getCreate_time() + "");
                tv_fukuan.setText(data.getPayment_time() + "");//付款时间
                tv_fahuo.setText(data.getSend_goods() + "");//发货时间
                tv_zongjine.setText("¥" + data.getOrder_amount() + "");

                tv_dingdanhao.setText(data.getOrder_sn() + "");
                tv_dingdanzhuangtai.setText(data.getFormat_order_state() + "");
                tv_ren.setText(data.getReciver_name() + "");
                tv_phone.setText(data.getReciver_mobile() + "");
                tv_address.setText(data.getReciver_info() + "");
                if(order_state == 1){
                    ll_fuKuanTime.setVisibility(View.GONE);
                    ll_fanHuoTime.setVisibility(View.GONE);
                }else if(order_state == 2){
                    ll_fanHuoTime.setVisibility(View.GONE);
                }else if(order_state == 10){
                    ll_fuKuanTime.setVisibility(View.GONE);
                    ll_fanHuoTime.setVisibility(View.GONE);
                }
                lv.setAdapter(new DingDanXiangQingAdapter1(data, DingDanXiangQingActivity.this));
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

    DingDanXiangQingBean.DataBean data;

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        lv = (ListView) findViewById(R.id.lv);
        View head = View.inflate(this, R.layout.dingdanxiangqing_head, null);
        View foot = View.inflate(this, R.layout.dingdanxiangqing_foot, null);

        lv.addHeaderView(head);
        lv.addFooterView(foot);//



        tv_ren = (TextView) head.findViewById(R.id.tv_ren);
        tv_phone = (TextView) head.findViewById(R.id.tv_phone);
        tv_address = (TextView) head.findViewById(R.id.tv_address);

        tv_dingdanhao = (TextView) foot.findViewById(R.id.tv_dingdanhao);
        tv_dingdanzhuangtai = (TextView) foot.findViewById(R.id.tv_dingdanzhuangtai);
        ll_fanHuoTime = (LinearLayout) foot.findViewById(R.id.ll_fanHuoTime);
        ll_fuKuanTime = (LinearLayout) foot.findViewById(R.id.ll_fuKuanTime);
        tv_shangpinjine = (TextView) foot.findViewById(R.id.tv_shangpinjine);
        //  tv_shangpinjine.setOnClickListener(this);
        tv_yunfei = (TextView) foot.findViewById(R.id.tv_yunfei);
        //   tv_yunfei.setOnClickListener(this);
        tv_zongjine = (TextView) foot.findViewById(R.id.tv_zongjine);
        //   tv_zongjine.setOnClickListener(this);
        tv_fangshi = (TextView) foot.findViewById(R.id.tv_fangshi);
        //   tv_fangshi.setOnClickListener(this);
        tv_chuangjian = (TextView) foot.findViewById(R.id.tv_chuangjian);
        //    tv_chuangjian.setOnClickListener(this);
        tv_fukuan = (TextView) foot.findViewById(R.id.tv_fukuan);
        //   tv_fukuan.setOnClickListener(this);
        tv_fahuo = (TextView) foot.findViewById(R.id.tv_fahuo);
        //  tv_fahuo.setOnClickListener(this);
        tv_chengjiao = (TextView) foot.findViewById(R.id.tv_chengjiao);
        //  tv_chengjiao.setOnClickListener(this);
    }
}
