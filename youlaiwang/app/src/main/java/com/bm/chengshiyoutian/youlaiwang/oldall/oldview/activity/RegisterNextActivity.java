package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.android.pc.ioc.event.EventBus;
import com.android.pc.ioc.inject.InjectBefore;
import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.PopAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.EventBusBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.PoPBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.Registerbean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.datetimepick.DatePickerPopupUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.Bimp;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.TestPicActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.Base64;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.ShaoMiaoUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.SheShiQuDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.WheelView;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.zxing.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;



/**
 * 注册下一步
 * Created by jayen on 16/1/12.
 */
public class RegisterNextActivity extends Activity implements View.OnClickListener,DatePickerPopupUtil.DateCallBck {

    private final int SCANNIN_GREQUEST_CODE = 1023;

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.et_restaurant_name)
    EditText etRestaurantName;
    @Bind(R.id.tv_cooking_type)
    TextView tvCookingType;
//    @Bind(R.id.et_restaurant_address)
//    EditText etRestaurantAddress;
    @Bind(R.id.et_restaurant_phone)
EditText etRestaurantPhone;
    @Bind(R.id.bt_register)
    Button btRegister;
//    @Bind(R.id.tv_area)
//    TextView tvArea;
    @Bind(R.id.tv_restaurant_name_verify)
TextView tvRestaurantNameVerify;
    @Bind(R.id.tv_restaurant_address_verify)
    TextView tvRestaurantAddressVerify;
    @Bind(R.id.tv_restaurant_phone_verify)
    TextView tvRestaurantPhoneVerify;
    @Bind(R.id.tv_register_status)
    TextView tvRegisterStatus;
//    @Bind(R.id.tv_street)
//    TextView tv_street;//街道

    //上传图片控件
    @Bind(R.id.ll_add_photo)
    LinearLayout ll_add_photo;
    @Bind(R.id.rl_photo_view)
    RelativeLayout rl_photo_view;
    @Bind(R.id.tv_photo_num)
    TextView tv_photo_num;
    @Bind(R.id.iv_photo)
    ImageView iv_photo;

    @Bind(R.id.tv_cooking_type_verify)
    TextView tvCookingTypeVerify;
//    @Bind(R.id.tv_district_tverify)
//    TextView tvDistrictTverify;
    @Bind(R.id.ll_store_photo)
LinearLayout ll_store_photo;
    @Bind(R.id.iv_store_photo)
    ImageView iv_store_photo;

    @Bind(R.id.et_licenseNo)
    EditText et_licenseNo;
    @Bind(R.id.et_business_num)
    EditText et_business_num;

    @Bind(R.id.iv_shaomiao)
    ImageView iv_shaomiao;

    @Bind(R.id.tv_business_expirydate_input)
    TextView tv_business_expirydate_input;


    @Bind(R.id.tv_user_expirydate_input)
    TextView tv_user_expirydate_input;

    private String storeBoss;

    private ProgressDialog progressDialog;
    private PopupWindow popupWindow;

    private SheShiQuDialog chengShiDialog;
    private String proIdStr;
    private String cityIdStr;
    private String areaIdStr;
    private GetImg img;
    private Bitmap bitmap;
    private Bitmap bitmapStore;
    private int selectphoto = 0;
    private String[] proName;
    private String[] proId;
    private String[] cityName;
    private String[] cityId;
    private String[] areaName;
    private String[] areaId;
    private ArrayList<PoPBean> popList;
//    private String streetId = "0";//街道id

    private ArrayList<PoPBean> mPoPBeansStreetList;//街道

    private DatePickerPopupUtil datePickerPopupUtil;
    /**
     * 菜系id
     */
    private String cookingTypeId;
    private AlertDialog dialog;

    private List<Bitmap> bitmapData;//选择的图片bitmap
    private ArrayList<String> bitmapDrr;//已经选择的图片的地址
    private Registerbean mRegisterbean;//包含注册的信息
    private EventBus mMEventBus;

    //时间有效期，1是食品有效期   2是营业执照有效期
    private int dateType;
    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register_next);
        ButterKnife.bind(this);
        init();
        mMEventBus = EventBus.getDefault();//注册eventbus
        mMEventBus.register(this);
    }

    private void init() {
        //ll_store_photo.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        title.setText("注册");
        btRegister.setOnClickListener(this);
//        tvArea.setOnClickListener(this);
        tvCookingType.setOnClickListener(this);
//        tv_street.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        ll_add_photo.setOnClickListener(this);
        iv_store_photo.setOnClickListener(this);
        iv_shaomiao.setOnClickListener(this);
        tv_user_expirydate_input.setOnClickListener(this);
        tv_business_expirydate_input.setOnClickListener(this);
        //getCookingType();//获取菜系类别
        getIndustryList();//获取行业类型

        datePickerPopupUtil = new DatePickerPopupUtil(getApplicationContext(), RegisterNextActivity.this);
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

    /**
     * 获取菜系类别
     */
    private void getCookingType() {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(4);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetMenu", params, config, this);
    }

    /**
     * 获取行业类型
     */
    private void getIndustryList() {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(4);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "getIndustryList", params, config, this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                if (Bimp.drr != null) {
                    Bimp.drr.clear();
                }
                finish();
                break;
            case R.id.ll_add_photo: //上传图片
                MyApplication.getInstance().imageNum = 100;
                selectPhotoDialog();
                break;
            case R.id.iv_photo://查看大图
                Constants.BIMPTYPE = "0";
                if (bitmapData != null && bitmapData.size() > 0) {
                    BigImageDialog bigImageDialog = new BigImageDialog(this, Constants.BIMPTYPE,bitmapData, 0, new BigImageDialog.RefreshBitmapData() {
                        @Override
                        public void refreshData(List<Bitmap> comment_pics) {
                            refreshBitmapData(comment_pics);
                        }
                    });
                    bigImageDialog.show();
                }
                break;
            case R.id.bt_register: //点击注册(下一步)
                register();
                break;
            case R.id.tv_area://点击所在地区
                initProvinceDatas();
                break;
            case R.id.tv_cooking_type:
                chooseCookType(); //选择主体业态(菜系)
                break;
            case R.id.btnCancel:
                chengShiDialog.dismiss();
                break;
            case R.id.btnSubmit:
                int[] arr = chengShiDialog.getSeletedIndexArr();
                proIdStr = proId[arr[0]];
                cityIdStr = cityId[arr[1]];
                areaIdStr = areaId[arr[2]];
//                tvArea.setText(proName[arr[0]] + " " + cityName[arr[1]] + " " + areaName[arr[2]]);
                if (mPoPBeansStreetList != null) {
                    mPoPBeansStreetList.clear();
                }
                getStreetList(areaIdStr);
                chengShiDialog.dismiss();
                break;
            case R.id.iv_store_photo:
                selectphoto = 1;
                selectPhotoDialog();
                break;
            case R.id.tv_street://街道
//                chooseStreet();
                break;
            case R.id.iv_shaomiao://扫一扫
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), CaptureActivity.class);
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
     * 注册前的验证
     */
    private void register() {
        Intent intent = getIntent();
        String phoneNum = intent.getStringExtra("phoneNum"); //填入的注册手机号
        String psw = intent.getStringExtra("psw"); //填入的密码
        String licenseNo = et_licenseNo.getText().toString();//食品经营许可证
        String licenseNoDate = tv_user_expirydate_input.getText().toString();//食品经营许可证有效期
        String etBusinessNum = et_business_num.getText().toString();//营业执照编号
        String businessNumExpiryDate = tv_business_expirydate_input.getText().toString();//执照或证件有效期
        String restaurantName = etRestaurantName.getText().toString().trim();//餐厅名
//        String restaurantAddress = etRestaurantAddress.getText().toString().trim();//餐厅地址
        String restaurantPhone = etRestaurantPhone.getText().toString().trim();
        if (bitmapData == null || bitmapData.size() == 0) {
            MyToastUtils.show(getApplicationContext(), getString(R.string.register_data));
            return;
        }

        if (TextUtils.isEmpty(etBusinessNum)){
            MyToastUtils.show(getApplicationContext(), "请输入执照或证件编号");
            return;
        }
        if (TextUtils.isEmpty(businessNumExpiryDate)){
            MyToastUtils.show(getApplicationContext(), "请输入执照或证件有效期");
            return;
        }
        if (TextUtils.isEmpty(restaurantName)) { //验证餐厅名是否为空
            //tvRestaurantNameVerify.setVisibility(View.VISIBLE);
            MyToastUtils.show(getApplicationContext(), getString(R.string.input_restaurant_name));
            return;
        } else {
            //tvRestaurantNameVerify.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(cookingTypeId)) { //验证是否选择菜系
            //tvCookingTypeVerify.setVisibility(View.VISIBLE);
            MyToastUtils.show(getApplicationContext(), "请选择所属主体业态");
            return;
        } else {
            //tvCookingTypeVerify.setVisibility(View.INVISIBLE);
        }

//        if (TextUtils.isEmpty(areaIdStr)) { //验证是否选择地区
//            //tvDistrictTverify.setVisibility(View.VISIBLE);
//            MyToastUtils.show(getApplicationContext(), "请选择省-市-区/县");
//            return;
//        } else {
//            //tvDistrictTverify.setVisibility(View.INVISIBLE);
//        }
        /*if (TextUtils.isEmpty(streetId)) { //街道
            MyToastUtils.show(getApplicationContext(), getString(R.string.street_address));
            return;
        }*/
//        if (TextUtils.isEmpty(restaurantAddress)) {//验证餐厅地址是否为空
//            //tvRestaurantAddressVerify.setVisibility(View.VISIBLE);
//            MyToastUtils.show(getApplicationContext(), getString(R.string.input_restaurant_address));
//            return;
//        } else {
//            //tvRestaurantAddressVerify.setVisibility(View.INVISIBLE);
//        }
        //将选中的图片带到下一个界面  bitmapDrr存放的是图片信息
        if (bitmapDrr == null) {
            bitmapDrr = new ArrayList<>();
        }
        bitmapDrr.clear();
        bitmapDrr.addAll(Bimp.drr);
        if (mRegisterbean == null) {
            mRegisterbean = new Registerbean();
        }
        mRegisterbean.tel = phoneNum;
        mRegisterbean.pwd = psw;
        mRegisterbean.bitmapDrr = bitmapDrr;
        mRegisterbean.licenseNo = licenseNo;
        mRegisterbean.licenseNoDate = licenseNoDate;
        mRegisterbean.title = restaurantName;
        mRegisterbean.industryId = cookingTypeId;
//        mRegisterbean.proid = proIdStr;
//        mRegisterbean.cityid = cityIdStr;
//        mRegisterbean.areaid = areaIdStr;
//        mRegisterbean.streetid = streetId;//街道id
//        mRegisterbean.address = restaurantAddress;
        mRegisterbean.businessNum = etBusinessNum;
        mRegisterbean.businessNumDate = businessNumExpiryDate;

        if (!MyUtils.isEmpty(storeBoss)) {
            mRegisterbean.restaurantPrincipal = storeBoss;
        }

        intent = new Intent(getApplicationContext(), RegisterThirdActivity.class);
        intent.putExtra("registerbean", mRegisterbean);
        startActivity(intent);
        Bimp.drr.clear();
        //验证餐厅电话是否为空
        /*if (TextUtils.isEmpty(restaurantPhone) || restaurantPhone.length() > 15) {
            tvRestaurantPhoneVerify.setVisibility(View.VISIBLE);
            return;
        } else {
            tvRestaurantPhoneVerify.setVisibility(View.INVISIBLE);
        }*/
        /*if (bitmapStore == null) {
            MyToastUtils.show(getApplicationContext(), "请上传营业执照");
            return;
        }*/
        /*if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        //params.put("category_id", cookingTypeId); //菜系id
        params.put("IndustryId", cookingTypeId); //行业id
        params.put("title", restaurantName); //餐厅名称
        params.put("img_url", bitmapToBase64(bitmap)); //餐厅图片
        params.put("licenseImg", bitmapToBase64(bitmapStore)); //营运执照图片
        params.put("phone", restaurantPhone); //餐厅电话
        params.put("tel", phoneNum); //注册手机号码
        params.put("proid", proIdStr); //省id
        params.put("cityid", cityIdStr); //市id
        params.put("areaid", areaIdStr); //区id
        params.put("address", restaurantAddress); //餐厅地址
        params.put("pwd", psw); //密码
//        params.put("pwd", Md5Utils.encode(psw)); //密码
        InternetConfig config = new InternetConfig();
        config.setKey(3);
        config.setTimeout(Constants.TIMEOUT);
        //FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "Register", params, config, this);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "Register2", params, config, this);*/
    }

    /*
     * 将日志打印出来测试用
	 */
    private void testLogContent(String content) {
        try {
            FileWriter fw = new FileWriter(getFilesDir() + "/userImage.txt");
            fw.flush();
            fw.write(content);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取区信息
     */
    private void updateAreas(String cityid) {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
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

        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
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
     * 获取街道
     */
    private void getStreetList(String areaCode) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("areaCode", areaCode);
        InternetConfig config = new InternetConfig();
        config.setKey(6);
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
            case 3:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        String restaurantID = jsonObject.optString("RestID");
                        tvRegisterStatus.setText("注册成功");
                        getInfoData(restaurantID); //登录成功获取信息
                    } else {
                        tvRegisterStatus.setText("注册失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 4: //获取菜系
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

            case 5:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (jsonObject.optInt("status") == 0) {
                        JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").getJSONObject(0);
                        final String id = object.optString("id"); //餐厅id
                        String restaurantPhone = object.optString("tel"); //餐厅电话
                        String mobilePhone = object.optString("tags"); //手机号码
                        String score = object.optString("score");
                        String integral = "null".equals(score) ? "0" : score; //积分
                        String img_url = object.optString("img_url"); //头像图片地址
                        String restaurantAddress = object.optString("seo_title"); //餐厅地址
                        String xing1 = object.optString("xing");
                        String xing = "null".equals(xing1) ? "0" : xing1;
                        //星星评分
                        String restaurantName = object.optString("title"); //餐厅名称
                        String cookingType = object.optString("category"); //菜系名称
                        String cookingTypeId = object.optString("category_id"); //菜系id
                        String proName = object.optString("ProName"); //省名
                        String cityName = object.optString("CityName"); //市名
                        String disName = object.optString("DisName"); //区名
                        String proid = object.optString("proid");
                        String cityid = object.optString("cityid");
                        String areaid = object.optString("areaid");

                        RestaurantBean bean = new RestaurantBean(img_url, restaurantName, restaurantAddress,
                                mobilePhone, restaurantPhone, xing, integral,
                                cookingType, cookingTypeId, proName, cityName, disName, id, proid, cityid, areaid);
                        MyApplication.getInstance().setUser(bean);
                        SPUtil.put(this, Constants.IS_LOGIN, true);
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                SPUtil.put(RegisterNextActivity.this, Constants.IS_LOGIN, true);
                                JPushInterface.setAlias(getApplicationContext(), id, null);
                                startActivity(new Intent(RegisterNextActivity.this, XiangQingActivity.class));
                                finish();
                            }
                        }).start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 6://获取街道
                getStreetListData(entity);
                break;
            default:
                break;
        }
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
     * 获取餐厅信息
     *
     * @param restaurantID
     */
    private void getInfoData(String restaurantID) {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("_id", restaurantID);
        InternetConfig config = new InternetConfig();
        config.setKey(5);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetRestaurantInfo", params, config, this);
    }

    /**
     * 弹出popwindows 选择主体业态(菜系)
     */
    private void chooseCookType() {
        Drawable drawable = MyUtils.getDrawa(this, 2);
        tvCookingType.setCompoundDrawables(null, null, drawable, null);
        ListView listView = new ListView(this);
//      listView.setBackgroundColor(Color.WHITE);
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
                Drawable drawable = MyUtils.getDrawa(RegisterNextActivity.this, 1);
                tvCookingType.setCompoundDrawables(null, null, drawable, null);
            }
        });

        //pop消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //pop消失就切换箭头
                tvCookingType.setCompoundDrawables(null, null, MyUtils.getDrawa(RegisterNextActivity.this, 1), null);
            }
        });
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
//                Drawable drawable = MyUtils.getDrawa(RegisterNextActivity.this, 1);
//                tv_street.setCompoundDrawables(null, null, drawable, null);
//            }
//        });
//
//        //pop消失监听
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                //pop消失就切换箭头
//                tv_street.setCompoundDrawables(null, null, MyUtils.getDrawa(RegisterNextActivity.this, 1), null);
//            }
//        });
//    }

    /**
     * 选择图片
     */
    private void selectPhotoDialog() {
        img = new GetImg(this);
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
                img.goToCamera(RegisterNextActivity.this);//通过拍照获取图片
                popupWindow.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                MyApplication.getInstance().selectPhoto = 2;
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
        if (requestCode == SCANNIN_GREQUEST_CODE && resultCode == RESULT_OK) {
            String content = data.getStringExtra("result");
            Log.i("扫描结果", content);
            String shaoMiaoMessageZhuCheHao = ShaoMiaoUtils.shaoMiaoMessageZhuCheHao(content);
            String shaoMiaoMessageStoreName = ShaoMiaoUtils.shaoMiaoMessageStoreName(content);
            String shaoMiaoMessageStoreBoss = ShaoMiaoUtils.shaoMiaoMessageStoreBoss(content);
            if (!MyUtils.isEmpty(shaoMiaoMessageZhuCheHao)) {
                et_business_num.setText(shaoMiaoMessageZhuCheHao);
            }
            if (!MyUtils.isEmpty(shaoMiaoMessageStoreName)) {
                etRestaurantName.setText(shaoMiaoMessageStoreName);
            }
            if (!MyUtils.isEmpty(shaoMiaoMessageStoreBoss)) {
                storeBoss = shaoMiaoMessageStoreBoss;
            }
            if (MyUtils.isEmpty(shaoMiaoMessageZhuCheHao) && MyUtils.isEmpty(shaoMiaoMessageStoreName) && MyUtils.isEmpty(shaoMiaoMessageStoreBoss)) {
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

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        MyToastUtils.show(this, getString(R.string.intnet_err));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Bimp.drr != null) {
                Bimp.drr.clear();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onEventMainThread(EventBusBean mEventBusBean) {
        if (mEventBusBean == null) {
            return;
        }
        if ("QualityGuaranteePeriodActivity".equals(mEventBusBean.getActivityName())) {
            mRegisterbean = mEventBusBean.getRegisterbean();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMEventBus != null) {
            mMEventBus.unregister(this);
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
            }else if(dateType==2){
                tv_business_expirydate_input.setText(MyUtils.getDate(time, "yyyy年MM月dd日", "yyyy-MM-dd"));
            }


        }
    }
}
