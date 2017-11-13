package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class AccountDetailBean {

    /**
     * Id : 37
     * className : 肉制品
     * unitType : 公斤
     * ClassID : 0
     * click : true
     */

    private int Id;
    private String className;
    private String unitType;
    private String unitSelectType;
    private int ClassID;
    private boolean click;
    private int num;
    private String guigeNum;
    private String productTime;
    private String productPiHao;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int ClassID) {
        this.ClassID = ClassID;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getGuigeNum() {
        return guigeNum;
    }

    public void setGuigeNum(String guigeNum) {
        this.guigeNum = guigeNum;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getProductPiHao() {
        return productPiHao;
    }

    public String getUnitSelectType() {

        return unitSelectType;
    }

    public void setUnitSelectType(String unitSelectType) {
        this.unitSelectType = unitSelectType;
    }

    public void setProductPiHao(String productPiHao) {
        this.productPiHao = productPiHao;
    }

    public static List<AccountDetailBean> getList(JSONArray array){
        List<AccountDetailBean> list = new ArrayList<AccountDetailBean>();
        JSONObject obj = null;
        AccountDetailBean bean =  null;

        for(int i = 0,count = array.length();i<count;i++){
            bean = new AccountDetailBean();
            obj = array.optJSONObject(i);
            bean.setClick(obj.optBoolean("click"));
            bean.setClassName(obj.optString("className"));
            bean.setUnitType(obj.optString("unitType"));
            bean.setNum(obj.optInt("num"));
            bean.setClassID(obj.optInt("ClassID"));
            bean.setId(obj.optInt("Id"));


            list.add(bean);
        }

        return list;
    }

    @Override
    public String toString() {
        return "AccountDetailBean{" +
                "Id=" + Id +
                ", className='" + className + '\'' +
                ", unitType='" + unitType + '\'' +
                ", ClassID=" + ClassID +
                ", click=" + click +
                ", num=" + num +
                ", guigeNum='" + guigeNum + '\'' +
                ", productTime='" + productTime + '\'' +
                ", productPiHao='" + productPiHao + '\'' +
                '}';
    }
}
