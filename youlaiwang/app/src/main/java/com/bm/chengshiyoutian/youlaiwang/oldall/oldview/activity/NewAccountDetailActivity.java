package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.AccountDetailLeftAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.AccountDetailRightBaseAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.PopGuiGeAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.AccountDetailBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyReciverEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 台账明细
 */
public class NewAccountDetailActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, AccountDetailRightBaseAdapter.AddCallBackListener, AccountDetailRightBaseAdapter.SubtractCallBackListener, AccountDetailRightBaseAdapter.GuiGeCallBackListener {

    @Bind(R.id.lv_left)
    ListView lvLeft;
    @Bind(R.id.lv_right)
    ListView lvRight;
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rl_other)
    RelativeLayout rlOther;
    @Bind(R.id.bt_complete)
    Button btComplete;
    @Bind(R.id.tv_declare_inventory)
    TextView tvDeclareInventory;
    @Bind(R.id.rl_quality)
    RelativeLayout rlQuality;
    @Bind(R.id.tv_other_food_num)
    TextView tvOtherFoodNum;
    @Bind(R.id.tv_shenbao_num)
    TextView tvShenbaoNum;
    @Bind(R.id.tv_zhong)
    TextView tvZhong;
    @Bind(R.id.rl_declare_inventory)
    RelativeLayout rlDeclareInventory;
    private ProgressDialog progressDialog;
    private boolean Firstdialog = true;

    //level：父节点ID，默认一级为0
    private String levelId = "0";

    private AccountDetailLeftAdapter leftadapter;

    private int keys = 0;

    //右边2级分类数据的集合
    private ArrayList<AccountDetailBean> rightList = new ArrayList<AccountDetailBean>();
    //临时存储右边规格类型
    private ArrayList<String> rightTempGuiGeList = new ArrayList<String>();

    private AccountDetailRightBaseAdapter rightAdapter;
    private ArrayList<AccountDetailBean> leftList;


    //规格类型的数组
    private String[] unitType;

    //选择规格的POPWndow
    private PopupWindow popupWindow;
    //    private List<AccountDetailBean> rightList;
//右边大ListVew选择规格的位置
    private int newPositon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_detail);
        ButterKnife.bind(this);
        initListener();

        title.setText("台账明细");
        progressDialog = new ProgressDialog(this);
        //获取左边1级数据
        getLeftList(keys, levelId);

    }



    @Override
    protected void onResume() {
        super.onResume();

        //其他食品的数量
        tvOtherFoodNum.setText(RepositoryBillBean.OtherRepositoryBillList.size() + "种");
        tvShenbaoNum.setText(RepositoryBillBean.OtherRepositoryBillList.size() + RepositoryBillBean.RepositoryBillList.size() + "");
    }

    private void initListener() {
        ivLeft.setOnClickListener(this);
        rlOther.setOnClickListener(this);
        btComplete.setOnClickListener(this);
        tvDeclareInventory.setOnClickListener(this);
        rlQuality.setOnClickListener(this);
        tvZhong.setOnClickListener(this);
        tvShenbaoNum.setOnClickListener(this);
        rlDeclareInventory.setOnClickListener(this);

    }


    /**
     * 获取左边1级数据
     */
    private void getLeftList(int keys, String levelid) {

        if (progressDialog != null && Firstdialog == true) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("Format", Constants.AccountDetaiTools.typeid + "");
        params.put("Level", levelid);
        InternetConfig config = new InternetConfig();
        config.setKey(keys);
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
                        Firstdialog = false;
                        JSONObject data = jsonObject.optJSONObject("data");
                        String ds = data.optString("ds");

                        Gson gson = new Gson();
                        Type lt = new TypeToken<ArrayList<AccountDetailBean>>() {
                        }.getType();

                        leftList = gson.fromJson(ds, lt);
                        //左边1级分类的适配器加载数据
                        leftadapter = new AccountDetailLeftAdapter(this, leftList, R.layout.item_account_detail_left);
                        lvLeft.setAdapter(leftadapter);
                        lvLeft.setOnItemClickListener(this);

                        //默认加载第0个1级分类下的2级分类
                        getLeftList(1, leftList.get(0).getId() + "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
//                        Log.i("Home",jsonObject+"");
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
                                    rightList.get(f).setUnitSelectType(RepositoryBillBean.RepositoryBillList.get(i).productNnit);
                                }

                            }
                        }

                        //设置右边2级分类的ListView数据
                        rightAdapter = new AccountDetailRightBaseAdapter(this,rightList, this, this, this,tvShenbaoNum);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_left://左边ListView的点击事件
                Firstdialog = true;
                leftadapter.setSelectedPosition(position);
                leftadapter.notifyDataSetChanged();
                //加载哪个1级分类下面的分类就传该1级分类的ID
                getLeftList(1, leftList.get(position).getId() + "");
                break;
            case R.id.lv_right://左边ListView的点击事件
                //如果还有下一级
                Intent intent = new Intent();
                intent.putExtra("formatType", Constants.AccountDetaiTools.typeid + "");
                intent.putExtra("levelid", rightList.get(position).getId() + "");
                intent.setClass(this, AccountDetailNextActivity.class);
                startActivity(intent);
                break;
            case R.id.lv_pop_list:
                TextView tv_action_type = (TextView) lvRight.getChildAt(newPositon - lvRight.getFirstVisiblePosition()).findViewById(R.id.tv_action_type);
                tv_action_type.setText(unitType[position]);

                //修改规格
                rightList.get(newPositon).setUnitSelectType(unitType[position]);
                rightAdapter.setUnit(rightList.get(newPositon).getClassName(),unitType[position]);
                popupWindow.dismiss();
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.rl_other://其他食品分类
                Intent intent = new Intent();
                intent.setClass(this, OtherFoodsLevelActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_complete://完成======================================清空列表数据？？？
                rightList.clear();
                rightTempGuiGeList.clear();


            case R.id.tv_declare_inventory://申报清单
            case R.id.tv_shenbao_num:
            case R.id.tv_zhong:
            case R.id.rl_declare_inventory:

//        if (RepositoryBillBean.RepositoryBillList.size()==0&&RepositoryBillBean.OtherRepositoryBillList.size()==0){
//                MyToastUtils.show(this,"请填写申报信息");
//        }else {
            Intent intent3 = new Intent();
            intent3.setClass(this, DeclareInventoryActivity.class);

            startActivity(intent3);
//                finish();
//        }
                break;
            case R.id.rl_quality://保质期限
                Intent intent4 = new Intent();
                intent4.setClass(this, QualityGuaranteeOrBatchNumActivity.class);
                startActivity(intent4);
                break;

        }

    }


    //增加产品的数量
    @Override
    public void addNum(int position) {
        //获取ListView条目下的EditText
        MyReciverEditText et_last_num = (MyReciverEditText) lvRight.getChildAt(position - lvRight.getFirstVisiblePosition()).findViewById(R.id.et_last_num);
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
        MyReciverEditText et_last_num = (MyReciverEditText) lvRight.getChildAt(position - lvRight.getFirstVisiblePosition()).findViewById(R.id.et_last_num);
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


    //选择规格
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
        for (int i = 0; i < rightTempGuiGeList.size(); i++) {
            if (p == i) {
                String Type = rightTempGuiGeList.get(i);
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
    protected void onDestroy() {
        super.onDestroy();

        if (RepositoryBillBean.RepositoryBillList != null) {
            RepositoryBillBean.RepositoryBillList.clear();
        }
        if (RepositoryBillBean.OtherRepositoryBillList != null) {
            RepositoryBillBean.OtherRepositoryBillList.clear();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //如果是保存的数据包含了新获取的集合数据,回显该数据的数量
        for (int i = 0; i < RepositoryBillBean.RepositoryBillList.size(); i++) {
            String productName = RepositoryBillBean.RepositoryBillList.get(i).productName;
            for (int f = 0; f < rightList.size(); f++) {
                if (productName.equals(rightList.get(f).getClassName())) {
                    //回显数量
                    rightList.get(f).setGuigeNum(RepositoryBillBean.RepositoryBillList.get(i).productNum);
                    //回显规格
                    rightList.get(f).setUnitSelectType(RepositoryBillBean.RepositoryBillList.get(i).productNnit);

                    rightList.get(f).setProductTime(RepositoryBillBean.RepositoryBillList.get(i).productTime);

                    rightList.get(f).setProductPiHao(RepositoryBillBean.RepositoryBillList.get(i).productPiHao);
                }

            }
        }
        if(null!=rightAdapter){
            rightAdapter.notifyDataSetChanged();
        }


    }

}
