package com.lixin.carclassstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.MyAllOrderAdapter;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/13
 * My mailbox is 1403241630@qq.com
 * 待支付
 */

public class WaitPaymentFragment extends BaseFragment{
    private View view;
    private MyAllOrderAdapter mAdapter;
    private int nowPage = 1;
    private String uid ="123";
    private String orderState = "1";
    private ListView order_lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_content,null);
        order_lv = (ListView)view.findViewById(R.id.order_lv);
        getdata();
        return view;
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

                        List<JSONObject> datalist = new ArrayList<>();
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray ordersArray = jsonObject.getJSONArray("orders");
                            Log.i("ordersArray", "onResponse: " + ordersArray.toString());
                            mAdapter = new MyAllOrderAdapter(context);
                            mAdapter.setCollection(ordersArray);
                            order_lv.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
