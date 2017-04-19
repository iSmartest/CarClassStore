package com.lixin.qiaoqixinyuan.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.Im.YWSDKUtil;
import com.lixin.qiaoqixinyuan.app.adapter.Pin_list_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseFragment;
import com.lixin.qiaoqixinyuan.app.bean.Ping_data_Bean;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.umeng.socialize.utils.DeviceConfig.context;
import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class PinFragment extends BaseFragment implements View.OnClickListener {
    private TextView tv_move1;
    private TextView tv_back1;
    private TextView tv_move2;
    private TextView tv_back2;
    private TextView tv_other_city;
    private TextView tv_person_bus;
    private String type = "1";
    private int nowPage = 1;
    private PullToRefreshListView list_pin;
    private List<Ping_data_Bean.Carpoolinglist> carpooling = new ArrayList<>();
    private Pin_list_Adapter pin_list_adapter;
    private String realeasepeoplestarting;
    private String searchType;
    private String carpoolingtype;
    private String realeasepeopleending;
    private String realeasecarinterval;
    private String realeasecardate;
    private LinearLayout layout_move1;
    private LinearLayout layout_move2;
     private String openId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(context, R.layout.ping_fragment, null);
        initView(view);
        getdata();

        openId = SharedPreferencesUtil.getSharePreStr(context, "openId");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_move1:
                nowPage = 1;
                carpooling.clear();
                type = "1";
                tv_move1.setTextColor(getResources().getColor(R.color.theme));
                tv_back1.setTextColor(getResources().getColor(R.color.theme));
                tv_move2.setTextColor(Color.BLACK);
                tv_back2.setTextColor(Color.BLACK);
                tv_other_city.setTextColor(Color.BLACK);
                tv_person_bus.setTextColor(Color.BLACK);
                getdata();
                break;
            case R.id.layout_move2:
                nowPage = 1;
                carpooling.clear();
                type = "2";
                tv_move2.setTextColor(getResources().getColor(R.color.theme));
                tv_back2.setTextColor(getResources().getColor(R.color.theme));
                tv_move1.setTextColor(Color.BLACK);
                tv_back1.setTextColor(Color.BLACK);
                tv_other_city.setTextColor(Color.BLACK);
                tv_person_bus.setTextColor(Color.BLACK);
                getdata();
                break;
            case R.id.tv_other_city:
                nowPage = 1;
                carpooling.clear();
                type = "3";
                tv_move1.setTextColor(Color.BLACK);
                tv_back1.setTextColor(Color.BLACK);
                tv_move2.setTextColor(Color.BLACK);
                tv_back2.setTextColor(Color.BLACK);
                tv_other_city.setTextColor(getResources().getColor(R.color.theme));
                tv_person_bus.setTextColor(Color.BLACK);
                getdata();
                break;
            case R.id.tv_person_bus:
                nowPage = 1;
                carpooling.clear();
                type = "4";
                tv_move1.setTextColor(Color.BLACK);
                tv_back1.setTextColor(Color.BLACK);
                tv_move2.setTextColor(Color.BLACK);
                tv_back2.setTextColor(Color.BLACK);
                tv_other_city.setTextColor(Color.BLACK);
                tv_person_bus.setTextColor(getResources().getColor(R.color.theme));
                getdata();
                break;
        }
    }
    private void initView(View view) {
        pin_list_adapter = new Pin_list_Adapter();
        tv_move1 = (TextView) view.findViewById(R.id.tv_move1);
        tv_back1 = (TextView) view.findViewById(R.id.tv_back1);
        tv_move2 = (TextView) view.findViewById(R.id.tv_move2);
        tv_back2 = (TextView) view.findViewById(R.id.tv_back2);
        tv_other_city = (TextView) view.findViewById(R.id.tv_other_city);
        tv_other_city.setOnClickListener(this);
        tv_person_bus = (TextView) view.findViewById(R.id.tv_person_bus);
        tv_person_bus.setOnClickListener(this);
        list_pin = (PullToRefreshListView) view.findViewById(R.id.list_pin);
        list_pin.setMode(PullToRefreshBase.Mode.BOTH);
        list_pin.setAdapter(pin_list_adapter);
        list_pin.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                carpooling.clear();
                getdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getdata();
            }
        });
        layout_move1 = (LinearLayout) view.findViewById(R.id.layout_move1);
        layout_move1.setOnClickListener(this);
        layout_move2 = (LinearLayout) view.findViewById(R.id.layout_move2);
        layout_move2.setOnClickListener(this);
        list_pin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (openId.equals("")){
                    ToastUtil.showToast(context,"用户未登陆");
                    return;
                }else {
                    final String target = carpooling.get(i-1).openId; //消息接收者ID
                    final String appkey = "23697682"; //消息接收者appKey
                    Intent chattingActivityIntent = YWSDKUtil.getywIMKit().getChattingActivityIntent(target, appkey);
                    startActivity(chattingActivityIntent);
                }
            }
        });
    }
    private void getdata() {
     /*   cmd:”carpoolinglist”
        type:“1”  // (沁源-长治,长治-沁源)type=1,(沁源-太原,太原-沁源)type=2,(其他城市)type=3,(人找车)type=4
        nowPage:"1"     //当前页   */
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "carpoolinglist");
        params.put("type", type);
        params.put("nowPage", nowPage + "");*/
        String json = "{\"cmd\":\"carpoolinglist\",\"type\":\"" + type + "\"" +
                ",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                        list_pin.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        list_pin.onRefreshComplete();
                        Ping_data_Bean ping_data_bean = gson.fromJson(response, Ping_data_Bean.class);
                        if (ping_data_bean.result.equals("1")) {
                            ToastUtil.showToast(context, ping_data_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(ping_data_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        List<Ping_data_Bean.Carpoolinglist> carpoolinglist = ping_data_bean.carpoolinglist;
                        carpooling.addAll(carpoolinglist);
                        pin_list_adapter.setCarpooling(carpooling);
                        list_pin.setAdapter(pin_list_adapter);
                        list_pin.onRefreshComplete();
                    }
                });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 12) {
            searchType = data.getStringExtra("searchType");
            carpoolingtype = data.getStringExtra("carpoolingtype");
            realeasepeoplestarting = data.getStringExtra("realeasepeoplestarting");
            realeasepeopleending = data.getStringExtra("realeasepeopleending");
            realeasecardate = data.getStringExtra("realeasecardate");
            realeasecarinterval = data.getStringExtra("realeasecarinterval");
            carpooling.clear();
            nowPage=1;
            getrenche();
        }
    }

    private void getrenche() {
        Map<String, String> params = new HashMap<>();
        /*cmd:”searchepeopleother”
        searchType:"0"    // 0 车找人 1 人找车
        carpoolingtype:"02" //0 沁源-长治 1 长治-沁源 2 沁源-太原 3 太原-沁源 4 其他城市
        realeasepeoplestarting:"沁源"    //起点城市  非其他城市传0
        realeasepeopleending:"太原"    //终点城市    非其他城市传0
        realeasecardate:"今天"    //发车日期
        realeasecarinterval:"中午12点-13点"   //发车区间
        nowPage:"1" //当前页*/
        String json = "{\"cmd\":\"searchepeopleother\"," +
                "\"searchType\":\"" + searchType + "\"" + ",\"realeasepeoplestarting\":\"" + realeasepeoplestarting + "\",\"realeasepeopleending\":\"" + realeasepeopleending + "\"" +
                ",\"realeasecarinterval\":\"" + realeasecarinterval + "\",\"carpoolingtype\":\"" + carpoolingtype + "\"" +
                ",\"realeasepeopleending\":\"" + realeasepeopleending + "\",\"realeasecardate\":\"" + realeasecardate + "\"" +
                ",\"nowPage\":\"" + nowPage + "\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
       OkHttpUtils.
                post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        list_pin.onRefreshComplete();
                        Ping_data_Bean ping_data_bean = gson.fromJson(response, Ping_data_Bean.class);
                        if (ping_data_bean.result.equals("1")) {
                            ToastUtil.showToast(context, ping_data_bean.resultNote);
                            return;
                        }
                        if (Integer.getInteger(ping_data_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        List<Ping_data_Bean.Carpoolinglist> carpoolinglist = ping_data_bean.carpoolinglist;
                        carpooling.addAll(carpoolinglist);
                        pin_list_adapter.setCarpooling(carpooling);
                        list_pin.setAdapter(pin_list_adapter);
                        list_pin.onRefreshComplete();
                    }
                });
    }
}
