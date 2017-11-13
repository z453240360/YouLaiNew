package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.app;

import android.app.Application;

import com.android.pc.ioc.app.Ioc;
import com.android.pc.util.Handler_File;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.BaseBannerBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;




/**
 * Created by youj on 2016/1/8.
 */
public class App extends Application {

    public static App app;


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

    public static App getInstance() {
        return app;
    }

    /**
     * 注册手机
     */

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
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
}
