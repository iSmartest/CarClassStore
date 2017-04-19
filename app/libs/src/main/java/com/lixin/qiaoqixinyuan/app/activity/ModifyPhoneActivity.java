package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
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
import com.lixin.qiaoqixinyuan.app.bean.VerificationCodeBean;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.TimerUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static com.baidu.location.h.j.S;
import static com.baidu.location.h.j.c;
import static com.zhy.http.okhttp.OkHttpUtils.post;

public class ModifyPhoneActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_oldphone;
    private EditText et_code;
    private TextView tv_getcode;
    private EditText et_newphone;
    private Button btn_confirm;
    private String code;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyphone);
        token= JPushInterface.getRegistrationID(context);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_oldphone = (EditText) findViewById(R.id.et_oldphone);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_getcode = (TextView) findViewById(R.id.tv_getcode);
        et_newphone = (EditText) findViewById(R.id.et_newphone);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);

    }

    private void initData() {
        tv_title.setText("修改手机号");
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);
        tv_getcode.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_getcode:
                String oldphone = et_oldphone.getText().toString().trim();
                if (TextUtils.isEmpty(oldphone)) {
                    ToastUtil.showToast(context, "请输入原手机号");
                    return;
                }
                code = TimerUtil.getNum();
                LogUtil.e("code", "---" + code);
                getPin(oldphone);
                TimerUtil timerUtil = new TimerUtil(tv_getcode);
                timerUtil.timers();
                break;
            case R.id.btn_confirm:
                submit();
                break;
        }
    }


    private void submit() {

        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showToast(context, "请输入验证码");
            return;
        }

        String newphone = et_newphone.getText().toString().trim();
        if (TextUtils.isEmpty(newphone)) {
            ToastUtil.showToast(context, "请输入新手机号");
            return;
        }
        if (newphone.equals(et_oldphone.getText().toString().trim())) {
            ToastUtil.showToast(context, "输入的新手机号与原手机号一致，无需更改");
            return;
        }
        String uid = MyApplication.getuId();
        String oldphone = et_oldphone.getText().toString().trim();
        changeBindPhone(uid,oldphone,newphone);
    }
    /**
     * 获取短信验证码
     * @param phone
     */
    private void getPin(String phone) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("tpl_value", URLEncoder.encode("#code#=" + code, "utf-8"));
            params.put("dtype", "json");
            params.put("tpl_id", "22341");
            params.put("key", "759feaf41f44398db3122d6aacc83270");
            params.put("mobile", phone);
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
                            Gson gson = new Gson();
                            VerificationCodeBean Vbean = gson.fromJson(response, VerificationCodeBean.class);
                            if ("操作成功".equals(Vbean.reason)) {
                                ToastUtil.showToast(context,"验证码已发送");
                                TimerUtil timerUtil = new TimerUtil(tv_getcode);
                                timerUtil.timers();
                                dialog.dismiss();
                            } else {
                                ToastUtil.showToast(context, "验证码发送失败");
                                dialog.dismiss();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 修改手机号
     * @param phone
     * @param oldPhone
     * @param phone
     */
    private void changeBindPhone(String uid, String oldPhone, final String phone) {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "changeBindPhone");
        params.put("uid", uid);
        params.put("oldPhone", oldPhone);
        params.put("phone", phone);
        params.put("token", token);*/
        String json="{\"cmd\":\"changeBindPhone\",\"uid\":\"" + uid + "\",\"oldPhone\":\"" + oldPhone + "\"," +
                "\"phone\":\"" + phone + "\",\"token\":\"" + token + "\"}";
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
                        try {
                            JSONObject object=new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("0".equals(result)){
                                ToastUtil.showToast(context,"手机号修改成功");
                                SharedPreferencesUtil.putSharePre(context,"phoneNum",phone);
                                finish();
                                dialog.dismiss();
                            }else {
                                ToastUtil.showToast(context, resultNote);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
