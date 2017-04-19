package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.umeng.socialize.utils.DeviceConfig.context;

public class WeiXinCasherActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_zhanghu;
    private EditText et_name;
    private EditText et_money;
    private Button btn_confirm;
    private String uid;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_casher);
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
        et_zhanghu = (EditText) findViewById(R.id.et_zhanghu);
        et_name = (EditText) findViewById(R.id.et_name);
        et_money = (EditText) findViewById(R.id.et_money);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void initData() {
        tv_title.setText("微信提现");
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.btn_confirm:
                submit();
                break;
        }
    }

    private void submit() {
        String zhanghu = et_zhanghu.getText().toString().trim();
        if (TextUtils.isEmpty(zhanghu)) {
            ToastUtil.showToast(context, "请输入微信账户");
            return;
        }

        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast(context, "请输入真实姓名");
            return;
        }

        String money = et_money.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            ToastUtil.showToast(context, "请输入提取金额");
            return;
        }
        getMoneyToweixin(MyApplication.getuId(),money,zhanghu,name);
    }
    /**
     * @param uid
     * @param money
     * @param cardNum
     * @param realName
     */
    private void getMoneyToweixin(String uid, String money, final String cardNum, String realName) {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "getMoneyToweixin");
        params.put("uid", uid);
        params.put("money", money);
        params.put("cardNum", cardNum);
        params.put("realName", realName);*/
        String json="{\"cmd\":\"getMoneyToweixin\",\"uid\":\"" + uid + "\",\"money\":\"" + money + "\"," +
                "\"cardNum\":\"" + cardNum + "\",\"realName\":\"" + realName + "\",\"token\":\"" + token + "\"}";
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
                        dialog.dismiss();
                        try {
                            JSONObject object=new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)){
                                ToastUtil.showToast(context,resultNote);
                            }else {
                                ToastUtil.showToast(context,resultNote);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
