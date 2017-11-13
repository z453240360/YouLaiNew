package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean;

import java.util.List;

/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/7/27 10:01
 *
 * @Description: 消息
 */
public class NewsBean {

    public List<NewsBeanMessage> ds;

    public class NewsBeanMessage {
        public String AddTimeStr;
        public String AlertCount;
        public String Content;
        public String Id;
        public String Status;
        public String Title;
        public String Type;
        public String UserId;
        public String row_number;
    }
}
