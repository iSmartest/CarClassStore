package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
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
import com.lixin.qiaoqixinyuan.app.util.Md5Util;
import com.lixin.qiaoqixinyuan.app.util.PhoneAndPwdUtil;
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

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_userphone;
    private EditText et_password;
    private EditText et_confirmpassword;
    private EditText et_passPin;
    private TextView tv_verification_code;
    private Button btn_submit;
    private String code;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView();
        initData();
        initListener();
    }
    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_userphone = (EditText) findViewById(R.id.et_userphone);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirmpassword = (EditText) findViewById(R.id.et_confirmpassword);
        et_passPin = (EditText) findViewById(R.id.et_passPin);
        tv_verification_code = (TextView) findViewById(R.id.tv_verification_code);
        btn_submit = (Button) findViewById(R.id.btn_submit);

    }
    private void initData() {
        tv_title.setText("找回密码");
    }
    private void initListener() {
        iv_turnback.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        tv_verification_code.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_verification_code:
                //验证电话号码不能为空
                String userphone = et_userphone.getText().toString().trim();
                if (TextUtils.isEmpty(userphone)) {
                    ToastUtil.showToast(context, "电话号码不能为空");
                    return;
                }
                //验证电话号码是否正确
                boolean isphonenum = PhoneAndPwdUtil.isPhone(userphone);
                if (!isphonenum) {
                    ToastUtil.showToast(context, "电话号码不正确，请核对后重新输入");
                    return;
                }
                code = TimerUtil.getNum();
                LogUtil.e("code", "---" + code);
                getPin(userphone);
                TimerUtil timerUtil = new TimerUtil(tv_verification_code);
                timerUtil.timers();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }
    private void submit() {
        //验证验证码不能为空
        String passPin = et_passPin.getText().toString().trim();
        if (TextUtils.isEmpty(passPin)) {
            ToastUtil.showToast(context, "验证码不能为空");
            return;
        }
        //验证验证码是否正确
        if (!passPin.equals(code)) {
            ToastUtil.showToast(context, "验证码不正确");
            return;
        }
        //验证密码不能为空
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(context, "密码不能为空");
            return;
        }
        //验证确认密码不能为空
        String confirmpwd = et_confirmpassword.getText().toString().trim();
        if (TextUtils.isEmpty(confirmpwd)) {
            ToastUtil.showToast(context, "确认密码不能为空");
            return;
        }
        //验证密码和确认密码是否相同
        if (!password.equals(confirmpwd)) {
            ToastUtil.showToast(context, "两次输入密码不一致");
            return;
        }
        //验证密码格式是否正确
        boolean ispassword = PhoneAndPwdUtil.isPwd(password);
        if (!ispassword) {
            ToastUtil.showToast(context, "密码格式不正确，请核对后重新输入");
            return;
        }
        //验证电话号码不能为空
        String userphone = et_userphone.getText().toString().trim();
        try {
            findPassword(userphone, Md5Util.md5Encode(password));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            params.put("tpl_id", "28078");
            params.put("key", "c6bc033aec60a1073b6950471592618f");
            params.put("mobile", phone);
            Log.e("tiramisu",params.toString());
            dialog.show();
            OkHttpUtils.post().url(getString(R.string.juhe_url)).params(params).build()
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
                                ToastUtil.showToast(context, "验证码已发送");
                                TimerUtil timerUtil = new TimerUtil(tv_verification_code);
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
     * 忘记密码
     * @param phone
     * @param password
     */
    private void findPassword(final String phone, String password) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "findPassword");
        params.put("phone", phone);
        params.put("password", password);*/
        String json="{\"cmd\":\"findPassword\",\"phone\":\"" + phone + "\"," +
                "\"password\":\"" + password +"\",\"token\":\"" + token + "\"}";
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
                                ToastUtil.showToast(context,"找回密码成功");
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
