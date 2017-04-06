package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.RoadRescueAdapter;
import com.lixin.carclassstore.bean.RoadRescue;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 */

public class RoadRescueActivity extends Activity implements View.OnClickListener{
    private ImageView Iv_base_back,iv_edit;
    private ListView list_road_rescue;
    private RoadRescueAdapter mRoadRescueAdapter;
    private List<RoadRescue> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_rescue);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            RoadRescue roadResuce = new RoadRescue();
            roadResuce.setDate("2017-3-31 18:26:35");
            roadResuce.setQuestion("汽车没油了");
            roadResuce.setState("已处理");
            mList.add(roadResuce);
        }
    }

    private void initView() {
        Iv_base_back = (ImageView) findViewById(R.id.Iv_base_back);
        Iv_base_back.setOnClickListener(this);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_edit.setOnClickListener(this);
        list_road_rescue = (ListView) findViewById(R.id.list_road_rescue);
        mRoadRescueAdapter = new RoadRescueAdapter(RoadRescueActivity.this,mList);
        list_road_rescue.setAdapter(mRoadRescueAdapter);
        list_road_rescue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Iv_base_back:
                finish();
                break;
            case R.id.iv_edit:
                startActivity(new Intent(RoadRescueActivity.this,ReleaseRescueInformationActivity.class));
                break;
        }
    }
}
