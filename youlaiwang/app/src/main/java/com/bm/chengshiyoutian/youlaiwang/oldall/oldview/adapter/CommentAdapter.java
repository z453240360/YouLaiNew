package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.CommentBean;

import java.util.List;



/**
 * Created by youj on 2016/1/18.
 */
public class CommentAdapter extends CommonAdapter<CommentBean> {
    private List<CommentBean> list;

    public CommentAdapter(Context context, List<CommentBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.list = mDatas;
    }


    @Override
    public void convert(ViewHolder helper, CommentBean item, int position) {
        TextView tv_comment = helper.getView(R.id.tv_comment);
        TextView tv_time = helper.getView(R.id.tv_time);
        CommentBean commentBean = list.get(position);
        tv_comment.setText(commentBean.comment);
        tv_time.setText(commentBean.time);
    }
}
