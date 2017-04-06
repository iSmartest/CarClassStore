package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.widget.ListView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.CollectionAdapter;
import com.lixin.carclassstore.bean.CollectionBean;
import com.lixin.carclassstore.pulldown.PullToRefreshBase;
import com.lixin.carclassstore.pulldown.PullToRefreshListView;
import com.lixin.carclassstore.utils.ConstantUtil;
import com.lixin.carclassstore.utils.GlobalMethod;
import com.lixin.carclassstore.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 * 我的收藏
 */

public class MyCollectionActivity extends BaseActivity
{
    private PullToRefreshListView pullToRefreshListView;
    private ListView mListView;
    private CollectionAdapter mAdapter;
    private List<CollectionBean> mList = new ArrayList<>();
    private int page = 1;
    private String noDataTips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        setTitleText("我的收藏");
        initView();
    }

    private void initView() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView);
        mListView = pullToRefreshListView.getRefreshableView();
        mAdapter = new CollectionAdapter(MyCollectionActivity.this,mList);
        mListView.setAdapter(mAdapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int type = pullToRefreshListView.getRefreshType();
                if (type == PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH){
                    if (GlobalMethod.isNetworkAvailable(MyCollectionActivity.this)){
                        loadData(ConstantUtil.PULL_DOWN_REFRESH);
                    }else {
                        ToastUtils.showMessageLong(MyCollectionActivity.this,"网络无法连接");
                    }
                }else {
                    if (type == PullToRefreshBase.MODE_PULL_UP_TO_REFRESH){
                        if (GlobalMethod.isNetworkAvailable(MyCollectionActivity.this)){
                            loadData(ConstantUtil.PULL_UP_MORE);
                        }else {
                            ToastUtils.showMessageLong(MyCollectionActivity.this,"网络不给力啊");
                        }
                    }
                }
            }
        });
    }

    private void loadData(int pullType) {
        if (pullType == ConstantUtil.PULL_DOWN_REFRESH){
            page = 1;
            noDataTips = getString(R.string.no_data);
        }else {
            page++;
            noDataTips = "加载完了";
        }
        if (GlobalMethod.isNetworkAvailable(MyCollectionActivity.this)){
            loadSeverData();
        }else {
            ToastUtils.showMessageLong(MyCollectionActivity.this,"网络不太好，请重试！");
        }
    }

    private void loadSeverData() {
        for (int i = 0; i < 20; i++) {
            CollectionBean collectionBean = new CollectionBean();
            collectionBean.setTitle("摩卡汽车服务中心");
            collectionBean.setAuthor("123单");
            collectionBean.setCateName("云南省曲靖市");
            collectionBean.setDocUrl("9分");
            mList.add(collectionBean);
        }
    }
}
