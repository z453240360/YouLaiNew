package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.PoPBean;

import java.util.List;



/**
 * pop Adapter
 *
 * @author youj
 */
public class PopAdapter extends BaseAdapter {
    private List<PoPBean> list;
    private Context context;
    private int i;

    public PopAdapter(List<PoPBean> list, Context context, int i) {
        super();
        this.list = list;
        this.context = context;
        this.i = i;
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            if (i == 1) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_pop1, null);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_pop, null);
            }
            if (i == 2000) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.green_bg));
                convertView.setPadding(0, DPtoPX(10, context), 0, DPtoPX(10, context));
            }
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            if (i == 100) {
                holder.title.setTextColor(context.getResources().getColor(R.color.black));
            }
            convertView.setTag(holder);
        }
        PoPBean bean = list.get(position);
        holder.title.setText(bean.title);
        return convertView;
    }

    /**
     * 将dp类型的尺寸转换成px类型的尺寸
     *
     * @param size
     * @param context
     * @return
     */
    private int DPtoPX(int size, Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return (int) ((float) size * metrics.density + 0.5);
    }

    class ViewHolder {
        TextView title;
    }
}
