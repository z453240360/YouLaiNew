package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import java.util.List;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 *
 * @author yangjie
 *         create at 2016/4/21 14:25
 * @Description 采购记录
 */
public class ProcurementRecordNewBean {

    public String supplierName;//供应商
    public String time;//时间
    public String year;//时间
    public String goodsName;//商品name
    public String goodsTypeId;//类型
    public String number;
    public String unit;//单位
    public String licenseImg;//营业执照
    public String bill;//票据
    public String supplierLicense;//供货商证件
    public List<String> billImageLists;//票据和供货商证件集合,需要在索票记录中显示（优先显示票据）


    public ProcurementRecordNewBean(String time, String goodsName, String supplierName, String goodsTypeId,
                                    String number, String unit, String licenseImg, String bill, String supplierLicense, List<String> billImageLists, String year) {
        this.supplierName = supplierName;
        this.time = time;
        this.goodsName = goodsName;
        this.goodsTypeId = goodsTypeId;
        this.number = number;
        this.unit = unit;
        this.licenseImg = licenseImg;
        this.bill = bill;
        this.supplierLicense = supplierLicense;
        this.billImageLists = billImageLists;
        this.year = year;
    }
}
