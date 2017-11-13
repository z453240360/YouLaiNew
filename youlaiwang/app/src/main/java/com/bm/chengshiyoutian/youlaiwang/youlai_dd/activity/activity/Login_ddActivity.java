package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.LoginActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ZhaoHuiMIMaActivity;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.bean.DengLuBean1;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.JPushAlias;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login_ddActivity extends AppCompatActivity implements IMainView {


    @Bind(R.id.activity_login2)
    RelativeLayout activityLogin2;
    private EditText phoneNum;
    private EditText passWord;
    @Bind(R.id.mBtn_forgetPassword)
    Button mBtnForgetPassword;
    @Bind(R.id.mBtn_login)
    Button mBtnLogin;
    @Bind(R.id.text_regist)
    TextView textRegist;

    private Present present;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private String phoneNumber;
    private String userPassword;
    private DengLuBean1 dengLuBean1;
    private ProgressDialog dialogs;
    private String phone;
    private String psw;
    private String type;
    private String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorState.setWindowStatusBarColor(this, Color.WHITE);
        ColorState.StatusBarLightMode(this);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);

        init();

        //设置点击空白处，自动隐藏键盘
        activityLogin2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

    }

    private void init() {
        dialogs = new ProgressDialog(this);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        passWord = (EditText) findViewById(R.id.passWord);
        dialogs.setMessage("登陆中，请稍等...");
        dialogs.setCancelable(false);

        sharedPreferences = getSharedPreferences(MyRes.CONFIG, Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();

        present = new Present(this);


    }

    @OnClick({R.id.mBtn_forgetPassword, R.id.mBtn_login, R.id.text_regist})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            //忘记密码
            case R.id.mBtn_forgetPassword:
                Intent intent2 = new Intent(this, ZhaoHuiMIMaActivity.class);
                startActivity(intent2);
                break;
            //登陆
            case R.id.mBtn_login:

                phoneNumber = phoneNum.getText().toString().trim();
                userPassword = passWord.getText().toString().trim();

                if ("".equals(phoneNumber)) {
                    ShowToast.showToast("请输入手机号");
                    return;
                } else if ("".equals(userPassword)) {
                    ShowToast.showToast("请输入密码");
                    return;
                } else {
                    login();
                    present.getLogin(phoneNumber, userPassword);
                }

                break;

            //去注册
            case R.id.text_regist:
                startActivity(new Intent(Login_ddActivity.this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void getCode(String s) {
        userLogin(s);
    }

    @Override
    public void showLoading() {
        dialogs.show();
    }

    @Override
    public void cancelLoading() {
        dialogs.dismiss();
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

    private void userLogin(String s) {

        try {
            JSONObject jsonObject = new JSONObject(s);
            int code = (int) jsonObject.get("code");
            if (code != 200) {
                ShowToast.showToast((String) jsonObject.get("msg"));
                return;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Gson gson = new Gson();
        dengLuBean1 = gson.fromJson(s, DengLuBean1.class);
        int code = dengLuBean1.getCode();
        if (code != 200) {
            ShowToast.showToast(dengLuBean1.getMsg());
            return;
        }

        DengLuBean1.DataBean data = dengLuBean1.getData();
        String token = data.getToken();
        if (!"".equals(token)) {
            edit.putBoolean(MyRes.tag, true);
            edit.putString(MyRes.TOKEN, token);
            edit.putString(MyRes.MY_TOKEN, "Bearer " + token);
            edit.commit();
//            this.setResult(201);
            SPUtil.put(this, Constants.PHONENUM, phoneNum.getText().toString().trim());
            finish();
        }
    }

//    //点击返回
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                startActivity(new Intent(this,MainActivity.class));
//                break;
//        }
//        return super.onKeyUp(keyCode, event);
//    }

    /**
     * 登录验证
     */
    private void login() {
        phone = phoneNum.getText().toString().trim();
        psw = passWord.getText().toString().trim();

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
        switch (entity.getKey()) {
            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    int status = jsonObject.optInt("status");
                    if (0 == status) {
                        String restaurantID = jsonObject.optString("RestaurantID");
                        userPhone = jsonObject.optString("data");
                        type = jsonObject.optString("type");


                        getInfoData(restaurantID); //登录成功获取信息
                    } else {
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
                                cookingType, cookingTypeId, proName, cityName, disName, id, proid, cityid, areaid, licenseDate, licenseTime, Integer.parseInt(type));
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

        SPUtil.put(this, Constants.REMEMBER_PSW, true);
        SPUtil.put(this, Constants.PASSWORD, psw);
        SPUtil.put(this, Constants.PHONENUM, phone);
    }


    @InjectHttpErr
    private void err(ResponseEntity entity) {


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