package com.bm.chengshiyoutian.youlaiwang.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.DingDan1Adapter1;
import com.bm.chengshiyoutian.youlaiwang.bean.DingDan1Bean;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;




public class Fragment_DaiFuKuan extends Fragment {


    private PullToRefreshListView lv;
    private ImageView iv;
    private TextView mIv_text;

    public static Fragment_DaiFuKuan newInstance(String param1) {
        Fragment_DaiFuKuan fragment = new Fragment_DaiFuKuan();

        return fragment;
    }

    public Fragment_DaiFuKuan() {

    }

    SharedPreferences sp;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daifukuan, container, false);
        context = getContext();
        sp = context.getSharedPreferences(MyRes.CONFIG, 0);
        initView(view);
        page = 1;
        allData.clear();
        dingDan1Adapter1 = null;
        getData(page);

        return view;
    }

    int page = 1;
    List<DingDan1Bean.DataBeanX.DataBean> allData = new ArrayList<>();
    DingDan1Adapter1 dingDan1Adapter1;

    private void getData(int page) {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/order");
        stringRequest.add("state", 1);
        stringRequest.add("page", page);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                List<DingDan1Bean.DataBeanX.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), DingDan1Bean.class).getData().getData();
                if (data.size()==0){
                    ShowToast.showToast("已经显示全部");
                }
                allData.addAll(data);
                if (allData.size() == 0) {
                    lv.setVisibility(View.GONE);
                    iv.setVisibility(View.VISIBLE);
                    mIv_text.setVisibility(View.VISIBLE);
                } else {
                    lv.setVisibility(View.VISIBLE);
                    iv.setVisibility(View.GONE);
                    mIv_text.setVisibility(View.GONE);
                }

                if (dingDan1Adapter1 == null) {
                    dingDan1Adapter1 = new DingDan1Adapter1(sp.getString(MyRes.MY_TOKEN, ""), allData, context);
                    lv.setAdapter(dingDan1Adapter1);

                } else {

                    dingDan1Adapter1.notifyDataSetChanged();
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

    Intent intent;


    private void initView(View view) {
        iv = (ImageView) view.findViewById(R.id.iv);
        mIv_text = (TextView) view.findViewById(R.id.iv_text);
        lv = (PullToRefreshListView) view.findViewById(R.id.lv);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                allData.clear();
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                new GetDataTask().execute();
            }
        });


    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getData(page);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            lv.onRefreshComplete();
        }
    }
}