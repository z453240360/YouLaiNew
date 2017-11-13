package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import java.util.ArrayList;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/3 10:56
 *
 * @Description: 保存索引索票的记录
 */
public class DemandingCertificatesBeans {

    public ArrayList<DemandingCertificates> demandingCertificates = new ArrayList<>();

    public class DemandingCertificates {
        public String name;
        public String contactsPersion;
        public String phone;
        public ArrayList<String> image;

        public DemandingCertificates(String name, String phone, ArrayList<String> image, String contactsPersion) {
            this.name = name;
            this.phone = phone;
            this.image = image;
            this.contactsPersion = contactsPersion;
        }
    }
}
