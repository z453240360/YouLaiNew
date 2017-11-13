package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.CommonAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.baseAdapter.ViewHolder;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.SupplierBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SuppliersAdapter extends CommonAdapter<SupplierBean> {
    private Map<Integer, Boolean> isSelected;
    private SupplierCallBack callBack;
    private EditSupplierCallBack esback;
    private List beSelectedData = new ArrayList();
    private List cs = null;
    private boolean defaulteCheck = false;//默认选择
    public SuppliersAdapter(Context context, List mDatas, int itemLayoutId, SupplierCallBack callBack, EditSupplierCallBack esback) {
        super(context, mDatas, itemLayoutId);
        this.callBack = callBack;
        this.esback = esback;
        cs = mDatas;
        if (cs == null || cs.size() == 0)
            return;
        if (isSelected != null)
            isSelected = null;
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < cs.size(); i++) {
            isSelected.put(i, false);
        }
        // 清除已经选择的项
        if (beSelectedData.size() > 0) {
            beSelectedData.clear();
        }
    }

    @Override
    public void convert(ViewHolder helper, SupplierBean item, final int position) {
        TextView tv_supplier = helper.getView(R.id.tv_supplier);
        RelativeLayout rl_all = helper.getView(R.id.rl_all);
        tv_supplier.setText(item.getSupperlierName());

          //单选
        final CheckBox cb = helper.getView(R.id.cb);

        cb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                defaulteCheck = true;
//				   // 当前点击的CB
                boolean cu = !isSelected.get(position);
                // 先将所有的置为FALSE
                for(Integer p : isSelected.keySet()) {
                    isSelected.put(p, false);
                }

                // 再将当前选择CB的实际状态
                isSelected.put(position, cu);
                notifyDataSetChanged();
                beSelectedData.clear();
                if(cu) beSelectedData.add(cs.get(position));
                if (cb.isChecked()) {
                    callBack.getPosition(position);
                }else {
                    callBack.getPosition(-1);
                }
            }
        });
        cb.setChecked(isSelected.get(position));

        if (defaulteCheck==false&&position==0){//默认选中第一个
            cb.setChecked(true);
        }

        //编辑
        ImageView iv_edit = helper.getView(R.id.iv_edit);
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esback.editSupplier(position);
            }
        });
    }

    //单选回调
    public interface SupplierCallBack{
        void getPosition(int position);
    }
    //编辑回调
    public interface EditSupplierCallBack{
        void editSupplier(int position);
    }
}
