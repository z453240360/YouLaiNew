package com.bm.chengshiyoutian.youlaiwang.activity;


import android.app.DownloadManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.SharedPUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.fragment.GongYingShangFragment;
import com.bm.chengshiyoutian.youlaiwang.fragment.GouWuCheFragment;
import com.bm.chengshiyoutian.youlaiwang.fragment.ShangPinFragment;
import com.bm.chengshiyoutian.youlaiwang.fragment.ShouYeFragment;
import com.bm.chengshiyoutian.youlaiwang.fragment.WoDeFragment2;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.packageutils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.UpDataDialogDialog;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.ShoppingCar.ShoppingFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment.FirstFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment.LastWeekFragment;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.jauker.widget.BadgeView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by SLD on 2017/4/19.
 */

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg;
    public static RadioButton rb1;
    public static RadioButton rb2;
    public static RadioButton rb3;
    public static RadioButton rb4;
    public static RadioButton rb5;
    private RelativeLayout ll;
    private UpDataDialogDialog mUpDataDialogDialog;
    private CompleteReceiver completeReceiver;
    public static TextView mIv_gouwuche;
    private BadgeView badgeView1;
    private long downloadId;
    private DownloadManager downloadManager;
    private long firstTime = 0;
    public static SharedPreferences sp;
    private ArrayList<Fragment> list = new ArrayList<>();
    private FragmentManager managers;
    private Fragment lastFragment;
    private GouWuCheFragment fragment3;
    private FirstFragment fragment1;
    private LastWeekFragment fragment;
    private ShoppingFragment fragment2;
    private WoDeFragment2 fragment4;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onResume() {
        super.onResume();
        getData1();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorState.StatusBarLightMode(this);
        setContentView(R.layout.activity_new_main);
        sp = getSharedPreferences(MyRes.CONFIG, 0);
        initView();
        getMeasureByPhone();
        checkForUpdata();
        initCallBack();
    }

    //处理Fragment回调事件
    private void initCallBack() {
        //首页回调

        fragment1.setmListener(new FirstFragment.onClickListener() {
            @Override
            public void getString(String s) {
                if (s.equals("gengduo1")) {
                    rb3.setChecked(true);

                    sp.edit().putString("myDdJump", "gengduo1").commit();
                } else if (s.equals("gengduo2")) {
                    rb3.setChecked(true);
                    sp.edit().putString("myDdJump", "gengduo2").commit();
                }
            }

            @Override
            public void getCarNum(String s) {
                getData1();
            }
        });


        fragment3.setChange(new GouWuCheFragment.GetChange() {
            @Override
            public void getChange(String s) {
                if (s.equals("shoppingCar")) {
                    rb3.setChecked(true);
                }
            }
        });

        fragment.setIsLogin(new LastWeekFragment.OnClicked() {
            @Override
            public void isLogin() {
                rb1.setChecked(true);
            }
        });

        fragment2.setGetCarNum(new ShoppingFragment.GetCarNum() {
            @Override
            public void getCarNum(int cartNum) {
                if (cartNum > 0) {
                    mIv_gouwuche.setVisibility(View.VISIBLE);
                    mIv_gouwuche.setText(cartNum + "");
                } else {
                    mIv_gouwuche.setVisibility(View.GONE);
                }
            }
        });

    }

    //测量手机尺寸
    private void getMeasureByPhone() {

    }

    private void initView() {

        sharedPreferences = getSharedPreferences(MyRes.CONFIG, Context.MODE_PRIVATE);
        mUpDataDialogDialog = new UpDataDialogDialog(this);

        mIv_gouwuche = (TextView) findViewById(R.id.iv_gouwuche);
        badgeView1 = new BadgeView(this);
        badgeView1.setTargetView(rb4);
        badgeView1.setBackground(9,Color.parseColor("#fe7938"));
        badgeView1.setBadgeCount(5);

        rg = (RadioGroup) findViewById(R.id.rg);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        rb5 = (RadioButton) findViewById(R.id.rb5);
        ll = (RelativeLayout) findViewById(R.id.ll);
        initFragment();
        initRadioButton();

    }

    //初始化Fragment
    private void initFragment() {
        managers = getSupportFragmentManager();

        fragment1 = new FirstFragment();
        fragment = new LastWeekFragment();
        fragment2 = new ShoppingFragment();
        fragment3 = new GouWuCheFragment();
        fragment4 = new WoDeFragment2();

        list.add(fragment1);

        list.add(fragment2);
        list.add(fragment);
        list.add(fragment3);
        list.add(fragment4);

        managers.beginTransaction().add(R.id.tb, list.get(0)).commit();
        lastFragment = list.get(0);
    }

    //初始化单选按钮
    private void initRadioButton() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton select = (RadioButton) findViewById(i);
                int index = Integer.parseInt(select.getTag().toString());

                if (index == 3||index==2) {
                    if (((sharedPreferences.getString(MyRes.TOKEN, "")).equals("")) | ((sharedPreferences.getString(MyRes.TOKEN, "")).equals("kong"))) {
                        Intent intent = new Intent(MainActivity.this, Login_ddActivity.class);


                        rb1.setChecked(true);

                        startActivityForResult(intent, 201);
                        return;
                    }
                }



                if (list.get(index).isAdded()) {
                    managers.beginTransaction().show(list.get(index)).commit();
                    if (index == 3) {
                        if (((sharedPreferences.getString(MyRes.TOKEN, "")).equals("")) | ((sharedPreferences.getString(MyRes.TOKEN, "")).equals("kong"))) {
                            Intent intent = new Intent(MainActivity.this, Login_ddActivity.class);
                            startActivityForResult(intent, 201);
                        }
                        fragment3.getData();
                    }
                } else {
                    managers.beginTransaction().add(R.id.tb, list.get(index)).commit();
                    if (index == 3) {
                        if (((sharedPreferences.getString(MyRes.TOKEN, "")).equals("")) | ((sharedPreferences.getString(MyRes.TOKEN, "")).equals("kong"))) {
                            startActivity(new Intent(MainActivity.this, Login_ddActivity.class));
                        }
                    }
                }
                managers.beginTransaction().hide(lastFragment).commit();
                lastFragment = list.get(index);
            }
        });

    }

    //检查更新
    private void checkForUpdata() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/version", RequestMethod.GET);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                String result = response.get().toString();
                if (response.get() != null) {
                    try {

                        JSONObject object = new JSONObject(result);
                        if (object.getInt("code") == 200) {
                            JSONObject object1 = object.getJSONObject("data");
                            JSONObject android = object1.getJSONObject("dataAndroid");
                            String url = android.getString("av_url");
                            String content = android.getString("av_content");
                            double v1 = Double.parseDouble(android.getString("av_version"));
                            double v2 = Double.parseDouble(packageutils.getVersionName(MainActivity.this));
                            if (v1 <= v2) {

                            } else {
                                initDialog(url, content);
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

    //显示更新信息
    private void initDialog(final String url, String content) {
        mUpDataDialogDialog.show();
        mUpDataDialogDialog.setMessage(content);
        mUpDataDialogDialog.setOnCanceClicklListener(new UpDataDialogDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpDataDialogDialog.dismiss();
            }
        });

        mUpDataDialogDialog.setOnClickListener(new UpDataDialogDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadApk(MyRes.BASE_URL1 + url);
            }
        });
    }

    //下载APP
    private void downloadApk(String url) {
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        String dir = isFolderExist("youlaiyouwang");
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        completeReceiver = new MainActivity.CompleteReceiver();
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

    //广播，提示下载完成
    private class CompleteReceiver extends BroadcastReceiver {
        public String save_path = "";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == downloadId) {

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
                }

            }

        }

        private void downComplete(String filePath) {
            File _file = new File(filePath);
            openApk(MainActivity.this, _file);
        }
    }

    //打开APP
    public void openApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    //仅第一次进入调用,获取购物车数量
    public static void getData1() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/total");
        String token = sp.getString(MyRes.TOKEN, "1");
        stringRequest.addHeader("Authorization", "Bearer " + token);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                String result = response.get().toString();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        JSONObject object1 = object.getJSONObject("data");
                        int cartNum = object1.getInt("cartNum");
                        if (cartNum > 0) {
                            mIv_gouwuche.setVisibility(View.VISIBLE);
                            mIv_gouwuche.setText(cartNum + "");
                        } else {
                            mIv_gouwuche.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
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


    //连续点击两次退出APP
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    //两次按键小于2秒时，退出应用
                    finish();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {

        } else if (requestCode == 201) {
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}