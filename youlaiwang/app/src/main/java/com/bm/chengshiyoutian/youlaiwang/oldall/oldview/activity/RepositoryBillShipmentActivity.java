package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.event.EventBus;
import com.android.pc.ioc.inject.InjectBefore;
import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.BillDataAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.EventBusBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.Bimp;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.TestPicActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/5/25 15:27
 * @Description 仓库台账销售
 */
public class RepositoryBillShipmentActivity extends Activity implements View.OnClickListener, View.OnTouchListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right_two)
    TextView tv_right_two;

    @Bind(R.id.rl_photo_view)
    RelativeLayout rl_photo_view;
    @Bind(R.id.iv_photo)
    ImageView iv_photo;
    @Bind(R.id.tv_photo_num)
    TextView tv_photo_num;
    @Bind(R.id.iv_add_photo)
    LinearLayout iv_add_photo;

    @Bind(R.id.lv_mingxi)
    ListView lv_mingxi;

    @Bind(R.id.ll_baozhiqixian)
    LinearLayout ll_baozhiqixian;

    @Bind(R.id.et_procurement_name)
    EditText et_procurement_name;
    @Bind(R.id.et_contacts_persion)
    EditText et_contacts_persion;
    @Bind(R.id.et_contacts_phone)
    EditText et_contacts_phone;

    @Bind(R.id.bt_submit)
    Button bt_submit;

    private GetImg img;
    private Bitmap bitmap;
    private List<Bitmap> bitmapData;//选择的图片bitmap
    private Intent mIntent;

    private String selectType;
    private BillDataAdapter mBillDataAdapter;
    private EventBus mMEventBus;
    private ArrayList<RepositoryBillBean> mRepositoryBillBeansOfTime;//选择保质期限后的集合

    private ProgressDialog progressDialog;

    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_repository_bill_shipment);
        ButterKnife.bind(this);
        setListener();
        init();
        mMEventBus = EventBus.getDefault();//注册eventbus
        mMEventBus.register(this);
    }

    private void init() {
        progressDialog = new ProgressDialog(this);

        initView();
        initData();
    }



    @Override
    protected void onResume() {
        super.onResume();
        showImage();
    }

    private void setListener() {
        ivLeft.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        iv_add_photo.setOnClickListener(this);
        ll_baozhiqixian.setOnClickListener(this);
        tv_right_two.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        lv_mingxi.setOnTouchListener(this);
    }

    private void initView() {
        Constants.AccountDetaiTools.isCirculatesShipment = 1;

            title.setText(getString(R.string.repositorybillsell_two));

        tv_right_two.setVisibility(ImageView.VISIBLE);
        tv_right_two.setText("下一步");
    }

    private void initData() {
        mBillDataAdapter = new BillDataAdapter(this);
        lv_mingxi.setAdapter(mBillDataAdapter);
            getGoodsClass();
    }

    private void showImage() {
        if (Bimp.drr != null && Bimp.drr.size() > 0) {
            if (bitmapData == null) {
                bitmapData = new ArrayList<>();
            }
            bitmapData.clear();
            for (int i = 0; i < Bimp.drr.size(); i++) {
                try {
                    bitmapData.add(Bimp.revitionImageSize(Bimp.drr.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bitmapData != null && bitmapData.size() > 0) {
                if (bitmapData.size() >= MyApplication.getInstance().imageNum) {
                    iv_add_photo.setVisibility(View.GONE);
                } else {
                    iv_add_photo.setVisibility(View.VISIBLE);
                }
                iv_photo.setImageBitmap(bitmapData.get(bitmapData.size() - 1));
                tv_photo_num.setText(bitmapData.size() + "张");
                rl_photo_view.setVisibility(View.VISIBLE);
            } else {
                iv_photo.setImageBitmap(null);
                iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
                tv_photo_num.setText("0张");
                iv_add_photo.setVisibility(View.VISIBLE);
                rl_photo_view.setVisibility(View.GONE);
            }
        } else {
            if (bitmapData != null) {
                bitmapData.clear();
            }
            iv_photo.setImageBitmap(null);
            iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
            tv_photo_num.setText("0张");
            iv_add_photo.setVisibility(View.VISIBLE);
            rl_photo_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                if (Bimp.drr != null) {
                    Bimp.drr.clear();
                }
                finish();
                break;
            case R.id.iv_photo://查看大图
                Constants.BIMPTYPE="0";
                if (bitmapData != null && bitmapData.size() > 0) {
                    BigImageDialog bigImageDialog = new BigImageDialog(this, Constants.BIMPTYPE,bitmapData, 0, new BigImageDialog.RefreshBitmapData() {
                        @Override
                        public void refreshData(List<Bitmap> comment_pics) {
                            refreshBitmapData(comment_pics);
                        }
                    });
                    bigImageDialog.show();
                }
                break;
            case R.id.iv_add_photo:
                selectPhotoDialog();
                break;
            case R.id.ll_baozhiqixian://选择保质期限
//                submitOrselectBaoZhiQiXian(0);
                break;
            case R.id.bt_submit://下一步
            case R.id.tv_right_two:

           if (bitmapData == null || bitmapData.size() == 0) {
            MyToastUtils.show(getApplicationContext(), getString(R.string.bill_all_good_data));
            return;
        }
                if ( Constants.AccountDetaiTools.piaojuList !=null){
                    Constants.AccountDetaiTools.piaojuList.clear();
                }
             Constants.AccountDetaiTools.piaojuList.addAll(bitmapData);

        String procurementName = et_procurement_name.getText().toString();
        String contactsPersion = this.et_contacts_persion.getText().toString();
        String contactsPhone = this.et_contacts_phone.getText().toString();

                Constants.RepositoryShipTools.procurementName = procurementName;
                Constants.RepositoryShipTools.procurementPersion = contactsPersion;
                Constants.RepositoryShipTools.procurementPhone = contactsPhone;

                startActivity(new Intent(this,NewAccountDetailActivity.class));
//                submitOrselectBaoZhiQiXian(1);

                break;
        }
    }

//    //选择保质期限或提交
//    private void submitOrselectBaoZhiQiXian(int type) {
//        ArrayList<RepositoryBillBean> billBeans = mBillDataAdapter.getData();
//        if (billBeans != null) {
//            ArrayList<RepositoryBillBean> repositoryBillBeans = MyUtils.selectHasNum(billBeans);
//            if (repositoryBillBeans != null && repositoryBillBeans.size() > 0) {
//                ArrayList<RepositoryBillBean> billBeanOfTime = MyUtils.getBillBeanOfTime(repositoryBillBeans, mRepositoryBillBeansOfTime);
//                if (type == 0) {
//                    //选择保质期限
//                    mIntent = new Intent(getApplicationContext(), QualityGuaranteePeriodActivity.class);
//                    mIntent.putParcelableArrayListExtra("billBeans", billBeanOfTime);
//                    startActivity(mIntent);
//                } else if (type == 1) {
//                    //提交
//                    if (bitmapData == null || bitmapData.size() == 0) {
//                        MyToastUtils.show(getApplicationContext(), getString(R.string.bill_all_good_data));
//                        return;
//                    }
//                    submit(billBeanOfTime);
//                }
//            } else {
//                if (type == 0) {
//                    MyToastUtils.show(getApplicationContext(), getString(R.string.select_product_ming_xi));
//                } else if (type == 1) {
//                    if (bitmapData == null || bitmapData.size() == 0) {
//                        MyToastUtils.show(getApplicationContext(), getString(R.string.bill_all_good_data));
//                        return;
//                    }
//                    submit(null);
//                }
//            }
//        }
//    }
//
//    //提交
//    private void submit(ArrayList<RepositoryBillBean> billBeanOfTime) {
//        if (bitmapData == null || bitmapData.size() == 0) {
//            MyToastUtils.show(getApplicationContext(), getString(R.string.bill_all_good_data));
//            return;
//        }
//        String procurementName = et_procurement_name.getText().toString();
//        String contactsPersion = this.et_contacts_persion.getText().toString();
//        String contactsPhone = this.et_contacts_phone.getText().toString();
//        if (billBeanOfTime != null) {
//            Gson gson = new Gson();
//            String toJson = gson.toJson(billBeanOfTime);
//            addOutstockRecord(selectType, MyUtils.bitmapStringData(bitmapData), procurementName, contactsPersion, contactsPhone, toJson);
//        } else {
//            addOutstockRecord(selectType, MyUtils.bitmapStringData(bitmapData), procurementName, contactsPersion, contactsPhone, "");
//
//        }
//    }

    private void refreshBitmapData(List<Bitmap> comment_pics) {
        //删除图片后做更新处理
        bitmapData = comment_pics;
        if (bitmapData != null && bitmapData.size() > 0) {
            if (bitmapData.size() >= MyApplication.getInstance().imageNum) {
                iv_add_photo.setVisibility(View.GONE);
            } else {
                iv_add_photo.setVisibility(View.VISIBLE);
            }
            iv_photo.setImageBitmap(bitmapData.get(bitmapData.size() - 1));
            tv_photo_num.setText(bitmapData.size() + "张");
            rl_photo_view.setVisibility(View.VISIBLE);
        } else {
            iv_photo.setImageBitmap(null);
            iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
            tv_photo_num.setText("0张");
            iv_add_photo.setVisibility(View.VISIBLE);
            rl_photo_view.setVisibility(View.GONE);
        }
    }

    /**
     * 获取库存的信息
     */
    private void getStorageInfo(String typeid) {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("typeid", typeid);
        params.put("restaurantid", MyApplication.getInstance().getUser().id);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "GetStorageInfo", params, config, this);
    }

    /**
     * 添加出库记录
     */
    private void addOutstockRecord(String typeid, String bill, String purchasecompany, String contacts, String phone, String jsondetails) {
        MyUtils.setTextViewClick(false, bt_submit, tv_right_two);
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("typeid", typeid);
        params.put("restaurantid", MyApplication.getInstance().getUser().id);
        params.put("bill", bill);
        params.put("purchasecompany", purchasecompany != null ? purchasecompany : "");
        params.put("contacts", contacts != null ? contacts : "");
        params.put("phone", phone != null ? phone : "");
        params.put("jsondetails", jsondetails);
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "AddOutstockRecord", params, config, this);
    }

    /**
     * 获取商品类别
     */
    private void getGoodsClass() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        InternetConfig config = new InternetConfig();
        config.setKey(2);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "getGoodsClass", params, config, this);
    }

    @InjectHttpOk
    public void ok(ResponseEntity entity) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        String contentAsString = entity.getContentAsString();
        int key = entity.getKey();
        switch (key) {
            case 0://获取库存信息
                getKuChunData(contentAsString);
                break;
            case 1://出库
                outStockRecord(contentAsString);
                break;
            case 2://获取商品类别
                getGoodsClassData(contentAsString);
                break;
        }
    }

    @InjectHttpErr
    public void err(ResponseEntity entity) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        MyUtils.setTextViewClick(true, bt_submit, tv_right_two);
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }

    //解析库存列表
    private void getKuChunData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            String status = jsonObject.optString("status");
            if ("0".equals(status)) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                if (jsonArray != null && jsonArray.length() > 0) {
                    ArrayList<RepositoryBillBean> repositoryBillBeans = new ArrayList<>();
                    int length = jsonArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        RepositoryBillBean repositoryBillBean = new RepositoryBillBean(object.optString("GoodsName"), "0", object.optString("Unit"));
                        repositoryBillBean.productTempNum = object.optString("Count");
                        repositoryBillBeans.add(repositoryBillBean);
                    }
                    mBillDataAdapter.setData(repositoryBillBeans);
                    mBillDataAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void outStockRecord(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            String msg = jsonObject.optString("msg");
            String status = jsonObject.optString("status");
            MyToastUtils.show(getApplicationContext(), msg);
            if ("0".equals(status)) {
                if (Bimp.drr != null) {
                    Bimp.drr.clear();
                }
                mIntent = new Intent(getApplicationContext(), SuoZhengSuoPiaoRecordActivity.class);
                startActivity(mIntent);
                finish();
            } else {
                MyUtils.setTextViewClick(true, bt_submit, tv_right_two);
            }
        } catch (JSONException e) {
            MyUtils.setTextViewClick(true, bt_submit, tv_right_two);
            e.printStackTrace();
        }
    }

    //解析获取的商品类别
    private void getGoodsClassData(String contentAsString) {
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);
            JSONArray jsonArrayData = jsonObject.optJSONArray("data");
            if (jsonArrayData != null && jsonArrayData.length() > 0) {
                ArrayList<RepositoryBillBean> repositoryData = new ArrayList<>();
                int length = jsonArrayData.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObjectData = (JSONObject) jsonArrayData.get(i);
                    repositoryData.add(new RepositoryBillBean(jsonObjectData.optString("className"), "0", jsonObjectData.optString("unitType")));
                }
                mBillDataAdapter.setData(repositoryData);
                mBillDataAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {

        }
    }

    /**
     * 选择图片
     */
    private void selectPhotoDialog() {
        img = new GetImg(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_photo, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        ImageView iv_cancel_popu = (ImageView) view.findViewById(R.id.iv_cancel_popu);
        TextView takePhoto = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView albume = (TextView) view.findViewById(R.id.tv_albume);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        iv_cancel_popu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            //拍照
            @Override
            public void onClick(View v) {
                img.goToCamera(RepositoryBillShipmentActivity.this);//通过拍照获取图片
                popupWindow.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                MyApplication.getInstance().selectPhoto = 10;
                Intent intent = new Intent(getApplicationContext(), TestPicActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            //取消
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (bitmapData == null) {
            bitmapData = new ArrayList<>();
        }
        if (requestCode == GetImg.go_to_camera_code && resultCode == RESULT_OK) { //通过拍照获取图片
            String path = "";
            path = img.file_save.getAbsolutePath();
            Bimp.drr.add(path);
        }
        if (requestCode == GetImg.go_to_gallery_code && resultCode == RESULT_OK) {
            String path = img.getGalleryPath(data);
            bitmap = getSmallBitmap(path);
            iv_photo.setImageBitmap(bitmap);
        }
    }

    /**
     * 获取图片
     *
     * @param filePath
     */
    private Bitmap getSmallBitmap(String filePath) {
        File file = new File(filePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = img.calculateInSampleSize(options, 800, 360);
        options.inJustDecodeBounds = false;

        /**
         * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
         */
        int angle = GetImg.readPictureDegree(filePath);

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        BufferedOutputStream baos = null;

        bitmap = GetImg.rotaingImageView(angle, bitmap);
        if (bitmap != null) {
            try {
                baos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MyUtils.setTextViewClick(true, bt_submit, tv_right_two);
            if (Bimp.drr != null) {
                Bimp.drr.clear();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.lv_mingxi:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        lv_mingxi.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                break;
        }
        return false;
    }

    public void onEventMainThread(EventBusBean mEventBusBean) {
        if (mEventBusBean == null) {
            return;
        }
        if ("QualityGuaranteePeriodActivity".equals(mEventBusBean.getActivityName())) {
            mRepositoryBillBeansOfTime = mEventBusBean.getRepositoryBillBeans();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMEventBus != null) {
            mMEventBus.unregister(this);
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}