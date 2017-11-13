package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.DemandingCertificatesBeans;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.SearchHistoryInteface;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;

import java.util.ArrayList;



/**
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @author wujm
 * @Description 首页弹出的对话框
 * @date 2015-3-12 下午4:29:41
 */
public class MyDialog extends Dialog implements AdapterView.OnItemClickListener {

    private Context context;
    private ArrayList<DemandingCertificatesBeans.DemandingCertificates> message;
    private EditText et_id;
    private ListView lv_history;
    private TextView tv_sure;
    private SearchHistoryInteface searchHistoryInteface;
    private TextView tv_cancel;

    public MyDialog(Context context, ArrayList<DemandingCertificatesBeans.DemandingCertificates> message, SearchHistoryInteface searchHistoryInteface) {
        super(context, R.style.Theme_MyDialog);// 使用样式
        this.context = context;
        this.message = message;
        this.searchHistoryInteface = searchHistoryInteface;
        setContentView(R.layout.demanding_dialog);
        findViewById();// 初始控件
        setProperty();// 设置对话框显示属性
        setContent();
        setOnClickListener();
        //屏蔽返回键
        this.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                  @Override
                                  public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                                      //默认返回 false
                                      return i == KeyEvent.KEYCODE_BACK;
                                  }
                              }

        );
    }

    /**
     * 初始控件
     */
    private void findViewById() {
        et_id = (EditText) findViewById(R.id.et_id);
        lv_history = (ListView) findViewById(R.id.lv_history);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
    }

    /**
     * 设置显示内容
     */
    public void setContent() {
        DemandingCertificatesAdapter demandingCertificatesAdapter = new DemandingCertificatesAdapter();
        lv_history.setAdapter(demandingCertificatesAdapter);
        lv_history.setOnItemClickListener(this);
    }

    /**
     * 设置事件
     */
    public void setOnClickListener() {
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String supplierName = et_id.getText().toString();
                if (TextUtils.isEmpty(supplierName)) {
                    MyToastUtils.show(context, context.getResources().getString(R.string.input_gongyingshang_num));
                    return;
                }
                if (searchHistoryInteface != null) {
                    searchHistoryInteface.sureAction(supplierName);
                    dismiss();
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 设置对话框窗体属性
     */
    private void setProperty() {
        setCanceledOnTouchOutside(false);// 对话框以外无法取消
        setCancelable(true);
        int h = this.context.getResources().getDisplayMetrics().heightPixels;
        int w = this.context.getResources().getDisplayMetrics().widthPixels;
        if (h < w) {
            w = h;
        }
        Window window = getWindow();// 　　　得到对话框的窗口．
        WindowManager.LayoutParams lp = window.getAttributes();
        // 中间
        lp.x = 0;// 这两句设置了对话框的位置．0为中间
        // 宽度
        lp.width = w * 5 / 6;// 对话框的宽 占屏幕比例的2/3
        // 高
        lp.height = h * 4 / 5;// 对话框的高包裹内容
        // 透明度
        // wl.alpha = 0.9f;// 这句设置了对话框的透明度
        // 布局相对位置
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (searchHistoryInteface != null) {
            searchHistoryInteface.itemSelectAction(position);
            dismiss();
        }
    }

    private class DemandingCertificatesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return message != null ? message.size() : 0;
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
            TextView textView = new TextView(context);
            DemandingCertificatesBeans.DemandingCertificates demandingCertificates = message.get(position);
            textView.setText(demandingCertificates.name);
            textView.setTextColor(context.getResources().getColor(R.color.white));
            textView.setPadding(DPtoPX(20, context), DPtoPX(10, context), DPtoPX(10, context), DPtoPX(10, context));
            return textView;
        }

        /**
         * 将dp类型的尺寸转换成px类型的尺寸
         *
         * @param size
         * @param context
         * @return
         */
        public int DPtoPX(int size, Context context) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
            return (int) ((float) size * metrics.density + 0.5);
        }
    }
}
