package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.ShoppingChildAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.CarBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ShoppingCarBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongdong on 2017/10/22.
 */

public class DingDanView extends FrameLayout {
    private RecyclerView list;


    private LinearLayoutManager manager;
    private ShoppingChildAdapter adapter;

    private List<ShoppingCarBean.DataBeanX.DataBean.SpecBean> mAllDatas = new ArrayList<>();

    public DingDanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DingDanView(Context context) {
        this(context,null);
    }

    public DingDanView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_rcy_view, null);
        addView(view);

        list = (RecyclerView) view.findViewById(R.id.my_rc);
        adapter=new ShoppingChildAdapter(context,mAllDatas);
        manager = new LinearLayoutManager(context);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);


        adapter.setCallBackData(new ShoppingChildAdapter.OnCallBackData() {
            @Override
            public void getList(List<ShoppingCarBean.DataBeanX.DataBean.SpecBean> mDatas) {

            }

            @Override
            public void addClicked(int postion) {
                if (onAddClicked!=null){
                    onAddClicked.addClicked(postion);
                }
            }

            @Override
            public void reduceClicked(int postion) {
                if (reduceClicked!=null){
                    reduceClicked.reduceClicked(postion);
                }
            }
        });
    }

    public void setDatas(List<ShoppingCarBean.DataBeanX.DataBean.SpecBean> datass, int pos){

        mAllDatas.clear();
        mAllDatas.addAll(datass);
        adapter.notifyDataSetChanged();
    }


    private OnCallBackData callBackData;
    private OnAddClicked onAddClicked;
    private OnReduceClicked reduceClicked;

    public void setOnAddClicked(OnAddClicked onAddClicked) {
        this.onAddClicked = onAddClicked;
    }

    public void setReduceClicked(OnReduceClicked reduceClicked) {
        this.reduceClicked = reduceClicked;
    }

    public void setCallBackData(OnCallBackData callBackData) {
        this.callBackData = callBackData;
    }


    public interface OnCallBackData{
        void getList(List<CarBean.DataBeanX.DataBean.GoodsBean> mDatas);
    }

    public interface OnAddClicked{
        void addClicked(int chiledPostion);
    }

    public interface OnReduceClicked{
        void reduceClicked(int chiledPostion);
    }




}
