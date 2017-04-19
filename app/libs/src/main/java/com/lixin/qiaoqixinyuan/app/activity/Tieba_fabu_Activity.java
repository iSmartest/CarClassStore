package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;

public class Tieba_fabu_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private EditText et_tieba_biaoti;
    private EditText et_tieba_shuru;
    private LinearLayout layout_tieba_add_image;
    private TextView tv_tieba_fabu;
    private LinearLayout activity_tieba_fabu_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tieba_fabu_);
        initView();
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.iv_turnback:
            finish();
            break;
    }
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        et_tieba_biaoti = (EditText) findViewById(R.id.et_tieba_biaoti);
        et_tieba_shuru = (EditText) findViewById(R.id.et_tieba_shuru);
        layout_tieba_add_image = (LinearLayout) findViewById(R.id.layout_tieba_add_image);
        tv_tieba_fabu = (TextView) findViewById(R.id.tv_tieba_fabu);
        tv_tieba_fabu.setOnClickListener(this);
        activity_tieba_fabu_ = (LinearLayout) findViewById(R.id.activity_tieba_fabu_);
    }

}
