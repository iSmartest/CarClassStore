package com.lixin.carclassstore.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.CarSeriesAdapter;
import com.lixin.carclassstore.bean.CarSeries;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
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
    private TextView text_car_name;
    private ListView lv_car_style;
    private ImageView imCarLeader;
    private CarSeriesAdapter mCarSeriesAdapter;
    private List<CarSeries.carVersionsList> mList1 = new ArrayList<>();
    private String carName;
    private String carBrandId;
    private String carleader;
    private String nowPage = "1";
    private String uid ;
    private String flag;
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
        flag = intent.getStringExtra("flag");
        uid = (String) SPUtils.get(context,"uid","");
        initView();
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"getCarVersionInfo\",\"carBrandId\":\""+ carBrandId + "\"" + ",\"nowPage\":\""+ nowPage + "\"" + ",\"uid\":\""+ uid + "\"}";
        params.put("json", json);
        Log.i("CarSeriesActivity", "CarSeriesActivity: " + json.toString());
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
                Log.i("CarSeriesActivity", "CarSeriesActivity: " + response.toString());
                Gson gson = new Gson();
                dialog1.dismiss();
                 CarSeries carServes = gson.fromJson(response, CarSeries.class);
                if (carServes.getResult().equals("1")){
                    ToastUtils.showMessageLong(CarSeriesActivity.this,carServes.getResultNote());
                }
                List<CarSeries.carVersionsList> carVersionsList = carServes.carVersionsList;
                mList1.addAll(carVersionsList);
                mCarSeriesAdapter.setCarSeriesList(context,mList1,flag,carName);
                lv_car_style.setAdapter(mCarSeriesAdapter);
                Log.i("qqqq", "carVersionsList: " + carVersionsList.get(0).getCarVersionName());
            }
        });
    }

    private void initView() {
        text_car_name = (TextView) findViewById(R.id.text_car_name);
        text_car_name.setText(carName);
        imCarLeader = (ImageView) findViewById(R.id.im_car_leader);
        if (TextUtils.isEmpty(carleader)){
            imCarLeader.setImageResource(R.drawable.image_car_defult);
        }else Picasso.with(context).load(carleader).into(imCarLeader);
        lv_car_style = (ListView) findViewById(R.id.lv_car_style);
        mCarSeriesAdapter = new CarSeriesAdapter(this);
        lv_car_style.setAdapter(mCarSeriesAdapter);
        lv_car_style.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

}
