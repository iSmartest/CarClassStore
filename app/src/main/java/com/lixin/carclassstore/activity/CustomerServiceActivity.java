package com.lixin.carclassstore.activity;

import android.os.Bundle;

import com.lixin.carclassstore.R;


/**
 * Created by 小火
 * Create time on  2017/3/25
 * My mailbox is 1403241630@qq.com
 * 客服
 */

public class CustomerServiceActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        setTitleText("客服");
        hideBack(false);
    }
}
