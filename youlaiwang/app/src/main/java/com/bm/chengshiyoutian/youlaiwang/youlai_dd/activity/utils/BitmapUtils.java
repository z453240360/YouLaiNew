package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import java.io.InputStream;

import static cn.jpush.android.api.b.h;
import static com.bm.chengshiyoutian.youlaiwang.R.drawable.p;

/**
 * Created by Administrator on 2017/8/31.
 */

public class BitmapUtils {

    /**
     *      图片缩放处理
     * @param bitmap 要缩放的对象
     * @param width 缩放后宽
     * @param heigth 缩放后高
     *        ture  是否保持图片质量
     * @return
     */
    public static Bitmap getScaleBitmap(Bitmap bitmap,int width,int heigth){
        return Bitmap.createScaledBitmap(bitmap,width,heigth,true);
    }

    /**
     *       图片的切割处理
    * @param bitmap 原始图片
    * @param startWidth  图片相对本身起始位置坐标
    * @param startHeigth 图片相对本身起始位置坐标
    * @param endWidth    图片相对本身截止位置坐标
    * @param endHeigth   图片相对本身截止位置坐标
    * @return
            */
    public static Bitmap getCutBitmap(Bitmap bitmap,int startWidth,int startHeigth,int endWidth,int endHeigth){
        return Bitmap.createBitmap(bitmap,startWidth,startHeigth,endWidth,endHeigth);
    }

    /**
     * 思想：绘制圆角矩形，在此基础上绘制直角图片，保留两者相交重合的部分
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap) {

        /**
         * 获取一张纯色图片  8888:支持透明，565不支持透明
         *
         * 参数1：获取图片宽
         * 参数2：获取图片高
         * 参数3：纯色图片，支持透明
         */
        Bitmap bit = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //通过画布保留的内容，会直接保留图片
        Canvas canvas = new Canvas(bit);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        //绘制圆角矩形,与图片本身等宽、等高
        canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), 20, 20, paint);
        //图片的相交保留原则
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制直角图片
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bit;
    }

    /**
     * 思想：绘制圆形图片，在此基础上绘制圆形图片，保留两者相交重合的部分（以图片中心点为圆心）
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getCircleBitmap(Bitmap bitmap) {

        //获取一张纯色图片  8888:支持透明，565不支持透明
        Bitmap bit = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //通过画布保留的内容，会直接保留图片
        Canvas canvas = new Canvas(bit);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        //取图片宽高中比较小的值 的一半作为半径
        float raduios = bitmap.getWidth()/2>bitmap.getHeight()/2?bitmap.getHeight()/2:bitmap.getWidth()/2;
        canvas.drawCircle(bitmap.getWidth()/2,bitmap.getHeight()/2,raduios,paint);
        //图片的相交保留原则
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制直角图片
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bit;
    }

    public static Bitmap getTwoCircleBitmap(Bitmap bitmap, View view) {

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        //获取一张纯色图片  8888:支持透明，565不支持透明
        Bitmap bit = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //通过画布保留的内容，会直接保留图片
        Canvas canvas = new Canvas(bit);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        Path path = new Path();
        path.moveTo(0,20);
        path.quadTo(0,0,20,0);
        path.lineTo(width-20,0);
        path.quadTo(width,0,width,20);
        path.lineTo(width,height);
        path.lineTo(0,height);
        path.close();

        canvas.drawPath(path,paint);
        //图片的相交保留原则
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制直角图片
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bit;
    }

    /**
     * （不解析图片的数据，想办法获取图片的宽高，用原有宽高和想要的宽高，计算缩小比例）
     *
     * @param id     图片的id，连网时候可以换成inputStream
     * @param width  图片想要显示的宽高
     * @param heigth
     */
    public static Bitmap getSecondScaleBitmapFromNative(Context context,int id, int width, int heigth) {
        //获取图片加载时参数对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //设置解析图片时，只加载图片宽高，不加载图片内容数据
        //第一次采样操作，目的是在参数3中存储宽高，不能接受返回值，因为是空
        BitmapFactory.decodeResource(context.getResources(), id, options);
        int bw = options.outWidth;
        int bh = options.outHeight;
        int scaleW = bw / width;
        int scaleH = bh / heigth;
        //选取宽或高中比较大的比例，作为最终缩小比例
        int scale = scaleW > scaleH ? scaleW : scaleH;
        //设置稍后解析图片的时候，可以加载图片的具体内容数据
        options.inJustDecodeBounds = false;
        //设置缩小比例
        options.inSampleSize = scale;
        //第二次采样，解析图片，并且获取图像 】
        Bitmap erci = BitmapFactory.decodeResource(context.getResources(), id, options);
        return erci;
    }


    /**
     * 图片的二次采样
     * @param bytes
     * @param width
     * @param heigth
     * @return
     */
    public static Bitmap getSecondScaleBitmapFromByte(byte[] bytes, int width, int heigth) {
        //获取图片加载时参数对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置解析图片时，只加载图片宽高，不加载图片内容数据
        options.inJustDecodeBounds = true;
        //第一次采样操作，目的是在参数3中存储宽高，不能接受返回值，因为是返回值是空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int bw = options.outWidth;
        int bh = options.outHeight;
        int scaleW = bw / width;
        int scaleH = bh / heigth;

        //选取宽或高中比较大的比例，作为最终缩小比例
        int scale = scaleW > scaleH ? scaleW : scaleH;
        //设置稍后解析图片的时候，可以加载图片的具体内容数据
        options.inJustDecodeBounds = false;
        //设置缩小比例
        options.inSampleSize = scale;
        //第二次采样，解析图片，并且获取图像 】
        Bitmap erci = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        return erci;
    }


    /**
     * 图片的二次采样
     * @param inputStream
     * @param width
     * @param heigth
     * @return
     */
    public static Bitmap getSecondScaleBitmapFromStrean(InputStream inputStream, int width, int heigth) {
        //获取图片加载时参数对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置解析图片时，只加载图片宽高，不加载图片内容数据
        options.inJustDecodeBounds = true;
        //第一次采样操作，目的是在参数3中存储宽高，不能接受返回值，因为是返回值是空
        BitmapFactory.decodeStream(inputStream,new Rect(0,0,width,heigth),options);

        int bw = options.outWidth;
        int bh = options.outHeight;
        int scaleW = bw / width;
        int scaleH = bh / heigth;

        //选取宽或高中比较大的比例，作为最终缩小比例
        int scale = scaleW > scaleH ? scaleW : scaleH;
        //设置稍后解析图片的时候，可以加载图片的具体内容数据
        options.inJustDecodeBounds = false;
        //设置缩小比例
        options.inSampleSize = scale;
        //第二次采样，解析图片，并且获取图像 】
        Bitmap erci = BitmapFactory.decodeStream(inputStream,new Rect(0,0,width,heigth),options);
        return erci;
    }







}
