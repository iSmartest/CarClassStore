package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.UserLoginBean;
import com.lixin.qiaoqixinyuan.app.util.DataCleanManager;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.StringUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static com.baidu.location.h.j.al;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private TextView tv_modifypwd;
    private TextView tv_modifyphone;
    private TextView tv_aboutus;
    private TextView tv_instruction;
    private TextView tv_applystore;
    private TextView tv_update;
    private TextView tv_sop;
    private ImageView iv_turntogoal;
    private RelativeLayout rl_update;
    private ProgressBar progressBar1;
    private TextView tv_suggestion;
    private TextView tv_messagefree;
    private TextView tv_share;
    private TextView tv_cache;
    private LinearLayout ll_clearcache;
    private Button btn_loginout;
    private String uid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        initData();
        initLiserner();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_modifypwd = (TextView) findViewById(R.id.tv_modifypwd);
        tv_modifyphone = (TextView) findViewById(R.id.tv_modifyphone);
        tv_aboutus = (TextView) findViewById(R.id.tv_aboutus);
        tv_instruction = (TextView) findViewById(R.id.tv_instruction);
        tv_applystore = (TextView) findViewById(R.id.tv_applystore);
        tv_update = (TextView) findViewById(R.id.tv_update);
        tv_sop = (TextView) findViewById(R.id.tv_sop);
        rl_update = (RelativeLayout) findViewById(R.id.rl_update);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        tv_suggestion = (TextView) findViewById(R.id.tv_suggestion);
        tv_messagefree = (TextView) findViewById(R.id.tv_messagefree);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_cache = (TextView) findViewById(R.id.tv_cache);
        ll_clearcache = (LinearLayout) findViewById(R.id.ll_clearcache);
        btn_loginout = (Button) findViewById(R.id.btn_loginout);
    }

    private void initData() {
        tv_title.setText("设置");
        uid=MyApplication.getuId();
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        try {
            tv_cache.setText(DataCleanManager.getTotalCacheSize(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLiserner() {
        iv_turnback.setOnClickListener(this);
        tv_modifypwd.setOnClickListener(this);
        tv_modifyphone.setOnClickListener(this);
        tv_aboutus.setOnClickListener(this);
        tv_instruction.setOnClickListener(this);
        tv_applystore.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        tv_suggestion.setOnClickListener(this);
        tv_messagefree.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        ll_clearcache.setOnClickListener(this);
        btn_loginout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_modifypwd:
                MyApplication.openActivity(context, ModifyPwdActivity.class);
                break;
            case R.id.tv_modifyphone:
                MyApplication.openActivity(context, ModifyPhoneActivity.class);
                break;
            case R.id.tv_aboutus:
                Intent intent = new Intent(context, MyWebViewActivity.class);
                intent.putExtra("code",0);
                startActivity(intent);
                break;
            case R.id.tv_instruction:
                Intent intent1 = new Intent(context, MyWebViewActivity.class);
                intent1.putExtra("code",1);
                startActivity(intent1);
                break;
            case R.id.tv_applystore:
                ToastUtil.showToast(context, "请下载商家端开店");
                break;
            case R.id.tv_update:
                Intent intent2 = new Intent(context, MyWebViewActivity.class);
                intent2.putExtra("code",2);
                startActivity(intent2);
                break;
            case R.id.tv_suggestion:
                MyApplication.openActivity(context, SuggestionActivity.class);
                break;
            case R.id.tv_messagefree:
//                阿里即时通讯
                break;
            case R.id.tv_share:
                Software();
                break;
            case R.id.ll_clearcache:
                if ("0.0KB".equals(StringUtil.tvTostr(tv_cache))) {
                    ToastUtil.showToast(context, "你的缓存为0.0KB，无需清理");
                } else {
                    DataCleanManager.clearAllCache(context);
                    ToastUtil.showToast(context, "缓存清理完成");
                    try {
                        tv_cache.setText(DataCleanManager.getTotalCacheSize(context));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_loginout:
                SharedPreferencesUtil.putSharePre(context, "uid", "");//用户ID
                SharedPreferencesUtil.putSharePre(context, "nickName", "");//用户昵称
                SharedPreferencesUtil.putSharePre(context, "phoneNum", "");//手机号码
                SharedPreferencesUtil.putSharePre(context, "isLogin", false);//登录状态
                ToastUtil.showToast(context, "已安全注销账号");
                MyApplication.openActivity(context, LoginActivity.class);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtil.d("plat", "platform" + platform);

            ToastUtil.showToast(context, platform + " 分享成功啦");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast(context, platform + " 分享失败啦");
            if (t != null) {
                LogUtil.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast(context, platform + " 分享取消了");
        }
    };
    /**
     * 软件分享
     */
    private void Software() {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "Software");
        params.put("uid", uid);
        params.put("token", token);*/
        String json="{\"cmd\":\"Software\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
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
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            if ("1".equals(result)) {
                                ToastUtil.showToast(context, resultNote);
                                return;
                            }
                            String shareintroduce = object.getString("shareintroduce");
                            new ShareAction(SettingsActivity.this)
                                    .withText("hello")
                                    .withTargetUrl(shareintroduce)
                                    .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                    .setCallback(umShareListener)
                                    .open();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }



}
