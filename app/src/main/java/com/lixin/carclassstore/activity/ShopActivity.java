package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.lixin.carclassstore.view.ProgressDialog;
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

public class ShopActivity extends Activity implements View.OnClickListener{
    private TextView textComprehensive,textSalesVolume,textPrice;
    private PullToRefreshListView list_store;
    private List<ShopBean.commoditys> mList = new ArrayList<>();
    private ShopAdapter storeAdapter;
    private int nowPage = 1;
    private String meunid;
    private int meunType;
    private int meunSort;
    private ImageView mBack,mSearch;
    private EditText ediShopSerchKey;
    protected Context context;
    protected Dialog dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        context = this;
        dialog1 = ProgressDialog.createLoadingDialog(context, "加载中.....");
        Intent intent = getIntent();
        meunid = intent.getStringExtra("serveTypeId");
        Log.i("meunid", "onCreate: " + meunid);
        meunType = 0;
        meunSort = 0;
        initView();
        getdata();
    }
    private void initView() {
        mBack = (ImageView) findViewById(R.id.iv_back);
        mBack.setOnClickListener(this);
        mSearch = (ImageView) findViewById(R.id.im_search);
        mSearch.setOnClickListener(this);
        ediShopSerchKey = (EditText) findViewById(R.id.a_shop_edt_search);
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
                Intent intent = new Intent(ShopActivity.this,ShopDetailsActivity.class);
                intent.putExtra("commodityid",mList.get(position-1).commodityid);
                intent.putExtra("meunid",mList.get(position-1).getCommodityTitle());
                intent.putExtra("commodityShopid",mList.get(position-1).getCommodityShopid());
                intent.putExtra("commodityBrandid",mList.get(position-1).commodityBrandid);
                intent.putExtra("commodityType",mList.get(position-1).commodityType);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_comprehensive:
                meunid = "";
                getdata();
                textComprehensive.setTextColor(getResources().getColor(R.color.btn_login_color));
                textSalesVolume.setTextColor(getResources().getColor(R.color.black));
                textPrice.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.text_sales_volume:
                textComprehensive.setTextColor(getResources().getColor(R.color.black));
                textSalesVolume.setTextColor(getResources().getColor(R.color.btn_login_color));
                textPrice.setTextColor(getResources().getColor(R.color.black));
                if (meunType == 0){
                    if (meunSort == 0){
                        meunSort =1;
                        getdata();
                    }else {
                        meunSort = 0;
                        getdata();
                    }
                }else {
                    meunType = 0;
                    if (meunSort == 0){
                        meunSort =1;
                        getdata();
                    }else {
                        meunSort = 0;
                        getdata();
                    }
                }
                break;
            case R.id.text_price:
                textComprehensive.setTextColor(getResources().getColor(R.color.black));
                textSalesVolume.setTextColor(getResources().getColor(R.color.black));
                textPrice.setTextColor(getResources().getColor(R.color.btn_login_color));
                if (meunType == 1){
                    if (meunSort == 0){
                        meunSort =1;
                        getdata();
                    }else {
                        meunSort = 0;
                        getdata();
                    }
                }else {
                    meunType = 1;
                    if (meunSort == 0){
                        meunSort =1;
                        getdata();
                    }else {
                        meunSort = 0;
                        getdata();
                    }
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.im_search:
                String searchKeys = ediShopSerchKey.getText().toString().trim();
                getSearchResult(searchKeys);
                break;
            default:
                break;
        }
    }

    private void getSearchResult(String searchKeys) {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getSerachCommodityListInfo\",\"nowPage\":\"" + nowPage +"\",\"searchKey\":\""
                + searchKeys + "\"}";
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
