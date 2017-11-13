package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.AccountDetailNextBaseAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.AccountDetailOtherBaseAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.PopGuiGeAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.AccountDetailBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyListView;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyReciverEditText;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 申报清单（本界面之所以用的两个ListView和两个集合的蠢办法来处理数据是为了不管跳到哪个界面都能正确的回显数据，试过很多其他办法都有BUG）
 */
public class DeclareInventoryActivity extends AppCompatActivity implements View.OnClickListener, AccountDetailNextBaseAdapter.GuiGeCallBackListener, AdapterView.OnItemClickListener, AccountDetailNextBaseAdapter.AddCallBackListener, AccountDetailNextBaseAdapter.SubtractCallBackListener, AccountDetailOtherBaseAdapter.AddCallBackOtherListener, AccountDetailOtherBaseAdapter.SubtractCallBackOtherListener, AccountDetailOtherBaseAdapter.GuiGeCallBackOtherListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right_two)
    TextView tvRightTwo;
    @Bind(R.id.lv_list)
    MyListView lvList;
    @Bind(R.id.tv_complete)
    TextView tvComplete;
    @Bind(R.id.lv_list_other)
    MyListView lvListOther;

    private String[] unitType;
    private PopupWindow popupWindow;
    /**
     * 常规临时数据
     */
    private List<AccountDetailBean> declareList = new ArrayList<AccountDetailBean>();
    /**
     * 其他食品临时数据
     */
    private List<AccountDetailBean> declareOtherList = new ArrayList<AccountDetailBean>();
    private AccountDetailNextBaseAdapter declareAdapter;
    private AccountDetailOtherBaseAdapter otherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_inventory);

        ButterKnife.bind(this);
        ivLeft.setOnClickListener(this);
        tvRightTwo.setOnClickListener(this);
        tvComplete.setOnClickListener(this);
        tvRightTwo.setVisibility(View.VISIBLE);
        title.setText("申报清单");
        tvRightTwo.setText("完成");

        setDeclareInfo();
    }

    /**
     * 申报清单数据
     */
    private void setDeclareInfo() {

        if (RepositoryBillBean.RepositoryBillList.size() > 0) {
            AccountDetailBean bean = null;
            for (int i = 0; i < RepositoryBillBean.RepositoryBillList.size(); i++) {
                bean = new AccountDetailBean();
                bean.setGuigeNum(RepositoryBillBean.RepositoryBillList.get(i).productNum);
                bean.setUnitSelectType(RepositoryBillBean.RepositoryBillList.get(i).productNnit);
                bean.setClassName(RepositoryBillBean.RepositoryBillList.get(i).productName);
                bean.setUnitType(RepositoryBillBean.RepositoryBillList.get(i).productAllNnit);
                declareList.add(bean);
            }
        }

        //常规食品明细
        declareAdapter = new AccountDetailNextBaseAdapter(this, declareList, this, this, this);
        lvList.setAdapter(declareAdapter);


        if (RepositoryBillBean.OtherRepositoryBillList.size() > 0) {
            AccountDetailBean bean = null;
            for (int i = 0; i < RepositoryBillBean.OtherRepositoryBillList.size(); i++) {
                bean = new AccountDetailBean();
                bean.setGuigeNum(RepositoryBillBean.OtherRepositoryBillList.get(i).productNum);
                bean.setUnitSelectType(RepositoryBillBean.OtherRepositoryBillList.get(i).productNnit);
                bean.setClassName(RepositoryBillBean.OtherRepositoryBillList.get(i).productName);
                bean.setUnitType(RepositoryBillBean.OtherRepositoryBillList.get(i).productAllNnit);
                declareOtherList.add(bean);
            }
        }

        //其他食品
        otherAdapter = new AccountDetailOtherBaseAdapter(this, declareOtherList, this, this, this);
        lvListOther.setAdapter(otherAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:

                finish();
                break;
            case R.id.tv_complete:// 完成=================================================
            case R.id.tv_right_two://完成
                declareList.clear();
                declareOtherList.clear();
                startActivity(new Intent(this, DeclareConfirmActivity.class));
//                finish();
                break;
        }
    }


    @Override
    public void selectGuiGe(RelativeLayout rl_select_unittype, int p) {
        showUnitTypePopWindow(rl_select_unittype, p);
    }

    private int newPositon;

    //选择常规食品明细规格
    private void showUnitTypePopWindow(RelativeLayout v, int p) {
        this.newPositon = p;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.unittype_pop_window, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, -2);
        ListView lv_pop_list = (ListView) view.findViewById(R.id.lv_pop_list);

        for (int i = 0; i < declareList.size(); i++) {
            if (p == i) {
                String Type = declareList.get(i).getUnitType();
                unitType = Type.split(",");
            }
        }
        PopGuiGeAdapter adapter = new PopGuiGeAdapter(this, unitType);
        lv_pop_list.setAdapter(adapter);
        lv_pop_list.setOnItemClickListener(this);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        //显示(在该控件下面)
        popupWindow.showAsDropDown(v);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv_action_type = (TextView) lvList.getChildAt(newPositon).findViewById(R.id.tv_action_type);
        tv_action_type.setText(unitType[position]);
        //修改规格
        declareList.get(newPositon).setUnitSelectType(unitType[position]);
        declareAdapter.setUnit(declareList.get(newPositon).getClassName(), unitType[position]);
        popupWindow.dismiss();
    }

    //增加常规食品明细
    @Override
    public void addNum(int position) {
        //获取ListView条目下的EditText
        MyReciverEditText et_last_num = (MyReciverEditText) lvList.getChildAt(position).findViewById(R.id.et_last_num);
        //该EditText的值
        String addNum = et_last_num.getText().toString();
        if ("".equals(addNum)) {
            addNum = "0";
        }
        if (addNum.contains(".")) {
            if (!(addNum.substring(addNum.length() - 1, addNum.length())).equals(".")) {
                BigDecimal b1 = new BigDecimal(addNum);
                et_last_num.setText(b1.add(new BigDecimal("1")).doubleValue() + "");
            } else {
                MyToastUtils.show(this, "请输入正确的数字");
            }
        } else {

            et_last_num.setText(Integer.parseInt(addNum) + 1 + "");
        }
    }

    //减少常规食品明细
    @Override
    public void subtractNum(int position) {
//获取ListView条目下的EditText
        MyReciverEditText et_last_num = (MyReciverEditText) lvList.getChildAt(position).findViewById(R.id.et_last_num);
        //该EditText的值
        String subtractNum = et_last_num.getText().toString();
        if ("".equals(subtractNum)) {
            subtractNum = "0";
        }
        if (subtractNum.contains(".")) {

            if (!(subtractNum.substring(subtractNum.length() - 1, subtractNum.length())).equals(".")) {
                if (Double.parseDouble(subtractNum) >= 0 && Double.parseDouble(subtractNum) < 1) {
                    et_last_num.setText("");
                    et_last_num.setHint("0");
                } else {
                    BigDecimal b1 = new BigDecimal(subtractNum);
                    et_last_num.setText(b1.subtract(new BigDecimal("1")).doubleValue() + "");
                }
            } else {
                MyToastUtils.show(this, "请输入正确的数字");
            }

        } else {
            if (Integer.parseInt(subtractNum) <= 0) {
                et_last_num.setText("");
                et_last_num.setHint("0");
            } else {
                et_last_num.setText(Integer.parseInt(subtractNum) - 1 + "");
            }
        }
    }


    //增加其他食品数量
    @Override
    public void addOtherNum(int p) {
        //获取ListView条目下的EditText
        MyReciverEditText et_last_num = (MyReciverEditText) lvListOther.getChildAt(p).findViewById(R.id.et_last_num);
        //该EditText的值
        String addNum = et_last_num.getText().toString();
        if ("".equals(addNum)) {
            addNum = "0";
        }
        if (addNum.contains(".")) {
            if (!(addNum.substring(addNum.length() - 1, addNum.length())).equals(".")) {
                BigDecimal b1 = new BigDecimal(addNum);
                et_last_num.setText(b1.add(new BigDecimal("1")).doubleValue() + "");
            } else {
                MyToastUtils.show(this, "请输入正确的数字");
            }
        } else {

            et_last_num.setText(Integer.parseInt(addNum) + 1 + "");
        }
    }

    //减少其他食品数量
    @Override
    public void subtractOtherNum(int p) {
        MyReciverEditText et_last_num = (MyReciverEditText) lvListOther.getChildAt(p).findViewById(R.id.et_last_num);
        //该EditText的值
        String subtractNum = et_last_num.getText().toString();
        if ("".equals(subtractNum)) {
            subtractNum = "0";
        }
        if (subtractNum.contains(".")) {

            if (!(subtractNum.substring(subtractNum.length() - 1, subtractNum.length())).equals(".")) {
                if (Double.parseDouble(subtractNum) >= 0 && Double.parseDouble(subtractNum) < 1) {
                    et_last_num.setText("");
                    et_last_num.setHint("0");
                } else {
                    BigDecimal b1 = new BigDecimal(subtractNum);
                    et_last_num.setText(b1.subtract(new BigDecimal("1")).doubleValue() + "");
                }
            } else {
                MyToastUtils.show(this, "请输入正确的数字");
            }

        } else {
            if (Integer.parseInt(subtractNum) <= 0) {
                et_last_num.setText("");
                et_last_num.setHint("0");
            } else {
                et_last_num.setText(Integer.parseInt(subtractNum) - 1 + "");
            }
        }
    }

    //选择其他食品规格
    @Override
    public void selectOtherGuiGe(RelativeLayout r, int p) {

    }
}
