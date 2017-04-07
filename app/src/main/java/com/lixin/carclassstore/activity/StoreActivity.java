package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.StoreAdapter;
import com.lixin.carclassstore.bean.StoreBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/25
 * My mailbox is 1403241630@qq.com
 */

public class StoreActivity extends BaseActivity implements View.OnClickListener{
    private TextView text_service_class;
    private ListView list_store;
    private List<StoreBean.shopList> mList = new ArrayList<>();
    private StoreAdapter storeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        setTitleText("门店");
        hideBack(false);
        initView();
        storeAdapter = new StoreAdapter(StoreActivity.this,mList);
        storeAdapter.setStoreBeanList(mList);
        list_store.setAdapter(storeAdapter);
    }
    private void initView() {
        text_service_class = (TextView) findViewById(R.id.text_service_class);
        text_service_class.setOnClickListener(this);
        list_store = (ListView) findViewById(R.id.list_store);
        list_store.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(StoreActivity.this,StoreDetailsActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_service_class:
                startActivity(new Intent(StoreActivity.this,ServiceClassActivity.class));
                break;
        }
    }
}
