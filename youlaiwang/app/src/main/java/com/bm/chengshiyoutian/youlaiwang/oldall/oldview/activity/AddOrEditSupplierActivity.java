package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectHttpErr;
import com.android.pc.ioc.inject.InjectHttpOk;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.CacheStoreImageDrr;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.Bimp;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.TestPicActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GlobalData;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.ShaoMiaoUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.PointDialog;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.zxing.CaptureActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AddOrEditSupplierActivity extends AppCompatActivity implements View.OnClickListener,ImageLoadingListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right_two)
    TextView tv_right_two;
    //上传图片控件
    @Bind(R.id.ll_add_photo)
    LinearLayout ll_add_photo;
    @Bind(R.id.tv_photo_num)
    TextView tv_photo_num;

    @Bind(R.id.iv_photo)
    ImageView iv_photo;
    @Bind(R.id.rl_photo_view)
    RelativeLayout rl_photo_view;

    @Bind(R.id.bt_complete)
    Button bt_complete;

    @Bind(R.id.bt_delete)
    Button bt_delete;

    @Bind(R.id.tv_loading)
    TextView tv_loading;//正在加载
    @Bind(R.id.et_business_num)
    EditText et_business_num;
    @Bind(R.id.et_restaurant_name)
    EditText et_restaurant_name;
    @Bind(R.id.et_contacts_persion)
    EditText et_contacts_persion;
    @Bind(R.id.ed_phone)
    EditText ed_phone;
    @Bind(R.id.iv_shaomiao)
    ImageView iv_shaomiao;

    private GetImg img;
    private List<Bitmap> bitmapData;//选择的图片bitmap
    private final int SCANNIN_GREQUEST_CODE = 1024;
    private ProgressDialog progressDialog;
    private String etBusinessNum;
    private String restaurantName;
    private String phone;
    private String contactsPersion;
    private String id;
    private String forActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_supplier);
        ButterKnife.bind(this);
        title.setText("供应商编辑");
        tv_right_two.setVisibility(View.VISIBLE);
        tv_right_two.setText("完成");
        progressDialog = new ProgressDialog(this);
        initListener();
        getIntentForActivity();
    }

    private List<String> imgList  = new ArrayList<String>();
     private void getIntentForActivity() {

         forActivity = getIntent().getStringExtra("ForActivity");//标示是编辑还是新增供应商
         if (forActivity.equals("1")) {
             String img = getIntent().getStringExtra("imgList");
             String businessNum = getIntent().getStringExtra("businessNum");
             String restaurantName = getIntent().getStringExtra("restaurantName");
             String contactsPersion = getIntent().getStringExtra("contactsPersion");
             String supplierPhone = getIntent().getStringExtra("SupplierPhone");
             id = getIntent().getStringExtra("SupplierId");
              //加载图片
             String[] split = img.split(",");
             for (int i = 0; i < split.length; i++) {
                 imgList.add(split[i]);
             }
             ImageLoader imageLoader = ImageLoader.getInstance();
             getCacheImageData(id, imageLoader);

             //回显文字信息
             et_business_num.setText(businessNum);//证件编号
             et_restaurant_name.setText(restaurantName);//供应商名称
             if (TextUtils.isEmpty(contactsPersion)||"".equals(contactsPersion)){
                 et_contacts_persion.setText("");
             }else {
                 et_contacts_persion.setText(contactsPersion);//联系人姓名
             }
             ed_phone.setText(supplierPhone);//联系人电话
         }

     }      //读取保存的图片信息
    private void getCacheImageData(String id, ImageLoader imageLoader) {
        String cacheStoreImageDrr = GlobalData.getData(getApplicationContext(), "cacheStoreImageDrrSupplier", "");
        if (TextUtils.isEmpty(cacheStoreImageDrr) || "".equals(cacheStoreImageDrr)) {
            showHttpImage(id, imageLoader);
        } else {
            Gson gson = new Gson();
            CacheStoreImageDrr cacheStoreImageDrrBean = gson.fromJson(cacheStoreImageDrr, CacheStoreImageDrr.class);
            if (cacheStoreImageDrrBean != null) {
                if (id.equals(cacheStoreImageDrrBean.userId)) {
                    if (imgList != null) {
                        if (imgList.size() != cacheStoreImageDrrBean.bitmapDrr.size()) {
                            showHttpImage(id, imageLoader);
                        } else {
                            boolean isFileExist = true;
                            for (String fileName : cacheStoreImageDrrBean.bitmapDrr) {
                                File file = new File(fileName);
                                if (!file.exists()) {
                                    isFileExist = false;
                                    break;
                                }
                            }
                            if (isFileExist) {
                                iv_photo.setClickable(true);
                                tv_loading.setVisibility(View.GONE);
                                Bimp.drrSupplier.addAll(cacheStoreImageDrrBean.bitmapDrr);
                            } else {
                                showHttpImage(id, imageLoader);
                            }
                        }
                    }
                } else {
                    showHttpImage(id, imageLoader);
                }
            } else {
                showHttpImage(id, imageLoader);
            }
        }

    }

    private void showHttpImage(String id, ImageLoader imageLoader) {
        iv_photo.setClickable(false);
        tv_loading.setVisibility(View.VISIBLE);
        if (imgList != null && imgList.size() > 0) {
            for (String imgUrl :imgList) {
                try {
                    imageLoader.loadImage(imgUrl, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void initListener() {
        ivLeft.setOnClickListener(this);
        tv_right_two.setOnClickListener(this);
        ll_add_photo.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        bt_complete.setOnClickListener(this);
        iv_shaomiao.setOnClickListener(this);
        tv_right_two.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_add_photo: //上传图片
                MyApplication.getInstance().imageNum = 100;

                selectPhotoDialog();

                break;
            case R.id.iv_photo://查看大图
                Constants.BIMPTYPE = "2";
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
            case R.id.bt_complete://完成
//                AddOrEditSupplierInfo();
//                break;
            case R.id.tv_right_two://完成

                AddOrEditSupplierInfo();

                break;
            case R.id.iv_shaomiao://扫一扫
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
            case R.id.bt_delete:

                final PointDialog dialog = new PointDialog(this,"提示","确认删除该供应商吗");
                dialog.show();
                dialog.setClicklistener(new PointDialog.ClickListenerInterface() {
                    @Override
                    public void doConfirm() {

                        if ("1".equals(forActivity)){
                            dialog.dismiss();
                            DeleteSupplierInfo();
                        }else {
                        dialog.dismiss();
                        bitmapData = null;
                        AddOrEditSupplierActivity.this.finish();
                        }
                    }

                    @Override
                    public void doCancel() {
                        dialog.dismiss();
                    }
                });

                break;
        }
    }




    /**
     * 完成
     */
    private void AddOrEditSupplierInfo() {
        if (bitmapData == null || bitmapData.size() == 0) {
            MyToastUtils.show(getApplicationContext(), getString(R.string.register_data));
            return;
        }
        //执照或证件编号
        etBusinessNum = et_business_num.getText().toString();
        //供应商企业名称
        restaurantName = et_restaurant_name.getText().toString().trim();
        //供应商电话
        phone = ed_phone.getText().toString();
        //供应商联系人
        contactsPersion = et_contacts_persion.getText().toString();

        if (TextUtils.isEmpty(etBusinessNum)){
            MyToastUtils.show(getApplicationContext(), "请输入执照或证件编号");
            return;
        }
        if (TextUtils.isEmpty(restaurantName)) {

            MyToastUtils.show(getApplicationContext(), "请输入供应商企业名称");
            return;
        }
        if (TextUtils.isEmpty(phone)) {

            MyToastUtils.show(getApplicationContext(), "请输入供应商电话");
            return;
        }


        final PointDialog dialog = new PointDialog(this,"提示","是否确认本次填写");
        dialog.show();
        dialog.setClicklistener(new PointDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                if ("1".equals(forActivity)){
                    EditForSupplierInfo();
                }else {
                    CompleteAddSupplier();
                }
                dialog.dismiss();
            }

            @Override
            public void doCancel() {
                dialog.dismiss();
            }
        });

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
                img.goToCamera(AddOrEditSupplierActivity.this);//通过拍照获取图片
                popupWindow.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                MyApplication.getInstance().selectPhoto = 8;
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
    protected void onResume() {
        super.onResume();
        showImage();
    }


    private void showImage() {

        if (Bimp.drrSupplier != null && Bimp.drrSupplier.size() > 0) {
            if (bitmapData == null) {
                bitmapData = new ArrayList<>();
            }
            bitmapData.clear();
            for (int i = 0; i < Bimp.drrSupplier.size(); i++) {
                try {
                    bitmapData.add(Bimp.revitionImageSize(Bimp.drrSupplier.get(i)));
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

    //删除图片后做更新处理
    private void refreshBitmapData(List<Bitmap> comment_pics) {
        if (bitmapData == null) {
            bitmapData = new ArrayList<>();
        }
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
     * 添加供应商接口
     */
    private void CompleteAddSupplier() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("restid", MyApplication.getInstance().getUser().id);
        params.put("licenseNo",etBusinessNum);
        params.put("phone", Constants.RepositoryShipTools.procurementPhone);
        params.put("licenseImg", MyUtils.bitmapStringData(bitmapData));
        params.put("supperlierName",restaurantName);
        params.put("supperlierTel",phone );
        params.put("supperlierContact",contactsPersion);
        InternetConfig config = new InternetConfig();
        config.setKey(0);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "AddSupplier", params, config, this);
    }

    /**
     * 编辑供应商接口
     */
    private void EditForSupplierInfo() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("ID",id );
        params.put("licenseNo",etBusinessNum);
        params.put("licenseImg",MyUtils.bitmapStringData(bitmapData));
        params.put("supperlierName",restaurantName);
        params.put("supperlierTel",phone );
        params.put("supperlierContact",contactsPersion);
        InternetConfig config = new InternetConfig();
        config.setKey(1);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "UpdateSupplier", params, config, this);
    }

    /**
     * 删除
     */
    private void DeleteSupplierInfo() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("ID",id );
        InternetConfig config = new InternetConfig();
        config.setKey(2);
        config.setTimeout(Constants.TIMEOUT);
        FastHttpHander.ajaxWebServer(Constants.RESTAURANT_URL, "DeleteSupplier", params, config, this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANNIN_GREQUEST_CODE && resultCode == RESULT_OK) {
            String content = data.getStringExtra("result");
            Log.i("扫描结果", content);
            String shaoMiaoMessageZhuCheHao = ShaoMiaoUtils.shaoMiaoMessageZhuCheHao(content);
            String shaoMiaoMessageStoreName = ShaoMiaoUtils.shaoMiaoMessageStoreName(content);
            String shaoMiaoMessageStoreBoss = ShaoMiaoUtils.shaoMiaoMessageStoreBoss(content);
            if (!MyUtils.isEmpty(shaoMiaoMessageZhuCheHao)) {
                et_business_num.setText(shaoMiaoMessageZhuCheHao);
            }
            if (!MyUtils.isEmpty(shaoMiaoMessageStoreName)) {
                et_restaurant_name.setText(shaoMiaoMessageStoreName);
            }
            if (!MyUtils.isEmpty(shaoMiaoMessageStoreBoss)) {
                et_contacts_persion.setText(shaoMiaoMessageStoreBoss);
            }
            if (MyUtils.isEmpty(shaoMiaoMessageZhuCheHao) && MyUtils.isEmpty(shaoMiaoMessageStoreName) && MyUtils.isEmpty(shaoMiaoMessageStoreBoss)) {
                MyToastUtils.show(getApplicationContext(), getString(R.string.shaomiao_error));
            }
            return;
        }
        if (bitmapData == null) {
            bitmapData = new ArrayList<>();
        }
        if (requestCode == GetImg.go_to_camera_code && resultCode == RESULT_OK) { //通过拍照获取图片
            String path = "";
            path = img.file_save.getAbsolutePath();
            Bimp.drrSupplier.add(path);
        }
//        if (requestCode == GetImg.go_to_gallery_code && resultCode == RESULT_OK) {
//            String path = img.getGalleryPath(data);
//            bitmap = getSmallBitmap(path);
//            iv_photo.setImageBitmap(bitmap);
//        }
    }

    @InjectHttpOk
    private void ok(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        switch (entity.getKey()) {
            case 0: //
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    String msg = jsonObject.optString("msg");
                    if (0 == jsonObject.getInt("status")) {
                        String id = jsonObject.optString("RestID");//供应商ID
                        MyToastUtils.show(this,"供应商编辑成功");
                        cacheStoreImageDrr(id, Bimp.drrSupplier);
                        Bimp.drrSupplier.clear();
                        this.finish();

                    }else if (2 == jsonObject.getInt("status")){
                        Intent mIntent = new Intent(getApplicationContext(), LoginActivtiy.class);
                        startActivity(mIntent);
                        finish();
                    }
                    MyToastUtils.show(this,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
            try {
                JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                String msg = jsonObject.optString("msg");
                if (0 == jsonObject.getInt("status")) {
                    String id = jsonObject.optString("RestID");//供应商ID
                    MyToastUtils.show(this,"供应商编辑成功");
                    cacheStoreImageDrr(id, Bimp.drrSupplier);
                    Bimp.drrSupplier.clear();
                    this.finish();

                }else {
                    MyToastUtils.show(this,msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;
            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(entity.getContentAsString());
                    String msg = jsonObject.optString("msg");
                    if (0 == jsonObject.getInt("status")) {
                        MyToastUtils.show(this,"供应商删除成功");
                        bitmapData = null;
                        Bimp.drrSupplier.clear();
                        this.finish();

                    }else {
                        MyToastUtils.show(this,msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
    @InjectHttpErr
    private void err(ResponseEntity entity) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        MyToastUtils.show(this, getString(R.string.intnet_err));
    }


    //下载证件
    @Override
    public void onLoadingStarted(String s, View view) {

    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        Log.i("---------a", "加载失败");
        mImageSave.add("");
        //图片下载完成保存
        if (mImageSave.size() ==imgList.size()) {
            iv_photo.setClickable(true);
            tv_loading.setVisibility(View.GONE);
            cacheStoreImageDrr(id, mImageSave);
        }
    }


    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        Date date = new Date();
        long time = date.getTime();
        GetImg img = new GetImg(this);
        File file = img.setDefaultFile("/" + time + "img.png");
        if (file != null) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                Bimp.drrSupplier.add(file.getAbsolutePath());
                showImage();
                if (Bimp.drrSupplier.size() == imgList.size()) {
                    iv_photo.setClickable(true);
                }
                mImageSave.add(file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                mImageSave.add("");
            } catch (IOException e) {
                e.printStackTrace();
                mImageSave.add("");
            } finally {
                //图片下载完成保存
                if (mImageSave.size() == imgList.size()) {
                    iv_photo.setClickable(true);
                    tv_loading.setVisibility(View.GONE);
//                    RestaurantBean restaurantBean = App.getInstance().getUser();
                    cacheStoreImageDrr(id, mImageSave);
                }
            }
        } else {
            iv_photo.setClickable(true);
        }
    }
    private ArrayList<String> mImageSave = new ArrayList<>();//保存http下载的图片
    @Override
    public void onLoadingCancelled(String s, View view) {

    }
    private void cacheStoreImageDrr(String Id, ArrayList<String> bitmapDrr) {
        CacheStoreImageDrr cacheStoreImageDrr = new CacheStoreImageDrr(Id, bitmapDrr);
        if (cacheStoreImageDrr != null) {
            if (cacheStoreImageDrr.bitmapDrr != null && cacheStoreImageDrr.bitmapDrr.size() > 0) {
                for (String imageDrr : cacheStoreImageDrr.bitmapDrr) {
                    if (TextUtils.isEmpty(imageDrr) || "".equals(imageDrr)) {
                        cacheStoreImageDrr.bitmapDrr.remove(imageDrr);
                    }
                }
            }
            Gson gson = new Gson();
            String toJson = gson.toJson(cacheStoreImageDrr);
            if (!TextUtils.isEmpty(toJson)) {
                GlobalData.cacheData(getApplicationContext(), "cacheStoreImageDrrSupplier", toJson);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bimp.drrSupplier.clear();
    }
}
