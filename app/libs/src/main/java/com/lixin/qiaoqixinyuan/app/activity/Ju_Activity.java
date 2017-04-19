package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static android.R.attr.type;
import static com.umeng.socialize.Config.dialog;

public class Ju_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private EditText et_huifu_message;
    private TextView tv_huifu_huifu;
    private TextView tv_huifu_quxiao;
    private LinearLayout activity_ju_;
    private String uid;
    private String token;
    private String newsid;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_);
        uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        newsid = getIntent().getStringExtra("newsid");
        type = getIntent().getStringExtra("type");
        initView();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("举报详情");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        et_huifu_message = (EditText) findViewById(R.id.et_huifu_message);
        tv_huifu_huifu = (TextView) findViewById(R.id.tv_huifu_huifu);
        tv_huifu_huifu.setOnClickListener(this);
        tv_huifu_quxiao = (TextView) findViewById(R.id.tv_huifu_quxiao);
        tv_huifu_quxiao.setOnClickListener(this);
        activity_ju_ = (LinearLayout) findViewById(R.id.activity_ju_);
    }

    private void submit() {
        // validate
        String message = et_huifu_message.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "请输入举报内容", Toast.LENGTH_SHORT).show();
            return;
        }
        getdata(message);
    }
    private void getdata(String message){
       /* cmd:”reporthousdetail”
        newsid:"12"   //房屋信息id 、二手信息id
        uid:"12"   //用户id
        type:"0"      // 0 房屋信息   1 二手信息  2 招聘  3 求职 //app端写死
        reporttext:"错误"   //举报信息
        token:    [JPUSHService registrationID]   //推送token*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "tiebadetail");
        params.put("tiebaId", tiebaId);*/
        String json = "{\"cmd\":\"reporthousdetail\",\"newsid\":\"" + newsid + "\"" +
                ",\"uid\":\"" + uid + "\",\"type\":\"" + android.R.attr.type + "\"" +
                ",\"reporttext\":\"" + message + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context,e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("0".equals(result)) {
                                ToastUtil.showToast(context, "举报成功");
                                finish();
                            } else {
                                ToastUtil.showToast(context, resultNote);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.tv_huifu_huifu:
            submit();
            break;
        case R.id.tv_huifu_quxiao:
            finish();
            break;
        case R.id.iv_turnback:
            finish();
            break;
    }
    }
}
