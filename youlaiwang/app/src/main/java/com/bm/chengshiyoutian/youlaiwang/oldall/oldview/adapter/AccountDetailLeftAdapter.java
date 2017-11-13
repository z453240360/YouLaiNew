package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.AccountDetailBean;

import java.util.List;



/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class AccountDetailLeftAdapter extends CommonAdapter<AccountDetailBean> {

    private int selectedPosition = 0;// 选中的位置
    private Context context;

    public AccountDetailLeftAdapter(Context context, List<AccountDetailBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @Override
    public void convert(ViewHolder helper, AccountDetailBean item, int position) {
       TextView tv_left_name = helper.getView(R.id.tv_left_name);
        tv_left_name.setText(item.getClassName());
        RelativeLayout ll = helper.getView(R.id.ll);

        if (selectedPosition == position) {

            tv_left_name.setTextColor(Color.parseColor("#FFFFFF"));
            ll.setBackgroundColor(context.getResources().getColor(R.color.green));
        }else {
            tv_left_name.setTextColor(Color.parseColor("#A0A0A0"));
            ll.setBackgroundColor(context.getResources().getColor(R.color.gray_list));
        }
        }
}
