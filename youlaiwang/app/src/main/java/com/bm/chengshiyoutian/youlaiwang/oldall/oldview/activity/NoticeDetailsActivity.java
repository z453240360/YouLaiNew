package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.NoticeBean;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by jayen on 16/1/12.
 */
public class NoticeDetailsActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_content)
    TextView tvContent;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notice_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ivLeft.setImageResource(R.mipmap.back);
        title.setText(R.string.notice);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.home);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        id = getIntent().getStringExtra("id");
        title.setText(getString(R.string.notice));
        setData();
    }

    private void setData() {
        NoticeBean notice = (NoticeBean) getIntent().getSerializableExtra("notice");
        String content = notice.content;
        String time = notice.time;
        String title = notice.title;
        tvTitle.setText(title);
        tvTime.setText(time);
        tvContent.setText(content);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.iv_right:
                startActivity(new Intent(this, XiangQingActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
}
