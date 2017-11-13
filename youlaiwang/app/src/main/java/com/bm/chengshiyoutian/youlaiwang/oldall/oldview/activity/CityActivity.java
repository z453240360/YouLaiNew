package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter.SortAdapter;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.CharacterParser;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.PinyinComparator;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils.SortModel;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 城市选择
 */
public class CityActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_current_city)
    TextView tvCurrentCity;
    @Bind(R.id.ll_hot)
    LinearLayout llHot;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.sidebar)
    SideBar sidebar;
    @Bind(R.id.t1)
    TextView t1;
    @Bind(R.id.t2)
    TextView t2;
    @Bind(R.id.t3)
    TextView t3;
    @Bind(R.id.t4)
    TextView t4;

    //城市集合
    private List<SortModel> SourceDateList;

    private PinyinComparator pinyinComparator;

    private CharacterParser characterParser;

    private TextView dialog;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        init();
        initViews();
    }


    private void init() {
        ivLeft.setOnClickListener(this);
        title.setText("城市选择");
        String cityName = getIntent().getStringExtra("cityName");
        tvCurrentCity.setText(cityName);
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_left:
                finish();
                break;

            case R.id.t1:
                cityName = t1.getText().toString().trim();
                break;
            case R.id.t2:
                cityName = t2.getText().toString().trim();
                break;
            case R.id.t3:
                cityName= t3.getText().toString().trim();
                break;
            case R.id.t4:
                cityName = t4.getText().toString().trim();
                break;
        }
        Intent intent = new Intent();
        intent.putExtra("city", cityName);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 设置城市数据
     */
    private void initViews() {
        SideBar sidebar = (SideBar) findViewById(R.id.sidebar);
        final ListView lv_hot = (ListView) findViewById(R.id.lv);
        lv_hot.setOnItemClickListener(this);
        pinyinComparator = new PinyinComparator();
        characterParser = CharacterParser.getInstance();

        String[] cityArray = getResources().getStringArray(R.array.arr_city);//本地获取测试数据

        SourceDateList = filledData(cityArray);

        final SortAdapter adapter = new SortAdapter(this, SourceDateList);
        Collections.sort(SourceDateList, pinyinComparator);
        lv_hot.setAdapter(adapter);
        sidebar.setTextView(dialog);

        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {

                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lv_hot.setSelection(position);
                }

            }
        });

    }

    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            String pinyin = "";
//			String pinyin = characterParser.getSelling(date[i]);
//			String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortModel.getName().contains("重庆")) {
                pinyin = "C";
            } else if (sortModel.getName().contains("亳州")) {
                pinyin = "B";
            } else {
                pinyin = characterParser.getSelling(date[i]);
            }
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SortModel model = SourceDateList.get(position);
        String name = model.getName();
        Intent intent = new Intent();
        intent.putExtra("city", name);
        setResult(RESULT_OK, intent);
        finish();
    }

}
