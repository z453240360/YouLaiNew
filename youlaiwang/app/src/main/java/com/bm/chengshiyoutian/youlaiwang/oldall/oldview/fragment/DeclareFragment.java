package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.DeclareAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.DeclareBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 交油记录申报
 * Created by jayen on 16/1/13.
 */
public class DeclareFragment extends Fragment {
    @Bind(R.id.lv)
    ListView lv;
    private View view;
    private ArrayList<DeclareBean> list = new ArrayList<DeclareBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_declare, container, false);
            ButterKnife.bind(this, view);
            init();
        }

        return view;
    }

    private void init() {
        setData();
        DeclareAdapter adapter = new DeclareAdapter(getActivity(), list, R.layout.item_declare,1);
        lv.setAdapter(adapter);
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            list.add(new DeclareBean());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
