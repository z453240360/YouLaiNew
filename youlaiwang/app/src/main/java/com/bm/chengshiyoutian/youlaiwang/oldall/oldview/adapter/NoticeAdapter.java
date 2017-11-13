package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.NoticeBean;

import java.util.List;



/**
 * Created by youj on 2016/1/8.
 */
public class NoticeAdapter extends CommonAdapter<NoticeBean> {

    private Context context;


    public NoticeAdapter(Context context, List<NoticeBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }



    @Override
    public void convert(ViewHolder helper, NoticeBean item, int position) {
        TextView title = helper.getView(R.id.tv_title);
        TextView time = helper.getView(R.id.tv_time);
        TextView content = helper.getView(R.id.tv_content);
        title.setText(item.title);
        time.setText(item.time);
        content.setText(item.content);
    }
}
