package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lixin.carclassstore.R;


/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */

public class FaultCategoryActivity extends BaseActivity {
    private TextView text_car_no_oil,text_car_tire_bad,text_other_car_question;
    private CheckBox ck_chose1,ck_chose2,ck_chose3;
    private Button a_sure_chose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_category);
        hideBack(false);
        setTitleText("故障类别");
        initView();
    }
    private void initView() {
        text_car_no_oil = (TextView) findViewById(R.id.text_car_no_oil);
        text_car_tire_bad = (TextView) findViewById(R.id.text_car_tire_bad);
        text_other_car_question = (TextView) findViewById(R.id.text_other_car_question);
        ck_chose1 = (CheckBox) findViewById(R.id.ck_chose1);
        ck_chose2 = (CheckBox) findViewById(R.id.ck_chose2);
        ck_chose3 = (CheckBox) findViewById(R.id.ck_chose3);
        a_sure_chose = (Button) findViewById(R.id.a_sure_chose);
        a_sure_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumit();
            }
        });
    }
    private void sumit() {
        String r = "";
        if(ck_chose1.isChecked())   r = r + text_car_no_oil.getText().toString().trim() + " ";
        if(ck_chose2.isChecked())    r = r + text_car_tire_bad.getText().toString().trim() +" ";
        if(ck_chose3.isChecked())    r = r + text_other_car_question.getText().toString().trim() + " ";
        Intent intent = new Intent(FaultCategoryActivity.this,ReleaseRescueInformationActivity.class);
        intent.putExtra("result", r);
        setResult(1001, intent);
        finish();
    }
}
