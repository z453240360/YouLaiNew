package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.ShoppingCar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.MainActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.QuYuActivityNew;
import com.bm.chengshiyoutian.youlaiwang.activity.SearchActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.XiTongTiXingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.ProductDetiaActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ClassOneBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ClassTwoBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ProductDetialBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ShoppingCarBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.bm.chengshiyoutian.youlaiwang.R.id.btn_new;
import static com.bm.chengshiyoutian.youlaiwang.R.id.storeId;
import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.mIv_gouwuche;
import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;

/**
 * Created by dongdong on 2017/11/5.
 */

public class ShoppingFragment extends Fragment implements IMainView {


    @Bind(R.id.rc_classOne)
    RecyclerView rcClassOne;
    @Bind(R.id.rc_classTwo)
    RecyclerView rcClassTwo;
    @Bind(R.id.btn_price)
    Button btnPrice;
    @Bind(R.id.btn_new)
    Button btnNew;
    @Bind(R.id.btn_pro)
    Button btnPro;
    @Bind(R.id.btn_near)
    Button btnNear;
    @Bind(R.id.rv)
    XRecyclerView rv;
    @Bind(R.id.isHasData)
    LinearLayout isHasData;
    @Bind(R.id.city_name)
    TextView cityName;
    @Bind(R.id.ll_quyu)
    LinearLayout llQuyu;
    @Bind(R.id.et_search)
    TextView etSearch;
    @Bind(R.id.ll_sou)
    LinearLayout llSou;
    @Bind(R.id.iv_emessage)
    ImageView ivEmessage;
    @Bind(R.id.iv_dian)
    TextView ivDian;
    private String token;
    private String areaId;

    private String TAG = "dd";
    private int page = 1;
    private String state = "5";
    private String classOneId = "";
    private int classTwoId = 0;
    private String orderBy = "";
    private String lng = "";
    private String lat = "";
    private boolean isByPriceUp = true;

    private Present present;
    private Dialog dialog;

    private SharedPreferences sp;

    private ShoppingAdapter adapter3;
    private ClassOneAdapter classOneAdapter;
    private ClassTwoAdapter classTwoAdapter;

    private LinearLayoutManager manager;
    private LinearLayoutManager classTwoManger;
    private GridLayoutManager gridLayoutManager;

    private List<ClassOneBean.DataBean> allClassOneData = new ArrayList<>();
    private List<ClassTwoBean.DataBean> allAlassTwoData = new ArrayList<>();
    private List<ShoppingCarBean.DataBeanX.DataBean> allData = new ArrayList<>();
    private ProgressDialog dialog4;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detial, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ColorState.setWindowStatusBarColorBlue(getActivity(), Color.parseColor("#ffffff"));
        sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);
        token = sp.getString(MyRes.MY_TOKEN, "");
        areaId = sp.getString(MyRes.area_id, "");
        cityName.setText(sp.getString(MyRes.city2, "") + "");

        present = new Present(this);
        dialog = new Dialog(getActivity());
        dialog.setTitle("正在加载");

        dialog4 = new ProgressDialog(getActivity());
        dialog4.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog4.setCancelable(false);

        //获取消息数量
        getMessage();

        //一级分类
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        classOneAdapter = new ClassOneAdapter(getActivity(), allClassOneData);
        rcClassOne.setLayoutManager(gridLayoutManager);
        rcClassOne.setAdapter(classOneAdapter);
        classOneAdapter.setClicked(new ClassOneAdapter.OnClicked() {
            @Override
            public void onItemClicked(int postion, int gc_id) {

                initButton();
                classOneId = gc_id + "";
                allAlassTwoData.clear();
                allData.clear();
                adapter3.notifyDataSetChanged();
                classTwoAdapter.init();
                present.getClassTwoData(token, gc_id + "", areaId);//获取二级分类
            }
        });


        //二级分类适配器
        classTwoAdapter = new ClassTwoAdapter(getActivity(), allAlassTwoData);
        classTwoManger = new LinearLayoutManager(getActivity());
        rcClassTwo.setLayoutManager(classTwoManger);
        rcClassTwo.setAdapter(classTwoAdapter);
        classTwoAdapter.setClicked(new ClassTwoAdapter.OnClicked() {
            @Override
            public void onItemClicked(int postion, int gc_id) {

                initButton();
                page=1;
                classTwoId = gc_id;
                allData.clear();
                adapter3.notifyDataSetChanged();
                present.getYouBianData(token, classTwoId, classOneId, "", "", "", page, "", "  ", "330782");
            }
        });

        //商品详情
        manager = new LinearLayoutManager(getActivity());
        adapter3 = new ShoppingAdapter(getActivity(), allData);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter3);

        //获取商品一级分类列表
        present.getClassOneData(token);

        //获取商品二级分类列表
        present.getClassTwoData(token, "", areaId);

        //商品列表设置加载更多
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                allData.clear();
                page = 1;
                adapter3.notifyDataSetChanged();
                present.getYouBianData(token, classTwoId, classOneId, "", "", "", page, "", "  ", "" + areaId);
                rv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                present.getYouBianData(token, classTwoId, classOneId, "", "", "", page, "", "  ", "" + areaId);
                rv.loadMoreComplete();
            }
        });

        //商品列表的点击跳转详情事件
        adapter3.setClicked(new ShoppingAdapter.OnItemClicked() {
            @Override
            public void getDetial(int goods_id) {
                Intent intent = new Intent(getActivity(), ShangPinXiangQingActivity.class);
                intent.putExtra("token", goods_id+"");
                startActivityForResult(intent, 100);//请求吗100
            }

            @Override
            public void addClicked() {
                MainActivity.getData1();
            }

            @Override
            public void reduceClicker() {
//                getCarNum.getCarNum();
                MainActivity.getData1();
            }
        });
    }


    //条件筛选
    @OnClick({R.id.btn_price, btn_new, R.id.btn_pro, R.id.btn_near,R.id.ll_quyu,R.id.ll_sou,R.id.iv_emessage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //价格排序
            case R.id.btn_price:

                initButton();

                btnPrice.setTextColor(Color.parseColor("#74ca31"));
                state = 1 + "";
                page = 1;
                allData.clear();
                adapter3.notifyDataSetChanged();

                if (isByPriceUp) {

                    Drawable top = getResources().getDrawable(R.mipmap.diangpu_xiangxia);
                    top.setBounds(-20, -5, 0, 30);
                    btnPrice.setCompoundDrawables(null, null, top, null);
                    present.getYouBianData(token, classTwoId, classOneId, state, "asc", "", page, "", "", areaId);
                    isByPriceUp = false;
                } else {

                    Drawable top = getResources().getDrawable(R.mipmap.diangpu_xiangshang);
                    top.setBounds(-20, -5, 0, 30);
                    btnPrice.setCompoundDrawables(null, null, top, null);
                    present.getYouBianData(token, classTwoId, classOneId, state, "desc", "", page, "", "", areaId);
                    isByPriceUp = true;
                }

                break;

            //新品排序
            case btn_new:
                ByNew();
                break;

            //促销排序
            case R.id.btn_pro:
                ByPro();
                break;

            //附近排序
            case R.id.btn_near:
                initButton();
                btnNear.setTextColor(Color.parseColor("#74ca31"));
                state = 4 + "";
                page = 1;
                lat="";
                lng="";
                allData.clear();
                adapter3.notifyDataSetChanged();
                present.getYouBianData(token, classTwoId, classOneId, state, "", "", page, "0.0", "0.0", areaId);
                break;

            //区域选择
            case R.id.ll_quyu:
                Intent intents = new Intent(getActivity(), QuYuActivityNew.class);
                startActivity(intents);
                break;

            //搜索
            case R.id.ll_sou:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;

            //系统消息
            case R.id.iv_emessage:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    ShowToast.showToast("请登录");
                    Intent intent3 = new Intent(getActivity(), Login_ddActivity.class);
                    intent3.putExtra("tag", "two");
                    startActivity(intent3);
                } else {
                    Intent intent3 = new Intent(getActivity(), XiTongTiXingActivity.class);
                    startActivity(intent3);
                }
                break;
        }
    }

    //促销
    private void ByPro() {
        initButton();
        btnPro.setTextColor(Color.parseColor("#74ca31"));
        state = 3 + "";
        orderBy="";
        lat="";
        lat="";
        page = 1;
        allData.clear();
        adapter3.notifyDataSetChanged();
        present.getYouBianData(token, classTwoId, classOneId, state, orderBy, "", page, "", "", areaId);
    }

    //新品
    private void ByNew() {
        initButton();
        btnNew.setTextColor(Color.parseColor("#74ca31"));
        state = 2 + "";
        page = 1;
        lat="";
        lat="";
        orderBy="";
        allData.clear();
        adapter3.notifyDataSetChanged();

        present.getYouBianData(token, classTwoId, classOneId, state, orderBy, "", page, "", "", areaId);
    }


    //商品页面消息
    private void getMessage() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/message/num");
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {
                String result = response.get().toString();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        JSONObject object1 = object.getJSONObject("data");
                        int num = object1.getInt("num");
                        if (num > 0) {
                            ivDian.setVisibility(View.VISIBLE);
                            ivDian.setText(num + "");
                        } else {
                            ivDian.setVisibility(View.GONE);
                        }

                    }

                } catch (JsonSyntaxException e) {
                } catch (JSONException e) {
                    e.printStackTrace();
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
    }

    //初始化按钮，
    public void initButton() {
        state = "5";
        btnPrice.setTextColor(Color.parseColor("#000000"));
        Drawable top = getResources().getDrawable(R.mipmap.xia);
        top.setBounds(0, 0, 0, 0);
        btnPrice.setCompoundDrawables(null, null, top, null);

        btnNear.setTextColor(Color.parseColor("#000000"));
        btnNew.setTextColor(Color.parseColor("#000000"));
        btnPro.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> backData = (List<ShangPinXQ1DaiPingLun.DataBean.SpecBean>) data.getSerializableExtra("goodsName");
                adapter3.refish(backData);
            }
        }
    }

    //一级分类
    @Override
    public void getCode(String s) {
        Gson gosn = new Gson();
        ClassOneBean classOneBean = gosn.fromJson(s, ClassOneBean.class);
        List<ClassOneBean.DataBean> classOneData = classOneBean.getData();
        allClassOneData.addAll(classOneData);
        classOneAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        dialog4.show();
    }

    @Override
    public void cancelLoading() {
        dialog4.cancel();
    }

    @Override
    public void showFaliure(String s) {
        dialog.cancel();
        Toast.makeText(getActivity(), "" + s, Toast.LENGTH_SHORT).show();
    }

    //二级分类
    @Override
    public void getLogin(String s) {
        Gson gosn = new Gson();
        ClassTwoBean classTwoBean = gosn.fromJson(s, ClassTwoBean.class);
        List<ClassTwoBean.DataBean> classTwoData = classTwoBean.getData();

        allAlassTwoData.addAll(classTwoData);

        for (int i = 0; i < allAlassTwoData.size(); i++) {
            if (i == 0) {
                allAlassTwoData.get(i).setSelected(true);
            } else {
                allAlassTwoData.get(i).setSelected(false);
            }
        }

        classTwoAdapter.notifyDataSetChanged();
        int postion = classTwoAdapter.getPostion();
        classTwoId = allAlassTwoData.get(postion).getGc_id();

        //获取商品列表
        page = 1;
        allData.clear();
        adapter3.notifyDataSetChanged();
        present.getYouBianData(token, classTwoId, classOneId, "", "", "", page, "", "  ", areaId);
    }

    //商品列表
    @Override
    public void getUpDate(String s) {

        try{
            Gson gson = new Gson();
            ShoppingCarBean shoppingCarBean = gson.fromJson(s, ShoppingCarBean.class);
            int code = shoppingCarBean.getCode();
            if (code != 200) {
                Toast.makeText(getActivity(), "" + shoppingCarBean.getMsg(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }

            List<ShoppingCarBean.DataBeanX.DataBean> data = shoppingCarBean.getData().getData();

            if (page == 1) {
                if (data.size() == 0) {
                    isHasData.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                    return;
                } else {
                    isHasData.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                }
            } else {
                isHasData.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                if (data.size() == 0) {
                    ShowToast.showToast("已经显示全部");
                    return;
                }
            }

            allData.addAll(data);
            dialog.cancel();
            adapter3.notifyDataSetChanged();
        }catch (JsonSyntaxException e){
            ShowToast.showToast("数据解析异常");
        }

    }

    //当重新获取焦点的时候，检查地址是否有改变
    @Override
    public void onResume() {
        super.onResume();

        String string = sp.getString("weizhi", "");
        if (string.equals("")){

        }else {
            allAlassTwoData.clear();
            allClassOneData.clear();
            allData.clear();

            classTwoAdapter.notifyDataSetChanged();
            classOneAdapter.notifyDataSetChanged();
            adapter3.notifyDataSetChanged();

            areaId = sp.getString(MyRes.area_id,"");
            //获取商品一级分类列表
            present.getClassOneData(token);

            //获取商品二级分类列表
            present.getClassTwoData(token, "", areaId);
            getMessage();
            cityName.setText(sp.getString(MyRes.city2, "") + "");
            sp.edit().putString("weizhi","").commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            ColorState.setWindowStatusBarColorBlue(getActivity(), Color.parseColor("#ffffff"));
            String string = sp.getString("myDdJump", "null");

            String gouwuche = sp.getString("购物车", "");
            if (string.equals("gengduo1")) {
                ByNew();
            } else if (string.equals("gengduo2")) {
                ByPro();
            }

            if (!gouwuche.equals("")){
                allData.clear();
                adapter3.notifyDataSetChanged();
                present.getYouBianData(token, classTwoId, classOneId, state, orderBy, "", page, lng, lat, areaId);
            }

            sp.edit().putString("myDdJump", "null").commit();
            sp.edit().putString("购物车", "").commit();


        }
    }


    //接口回调，更新购物车数据
    private GetCarNum getCarNum;

    public void setGetCarNum(GetCarNum getCarNum) {
        this.getCarNum = getCarNum;
    }

    public interface GetCarNum{
        void getCarNum(int num);
    }
}
