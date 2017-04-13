package com.lixin.carclassstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.MyAllOrderAdapter;
import com.lixin.carclassstore.bean.MyOrderBean;
import com.lixin.carclassstore.bean.ReplyBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/13
 * My mailbox is 1403241630@qq.com
 */

public class MyAllOrderFragment extends BaseFragment{
    private PullToRefreshListView list_order_content;
    private View view;
    private MyAllOrderAdapter mAdapter;
    private int nowPage = 1;
    private String uid ="123";
    private String orderState = "0";
    private List<MyOrderBean.orders> ordersList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_content,null);
        initView();
        return view;

    }

    private void initView() {
        list_order_content = (PullToRefreshListView) view.findViewById(R.id.list_order_content);
        list_order_content.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new MyAllOrderAdapter();
        list_order_content.setAdapter(mAdapter);
        list_order_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                ordersList.clear();
                getdata();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getdata();
            }
        });
    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"getOrderInfo\",\"orderState\":\"" + orderState + "\"" + ",\"uid\":\"" + uid + "\"" + ",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showMessageShort(context, e.getMessage());
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("111", "onResponse: " + response.toString());
                        Gson gson = new Gson();
                        dialog.dismiss();
                        ReplyBean rplyBean = gson.fromJson(response, ReplyBean.class);
                        if (rplyBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, rplyBean.getResultNote());
                            return;
                        }else {
                            ToastUtils.showMessageShort(context, "回复成功！");

//                            mList.clear();
//                            commentsList();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
