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
import com.lixin.qiaoqixinyuan.app.adapter.BalanceMingXiAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.IncomedetailsListBean;
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

import static android.R.string.ok;

public class BalanceMingXiActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_turnback;
    private TextView tv_title;
    private PullToRefreshListView prlv_casher;
    private BalanceMingXiAdapter adapter;
    private String uid;
    private String token;
    private List<IncomedetailsListBean.incomedetailsBean> incomedetailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_mingxi);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        prlv_casher = (PullToRefreshListView) findViewById(R.id.prlv_casher);
        prlv_casher.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    private void initData() {
        tv_title.setText("提现明细");
        uid = MyApplication.getuId();
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        adapter = new BalanceMingXiAdapter(context, incomedetailsList);
        prlv_casher.setAdapter(adapter);
        incomedetails(uid, token);
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);
        prlv_casher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
     * 收入明细列表
     *
     * @param uid
     * @param token
     */
    private void incomedetails(String uid, String token) {
        Map<String, String> params = new HashMap<>();
      /*  params.put("cmd", "incomedetails");
        params.put("phone", uid);
        params.put("token", token);*/
        String json="{\"cmd\":\"incomedetails\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
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
                        Gson gson = new Gson();
                        IncomedetailsListBean bean = gson.fromJson(response, IncomedetailsListBean.class);
                        if ("1".equals(bean.result)) {
                            ToastUtil.showToast(context, bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        incomedetailsList = bean.incomedetailsList;
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

    }
}