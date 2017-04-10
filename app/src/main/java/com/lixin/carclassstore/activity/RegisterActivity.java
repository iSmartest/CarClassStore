package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.UserRegisterBean;
import com.lixin.carclassstore.bean.VerificationCodeBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.Md5Util;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SharedPreferencesUtil;
import com.lixin.carclassstore.utils.StringUtils;
import com.lixin.carclassstore.utils.TimerUtil;
import com.lixin.carclassstore.utils.ToastUtils;
import com.sina.weibo.sdk.utils.LogUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.umeng.socialize.Config.dialog;


/**
 * Created by 小火
 * Create time on  2017/3/22
 * My mailbox is 1403241630@qq.com
 */

public class RegisterActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "ForgetPwdActivity";
    private EditText edi_phone_number,edi_verification_code,edi_password,edi_password_again;
    private TextView activity_base_textview_back;
    private Button btn_fast_register,btn_verification_code;
    private Context mContext;
    private String code;
    private String logpwd;
    private String addressId;//没有
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_register);
        mContext = this;
        initView();
    }

    private void initView() {
        edi_phone_number = (EditText) findViewById(R.id.edi_phone_number);
        edi_verification_code = (EditText) findViewById(R.id.edi_verification_code);
        edi_password = (EditText) findViewById(R.id.edi_password);
        edi_password_again = (EditText) findViewById(R.id.edi_password_again);
        btn_verification_code = (Button) findViewById(R.id.btn_verification_code);
        activity_base_textview_back = (TextView) findViewById(R.id.activity_base_textview_back);
        btn_fast_register = (Button) findViewById(R.id.btn_fast_register);

        edi_phone_number.setOnClickListener(this);
        edi_verification_code.setOnClickListener(this);
        edi_password.setOnClickListener(this);
        edi_password_again.setOnClickListener(this);
        btn_verification_code.setOnClickListener(this);
        btn_fast_register.setOnClickListener(this);
        activity_base_textview_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_base_textview_back:
            finish();
            break;
            case R.id.btn_verification_code:
                String user_phone_number = edi_phone_number.getText().toString().trim();
                //验证手机号是否正确
                if (!StringUtils.isMatchesPhone(user_phone_number)){
                    ToastUtils.showMessageShort(mContext,"你输入的手机号格式不正确");
                    return;
                }
                //验证电话号码不能为空
                if (TextUtils.isEmpty(user_phone_number)){
                    ToastUtils.showMessageShort(mContext,"请输入手机号！");
                    return;
                }
                code = TimerUtil.getNum();
                LogUtil.i("code","---------" + code);
                getPin(user_phone_number);
                TimerUtil mTimerUtil = new TimerUtil(btn_verification_code);
                mTimerUtil.timers();
            break;
            case R.id.btn_fast_register:
                submit();
            break;

        }
    }

    private void submit() {

        //验证密码不能为空
        String password = edi_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showMessageShort(mContext, "密码不能为空");
            return;
        }
        //验证确认密码不能为空
        String confirmpwd = edi_password_again.getText().toString().trim();
        if (TextUtils.isEmpty(confirmpwd)) {
            ToastUtils.showMessageShort(mContext, "确认密码不能为空");
            return;
        }
        //验证密码和确认密码是否相同
        if (!password.equals(confirmpwd)) {
            ToastUtils.showMessageShort(mContext, "两次输入密码不一致");
            return;
        }
        //验证密码格式是否正确

        if (!StringUtils.isPwd(password)) {
            ToastUtils.showMessageShort(mContext, "密码格式不正确，请核对后重新输入");
            return;
        }
        String userphone = edi_phone_number.getText().toString().trim();
        logpwd = password;
        String inviteCode = edi_verification_code.getText().toString().trim();
        try {
            userRegister(userphone, Md5Util.md5Encode(password), inviteCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 用户注册
     *
     * @param userPhone
     * @param password
     * @param inviteCode
     */
    private void userRegister(final String userPhone, final String password, String inviteCode) {
       /* cmd:”userRegister”
        userPhone:”18023344”  //用户手机号
        password:”  ”    //用户密码
        addressId:@“410100” //用户活动县ID */
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "userRegister");
        params.put("userPhone", userPhone);
        params.put("password", password);
        params.put("addressId", "100100");*/
        String json="{\"cmd\":\"userRegister\",\"userPhone\":\"" + userPhone + "\"," +
                "\"password\":\"" + password +"\",\"inviteCode\":\"" + inviteCode + "\"}";
        params.put("json", json);
        dialog.show();
        //车品商城服务端
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showMessageShort(mContext, e.getMessage());
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        UserRegisterBean bean = gson.fromJson(response, UserRegisterBean.class);
                        if ("0".equals(bean.result)) {
                            ToastUtils.showMessageShort(mContext, "注册成功");
//                            SharedPreferencesUtil.putSharePre(RegisterActivity.this,"openId",bean.userInfo.openId);
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", userPhone);
                            bundle.putString("password", logpwd);
                            MyApplication.openActivity(mContext, LoginActivity.class, bundle);
                            finish();
                        } else {
                            ToastUtils.showMessageShort(mContext, bean.resultNote);
                        }
                    }
                });
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
            //聚合验证码
            OkHttpUtils.post().url(getString(R.string.juhe_url)).params(params).build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.showMessageShort(mContext, e.getMessage());
                            dialog.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Gson gson = new Gson();
                            VerificationCodeBean Vbean = gson.fromJson(response, VerificationCodeBean.class);
                            if ("操作成功".equals(Vbean.reason)) {
                                ToastUtils.showMessageShort(mContext, "验证码已发送");
                                TimerUtil timerUtil = new TimerUtil(btn_verification_code);
                                timerUtil.timers();
                                dialog.dismiss();
                            } else {
                                ToastUtils.showMessageShort(mContext, "验证码发送失败");
                                dialog.dismiss();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
