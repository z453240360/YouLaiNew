package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.DeclareBean;

import java.util.List;



/**
 * Created by jayen on 16/1/13.
 */
public class DeclareAdapter extends CommonAdapter<DeclareBean> {
    private List<DeclareBean> list;
    private int type;
    private Context context;


    public DeclareAdapter(Context context, List<DeclareBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        list = mDatas;
        this.context = context;
    }

    public DeclareAdapter(Context context, List<DeclareBean> mDatas, int itemLayoutId, int type) {
        super(context, mDatas, itemLayoutId);
        list = mDatas;
        this.context = context;
        this.type = type;
    }


    @Override
    public void convert(ViewHolder helper, DeclareBean item, int position) {
        TextView time = helper.getView(R.id.tv_time);
        TextView oilNum = helper.getView(R.id.tv_oil_num);
        TextView junkNum = helper.getView(R.id.tv_junk_num);
        DeclareBean bean = list.get(position);

        if (type == 1) {

        } else {
            oilNum.setText(bean.oil_num+ context.getResources().getString(R.string.L));
            junkNum.setText(bean.junk_num+ "桶");
            time.setText(bean.time);
        }

    }
}
