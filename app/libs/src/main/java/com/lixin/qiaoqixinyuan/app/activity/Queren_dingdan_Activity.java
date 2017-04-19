package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Zhifu_fangshi_Bean;
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

import static android.R.attr.id;
import static com.baidu.location.h.j.am;import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Queren_dingdan_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private TextView tv_queren_dingdan_jiage;
    private TextView tv_queren_dingdan_jianmian;
    private TextView tv_queren_dingdan_xianzai;
    private LinearLayout activity_queren_dingdan_;
    private LinearLayout layout_queren_dingdan_fangshi;
    private String shangjiaId;
    private TextView tv_item_zhifu_zaixian;
    private TextView tv_item_zhifu_jianmian;
    private String uid;
    private String token;
    private String orderId;
    private Zhifu_fangshi_Bean zhifu_fangshi_bean;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queren_dingdan_);
        Intent intent = getIntent();
        shangjiaId = intent.getStringExtra("shangjiaId");
        orderId = intent.getStringExtra("orderId");
        amount = intent.getStringExtra("amount");
        uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView();
        getdata();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("确认订单");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        tv_queren_dingdan_jiage = (TextView) findViewById(R.id.tv_queren_dingdan_jiage);
        activity_queren_dingdan_ = (LinearLayout) findViewById(R.id.activity_queren_dingdan_);
        layout_queren_dingdan_fangshi = (LinearLayout) findViewById(R.id.layout_queren_dingdan_fangshi);
        tv_item_zhifu_zaixian = (TextView) findViewById(R.id.tv_item_zhifu_zaixian);
        tv_item_zhifu_zaixian.setOnClickListener(this);
        tv_item_zhifu_jianmian = (TextView) findViewById(R.id.tv_item_zhifu_jianmian);
        tv_item_zhifu_jianmian.setOnClickListener(this);
        tv_queren_dingdan_jiage.setText("￥"+amount);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id. tv_item_zhifu_zaixian:
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra("amount",amount);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
                break;
            case R.id.tv_item_zhifu_jianmian:
                getjianmianpay();
                break;
        }
    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
        String json="{\"cmd\":\"getpayway\",\"shangjiaid\":\"" +
                shangjiaId + "\" }";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Queren_dingdan_Activity.this, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        zhifu_fangshi_bean = gson.fromJson(response, Zhifu_fangshi_Bean.class);
                        if (zhifu_fangshi_bean.result.equals("1")) {
                            ToastUtil.showToast(Queren_dingdan_Activity.this, zhifu_fangshi_bean.resultNote);
                            return;
                        }

                        if (zhifu_fangshi_bean.paywayid.equals("0")) {
                            tv_item_zhifu_zaixian.setVisibility(View.VISIBLE);
                            tv_item_zhifu_jianmian.setVisibility(View.GONE);
                        }else if (zhifu_fangshi_bean.paywayid.equals("1")){
                            tv_item_zhifu_zaixian.setVisibility(View.GONE);
                            tv_item_zhifu_jianmian.setVisibility(View.VISIBLE);
                        }else if (zhifu_fangshi_bean.paywayid.equals("2")){
                            tv_item_zhifu_zaixian.setVisibility(View.VISIBLE);
                            tv_item_zhifu_jianmian.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    private void getjianmianpay(){
 /*  cmd：“makeorder”
  uid:"12"   //用户id
  shangjiaid:"56"   //商家id
  orderId:”20141314” // 订单号
  token:    [JPUSHService registrationID]   //推送token*/
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "makeorder");
        params.put("uid", uid);
        params.put("shangjiaid", shangjiaId);
        params.put("orderId", orderId);
        params.put("token", token)*/;
        String json="{\"cmd\":\"makeorder\",\"uid\":\"" + uid + "\",\"shangjiaid\":\"" + shangjiaId + "\"" +
                ",\"orderId\":\"" + orderId + "\",\"token\":\"" + token + "\" }";
        params.put("json", json);
        dialog.show();
       OkHttpUtils.
                post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Queren_dingdan_Activity.this, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            String amount = object.getString("amount");
                            ToastUtil.showToast(context, resultNote);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
