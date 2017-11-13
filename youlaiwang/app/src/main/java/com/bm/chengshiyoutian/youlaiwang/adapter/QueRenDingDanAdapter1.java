package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.QueRenDingDanBena1;
import com.bm.chengshiyoutian.youlaiwang.view.MylistView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by sld on 2017/6/1.
 */

public class QueRenDingDanAdapter1 extends BaseAdapter {
    Context context;
    List<QueRenDingDanBena1.DataBean.GoodsDataBean> goods;

    public QueRenDingDanAdapter1(Context context, List<QueRenDingDanBena1.DataBean.GoodsDataBean> goods) {
        this.context = context;
        this.goods = goods;
        inputContainer = new LinkedHashMap<Integer, Object>();
        if(goods.size() > 0){
            for(int i=0;i<goods.size();i++){
                inputContainer.put(i,"");
            }
        }
        myFoucus = new MyFoucus();
        myWatch = new MyWatch();
    }

    @Override
    public int getCount() {
        return goods.size();
    }

    @Override
    public Object getItem(int position) {
        return goods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_querendingdan1, null);
        TextView tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
        MylistView lv_111 = (MylistView) view.findViewById(R.id.lv_111);
        tv_store_name.setText(goods.get(position).getStore_name() + "");

        ImageView img_call = (ImageView) view.findViewById(R.id.tv_call);//店铺电话








        View foot2 = View.inflate(context, R.layout.item_foot1, null);
        TextView tv_yunfei = (TextView) foot2.findViewById(R.id.tv_yunfei);
        TextView tv_mianyunfei = (TextView) foot2.findViewById(R.id.tv_mianyunfei);
        TextView store_price = (TextView) foot2.findViewById(R.id.store_price);
        EditText tv_liuYan = (EditText) foot2.findViewById(R.id.tv_liuYan);
        // 注册上自己写的焦点监听
        tv_liuYan.setOnFocusChangeListener(myFoucus);
        // setTag是个好东西呀，把position放上去，一会用
        tv_liuYan.setTag(position);
        View currentFocus = ((Activity) context).getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }
        // 为了实现最小的内存开销，复用两个不同的监听对象，通过每次点击的事件来修正mywatch中的position；s
        // 使用remove和add来区别开复用修正和手动添加；之所以费劲的加个remove又加个add也是为了能尽量减少些
        // 思考量，剔除修正EditText时的TextChange监听事件，整个世界都清净了。。。
        tv_liuYan.removeTextChangedListener(myWatch);
        if (inputContainer.containsKey(position)) {
            tv_liuYan.setText(inputContainer.get(position).toString());
        } else {
            tv_liuYan.setText("");
            inputContainer.put(position, "");
        }
        tv_liuYan.addTextChangedListener(myWatch);

        tv_yunfei.setText(goods.get(position).getLogistics_cost() + "");
        tv_mianyunfei.setText("("+"大于"+goods.get(position).getMin_pay_money() + "元免运费)");
        DecimalFormat df   = new DecimalFormat("######0.00");

        store_price.setText( df.format(goods.get(position).getStore_price())+"");
        lv_111.addFooterView(foot2);

        List<QueRenDingDanBena1.DataBean.GoodsDataBean.GoodsBean> goods_two = this.goods.get(position).getGoods();

        QueRenDingDanAdapter2 queRenDingDanAdapter2 = new QueRenDingDanAdapter2(context, goods_two);
        lv_111.setAdapter(queRenDingDanAdapter2);


        return view;
    }


    public class ViewHolder {
        public TextView tv_store_name;
        MylistView lv_111;
        ImageView img_call;
    }


    // 用来存放输入的数据
    public static HashMap<Integer, Object> inputContainer;
    //继承写的两个监听，没用匿名内部类，是为了方便对position这个变量进行操作
    private MyFoucus myFoucus;
    private MyWatch myWatch;

    public HashMap<Integer, Object> getInputContainer() {
        return inputContainer;
    }

    class MyFoucus implements View.OnFocusChangeListener {
        // 当获取焦点时修正myWatch中的position值,这是最重要的一步!
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                int position = (int) v.getTag();
                myWatch.position = position;
            }
        }
    }

    class MyWatch implements TextWatcher {
        // 不得不吐槽一下这里，java的内部类机制怎么就和我想的不一样呢，外部依然能很轻松的访问这个“私有
        // 化的”position，我是不是该去看看《think in java》了。
        private int position;

        @Override
        public void afterTextChanged(Editable s) {

            inputContainer.put(position, s.toString());

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub

        }

    }

}
