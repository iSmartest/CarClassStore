package com.lixin.qiaoqixinyuan.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;

import static com.baidu.location.h.j.P;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class MyScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;

    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }

    }
}
