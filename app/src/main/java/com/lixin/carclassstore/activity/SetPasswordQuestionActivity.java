package com.lixin.carclassstore.activity;

import android.os.Bundle;

import com.lixin.carclassstore.R;

/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 * 设置密保问题
 */

public class SetPasswordQuestionActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_password_question);
        setTitleText("设置密保问题");
        hideBack(false);
    }
}
