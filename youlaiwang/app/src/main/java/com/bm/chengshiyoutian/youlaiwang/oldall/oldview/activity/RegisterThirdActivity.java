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

import com.android.pc.ioc.event.EventBus;
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
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.EventBusBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.PoPBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.Registerbean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.Bimp;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.TestPicActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GlobalData;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.JPushAlias;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.SheShiQuDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.TipDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.WheelView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/5/20 17:35
 * @Description 注册第三步
 */
public class RegisterThirdActivity extends Activity implements View.OnClickListener {
    //上传图片控件
    @Bind(R.id.ll_add_photo)
    LinearLayout ll_add_photo;

    @Bind(R.id.iv_photo)
    ImageView iv_photo;

    @Bind(R.id.tv_photo_num)
    TextView tv_photo_num;
    @Bind(R.id.rl_photo_view)
    RelativeLayout rl_photo_view;

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.et_principal)
    EditText et_principal;
    @Bind(R.id.et_principal_phone)
    EditText et_principal_phone;
    @Bind(R.id.et_restaurant_phone)
    EditText et_restaurant_phone;
    @Bind(R.id.ll_store_photo)
    LinearLayout ll_store_photo;
    @Bind(R.id.iv_store_photo)
    ImageView iv_store_photo;
    @Bind(R.id.bt_register)
    Button bt_register;
        @Bind(R.id.et_restaurant_address)
    EditText etRestaurantAddress;
        @Bind(R.id.tv_area)
    TextView tvArea;
        @Bind(R.id.tv_street)
    TextView tv_street;//街道
    //    @Bind(R.id.tv_district_tverify)
//    TextView tvDistrictTverify;

    private TipDialog mTipDialog;
    private GetImg img;
    private Bitmap bitmap;
    private Registerbean mRegisterbean;
    private EventBus mMEventBus;
    private EventBusBean mEventBusBean;
    private ArrayList<Bitmap> mBeforBitmapData;//存放相关证件的集合
    private ArrayList<Bitmap> mBeforBitmapDataQiYe;//存放企业门头的集合
    private List<Bitmap> bitmapData;//选择门头照的图片bitmap
    private ArrayList<String> bitmapDataStr;//选择门头照的图片地址
    private ProgressDialog progressDialog;
    private String mFromActivity;

    private String[] proName;
    private String[] proId;
    private String[] cityName;
    private String[] cityId;
    private String[] areaName;
    private String[] areaId;
    private ArrayList<PoPBean> mPoPBeansStreetList;//街道
    private String streetId = "0";//街道id


    private String proIdStr;
    private String cityIdStr;
    private String areaIdStr;
    private RestaurantBean user;
    private String type;
    private String childtype;

    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register_third1);
        ButterKnife.bind(this);

        getIntentData();
        setlistener();
        init();
        if ("MyRestaurantActivity".equals(mFromActivity)) {
            title.setText("我的商家");
            bt_register.setText("提交");

            user = MyApplication.getInstance().getUser();

            String proName = user.proName.equals("null") ? "" : user.proName;
            String cityName = user.cityName.equals("null") ? "" : user.cityName;
            String areaName = user.areaName.equals("null") ? "" : user.areaName;
            tvArea.setText(proName+" "+ cityName+" "+ areaName);

            if (!TextUtils.isEmpty(user.streetName)&&!user.streetName.equals("null")) {
                tv_street.setText(user.streetName);
            }
            if (!TextUtils.isEmpty(user.address)) {
                etRestaurantAddress.setText(user.address);
            }


            if (user != null) {
                if (TextUtils.isEmpty(user.manager) || "null".equals(user.manager)) {
                    et_principal.setText("");
                } else {
                    et_principal.setText(user.manager);
                }
                if (TextUtils.isEmpty(user.managerphone) || "null".equals(user.managerphone)) {
                    et_principal_phone.setText("");
                } else {
                    et_principal_phone.setText(user.managerphone);
                }
                if (TextUtils.isEmpty(user.restaurantPhone) || "null".equals(user.restaurantPhone)) {
                    et_restaurant_phone.setText("");
                } else {
                    et_restaurant_phone.setText(user.restaurantPhone);
                }
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(user.photoUrl, iv_store_photo, MyApplication.getInstance().getIconOptions());
            }
        } else {
            setSaveData();
            title.setText("注册");
            bt_register.setText("注册");
        }
        mMEventBus = EventBus.getDefault();
    }


    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            mRegisterbean = intent.getParcelableExtra("registerbean");
            mFromActivity = intent.getStringExtra("fromActivity");
        }
    }

    private void setSaveData() {
        if (mRegisterbean != null) {
            if (mRegisterbean.bitmapQiYeData!=null){
                Bimp.drrThird=mRegisterbean.bitmapQiYeData ;
            }
            if (!TextUtils.isEmpty(mRegisterbean.restaurantPrincipal)) {
                et_principal.setText(mRegisterbean.restaurantPrincipal);
            }
            if (!TextUtils.isEmpty(mRegisterbean.principalPhone)) {
                et_principal_phone.setText(mRegisterbean.principalPhone);
            }
            if (!TextUtils.isEmpty(mRegisterbean.restaurantPhone)) {
                et_restaurant_phone.setText(mRegisterbean.restaurantPhone);
            }
            if (!TextUtils.isEmpty(mRegisterbean.RegisterArea)) {
                tvArea.setText(mRegisterbean.RegisterArea);
            }
            if (!TextUtils.isEmpty(mRegisterbean.RegisterStreet)) {
                tv_street.setText(mRegisterbean.RegisterStreet);
            }
            if (!TextUtils.isEmpty(mRegisterbean.address)) {
                etRestaurantAddress.setText(mRegisterbean.address);
            }

            if (!TextUtils.isEmpty(mRegisterbean.restaurantImage)) {
                try {
                    bitmap = getSmallBitmap(mRegisterbean.restaurantImage);
                    if (bitmap != null) {
                        iv_store_photo.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        mTipDialog = new TipDialog(this);
        img = new GetImg(this);
    }

    private void setlistener() {
        ivLeft.setOnClickListener(this);
        ll_store_photo.setOnClickListener(this);
        bt_register.setOnClickListener(this);
         tvArea.setOnClickListener(this);
        tv_street.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        ll_add_photo.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showImage();
    }

    private void showImage() {



        if (Bimp.drrThird != null && Bimp.drrThird.size() > 0) {
            if (bitmapData == null) {
                bitmapData = new ArrayList<>();
            }
            bitmapData.clear();
            for (int i = 0; i < Bimp.drrThird.size(); i++) {
                try {
                    bitmapData.add(Bimp.revitionImageSize(Bimp.drrThird.get(i)));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                if ("MyRestaurantActivity".equals(mFromActivity)) {
                    finish();
                } else {
                    logout();
                }
                break;
//            case R.id.ll_store_photo://选择餐厅图片
//                selectPhotoDialog();
//                break;
            case R.id.bt_register://注册
                registerOrUpdata();
                break;
            case R.id.tv_area://点击所在地区
                initProvinceDatas();
                break;
            case R.id.tv_street://街道
                if ("MyRestaurantActivity".equals(mFromActivity)) {
                    if ("".equals(user.areaId)||TextUtils.isEmpty(user.areaId)){
                        MyToastUtils.show(this,"请选择省市区");
                    }else {
                        getStreetList(user.areaId);
                    }
                }else {
                    if ("".equals(mRegisterbean.areaid)||TextUtils.isEmpty(mRegisterbean.areaid)){
                        MyToastUtils.show(this,"请选择省市区");
                    }else {
                        getStreetList(mRegisterbean.areaid);
                    }
                }
                break;
            case R.id.btnSubmit:
                int[] arr = chengShiDialog.getSeletedIndexArr();
                proIdStr = proId[arr[0]];
                cityIdStr = cityId[arr[1]];
                areaIdStr = areaId[arr[2]];
                tvArea.setText(proName[arr[0]] + " " + cityName[arr[1]] + " " + areaName[arr[2]]);


                if ("MyRestaurantActivity".equals(mFromActivity)) {
                    user.proId = proIdStr;
                    user.cityId = cityIdStr;
                    user.areaId = areaIdStr;
                    user.streetid = streetId;
                }else {
                    mRegisterbean.proid = proIdStr;
                    mRegisterbean.cityid = cityIdStr;
                    mRegisterbean.areaid = areaIdStr;
                    mRegisterbean.streetid = streetId;//街道id
                }



                if (mPoPBeansStreetList != null) {
                    mPoPBeansStreetList.clear();
                }


//                getStreetList(areaIdStr);
                chengShiDialog.dismiss();
                break;
            case R.id.iv_photo://查看大图
                Constants.BIMPTYPE="1";
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
            case R.id.ll_add_photo: //上传图片
                MyApplication.getInstance().imageNum = 100;
                selectPhotoDialog();
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
     *
     */
    //注册或者更新数据
    private void registerOrUpdata() {


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



        if ("MyRestaurantActivity".equals(mFromActivity)) {
            //我的商户
            String principal = et_principal.getText().toString();
            String principalPhone = et_principal_phone.getText().toString();
            String restaurantPhone = et_restaurant_phone.getText().toString();
            String address = etRestaurantAddress.getText().toString();


            if (TextUtils.isEmpty(address)){
                MyToastUtils.show(getApplicationContext(), "请输入您企业的详细地址");

                return;
            }
            updateRestInfoForTwo(principal, principalPhone, restaurantPhone, MyUtils.bitmapToBase64(bitmap),address);
        } else {
            //注册
            String areaStr = tvArea.getText().toString();
            if (TextUtils.isEmpty(areaStr)) { //验证是否选择地区
                //tvDistrictTverify.setVisibility(View.VISIBLE);
                MyToastUtils.show(getApplicationContext(), "请选择省-市-区/县");
                return;
            } else {
                //tvDistrictTverify.setVisibility(View.INVISIBLE);
            }
            String enterpriseAddress = etRestaurantAddress.getText().toString();
            if (TextUtils.isEmpty(enterpriseAddress)) { //验证是否选择地区
                //tvDistrictTverify.setVisibility(View.VISIBLE);
                MyToastUtils.show(getApplicationContext(), "请输入您企业的详细地址");
                return;
            }
            if (mRegisterbean != null) {
                stringToBitmap(mRegisterbean.bitmapDrr);

                mRegisterbean.bitmapQiYeData =Bimp.drrThird;

                stringToBitmapTwo(mRegisterbean.bitmapQiYeData);

                String principal = et_principal.getText().toString();
                String principalPhone = et_principal_phone.getText().toString();
                String restaurantPhone = et_restaurant_phone.getText().toString();

                /*if (!TextUtils.isEmpty(principalPhone) && principalPhone.length() != 11) {
                    MyToastUtils.show(getApplicationContext(), "负责人电话格式不对");
                    return;
                }*/
//                mRegisterbean.proid = proIdStr;
//                mRegisterbean.cityid = cityIdStr;
//                mRegisterbean.areaid = areaIdStr;
//                mRegisterbean.streetid = streetId;//街道id
                mRegisterbean.restaurantPrincipal = principal;
                mRegisterbean.principalPhone = principalPhone;
                mRegisterbean.restaurantPhone = restaurantPhone;
                mRegisterbean.address = enterpriseAddress;
                if (mRegisterbean != null) {
                    ivLeft.setClickable(false);
                    bt_register.setClickable(false);
                    registerHttp(mRegisterbean);
                }
            }
        }
    }

    //将字符串转成bitmap
    private void stringToBitmap(ArrayList<String> bitmapDrr) {
        if (bitmapDrr != null && bitmapDrr.size() > 0) {
            mBeforBitmapData = new ArrayList<>();
            for (int i = 0; i < bitmapDrr.size(); i++) {
                try {
                    mBeforBitmapData.add(Bimp.revitionImageSize(bitmapDrr.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //将字符串转成bitmap
    private void stringToBitmapTwo(ArrayList<String> bitmapDrr) {
        if (bitmapDrr != null && bitmapDrr.size() > 0) {
            mBeforBitmapDataQiYe = new ArrayList<>();
            for (int i = 0; i < bitmapDrr.size(); i++) {
                try {
                    mBeforBitmapDataQiYe.add(Bimp.revitionImageSize(bitmapDrr.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //注册
    private void registerHttp(Registerbean mRegisterbean) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
//        params.put("tel", mRegisterbean.tel); //注册手机号码
//        params.put("pwd", mRegisterbean.pwd); //密码
//
//        params.put("licenseImg", MyUtils.bitmapStringData(mBeforBitmapData)); //营运执照图片
//        params.put("licenseNo", mRegisterbean.licenseNo); //许可证
//        params.put("licenseID", mRegisterbean.businessNum);
//        params.put("title", mRegisterbean.title); //餐厅名称
//        params.put("IndustryId", mRegisterbean.industryId); //行业id
//        params.put("proid", mRegisterbean.proid); //省id
//        params.put("cityid", mRegisterbean.cityid); //市id
//        params.put("areaid", mRegisterbean.areaid); //区idO
//        params.put("streetid", mRegisterbean.streetid); //街道id
//        params.put("address", mRegisterbean.address); //餐厅地址
//
//        params.put("manager", mRegisterbean.restaurantPrincipal); //负责人
//        params.put("managerphone", mRegisterbean.principalPhone); //负责人电话
//        params.put("phone", mRegisterbean.restaurantPhone); //餐厅电话
//        params.put("img_url", MyUtils.bitmapToBase64(bitmap)); //餐厅图片

        params.put("IndustryId", mRegisterbean.industryId);//主体业态ID
        params.put("title", mRegisterbean.title);//企业名称
        params.put("img_url",MyUtils.bitmapStringData(mBeforBitmapDataQiYe) );//企业门头照
        params.put("licenseImg",MyUtils.bitmapStringData(mBeforBitmapData) );//营业执照,食品经营许可证及其他证件
        params.put("phone",  mRegisterbean.restaurantPhone);//企业电话
        params.put("tel",mRegisterbean.tel );//注册手机号
        params.put("proid",mRegisterbean.proid );//省ID
        params.put("cityid",mRegisterbean.cityid );//市ID
        params.put("areaid",mRegisterbean.areaid );//区ID
        params.put("address", mRegisterbean.address);//企业地址
        params.put("pwd", mRegisterbean.pwd);//登陆密码
        params.put("licenseNo",  mRegisterbean.licenseNo);//食品经营许可证编号
        params.put("licenseDate",mRegisterbean.licenseNoDate );//食品经营许可证有效期
        params.put("licenseID", mRegisterbean.businessNum);//营业执照编号或其他经营许可证号
        params.put("licenseTime",mRegisterbean.businessNumDate );//营业执照或其他证件有效期
        params.put("streetid",streetId);//街道id(没有传0)
        params.put("manager",mRegisterbean.restaurantPrincipal );//企业负责人
        params.put("managerphone",mRegisterbean.principalPhone );//联系电话
        Log.i("Home",mBeforBitmapDataQiYe+"");
        Log.i("Home",MyUtils.bitmapStringData(mBeforBitmapDataQiYe));
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "Register5", params, config, this);
    }

    /**
     * 获取餐厅信息
     *u
     * @param restaurantID
     */
    private void getInfoData(String restaurantID) {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("_id", restaurantID);
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetRestaurantInfo", params, config, this);
    }

    /**
     * 修改用户数据
     *
     * @param manager
     * @param managerphone
     * @param phone
     * @param img_url
     */
    private void updateRestInfoForTwo(String manager, String managerphone, String phone, String img_url,String address) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("restId", MyApplication.getInstance().getUser().id);
        params.put("manager", manager != null ? manager : "");
        params.put("managerphone", managerphone != null ? managerphone : "");
        params.put("phone", phone != null ? phone : "");
        params.put("img_url", MyUtils.bitmapStringData(bitmapData));

        params.put("address", address);
        params.put("proid", user.proId);
        params.put("cityid", user.cityId);
        params.put("areaid", user.areaId);
        params.put("streetid", streetId);


        InternetConfig config = new InternetConfig();
        config.setKey(2);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "UpdateRestInfoForTwo5", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        String contentAsString = entity.getContentAsString();
        int key = entity.getKey();
        switch (key) {
            case 0://注册
                registerData(contentAsString);
                break;
            case 1://注册成功获取餐厅的信息
                //bt_register.setClickable(true);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                registerSuccessData(contentAsString);
                break;
            case 2://修改用户数据
                updataData(contentAsString);
                break;
            case 3: // 获取省信息
                try {
                    progressDialog.dismiss();
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
            case 4: //获取市信息
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
            case 5: //获取区信息
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
            case 6://获取街道
                getStreetListData(entity);
                break;
        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        ivLeft.setClickable(true);
        bt_register.setClickable(true);
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    //修改用户数据
    private void updataData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            String status = jsonObject.optString("status");
            String msg = jsonObject.optString("msg");
            MyToastUtils.show(getApplicationContext(), msg);
            if ("0".equals(status)) {
                MyApplication.getInstance().isUpdataPersionMessage = true;
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //注册后获取的信息
    private void registerData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            Log.i("Home",jsonObject+"");
            if (0 == jsonObject.getInt("status")) {
//                childtype = jsonObject.optString("type");
                String restaurantID = jsonObject.optString("RestID");
                getInfoData(restaurantID); //登录成功获取信息
            } else {
                ivLeft.setClickable(true);
                bt_register.setClickable(true);
                MyToastUtils.show(this, "注册失败");
            }
        } catch (JSONException e) {
            ivLeft.setClickable(true);
            bt_register.setClickable(true);
            e.printStackTrace();
        }
    }

    //解析获取的餐厅信息
    private void registerSuccessData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            String msg = jsonObject.optString("msg");
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
                String xing = "null".equals(xing1) ? "0" : xing1;//星星评分
                String restaurantName = object.optString("title"); //餐厅名称
                String cookingType = object.optString("category"); //菜系名称
                String cookingTypeId = object.optString("category_id"); //菜系id
                String proName = object.optString("ProName"); //省名
                String cityName = object.optString("CityName"); //市名
                String disName = object.optString("DisName"); //区名
                String proid = object.optString("proid");
                String cityid = object.optString("cityid");
                String areaid = object.optString("areaid");

                //主体业态
                String industryId = object.optString("IndustryId");
                String industryName = object.optString("IndustryName");
                //食品经营许可证
                String licenseNo = object.optString("licenseNo");
                //营业执照
                String licenseID = object.optString("licenseID");
                //街道
                String streetName = object.optString("StreetName");
                String streetid = object.optString("streetid");
                //负责人和负责人电话
                String manager = object.optString("manager");
                String managerphone = object.optString("managerphone");
                //相关证件
                String licenseImg = object.optString("licenseImg");
                //食品经营许可证有效期
                String licenseDate = object.optString("licenseDate");
                //营业执照或其他证件有效期
                String licenseTime = object.optString("licenseTime");


                List<String> licenseImgData = splitLicenseImg(licenseImg);

                RestaurantBean bean = new RestaurantBean(img_url, restaurantName, restaurantAddress,
                        mobilePhone, restaurantPhone, xing, integral,
                        cookingType, cookingTypeId, proName, cityName, disName, id, proid, cityid, areaid,licenseDate,licenseTime);
                bean.industryId = industryId;
                bean.industryName = industryName;
                bean.licenseNo = licenseNo;
                bean.licenseID = licenseID;
                bean.streetName = streetName;
                bean.streetid = streetid;
                bean.manager = manager;
                bean.managerphone = managerphone;
                bean.licenseImg = licenseImgData;
                bean.licenseDate = licenseDate;
                bean.licenseTime = licenseTime;
//                bean.childAccountType = Integer.parseInt(childtype);
                MyApplication.getInstance().setUser(bean);
                //CacheImageService.startsevice(this);
                cacheStoreImageDrr(id, mRegisterbean.bitmapDrr);
                SPUtil.put(this, Constants.IS_LOGIN, true);
                MyToastUtils.show(getApplicationContext(), "注册成功");
                JPushAlias jPushAlias = new JPushAlias();
                jPushAlias.setAlias(getApplicationContext(), MyApplication.getInstance().getUser().id);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        SPUtil.put(RegisterThirdActivity.this, Constants.IS_LOGIN, true);
                        JPushInterface.setAlias(getApplicationContext(), id, null);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        if (mBeforBitmapData != null) {
                            mBeforBitmapData.clear();
                            mBeforBitmapData = null;
                        }
                        finish();
                    }
                }).start();
            } else {
                ivLeft.setClickable(true);
                bt_register.setClickable(true);
                MyToastUtils.show(getApplicationContext(), msg);
            }
        } catch (JSONException e) {
            ivLeft.setClickable(true);
            bt_register.setClickable(true);
            e.printStackTrace();
        }
    }

    //保存上传图片的路径
    private void cacheStoreImageDrr(String userId, ArrayList<String> bitmapDrr) {
        CacheStoreImageDrr cacheStoreImageDrr = new CacheStoreImageDrr(userId, bitmapDrr);
        if (cacheStoreImageDrr != null) {
            Gson gson = new Gson();
            String toJson = gson.toJson(cacheStoreImageDrr);
            if (!TextUtils.isEmpty(toJson)) {
                GlobalData.cacheData(getApplicationContext(), "cacheStoreImageDrr", toJson);
            }
        }
    }

    private List<String> splitLicenseImg(String licenseImg) {
        if (!TextUtils.isEmpty(licenseImg)) {
            String[] split = licenseImg.split(",");
            List<String> licenseImgData = Arrays.asList(split);
            return licenseImgData;
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ivLeft.setClickable(true);
            bt_register.setClickable(true);
            if ("MyRestaurantActivity".equals(mFromActivity)) {
                finish();
            } else {
                logout();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回
    private void logout() {
        mTipDialog.show();
        mTipDialog.setTitle("提醒");
        mTipDialog.setMessage("是否保存填写数据，并返回上一页");
        mTipDialog.showCancel();
        mTipDialog.setActionMessage("是", "否");
        mTipDialog.setOnClickListener(new TipDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(1);
                finish();
                mTipDialog.dismiss();
            }
        });
        mTipDialog.setOnCanceClicklListener(new TipDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(0);
                finish();
                mTipDialog.dismiss();
            }
        });
    }

    private void saveData(int type) {  //0：不保存；1保存
        String principal = et_principal.getText().toString();
        String principalPhone = et_principal_phone.getText().toString();
        String restaurantPhone = et_restaurant_phone.getText().toString();
        String qiyiAddress = etRestaurantAddress.getText().toString();
        String area = tvArea.getText().toString();
        String street = tv_street.getText().toString();

        if (mRegisterbean != null) {
            if (type == 0) {
                mRegisterbean.restaurantPrincipal = "";
                mRegisterbean.principalPhone = "";
                mRegisterbean.restaurantPhone = "";
                mRegisterbean.restaurantImage = "";
                mRegisterbean.address = "";
                mRegisterbean.RegisterArea = "";
                mRegisterbean.RegisterStreet = "";
                if (mRegisterbean.bitmapQiYeData!=null) {
                    mRegisterbean.bitmapQiYeData.clear();
                }
            } else if (type == 1) {
                mRegisterbean.restaurantPrincipal = principal;
                mRegisterbean.principalPhone = principalPhone;
                mRegisterbean.restaurantPhone = restaurantPhone;
                mRegisterbean.address = qiyiAddress;
                mRegisterbean.RegisterArea = area;
                mRegisterbean.RegisterStreet = street;
                mRegisterbean.bitmapQiYeData =Bimp.drrThird;
            }
            Bimp.drr.addAll(mRegisterbean.bitmapDrr);



            sendEventBus(mRegisterbean);
        }
    }

    private void sendEventBus(Registerbean mRegisterbean) {
        if (mEventBusBean == null) {
            mEventBusBean = new EventBusBean();
        }
        mEventBusBean.setActivityName("QualityGuaranteePeriodActivity");
        mEventBusBean.setRegisterbean(mRegisterbean);
        mMEventBus.post(mEventBusBean);

    }

   /* *//**
     * 选择图片
     *//*
    private void selectPhotoDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_photo, null);
        final PopupWindow pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
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
                img.goToCamera(RegisterThirdActivity.this);//通过拍照获取图片
                pop.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {
            //从相册获取照片
            @Override
            public void onClick(View v) {
                img.goToGallery(); //从相册获取照片
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

    }*/
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
                img.goToCamera(RegisterThirdActivity.this);//通过拍照获取图片
                popupWindow.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                MyApplication.getInstance().selectPhoto = 7;
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
        if (requestCode == GetImg.go_to_camera_code && resultCode == RESULT_OK) {
//            String path = "";
//            path = img.file_save.getAbsolutePath();
//            bitmap = getSmallBitmap(path);
//            iv_store_photo.setImageBitmap(bitmap);
//            if (mRegisterbean != null) {
//                mRegisterbean.restaurantImage = path;
//            }
//        }
//        if (requestCode == GetImg.go_to_gallery_code && resultCode == RESULT_OK) {
//            String path = img.getGalleryPath(data);
//            bitmap = getSmallBitmap(path);
//            iv_store_photo.setImageBitmap(bitmap);
//            if (mRegisterbean != null) {
//                mRegisterbean.restaurantImage = path;
//            }
//        }
            if (bitmapData == null) {
                bitmapData = new ArrayList<>();
            }
            if (requestCode == GetImg.go_to_camera_code && resultCode == RESULT_OK) { //通过拍照获取图片
                String path = "";
                path = img.file_save.getAbsolutePath();
                Bimp.drrThird.add(path);

            }
            if (requestCode == GetImg.go_to_gallery_code && resultCode == RESULT_OK) {
                String path = img.getGalleryPath(data);
                bitmap = getSmallBitmap(path);
                iv_photo.setImageBitmap(bitmap);
            }

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
        int angle = img.readPictureDegree(filePath);

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        BufferedOutputStream baos = null;

        bitmap = img.rotaingImageView(angle, bitmap);
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
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

        /**
     * 获取省数据
     */
    private void initProvinceDatas() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        InternetConfig config = new InternetConfig();
        config.setKey(3);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetProvinceList", config, this);
    }

    /**
     * 选择城市
     */
    private SheShiQuDialog chengShiDialog;
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
     * 获取市信息
     */
    private void updateCities(String proId) {

        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("provinceCode", proId);
        InternetConfig config = new InternetConfig();
        config.setKey(4);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetCityList", params, config, this);

    }
    /**
     * 获取区信息
     */
    private void updateAreas(String cityid) {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("cityCode", cityid);
        InternetConfig config = new InternetConfig();
        config.setKey(5);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetAreaList", params, config, this);
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
                    chooseStreet();
                }else {
                    MyToastUtils.show(this,"该地区暂未收录街道信息");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹出popwindows 选择街道
     */
    private PopupWindow popupWindow;
    private void chooseStreet() {
        Drawable drawable = MyUtils.getDrawa(this, 2);
        tv_street.setCompoundDrawables(null, null, drawable, null);
        ListView listView = new ListView(this);
        listView.setDividerHeight(1);
        listView.setLayoutParams(new ViewGroup.LayoutParams(tv_street.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        popupWindow = new PopupWindow(listView, tv_street.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(tv_street);
        listView.setAdapter(new PopAdapter(mPoPBeansStreetList, this, 2));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoPBean poPBean = mPoPBeansStreetList.get(position);
                tv_street.setText(poPBean.title);
                streetId = poPBean.id;
                popupWindow.dismiss();
                Drawable drawable = MyUtils.getDrawa(RegisterThirdActivity.this, 1);
                tv_street.setCompoundDrawables(null, null, drawable, null);
            }
        });

        //pop消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //pop消失就切换箭头
                tv_street.setCompoundDrawables(null, null, MyUtils.getDrawa(RegisterThirdActivity.this, 1), null);
            }
        });
    }
}
