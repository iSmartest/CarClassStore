package com.lixin.qiaoqixinyuan.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class MyListview extends ListView {
    public MyListview(Context context) {
        super(context);
    }

    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
