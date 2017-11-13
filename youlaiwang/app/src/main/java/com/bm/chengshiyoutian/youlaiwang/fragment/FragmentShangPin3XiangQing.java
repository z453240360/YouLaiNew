package com.bm.chengshiyoutian.youlaiwang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.adapter.PingJiaAdapter;
import com.bm.chengshiyoutian.youlaiwang.bean.XiangXing3Bean;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;




public class FragmentShangPin3XiangQing extends Fragment {


    private PullToRefreshListView lv;

    public static FragmentShangPin3XiangQing newInstance(String param1) {
        FragmentShangPin3XiangQing fragment = new FragmentShangPin3XiangQing();

        return fragment;
    }

    public FragmentShangPin3XiangQing() {

    }

    String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangpinxq3, container, false);
        //token 不是token  是商品id
        token = getActivity().getIntent().getStringExtra("token");
        initView(view);


        getData();
        return view;
    }

    private void getData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/" + token + "/comment");
        CallServer.getInstance().add(123, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                List<XiangXing3Bean.DataBeanX.DataBean> data = GsonUtils.getInstance().fromJson((String) response.get(), XiangXing3Bean.class).getData().getData();
                lv.setAdapter(new PingJiaAdapter(data, getContext()));

            }

            @Override
            public void onFailed(int what, Response response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    private void initView(View view) {
        lv = (PullToRefreshListView) view.findViewById(R.id.lv);
    }
}