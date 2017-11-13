package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.services;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;



public class CacheImageService extends Service implements ImageLoadingListener {

    private static Activity activity;

    public static void startsevice(Context context) {
        activity = (Activity) context;
        Intent in = new Intent(context, CacheImageService.class);
        context.startService(in);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //编辑个人资料回现上传的相关证件
        RestaurantBean restaurantBean = MyApplication.getInstance().getUser();
        if (restaurantBean != null && !TextUtils.isEmpty(restaurantBean.id)) {
            MyApplication.getInstance().imageStringDrr.clear();
            showHttpImage(restaurantBean, ImageLoader.getInstance());
        }
    }

    private void showHttpImage(RestaurantBean bean, ImageLoader imageLoader) {
        if (bean.licenseImg != null && bean.licenseImg.size() > 0) {
            for (String imgUrl : bean.licenseImg) {
                try {
                    imageLoader.loadImage(imgUrl, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onLoadingStarted(String s, View view) {

    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        Date date = new Date();
        long time = date.getTime();
        if (activity != null) {
            GetImg img = new GetImg(activity);
            File file = img.setDefaultFile("/" + time + "img.png");
            if (file != null) {
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.flush();
                    out.close();
                    MyApplication.getInstance().imageStringDrr.add(file.getAbsolutePath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onLoadingCancelled(String s, View view) {

    }
}
