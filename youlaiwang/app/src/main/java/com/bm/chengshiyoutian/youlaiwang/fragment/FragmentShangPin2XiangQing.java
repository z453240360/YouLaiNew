package com.bm.chengshiyoutian.youlaiwang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GlideImageLoader;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.bean.XiangQingShangPinBean;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.youth.banner.Banner;

import java.util.List;


public class FragmentShangPin2XiangQing extends Fragment {



    private TextView textView;
    private Banner banner;
    public static FragmentShangPin2XiangQing newInstance(String param1) {
        FragmentShangPin2XiangQing fragment = new FragmentShangPin2XiangQing();

        return fragment;
    }

    public FragmentShangPin2XiangQing() {

    }

    String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangpinxq2, container, false);
//token 不是token  是商品id
        token = getActivity().getIntent().getStringExtra("token");
        getData();
        initView(view);
        return view;
    }

    private void getData() {
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/" + token + "/detail");
        CallServer.getInstance().add(112, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                try {
                    XiangQingShangPinBean xiangQingShangPinBean = GsonUtils.getInstance().fromJson((String) response.get(), XiangQingShangPinBean.class);
                    List<String> goods_images = (List<String>) xiangQingShangPinBean.getData().getGoods_detail_images();
                    textView.setText(xiangQingShangPinBean.getData().getGoods_content());

                    //设置图片加载器
                    banner.setImageLoader(new GlideImageLoader(getContext()));
                    //设置图片集合
                    banner.setImages(goods_images);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();

                } catch (Exception e) {
                    ShowToast.showToast("数据非法");
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


    private void initView(View view) {
        textView = (TextView) view.findViewById(R.id.textView);
        banner = (Banner) view.findViewById(R.id.banner);
    }
}