package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.AccountDetailNextBaseAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.PopGuiGeAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.AccountDetailBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyReciverEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 食品分类3级
 */
public class AccountDetailNextActivity extends Activity implements View.OnClickListener, AccountDetailNextBaseAdapter.AddCallBackListener, AccountDetailNextBaseAdapter.SubtractCallBackListener, AdapterView.OnItemClickListener, AccountDetailNextBaseAdapter.GuiGeCallBackListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv_right)
    ListView lvRight;
    @Bind(R.id.tv_right_two)
    TextView tvRightTwo;
    @Bind(R.id.tv_complete)
    TextView tvComplete;
    private ProgressDialog progressDialog;
    private AccountDetailNextBaseAdapter rightAdapter;
    //分类数据的集合
    private ArrayList<AccountDetailBean> rightList = new ArrayList<AccountDetailBean>();
    private String formatType;
    private String levelid;
    private PopupWindow popupWindow;

    //规格类型的数组
    private String[] unitType;
    //右边大ListVew选择规格的位置
    private int newPositon;
    //临时存储右边规格类型
    private ArrayList<String> rightTempGuiGeList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail_next);

        ButterKnife.bind(this);
        ivLeft.setOnClickListener(this);
        tvRightTwo.setOnClickListener(this);
        tvRightTwo.setVisibility(View.VISIBLE);
        tvComplete.setOnClickListener(this);
        title.setText("食品分类");
        tvRightTwo.setText("完成");
        progressDialog = new ProgressDialog(this);
        formatType = getIntent().getStringExtra("formatType");
        levelid = getIntent().getStringExtra("levelid");

        //获取分类数据
        getLeftList();

    }

    /**
     * 获取分类数据
     */
    private void getLeftList() {

        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("Format", Constants.AccountDetaiTools.typeid+"");
        params.put("Level", levelid);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetGoodsClassList5", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {

            case 0:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        JSONArray array = data.getJSONArray("ds");
                        JSONObject obj = null;
                        AccountDetailBean bean = null;
                        rightList.clear();
                        rightTempGuiGeList.clear();
                        for (int i = 0, count = array.length(); i < count; i++) {
                            bean = new AccountDetailBean();
                            obj = array.optJSONObject(i);
                            bean.setClick(obj.optBoolean("click"));
                            bean.setClassName(obj.optString("className"));
                            bean.setUnitType(obj.optString("unitType"));
                            bean.setNum(obj.optInt("num"));
                            bean.setClassID(obj.optInt("ClassID"));
                            bean.setId(obj.optInt("Id"));
                            bean.setUnitSelectType(obj.optString("unitType").split(",")[0]);
                            rightList.add(bean);
                            rightTempGuiGeList.add(obj.optString("unitType"));
                        }
                        //如果保存的数据包含了新获取的集合数据,回显该数据的数量
                        for (int i = 0; i < RepositoryBillBean.RepositoryBillList.size(); i++) {
                            String productName = RepositoryBillBean.RepositoryBillList.get(i).productName;
                            for (int f = 0; f < rightList.size(); f++) {
                                if (productName.equals(rightList.get(f).getClassName())) {
                                    //回显数量
                                    rightList.get(f).setGuigeNum(RepositoryBillBean.RepositoryBillList.get(i).productNum);
                                    //回显规格
                                    rightList.get(f).setUnitType(RepositoryBillBean.RepositoryBillList.get(i).productNnit);
                                }

                            }
                        }


                        //设置右边2级分类的ListView数据
//                        rightAdapter = new AccountDetailRightAdapter(this, rightList, R.layout.item_account_detail_right, this, this, this);
                        rightAdapter = new AccountDetailNextBaseAdapter(this,rightList, this, this, this);
                        lvRight.setAdapter(rightAdapter);
                        lvRight.setOnItemClickListener(this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_right_two:
            case R.id.tv_complete://保存数据并返回
                finish();
                break;
        }
    }


    //增加产品的数量
    @Override
    public void addNum(int position) {
        //获取ListView条目下的EditText
        MyReciverEditText et_last_num = (MyReciverEditText) lvRight.getChildAt(position-lvRight.getFirstVisiblePosition()).findViewById(R.id.et_last_num);
        //该EditText的值
        String addNum = et_last_num.getText().toString();
        if ("".equals(addNum)){
            addNum="0";
        }
        if (addNum.contains(".")) {
            if(!(addNum.substring(addNum.length()-1,addNum.length())).equals(".")) {
                BigDecimal b1 = new BigDecimal(addNum);
                et_last_num.setText(b1.add(new BigDecimal("1")).doubleValue() + "");
            }else {
                MyToastUtils.show(this,"请输入正确的数字");
            }
        } else {

            et_last_num.setText(Integer.parseInt(addNum) + 1 + "");
        }

    }

    //减少产品的数量
    @Override
    public void subtractNum(int position) {
        //获取ListView条目下的EditText
        MyReciverEditText et_last_num = (MyReciverEditText) lvRight.getChildAt(position-lvRight.getFirstVisiblePosition()).findViewById(R.id.et_last_num);
        //该EditText的值
        String subtractNum = et_last_num.getText().toString();
        if ("".equals(subtractNum)){
            subtractNum="0";
        }
        if (subtractNum.contains(".")) {

            if(!(subtractNum.substring(subtractNum.length()-1,subtractNum.length())).equals(".")){
                if (Double.parseDouble(subtractNum) >= 0 && Double.parseDouble(subtractNum) < 1) {
                    et_last_num.setText("");
                    et_last_num.setHint("0");
                } else {
                    BigDecimal b1 = new BigDecimal(subtractNum);
                    et_last_num.setText(b1.subtract(new BigDecimal("1")).doubleValue() + "");
                }
            }else {
                MyToastUtils.show(this,"请输入正确的数字");
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_right:

                //如果还有下一级
                Intent intent = new Intent();
                intent.putExtra("formatType", Constants.AccountDetaiTools.typeid+"");
                intent.putExtra("levelid", rightList.get(position).getId() + "");
                intent.setClass(this, AccountDetailThirdActivity.class);
                startActivity(intent);
                break;
            case R.id.lv_pop_list:
                TextView tv_action_type = (TextView) lvRight.getChildAt(newPositon-lvRight.getFirstVisiblePosition()).findViewById(R.id.tv_action_type);
                tv_action_type.setText(unitType[position]);
                //修改规格
                rightList.get(newPositon).setUnitSelectType(unitType[position]);
                rightAdapter.setUnit(rightList.get(newPositon).getClassName(),unitType[position]);
                popupWindow.dismiss();
                break;
        }

    }

    @Override
    public void selectGuiGe(RelativeLayout rl_select_unittype, int p) {
        this.newPositon = p;
        showUnitTypePopWindow(rl_select_unittype, p);
    }

    //选择规格
    private void showUnitTypePopWindow(RelativeLayout v, int p) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.unittype_pop_window, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, -2);
        ListView lv_pop_list = (ListView) view.findViewById(R.id.lv_pop_list);
        for (int i = 0; i < rightList.size(); i++) {
            if (p == i) {
                String Type = rightList.get(i).getUnitType();
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


}
