package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyEditText;

import java.util.List;



/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class DeclareInventoryAdapter extends CommonAdapter<RepositoryBillBean> {
    private GuiGeCallBackListener guiGeListener;
    private AddCallBackListener addListener;
    private SubtractCallBackListener subtractListener;
    public DeclareInventoryAdapter(Context context, List<RepositoryBillBean> mDatas, int itemLayoutId, GuiGeCallBackListener guiGeListener, AddCallBackListener addListener, SubtractCallBackListener subtractListener) {
        super(context, mDatas, itemLayoutId);
        this.guiGeListener = guiGeListener;
        this.addListener = addListener;
        this.subtractListener = subtractListener;
    }

    @Override
    public void convert(ViewHolder helper, RepositoryBillBean item, final int position) {


        RelativeLayout rl_two_level = helper.getView(R.id.rl_two_level);
        rl_two_level.setVisibility(View.GONE);
            TextView tv_lastlevel_name = helper.getView(R.id.tv_lastlevel_name);//名称
        tv_lastlevel_name.setText(item.productName);
          TextView tv_action_type = helper.getView(R.id.tv_action_type);
        tv_action_type.setText(item.productNnit);
            //数量
            MyEditText et_last_num = helper.getView(R.id.et_last_num);

            et_last_num.setText(item.productNum);
            ImageView iv_jian = helper.getView(R.id.iv_jian);
            ImageView iv_jia = helper.getView(R.id.iv_jia);
//            //只能输入数字和小数点
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

        final RelativeLayout rl_select_unittype = helper.getView(R.id.rl_select_unittype); //规格
        //选择规格
        rl_select_unittype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guiGeListener.selectGuiGe(rl_select_unittype,position);

            }
        });
    }

    //选择规格
    public interface GuiGeCallBackListener{
        void selectGuiGe(RelativeLayout r, int p);
    }


    //增加数量的回调
    public interface AddCallBackListener{
        void addNum(int p);
    }
    //减少数量的回调
    public interface SubtractCallBackListener{
        void subtractNum(int p);
    }
}
