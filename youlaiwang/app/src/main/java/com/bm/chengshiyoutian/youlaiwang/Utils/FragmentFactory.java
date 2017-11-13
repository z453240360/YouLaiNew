package com.bm.chengshiyoutian.youlaiwang.Utils;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_All;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiFaHuo;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiFuKuan;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiPingJia;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_DaiShouHuo;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_ShangPinSouSuo;
import com.bm.chengshiyoutian.youlaiwang.fragment.Fragment_YiQuXiao;


public class FragmentFactory {
    public static Fragment createDingDanFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = Fragment_All.newInstance("全部订单");
                break;
            case 1:
                fragment = Fragment_DaiFuKuan.newInstance("1111");
                break;
            case 2:
                fragment = Fragment_DaiFaHuo.newInstance("2222");
                break;
            case 3:
                fragment = Fragment_DaiShouHuo.newInstance("收货");
                break;
            case 4:
                fragment = Fragment_DaiPingJia.newInstance("评价");
                break;
            case 5:
                fragment = Fragment_YiQuXiao.newInstance("已取消订单");
                break;

        }
        return fragment;
    }

    public static Fragment createShangPinFragment(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = Fragment_ShangPinSouSuo.newInstance("1111");
                break;
            case 1:
                fragment = Fragment_ShangPinSouSuo.newInstance("2222");
                break;
            case 2:
                fragment = Fragment_ShangPinSouSuo.newInstance("3333");
                break;
            case 3:
                fragment = Fragment_ShangPinSouSuo.newInstance("3333");
                break;
            case 4:
                fragment = Fragment_ShangPinSouSuo.newInstance("1111");
                break;
            case 5:
                fragment = Fragment_ShangPinSouSuo.newInstance("2222");
                break;
            case 6:
                fragment = Fragment_ShangPinSouSuo.newInstance("3333");
                break;
            case 7:
                fragment = Fragment_ShangPinSouSuo.newInstance("3333");
                break;
            case 8:
                fragment = Fragment_ShangPinSouSuo.newInstance("3333");
                break;
        }
        return fragment;
    }

}