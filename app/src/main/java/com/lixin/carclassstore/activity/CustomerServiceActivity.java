package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.CustomerServiceAdapter;
import com.lixin.carclassstore.bean.CustomerServiceBean;
import com.lixin.carclassstore.bean.ShoppingCollectionFootBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/3/25
 * My mailbox is 1403241630@qq.com
 * 客服
 */

public class CustomerServiceActivity extends BaseActivity{
    private ListView list_customer_service;
    private CustomerServiceAdapter mAdapter;
    private List<CustomerServiceBean.service> servicesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        setTitleText("客服");
        hideBack(false);
        initView();
        getdata();
    }

    private void initView() {
        list_customer_service = (ListView) findViewById(R.id.list_customer_service);
        mAdapter = new CustomerServiceAdapter(this);
        list_customer_service.setAdapter(mAdapter);
        list_customer_service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showMessageShort(context,servicesList.get(position).getServiceQQ());
            }
        });
    }
    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getServiceInfo\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageLong(context, "网络异常");
                dialog1.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("CustomerServiceActivity", "CustomerServiceActivity: " + response.toString());
                Gson gson = new Gson();
                dialog1.dismiss();
                CustomerServiceBean customerServiceBean = gson.fromJson(response, CustomerServiceBean.class);
                Log.i("CustomerServiceActivity", "CustomerServiceActivity: " + response.toString());
                if (customerServiceBean.getResult().equals("1")){
                    ToastUtils.showMessageLong(context,customerServiceBean.getResultNote());
                }
                List<CustomerServiceBean.service> service = customerServiceBean.service;
//                Log.i("CustomerServiceActivity", "CustomerServiceActivity: " + service.toString());
                servicesList.addAll(service);
                mAdapter.setCustomerService(context,servicesList);
                list_customer_service.setAdapter(mAdapter);
            }
        });
    }
}
