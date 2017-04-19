package com.lixin.qiaoqixinyuan.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.lixin.qiaoqixinyuan.app.Im.YWSDKUtil;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.UserLoginBean;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
import com.lixin.qiaoqixinyuan.app.util.Md5Util;
import com.lixin.qiaoqixinyuan.app.util.PhoneAndPwdUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.ProgressDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_userphone;
    private EditText et_password;
    private TextView tv_forgetPassword;
    private TextView tv_register;
    private Button btn_login;
    private ImageView iv_qqlogin;
    private ImageView iv_weixinlogin;
    private UMShareAPI mShareAPI;
    private Dialog progressDlg;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        mShareAPI = UMShareAPI.get(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_userphone = (EditText) findViewById(R.id.et_userphone);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_forgetPassword = (TextView) findViewById(R.id.tv_forgetPassword);
        tv_register = (TextView) findViewById(R.id.tv_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        iv_qqlogin = (ImageView) findViewById(R.id.iv_qqlogin);
        iv_weixinlogin = (ImageView) findViewById(R.id.iv_weixinlogin);
    }

    private void initData() {
        tv_title.setText("登录");
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        String phone = intent.getStringExtra("phone");
        String password = intent.getStringExtra("password");
        et_userphone.setText(phone);
        et_password.setText(password);
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        iv_qqlogin.setOnClickListener(this);
        iv_weixinlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.btn_login:
                submit();
                break;
            case R.id.iv_qqlogin://QQ登录
                progressDlg = ProgressDialog.createLoadingDialog(context, "登录跳转中...");
                progressDlg.show();
                ToastUtil.showToast(context, "正在跳转QQ登录,请稍后...");
                mShareAPI.isInstall(this, SHARE_MEDIA.QQ);
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.iv_weixinlogin://微信登录
                if (!isWeixinAvilible(this)) {
                    ToastUtil.showToast(this, "请安装微信客户端");
                    return;
                }
                progressDlg = ProgressDialog.createLoadingDialog(context, "登录跳转中...");
                progressDlg.show();
                ToastUtil.showToast(this, "正在跳转微信登录,请稍后...");
                mShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN);
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.tv_forgetPassword:
                MyApplication.openActivity(context, ForgetPwdActivity.class);
                break;
            case R.id.tv_register:
                MyApplication.openActivity(context, RegisterActivity.class);
                break;
        }
    }

    private void submit() {
        //验证电话号码是否正确
        String userphone = et_userphone.getText().toString().trim();//电话号码
        if (TextUtils.isEmpty(userphone)) {
            ToastUtil.showToast(context, "电话号码不能为空");
            return;
        }
        boolean isphonenum = PhoneAndPwdUtil.isPhone(userphone);
        if (!isphonenum) {
            ToastUtil.showToast(context, "电话号码不正确，请核对后重新输入");
            return;
        }
        //验证密码是否为空
        String password = et_password.getText().toString().trim();//密码
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(context, "密码不能为空");
            return;
        }
        //验证密码格式是否正确
        boolean ispassword = PhoneAndPwdUtil.isPwd(password);
        if (!ispassword) {
            ToastUtil.showToast(context, "密码格式不正确，请核对后重新输入");
            return;
        }
        try {
            userLogin(userphone, Md5Util.md5Encode(password), token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        if (progressDlg != null && progressDlg.isShowing()) {
            progressDlg.dismiss();
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            String screen_name = null, profile_image_url = null, openid = null;
            if (SHARE_MEDIA.QQ.equals(share_media)) {
                screen_name = map.get("screen_name");//昵称
                profile_image_url = map.get("profile_image_url");//头像
                openid = map.get("openid");//第三方平台id
            } else if (SHARE_MEDIA.WEIXIN.equals(share_media)) {
                screen_name = map.get("screen_name");//昵称
                profile_image_url = map.get("profile_image_url");//头像
                openid = map.get("openid");//第三方平台id
            }
            thirdLogin(openid, screen_name, profile_image_url);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 用户登录
     * @param phone
     * @param password
     * @param token
     */
    private void userLogin(String phone, String password, String token) {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "userLogin");
        params.put("phone", phone);
        params.put("password", password);*/
        //params.put("token", "123123");
        String json="{\"cmd\":\"userLogin\",\"phone\":\"" + phone + "\",\"password\":\""
                + password + "\",\"token\":\"" + token + "\"}";
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
                        Gson gson = new Gson();
                        UserLoginBean bean = gson.fromJson(response, UserLoginBean.class);
                        if ("0".equals(bean.result)) {
                            SharedPreferencesUtil.putSharePre(context, "uid", bean.userInfo.uid);
                            SharedPreferencesUtil.putSharePre(context, "phoneNum", bean.userInfo.phoneNum);
                            SharedPreferencesUtil.putSharePre(context, "nickName", bean.userInfo.nickName);
                            SharedPreferencesUtil.putSharePre(context, "isLogin", true);
                            ToastUtil.showToast(context, "登录成功");
                            String openId = SharedPreferencesUtil.getSharePreStr(context, "openId");
                            YWSDKUtil.loginYM(openId, context);
                            LogUtil.d("openId",bean.userInfo.openId);
                            SharedPreferencesUtil.putSharePre(context,"openId",bean.userInfo.openId);
                            MyApplication.openActivity(context, MainActivity.class);
                            finish();
                            dialog.dismiss();
                        } else {
                            ToastUtil.showToast(context, bean.resultNote);
                            dialog.dismiss();
                        }
                    }
                });
    }

    /**
     * 第三方登录
     * @param thirdUid
     * @param nickName
     * @param userIcon
     */
    private void thirdLogin(String thirdUid, final String nickName, String userIcon) {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "thirdLogin");
        params.put("thirdUid", thirdUid);
        params.put("nickName", nickName);
        params.put("userIcon", userIcon);
        params.put("token", token);*/
        String json="{\"cmd\":\"thirdLogin\",\"thirdUid\":\"" + thirdUid + "\",\"nickName\":\""
                + nickName + "\",\"userIcon\":\"" + userIcon + "\",\"token\":\"" + token + "\"}";
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
                        Gson gson = new Gson();
                        UserLoginBean bean = gson.fromJson(response, UserLoginBean.class);
                        if ("0".equals(bean.result)) {

                          /*  if (bean.userInfo.phoneNum==null||"".equals(bean.userInfo.phoneNum)){
                                ToastUtil.showToast(context,"请先绑定手机号");
                                Bundle bundle=new Bundle();
                                bundle.putString("uid",bean.userInfo.uid);
                                bundle.putString("nickName",bean.userInfo.nickName);
                                MyApplication.openActivity(context,ModifyPhoneActivity.class,bundle);
                                finish();
                                dialog.dismiss();
                            }else {*/
                                SharedPreferencesUtil.putSharePre(context, "uid", bean.userInfo.uid);
                                SharedPreferencesUtil.putSharePre(context, "phoneNum", bean.userInfo.phoneNum);
                                SharedPreferencesUtil.putSharePre(context, "openId", bean.userInfo.openId);
//                                SharedPreferencesUtil.putSharePre(context, "nickName", bean.userInfo.nickName);
                            ToastUtil.showToast(context, "登录成功");
                            LogUtil.d("openId",bean.userInfo.openId);
                            SharedPreferencesUtil.putSharePre(context,"openId",bean.userInfo.openId);
                                MyApplication.openActivity(context, MainActivity.class);
                                finish();
                                dialog.dismiss();
                            //}
                        } else {
                            ToastUtil.showToast(context, bean.resultNote);
                            dialog.dismiss();
                        }
                    }
                });
    }
}
