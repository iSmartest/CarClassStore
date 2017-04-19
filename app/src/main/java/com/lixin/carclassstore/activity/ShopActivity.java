package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.CollectionAdapter;
import com.lixin.carclassstore.adapter.ShopAdapter;
import com.lixin.carclassstore.adapter.StoreAdapter;
import com.lixin.carclassstore.bean.JavaBean;
import com.lixin.carclassstore.bean.ShopBean;
import com.lixin.carclassstore.bean.ShoppingCollectionFootBean;
import com.lixin.carclassstore.bean.StoreBean;
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
 * Create time on  2017/3/25
 * My mailbox is 1403241630@qq.com
 */

public class ShopActivity extends BaseActivity implements View.OnClickListener{
    private TextView textComprehensive,textSalesVolume,textPrice;
    private PullToRefreshListView list_store;
    private List<ShopBean.commoditys> mList = new ArrayList<>();
    private ShopAdapter storeAdapter;
    private int nowPage = 1;
    private String meunid;
    private int meunType;
    private int meunSort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        setTitleText("门店");
        hideBack(false);
        Intent intent = getIntent();
        meunid = intent.getStringExtra("serveTypeId");
        Log.i("meunid", "onCreate: " + meunid);
        meunType = 0;
        meunSort = 0;
        initView();
    }
    private void initView() {
        textComprehensive = (TextView) findViewById(R.id.text_comprehensive);
        textComprehensive.setOnClickListener(this);
        textSalesVolume = (TextView) findViewById(R.id.text_sales_volume);
        textSalesVolume.setOnClickListener(this);
        textPrice = (TextView) findViewById(R.id.text_price);
        textPrice.setOnClickListener(this);
        list_store = (PullToRefreshListView) findViewById(R.id.list_store);
        list_store.setMode(PullToRefreshBase.Mode.BOTH);
        storeAdapter = new ShopAdapter(ShopActivity.this);
        list_store.setAdapter(storeAdapter);
        list_store.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        list_store.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ShopActivity.this,StoreDetailsActivity.class));
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_comprehensive:

                break;
            case R.id.text_sales_volume:

                break;
            case R.id.text_price:

                break;

        }
    }
    //请求参数
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getCommodityListInfo\",\"nowPage\":\"" + nowPage +"\",\"meunid\":\""
        + meunid + "\",\"meunType\":\"" + meunType + "\",\"meunSort\":\"" + meunSort +"\"}";
        params.put("json", json);
        Log.i("MyCollectionFootActivity", "onResponse: " + json.toString());
        dialog1.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageShort(context, e.getMessage());
                dialog1.dismiss();
                list_store.onRefreshComplete();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("MyCollectionFootActivity", "onResponse: " + response.toString());
                Gson gson = new Gson();
                dialog1.dismiss();
                list_store.onRefreshComplete();
                ShopBean shopBean = gson.fromJson(response, ShopBean.class);
                if (shopBean.result.equals("1")) {
                    ToastUtils.showMessageShort(context, shopBean.resultNote);
                    return;
                }
                if (Integer.parseInt(shopBean.totalPage) < nowPage) {
                    ToastUtils.showMessageShort(context, "没有更多了");
                    return;
                }
                List<ShopBean.commoditys> commodityslist = shopBean.commoditys;
                Log.i("commodityslist", "commodityslist: " + commodityslist.get(0).getCommodityTitle());
                mList.addAll(commodityslist);
                storeAdapter.setShopBeanList(ShopActivity.this,mList);
                list_store.setAdapter(storeAdapter);
                list_store.onRefreshComplete();
            }
        });
    }
}
