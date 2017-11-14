package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.CacheStoreImageDrr;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.PoPBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.datetimepick.DatePickerPopupUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.Bimp;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.TestPicActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.Base64;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GlobalData;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.ShaoMiaoUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.SheShiQuDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.WheelView;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.zxing.CaptureActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 资料编辑
 * Created by youj on 2016/1/13.
 */
public class EditInfoActivity extends Activity implements View.OnClickListener, ImageLoadingListener ,DatePickerPopupUtil.DateCallBck{

    private final int SCANNIN_GREQUEST_CODE = 1023;
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_up_photo)
    ImageView ivUpPhoto;
    @Bind(R.id.et_restaurant_name)
    EditText etRestaurantName;
    @Bind(R.id.tv_cooking_type)
    TextView tvCookingType;
//    @Bind(R.id.et_restaurant_address)
//    EditText etRestaurantAddress;
    @Bind(R.id.et_restaurant_phone)
    EditText etRestaurantPhone;
    @Bind(R.id.bt_register)
    Button btSubmit;
//    @Bind(R.id.tv_area)
//    TextView tvArea;
    @Bind(R.id.tv_restaurant_name_verify)
TextView tvRestaurantNameVerify;
    @Bind(R.id.tv_restaurant_address_verify)
    TextView tvRestaurantAddressVerify;
    @Bind(R.id.tv_restaurant_phone_verify)
    TextView tvRestaurantPhoneVerify;
    @Bind(R.id.tv_register_status)
    TextView tvStatus;
    @Bind(R.id.tv_cooking_type_verify)
    TextView tvCookingTypeVerify;
//    @Bind(R.id.tv_district_tverify)
//    TextView tvDistrictTverify;
    @Bind(R.id.et_licenseNo)
    EditText et_licenseNo;
    //上传图片控件
    @Bind(R.id.ll_add_photo)
    LinearLayout ll_add_photo;
    @Bind(R.id.rl_photo_view)
    RelativeLayout rl_photo_view;
    @Bind(R.id.tv_photo_num)
    TextView tv_photo_num;
    @Bind(R.id.iv_photo)
    ImageView iv_photo;
//    @Bind(R.id.tv_street)
//    TextView tv_street;//街道
    @Bind(R.id.tv_loading)
TextView tv_loading;//正在加载
    @Bind(R.id.et_business_num)
    EditText et_business_num;//营业执照
    @Bind(R.id.iv_shaomiao)
    ImageView iv_shaomiao;//扫描二维码


    @Bind(R.id.tv_user_expirydate_input)
    TextView tv_user_expirydate_input;

    @Bind(R.id.tv_business_expirydate_input)
    TextView tv_business_expirydate_input;

    private GetImg img;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;
    private String proIdStr;
    private String cityIdStr;
    private String areaIdStr;
    private String areaIdStrTemp = "";//临时的区id
    private String id;
    private String[] proName;
    private String[] proId;
    private String[] cityName;
    private String[] cityId;
    private String[] areaName;
    private String[] areaId;
    private SheShiQuDialog chengShiDialog;
    private ArrayList<PoPBean> popList;
    //时间有效期，1是食品有效期   2是营业执照有效期
    private int dateType;
    /**
     * 菜系id
     */
    private String cookingTypeId = "";
    private PopupWindow popupWindow;

    private List<Bitmap> bitmapData;//选择的图片bitmap
    private String streetId = "0";//街道id
    private ArrayList<PoPBean> mPoPBeansStreetList;//街道
    private ArrayList<String> mImageSave = new ArrayList<>();//保存http下载的图片
    private DatePickerPopupUtil datePickerPopupUtil;
    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register_next1);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showImage();
    }

    private void showImage() {
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
                    ll_add_photo.setVisibility(View.GONE);
                } else {
                    ll_add_photo.setVisibility(View.VISIBLE);
                }
                iv_photo.setImageBitmap(bitmapData.get(bitmapData.size() - 1));
                tv_photo_num.setText(bitmapData.size() + "张");
                rl_photo_view.setVisibility(View.VISIBLE);
            } else {
                iv_photo.setImageBitmap(null);
                iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
                tv_photo_num.setText("0张");
                ll_add_photo.setVisibility(View.VISIBLE);
                rl_photo_view.setVisibility(View.GONE);
            }
        } else {
            if (bitmapData != null) {
                bitmapData.clear();
            }
            iv_photo.setImageBitmap(null);
            iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
            tv_photo_num.setText("0张");
            ll_add_photo.setVisibility(View.VISIBLE);
            rl_photo_view.setVisibility(View.GONE);
        }
    }

    private void init() {
        datePickerPopupUtil = new DatePickerPopupUtil(getApplicationContext(), EditInfoActivity.this);


//        tvArea.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        btSubmit.setText(getString(R.string.ensure));
        title.setText("资料编辑");
        ivLeft.setOnClickListener(this);
        ivUpPhoto.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        tvCookingType.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        ll_add_photo.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        tv_user_expirydate_input.setOnClickListener(this);
        tv_business_expirydate_input.setOnClickListener(this);
//        tv_street.setOnClickListener(this);
        iv_shaomiao.setOnClickListener(this);
        getIndustryList();
        setData();
    }

    /**
     * 给控件设置数据
     */
    private void setData() {
        RestaurantBean bean = MyApplication.getInstance().getUser();
        ImageLoader imageLoader = ImageLoader.getInstance();
        getCacheImageData(bean, imageLoader);
        String restaurantName = bean.restaurantName;
        etRestaurantName.setText(restaurantName);
        etRestaurantName.setSelection(restaurantName.length());

//        setTextMessage(tv_street, bean.streetName);
        tv_user_expirydate_input.setText(bean.licenseDate);
        tv_business_expirydate_input.setText(bean.licenseTime);


        setTextMessage(tvCookingType, bean.industryName);
        if (!TextUtils.isEmpty(bean.licenseNo) && !"null".equals(bean.licenseNo)) {
            et_licenseNo.setText(bean.licenseNo);
        }
        if (!MyUtils.isEmpty(bean.licenseID)) {
            et_business_num.setText(bean.licenseID);
        }

//        tvArea.setText(bean.proName + " " + bean.cityName + " " + bean.areaName);
//        etRestaurantAddress.setText(bean.address);

        etRestaurantPhone.setText(bean.restaurantPhone);
        cookingTypeId = bean.industryId;
        areaIdStr = bean.areaId;
        areaIdStrTemp = areaIdStr;
        cityIdStr = bean.cityId;
        proIdStr = bean.proId;
        streetId = bean.streetid;
        getStreetList(areaIdStr);
        String photoUrl = bean.photoUrl;
        if (!TextUtils.isEmpty(photoUrl)) {
            imageLoader.displayImage(photoUrl, ivUpPhoto, MyApplication.getInstance().getOptions());
        }
    }

    //读取保存的图片信息
    private void getCacheImageData(RestaurantBean bean, ImageLoader imageLoader) {
        String cacheStoreImageDrr = GlobalData.getData(getApplicationContext(), "cacheStoreImageDrr", "");
        if (TextUtils.isEmpty(cacheStoreImageDrr) || "".equals(cacheStoreImageDrr)) {
            showHttpImage(bean, imageLoader);
        } else {
            Gson gson = new Gson();
            CacheStoreImageDrr cacheStoreImageDrrBean = gson.fromJson(cacheStoreImageDrr, CacheStoreImageDrr.class);
            if (cacheStoreImageDrrBean != null) {
                if (bean.id.equals(cacheStoreImageDrrBean.userId)) {
                    if (bean.licenseImg != null) {
                        if (bean.licenseImg.size() != cacheStoreImageDrrBean.bitmapDrr.size()) {
                            showHttpImage(bean, imageLoader);
                        } else {
                            boolean isFileExist = true;
                            for (String fileName : cacheStoreImageDrrBean.bitmapDrr) {
                                File file = new File(fileName);
                                if (!file.exists()) {
                                    isFileExist = false;
                                    break;
                                }
                            }
                            if (isFileExist) {
                                iv_photo.setClickable(true);
                                tv_loading.setVisibility(View.GONE);
                                Bimp.drr.addAll(cacheStoreImageDrrBean.bitmapDrr);
                            } else {
                                showHttpImage(bean, imageLoader);
                            }
                        }
                    }
                } else {
                    showHttpImage(bean, imageLoader);
                }
            } else {
                showHttpImage(bean, imageLoader);
            }
        }
    }

    private void setTextMessage(TextView textview, String message) {
        if (TextUtils.isEmpty(message) || "null".equals(message)) {
            textview.setText("");
        } else {
            textview.setText(message);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                if (Bimp.drr != null) {
                    Bimp.drr.clear();
                }
                deleteSaveImage();
                finish();
                break;
            case R.id.iv_up_photo://选择照片
                selectPhotoDialog();
                break;
            case R.id.bt_register: //点击确定 提交信息
                submit();
                break;
            case R.id.tv_area://点击所在地区
                initProvinceDatas();
                break;
            case R.id.btnCancel:
                chengShiDialog.dismiss();
                break;
            case R.id.tv_cooking_type:
                chooseCookType(); //选择菜系
                break;
//            case R.id.btnSubmit:
//                int[] arr = chengShiDialog.getSeletedIndexArr();
//                proIdStr = proId[arr[0]];
//                cityIdStr = cityId[arr[1]];
//                areaIdStr = areaId[arr[2]];
//                tvArea.setText(proName[arr[0]] + " " + cityName[arr[1]] + " " + areaName[arr[2]]);
//                if (mPoPBeansStreetList != null) {
//                    mPoPBeansStreetList.clear();
//                }
//                if (!areaIdStrTemp.equals(areaIdStr)) {
//                    tv_street.setText("");
//                    streetId = "0";
//                }
//                getStreetList(areaIdStr);
//                chengShiDialog.dismiss();
//                break;
            case R.id.ll_add_photo://添加图片
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
//            case R.id.tv_street://街道
//                chooseStreet();
//                break;
            case R.id.iv_shaomiao://扫描
                Intent intent = new Intent();
                intent.setClass(EditInfoActivity.this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
            case R.id.tv_user_expirydate_input://食品经营许可证有效期
                dateType = 1;
                Calendar c = Calendar.getInstance();
                datePickerPopupUtil.setCurrent(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerPopupUtil.getDatePicker().showAtLocation(
                        tv_user_expirydate_input,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_business_expirydate_input://证件有效期
                dateType = 2;
                Calendar c2 = Calendar.getInstance();
                datePickerPopupUtil.setCurrent(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH));
                datePickerPopupUtil.getDatePicker().showAtLocation(
                        tv_business_expirydate_input,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 提交信息
     */
    private void submit() {
        String licenseNo = et_licenseNo.getText().toString();
        String businessNum = et_business_num.getText().toString();//营业执照
        String restaurantName = etRestaurantName.getText().toString().trim();//餐厅名
//        String restaurantAddress = etRestaurantAddress.getText().toString().trim();//餐厅地址
        if (bitmapData == null || bitmapData.size() == 0) {
            MyToastUtils.show(getApplicationContext(), getString(R.string.register_data));
            return;
        }
        if (TextUtils.isEmpty(restaurantName) || "null".equals(restaurantName)) { //验证餐厅名是否为空
            MyToastUtils.show(getApplicationContext(), getString(R.string.input_restaurant_name));
            return;
        }
        if (TextUtils.isEmpty(cookingTypeId) || "null".equals(cookingTypeId)) { //验证是否选择主体业态
            MyToastUtils.show(getApplicationContext(), "请选择所属主体业态");
            return;
        }
        if (TextUtils.isEmpty(areaIdStr)) { //验证是否选择地区
            MyToastUtils.show(getApplicationContext(), "请选择省-市-区/县");
            return;
        }
        /*if (TextUtils.isEmpty(streetId)) { //街道
            MyToastUtils.show(getApplicationContext(), getString(R.string.street_address));
            return;
        }*/
//        if (TextUtils.isEmpty(restaurantAddress) || "null".equals(restaurantAddress)) {//验证餐厅地址是否为空
//            MyToastUtils.show(getApplicationContext(), getString(R.string.input_restaurant_address));
//            return;
//        }
//        updataDataMessage(licenseNo, restaurantName, restaurantAddress, businessNum);

        String licenseDate = tv_user_expirydate_input.getText().toString();

        String licenseTime = tv_business_expirydate_input.getText().toString();

        if (TextUtils.isEmpty(licenseTime)){
            MyToastUtils.show(getApplicationContext(), "请输入执照或证件有效期");
            return;
        }

        updataDataMessage(licenseNo, restaurantName,businessNum,licenseDate,licenseTime);
    }

    /**
     * 更新数据
     *
     * @param licenseNo
     * @param restaurantName
     */
    private void updataDataMessage(String licenseNo, String restaurantName, String licenseID, String licenseDate, String licenseTime) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }


        LinkedHashMap<String, String> params = new LinkedHashMap<>();

        params.put("restId", MyApplication.getInstance().getUser().id); //用户id
        params.put("licenseImg", MyUtils.bitmapStringData(bitmapData)); //营运执照图片
        params.put("licenseID", licenseID != null ? licenseID : "");//营业执照
        params.put("licenseNo", licenseNo != null ? licenseNo : ""); //许可证
        params.put("title", restaurantName != null ? restaurantName : ""); //餐厅名称
        params.put("industryId", cookingTypeId); //行业id
        params.put("licenseDate", licenseDate); //行业id
        params.put("licenseTime", licenseTime); //行业id
//        params.put("proid", proIdStr); //省id
//        params.put("cityid", cityIdStr); //市id
//        params.put("areaid", areaIdStr); //区id
//        params.put("streetid", streetId); //街道id
//        params.put("address", restaurantAddress != null ? restaurantAddress : ""); //餐厅地址

        InternetConfig config = new InternetConfig();
        config.setKey(6);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "UpdateRestInfoForOne5", params, config, this);
    }

    /**
     * 获取街道
     */
    private void getStreetList(String areaCode) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("areaCode", areaCode);
        InternetConfig config = new InternetConfig();
        config.setKey(7);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetStreetList", params, config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0: // 获取省信息
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        JSONArray ds = data.optJSONArray("ds");
                        int length = ds.length();
                        proName = new String[length + 2];
                        proName[0] = "";
                        proId = new String[length + 2];
                        proId[0] = "-1";
                        for (int i = 0; i < length; i++) {
                            JSONObject object = ds.optJSONObject(i);
                            proName[i + 1] = object.optString("ProName");
                            proId[i + 1] = object.optString("ProID");
                        }
                        proName[length + 1] = "";
                        showSelectCity();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1: //获取市信息
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        JSONArray ds = data.optJSONArray("ds");
                        int length = ds.length();
                        cityName = new String[length + 2];
                        cityId = new String[length + 2];
                        cityName[0] = "";
                        for (int i = 0; i < length; i++) {
                            JSONObject object = ds.optJSONObject(i);
                            cityId[i + 1] = object.optString("CityID");
                            cityName[i + 1] = object.optString("CityName");
                        }
                        cityName[length + 1] = "";
                        chengShiDialog.setShis(Arrays.asList(cityName));
                        areaName = new String[3];
                        for (int i = 0; i < 3; i++) {
                            areaName[i] = "";
                        }
                        chengShiDialog.setQus(Arrays.asList(areaName));
                        updateAreas(cityId[1]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2: //获取区信息
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        JSONArray ds = data.optJSONArray("ds");
                        int length = ds.length();
                        areaName = new String[length + 2];
                        areaId = new String[length + 2];
                        areaName[0] = "";
                        areaId[0] = "";
                        for (int i = 0; i < length; i++) {
                            JSONObject object = ds.optJSONObject(i);
                            areaName[i + 1] = object.optString("DisName");
                            areaId[i + 1] = object.optString("Id");
                        }
                        areaName[length + 1] = "";
                        chengShiDialog.setQus(Arrays.asList(areaName));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3://选择主体业态
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONArray data = jsonObject.optJSONArray("data");
                        int length = data.length();
                        popList = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = data.optJSONObject(i);
                            //popList.add(new PoPBean(object.optString("id"), object.optString("title")));
                            popList.add(new PoPBean(object.optString("Id"), object.optString("IndustryName")));
                        }
                    } else {
                        MyToastUtils.show(this, "数据获取失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 6://更新数据
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    String status = jsonObject.optString("status");
                    String msg = jsonObject.optString("msg");
                    MyToastUtils.show(getApplicationContext(), msg);
                    if ("0".equals(status)) {
                        MyApplication.getInstance().isUpdataPersionMessage = true;
                        if (Bimp.drr != null) {
                            cacheStoreImageDrr(MyApplication.getInstance().getUser().id, Bimp.drr);
                            Bimp.drr.clear();
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 7://获取街道
                getStreetListData(entity);
                break;
            default:
                break;
        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    //获取街道
    private void getStreetListData(ResponseEntity entity) {
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());
            String status = jsonObject.optString("status");
            if ("0".equals(status)) {
                String data = jsonObject.optString("data");
                JSONObject jsonData = new JSONObject(data);
                JSONArray ds = jsonData.optJSONArray("ds");
                if (ds != null && ds.length() > 0) {
                    mPoPBeansStreetList = new ArrayList<>();
                    int dsLength = ds.length();
                    for (int i = 0; i < dsLength; i++) {
                        JSONObject object = (JSONObject) ds.get(i);
                        mPoPBeansStreetList.add(new PoPBean(object.optString("Id"), object.optString("DisName")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择城市
     */
    private void showSelectCity() {
        chengShiDialog = new SheShiQuDialog(this);
        chengShiDialog.setConfirmOnClickListener(this);
        chengShiDialog.setCancelOnClickListener(this);
        chengShiDialog.setShens(Arrays.asList(proName));
        updateCities("1");
        chengShiDialog.setSheOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                cityName = new String[3];
                for (int i = 0; i < 3; i++) {
                    cityName[i] = "";
                }
                chengShiDialog.setShis(Arrays.asList(cityName));
                String s = proId[selectedIndex];
                updateCities(s);
            }
        });

        chengShiDialog.setShiOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                areaName = new String[3];
                for (int i = 0; i < 3; i++) {
                    areaName[i] = "";
                }
                chengShiDialog.setQus(Arrays.asList(areaName));
                updateAreas(cityId[selectedIndex]);
            }
        });
        chengShiDialog.show();
    }

    /**
     * 获取区信息
     */
    private void updateAreas(String cityid) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("cityCode", cityid);
        InternetConfig config = new InternetConfig();
        config.setKey(2);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetAreaList", params, config, this);
    }

    /**
     * 获取市信息
     */
    private void updateCities(String proId) {

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("provinceCode", proId);
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetCityList", params, config, this);

    }

    /**
     * 获取省数据
     */
    private void initProvinceDatas() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetProvinceList", config, this);
    }

    /**
     * 选择图片
     */
    private void selectPhotoDialog() {
        img = new GetImg(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_photo, null);
        final PopupWindow pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
//        final WindowManager.LayoutParams params = getWindow().getAttributes();//创建当前界面的一个参数对象
//        params.alpha = 0.5f;//设置参数的透明度
//        getWindow().setAttributes(params);
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        ImageView iv_cancel_popu = (ImageView) view.findViewById(R.id.iv_cancel_popu);
        TextView takePhoto = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView albume = (TextView) view.findViewById(R.id.tv_albume);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        iv_cancel_popu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            //拍照
            @Override
            public void onClick(View v) {
                img.goToCamera(EditInfoActivity.this);//通过拍照获取图片
                pop.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {
            //从相册获取照片
            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                MyApplication.getInstance().selectPhoto = 6;
                Intent intent = new Intent(getApplicationContext(), TestPicActivity.class);
                startActivity(intent);
                pop.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            //取消
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANNIN_GREQUEST_CODE && resultCode == RESULT_OK) {
            String content = data.getStringExtra("result");
            Log.i("扫描结果", content);
            String shaoMiaoMessageZhuCheHao = ShaoMiaoUtils.shaoMiaoMessageZhuCheHao(content);
            String shaoMiaoMessageStoreName = ShaoMiaoUtils.shaoMiaoMessageStoreName(content);
            if (!MyUtils.isEmpty(shaoMiaoMessageZhuCheHao)) {
                et_business_num.setText(shaoMiaoMessageZhuCheHao);
            }
            if (!MyUtils.isEmpty(shaoMiaoMessageStoreName)) {
                etRestaurantName.setText(shaoMiaoMessageStoreName);
            }
            if (MyUtils.isEmpty(shaoMiaoMessageZhuCheHao) && MyUtils.isEmpty(shaoMiaoMessageStoreName)) {
                MyToastUtils.show(getApplicationContext(), getString(R.string.shaomiao_error));
            }
            return;
        }
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

    /**
     * 获取行业类型
     */
    private void getIndustryList() {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        InternetConfig config = new InternetConfig();
        config.setKey(3);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "getIndustryList", params, config, this);
    }

    /**
     * 弹出popwindows 选择菜系
     */
    private void chooseCookType() {

        Drawable drawable = MyUtils.getDrawa(this, 2);
        tvCookingType.setCompoundDrawables(null, null, drawable, null);
        ListView listView = new ListView(this);
//        listView.setBackgroundColor(Color.WHITE);
        listView.setDividerHeight(1);
        listView.setLayoutParams(new ViewGroup.LayoutParams(tvCookingType.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        popupWindow = new PopupWindow(listView, tvCookingType.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(tvCookingType);
        listView.setAdapter(new PopAdapter(popList, this, 2));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoPBean poPBean = popList.get(position);
                tvCookingType.setText(poPBean.title);
                cookingTypeId = poPBean.id;
                popupWindow.dismiss();
                Drawable drawable = MyUtils.getDrawa(EditInfoActivity.this, 1);
                tvCookingType.setCompoundDrawables(null, null, drawable, null);
            }
        });

        //pop消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //pop消失就切换箭头
                tvCookingType.setCompoundDrawables(null, null, MyUtils.getDrawa(EditInfoActivity.this, 1), null);
            }
        });
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

    //删除图片后做更新处理
    private void refreshBitmapData(List<Bitmap> comment_pics) {
        if (bitmapData == null) {
            bitmapData = new ArrayList<>();
        }
        bitmapData = comment_pics;
        if (bitmapData != null && bitmapData.size() > 0) {
            if (bitmapData.size() >= MyApplication.getInstance().imageNum) {
                ll_add_photo.setVisibility(View.GONE);
            } else {
                ll_add_photo.setVisibility(View.VISIBLE);
            }
            iv_photo.setImageBitmap(bitmapData.get(bitmapData.size() - 1));
            tv_photo_num.setText(bitmapData.size() + "张");
            rl_photo_view.setVisibility(View.VISIBLE);
        } else {
            iv_photo.setImageBitmap(null);
            iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
            tv_photo_num.setText("0张");
            ll_add_photo.setVisibility(View.VISIBLE);
            rl_photo_view.setVisibility(View.GONE);
        }
    }

//    /**
//     * 弹出popwindows 选择街道
//     */
//    private void chooseStreet() {
//        Drawable drawable = MyUtils.getDrawa(this, 2);
//        tv_street.setCompoundDrawables(null, null, drawable, null);
//        ListView listView = new ListView(this);
//        listView.setDividerHeight(1);
//        listView.setLayoutParams(new ViewGroup.LayoutParams(tv_street.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
//        popupWindow = new PopupWindow(listView, tv_street.getWidth(),
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.showAsDropDown(tv_street);
//        listView.setAdapter(new PopAdapter(mPoPBeansStreetList, this, 2));
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                PoPBean poPBean = mPoPBeansStreetList.get(position);
//                tv_street.setText(poPBean.title);
//                streetId = poPBean.id;
//                popupWindow.dismiss();
//                Drawable drawable = MyUtils.getDrawa(EditInfoActivity.this, 1);
//                tv_street.setCompoundDrawables(null, null, drawable, null);
//            }
//        });
//
//        //pop消失监听
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                //pop消失就切换箭头
//                tv_street.setCompoundDrawables(null, null, MyUtils.getDrawa(EditInfoActivity.this, 1), null);
//            }
//        });
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Bimp.drr != null) {
                Bimp.drr.clear();
            }
            deleteSaveImage();
        }
        return super.onKeyDown(keyCode, event);
    }

    //删除保存的图片
    private void deleteSaveImage() {
        /*if (mImageSave != null && mImageSave.size() > 0) {
            for (String imagePath : mImageSave) {
                try {
                    File file = new File(imagePath);
                    if (file.exists()) {
                        file.delete();
                        Log.i("-----", "---删除成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    private void showHttpImage(RestaurantBean bean, ImageLoader imageLoader) {
        iv_photo.setClickable(false);
        tv_loading.setVisibility(View.VISIBLE);
        if (bean.licenseImg != null && bean.licenseImg.size() > 0) {
            for (String imgUrl : bean.licenseImg) {
                try {
                    imageLoader.loadImage(imgUrl, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onLoadingStarted(String s, View view) {

    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        Log.i("---------a", "加载失败");
        mImageSave.add("");
        //图片下载完成保存
        if (mImageSave.size() == MyApplication.getInstance().getUser().licenseImg.size()) {
            iv_photo.setClickable(true);
            tv_loading.setVisibility(View.GONE);
            RestaurantBean restaurantBean = MyApplication.getInstance().getUser();
            cacheStoreImageDrr(restaurantBean.id, mImageSave);
        }
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        Date date = new Date();
        long time = date.getTime();
        GetImg img = new GetImg(this);
        File file = img.setDefaultFile("/" + time + "img.png");
        if (file != null) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                Bimp.drr.add(file.getAbsolutePath());
                showImage();
                if (Bimp.drr.size() == MyApplication.getInstance().getUser().licenseImg.size()) {
                    iv_photo.setClickable(true);
                }
                mImageSave.add(file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                mImageSave.add("");
            } catch (IOException e) {
                e.printStackTrace();
                mImageSave.add("");
            } finally {
                //图片下载完成保存
                if (mImageSave.size() == MyApplication.getInstance().getUser().licenseImg.size()) {
                    iv_photo.setClickable(true);
                    tv_loading.setVisibility(View.GONE);
                    RestaurantBean restaurantBean = MyApplication.getInstance().getUser();
                    cacheStoreImageDrr(restaurantBean.id, mImageSave);
                }
            }
        } else {
            iv_photo.setClickable(true);
        }
    }

    @Override
    public void onLoadingCancelled(String s, View view) {

    }

    private void cacheStoreImageDrr(String userId, ArrayList<String> bitmapDrr) {
        CacheStoreImageDrr cacheStoreImageDrr = new CacheStoreImageDrr(userId, bitmapDrr);
        if (cacheStoreImageDrr != null) {
            if (cacheStoreImageDrr.bitmapDrr != null && cacheStoreImageDrr.bitmapDrr.size() > 0) {
                for (String imageDrr : cacheStoreImageDrr.bitmapDrr) {
                    if (TextUtils.isEmpty(imageDrr) || "".equals(imageDrr)) {
                        cacheStoreImageDrr.bitmapDrr.remove(imageDrr);
                    }
                }
            }
            Gson gson = new Gson();
            String toJson = gson.toJson(cacheStoreImageDrr);
            if (!TextUtils.isEmpty(toJson)) {
                GlobalData.cacheData(getApplicationContext(), "cacheStoreImageDrr", toJson);
            }
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
    public void complete() {
        String time = datePickerPopupUtil.birthday;
        if (time != null) {
            if (dateType == 1) {
                tv_user_expirydate_input.setText(MyUtils.getDate(time, "yyyy年MM月dd日", "yyyy-MM-dd"));
            } else if (dateType == 2) {
                tv_business_expirydate_input.setText(MyUtils.getDate(time, "yyyy年MM月dd日", "yyyy-MM-dd"));
            }
        }
    }
}
