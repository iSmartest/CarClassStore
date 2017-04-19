package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.MyBlacklistAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyBlackListBean;
import com.lixin.qiaoqixinyuan.app.bean.UserLoginBean;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class MyBlacklistActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private PullToRefreshListView prlv_myblacklist;
    private MyBlacklistAdapter adapter;
    private String uid;
    private int nowPage = 1;
    private String token;
    private List<MyBlackListBean.MyBlackBean> myblacklist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myblacklist);
        initView();
        initData();
        initLiserner();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        prlv_myblacklist = (PullToRefreshListView) findViewById(R.id.prlv_myblacklist);
        prlv_myblacklist.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initData() {
        tv_title.setText("我的黑名单");
        uid=MyApplication.getuId();
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        adapter = new MyBlacklistAdapter(context, myblacklist,dialog);
        prlv_myblacklist.setAdapter(adapter);
    }

    private void initLiserner() {
        iv_turnback.setOnClickListener(this);
        prlv_myblacklist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                myblacklist.clear();
                myblacklist();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                myblacklist();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
        }
    }

    /**
     * 我的黑名单
     */
    private void myblacklist() {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "myblacklist");
        params.put("uid", uid);
        params.put("nowPage", String.valueOf(nowPage));
        params.put("token", token);*/
        String json="{\"cmd\":\"myblacklist\",\"uid\":\"" + uid + "\",\"nowPage\":\"" + nowPage + "\"," +
                "\"token\":\"" + token + "\"}";
        params.put("json",json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        prlv_myblacklist.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyBlackListBean bean = gson.fromJson(response, MyBlackListBean.class);
                        prlv_myblacklist.onRefreshComplete();
                        if ("1".equals(bean.result)) {
                            ToastUtil.showToast(context, bean.resultNote);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            return;
                        }
                        if (Integer.parseInt(bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            return;
                        }
                        myblacklist.addAll(bean.myblacklist);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

    }
}
