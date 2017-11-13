package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.interfaces;

import com.android.pc.ioc.internet.ResponseEntity;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/25 14:59
 *
 * @Description: 基础接口
 */
public interface BaseInterface {

    void init();

    void initView();

    void initData();

    void setListener();

    void ok(ResponseEntity entity);

    void err(ResponseEntity entity);
}
