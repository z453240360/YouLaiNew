package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.pic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.app.MyApplication;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.AccountDetailActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.DemandingCertificatesThreeActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.EditInfoActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.OriginalBillActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.PastTimeProductActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.RegisterNextActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.RepositoryBillSellActivity;
import com.bm.chengshiyoutian.youlaiwang.oldall.oldview.activity.RepositoryBillShipmentActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;



public class ImageGridActivity extends Activity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";

    // ArrayList<Entity> dataList;//鐢ㄦ潵瑁呰浇鏁版嵁婧愮殑鍒楄〃
    List<ImageItem> dataList;
    GridView gridView;
    ImageGridAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣�
    AlbumHelper helper;
    Button bt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(ImageGridActivity.this, "最多选择" + MyApplication.getInstance().imageNum + "张图片", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_grid);

        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        dataList = (List<ImageItem>) getIntent().getSerializableExtra(
                EXTRA_IMAGE_LIST);

        initView();
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext(); ) {
                    list.add(it.next());
                }

                if (Bimp.act_bool) {
                    if (MyApplication.getInstance().selectPhoto == 0) {
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drr.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drr.add(list.get(i));
                            }
                        }
                        Intent intent = new Intent(ImageGridActivity.this,
                                DemandingCertificatesThreeActivity.class);
                        intent.putExtra("selectPhoto", "selectPhoto");
                        startActivity(intent);
                    } else if (MyApplication.getInstance().selectPhoto == 1) {
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drr.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drr.add(list.get(i));
                            }
                        }
                        Intent intent = new Intent(ImageGridActivity.this,
                                AccountDetailActivity.class);
                        intent.putExtra("selectPhoto", "selectPhoto");
                        startActivity(intent);
                    } else if (MyApplication.getInstance().selectPhoto == 2) {
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drr.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drr.add(list.get(i));
                            }
                        }

                        Intent intent = new Intent(ImageGridActivity.this,
                                RegisterNextActivity.class);
                        intent.putExtra("selectPhoto", "selectPhoto");
                        startActivity(intent);
                    } else if (MyApplication.getInstance().selectPhoto == 3) {
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drr.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drr.add(list.get(i));
                            }
                        }
                        Intent intent = new Intent(ImageGridActivity.this,
                                OriginalBillActivity.class);
                        intent.putExtra("selectPhoto", "selectPhoto");
                        startActivity(intent);
                    } else if (MyApplication.getInstance().selectPhoto == 4) {
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drr.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drr.add(list.get(i));
                            }
                        }
                        Intent intent = new Intent(ImageGridActivity.this,
                                PastTimeProductActivity.class);
                        intent.putExtra("selectPhoto", "selectPhoto");
                        startActivity(intent);
                    } else if (MyApplication.getInstance().selectPhoto == 5) {
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drr.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drr.add(list.get(i));
                            }
                        }
                        Intent intent = new Intent(ImageGridActivity.this,
                                RepositoryBillSellActivity.class);
                        intent.putExtra("selectPhoto", "selectPhoto");
                        startActivity(intent);
                    }else if (MyApplication.getInstance().selectPhoto == 6) {
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drr.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drr.add(list.get(i));
                            }
                        }
                        Intent intent = new Intent(ImageGridActivity.this,
                                EditInfoActivity.class);
                        intent.putExtra("selectPhoto", "selectPhoto");
                        startActivity(intent);
                    }else if (MyApplication.getInstance().selectPhoto == 7) {

//                        Intent intent = new Intent(ImageGridActivity.this,
//                                RegisterThirdActivity.class);
//                        intent.putExtra("selectPhoto", "selectPhoto");
//                        startActivity(intent);
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drrThird.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drrThird.add(list.get(i));
                            }
                        }
                    }else if (MyApplication.getInstance().selectPhoto == 8) {
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drrSupplier.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drrSupplier.add(list.get(i));
                            }
                        }

                    }else if(MyApplication.getInstance().selectPhoto==9){
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drr.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drr.add(list.get(i));
                            }
                        }
                    } else if (MyApplication.getInstance().selectPhoto == 10) {
                        for (int i = 0; i < list.size(); i++) {
                            if (Bimp.drr.size() < MyApplication.getInstance().imageNum) {
                                Bimp.drr.add(list.get(i));
                            }
                        }
                        Intent intent = new Intent(ImageGridActivity.this,
                                RepositoryBillShipmentActivity.class);
                        intent.putExtra("selectPhoto", "selectPhoto");
                        startActivity(intent);
                    }
                    //Bimp.act_bool = false;
                }
//                for (int i = 0; i < list.size(); i++) {
//                    if (Bimp.drr.size() < App.getInstance().imageNum) {
//                        Bimp.drr.add(list.get(i));
//                    }
//                }

                setResult(200);
                finish();
            }
        });
    }

    /**
     * 鍒濆鍖杤iew瑙嗗浘
     */
    private void initView() {
        ImageView iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
                mHandler);
        gridView.setAdapter(adapter);
        adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /**
                 * 鏍规嵁position鍙傛暟锛屽彲浠ヨ幏寰楄窡GridView鐨勫瓙View鐩哥粦瀹氱殑瀹炰綋绫伙紝鐒跺悗鏍规嵁瀹冪殑isSelected鐘舵
                 * �锛� 鏉ュ垽鏂槸鍚︽樉绀洪�涓晥鏋溿� 鑷充簬閫変腑鏁堟灉鐨勮鍒欙紝涓嬮潰閫傞厤鍣ㄧ殑浠ｇ爜涓細鏈夎鏄�
                 */
                // if(dataList.get(position).isSelected()){
                // dataList.get(position).setSelected(false);
                // }else{
                // dataList.get(position).setSelected(true);
                // }
                /**
                 * 閫氱煡閫傞厤鍣紝缁戝畾鐨勬暟鎹彂鐢熶簡鏀瑰彉锛屽簲褰撳埛鏂拌鍥�
                 */
                adapter.notifyDataSetChanged();
            }
        });
    }
}
