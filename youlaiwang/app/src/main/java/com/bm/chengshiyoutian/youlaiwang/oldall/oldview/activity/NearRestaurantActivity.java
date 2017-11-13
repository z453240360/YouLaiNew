package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.PopAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.RestaurantAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.PoPBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RestaurantBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 附近餐厅
 * Created by youj on 2016/1/13.
 */
public class NearRestaurantActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener,  PullToRefreshBase.OnRefreshListener2<ListView> {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.ed_search)
    EditText edSearch;
    @Bind(R.id.bt_search)
    Button btSearch;
    @Bind(R.id.tv_near)
    TextView tvNear;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_ranking)
    TextView tvRanking;
    @Bind(R.id.lv)
    PullToRefreshListView lv;

    private RestaurantAdapter adapter;
    private PopupWindow popupWindow;
    private String data[] = {"全部", "1km", "2km", "5km"};
    private ProgressDialog progressDialog;
    private String latitude;
    private String longitude;
    private String cityName;

    /**
     * 距离
     */
    private String distance = "1000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_near_restaurant);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        latitude = intent.getStringExtra("lat");
        longitude = intent.getStringExtra("lon");
        cityName = intent.getStringExtra("cityName");
        progressDialog = new ProgressDialog(this);
        restaurantList = new ArrayList<>();
        tvRight.setText(cityName);
        ivLeft.setOnClickListener(this);
        title.setText(getString(R.string.near_mess));
        tvRight.setVisibility(View.VISIBLE);
        tvNear.setOnClickListener(this);
        tvType.setOnClickListener(this);
        tvRanking.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        loadData();
        adapter = new RestaurantAdapter(this, restaurantList, R.layout.item_restaurant);
        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);

    }

    private void loadData() {

        getCookingType(); //获取菜系
        getFiledOrder(); //获取排名条件
        getRestaurantList(1); //获取附近餐厅列表
    }

    private void getFiledOrder() {
        InternetConfig config = new InternetConfig();
        config.setKey(2);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "FiledOrder",  config, this);
    }

    private int pageIndex = 1;
    private String pageSize = Constants.PAGE_SIZE;
    private int pageCount = 1;


    /**
     * 获取餐厅列表
     */
    private void getRestaurantList(int type) {
        if ("全部".equals(distance)) {
            distance = "1000";
        }
        if (!progressDialog.isShowing() && type ==1) {
            progressDialog.show();
        }
        if (type == 1) {
            restaurantList.clear();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        params.put("longitude", longitude );
        params.put("latitude", latitude );
        params.put("distance", distance.replace("km",""));
        params.put("category_id", cookingTypeId);
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize);
        params.put("CityName", tvRight.getText().toString());
        params.put("filedOrder", orderId);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetNearList", params, config, this);

    }



    /**
     * 获取菜系列表
     */
    private void getCookingType() {
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetMenu", config, this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_near://附近
                showPop(data, tvNear);
                break;

            case R.id.tv_type://菜系
                chooseCookType(tvType,cookTypeList);
                break;

            case R.id.tv_ranking://排名
                chooseCookType(tvRanking,orderList);
                break;

            case R.id.tv_right://跳转到城市列表
                Intent intent = new Intent();
                intent.putExtra("cityName",tvRight.getText());
                intent.setClass(this,CityActivity.class);
                startActivityForResult(intent,0);
                break;

            case R.id.bt_search://搜索
                searchByKeyW();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String city = data.getStringExtra("city");
            tvRight.setText(city);
            getRestaurantList(1);
        }
    }

    /**
     * 搜索
     */
    private void searchByKeyW() {
        String keyWord = edSearch.getText().toString().trim();
        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("keyWord",keyWord);
        intent.putExtra("cityName",tvRight.getText().toString());
        startActivity(intent);
    }

    private ArrayList<RestaurantBean> restaurantList = new ArrayList<>();
    private ArrayList<RestaurantBean> tempList = new ArrayList<>();


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        lv.onRefreshComplete();
        switch (entity.getKey()) {
            case 0:
                try {  //获取菜系
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONArray data = jsonObject.optJSONArray("data");
                        int length = data.length();
                        cookTypeList = new ArrayList<>();
                        cookTypeList.add(new PoPBean("0","全部"));
                        for (int i = 0; i < length; i++) {
                            JSONObject object = data.optJSONObject(i);
                            cookTypeList.add(new PoPBean(object.optString("id"), object.optString("title")));
                        }
                    } else {
                        MyToastUtils.show(this, "数据获取失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 1:
                try {

                    JSONObject object = new JSONObject(entity.getContentAsString());
                    pageCount = object.optInt("recordCount");
                    if (!(pageIndex < pageCount )) {
                        lv.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
                    } else {
                        lv.setMode(PullToRefreshBase.Mode.BOTH);
                    }
                    if (object.optInt("status") == 0) {
                        JSONArray jsonArray = object.optJSONObject("data").optJSONArray("ds");
                        int length = jsonArray.length();
                        tempList.clear();
                        for (int i = 0; i < length; i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            String id = jsonObject.optString("id");
                            String retaurantName = jsonObject.optString("title");
                            String img_url = jsonObject.optString("img_url");
                            String retaurantAddress = jsonObject.optString("seo_title");//地址
                            String phone = jsonObject.optString("seo_keywords"); //电话
                            String xing = jsonObject.optString("xing");//星级
                            String distance = jsonObject.optString("distance");//距离
                            RestaurantBean bean = new RestaurantBean(id, retaurantName, img_url, retaurantAddress, phone, xing, distance);
                            tempList.add(bean);
                        }
                        restaurantList.addAll(tempList);
                        adapter.notifyDataSetChanged();
                    } else {
                        MyToastUtils.show(this, "数据获取失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (jsonObject.optInt("status") == 0) {
                        JSONArray jsonArray = jsonObject.optJSONArray("data");
                        orderList = new ArrayList<>();
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            String id = object.optString("id");
                            String value = object.optString("value");
                            String name = object.optString("name");
                            orderList.add(new PoPBean(id,value,name));
                        }
                    } else {
                        MyToastUtils.show(this,"数据获取失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    @InjectHttpErr
    public void err(ResponseEntity entity) {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        lv.onRefreshComplete();
        MyToastUtils.show(this,getString(R.string.intnet_err));
    }

    /**
     * 菜系id
     */
    private String cookingTypeId = "0";
    /**
     * 菜系list
     */
    private ArrayList<PoPBean> cookTypeList;


   private ArrayList<PoPBean> orderList;

    private String orderId = "id";


    /**
     * 弹出popwindows 选择菜系
     */
    private void chooseCookType(final TextView t, ArrayList<PoPBean> list) {

        Drawable drawable = MyUtils.getDrawa(this, 2);
        t.setCompoundDrawables(null, null, drawable, null);
        ListView listView = new ListView(this);
//        listView.setBackgroundColor(Color.WHITE);
        listView.setDivider(getResources().getDrawable(R.drawable.black_line));
        listView.setLayoutParams(new ViewGroup.LayoutParams(t.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        final PopupWindow cookTypePop = new PopupWindow(listView, t.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        cookTypePop.setFocusable(true);
        cookTypePop.setOutsideTouchable(true);
        cookTypePop.setBackgroundDrawable(new BitmapDrawable());
        cookTypePop.showAsDropDown(t);
        final PopAdapter popAdapter = new PopAdapter(list, this, 1);
        listView.setAdapter(popAdapter);
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoPBean popBean = (PoPBean) popAdapter.getItem(position);
                t.setText(popBean.title);
                if (t == tvRanking) {
                    orderId  = popBean.channel_id;
                } else if (t== tvType) {
                    cookingTypeId = popBean.id;
                }
                getRestaurantList(1);
                cookTypePop.dismiss();
                Drawable drawable = MyUtils.getDrawa(NearRestaurantActivity.this,1);
                t.setCompoundDrawables(null, null, drawable, null);
            }
        });

        //pop消失监听
        cookTypePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //pop消失就切换箭头
                t.setCompoundDrawables(null, null, MyUtils.getDrawa(NearRestaurantActivity.this, 1), null);
            }
        });
    }
    

    /**
     * 弹出pop
     * @param data pop里面的填充数据
     * @param t  点击的控件
     */
    private void showPop(String[] data, final TextView t) {
        Drawable drawable = MyUtils.getDrawa(this, 2);
        t.setCompoundDrawables(null, null, drawable, null);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(t.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < data.length; i++) {
            final TextView textView = new TextView(this);
            textView.setHeight(t.getHeight());
            textView.setWidth(t.getWidth());
            textView.setTextSize(13);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding((int)getResources().getDimension(R.dimen.pading_16),0,0,0);
            textView.setBackgroundResource(R.drawable.bg_edittext1);

            textView.setText(data[i]);
            layout.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    distance = textView.getText().toString().trim();
                    t.setText(distance);
                    getRestaurantList(1);
                    popupWindow.dismiss();
                    Drawable drawable = MyUtils.getDrawa(NearRestaurantActivity.this, 1);
                    t.setCompoundDrawables(null, null, drawable, null);
                }
            });
        }
        popupWindow = new PopupWindow(layout, t.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(t);

        //pop消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //pop消失就切换箭头
                t.setCompoundDrawables(null, null, MyUtils.getDrawa(NearRestaurantActivity.this, 1), null);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RestaurantBean bean = adapter.getItem(position-1);
        Intent intent = new Intent(this,ResturantDetaisActivity.class);
        intent.putExtra("id",bean.id);
        startActivity(intent);
    }



    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        restaurantList.clear();
        adapter.notifyDataSetChanged();
        pageIndex = 1;
        getRestaurantList(2);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageIndex = pageIndex+1;
        getRestaurantList(2);
    }
}
