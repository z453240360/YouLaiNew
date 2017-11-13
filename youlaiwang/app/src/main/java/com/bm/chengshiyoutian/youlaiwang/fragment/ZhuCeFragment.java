package com.bm.chengshiyoutian.youlaiwang.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.bm.chengshiyoutian.youlaiwang.activity.ZhuCeXieyiActivity;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.bean.YanZhengMaBean;
import com.bm.chengshiyoutian.youlaiwang.bean.ZhuCeBean;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;




public class ZhuCeFragment extends Fragment implements View.OnClickListener {

    private TimeCount time;
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private Button btn1;
    private Button btn2;
    private EditText et5;
    private String phone;
    private String psw;

    private String type;
    private String userPhone;


    public static ZhuCeFragment newInstance(String param1) {
        ZhuCeFragment fragment = new ZhuCeFragment();

        return fragment;
    }

    public ZhuCeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhu_ce, container, false);

        initView(view);
        return view;
    }


    private void initView(View view) {
        et1 = (EditText) view.findViewById(R.id.et1);
        et2 = (EditText) view.findViewById(R.id.et2);
        et3 = (EditText) view.findViewById(R.id.et3);
        et4 = (EditText) view.findViewById(R.id.et4);
        btn1 = (Button) view.findViewById(R.id.btn1);

        TextView xieyi1 = (TextView) view.findViewById(R.id.xieyi1);
        TextView xieyi2 = (TextView) view.findViewById(R.id.xieyi2);
        TextView xieyi3 = (TextView) view.findViewById(R.id.xieyi3);
        xieyi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ZhuCeXieyiActivity.class);
                intent.putExtra("key", "1");
                startActivity(intent);
            }
        });


        xieyi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ZhuCeXieyiActivity.class);
                intent.putExtra("key", "2");
                startActivity(intent);
            }
        });


        xieyi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ZhuCeXieyiActivity.class);
                intent.putExtra("key", "3");
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(this);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        et5 = (EditText) view.findViewById(R.id.et5);
        et5.setOnClickListener(this);
    }

    YanZhengMaBean yanZhengMaBean;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1://注册
                if (submit()) {

                    LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
                    params.put("phone", et1.getText().toString().trim());
                    params.put("password", et3.getText().toString().trim());
//        params.put("password", Md5Utils.encode(psw));
                    InternetConfig config = new InternetConfig();
                    config.setKey(0);
                    config.setTimeout(Constants.TIMEOUT);
                    FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "OnLoginPhone", params, config, this);


                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/register", RequestMethod.POST);
                    try {
                        stringRequest.add("mobile", et1.getText().toString().trim());
                        stringRequest.add("code", et2.getText().toString().trim());
                        stringRequest.add("password", et3.getText().toString().trim());
                        stringRequest.add("token", yanZhengMaBean.getData().getToken());
                        stringRequest.add("referee_mobile", et5.getText().toString().trim());
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "注册时，请不要退出当前界面", Toast.LENGTH_SHORT).show();
                    }
                    CallServer.getInstance().add(1, stringRequest, new OnResponseListener<String>() {

                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response<String> response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                String code = jsonObject.getString("code");
                                String msg = jsonObject.getString("msg");

                                if ("200".equals(code)) {
                                    String token = GsonUtils.getInstance().fromJson(response.get(), ZhuCeBean.class).getData().getToken();
                                    SharedPreferences sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);
                                    sp.edit().putBoolean(MyRes.tag,true).commit();
                                    sp.edit().putString(MyRes.MY_TOKEN, "Bearer "+token).commit();
                                    sp.edit().putString(MyRes.TOKEN, token).commit();
                                    SPUtil.put(getContext(), Constants.PHONENUM, et1.getText().toString().trim());
                                    ShowToast.showToast("注册成功");
                                   getActivity().finish();


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

                        }
                    });
                } else {
                    Toast.makeText(getContext(), "请把资料填写完整", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.btn2://获取验证码


                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/sms/register", RequestMethod.POST);
                stringRequest.add("mobile", et1.getText().toString().trim());
                //15655451139
                CallServer.getInstance().add(1, stringRequest, new OnResponseListener<String>() {

                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        try {
                            JSONObject object = new JSONObject(response.get());
                            if(object.getInt("code") == 200){
                                yanZhengMaBean = GsonUtils.getInstance().fromJson(response.get(), YanZhengMaBean.class);
                                ShowToast.showToast(yanZhengMaBean.getMsg());
                                time = new TimeCount(60000, 1000);
                                time.start();// 开始计时
                            }else {
                                ShowToast.showToast(object.getString("msg"));
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

                    }
                });

                break;


        }
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btn2.setText("获取验证码");
            btn2.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btn2.setClickable(false);//防止重复点击
            btn2.setText(millisUntilFinished / 1000 + "s");
        }
    }

    private boolean submit() {
        // validate
        String et1String = et1.getText().toString().trim();
        if (TextUtils.isEmpty(et1String)) {

            return false;
        }

        String et2String = et2.getText().toString().trim();
        if (TextUtils.isEmpty(et2String)) {

            return false;
        }

        String et3String = et3.getText().toString().trim();
        if (TextUtils.isEmpty(et3String)) {

            return false;
        }

        String et4String = et4.getText().toString().trim();
        return !TextUtils.isEmpty(et4String);
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