package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/8/1 17:55
 *
 * @Description:
 */
public class DialogChangeNum extends Dialog implements View.OnClickListener {
    private final TextView mTv_sure;
    private final EditText mEt_input_num;
    private final TextView mTv_product_name;
    private final TextView mTv_product_num;
    private final TextView mTv_product_unit;
    private Context mContext;
    private ChangeNumListener mChangeNumListener;
    private RepositoryBillBean mRepositoryBillBean;

    public DialogChangeNum(Context context, RepositoryBillBean repositoryBillBean) {
        super(context, R.style.Theme_MyDialog);// 使用样式
        setContentView(R.layout.dialog_change_num);
        mContext = context;
        mRepositoryBillBean = repositoryBillBean;
        mEt_input_num = (EditText) findViewById(R.id.et_input_num);
        MyUtils.setPricePoint(mEt_input_num);
        mTv_sure = (TextView) findViewById(R.id.tv_sure);
        mTv_product_name = (TextView) findViewById(R.id.tv_product_name);
        mTv_product_num = (TextView) findViewById(R.id.tv_product_num);
        mTv_product_unit = (TextView) findViewById(R.id.tv_product_unit);
        setData();
        setListener();
        setProperty();
    }

    private void setData() {
        if (mRepositoryBillBean != null) {
            mTv_product_name.setText(mRepositoryBillBean.productName);
            mTv_product_num.setText(mRepositoryBillBean.productNum);
            mTv_product_unit.setText(mRepositoryBillBean.productNnit);
        }
    }

    public void setChangeNumListener(ChangeNumListener changeNumListene) {
        mChangeNumListener = changeNumListene;
    }

    public void setListener() {
        mTv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                String numInput = mEt_input_num.getText().toString();
                if (!MyUtils.isEmpty(numInput) && mChangeNumListener != null) {
                    mChangeNumListener.changeNum(numInput);
                }
                break;
        }
    }

    /**
     * 设置对话框窗体属性
     */
    private void setProperty() {
        //setCanceledOnTouchOutside(false);// 对话框以外无法取消
        setCancelable(true);
        int h = this.mContext.getResources().getDisplayMetrics().heightPixels;
        int w = this.mContext.getResources().getDisplayMetrics().widthPixels;
        if (h < w) {
            w = h;
        }
        Window window = getWindow();//得到对话框的窗口．
        WindowManager.LayoutParams lp = window.getAttributes();
        // 中间
        lp.x = 0;// 这两句设置了对话框的位置．0为中间
        // 宽度
        lp.width = w * 3 / 4;// 对话框的宽 占屏幕
        // 高
        // lp.height = lp.WRAP_CONTENT;// 对话框的高包裹内容
        lp.height = h / 3;
        // 透明度
        // wl.alpha = 0.9f;// 这句设置了对话框的透明度
        // 布局相对位置
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    public interface ChangeNumListener {
        void changeNum(String number);
    }
}
