package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.bean.ChangJianWenTiBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;


/**
 * Created by sld on 2017/5/12.
 */

public class ChangJianWenTiLVAdapter extends BaseAdapter {


    private Map<Integer, Boolean> map = new HashMap<>();

    public ChangJianWenTiLVAdapter(List<ChangJianWenTiBean.DataBeanX.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
        initMap();
    }

    private void initMap() {
        if (data!=null) {
            for (int i = 0; i <data.size(); i++) {
                map.put(i,false);
            }
        }
    }

    List<ChangJianWenTiBean.DataBeanX.DataBean> data;
    Context context;


    @Override
    public int getCount() {
        if (null != data) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_changjianwenti, null);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.tv1);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.tv2);

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv1);

            viewHolder.ll2 = (LinearLayout) convertView.findViewById(R.id.ll2);
            viewHolder.ll1 = (LinearLayout) convertView.findViewById(R.id.ll1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (map.get(position)==null){
            map.put(position,false);
        }

        viewHolder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (map.get(position)){
                    map.put(position,false);
                }else {
                    map.put(position,true);
                }
            }
        });



        if (map.get(position)){
            viewHolder.ll2.setVisibility(View.GONE);
        }else {
            viewHolder.ll2.setVisibility(View.VISIBLE);
        }
        viewHolder.textView1.setText(data.get(position).getArticle_title());
        viewHolder.textView2.setText(data.get(position).getArticle_content());

        return convertView;
    }

    class ViewHolder {
        TextView textView1, textView2;
        ImageView imageView;
        LinearLayout ll2,ll1;
    }
}