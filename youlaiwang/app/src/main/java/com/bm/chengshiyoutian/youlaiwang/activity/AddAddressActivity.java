package com.bm.chengshiyoutian.youlaiwang.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GetJsonDataUtil;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.CustomHelper;
import com.bm.chengshiyoutian.youlaiwang.bean.MyCityBean;
import com.bm.chengshiyoutian.youlaiwang.bean.ShanChuBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.jph.takephoto.model.TImage;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;

import java.util.ArrayList;



/**
 * Created by sld on 2017/5/11.
 */

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_toolbar;
    private LinearLayout ll_address;
    private TextView tv_address;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_content;
    private Button btn1;
    private ImageView iv_check;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_address);
        initView();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        StatusBarUtil.setTransparent(AddAddressActivity.this);
        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }


    private void initView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_address.setOnClickListener(this);
        tv_address = (TextView) findViewById(R.id.tv_address);
        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setOnClickListener(this);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_phone.setOnClickListener(this);
        et_content = (EditText) findViewById(R.id.et_content);
        et_content.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        iv_check = (ImageView) findViewById(R.id.iv_check);
        iv_check.setOnClickListener(this);
    }

    String province_id, city_id, area_id;
    String tag = "2";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_address:
                if (isLoaded) {
                    ShowPickerView();
                } else {
                    Toast.makeText(AddAddressActivity.this, "数据暂未解析成功，请等待", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_check:
                if (tag.equals("1")) {
                    iv_check.setImageResource(R.mipmap.dizhi_guanbi);
                    tag="2";

                } else {
                    iv_check.setImageResource(R.mipmap.dizhi_dakai);
                    tag = "1";

                }
                break;
            case R.id.btn1:
                if (submit()) {

                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/address/store", RequestMethod.POST);
                    SharedPreferences sp=getSharedPreferences(MyRes.CONFIG,0);
                    stringRequest.addHeader("Authorization",sp.getString(MyRes.MY_TOKEN,""));
                    stringRequest.add("name", et_name.getText().toString());
                    stringRequest.add("mobile", et_phone.getText().toString());
                    stringRequest.add("province_id", jsonBean.get(x).getK());
                    stringRequest.add("city_id", jsonBean.get(x).getN().get(y).getK());
                    stringRequest.add("area_id", jsonBean.get(x).getN().get(y).getN().get(z).getK());
                    stringRequest.add("is_default", tag);
                    stringRequest.add("cityinfo", tv_address.getText().toString());
                    stringRequest.add("areainfo", et_content.getText().toString());
                    CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response response) {
                            try {
                                ShanChuBean shanChuBean = GsonUtils.getInstance().fromJson((String) response.get(), ShanChuBean.class);

                                if (shanChuBean.getCode() == 200) {
                                    ShowToast.showToast("成功");
                                    finish();
                                } else {
                                    ShowToast.showToast("失败");

                                }
                            } catch (JsonSyntaxException e) {
                                ShowToast.showToast("数据异常");
                            }
                        }

                        @Override
                        public void onFailed(int what, Response response) {
                            ShowToast.showToast("联网失败");
                        }

                        @Override
                        public void onFinish(int what) {

                        }
                    });
                } else {
                    ShowToast.showToast("资料请填写完整");

                }
                break;
        }
    }

    String tx;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    int x = -1, y = -1, z = -1;

    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                x = options1;
                y = options2;
                z = options3;

                if (options1Items.get(options1).getV().equals(options2Items.get(options1).get(options2)

                )) {
                    tx =
                            options2Items.get(options1).get(options2) +
                                    options3Items.get(options1).get(options2).get(options3);

                } else {
                    tx = options1Items.get(options1).getV() +
                            options2Items.get(options1).get(options2) +
                            options3Items.get(options1).get(options2).get(options3);

                }

                tv_address.setText(tx);


            }
        })

                .setTitleText("城市选择")
                .setCancelText("取消")
                .setSubmitText("确定")
                .setSubmitColor(Color.parseColor("#76c933"))
                .setCancelColor(Color.parseColor("#76c933"))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setTitleBgColor(Color.parseColor("#ffffff"))//标题背景颜色 Night mode
                .setBgColor(Color.parseColor("#dfdfdf"))//滚轮背景颜色 Night mode dfdfdf
                .setOutSideCancelable(false)// default is true
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(datas, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private boolean isLoaded = false;

    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    ArrayList<TImage> images;
    private CustomHelper customHelper;
    private ArrayList<MyCityBean> options1Items = new ArrayList<>();
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        //   Toast.makeText(SettingActivity.this,"开始解析数据",Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    // Toast.makeText(SettingActivity.this,"解析数据成功",Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(AddAddressActivity.this, "省市区数据损坏，请重新下载应用", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
    ArrayList<String> datas;
    String JsonData;
    ArrayList<MyCityBean> jsonBean;

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        JsonData = new GetJsonDataUtil().getJson(this, "city.json");//获取assets目录下的json文件数据
        jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        datas = new ArrayList<>();
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {
            datas.add(jsonBean.get(i).getV());

        }

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getN().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getN().get(c).getV().toString();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getN().get(c).getN() == null
                        || jsonBean.get(i).getN().get(c).getN().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getN().get(c).getN().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getN().get(c).getN().get(d).getV().toString();

                        City_AreaList.add(AreaName.toString());//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<MyCityBean> parseData(String result) {//Gson 解析
        ArrayList<MyCityBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                MyCityBean entity = gson.fromJson(data.optJSONObject(i).toString(), MyCityBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private boolean submit() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请添加详细收货地址，不少于5个字", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
        // TODO validate success, do something


    }
}
