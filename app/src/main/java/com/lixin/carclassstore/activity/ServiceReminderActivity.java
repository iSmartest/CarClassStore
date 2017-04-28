package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.MineMenuBean;
import com.lixin.carclassstore.bean.ReplyBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/17
 * My mailbox is 1403241630@qq.com
 */

public class ServiceReminderActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private TextView text_insurance_expiration_reminder,text_annual_expiration_reminder,text_maintenance_expiration_reminder;
    Switch switch_01,switch_02,switch_03;
    private String remindOpen;
    private String remindType;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_reminder);
        uid = (String) SPUtils.get(ServiceReminderActivity.this,"uid","");
        hideBack(false);
        setTitleText("服务提醒");
        initView();
    }

    private void initView() {
        text_insurance_expiration_reminder = (TextView) findViewById(R.id.text_insurance_expiration_reminder);
        text_annual_expiration_reminder = (TextView) findViewById(R.id.text_annual_expiration_reminder);
        text_maintenance_expiration_reminder = (TextView) findViewById(R.id.text_maintenance_expiration_reminder);
        switch_01 = (Switch) findViewById(R.id.switch_01);
        switch_01.setOnCheckedChangeListener(this);
        switch_02 = (Switch) findViewById(R.id.switch_02);
        switch_02.setOnCheckedChangeListener(this);
        switch_03 = (Switch) findViewById(R.id.switch_03);
        switch_03.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_01:
                if (isChecked){
                    remindOpen = "0";
                    remindType = "0";
                    ToastUtils.showMessageLong(context,"提醒已开启");
                }else {
                    remindOpen = "1";
                    remindType = "0";
                    ToastUtils.showMessageLong(context,"提醒已关闭");
                }
                getdata(remindOpen,remindType);
                break;
            case R.id.switch_02:
                if (isChecked){
                    remindOpen = "0";
                    remindType = "1";
                    ToastUtils.showMessageLong(context,"提醒已开启");
                }else {
                    remindOpen = "1";
                    remindType = "1";
                    ToastUtils.showMessageLong(context,"提醒已关闭");
                }
                getdata(remindOpen,remindType);
                break;
            case R.id.switch_03:
                if (isChecked){
                    remindOpen = "0";
                    remindType = "2";
                    ToastUtils.showMessageLong(context,"提醒已开启");
                }else {
                    remindOpen = "1";
                    remindType = "2";
                    ToastUtils.showMessageLong(context,"提醒已关闭");
                }
                getdata(remindOpen,remindType);
                break;
        }
    }

    private void getdata(final String remindOpen, String remindType) {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getMineMenuInfo\",\"remindOpen\":\"" + remindOpen +"\",\"remindType\":\"" + remindType + "\",\"uid\":\"" + uid + "\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageLong(context, "网络异常");
                dialog1.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                dialog1.dismiss();
                ReplyBean replyBean = gson.fromJson(response, ReplyBean.class);
                if (replyBean.getResult().equals("1")){
                    ToastUtils.showMessageLong(context,replyBean.getResultNote());
                }else
                    ToastUtils.showMessageLong(context,"操作成功");
            }
        });
    }
}
