package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.OpinionAdapter;
import com.lixin.carclassstore.bean.CarModel;
import com.lixin.carclassstore.bean.OpinionBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.squareup.picasso.Picasso;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/25
 * My mailbox is 1403241630@qq.com
 * 添加爱车
 */

public class CarModelActivity extends BaseActivity{
    private ListView list_car__model_list;
    private ImageView im_car_icon;
    private TextView textCarName;
    private String carVersionId;
    private String carName;
    private String carIcon;
    private List<CarModel.carModelList> mList = new ArrayList<>();
    private CarModelAdapter modelAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_modle);
        Intent intent = getIntent();
        carVersionId = intent.getStringExtra("carVersionId");
        carName = intent.getStringExtra("carName");
        carIcon = intent.getStringExtra("carIcon");
        hideBack(false);
        initView();
    }

    private void initView() {
        list_car__model_list = (ListView) findViewById(R.id.list_car__model_list);
        im_car_icon =(ImageView) findViewById(R.id.im_car_icon);
        Picasso.with(context).load(carIcon).into(im_car_icon);
        textCarName = (TextView) findViewById(R.id.text_car_name);
        textCarName.setText(carName);
        modelAdapter = new CarModelAdapter(this);
        list_car__model_list.setAdapter(modelAdapter);
        getdata();
    }
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getCarModelInfo\",\"carVersionId\":\"" + carVersionId +"\"}";
        params.put("json", json);
        Log.i("OpinionFragment", "onResponse: " + json.toString());
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
                Log.i("OpinionFragment", "onResponse: " + response.toString());
                Gson gson = new Gson();
                dialog1.dismiss();
                CarModel carModelBean = gson.fromJson(response, CarModel.class);
                if (carModelBean.result.equals("1")) {
                    ToastUtils.showMessageShort(context, carModelBean.resultNote);
                    return;
                }
                List<CarModel.carModelList> carModelList = carModelBean.carModelList;
                Log.i("CarModelActivity", "CarModelActivity: " + carModelList.get(0).getCarModel());
                mList.addAll(carModelList);
                modelAdapter.setModelList(context,mList);
                list_car__model_list.setAdapter(modelAdapter);

            }
        });
    }
}
