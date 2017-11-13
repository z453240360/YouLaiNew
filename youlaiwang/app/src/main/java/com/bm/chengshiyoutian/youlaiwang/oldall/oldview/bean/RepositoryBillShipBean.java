package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class RepositoryBillShipBean implements Parcelable {
    private String purchaserName;
    private String purchaserpeople;
    private String purchaserphone;

    public static final Creator<RepositoryBillShipBean> CREATOR = new Creator<RepositoryBillShipBean>() {
        @Override
        public RepositoryBillShipBean createFromParcel(Parcel in) {
            return new RepositoryBillShipBean(in);
        }

        @Override
        public RepositoryBillShipBean[] newArray(int size) {
            return new RepositoryBillShipBean[size];
        }
    };
    public RepositoryBillShipBean(String purchaserName, String purchaserpeople, String purchaserphone) {
        this.purchaserName = purchaserName;
        this.purchaserpeople = purchaserpeople;
        this.purchaserphone = purchaserphone;

    }
    @Override
    public int describeContents() {
        return 0;
    }

    protected RepositoryBillShipBean(Parcel in) {
        this.purchaserName = in.readString();
        this.purchaserpeople =  in.readString();
        this.purchaserphone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.purchaserName);
        dest.writeString(this.purchaserpeople);
        dest.writeString(this.purchaserphone);

    }

    @Override
    public String toString() {
        return "RepositoryBillShipBean{" +
                "purchaserName='" + purchaserName + '\'' +
                ", purchaserpeople='" + purchaserpeople + '\'' +
                ", purchaserphone='" + purchaserphone + '\'' +
                '}';
    }
}
