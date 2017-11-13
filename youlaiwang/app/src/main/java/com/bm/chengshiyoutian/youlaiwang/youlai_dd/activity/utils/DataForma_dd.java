package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/11/2.
 */

public class DataForma_dd {

    public static String getFloat(int data){

        //格式化数据
        float v = (float)(data);
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String dd=fnum.format(v);
        return dd;
    }

    public static String getFloatByFloat(float data){

        DecimalFormat fnum = new DecimalFormat("##0.00");
        String dd=fnum.format(data);
        return dd;
    }


    //double类型保留两位小数
    public static String getDoubleByDouble(double data){

        DecimalFormat fnum = new DecimalFormat("##0.00");
        String dd=fnum.format(data);
        return dd;
    }
}
