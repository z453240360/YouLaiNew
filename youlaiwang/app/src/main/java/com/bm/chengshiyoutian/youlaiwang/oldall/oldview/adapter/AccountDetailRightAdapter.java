package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.AccountDetailBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyEditText;

import java.util.List;


/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class AccountDetailRightAdapter extends CommonAdapter<AccountDetailBean> implements AdapterView.OnItemClickListener {

    private Context mcontext;
    private AddCallBackListener addListener;
    private SubtractCallBackListener subtractListener;
    private GuiGeCallBackListener guiGeListener;
    private String[] unitArrayType;
    private TextView tv_action_type;

    public AccountDetailRightAdapter(Context context, List<AccountDetailBean> mDatas, int itemLayoutId, AddCallBackListener addListener, SubtractCallBackListener subtractListener, GuiGeCallBackListener guiGeListener) {
        super(context, mDatas, itemLayoutId);
        this.addListener = addListener;
        this.subtractListener = subtractListener;
        this.mcontext = context;
        this.guiGeListener = guiGeListener;
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
             MyEditText et_last_num = helper.getView(R.id.et_last_num);

            et_last_num.setText(item.getGuigeNum());
            ImageView iv_jian = helper.getView(R.id.iv_jian);
            ImageView iv_jia = helper.getView(R.id.iv_jia);
            //只能输入数字和小数点
//            et_last_num.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

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



        }


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

}