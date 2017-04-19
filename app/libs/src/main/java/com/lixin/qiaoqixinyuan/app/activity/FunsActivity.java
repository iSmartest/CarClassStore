package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.FunsAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyfanseListBean;
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

public class FunsActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView prlv_funs;
    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private int nowPage = 1;
    private String uid;
    private List<MyfanseListBean.MyfanseBean> myfanselist = new ArrayList<>();
    private FunsAdapter adapter;
 private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funs);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView();
        initData();
        initLiserner();
    }

    private void initView() {
        prlv_funs = (PullToRefreshListView) findViewById(R.id.prlv_funs);
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        prlv_funs.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initData() {
        et_basesearch.setHint("搜索昵称");
        adapter = new FunsAdapter(context,myfanselist,dialog);
        prlv_funs.setAdapter(adapter);
        uid = MyApplication.getuId();
        myfanse();
    }

    private void initLiserner() {
        iv_basesearch_back.setOnClickListener(this);
        prlv_funs.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                myfanselist.clear();
                myfanse();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                myfanse();
            }
        });
        prlv_funs.onRefreshComplete();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_basesearch_back:
                finish();
                break;
        }
    }

    private void submit() {
        String basesearch = et_basesearch.getText().toString().trim();
        if (TextUtils.isEmpty(basesearch)) {
            ToastUtil.showToast(context, "请输入昵称");
            return;
        }
    }

    /**
     * 我关注的人
     */
    private void myfanse() {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "myfanse");
        params.put("uid", uid);
        params.put("nowPage", String.valueOf(nowPage));*/
        String json="{\"cmd\":\"myfanse\",\"uid\":\"" + uid + "\"," +
                "\"nowPage\":\"" + nowPage +"\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        prlv_funs.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyfanseListBean bean = gson.fromJson(response, MyfanseListBean.class);
                        prlv_funs.onRefreshComplete();
                        if (bean.result.equals("1")) {
                            ToastUtil.showToast(context, bean.resultNote);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            return;
                        }
                        if (Integer.getInteger(bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            return;
                        }
                        myfanselist.addAll(bean.myfanselist);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

    }
}
