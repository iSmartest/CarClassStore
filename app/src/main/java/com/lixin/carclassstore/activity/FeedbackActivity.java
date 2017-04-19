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
import com.lixin.carclassstore.view.ErrorDialog;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/17
 * My mailbox is 1403241630@qq.com
 */

public class FeedbackActivity extends BaseActivity{
    private EditText feedback;
    private Button btnFeedback;
    private String uid = "123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        hideBack(false);
        setTitleText("意见反馈");
        initView();
    }

    private void initView() {
        feedback = (EditText) findViewById(R.id.edi_feedback_edt_content);
        btnFeedback = (Button) findViewById(R.id.btn_feedback);
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = feedback.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    setTips();
                    return;
                }
                getdata(content);
            }
        });
    }

    private void getdata(String content) {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"feedbackOption\",\"uid\":\"" + uid +"\",\"content\":\"" + content +"\"}";
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
                        ReplyBean replyBean = gson.fromJson(response, ReplyBean.class);
                        if (replyBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, replyBean.getResultNote());
                            return;
                        }
                        ToastUtils.showMessageShort(context, "意见反馈成功");
                    }
                });
    }

    private ErrorDialog mDialog;

    private void setTips() {
        if (mDialog == null) {
            mDialog = new ErrorDialog(FeedbackActivity.this, null);
        }
        mDialog.setTextView("请输入要反馈的内容");
        mDialog.show();
    }
}
