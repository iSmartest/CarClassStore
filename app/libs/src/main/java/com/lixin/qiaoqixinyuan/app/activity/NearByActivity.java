package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.NearByAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.NearByBean;
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

import okhttp3.Call;

public class NearByActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private PullToRefreshListView prlv_nearby;
    private NearByAdapter adapter;
    private String lat;
    private String lon;
    private String uid;
    private String token;
    private int nowPage=1;
    private List<NearByBean.NearByUserBean> userlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        uid=SharedPreferencesUtil.getSharePreStr(context,"uid");
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView();
        initData();
        initLiserner();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        prlv_nearby = (PullToRefreshListView) findViewById(R.id.prlv_nearby);
        prlv_nearby.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initData() {
        tv_title.setText("附近的人");
        adapter = new NearByAdapter(context,userlist,dialog);
        prlv_nearby.setAdapter(adapter);
        mannear();
    }

    private void initLiserner() {
        iv_turnback.setOnClickListener(this);
        prlv_nearby.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage=1;
                userlist.clear();
                mannear();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                mannear();
            }
        });
        prlv_nearby.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
     * 附近的人
     */
    private void mannear() {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "mannear");
        params.put("lat", lat);
        params.put("lon", lon);
        params.put("uid", uid);
        params.put("nowPage",nowPage+"");*/
        String json="{\"cmd\":\"mannear\",\"uid\":\"" + uid + "\",\"lat\":\"" + lat + "\"," +
                "\"lon\":\"" + lon + "\"," +
                "\"nowPage\":\"" + nowPage + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        prlv_nearby.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        NearByBean bean = gson.fromJson(response, NearByBean.class);
                        prlv_nearby.onRefreshComplete();
                        dialog.dismiss();
                        if (bean.result.equals("1")){
                            ToastUtil.showToast(context,bean.resultNote);
                            adapter.notifyDataSetChanged();
                            return;
                        }if (Integer.parseInt(bean.totalPage)<nowPage){
                            ToastUtil.showToast(context,"没有更多了");
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        userlist.addAll(bean.userlist);
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
