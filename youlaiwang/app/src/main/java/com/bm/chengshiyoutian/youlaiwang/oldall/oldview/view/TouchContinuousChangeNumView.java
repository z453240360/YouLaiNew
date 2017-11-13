package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;

import java.math.BigDecimal;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/24 17:41
 *
 * @Description: 触摸连续改变数据, (可用于更改购物车数据)
 */
public class TouchContinuousChangeNumView extends LinearLayout implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    private Context mContext;
    private Context myContext;
    private ImageView mIv_bill_data_jian;
    private TextView mTv_bill_data;
    private ImageView mIv_bill_data_jia;

    private ChangeProductNumInterface changeProductNumInterface;
    private RepositoryBillBean repositoryBillBean;
    private int changeNum;
    private Handler mHandler = new Handler();
    Runnable test = new Runnable() {

        @Override
        public void run() {// 主线程
            // 过50m秒钟再执行run()方法
            if (changeNum == 0) {
                changeGoodsNum(0, "");
            } else if (changeNum == 1) {
                changeGoodsNum(1, "");
            }
            mHandler.postDelayed(this, 20);
        }
    };
    private double inputNumDou;

    public TouchContinuousChangeNumView(Context context) {
        super(context);
        mContext = context;
        initView();
        setListener();
    }

    public TouchContinuousChangeNumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        setListener();
    }

    public TouchContinuousChangeNumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        setListener();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.touch_continuous_change_num_view, null);
        addView(view);
        mIv_bill_data_jian = (ImageView) view.findViewById(R.id.iv_bill_data_jian);
        mTv_bill_data = (TextView) view.findViewById(R.id.tv_bill_data);
        mIv_bill_data_jia = (ImageView) view.findViewById(R.id.iv_bill_data_jia);
    }

    public void setChangeProductNumInterface(ChangeProductNumInterface changeProductNumInterface) {
        this.changeProductNumInterface = changeProductNumInterface;
    }

    public void setContext(Context context) {
        this.myContext = context;
    }

    public void setDataNum(RepositoryBillBean repositoryBillBean) {
        this.repositoryBillBean = repositoryBillBean;
        if (repositoryBillBean != null && !TextUtils.isEmpty(repositoryBillBean.productNum)) {
            mTv_bill_data.setText(repositoryBillBean.productNum);
        } else {
            mTv_bill_data.setText("0");
        }
    }

    private void setListener() {
        mIv_bill_data_jian.setOnClickListener(this);
        mIv_bill_data_jian.setOnLongClickListener(this);
        mIv_bill_data_jian.setOnTouchListener(this);

        mIv_bill_data_jia.setOnClickListener(this);
        mIv_bill_data_jia.setOnLongClickListener(this);
        mIv_bill_data_jia.setOnTouchListener(this);

        mTv_bill_data.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                mHandler.removeCallbacks(test);
                break;
        }
        return false;
    }

    private void AutomaticCarousel() {
        // 延时50ms调用该方法
        mHandler.postDelayed(test, 50);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bill_data_jian:
                changeNum = 0;
                break;
            case R.id.iv_bill_data_jia:
                changeNum = 1;
                break;
        }
        AutomaticCarousel();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bill_data_jian:
                changeGoodsNum(0, "");
                break;
            case R.id.iv_bill_data_jia:
                changeGoodsNum(1, "");
                break;
            case R.id.tv_bill_data://点击中间的数据弹出对话框
                final DialogChangeNum dialogChangeNum = new DialogChangeNum(myContext, repositoryBillBean);
                dialogChangeNum.setChangeNumListener(new DialogChangeNum.ChangeNumListener() {
                    @Override
                    public void changeNum(String number) {

                      setGoodsNum(number);

                        dialogChangeNum.dismiss();
                    }
                });
                dialogChangeNum.show();
                break;
        }
    }

    private void changeGoodsNum(int type, String inputNum) {
        String goodsNum = mTv_bill_data.getText().toString();
        if (TextUtils.isEmpty(goodsNum) || "".equals(goodsNum)) {
            goodsNum = "1";
        }

        if (type == 0) {

            if (!MyUtils.isEmpty(goodsNum)) {
                if (goodsNum.contains(".")) {//小数


                    if (repositoryBillBean != null && !TextUtils.isEmpty(repositoryBillBean.productTempNum)) {
                        double productTempNumDou = Double.parseDouble(repositoryBillBean.productTempNum);
                        if (Double.parseDouble(goodsNum) >= productTempNumDou) {
                            mTv_bill_data.setText(String.valueOf(productTempNumDou - 1));
                            MyToastUtils.show(mContext, mContext.getResources().getString(R.string.zui_da_ku_chun));
                        } else {
                            if (Double.parseDouble(goodsNum)<1){
                                mTv_bill_data.setText("0");
                            }else {
                                BigDecimal b1 = new BigDecimal(goodsNum);
                                mTv_bill_data.setText((b1.subtract(new BigDecimal("1")).doubleValue())+"");
                            }
                        }
                    } else {
                        if (Double.parseDouble(goodsNum)<1){
                            mTv_bill_data.setText("0");
                        }else {
                            BigDecimal b1 = new BigDecimal(goodsNum);
                            mTv_bill_data.setText((b1.subtract(new BigDecimal("1")).doubleValue())+"");
                        }
                    }


                } else {//整数
                    if (repositoryBillBean != null && !TextUtils.isEmpty(repositoryBillBean.productTempNum)) {
                        int productTempNum = Integer.parseInt(repositoryBillBean.productTempNum);
                        if (Integer.parseInt(goodsNum) >= productTempNum) {
                            mTv_bill_data.setText(String.valueOf(productTempNum - 1));
                            MyToastUtils.show(mContext, mContext.getResources().getString(R.string.zui_da_ku_chun));
                        } else {
                            if (Integer.parseInt(goodsNum)<1){
                                mTv_bill_data.setText("0");
                            }else {
                                mTv_bill_data.setText(String.valueOf(Integer.parseInt(goodsNum) - 1));
                            }
                        }
                    } else {
                        if (Integer.parseInt(goodsNum)<1){
                            mTv_bill_data.setText("0");
                        }else {
                            mTv_bill_data.setText(String.valueOf(Integer.parseInt(goodsNum) - 1));
                        }
                    }

                }
            }

            } else if (type == 1) {
                if (!MyUtils.isEmpty(goodsNum)) {
                    if (goodsNum.contains(".")) {//小数


                            if (repositoryBillBean != null && !TextUtils.isEmpty(repositoryBillBean.productTempNum)) {
                                double productTempNumDou = Double.parseDouble(repositoryBillBean.productTempNum);
                                if (Double.parseDouble(goodsNum) >= productTempNumDou) {
                                    mTv_bill_data.setText(String.valueOf(productTempNumDou));
                                    MyToastUtils.show(mContext, mContext.getResources().getString(R.string.zui_da_ku_chun));
                                } else {
                                    BigDecimal b1 = new BigDecimal(goodsNum);
                                    mTv_bill_data.setText((b1.add(new BigDecimal("1")).doubleValue())+"");
                                }
                            } else {

                                BigDecimal b1 = new BigDecimal(goodsNum);
                                mTv_bill_data.setText((b1.add(new BigDecimal("1")).doubleValue())+"");mTv_bill_data.setText(String.valueOf(Double.parseDouble(goodsNum) + 1));
                            }


                    } else {//整数
                        if (repositoryBillBean != null && !TextUtils.isEmpty(repositoryBillBean.productTempNum)) {
                            int productTempNum = Integer.parseInt(repositoryBillBean.productTempNum);
                            if (Integer.parseInt(goodsNum) >= productTempNum) {
                                mTv_bill_data.setText(String.valueOf(productTempNum));
                                MyToastUtils.show(mContext, mContext.getResources().getString(R.string.zui_da_ku_chun));
                            } else {
                                mTv_bill_data.setText(String.valueOf(Integer.parseInt(goodsNum) + 1));
                            }
                        } else {
                            mTv_bill_data.setText(String.valueOf(Integer.parseInt(goodsNum) + 1));
                        }

                    }
                }


        }


        if (changeProductNumInterface != null) {
            changeProductNumInterface.changeNum(mTv_bill_data);
        }
    }

    public interface ChangeProductNumInterface {
        void changeNum(TextView mTv_bill_data);
    }



    private void setGoodsNum(String inputNum) {
        String goodsNum = mTv_bill_data.getText().toString();
        if (TextUtils.isEmpty(goodsNum) || "".equals(goodsNum)) {
            goodsNum = "1";
        }

        double inputNumDou = 0.0;
        int goodsNumInt = 0;
            if (!MyUtils.isEmpty(inputNum)) {

                if (inputNum.contains(".")){
                    if(!(inputNum.substring(inputNum.length()-1,inputNum.length())).equals(".")) {
                        inputNumDou = Double.parseDouble(inputNum);
                    }else {
                        MyToastUtils.show(mContext,"请输入正确的数字");
                    }
                }else {
                    goodsNumInt = Integer.parseInt(inputNum);
                }

            } else {
                goodsNumInt = 0;
            }



        if (repositoryBillBean != null && !TextUtils.isEmpty(repositoryBillBean.productTempNum)) {

            if (repositoryBillBean.productTempNum.contains(".")){
                double productTempNumDou = Double.parseDouble(repositoryBillBean.productTempNum);
                if (inputNumDou >= productTempNumDou) {
                    mTv_bill_data.setText(String.valueOf(productTempNumDou));
                    MyToastUtils.show(mContext, mContext.getResources().getString(R.string.zui_da_ku_chun));
                } else {
                    mTv_bill_data.setText(String.valueOf(inputNumDou));
                }

            }else {
                int productTempNum = Integer.parseInt(repositoryBillBean.productTempNum);//产品总数
                if (goodsNumInt >= productTempNum) {
                    mTv_bill_data.setText(String.valueOf(productTempNum));
                    MyToastUtils.show(mContext, mContext.getResources().getString(R.string.zui_da_ku_chun));
                } else {
                    mTv_bill_data.setText(String.valueOf(goodsNumInt));
                }

            }


        } else {
            if (inputNum.contains(".")){
                mTv_bill_data.setText(String.valueOf(inputNumDou));
            }else {
                mTv_bill_data.setText(String.valueOf(goodsNumInt));
            }

        }



        if (changeProductNumInterface != null) {
            changeProductNumInterface.changeNum(mTv_bill_data);
        }
    }

}
