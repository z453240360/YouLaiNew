package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectBefore;
import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.PopAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.RepositoryBillAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.PoPBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces.BaseInterface;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.Bimp;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.TestPicActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyListView;
import com.google.gson.Gson;

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
 *         create at 2016/5/25 17:57
 * @Description 不合格 临期产品
 */
public class PastTimeProductActivity extends Activity implements BaseInterface, View.OnClickListener {

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

    @Bind(R.id.tv_action_type)
    TextView tv_action_type;

    @Bind(R.id.ll_action_back)
    LinearLayout ll_action_back;

    @Bind(R.id.tv_add_data)
    TextView tv_add_data;
    @Bind(R.id.bt_submit)
    Button bt_submit;

    @Bind(R.id.et_returncompany)
    EditText et_returncompany;
    @Bind(R.id.et_contacts)
    EditText et_contacts;
    @Bind(R.id.et_phone)
    EditText et_phone;

    @Bind(R.id.lv_mingxi_data)
    MyListView lv_mingxi_data;

    private String selectType;
    private Intent mIntent;

    private ArrayList<RepositoryBillBean> mRepositoryBillBeans;
    private List<PoPBean> mPastTimeProductAllBeans;//所有的过期产品

    private RepositoryBillAdapter mRepositoryBillAdapter;
    private PopupWindow mPopupWindow;
    private List<PoPBean> popListGoodsClass;

    private GetImg img;
    private Bitmap bitmap;
    private List<Bitmap> bitmapData;//选择的图片bitmap

    private int treatmentStyle = 4;//处理方式,默认是其他

    private ProgressDialog progressDialog;

    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_past_time_product);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showImage();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectType = intent.getStringExtra("selectType");
        }
    }

    @Override
    public void init() {
        progressDialog = new ProgressDialog(this);
        getIntentData();
        initView();
        initData();
        setListener();
    }

    @Override
    public void initView() {
        title.setText(getString(R.string.pasttimeproduct));
        tv_right_two.setVisibility(ImageView.VISIBLE);
        tv_right_two.setText("提交");
    }

    @Override
    public void initData() {
        mRepositoryBillAdapter = new RepositoryBillAdapter(PastTimeProductActivity.this);
        lv_mingxi_data.setAdapter(mRepositoryBillAdapter);

        mRepositoryBillBeans = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mRepositoryBillBeans.add(new RepositoryBillBean());
        }
        mRepositoryBillAdapter.setData(mRepositoryBillBeans);
        mRepositoryBillAdapter.notifyDataSetChanged();

        popListGoodsClass = new ArrayList<>();
        popListGoodsClass.add(new PoPBean("1", "返回处理单位"));
        popListGoodsClass.add(new PoPBean("2", "销毁"));
        popListGoodsClass.add(new PoPBean("3", "降价促销"));
        popListGoodsClass.add(new PoPBean("4", "其他"));
    }

    @Override
    public void setListener() {
        ivLeft.setOnClickListener(this);
        tv_right_two.setOnClickListener(this);
        tv_add_data.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        tv_action_type.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        iv_add_photo.setOnClickListener(this);
    }

    /**
     * 添加不合格产品记录
     */
    private void addUnqualifiedRecord(String typeid, String bill, String treatment, String purchasecompany, String contacts, String phone, String jsondetails) {
        MyUtils.setTextViewClick(false, bt_submit, tv_right_two);
        if (progressDialog != null) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("typeid", typeid);
        params.put("restaurantid", MyApplication.getInstance().getUser().id);
        params.put("bill", bill);
        params.put("treatment", treatment);
        params.put("purchasecompany", purchasecompany != null ? purchasecompany : "");
        params.put("contacts", contacts != null ? contacts : "");
        params.put("phone", phone != null ? phone : "");
        params.put("jsondetails", jsondetails);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUTLONG);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "AddUnqualifiedRecord", params, config, this);
    }

    @InjectHttpOk
    @Override
    public void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        String contentAsString = entity.getContentAsString();
        int key = entity.getKey();
        switch (key) {
            case 0://添加不合格产品记录
                addUnqualifiedJsonData(contentAsString);
                break;
        }
    }

    @InjectHttpErr
    @Override
    public void err(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyUtils.setTextViewClick(true, bt_submit, tv_right_two);
        MyToastUtils.show(getApplicationContext(), getString(R.string.intnet_err));
    }

    //添加不合格产品记录
    private void addUnqualifiedJsonData(String contentAsString) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                if (Bimp.drr != null) {
                    Bimp.drr.clear();
                }
                finish();
                break;
            case R.id.tv_right_two://提交
            case R.id.bt_submit:
                submit();
                break;
            case R.id.tv_add_data://添加新条目
                mRepositoryBillBeans.add(new RepositoryBillBean());
                mRepositoryBillAdapter.setData(mRepositoryBillBeans);
                mRepositoryBillAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_action_type://选择处理方式
                chooseActionType();
                break;
            case R.id.iv_add_photo://添加图片
                selectPhotoDialog();
                break;
            case R.id.iv_photo://查看大图
                Constants.BIMPTYPE="0";
                if (bitmapData != null && bitmapData.size() > 0) {
                    BigImageDialog bigImageDialog = new BigImageDialog(this,Constants.BIMPTYPE, bitmapData, 0, new BigImageDialog.RefreshBitmapData() {
                        @Override
                        public void refreshData(List<Bitmap> comment_pics) {
                            refreshBitmapData(comment_pics);
                        }
                    });
                    bigImageDialog.show();
                }
                break;
        }
    }

    //提交
    private void submit() {
        if (bitmapData == null || bitmapData.size() == 0) {
            MyToastUtils.show(getApplicationContext(), getString(R.string.bill_all_data));
            return;
        }
        ArrayList<RepositoryBillBean> repositoryBillBeans = submitSelectData();
        String toJson = "";
        if (repositoryBillBeans != null) {
            Gson gson = new Gson();
            toJson = gson.toJson(repositoryBillBeans);
        }
        if (treatmentStyle == 1) {
            //返回处理单位
            String returncompany = et_returncompany.getText().toString();
            String contacts = et_contacts.getText().toString();
            String phone = et_phone.getText().toString();
            addUnqualifiedRecord(selectType, MyUtils.bitmapStringData(bitmapData), popListGoodsClass.get(treatmentStyle - 1).title,
                    returncompany != null ? returncompany : "", contacts != null ? contacts : "",
                    phone != null ? phone : "", toJson);
        } else {
            addUnqualifiedRecord(selectType, MyUtils.bitmapStringData(bitmapData), popListGoodsClass.get(treatmentStyle - 1).title, "", "", "", toJson);
        }
    }

    /**
     * 提交筛选数据
     */
    private ArrayList<RepositoryBillBean> submitSelectData() {
        ArrayList<RepositoryBillBean> billBeans = mRepositoryBillAdapter.getData();
        if (billBeans != null) {
            ArrayList<RepositoryBillBean> repositoryBillBeans = MyUtils.selectHasNum(billBeans);
            if (repositoryBillBeans != null && repositoryBillBeans.size() > 0) {
                return repositoryBillBeans;
            }
        }
        return null;
    }

    /**
     * 弹出popwindows 选择不合格商品处理方式
     */
    private void chooseActionType() {
        Drawable drawable = MyUtils.getDrawa(this, 2);
        tv_action_type.setCompoundDrawables(null, null, drawable, null);
        ListView listView = new ListView(this);
        listView.setBackgroundColor(Color.WHITE);
        listView.setDividerHeight(1);
        listView.setLayoutParams(new ViewGroup.LayoutParams(tv_action_type.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        mPopupWindow = new PopupWindow(listView, tv_action_type.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAsDropDown(tv_action_type);
        listView.setAdapter(new PopAdapter(popListGoodsClass, this, 2));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoPBean poPBean = popListGoodsClass.get(position);
                tv_action_type.setText(poPBean.title);
                if ("1".equals(poPBean.id)) {
                    ll_action_back.setVisibility(View.VISIBLE);
                    treatmentStyle = Integer.parseInt(poPBean.id);
                } else {
                    ll_action_back.setVisibility(View.GONE);
                }
                treatmentStyle = Integer.parseInt(poPBean.id);
                mPopupWindow.dismiss();
                Drawable drawable = MyUtils.getDrawa(PastTimeProductActivity.this, 1);
                tv_action_type.setCompoundDrawables(null, null, drawable, null);
                tv_action_type.setTextColor(Color.WHITE);
            }
        });
        //pop消失监听
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //pop消失就切换箭头
                tv_action_type.setCompoundDrawables(null, null, MyUtils.getDrawa(PastTimeProductActivity.this, 1), null);
            }
        });
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
                img.goToCamera(PastTimeProductActivity.this);//通过拍照获取图片
                popupWindow.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                MyApplication.getInstance().selectPhoto = 4;
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

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
