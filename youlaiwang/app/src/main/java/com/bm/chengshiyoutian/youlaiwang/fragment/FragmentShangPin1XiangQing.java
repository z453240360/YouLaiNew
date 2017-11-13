package com.bm.chengshiyoutian.youlaiwang.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.GlideCircleTransform;
import com.bm.chengshiyoutian.youlaiwang.Utils.GlideImageLoader;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinGuiGeXiangQingAdapter1;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinGuiGeXiangQingAdapter2;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinXQ1DaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPingXIangQIng1BuDaiPingLun;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.photoview.PhotoView;
import com.bm.chengshiyoutian.youlaiwang.view.MylistView;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.DD_bean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view.MyLagerImageView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;
import static com.nostra13.universalimageloader.core.ImageLoader.TAG;


public class FragmentShangPin1XiangQing extends Fragment {


    private Banner banner;
    private ProgressBar pb;
    private TextView name,tv_duoduo;
    private TextView goods_subtitle;
    private MylistView lv;
    private TextView tv_miao;
    private TextView tv_pingjia;
    private TextView tv_count;
    private TextView tv_name;
    private LinearLayout ll_you;
    private ImageView iv;
    private TextView tv_time;
    private TextView tv_content;
    private Context mContext;
    private ShangPinGuiGeXiangQingAdapter1 adapter1;
    private SharedPreferences sp;
    private List<MyLagerImageView> list=new ArrayList<>();
    private MyAdapter adapter;

    public static FragmentShangPin1XiangQing newInstance(String param1) {
        FragmentShangPin1XiangQing fragment = new FragmentShangPin1XiangQing();
        return fragment;
    }

    public FragmentShangPin1XiangQing() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangpinxq1, container, false);
        sp = getContext().getSharedPreferences(MyRes.CONFIG, 0);
        mContext = getContext();
        initView(view);
        getData();
        adapter = new MyAdapter();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        String token = getActivity().getIntent().getStringExtra("token");
        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/goods/" + token);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucceed(int what, Response response) {


                if (adapter==null){
                    adapter= new MyAdapter();
                }else {

                    list.clear();
                    adapter.notifyDataSetChanged();
                }


                try {
                    ll_you.setVisibility(View.VISIBLE);
                    final List<String> goods_images = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinXQ1DaiPingLun.class).getData().getGoods_images();
                    final ShangPinXQ1DaiPingLun.DataBean data = GsonUtils.getInstance().fromJson((String) response.get(), ShangPinXQ1DaiPingLun.class).getData();
                    List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> spec = data.getSpec();


                    if (change!=null){
                        change.getData(0,spec);
                    }


                    adapter1 = new ShangPinGuiGeXiangQingAdapter1(getContext(), data.getGoods_id() + "", spec, sp.getString(MyRes.MY_TOKEN, "kong"));

                    adapter1.setMyListener(new ShangPinGuiGeXiangQingAdapter1.OnTextChanged() {
                        @Override
                        public void getTextNumber(String s) {
                            ShowToast.showToast(s);
                        }

                        @Override
                        public void getMap(int postion,List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> goods_spec) {
                            if (change!=null){
                                change.getData(postion,goods_spec);
                            }
                        }
                    });

                    lv.setAdapter(adapter1);

                    name.setText(data.getGoods_name());
                    goods_subtitle.setText(data.getGoods_subtitle());
                    if (data.getGoods_subtitle().equals("")) {
                        tv_miao.setVisibility(View.INVISIBLE);

                    }
                    tv_count.setText("评价" + "(" + data.getEvaluation_count() + ")");

                    //设置评价
                    Glide.with(getActivity()).load(data.getComment().getAvatar())

                            .transform(new GlideCircleTransform(getActivity()))

                            .into(iv);
                    tv_name.setText(data.getComment().getUser_nicename());
                    tv_content.setText(data.getComment().getComment_content());
                    tv_time.setText(data.getComment().getComment_addtime());
                    //设置图片加载器
                    banner.setImageLoader(new GlideImageLoader(getContext()));
                    //设置图片集合
                    banner.setImages(goods_images);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
                    banner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {

                            if (goods_images.size()==0){
                                return;
                            }

                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_largeimg,null);
                            Dialog dialog = new Dialog(getActivity(),R.style.dialog2);
                            dialog.setContentView(view);




                            ViewPager viewPage = (ViewPager) view.findViewById(R.id.viewpage);
                            final TextView textView = (TextView) view.findViewById(R.id.number);



                            for (int i = 0; i < goods_images.size(); i++) {
                                MyLagerImageView imageView = new MyLagerImageView(getActivity());
                                imageView.setLargeImg(getActivity(),goods_images.get(i));
                                list.add(imageView);
                            }

                            viewPage.setAdapter(adapter);
                            final int size = goods_images.size();

                            textView.setText(1+"/"+size);

                            viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    textView.setText((position+1)+"/"+size);
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });

                            dialog.show();
                        }
                    });
                    tv_pingjia.setVisibility(View.GONE);
                } catch (JsonSyntaxException e) {

                }
            }

            @Override
            public void onFailed(int what, Response response) {
                ShowToast.showToast("联网失败");
            }

            @Override
            public void onFinish(int what) {
                pb.setVisibility(View.GONE);

            }
        });

    }

    private void initView(View view) {
        banner = (Banner) view.findViewById(R.id.banner);
        pb = (ProgressBar) view.findViewById(R.id.pb);
        name = (TextView) view.findViewById(R.id.name);
        goods_subtitle = (TextView) view.findViewById(R.id.goods_subtitle);
        lv = (MylistView) view.findViewById(R.id.lv);
        tv_miao = (TextView) view.findViewById(R.id.tv_miao);
        tv_pingjia = (TextView) view.findViewById(R.id.tv_pingjia);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        ll_you = (LinearLayout) view.findViewById(R.id.ll_you);
        iv = (ImageView) view.findViewById(R.id.iv);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_duoduo = (TextView) view.findViewById(R.id.tv_duoduo);

        tv_duoduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change.getJump();
            }
        });

    }

    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list==null?0:list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }


    private onChange change;

    public void setChange(onChange change) {
        this.change = change;
    }

    public interface onChange{
        void getJump();
        void getData(int postion,List<ShangPinXQ1DaiPingLun.DataBean.SpecBean> goods_spec);
    }

}