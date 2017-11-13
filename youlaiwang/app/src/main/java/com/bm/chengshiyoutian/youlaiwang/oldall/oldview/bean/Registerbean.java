package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/6/1 13:56
 *
 * @Description: 注册实体类
 */
public class Registerbean implements Parcelable {


    public static final Creator<Registerbean> CREATOR = new Creator<Registerbean>() {
        public Registerbean createFromParcel(Parcel source) {
            return new Registerbean(source);
        }

        public Registerbean[] newArray(int size) {
            return new Registerbean[size];
        }
    };
    public String tel;//注册的手机号
    public String pwd;//注册密码
    public ArrayList<String> bitmapDrr;//餐厅相关证件
    public ArrayList<String> bitmapQiYeData;//企业门头照
    public String licenseNo = "";//食品经营许可证编号
    public String licenseNoDate = "";//食品经营许可证有效期

    public String title;//餐厅名称
    public String industryId;//主体业态-
    public String proid;//省id
    public String cityid;//市id
    public String areaid = "";//区id
    public String streetid;//街道id
    public String address;//具体地址
    public String restaurantPrincipal = "";//餐厅负责人
    public String principalPhone = "";//负责人电话
    public String restaurantPhone = "";//餐厅电话
    public String restaurantImage = "";//餐厅图片
    public String businessNum = "";//营业执照或其他经营许可证号
    public String businessNumDate = "";//营业执照或其他经营许可证号有效期
    public String RegisterArea ;
    public String RegisterStreet ;
    public Registerbean() {
    }

    protected Registerbean(Parcel in) {
        this.tel = in.readString();
        this.pwd = in.readString();
        this.bitmapDrr = in.createStringArrayList();
        this.bitmapQiYeData = in.createStringArrayList();
        this.licenseNo = in.readString();
        this.title = in.readString();
        this.industryId = in.readString();
        this.proid = in.readString();
        this.cityid = in.readString();
        this.areaid = in.readString();
        this.streetid = in.readString();
        this.address = in.readString();
        this.restaurantPrincipal = in.readString();
        this.principalPhone = in.readString();
        this.restaurantPhone = in.readString();
        this.restaurantImage = in.readString();
        this.businessNum = in.readString();
        this.businessNumDate = in.readString();
        this.licenseNoDate = in.readString();
        this.RegisterArea = in.readString();
        this.RegisterStreet = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tel);
        dest.writeString(this.pwd);
        dest.writeStringList(this.bitmapDrr);
        dest.writeStringList(this.bitmapQiYeData);
        dest.writeString(this.licenseNo);
        dest.writeString(this.title);
        dest.writeString(this.industryId);
        dest.writeString(this.proid);
        dest.writeString(this.cityid);
        dest.writeString(this.areaid);
        dest.writeString(this.streetid);
        dest.writeString(this.address);
        dest.writeString(this.restaurantPrincipal);
        dest.writeString(this.principalPhone);
        dest.writeString(this.restaurantPhone);
        dest.writeString(this.restaurantImage);
        dest.writeString(this.businessNum);
        dest.writeString(this.businessNumDate);
        dest.writeString(this.licenseNoDate);
        dest.writeString(this.RegisterArea);
        dest.writeString(this.RegisterStreet);
    }
}
