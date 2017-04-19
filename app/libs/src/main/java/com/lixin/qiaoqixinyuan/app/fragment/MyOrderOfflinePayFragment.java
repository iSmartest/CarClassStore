package com.lixin.qiaoqixinyuan.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.Dingdan_xiangqing_Activity;
import com.lixin.qiaoqixinyuan.app.adapter.MyOrderOfflinePayAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseFragment;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyorderBean;
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

public class MyOrderOfflinePayFragment extends BaseFragment {

    private PullToRefreshListView prlv_offlinepay;
    private MyOrderOfflinePayAdapter adapter;
    private String uid;
    private int nowPage = 1;
    private List<MyorderBean.OrderBean> ordersList=new ArrayList<>();
    private String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(context, R.layout.fragment_myorder_offlinepay, null);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView(view);
        initData();
        initListener();
        myselfdata();
        return view;
    }

    private void initView(View view) {
        prlv_offlinepay = (PullToRefreshListView) view.findViewById(R.id.prlv_offlinepay);
    }

    private void initData() {
        uid = MyApplication.getuId();
        ordersList.clear();
        adapter = new MyOrderOfflinePayAdapter(context,ordersList,dialog);
        prlv_offlinepay.setMode(PullToRefreshBase.Mode.BOTH);
        prlv_offlinepay.setAdapter(adapter);
        myselfdata();
    }

    private void initListener() {
        prlv_offlinepay.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        prlv_offlinepay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, Dingdan_xiangqing_Activity.class);
                Bundle bundle = new Bundle();
                MyorderBean.OrderBean value = ordersList.get(i-1);
                bundle.putSerializable("orders", value);
                bundle.putString("ordertype","0");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 个人订单见面付款
     */
    private void myselfdata() {
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "myorder");
        params.put("uid", uid);
        params.put("ordertype", "0");
        params.put("nowPage", String.valueOf(nowPage));*/
        String json="{\"cmd\":\"myorder\",\"uid\":\"" + uid + "\",\"ordertype\":\"" +0 + "\"" +
                ",\"nowPage\":\"" + nowPage + "\",\"token\":\"" + token +"\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog.dismiss();
                        ToastUtil.showToast(context, e.getMessage());
                        adapter.notifyDataSetChanged();
                        prlv_offlinepay.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        prlv_offlinepay.onRefreshComplete();
                        Gson gson = new Gson();
                        MyorderBean bean = gson.fromJson(response, MyorderBean.class);
                        if (bean.result.equals("1")) {
                            ToastUtil.showToast(context, bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        ordersList.addAll(bean.ordersList);
                        adapter = new MyOrderOfflinePayAdapter(context,ordersList,dialog);
                        prlv_offlinepay.setAdapter(adapter);
                    }
                });

    }
}
