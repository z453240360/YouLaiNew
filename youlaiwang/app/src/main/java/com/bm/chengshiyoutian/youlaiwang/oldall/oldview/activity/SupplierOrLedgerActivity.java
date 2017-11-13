package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.SuppliersAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.SupplierBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



public class SupplierOrLedgerActivity extends Activity implements View.OnClickListener, SuppliersAdapter.SupplierCallBack, SuppliersAdapter.EditSupplierCallBack, AdapterView.OnItemClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_add_supplier)
    TextView tvAddSupplier;//添加新供应商
    @Bind(R.id.lv_list)
    MyListView lvList;
    @Bind(R.id.rl_supplier)
    RelativeLayout rlSupplier;
    @Bind(R.id.tv_right_two)
    TextView tv_right_two;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.tv_next)
    TextView tvNext;
    @Bind(R.id.et_suppliername)
    EditText etSuppliername;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    private ProgressDialog progressDialog;
    private ArrayList<SupplierBean> SupplierList = new ArrayList<SupplierBean>();
    private ArrayList<SupplierBean> SearchSupplierList = new ArrayList<SupplierBean>();
    private int checkPosition = 0;
    private String SearchKeys = "";//搜索关键字
    private SuppliersAdapter adapter;
    private int searchType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_or_ledger);
        ButterKnife.bind(this);
        lvList.setDivider(null);
        title.setText("供应商");
        progressDialog = new ProgressDialog(this);
        initListener();


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPosition = 0;
        getSupplierOrLedger();
    }

    /**
     * 获取供应商列表
     */
    private void getSupplierOrLedger() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("Restid", MyApplication.getInstance().getUser().id);
        params.put("pageIndex", "1");
        params.put("pageSize", "100000");
        params.put("keys",SearchKeys);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetSupplierList", params, config, this);
    }


    private void initListener() {
        ivLeft.setOnClickListener(this);
        tvAddSupplier.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        tv_right_two.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_add_supplier://添加新供应商
            case R.id.tv_add://新增
                Intent intent = new Intent();
                Constants.SUPPLIERTYPE = "0";
                intent.putExtra("ForActivity", Constants.SUPPLIERTYPE);
                intent.setClass(this, AddOrEditSupplierActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_right_two:
            case R.id.tv_next://下一步
                Constants.AccountDetaiTools.supplierid = SupplierList.get(checkPosition).getId();
                Intent intentTwo = new Intent();
                intentTwo.putExtra("businessNum", SupplierList.get(checkPosition).getLicenseNo());
                intentTwo.putExtra("restaurantName", SupplierList.get(checkPosition).getSupperlierName());
                intentTwo.putExtra("contactsPersion", SupplierList.get(checkPosition).getSupperlierContact());
                intentTwo.putExtra("SupplierPhone", SupplierList.get(checkPosition).getSupperlierTel());
                intentTwo.putExtra("SupplierId", SupplierList.get(checkPosition).getId() + "");
                intentTwo.setClass(this,SupplyBillActivity.class);
                startActivity(intentTwo);
                break;
            case R.id.iv_search://搜索

                SearchKeys = etSuppliername.getText().toString();
                getSupplierOrLedger();
                break;
        }
    }



    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0: //
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());

                    if (0 == jsonObject.getInt("status")) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        String ds = data.optString("ds");

                        Gson gson = new Gson();
                        Type lt = new TypeToken<ArrayList<SupplierBean>>() {
                        }.getType();
                        SupplierList.clear();
                        ArrayList<SupplierBean> temmpList = gson.fromJson(ds, lt);

                        SupplierList.addAll(temmpList);
//                        SearchSupplierList.addAll(temmpList);

                            if (SupplierList == null || SupplierList.size() == 0) {//如果列表没有供应商

                                rlSupplier.setVisibility(View.VISIBLE);
                                tv_right_two.setVisibility(View.GONE);
                                lvList.setVisibility(View.GONE);
                                llBottom.setVisibility(View.GONE);
                                if (searchType==2){
                                    searchType =0;
                                    SearchKeys = "";
                                    getSupplierOrLedger();
                                }
                            } else {
                                rlSupplier.setVisibility(View.GONE);
                                lvList.setVisibility(View.VISIBLE);
                                tv_right_two.setText("下一步");
                                tv_right_two.setVisibility(View.VISIBLE);
                                llBottom.setVisibility(View.VISIBLE);
                                if ("".equals(SearchKeys)) {
                                    //设置列表数据
                                    adapter = new SuppliersAdapter(this, SupplierList, R.layout.item_supplier, this, this);
                                    lvList.setAdapter(adapter);

                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                                lvList.setOnItemClickListener(this);
                            }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        searchType = 2;
    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    //单选回调
    @Override
    public void getPosition(int p) {
        checkPosition = p;

    }

    //编辑供应商回调
    @Override
    public void editSupplier(int position) {
        Constants.SUPPLIERTYPE = "1";
        Intent intent = new Intent();
        intent.putExtra("ForActivity", Constants.SUPPLIERTYPE);
        intent.putExtra("imgList", SupplierList.get(position).getLicenseImg());
        intent.putExtra("businessNum", SupplierList.get(position).getLicenseNo());
        intent.putExtra("restaurantName", SupplierList.get(position).getSupperlierName());
        intent.putExtra("contactsPersion", SupplierList.get(position).getSupperlierContact());
        intent.putExtra("SupplierPhone", SupplierList.get(position).getSupperlierTel());
        intent.putExtra("SupplierId", SupplierList.get(position).getId() + "");
        intent.setClass(this, AddOrEditSupplierActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        checkPosition = position;
        for (int i = 0 ;i<adapter.getCount();i++){
            CheckBox checkBox = (CheckBox) lvList.getChildAt(i - lvList.getFirstVisiblePosition()).findViewById(R.id.cb);
            if (i==position){
                checkBox.setChecked(true);
            }else {
                checkBox.setChecked(false);
            }
        }
    }
}
