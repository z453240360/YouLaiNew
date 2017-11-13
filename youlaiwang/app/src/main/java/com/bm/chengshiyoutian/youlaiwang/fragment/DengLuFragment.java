package com.bm.chengshiyoutian.youlaiwang.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.MainActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ZhaoHuiMIMaActivity;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.bean.DengLuBean1;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.JPushAlias;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;




public class DengLuFragment extends Fragment implements View.OnClickListener {


    private EditText et1;
    private EditText et2;
    private TextView tv3;
    private Button btn1;

    //登录按钮是否只点了一次
    private String tag = "1";

    private String phone;
    private String psw;
    private String type;
    private String userPhone;


    public static DengLuFragment newInstance(String param1) {
        DengLuFragment fragment = new DengLuFragment();

        return fragment;
    }

    public DengLuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deng_lu, container, false);

        initView(view);
        return view;
    }


    private void initView(View view) {
        et1 = (EditText) view.findViewById(R.id.et1);
        et2 = (EditText) view.findViewById(R.id.et2);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        tv3.setOnClickListener(this);
    }

    /**
     * 登陆+++忘记密码
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击登陆按钮
            case R.id.btn1:
                if (tag.equals("1")) {

                    if (submit()) {

                        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
                        params.put("phone", et1.getText().toString().trim());
                        params.put("password", et2.getText().toString().trim());
//        params.put("password", Md5Utils.encode(psw));
                        InternetConfig config = new InternetConfig();
                        config.setKey(0);
                        config.setTimeout(Constants.TIMEOUT);
                        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "OnLoginPhone", params, config, this);


                        tag = "2";
                        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/login", RequestMethod.POST);
                        stringRequest.add("mobile", et1.getText().toString().trim());
                        stringRequest.add("password", et2.getText().toString().trim());
                        CallServer.getInstance().add(1, stringRequest, new OnResponseListener<String>() {
                            @Override
                            public void onStart(int what) {

                            }

                            @Override
                            public void onSucceed(int what, Response<String> response) {
                                response.get();
                                try {
                                    JSONObject jsonObject = new JSONObject(response.get());
                                    String code = jsonObject.getString("code");

                                    if (code.equals("200")) {

                                        String tag = getActivity().getIntent().getStringExtra("tag");

                                        if ("two".equals(tag)){
                                            DengLuBean1 dengLuBean = GsonUtils.getInstance().fromJson(response.get(), DengLuBean1.class);
                                            String token = dengLuBean.getData().getToken();
                                            SharedPreferences sp=getActivity().getSharedPreferences(MyRes.CONFIG,0);
                                            sp.edit().putBoolean(MyRes.tag,true).commit();
                                            sp.edit().putString(MyRes.MY_TOKEN, "Bearer "+token).commit();
                                            sp.edit().putString(MyRes.TOKEN, token).commit();

                                            SPUtil.put(getContext(), Constants.PHONENUM, et1.getText().toString().trim());
                                            getActivity().finish();


                                        }else {

                                            //登陆成功，退出到主页面

                                            DengLuBean1 dengLuBean = GsonUtils.getInstance().fromJson(response.get(), DengLuBean1.class);
                                            Intent intent2 = new Intent(getContext(), MainActivity.class);
                                            String token = dengLuBean.getData().getToken();
                                            SharedPreferences sp=getActivity().getSharedPreferences(MyRes.CONFIG,0);

                                            sp.edit().putString(MyRes.MY_TOKEN, "Bearer "+token).commit();
                                            sp.edit().putString(MyRes.TOKEN, token).commit();
                                            intent2.putExtra("token", token);
                                            startActivity(intent2);
                                            getActivity().finish();
                                            EventBus.getDefault().post("123");

                                        }


                                    } else {
                                        ShowToast.showToast(jsonObject.getString("msg"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }




                            }

                            @Override
                            public void onFailed(int what, Response<String> response) {
                                Toast.makeText(getContext(), "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFinish(int what) {
                                tag = "1";
                            }
                        });

                    } else {
                        Toast.makeText(getContext(), "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    }

                }




                break;

            //忘记密码，跳转密码找回页面

            case R.id.tv3:
                Intent intent2 = new Intent(getContext(), ZhaoHuiMIMaActivity.class);
                startActivity(intent2);
                break;
        }
    }

    //判断输入框是否为空
    private boolean submit() {
        // validate
        String et1String = et1.getText().toString().trim();
        if (TextUtils.isEmpty(et1String)) {

            return false;
        }

        String et2String = et2.getText().toString().trim();
        return !TextUtils.isEmpty(et2String);
        // TODO validate success, do something


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
                        SPUtil.put(getContext(), Constants.IS_LOGIN, true);
                        //JPushInterface.setAlias(getApplicationContext(), App.getInstance().getUser().id, null);
                        JPushAlias jPushAlias = new JPushAlias();
                        jPushAlias.setAlias(MyApplication.getContext(), MyApplication.getInstance().getUser().id);
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
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

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        MyToastUtils.show(MyApplication.getContext(), "网络错误");
    }
}