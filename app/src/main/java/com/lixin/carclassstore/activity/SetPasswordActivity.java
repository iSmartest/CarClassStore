package com.lixin.carclassstore.activity;

import android.os.Bundle;

import com.lixin.carclassstore.R;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 * 修改密码
 */

public class SetPasswordActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        hideBack(false);
        setTitleText("设置密保");
    }
}
