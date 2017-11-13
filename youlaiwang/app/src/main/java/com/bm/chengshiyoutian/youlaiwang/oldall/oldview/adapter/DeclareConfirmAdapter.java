package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;

import java.util.List;



/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class DeclareConfirmAdapter extends CommonAdapter<RepositoryBillBean> {
    public DeclareConfirmAdapter(Context context, List<RepositoryBillBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, RepositoryBillBean item, int position) {
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.productName);
        TextView tv_count_unit = helper.getView(R.id.tv_count_unit);
        tv_count_unit.setText(item.productNum+item.productNnit);
        TextView tv_years = helper.getView(R.id.tv_years);

        if ("".equals(item.productTime)|| TextUtils.isEmpty(item.productTime)){
            tv_years.setText("");
        }else {
        tv_years.setText(item.productTime);
        }

        TextView tv_pihao = helper.getView(R.id.tv_pihao);
        if ("".equals(item.productPiHao)|| TextUtils.isEmpty(item.productPiHao)){
            tv_pihao.setText("");
        }else {
            tv_pihao.setText(item.productPiHao);
        }


    }
}
