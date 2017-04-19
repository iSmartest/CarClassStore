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
import com.lixin.qiaoqixinyuan.app.util.Md5Util;
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

public class ModifyPwdActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_oldpwd;
    private EditText et_newpwd;
    private EditText et_confirmpwd;
    private Button btn_confirm;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypwd);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_oldpwd = (EditText) findViewById(R.id.et_oldpwd);
        et_newpwd = (EditText) findViewById(R.id.et_newpwd);
        et_confirmpwd = (EditText) findViewById(R.id.et_confirmpwd);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void initData() {
        tv_title.setText("修改密码");
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.btn_confirm:
                submit();
                break;
        }
    }

    private void submit() {
        String oldpwd = et_oldpwd.getText().toString().trim();
        if (TextUtils.isEmpty(oldpwd)) {
            ToastUtil.showToast(context,"请输入旧密码");
            return;
        }

        String newpwd = et_newpwd.getText().toString().trim();
        if (TextUtils.isEmpty(newpwd)) {
            ToastUtil.showToast(context,"请输入新密码");
            return;
        }

        String confirmpwd = et_confirmpwd.getText().toString().trim();
        if (TextUtils.isEmpty(confirmpwd)) {
            ToastUtil.showToast(context,"请确认新密码");
            return;
        }

        if (!confirmpwd.equals(newpwd)){
            ToastUtil.showToast(context,"两次输入新密码不一致，请核对后重新输入");
            return;
        }
        String uid = MyApplication.getuId();
        try {
            changePassword(uid, Md5Util.md5Encode(oldpwd),Md5Util.md5Encode(newpwd));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 修改密码
     * @param uid
     * @param oldPassword
     * @param password
     */
    private void changePassword(String uid, String oldPassword, String password) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "changePassword");
        params.put("uid", uid);
        params.put("oldPassword", oldPassword);
        params.put("password", password);
        params.put("token", token);*/
        String json="{\"cmd\":\"changePassword\",\"uid\":\"" + uid + "\",\"oldPassword\":\"" + oldPassword + "\"," +
                "\"password\":\"" + password + "\",\"token\":\"" + token + "\"}";
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
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("0".equals(result)) {
                                ToastUtil.showToast(context, "密码修改成功");
                                finish();
                                dialog.dismiss();
                            } else {
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
