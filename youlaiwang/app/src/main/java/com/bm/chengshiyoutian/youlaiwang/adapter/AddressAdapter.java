package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.DialogUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.GsonUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.XiuGaiDiZhiActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.ShanChuBean;
import com.bm.chengshiyoutian.youlaiwang.bean.WoDeShouHuoDiZhiBena;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;




/**
 * Created by MingweiLi on 2017/4/5.
 */

public class AddressAdapter extends BaseAdapter {
    List<WoDeShouHuoDiZhiBena.DataBean> data;
SharedPreferences sp;
    public AddressAdapter(List<WoDeShouHuoDiZhiBena.DataBean> data, String token, Context mContext) {
        this.data = data;
        this.token = token;

        this.mContext = mContext;
        sp=mContext.getSharedPreferences(MyRes.CONFIG,0);
    }

    // 定义Context
    String token;

    private Context mContext;


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
    public View getView(final int position, View convertView, ViewGroup parent)

    {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_my_address, null);
            viewHolder = new ViewHolder();
            viewHolder.yv_name = (TextView) view.findViewById(R.id.yv_name);
            viewHolder.tv_num = (TextView) view.findViewById(R.id.tv_num);
            viewHolder.tv_address = (TextView) view.findViewById(R.id.tv_address);
            viewHolder.iv_check = (ImageView) view.findViewById(R.id.iv_check);
            viewHolder.tv_check = (TextView) view.findViewById(R.id.tv_check);
            viewHolder.ll1 = (LinearLayout) view.findViewById(R.id.ll_xiugai);
            viewHolder.ll2 = (LinearLayout) view.findViewById(R.id.ll_shanchu);
            view.setTag(viewHolder);


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();


        }
        if (data.get(position).getIs_default() == 1) {
            viewHolder.iv_check.setImageResource(R.mipmap.shezhi_xuanzhhong);
            viewHolder.tv_check.setTextColor(Color.rgb(118,201,51));

        } else {
            viewHolder.iv_check.setImageResource(R.mipmap.shezhi_weixuanzhong);
            viewHolder.tv_check.setTextColor(0xff333333);


        }

        viewHolder.tv_address.setText(data.get(position).getCityinfo() + data.get(position).getAreainfo());
        viewHolder.tv_num.setText(data.get(position).getMobile() + "");
        viewHolder.yv_name.setText(data.get(position).getName());
        //修改
        viewHolder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, XiuGaiDiZhiActivity.class);

                String address_id = data.get(position).getAddress_id() + "";
                intent.putExtra("address_id", address_id);


                mContext.startActivity(intent);
            }
        });
        //删除
        viewHolder.ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = View.inflate(mContext, R.layout.dialog_shanchu, null);
                final AlertDialog alertDialog = DialogUtils.ShowDialog2(view1, mContext);
                view1.findViewById(R.id.dialog_dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                view1.findViewById(R.id.dialog_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/address/destroy", RequestMethod.POST);
                        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN,""));
                        stringRequest.add("_method", "delete");
                        stringRequest.add("address_id", data.get(position).getAddress_id());
                        //   ShowToast.showToast(token);
                        CallServer.getInstance().add(1, stringRequest, new OnResponseListener() {
                            @Override
                            public void onStart(int what) {

                            }

                            @Override
                            public void onSucceed(int what, Response response) {
                                ShanChuBean shanChuBean = GsonUtils.getInstance().fromJson((String) response.get(), ShanChuBean.class);

                                if (shanChuBean.getCode() == 200

                                        ) {
                                    alertDialog.dismiss();
                                    ShowToast.showToast("删除成功");
                                    data.remove(position);
                                    notifyDataSetChanged();

                                }
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


            }
        });

        //设为默认地址
        viewHolder.iv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/user/address/default", RequestMethod.POST);
                stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN,""));
                stringRequest.add("_method", "patch");
                stringRequest.add("address_id", data.get(position).getAddress_id() + "");
                CallServer.getInstance().add(312, stringRequest, new OnResponseListener() {
                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject((String) response.get());
                            String code = jsonObject.getString("code");
                            String msg = jsonObject.getString("msg");

                            for (int i = 0; i < data.size(); i++) {
                                data.get(i).setIs_default(2);
                            }
                            data.get(position).setIs_default(1);
                            notifyDataSetChanged();
                            ShowToast.showToast(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

        return view;

    }

    public class ViewHolder {

        public TextView yv_name;
        public TextView tv_num;
        public TextView tv_address;
        public ImageView iv_check;
        public TextView tv_check;
        LinearLayout ll1, ll2;

    }
}
