package com.lixin.qiaoqixinyuan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.pingplusplus.android.Pingpp;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;


/**
 * 支付
 * Created by Administrator on 2016/10/6 0006.
 */

public class PayActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private TextView tv_all_money;
    private ImageView iv_alipay;
    private RelativeLayout rl_alipay;
    private ImageView iv_wx;
    private RelativeLayout rl_wx;
    private Button btn_confirm;
    private String orderId, allPrice, body;
    private String channel = "alipay";//支付方式，alipay支付宝，wx微信
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        initData();
        initListner();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_all_money = (TextView) findViewById(R.id.tv_all_money);
        iv_alipay = (ImageView) findViewById(R.id.iv_alipay);
        rl_alipay = (RelativeLayout) findViewById(R.id.rl_alipay);
        iv_wx = (ImageView) findViewById(R.id.iv_wx);
        rl_wx = (RelativeLayout) findViewById(R.id.rl_wx);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void initData() {
        tv_title.setText("支付");
        body="qiaodongqinyuan";
        Intent intent = getIntent();
        if (intent==null){
            return;
        }
        allPrice = intent.getStringExtra("amount");
        orderId = intent.getStringExtra("orderId");
        float price = Float.parseFloat(allPrice);
        int e = (int) price;
        amount = String.valueOf(e * 100);
        tv_all_money.setText("￥" + allPrice);
    }

    private void initListner() {
        iv_turnback.setOnClickListener(this);
        rl_alipay.setOnClickListener(this);
        rl_wx.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback:
                finish();
                break;

            case R.id.rl_alipay:
                channel = "alipay";
                iv_alipay.setBackgroundResource(R.mipmap.ic_select);
                iv_wx.setBackgroundResource(R.mipmap.ic_unselect);
                break;

            case R.id.rl_wx:
                channel = "wx";
                iv_alipay.setBackgroundResource(R.mipmap.ic_unselect);
                iv_wx.setBackgroundResource(R.mipmap.ic_select);
                break;

            case R.id.btn_confirm:
                String charge = "";
                Pingpp.createPayment(PayActivity.this, charge);//发起支付
                break;
        }
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                Intent i = new Intent();
                if (result.equals("success")) {
                    ToastUtil.showToast(this, "支付完成");
                    i.putExtra("pay", "0");
                    setResult(PayActivity.this.RESULT_OK, i);
                    finish();
                } else {
                    i.putExtra("pay", "1");
                    ToastUtil.showToast(this, "支付取消");
                    setResult(PayActivity.this.RESULT_OK, i);
                    finish();
                }
            }
        }
    }
    private void getzaixianpayway() {
      /*  cmd：“getCharge”
        amount:”555”   // 支付多少钱 以分传
        orderId:”20141314”    //订单号
        channel:” wx” //wx:微信  alipay:支付宝  upacp:银联支付*/
        Map<String, String> params = new HashMap<>();
      /*  params.put("cmd", "getCharge");
        params.put("amount", "12");
        params.put("orderId", "12");
        params.put("channel", "12");*/
        String json="{\"cmd\":\"getCharge\",\"amount\":\"" + amount + "\" " +
                ",\"orderId\":\"" + orderId + "\",\"channel\":\"" + channel + "\"}";
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
                        ToastUtil.showToast(context, e.getMessage());
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
                            String charge = object.getString("charge");
                            ToastUtil.showToast(context, resultNote);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
