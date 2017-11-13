package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.DeclareAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.DeclareBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;



/**
 * 交油记录 实收
 * Created by jayen on 16/1/13.
 */
public class ReceivedFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2<ListView>, View.OnClickListener {

    View view;
    @Bind(R.id.lv)
    PullToRefreshListView lv;
    @Bind(R.id.tv_declare_num)
    TextView tvDeclareNum;
    @Bind(R.id.tv_junk_num)
    TextView tvJunkNum;
    @Bind(R.id.bt_share)
    Button btShare;

    private int pageIndex = 1;
    private String pageSize = "10";
    private int pageCount = 1;

    private ArrayList<DeclareBean> declareList;
    /**
     * 临时集合
     */
    private ArrayList<DeclareBean> tempList;
    private ProgressDialog dialog;
    private DeclareAdapter adapter;
    private String address;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_received, container, false);
            ButterKnife.bind(this, view);
            init();

        }
        return view;
    }

    private void init() {
        ShareSDK.initSDK(getActivity());

        declareList = new ArrayList<>();
        dialog = new ProgressDialog(getActivity());
        loadOilTotal();
        loadListData(1);
        adapter = new DeclareAdapter(getActivity(), declareList, R.layout.item_declare);
        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
        btShare.setOnClickListener(this);
    }

    private void loadListData(int type) {
        if (type == 1 && dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("_id", MyApplication.getInstance().getUser().id);
        params.put("States", "2");
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize);
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetOilList", params, config, this);
    }


    /**
     * 获取交油总量和垃圾总量
     */
    private void loadOilTotal() {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        params.put("_id", MyApplication.getInstance().getUser().id); //餐厅id
        params.put("States", "2");
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetOilTotal", params, config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        lv.onRefreshComplete();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        btShare.setEnabled(true);
        switch (entity.getKey()) {
            case 0: //获取交油总量和垃圾总量
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.optInt("status")) {
                        JSONObject object = jsonObject.optJSONObject("data").optJSONArray("ds").optJSONObject(0);
                        String record = object.optString("Record");
                        tvDeclareNum.setText("null".equals(record)?"0":record);
                        String cclj = object.optString("Cclj");
                        tvJunkNum.setText("null".equals(cclj)?"0":cclj);
                    } else {
                        MyToastUtils.show(getActivity(), "数据异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case 1: //获取数据列表
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.optInt("status")) {
                        pageCount = jsonObject.getInt("recordCount");
                        if (!(pageIndex < pageCount)) {
                            lv.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
                        } else {
                            lv.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("ds");
                        int length = jsonArray.length();
                        tempList = new ArrayList<DeclareBean>();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            String oilNum = object.optString("Record");
                            String junkNum = object.optString("Cclj");
                            String time = object.optString("AddTime");
                            tempList.add(new DeclareBean(oilNum, junkNum, time));
                        }
                        declareList.addAll(tempList);
                        adapter.notifyDataSetChanged();
                    } else {
                        MyToastUtils.show(getActivity(), "数据异常");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (jsonObject.optInt("status") == 0) {
                        address = jsonObject.optString("address");
                        ShowPopWindow();
                    } else {
                        MyToastUtils.show(getActivity(),"分享失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        declareList.clear();
        pageIndex = 1;
        adapter.notifyDataSetChanged();
        loadListData(2);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (pageIndex < pageCount) {
            pageIndex = pageIndex+1;
            loadListData(2);
        }

    }

    @InjectHttpErr
    private void err(ResponseEntity entity) {
        lv.onRefreshComplete();
        btShare.setEnabled(true);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        MyToastUtils.show(getActivity(), getString(R.string.intnet_err));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_share:
                btShare.setEnabled(false);
                getsharUrl();//获取分享uri
                break;

            case R.id.tv_weibo:
                showShare(SinaWeibo.NAME);
                params.alpha = 1f;//设置参数的透明度
                window.setAttributes(params);
                popupWindow.dismiss();
                break;

            case R.id.tv_qzone:
                showShare(QZone.NAME);
                params.alpha = 1f;//设置参数的透明度
                window.setAttributes(params);
                popupWindow.dismiss();
                break;

            case R.id.tv_weixin:
                showShare(Wechat.NAME);
                params.alpha = 1f;//设置参数的透明度
                window.setAttributes(params);
                popupWindow.dismiss();
                break;

            case R.id.tv_weixin_friend:
                showShare(WechatMoments.NAME);
                params.alpha = 1f;//设置参数的透明度
                window.setAttributes(params);
                popupWindow.dismiss();
                break;

            case R.id.bt_canecl:
                params.alpha = 1f;//设置参数的透明度
                window.setAttributes(params);
                popupWindow.dismiss();
                break;

            default:
                 break;
        }
    }


    private PopupWindow popupWindow;
    private WindowManager.LayoutParams params;
    private Window window;

    private void ShowPopWindow() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_share_window, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,-2);
        popupWindow.setFocusable(false);
        window = getActivity().getWindow();
        params = window.getAttributes();
        params.alpha = 0.5f;//设置参数的透明度
        window.setAttributes(params);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        TextView tv_weibo = (TextView) view.findViewById(R.id.tv_weibo);
        TextView tv_weixin = (TextView) view.findViewById(R.id.tv_weixin);
        TextView tv_qzone = (TextView) view.findViewById(R.id.tv_qzone);
        TextView tv_weixinFriend = (TextView) view.findViewById(R.id.tv_weixin_friend);
        Button bt_canecl = (Button) view.findViewById(R.id.bt_canecl);
        tv_weibo.setOnClickListener(this);
        tv_weixin.setOnClickListener(this);
        tv_qzone.setOnClickListener(this);
        tv_weixinFriend.setOnClickListener(this);
        bt_canecl.setOnClickListener(this);
    }


    /**
     * 获取分享uri
     */
    private void getsharUrl() {
        btShare.setEnabled(false);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("_id", MyApplication.getInstance().getUser().id);
        InternetConfig config = new InternetConfig();
        config.setKey(2);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "ShareRest", params, config, this);
    }

    private void showShare(String name) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("油来油往的分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(address);
        // text是分享文本，所有平台都需要这个字段
        if (SinaWeibo.NAME.equals(name)) {
            oks.setText(getString(R.string.app_name)+"的分享"+address);
        } else {
            oks.setText(address);
        }
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(address);
        oks.setImageUrl("http://tp4.sinaimg.cn/2465146235/180/5747613099/1");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(address);
        oks.setDialogMode();
        oks.disableSSOWhenAuthorize();
        if (name != null) {
            oks.setPlatform(name);
        }
        // 去自定义不同平台的字段内容
        oks.show(getActivity());
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
