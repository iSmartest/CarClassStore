package com.lixin.carclassstore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.CarSeriesAdapter;
import com.lixin.carclassstore.bean.CarSeries;
import com.lixin.carclassstore.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/27
 * My mailbox is 1403241630@qq.com
 * 选择车系列
 */

public class CarSeriesActivity extends BaseActivity {
    private TextView text_car_name,text_car_style_name;
    private LinearLayout line_brand_introduction;
    private ListView lv_car_style;
    private CarSeriesAdapter mCarSeriesAdapter;
    private List<CarSeries> mList = new ArrayList<>();
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_series);
        setTitleText("请选择车系列");
        initView();
        initData();

        mCarSeriesAdapter = new CarSeriesAdapter(CarSeriesActivity.this, mList);
        lv_car_style.setAdapter(mCarSeriesAdapter);
        mCarSeriesAdapter.setCarSeriesList(mList);

    }

    private void initData() {
        for (int i = 0; i < 6; i++) {
            CarSeries carSeries = new CarSeries();
            carSeries.setName("奥迪A3");
            carSeries.setPrice("18.46-28.10万");
            mList.add(carSeries);
        }
    }

    private void initView() {
        text_car_name = (TextView) findViewById(R.id.text_car_name);
        text_car_style_name = (TextView) findViewById(R.id.text_car_style_name);
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

                startActivityForResult(new Intent(CarSeriesActivity.this,NewCarDetailsActivity.class).putExtra(NewCarDetailsActivity.ARG, carSeries.getName())
                        , 3);
            }
        });
    }

}
