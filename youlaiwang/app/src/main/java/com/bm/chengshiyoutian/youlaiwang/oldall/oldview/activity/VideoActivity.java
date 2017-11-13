package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.VideoAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.VideoBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;



public class VideoActivity extends Activity implements View.OnClickListener, VideoAdapter.VideoCallBack {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv_list)
    ListView lvList;
    private ProgressDialog progressDialog;
    private List<VideoBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        ivLeft.setOnClickListener(this);
        title.setText("软件使用视频");

        getInfo();


    }

    private void getInfo() {
        progressDialog.show();
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("keyWords", "视频");
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.SETTING_URL, "GetProtocol", params, config, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {

        switch (entity.getKey()) {
            case 0:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.optInt("status")) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        JSONArray dsarray = data.optJSONArray("ds");

                        list = VideoBean.getList(dsarray);

                        VideoAdapter adapter = new VideoAdapter(this, list,R.layout.item_video,this);
                        lvList.setAdapter(adapter);
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
    public void videoIntent(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(list.get(position).getUrl()), "video/mp4");
        startActivity(intent);
    }
}
