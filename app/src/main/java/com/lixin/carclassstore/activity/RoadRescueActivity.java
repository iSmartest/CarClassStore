package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.RoadRescueAdapter;
import com.lixin.carclassstore.bean.RoadRescueBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.ProgressDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 */

public class RoadRescueActivity extends Activity implements View.OnClickListener{
    private ImageView Iv_base_back,iv_edit;
    private ListView list_road_rescue;
    private RoadRescueAdapter mRoadRescueAdapter;
    private List<RoadRescueBean.rescueList> mList = new ArrayList<>();
    private List<RoadRescueBean.accidentTypes> accidenttypesList = new ArrayList<>();
    private String uid = "123";
    protected Context context;
    protected Dialog dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_rescue);
        context = this;
        dialog1 = ProgressDialog.createLoadingDialog(context, "加载中.....");
        initView();
        getdata();
    }
    private void initView() {
        Iv_base_back = (ImageView) findViewById(R.id.Iv_base_back);
        Iv_base_back.setOnClickListener(this);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_edit.setOnClickListener(this);
        list_road_rescue = (ListView) findViewById(R.id.list_road_rescue);
        mRoadRescueAdapter = new RoadRescueAdapter(context);
        list_road_rescue.setAdapter(mRoadRescueAdapter);
        list_road_rescue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,RescueInformationActivity.class);
                intent.putExtra("rescueList",mList.get(position).getAccidentid());
                startActivity(intent);
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
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"accidentList\",\"uid\":\"" + uid +"\"}";
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
                        dialog1.dismiss();
                        ToastUtils.showMessageShort(context, e.getMessage());
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        Log.i("response", "onResponse: "+ response.toString());
                        RoadRescueBean roadRescueBean = gson.fromJson(response, RoadRescueBean.class);
                        if (roadRescueBean.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, roadRescueBean.getResultNote());
                            return;
                        }
                        List<RoadRescueBean.rescueList> rescueList = roadRescueBean.rescueList;
                        List<RoadRescueBean.accidentTypes> accidenttypes = roadRescueBean.accidentTypes;
//                        Log.i("rescueList", "onResponse: " + rescueList.get(0).accidentHandleType);
                        mList.addAll(rescueList);
                        accidenttypesList.addAll(accidenttypes);
                        mRoadRescueAdapter.setRoadRescue(mList);
                        list_road_rescue.setAdapter(mRoadRescueAdapter);
                    }
                });
    }


}
