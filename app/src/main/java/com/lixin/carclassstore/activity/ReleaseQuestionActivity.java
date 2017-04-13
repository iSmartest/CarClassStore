package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.ReplyBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/12
 * My mailbox is 1403241630@qq.com
 */

public class ReleaseQuestionActivity extends BaseActivity{
    private EditText mEditQuestion;
    private Button mButtonRelease;
    private String uid = "123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_question);
        mEditQuestion = (EditText) findViewById(R.id.a_release_edt_question);
        mButtonRelease = (Button) findViewById(R.id.btn_feedback_btn);
        mButtonRelease.setOnClickListener(this);
        hideBack(false);
        setTitleText("发布问题");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.btn_feedback_btn:
            submit();
            break;
        }
    }
    private void submit() {
        String comment = mEditQuestion.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            ToastUtils.showMessageShort(context, "请输入问题描述!");
            return;
        }
        getdata(comment);
    }
    private void getdata(String releaseQuestion) {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"getForumPubilckQuestion\",\"userTalk\":\"" + releaseQuestion + "\"" + ",\"uid\":\"" + uid + "\"}";
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
                        ToastUtils.showMessageShort(context, e.getMessage());
                        dialog1.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("111", "onResponse: " + response.toString());
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        ReplyBean rplyBean = gson.fromJson(response, ReplyBean.class);
                        if (rplyBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, rplyBean.getResultNote());
                            return;
                        }else {
                            ToastUtils.showMessageShort(context, "问题发布成功!");
                        }
                    }
                });
    }
}
