package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import java.util.ArrayList;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/6/6 09:42
 *
 * @Description: 保存商家信息的相关证件
 */
public class CacheStoreImageDrr {
    public String userId;
    public ArrayList<String> bitmapDrr;

    public CacheStoreImageDrr(String userId, ArrayList<String> bitmapDrr) {
        this.userId = userId;
        this.bitmapDrr = bitmapDrr;
    }
}
