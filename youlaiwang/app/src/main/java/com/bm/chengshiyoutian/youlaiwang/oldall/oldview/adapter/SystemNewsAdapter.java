package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.NewsBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;

import java.util.List;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/7/21 16:55
 *
 * @Description:
 */
public class SystemNewsAdapter extends BaseAdapter {

    public List<NewsBean.NewsBeanMessage> newsBeanMessage;
    private Context mContext;

    public SystemNewsAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<NewsBean.NewsBeanMessage> newsBeanMessage) {
        this.newsBeanMessage = newsBeanMessage;
    }

    @Override
    public int getCount() {
        return newsBeanMessage != null ? newsBeanMessage.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_system_new, null);
        }
        TextView tv_title = UniversalViewHolder.get(convertView, R.id.tv_title);
        TextView tv_line = UniversalViewHolder.get(convertView, R.id.tv_line);
        TextView tv_count = UniversalViewHolder.get(convertView, R.id.tv_count);
        TextView tv_time = UniversalViewHolder.get(convertView, R.id.tv_time);
        TextView tv_content = UniversalViewHolder.get(convertView, R.id.tv_content);
        NewsBean.NewsBeanMessage newsBeanMessage = this.newsBeanMessage.get(position);
        if ("0".equals(newsBeanMessage.AlertCount)) {
            tv_count.setVisibility(View.GONE);
            tv_line.setVisibility(View.GONE);
        }
        tv_title.setText(newsBeanMessage.Title);
        tv_count.setText(newsBeanMessage.AlertCount + "次");
        tv_time.setText(newsBeanMessage.AddTimeStr);
        if (!MyUtils.isEmpty(newsBeanMessage.Content)) {
            if (newsBeanMessage.Content.startsWith("您好，您已超过规定时间")) {
                SpannableStringBuilder builder = getTextBuilder(newsBeanMessage.Content, "您好，您已超过规定时间", 0);
                tv_content.setText(builder);
            } else if (newsBeanMessage.Content.startsWith("您好,您已超过规定时间")) {
                SpannableStringBuilder builder = getTextBuilder(newsBeanMessage.Content, "您好,您已超过规定时间", 0);
                tv_content.setText(builder);
            } else if (newsBeanMessage.Content.startsWith("您好，还有")) {
                SpannableStringBuilder builder = getTextBuilder(newsBeanMessage.Content, "您好，还有", 1);
                tv_content.setText(builder);
            } else if (newsBeanMessage.Content.startsWith("您好,还有")) {
                SpannableStringBuilder builder = getTextBuilder(newsBeanMessage.Content, "您好,还有", 1);
                tv_content.setText(builder);
            } else if (newsBeanMessage.Content.startsWith("您好，您已超时未上报")) {
                SpannableStringBuilder builder = getTextBuilder(newsBeanMessage.Content, "您好，您已超时未上报", 1);
                tv_content.setText(builder);
            } else if (newsBeanMessage.Content.startsWith("您好,您已超时未上报")) {
                SpannableStringBuilder builder = getTextBuilder(newsBeanMessage.Content, "您好,您已超时未上报", 1);
                tv_content.setText(builder);
            } else {
                tv_content.setText(newsBeanMessage.Content);
            }
        } else {
            tv_content.setText(newsBeanMessage.Content);
        }
        return convertView;
    }

    private SpannableStringBuilder getTextBuilder(String messageAll, String message, int type) {
        SpannableStringBuilder builder = new SpannableStringBuilder(messageAll);
        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.yello));
        if (type == 1) {
            builder.setSpan(yellowSpan, message.length(), message.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            builder.setSpan(yellowSpan, message.length() + 1, message.length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }
}
