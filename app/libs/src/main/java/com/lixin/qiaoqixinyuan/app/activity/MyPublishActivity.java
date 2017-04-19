package com.lixin.qiaoqixinyuan.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Home_ershou_Adapter;
import com.lixin.qiaoqixinyuan.app.adapter.Home_fangwu_Adapter;
import com.lixin.qiaoqixinyuan.app.adapter.Home_qiuzhi_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.Ershou_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Fangwu_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Qiuzhi_Bean;
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

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class MyPublishActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private TextView tv_house;
    private TextView tv_oldthing;
    private TextView tv_recruit;
    private PullToRefreshListView prlv_mypublish;
    private int nowPage = 1;
    private Home_fangwu_Adapter home_fangwu_adapter;
    private Home_ershou_Adapter home_ershou_adapter;
    private Home_qiuzhi_Adapter home_qiuzhi_adapter;
    private int type = 0;
    private List<Fangwu_Bean.Housinginnewslist> housinginnews = new ArrayList<>();
    private List<Ershou_Bean.Secondnewslist> secondnews = new ArrayList<>();
    private List<Qiuzhi_Bean.Jobhuntinglist> jobhunting = new ArrayList<>();
    private String uid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypublish);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView();
        initData();
        initLiserner();
        getdata();
    }

    private void initView() {
        home_fangwu_adapter = new Home_fangwu_Adapter(dialog,1);
        home_ershou_adapter = new Home_ershou_Adapter(dialog,1);
        home_qiuzhi_adapter = new Home_qiuzhi_Adapter(dialog,1);
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_house = (TextView) findViewById(R.id.tv_house);
        tv_oldthing = (TextView) findViewById(R.id.tv_oldthing);
        tv_recruit = (TextView) findViewById(R.id.tv_recruit);
        prlv_mypublish = (PullToRefreshListView) findViewById(R.id.prlv_mypublish);
        prlv_mypublish.setMode(PullToRefreshBase.Mode.BOTH);
        prlv_mypublish.setAdapter(home_fangwu_adapter);
    }

    private void initData() {
        tv_title.setText("我的发布");
        uid = MyApplication.getuId();
    }

    private void initLiserner() {
        iv_turnback.setOnClickListener(this);
        tv_house.setOnClickListener(this);
        tv_oldthing.setOnClickListener(this);
        tv_recruit.setOnClickListener(this);
        prlv_mypublish.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                if (type == 0) {
                    housinginnews.clear();
                } else if (type == 1) {
                    secondnews.clear();
                } else if (type == 2) {
                    jobhunting.clear();
                }
                getdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getdata();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_house:
                tv_house.setTextColor(getResources().getColor(R.color.theme));
                tv_oldthing.setTextColor(getResources().getColor(R.color.text_gray));
                tv_recruit.setTextColor(getResources().getColor(R.color.text_gray));
                type = 0;
                nowPage=1;
                home_fangwu_adapter.setHousinginnews(housinginnews);
                prlv_mypublish.setAdapter(home_fangwu_adapter);
                getdata();
                break;
            case R.id.tv_oldthing:
                tv_oldthing.setTextColor(getResources().getColor(R.color.theme));
                tv_house.setTextColor(getResources().getColor(R.color.text_gray));
                tv_recruit.setTextColor(getResources().getColor(R.color.text_gray));
                type = 1;
                nowPage=1;
                home_ershou_adapter.setHousinginnews(secondnews);
                prlv_mypublish.setAdapter(home_ershou_adapter);
                getdata();
                break;
            case R.id.tv_recruit:
                tv_recruit.setTextColor(getResources().getColor(R.color.theme));
                tv_house.setTextColor(getResources().getColor(R.color.text_gray));
                tv_oldthing.setTextColor(getResources().getColor(R.color.text_gray));
                nowPage=1;
                home_qiuzhi_adapter.setHousinginnews(jobhunting);
                prlv_mypublish.setAdapter(home_qiuzhi_adapter);
                getdata();
                break;
        }
    }

    private void getdata() {
     /*   cmd:”releaseinformation”
        uid:"33"    //用户id
        releaseuid:"12"   //个人主页的个人id  / 个人中心 和uid相同
        nowPage:"1"     //当前页
        type:"0"       //0 房屋信息 1 二手信息 2 招聘信息
        token:    [JPUSHService registrationID]   //推送token*/
        Map<String, String> params = new HashMap<>();

        String json="{\"cmd\":\"releaseinformation\",\"uid\":\"" + uid + "\",\"releaseuid\":\"" + uid + "\"," +
                "\"type\":\"" + type + "\",\"token\":\"" + token + "\",\"nowPage\":\"" + nowPage + "\"}";
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
                        prlv_mypublish.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        prlv_mypublish.onRefreshComplete();
                        dialog.dismiss();
                        switch (type) {
                            case 0:
                                Fangwu_Bean fangwu_bean = gson.fromJson(response, Fangwu_Bean.class);
                                if (fangwu_bean.result.equals("1")) {
                                    ToastUtil.showToast(context, fangwu_bean.resultNote);
                                    home_fangwu_adapter.notifyDataSetChanged();
                                    return;
                                }
                                if (Integer.parseInt(fangwu_bean.totalPage) < nowPage) {
                                    ToastUtil.showToast(context, "没有更多了");
                                    home_fangwu_adapter.notifyDataSetChanged();
                                    return;
                                }
                                List<Fangwu_Bean.Housinginnewslist> housinginnewslist = fangwu_bean.housinginnewslist;
                                housinginnews.addAll(housinginnewslist);
                                home_fangwu_adapter.setHousinginnews(housinginnews);
                                home_fangwu_adapter.notifyDataSetChanged();
                                break;
                            case 1:
                                Ershou_Bean ershou_bean = gson.fromJson(response, Ershou_Bean.class);
                                if (ershou_bean.result.equals("1")) {
                                    ToastUtil.showToast(context, ershou_bean.resultNote);
                                    home_ershou_adapter.notifyDataSetChanged();
                                    return;
                                }
                                if (Integer.parseInt(ershou_bean.totalPage) < nowPage) {
                                    ToastUtil.showToast(context, "没有更多了");
                                    home_ershou_adapter.notifyDataSetChanged();
                                    return;
                                }
                                List<Ershou_Bean.Secondnewslist> secondnewslist = ershou_bean.secondnewslist;
                                secondnews.addAll(secondnewslist);
                                home_ershou_adapter.setHousinginnews(secondnews);
                                home_ershou_adapter.notifyDataSetChanged();
                                break;
                            case 2:
                                Qiuzhi_Bean qiuzhi_bean = gson.fromJson(response, Qiuzhi_Bean.class);
                                if (qiuzhi_bean.result.equals("1")) {
                                    ToastUtil.showToast(context, qiuzhi_bean.resultNote);
                                    home_qiuzhi_adapter.notifyDataSetChanged();
                                    return;
                                }
                                if (Integer.parseInt(qiuzhi_bean.totalPage) < nowPage) {
                                    ToastUtil.showToast(context, "没有更多了");
                                    home_qiuzhi_adapter.notifyDataSetChanged();
                                    return;
                                }
                                List<Qiuzhi_Bean.Jobhuntinglist> jobhuntinglist = qiuzhi_bean.jobhuntinglist;
                                jobhunting.addAll(jobhuntinglist);
                                home_qiuzhi_adapter.setHousinginnews(jobhunting);
                                home_qiuzhi_adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
    }
}
