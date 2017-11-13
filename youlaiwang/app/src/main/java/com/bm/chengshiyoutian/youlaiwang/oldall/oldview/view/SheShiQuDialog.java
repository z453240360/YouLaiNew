package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.bm.chengshiyoutian.youlaiwang.R;

import java.util.List;

/**
 * 省市区对话框
 * Created by wujm on 2016/1/21.
 */
public class SheShiQuDialog extends Dialog {

    private Context context;

    public SheShiQuDialog(Context context) {
        super(context, R.style.Theme_MyDialog_Shape_White_Fillet_No_Radius);
        this.context = context;
        // 加载布局
        setContentView(R.layout.popup_city);
        // 获取控件
        findViewById();
//        setOnClick();
        // 设置对话框窗体属性
        setProperty(this.context);
    }

    private WheelView wvShen;
    private WheelView wvShi;
    private WheelView wvQu;
    private Button btnSubmit;
    private Button btnCancel;

    public void setQus(List<String> qus) {
        wvQu.setOffset(1);
        wvQu.setItems(qus);
    }

    public int[] getSeletedIndexArr() {
        int shenSeletedIndex= wvShen.getSeletedIndex()+1;
        int shiSeletedIndex= wvShi.getSeletedIndex()+1;
        int quSeletedIndex= wvQu.getSeletedIndex()+1;
        int shenOffsetIndex=wvShen.getOffset();
        int shiOffsetIndex=wvShi.getOffset();
        int quOffsetIndex=wvQu.getOffset();
        int[]seletedIndexArr=new int[]{shenSeletedIndex,shiSeletedIndex,quSeletedIndex};
        return seletedIndexArr;
    }
    public void setShis(List<String> shiStrs) {
        wvShi.setOffset(1);
        wvShi.setItems(shiStrs);
    }


    private void findViewById() {
        wvShen = (WheelView) findViewById(R.id.wv_shen);

        wvShi = (WheelView) findViewById(R.id.wv_shi);
        wvQu = (WheelView) findViewById(R.id.wv_qu);

        // -----确定和取消按钮
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
//        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = (Button) findViewById(R.id.btnCancel);

    }

    public void setConfirmOnClickListener(View.OnClickListener listener) {
        btnSubmit.setOnClickListener(listener);
    }

    public void setCancelOnClickListener(View.OnClickListener listener) {
        btnCancel.setOnClickListener(listener);
    }

    public void setSheOnWheelViewListener(WheelView.OnWheelViewListener onWheelViewListener) {
        wvShen.setOnWheelViewListener(onWheelViewListener);
    }

    public void setShiOnWheelViewListener(WheelView.OnWheelViewListener onWheelViewListener) {
        wvShi.setOnWheelViewListener(onWheelViewListener);
    }

    private void setOnClick() {

    }

    public void setShens(List<String> sheStrs) {
        wvShen.setOffset(1);
        wvShen.setItems(sheStrs);
    }

    /**
     * 设置对话框窗体属性
     *
     * @param context 上下文
     */
    private void setProperty(Context context) {
        // setCanceledOnTouchOutside(false);// 对话框以外无法取消
        setCancelable(true);
        int h = context.getResources().getDisplayMetrics().heightPixels;
        int w = context.getResources().getDisplayMetrics().widthPixels;
        if (h < w) {
            w = h;
        }
        Window window = getWindow();// 　　　得到对话框的窗口．
        WindowManager.LayoutParams lp = window.getAttributes();
        // 中间
        // lp.x = -vWidth;// 这两句设置了对话框的位置．0为中间
        // lp.y = vheight;// 这两句设置了对话框的位置．0为中间//-(292 - 45)=-247
        // 宽度
        // lp.width = lvW;// 对话框的宽 占屏幕比例的2/3
//        lp.width = w * 4 / 5;// 对话框的宽 占屏幕比例的4/5
        lp.width = ViewGroup.LayoutParams.FILL_PARENT;// 对话框的宽 占屏幕比例的2/3
        // 高
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;// 对话框的高包裹内容
        // 透明度
        // lp.alpha = 0.9f;// 这句设置了对话框的透明度
        // 布局相对位置
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }

}