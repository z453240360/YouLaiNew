package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.RepositoryBillAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.MyUtils;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.MyListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 其他食品分类
 */
public class OtherFoodsLevelActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right_two)
    TextView tvRightTwo;
    @Bind(R.id.lv_mingxi_data)
    MyListView lvMingxiData;
    @Bind(R.id.tv_add_data)
    TextView tvAddData;
    @Bind(R.id.bt_submit)
    Button btSubmit;

    private ArrayList<RepositoryBillBean> mRepositoryBillBeans;
    private RepositoryBillAdapter mRepositoryBillAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_foods_level);
        ButterKnife.bind(this);
        title.setText("其他食品");
        tvRightTwo.setVisibility(View.VISIBLE);
        tvRightTwo.setText("完成");
        initData();
        initListener();
    }

    private void initListener() {
        ivLeft.setOnClickListener(this);
        tvAddData.setOnClickListener(this);
        tvRightTwo.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
    }

    public void initData() {
        mRepositoryBillAdapter = new RepositoryBillAdapter(this);
        lvMingxiData.setAdapter(mRepositoryBillAdapter);
        mRepositoryBillBeans = new ArrayList<>();
        if (RepositoryBillBean.OtherRepositoryBillList == null || RepositoryBillBean.OtherRepositoryBillList.size() == 0) {
            for (int i = 0; i < 3; i++) {
                mRepositoryBillBeans.add(new RepositoryBillBean());
            }
        } else {
            mRepositoryBillBeans.addAll(RepositoryBillBean.OtherRepositoryBillList);
            for (int i = RepositoryBillBean.OtherRepositoryBillList.size(); i < 3; i++) {
                mRepositoryBillBeans.add(new RepositoryBillBean());
            }
        }
        mRepositoryBillAdapter.setData(mRepositoryBillBeans);
        mRepositoryBillAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_add_data:
                mRepositoryBillBeans.add(new RepositoryBillBean());
                mRepositoryBillAdapter.setData(mRepositoryBillBeans);
                mRepositoryBillAdapter.notifyDataSetChanged();
                lvMingxiData.setSelection(mRepositoryBillBeans.size() - 1);
                break;
            case R.id.tv_right_two:
            case R.id.bt_submit:

                //将数据存到其他食品的集合里，用于回显数据。

                ArrayList<RepositoryBillBean> data = mRepositoryBillAdapter.getData();
                ArrayList<RepositoryBillBean> repositoryBillBeans = MyUtils.selectHasNums(data);

               for (int i = 0;i<repositoryBillBeans.size();i++){//删除重名的数据
                   String productName = repositoryBillBeans.get(i).productName;
                   for (int y = 0;y< RepositoryBillBean.OtherRepositoryBillList.size();y++){
                  if (productName.equals(RepositoryBillBean.OtherRepositoryBillList.get(y).productName)){
                      RepositoryBillBean.OtherRepositoryBillList.remove(y);
                     }
                   }
               }
                RepositoryBillBean.OtherRepositoryBillList.addAll(repositoryBillBeans);
                //去掉数量是0或者没有名称，规格的数据
                RepositoryBillBean.OtherRepositoryBillList = MyUtils.selectHasNums(RepositoryBillBean.OtherRepositoryBillList);

//                RepositoryBillBean.RepositoryBillList.addAll(RepositoryBillBean.OtherRepositoryBillList );
                finish();
                break;
        }
    }
}
