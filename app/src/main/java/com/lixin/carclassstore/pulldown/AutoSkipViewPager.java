package com.lixin.carclassstore.pulldown;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 自动进行向后跳转的循环ViewPager
 */
public class AutoSkipViewPager extends ViewPager {
    private Handler mHandler;
    private OnPageChangeListener mListener;
    private boolean mCanSkip;

    private long mLastTime;
    /**
     * 自动跳转的时间间隔
     */
    private long mPeriod;
    /**
     * 表示是否已经开始了自动跳转
     */
    private boolean mIsStartSkip;

    public static final int SKIP = 2;

    public AutoSkipViewPager(Context context) {
        super(context);
        init();
    }

    public AutoSkipViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (mIsStartSkip) {
                    if (msg.what == SKIP) {
                        if (mCanSkip && System.currentTimeMillis() - mLastTime > mPeriod) {
                            setCurrentItem(getCurrentItem() + 1);
                        }
                        mCanSkip = true;
                        mHandler.sendEmptyMessageDelayed(SKIP, mPeriod);
                    }
                }
            }
        };

        super.setOnPageChangeListener(new PageChangeListener());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (getAdapter() != null && getAdapter().getCount() > 3)
            beginSkip(1000 * 3);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        cancelSkip();
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    class PageChangeListener implements OnPageChangeListener {
        private boolean flag = true;
        private boolean first = true;

        /**
         * 滚动状态变化。<br>
         * <li>ViewPager.SCROLL_STATE_DRAGGING(1)：当用户开始拖动时(无论是否发生位移)<br> <li>
         * ViewPager.SCROLL_STATE_SETTLING(2)：当ViewPager自动滚动到一个新的页面<br> <li>
         * ViewPager.SCROLL_STATE_IDLE(0)：当ViewPager完全停止/闲置。
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (getAdapter() == null) {
                return;
            }

            if (mListener != null) {
                mListener.onPageScrollStateChanged(state);
            }

            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (getCurrentItem() >= getAdapter().getCount() - 1) {
                    setCurrentItem(1, false);
                }
                if (getCurrentItem() < 1) {
                    setCurrentItem(getAdapter().getCount() - 2, false);
                }
            }
        }

        /**
         * 前页面滚动时，这个方法会被调用，无论是编程启动平滑滚动还是用户触摸滚动。
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (getAdapter() == null) {
                return;
            }
            int length = getAdapter().getCount() - 2;
            if (position == 0) {
                position = length - 1;
            } else {
                if (position > length) {
                    position = 0;
                } else {
                    position -= 1;
                }
            }

            if (mListener != null) {
                mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        /**
         * 当滑动到一个新的页面时。动画不一定是完整的。
         */
        @Override
        public void onPageSelected(int position) {
            if (getAdapter() == null) {
                return;
            }

            int length = getAdapter().getCount() - 2;

            flag = true;
            if (position == 0) {
                first = true;
                position = length - 1;
            } else {
                if (position > length) {
                    first = true;
                    position = 0;
                } else {
                    position -= 1;
                    if (first) {
                        first = false;
                        flag = false;
                    }
                }
            }

            if (mListener != null && flag) {
                mCanSkip = false;
                mListener.onPageSelected(position);
                mLastTime = System.currentTimeMillis();
            }
        }
    }

    public void beginSkip(long period) {
        mPeriod = period;
        if (!mIsStartSkip) {
            mHandler.sendEmptyMessageDelayed(SKIP, mPeriod);
        }

        mIsStartSkip = true;
    }

    public void cancelSkip() {
        mIsStartSkip = false;
        mHandler.removeMessages(SKIP);
    }
}
