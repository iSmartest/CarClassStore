package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.MyReleaseAdapter;
import com.lixin.carclassstore.adapter.ShoppingCartAdapter;
import com.lixin.carclassstore.bean.MyReleaseBean;
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
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 */

public class MyReleaseActivity extends BaseActivity implements View.OnClickListener
        ,ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface{
    private PullToRefreshListView list_my_release;
    private MyReleaseAdapter myReleaseAdapter;
    private List<MyReleaseBean.qusetions> myReleaseBeanList = new ArrayList<>();
    private String uid = "123";
    private int nowPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_release);
        hideBack(false);
        setTitleText("我的发布");
        initView();
    }

    private void initView() {
        list_my_release = (PullToRefreshListView) findViewById(R.id.list_my_release);
        list_my_release.setMode(PullToRefreshBase.Mode.BOTH);
        myReleaseAdapter = new MyReleaseAdapter(this);
        myReleaseAdapter.setModifyCountInterface(this);
        list_my_release.setAdapter(myReleaseAdapter);
        list_my_release.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                myReleaseBeanList.clear();
                getdata();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getdata();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"queryPubilckQuestion\",\"uid\":\"" + uid +"\",\"nowPage\":\"" + nowPage + "\"}";
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
                        list_my_release.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        list_my_release.onRefreshComplete();
                        Log.i("response", "response: " + response.toString());
                        MyReleaseBean myReleaseBean = gson.fromJson(response, MyReleaseBean.class);
                        if (myReleaseBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, myReleaseBean.getResultNote());
                            return;
                        }
                        if (Integer.parseInt(myReleaseBean.getTotalPage()) < nowPage) {
                            ToastUtils.showMessageShort(context, "没有更多了");
                            return;
                        }
                        List<MyReleaseBean.qusetions> qusetionslist = myReleaseBean.qusetions;
                        myReleaseBeanList.addAll(qusetionslist);
                        myReleaseAdapter.setMyReleaseBean(myReleaseBeanList);
                        list_my_release.setAdapter(myReleaseAdapter);
                        list_my_release.onRefreshComplete();
                    }
                });
    }


    @Override
    public void checkGroup(int position, boolean isChecked) {

    }

    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {

    }

    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        MyReleaseBean.qusetions qusetions = myReleaseBeanList.get(position);
        int currentCount = qusetions.getCount();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        qusetions.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        myReleaseAdapter.notifyDataSetChanged();

    }

    @Override
    public void childDelete(int position) {
        myReleaseBeanList.remove(position);
        myReleaseAdapter.notifyDataSetChanged();

    }

}
