package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lixin.carclassstore.R;


/**
 * Created by 小火
 * Create time on  2017/3/29
 * My mailbox is 1403241630@qq.com
 * 服务类别
 */

public class ServiceClassActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout linear_all_store,linear_cosmetology_store,linear_tyre_store
            ,linear_maintain_store,linear_refit_store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_class);
        setTitleText("门店");
        initView();
    }

    private void initView() {
        linear_all_store = (LinearLayout) findViewById(R.id.linear_all_store);
        linear_all_store.setOnClickListener(this);
        linear_cosmetology_store = (LinearLayout) findViewById(R.id.linear_cosmetology_store);
        linear_cosmetology_store.setOnClickListener(this);
        linear_tyre_store = (LinearLayout) findViewById(R.id.linear_tyre_store);
        linear_tyre_store.setOnClickListener(this);
        linear_maintain_store = (LinearLayout) findViewById(R.id.linear_maintain_store);
        linear_maintain_store.setOnClickListener(this);
        linear_refit_store = (LinearLayout) findViewById(R.id.linear_refit_store);
        linear_refit_store.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //传入不同的参数，在门店中根据不同的参数解析不同的数据进行加载
        switch (v.getId()){
            case R.id.linear_all_store://全部门店
                startActivity(new Intent(ServiceClassActivity.this,StoreActivity.class));
                break;
            case R.id.linear_cosmetology_store://美容门店
                startActivity(new Intent(ServiceClassActivity.this,StoreActivity.class));
                break;
            case R.id.linear_tyre_store://轮胎门店
                startActivity(new Intent(ServiceClassActivity.this,StoreActivity.class));
                break;
            case R.id.linear_maintain_store://保养门店
                startActivity(new Intent(ServiceClassActivity.this,StoreActivity.class));
                break;
            case R.id.linear_refit_store://改装门店
                startActivity(new Intent(ServiceClassActivity.this,StoreActivity.class));
                break;
        }
    }
}
