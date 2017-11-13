package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.GouWuCheAdapter1;
import com.bm.chengshiyoutian.youlaiwang.adapter.MyShouYeBannerAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static com.bm.chengshiyoutian.youlaiwang.R.id.dd;
import static com.bm.chengshiyoutian.youlaiwang.R.id.ll_kongGouWuChe;
import static com.bm.chengshiyoutian.youlaiwang.R.id.lv;
import static com.bm.chengshiyoutian.youlaiwang.R.id.start;
import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;
import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Created by Administrator on 2017/9/18.
 * 用于显示购物车数据
 */


public class ShoppingCarFragment extends Fragment implements IMainView {

    private String Authorization = "";
    private Present present;
    private SharedPreferences sp;
    private List<GouWuCheBean.DataBeanX.DataBean> mData = new ArrayList<>();
    private List<GouWuCheBean.DataBeanX.DataBean> allData = new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dd_fragment_shopcar, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);
        Authorization = "Bearer " + sp.getString(MyRes.TOKEN, "");
        if (sp.getString(MyRes.TOKEN, "").equals("")) {
            ShowToast.showToast("请登陆");
            startActivity(new Intent(getActivity(), Login_ddActivity.class));
            return;
        }

        present = new Present(this);
        present.getCatData(Authorization);//获取购物车数据
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

    //获取购物车数据
    @Override
    public void getLogin(String s) {
        Gson gson = new Gson();
        try {
            GouWuCheBean gouWuCheBean = gson.fromJson(s, GouWuCheBean.class);
            int code = gouWuCheBean.getCode();
            if (code != 200) {
                ShowToast.showToast(gouWuCheBean.getMsg());
                return;
            }

            mData = gouWuCheBean.getData().getData();
            allData.addAll(mData);

        } catch (JsonSyntaxException e) {
            ShowToast.showToast("数据解析异常");
        }

    }

    //删除购物车数据
    @Override
    public void getUpDate(String s) {
        Log.i("dd", "删除购物车" + s);
    }
}
