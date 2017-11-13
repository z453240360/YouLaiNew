package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.VideoBean;

import java.util.List;



/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class VideoAdapter extends CommonAdapter<VideoBean> {

    private VideoCallBack callback;
    public VideoAdapter(Context context, List<VideoBean> mDatas, int itemLayoutId, VideoCallBack callback) {
        super(context, mDatas, itemLayoutId);
        this.callback = callback;
    }

    @Override
    public void convert(ViewHolder helper, VideoBean item, final int position) {
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.getName()+":");
        /*TextView tv_url = helper.getView(R.id.tv_url);
        tv_url.setText(Html.fromHtml("<u>"+item.getUrl()+"</u>"));
        tv_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.videoIntent(position);
            }
        });*/
    }
    public interface VideoCallBack{
        void videoIntent(int position);
    }
}
