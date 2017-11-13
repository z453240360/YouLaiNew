package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.BaoZhiQiXianAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.BaoZhiQiXianOtherAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.datetimepick.DatePickerPopupUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.DialogSelectTimeInterface;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyListView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 保质期限和生产批号(本界面之所以用的两个ListView和两个集合的蠢办法来处理数据是为了不管跳到哪个界面都能正确的回显数据，试过很多办法都有BUG)
 */
public class QualityGuaranteeOrBatchNumActivity extends Activity implements DatePickerPopupUtil.DateCallBck, DialogSelectTimeInterface, View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv_qixian)
    ListView lvQixian;
    @Bind(R.id.bt_done)
    Button btDone;
    @Bind(R.id.tv_product_name)
    TextView tvProductName;
    @Bind(R.id.tv_select_time)
    TextView tvSelectTime;
    @Bind(R.id.tv_sure_time)
    TextView tvSureTime;
    @Bind(R.id.et_select_time)
    EditText etSelectTime;
    @Bind(R.id.ll_dialog_tip)
    LinearLayout llDialogTip;
    @Bind(R.id.tv_product_num)
    TextView tvProductNum;
    @Bind(R.id.tv_product_unit)
    TextView tvProductUnit;
    @Bind(R.id.tv_action_message)
    TextView tvActionMessage;
    @Bind(R.id.tv_shengchan_pihao)
    TextView tvShengchanPihao;
    @Bind(R.id.lv_qixianother)
    MyListView lvQixianother;
    private DatePickerPopupUtil datePickerPopupUtil;
    private TextView textViewAction;//当前点击的保质期限或生产批号
    private TextView textViewOtherAction;//当前点击的其他食品保质期限或生产批号
    private int selectPosition;//当前选择的条目的下标（保质期限或生产批号）
    private int selectOtherPosition;//当前选择的条目的下标（保质期限或生产批号）
    private ArrayList<RepositoryBillBean> mBillBeansBefore;//开始传入到该界面的集合
    private ArrayList<RepositoryBillBean> mBillBeansBeforeOther;//开始传入到该界面其他食品的集合
    /**
     * 判断点击的是明细还是其他食品的ListView,0是明细，1是其他食品的
     */
    private int ListType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_guarantee_or_batch_num);
        ButterKnife.bind(this);

        initView();
        initData();
        setData();

    }


    private void initView() {
        title.setText("保质期限");
        tvSelectTime.setOnClickListener(this);
        tvSureTime.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        btDone.setOnClickListener(this);
    }

    private void initData() {
//        Log.i("Home", RepositoryBillBean.RepositoryBillList+"%%%%%%");

        if (RepositoryBillBean.RepositoryBillList != null) {

            if (RepositoryBillBean.RepositoryBillList != null) {
                mBillBeansBefore = new ArrayList<>();
                for (RepositoryBillBean repositoryBillBean : RepositoryBillBean.RepositoryBillList) {
                    RepositoryBillBean repositoryBillBeanTemp = new RepositoryBillBean(repositoryBillBean.productName, repositoryBillBean.productNum,
                            repositoryBillBean.productNnit, repositoryBillBean.productTime, repositoryBillBean.productPiHao);
                    repositoryBillBeanTemp.productTempNum = repositoryBillBean.productTempNum;
                    mBillBeansBefore.add(repositoryBillBeanTemp);
                }
            }
        }

        if (RepositoryBillBean.OtherRepositoryBillList != null) {

            if (RepositoryBillBean.OtherRepositoryBillList != null) {
                mBillBeansBeforeOther = new ArrayList<>();
                for (RepositoryBillBean repositoryBillBean : RepositoryBillBean.OtherRepositoryBillList) {
                    RepositoryBillBean repositoryBillBeanTemp = new RepositoryBillBean(repositoryBillBean.productName, repositoryBillBean.productNum,
                            repositoryBillBean.productNnit, repositoryBillBean.productTime, repositoryBillBean.productPiHao);
                    repositoryBillBeanTemp.productTempNum = repositoryBillBean.productTempNum;
                    mBillBeansBeforeOther.add(repositoryBillBeanTemp);
                }
            }
        }
    }

    private void setData() {


//设普通明细数据
        datePickerPopupUtil = new DatePickerPopupUtil(getApplicationContext(), QualityGuaranteeOrBatchNumActivity.this);
        BaoZhiQiXianAdapter baoZhiQiXianAdapter = new BaoZhiQiXianAdapter(this, this);
        lvQixian.setAdapter(baoZhiQiXianAdapter);
        baoZhiQiXianAdapter.setData(mBillBeansBefore);
        baoZhiQiXianAdapter.notifyDataSetChanged();
//设置其他食品数据
        BaoZhiQiXianOtherAdapter otherAdapter = new BaoZhiQiXianOtherAdapter(this, this);
        lvQixianother.setAdapter(otherAdapter);
        otherAdapter.setData(mBillBeansBeforeOther);
        otherAdapter.notifyDataSetChanged();
    }


    @Override
    public void complete() {
        String time = datePickerPopupUtil.birthday;
        if (time != null) {
            tvSelectTime.setText(MyUtils.getDate(time, "yyyy年MM月dd日", "yyyy-MM-dd"));
            tvSelectTime.setTextColor(Color.BLACK);
        }
    }

    /**
     * 普通明细的选择
     * @param type 0:保质期限 ； 1：生产批号
     * @param textView
     * @param position
     */
    @Override
    public void selectTime(int type, TextView textView, int position) {
        ListType = 0;
        textViewAction = textView;
        selectPosition = position;
        showDialogTipAddTime();
        if (type == 0) {
            //保质期限
            shoWDialogText("保质期限", "请填写保质期限", RepositoryBillBean.RepositoryBillList.get(position).productPiHao);
            tvProductName.setText(RepositoryBillBean.RepositoryBillList.get(position).productName);
            tvProductNum.setText(RepositoryBillBean.RepositoryBillList.get(position).productNum);
            tvProductUnit.setText(RepositoryBillBean.RepositoryBillList.get(position).productNnit);
            tvSelectTime.setVisibility(View.VISIBLE);
            etSelectTime.setVisibility(View.GONE);
        } else if (type == 1) {
            //生产批号
            shoWDialogText("生产批号", "请填写生产批号", RepositoryBillBean.RepositoryBillList.get(position).productTime);
            tvProductName.setText(RepositoryBillBean.RepositoryBillList.get(position).productName);
            tvProductNum.setText(RepositoryBillBean.RepositoryBillList.get(position).productNum);
            tvProductUnit.setText(RepositoryBillBean.RepositoryBillList.get(position).productNnit);
            tvSelectTime.setVisibility(View.GONE);
            etSelectTime.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 其他食品明细的选择
     * @param type
     * @param textView
     * @param position
     */
    @Override
    public void selectOtherTime(int type, TextView textView, int position) {
        textViewOtherAction = textView;
        ListType = 1;
        selectOtherPosition = position;
        showDialogTipAddTime();
        if (type == 0) {
            //保质期限
            shoWDialogText("保质期限", "请填写保质期限", RepositoryBillBean.OtherRepositoryBillList.get(position).productPiHao);
            tvProductName.setText(RepositoryBillBean.OtherRepositoryBillList.get(position).productName);
            tvProductNum.setText(RepositoryBillBean.OtherRepositoryBillList.get(position).productNum);
            tvProductUnit.setText(RepositoryBillBean.OtherRepositoryBillList.get(position).productNnit);
            tvSelectTime.setVisibility(View.VISIBLE);
            etSelectTime.setVisibility(View.GONE);
        } else if (type == 1) {
            //生产批号
            shoWDialogText("生产批号", "请填写生产批号", RepositoryBillBean.OtherRepositoryBillList.get(position).productTime);
            tvProductName.setText(RepositoryBillBean.OtherRepositoryBillList.get(position).productName);
            tvProductNum.setText(RepositoryBillBean.OtherRepositoryBillList.get(position).productNum);
            tvProductUnit.setText(RepositoryBillBean.OtherRepositoryBillList.get(position).productNnit);
            tvSelectTime.setVisibility(View.GONE);
            etSelectTime.setVisibility(View.VISIBLE);
        }
    }

    private void shoWDialogText(String actionMessage, String selectTime, String shengchanPihao) {
        tvActionMessage.setText(actionMessage);
        tvShengchanPihao.setText(shengchanPihao);
    }

    //显示保质期限和生产批号的对话框
    public void showDialogTipAddTime() {
        llDialogTip.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_time://选择保质期限
                Calendar c = Calendar.getInstance();
                datePickerPopupUtil.setCurrent(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerPopupUtil.getDatePicker().showAtLocation(
                        tvSelectTime,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_sure_time://选择时间确定或输入生产批号确定
                if (View.VISIBLE == tvSelectTime.getVisibility()) {

                    if (!"请填写保质期限".equals(tvSelectTime.getText())) {

                        if (ListType==0) {//设置普通明细的保质期或批号
                            textViewAction.setText(tvSelectTime.getText());
                            textViewAction.setTextColor(Color.WHITE);
                            if (mBillBeansBefore != null) {
                                mBillBeansBefore.get(selectPosition).productTime = tvSelectTime.getText().toString();

                            }
                        }else {
                            textViewOtherAction.setText(tvSelectTime.getText());
                            textViewOtherAction.setTextColor(Color.WHITE);
                            if (mBillBeansBeforeOther != null) {
                                mBillBeansBeforeOther.get(selectOtherPosition).productTime = tvSelectTime.getText().toString();

                            }
                        }
                    }
                } else {//设置其他食品的保质期或批号
                    if (!TextUtils.isEmpty(etSelectTime.getText().toString()) && !"".equals(etSelectTime.getText().toString().trim())) {

                        if (ListType==0) {
                            textViewAction.setText(etSelectTime.getText());
                            textViewAction.setTextColor(Color.WHITE);
                            if (mBillBeansBefore != null) {
                                mBillBeansBefore.get(selectPosition).productPiHao = etSelectTime.getText().toString();
                            }
                        }else {
                            textViewOtherAction.setText(etSelectTime.getText());
                            textViewOtherAction.setTextColor(Color.WHITE);
                            if (mBillBeansBeforeOther != null) {
                                mBillBeansBeforeOther.get(selectOtherPosition).productPiHao = etSelectTime.getText().toString();

                            }
                        }
                    }
                }

                llDialogTip.setVisibility(View.GONE);
                break;
            case R.id.iv_left:
            case R.id.bt_done:
                llDialogTip.setVisibility(View.GONE);
                //把普通明细数据保存起来用于回显和提交
                if (RepositoryBillBean.RepositoryBillList != null) {
                    RepositoryBillBean.RepositoryBillList.clear();
                    RepositoryBillBean.RepositoryBillList.addAll(mBillBeansBefore);
                }
                //把其他食品数据保存起来用于回显和提交
                if (RepositoryBillBean.OtherRepositoryBillList != null) {
                    RepositoryBillBean.OtherRepositoryBillList.clear();
                    RepositoryBillBean.OtherRepositoryBillList.addAll(mBillBeansBeforeOther);
                }

                finish();
                break;
        }
    }
}
