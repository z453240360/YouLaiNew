package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic;

import java.io.Serializable;

/**
 * 一个图片对象
 *
 * @author Administrator
 */
public class ImageItem implements Serializable {
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    public boolean isSelected = false;
}
