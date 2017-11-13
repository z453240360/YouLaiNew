package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HelpNewActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ll_trouble_shooting)
    LinearLayout llTroubleShooting;
    @Bind(R.id.ll_video)
    LinearLayout llVideo;
    @Bind(R.id.ll_pdf)
    LinearLayout llPdf;
    @Bind(R.id.ll_agreement)
    LinearLayout llAgreement;
    @Bind(R.id.ll_detailed)
    LinearLayout llDetailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_new);
        ButterKnife.bind(this);
        title.setText("帮助");
        initlistener();

    }

    private void initlistener() {
        ivLeft.setOnClickListener(this);
        llTroubleShooting.setOnClickListener(this);
        llVideo.setOnClickListener(this);
        llPdf.setOnClickListener(this);
        llAgreement.setOnClickListener(this);
        llDetailed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_trouble_shooting://常见问题解答
                intent.putExtra("Type","1");
                intent.setClass(this,HelpDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_video://软件使用视频
                intent.setClass(this,VideoActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_pdf://pdf版本说明
                http://112.64.173.178:20082/admin/expand/upload/0906_App端需求说明.pdf
                intent.putExtra("Type","3");
                intent.setClass(this,PDFActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_agreement://注册协议
                intent.putExtra("Type","2");
                intent.setClass(this,HelpDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_detailed://软件使用说明
                intent.putExtra("Type","4");
                intent.setClass(this,PDFActivity.class);
                startActivity(intent);
                break;

        }

    }
}
