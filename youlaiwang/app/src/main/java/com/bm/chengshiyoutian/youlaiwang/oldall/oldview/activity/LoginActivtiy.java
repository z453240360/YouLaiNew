package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.JPushAlias;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 登录
 * Created by youj on 2016/1/11.
 */
public class LoginActivtiy extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.et_id)
    EditText etId;
    @Bind(R.id.ed_psw)
    EditText edPsw;
    @Bind(R.id.cb_rember_psw)
    CheckBox cbRemberPsw;
    @Bind(R.id.tv_free_register)
    TextView tvFreeRegister;
    @Bind(R.id.tv_forget)
    TextView tvForget;
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.tv_verify_phone)
    TextView tvVerifyPhone;
    @Bind(R.id.tv_verify_psw)
    TextView tvVerifyPsw;
    @Bind(R.id.tv_login_status)
    TextView tvLoginStatus;
    private String phone;
    private String psw;
    private ProgressDialog progressDialog;
    private String type;
    private String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        ivLeft.setOnClickListener(this);
        title.setText("登录");
        tvForget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvFreeRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvFreeRegister.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        btLogin.setOnClickListener(this);

        //判断是否记住密码
        if (SPUtil.getBoolean(this, Constants.REMEMBER_PSW)) {
            cbRemberPsw.setChecked(true);
            edPsw.setText(SPUtil.getString(this, Constants.PASSWORD));
        }
        String phoneStr = SPUtil.getString(this, Constants.PHONENUM);
        etId.setText(phoneStr);
        if (!TextUtils.isEmpty(phoneStr)) {
            etId.setSelection(phoneStr.length());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.tv_forget: //忘记密码
                startActivity(new Intent(this, ForgetPswActivity.class));
                break;

            case R.id.tv_free_register: //免费注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.bt_login:
                login();
                break;
            default:
                break;
        }
    }

    /**
     * 登录验证
     */
    private void login() {
        phone = etId.getText().toString().trim();
        psw = edPsw.getText().toString().trim();

        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            tvVerifyPhone.setVisibility(View.VISIBLE);
            return;
        } else {
            tvVerifyPhone.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(psw) || psw.length() < 6 || psw.length() > 16) {
            tvVerifyPsw.setVisibility(View.VISIBLE);
            tvVerifyPsw.setText("密码格式不正确");
            return;
        } else {
            tvVerifyPsw.setVisibility(View.INVISIBLE);
        }


        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("phone", phone);
        params.put("password", psw);
//        params.put("password", Md5Utils.encode(psw));
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "OnLoginPhone", params, config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    int status = jsonObject.optInt("status");
                    if (0 == status) {
                        String restaurantID = jsonObject.optString("RestaurantID");
                        userPhone = jsonObject.optString("data");
                        type = jsonObject.optString("type");

                        tvLoginStatus.setVisibility(View.VISIBLE);
                        getInfoData(restaurantID); //登录成功获取信息
                    } else {
                        tvVerifyPsw.setVisibility(View.VISIBLE);
                        tvVerifyPsw.setText(jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
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
                                cookingType, cookingTypeId, proName, cityName, disName, id, proid, cityid, areaid,licenseDate,licenseTime, Integer.parseInt(type));
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
                        bean.childAccountType = Integer.parseInt(type);
                        MyApplication.getInstance().setUser(bean);
                        //CacheImageService.startsevice(this);
                        SPUtil.put(this, Constants.IS_LOGIN, true);
                        //JPushInterface.setAlias(getApplicationContext(), App.getInstance().getUser().id, null);
                        JPushAlias jPushAlias = new JPushAlias();
                        jPushAlias.setAlias(getApplicationContext(), MyApplication.getInstance().getUser().id);
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                rememberPsw(); //判断是否记住密码
                                finish();
                            }
                        }).start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
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

    /**
     * 获取餐厅信息
     *
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
     * 判断是否记住密码
     */
    private void rememberPsw() {

        if (cbRemberPsw.isChecked()) {
            SPUtil.put(this, Constants.REMEMBER_PSW, true);
            SPUtil.put(this, Constants.PASSWORD, psw);
        } else {
            SPUtil.put(this, Constants.REMEMBER_PSW, false);
            SPUtil.put(this, Constants.PASSWORD, "");
        }
        SPUtil.put(this, Constants.PHONENUM, phone);
    }


    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        MyToastUtils.show(this, "网络错误");
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
