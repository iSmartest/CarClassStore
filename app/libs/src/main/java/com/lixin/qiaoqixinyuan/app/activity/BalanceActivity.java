package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class BalanceActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private TextView tv_balance;
    private TextView tv_alipay;
    private TextView tv_weixin;
    private TextView tv_upacp;
    private TextView tv_mingxi;
    private String uid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_alipay = (TextView) findViewById(R.id.tv_alipay);
        tv_weixin = (TextView) findViewById(R.id.tv_weixin);
        tv_upacp = (TextView) findViewById(R.id.tv_upacp);
        tv_mingxi = (TextView) findViewById(R.id.tv_mingxi);
    }

    private void initData() {
        tv_title.setText("余额");
        tv_balance.setText("200");
        getBalance(MyApplication.getuId());
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);
        tv_mingxi.setOnClickListener(this);
        tv_alipay.setOnClickListener(this);
        tv_weixin.setOnClickListener(this);
        tv_upacp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_mingxi:
                finish();
                break;
            case R.id.tv_alipay:
                MyApplication.openActivity(context,AlipayCasherActivity.class);
                break;
            case R.id.tv_weixin:
                MyApplication.openActivity(context,WeiXinCasherActivity.class);
                break;
            case R.id.tv_upacp:
                MyApplication.openActivity(context,UpacpCasherActivity.class);
                break;
        }
    }

    /**
     * @param uid
     */
    private void getBalance(String uid) {
        Map<String, String> params = new HashMap<>();
        /*cmd:"getBalance"
        uid:"25"
        token:    [JPUSHService registrationID]   //推送token*/
        String json="{\"cmd\":\"getBalance\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
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
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)) {
                                ToastUtil.showToast(context, resultNote);
                                dialog.dismiss();
                            } else {
                                String balance = object.getString("balance");
                                tv_balance.setText(balance);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
