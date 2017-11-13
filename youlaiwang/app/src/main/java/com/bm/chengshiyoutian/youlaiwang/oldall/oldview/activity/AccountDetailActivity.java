package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectBefore;
import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.PopAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.DemandingCertificatesBeans;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.PoPBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.datetimepick.DatePickerPopupUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.Bimp;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.TestPicActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.Base64;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GlobalData;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.ProgressDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/4/19 10:48
 * @Description 台账明细
 */
public class AccountDetailActivity extends FragmentActivity implements View.OnClickListener, DatePickerPopupUtil.DateCallBck, View.OnTouchListener, View.OnLongClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right_two)
    TextView tv_right_two;
    @Bind(R.id.iv_photo)
    ImageView iv_photo;
    @Bind(R.id.iv_add_photo)
    ImageView iv_add_photo;
    @Bind(R.id.tv_photo_num)
    TextView tv_photo_num;
    @Bind(R.id.tv_cooking_type)
    TextView tv_cooking_type;
    @Bind(R.id.et_id)
    EditText et_id;
    @Bind(R.id.iv_jian_num)
    ImageView iv_jian_num;
    @Bind(R.id.tv_goods_num)
    TextView tv_goods_num;
    @Bind(R.id.iv_add_num)
    ImageView iv_add_num;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.bt_save)
    Button bt_save;
    @Bind(R.id.rl_photo_view)
    RelativeLayout rl_photo_view;
    @Bind(R.id.tv_unit)
    TextView tv_unit;
    @Bind(R.id.sv_whole)
    ScrollView sv_whole;

    private ProgressDialog progressDialog;
    private GetImg img;
    private Bitmap bitmap;
    private List<Bitmap> bitmapData;
    private PopupWindow popupWindow;
    private List<PoPBean> popListGoodsClass;//商品类型
    private List<PoPBean> popListUnitList;//商品规格
    private String goodsClassid;//商品类型id
    private String unitListid;////商品规格id
    private DatePickerPopupUtil datePickerPopupUtil;
    private String selectType;//选择的类型
    private String supplierName;//供货商名字
    private String supplierTel;//供货商电话
    private ArrayList<String> bitmapDrr;//上一界面上传的图片string类型
    private ArrayList<Bitmap> beforBitmapData;//上一界面上传的图片Bitmap类型
    private String httpType = "save";//save 表示保存  commit 表示 提交
    private int changeNum = 0;//用于判断加减数据
    private DemandingCertificatesBeans demandingCertificatesBeans;//保存了供应商信息
    private Handler mHandler = new Handler();
    Runnable test = new Runnable() {

        @Override
        public void run() {// 主线程
            // 过50m秒钟再执行run()方法
            if (changeNum == 0) {
                changeGoodsNum(0);
            } else if (changeNum == 1) {
                changeGoodsNum(1);
            }
            mHandler.postDelayed(this, 50);
        }
    };

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

    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_account_detail);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        datePickerPopupUtil = new DatePickerPopupUtil(getApplicationContext(), AccountDetailActivity.this);
        title.setText("台账明细");
        tv_right_two.setVisibility(ImageView.VISIBLE);
        tv_right_two.setText("提交");
        setViewListener();
        img = new GetImg(this);
        getGoodsClass();
        //getUnitList();
        getIntentData();
    }

    private void setViewListener() {
        ivLeft.setOnClickListener(this);
        tv_right_two.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        iv_add_photo.setOnClickListener(this);
        tv_cooking_type.setOnClickListener(this);
        iv_jian_num.setOnClickListener(this);
        iv_jian_num.setOnTouchListener(this);
        iv_jian_num.setOnLongClickListener(this);
        iv_add_num.setOnClickListener(this);
        iv_add_num.setOnTouchListener(this);
        iv_add_num.setOnLongClickListener(this);
        bt_save.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_unit.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectType = intent.getStringExtra("selectType");
            supplierName = intent.getStringExtra("name");
            supplierTel = intent.getStringExtra("phone");
            if (TextUtils.isEmpty(supplierTel)) {
                supplierTel = "";
            }
            bitmapDrr = intent.getStringArrayListExtra("bitmapDrr");
            if (bitmapDrr != null && bitmapDrr.size() > 0) {
                beforBitmapData = new ArrayList<>();
                for (int i = 0; i < bitmapDrr.size(); i++) {
                    try {
                        beforBitmapData.add(Bimp.revitionImageSize(bitmapDrr.get(i)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Bimp.drr != null && Bimp.drr.size() > 0) {
            if (bitmapData == null) {
                bitmapData = new ArrayList<>();
            }
            bitmapData.clear();
            for (int i = 0; i < Bimp.drr.size(); i++) {
                try {
                    bitmapData.add(Bimp.revitionImageSize(Bimp.drr.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bitmapData != null && bitmapData.size() > 0) {
                if (bitmapData.size() >= MyApplication.getInstance().imageNum) {
                    iv_add_photo.setVisibility(View.GONE);
                } else {
                    iv_add_photo.setVisibility(View.VISIBLE);
                }
                iv_photo.setImageBitmap(bitmapData.get(bitmapData.size() - 1));
                tv_photo_num.setText(bitmapData.size() + "张");
                rl_photo_view.setVisibility(View.VISIBLE);
            } else {
                iv_photo.setImageBitmap(null);
                iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
                tv_photo_num.setText("0张");
                iv_add_photo.setVisibility(View.VISIBLE);
                rl_photo_view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取商品类型
     */
    private void getGoodsClass() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "getGoodsClass", params, config, this);
    }

    /**
     * 获取商品规格
     */
    private void getUnitList() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "getUnitList", params, config, this);
    }

    /**
     * 提交数据 TypeId=1:生产;2：通用;3：餐饮,
     * supplierName:供货商名，
     * supplierTel:供货商电话,
     * supplierLicense:供货商证件,
     * bill:票据,
     * goodsTypeId:商品类别,
     * goodsName:商品名称,
     * number:商品数量,
     * unit:商品规格(单位),
     * shelfLife:保质期
     */
    private void insertPurchase(String TypeId, String supplierName, String supplierTel, String supplierLicense,
                                String bill, String goodsTypeId, String goodsName, String number, String unit, String shelfLife) {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("TypeId", TypeId);
        params.put("supplierName", supplierName);
        params.put("supplierTel", supplierTel);
        params.put("supplierLicense", supplierLicense);
        params.put("bill", bill);
        params.put("goodsTypeId", goodsTypeId);
        params.put("goodsName", goodsName);
        params.put("number", number);
        params.put("unitId", unit);
        params.put("shelfLife", shelfLife);
        params.put("restaurantId", MyApplication.getInstance().getUser().id);
        InternetConfig config = new InternetConfig();
        config.setKey(2);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "InsertPurchase", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0: //获取商品类型
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONArray data = jsonObject.optJSONArray("data");
                        int length = data.length();
                        popListGoodsClass = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = data.optJSONObject(i);
                            popListGoodsClass.add(new PoPBean(object.optString("Id"), object.optString("unitType"), object.optString("className")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1://获取商品规格
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONArray data = jsonObject.optJSONArray("data");
                        int length = data.length();
                        popListUnitList = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = data.optJSONObject(i);
                            popListUnitList.add(new PoPBean(object.optString("Id"), object.optString("unitName")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2://提交或保存信息
                try {
                    String contentAsString = entity.getContentAsString();
                    JSONObject jsonObject = new JSONObject(contentAsString);
                    if (0 == jsonObject.getInt("status")) {
                        JSONArray data = jsonObject.optJSONArray("data");
                        goodsClassid = "";
                        unitListid = "";
                        if ("save".equals(httpType)) {
                            String msg = jsonObject.optString("msg");
                            MyToastUtils.show(getApplicationContext(), msg);
                            tv_cooking_type.setText("请选择商品类型");
                            tv_cooking_type.setTextColor(getResources().getColor(R.color.hint));
                            et_id.setText("");
                            tv_goods_num.setText("10");
                            tv_time.setText("请输入保质期限");
                            tv_unit.setText("规格");
                            tv_time.setTextColor(getResources().getColor(R.color.hint));
                            MyToastUtils.show(getApplicationContext(), "操作成功");
                        } else {
                            String msg = jsonObject.optString("msg");
                            MyToastUtils.show(getApplicationContext(), msg);
                            Bimp.drr.clear();
                            if (bitmapData != null) {
                                bitmapData.clear();
                            }
                            if (beforBitmapData != null) {
                                beforBitmapData.clear();
                            }
                            MyToastUtils.show(getApplicationContext(), "操作成功");
                            Intent intent = new Intent(getApplicationContext(), SuoZhengSuoPiaoRecordActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        saveGongYingShangName();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    //保存输入的供应商的信息  bitmapDrr图片信息
    private void saveGongYingShangName() {
        Gson gson = new Gson();
        String supplierNameString = GlobalData.getData(getApplicationContext(), "supplier", "");
        if (!TextUtils.isEmpty(supplierNameString) && supplierNameString != "") {
            demandingCertificatesBeans = gson.fromJson(supplierNameString, DemandingCertificatesBeans.class);
        }
        if (demandingCertificatesBeans == null) {
            demandingCertificatesBeans = new DemandingCertificatesBeans();
            demandingCertificatesBeans.demandingCertificates.add(0, demandingCertificatesBeans.new DemandingCertificates(supplierName, supplierTel, bitmapDrr, ""));
        } else {
            //判断这次提交的内容是否已经在以前提交过了（主要判断的是供应商全称）
            boolean flagIsHave = false;
            for (DemandingCertificatesBeans.DemandingCertificates demandingCertificates : demandingCertificatesBeans.demandingCertificates) {
                if (supplierName.equals(demandingCertificates.name)) {
                    flagIsHave = true;
                    if (demandingCertificates.image != null) {
                        demandingCertificates.image.clear();
                    }
                    demandingCertificates.phone = supplierTel;
                    demandingCertificates.image = bitmapDrr;
                    break;
                }
            }
            if (!flagIsHave) {
                demandingCertificatesBeans.demandingCertificates.add(0, demandingCertificatesBeans.new DemandingCertificates(supplierName, supplierTel, bitmapDrr, ""));
            }
        }
        String message = gson.toJson(demandingCertificatesBeans);
        GlobalData.cacheData(getApplicationContext(), "supplier", message);
        /*String supplierNameString = GlobalData.getData(getApplicationContext(), "supplierName", "");
        StringBuffer stringBuffer = new StringBuffer();
        boolean flagIsHave = false;
        if (!TextUtils.isEmpty(supplierNameString)) {
            String[] split = supplierNameString.split(",");
            if (split != null) {
                for (int i = 0; i < split.length; i++) {
                    stringBuffer.append(split[i]);
                    if (supplierName.equals(split[i])) {
                        flagIsHave = true;
                    }
                    if (i != split.length - 1) {
                        stringBuffer.append(",");
                    }
                }
            }
            if (!flagIsHave) {
                stringBuffer.append(",");
                stringBuffer.append(supplierName);
            }
        } else {
            stringBuffer.append(supplierName);
        }
        GlobalData.cacheData(getApplicationContext(), "supplierName", stringBuffer.toString());*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                if (Bimp.drr != null) {
                    Bimp.drr.clear();
                }
                finish();
                break;
            case R.id.tv_right_two://提交
                httpType = "commit";
                submit();
                break;
            case R.id.iv_add_photo://添加图片
                MyApplication.getInstance().imageNum = 100;
                selectPhotoDialog();
                break;
            case R.id.iv_photo://查看大图
                Constants.BIMPTYPE = "0";
                if (bitmapData != null && bitmapData.size() > 0) {
                    BigImageDialog bigImageDialog = new BigImageDialog(this,Constants.BIMPTYPE, bitmapData, 0, new BigImageDialog.RefreshBitmapData() {
                        @Override
                        public void refreshData(List<Bitmap> comment_pics) {
                            refreshBitmapData(comment_pics);
                        }
                    });
                    bigImageDialog.show();
                }
                break;
            case R.id.tv_cooking_type://选择商品类型
                chooseCookType();
                break;
            case R.id.tv_unit://选择商品规格
                //chooseUnitType();
                break;
            case R.id.iv_jian_num://减商品数量
                changeGoodsNum(0);
                break;
            case R.id.iv_add_num:
                changeGoodsNum(1);
                break;
            case R.id.bt_save://保存并继续
                httpType = "save";
                saveNext();
                break;
            case R.id.tv_time:
                Calendar c = Calendar.getInstance();
                datePickerPopupUtil.setCurrent(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerPopupUtil.getDatePicker().showAtLocation(
                        tv_time,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    private String getToDataTime() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + 1 + "-" + c.get(Calendar.DAY_OF_MONTH);
    }

    private void refreshBitmapData(List<Bitmap> comment_pics) {
        //删除图片后做更新处理
        bitmapData = comment_pics;
        if (bitmapData != null && bitmapData.size() > 0) {
            if (bitmapData.size() >= MyApplication.getInstance().imageNum) {
                iv_add_photo.setVisibility(View.GONE);
            } else {
                iv_add_photo.setVisibility(View.VISIBLE);
            }
            iv_photo.setImageBitmap(bitmapData.get(bitmapData.size() - 1));
            tv_photo_num.setText(bitmapData.size() + "张");
            rl_photo_view.setVisibility(View.VISIBLE);
        } else {
            iv_photo.setImageBitmap(null);
            iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
            tv_photo_num.setText("0张");
            iv_add_photo.setVisibility(View.VISIBLE);
            rl_photo_view.setVisibility(View.GONE);
        }
    }

    /**
     * 弹出popwindows 选择商品类型
     */
    private void chooseCookType() {
        Drawable drawable = MyUtils.getDrawa(this, 2);
        tv_cooking_type.setCompoundDrawables(null, null, drawable, null);
        ListView listView = new ListView(this);
        listView.setBackgroundColor(Color.WHITE);
        listView.setDividerHeight(1);
        listView.setLayoutParams(new ViewGroup.LayoutParams(tv_cooking_type.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        popupWindow = new PopupWindow(listView, tv_cooking_type.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(tv_cooking_type);
        listView.setAdapter(new PopAdapter(popListGoodsClass, this, 2));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoPBean poPBean = popListGoodsClass.get(position);
                tv_cooking_type.setText(poPBean.title);
                tv_unit.setText(poPBean.channel_id);
                goodsClassid = poPBean.id;
                popupWindow.dismiss();
                Drawable drawable = MyUtils.getDrawa(AccountDetailActivity.this, 1);
                tv_cooking_type.setCompoundDrawables(null, null, drawable, null);
                tv_cooking_type.setTextColor(Color.WHITE);
            }
        });
        //pop消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //pop消失就切换箭头
                tv_cooking_type.setCompoundDrawables(null, null, MyUtils.getDrawa(AccountDetailActivity.this, 1), null);
            }
        });
    }

    /**
     * 弹出popwindows 选择商品规格
     */
    private void chooseUnitType() {
        //Drawable drawable = MyUtils.getDrawa(this, 2);
        //tv_unit.setCompoundDrawables(null, null, drawable, null);
        ListView listView = new ListView(this);
        listView.setBackgroundColor(Color.WHITE);
        listView.setDividerHeight(1);
        listView.setLayoutParams(new ViewGroup.LayoutParams(tv_unit.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        popupWindow = new PopupWindow(listView, tv_unit.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(tv_unit);
        listView.setAdapter(new PopAdapter(popListUnitList, this, 2));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoPBean poPBean = popListUnitList.get(position);
                tv_unit.setText(poPBean.title);
                unitListid = poPBean.id;
                popupWindow.dismiss();
                //Drawable drawable = MyUtils.getDrawa(AccountDetailActivity.this, 1);
                //tv_unit.setCompoundDrawables(null, null, drawable, null);
                tv_unit.setTextColor(getResources().getColor(R.color.yello));
            }
        });
    }

    private void changeGoodsNum(int type) {
        String goodsNum = tv_goods_num.getText().toString();
        if (TextUtils.isEmpty(goodsNum) || "".equals(goodsNum)) {
            goodsNum = "1";
        }
        int goodsNumInt = Integer.parseInt(goodsNum);
        if (type == 0) {
            if (goodsNumInt <= 1) {
                goodsNumInt = 1;
            } else {
                goodsNumInt--;
            }
        } else {
            goodsNumInt++;
        }
        tv_goods_num.setText(String.valueOf(goodsNumInt));
    }

    //保存并继续
    private void saveNext() {
        String cookType = tv_cooking_type.getText().toString();
        String detailsName = et_id.getText().toString();
        String goodsNum = tv_goods_num.getText().toString();
        String time = tv_time.getText().toString();
        if (bitmapData == null || bitmapData.size() == 0) {
            MyToastUtils.show(getApplicationContext(), "请上传本次购买所有相关票据");
            return;
        }
        if (TextUtils.isEmpty(goodsClassid)) {
           /* MyToastUtils.show(getApplicationContext(), "请选择商品类别");
            return;*/
            goodsClassid = "";
        }
        if (TextUtils.isEmpty(detailsName)) {
           /* MyToastUtils.show(getApplicationContext(), "请输入商品具体名称");
            return;*/
            detailsName = "";
        }
        if (TextUtils.isEmpty(unitListid)) {
           /* MyToastUtils.show(getApplicationContext(), "请选择商品规格");
            return;*/
            unitListid = "";
        }
        if ("请输入保质期限".equals(time)) {
           /* MyToastUtils.show(getApplicationContext(), "请输入保质期限");
            return;*/
            time = "";
        }
        insertPurchase(selectType, supplierName, supplierTel, bitmapStringData(beforBitmapData), bitmapStringData(bitmapData),
                goodsClassid, detailsName, goodsNum, unitListid, time);
    }

    //提交
    private void submit() {
        String cookType = tv_cooking_type.getText().toString();
        String detailsName = et_id.getText().toString();
        String goodsNum = tv_goods_num.getText().toString();
        String time = tv_time.getText().toString();
        if (bitmapData == null || bitmapData.size() == 0) {
            MyToastUtils.show(getApplicationContext(), "请上传本次购买所有相关票据");
            return;
        }
        if (TextUtils.isEmpty(goodsClassid)) {
           /* MyToastUtils.show(getApplicationContext(), "请选择商品类别");
            return;*/
            goodsClassid = "";
        }
        if (TextUtils.isEmpty(detailsName)) {
           /* MyToastUtils.show(getApplicationContext(), "请输入商品具体名称");
            return;*/
            detailsName = "";
        }
        if (TextUtils.isEmpty(unitListid)) {
           /* MyToastUtils.show(getApplicationContext(), "请选择商品规格");
            return;*/
            unitListid = "";
        }
        if ("请输入保质期限".equals(time)) {
           /* MyToastUtils.show(getApplicationContext(), "请输入保质期限");
            return;*/
            time = "";
        }
        insertPurchase(selectType, supplierName, supplierTel, bitmapStringData(beforBitmapData), bitmapStringData(bitmapData),
                goodsClassid, detailsName, goodsNum, unitListid, time);
    }

    /**
     * 选择图片
     */
    private void selectPhotoDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_photo, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        ImageView iv_cancel_popu = (ImageView) view.findViewById(R.id.iv_cancel_popu);
        TextView takePhoto = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView albume = (TextView) view.findViewById(R.id.tv_albume);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        iv_cancel_popu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            //拍照
            @Override
            public void onClick(View v) {
                img.goToCamera(AccountDetailActivity.this);//通过拍照获取图片
                popupWindow.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                MyApplication.getInstance().selectPhoto = 1;
                Intent intent = new Intent(getApplicationContext(), TestPicActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            //取消
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (bitmapData == null) {
            bitmapData = new ArrayList<>();
        }
        if (requestCode == GetImg.go_to_camera_code && resultCode == RESULT_OK) { //通过拍照获取图片
            String path = "";
            path = img.file_save.getAbsolutePath();
            Bimp.drr.add(path);
        }
        if (requestCode == GetImg.go_to_gallery_code && resultCode == RESULT_OK) {
            String path = img.getGalleryPath(data);
            bitmap = getSmallBitmap(path);
            iv_photo.setImageBitmap(bitmap);
        }
    }

    /**
     * 获取图片
     *
     * @param filePath
     */
    private Bitmap getSmallBitmap(String filePath) {
        File file = new File(filePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = img.calculateInSampleSize(options, 800, 360);
        options.inJustDecodeBounds = false;

        /**
         * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
         */
        int angle = GetImg.readPictureDegree(filePath);

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        BufferedOutputStream baos = null;

        bitmap = GetImg.rotaingImageView(angle, bitmap);
        if (bitmap != null) {
            try {
                baos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    @Override
    public void complete() {
        String time = datePickerPopupUtil.birthday;
        if (time != null) {
            tv_time.setText(getDate(time, "yyyy年MM月dd日", "yyyy-MM-dd"));
            tv_time.setTextColor(Color.WHITE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Bimp.drr != null) {
                Bimp.drr.clear();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private String bitmapStringData(List<Bitmap> bitmapList) {
        if (bitmapList != null && bitmapList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bitmapList.size(); i++) {
                String stringBitmap = bitmapToBase64(bitmapList.get(i));
                sb.append(stringBitmap);
                if (i != bitmapList.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        return "";
    }

    private String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encode(bitmapBytes);
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return result;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                mHandler.removeCallbacks(test);
                break;
        }
        return false;
    }

    private void AutomaticCarousel() {
        // 让图片循环播放
        mHandler.postDelayed(test, 50);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.iv_jian_num:
                changeNum = 0;
                break;
            case R.id.iv_add_num:
                changeNum = 1;
                break;
        }
        AutomaticCarousel();
        return false;
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