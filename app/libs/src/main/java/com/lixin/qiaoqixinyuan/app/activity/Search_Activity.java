package com.lixin.qiaoqixinyuan.app.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Shangpin_list_Adapter;
import com.lixin.qiaoqixinyuan.app.adapter.Shangpin_shangjia_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_shangpin_search_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Shangjialiebiao_Bean;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.MyListview;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class Search_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ImageView iv_basesearch_shuru;
    private MyListview list_search_shangjia;
    private PullToRefreshListView list_search_shangpin;
    private LinearLayout activity_search_;
    private int nowPage=1;
    List<Shangjia_shangpin_search_Bean.Goodslist> goods=new ArrayList<>();
    private List<Shangjialiebiao_Bean.Merchantslist> merchantslist;
    private Shangpin_shangjia_Adapter shangpin_shangjia_adapter;
    private Shangpin_list_Adapter shangpin_list_adapter;
    private String shangjiaid;
    private String lat;
    private String lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        shangjiaid = getIntent().getStringExtra("shangjiaId");
        lat= SharedPreferencesUtil.getSharePreStr(context,"lat");
        lon= SharedPreferencesUtil.getSharePreStr(context,"lon");
        initView();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            //隐藏软键盘
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (shangjiaid==null) {
                getsearchdata();
            }else {
                getgoodsdata();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void initView() {
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        iv_basesearch_back.setOnClickListener(this);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        iv_basesearch_shuru = (ImageView) findViewById(R.id.iv_basesearch_shuru);
        list_search_shangjia = (MyListview) findViewById(R.id.list_search_shangjia);
        list_search_shangpin = (PullToRefreshListView) findViewById(R.id.list_search_shangpin);
        list_search_shangpin.setMode(PullToRefreshBase.Mode.BOTH);
        activity_search_ = (LinearLayout) findViewById(R.id.activity_search_);
        shangpin_shangjia_adapter = new Shangpin_shangjia_Adapter();
        list_search_shangjia.setAdapter(shangpin_shangjia_adapter);
        shangpin_list_adapter = new Shangpin_list_Adapter();
        list_search_shangpin.setAdapter(shangpin_list_adapter);
        list_search_shangpin.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage=1;
                goods.clear();
                if (shangjiaid==null) {
                    getsearchdata();
                }else {
                    getgoodsdata();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                if (shangjiaid==null) {
                    getsearchdata();
                }else {
                    getgoodsdata();
                }
            }
        });
        list_search_shangjia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(context, Shangjia_xiangqing_Activity.class));
            }
        });
    }
 /*   private void getsearch(){
       *//*   cmd:”searchshangpinlist”
    shangjiaId: “1”       //商家id
    searchtext:“蛋糕”         //搜索字段
    lat: 34.345345                 // 用户的纬度
    lon：120.23432                // 用户的经度
    nowPage:"1"     //当前页
 }*//*
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "searchshangpinlist");
        params.put("shangjiaid", shangjiaId);
        params.put("searchtext", "蛋糕");
        params.put("lat", "1");
        params.put("lon", "1");
        params.put("nowPage", nowPage+"");
        String json="{\"cmd\":\"searchshangpinlist\",\"lat\":\"" + lat + "\",\"lon\":\""
                + lon + "\",\"shangjiaid\":\"" + shangjiaId + "\",\"searchtext\":\"" + et_basesearch.getText().toString() + "\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Search_Activity.this, e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Shangjia_shangpin_search_Bean shangjia_shangpin_search_bean = gson.fromJson(response, Shangjia_shangpin_search_Bean.class);
                        if (shangjia_shangpin_search_bean.result.equals("1")){
                            ToastUtil.showToast(Search_Activity.this,shangjia_shangpin_search_bean.resultNote);
                            return;
                        }if (Integer.getInteger(shangjia_shangpin_search_bean.totalPage)<nowPage){
                            ToastUtil.showToast(Search_Activity.this,"没有更多了");
                            return;
                        }
                        List<Shangjia_shangpin_search_Bean.Goodslist> goodslist = shangjia_shangpin_search_bean.goodslist;
                        goods.addAll(goodslist);
                    }
                });
    }*/
    private void getgoodsdata(){
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "searchshangpinlist");
        params.put("searchtext", "蛋糕");
        params.put("lat",nowPage+"");
        params.put("lon","34.345345");
        params.put("nowPage",nowPage+"");*/
        String json="{\"cmd\":\"searchshangpin\",\"lat\":\"" + lat + "\",\"lon\":\""
                + lon + "\",\"shangjiaid\":\"" + shangjiaid + "\",\"searchtext\":\"" + et_basesearch.getText().toString() + "\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Search_Activity.this, e.getLocalizedMessage());
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        Shangjia_shangpin_search_Bean shangjia_shangpin_search_bean = gson.fromJson(response, Shangjia_shangpin_search_Bean.class);
                        if (shangjia_shangpin_search_bean.result.equals("1")) {
                            ToastUtil.showToast(Search_Activity.this, shangjia_shangpin_search_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(shangjia_shangpin_search_bean.totalPage)< nowPage){
                            ToastUtil.showToast(Search_Activity.this,"没有更多了");
                            return;
                        }
                        merchantslist = shangjia_shangpin_search_bean.merchantslist;
                        shangpin_shangjia_adapter.setMerchantslist(merchantslist);
                        shangpin_shangjia_adapter.notifyDataSetChanged();
                        List<Shangjia_shangpin_search_Bean.Goodslist> goodslist = shangjia_shangpin_search_bean.goodslist;
                        goods.addAll(goodslist);
                        shangpin_list_adapter.setGoodslist(goods);
                        shangpin_list_adapter.notifyDataSetChanged();
                    }
                });
    }
    private void getsearchdata(){
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "searchshangpinlist");
        params.put("searchtext", "蛋糕");
        params.put("lat",nowPage+"");
        params.put("lon","34.345345");
        params.put("nowPage",nowPage+"");*/
        String json="{\"cmd\":\"searchshangpinlist\",\"lat\":\"" + lat + "\",\"lon\":\""
                + lon + "\",\"searchtext\":\"" + et_basesearch.getText().toString() + "\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Search_Activity.this, e.getLocalizedMessage());
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        Shangjia_shangpin_search_Bean shangjia_shangpin_search_bean = gson.fromJson(response, Shangjia_shangpin_search_Bean.class);
                        if (shangjia_shangpin_search_bean.result.equals("1")) {
                            ToastUtil.showToast(Search_Activity.this, shangjia_shangpin_search_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(shangjia_shangpin_search_bean.totalPage)< nowPage){
                            ToastUtil.showToast(Search_Activity.this,"没有更多了");
                            return;
                        }
                        merchantslist = shangjia_shangpin_search_bean.merchantslist;
                        shangpin_shangjia_adapter.setMerchantslist(merchantslist);
                        shangpin_shangjia_adapter.notifyDataSetChanged();
                        List<Shangjia_shangpin_search_Bean.Goodslist> goodslist = shangjia_shangpin_search_bean.goodslist;
                        goods.addAll(goodslist);
                        shangpin_list_adapter.setGoodslist(goods);
                        shangpin_list_adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case
            R.id.iv_basesearch_back:
                finish();
                break;
        }
    }
}
