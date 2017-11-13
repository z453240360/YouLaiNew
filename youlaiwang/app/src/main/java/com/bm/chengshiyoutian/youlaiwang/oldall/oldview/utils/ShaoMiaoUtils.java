package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/7/25 14:54
 *
 * @Description: 二维码扫描 对二维码扫描的结果处理
 */
public class ShaoMiaoUtils {

    /**
     * @param messageResult
     * @return 获取注册号
     */
    public static String shaoMiaoMessageZhuCheHao(String messageResult) {
        if (!MyUtils.isEmpty(messageResult)) {
            int zhuCheHao = messageResult.indexOf("注册号");
            if (zhuCheHao >= 0) {
                return actionString(messageResult, zhuCheHao, "注册号");
            } else {
                int qiYueZhuCheHao = messageResult.indexOf("企业注册号");
                if (qiYueZhuCheHao >= 0) {
                    return actionString(messageResult, qiYueZhuCheHao, "企业注册号");
                } else {
                    int yingYueZhiZhaoZhuCheHao = messageResult.indexOf("营业执照注册号");
                    if (yingYueZhiZhaoZhuCheHao >= 0) {
                        return actionString(messageResult, yingYueZhiZhaoZhuCheHao, "营业执照注册号");
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param messageResult
     * @return 获取企业名称
     */
    public static String shaoMiaoMessageStoreName(String messageResult) {
        if (!MyUtils.isEmpty(messageResult)) {
            int mingChen = messageResult.indexOf("名称");
            if (mingChen >= 0) {
                return actionString(messageResult, mingChen, "名称");
            } else {
                int qiYueNingChen = messageResult.indexOf("企业名称");
                if (qiYueNingChen >= 0) {
                    return actionString(messageResult, qiYueNingChen, "企业名称");
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * @param messageResult
     * @return 获取商家负责人
     */
    public static String shaoMiaoMessageStoreBoss(String messageResult) {
        if (!MyUtils.isEmpty(messageResult)) {
            int faDingDaiBiaoRen = messageResult.indexOf("法定代表人");
            if (faDingDaiBiaoRen >= 0) {
                return actionString(messageResult, faDingDaiBiaoRen, "法定代表人");
            } else {
                int jinYingZhe = messageResult.indexOf("经营者");
                if (jinYingZhe >= 0) {
                    return actionString(messageResult, jinYingZhe, "经营者");
                } else {
                    int touZhiRen = messageResult.indexOf("投资人");
                    if (touZhiRen >= 0) {
                        return actionString(messageResult, touZhiRen, "投资人");
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    private static String actionString(String messageResult, int startIndex, String flagMessage) {
        String substring = messageResult.substring(startIndex, messageResult.length());
        int i = substring.indexOf(";");
        if (i > 0) {
            String substringResult = substring.substring(flagMessage.length() + 1, i);
            return substringResult;
        }
        return null;
    }
}
