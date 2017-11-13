package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.bean.RepositoryBillBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by youj on 2016/1/22.
 */
public class MyUtils {


    /**
     * 限制只能输入小数点后两位
     *
     * @param editText
     */
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });


        editText.setKeyListener(new NumberKeyListener() {
            // 0无键盘 1英文键盘 2模拟键盘 3数字键盘
            @Override
            public int getInputType() {

                return 3;
            }
            // 返回允许输入的字符
            @Override
            protected char[] getAcceptedChars() {

                char[] c = {'0','1','2','3','4','5','6','7','8','9','.'};
                return c;
            }
        });


}


    /**
     * 切换上下箭头
     *
     * @param type type=1 pop消失 箭头向下  type=2 pop弹出 箭头向上
     * @return
     */
    public static Drawable getDrawa(Context context, int type) {
        Drawable drawable = null;
        if (type == 2) {
            drawable = context.getResources().getDrawable(R.mipmap.jiantou2);
        } else if (type == 1) {
            drawable = context.getResources().getDrawable(R.mipmap.jiantou1);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    /**
     * 切换上下箭头
     *
     * @param type type=1 pop消失 箭头向下  type=2 pop弹出 箭头向上
     * @return
     */
    public static Drawable getDrawaPastTime(Context context, int type) {
        Drawable drawable = null;
        if (type == 2) {
            drawable = context.getResources().getDrawable(R.mipmap.jiantoushang_past_time);
        } else if (type == 1) {
            drawable = context.getResources().getDrawable(R.mipmap.jiantouxia_past_time);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    public static String getDate(String source, String patternOld, String patternNew) {
        String newDateStr = null;
        try {
            newDateStr = new SimpleDateFormat(patternNew).format(new SimpleDateFormat(patternOld).parse(source))
                    .toString();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newDateStr;
    }

    /**
     * @param bitmapList 将bitmap转成二进制流
     * @return
     */
    public static String bitmapStringData(List<Bitmap> bitmapList) {
        if (bitmapList != null && bitmapList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bitmapList.size(); i++) {
                String stringBitmap = bitmapToBase64(bitmapList.get(i));
                sb.append(stringBitmap);
                if (i != bitmapList.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        return "";
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encode(bitmapBytes);
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return result;
    }

    /**
     * 获取食品分类明细
     */
    public static ArrayList<RepositoryBillBean> getRepositoryData() {
        ArrayList<RepositoryBillBean> repositoryBillBeans = new ArrayList<>();
        repositoryBillBeans.add(new RepositoryBillBean("粮食加工品", "0", "公斤"));
        repositoryBillBeans.add(new RepositoryBillBean("食用油、油脂及其制品", "0", "公斤"));
        repositoryBillBeans.add(new RepositoryBillBean("调味品", "0", "克"));
        repositoryBillBeans.add(new RepositoryBillBean("肉制品", "0", "克"));
        repositoryBillBeans.add(new RepositoryBillBean("乳制品", "0", "克或毫升"));
        repositoryBillBeans.add(new RepositoryBillBean("饮料", "0", "毫升"));
        repositoryBillBeans.add(new RepositoryBillBean("方便食品", "0", "袋或桶、克"));
        repositoryBillBeans.add(new RepositoryBillBean("饼干", "0", "袋或盒"));
        repositoryBillBeans.add(new RepositoryBillBean("罐头", "0", "瓶"));
        repositoryBillBeans.add(new RepositoryBillBean("冷冻饮品", "0", "个"));
        repositoryBillBeans.add(new RepositoryBillBean("速冻食品", "0", "个"));
        repositoryBillBeans.add(new RepositoryBillBean("薯类和膨化食品", "0", "克"));
        repositoryBillBeans.add(new RepositoryBillBean("糖果制品", "0", "个"));
        repositoryBillBeans.add(new RepositoryBillBean("茶叶及相关制品", "0", "克"));
        repositoryBillBeans.add(new RepositoryBillBean("酒类", "0", "瓶或毫升"));
        repositoryBillBeans.add(new RepositoryBillBean("蔬菜制品", "0", "斤"));
        repositoryBillBeans.add(new RepositoryBillBean("蜜饯及水果制品", "0", "公斤"));
        repositoryBillBeans.add(new RepositoryBillBean("炒货食品及坚果制品", "0", "公斤"));
        repositoryBillBeans.add(new RepositoryBillBean("蛋制品", "0", "个"));
        repositoryBillBeans.add(new RepositoryBillBean("可可及焙炒咖啡制品", "0", "克"));
        repositoryBillBeans.add(new RepositoryBillBean("食糖", "0", "斤"));
        repositoryBillBeans.add(new RepositoryBillBean("水产制品", "0", "公斤"));
        repositoryBillBeans.add(new RepositoryBillBean("淀粉及淀粉制品", "0", "袋或斤"));
        repositoryBillBeans.add(new RepositoryBillBean("糕点", "0", "袋或盒"));
        repositoryBillBeans.add(new RepositoryBillBean("豆制品", "0", "斤"));
        repositoryBillBeans.add(new RepositoryBillBean("蜂产品", "0", "瓶"));
        repositoryBillBeans.add(new RepositoryBillBean("特殊膳食食品", "0", "克"));
        repositoryBillBeans.add(new RepositoryBillBean("其它食品", "0", "个"));
        return repositoryBillBeans;
    }

    public static ArrayList<RepositoryBillBean> selectHasNum(ArrayList<RepositoryBillBean> billBeans) {
        ArrayList<RepositoryBillBean> repositoryBillBeans = new ArrayList<>();
        if (billBeans != null) {
            for (RepositoryBillBean repositoryBillBean : billBeans) {
                if (TextUtils.isEmpty(repositoryBillBean.productName)) {
                    continue;
                }
                if (TextUtils.isEmpty(repositoryBillBean.productNnit)) {
                    if (!"其他食品".equals(repositoryBillBean.productName)) {
                        continue;
                    }
                }
                if (!TextUtils.isEmpty(repositoryBillBean.productNum) && !"0".equals(repositoryBillBean.productNum)) {
                    repositoryBillBeans.add(repositoryBillBean);
                }
            }
        }
        return repositoryBillBeans;
    }


    public static ArrayList<RepositoryBillBean> selectHasNums(List<RepositoryBillBean> billBeans) {
        ArrayList<RepositoryBillBean> repositoryBillBeans = new ArrayList<>();
        if (billBeans != null) {
            for (RepositoryBillBean repositoryBillBean : billBeans) {
                if (TextUtils.isEmpty(repositoryBillBean.productName)) {
                    continue;
                }
                if (TextUtils.isEmpty(repositoryBillBean.productNnit)) {
//                    if (!"其他食品".equals(repositoryBillBean.productName)) {
                        continue;
//                    }
                }
                if (!TextUtils.isEmpty(repositoryBillBean.productNum) && !"0".equals(repositoryBillBean.productNum)) {
                    repositoryBillBeans.add(repositoryBillBean);
                }
            }
        }
        return repositoryBillBeans;
    }


    /**
     * @param billBeans       选择的产品明细
     * @param billBeansOfTime 选择的包含保质期限或生产批号的产品明细
     */
    public static ArrayList<RepositoryBillBean> getBillBeanOfTime(ArrayList<RepositoryBillBean> billBeans, ArrayList<RepositoryBillBean> billBeansOfTime) {
        if (billBeans != null && billBeansOfTime != null) {
            for (RepositoryBillBean repositoryBillBean : billBeans) {
                for (RepositoryBillBean repositoryBillBeanOfTiem : billBeansOfTime) {
                    if (!TextUtils.isEmpty(repositoryBillBean.productName) && !TextUtils.isEmpty(repositoryBillBeanOfTiem.productName)) {
                        if (repositoryBillBean.productName.equals(repositoryBillBeanOfTiem.productName)) {
                            repositoryBillBean.productTime = repositoryBillBeanOfTiem.productTime;
                            repositoryBillBean.productPiHao = repositoryBillBeanOfTiem.productPiHao;
                        }
                    }
                }
            }
        }
        return billBeans;
    }

    /**
     * @param context
     * @param jsonDataName
     * @return 获取保存的产品明细
     */
    public static ArrayList<RepositoryBillBean> getSaveData(Context context, String jsonDataName) {
        String productMingXi = GlobalData.getData(context, jsonDataName, "");
        if (!TextUtils.isEmpty(productMingXi)) {
            try {
                JSONArray jsonArray = new JSONArray(productMingXi);
                if (jsonArray != null && jsonArray.length() > 0) {
                    ArrayList<RepositoryBillBean> repositoryBillBeans = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        repositoryBillBeans.add(new RepositoryBillBean(jsonObject.optString("productName"), jsonObject.optString("productNum"),
                                jsonObject.optString("productNnit"), jsonObject.optString("productTime"), jsonObject.optString("productPiHao")));
                    }
                    return repositoryBillBeans;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将dp类型的尺寸转换成px类型的尺寸
     *
     * @param size
     * @param context
     * @return
     */
    public static int DPtoPX(int size, Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return (int) ((float) size * metrics.density + 0.5);
    }

    /**
     * 判断字符串是否为空
     *
     * @param message
     * @return
     */
    public static boolean isEmpty(String message) {
        return TextUtils.isEmpty(message) || "null".equals(message);
    }

    /**
     * 设置字体大小
     *
     * @param textViews
     */
    public static void setTextViewSize14(TextView... textViews) {
        if (textViews != null && textViews.length > 0) {
            int length = textViews.length;
            for (int i = 0; i < length; i++) {
                textViews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
        }
    }

    /**
     * 设置字体大小
     *
     * @param textViews
     */
    public static void setTextViewSize16(TextView... textViews) {
        if (textViews != null && textViews.length > 0) {
            int length = textViews.length;
            for (int i = 0; i < length; i++) {
                textViews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
        }
    }

    /**
     * 设置字体大小
     *
     * @param textViews
     */
    public static void setTextViewSize18(TextView... textViews) {
        if (textViews != null && textViews.length > 0) {
            int length = textViews.length;
            for (int i = 0; i < length; i++) {
                textViews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        }
    }

    /**
     * 设置字体大小
     *
     * @param textViews
     */
    public static void setTextViewSize20(TextView... textViews) {
        if (textViews != null && textViews.length > 0) {
            int length = textViews.length;
            for (int i = 0; i < length; i++) {
                textViews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
        }
    }

    /**
     * 设置字体大小
     *
     * @param textViews
     */
    public static void setTextViewSize22(TextView... textViews) {
        if (textViews != null && textViews.length > 0) {
            int length = textViews.length;
            for (int i = 0; i < length; i++) {
                textViews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            }
        }
    }

    /**
     * 提交数据时防止按钮出现连点的情况
     *
     * @param flag
     * @param textViews
     */
    public static void setTextViewClick(boolean flag, TextView... textViews) {
        if (textViews != null && textViews.length > 0) {
            int length = textViews.length;
            for (int i = 0; i < length; i++) {
                textViews[i].setClickable(flag);
            }
        }
    }
}
