package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.CusromerComplaintAdapter;
import com.lixin.carclassstore.bean.CustomerComplaint;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */

public class CustomerComplaintActivity extends BaseActivity{
    public ListView list_complaint;
    private CusromerComplaintAdapter cusromerComplaintAdapter;
    private List<CustomerComplaint> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_complaint);
        setTitleText("客户投诉");
        initView();

    }

    private void initView() {
        list_complaint = (ListView) findViewById(R.id.list_complaint);

    }
}
