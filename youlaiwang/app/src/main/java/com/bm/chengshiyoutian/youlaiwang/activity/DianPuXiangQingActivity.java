package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.DianPuXiangQingBeanWuPIC;
import com.bm.chengshiyoutian.youlaiwang.bean.ZhenDianPuXiangQingBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.JsonSyntaxException;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;



/**
 * Created by Administrator on 2017/5/22.
 */

public class DianPuXiangQingActivity extends AppCompatActivity {
    String stringExtra;
    private LinearLayout detail_sign;
    private ImageView logo;
    private TextView store_name;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_renzheng;
    private TextView tv_jieshao;
    private TextView tv_diqu;
    private TextView tv_time;
    private ImageView tv_pic;
    private ImageView iv_finish;
    private TextView tv;
    private TextView company_license_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_dianpuxiangqing);
        initView();
        stringExtra = getIntent().getStringExtra(MyRes.DIANPU_ID);
        getDatas();
    }

    private void getDatas() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "/api/store/" + stringExtra);
        SharedPreferences sp=getSharedPreferences(MyRes.CONFIG,0);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(11,stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                try {
                    final ZhenDianPuXiangQingBean zhenDianPuXiangQingBean = GsonUtils.getInstance().fromJson((String) response.get(), ZhenDianPuXiangQingBean.class);
                    //   zhenDianPuXiangQingBean.getData().gets
                    if (zhenDianPuXiangQingBean.getData().getIs_favorites() == 1) {
                        tv.setText("已收藏");
                        tv.setBackgroundColor(0xffdfb800);
                    } else {
                        tv.setBackgroundColor(0xff404040);
                        tv.setText("未收藏");
                    }
                    company_license_num.setText(getIntent().getStringExtra("data"));
                    tv_phone.setText(zhenDianPuXiangQingBean.getData().getStore_phone());
                    tv_diqu.setText(zhenDianPuXiangQingBean.getData().getPca_info());
                    store_name.setText(zhenDianPuXiangQingBean.getData().getStore_name());
                    tv_jieshao.setText(zhenDianPuXiangQingBean.getData().getStore_synopsis());
                    tv_time.setText(zhenDianPuXiangQingBean.getData().getCreate_time());
                    tv_name.setText(zhenDianPuXiangQingBean.getData().getStore_linkman());
                    Glide.with(DianPuXiangQingActivity.this)
                            .load(zhenDianPuXiangQingBean.getData().getStore_logo()).into(logo);
                    Glide.with(DianPuXiangQingActivity.this)
                            .load(zhenDianPuXiangQingBean.getData().getCompany_license().get(0)).centerCrop().into(tv_pic);
                    tv_pic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DianPuXiangQingActivity.this, PicActivity.class);
                            intent.putExtra("url", zhenDianPuXiangQingBean.getData().getCompany_license().get(0));
                            startActivity(intent);

                        }
                    });

                    Glide.with(DianPuXiangQingActivity.this).load(zhenDianPuXiangQingBean.getData().getDetail_sign()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Drawable drawable = new BitmapDrawable(resource);


                            detail_sign.setBackgroundDrawable(drawable);
                        }
                    });
                } catch (JsonSyntaxException e) {
                    final DianPuXiangQingBeanWuPIC zhenDianPuXiangQingBean = GsonUtils.getInstance().fromJson((String) response.get(), DianPuXiangQingBeanWuPIC.class);
                    //   zhenDianPuXiangQingBean.getData().gets
                    if (zhenDianPuXiangQingBean.getData().getIs_favorites() == 1) {
                        tv.setText("已收藏");
                        tv.setBackgroundColor(0xffdfb800);
                    } else {
                        tv.setBackgroundColor(0xff404040);
                        tv.setText("未收藏");
                    }
                    company_license_num.setText(getIntent().getStringExtra("data"));
                    tv_phone.setText(zhenDianPuXiangQingBean.getData().getStore_phone());
                    tv_diqu.setText(zhenDianPuXiangQingBean.getData().getPca_info());
                    store_name.setText(zhenDianPuXiangQingBean.getData().getStore_name());
                    tv_jieshao.setText(zhenDianPuXiangQingBean.getData().getStore_synopsis());
                    tv_time.setText(zhenDianPuXiangQingBean.getData().getCreate_time());
                    tv_name.setText(zhenDianPuXiangQingBean.getData().getStore_linkman());
                    Glide.with(DianPuXiangQingActivity.this)
                            .load(zhenDianPuXiangQingBean.getData().getStore_logo()).into(logo);
              //      Glide.with(DianPuXiangQingActivity.this)
//                            .load(zhenDianPuXiangQingBean.getData().getCompany_license().get(0)).centerCrop().into(tv_pic);
//                    tv_pic.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(DianPuXiangQingActivity.this, PicActivity.class);
//                            intent.putExtra("url", zhenDianPuXiangQingBean.getData().getCompany_license().get(0));
//                            startActivity(intent);
//
//                        }
//                    });

                    Glide.with(DianPuXiangQingActivity.this).load(zhenDianPuXiangQingBean.getData().getDetail_sign()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Drawable drawable = new BitmapDrawable(resource);


                            detail_sign.setBackgroundDrawable(drawable);
                        }
                    });
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
        detail_sign = (LinearLayout) findViewById(R.id.detail_sign);
        logo = (ImageView) findViewById(R.id.logo);
        store_name = (TextView) findViewById(R.id.store_name);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_renzheng = (TextView) findViewById(R.id.tv_renzheng);
        tv_jieshao = (TextView) findViewById(R.id.tv_jieshao);
        tv_diqu = (TextView) findViewById(R.id.tv_diqu);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_pic = (ImageView) findViewById(R.id.tv_pic);

        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv = (TextView) findViewById(R.id.tv);

        company_license_num = (TextView) findViewById(R.id.company_license_num);

    }
}
