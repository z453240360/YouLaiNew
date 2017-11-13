package com.bm.chengshiyoutian.youlaiwang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.chengshiyoutian.youlaiwang.R;


public class LunTanFragment extends android.support.v4.app.Fragment {


    public static LunTanFragment newInstance(String param1) {
        LunTanFragment fragment = new LunTanFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public LunTanFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luntan, container, false);

        return view;
    }



}