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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.PingJiaRCAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.CustomHelper;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



/**
 * Created by sld on 2017/6/14.
 */

public class PingJiaActivity extends TakePhotoActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;
    ArrayList<TImage> images;
    private CustomHelper customHelper;
    private RecyclerView rc;
    private ImageView iv_check;
    boolean tag = true;
    private EditText et;
    private Button bt;
    SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_pingjia, null);
        setContentView(view);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        customHelper = CustomHelper.of(view);
        initView();

        StatusBarUtil.setColorForSwipeBack(this, Color.parseColor("#ffa800"), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    public void MyonClick(View view) {
        customHelper.MyonClick(view, getTakePhoto());
    }

    ArrayList<String> company_license = new ArrayList<>();
    PingJiaRCAdapter qiYeRCAdapter;

    private void initView() {

        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        rc = (RecyclerView) findViewById(R.id.rc);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(PingJiaActivity.this);
        // 设置布局管理器
        rc.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置Adapter
        qiYeRCAdapter = new PingJiaRCAdapter(PingJiaActivity.this, company_license);
        qiYeRCAdapter.setOnItemClickListener(new PingJiaRCAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (position == company_license.size()) {


                    View contentView = View.inflate(PingJiaActivity.this
                            , R.layout.popu_pic, null);
                    PopupWindow popupWindow = new PopupWindow(PingJiaActivity.this);

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
        iv_check = (ImageView) findViewById(R.id.iv_check);
        iv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag) {
                    iv_check.setImageResource(R.drawable.xuanzhong_02);
                    tag = false;
                } else {
                    iv_check.setImageResource(R.drawable.xuanzhong_01);
                    tag = true;
                }
            }
        });
        et = (EditText) findViewById(R.id.et);

        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(et.getText().toString())) {
                    ShowToast.showToast("评价不能为空");
                } else {
                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/order/comments", RequestMethod.POST);
                    stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                    stringRequest.add("content", et.getText().toString());
                    if (tag) {
                        stringRequest.add("comment_is_name", 1);
                    } else {
                        stringRequest.add("comment_is_name", 2);
                    }

                    names = "";
                    for (int i = 0; i < company_license.size(); i++) {
                        names = names + "," + company_license.get(i).replace("http://oqv8tlktu.bkt.clouddn.com/", "");
                    }


                    stringRequest.add("images",names);
                    stringRequest.add("order_id", getIntent().getStringExtra("id"));

                    CallServer.getInstance().add(32, stringRequest, new OnResponseListener() {
                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject((String) response.get());
                                String code = jsonObject.getString("code");
                                String msg = jsonObject.getString("msg");
                                ShowToast.showToast(msg);
                                if ("200".equals(code)){

                                    finish();
                                }
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


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    String names = "";

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        images = result.getImages();

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
            uploadManager.put(file, null, _uploadToken,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            if (info.isOK()) {

                                try {
                                    String code = res.getString("key");
                                    company_license.add("http://oqv8tlktu.bkt.clouddn.com/" + code);
                                    Log.d("tag", company_license.size() + "");

                                    qiYeRCAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Log.i("qiniu", "Upload Fail");
                                ShowToast.showToast("上传失败");
                            }
                            Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        }

                    }, null);
        }
    }

    private void submit() {
        // validate
        String etString = et.getText().toString().trim();
        if (TextUtils.isEmpty(etString)) {
            Toast.makeText(this, "etString不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:

                break;
        }
    }
}
