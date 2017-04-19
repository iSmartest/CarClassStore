package com.lixin.qiaoqixinyuan.app.activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.UserLoginBean;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class SuggestionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_content;
    private Button btn_submit;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView();
        initData();
        initLiserner();
    }
    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }
    private void initData() {
        tv_title.setText("投诉意见");
    }
    private void initLiserner() {
        iv_turnback.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }
    private void submit() {
        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(context,"请输入内容");
            return;
        }
        String registrationID = JPushInterface.getRegistrationID(context);
        sendComplainOption(MyApplication.getuId(),content);
    }
    /**
     * @param uid
     * @param optionMessage
     */
    private void sendComplainOption(String uid, String optionMessage) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "sendComplainOption");
        params.put("uid", uid);
        params.put("optionMessage", optionMessage);
        params.put("token", token);*/
        String json="{\"cmd\":\"sendComplainOption\",\"uid\":\"" + uid + "\",\"optionMessage\":\"" + optionMessage + "\",\"token\":\"" + token + "\"}";
        params.put("json",json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        try {
                            JSONObject object=new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)){
                                ToastUtil.showToast(context,resultNote);
                            }else {
                                ToastUtil.showToast(context,resultNote);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
