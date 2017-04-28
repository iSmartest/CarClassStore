package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.MyAllOrderAdapter;
import com.lixin.carclassstore.bean.RoadRescueBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/15
 * My mailbox is 1403241630@qq.com
 */

public class RescueInformationActivity extends BaseActivity{
    private String accidentid;
    private TextView text_accident_type,text_accident_dec,text_accident_address,text_accident_handle_type,text_accident_rceply;
    private String uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue_information);
        hideBack(false);
        setTitleText("救援信息详情");
        Intent intent = getIntent();
        accidentid = intent.getStringExtra("rescueList");
        uid = (String) SPUtils.get(RescueInformationActivity.this,"uid","");
        initView();
        getdata();
    }

    private void initView() {
        text_accident_type = (TextView) findViewById(R.id.text_accident_type);
        text_accident_dec = (TextView) findViewById(R.id.text_accident_dec);
        text_accident_address = (TextView) findViewById(R.id.text_accident_address);
        text_accident_handle_type = (TextView) findViewById(R.id.text_accident_handle_type);
        text_accident_rceply = (TextView) findViewById(R.id.text_accident_rceply);
    }

    public void getdata() {
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"accidentDetail\",\"accidentid\":\"" + accidentid + "\"" + ",\"uid\":\"" + uid + "\"}";
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
                        dialog1.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray ordersArray = jsonObject.getJSONArray("rescueList");
                            Log.i("ordersArray", "onResponse: " + ordersArray.toString());
                            text_accident_type.setText(ordersArray.getJSONObject(0).getString("accidentType"));
                            text_accident_dec.setText(ordersArray.getJSONObject(0).getString("accidentDec"));
                            text_accident_address.setText(ordersArray.getJSONObject(0).getString("accidentaddress"));
                            text_accident_handle_type.setText(ordersArray.getJSONObject(0).getString("accidentHandleType"));
                            text_accident_rceply.setText(ordersArray.getJSONObject(0).getString("accidentReply"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
