package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectBefore;
import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 我的餐厅
 * Created by youj on 2016/1/13.
 */
public class MyRestaurantActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.photo)
    RoundImageView photo;
    @Bind(R.id.app_ratingbar)
    RatingBar appRatingbar;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.tv_cook_type)
    TextView tvCookType;
    @Bind(R.id.tv_restaurant_name)
    TextView tvRestaurantName;
    @Bind(R.id.tv_restaurant_address)
    TextView tvRestaurantAddress;
    @Bind(R.id.tv_restaurant_phone)
    TextView tvRestaurantPhone;
    @Bind(R.id.bt_edit)
    Button btEdit;
    @Bind(R.id.iv_edit_message)
    ImageView iv_edit_message;
    private ProgressDialog progressDialog;

    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_restaurant);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        title.setText("我的商家");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.set);
        ivRight.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        btEdit.setOnClickListener(this);
        photo.setOnClickListener(this);
        tvRestaurantPhone.setOnClickListener(this);
        iv_edit_message.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!MyApplication.getInstance().isUpdataPersionMessage) {
            setData();
        } else {
            getInfoData();
        }
    }

    /**
     * 给控件设置数据
     */
    private void setData() {
        RestaurantBean bean = MyApplication.getInstance().getUser();
        tvPhone.setText(bean.mobilePhone);
        tvIntegral.setText(bean.integral);
        if (TextUtils.isEmpty(bean.industryName) || "null".equals(bean.industryName)) {
            tvCookType.setText("");
        } else {
            tvCookType.setText(bean.industryName);
        }
        tvRestaurantName.setText(bean.restaurantName != null ? bean.restaurantName : "");
        if (TextUtils.isEmpty(bean.streetName) || "null".equals(bean.streetName)) {
            String proname = bean.proName.equals("null") ? "-" : bean.proName+"-";
            String cityName = bean.cityName.equals("null") ? "" : bean.cityName+"-";
            String areaName = bean.areaName.equals("null") ? "" : bean.areaName+"-";
            String address = bean.address.equals("null") ? "" : bean.address;
            tvRestaurantAddress.setText( proname + cityName  + areaName  + address);
        } else {
            String proname = bean.proName.equals("null") ? "-" : bean.proName+"-";
            String cityName = bean.cityName.equals("null") ? "" : bean.cityName+"-";
            String areaName = bean.areaName.equals("null") ? "" : bean.areaName+"-";
            String address = bean.address.equals("null") ? "" : bean.address;
            String streetName = bean.streetName.equals("null") ? "" : bean.streetName+"-";
            tvRestaurantAddress.setText(proname  + cityName  + areaName  + streetName  + address);
        }
        tvRestaurantPhone.setText(bean.restaurantPhone);
        if (!TextUtils.isEmpty(bean.grade)) {
            int grade = Integer.parseInt(bean.grade);
            appRatingbar.setRating(grade);
        }
        ImageLoader.getInstance().displayImage(bean.photoUrl, photo, MyApplication.getInstance().getIconOptions());
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.iv_right:
                startActivity(new Intent(this, SettingActivity.class));
                break;

            case R.id.bt_edit:
                if (MyApplication.getInstance().getUser().childAccountType == 0) {
                    startActivity(new Intent(this, EditInfoActivity.class));
                } else {
                    Toast.makeText(this, "您没有权限修改资料", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_phone:
                String phone = tvPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    callPhone(phone);
                }
                break;

            case R.id.tv_restaurant_phone:
                String phoneNum = tvRestaurantPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNum)) {
                    callPhone(phoneNum);
                }
                break;
            case R.id.iv_edit_message:
            case R.id.photo:
                if (MyApplication.getInstance().getUser().childAccountType == 0) {
                    Intent intent = new Intent(getApplicationContext(), RegisterThirdActivity.class);
                    intent.putExtra("fromActivity", "MyRestaurantActivity");
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "您没有权限修改资料", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 弹出打电话的dialog
     * @param phoneNum
     */
    private void callPhone(final String phoneNum) {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_call_phone, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        final WindowManager.LayoutParams params = getWindow().getAttributes();//创建当前界面的一个参数对象
        params.alpha = 0.5f;//设置参数的透明度
        getWindow().setAttributes(params);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        final TextView phone = (TextView) view.findViewById(R.id.tv_phone);
        phone.setText(phoneNum);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                if (ActivityCompat.checkSelfPermission(MyRestaurantActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(MyRestaurantActivity.this,new String[]{Manifest.permission.CALL_PHONE},100);
                    return;
                }
                startActivity(intent);
                popupWindow.dismiss();
                params.alpha = 1f;//设置参数的透明度
                getWindow().setAttributes(params);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                params.alpha = 1f;//设置参数的透明度
                getWindow().setAttributes(params);
            }
        });


    }

    /**
     * 获取餐厅信息
     */
    private void getInfoData() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("_id", MyApplication.getInstance().getUser().id);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetRestaurantInfo", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        String contentAsString = entity.getContentAsString();
        int key = entity.getKey();
        switch (key) {
            case 0:
                persionData(contentAsString);
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

    //处理用户数据
    private void persionData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            Log.i("Home",jsonObject+"");
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
                //食品经营许可证有效期
                String licenseDate = object.optString("licenseDate");
                //营业执照或其他证件有效期
                String licenseTime = object.optString("licenseTime");

                List<String> licenseImgData = splitLicenseImg(licenseImg);

                RestaurantBean bean = new RestaurantBean(img_url, restaurantName, restaurantAddress,
                        mobilePhone, restaurantPhone, xing, integral,
                        cookingType, cookingTypeId, proName, cityName,
                        disName, id, proid, cityid, areaid,licenseDate,licenseTime);
                bean.address = restaurantAddress;
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
                setData();
                MyApplication.getInstance().isUpdataPersionMessage = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
}
