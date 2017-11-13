package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by youj on 2016/1/14.
 */
public class RestaurantBean implements Serializable {


    /**
     * 照片名
     */
    public String photoUrl;

    /**
     * 餐厅名
     */
    public String restaurantName;

    /**
     * 评分
     */
    public String grade;

    /**
     * 地址
     */
    public String address;

    /**
     * 餐厅电话
     */
    public String restaurantPhone;

    /**
     * 手机号
     */
    public String mobilePhone;

    /**
     * 菜系名
     */
    public String cookingType;

    /**
     * 菜系id
     */
    public String cookingTypeId;

    /**
     * 积分
     */
    public String integral;
    /**
     * 省
     */
    public String proName;

    /**
     * 市
     */
    public String cityName;

    /**
     * 区
     */
    public String areaName;

    /**
     * 餐厅id
     */
    public String id;

    public String proId;

    public String cityId;

    public String areaId;


    public String caigou; //采购

    public String jiaoyou;//交油

    public String cclj;//餐厨垃圾

    public String dqzhbl;//当前转换比例

    public String distance;//距离
    /**
     * 定位
     */
    public String loction;

    public String industryId;//主体业态id
    public String industryName;//主体业态name

    public String licenseNo;//食品经营许可证

    public String streetName;//街道
    public String streetid;//街道id
    public String manager;//餐厅负责人
    public String managerphone;//餐厅负责人电话
    public List<String> licenseImg;//相关证件
    public String licenseID;//营业执照
    public String licenseDate;//食品经营许可证有效期
    public String licenseTime;//营业执照或其他证件有效期

    //如果为0是商家账号，为1是子账号
    public  int childAccountType = 0;

    public RestaurantBean() {
    }

    public RestaurantBean(String photoUrl, String restaurantName, String grade,
                          String address, String restaurantPhone, String mobilePhone,
                          String cookingType, String integral, String id, String caigou,
                          String jiaoyou, String cclj, String dqzhbl, String distance) {
        this.photoUrl = photoUrl;
        this.restaurantName = restaurantName;
        this.grade = grade;
        this.address = address;
        this.restaurantPhone = restaurantPhone;
        this.mobilePhone = mobilePhone;
        this.cookingType = cookingType;
        this.integral = integral;
        this.id = id;
        this.caigou = caigou;
        this.jiaoyou = jiaoyou;
        this.cclj = cclj;
        this.dqzhbl = dqzhbl;
        this.distance = distance;
    }

    public RestaurantBean(String img_url, String restaurantName, String restaurantAddress,
                          String mobilePhone, String restaurantPhone, String xing,
                          String integral, String cookingType, String cookingTypeId,
                          String proName, String cityName, String areaName, String id,
                          String proId, String cityId, String areaId, String licenseDate, String licenseTime) {
        this.photoUrl = img_url;
        this.restaurantName = restaurantName;
        this.address = restaurantAddress;
        this.mobilePhone = mobilePhone;
        this.restaurantPhone = restaurantPhone;
        this.grade = xing;
        this.integral = integral;
        this.cookingType = cookingType;
        this.cookingTypeId = cookingTypeId;
        this.proName = proName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.id = id;
        this.proId = proId;
        this.cityId = cityId;
        this.areaId = areaId;
        this.licenseDate = licenseDate;
        this.licenseTime = licenseTime;
    }

    public RestaurantBean(String img_url, String restaurantName, String restaurantAddress,
                          String mobilePhone, String restaurantPhone, String xing,
                          String integral, String cookingType, String cookingTypeId,
                          String proName, String cityName, String areaName, String id,
                          String proId, String cityId, String areaId, String licenseDate, String licenseTime, int childAccountType) {
        this.photoUrl = img_url;
        this.restaurantName = restaurantName;
        this.address = restaurantAddress;
        this.mobilePhone = mobilePhone;
        this.restaurantPhone = restaurantPhone;
        this.grade = xing;
        this.integral = integral;
        this.cookingType = cookingType;
        this.cookingTypeId = cookingTypeId;
        this.proName = proName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.id = id;
        this.proId = proId;
        this.cityId = cityId;
        this.areaId = areaId;
        this.licenseDate = licenseDate;
        this.licenseTime = licenseTime;
        this.childAccountType = childAccountType;
    }

    public RestaurantBean(String img_url, String restaurantName, String restaurantAddress,
                          String mobilePhone, String restaurantPhone, String xing,
                          String integral, String cookingType, String cookingTypeId,
                          String proName, String cityName, String areaName, String id,
                          String proId, String cityId, String areaId ) {
        this.photoUrl = img_url;
        this.restaurantName = restaurantName;
        this.address = restaurantAddress;
        this.mobilePhone = mobilePhone;
        this.restaurantPhone = restaurantPhone;
        this.grade = xing;
        this.integral = integral;
        this.cookingType = cookingType;
        this.cookingTypeId = cookingTypeId;
        this.proName = proName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.id = id;
        this.proId = proId;
        this.cityId = cityId;
        this.areaId = areaId;

    }

    public RestaurantBean(String id, String retaurantName, String img_url, String retaurantAddress,
                          String phone, String xing, String distance) {
        this.id = id;
        this.restaurantName = retaurantName;
        this.photoUrl = img_url;
        this.address = retaurantAddress;
        this.grade = xing;
        this.restaurantPhone = phone;
        this.distance = distance;
    }


}
