package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.adapter.QiYeRCAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.CustomHelper;
import com.bm.chengshiyoutian.youlaiwang.bean.QiYeRenZhengBean;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.jph.takephoto.app.TakePhoto;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast.showToast;


/**
 * Created by sld on 2017/5/11.
 */

public class QiYeRenZhengActivitynew extends TakePhotoActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;
    private EditText et_name;
    private EditText et_person;
    private EditText et_number;
    private EditText et_fanwei;

    private Button btn1;
    SharedPreferences sp;
    private RecyclerView rc;

    TakePhoto takePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_qiyerenzheng, null);
        setContentView(view);
        takePhoto = getTakePhoto();

        sp = getSharedPreferences(MyRes.CONFIG, 0);
        initView();
        initData();


        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(118,201,51), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    ArrayList<TImage> images;

    private CustomHelper customHelper;
    QiYeRenZhengBean qiYeRenZhengBean;
    PopupWindow popupWindow;

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
                    et_person.setText(qiYeRenZhengBean.getData().getCompany_linkman());
                    et_fanwei.setText(qiYeRenZhengBean.getData().getClass_names());
                    et_name.setText(qiYeRenZhengBean.getData().getCompany_name());
                    et_number.setText(qiYeRenZhengBean.getData().getCompany_mobile() + "");
                    rcImages = (ArrayList<String>) qiYeRenZhengBean.getData().getCompany_license();

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    rcImages = new ArrayList<String>();
                }


                // 创建一个线性布局管理器
                LinearLayoutManager layoutManager = new LinearLayoutManager(QiYeRenZhengActivitynew.this);
                // 设置布局管理器
                rc.setLayoutManager(layoutManager);
                layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                //设置Adapter

                QiYeRCAdapter qiYeRCAdapter = new QiYeRCAdapter(QiYeRenZhengActivitynew.this, rcImages);
                qiYeRCAdapter.setOnItemClickListener(new QiYeRCAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == rcImages.size()) {

                            View contentView = View.inflate(QiYeRenZhengActivitynew.this
                                    , R.layout.popu_pic, null);
                            popupWindow = new PopupWindow(QiYeRenZhengActivitynew.this);

                            final LinearLayout ll = (LinearLayout) contentView.findViewById(R.id.alpha);

                            popupWindow.setContentView(contentView);
                            // 实例化一个ColorDrawable颜色为半透明
                            ColorDrawable dw = new ColorDrawable(0xb0000000);
                            // 设置弹出窗体的背景
                            popupWindow.setBackgroundDrawable(dw);
                            WindowManager.LayoutParams attributes = getWindow().getAttributes();
                            //当弹出Popupwindow时，背景变半透明
                            attributes.alpha = 0.7f;
                            //      ll.getBackground().setAlpha((int) 0.7f);
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
                                    // ll.getBackground().setAlpha((int) 1f);
                                }
                            });

                        }
                    }
                });
                rc.setAdapter(qiYeRCAdapter);


            }

            @Override
            public void onFailed(int what, Response response) {
                showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }



    QiYeRCAdapter qiYeRCAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setOnClickListener(this);
        et_person = (EditText) findViewById(R.id.et_person);
        et_person.setOnClickListener(this);
        et_number = (EditText) findViewById(R.id.et_number);
        et_number.setOnClickListener(this);
//        et_fanwei = (EditText) findViewById(R.id.et_fanwei);
//        et_fanwei.setOnClickListener(this);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        rc = (RecyclerView) findViewById(R.id.rc);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1://更新
                if (submit()) {
                    SentData();
                } else {
                    showToast("请资料填写完整");

                }

                break;

        }
    }

    private boolean submit() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "企业名称", Toast.LENGTH_SHORT).show();
            return false;
        }

        String person = et_person.getText().toString().trim();
        if (TextUtils.isEmpty(person)) {
            Toast.makeText(this, "联系人", Toast.LENGTH_SHORT).show();
            return false;
        }

        String number = et_number.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "联系电话", Toast.LENGTH_SHORT).show();
            return false;
        }

        String fanwei = et_fanwei.getText().toString().trim();
        if (TextUtils.isEmpty(fanwei)) {
            Toast.makeText(this, "经营范围", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;



    }

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
    ArrayList<String> rcImages = new ArrayList<>();
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        images = result.getImages();
        if (images != null && images.size() > 0) {
            for (int i = 0; i < images.size(); i++) {
                rcImages.add(images.get(i).getCompressPath());
            }
            qiYeRCAdapter = new QiYeRCAdapter(QiYeRenZhengActivitynew.this, rcImages);
            rc.setAdapter(qiYeRCAdapter);
        }

        if (images != null && images.size() > 0) {

            final File file = new File(images.get(0).getCompressPath());

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
                _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                        .toString().getBytes());
                _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String _encodedSign = UrlSafeBase64.encodeToString(_sign);
            String _uploadToken = AccessKey + ':' + _encodedSign + ':'
                    + _encodedPutPolicy;
            UploadManager uploadManager = new UploadManager();
            Log.d("_uploadToken", _uploadToken);
            //上传七牛的接口
            uploadManager.put(file, null, _uploadToken,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            if (info.isOK()) {
                                try {
                                    String code = res.getString("key");
                                    //把图片的名字发给服务器

                                    Request<String> request = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/company/update", RequestMethod.POST);
                                    request.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                                    request.add("company_name", qiYeRenZhengBean.getData().getCompany_name());
                                    request.add("company_linkman", qiYeRenZhengBean.getData().getCompany_linkman());
                                    request.add("company_mobile", qiYeRenZhengBean.getData().getCompany_mobile());
                                    request.add("class_names", qiYeRenZhengBean.getData().getClass_names());
                                    request.add("company_license", code + "");
                                    request.add("_method", "patch");

                                    CallServer.getInstance().add(123, request, new OnResponseListener() {
                                        @Override
                                        public void onStart(int what) {
                                        }

                                        @Override
                                        public void onSucceed(int what, Response response) {
                                            String result = response.toString();
                                            try {
                                                JSONObject jsonObject = new JSONObject((String) response.get());
                                                String msg = jsonObject.getString("msg");
                                                showToast(msg);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailed(int what, Response response) {
                                            showToast("联网失败");
                                        }

                                        @Override
                                        public void onFinish(int what) {
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // Log.("qiniu", "Upload Fail");
                                showToast("上传失败");
                            }
                        }
                    }, null);


        }


    }

    private void SentData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/company/update", RequestMethod.POST);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("company_name", et_name.getText().toString() + "");
        stringRequest.add("company_linkman", et_person.getText().toString() + "");
        stringRequest.add("company_mobile", et_number.getText().toString() + "");
        stringRequest.add("class_names", et_fanwei.getText().toString() + "");
        //  stringRequest.add("company_license", names + "");
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
                    showToast(msg);
                    if ("200".equals(code)) {

                        finish();
                    }

                    showToast(msg);
                    //  getData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    /**
     * 这个签名方法找了半天 一个个对出来的、、、、程序猿辛苦啊、、、 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
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
}
