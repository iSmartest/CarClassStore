package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.FirstAdapter;
import com.lixin.carclassstore.bean.ShopDetailsBean;
import com.lixin.carclassstore.bean.StoreDetailsBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.viewPage.ImageSlideshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/24
 * My mailbox is 1403241630@qq.com
 * 门店详情
 */

public class StoreDetailsActivity extends BaseActivity{
    private String shopid;
    private ImageSlideshow mSlidShow;
    private ImageView mBack,mStorePhone;
    private TextView mStoreName,mStoreTotalOrder,mStoreAddress,mStoreOpenTime,mFirstPay;
    private LinearLayout mStoreDetails,mShoppingCart;
    private ListView mFristList,mSecondList;
    private String telephone;
    private String StoreDetials;
    private List<StoreDetailsBean.shopCommoditys> mList = new ArrayList<>();
    private FirstAdapter mFirstAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        initView();
        getdata();
    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getShopDetailInfo\",\"shopid\":\"" + shopid +"\"}";
        params.put("json", json);
        Log.i("StoreDetailsActivity", "onResponse: " + json.toString());
        dialog1.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageShort(context, e.getMessage());
                dialog1.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("StoreDetailsActivity", "onResponse: " + response.toString());
                Gson gson = new Gson();
                dialog1.dismiss();
                StoreDetailsBean storeDetailsBean = gson.fromJson(response, StoreDetailsBean.class);
                if (storeDetailsBean.result.equals("1")) {
                    ToastUtils.showMessageShort(context, storeDetailsBean.resultNote);
                    return;
                }
                //商品展示轮播图
                List<String> rotateShopPics = storeDetailsBean.rotateShopPics;
                Log.i("StoreDetailsActivity", "onResponse: " + rotateShopPics.toString());
                String[] relate = new String[rotateShopPics.size()];
                for (int i = 0; i <rotateShopPics.size() ; i++) {
                    relate[i] = rotateShopPics.get(i);
                }
                initData(relate);
                //门店详情数据
                mStoreName.setText(storeDetailsBean.getShopName());
                mStoreTotalOrder.setText(storeDetailsBean.getSellerNum());
                mStoreOpenTime.setText(storeDetailsBean.getShopTime());
                mStoreAddress.setText(storeDetailsBean.getShopLocaltion());
                telephone = storeDetailsBean.getShopTelephone();
                StoreDetials = storeDetailsBean.getShopDatil();
                //门店二级联动列表数据
                List<StoreDetailsBean.shopCommoditys> shopCommoditysList = storeDetailsBean.shopCommoditys;
                Log.i("StoreDetailsActivity", "onResponse: " + shopCommoditysList.get(0).commodityType);
                mList.addAll(shopCommoditysList);
                Log.i("StoreDetailsActivity", "onResponse: " + mList.get(0).getCommodityType());
                mFirstAdapter.setFirst(context,mList);
                mFristList.setAdapter(mFirstAdapter);
            }
        });
    }
    //轮播
    private void initData(String[] rotateShopPics) {
        for (int i = 0; i < rotateShopPics.length; i++) {
            mSlidShow.addImageTitle(rotateShopPics[i]);
            Log.i("ShopFragment", "onResponse: " + rotateShopPics[i]);
        }
        mSlidShow.setDotSpace(12);
        mSlidShow.setDotSize(12);
        mSlidShow.setDelay(3000);
        mSlidShow.setOnItemClickListener(new ImageSlideshow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mSlidShow.commit();
        Log.i("jjjjjj", "initViewData: " + mSlidShow);
    }
    private void initView() {
        mSlidShow = (ImageSlideshow) findViewById(R.id.img_store_gallery);
        mBack = (ImageView) findViewById(R.id.im_back);
        mBack.setOnClickListener(this);
        mStoreName = (TextView) findViewById(R.id.text_store_name);
        mStoreTotalOrder = (TextView) findViewById(R.id.text_store_total_order);
        mStoreDetails = (LinearLayout) findViewById(R.id.linear_store_details);
        mStoreDetails.setOnClickListener(this);
        mStorePhone = (ImageView) findViewById(R.id.im_store_telephone);
        mStorePhone.setOnClickListener(this);
        mStoreAddress = (TextView) findViewById(R.id.text_store_address);
        mStoreOpenTime = (TextView) findViewById(R.id.text_store_open_time);
//        mSecondList = (ListView) findViewById(R.id.list_second_shop);
        mFirstPay = (TextView) findViewById(R.id.text_immediately_pay);
        mFirstPay.setOnClickListener(this);
        mShoppingCart = (LinearLayout) findViewById(R.id.linear_shopping_cart);
        mShoppingCart.setOnClickListener(this);
        mFristList = (ListView) findViewById(R.id.list_first_choose);
        mFirstAdapter = new FirstAdapter(this);
        mFristList.setAdapter(mFirstAdapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_back:
                finish();
                break;
            case R.id.im_store_telephone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + telephone));
                startActivity(intent);
                break;
            case R.id.linear_store_details:
                Intent intent1 = new Intent(StoreDetailsActivity.this, CheckViolationWebActivity.class);
                intent1.putExtra("isStoreDetailsOrCheckviolation","1");
                intent1.putExtra(CheckViolationWebActivity.URL, StoreDetials);
                startActivity(intent1);
                break;
            case R.id.text_immediately_pay:
                Intent intent3 = new Intent(StoreDetailsActivity.this, OrderPaymentActivity.class);
                startActivity(intent3);
                break;
            case R.id.linear_shopping_cart:
                break;
        }
    }
}
