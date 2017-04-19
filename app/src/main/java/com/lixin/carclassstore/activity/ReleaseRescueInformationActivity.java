package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.view.ErrorDialog;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 */

public class ReleaseRescueInformationActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout linear_fault_category,linear_current_location;
    private TextView text_fault_category,text_current_location;
    private EditText a_feedback_edt_content;
    private Button a_feedback_btn;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_rescue_information);
        hideBack(false);
        setTitleText("发布救援信息");
        initView();
    }

    private void initView() {
        linear_fault_category = (LinearLayout) findViewById(R.id.linear_fault_category);
        linear_fault_category.setOnClickListener(this);
        linear_current_location = (LinearLayout) findViewById(R.id.linear_current_location);
        linear_current_location.setOnClickListener(this);
        text_fault_category = (TextView) findViewById(R.id.text_fault_category);
        text_fault_category.setOnClickListener(this);
        text_current_location = (TextView) findViewById(R.id.text_current_location);
        text_current_location.setOnClickListener(this);
        a_feedback_edt_content = (EditText) findViewById(R.id.a_feedback_edt_content);
        a_feedback_edt_content.setOnClickListener(this);
        a_feedback_btn = (Button) findViewById(R.id.a_feedback_btn);
        a_feedback_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_fault_category:
                startActivityForResult(new Intent(ReleaseRescueInformationActivity.this,FaultCategoryActivity.class),1000);
                break;
            case R.id.linear_current_location:
                startActivity(new Intent(ReleaseRescueInformationActivity.this,CurrentLocationActivity.class));
                break;
            case R.id.a_feedback_btn:
                content = a_feedback_edt_content.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    setTips();
                    return;
                }
                submit();
                break;
        }
    }

    private void submit() {


    }

    /**
     * 出错时提醒
     *
     * @param tips
     */
    private ErrorDialog mDialog;

    private void setTips() {
        if (mDialog == null) {
            mDialog = new ErrorDialog(ReleaseRescueInformationActivity.this, null);
        }
        mDialog.setTextView("请输入要反馈的内容");
        mDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == 1001)
        {
            String result_value = data.getStringExtra("result");
            if (TextUtils.isEmpty(result_value)){
                setTips();
            }
            text_fault_category.setText(result_value);
        }
    }
}
