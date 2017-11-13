package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.joanzapata.pdfview.PDFView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



//2017/1/5
public class PDFActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    private PDFView mPdfView;
    private ProgressDialog dialog;
    private String Keys = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
//        dialog = new ProgressDialog(this);
        mPdfView = (PDFView) findViewById(R.id.pdfView);
        String type = getIntent().getStringExtra("Type");

        if ("3".equals(type)) {
            title.setText("食品分类明细表");
            Keys = "食品分类";
        }else if("4".equals(type)){
            title.setText("软件使用说明");
            Keys="软件使用";
        }
        ivLeft.setOnClickListener(this);
        getAdress();


    }

    private void getAdress() {
//            dialog.show();
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("keyWords", Keys);
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.SETTING_URL, "GetProtocol", params, config, this);

    }

    private void setInfo(String url) {

        mPdfView.fromFile(new File(url))
//               . swipeVertical(true)
                .defaultPage(1)
                .showMinimap(true)
                .enableSwipe(true)
                .load();
    }


    public void download(String urlStr) {

//        dialog.show();
        String urlString = urlStr;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)
                    url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            connection.setUseCaches(false);
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
            //实现连接
            connection.connect();


            System.out.println("connection.getResponseCode()=" + connection.getResponseCode());
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                //以下为下载操作
                byte[] arr = new byte[1];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(baos);
                int n = is.read(arr);
                while (n > 0) {
                    bos.write(arr);
                    n = is.read(arr);
                }
                bos.close();
                String path = Environment.getExternalStorageDirectory()
                        + "/Download/";
                String[] name = urlString.split("/");
                path = path + name[name.length - 1];


                File file = new File(path);
                if (file.exists()) {
                    setInfo(path);
//                    Log.i("Home","已有");
                } else {

                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(baos.toByteArray());
                    fos.close();
                    //关闭网络连接
                    connection.disconnect();
//                    Log.i("Home","下载");
                    setInfo(path);
                }
//                dialog.dismiss();
            }
        } catch (IOException e) {

        }

    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {

        switch (entity.getKey()) {
            case 0:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.optInt("status")) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        JSONArray dsarray = data.optJSONArray("ds");
//                        final String link_url= "";

                        for (int i = 0; i < dsarray.length(); i++) {
                            if (dsarray.optJSONObject(i).optString("title").equals("软件使用说明")||dsarray.optJSONObject(i).optString("title").equals("食品分类明细表")) {
                                final String link_url = dsarray.optJSONObject(i).optString("link_url");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        download(link_url);
                                    }
                                }).start();
                            }
                        }


                    } else {
                        MyToastUtils.show(this, "数据异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
