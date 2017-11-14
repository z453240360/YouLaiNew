package com.bm.chengshiyoutian.youlaiwang.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.*;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.banner.MainBannerView;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.BaseBannerBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.slidingmenu.DragLayout;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.slidingmenu.MyRelativeLayout;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.JPushAlias;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.RoundImageView;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.TipDialog;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.permission.AndPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Created by sld on 2017/5/2.
 */

public class XiangQingActivity extends Activity implements View.OnClickListener, AMapLocationListener,IMainView {


    private static final String TAG = "MainActivity";
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.photo)
    RoundImageView photo;
    @Bind(R.id.tv_store_name)
    TextView tvStoreName;
    @Bind(R.id.app_ratingbar)
    RatingBar appRatingbar;
    @Bind(R.id.tv_suozheng_suopiao)
    TextView tv_suozheng_suopiao;
    @Bind(R.id.tv_cooking_oil_declare)
    TextView tvCookingOilDeclare;
    @Bind(R.id.tv_refuse_oil_declare)
    TextView tvRefuseOilDeclare;
    @Bind(R.id.rl_notice)
    RelativeLayout tvNotice;
    @Bind(R.id.tv_conversion_ratio)
    TextView tvConversionRatio;
    @Bind(R.id.tv_near_mess)
    TextView tvNearMess;
    @Bind(R.id.tv_user_comment)
    TextView tvUserComment;
    @Bind(R.id.tv_qr_code)
    TextView tvQrCode;
    @Bind(R.id.tv_setting)
    TextView tvSetting;
    @Bind(R.id.tv_logout)
    TextView tvLogout;
    @Bind(R.id.ll_bom_left)
    LinearLayout llBomLeft;
    @Bind(R.id.banner_cont)
    MyRelativeLayout bannerContent;
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.ll_cooking_oil_declare)
    LinearLayout llCookingOilDeclare;
    @Bind(R.id.ll_refuse_oil_declare)
    LinearLayout llRefuseOilDeclare;
    @Bind(R.id.iv_group_b)
    LinearLayout ivGroupB;
    @Bind(R.id.dl)
    DragLayout dl;
    @Bind(R.id.ll_shengchan)
    LinearLayout ll_shengchan;
    @Bind(R.id.ll_liutong)
    LinearLayout ll_liutong;
    @Bind(R.id.ll_chanyin)
    LinearLayout ll_chanyin;
    @Bind(R.id.rl_news)
    RelativeLayout rl_news;
    @Bind(R.id.iv_tip)
    ImageView iv_tip;

    @Bind(R.id.iv_tip_notice)
    ImageView iv_tip_notice;
    @Bind(R.id.tv_news)
    TextView tvNews;
    @Bind(R.id.rl)
    RelativeLayout rl;

    private TipDialog mTipDialog;
    private MainBannerView banner;
    private Intent intent;
    private ArrayList<BaseBannerBean> bannerList;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private double latitude;
    private double longitude;
    private String cityName;

    //更新APK
    private CompleteReceiver completeReceiver;
    private TipDialog tipDialog;
    private long downloadId;
    private int mExistNewSysInfo;
    private int mExistNewGovInfo;
    private int mExistNewInfo = 0;
    private String mStatesAction;
    private int mExistNewAd = 0;

    private Present present;
    private SharedPreferences sp;
    private String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing);
        ButterKnife.bind(this);
        sp = getSharedPreferences(MyRes.CONFIG,0);
        token = sp.getString(MyRes.MY_TOKEN, "");
        present = new Present(this);
        init();
       /* checkUpdate();
        if (!MyUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
            getInfoData();
        }*/

        present.getResMessage(token);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bannerList == null || bannerList.size() == 0) {
            getBannerData();
        }


        String message = SPUtil.getString(this, Constants.NOTICE_MESSAGE); //通知栏的信息内容


        if (!TextUtils.isEmpty(message)) {
            mStatesAction = SPUtil.getString(this, "statesAction");
            declareOil(message);//是否交油
            SPUtil.put(this, Constants.NOTICE_MESSAGE, "");//把保存的通知栏信息置空
            SPUtil.put(this, "statesAction", "");
        }

        if (banner != null) {
            banner.onStartChange();
        }

        if (!MyUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
            isExistNewInfo(MyApplication.getInstance().getUser().id);
        }


        //判断是否登录  未登录状态
        //if (!SPUtil.getBoolean(this, Constants.IS_LOGIN)) {
        if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
            tvRight.setText("0");
            tvLogout.setText(getString(R.string.login_main));
            tvStoreName.setText("餐厅名称");
            appRatingbar.setRating(0);
            photo.setImageResource(R.mipmap.peoson);
        } else {
            tvRight.setText(MyApplication.getInstance().getUser().integral);//100
            tvLogout.setText(getString(R.string.logout));

            RestaurantBean bean = MyApplication.getInstance().getUser();
            tvStoreName.setText(bean.restaurantName);

            ImageLoader.getInstance().displayImage(bean.photoUrl, photo, MyApplication.getInstance().getIconOptions());
            if (!TextUtils.isEmpty(bean.grade)) {
                int grade = Integer.parseInt(bean.grade);
                appRatingbar.setRating(grade);
            }
        }
        getLoction();
    }

    private void init() {
        mTipDialog = new TipDialog(this);
        tvSetting.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvLogout.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        ivBack.setOnClickListener(this);
        photo.setOnClickListener(this);

        tv_suozheng_suopiao.setOnClickListener(this);
        tvCookingOilDeclare.setOnClickListener(this);
        tvRefuseOilDeclare.setOnClickListener(this);
        tvNotice.setOnClickListener(this);
        tvConversionRatio.setOnClickListener(this);
        tvNearMess.setOnClickListener(this);
        tvUserComment.setOnClickListener(this);
        tvQrCode.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        llCookingOilDeclare.setOnClickListener(this);
        llRefuseOilDeclare.setOnClickListener(this);
        ivGroupB.setOnClickListener(this);
        ll_shengchan.setOnClickListener(this);
        ll_liutong.setOnClickListener(this);
        ll_chanyin.setOnClickListener(this);
        rl_news.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                dl.open(); //打开侧滑
               //finish();
                break;

            case R.id.iv_back:
                dl.close(); //关闭侧滑
                break;

            case R.id.ll_cooking_oil_declare: //食用油采购(废弃油)
               /*if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, CookingOilDeclareActivity.class));
                }*/
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, RefuseOilDeclareActivity.class));
                }

                break;

            case R.id.ll_refuse_oil_declare: //废弃油申报(餐厨垃圾)
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, RefuseOilDeclareActivity.class));
                }
                break;

            case R.id.iv_group_b: //团购（采购分析）
                //startActivity(new Intent(this, GroupBuyActivity.class));
                startActivity(new Intent(getApplicationContext(), ProcurementAnalyzeActivity.class));
                break;

            case R.id.tv_cooking_oil_declare:  //食用油

                boolean b = AndPermission.hasPermission(this, Manifest.permission.READ_PHONE_STATE);
                if (b) {
                }

                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, CookingOilDeclareActivity.class));
                }
                break;

            case R.id.tv_refuse_oil_declare: //地沟油
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, RefuseOilDeclareActivity.class));
                }

                break;

            case R.id.tv_setting: //设置
                startActivity(new Intent(this, com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.SettingActivity.class));
                break;

            case R.id.rl_notice: //公告
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, NoticeActivity.class));
                    iv_tip_notice.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iv_tip_notice.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
                break;

            case R.id.photo: //我的餐厅
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, MyRestaurantActivity.class));
                }
                break;

            case R.id.tv_conversion_ratio://转换比例
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, ConversionRatioActivity.class));
                }
                break;

            case R.id.tv_near_mess://附近餐厅
                Intent intent = new Intent(this, NearRestaurantActivity.class);
                intent.putExtra("lon", longitude + "");
                intent.putExtra("lat", latitude + "");
                intent.putExtra("cityName", cityName);
                startActivity(intent);
                break;

            case R.id.tv_logout: //注销
                if (!TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    //如果登录 就退出注销
                    logout();
                } else {
                    startActivity(new Intent(this, LoginActivtiy.class));
                }
                break;

            case R.id.tv_user_comment://用户评价
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, UserCommentActivity.class));
                }
                break;

            case R.id.tv_qr_code://二维码
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    startActivity(new Intent(this, QRCodeActivity.class));
                }
                break;
            case R.id.ll_shengchan://生产
                Constants.AccountDetaiTools.typeid = 1;
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    /*intent = new Intent(getMyApplicationlicationContext(), DemandingCertificatesActivity.class);
                    intent.putExtra("selectType", "1");
                    startActivity(intent);*/
                    intent = new Intent(getApplicationContext(), ProductionActivity.class);
                    intent.putExtra("selectType", "1");
                    startActivity(intent);
                }
                break;
            case R.id.ll_liutong://流通
                Constants.AccountDetaiTools.typeid = 2;
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    intent = new Intent(getApplicationContext(), ProductionActivity.class);
                    intent.putExtra("selectType", "2");
                    startActivity(intent);
                }
                break;
            case R.id.ll_chanyin://餐饮
                Constants.AccountDetaiTools.typeid = 3;
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
//                    intent = new Intent(getApplicationContext(), DemandingCertificatesThreeActivity.class);
                    intent = new Intent(getApplicationContext(), SupplierOrLedgerActivity.class);
                    intent.putExtra("selectType", "3");
                    startActivity(intent);
                }
                break;
            case R.id.tv_suozheng_suopiao://索证索票记录
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    /*intent = new Intent(getApplicationContext(), SuoZhengSuoPiaoRecordActivity.class);
                    startActivity(intent);*/
                    intent = new Intent(getApplicationContext(), SuoZhenSuoPiaoListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_news://消息
                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().id)) {
                    startActivity(new Intent(this, LoginActivtiy.class));
                } else {
                    intent = new Intent(getApplicationContext(), NewsActivity.class);
                    intent.putExtra("mExistNewSysInfo", mExistNewSysInfo);
                    intent.putExtra("mExistNewGovInfo", mExistNewGovInfo);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 让用户选择是否交油
     *
     * @param bundleString
     */
    private void declareOil(String bundleString) {
        tip(bundleString);
        mTipDialog.showCancel();
        if ("2".equals(mStatesAction)) {
            //查看消息
            mTipDialog.setButtonSure("查看");
            mTipDialog.setOnClickListener(new TipDialog.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mTipDialog.dismiss();
                    intent = new Intent(XiangQingActivity.this, NewsActivity.class);
                    intent.putExtra("mExistNewSysInfo", mExistNewSysInfo);
                    intent.putExtra("mExistNewGovInfo", mExistNewGovInfo);
                    startActivity(intent);
                }
            });
        }else if("100".equals(mStatesAction)){
            mTipDialog.setButtonSure("上传");
            mTipDialog.setOnClickListener(new TipDialog.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mTipDialog.dismiss();
                    startActivity(new Intent(XiangQingActivity.this, EditInfoActivity.class));
                }
            });
        }

        else {
            mTipDialog.setButtonSure("申报");
            mTipDialog.setOnClickListener(new TipDialog.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mTipDialog.dismiss();
                    startActivity(new Intent(XiangQingActivity.this, RefuseOilDeclareActivity.class));
                }
            });
        }
    }

    /**
     * 获取轮播图信息
     */
    private void getBannerData() {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("Top", "5");
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(10000);
        FastHttpHander.ajaxWebServer(Constants.BANNERSERVER_URL, "GetBannerList", params, config, this);
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("typeid", "1");
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.SETTING_URL, "GetNewVersion", params, config, this);
    }

    /**
     * 检查未读消息
     */
    private void isExistNewInfo(String userId) {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("userid", userId);



        InternetConfig config = new InternetConfig();
        config.setKey(2);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "IsExistNewInfo", params, config, this);
    }

    /**
     * 获取餐厅信息
     */
    private void getInfoData() {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("_id", MyApplication.getInstance().getUser().id);
        InternetConfig config = new InternetConfig();
        config.setKey(3);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetRestaurantInfo", params, config, this);
    }

    @InjectHttp
    private void injectHttp(ResponseEntity entity) {
        switch (entity.getKey()) {
            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (jsonObject.optInt("status") == 0) {
                        JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("ds");
                        int length = jsonArray.length();
                        bannerList = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            String id = object.optString("id");
                            String img_url = object.optString("img_url");
                            String content = object.optString("content");
                            bannerList.add(new BaseBannerBean(id, img_url, content));
                        }
                        if (banner == null) {
                            banner = new MainBannerView(this);
                            bannerContent.addView(banner);
                        }
                        banner.update(bannerList);

                    } else {
                        MyToastUtils.show(this, "请检查网络");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1://检查更新
                checkUpDataAction(entity);
                break;
            case 2://检查新消息
                checkNews(entity);
                break;
            case 3://获取餐厅信息
                persionData(entity.getContentAsString());
                break;
        }
    }

    private void checkNews(ResponseEntity entity) {
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());
            String status = jsonObject.optString("status");
            if ("0".equals(status)) {
                JSONObject optJSONObject = jsonObject.optJSONObject("data");
                if (optJSONObject != null) {
                    mExistNewInfo = optJSONObject.optInt("ExistNewInfo");
                    mExistNewSysInfo = optJSONObject.optInt("ExistNewSysInfo");
                    mExistNewGovInfo = optJSONObject.optInt("ExistNewGovInfo");
                    mExistNewAd = optJSONObject.optInt("ExistNewAd");//新的公告
                    if (mExistNewInfo > 0) {
                        iv_tip.setVisibility(View.VISIBLE);
                    } else {
                        iv_tip.setVisibility(View.GONE);
                    }
                    if (mExistNewAd == 1) {
                        iv_tip_notice.setVisibility(View.VISIBLE);
                    } else {
                        iv_tip_notice.setVisibility(View.GONE);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //处理用户数据
    private void persionData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            if (jsonObject.optInt("status") == 0) {
                JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").getJSONObject(0);
                String id = object.optString("id"); //餐厅id
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
                List<String> licenseImgData = splitLicenseImg(licenseImg);
                //食品经营许可证有效期
                String licenseDate = object.optString("licenseDate");
                //营业执照或其他证件有效期
                String licenseTime = object.optString("licenseTime");
                RestaurantBean bean = new RestaurantBean(img_url, restaurantName, restaurantAddress,
                        mobilePhone, restaurantPhone, xing, integral,
                        cookingType, cookingTypeId, proName, cityName,
                        disName, id, proid, cityid, areaid, licenseDate, licenseTime);

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
                MyApplication.getInstance().setUser(bean);
                MyApplication.getInstance().isUpdataPersionMessage = false;
                tvStoreName.setText(bean.restaurantName);
                if (!MyUtils.isEmpty(bean.grade)) {
                    int grade = Integer.parseInt(bean.grade);
                    appRatingbar.setRating(grade);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<String> splitLicenseImg(String licenseImg) {
        if (!MyUtils.isEmpty(licenseImg)) {
            String[] split = licenseImg.split(",");
            List<String> licenseImgData = Arrays.asList(split);
            return licenseImgData;
        }
        return null;
    }

    /**
     * dingwei
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
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        latitude = aMapLocation.getLatitude();
        longitude = aMapLocation.getLongitude();
        cityName = aMapLocation.getCity();
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    private void logout() {
        mTipDialog.show();
        mTipDialog.setTitle("提醒");
        mTipDialog.setMessage("确定要退出登录吗");
        mTipDialog.showCancel();
        mTipDialog.setOnClickListener(new TipDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录状态改为未登录
                SPUtil.put(XiangQingActivity.this, Constants.IS_LOGIN, false);
                MyApplication.getInstance().setUser(null);

                //极光推送别名置空
                //JPushInterface.setAlias(MainActivity.this.getApplicationContext(), "", null);

                JPushAlias jPushAlias = new JPushAlias();
                jPushAlias.setAlias(getApplicationContext(), "");
                mTipDialog.dismiss();
                startActivity(new Intent(XiangQingActivity.this, LoginActivtiy.class));
            }
        });
        mTipDialog.setOnCanceClicklListener(new TipDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTipDialog.dismiss();
            }
        });
    }



    //
    private void tip(String msg) {
        mTipDialog.show();
        mTipDialog.setTitle("提醒");
        mTipDialog.setOnClickListener(null);
        mTipDialog.setMessage(msg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (banner != null) {
            banner.onDestroyHandler();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTipDialog.isShowing()) {
            mTipDialog.dismiss();
        }
        if (completeReceiver != null) {
            unregisterReceiver(completeReceiver);
        }
    }

    private void checkUpDataAction(ResponseEntity entity) {
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());
            if (jsonObject.optInt("status") == 0) {
                JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").optJSONObject(0);
                double banben = object.optDouble("Banben");
                final String url = object.optString("FileUrl");
                PackageManager manager = getPackageManager();
                PackageInfo info = manager.getPackageInfo(getPackageName(), 0);

                String versionName = info.versionName; //版本号
                if (banben > Double.parseDouble(versionName)) {
                    //如果后台版本大于本地版本就提示更新
                    tip("有新的版本 是否更新？");
                    mTipDialog.showCancel();
                    mTipDialog.setOnClickListener(new TipDialog.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            downloadApk(url);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载更新包
     *
     * @param url 下载地址
     */
    private void downloadApk(String url) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        String dir = isFolderExist("youlaiyouwang");
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        completeReceiver = new CompleteReceiver();
        File f = new File(dir, "update.apk");
        completeReceiver.save_path = f.getAbsolutePath();
        if (f.exists()) {
            f.delete();
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir("youlaiyouwang", "update.apk");
        request.allowScanningByMediaScanner();// 表示允许MediaScanner扫描到这个文件，默认不允许。
        request.setTitle(getString(R.string.app_name));// 设置下载中通知栏提示的标题
        request.setDescription("正在下载" + getString(R.string.app_name));// 设置下载中通知栏提示的介绍
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadId = downloadManager.enqueue(request);
        registerReceiver(completeReceiver, filter);
    }

    private String isFolderExist(String dir) {
        File folder = Environment.getExternalStoragePublicDirectory(dir);
        return folder.getAbsolutePath();
    }

    public void openApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    //获取企业认证的信息
    @Override
    public void getCode(String s) {
        Log.i("dd", "getCode: "+s);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showFaliure(String s) {

    }

    @Override
    public void getLogin(String s) {

    }

    @Override
    public void getUpDate(String s) {

    }

    private class CompleteReceiver extends BroadcastReceiver {
        public String save_path = "";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == downloadId)
                downComplete(save_path);
        }

        private void downComplete(String filePath) {
            File _file = new File(filePath);
            openApk(XiangQingActivity.this, _file);
        }
    }
}

