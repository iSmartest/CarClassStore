package com.lixin.carclassstore.activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.StoreDetailAdapter;
import com.lixin.carclassstore.bean.StoreDetailBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/29
 * My mailbox is 1403241630@qq.com
 * 门店详情
 */

public class StoreDetailsActivity extends BaseActivity implements View.OnClickListener{
    private TextView text_immediately_pay,text_tyre,text_maintenance;
    private LinearLayout linear_shopping_cart;
    private ListView list_something;
    private StoreDetailAdapter storeDetailAdapter;
    private List<StoreDetailBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_store_details);
        hideBack(false);
        initView();
        initData();

    }
    protected void initData() {
        for (int i = 0; i < 6; i++) {
            StoreDetailBean storeDetailBean = new StoreDetailBean();
            storeDetailBean.setShoppingName("米其林轮胎");
            storeDetailBean.setSize("15寸");
            storeDetailBean.setSalesvolume(200);
            storeDetailBean.setPrice(750);
            mList.add(storeDetailBean);
        }
    }
    protected void initView() {
        text_immediately_pay = (TextView) findViewById(R.id.text_immediately_pay);
        text_immediately_pay.setOnClickListener(this);
        text_tyre = (TextView) findViewById(R.id.text_tyre);
        text_tyre.setOnClickListener(this);
        text_maintenance = (TextView) findViewById(R.id.text_maintenance);
        text_maintenance.setOnClickListener(this);
        linear_shopping_cart = (LinearLayout) findViewById(R.id.linear_shopping_cart);
        linear_shopping_cart.setOnClickListener(this);
        list_something = (ListView) findViewById(R.id.list_something);

        storeDetailAdapter = new StoreDetailAdapter(StoreDetailsActivity.this,mList);
        list_something.setAdapter(storeDetailAdapter);
        storeDetailAdapter.setStoreDetailBean(mList);
        storeDetailAdapter.setModifyCountInterface((StoreDetailAdapter.ModifyCountInterface) this);


        list_something.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_immediately_pay:
                startActivity(new Intent(StoreDetailsActivity.this,OrderPaymentActivity.class));//订单支付
                break;
            case R.id.text_tyre:
                break;
            case R.id.text_maintenance:
                break;
            case R.id.linear_shopping_cart:
                startActivity(new Intent(StoreDetailsActivity.this,ShoppingCartActivity.class));//购物车
                break;
        }

    }
}
