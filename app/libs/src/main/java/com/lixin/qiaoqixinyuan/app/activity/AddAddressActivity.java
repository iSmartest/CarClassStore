package com.lixin.qiaoqixinyuan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyAddressBean;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    private List<MyAddressBean.AddressBean> addressList = new ArrayList<>();
    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_name;
    private EditText et_phone;
    private TextView tv_address;
    private TextView tv_select_address;
    private RelativeLayout rl_address;
    private EditText et_detailaddress;
    private EditText et_postcode;
    private ImageView iv_select;
    private RelativeLayout rl_set_defaultaddress;
    private Button btn_submit;
    private String latitude;
    private String longitude;
    private String isDefault="1";
    private String token;
    private LinearLayout ll_title;
    private ImageView iv_mapselect_address;
    private LinearLayout activity_modify_address;
    private int code;
    private String lat;
    private String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        code = getIntent().getIntExtra("code",-1);
        initView();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("添加地址");
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_select_address = (TextView) findViewById(R.id.tv_select_address);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        et_detailaddress = (EditText) findViewById(R.id.et_detailaddress);
        et_postcode = (EditText) findViewById(R.id.et_postcode);
        iv_select = (ImageView) findViewById(R.id.iv_select);
        iv_select.setOnClickListener(this);
        rl_set_defaultaddress = (RelativeLayout) findViewById(R.id.rl_set_defaultaddress);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        ll_title.setOnClickListener(this);
        iv_mapselect_address = (ImageView) findViewById(R.id.iv_mapselect_address);
        iv_mapselect_address.setOnClickListener(this);
        activity_modify_address = (LinearLayout) findViewById(R.id.activity_modify_address);
        activity_modify_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.iv_mapselect_address:
                MyApplication.openActivityForResult((Activity) context, SelectMapAddressActivity.class, 2);
                break;
            case R.id.iv_select:
                isDefault="0";
                iv_select.setImageResource(R.mipmap.ic_select);
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            String address = data.getStringExtra("address");
            lat = data.getStringExtra("lat");
            lon = data.getStringExtra("lon");
            tv_select_address.setText(address);
        }
    }

    private void submit() {
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast(context, "请输入收货人姓名");
            return;
        }

        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast(context, "请输入收件人手机号码");
            return;
        }
        String detailaddress = tv_select_address.getText().toString().trim();
        if (TextUtils.isEmpty(detailaddress)) {
            ToastUtil.showToast(context, "选择定位地址");
            return;
        }
        String address = et_detailaddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showToast(context, "请输入详细地址");
            return;
        }

        String postcode = et_postcode.getText().toString().trim();
        if (TextUtils.isEmpty(postcode)) {
            ToastUtil.showToast(context, "请输入邮政编码");
            return;
        }
        String myaddress = detailaddress+address;
        addMyAddress(MyApplication.getuId(), myaddress, phone, name, postcode);
    }

    /**
     * 增加收货地址
     *
     * @param uid
     * @param address
     * @param userPhone
     * @param userName
     * @param cityId
     */
    private void addMyAddress(String uid, String address, String userPhone, String userName,
                              final String cityId) {
       /* cmd：”addMyAddress
        uid:”12”  //用户Id
        address:”河南省郑州市管城区中州大道与航海路交叉口美林河畔   1号楼”
        userPhone:”12983728432” //收货人电话
        userName:”张三” //收货人姓名
        cityId:"410100"//城市ID
        latitude:"23.5463"//客户当前纬度
        longitude:"25.6548"//客户当前经度
        isDefault:“0”    // 0 是默认 1 非默认
        token:    [JPUSHService registrationID]   //推送token*/
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"addMyAddress\",\"uid\":\"" + uid + "\",\"address\":\"" + address + "\"" +
                ",\"userPhone\":\"" + userPhone + "\",\"userName\":\"" + userName + "\"" +
                ",\"cityId\":\"" + cityId + "\",\"latitude\":\"" + lat+ "\"" +
                ",\"longitude\":\"" + lon + "\",\"isDefault\":\"" + isDefault + "\"" +
                ",\"token\":\"" + token + "\"}";
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
                        MyAddressBean bean = gson.fromJson(response, MyAddressBean.class);
                        if ("1".equals(bean.result)) {
                            ToastUtil.showToast(context, bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        ToastUtil.showToast(context, "添加收货地址成功");
                        setResult(3);
                        finish();
                        dialog.dismiss();
                    }
                });

    }

}
