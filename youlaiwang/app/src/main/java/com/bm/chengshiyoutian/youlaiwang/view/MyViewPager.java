package com.bm.chengshiyoutian.youlaiwang.view;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Administrator on 2016/12/9.
 */
public class MyViewPager extends ViewPager {
    private OnItemClickListener mOnItemClickListener;
    private int mDownX;
    private int mDownY;
    private int mDownTime;
    public MyViewPager(Context context) {
        super(context);
    }
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        MyPageChangeListener myPageChangeListener = new MyPageChangeListener(listener);
        super.setOnPageChangeListener(myPageChangeListener);
    }
    @Override
    public void setAdapter(PagerAdapter adapter) {
        //  修正  adapter 中position
        MyAdapter myAdapter = new MyAdapter(adapter);
        setOnPageChangeListener(null); //手动增加一个监听
        super.setAdapter(myAdapter);
        setCurrentItem(1);
        //开启自动轮播
        startScroll();//自动轮播
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //手指触摸  按下 停止轮播  抬起继续轮播
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录按下时候手指的坐标
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                mDownTime = (int) System.currentTimeMillis();
                stopScroll();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                startScroll();//当手指抬起的时候开启自动轮播
                int upX = (int) ev.getX();
                int upY = (int) ev.getY();
                int upTime = (int) System.currentTimeMillis();
                int disX = Math.abs(upX- mDownX);
                int disY = Math.abs(upY - mDownY);
                //判断手指在屏幕上移动的距离和时间，如果时间在500毫秒之内，且移动的距离小于5就说明是单击事件
                if (disX<=5&&disY<=5&&upTime - mDownTime<=500){
                    //如果传进来的条目点击事件不为空，就执行条目点击事件
                    if (mOnItemClickListener !=null){
                        mOnItemClickListener.onItemClick(getCurrentItem());
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                //取消事件，当手指移出轮播图的区域后，开启轮播
                startScroll();
                break;
        }
        return super.onTouchEvent(ev);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int currentItem = getCurrentItem();
                    currentItem++;
                    setCurrentItem(currentItem);
                    handler.sendEmptyMessageDelayed(1, 2000);//4s发送消息
                    break;
                default:
                    break;
            }
        }
    };
    public void startScroll() {
        //开启轮播之前先关闭轮播，以免混乱
        stopScroll();
        //开启轮播
        handler.sendEmptyMessageDelayed(1, 2000);//2s发送消息
    }
    public void stopScroll()
    {
        //关闭轮播，删除所有的handler消息
        handler.removeMessages(1);
    }
    public class MyPageChangeListener implements OnPageChangeListener {
        private OnPageChangeListener listener;
        private int position;
        public MyPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (listener != null)
                listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
        @Override
        public void onPageSelected(int position) {
            this.position = position;
            if (listener != null)
                listener.onPageSelected(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {
            //状态改变的时候 调用    手指抬起的时候切换
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                //空闲切换
                // 页面切换   自动的切换到对应的界面    最后一个A----->第一个A
                if (position == getAdapter().getCount() - 1) {
                    //最后一个元素  是否平滑切换
                    setCurrentItem(1, false);
                } else if (position == 0) {
                    //是第一个元素{D] ----> 倒数第二个元素[D]
                    setCurrentItem(getAdapter().getCount() - 2, false);
                }
            }
            if (listener != null)
                listener.onPageScrollStateChanged(state);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    /**
     * 轮播图页面的点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public class MyAdapter extends PagerAdapter {
        private PagerAdapter adapter;
        public MyAdapter(PagerAdapter adapter) {
            this.adapter = adapter;  //[ABCD]
        }
        @Override
        public Object instantiateItem(ViewGroup Container, int position) {
            //  position 已经是 [DABCDA] 的索引了
            // 修正后的索引 应该是 [ABCD]的索引
            //修正position
            if (position == 0) { //新增的D
                position = adapter.getCount() - 1;// 最后一个元素
            } else if (position == getCount() - 1) {  //最后一个元素 A
                position = 0;
            } else {
                position -= 1; //计算新的索引
            }
            return adapter.instantiateItem(Container, position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            adapter.destroyItem(container, position, object);
        }
        @Override
        public int getCount() {
            return adapter.getCount() + 2;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return adapter.isViewFromObject(view, object);
        }
    }
}

