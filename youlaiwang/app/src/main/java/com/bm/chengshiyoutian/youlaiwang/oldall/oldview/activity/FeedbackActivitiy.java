package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 意见反馈
 * Created by youj on 2016/1/9.
 */
public class FeedbackActivitiy extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.bt_submit)
    Button btSubmit;
    @Bind(R.id.ed_str)
    EditText edStr;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_feedback);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        ivLeft.setOnClickListener(this);
        title.setText(getString(R.string.feedback));
        btSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.bt_submit:
                submit();
                break;

            default:
                break;
        }
    }


    private void submit() {
        String str = edStr.getText().toString().trim();
        if (TextUtils.isEmpty(str)) {
            tvState.setVisibility(View.VISIBLE);
            tvState.setText("请填写内容");
            return;
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        String id = MyApplication.getInstance().getUser().id;
        params.put("ShopId",null==id ? "":id);
        params.put("strCotent", str);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.FEEDBACK, "AppFeekBack", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        try {
            JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                tvState.setVisibility(View.VISIBLE);
            if(jsonObject.optInt("status") == 0) {
                tvState.setText("反馈成功");
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }).start();
            } else {
                tvState.setText(jsonObject.optString("msg"));
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
        MyToastUtils.show(this,getString(R.string.intnet_err));
    }
}
