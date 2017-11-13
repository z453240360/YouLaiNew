package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.GouWuCheActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.KuaiSuXiaDanActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.fragment.FragmentShangPin1XiangQing;
import com.bm.chengshiyoutian.youlaiwang.fragment.FragmentShangPin2XiangQing;
import com.bm.chengshiyoutian.youlaiwang.fragment.FragmentShangPin3XiangQing;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ProDetialBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.Gsonutils;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bm.chengshiyoutian.youlaiwang.R.id.iv_shoucang;
import static com.bm.chengshiyoutian.youlaiwang.R.string.code;

public class ProductDetiaActivity extends AppCompatActivity implements IMainView {


    @Bind(R.id.toolbars_longhu)
    Toolbar toolbarsLonghu;
    @Bind(R.id.mRg_longhu)
    RadioGroup mRgLonghu;
    @Bind(R.id.frame_longhu)
    FrameLayout frameLonghu;
    @Bind(R.id.ll_dianhua)
    LinearLayout llDianhua;
    @Bind(R.id.ll_dianpu)
    LinearLayout llDianpu;
    @Bind(iv_shoucang)
    ImageView ivShoucang;
    @Bind(R.id.ll_shoucang)
    LinearLayout llShoucang;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_count)
    TextView tvCount;
    @Bind(R.id.llgouwuche)
    LinearLayout llgouwuche;
    @Bind(R.id.ll_pro)
    LinearLayout llPro;
    @Bind(R.id.mRb_goods)
    RadioButton mRbGoods;
    @Bind(R.id.mRb_dital)
    RadioButton mRbDital;
    @Bind(R.id.mRb_dicass)
    RadioButton mRbDicass;
    @Bind(R.id.activity_product_detia)
    RelativeLayout activityProductDetia;

    private Intent intent;
    private Intent dongData;
    private Present present;
    private SharedPreferences sp;

    private ArrayList<Fragment> list = new ArrayList<>();
    private FragmentManager managers;
    private Fragment lastFragment;


    private String goodsId;
    private String token;
    private String store_phone;
    private int store_id;
    private String cart_num;
    private double cart_price;
    private int is_favorites;

    private FragmentShangPin1XiangQing fragmentShangPin1;
    private FragmentShangPin2XiangQing fragmentShangPin2;
    private FragmentShangPin3XiangQing fragmentShangPin3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorState.setWindowStatusBarColorBlue(this, Color.BLUE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_detia);
        intent = getIntent();
        dongData = new Intent();
        goodsId = intent.getStringExtra("token");

        present = new Present(this);
        sp = getSharedPreferences(MyRes.CONFIG, 0);

        token = sp.getString(MyRes.MY_TOKEN, "");


        ButterKnife.bind(this);
        initToolBar();
        initFragment();
        initRadioButton();

        present.getGoodsDetial(token, goodsId);

        fragmentShangPin1.setChange(new FragmentShangPin1XiangQing.onChange() {
            @Override
            public void getJump() {
                mRbDicass.setChecked(true);
            }

            @Override
            public void getData(int postion, List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> goods_spec) {
                dongData.putExtra("backData", (Serializable) (goods_spec));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        token = sp.getString(MyRes.MY_TOKEN, "");
    }

    @Override
    public void getCode(String s) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showFaliure(String s) {

    }

    //商品详情
    @Override
    public void getLogin(String s) {
        Gson gson = new Gson();
        ProDetialBean bean = gson.fromJson(s, ProDetialBean.class);
        int code = bean.getCode();
        if (code == 200) {
            //店铺电话
            store_phone = bean.getData().getStore().getStore_phone();
            //店铺ID
            store_id = bean.getData().getStore_id();
            cart_num = bean.getData().getCart_num();
            cart_price = bean.getData().getCart_price();
            is_favorites = bean.getData().getIs_favorites();

            tvCount.setText("" + cart_num);
            tvMoney.setText("" + cart_price);


            if (is_favorites == 1) {
                ivShoucang.setImageResource(R.drawable.shoucang_004);
            } else {
                ivShoucang.setImageResource(R.drawable.shoucang_003);
            }

        } else {
            ShowToast.showToast(bean.getMsg() + "");
        }

    }

    @Override
    public void getUpDate(String s) {

    }

    private void initToolBar() {
        setSupportActionBar(toolbarsLonghu);
        toolbarsLonghu.setNavigationIcon(R.mipmap.gouwuche_fanhui);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @OnClick({R.id.ll_dianhua, R.id.ll_dianpu, iv_shoucang, R.id.ll_shoucang, R.id.llgouwuche})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dianhua:
                if (store_phone.equals("")) {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + store_phone));
                    startActivity(intent);
                } else {
                    ShowToast.showToast("未获取到电话");
                }

                break;
            case R.id.ll_dianpu:
                intent = new Intent(this, KuaiSuXiaDanActivity.class);
                intent.putExtra(MyRes.DIANPU_ID, store_id);
                sp.edit().putString(MyRes.DIANPU_ID, store_id + "").commit();
                startActivity(intent);
                break;
            case R.id.ll_shoucang:
                present.shouCang(token, goodsId);//商品收藏
                break;
            case R.id.llgouwuche:
                if ("".equals(token)) {
                    ShowToast.showToast("请登录");
                    startActivity(new Intent(this, Login_ddActivity.class));
                } else {
                    startActivity(new Intent(this, GouWuCheActivity.class));
                }
                break;
        }
    }


    private void initFragment() {
        managers = getSupportFragmentManager();

        fragmentShangPin1 = new FragmentShangPin1XiangQing();
        fragmentShangPin2 = new FragmentShangPin2XiangQing();
        fragmentShangPin3 = new FragmentShangPin3XiangQing();


        list.add(fragmentShangPin1);
        list.add(fragmentShangPin2);
        list.add(fragmentShangPin3);

        managers.beginTransaction().add(R.id.frame_longhu, list.get(0)).commit();
        lastFragment = list.get(0);
    }

    private void initRadioButton() {
        mRgLonghu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton select = (RadioButton) findViewById(i);
                int index = Integer.parseInt(select.getTag().toString());

                if (list.get(index).isAdded()) {
                    managers.beginTransaction().show(list.get(index)).commit();
                } else {
                    managers.beginTransaction().add(R.id.frame_longhu, list.get(index)).commit();
                }

                managers.beginTransaction().hide(lastFragment).commit();
                lastFragment = list.get(index);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> backData = (List<ShangPinXQ1DaiPingLun.DataBean.SpecBean>) dongData.getSerializableExtra("backData");
                setResult(RESULT_OK, dongData);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
