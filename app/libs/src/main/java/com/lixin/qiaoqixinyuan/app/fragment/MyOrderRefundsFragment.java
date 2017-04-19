package com.lixin.qiaoqixinyuan.app.fragment;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.MyOrderRefundsAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseFragment;
import com.lixin.qiaoqixinyuan.app.bean.MyorderBean;
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

import static android.content.Context.TELEPHONY_SERVICE;
import static com.lixin.qiaoqixinyuan.R.id.prlv_offlinepay;

public class MyOrderRefundsFragment extends BaseFragment {

    private PullToRefreshListView prlv_refunds;
    private MyOrderRefundsAdapter adapter;
    private String uid;
    private int nowPage = 1;
    private List<MyorderBean.OrderBean> ordersList=new ArrayList<>();
    private String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(context, R.layout.fragment_myorder_refunds, null);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        uid= SharedPreferencesUtil.getSharePreStr(context,"uid");
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        prlv_refunds = (PullToRefreshListView) view.findViewById(R.id.prlv_refunds);
        prlv_refunds.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initData() {
        adapter = new MyOrderRefundsAdapter(context,ordersList,dialog);
        prlv_refunds.setAdapter(adapter);
        myselfdata();
    }

    private void initListener() {
        prlv_refunds.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                ordersList.clear();
                myselfdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                myselfdata();
            }
        });
        prlv_refunds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    /**
     * 个人订单退款
     */
    private void myselfdata() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "myselfdata");
        /*params.put("uid", uid);
        params.put("ordertype", "3");
        params.put("nowPage", String.valueOf(nowPage));*/
        String json="{\"cmd\":\"myorder\",\"uid\":\"" + uid + "\",\"ordertype\":\"" +3 + "\"" +
                ",\"nowPage\":\"" + nowPage + "\",\"token\":\"" + token +"\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                        prlv_refunds.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyorderBean bean = gson.fromJson(response, MyorderBean.class);
                        dialog.dismiss();
                        prlv_refunds.onRefreshComplete();
                        if (bean.result.equals("1")) {
                            ToastUtil.showToast(context, bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        ordersList.addAll(bean.ordersList);
                        adapter.ordersList=ordersList;
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
