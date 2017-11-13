package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.DeclareConfirmAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SPUtil;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyListView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * 申报确认
 */
public class DeclareConfirmActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv_list)
    MyListView lvList;
    @Bind(R.id.tv_alter)
    TextView tvAlter;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    private List<RepositoryBillBean> tempList = new ArrayList<>();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_confirm);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        title.setText("申报确认");
        ivLeft.setOnClickListener(this);
        tvAlter.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

        setData();
    }

    //设置数据
    private void setData() {
        tempList.addAll(RepositoryBillBean.RepositoryBillList);
        tempList.addAll(RepositoryBillBean.OtherRepositoryBillList);

        DeclareConfirmAdapter adapter = new DeclareConfirmAdapter(this, tempList, R.layout.item_declare_confirm);
        lvList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_alter://修改
                finish();
                break;
            case R.id.tv_submit://提交=================================================================
            submitInfo();
                tempList.clear();
                break;
        }
    }

    /**
     * 把数据提交上去
     */
        private void submitInfo() {
            if (tempList != null) {

                Gson gson = new Gson();
                String toJson = gson.toJson(tempList);

                if (Constants.AccountDetaiTools.isCirculatesShipment==1){
                    //流通的出货台账
                    addOutstockRecord(toJson);
                }else {
                addSupplierRecord(toJson);
                }
            }

        }

    private void addSupplierRecord(String jsondetails) {
        tvSubmit.setClickable(false);

        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("typeid",Constants.AccountDetaiTools.typeid+"");
        params.put("phone", SPUtil.getString(this, Constants.PHONENUM));
        params.put("restaurantid", MyApplication.getInstance().getUser().id);
        params.put("supplierid", Constants.AccountDetaiTools.supplierid+"");
        params.put("bill", MyUtils.bitmapStringData(Constants.AccountDetaiTools.piaojuList));
        if (TextUtils.isEmpty(jsondetails)||"[]".equals(jsondetails)){
            params.put("jsondetails", "");
        }else {

            params.put("jsondetails", jsondetails);
        }
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "AddSupplierRecord5", params, config, this);
    }

    /**
     * 添加出货台账
     *
     */
    private void addOutstockRecord(String jsondetails) {
        tvSubmit.setClickable(false);
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("typeid", Constants.AccountDetaiTools.typeid+"");
        params.put("restaurantid", MyApplication.getInstance().getUser().id);
        params.put("bill", MyUtils.bitmapStringData(Constants.AccountDetaiTools.piaojuList));
        params.put("purchasecompany",Constants.RepositoryShipTools.procurementName );
        params.put("contacts", Constants.RepositoryShipTools.procurementPersion);
        params.put("phone", Constants.RepositoryShipTools.procurementPhone);
        params.put("jsondetails", jsondetails);
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "AddOutstockRecord", params, config, this);
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        Log.e("tag", entity.getContentAsString());
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        String contentAsString = entity.getContentAsString();
        int key = entity.getKey();
        switch (key) {
            case 0://提交
                try {
                    JSONObject jsonObject = new JSONObject(contentAsString);
                    String msg = jsonObject.optString("msg");
                    if (0 == jsonObject.getInt("status")) {
                        Intent mIntent = new Intent(getApplicationContext(), SuoZhenSuoPiaoListActivity.class);
                        startActivity(mIntent);
                        tempList.clear();
//                        saveGongYingShangName();
                        finish();


                    } else if (2==jsonObject.getInt("status")){
                        Intent mIntent = new Intent(getApplicationContext(), LoginActivtiy.class);
                        startActivity(mIntent);
                        tempList.clear();
                        finish();
                    } else {
                        tvSubmit.setClickable(true);

                    }
                    MyToastUtils.show(getApplicationContext(), msg);
                } catch (JSONException e) {
                    tvSubmit.setClickable(true);

                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(contentAsString);
                    String msg = jsonObject.optString("msg");
                    String status = jsonObject.optString("status");
                    MyToastUtils.show(getApplicationContext(), msg);
                    if ("0".equals(status)) {

                        Constants.AccountDetaiTools.piaojuList.clear();

                        Intent mIntent = new Intent(getApplicationContext(), SuoZhenSuoPiaoListActivity.class);
                        tempList.clear();
                        startActivity(mIntent);
                        finish();
                    } else {
                        tvSubmit.setClickable(true);
                    }
                    MyToastUtils.show(getApplicationContext(), msg);
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
        Log.e("tag", entity.getContentAsString());
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }
}
