package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.XiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.CommentAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.CommentBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 餐厅详情
 * Created by youj on 2016/1/14.
 */
public class ResturantDetaisActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.photo)
    RoundImageView photo;
    @Bind(R.id.app_ratingbar)
    RatingBar appRatingbar;
    @Bind(R.id.tv_new_oil_num)
    TextView tvNewOilNum;
    @Bind(R.id.tv_total_oil_num)
    TextView tvTotalOilNum;
    @Bind(R.id.tv_new_junk_num)
    TextView tvNewJunkNum;
    @Bind(R.id.tv_total_junk_num)
    TextView tvTotalJunkNum;
    @Bind(R.id.tv_current_scale)
    TextView tvCurrentScale;
    @Bind(R.id.tv_history_scale)
    TextView tvHistoryScale;
    @Bind(R.id.ll_more_comment)
    LinearLayout llMoreComment;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_cook_type)
    TextView tvCookType;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.tv_qr_code)
    TextView tvQrCode;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.textView7)
    TextView textView7;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv_restaurant_name)
    TextView tvRestaurantName;
    private CommentAdapter adapter;

    private ArrayList<CommentBean> commentList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_resturant_detais);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("餐厅详情");
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        id = getIntent().getStringExtra("id");
        getDetais(id);
        getCommentList(id);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.home);
        ivRight.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        llMoreComment.setOnClickListener(this);
        tvQrCode.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        adapter = new CommentAdapter(this, commentList, R.layout.item_comment);
        lv.setAdapter(adapter);
    }

    /**
     * 获取餐厅评论列表
     *
     * @param id
     */
    private void getCommentList(String id) {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        params.put("id", id);
        params.put("pageIndex", "1");
        params.put("pageSize", "3");
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetCommentList", params, config, this);
    }


    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    if (0 == jsonObject.getInt("status")) {
                        JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("ds");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            String content = object.optString("content");
                            String time = object.optString("add_time");
                            commentList.add(new CommentBean(content, time));
                        }
                        adapter.setData(commentList);
                    } else {
                        MyToastUtils.show(this, "数据获取失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 0:
                try {
                    JSONObject object = new JSONObject(entity.getContentAsString());
                    if (object.optInt("status") == 0) {
                        JSONObject jsonObject = object.optJSONArray("data").getJSONObject(0);
                        String retaurantName = jsonObject.optString("title");
                        String img_url = jsonObject.optString("img_url");
                        String retaurantAddress = jsonObject.optString("seo_title");
                        String phone = jsonObject.optString("seo_keywords"); //电话
                        String cookType = jsonObject.optString("category_name"); //菜系名
                        String internal = jsonObject.optString("score");
                        String xing = jsonObject.optString("xing");//星级
                        String zxjy = jsonObject.optString("zxjy");//最新交油
                        String jyzl = jsonObject.optString("jyzl");//交油总量
                        String zxcclj = jsonObject.optString("zxcclj");//最新餐厨垃圾
                        String ccljzl = jsonObject.optString("ccljzl");//餐厨垃圾总量
                        String dqzhbl = jsonObject.optString("dqzhbl");//当前转换比例
                        String lszhbl = jsonObject.optString("lszhbl");//历史转换比例
                        tvRestaurantName.setText(retaurantName);
                        ImageLoader.getInstance().displayImage(img_url, photo, MyApplication.getInstance().getOptions());
                        tvAddress.setText(retaurantAddress);
                        tvPhone.setText(phone);
                        tvCookType.setText(cookType);
                        tvIntegral.setText(TextUtils.isEmpty(internal) ? "0" : internal);
                        tvNewOilNum.setText(zxjy);
                        tvNewJunkNum.setText(zxcclj);
                        tvTotalOilNum.setText(TextUtils.isEmpty(jyzl) ? "0" : jyzl);
                        tvTotalJunkNum.setText(TextUtils.isEmpty(ccljzl) ? "0" : ccljzl);
                        tvCurrentScale.setText(dqzhbl);
                        tvHistoryScale.setText(lszhbl);
                        appRatingbar.setRating(Float.parseFloat(xing));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }


    }

    @InjectHttpErr
    public void err(ResponseEntity entity) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    private void getDetais(String id) {
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        params.put("_id", id);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetRestaurantDetail", params, config, this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.iv_right:
                startActivity(new Intent(this, XiangQingActivity.class));
                finish();
                break;

            case R.id.tv_phone:
                callPhone();
                break;

            case R.id.tv_qr_code: //二维码
                Intent intent2 = new Intent(this, QRCodeActivity.class);
                intent2.putExtra("id", this.id);
                startActivity(intent2);
                break;

            case R.id.ll_more_comment: //用户评论
                if (commentList.size() == 0) {
                    MyToastUtils.show(this, "没有更多评论");
                    return;
                }
                Intent intent = new Intent(this, UserCommentActivity.class);
                intent.putExtra("id", this.id);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    /**
     * 弹出打电话的dialog
     */
    private void callPhone() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_call_phone, null);
        final PopupWindow pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, -2);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        final WindowManager.LayoutParams params = getWindow().getAttributes();//创建当前界面的一个参数对象
        params.alpha = 0.5f;//设置参数的透明度
        getWindow().setAttributes(params);
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 15);

        final TextView phone = (TextView) view.findViewById(R.id.tv_phone);
        final String phoneNum = tvPhone.getText().toString();
        phone.setText(phoneNum);

        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                if (ActivityCompat.checkSelfPermission(ResturantDetaisActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(ResturantDetaisActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
                    return;
                }
                startActivity(intent);
                pop.dismiss();
                params.alpha = 1f;//设置参数的透明度
                getWindow().setAttributes(params);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                params.alpha = 1f;//设置参数的透明度
                getWindow().setAttributes(params);
            }
        });

    }
}
