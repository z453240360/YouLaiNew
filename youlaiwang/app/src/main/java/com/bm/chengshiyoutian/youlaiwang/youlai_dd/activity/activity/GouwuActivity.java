package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.EXpandAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.CarBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.CarBean_new;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.constant.Constant;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.ICallBack;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.DataForma_dd;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.R.id.btn;
import static com.bm.chengshiyoutian.youlaiwang.R.id.ll_bottom;
import static com.bm.chengshiyoutian.youlaiwang.R.id.logo;
import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;

public class GouwuActivity extends AppCompatActivity {


    private TextView txt,btn_delete;
    private Button btn;
    private ImageView isAllSelect;
    private LinearLayout ll_kong;
    private RelativeLayout ll_bottom;
    private boolean flag = true;

    private String token;
    private String TAG = "dd";

    private EXpandAdapter adapter;
    private SharedPreferences sp;


    private List<GouWuCheBean.DataBeanX> mDates = new ArrayList<>();
    private List<CarBean_new.DataBeanX.DataBean> allData = new ArrayList<>();
    private ExpandableListView expandableListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dd_gouwu);
        sp = getSharedPreferences(MyRes.CONFIG,0);
        String string = sp.getString(MyRes.TOKEN, "");

        if (string.equals("")||string.equals("kong"))
        {
            return;
        }

        init(string);//初始化


        getCarData(token);//获取购物车数据
        clicked();//点击事件
    }

    private void clicked() {
        //去结算
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText(adapter.getCarId());

                goToPay(token,adapter.getCarId());
            }
        });

        //全选按钮
        isAllSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    isAllSelect.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
                    adapter.setAllSelected(true);
                    flag = false;
                } else {
                    isAllSelect.setImageResource(R.mipmap.shezhi_weixuanzhong);
                    flag = true;
                    adapter.setAllSelected(false);
                }
            }
        });

        //删除购物车选中项目
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteCarData(adapter.getCarId(),token);
            }
        });
    }

    private void init(String string) {
        token = "Bearer "+string;
        txt = (TextView) findViewById(R.id.totP);
        btn_delete = (TextView) findViewById(R.id.btn_delete);
        btn = (Button) findViewById(R.id.btn);

        ll_bottom = (RelativeLayout) findViewById(R.id.ll_bottom);
        ll_kong = (LinearLayout) findViewById(R.id.ll_kongGouWuChe);

        expandableListView = (ExpandableListView) findViewById(R.id.expanded_menu);
        isAllSelect = (ImageView) findViewById(R.id.isAllSelect);

        for (int i = 0; i < expandableListView.getCount(); i++) {
            expandableListView.expandGroup(i);
        }

    }

    //获取购物车数据
    private void getCarData(String token) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart");
        stringRequest.addHeader("Authorization", token);
        NoHttp.newRequestQueue().add(1, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                Gson gson = new Gson();
                CarBean_new carBean = gson.fromJson(response.get().toString(), CarBean_new.class);
                CarBean_new.DataBeanX data = carBean.getData();
                List<CarBean_new.DataBeanX.DataBean> storeBean = data.getData();//所有商店的集合

                if (storeBean.size()==0){
                    ll_kong.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.VISIBLE);
                    expandableListView.setVisibility(View.VISIBLE);
                    return;
                }else {
                    ll_kong.setVisibility(View.VISIBLE);
                    ll_bottom.setVisibility(View.GONE);
                    expandableListView.setVisibility(View.GONE);
                }

                adapter = new EXpandAdapter(storeBean, GouwuActivity.this);
                expandableListView.setAdapter(adapter);
                for (int i = 0; i < storeBean.size(); i++) {
                    expandableListView.expandGroup(i);
                }

                expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                        view.setClickable(true);
                        return true;
                    }
                });

                adapter.setCallBackData(new EXpandAdapter.OnCallBackData() {

                    //购物车列表头被点击了
                    @Override
                    public void getGroupListClicked(int postion) {
                        Toast.makeText(GouwuActivity.this, "父条目" + postion, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void getChildClicked(int groupPos, int childPos) {

                    }

                    @Override
                    public void getChildAdd(int groupPos, int childPos) {
//                        Toast.makeText(Main3Activity.this, "父条目"+groupPos+"子条目"+childPos, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void getChildReduce(int groupPos, int childPos) {
//                        Toast.makeText(Main3Activity.this, "父条目"+groupPos+"子条目"+childPos, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void getChildedTotalPrice(int groupPos, int childPos, String price) {

                    }

                    @Override
                    public void getTotalPrice(double s) {
                        txt.setText("总计" + DataForma_dd.getDoubleByDouble(s) + "");
                    }

                    @Override
                    public void getAllSelected(int postion, boolean b) {
                        if (b) {
                            isAllSelect.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
                            flag = false;
                        } else {
                            isAllSelect.setImageResource(R.mipmap.shezhi_weixuanzhong);
                            flag = true;
                        }
                    }
                });
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ShowToast.showToast("网络联接失败");
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

    //去支付
    private void goToPay(String token, String cardsID){
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/pay/confirm");
        stringRequest.addHeader("Authorization", token);
        stringRequest.add("cartIds", cardsID);
        NoHttp.newRequestQueue().add(1333, stringRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response response) {
                Log.i(TAG, "onSucceed: "+response.get().toString());
            }
            @Override
            public void onFailed(int what, Response response) {
            }
            @Override
            public void onFinish(int what) {
            }
        });


    }


    //移除购物车数据
    public void deleteCarData(String list, final String token) {
        Request<String> stringRequest1 = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/destroy", RequestMethod.POST);
        stringRequest1.addHeader("Authorization", token);
        stringRequest1.add("cart_id", list);
        stringRequest1.add("_method", "delete");
        CallServer.getInstance().add(13, stringRequest1, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                if (response.get().toString() == null) {
                    ShowToast.showToast("数据异常");
                    return;
                }
                int responseCode = response.responseCode();
                if (responseCode != 200) {
                    ShowToast.showToast("服务器异常：" + responseCode);
                    return;
                }

                String s = response.get().toString();
                getCarData(token);

            }

            @Override
            public void onFailed(int what, com.yanzhenjie.nohttp.rest.Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

}
