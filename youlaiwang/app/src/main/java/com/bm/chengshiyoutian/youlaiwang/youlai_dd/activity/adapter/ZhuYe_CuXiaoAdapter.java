package com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.Utils.CallServer;
import com.bm.chengshiyoutian.youlaiwang.Utils.MyRes;
import com.bm.chengshiyoutian.youlaiwang.Utils.ShowToast;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.activity.Login_ddActivity;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.bean.ZhuYe_CuXiaoBean;
import com.bm.chengshiyoutian.youlaiwang.youlai_dd.activity.utils.BitmapUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bm.chengshiyoutian.youlaiwang.activity.MainActivity.sp;

/**
 * Created by Administrator on 2017/8/18.
 */

public class ZhuYe_CuXiaoAdapter extends RecyclerView.Adapter<ZhuYe_CuXiaoAdapter.MyHolder> {
    private List<ZhuYe_CuXiaoBean.DataBeanX.DataBean> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;


    public ZhuYe_CuXiaoAdapter(List<ZhuYe_CuXiaoBean.DataBeanX.DataBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_zhuye__dd, parent, false);
        MyHolder myViewHolder = new MyHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position, holder.itemView);
                }
            }
        });

        ZhuYe_CuXiaoBean.DataBeanX.DataBean dataRecommendBean = mDatas.get(position);
        //获取图片地址
        String goods_cover = dataRecommendBean.getGoods_cover();

        Glide.with(mContext).load(goods_cover).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                Bitmap twoCircleBitmap = BitmapUtils.getTwoCircleBitmap(resource,holder.mImg);

                holder.mImg.setImageBitmap(twoCircleBitmap);
            }
        });

        holder.title.setText(dataRecommendBean.getGoods_name());
        holder.danwei.setText("¥" + dataRecommendBean.getGoods_price());
        holder.danwei2.setText(dataRecommendBean.getRatio());
        holder.storeId.setText(dataRecommendBean.getStore_name());
        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = sp.getString(MyRes.MY_TOKEN, "");

                if (string.equals("") || string.equals("kong")) {
                    mContext.startActivity(new Intent(mContext, Login_ddActivity.class));
                    ShowToast.showToast("请登陆");
                    return;
                }

                addCar(position);
            }
        });


    }

    @Override
    public int getItemCount() {

        if (mDatas.size() == 0) {
            return 0;
        }
        if (mDatas.size() > 4) {
            return 4;
        } else {
            return mDatas.size();
        }
    }

    public class MyHolder extends XRecyclerView.ViewHolder {

        ImageView mImg, img_add;
        TextView title, danwei, danwei2, storeId;

        public MyHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.mImg_zhuye);
            img_add = (ImageView) itemView.findViewById(R.id.img_add);


            title = (TextView) itemView.findViewById(R.id.mTxt_name);
            danwei = (TextView) itemView.findViewById(R.id.mTxt_danwei);
            danwei2 = (TextView) itemView.findViewById(R.id.mTxt_danwei2);

            storeId = (TextView) itemView.findViewById(R.id.storeId);
        }
    }

    /**
     * 点击事件的接口回调
     */

    public interface OnItemClickListener {
        void onItemClick(int pos, View view);
    }

    private ZhuYe_DongTaiAdapter.OnItemClickListener mListener;

    public void setOnItemClickListener(ZhuYe_DongTaiAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    //同步购物车
    private void addCar(int postion) {

        String string = sp.getString(MyRes.MY_TOKEN, "");

        if (string.equals("") || string.equals("kong")) {
            mContext.startActivity(new Intent(mContext, Login_ddActivity.class));
            ShowToast.showToast("请登陆");
            return;
        }

        Request<String> stringRequest = NoHttp.createStringRequest(MyRes.BASE_URL + "api/cart/store", RequestMethod.POST);
        stringRequest.addHeader("Authorization", sp.getString(MyRes.MY_TOKEN, ""));
        stringRequest.add("goodsId", mDatas.get(postion).getGoods_id() + "");//商品编号
        stringRequest.add("specId", mDatas.get(postion).getGoods_measure_id());//规格
        stringRequest.add("type", 1);
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
                        String msg = object.getString("msg");
                        if (mListener != null) {
                            mListener.onCarClicked();
                        }

                        ShowToast.showToast(object.getString("msg"));
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
}
