package com.lixin.carclassstore.activity;

import android.os.Bundle;

import com.lixin.carclassstore.R;


/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */

public class CurrentLocationActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        setTitleText("当前位置");
    }
}
