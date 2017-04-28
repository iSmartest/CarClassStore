package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.ReplyBean;
import com.lixin.carclassstore.bean.RoadRescueBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.ErrorDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 * 发布救援信息
 */

public class ReleaseRescueInformationActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout linear_fault_category,linear_current_location;
    private TextView text_fault_category,text_current_location;
    private EditText a_feedback_edt_content;
    private Button a_feedback_btn;
    private String content;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_rescue_information);
        uid = (String) SPUtils.get(ReleaseRescueInformationActivity.this,"uid","");
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
                startActivityForResult(new Intent(ReleaseRescueInformationActivity.this,MapApiDemoActivity.class),1002);
                break;
            case R.id.a_feedback_btn:
                content = a_feedback_edt_content.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    setTips();
                    return;
                }
                submit(content);
                break;
        }
    }

    private void submit(String accidentDec) {
        String accidentType = text_fault_category.getText().toString().trim();
        String accidentaddress = text_current_location.getText().toString().trim();
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"commintAccident\",\"uid\":\"" + uid +"\"," +
                "\"accidentType\":\"" + accidentType +"\",\"accidentDec\":\"" + accidentDec + "\",\"accidentaddress\":\"" + accidentaddress +"\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog1.dismiss();
                        ToastUtils.showMessageShort(context, e.getMessage());
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        Log.i("response", "onResponse: "+ response.toString());
                        ReplyBean roadRescueBean = gson.fromJson(response, ReplyBean.class);
                        if (roadRescueBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, roadRescueBean.getResultNote());
                            return;
                        }
                            ToastUtils.showMessageShort(ReleaseRescueInformationActivity.this,"救援信息发布成功！");
                    }
                });
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
        if(requestCode == 1000 && resultCode == 1001) {
            String result_value = data.getStringExtra("result");
            if (TextUtils.isEmpty(result_value)){
                setTips();
            }
            text_fault_category.setText(result_value);
        }else if (requestCode == 1002 && resultCode == 1003){
            String result = data.getStringExtra("result02");
            if (TextUtils.isEmpty(result)){
                setTips();
            }
            text_current_location.setText(result);
            Log.i("text_current_location", "onActivityResult: "+ text_current_location.getText().toString().trim());
        }
    }
}
