package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinFenLeiActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.ShangPinFenLeiBena;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;



/**
 * Created by Administrator on 2017/5/22.
 */

public class ShangPinLieBiaoLVAdapter extends BaseAdapter {

    List<ShangPinFenLeiBena.DataBean> data;
    Context context;
    String DIANPU_ID;

    public ShangPinLieBiaoLVAdapter(List<ShangPinFenLeiBena.DataBean> data, Context context, String DIANPU_ID) {
        this.data = data;
        this.context = context;
        this.DIANPU_ID = DIANPU_ID;
    }

    @Override
    public int getCount() {
        return data.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shangpinliebiao, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.gridView = (GridView) convertView.findViewById(R.id.gv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.tv_name.setText(data.get(position).getGc_name());
        final List<ShangPinFenLeiBena.DataBean.GoodsClassTwoBean> goods_class_two = data.get(position).getGoods_class_two();
        viewHolder.gridView.setAdapter(new ShangPinLieBiaoGVAdapter(context, goods_class_two));
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                CallServer.getInstance().add(1, NoHttp.createStringRequest(MyRes.BASE_URL + "api/store/" + DIANPU_ID + "/class/" + goods_class_two.get(position).getGc_id()), new OnResponseListener() {
                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response response) {
                        Log.d("sld", (String) response.get());
                        Intent intent= new Intent(context, ShangPinFenLeiActivity.class);
                        String url = MyRes.BASE_URL + "api/store/" + DIANPU_ID + "/class/" + goods_class_two.get(position).getGc_id();
                        intent.putExtra("data",(String) response.get());
                        intent.putExtra("url",url);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailed(int what, Response response) {
                        ShowToast.showToast("联网失败");
                    }

                    @Override
                    public void onFinish(int what) {

                    }
                });

            }
        });
        return convertView;
    }


    class ViewHolder {
        public TextView tv_name;
        GridView gridView;

    }

}
