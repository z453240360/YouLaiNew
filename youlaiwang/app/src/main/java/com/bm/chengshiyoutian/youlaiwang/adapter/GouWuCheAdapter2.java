package com.bm.chengshiyoutian.youlaiwang.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.SharedPUtils;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.activity.MainActivity;
import com.bm.chengshiyoutian.youlaiwang.activity.ShangPinXiangQingActivity;
import com.bm.chengshiyoutian.youlaiwang.bean.GouWuCheBean;
import com.bumptech.glide.Glide;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.bm.chengshiyoutian.youlaiwang.R.id.et_num;
import static com.bm.chengshiyoutian.youlaiwang.R.id.iv_jian;
import static com.bm.chengshiyoutian.youlaiwang.R.id.pop_callphone;
import static com.bm.chengshiyoutian.youlaiwang.R.id.tv_count;
import static com.bm.chengshiyoutian.youlaiwang.activity.GouWuCheActivity.gouInstance;


/**
 * Created by sld on 2017/5/26.
 */

public class GouWuCheAdapter2 extends BaseAdapter {


    private List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> datas;
    private Context mContext;
    private String Authorizatio;
    private ProgressDialog progressDialog;


    public GouWuCheAdapter2(List<GouWuCheBean.DataBeanX.DataBean.GoodsBean> datas, Context mContext, String Authorizatio) {
        this.datas = datas;
        this.mContext = mContext;
        this.Authorizatio = Authorizatio;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        final int[] x = {datas.get(position).getGoods_num()};
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            view = inflater.inflate(R.layout.item_gouwuche_content, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.danwei = (TextView) view.findViewById(R.id.danwei);
            viewHolder.jiage = (TextView) view.findViewById(R.id.jiage);
            viewHolder.tv_count = (TextView) view.findViewById(tv_count);
            viewHolder.check = (ImageView) view.findViewById(R.id.check);
            viewHolder.iv = (ImageView) view.findViewById(R.id.iv);
            viewHolder.iv_jia = (ImageView) view.findViewById(R.id.iv_jia);
            viewHolder.iv_jian = (ImageView) view.findViewById(iv_jian);

            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


        //手动输入具体数字
        viewHolder.tv_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .create();
                dialog.setView(LayoutInflater.from(mContext).inflate(R.layout.item_goumai, null));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                final EditText et_num = (EditText) dialog.findViewById(R.id.et_num);
                TextView tv_quXiao = (TextView) dialog.findViewById(R.id.tv_quXiao);
                TextView tv_queDing = (TextView) dialog.findViewById(R.id.tv_queDing);
                et_num.setText(viewHolder.tv_count.getText().toString().trim());

                tv_quXiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                tv_queDing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                        stringRequest.addHeader("Authorization", Authorizatio);
                        stringRequest.add("goodsId", datas.get(position).getGoods_id() + "");//商品编号
                        stringRequest.add("specId", datas.get(position).getSpec_id());//规格
                        stringRequest.add("type", 3);
                        stringRequest.add("goodsNum", et_num.getText().toString().trim());
                        CallServer.getInstance().add(12, stringRequest, new OnResponseListener() {
                            @Override
                            public void onStart(int what) {
                            }

                            @Override
                            public void onSucceed(int what, Response response) {
                                String result = response.get().toString();
                                try {
                                    JSONObject object = new JSONObject(result);
                                    if (object.getInt("code") == 200) {
                                        dialog.dismiss();
                                        viewHolder.tv_count.setText(et_num.getText().toString().trim());

                                        String msg = object.getString("msg");
                                        ShowToast.showToast(msg);
                                        String json = "变化";
                                        EventBus.getDefault().post(json);
                                        MainActivity.getData1();
                                        //存储改变后的字符串,商品ID 对应的数量
                                        SharedPUtils.storeString(mContext, datas.get(position).getGoods_id() + "", datas.get(position).getSpec_id() + "东东分割点" + et_num.getText().toString().trim());
                                        SharedPUtils.storeBoolean(mContext, "isChanged", true);




                                        if (gouInstance != null) {
                                            gouInstance.getData();
                                        }

                                    } else {
                                        ShowToast.showToast(object.getString("msg"));
                                    }
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

            }
        });

        if (datas.get(position).tag) {
            viewHolder.check.setImageResource(R.mipmap.gouwuche_xuanzhohng2);
        } else {
            viewHolder.check.setImageResource(R.mipmap.shezhi_weixuanzhong);
        }

        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCheckListener.onCheck(position);
            }
        });

        viewHolder.tv_name.setText(datas.get(position).getGoods_name() + "");
        viewHolder.danwei.setText(datas.get(position).getSpec_text() + "");
        DecimalFormat df = new DecimalFormat("######0.00");
        viewHolder.jiage.setText("¥ " + df.format(datas.get(position).getPrice()) + "");
        viewHolder.tv_count.setText(datas.get(position).getGoods_num() + "");
        Glide.with(mContext)
                .load(datas.get(position).getGoods_cover()).centerCrop()
                .into(viewHolder.iv);

        viewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ShangPinXiangQingActivity.class);
                intent.putExtra("token", datas.get(position).getGoods_id() + "");
                mContext.startActivity(intent);
            }
        });

        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ShangPinXiangQingActivity.class);
                intent.putExtra("token", datas.get(position).getGoods_id() + "");
                mContext.startActivity(intent);
            }
        });

        /**
         *
         * 修改购物车数据，----------增加
         *
         *  --请求网络数据，当在购物车中修改数据时候，保存商品id,缓存在本地，以便更新商品页面的数据
         *
         */


        viewHolder.iv_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                stringRequest.addHeader("Authorization", Authorizatio);
                stringRequest.add("goodsId", datas.get(position).getGoods_id() + "");
                stringRequest.add("specId", datas.get(position).getSpec_id());
                stringRequest.add("type", 1);

                CallServer.getInstance().add(1232, stringRequest, new OnResponseListener() {
                    @Override
                    public void onStart(int what) {
                    }

                    @Override
                    public void onSucceed(int what, Response response) {
                        datas.get(position).setGoods_num(++x[0]);
                        viewHolder.tv_count.setText(datas.get(position).getGoods_num() + "");
                        String s = viewHolder.tv_count.getText().toString();
                        try {
                            JSONObject jsonObject = new JSONObject((String) response.get());
                            String msg = jsonObject.getString("msg");
                            ShowToast.showToast(msg);
                            String json = "变化";
                            EventBus.getDefault().post(json);
                            MainActivity.getData1();

                            SharedPUtils.storeString(mContext, datas.get(position).getGoods_id() + "", datas.get(position).getSpec_id() + "东东分割点" + datas.get(position).getGoods_num() + "");
                            SharedPUtils.storeBoolean(mContext, "isChanged", true);

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

        /**
         *
         * 修改购物车数据，----------减少
         *
         * --请求网络数据，当在购物车中修改数据时候，保存商品id,缓存在本地，以便更新商品页面的数据
         *
         */
        viewHolder.iv_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x[0] < 1) {
                    ShowToast.showToast("已经最少");
                } else {

                    Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
                    stringRequest.addHeader("Authorization", Authorizatio);
                    stringRequest.add("goodsId", datas.get(position).getGoods_id());
                    stringRequest.add("specId", datas.get(position).getSpec_id() + "");
                    stringRequest.add("type", 2);
                    CallServer.getInstance().add(100, stringRequest, new OnResponseListener() {
                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject((String) response.get());
                                String msg = jsonObject.getString("msg");
                                if (jsonObject.getInt("code") == 200) {
                                    ShowToast.showToast(msg);
                                    //x[0]--;
                                    datas.get(position).setGoods_num(--x[0]);
                                    String json = "变化";


                                    //存储改变后的字符串 key=商品id，value=商品规格+”东东分割点“+商品数量；
                                    SharedPUtils.storeString(mContext, datas.get(position).getGoods_id() + "", datas.get(position).getSpec_id() + "东东分割点" + datas.get(position).getGoods_num() + "");
                                    SharedPUtils.storeBoolean(mContext, "isChanged", true);
                                    EventBus.getDefault().post(json);
                                    viewHolder.tv_count.setText(x[0] + "");

                                    MainActivity.getData1();
                                }

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

            }
        });
        return view;

    }


    //获取列表的总价格
    public double getTotalP(){
        double price = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).tag){
                double v = datas.get(i).getPrice() * datas.get(i).getGoods_num();
                price = v+price;
            }
        }

        return price;
    }

    public class ViewHolder {

        public TextView tv_name, danwei, jiage;
        TextView tv_count;
        ImageView check, iv, iv_jian, iv_jia;

    }

    public interface OnCheckListener {
        void onCheck(int pos);
    }


    public OnCheckListener mOnCheckListener;

    public void setOnCheckListener(OnCheckListener mOnCheckListener) {
        this.mOnCheckListener = mOnCheckListener;
    }


    public interface OnCheckListener1 {
        void onCheck(int pos, TextView tv1, int[] arr);

        void totalPrice(int pos, double price);
    }


    public OnCheckListener1 mOnCheckListener1;

    public void setOnCheckListener1(OnCheckListener1 mOnCheckListener) {
        this.mOnCheckListener1 = mOnCheckListener;
    }


    public interface OnCheckListener2 {
        void onCheck(int pos, TextView tv2, int[] arr);
    }


    public OnCheckListener2 mOnCheckListener2;

    public void setOnCheckListener2(OnCheckListener2 mOnCheckListener) {
        this.mOnCheckListener2 = mOnCheckListener;
    }

}
