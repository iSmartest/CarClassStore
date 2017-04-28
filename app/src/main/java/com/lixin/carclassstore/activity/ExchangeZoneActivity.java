package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.ExchangeZoneAdapter;
import com.lixin.carclassstore.bean.ExchangeZone;
import com.lixin.carclassstore.bean.ShoppingCollectionFootBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/28
 * My mailbox is 1403241630@qq.com
 */

public class ExchangeZoneActivity extends BaseActivity{
    private PullToRefreshGridView grid_exchange_list;
    private int nowPage = 1;
    private List<ExchangeZone.commoditys> mList = new ArrayList<>();
    private ExchangeZoneAdapter mAdapter;
    private GridView mGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_zone);
        hideBack(false);
        setTitleText("兑换中心");
        initView();
    }

    private void initView() {
        grid_exchange_list = (PullToRefreshGridView) findViewById(R.id.grid_exchange_list);
        grid_exchange_list.setMode(PullToRefreshBase.Mode.BOTH);
        mGridView = grid_exchange_list.getRefreshableView();
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("这里很空，下拉刷新试试");
        //当界面为空的时候显示的视图
        grid_exchange_list.setEmptyView(tv);
        mAdapter = new ExchangeZoneAdapter(this);
        mGridView.setAdapter(mAdapter);
        grid_exchange_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                nowPage = 1;
                mList.clear();
                getdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                nowPage++;
                getdata();
            }
        });
    }
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"creditsExchangeCenter\",\"nowPage\":\"" + nowPage + "\"}";
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
                        grid_exchange_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        grid_exchange_list.onRefreshComplete();
                        Log.i("ExchangeZoneActivity", "onResponse: " + response.toString());
                        ExchangeZone exchangeZone = gson.fromJson(response, ExchangeZone.class);
                        if (exchangeZone.result.equals("1")) {
                            ToastUtils.showMessageShort(context, exchangeZone.resultNote);
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject("jj");
                            if (exchangeZone.totalPage.equals("")){
                                ToastUtils.showMessageShort(context, "没有更多了");
                            }else {
                                if (nowPage > Integer.parseInt(exchangeZone.totalPage)) {
                                    ToastUtils.showMessageShort(context, "没有更多了");
                                    return;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        List<ExchangeZone.commoditys> commodityslist = exchangeZone.commoditys;
                        mList.addAll(commodityslist);
                        mAdapter.setExchangeZone(mList);
                        mGridView.setAdapter(mAdapter);
                        grid_exchange_list.onRefreshComplete();
                    }
                });
    }

}
