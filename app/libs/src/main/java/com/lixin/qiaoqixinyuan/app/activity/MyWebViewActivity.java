package com.lixin.qiaoqixinyuan.app.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class MyWebViewActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private WebView wv_detail;
    private String uid;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywebview);
        initView();
        initListener();
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        int code = getIntent().getIntExtra("code", -1);
        if (code==0) {
            tv_title.setText("关于我们");
            aboutMe();
        }else if (code==1){
            tv_title.setText("使用帮助");
            detailIntroduce();
        }else if (code==2){
            tv_title.setText("检查更新");
            update();
        }
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        wv_detail = (WebView) findViewById(R.id.wv_detail);
    }

    private void initData(String url) {
        wv_detail.loadUrl(url);
        wv_detail.getSettings().setJavaScriptEnabled(true);
        wv_detail.getSettings().setLoadWithOverviewMode(true);
        wv_detail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);//返回
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback://返回
                if (wv_detail.canGoBack()) {
                    wv_detail.goBack();
                } else {
                    finish();
                }
                break;
        }
    }
    /**
     * 关于我们
     */
    private void aboutMe() {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "aboutMe");
        params.put("uid", uid);
        params.put("token", token);*/
        String json="{\"cmd\":\"aboutMe\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
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
                            initData(shareintroduce);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    /**
     * 使用条款
     */
    private void detailIntroduce() {
        Map<String, String> params = new HashMap<>();
    /*    params.put("cmd", "detailIntroduce");
        params.put("uid", uid);
        params.put("token", token);*/
        String json="{\"cmd\":\"detailIntroduce\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
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
                            String detailIntroduce = object.getString("detailIntroduce");
                            initData(detailIntroduce);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    /**
     * 检查更新
     */
    private void update() {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "update");
        params.put("uid", uid);
        params.put("token", token);*/
        String json="{\"cmd\":\"update\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
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
                              initData(shareintroduce);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
