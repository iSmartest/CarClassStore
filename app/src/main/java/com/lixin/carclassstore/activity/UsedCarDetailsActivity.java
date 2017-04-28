package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.UsedCarList;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/3/28
 * My mailbox is 1403241630@qq.com
 * 二手车详情
 */

public class UsedCarDetailsActivity extends BaseActivity{
    private String carId;
    private TextView mDisplacement,mTransfer,mExterior,mStall,mFirstLicense,mRunKm
            ,mModel,mNowPrice,mNewPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_used_car_details);
        hideBack(false);
        setTitleText("二手车详情");
        Intent intent = getIntent();
        carId = intent.getStringExtra("carId");
        initView();
        getdata();
    }
    private void initView() {
        mDisplacement = (TextView) findViewById(R.id.text_car_displacement);
        mTransfer = (TextView) findViewById(R.id.text_transfer_times);
        mExterior = (TextView) findViewById(R.id.text_exterior);
        mStall = (TextView) findViewById(R.id.text_stall);
        mFirstLicense = (TextView) findViewById(R.id.text_first_license_time);
        mRunKm = (TextView) findViewById(R.id.text_run_km);
        mModel = (TextView) findViewById(R.id.text_used_car_model);
        mNowPrice = (TextView) findViewById(R.id.text_used_car_now_price);
        mNewPrice = (TextView) findViewById(R.id.text_used_new_price);

    }
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json = "{\"cmd\":\"getOldCarInfo\",\"carid\":\"" + carId + "\"}";
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
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        Log.i("UsedCarListActivity", "UsedCarListActivity: " + response.toString());
                        UsedCarList usedCar = gson.fromJson(response, UsedCarList.class);
                        if (usedCar.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, usedCar.getResultNote());
                            return;
                        }
                        List<UsedCarList.carModelList> qusetionslist = usedCar.carModelList;
//                        usedCarList.addAll(qusetionslist);
//                        mAdapter.setUsedCarBrandList(context,usedCarList);
//                        mUsedCarList.setAdapter(mAdapter);
                    }
                });
    }
}
