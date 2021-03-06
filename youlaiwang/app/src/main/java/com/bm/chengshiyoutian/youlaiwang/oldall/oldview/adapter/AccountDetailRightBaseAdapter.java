package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.AccountDetailBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyReciverEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class AccountDetailRightBaseAdapter extends BaseAdapter {
    private Context context;
    private List<AccountDetailBean> mdatas;
    private TextView tv_action_type;
    private TextView tvShenbaoNum;
    private String[] unitArrayType;
    private AddCallBackListener addListener;
    private SubtractCallBackListener subtractListener;
    private GuiGeCallBackListener guiGeListener;
    HashMap<Integer, String> saveMap = new HashMap<>();//这个集合用来存储对应位置上Editext中的文本内容，防止滑动时item数据清空或错乱

    private List<RepositoryBillBean> tempList = new ArrayList<>();
    private Integer tag;

    public AccountDetailRightBaseAdapter(Context context, List<AccountDetailBean> mdatas, AddCallBackListener addListener, SubtractCallBackListener subtractListener, GuiGeCallBackListener guiGeListener, TextView tvShenbaoNum) {
        this.context = context;
        this.mdatas = mdatas;
        this.addListener = addListener;
        this.subtractListener = subtractListener;
        this.guiGeListener = guiGeListener;
        this.tvShenbaoNum = tvShenbaoNum;

    }

    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.item_account_detail_new_right, null);
        RelativeLayout rl_two_level = (RelativeLayout) view.findViewById(R.id.rl_two_level);
        RelativeLayout rl_last_level = (RelativeLayout) view.findViewById(R.id.rl_last_level);
        final AccountDetailBean item = mdatas.get(position);
        if (item.isClick() == true) {//如果还有下一级
            rl_two_level.setVisibility(View.VISIBLE);
            rl_last_level.setVisibility(View.GONE);
            TextView tv_two_level_name = (TextView) view.findViewById(R.id.tv_two_level_name);//名称
            tv_two_level_name.setText(item.getClassName());
            TextView tv_two_level_num = (TextView) view.findViewById(R.id.tv_two_level_num);//几种
            tv_two_level_num.setText(item.getNum() + "种");
        } else {//到最后一级了
            TextView tv_lastlevel_name = (TextView) view.findViewById(R.id.tv_lastlevel_name);//名称
            final RelativeLayout rl_select_unittype = (RelativeLayout) view.findViewById(R.id.rl_select_unittype); //规格
            tv_action_type = (TextView) view.findViewById(R.id.tv_action_type);
                tv_action_type.setText(item.getUnitSelectType());//设置规格
            //选择规格
            rl_select_unittype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guiGeListener.selectGuiGe(rl_select_unittype, position);
                }
            });

        //数量
        final MyReciverEditText et_last_num = (MyReciverEditText) view.findViewById(R.id.et_last_num);

            //如果集合里已有数据，回显在对应的item上
            if (RepositoryBillBean.RepositoryBillList.size()>0) {
                for (int i = 0; i < RepositoryBillBean.RepositoryBillList.size(); i++) {
                    if (RepositoryBillBean.RepositoryBillList.get(i).productName.equals(item.getClassName())){
                        item.setGuigeNum(RepositoryBillBean.RepositoryBillList.get(i).productNum);
                    }
                }
            }

            et_last_num.setText(item.getGuigeNum());
            et_last_num.setTag(position);//设置editext一个标记setGuigeNum

        ImageView iv_jian = (ImageView) view.findViewById(R.id.iv_jian);
        ImageView iv_jia = (ImageView) view.findViewById(R.id.iv_jia);

        //减少产品的数量
        iv_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractListener.subtractNum(position);
            }
        });
        iv_jia.setOnClickListener(new View.OnClickListener() {//增加产品的数量
            @Override
            public void onClick(View v) {
                addListener.addNum(position);
            }
        });

        tv_lastlevel_name.setText(item.getClassName());
        rl_two_level.setVisibility(View.GONE);
        rl_last_level.setVisibility(View.VISIBLE);

            et_last_num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    tag = (Integer) et_last_num.getTag();

                    if ("".equals(s.toString())){
                        saveMap.put(tag, "0");
                    }

                    else {

                        saveMap.put(tag, s.toString());//在这里根据position去保存文本内容
                    }
                    getThisListViewInfo(tag,item);
                }
            });
        }

            return view;
        }

    /**
     * 如果用户在editText上输入数字了记录下来
     * @param tag
     * @param item
     */
            private void getThisListViewInfo(int tag,AccountDetailBean item) {
                RepositoryBillBean beans = new RepositoryBillBean();
                String s = saveMap.get(tag);
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                      s = s.subSequence(1, 2).toString();

                    }
                }
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().substring(0, s.toString().indexOf(".") + 3);
                    }
                }
                    beans.productName = item.getClassName();
                    beans.productNum = s;
                    beans.productNnit = item.getUnitSelectType();
                    beans.productAllNnit = item.getUnitType();
                    RepositoryBillBean.RepositoryBillList.add(beans);

              //如果有重复数据就把重复的删除掉
                for (int i = 0; i < RepositoryBillBean.RepositoryBillList.size() - 1; i++) {
                    for (int f = RepositoryBillBean.RepositoryBillList.size() - 1; f > i; f--) {
                        if (RepositoryBillBean.RepositoryBillList.get(f).productName.equals(RepositoryBillBean.RepositoryBillList.get(i).productName)) {
                            RepositoryBillBean.RepositoryBillList.get(f).productTime = RepositoryBillBean.RepositoryBillList.get(i).productTime;
                            RepositoryBillBean.RepositoryBillList.get(f).productPiHao = RepositoryBillBean.RepositoryBillList.get(i).productPiHao;

                            RepositoryBillBean.RepositoryBillList.remove(i);
                        }
                    }
                }
//
                for (int i = 0; i < RepositoryBillBean.RepositoryBillList.size(); i++) {
                  if (RepositoryBillBean.RepositoryBillList.get(i).productNum.equals("0")||RepositoryBillBean.RepositoryBillList.get(i).productNum.equals("0.")||RepositoryBillBean.RepositoryBillList.get(i).productNum.equals("0.0")||RepositoryBillBean.RepositoryBillList.get(i).productNum.equals("0.00")){
                      RepositoryBillBean.RepositoryBillList.remove(i);
                  }
                }
                tvShenbaoNum.setText(RepositoryBillBean.RepositoryBillList.size()+RepositoryBillBean.OtherRepositoryBillList.size()+"");

            }



    //增加数量的回调
    public interface AddCallBackListener{
        void addNum(int p);
    }
    //减少数量的回调
    public interface SubtractCallBackListener{
        void subtractNum(int p);
    }

    //选择规格
    public interface GuiGeCallBackListener{
        void selectGuiGe(RelativeLayout r, int p);
    }

    //实时设置修改集合里对应的规格
        public void setUnit(String name, String unit){
            for (int i = 0; i < RepositoryBillBean.RepositoryBillList.size(); i++) {
                if (RepositoryBillBean.RepositoryBillList.get(i).productName.equals(name)){
                    RepositoryBillBean.RepositoryBillList.get(i).productNnit = unit;
                }
            }
            }

}