package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import java.util.ArrayList;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/30 10:40
 *
 * @Description: 这个类就是用来携带信息  eventBus
 */
public class EventBusBean {
    private String activityName;
    private ArrayList<RepositoryBillBean> repositoryBillBeans;
    private Registerbean mRegisterbean;

    public Registerbean getRegisterbean() {
        return mRegisterbean;
    }

    public void setRegisterbean(Registerbean registerbean) {

        mRegisterbean = registerbean;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public ArrayList<RepositoryBillBean> getRepositoryBillBeans() {
        return repositoryBillBeans;
    }

    public void setRepositoryBillBeans(ArrayList<RepositoryBillBean> repositoryBillBeans) {
        this.repositoryBillBeans = repositoryBillBeans;
    }
}
