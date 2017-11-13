package com.bm.chengshiyoutian.youlaiwang.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.adapter.ShangPinSouSuoAdapter;
import com.bm.chengshiyoutian.youlaiwang.view.ListViewAdaptWidth;

import java.util.ArrayList;




public class Fragment_ShangPinSouSuo extends Fragment {

    ArrayList<String> datas,datas2;
    private ListViewAdaptWidth lv;
    private LinearLayout ll;
    private ListView lv2;
    private TextView tv_name;
    private LinearLayout ll1;

    public static Fragment_ShangPinSouSuo newInstance(String param1) {
        Fragment_ShangPinSouSuo fragment = new Fragment_ShangPinSouSuo();

        return fragment;
    }

    public Fragment_ShangPinSouSuo() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangpinsousuo, container, false);
        getData();
        initView(view);
        return view;
    }

    private void getData() {
        datas = new ArrayList<>();
        datas2 = new ArrayList<>();
        for (int j = 0; j < 30; j++) {
            datas.add("苹果" + j);
        }


    }


    private void initView(View view) {
        lv = (ListViewAdaptWidth) view.findViewById(R.id.lv);
        ShangPinSouSuoAdapter shangPinSouSuoAdapter = new ShangPinSouSuoAdapter(datas, getActivity());
         final ShangPinSouSuoAdapter shangPinSouSuoAdapter2 = new ShangPinSouSuoAdapter(datas2, getActivity());
        lv.setAdapter(shangPinSouSuoAdapter);
        ll1 = (LinearLayout) view.findViewById(R.id.ll1);

        ll = (LinearLayout) view.findViewById(R.id.ll);
        ViewGroup.LayoutParams layoutParams = ll.getLayoutParams();
        ListAdapter adapter = lv.getAdapter();
        if (adapter == null) {
            return;
        }
        View item = shangPinSouSuoAdapter.getView(0, null, lv);
        item.measure(0, 0);
        int height = item.getMeasuredHeight();
        Toast.makeText(getActivity(), height + "'", Toast.LENGTH_LONG).show();
        tv_name = (TextView) view.findViewById(R.id.tv_name);

        ll.setLayoutParams(layoutParams);
        ll1.setLayoutParams(layoutParams);
        lv2 = (ListView) view.findViewById(R.id.lv2);
        lv2.setAdapter(shangPinSouSuoAdapter2);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_name.setText(datas.get(position));
                datas2.clear();
                datas2.addAll(datas);
                lv2.setAdapter(shangPinSouSuoAdapter2);
            }
        });


    }
}