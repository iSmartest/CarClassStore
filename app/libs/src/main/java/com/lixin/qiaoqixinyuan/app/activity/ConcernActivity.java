package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.ConcernAdapter;
import com.lixin.qiaoqixinyuan.app.adapter.FunsAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyfanseListBean;
import com.lixin.qiaoqixinyuan.app.bean.MyfocusBean;
import com.lixin.qiaoqixinyuan.app.bean.MyfocuslistBean;
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

import static android.R.attr.tag;

public class ConcernActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView prlv_concern;
    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ConcernAdapter adapter;
    private int nowPage = 1;
    private String uid;
    private String token;
    private List<MyfocusBean> myfocuslist = new ArrayList<>();
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concern);
        code = getIntent().getExtras().getString("code");
        initView();
        initData();
        initLiserner();
    }

    private void initView() {
        prlv_concern = (PullToRefreshListView) findViewById(R.id.prlv_concern);
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        prlv_concern.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initData() {
        et_basesearch.setHint("搜索昵称");
        adapter = new ConcernAdapter(context,myfocuslist,dialog);
        prlv_concern.setAdapter(adapter);
        uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        if (code.equals("0")) {
            myfocus();
        }else if (code.equals("1")){
            myfancs();
        }
    }

    private void initLiserner() {
        iv_basesearch_back.setOnClickListener(this);
        prlv_concern.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                myfocuslist.clear();
                if (code.equals("0")) {
                    myfocus();
                }else if (code.equals("1")){
                    myfancs();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                if (code.equals("0")) {
                    myfocus();
                }else if (code.equals("1")){
                    myfancs();
                }
            }
        });
        prlv_concern.onRefreshComplete();
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
    private void myfocus() {
        Map<String, String> params = new HashMap<>();
       /* cmd:"myfocus"
      uid:"5"    //用户uid
     nowPage:"1" //当前页
  token:    [JPUSHService registrationID]   //推送token*/
        String json="{\"cmd\":\"myfocus\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"" +
                ",\"nowPage\":\"" + nowPage +"\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        prlv_concern.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyfocuslistBean bean = gson.fromJson(response, MyfocuslistBean.class);
                        prlv_concern.onRefreshComplete();
                        if (bean.result.equals("1")) {
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
                        myfocuslist.addAll(bean.myfocuslist);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

    }
    private void myfancs(){
      /*  cmd:"myfanse"
        uid:"5"    //用户uid
        nowPage:"1" //当前页
        token:    [JPUSHService registrationID]   //推送token*/
        Map<String, String> params = new HashMap<>();
        String json="{\"cmd\":\"myfanse\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"" +
                ",\"nowPage\":\"" + nowPage +"\"}";
        params.put("json", json);
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        prlv_concern.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyfanseListBean myfanseListBean = gson.fromJson(response, MyfanseListBean.class);
                        prlv_concern.onRefreshComplete();
                        if (myfanseListBean.result.equals("1")) {
                            ToastUtil.showToast(context, myfanseListBean.resultNote);
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        if (Integer.parseInt(myfanseListBean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        List<MyfanseListBean.MyfanseBean> myfanselist = myfanseListBean.myfanselist;
                        if (myfanselist==null){
                            ToastUtil.showToast(context,"还，诶有粉丝");
                            return;
                        }
                        for (int i = 0; i < myfanselist.size() ; i++) {
                            MyfocusBean myfocusBean = new MyfocusBean();
                            MyfanseListBean.MyfanseBean myfanseBean = myfanselist.get(i);
                            myfocusBean.focususerid= myfanseBean.myfanseage;
                            myfocusBean.focususerage= myfanseBean.myfanseage;
                            myfocusBean.focususericon= myfanseBean.myfanseage;
                            myfocusBean.focususernick= myfanseBean.myfanseage;
                            myfocusBean.focususersex= myfanseBean.myfanseage;
                            myfocusBean.focususersignature= myfanseBean.myfanseage;
                            myfocuslist.add(myfocusBean);
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
