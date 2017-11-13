package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.app.AppManager;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.TipDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


/**
 * 设置界面
 * Created by youj on 2016/1/9.
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ll_change_psw)
    LinearLayout llChangePsw;
    @Bind(R.id.ll_feedback)
    LinearLayout llFeedback;
    @Bind(R.id.ll_about)
    LinearLayout llAbout;
    @Bind(R.id.ll_software_update)
    LinearLayout llSoftwareUpdate;
    @Bind(R.id.ll_message)
    LinearLayout llMessage;
    @Bind(R.id.ll_help)
    LinearLayout llHelp;
    @Bind(R.id.bt_exit)
    Button btExit;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.v2)
    View v2;
    @Bind(R.id.tv_new_version)
    TextView tv_new_version;
    private TipDialog mTipDialog;
    private ProgressDialog progressDialog;
    private CompleteReceiver completeReceiver;
    private TipDialog tipDialog;
    private long downloadId;
    private ResponseEntity entityMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        init();
        checkUpdate();
    }

    private void init() {
        tipDialog = new TipDialog(this);
        progressDialog = new ProgressDialog(this);
        mTipDialog = new TipDialog(this);
        title.setText(getString(R.string.setting));
        ivLeft.setOnClickListener(this);
        llChangePsw.setOnClickListener(this);
        llAbout.setOnClickListener(this);
        llFeedback.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llMessage.setOnClickListener(this);
        llSoftwareUpdate.setOnClickListener(this);
        btExit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SPUtil.getBoolean(this, Constants.IS_LOGIN)) {
            llChangePsw.setVisibility(View.GONE);
//            llMessage.setVisibility(View.GONE);
            btExit.setText(getString(R.string.login));
            v1.setVisibility(View.GONE);
//            v2.setVisibility(View.GONE);
        } else {
            llChangePsw.setVisibility(View.VISIBLE);
//            llMessage.setVisibility(View.VISIBLE);
            btExit.setText(getString(R.string.logout));
            v1.setVisibility(View.VISIBLE);
//            v2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:  //后退
                //startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            case R.id.ll_about://关于我们
                startActivity(new Intent(this, AboutActivity.class));
                break;

            case R.id.ll_change_psw://修改密码
                startActivity(new Intent(this, ChangePswActivity.class));
                break;

            case R.id.ll_feedback://意见反馈
                startActivity(new Intent(this, FeedbackActivitiy.class));
                break;

            case R.id.ll_help://帮助
                Intent intent = new Intent(this, HelpNewActivity.class);
//                intent.putExtra(Constants.TYPE, Constants.HELP);
                startActivity(intent);
                break;

            case R.id.ll_message://消息
                startActivity(new Intent(this, MessageActivity.class));
                break;

            case R.id.ll_software_update://软件升级
                //checkUpdate();
                checkApkAction(entityMessage);
                break;

            case R.id.bt_exit://退出
                if (SPUtil.getBoolean(this, Constants.IS_LOGIN)) {
                    //如果是登录状态
                    logout();
                } else {
                    startActivity(new Intent(this, LoginActivtiy.class));
                }

                break;
            default:
                break;
        }
    }

    /**
     * 注销
     */
    private void logout() {
        tipDialog.show();
        tipDialog.setTitle("提醒");
        tipDialog.setMessage("确定要退出登录吗");
        tipDialog.showCancel();
        tipDialog.setOnClickListener(new TipDialog.OnClickListener() {
            @Override
            public void onClick(View v) {

                SPUtil.put(SettingActivity.this, Constants.IS_LOGIN, false);//登录状态改为未登录
                JPushInterface.setAlias(getApplicationContext(), "", null); //极光推送别名置空
                tipDialog.dismiss();
                startActivity(new Intent(SettingActivity.this, LoginActivtiy.class));
            }
        });
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("typeid", "1");
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.SETTING_URL, "GetNewVersion", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    entityMessage = entity;
                    if (jsonObject.optInt("status") == 0) {
                        JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").optJSONObject(0);
                        double banben = object.optDouble("Banben");
                        final String url = object.optString("FileUrl");
                        PackageManager manager = getPackageManager();
                        PackageInfo info = manager.getPackageInfo(getPackageName(), 0);

                        String versionName = info.versionName; //版本号
                        if (banben > Double.parseDouble(versionName)) {
                            //如果后台版本大于本地版本就提示更新
                            tv_new_version.setText("(发现新版本: " + banben + ")");
                        } else {
                            //tip("已是最新版本");
                            tv_new_version.setText("已是最新版本");
                        }
                    } else {
                        tv_new_version.setText("已是最新版本");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    tv_new_version.setText("已是最新版本");
                }
                break;
        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        tv_new_version.setText("已是最新版本");
    }

    private void checkApkAction(ResponseEntity entity) {
        if (entity != null) {
            try {
                JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                if (jsonObject.optInt("status") == 0) {
                    JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").optJSONObject(0);
                    double banben = object.optDouble("Banben");
                    final String url = object.optString("FileUrl");
                    PackageManager manager = getPackageManager();
                    PackageInfo info = manager.getPackageInfo(getPackageName(), 0);

                    String versionName = info.versionName; //版本号
                    if (banben > Double.parseDouble(versionName)) {
                        //如果后台版本大于本地版本就提示更新
                        tip("有新的版本 是否更新？");
                        mTipDialog.showCancel();
                        mTipDialog.setOnClickListener(new TipDialog.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                downloadApk(url);
                            }
                        });
                    } else {
                        tip("已是最新版本");
                    }
                } else {
                    MyToastUtils.show(this, "获取版本号失败");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tip("已是最新版本");
        }
    }

    /**
     * 下载更新包
     *
     * @param url 下载地址
     */
    private void downloadApk(String url) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
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

    public void openApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    private void tip(String msg) {
        mTipDialog.show();
        mTipDialog.setTitle("提醒");
        mTipDialog.setOnClickListener(null);
        mTipDialog.setMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (completeReceiver != null) {
            unregisterReceiver(completeReceiver);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            //startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    private class CompleteReceiver extends BroadcastReceiver {
        public String save_path = "";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == downloadId)
                downComplete(save_path);
        }

        private void downComplete(String filePath) {
            File _file = new File(filePath);
            openApk(SettingActivity.this, _file);
        }
    }
}
