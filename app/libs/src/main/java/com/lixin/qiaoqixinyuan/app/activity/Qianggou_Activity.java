package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Home_list_qianggou_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Dianpu_huodong_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Home_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Huoqu_Juli_Bean;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.MyListview;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.R.attr.id;
import static com.lixin.qiaoqixinyuan.R.id.view;
import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Qianggou_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ImageView iv_basesearch_shuru;
    private TextView tv_shangpin_all;
    private TextView tv_shangpin_fujin;
    private TextView tv_shangpin_paixu;
    private LinearLayout activity_qianggou_;
    private PullToRefreshListView prlv_qiugou_list;
    private int nowPage=1;
    private List<Home_Bean.Huodongmodel> huodong=new ArrayList<>();
    private Home_list_qianggou_Adapter home_list_qianggou_adapter;
    private List<String>  mListType = new ArrayList<String>();  //类型列表
    private List<Huoqu_Juli_Bean.Fujinlist> fujinlist;
    private String lat;
    private String lon;
    private PopupWindow popupWindow;
    private int mycode=-1;
    private String fujinId;
    private int positionid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qianggou_);
        lat= SharedPreferencesUtil.getSharePreStr(context,"lat");
        lon= SharedPreferencesUtil.getSharePreStr(context,"lon");
        initView();
        gethuodong();
    }
    private void initView() {
        home_list_qianggou_adapter = new Home_list_qianggou_Adapter();
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        iv_basesearch_back.setOnClickListener(this);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        et_basesearch.setOnClickListener(this);
        et_basesearch.setHint("搜索活动抢购和商品");
        iv_basesearch_shuru = (ImageView) findViewById(R.id.iv_basesearch_shuru);
        iv_basesearch_shuru.setOnClickListener(this);
        tv_shangpin_all = (TextView) findViewById(R.id.tv_shangpin_all);
        tv_shangpin_all.setOnClickListener(this);
        tv_shangpin_fujin = (TextView) findViewById(R.id.tv_shangpin_fujin);
        tv_shangpin_fujin.setOnClickListener(this);
        tv_shangpin_paixu = (TextView) findViewById(R.id.tv_shangpin_paixu);
        tv_shangpin_paixu.setOnClickListener(this);
        activity_qianggou_ = (LinearLayout) findViewById(R.id.activity_qianggou_);
        prlv_qiugou_list = (PullToRefreshListView) findViewById(R.id.prlv_qiugou_list);
        prlv_qiugou_list.setAdapter(home_list_qianggou_adapter);
        prlv_qiugou_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage=1;
                huodong.clear();
                if (mycode==-1){
                gethuodong();
                }else if (mycode==0){
                    getfeileialldata(positionid);
                }else if (mycode==1){
                    getjulidata(fujinId);
                }else if (mycode==2){
                    getpaixudata(positionid);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                if (mycode==-1){
                    gethuodong();
                }else if (mycode==0){
                    getfeileialldata(positionid);
                }else if (mycode==1){
                    getjulidata(fujinId);
                }else if (mycode==2){
                    getpaixudata(positionid);
                }
            }
        });
        prlv_qiugou_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, qianggou_xiangqing_Activity.class);
                intent.putExtra("shangjiaid",huodong.get(i-1).shangjiaid);
                intent.putExtra("huodongid",huodong.get(i-1).huodongid);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_basesearch_back:
                finish();
                break;
            case R.id.tv_shangpin_all:
                nowPage=1;
                huodong.clear();
                getfenlei();
                break;
                //startActivity(new Intent(this, ShaixuanActivity.class));
            case R.id.tv_shangpin_fujin:
                nowPage=1;
                huodong.clear();
                getjuli();
                //startActivity(new Intent(this, ShaixuanActivity.class));
                break;
            case R.id.tv_shangpin_paixu:
                huodong.clear();
                nowPage=1;
                getzhinengpaixu();
               // startActivity(new Intent(this, ShaixuanActivity.class));
                break;
            case R.id.et_basesearch:
                Intent intent = new Intent(context, Search_huodong_list.class);
                startActivity(intent);
                break;
        }
    }

    private void gethuodong() {//活动类表
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "getactivitylist");
        params.put("nowPage", "1");*/
        String json="{\"cmd\":\"getactivitylist\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
           OkHttpUtils.post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        prlv_qiugou_list.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        prlv_qiugou_list.onRefreshComplete();
                        dialog.dismiss();
                        Dianpu_huodong_Bean dianpu_huodong_bean = gson.fromJson(response, Dianpu_huodong_Bean.class);
                        if (dianpu_huodong_bean.result.equals("1")) {
                            ToastUtil.showToast(Qianggou_Activity.this, dianpu_huodong_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(dianpu_huodong_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(Qianggou_Activity.this, "没有更多了");
                             prlv_qiugou_list.onRefreshComplete();
                            return;
                        }
                        List<Home_Bean.Huodongmodel> huodongmodel = dianpu_huodong_bean.huodongmodel;
                        huodong.addAll(huodongmodel);
                        home_list_qianggou_adapter.setHuodongmodel(huodong);
                        home_list_qianggou_adapter.notifyDataSetChanged();
                        prlv_qiugou_list.onRefreshComplete();
                    }
                });
    }
    private void getfenlei(){//分类列表
       /* Map<String, String> params = new HashMap<>();
        params.put("cmd", "activitycategorylist");
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Qianggou_Activity.this, e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Shangpin_all_Bean shangpin_all_bean = gson.fromJson(response, Shangpin_all_Bean.class);
                        if (shangpin_all_bean.result.equals("1")) {
                            ToastUtil.showToast(context, shangpin_all_bean.resultNote);
                            return;
                        }
                        classificationlist = shangpin_all_bean.classificationlist;
                        showPopup(0);
                    }
                });*/
        mListType.clear();
            mListType.add("单品");
            mListType.add("全部");
            mListType.add("多品");
        showPopup(0);
    }
    private void getzhinengpaixu(){//只能排序
        /*Map<String, String> params = new HashMap<>();
        params.put("cmd", "activityzhinenglist");
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Qianggou_Activity.this, e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Shangpin_all_Bean shangpin_all_bean = gson.fromJson(response, Shangpin_all_Bean.class);
                        if (shangpin_all_bean.result.equals("1")) {
                            ToastUtil.showToast(context, shangpin_all_bean.resultNote);
                            return;
                        }
                        classificationlist = shangpin_all_bean.classificationlist;
                        showPopup(0);
                    }
                });*/
        mListType.clear();
            mListType.add("单品");
            mListType.add("全部");
            mListType.add("多品");
            mListType.add("最新活动");
        showPopup(2);

    }
    public void showPopup(final int code) {
        mycode = code;
        ListView contentView = new ListView(context);
        popupWindow = new PopupWindow(contentView,
                    getWindowManager().getDefaultDisplay().getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        contentView.setBackgroundColor(0XFFEEEEEE);
        contentView.setDivider(new ColorDrawable(getResources().getColor(R.color.gray)));
        contentView.setDividerHeight(getResources().getDimensionPixelSize(R.dimen.high));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                R.layout.item_main_popup_list, mListType);
        contentView.setAdapter(adapter);
        popupWindow.setOutsideTouchable(true);// 设置此数获得焦点，否则无法参点击
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override

            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }

        });
        popupWindow.showAsDropDown(tv_shangpin_all);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        contentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                popupWindow.dismiss();
                if (code==0) {
                    tv_shangpin_all.setText(adapter.getItem(position));
                    positionid=position;
                    getfeileialldata(position);
                }else if (code==1){
                    tv_shangpin_fujin.setText(adapter.getItem(position));
                    fujinId = fujinlist.get(position).fujinId;
                    getjulidata(fujinId);
                }else if (code==2){
                    positionid=position;
                    getpaixudata(position);
                }
            }
        });
    }

    private void getfeileialldata(int id){//分类全部列表
        /*cmd:”activityfenleilist”
        activitycategoryId:“12”          // 0单品 1 全部 2 多品
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
/*        params.put("cmd", "activityfenleilist");
        params.put("activitycategoryId", "12");
        params.put("nowPage",nowPage+"");*/
        String json="{\"cmd\":\"activityfenleilist\",\"activitycategoryId\":\"" + id + "\",\"nowPage\":\"" + nowPage + "\"}";
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
                        ToastUtil.showToast(Qianggou_Activity.this, e.getLocalizedMessage());
                        dialog.dismiss();
                        prlv_qiugou_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Dianpu_huodong_Bean dianpu_huodong_bean = gson.fromJson(response, Dianpu_huodong_Bean.class);
                        if (dianpu_huodong_bean.result.equals("1")) {
                            ToastUtil.showToast(context, dianpu_huodong_bean.resultNote);
                            dialog.dismiss();
                            prlv_qiugou_list.onRefreshComplete();
                            return;
                        }
                        if (Integer.parseInt(dianpu_huodong_bean.totalPage)< nowPage){
                            ToastUtil.showToast(context,"没有更多了");
                            dialog.dismiss();
                            prlv_qiugou_list.onRefreshComplete();
                            return;
                        }
                        List<Home_Bean.Huodongmodel> huodongmodel = dianpu_huodong_bean.huodongmodel;
                        huodong.addAll(huodongmodel);
                        home_list_qianggou_adapter.setHuodongmodel(huodong);
                        home_list_qianggou_adapter.notifyDataSetChanged();
                        dialog.dismiss();
                        prlv_qiugou_list.onRefreshComplete();
                    }
                });
    }
    private void getpaixudata(int activityzhinengId) {//只能排序数据
        /*cmd:”activityzhinenggoods”
        activityzhinengId:“12”  //0 单品 1 全部 2 多品  3 最新活动
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "activityzhinenggoods");
        params.put("activityzhinengId", activityzhinengId);
        params.put("nowPage",nowPage+"");*/
        String json="{\"cmd\":\"activityzhinenggoods\",\"activityzhinengId\":\"" + activityzhinengId + "\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
                OkHttpUtils.post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Qianggou_Activity.this, e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Dianpu_huodong_Bean dianpu_huodong_bean = gson.fromJson(response, Dianpu_huodong_Bean.class);
                        if (dianpu_huodong_bean.result.equals("1")) {
                            ToastUtil.showToast(context, dianpu_huodong_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(dianpu_huodong_bean.totalPage)< nowPage){
                            ToastUtil.showToast(context,"没有更多了");
                            prlv_qiugou_list.onRefreshComplete();
                            return;
                        }
                        List<Home_Bean.Huodongmodel> huodongmodel = dianpu_huodong_bean.huodongmodel;
                        huodong.addAll(huodongmodel);
                        home_list_qianggou_adapter.setHuodongmodel(huodong);
                        home_list_qianggou_adapter.notifyDataSetChanged();
                        prlv_qiugou_list.onRefreshComplete();
                    }
                });
    }
    private void getjuli(){//获取距离
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "getfujinlist");*/
        String json="{\"cmd\":\"getfujinlist\"}";
        params.put("json", json);
               OkHttpUtils. post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Qianggou_Activity.this, e.getLocalizedMessage());
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Huoqu_Juli_Bean huoqu_juli_bean = gson.fromJson(response, Huoqu_Juli_Bean.class);
                        if (huoqu_juli_bean.result.equals("1")) {
                            ToastUtil.showToast(context, huoqu_juli_bean.resultNote);
                            return;
                        }
                        fujinlist = huoqu_juli_bean.fujinlist;
                        mListType.clear();
                        for (int i = 0; i <fujinlist.size() ; i++) {
                            mListType.add(fujinlist.get(i).fujinname);
                        }
                        showPopup(1);
                    }
                });
    }
    private void getjulidata(String id){
     /*   cmd:”activityfujingoods”
        fujinId:“12”          //附近距离id
        lat: 34.345345                 // 用户的纬度
        lon：120.23432                // 用户的经度
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "activityfujingoods");
        params.put("fujinId", id);
        params.put("lat", id);
        params.put("lon", id);
        params.put("nowPage",nowPage+"");*/
        String json="{\"cmd\":\"activityfujingoods\",\"fujinId\":\"" + id + "\",\"lat\":\"" + lat + "\"" +
                ",\"lon\":\"" + lon + "\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
                OkHttpUtils.post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Qianggou_Activity.this, e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Dianpu_huodong_Bean dianpu_huodong_bean = gson.fromJson(response, Dianpu_huodong_Bean.class);
                        if (dianpu_huodong_bean.result.equals("1")) {
                            ToastUtil.showToast(context, dianpu_huodong_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(dianpu_huodong_bean.totalPage)< nowPage){
                            ToastUtil.showToast(context,"没有更多了");
                            prlv_qiugou_list.onRefreshComplete();
                            return;
                        }
                        List<Home_Bean.Huodongmodel> huodongmodel = dianpu_huodong_bean.huodongmodel;
                        huodong.addAll(huodongmodel);
                        home_list_qianggou_adapter.setHuodongmodel(huodong);
                        home_list_qianggou_adapter.notifyDataSetChanged();
                        prlv_qiugou_list.onRefreshComplete();
                    }
                });
    }
}
