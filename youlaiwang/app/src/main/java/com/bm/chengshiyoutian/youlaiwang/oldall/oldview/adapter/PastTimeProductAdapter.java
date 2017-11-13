package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.PoPBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;

import java.util.List;



/**
 * Copyright © 2016 蓝色互动. All rights reserved.
 * 作者:杨杰 on 2016/5/27 16:14
 *
 * @Description: 过期产品/不合格  适配器
 */
public class PastTimeProductAdapter extends BaseAdapter {

    private Context mContext;
    private List<RepositoryBillBean> mPastTimeProductBeans;//输入框中选中的产品
    private List<PoPBean> mPastTimeProductAllBeans;//所有的过期产品

    public PastTimeProductAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<PoPBean> pastTimeProductAllBeans, List<RepositoryBillBean> pastTimeProductBeans) {
        mPastTimeProductAllBeans = pastTimeProductAllBeans;
        mPastTimeProductBeans = pastTimeProductBeans;
    }

    @Override
    public int getCount() {
        return mPastTimeProductBeans != null ? mPastTimeProductBeans.size() : 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.item_repository_bill, null);
        TextView et_product_name = UniversalViewHolder.get(convertView, R.id.et_product_name);
        TextView et_product_unit = UniversalViewHolder.get(convertView, R.id.et_product_unit);
        RepositoryBillBean repositoryBillBean = mPastTimeProductBeans.get(position);
        if (!TextUtils.isEmpty(repositoryBillBean.productName)) {
            et_product_name.setText(repositoryBillBean.productName);
            et_product_unit.setText(repositoryBillBean.productNnit);
        }
        //setListener(et_product_name, et_product_unit, repositoryBillBean);
        return convertView;
    }

    private void setListener(final TextView textViewName, final TextView textViewUnit, final RepositoryBillBean repositoryBillBean) {
        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseStreet(textViewName, textViewUnit, repositoryBillBean);
            }
        });
    }

    /**
     * 弹出popwindows 显示商品选择
     */
    private void chooseStreet(final TextView textViewName, final TextView textViewUnit, final RepositoryBillBean repositoryBillBean) {
        Drawable drawable = MyUtils.getDrawaPastTime(mContext, 2);
        textViewName.setCompoundDrawables(null, null, drawable, null);
        ListView listView = new ListView(mContext);
        listView.setDividerHeight(1);
        //listView.setBackgroundColor(Color.WHITE);
        listView.setLayoutParams(new ViewGroup.LayoutParams(textViewName.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        final PopupWindow popupWindow = new PopupWindow(listView, textViewName.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(textViewName);
        listView.setAdapter(new PopAdapter(mPastTimeProductAllBeans, mContext, 100));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoPBean poPBean = mPastTimeProductAllBeans.get(position);
                repositoryBillBean.productName = poPBean.title;
                repositoryBillBean.productNnit = poPBean.id;
                textViewName.setText(poPBean.title);
                textViewUnit.setText(poPBean.id);
                Drawable drawable = MyUtils.getDrawaPastTime(mContext, 1);
                textViewName.setCompoundDrawables(null, null, drawable, null);
                popupWindow.dismiss();
            }
        });
        //pop消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //pop消失就切换箭头
                textViewName.setCompoundDrawables(null, null, MyUtils.getDrawaPastTime(mContext, 1), null);
            }
        });
    }
}
