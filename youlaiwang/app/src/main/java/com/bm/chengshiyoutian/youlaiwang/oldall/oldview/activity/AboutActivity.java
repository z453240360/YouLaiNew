package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 关于  界面
 * Created by youj on 2016/1/9.
 */
public class AboutActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_web)
    TextView tvWeb;
    @Bind(R.id.tv_email)
    TextView tvEmail;
    @Bind(R.id.tv_service_terms)
    TextView tvServiceTerms;
    @Bind(R.id.tv_notice_use)
    TextView tvNoticeUse;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.tv_versions)
    TextView tvVersions;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_about);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ivLeft.setOnClickListener(this);
        title.setText(getString(R.string.about));
        tvEmail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //添加下划线
        tvPhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvWeb.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvServiceTerms.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvNoticeUse.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvServiceTerms.setText(getString(R.string.app_name) + "服务条款");
        tvNoticeUse.setText(getString(R.string.app_name) + "使用须知");
        tvPhone.setOnClickListener(this);
        tvEmail.setOnClickListener(this);
        tvWeb.setOnClickListener(this);
        tvNoticeUse.setOnClickListener(this);
        tvServiceTerms.setOnClickListener(this);
        getVersions();
        getData();
    }

    private void getVersions() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = info.versionName;
            tvVersions.setText("版本号: " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tvVersions.setText("版本号: 1.0.0");
        }
    }

    private void getData() {
        InternetConfig config = new InternetConfig();
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.SETTING_URL, "AboutUs", config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());
            int status = jsonObject.optInt("status");
            if (status == 0) {
                JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").optJSONObject(0);
                tvEmail.setText(object.optString("email"));
                tvWeb.setText(object.optString("link_url"));
                phoneNum = object.optString("tel");
                String img_url = object.optString("img_url");
                ImageLoader.getInstance().displayImage(img_url, ivLogo, MyApplication.getInstance().getOptions());
                tvPhone.setText(phoneNum);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_phone:
                callPhone();
                break;
            case R.id.tv_email:

                openEmail();
                break;
            case R.id.tv_web:
                openWeb();
                break;
            case R.id.tv_notice_use:
                Intent intent = new Intent(this, HelpActivity.class);
                intent.putExtra(Constants.TYPE, Constants.NOTICE_FOR_USE);
                startActivity(intent);
                break;

            case R.id.tv_service_terms:
                Intent intent2 = new Intent(this, HelpActivity.class);
                intent2.putExtra(Constants.TYPE, Constants.TERMS);
                startActivity(intent2);
                break;
            default:
                break;
        }

    }

    /**
     * 打开网页
     */
    private void openWeb() {
        Uri uri = Uri.parse("http://" + tvWeb.getText().toString());
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
    }

    /**
     * 打开邮箱
     */
    private void openEmail() {
        String[] email = {tvEmail.getText().toString()};

        Uri uri = Uri.parse("mailto:" + tvEmail.getText().toString());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_EMAIL, email); // 接收人
        //intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
        startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }

    /**
     * 弹出打电话的dialog
     */
    private void callPhone() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_call_phone, null);
        final PopupWindow pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, -2);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        final WindowManager.LayoutParams params = getWindow().getAttributes();//创建当前界面的一个参数对象
        params.alpha = 0.5f;//设置参数的透明度
        getWindow().setAttributes(params);
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 15);

        final TextView phone = (TextView) view.findViewById(R.id.tv_phone);
        phone.setText(phoneNum);

        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                if (ActivityCompat.checkSelfPermission(AboutActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(AboutActivity.this,new String[]{Manifest.permission.CALL_PHONE},100);
                    return;
                }
                startActivity(intent);
                pop.dismiss();
                params.alpha = 1f;//设置参数的透明度
                getWindow().setAttributes(params);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                params.alpha = 1f;//设置参数的透明度
                getWindow().setAttributes(params);
            }
        });

    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
