package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.MyReleaseAdapter;
import com.lixin.carclassstore.adapter.UsedCarListAdapter;
import com.lixin.carclassstore.bean.MyReleaseBean;
import com.lixin.carclassstore.bean.UsedCarList;
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
 * Create time on  2017/4/26
 * My mailbox is 1403241630@qq.com
 */

public class UsedCarListActivity extends BaseActivity {
    private PullToRefreshListView mUsedCarList;
    private UsedCarListAdapter mAdapter;
    private String carVersionId;
    private List<UsedCarList.carModelList> usedCarList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_release);
        hideBack(false);
        setTitleText("二手车");
        Intent intent = getIntent();
        carVersionId = intent.getStringExtra("carVersionId");
        initView();
        getdata();
    }

    private void initView() {
        mUsedCarList = (PullToRefreshListView) findViewById(R.id.list_my_release);
        mUsedCarList.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new UsedCarListAdapter(this);
        mUsedCarList.setAdapter(mAdapter);
        mUsedCarList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                usedCarList.clear();
                getdata();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getdata();
            }
        });
        mUsedCarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UsedCarListActivity.this,UsedCarDetailsActivity.class);
                intent.putExtra("carId",usedCarList.get(position).getCarid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json = "{\"cmd\":\"getOldCarModelInfo\",\"carVersionId\":\"" + carVersionId + "\"}";
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
                        mUsedCarList.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        mUsedCarList.onRefreshComplete();
                        Log.i("UsedCarListActivity", "UsedCarListActivity: " + response.toString());
                        UsedCarList usedCar = gson.fromJson(response, UsedCarList.class);
                        if (usedCar.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, usedCar.getResultNote());
                            return;
                        }
                        List<UsedCarList.carModelList> qusetionslist = usedCar.carModelList;
                        usedCarList.addAll(qusetionslist);
                        mAdapter.setUsedCarBrandList(context,usedCarList);
                        mUsedCarList.setAdapter(mAdapter);
                        mUsedCarList.onRefreshComplete();
                    }
                });
    }
}