package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Created by Administrator on 2017/9/13.
 */

public class DingDanFragment extends Fragment implements IMainView {


    @Bind(R.id.xr_dingdan)
    XRecyclerView xrDingdan;

    private Present present;
    private SharedPreferences sp;
    private String token;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dingdan, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sp = getActivity().getSharedPreferences(MyRes.CONFIG,0);
        token = sp.getString(MyRes.MY_TOKEN, "");
        present = new Present(this);

        present.getOrder(token,page,5);
    }

    //获取订单信息
    @Override
    public void getCode(String s) {
        Log.i("dd", "getCode: "+s);
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

    @Override
    public void getLogin(String s) {

    }

    @Override
    public void getUpDate(String s) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
