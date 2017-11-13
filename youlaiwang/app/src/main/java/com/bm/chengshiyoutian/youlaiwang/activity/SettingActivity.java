package com.bm.chengshiyoutian.youlaiwang.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.FileCacheUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.GlideCircleTransform;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.CustomHelper;
import com.bm.chengshiyoutian.youlaiwang.bean.UpdateBean;
import com.bm.chengshiyoutian.youlaiwang.bean.WoDeBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.packageutils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.UpDataDialogDialog;
import com.bumptech.glide.Glide;
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

import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;


/**
 * Created by sld on 2017/4/26.
 */

public class SettingActivity extends TakePhotoActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;
    private ImageView iv;
    private LinearLayout ll1;
    private TextView tv1;
    private LinearLayout ll2;
    private TextView tv2;
    private LinearLayout ll4;
    private TextView tv3;
    private LinearLayout ll5, ll_pic;
    boolean tag = true;
    private ArrayList<TImage> images;
    private CustomHelper customHelper;
    private LinearLayout ll_qiye;
    private LinearLayout ll_jubao;
    private LinearLayout ll_banben;
    private LinearLayout ll_huancun;
    private TextView tv_tuichu;
    private TextView mVersion_name;
    private UpDataDialogDialog mUpDataDialogDialog;
    private TextView mViewById;
    private TextView mSetting_update;
    private String url = "";
    private SharedPreferences sp;
    private String token;

    public void MyonClick(View view) {
        customHelper.MyonClick(view, getTakePhoto());
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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(this, R.layout.activity_setting, null);
        setContentView(view);
        customHelper = CustomHelper.of(view);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        head = sp.getString(MyRes.MY_TOKEN, "");
        token = sp.getString(MyRes.TOKEN, "");
        initView();
        StatusBarUtil.setColorForSwipeBack(this, Color.rgb(255,168,0), 0);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        checkForUpdata1();


    }

    String head;

    private void getData() {
        
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/index");
        stringRequest.addHeader("Authorization", head);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                WoDeBean woDeBean = GsonUtils.getInstance().fromJson((String) response.get(), WoDeBean.class);


                try {
                    Glide.with(SettingActivity.this)
                            .load(woDeBean.getData().getAvatar())
                            .transform(new GlideCircleTransform(SettingActivity.this))
                            .into(iv);
                    tv1.setText(woDeBean.getData().getUser_nicename());
                } catch (Exception e) {
                    ShowToast.showToast("网络错误");
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

    private void initView() {

        mSetting_update = (TextView) findViewById(R.id.setting_update);
        mSetting_update.setOnClickListener(this);
        mSetting_update.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

        mUpDataDialogDialog = new UpDataDialogDialog(this);
        mVersion_name = (TextView) findViewById(R.id.version_name);
        mVersion_name.setText(""+ packageutils.getVersionName(this));
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll1.setOnClickListener(this);
        tv1 = (TextView) findViewById(R.id.tv1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll2.setOnClickListener(this);
        tv2 = (TextView) findViewById(R.id.tv2);

        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll4.setOnClickListener(this);
        tv3 = (TextView) findViewById(R.id.tv3);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll5.setOnClickListener(this);
        ll_pic = (LinearLayout) findViewById(R.id.ll_pic);
        ll_qiye = (LinearLayout) findViewById(R.id.ll_qiye);
        ll_qiye.setOnClickListener(this);
        ll_jubao = (LinearLayout) findViewById(R.id.ll_jubao);
        ll_jubao.setOnClickListener(this);
        ll_banben = (LinearLayout) findViewById(R.id.ll_banben);
        ll_banben.setOnClickListener(this);
        ll_huancun = (LinearLayout) findViewById(R.id.ll_huancun);
        ll_huancun.setOnClickListener(this);


        tv_tuichu = (TextView) findViewById(R.id.tv_tuichu);
        tv_tuichu.setOnClickListener(this);


        mViewById = (TextView) findViewById(R.id.cachesize);


        File externalCacheDir = getExternalCacheDir();
        try {
            String cacheSize = FileCacheUtils.getCacheSize(externalCacheDir);
            mViewById.setText(cacheSize);
        } catch (Exception e) {
            Log.e("ttt", "获取缓存大小失败");
        }
    }

    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll1:

                View contentView = View.inflate(this
                        , R.layout.popu_pic, null);
                PopupWindow popupWindow = new PopupWindow(this);

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
                    }
                });

                break;
            case R.id.ll2://修改昵称
                intent = new Intent(this, ChangeUserNameActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
                Log.d("sss", "2");

                break;

            case R.id.ll4://密码修改

                intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_tuichu://退出登录
                SharedPreferences idSp = getSharedPreferences("cityId",MODE_PRIVATE);
                idSp.edit().clear().commit();
                sp.edit().putBoolean(MyRes.tag, false).commit();
                sp.edit().putString(MyRes.MY_TOKEN, "kong").commit();
                sp.edit().putString(MyRes.TOKEN, "kong").commit();

                this.setResult(201);

                finish();

                //   intent = new Intent(this, ChangePasswordActivity.class);
                // startActivity(intent);
                break;
            case R.id.ll5://收货地址
                intent = new Intent(this, MyAddressActivity.class);

                intent.putExtra("token", token);
                startActivity(intent);
                Log.d("sss", "5");


                break;
            case R.id.ll_qiye://企业认证

                intent = new Intent(this, QiYeRenZhengActivity.class);

                intent.putExtra("token", token);
                startActivity(intent);
                break;
            case R.id.ll_jubao:
                token = getIntent().getStringExtra("token");

                intent = new Intent(this, JuBaoJiLuActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
                break;
//            case R.id.ll_banben:
//                checkForUpdata();
//                break;
            case R.id.setting_update:
                downloadApk(MyRes.BASE_URL1 + url);
                mSetting_update.setVisibility(View.GONE);
                break;
            case R.id.ll_huancun://清理缓存
                FileCacheUtils.cleanApplicationData(this,getExternalCacheDir().getPath());
                try {
                    Toast.makeText(this, "已经清理"+FileCacheUtils.getCacheSize(getExternalCacheDir()), Toast.LENGTH_SHORT).show();
                    File externalCacheDir = getExternalCacheDir();
                        String cacheSize = FileCacheUtils.getCacheSize(externalCacheDir);
                        mViewById.setText("0B");
                        Log.e("ttt", "获取缓存大小失败");
                } catch (Exception e) {
                    Log.e("ttt", "缓存获取失败");
                }
                break;
        }
    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        images = result.getImages();

        if (images != null && images.size() > 0) {
            final File file = new File(images.get(0).getCompressPath());

            Glide.with(this).load(file)

                    .transform(new GlideCircleTransform(SettingActivity.this))

                    .into(iv);
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
//把图片的名字发给服务器

                                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/update/avatar", RequestMethod.POST);
                                    stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
                                    stringRequest.add("_method", "patch");
                                    stringRequest.add("avatar", code + "");
                                    CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
                                        @Override
                                        public void onStart(int what) {

                                        }

                                        @Override
                                        public void onSucceed(int what, Response response) {
                                            Log.d("ddd", (String) response.get());
                                            try {
                                                JSONObject jsonObject = new JSONObject((String) response.get());
                                                String msg = jsonObject.getString("msg");
                                                ShowToast.showToast(msg);
                                                getData();
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

    @Override
    protected void onStart() {
        super.onStart();
        ll_pic.setVisibility(View.GONE);
        getData();
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


    private void checkForUpdata() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/version", RequestMethod.GET);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
            }
            @Override
            public void onSucceed(int what, Response response) {
                Log.e("tag", (String) response.get());
                String result = response.get().toString();
                if (response.get() != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        if(object.getInt("code") == 200){
                            JSONObject object1 = object.getJSONObject("data");
                            JSONObject android = object1.getJSONObject("dataAndroid");
                            String url = android.getString("av_url");
                            double v1 = Double.parseDouble(android.getString("av_version"));
                            double v2 = Double.parseDouble(packageutils.getVersionName(SettingActivity.this));
                            if (v1 <= v2) {
                                Toast.makeText(SettingActivity.this,"当前已是最新版本",Toast.LENGTH_SHORT).show();
                            } else {
                                downloadApk(MyRes.BASE_URL1 + url);
//                                    initDialog(updateBean.data.dataAndroid);
                            }
                        }
//                        UpdateBean updateBean = Gsonutils.getInstance().fromJson((String) response.get(), UpdateBean.class);
//                        if (updateBean != null) {
//                            if (updateBean.code == 200) {
//                                double v1 = Double.parseDouble(updateBean.data.dataAndroid.av_version);
//                                double v2 = Double.parseDouble(packageutils.getVersionName(SettingActivity.this));
//                                Log.e("tag", v1 + "..." + v2);
//                                if (v1 <= v2) {
//                                    Toast.makeText(SettingActivity.this,"当前已是最新版本",Toast.LENGTH_SHORT).show();
//                                } else {
//                                    downloadApk(MyRes.BASE_URL1 + updateBean.data.dataAndroid.av_url);
////                                    initDialog(updateBean.data.dataAndroid);
//                                }
//                            } else {
//                                // ShowToast.showToast(updateBean.msg);
//                            }
//                        }
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    private void checkForUpdata1() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/version", RequestMethod.GET);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                Log.e("tag", (String) response.get());
                String result = response.get().toString();
                if (response.get() != null) {
                    try {
//                        UpdateBean updateBean = Gsonutils.getInstance().fromJson((String) response.get(), UpdateBean.class);
//                        if (updateBean != null) {
//                            if (updateBean.code == 200) {
//                                double v1 = Double.parseDouble(updateBean.data.dataAndroid.av_version);
//                                double v2 = Double.parseDouble(packageutils.getVersionName(SettingActivity.this));
//                                if (v1 <= v2) {
//                                    mSetting_update.setVisibility(View.INVISIBLE);
//                                } else {
//                                    mSetting_update.setVisibility(View.VISIBLE);
//                                  //  initDialog(updateBean.data.dataAndroid);
//                                }
//                            } else {
//                                // ShowToast.showToast(updateBean.msg);
//                            }
//                        }

                        JSONObject object = new JSONObject(result);
                        if(object.getInt("code") == 200){
                            JSONObject object1 = object.getJSONObject("data");
                            JSONObject android = object1.getJSONObject("dataAndroid");
                            url = android.getString("av_url");
                            double v1 = Double.parseDouble(android.getString("av_version"));
                            double v2 = Double.parseDouble(packageutils.getVersionName(SettingActivity.this));
                            Log.e("设置界面版本号",v1 + "...." +v2);
                            if (v1 <= v2) {

                            } else {
                                mSetting_update.setVisibility(View.VISIBLE);

//                                    initDialog(updateBean.data.dataAndroid);
                            }
                        }
                    } catch (Exception e) {

                    }

                }


            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void initDialog(final UpdateBean.DataBean.DataAndroidBean dataAndroid) {
        mUpDataDialogDialog.show();
        mUpDataDialogDialog.setMessage(dataAndroid.getAv_content());
        mUpDataDialogDialog.setOnCanceClicklListener(new UpDataDialogDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpDataDialogDialog.dismiss();
            }
        });

        mUpDataDialogDialog.setOnClickListener(new UpDataDialogDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadApk(MyRes.BASE_URL1+dataAndroid.getAv_url());
            }
        });
    }



    private CompleteReceiver completeReceiver;
    private long downloadId;
    private DownloadManager downloadManager;
    /**
     * 下载更新包
     *
     * @param url 下载地址
     */
    private void downloadApk(String url) {
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        String dir = isFolderExist("youlaiyouwang");
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        completeReceiver = new CompleteReceiver();
        File f = new File(dir, "update.apk");
//        File f = new File(dir, "update.jpg");
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


    private class CompleteReceiver extends BroadcastReceiver {
        public String save_path = "";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == downloadId ){

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                Cursor cursor = downloadManager.query(query);
                String statusMsg = "";
                if (cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        case DownloadManager.STATUS_PAUSED:
                            statusMsg = "STATUS_PAUSED";
                        case DownloadManager.STATUS_PENDING:
                            statusMsg = "STATUS_PENDING";
                        case DownloadManager.STATUS_RUNNING:
                            statusMsg = "STATUS_RUNNING";
                            break;
                        case DownloadManager.STATUS_SUCCESSFUL:
                            downComplete(save_path);
                            break;
                        case DownloadManager.STATUS_FAILED:
                            statusMsg = "STATUS_FAILED";
                            break;
                        default:
                            statusMsg = "未知状态";
                            break;
                    }
//                    Toast.makeText(getApplicationContext(), statusMsg, Toast.LENGTH_SHORT).show();
                }
            }


        }

        private void downComplete(String filePath) {
            Log.e("filePath",filePath);
            if(filePath.endsWith(".apk")){
                File _file = new File(filePath);
                openApk(SettingActivity.this, _file);
            }

        }
    }


    public void openApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    // 根据DownloadManager下载的Id，查询DownloadManager某个Id的下载任务状态。
    private void queryStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);

        String statusMsg = "";
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    statusMsg = "STATUS_PAUSED";
                case DownloadManager.STATUS_PENDING:
                    statusMsg = "STATUS_PENDING";
                case DownloadManager.STATUS_RUNNING:
                    statusMsg = "STATUS_RUNNING";
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:

                    break;
                case DownloadManager.STATUS_FAILED:
                    statusMsg = "STATUS_FAILED";
                    break;

                default:
                    statusMsg = "未知状态";
                    break;
            }

            Toast.makeText(getApplicationContext(), statusMsg, Toast.LENGTH_SHORT).show();
        }
    }


}

