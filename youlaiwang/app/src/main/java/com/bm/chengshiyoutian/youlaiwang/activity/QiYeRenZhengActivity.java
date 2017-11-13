package com.bm.chengshiyoutian.youlaiwang.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GetJsonDataUtil;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.QiYeRCAdapter;
import com.bm.chengshiyoutian.youlaiwang.adapter.QiYeRCAdapter_ddNew;
import com.bm.chengshiyoutian.youlaiwang.bean.CustomHelper;
import com.bm.chengshiyoutian.youlaiwang.bean.MyCityBean;
import com.bm.chengshiyoutian.youlaiwang.bean.QiYeRenZhengBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;




/**
 * Created by sld on 2017/5/11.
 */

public class QiYeRenZhengActivity extends TakePhotoActivity implements View.OnClickListener {

    private int pNum = 0;
    private String tx;
    private String JsonData;
    private int x = -1, y = -1, z = -1;
    private String shenId = "",shiId = "",quId = "";
    private String shen = "", shi = "", qu = "";
    private String names = "";
    private String mentouzha = "";
    private int year, month, date;

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    private Button btn1;
    private RecyclerView rc, rc_zhengJian;
    private Toolbar tb_toolbar;
    private EditText et_name, et_zhuTiYeTai, et_xiangXiDiZhi, et_person;
    private TextView et_diQu, et_youXiaoQi, et_qiTaYouXiaoQi,et_jiankang2;
    private EditText et_number,et_jiankang,et_qiYeDianHua,et_bianHao, et_qiTaBianHao;

    private SharedPreferences sp;
    private SharedPreferences idSp;

    private ArrayList<String> datas;
    private ArrayList<TImage> images;
    private ArrayList<MyCityBean> jsonBean;
    private List<String> company_license = new ArrayList<String>();
    private List<String> sign = new ArrayList<>();
    private ArrayList<String> rcImages = new ArrayList<>();
    private ArrayList<String> rcImages_zhengjian = new ArrayList<>();
    private ArrayList<String> rcImages_mentou = new ArrayList<>();

    private CustomHelper customHelper;
    private QiYeRenZhengBean qiYeRenZhengBean;
    private PopupWindow popupWindow;

    private QiYeRCAdapter qiYeRCAdapter;
    private QiYeRCAdapter_ddNew adapter_ddNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_qiyerenzheng, null);
        setContentView(view);

        customHelper = CustomHelper.of(view);//拍照帮助类
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        idSp = getSharedPreferences("cityId",MODE_PRIVATE);//

        initView();
        initData();

        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }



    //获取企业认证的信息
    private void initData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/company");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));

        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                try {
                    qiYeRenZhengBean = GsonUtils.getInstance().fromJson((String) response.get(), QiYeRenZhengBean.class);
                    et_bianHao.setText(qiYeRenZhengBean.getData().getFood_license_number());
                    et_youXiaoQi.setText(qiYeRenZhengBean.getData().getFood_license_period());
                    et_qiTaBianHao.setText(qiYeRenZhengBean.getData().getCompany_license_number());
                    et_qiTaYouXiaoQi.setText(qiYeRenZhengBean.getData().getCompany_license_period());
                    company_license = qiYeRenZhengBean.getData().getCompany_license();
                    names = "";
                    et_name.setText(qiYeRenZhengBean.getData().getCompany_name());
                    et_zhuTiYeTai.setText(qiYeRenZhengBean.getData().getClass_names());
                    String diqu = "";
                    shen = qiYeRenZhengBean.getData().getProvince_id();
                    et_jiankang.setText(qiYeRenZhengBean.getData().getCompany_healthy_number());
                    et_jiankang2.setText(qiYeRenZhengBean.getData().getCompany_healthy_period());


                    shi = qiYeRenZhengBean.getData().getCity_id();
                    qu = qiYeRenZhengBean.getData().getArea_id();
                    diqu = shen + shi + qu;
                    et_diQu.setText(diqu);



                    et_xiangXiDiZhi.setText(qiYeRenZhengBean.getData().getCompany_areainfo());
                    et_person.setText(qiYeRenZhengBean.getData().getCompany_linkman());
                    et_number.setText(qiYeRenZhengBean.getData().getCompany_mobile() + "");
                    et_qiYeDianHua.setText(qiYeRenZhengBean.getData().getCompany_phone());
                    sign.add(qiYeRenZhengBean.getData().getSign());

                    mentouzha = "";
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    company_license = new ArrayList<String>();
                }

                for (int i = 0; i < sign.size(); i++) {
                    mentouzha = sign.get(i).replace("http://oqv8tlktu.bkt.clouddn.com/", "");
                }

                for (int i = 0; i < company_license.size(); i++) {
                    names = names + "," + company_license.get(i).replace("http://oqv8tlktu.bkt.clouddn.com/", "");
                }

                // 创建一个线性布局管理器
                LinearLayoutManager layoutManager = new LinearLayoutManager(QiYeRenZhengActivity.this);
                LinearLayoutManager layoutManager11 = new LinearLayoutManager(QiYeRenZhengActivity.this);
                rc.setLayoutManager(layoutManager);//证件照布局管理器
                rc_zhengJian.setLayoutManager(layoutManager11);//门头照布局管理器
                layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                layoutManager11.setOrientation(OrientationHelper.HORIZONTAL);

                //证件上传适配器
                QiYeRCAdapter qiYeRCAdapter = new QiYeRCAdapter(QiYeRenZhengActivity.this, company_license);

                //门头照适配器
                QiYeRCAdapter_ddNew qiYeRCAdapter_ddNew = new QiYeRCAdapter_ddNew(QiYeRenZhengActivity.this, company_license);

                //点击门头照，显示拍照选择器
                qiYeRCAdapter_ddNew.setOnItemClickListener(new QiYeRCAdapter_ddNew.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == company_license.size()) {

                            pNum = 2;//？？？？


                            View contentView = View.inflate(QiYeRenZhengActivity.this, R.layout.popu_pic, null);
                            popupWindow = new PopupWindow(QiYeRenZhengActivity.this);
                            final LinearLayout ll = (LinearLayout) contentView.findViewById(R.id.alpha);
                            popupWindow.setContentView(contentView);
                            // 实例化一个ColorDrawable颜色为半透明
                            ColorDrawable dw = new ColorDrawable(0xb0000000);
                            // 设置弹出窗体的背景
                            popupWindow.setBackgroundDrawable(dw);
                            WindowManager.LayoutParams attributes = getWindow().getAttributes();
                            //当弹出Popupwindow时，背景变半透明
                            attributes.alpha = 0.7f;
                            getWindow().setAttributes(attributes);
                            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                            popupWindow.setFocusable(true);
                            popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 100);
                            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    WindowManager.LayoutParams params = getWindow().getAttributes();
                                    params.alpha = 1f;
                                    getWindow().setAttributes(params);
                                }
                            });

                        }
                    }
                });



                //点击证件上传，显示拍照选择器
                qiYeRCAdapter.setOnItemClickListener(new QiYeRCAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == company_license.size()) {
                            pNum = 2;
                            View contentView = View.inflate(QiYeRenZhengActivity.this, R.layout.popu_pic, null);
                            popupWindow = new PopupWindow(QiYeRenZhengActivity.this);
                            final LinearLayout ll = (LinearLayout) contentView.findViewById(R.id.alpha);
                            popupWindow.setContentView(contentView);
                            ColorDrawable dw = new ColorDrawable(0xb0000000);
                            popupWindow.setBackgroundDrawable(dw);
                            WindowManager.LayoutParams attributes = getWindow().getAttributes();
                            attributes.alpha = 0.7f;
                            getWindow().setAttributes(attributes);
                            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                            popupWindow.setFocusable(true);
                            popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 100);
                            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    WindowManager.LayoutParams params = getWindow().getAttributes();
                                    params.alpha = 1f;
                                    getWindow().setAttributes(params);
                                }
                            });

                        }
                    }
                });

                //证件上传设置适配器
                rc.setAdapter(qiYeRCAdapter);

                //证件照片
                QiYeRCAdapter_ddNew qiYeRCAdapter11 = new QiYeRCAdapter_ddNew(QiYeRenZhengActivity.this, sign);
                qiYeRCAdapter11.setOnItemClickListener(new QiYeRCAdapter_ddNew.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == sign.size()) {
                            pNum = 1;
                            View contentView = View.inflate(QiYeRenZhengActivity.this, R.layout.popu_pic, null);
                            popupWindow = new PopupWindow(QiYeRenZhengActivity.this);
                            final LinearLayout ll = (LinearLayout) contentView.findViewById(R.id.alpha);
                            popupWindow.setContentView(contentView);
                            ColorDrawable dw = new ColorDrawable(0xb0000000);
                            popupWindow.setBackgroundDrawable(dw);
                            WindowManager.LayoutParams attributes = getWindow().getAttributes();
                            attributes.alpha = 0.7f;
                            getWindow().setAttributes(attributes);
                            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                            popupWindow.setFocusable(true);
                            popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 100);
                            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    WindowManager.LayoutParams params = getWindow().getAttributes();
                                    params.alpha = 1f;
                                    getWindow().setAttributes(params);
                                }
                            });

                        }
                    }
                });
                rc_zhengJian.setAdapter(qiYeRCAdapter11);

            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    //弹出照相机
    public void MyonClick(View view) {
        customHelper.MyonClick(view, getTakePhoto());
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    //获取照片进行上传，拍完照上传，压缩照片
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        images = result.getImages();

        if (images != null && images.size() > 0) {
            rcImages.clear();
            for (int i = 0; i < images.size(); i++) {
                rcImages.add(images.get(i).getCompressPath());
            }
            if (pNum == 1) {



                adapter_ddNew = new QiYeRCAdapter_ddNew(QiYeRenZhengActivity.this, rcImages);
                rc_zhengJian.setAdapter(adapter_ddNew);
            } else if (pNum == 2) {
                qiYeRCAdapter = new QiYeRCAdapter(QiYeRenZhengActivity.this, rcImages);
                rc.setAdapter(qiYeRCAdapter);
            }
        }





        if (images != null && images.size() > 0) {
            final File file = new File(images.get(0).getCompressPath());//文件路径




            for (int i = 0; i < images.size(); i++) {
                images.get(i).getCompressPath();
            }




            // 1 构造上传策略
            String AccessKey = "khXjFIX-99Lv9FGACADrkyb3PMEf__uOHWmzuKgE";
            String SecretKey = "pr6g5YSAIn-SW-_VdCORxLJGkhn6IFL4FyD-hdyu";

            JSONObject _json = new JSONObject();

            String _encodedPutPolicy = null;

            byte[] _sign = new byte[0];

            try {
                long _dataline = System.currentTimeMillis() / 1000 + 3600;
                _json.put("deadline", _dataline);// 有效时间为一个小时
                _json.put("scope", "youlaiwang");
                _encodedPutPolicy = UrlSafeBase64.encodeToString(_json.toString().getBytes());
                _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);

            } catch (Exception e) {
                e.printStackTrace();
            }

            String _encodedSign = UrlSafeBase64.encodeToString(_sign);
            String _uploadToken = AccessKey + ':' + _encodedSign + ':' + _encodedPutPolicy;

            //七牛云
            UploadManager uploadManager = new UploadManager();
            uploadManager.put(file, null, _uploadToken, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, final JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            if (info.isOK()) {

                                try {
                                    String code = res.getString("key");
                                    if (pNum == 1) {
                                        sign.add(code);
                                        mentouzha = "";

                                        for (int i = 0; i < sign.size(); i++) {
                                            mentouzha = sign.get(i).replace("http://oqv8tlktu.bkt.clouddn.com/", "");
                                        }
                                    } else if (pNum == 2) {
                                        company_license.add(code);
                                        names = "";
                                        for (int i = 0; i < company_license.size(); i++) {
                                            names = names + "," + company_license.get(i).replace("http://oqv8tlktu.bkt.clouddn.com/", "");
                                        }
                                    }
                                    popupWindow.dismiss();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                ShowToast.showToast("上传失败");
                            }
                        }

                    }, null);
        }
    }

    private void SentData() {

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/company/update", RequestMethod.POST);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("food_license_number", et_bianHao.getText().toString() + "");//食品经营许可证编号
        stringRequest.add("food_license_period", et_youXiaoQi.getText().toString() + "");//食品经营许可证有效期
        stringRequest.add("company_license_number", et_qiTaBianHao.getText().toString() + "");// 企业营业执照号 *
        stringRequest.add("company_license_period", et_qiTaYouXiaoQi.getText().toString() + "");//企业营业执照有效期 *
        stringRequest.add("company_healthy_number", et_jiankang.getText().toString() + "");// 健康证编号 *
        stringRequest.add("company_healthy_period", et_jiankang2.getText().toString() + "");//健康证有效期 *
        stringRequest.add("company_license", names + "");//企业资质照片*
        stringRequest.add("company_name", et_name.getText().toString() + "");//企业名称*
        stringRequest.add("province_id", idSp.getString("shenId","") + "");//省*
        stringRequest.add("city_id", idSp.getString("shiId","") + "");//市*
        stringRequest.add("area_id", idSp.getString("quId","") + "");//区*
        stringRequest.add("company_areainfo", et_xiangXiDiZhi.getText().toString() + "");//详细地址*
        stringRequest.add("company_linkman", et_person.getText().toString() + "");//企业联系人
        stringRequest.add("company_mobile", et_number.getText().toString() + "");// 联系人电话
        stringRequest.add("company_phone", et_qiYeDianHua.getText().toString() + "");// 企业电话
        stringRequest.add("sign", mentouzha + "");// 门头照
//        stringRequest.add("class_names",et_zhuTiYeTai.getText().toString() + "");//主体业态
        stringRequest.add("_method", "patch");

        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject((String) response.get());
                    String msg = jsonObject.getString("msg");
                    String code = jsonObject.getString("code");
                    ShowToast.showToast(msg);
                    if ("200".equals(code)) {


                        //弹出框以及对应的控件功能
                        View view = LayoutInflater.from(QiYeRenZhengActivity.this).inflate(R.layout.dialog_company, null);
                        final Dialog dialog = new Dialog(QiYeRenZhengActivity.this, R.style.dialog);
                        dialog.setContentView(view);
                        dialog.setCancelable(false);
                        dialog.show();

                        ImageView img2 = (ImageView) view.findViewById(R.id.img_big2);
                        ImageView img3 = (ImageView) view.findViewById(R.id.img_big3);

                        img2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(QiYeRenZhengActivity.this,MainActivity.class));
                                dialog.cancel();
                                finish();
                            }
                        });

                        img3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                finish();
                            }
                        });



                    }

                    ShowToast.showToast(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }



    /**
     * 这个签名方法找了半天 一个个对出来的、、、、程序猿辛苦啊、、、 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * 接收这份代码我就更苦逼了....
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }





    //城市选择器
    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                x = options1;
                y = options2;
                z = options3;
                shenId = jsonBean.get(x).getK();
                shiId = jsonBean.get(x).getN().get(y).getK();
                quId = jsonBean.get(x).getN().get(y).getN().get(z).getK();

                idSp.edit().putString("shenId",shenId).putString("shiId",shiId).putString("quId",quId).commit();

                if (options1Items.get(options1).getV().equals(options2Items.get(options1).get(options2)

                )) {
                    tx = options2Items.get(options1).get(options2) + options3Items.get(options1).get(options2).get(options3);
                } else {
                    tx = options1Items.get(options1).getV() + options2Items.get(options1).get(options2) + options3Items.get(options1).get(options2).get(options3);
                }

                et_diQu.setText(tx);

            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(datas, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private boolean isLoaded = false;
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private ArrayList<MyCityBean> options1Items = new ArrayList<>();
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(QiYeRenZhengActivity.this, "省市区数据损坏，请重新下载应用", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        JsonData = new GetJsonDataUtil().getJson(this, "city.json");//获取assets目录下的json文件数据
        jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        datas = new ArrayList<>();
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {
            datas.add(jsonBean.get(i).getV());

        }

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getN().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getN().get(c).getV().toString();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getN().get(c).getN() == null || jsonBean.get(i).getN().get(c).getN().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getN().get(c).getN().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getN().get(c).getN().get(d).getV().toString();
                        City_AreaList.add(AreaName.toString());//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
    public ArrayList<MyCityBean> parseData(String result) {//Gson 解析
        ArrayList<MyCityBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                MyCityBean entity = gson.fromJson(data.optJSONObject(i).toString(), MyCityBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    /**
     * 不用多看
     */
    private void initView() {

        et_jiankang2= (TextView) findViewById(R.id.et_jiankang2);
        et_jiankang= (EditText) findViewById(R.id.et_jiankang);

        //健康证日期
        et_jiankang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(QiYeRenZhengActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = year + "-" + (month + 1) + "-" + dayOfMonth;
                        et_jiankang2.setText(text);
                    }
                }, year, month, date);
                datePickerDialog2.show();//展示
            }
        });


        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setOnClickListener(this);
        et_person = (EditText) findViewById(R.id.et_person);
        et_person.setOnClickListener(this);
        et_number = (EditText) findViewById(R.id.et_number);
        et_number.setOnClickListener(this);
        et_qiYeDianHua = (EditText) findViewById(R.id.et_qiYeDianHua);
        et_qiYeDianHua.setOnClickListener(this);

        et_bianHao = (EditText) findViewById(R.id.et_bianHao);
        et_youXiaoQi = (TextView) findViewById(R.id.et_youXiaoQi);
        et_youXiaoQi.setOnClickListener(this);
        et_qiTaBianHao = (EditText) findViewById(R.id.et_qiTaBianHao);
        et_qiTaYouXiaoQi = (TextView) findViewById(R.id.et_qiTaYouXiaoQi);
        et_qiTaYouXiaoQi.setOnClickListener(this);
        et_zhuTiYeTai = (EditText) findViewById(R.id.et_zhuTiYeTai);
        et_diQu = (TextView) findViewById(R.id.et_diQu);
        et_xiangXiDiZhi = (EditText) findViewById(R.id.et_xiangXiDiZhi);
        et_diQu.setOnClickListener(this);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        rc = (RecyclerView) findViewById(R.id.rc);
        rc_zhengJian = (RecyclerView) findViewById(R.id.rc_zhengJian);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1://更新
                if (submit()) {
                    SentData();
                } else {
                    ShowToast.showToast("请资料填写完整");
                }

                break;
            case R.id.et_youXiaoQi:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = year + "-" + (month + 1) + "-" + dayOfMonth;
                        et_youXiaoQi.setText(text);
                    }
                }, year, month, date);
                datePickerDialog.show();//展示
                break;


            case R.id.et_qiTaYouXiaoQi:
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = year + "-" + (month + 1) + "-" + dayOfMonth;
                        et_qiTaYouXiaoQi.setText(text);
                    }
                }, year, month, date);
                datePickerDialog1.show();//展示
                break;
            case R.id.et_diQu:

                if (isLoaded) {
                    ShowPickerView();
                } else {
                    Toast.makeText(QiYeRenZhengActivity.this, "数据暂未解析成功，请等待", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.et_jiankang2:

                DatePickerDialog datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = year + "-" + (month + 1) + "-" + dayOfMonth;
                        et_jiankang2.setText(text);
                    }
                }, year, month, date);
                datePickerDialog2.show();//展示

                break;
        }
    }

    private boolean submit() {
        // validate
        String qiTaBianHao = et_qiTaBianHao.getText().toString().trim();
        if (TextUtils.isEmpty(qiTaBianHao)) {
            Toast.makeText(this, "企业营业执照号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        String qiTaYouXiaoQi = et_qiTaYouXiaoQi.getText().toString().trim();
        if (TextUtils.isEmpty(qiTaYouXiaoQi)) {
            Toast.makeText(this, "企业营业执照有效期不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }


        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "企业名称不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        String jiankang = et_jiankang.getText().toString().trim();
        if (TextUtils.isEmpty(jiankang)) {
            Toast.makeText(this, "健康证编号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        String jiankang2 = et_jiankang2.getText().toString().trim();
        if (TextUtils.isEmpty(jiankang2)) {
            Toast.makeText(this, "健康证有效期不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        String diQu = et_diQu.getText().toString().trim();
        if (TextUtils.isEmpty(diQu)) {
            Toast.makeText(this, "所在地区不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        String xiangXiDiZhi = et_xiangXiDiZhi.getText().toString().trim();
        if (TextUtils.isEmpty(xiangXiDiZhi)) {
            Toast.makeText(this, "企业详细地址不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (names.length() < 1) {
            Toast.makeText(this, "企业资质不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }




        return true;

        // TODO validate success, do something

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
