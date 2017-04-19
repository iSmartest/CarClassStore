package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.OrderGoodsAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.MyorderBean;
import com.lixin.qiaoqixinyuan.app.view.MyListview;

import java.util.List;

public class Dingdan_xiangqing_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private TextView tv_ordernum;
    private TextView tv_state;
    private MyListview lv_ordergoods;
    private TextView tv_goodsmoney;
    private TextView tv_fare;
    private TextView tv_allmoney;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_time;
    private TextView tv_paystyle;
    private TextView tv_leavemessage;
    private Button btn_complete;
    private RelativeLayout activity_dingdan_xiangqing_;
    private MyorderBean.OrderBean orders;
    private List<MyorderBean.OrderBean.ProductBean> productList;
    private String ordertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan_xiangqing_);
        orders= (MyorderBean.OrderBean) getIntent().getExtras().getSerializable("orders");
        ordertype = getIntent().getExtras().getString("ordertype");
        productList = orders.productList;
        initView();
        setdata();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("订单详情");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        tv_ordernum = (TextView) findViewById(R.id.tv_ordernum);
        tv_state = (TextView) findViewById(R.id.tv_state);
        lv_ordergoods = (MyListview) findViewById(R.id.lv_ordergoods);
        tv_goodsmoney = (TextView) findViewById(R.id.tv_goodsmoney);
        tv_fare = (TextView) findViewById(R.id.tv_fare);
        tv_allmoney = (TextView) findViewById(R.id.tv_allmoney);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_paystyle = (TextView) findViewById(R.id.tv_paystyle);
        tv_leavemessage = (TextView) findViewById(R.id.tv_leavemessage);
        btn_complete = (Button) findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(this);
        tv_title.setText("订单详情");
        OrderGoodsAdapter orderGoodsAdapter = new OrderGoodsAdapter(context);
        orderGoodsAdapter.setProductList(productList);
        lv_ordergoods.setAdapter(orderGoodsAdapter);
    }
 private void  setdata(){
     tv_ordernum.setText(orders.ordernum);
     if (orders.statustype.equals("0")) {
         tv_state.setText("进行中");
     }else {
         tv_state.setText("已完成");
     }
     tv_goodsmoney.setText("￥"+orders.orderprice);
     tv_allmoney.setText("￥"+orders.orderprice);
     tv_name.setText(orders.shopName);
     tv_time.setText(orders.adTime);
     tv_fare.setText(orders.sendMoney);
     tv_name.setText(orders.goalName);
     tv_phone.setText(orders.goalPhone);
     tv_address.setText(orders.goalAddr);
     tv_leavemessage.setText(orders.oremark);
     if (ordertype.equals("0")){
         tv_paystyle.setVisibility(View.VISIBLE);
         tv_paystyle.setText("见面付款  ");
     } else if (ordertype.equals("1")) {
         tv_paystyle.setVisibility(View.VISIBLE);
         tv_paystyle.setText("线上支付  ");
     }else {
         tv_paystyle.setVisibility(View.GONE);
     }
 }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
        }
    }

}
