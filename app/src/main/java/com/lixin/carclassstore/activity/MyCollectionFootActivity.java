package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.CollectionAdapter;
import com.lixin.carclassstore.bean.ShoppingCollectionFootBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 * 我的收藏和足迹
 */

public class MyCollectionFootActivity extends BaseActivity
{
    private Button mClearAll;
    private CollectionAdapter mAdapter;
    private List<ShoppingCollectionFootBean.commoditys> mList = new ArrayList<>();
    private PullToRefreshListView list_collection;
    private String handleType;//"1"收藏"2"足迹
    private String uid ;
    private int nowPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        hideBack(false);
        Intent intent = getIntent();
        handleType = intent.getStringExtra("handleType");
        uid = (String) SPUtils.get(MyCollectionFootActivity.this,"uid","");
        if (handleType.equals("1"))
            setTitleText("我的收藏");
        else
            setTitleText("我的足迹");
        initView();
    }

    private void initView() {
        list_collection = (PullToRefreshListView)findViewById(R.id.list_collection);
        list_collection.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new CollectionAdapter(this);
        list_collection.setAdapter(mAdapter);
        list_collection.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        mClearAll = (Button) findViewById(R.id.btn_clear_all);
        mClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.clear();
                mAdapter.updateList(mList);
            }
        });
    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getQueryHandleInfo\",\"handleType\":\"" + handleType + "\",\"uid\":\"" + uid +"\",\"nowPage\":\"" + nowPage + "\"}";
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
                        list_collection.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        list_collection.onRefreshComplete();
                        Log.i("MyCollectionFootActivity", "onResponse: " + response.toString());
                        ShoppingCollectionFootBean shoppingCollectionFootBean = gson.fromJson(response, ShoppingCollectionFootBean.class);
                        if (shoppingCollectionFootBean.result.equals("1")) {
                            ToastUtils.showMessageShort(context, shoppingCollectionFootBean.resultNote);
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject("jjj");
                            if (shoppingCollectionFootBean.totalPage.equals("")) {
                                ToastUtils.showMessageShort(context, "空空如也");
                            } else {
                                if (Integer.parseInt(shoppingCollectionFootBean.totalPage) < nowPage) {
                                    ToastUtils.showMessageShort(context, "没有更多了");
                                    return;
                                }
                            }
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        List<ShoppingCollectionFootBean.commoditys> commodityslist = shoppingCollectionFootBean.commoditys;
                        mList.addAll(commodityslist);
                        mAdapter.setCollection(mList);
                        list_collection.setAdapter(mAdapter);
                        list_collection.onRefreshComplete();
                    }
                });
    }

}
