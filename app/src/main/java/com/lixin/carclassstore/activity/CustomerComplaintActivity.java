package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.CustomerComplaintAdapter;
import com.lixin.carclassstore.bean.CustomerComplaint;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
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
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */

public class CustomerComplaintActivity extends BaseActivity{
    public PullToRefreshListView list_complaint;
    private List<CustomerComplaint.complains> mList = new ArrayList<>();
    private CustomerComplaintAdapter mAdapter;
    private int nowPage = 1;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_complaint);
        uid = (String) SPUtils.get(CustomerComplaintActivity.this,"uid","");
        hideBack(false);
        setTitleText("客户投诉");
        initView();
    }

    private void initView() {
        list_complaint = (PullToRefreshListView) findViewById(R.id.list_complaint);
        mAdapter = new CustomerComplaintAdapter(this);
        list_complaint.setAdapter(mAdapter);
        list_complaint.setMode(PullToRefreshBase.Mode.BOTH);
        list_complaint.setAdapter(mAdapter);
        list_complaint.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                mList.clear();
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
        final String json="{\"cmd\":\"userComplains\",\"uid\":\"" + uid +"\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showMessageShort(context, e.getMessage());
                        dialog1.dismiss();
                        list_complaint.onRefreshComplete();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("CustomerComplaintActivity", "onResponse1: " + response.toString());
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        CustomerComplaint customerComplaint = gson.fromJson(response, CustomerComplaint.class);
                        if (customerComplaint.result.equals("1")) {
                            ToastUtils.showMessageShort(context, customerComplaint.resultNote);
                            return;
                        }
                        List<CustomerComplaint.complains> complaintsList = customerComplaint.complains;
                        Log.i("CustomerComplaintActivity", "onResponse2: " + complaintsList.toString());
                        mList.addAll(complaintsList);
                        mAdapter.setCustomerComplaint(mList);
                        list_complaint.setAdapter(mAdapter);
                        list_complaint.onRefreshComplete();
                    }
                });
    }
}
