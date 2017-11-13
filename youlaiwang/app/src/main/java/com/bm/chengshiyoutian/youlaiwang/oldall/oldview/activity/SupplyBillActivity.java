package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.constant.Constants;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.Bimp;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic.TestPicActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.GetImg;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyToastUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.BigImageDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SupplyBillActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right_two)
    TextView tv_right_two;
    @Bind(R.id.tv_supplier_name)
    TextView tvSupplierName;
    @Bind(R.id.tv_business_num)
    TextView tvBusinessNum;
    @Bind(R.id.tv_Supplier_persion)
    TextView tvSupplierPersion;
    @Bind(R.id.tv_supplier_phone)
    TextView tvSupplierPhone;
    //上传图片控件
    @Bind(R.id.ll_add_photo)
    LinearLayout ll_add_photo;

    @Bind(R.id.iv_photo)
    ImageView iv_photo;
    @Bind(R.id.tv_photo_num)
    TextView tv_photo_num;
    @Bind(R.id.rl_photo_view)
    RelativeLayout rl_photo_view;
    @Bind(R.id.bt_next)
    Button btNext;

    private List<Bitmap> bitmapData;//选择票据的图片bitmap
    private GetImg img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_bill);
        ButterKnife.bind(this);
        title.setText("供货票据");
        tv_right_two.setVisibility(View.VISIBLE);
        tv_right_two.setText("下一步");
        initListener();
        getForIntent();//回显数据
    }

    /**
     * 回显传递过来的信息
     */
    private void getForIntent() {
        String businessNum = getIntent().getStringExtra("businessNum");
        String restaurantName = getIntent().getStringExtra("restaurantName");
        String contactsPersion = getIntent().getStringExtra("contactsPersion");
        String supplierPhone = getIntent().getStringExtra("SupplierPhone");
        String id = getIntent().getStringExtra("SupplierId");
        tvSupplierName.setText(restaurantName);
        tvBusinessNum.setText(businessNum);
        tvSupplierPersion.setText(contactsPersion);
        tvSupplierPhone.setText(supplierPhone);
    }

    private void initListener() {
        ivLeft.setOnClickListener(this);
        tv_right_two.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        ll_add_photo.setOnClickListener(this);
        btNext.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_photo://查看大图
                Constants.BIMPTYPE = "0";
                if (bitmapData != null && bitmapData.size() > 0) {
                    BigImageDialog bigImageDialog = new BigImageDialog(this, Constants.BIMPTYPE, bitmapData, 0, new BigImageDialog.RefreshBitmapData() {
                        @Override
                        public void refreshData(List<Bitmap> comment_pics) {
                            refreshBitmapData(comment_pics);
                        }
                    });
                    bigImageDialog.show();
                }
                break;
            case R.id.ll_add_photo: //上传图片
                MyApplication.getInstance().imageNum = 100;
                selectPhotoDialog();
                break;
            case R.id.tv_right_two:

                //TODO   提交下一步
            case R.id.bt_next:
                //提交
                if (bitmapData == null || bitmapData.size() == 0) {
                    MyToastUtils.show(getApplicationContext(), getString(R.string.bill_all_good_data));
                    return;
                }
                if (Constants.AccountDetaiTools.piaojuList!=null){
                    Constants.AccountDetaiTools.piaojuList.clear();
                }
                Constants.AccountDetaiTools.piaojuList.addAll(bitmapData);

                Intent intent = new Intent();
                intent.setClass(this, NewAccountDetailActivity.class);
                bitmapData.clear();
                ll_add_photo.setVisibility(View.VISIBLE);
                rl_photo_view.setVisibility(View.GONE);
                startActivity(intent);
//                finish();
                break;
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
                img.goToCamera(SupplyBillActivity.this);//通过拍照获取图片
                popupWindow.dismiss();
            }
        });
        albume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //img.goToGallery(); //从相册获取照片
                MyApplication.getInstance().selectPhoto = 9;
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
    protected void onDestroy() {
        super.onDestroy();
        Bimp.drr.clear();
        Constants.AccountDetaiTools.piaojuList.clear();
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
//        if (requestCode == GetImg.go_to_gallery_code && resultCode == RESULT_OK) {
//            String path = img.getGalleryPath(data);
//            bitmap = getSmallBitmap(path);
//            iv_photo.setImageBitmap(bitmap);
//        }
    }
}
