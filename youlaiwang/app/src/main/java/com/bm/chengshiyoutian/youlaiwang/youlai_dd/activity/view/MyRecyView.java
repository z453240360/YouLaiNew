package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.CarAdapter;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter.ShoppingCarAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/27.
 */

public class MyRecyView extends FrameLayout {
    private RecyclerView recyclerView;
    private TextView postMoney,minPay,shopName;
    private CheckBox checkBox;
    private ShoppingCarAdapter adapter;
    private List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> allGoods = new ArrayList<>();
    private List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> goodss=new ArrayList<>();

    private LinearLayoutManager manager;

    public MyRecyView(Context context) {
        this(context,null);
    }

    public MyRecyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRecyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyler_view, null);
        addView(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.view_reclyer);
        shopName = (TextView) view.findViewById(R.id.shopName);
        postMoney = (TextView) view.findViewById(R.id.postMoney);
        minPay = (TextView) view.findViewById(R.id.minPay);
        checkBox = (CheckBox) view.findViewById(R.id.isSelectedAll);

        adapter = new ShoppingCarAdapter(context,allGoods);
        manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);




    }

    public void setData(List<GouWuCheBean.DataBeanX.DataBean> goods,int postion){
        String store_name = goods.get(postion).getStore_name();
        int min_pay_money = goods.get(postion).getMin_pay_money();
        int logistics_cost = goods.get(postion).getLogistics_cost();
        int store_id = goods.get(postion).getStore_id();

        shopName.setText(""+store_name);
        minPay.setText(""+min_pay_money);
        postMoney.setText(""+logistics_cost);

        List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> goods1 = goods.get(postion).getGoods();
        allGoods.addAll(goods1);
        adapter.notifyDataSetChanged();
    }

}
