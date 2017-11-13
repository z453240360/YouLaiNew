package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/25 15:33
 *
 * @Description: 仓库台账实体类
 */
public class RepositoryBillBean implements Parcelable {
    //存放明细的集合
    public static List<RepositoryBillBean> RepositoryBillList = new ArrayList<RepositoryBillBean>();
    //存放其他食品的集合
    public static List<RepositoryBillBean> OtherRepositoryBillList = new ArrayList<RepositoryBillBean>();

    //存放明细+其他食品的总集合
//    public static List<RepositoryBillBean> AllRepositoryBillList = new ArrayList<RepositoryBillBean>();

    public static final Creator<RepositoryBillBean> CREATOR = new Creator<RepositoryBillBean>() {
        public RepositoryBillBean createFromParcel(Parcel source) {
            return new RepositoryBillBean(source);
        }

        public RepositoryBillBean[] newArray(int size) {
            return new RepositoryBillBean[size];
        }
    };
    public String productName;//商品名称
    public String productNum;//商品数量
    public String productNnit;//商品规格
    public String productAllNnit;//商品所有规格
    public String productTime;//保质期限
    public String productPiHao;//生产批号
    public String productTempNum;//商品数量(临时数据在台账销售使用)


    public RepositoryBillBean() {
    }

    public RepositoryBillBean(String productName, String productNum, String productNnit) {
        this.productName = productName;
        this.productNum = productNum;
        this.productNnit = productNnit;

    }


    public RepositoryBillBean(String productName, String productNum, String productNnit, String productTime, String productPiHao) {
        this.productName = productName;
        this.productNum = productNum;
        this.productNnit = productNnit;
        this.productTime = productTime;
        this.productPiHao = productPiHao;
    }

    protected RepositoryBillBean(Parcel in) {
        this.productName = in.readString();
        this.productNum = in.readString();
        this.productNnit = in.readString();
        this.productTime = in.readString();
        this.productPiHao = in.readString();
        this.productTempNum = in.readString();
        this.productAllNnit = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productName);
        dest.writeString(this.productNum);
        dest.writeString(this.productNnit);
        dest.writeString(this.productTime);
        dest.writeString(this.productPiHao);
        dest.writeString(this.productTempNum);
        dest.writeString(this.productAllNnit);
    }

    @Override
    public String toString() {
        return "RepositoryBillBean{" +
                "productName='" + productName + '\'' +
                ", productNum='" + productNum + '\'' +
                ", productNnit='" + productNnit + '\'' +
                ", productTime='" + productTime + '\'' +
                ", productPiHao='" + productPiHao + '\'' +
                ", productPiHao='" + productAllNnit + '\'' +
                ", productTempNum='" + productTempNum + '\'' +
                '}';
    }
}
