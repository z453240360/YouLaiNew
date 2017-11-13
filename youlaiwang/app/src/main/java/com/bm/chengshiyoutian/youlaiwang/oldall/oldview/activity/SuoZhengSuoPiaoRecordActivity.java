package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
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

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.PurchaseRechaseAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.ProcurementRecordBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.ProcurementRecordNewBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageJiLuDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyTimePickerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/4/22 19:10
 * @Description 索证索票记录
 */
public class SuoZhengSuoPiaoRecordActivity extends Activity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, AdapterView.OnItemClickListener {

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
    //private ProcurementRecordAdapter adapter;
    private PurchaseRechaseAdapter adapter;

    private ArrayList<ProcurementRecordBean> recordList = new ArrayList<ProcurementRecordBean>();
    private ArrayList<ProcurementRecordBean> tempList;
    private ProgressDialog dialog;

    private int pageIndex = 1;
    private String pageSize = Constants.PAGE_SIZE;
    private int pageCount = 1;

    private ProgressDialog progressDialog;
    private ArrayList<ProcurementRecordNewBean> procurementRecordNewBeans;
    private Calendar calendar;
    private MyTimePickerView pvTime;

    public static String getDate(String source, String patternOld, String patternNew) {
        String newDateStr = null;
        try {
            newDateStr = new SimpleDateFormat(patternNew).format(new SimpleDateFormat(patternOld).parse(source))
                    .toString();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newDateStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_suo_zheng_suo_piao_recode);
        ButterKnife.bind(this);
        init();
        showTimePicker();
        String s = getIntent().getStringExtra("json");
        if (s != null) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray data = jsonObject.optJSONArray("purchaseList");
                if (data != null && data.length() > 0) {
                    //ll_biaoji.setVisibility(View.GONE);
                    if (procurementRecordNewBeans == null) {
                        procurementRecordNewBeans = new ArrayList<>();

                    }
                    procurementRecordNewBeans.clear();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.optJSONObject(i);
                        String bill = object.optString("bill");
                        String supplierLicense = object.optString("supplierLicense");
                        String addTime = object.optString("addTime");
                        String time = addTime.replace("T", "");
                        procurementRecordNewBeans.add(new ProcurementRecordNewBean(getDate(time, "yyyy-MM-ddHH:mm:ss", "MM-dd"), object.optString("goodsName"), object.optString("supplierName"),
                                object.optString("className"), object.optString("number"), object.optString("unitType"), object.optString("licenseImg"), bill, supplierLicense, imageData(bill, supplierLicense), getDate(time, "yyyy-MM-ddHH:mm:ss", "yyyy")));
                        adapter.setData(procurementRecordNewBeans);
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        dialog = new ProgressDialog(this);
        title.setText("详情");
        ivLeft.setOnClickListener(this);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.home);
        ivRight.setOnClickListener(this);
        /*loadOilTotal();
         loadListData(1);
         adapter = new ProcurementRecordAdapter(this,recordList);
         lv.setAdapter(adapter);*/
       /* getPurchaseList(MyApplication.getInstance().getUser().id, pageIndex);*/
        adapter = new PurchaseRechaseAdapter(getApplicationContext());
        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
        initData();

        //时间日期选择
        suozhen_timechoose.setOnClickListener(this);
        //搜索按钮
        suozhen_search.setOnClickListener(this);
    }

    private void initData() {
        adapter.setData(procurementRecordNewBeans);
        adapter.notifyDataSetChanged();
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
        InternetConfig config = new InternetConfig();
        config.setKey(10);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "getPurchaseList3", params, config, this);
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
        Log.e("sld", entity.getContentAsString());
        lv.onRefreshComplete();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
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
            case 10://获取采购信息列表
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.optInt("status")) {
                        JSONArray data = jsonObject.optJSONArray("data");
                        int recordCount = jsonObject.optInt("recordCount");
                        if (data != null && data.length() > 0) {
                            //ll_biaoji.setVisibility(View.GONE);
                            if (procurementRecordNewBeans == null) {
                                procurementRecordNewBeans = new ArrayList<>();
                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.optJSONObject(i);
                                String bill = object.optString("bill");
                                String supplierLicense = object.optString("supplierLicense");
                                String addTime = object.optString("addTime");
                                String time = addTime.replace("T", "");
                                procurementRecordNewBeans.add(new ProcurementRecordNewBean(getDate(time, "yyyy-MM-ddHH:mm:ss", "MM-dd"), object.optString("goodsName"), object.optString("supplierName"),
                                        object.optString("className"), object.optString("number"), object.optString("unitType"), object.optString("licenseImg"), bill, supplierLicense, imageData(bill, supplierLicense), getDate(time, "yyyy-MM-ddHH:mm:ss", "yyyy")));
                                adapter.setData(procurementRecordNewBeans);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        //判断当前获取记录的条数，如果条数大于等于记录数，就取消上拉加载
                        if (procurementRecordNewBeans != null && procurementRecordNewBeans.size() >= recordCount) {
                            lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        } else {
                            lv.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                    } else {
                        MyToastUtils.show(this, "数据异常");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    private List<String> imageData(String dataBill, String dataSupplierLicense) {
        ArrayList<String> imageDataList = new ArrayList<>();
        if (!TextUtils.isEmpty(dataBill)) {
            String[] dataBillList = dataBill.split(",");
            if (dataBillList != null) {
                for (int i = 0; i < dataBillList.length; i++) {
                    imageDataList.add(dataBillList[i]);
                }
            }
        }
        if (!TextUtils.isEmpty(dataSupplierLicense)) {
            String[] dataSupplierLicenseList = dataSupplierLicense.split(",");
            if (dataSupplierLicenseList != null) {
                for (int i = 0; i < dataSupplierLicenseList.length; i++) {
                    imageDataList.add(dataSupplierLicenseList[i]);
                }
            }
        }
        return imageDataList;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
            case R.id.iv_right:
               finish();
                break;
            case R.id.suozhen_timechoose:
                pvTime.show();
                break;
            case R.id.suozhen_search:
                if (procurementRecordNewBeans != null) {
                    procurementRecordNewBeans.clear();
                }

                 getPurchaseList(MyApplication.getInstance().getUser().id, 1);
                break;
            default:
                break;
        }
    }
    //把日期转为字符串
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
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

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        lv.onRefreshComplete();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
       /* recordList.clear();
        adapter.notifyDataSetChanged();
        pageIndex = 1;
        loadListData(2);*/
        pageIndex = 1;
        if (procurementRecordNewBeans != null) {
            procurementRecordNewBeans.clear();
        }
        getPurchaseList(MyApplication.getInstance().getUser().id, pageIndex);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = pageIndex + 1;
        //loadListData(2);
        getPurchaseList(MyApplication.getInstance().getUser().id, pageIndex);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProcurementRecordNewBean procurementRecordNewBean = procurementRecordNewBeans.get(position - 1);
        List<String> imageData = imageData(procurementRecordNewBean.bill, procurementRecordNewBean.supplierLicense);
        if (imageData != null && imageData.size() > 0) {
            BigImageJiLuDialog bigImageJiLuDialog = new BigImageJiLuDialog(this, imageData, 0, ImageLoader.getInstance());
            bigImageJiLuDialog.show();
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
           /* startActivity(new Intent(this, XiangQingActivity.class));*/
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
