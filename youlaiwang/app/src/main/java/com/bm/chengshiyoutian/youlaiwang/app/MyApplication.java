package com.bm.chengshiyoutian.youlaiwang.app;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.pc.ioc.app.Ioc;
import com.android.pc.util.Handler_File;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.BaseBannerBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by youj on 2016/1/8.
 */
public class MyApplication extends Application {

    static Context mContext;

    public static MyApplication app;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public ArrayList<BaseBannerBean> bannerList;

    /**
     * 0 表示 索票 选择图片的跳转，
     * 1 表示明细选择图片的跳转,
     * 2 表示注册选择图片的跳转,
     * 3.表示原料票据选择图片的跳转,
     * 4.表示不合格/临期产品选择图片跳转
     * 5.表示仓库台账（销售）选择图片跳转
     * 6.表示编辑个人资料选择图片跳转
     */
    public int selectPhoto = 0;

    public int imageNum = 100;//选择图片的数量

    public boolean isUpdataPersionMessage = false;//是否更新了用户数据
    public List<String> imageStringDrr = new ArrayList<>();
    //存放明细的集合
    public List<RepositoryBillBean> RepositoryBillList = new ArrayList<RepositoryBillBean>();

    /**
     * 用户对象
     */
    private RestaurantBean bean;

    /**
     * 获取全局的context
     */
    public static Context getContext() {
        return mContext;
    }


    public static MyApplication getInstance() {
        return app;
    }


    @Override
    public void onCreate() {

        super.onCreate();
        app = this;
        Fresco.initialize(this);


        mContext = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        NoHttp.initialize(this, new NoHttp.Config()
                .setNetworkExecutor(new OkHttpNetworkExecutor())  // 使用OkHttp做网络层。
        );
//        Logger.setDebug(false); // 开启NoHttp调试模式。
//        Logger.setTag("nohttp"); // 设置NoHttp打印Log的TAG。
        Ioc.getIoc().init(this);
        initImageLoader();
    }

    /**
     * 初始imageLoader
     */
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * ImagerLoader默认图片配置
     *
     * @return
     */
    public DisplayImageOptions getOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.loading_on_img)/* 正在加载 */
                .showImageForEmptyUri(R.mipmap.loading_empty_img)/* 暂无图片 */
                .showImageOnFail(R.mipmap.loading_fail_img)/* 加载失败 */
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .build();
    }

    /**
     * ImagerLoader默认图片配置  头像加载
     *
     * @return
     */
    public DisplayImageOptions getIconOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.loading_on_img)/* 正在加载 */
                .showImageForEmptyUri(R.mipmap.loading_empty_img)/* 暂无图片 */
                .showImageOnFail(R.mipmap.peoson)/* 加载失败 */
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .build();
    }

    public RestaurantBean getUser() {
        bean = Handler_File.getObject("user.bin");
        if (null == bean) {
            bean = new RestaurantBean();
            setUser(bean);
        }
        return bean;
    }

    public void setUser(RestaurantBean user) {
        Handler_File.saveObject("user.bin", user);
    }




    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                       Ioc.getIoc().init(MyApplication.app);
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                // 显示缺失权限提示
                showMissingPermissionDialog();
            }
        }
    };


    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help);
        builder.setMessage("当前应用缺少必要权限\n请点击-设置-权限-打开所需权限.");

        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.show();
    }


    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }



}
