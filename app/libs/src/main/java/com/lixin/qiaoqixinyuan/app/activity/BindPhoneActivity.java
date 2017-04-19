package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
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

import okhttp3.Call;

import static com.baidu.location.h.j.S;
import static com.baidu.location.h.j.c;
import static com.baidu.location.h.j.f;
import static com.baidu.location.h.j.i;
import static com.lixin.qiaoqixinyuan.R.id.et_newphone;
import static com.lixin.qiaoqixinyuan.R.id.et_oldphone;
import static com.zhy.http.okhttp.OkHttpUtils.post;

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_phone;
    private EditText et_code;
    private TextView tv_getcode;
    private Button btn_confirm;
    private String code;
    private String uid;
    private String nickName;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindphone);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_getcode = (TextView) findViewById(R.id.tv_getcode);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
    }
    private void initData() {
        tv_title.setText("绑定手机号");
        Intent intent=getIntent();
        if (intent==null){
            return;
        }
        uid = intent.getStringExtra("uid");
        nickName = intent.getStringExtra("nickName");
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
                String phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast(context, "请输入手机号");
                    return;
                }
                code = TimerUtil.getNum();
                LogUtil.e("code", "---" + code);
                getPin(phone);
                TimerUtil timerUtil = new TimerUtil(tv_getcode);
                timerUtil.timers();
                break;
            case R.id.btn_confirm:
                submit();
                break;
        }
    }


    private void submit() {

        String pincode = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(pincode)) {
            ToastUtil.showToast(context, "请输入验证码");
            return;
        }
        if (!code.equals(pincode)){
            ToastUtil.showToast(context,"验证码不正确");
            return;
        }
        String phone = et_phone.getText().toString().trim();
        bindPhone(phone);
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
     * 绑定手机号
     * @param phone
     */
    private void bindPhone(final String phone) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "bindPhone");
        params.put("uid", uid);
        params.put("phone", phone);*/
        String json="{\"cmd\":\"bindPhone\",\"uid\":\"" + uid + "\",\"phone\":\"" + phone + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
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
                                ToastUtil.showToast(context,"绑定手机号成功");
                                SharedPreferencesUtil.putSharePre(context,"uid",uid);
                                SharedPreferencesUtil.putSharePre(context,"phoneNum",phone);
                                SharedPreferencesUtil.putSharePre(context,"nickName",nickName);
                                SharedPreferencesUtil.putSharePre(context,"isLogin",true);
                                MyApplication.openActivity(context,MainActivity.class);
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
