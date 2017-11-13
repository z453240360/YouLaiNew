package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.PurchaseRechaseAdapter1;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.SuoZhenSuoPiao;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyTimePickerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SuoZhenSuoPiaoListActivity extends AppCompatActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, AdapterView.OnItemClickListener {
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
    @Bind(R.id.ll_biaoji)
    LinearLayout ll_biaoji;


    @Bind(R.id.suozhen_guanjianci)
    EditText suozhen_guanjianci;

    @Bind(R.id.suozhen_timechoose)
    RelativeLayout suozhen_timechoose;

    @Bind(R.id.suozhen_search)
    Button suozhen_search;

    @Bind(R.id.suozhen_riqi)
    TextView suozhen_riqi;
    private MyTimePickerView pvTime;
    private ProgressDialog progressDialog;
    private ProgressDialog dialog;
    private PurchaseRechaseAdapter1 adapter;


    private int pageIndex = 1;
    private String pageSize = Constants.PAGE_SIZE;
    private int pageCount = 1;
    private String keywords = "";
    private List<SuoZhenSuoPiao.DataBean> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suo_zhen_suo_piao_list);
        ButterKnife.bind(this);
        showTimePicker();
        init();

    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        dialog = new ProgressDialog(this);

        title.setText("索证索票记录");
        ivLeft.setOnClickListener(this);
        ivRight.setVisibility(View.GONE);
        ivRight.setImageResource(R.mipmap.home);
        ivRight.setOnClickListener(this);
        adapter = new PurchaseRechaseAdapter1(getApplicationContext());
        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);

        //时间日期选择
        suozhen_timechoose.setOnClickListener(this);
        //搜索按钮
        suozhen_search.setOnClickListener(this);
        getPurchaseList(MyApplication.getInstance().getUser().id, pageIndex);


        initData();

    }

    private void initData() {
        if (mData != null) {
            adapter.setData(mData);
            adapter.notifyDataSetChanged();
        }

    }


    public String startTime = "";
    public String endTime = "";
    private void showTimePicker() {
        Calendar selectDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        pvTime = new MyTimePickerView.Builder(this, new MyTimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v, Date date1) {//选中事件回调
               /* tvTime.setText(getTime(date));*/
                startTime=  getTime(date);
                endTime= getTime(date1);

                suozhen_riqi.setText( getTime(date)+"-"+getTime(date1));
            }
        })
                .setType(MyTimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
               /* .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字*/
                .setContentSize(18)//滚轮文字大小
           /*     .setTitleSize(20)//标题文字大小*/
               /* .setTitleText("Title")//标题文字*/
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.WHITE)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setDividerColor(0xff0bb04a)
               /* .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode*/
                .setBgColor(0x7CD2CBCB)//滚轮背景颜色 Night mode
                .setRange(selectDate.get(Calendar.YEAR) - 20, selectDate.get(Calendar.YEAR) + 20)//默认是1900-2100年
                .setDate(selectDate)// 默认是系统时间*/
               /* .setLabel("年", "月", "日", "时", "分", "秒")*/
                .build();

    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_left:
            case R.id.iv_right:
                startActivity(new Intent(this, XiangQingActivity.class));
                finish();
                break;
            case R.id.suozhen_timechoose:
                pvTime.show();
                break;
            case R.id.suozhen_search:
                if (TextUtils.isEmpty(suozhen_guanjianci.getText().toString())) {
                    keywords = "";
                } else {
                    keywords = suozhen_guanjianci.getText().toString();
                }
                if (mData != null) {
                    mData.clear();
                }
                getPurchaseList(MyApplication.getInstance().getUser().id, 1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (mData != null) {
            mData.clear();
        }
        getPurchaseList(MyApplication.getInstance().getUser().id, pageIndex);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = pageIndex + 1;
        getPurchaseList(MyApplication.getInstance().getUser().id, pageIndex);
    }


    /**
     * 获取采购信息列表
     */
    private void getPurchaseList(String restaurantId, int pageIndex) {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("restaurantId", restaurantId);
        params.put("pageIndex", String.valueOf(pageIndex));
        params.put("pageSize", pageSize);
        params.put("startDate", startTime);
        params.put("endDate", endTime);
        params.put("keywords", keywords);
        InternetConfig config = new InternetConfig();
        config.setKey(10);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "getSearchPurchaseList", params, config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        lv.onRefreshComplete();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 10://获取采购信息列表
                SuoZhenSuoPiao suoZhenSuoPiao = GsonUtils.getInstance().fromJson(entity.getContentAsString(), SuoZhenSuoPiao.class);
                if (suoZhenSuoPiao != null) {
                    int recordCount = suoZhenSuoPiao.getRecordCount();
                    if (suoZhenSuoPiao.getStatus() == 0) {

                        List<SuoZhenSuoPiao.DataBean> data = suoZhenSuoPiao.getData();
                        if (data.size()==0){

                            return;
                        }
                        mData.addAll(data);
                        adapter.setData(mData);
                        adapter.notifyDataSetChanged();
                        //判断当前获取记录的条数，如果条数大于等于记录数，就取消上拉加载
                        if (mData != null && mData.size() >= recordCount) {
                            lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            lv.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                    } else {
                        ShowToast.showToast("数据异常");
                    }
                }

                break;
            default:
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, XiangQingActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
