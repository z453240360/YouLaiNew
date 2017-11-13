package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectHttpErr;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * 二维码
 * Created by youj on 2016/1/19.
 */
public class QRCodeActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_qrCode)
    ImageView ivQrCode;
    private String oldFile;
    private ProgressDialog progressDialog;
    private String uri;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_qrcode);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        id = getIntent().getStringExtra("id");
        if (null == id) {
            id = MyApplication.getInstance().getUser().id;
        }
        progressDialog = new ProgressDialog(this);
        ivLeft.setOnClickListener(this);
        ivQrCode.setOnLongClickListener(this);
        title.setText("二维码");
        getQRcode();
    }


    /**
     * 获取验证码
     */
    private void getQRcode() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("id",id);
        InternetConfig config = new InternetConfig();
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetErweima", params, config, this);
    }


    @InjectHttp
    private void injectHttp(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());
            if (jsonObject.optInt("status") == 0) {
                uri = jsonObject.optString("data");
                ImageLoader.getInstance().displayImage(uri, ivQrCode, MyApplication.getInstance().getOptions());
            } else {
                MyToastUtils.show(this,"获取验证码失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            default:
                 break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/DCIM/Camera");
        oldFile = ImageLoader.getInstance().getDiskCache().get(uri).toString();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String newFile = new File(dir, System.currentTimeMillis() + ".jpg").toString();
        copyFile(oldFile , newFile);
        return false;
    }



    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
               MyToastUtils.show(this,"保存成功");
            }
        }
        catch (Exception e) {
            MyToastUtils.show(this, "保存失败");
            e.printStackTrace();
        }

    }
}
