package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.DenandingCertificatesAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.app.App;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.DemandingCertificatesBeans;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.PoPBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.Bimp;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.TestPicActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GlobalData;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.HideSoftInputSelf;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageDialog;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/4/18 15:52
 * @Description 索证索票  三期界面
 */
public class DemandingCertificatesThreeActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher, View.OnTouchListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_photo)
    ImageView iv_photo;
    @Bind(R.id.ll_add_photo)
    LinearLayout ll_add_photo;
    @Bind(R.id.bt_next)
    Button bt_next;
    @Bind(R.id.tv_photo_num)
    TextView tv_photo_num;
    @Bind(R.id.et_id)
    EditText et_id;
    @Bind(R.id.ed_psw)
    EditText ed_psw;
    @Bind(R.id.et_contacts_persion)
    EditText et_contacts_persion;
    @Bind(R.id.rl_photo_view)
    RelativeLayout rl_photo_view;
    //选择照片
    @Bind(R.id.ll_select_photo)
    LinearLayout ll_select_photo;
    @Bind(R.id.tv_take_photo)
    TextView tv_take_photo;
    @Bind(R.id.tv_albume)
    TextView tv_albume;
    @Bind(R.id.tv_cancel)
    TextView tv_cancel;

    @Bind(R.id.lv_history)
    ListView lv_history;

    private Intent intent;

    private GetImg img;
    private Bitmap bitmap;
    private List<Bitmap> bitmapData;//选择的图片bitmap
    private String selectType;//标记选择的是生产 流通  餐饮;
    private ArrayList<String> bitmapDrr;//已经选择的图片的地址
    private PopupWindow popupWindow;
    private List<PoPBean> popList = new ArrayList<>();
    private DemandingCertificatesBeans demandingCertificatesBeans;
    private DenandingCertificatesAdapter mDenandingCertificatesAdapter;

    @InjectBefore
    private void before() {
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_demanding_certificates_three);
        ButterKnife.bind(this);
        title.setText("供应商(生产)");
        initListener();
        getIntentData();
        getGongYingShangMessage();
        hideSoftToHideHistory();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectType = intent.getStringExtra("selectType");
            if ("1".equals(selectType)) {
                title.setText(getString(R.string.gongyingshang_one));
            } else if ("2".equals(selectType)) {
                title.setText(getString(R.string.gongyingshang_two));
            } else if ("3".equals(selectType)) {
                title.setText(getString(R.string.gongyingshang_three));
            }
        }
    }

    //获取保存的供应商的信息
    private void getGongYingShangMessage() {
        String supplierNameString = GlobalData.getData(getApplicationContext(), "supplier", "");
        if (!TextUtils.isEmpty(supplierNameString) && supplierNameString != "") {
            Gson gson = new Gson();
            demandingCertificatesBeans = gson.fromJson(supplierNameString, DemandingCertificatesBeans.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showImage();
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
                    ll_add_photo.setVisibility(View.GONE);
                } else {
                    ll_add_photo.setVisibility(View.VISIBLE);
                }
                iv_photo.setImageBitmap(bitmapData.get(bitmapData.size() - 1));
                tv_photo_num.setText(bitmapData.size() + "张");
                rl_photo_view.setVisibility(View.VISIBLE);
            } else {
                iv_photo.setImageBitmap(null);
                iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
                tv_photo_num.setText("0张");
                ll_add_photo.setVisibility(View.VISIBLE);
                rl_photo_view.setVisibility(View.GONE);
            }
        } else {
            if (bitmapData != null) {
                bitmapData.clear();
            }
            iv_photo.setImageBitmap(null);
            iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
            tv_photo_num.setText("0张");
            ll_add_photo.setVisibility(View.VISIBLE);
            rl_photo_view.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        ivLeft.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        ll_add_photo.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        et_id.setOnTouchListener(this);
        ed_psw.setOnTouchListener(this);
        et_id.addTextChangedListener(this);
        lv_history.setOnItemClickListener(this);
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
            case R.id.ll_add_photo://添加图片
                MyApplication.getInstance().imageNum = 100;
                //selectPhotoDialog();
                selectPhoto();
                break;
            case R.id.bt_next://下一步
                nextSumbit();
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
            case R.id.et_id://供货商
                //showHistory();
                break;
        }
    }

    private void showHistory() {
        if (demandingCertificatesBeans != null && demandingCertificatesBeans.demandingCertificates != null) {
            lv_history.setVisibility(View.VISIBLE);
            mDenandingCertificatesAdapter = new DenandingCertificatesAdapter(getApplicationContext());
            lv_history.setAdapter(mDenandingCertificatesAdapter);
            setHistoryListHeight(demandingCertificatesBeans.demandingCertificates);
            mDenandingCertificatesAdapter.setData(demandingCertificatesBeans.demandingCertificates);
            mDenandingCertificatesAdapter.notifyDataSetChanged();
            String inputMessage = et_id.getText().toString();
            if (!TextUtils.isEmpty(inputMessage) && !"".equals(inputMessage)) {
                textChange(inputMessage);
            }
        }
    }

    //历史记录列表,只展示5条记录  设置历史记录的高度
    private void setHistoryListHeight(ArrayList<DemandingCertificatesBeans.DemandingCertificates> demandingCertificates) {
        ViewGroup.LayoutParams params = lv_history.getLayoutParams();
        if (demandingCertificates != null && demandingCertificates.size() > 5) {
            int dPtoPX = MyUtils.DPtoPX(220, getApplicationContext());
            params.height = dPtoPX;
        } else {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        lv_history.setLayoutParams(params);
        lv_history.requestLayout();
    }

    private void nextSumbit() {
        String name = et_id.getText().toString();
        String contactsPersion = et_contacts_persion.getText().toString();
        String phone = ed_psw.getText().toString();
        if (TextUtils.isEmpty(name)) {
            MyToastUtils.show(getApplicationContext(), "请输入供应商名称");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.show(getApplicationContext(), "请输入供应商电话");
            return;
        }
        /*if (!TextUtils.isEmpty(phone) && phone.length() != 11) {
            MyToastUtils.show(getApplicationContext(), "供应商电话格式不对");
            return;
        }*/
        if (bitmapData == null || bitmapData.size() == 0) {// || bitmapData.size() != App.getInstance().imageNum
            MyToastUtils.show(getApplicationContext(), "未按照规定数量上传照片");
            return;
        }
        if (bitmapDrr == null) {
            bitmapDrr = new ArrayList<>();
        }
        bitmapDrr.clear();
        bitmapDrr = Bimp.drr;
        Log.i("-----------", bitmapDrr.toString());
        intent = new Intent(getApplicationContext(), OriginalBillActivity.class);
        intent.putExtra("selectType", selectType);
        intent.putExtra("name", name);
        if (!TextUtils.isEmpty(contactsPersion)) {
            intent.putExtra("contactsPersion", contactsPersion);
        } else {
            intent.putExtra("contactsPersion", "");
        }
        intent.putExtra("phone", phone);
        intent.putStringArrayListExtra("bitmapDrr", bitmapDrr);
        startActivity(intent);
        Bimp.drr.clear();
        finish();
    }

    //删除图片后做更新处理
    private void refreshBitmapData(List<Bitmap> comment_pics) {
        bitmapData = comment_pics;
        if (bitmapData != null && bitmapData.size() > 0) {
            if (bitmapData.size() >= MyApplication.getInstance().imageNum) {
                ll_add_photo.setVisibility(View.GONE);
            } else {
                ll_add_photo.setVisibility(View.VISIBLE);
            }
            iv_photo.setImageBitmap(bitmapData.get(bitmapData.size() - 1));
            tv_photo_num.setText(bitmapData.size() + "张");
            rl_photo_view.setVisibility(View.VISIBLE);
        } else {
            iv_photo.setImageBitmap(null);
            iv_photo.setBackgroundResource(R.mipmap.zhengjian_picture);
            tv_photo_num.setText("0张");
            ll_add_photo.setVisibility(View.VISIBLE);
            rl_photo_view.setVisibility(View.GONE);
        }
    }

    /**
     * 选择图片
     */
    private void selectPhotoDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_photo, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        TextView takePhoto = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView albume = (TextView) view.findViewById(R.id.tv_albume);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            //拍照
            @Override
            public void onClick(View v) {
                img.goToCamera(DemandingCertificatesThreeActivity.this);//通过拍照获取图片
                popupWindow.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                MyApplication.getInstance().selectPhoto = 0;
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

    //选择图片（不使用popupWindow）
    private void selectPhoto() {
        img = new GetImg(this);
        lv_history.setVisibility(View.GONE);
        ll_select_photo.setVisibility(View.VISIBLE);
        tv_take_photo.setOnClickListener(new View.OnClickListener() {
            //拍照
            @Override
            public void onClick(View v) {
                img.goToCamera(DemandingCertificatesThreeActivity.this);//通过拍照获取图片
                ll_select_photo.setVisibility(View.GONE);
            }
        });
        tv_albume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                App.getInstance().selectPhoto = 0;
                Intent intent = new Intent(getApplicationContext(), TestPicActivity.class);
                startActivity(intent);
                ll_select_photo.setVisibility(View.GONE);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            //取消
            @Override
            public void onClick(View v) {
                ll_select_photo.setVisibility(View.GONE);
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
           /* bitmap = getSmallBitmap(path);
            iv_photo.setImageBitmap(bitmap);*/
        }
        if (requestCode == GetImg.go_to_gallery_code && resultCode == RESULT_OK) {
            String path = img.getGalleryPath(data);
            bitmap = getSmallBitmap(path);
            iv_photo.setImageBitmap(bitmap);
        }
        /*bitmapData.add(bitmap);
        tv_photo_num.setText(bitmapData.size() + "张");
        if (bitmapData.size() >= 4) {
            ll_add_photo.setVisibility(View.GONE);
        } else {
            ll_add_photo.setVisibility(View.VISIBLE);
            rl_photo_view.setVisibility(View.VISIBLE);
        }*/
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
            if (Bimp.drr != null) {
                Bimp.drr.clear();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DenandingCertificatesAdapter adapter = (DenandingCertificatesAdapter) parent.getAdapter();
        DemandingCertificatesBeans.DemandingCertificates demandingCertificates = adapter.getItem(position);
        if (demandingCertificates != null) {
            et_id.setText(demandingCertificates.name);
            ed_psw.setText(demandingCertificates.phone);
            et_contacts_persion.setText(demandingCertificates.contactsPersion);
            Bimp.drr.clear();
            if (demandingCertificates.image != null) {
                for (String image : demandingCertificates.image) {
                    Bimp.drr.add(image);
                }
                showImage();
            }
        }
        lv_history.setVisibility(View.GONE);
        hideSoftInput();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (lv_history.getVisibility() == View.VISIBLE) {
            String message = et_id.getText().toString();
            if (TextUtils.isEmpty(message) || "".equals(message)) {
                ed_psw.setText("");
                if (Bimp.drr != null) {
                    Bimp.drr.clear();
                }
                showImage();
            }
        }
        lv_history.setVisibility(View.GONE);
        ll_select_photo.setVisibility(View.GONE);
        if (null != imm && null != getCurrentFocus()) {
            return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } else {
            return true;
        }
    }

    //隐藏键盘
    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }

    //筛选历史记录
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String inputMessage = s.toString();
        textChange(inputMessage);
    }

    private void textChange(String inputMessage) {
        if (!TextUtils.isEmpty(inputMessage) && !"".equals(inputMessage)) {
            if (demandingCertificatesBeans != null && demandingCertificatesBeans.demandingCertificates != null && demandingCertificatesBeans.demandingCertificates.size() > 0) {
                ArrayList<DemandingCertificatesBeans.DemandingCertificates> demandingCertificates = new ArrayList<>();
                DemandingCertificatesBeans.DemandingCertificates certificatesTemp = null;
                boolean isHasHistory = false;
                for (DemandingCertificatesBeans.DemandingCertificates certificates : demandingCertificatesBeans.demandingCertificates) {
                    if (certificates.name.contains(inputMessage)) {
                        demandingCertificates.add(certificates);
                    }
                    if (inputMessage.equals(certificates.name)) {
                        isHasHistory = true;
                        certificatesTemp = certificates;
                    }
                }
                setHistoryListHeight(demandingCertificates);
                mDenandingCertificatesAdapter.setData(demandingCertificates);
                mDenandingCertificatesAdapter.notifyDataSetChanged();
                //如果用户选中了历史记录，而又在输入框中输入，那么判断输入的内容是否在历史记录中，如果不在历史记录中就清空上次选中的历史立即在界面上显示的数据
                if (!isHasHistory) {
                    certificatesTemp = null;
                }
                if (certificatesTemp != null) {
                    ed_psw.setText(certificatesTemp.phone);
                    et_contacts_persion.setText(certificatesTemp.contactsPersion);
                    Bimp.drr.clear();
                    if (certificatesTemp.image != null) {
                        for (String image : certificatesTemp.image) {
                            Bimp.drr.add(image);
                        }
                        showImage();
                    }
                } else {
                    ed_psw.setText("");
                    et_contacts_persion.setText("");
                    Bimp.drr.clear();
                    showImage();
                }
            }
        } else {
            showHistory();
        }
    }

    //供应商的触摸事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.et_id:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ll_select_photo.setVisibility(View.GONE);
                        showHistory();
                        break;
                }
                break;
            case R.id.ed_psw://供货商电话
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ll_select_photo.setVisibility(View.GONE);
                        lv_history.setVisibility(View.GONE);
                        break;
                }
                break;
        }
        return false;
    }

    //如果用户输入完成了在隐藏键盘的同时隐藏立即记录
    private void hideSoftToHideHistory() {
        HideSoftInputSelf hideSoftInputSelf = new HideSoftInputSelf(et_id);
        hideSoftInputSelf.addSoftKeyboardStateListener(new HideSoftInputSelf.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {

            }

            @Override
            public void onSoftKeyboardClosed() {
                String message = et_id.getText().toString();
                if (!TextUtils.isEmpty(message) && !"".equals(message)) {
                    lv_history.setVisibility(View.GONE);
                }
            }
        });
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
