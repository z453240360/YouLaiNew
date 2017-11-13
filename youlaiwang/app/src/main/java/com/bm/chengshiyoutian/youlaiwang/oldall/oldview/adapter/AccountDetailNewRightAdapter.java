package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
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
public class AccountDetailNewRightAdapter extends CommonAdapter<AccountDetailBean> implements AdapterView.OnItemClickListener {
private List<RepositoryBillBean> templist = new ArrayList<>();
    private Context mcontext;
    private AddCallBackListener addListener;
    private SubtractCallBackListener subtractListener;
    private GuiGeCallBackListener guiGeListener;
    private String[] unitArrayType;
    private TextView tv_action_type;
    private EditTextChangedCallBackListener editListener;
    HashMap<Integer, String> saveMap;//这个集合用来存储对应位置上Editext中的文本内容
    private Integer tag;

    public AccountDetailNewRightAdapter(Context context, List<AccountDetailBean> mDatas, int itemLayoutId, AddCallBackListener addListener, SubtractCallBackListener subtractListener, GuiGeCallBackListener guiGeListener
     , EditTextChangedCallBackListener editListener
    ) {
        super(context, mDatas, itemLayoutId);
        this.addListener = addListener;
        this.subtractListener = subtractListener;
        this.mcontext = context;
        this.guiGeListener = guiGeListener;
        this.editListener = editListener;
        saveMap = new HashMap<Integer, String>();
    }

    @Override
    public void convert(ViewHolder helper, final AccountDetailBean item, final int position) {

        RelativeLayout rl_two_level = helper.getView(R.id.rl_two_level);
        RelativeLayout rl_last_level = helper.getView(R.id.rl_last_level);

        if (item.isClick()==true){//如果还有下一级
            rl_two_level.setVisibility(View.VISIBLE);
            rl_last_level.setVisibility(View.GONE);
            TextView tv_two_level_name = helper.getView(R.id.tv_two_level_name);//名称
            tv_two_level_name.setText(item.getClassName());
            TextView tv_two_level_num = helper.getView(R.id.tv_two_level_num);//几种
            tv_two_level_num.setText(item.getNum()+"种");
        }else {//到最后一级了
            TextView tv_lastlevel_name = helper.getView(R.id.tv_lastlevel_name);//名称
            final RelativeLayout rl_select_unittype = helper.getView(R.id.rl_select_unittype); //规格
            tv_action_type = helper.getView(R.id.tv_action_type);
            String unitType = item.getUnitType();
            unitArrayType = unitType.split(",");
            tv_action_type.setText(unitArrayType[0]);//设置规格
            //选择规格
            rl_select_unittype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guiGeListener.selectGuiGe(rl_select_unittype,position);

                }
            });


                //数量
                 final MyReciverEditText et_last_num = helper.getView(R.id.et_last_num);



            et_last_num.setText(item.getGuigeNum());

            et_last_num.setTag(position);//设置editext一个标记
            et_last_num.clearFocus();//清除焦点  不清除的话因为ite
            ImageView iv_jian = helper.getView(R.id.iv_jian);
            ImageView iv_jia = helper.getView(R.id.iv_jia);
            //只能输入数字和小数点
//            et_last_num.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            //减少产品的数量
            iv_jian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    subtractListener.subtractNum(position);
                    getThisListViewInfo(position,item);
                }
            });
            iv_jia.setOnClickListener(new View.OnClickListener() {//增加产品的数量
                @Override
                public void onClick(View v) {
//                 addListener.addNum(position);
                    getThisListViewInfo(position,item);
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
                editListener.editTextChanged();
                     tag=   (Integer) et_last_num.getTag();
                    saveMap.put(tag, s.toString());//在这里根据position去保存文本内容


//                    getThisListViewInfo(position,item);
                }
            });

        }

    }


            private void getThisListViewInfo(int position,AccountDetailBean item) {
                RepositoryBillBean beans = new RepositoryBillBean();
                if (!"".equals(saveMap.get(position))&&saveMap.get(position)!=null&& Integer.parseInt(saveMap.get(position))>0) {
                    beans.productName = item.getClassName();
                    beans.productNum = saveMap.get(position);
                    beans.productNnit = item.getUnitType();
                    RepositoryBillBean.RepositoryBillList.add(beans);
                }

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
                Log.i("Home", RepositoryBillBean.RepositoryBillList+"");
            }






    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tv_action_type.setText(unitArrayType[position]);
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



    public interface EditTextChangedCallBackListener{
        void editTextChanged();
    }

}