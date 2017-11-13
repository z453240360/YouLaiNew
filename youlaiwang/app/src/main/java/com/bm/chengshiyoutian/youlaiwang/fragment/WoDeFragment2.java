package com.bm.chengshiyoutian.youlaiwang.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.DialogUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.GlideCircleTransform;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.ChangJianWenTiActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.DianPuShouCangActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.GouMaiDianPuActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.GouMaiShangPinActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.JuBaoJiLuActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.SettingActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.WoDeDingDanActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.XiTongTiXingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.WoDeBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.BuyRecodeActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.GouMaiHestoryActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.HistoryActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.ColorState;
import com.bumptech.glide.Glide;
import com.google.gson.JsonSyntaxException;
import com.jaeger.library.StatusBarUtil;
import com.jauker.widget.BadgeView;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bm.chengshiyoutian.youlaiwang.R.id.iv_touxiang;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_gongyingshang;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_goumaidianpu;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_goumaishangpin;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_name;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_shoucang;

/**
 * Created by Administrator on 2017/9/12.
 */

public class WoDeFragment2 extends Fragment {
    @Bind(tv_name)
    TextView tvName;
    @Bind(iv_touxiang)
    ImageView ivTouxiang;
    @Bind(R.id.iv_setting)
    ImageView ivSetting;
    @Bind(R.id.iv_emessage)
    ImageView ivEmessage;
    @Bind(R.id.iv_dian)
    TextView ivDian;
    @Bind(R.id.iv_daifukuan)
    ImageView ivDaifukuan;
    @Bind(R.id.iv_daifahuo)
    ImageView ivDaifahuo;
    @Bind(R.id.iv_daishouhuo)
    ImageView ivDaishouhuo;
    @Bind(R.id.iv_daipingjia)
    ImageView ivDaipingjia;
    @Bind(tv_shoucang)
    TextView tvShoucang;
    @Bind(tv_gongyingshang)
    TextView tvGongyingshang;
    @Bind(tv_goumaishangpin)
    TextView tvGoumaishangpin;
    @Bind(tv_goumaidianpu)
    TextView tvGoumaidianpu;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.ll_wodedingdan)
    LinearLayout llWodedingdan;
    @Bind(R.id.ll_daifukuan)
    LinearLayout llDaifukuan;
    @Bind(R.id.ll_daifahuo)
    LinearLayout llDaifahuo;
    @Bind(R.id.ll_daishouhuo)
    LinearLayout llDaishouhuo;
    @Bind(R.id.ll_daipingjia)
    LinearLayout llDaipingjia;
    @Bind(R.id.ll_shangpin_shoucang)
    LinearLayout llShangpinShoucang;
    @Bind(R.id.ll_dianpu_shoucang)
    LinearLayout llDianpuShoucang;
    @Bind(R.id.ll_goumaishangpin)
    LinearLayout llGoumaishangpin;
    @Bind(R.id.ll_goumaidianpu)
    LinearLayout llGoumaidianpu;
    @Bind(R.id.ll_changjianwenti)
    LinearLayout llChangjianwenti;
    @Bind(R.id.ll_kefudianhua)
    LinearLayout llKefudianhua;
    @Bind(R.id.ll_jubaotousu)
    LinearLayout llJubaotousu;
    @Bind(R.id.ll_head)
    LinearLayout llHead;

    private SharedPreferences sp;
    private View view;
    private BadgeView badgeView1, badgeView2, badgeView3, badgeView4;

    private String token;
    private WoDeBean woDeBean;
    private Intent intent;

    public static WoDeFragment2 newInstance(String param1) {
        WoDeFragment2 fragment = new WoDeFragment2();
        return fragment;
    }

    public WoDeFragment2() {

    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ColorState.setWindowStatusBarColorBlue(getActivity(),Color.BLUE);
        View view = inflater.inflate(R.layout.fragment_wode2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);

        badgeView1 = new BadgeView(getActivity());
        badgeView2 = new BadgeView(getActivity());
        badgeView3 = new BadgeView(getActivity());
        badgeView4 = new BadgeView(getActivity());

        badgeView1.setBackground(9,Color.parseColor("#ff783a"));
        badgeView2.setBackground(9,Color.parseColor("#ff783a"));
        badgeView3.setBackground(9,Color.parseColor("#ff783a"));
        badgeView4.setBackground(9,Color.parseColor("#ff783a"));

        getData();

        ivTouxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();

                } else {
                    intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({tv_name, iv_touxiang,
            R.id.iv_setting,
            R.id.iv_emessage,
            R.id.iv_dian,
            R.id.tv_shoucang,
            R.id.tv_gongyingshang,
            R.id.tv_goumaishangpin,
            R.id.tv_goumaidianpu,
            R.id.textView5,
            R.id.textView6,
            R.id.ll_wodedingdan,
            R.id.ll_dianpu_shoucang,
            R.id.ll_daifahuo,
            R.id.ll_daishouhuo,
            R.id.ll_daipingjia,
            R.id.ll_daifukuan,
            R.id.ll_shangpin_shoucang,
            R.id.ll_goumaishangpin,
            R.id.ll_goumaidianpu,
            R.id.ll_jubaotousu,
            R.id.ll_kefudianhua,
            R.id.ll_changjianwenti
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_changjianwenti:
                intent = new Intent(getActivity(), ChangJianWenTiActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
                break;
            case R.id.ll_kefudianhua:

                View view1 = View.inflate(getActivity(), R.layout.dialog_kefu, null);
                final AlertDialog alertDialog = DialogUtils.ShowDialog2(view1, getActivity());
                view1.findViewById(R.id.dialog_dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                view1.findViewById(R.id.dialog_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + woDeBean.getData().getSiteMobile()));
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                break;
            case R.id.ll_jubaotousu:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), JuBaoJiLuActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }

                break;
            case R.id.ll_goumaidianpu://购买店铺
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), GouMaiDianPuActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }

                break;
            case R.id.ll_goumaishangpin://购买商品
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), HistoryActivity.class);

                    intent.putExtra("token", token);
                    startActivity(intent);
                }

                break;
            case R.id.ll_daishouhuo:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), WoDeDingDanActivity.class);
                    intent.putExtra("current", 3);
                    startActivity(intent);
                }
                break;

            case R.id.ll_shangpin_shoucang://商品收藏
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), BuyRecodeActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }

                break;
            case R.id.ll_daifukuan:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), WoDeDingDanActivity.class);
                    intent.putExtra("current", 1);
                    startActivity(intent);
                }
                break;
            case R.id.ll_daipingjia:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), WoDeDingDanActivity.class);
                    intent.putExtra("current", 4);
                    startActivity(intent);
                }
                break;
            case R.id.ll_wodedingdan:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();

                } else {

                    intent = new Intent(getActivity(), WoDeDingDanActivity.class);
                    intent.putExtra("current", 0);
                    startActivity(intent);
                }


                break;

            case R.id.ll_daifahuo:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), WoDeDingDanActivity.class);
                    intent.putExtra("current", 2);
                    startActivity(intent);
                }

                break;
            case R.id.ll_dianpu_shoucang://关注供应商
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), DianPuShouCangActivity.class);
                    startActivity(intent);
                }

                break;

//            case tv_name:
//                break;
//            case iv_touxiang:
//                break;

            case R.id.iv_setting:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();

                } else {
                    intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.iv_emessage:

                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), XiTongTiXingActivity.class);
                    startActivity(intent);
                }

                break;

            case tv_shoucang:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), BuyRecodeActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }

                break;
            case tv_gongyingshang:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), DianPuShouCangActivity.class);
                    startActivity(intent);
                }
                break;
            case tv_goumaishangpin:

                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), HistoryActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
                break;
            case tv_goumaidianpu:
                if ("kong".equals(sp.getString(MyRes.MY_TOKEN, "kong"))) {
                    login_dd();
                } else {
                    intent = new Intent(getActivity(), GouMaiDianPuActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
                break;
        }
    }

    private void login_dd() {
        ShowToast.showToast("请登录");
        intent = new Intent(getActivity(), Login_ddActivity.class);
        intent.putExtra("tag", "two");
        startActivity(intent);
    }

    private void getData() {

        token = sp.getString(MyRes.TOKEN, "");

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/index");
        stringRequest.addHeader("Authorization", "Bearer " + token);
        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

                try {
                    woDeBean = GsonUtils.getInstance().fromJson((String) response.get(), WoDeBean.class);

                    badgeView1.setTargetView(ivDaifukuan);
                    badgeView1.setBadgeCount(woDeBean.getData().getPay_order_num());
                    badgeView2.setTargetView(ivDaifahuo);
                    badgeView2.setBadgeCount(woDeBean.getData().getDeliver_num());
                    badgeView3.setTargetView(ivDaishouhuo);
                    badgeView3.setBadgeCount(woDeBean.getData().getReceipt_num());
                    badgeView4.setTargetView(ivDaipingjia);
                    badgeView4.setBadgeCount(woDeBean.getData().getComment_num());

                    tvShoucang.setText(woDeBean.getData().getCollect_goods() + "");
                    tvGongyingshang.setText(woDeBean.getData().getCollect_store() + "");
                    tvGoumaishangpin.setText(woDeBean.getData().getBug_goods() + "");
                    tvGoumaidianpu.setText(woDeBean.getData().getBug_store() + "");
                    tvName.setText(woDeBean.getData().getUser_nicename());
                    Glide.with(getActivity())
                            .load(woDeBean.getData().getAvatar())
                            .transform(new GlideCircleTransform(getActivity()))
                            .into(ivTouxiang);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    badgeView1.setBadgeCount(0);
                    badgeView2.setBadgeCount(0);
                    badgeView3.setBadgeCount(0);
                    badgeView4.setBadgeCount(0);
                    tvShoucang.setText(0 + "");
                    tvGongyingshang.setText(0 + "");
                    tvGoumaishangpin.setText(0 + "");
                    tvGoumaidianpu.setText(0 + "");
                    ivTouxiang.setImageResource(R.drawable.t);
                    tvName.setText("");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==201){
            sp = getActivity().getSharedPreferences(MyRes.CONFIG, 0);
            getData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            ColorState.setWindowStatusBarColorBlue(getActivity(),Color.parseColor("#ffffff"));
            getData();
        }
    }
}
