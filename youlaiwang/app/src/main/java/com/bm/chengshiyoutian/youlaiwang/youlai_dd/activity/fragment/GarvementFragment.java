package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.NewsDetialActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.ZhuYe_DongTaiAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ZuiXinDongTaiBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.IMainView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.model.Present;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class GarvementFragment extends Fragment implements IMainView{

    private boolean isAll = true;
    private Present present;
    private int page=1;
    private String classID = "2";
    private ZhuYe_DongTaiAdapter adapter_dongtai;
    private List<ZuiXinDongTaiBean.DataBeanX.DataBean> DongTaiList = new ArrayList<>();
    private List<ZuiXinDongTaiBean.DataBeanX.DataBean> DongTaiList2 = new ArrayList<>();

    private XRecyclerView recylerView;
    private LinearLayoutManager manager_dongtai;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dd_fragment_fashion,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recylerView= (XRecyclerView) view.findViewById(R.id.xrv);
        present = new Present(this);
        present.counselMinute(page+"",classID);// 新闻列表

        adapter_dongtai = new ZhuYe_DongTaiAdapter(DongTaiList2,getActivity());
        manager_dongtai = new LinearLayoutManager(getActivity());
        recylerView.setLayoutManager(manager_dongtai);
        recylerView.setAdapter(adapter_dongtai);
        recylerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
        recylerView.setLoadingMoreProgressStyle(ProgressStyle.LineScalePulseOutRapid);

        //新闻公告，下拉刷新，上拉加载
        recylerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                DongTaiList2.clear();
                present.counselMinute(page+"",classID);
                recylerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                present.counselMinute(page+"",classID);
                recylerView.smoothScrollToPosition(DongTaiList2.size()-1);
                recylerView.loadMoreComplete();
            }
        });

        adapter_dongtai.setOnItemClickListener(new ZhuYe_DongTaiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                Intent intent = new Intent(getActivity(), NewsDetialActivity.class);
                intent.putExtra("title","政府公告");
                intent.putExtra("newsId",DongTaiList2.get(pos).getArticle_id());
                startActivity(intent);
            }

            @Override
            public void onCarClicked() {

            }
        });
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

    @Override
    public void getLogin(String s) {
        Log.i("dd", "getLogin: "+s);
        Gson g = new Gson();
        ZuiXinDongTaiBean zuiXinDongTaiBean = g.fromJson(s, ZuiXinDongTaiBean.class);
        ZuiXinDongTaiBean.DataBeanX data = zuiXinDongTaiBean.getData();
        DongTaiList= data.getData();
        if (DongTaiList.size()==0){
            if (isAll){
                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                isAll = false;
            }

            return;
        }

        DongTaiList2.addAll(DongTaiList);
        adapter_dongtai.notifyDataSetChanged();
    }

    @Override
    public void getUpDate(String s) {

    }
}
