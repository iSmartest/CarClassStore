package com.lixin.carclassstore.activity;

import android.os.Bundle;

import com.lixin.carclassstore.R;

/**
 * Created by 小火
 * Create time on  2017/4/28
 * My mailbox is 1403241630@qq.com
 */

public class IntegralCenter extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_center);
        hideBack(false);
        setTitleText("积分中心");

    }
}
