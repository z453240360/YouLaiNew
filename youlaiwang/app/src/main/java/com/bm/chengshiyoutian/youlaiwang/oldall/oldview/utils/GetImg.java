package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * 获取照片
 */
public class GetImg {

    public static final int go_to_camera_code = 1;
    public static final int go_to_cutimg_code = 2;
    public static final int go_to_gallery_code = 3;
    public static final int add_to_gallery_code = 4;
    public static final int add_to_camera_code = 5;
    public AsyncTask<Intent, String, String> saveImgTask = null;
    /* 拍照所得相片路径 */
    public File file_save = null;
    /* 裁切照片存储路径 */
    public File file_cut = null;
    public File file_path = null;
    private Activity mActivity = null;

    public GetImg(Activity activity, File fileSave, File fileCut) {
        mActivity = activity;

        file_save = fileSave;
        file_cut = fileCut;
        Date date = new Date();
        long time = date.getTime();
        if (null == file_save) {
            file_save = setDefaultFile("/" + time + "img.png");
        } else {
            file_save = new File(file_save.getPath() + "/" + time + "/img.png");
        }
        if (null == file_cut) {
            file_cut = setDefaultFile("/" + time + "cut_img.png");
        } else {
            file_cut = new File(fileCut.getPath() + "/" + time + "/cut_img.png");
        }
    }

    public GetImg(Activity activity) {
        this(activity, null, null);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    public File setDefaultFile(String name) {
        if (!TextUtils.isEmpty(name) && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return new File(Environment.getExternalStorageDirectory(), name);
        }
        return null;
    }

    /**
     * 重置裁切图片存储位置
     *
     * @param file
     * @author windy 2014年6月27日 上午11:43:36
     */
    public void resetCutFile(File file) {
        file_cut = file;
    }

    /**
     * 重置裁切图片存储位置
     *
     * @param name
     * @author windy 2014年6月27日 上午11:44:33
     */
    public void resetCutFile(String name) {
        file_cut = setDefaultFile(name);
    }

    /**
     * 相册
     *
     * @author Michael.Zhang 2013-06-20 17:06:04
     */
    public void goToGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, go_to_gallery_code);
    }

    /**
     * 相机
     *
     * @author Michael.Zhang 2013-06-20 16:54:47
     */
    public void goToCamera(Context context) {

        if (null != file_save) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file_save));
                mActivity.startActivityForResult(intent, go_to_camera_code);
            } catch (Exception e) {
                e.printStackTrace();
                MyToastUtils.show(context, "您的相机不支持");
            }
        }
    }

    /**
     * 裁切图片，
     *
     * @param path 裁切图片途径
     * @author TangWei 2013-12-23下午5:11:46
     */
    public void gotoCutImage(String path) {
        gotoCutImage(path, 0, null, 0, 0, 0, 0);
    }

    /**
     * 裁切图片，跳转到CropImageActivity
     *
     * @param path     裁切图片途径
     * @param gridView 图片列表
     * @author windy 2014年6月27日 上午11:01:47
     */
    public void gotoCutImage(String path, View gridView) {

        int aspectX = 0, aspectY = 0, outputX = 0, outputY = 0;
        if (null != gridView) {
            aspectX = gridView.getWidth();
            aspectY = gridView.getHeight();
            outputX = gridView.getWidth();
            outputY = gridView.getHeight();
        }

        gotoCutImage(path, 0, null, aspectX, aspectY, outputX, outputY);
    }

    /**
     * 裁切图片，跳转到CropImageActivity，path/resouceId/bitmap 任选一
     *
     * @param path      图片对应的本地路径
     * @param resouceId 图片资源ID
     * @param bitmap    图片Bitmap
     * @param aspectX   裁切框比例，宽
     * @param aspectY   裁切框比例，高
     * @param outputX   裁切图片保存像素，宽
     * @param outputY   裁切图片保存像素，高
     * @author windy 2014年6月27日 上午11:17:15
     */
    public void gotoCutImage(String path, int resouceId, Bitmap bitmap, int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri uri = null;
        if (!TextUtils.isEmpty(path)) {
            uri = Uri.fromFile(new File(path));
        }
        if (resouceId > 0) {
            Bitmap mBitmap = BitmapFactory.decodeResource(mActivity.getResources(), resouceId);
            uri = Uri.parse(MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), mBitmap, null, null));
        }
        if (null != bitmap) {
            uri = Uri.parse(MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), bitmap, null, null));
        }
        if (aspectX > 0) {
            intent.putExtra("aspectX", aspectX);
        }
        if (aspectY > 0) {
            intent.putExtra("aspectY", aspectY);
        }
        if (outputX > 0) {
            intent.putExtra("outputX", outputX);
        }
        if (outputY > 0) {
            intent.putExtra("outputY", outputY);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);
        mActivity.startActivityForResult(intent, go_to_cutimg_code);
    }

    /**
     * 获取 从相册选择的照片路径
     *
     * @param data
     * @author Michael.Zhang 2013-07-05 23:30:56
     */
    @SuppressWarnings("deprecation")
    public String getGalleryPath(Intent data) {
        Uri mImageCaptureUri = data.getData();
        if (mImageCaptureUri != null) {
            String[] proj = {MediaStore.Images.Media.DATA};
            // 好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = mActivity.managedQuery(mImageCaptureUri, proj, null, null, null);
            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径www.2cto.com
            return cursor.getString(column_index);
        } else {
            return "";
        }
    }

    /**
     * 保存裁切后的图片
     */
    public void saveCutImg(final Intent data) {
        if (null != file_cut) {
            saveImgTask = new AsyncTask<Intent, String, String>() {
                @Override
                protected String doInBackground(Intent... params) {
                    // if (params.length > 0) {
                    try {
                        Bitmap photo = BitmapFactory.decodeFile(file_cut.getAbsolutePath());
                        FileOutputStream out = new FileOutputStream(file_cut);
                        photo.compress(Bitmap.CompressFormat.JPEG, 35, out);
                        return file_cut.getAbsolutePath();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // }
                    return null;
                }
            };
            saveImgTask.execute(data);
        }
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    public File getFile_path() {
        return file_path;
    }

    public void setFile_path(File file_path) {
        this.file_path = file_path;
    }
}