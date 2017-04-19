package com.lixin.qiaoqixinyuan.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.widgets.ProvincePick;
import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.UserRegisterBean;
import com.lixin.qiaoqixinyuan.app.bean.VerificationCodeBean;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
import com.lixin.qiaoqixinyuan.app.util.Md5Util;
import com.lixin.qiaoqixinyuan.app.util.PhoneAndPwdUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.TimerUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.CityVo;
import com.lixin.qiaoqixinyuan.app.view.ProvinceVo;
import com.lixin.qiaoqixinyuan.app.view.TxtReader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static android.R.id.list;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_userphone;
    private EditText et_password;
    private EditText et_confirmpassword;
    private EditText et_passPin;
    private TextView tv_verification_code;
    private Button btn_register;
    private String code;
    private String addressId;
    private String logpwd;
    private LinearLayout layout_city_select;
    private ArrayList<ProvinceVo> provinceList;
    private ArrayList<String> provinceStrList;
    private HashMap<String,ArrayList<String>> cityStrList;
    private ArrayList<CityVo> cityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
        initListener();
        provinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        provinceStrList=new ArrayList<>();
        cityStrList=new HashMap<>();
        InputStream inputStream = getResources()
                .openRawResource(R.raw.cityinfo);
        TxtReader.getProvinceListAndCityList(inputStream, provinceList,
                cityList);
        ProvinceVo province;
        ArrayList<String> list;
        for (int i=0;i<provinceList.size();i++) {
            province=provinceList.get(i);
            provinceStrList.add(province.getProvinceName());
            list=getCityListByProviceCode(cityList,province.getProvincePostCode());
            cityStrList.put(province.getProvinceName(),list);
        }
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_userphone = (EditText) findViewById(R.id.et_userphone);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirmpassword = (EditText) findViewById(R.id.et_confirmpassword);
        et_passPin = (EditText) findViewById(R.id.et_passPin);
        tv_verification_code = (TextView) findViewById(R.id.tv_verification_code);
        btn_register = (Button) findViewById(R.id.btn_register);

        layout_city_select = (LinearLayout) findViewById(R.id.layout_city_select);
        layout_city_select.setOnClickListener(this);
    }

    private void initData() {
        tv_title.setText("注册");
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);
        btn_register.setOnClickListener(this);
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
            case R.id.btn_register:
                showcity();
                submit();
                break;
        }
    }
  private void showcity(){
      ProvincePick provincePopWin = new ProvincePick.Builder(context,new ProvincePick.OnProCityPickedListener(){
          @Override
          public void onProCityPickCompleted(String province, String city, String dateDesc) {
              Toast.makeText(context, dateDesc, Toast.LENGTH_SHORT).show();
              addressId="110100";
          }
      }).textConfirm("确定") //text of confirm button
              .textCancel("取消") //text of cancel button
              .btnTextSize(16) // button text size
              .viewTextSize(25) // pick view text size
              .colorCancel(Color.parseColor("#999999")) //color of cancel button
              .colorConfirm(Color.parseColor("#009900"))//color of confirm button
              .setProvinceList(provinceStrList) //min year in loop
              .setCityList(cityStrList) // max year in loop
              .dateChose("浙江省-宁波市") // date chose when init popwindow
              .build();
      provincePopWin.showPopWin(RegisterActivity.this);

  }
    private void submit() {
        //验证验证码不能为空
      /* String passPin = et_passPin.getText().toString().trim();
        if (TextUtils.isEmpty(passPin)) {
            ToastUtil.showToast(context, "验证码不能为空");
            return;
        }
        //验证验证码是否正确
        if (!passPin.equals(code)) {
            ToastUtil.showToast(context, "验证码不正确");
            return;
        }*/
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
        String userphone = et_userphone.getText().toString().trim();
        logpwd = password;
        try {
            userRegister(userphone, Md5Util.md5Encode(password), addressId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取短信验证码
     *
     * @param phone
     */
    private void getPin(String phone) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("tpl_value", URLEncoder.encode("#code#=" + code, "utf-8"));
            params.put("dtype", "json");
            params.put("tpl_id", "29654");
            params.put("key", "9619df26effb28c8b5099db402a04155");
            params.put("mobile", phone);
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
                            dialog.dismiss();
                            VerificationCodeBean Vbean = gson.fromJson(response, VerificationCodeBean.class);
                            if ("操作成功".equals(Vbean.reason)) {
                                ToastUtil.showToast(context, "验证码已发送");
                                TimerUtil timerUtil = new TimerUtil(tv_verification_code);
                                timerUtil.timers();
                            } else {
                                ToastUtil.showToast(context, "验证码发送失败");
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 用户注册
     *
     * @param userPhone
     * @param password
     * @param addressId
     */
    private void userRegister(final String userPhone, final String password, String addressId) {
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
                "\"password\":\"" + password +"\",\"addressId\":\"" + 100100 + "\"}";
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
                        dialog.dismiss();
                        UserRegisterBean bean = gson.fromJson(response, UserRegisterBean.class);
                        if ("0".equals(bean.result)) {
                            ToastUtil.showToast(context, "注册成功");
                            SharedPreferencesUtil.putSharePre(RegisterActivity.this,"openId",bean.userInfo.openId);
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", userPhone);
                            bundle.putString("password", logpwd);
                            MyApplication.openActivity(context, LoginActivity.class, bundle);
                            finish();
                        } else {
                            ToastUtil.showToast(context, bean.resultNote);
                        }
                    }
                });
    }
    private  ArrayList<String> getCityListByProviceCode(ArrayList<CityVo>cityList,String provincePostCode){
        ArrayList<String>cityName=new ArrayList<>();
        for (CityVo cv : cityList) {
            if (cv.getProvincePostCode().equals(provincePostCode)) {
                cityName.add(cv.getCityName());
            }
        }
        return cityName;
    }
}
