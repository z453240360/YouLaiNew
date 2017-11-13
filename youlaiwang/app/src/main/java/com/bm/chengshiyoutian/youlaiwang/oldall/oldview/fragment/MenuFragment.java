package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.ConversionRatioActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.CookingOilDeclareActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.LoginActivtiy;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.MyRestaurantActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.NearRestaurantActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.NoticeActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.QRCodeActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.RefuseOilDeclareActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.SettingActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.UserCommentActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.RoundImageView;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.TipDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


/**
 * Created by youj on 2016/1/8.
 */
public class MenuFragment extends Fragment implements View.OnClickListener, AMapLocationListener {

    /**
     * 关掉侧滑
     */
    @Bind(R.id.iv_back)
    ImageView ivBack;

    /**
     * 头像
     */
    @Bind(R.id.photo)
    RoundImageView photo;
    /**
     * 店名
     */
    @Bind(R.id.tv_store_name)
    TextView storeName;
    /**
     * 评分
     */
    @Bind(R.id.app_ratingbar)
    RatingBar appRatingbar;
    /**
     * 食用油申报
     */
    @Bind(R.id.tv_cooking_oil_declare)
    TextView tvCookingOilDeclare;
    /**
     * 废弃油申报
     */
    @Bind(R.id.tv_refuse_oil_declare)
    TextView tvRefuseOilDeclare;
    /**
     * 公告
     */
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    /**
     * 转化比例
     */
    @Bind(R.id.tv_conversion_ratio)
    TextView tvConversionRatio;
    /**
     * 附近餐厅
     */
    @Bind(R.id.tv_near_mess)
    TextView tvNearMess;
    /**
     * 用户评论
     */
    @Bind(R.id.tv_user_comment)
    TextView tvUserComment;
    /**
     * 餐厅二维码
     */
    @Bind(R.id.tv_qr_code)
    TextView tvQrCode;
    /**
     * 设置
     */
    @Bind(R.id.tv_setting)
    TextView tvSetting;
    /**
     * 注销
     */
    @Bind(R.id.tv_logout)
    TextView tvLogout;

    private View view;
    private TipDialog tipDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.left_menu, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        tipDialog = new TipDialog(getActivity());
        tvSetting.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvLogout.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        ivBack.setOnClickListener(this);
        photo.setOnClickListener(this);
        tvCookingOilDeclare.setOnClickListener(this);
        tvRefuseOilDeclare.setOnClickListener(this);
        tvNotice.setOnClickListener(this);
        tvConversionRatio.setOnClickListener(this);
        tvNearMess.setOnClickListener(this);
        tvUserComment.setOnClickListener(this);
        tvQrCode.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SPUtil.getBoolean(getActivity(), Constants.IS_LOGIN)) {
            //未登录状态
            tvLogout.setText(getString(R.string.login));
            storeName.setText("餐厅名称");
            appRatingbar.setRating(0);
        } else {
            tvLogout.setText(getString(R.string.logout));
            storeName.setText(MyApplication.getInstance().getUser().restaurantName);
            RestaurantBean bean = MyApplication.getInstance().getUser();
            storeName.setText(bean.restaurantName);
            ImageLoader.getInstance().displayImage(bean.photoUrl, photo, MyApplication.getInstance().getOptions());
            int grade = Integer.parseInt(bean.grade);
            appRatingbar.setRating(grade);
        }
        getLoction();
    }

    private double latitude;
    private double longitude;
    private String cityName;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_back:
//                ((MainActivity) getActivity()).getSlidingMenu().toggle();//关闭侧滑
                break;

            case R.id.tv_cooking_oil_declare:  //食用油
                if (!SPUtil.getBoolean(getActivity(), Constants.IS_LOGIN)) {
                    startActivity(new Intent(getActivity(), LoginActivtiy.class));
                } else {
                    startActivity(new Intent(getActivity(), CookingOilDeclareActivity.class));
                }
                break;

            case R.id.tv_refuse_oil_declare: //地沟油
                if (!SPUtil.getBoolean(getActivity(), Constants.IS_LOGIN)) {
                    startActivity(new Intent(getActivity(), LoginActivtiy.class));
                } else {
                    startActivity(new Intent(getActivity(), RefuseOilDeclareActivity.class));
                }
                break;

            case R.id.tv_setting: //设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;

            case R.id.tv_notice: //公告
                startActivity(new Intent(getActivity(), NoticeActivity.class));
                break;

            case R.id.photo: //我的餐厅
                if (!SPUtil.getBoolean(getActivity(), Constants.IS_LOGIN)) {
                    startActivity(new Intent(getActivity(), LoginActivtiy.class));
                } else {
                    startActivity(new Intent(getActivity(), MyRestaurantActivity.class));
                }
                break;

            case R.id.tv_conversion_ratio://转换比例
                if (!SPUtil.getBoolean(getActivity(), Constants.IS_LOGIN)) {
                    startActivity(new Intent(getActivity(), LoginActivtiy.class));
                } else {
                    startActivity(new Intent(getActivity(), ConversionRatioActivity.class));
                }
                break;

            case R.id.tv_near_mess://附近餐厅
                Intent intent = new Intent(getActivity(), NearRestaurantActivity.class);
                intent.putExtra("lon", longitude + "");
                intent.putExtra("lat", latitude + "");
                intent.putExtra("cityName", cityName);
                startActivity(intent);
                break;

            case R.id.tv_logout: //注销
                if (SPUtil.getBoolean(getActivity(), Constants.IS_LOGIN)) {
                    //如果登录 就退出注销
                    logout();
                } else {
                    startActivity(new Intent(getActivity(), LoginActivtiy.class));
                }
                break;

            case R.id.tv_user_comment://用户评价
                if (!SPUtil.getBoolean(getActivity(), Constants.IS_LOGIN)) {
                    startActivity(new Intent(getActivity(), LoginActivtiy.class));
                } else {
                    startActivity(new Intent(getActivity(), UserCommentActivity.class));
                }
                break;

            case R.id.tv_qr_code://二维码
                if (!SPUtil.getBoolean(getActivity(), Constants.IS_LOGIN)) {
                    startActivity(new Intent(getActivity(), LoginActivtiy.class));
                } else {
                    startActivity(new Intent(getActivity(), QRCodeActivity.class));
                }
                break;
            default:
                break;
        }
    }

    private void logout() {
        tipDialog.show();
        tipDialog.setTitle("提醒");
        tipDialog.setMessage("确定要退出登录吗");
        tipDialog.showCancel();
        tipDialog.setOnClickListener(new TipDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录状态改为未登录
                SPUtil.put(getActivity(), Constants.IS_LOGIN, false);
                //极光推送别名置空
                JPushInterface.setAlias(getActivity().getApplicationContext(), "", null);
                tipDialog.dismiss();
                startActivity(new Intent(getActivity(), LoginActivtiy.class));
            }
        });
        tipDialog.setOnCanceClicklListener(new TipDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.dismiss();
            }
        });
    }


    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;

    /**
     * 定位
     */
    private void getLoction() {
        locationClient = new AMapLocationClient(getActivity());
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
        Log.i("id", latitude + "sss" + longitude + "--" + cityName);
    }


}
