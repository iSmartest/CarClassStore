package com.lixin.carclassstore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.CarSeriesAdapter;
import com.lixin.carclassstore.bean.CarSeries;
import com.lixin.carclassstore.bean.JavaBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SharedPreferencesUtil;
import com.lixin.carclassstore.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/3/27
 * My mailbox is 1403241630@qq.com
 * 选择车系列
 */

public class CarSeriesActivity extends BaseActivity {
    private static final String TAG = "CarSeriesActivity";
    private TextView text_car_name,text_car_style_name;
    private LinearLayout line_brand_introduction;
    private ListView lv_car_style;
    private ImageView imCarLeader;
    private CarSeriesAdapter mCarSeriesAdapter;
    private CarSeries carServes;
    private List<CarSeries.carVersionsList> carVersionsList = new ArrayList<>();
    private List<CarSeries.carVersionsList.carVersions> carVersions = new ArrayList<>();
    private String carName;
    private String carBrandId;
    private String carleader;
    private String nowPage = "1";
    private String uid  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_series);
        setTitleText("请选择车系列");
        hideBack(false);
        Intent intent = getIntent();
        carName = intent.getStringExtra("carname");
        carBrandId = intent.getStringExtra("carbrandId");
        carleader = intent.getStringExtra("carleader");
        uid =  SharedPreferencesUtil.getSharePreStr(context,"uid");

        initView();
        initData();
//        mCarSeriesAdapter = new CarSeriesAdapter(CarSeriesActivity.this, mList);
//        lv_car_style.setAdapter(mCarSeriesAdapter);
//        mCarSeriesAdapter.setCarSeriesList(mList);

    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"getCarVersioninfo\",\"carBrandId\":\""+ carBrandId + "\"" + ",\"nowPage\":\""+ nowPage + "\"" + ",\"uId\":\""+ uid + "\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageLong(context, "网络异常");
                dialog1.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                dialog1.dismiss();
                Log.i(TAG, "onResponse: " + response.toString());
                carServes = gson.fromJson(response, CarSeries.class);
                if (carServes.getResult().equals("1")){
                    ToastUtils.showMessageLong(CarSeriesActivity.this,carServes.getResultNote());
                }

//                carVersionsList = carServes.carVersionsList;//
//                Log.i("qqqq", "carVersionsList: " + carVersionsList.get(0).getCarVersionName());

            }
        });
    }

    private void initView() {
        text_car_name = (TextView) findViewById(R.id.text_car_name);
        text_car_name.setText(carName);
        text_car_style_name = (TextView) findViewById(R.id.text_car_style_name);
        text_car_style_name.setText(carName);
        imCarLeader = (ImageView) findViewById(R.id.im_car_leader);
        Picasso.with(context).load(carleader).into(imCarLeader);
        line_brand_introduction = (LinearLayout) findViewById(R.id.line_brand_introduction);
        line_brand_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showMessageLong(context,"你点击了品牌介绍");
                startActivity(new Intent(CarSeriesActivity.this,NewCarModelsActivity.class));
            }
        });
        lv_car_style = (ListView) findViewById(R.id.lv_car_style);
        lv_car_style.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarSeries carSeries = new CarSeries();

//                startActivityForResult(new Intent(CarSeriesActivity.this,NewCarDetailsActivity.class).putExtra(NewCarDetailsActivity.ARG, carSeries.getName())
//                        , 3);
            }
        });
    }

}
