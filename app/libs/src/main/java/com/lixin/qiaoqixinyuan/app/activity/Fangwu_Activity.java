package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Home_fangwu_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Fangwu_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Fangwu_fenlei_Bean;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.MyViewGroup;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Fangwu_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ImageView iv_basesearch_shuru;
    private TextView tv_fangwu_new;
    private TextView tv_fangwu_fenlei;
    private LinearLayout activity_fangwu_;
    private PopupWindow popuWindow;
    private View contentView;
    private TextView tv_pop_in;
    private TextView tv_pop_out;
    private PullToRefreshListView prlv_fangwu_list;
    private int nowPage = 1;
    private List<Fangwu_Bean.Housinginnewslist> housinginnews = new ArrayList<>();
    private Home_fangwu_Adapter home_fangwu_adapter;
    private MyViewGroup viegroup_fangwu_fenlei;
    private TextView fangwu_fenlei_search;
    private LinearLayout fangwu_fenlei;
    private int code = 0;
    private String housclassid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangwu_);
        initView();
        getdata();
    }

    private void initView() {
        home_fangwu_adapter = new Home_fangwu_Adapter(dialog,0);
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        iv_basesearch_back.setOnClickListener(this);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        et_basesearch.setHint("搜索房屋信息");
        iv_basesearch_shuru = (ImageView) findViewById(R.id.iv_basesearch_shuru);
        iv_basesearch_shuru.setOnClickListener(this);
        tv_fangwu_new = (TextView) findViewById(R.id.tv_fangwu_new);
        tv_fangwu_new.setOnClickListener(this);
        tv_fangwu_fenlei = (TextView) findViewById(R.id.tv_fangwu_fenlei);
        tv_fangwu_fenlei.setOnClickListener(this);
        activity_fangwu_ = (LinearLayout) findViewById(R.id.activity_fangwu_);
        iv_basesearch_shuru.setVisibility(View.VISIBLE);
        prlv_fangwu_list = (PullToRefreshListView) findViewById(R.id.prlv_fangwu_list);
        prlv_fangwu_list.setMode(PullToRefreshBase.Mode.BOTH);
        prlv_fangwu_list.setAdapter(home_fangwu_adapter);
        viegroup_fangwu_fenlei = (MyViewGroup) findViewById(R.id.viegroup_fangwu_fenlei);
        viegroup_fangwu_fenlei.setOnClickListener(this);
        fangwu_fenlei_search = (TextView) findViewById(R.id.fangwu_fenlei_search);
        fangwu_fenlei_search.setOnClickListener(this);
        fangwu_fenlei = (LinearLayout) findViewById(R.id.fangwu_fenlei);
        fangwu_fenlei.setOnClickListener(this);
        prlv_fangwu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /* *//*cmd:”housdetail”
                housinginnewsid:"12"   //房屋信息id
                housclassid:"12"   //房屋分类id
                type:"0"   //0 出售   1 求租*//*
                Intent intent = new Intent(context, Fangwu_xiangqing_Activity.class);
                Intent intent1 = new Intent(context, Fangwu_qiuzu_Activity.class);
                Fangwu_Bean.Housinginnewslist housinginnewslist = housinginnews.get(i);
                intent.putExtra("housinginnewsid", housinginnewslist.housinginnewsid);
                intent.putExtra("housclassid", housinginnewslist.housclassid);
                intent.putExtra("type", housinginnewslist.type);
                intent1.putExtra("housinginnewsid", housinginnewslist.housinginnewsid);
                intent1.putExtra("housclassid", housinginnewslist.housclassid);
                intent1.putExtra("type", housinginnewslist.type);
                if (housinginnewslist.type.equals("0")) {
                    startActivity(intent);
                } else {
                    startActivity(intent1);
                }*/
                Intent intent = new Intent(context, Fangwu_xiangqing_Activity.class);
                intent.putExtra("housinginnewsid",housinginnews.get(i-1).housinginnewsid);
                intent.putExtra("type",housinginnews.get(i-1).type);
                startActivity(intent);
            }
        });
        prlv_fangwu_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (code == 0) {
                    nowPage = 1;
                    housinginnews.clear();
                    getdata();
                } else if (code == 1) {
                    nowPage = 1;
                    housinginnews.clear();
                    getfenleisearch();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (code == 0) {
                    nowPage++;
                    getdata();
                } else if (code == 1) {
                    nowPage++;
                    getfenleisearch();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_basesearch_back:
                finish();
                break;
            case R.id.iv_basesearch_shuru:
                initPopuWindow(view);
                break;
            case R.id.tv_pop_in:
                Intent intent = new Intent(this, Fangwu_qiugou_Activity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.tv_pop_out:
                Intent intent1 = new Intent(this, Fangwu_qiugou_Activity.class);
                intent1.putExtra("type", "1");
                startActivity(intent1);
                break;
            case R.id.tv_fangwu_new:
                tv_fangwu_new.setTextColor(getResources().getColor(R.color.theme));
                tv_fangwu_fenlei.setTextColor(Color.BLACK);
                fangwu_fenlei.setVisibility(View.GONE);
                prlv_fangwu_list.setVisibility(View.VISIBLE);
                code = 0;
                nowPage = 1;
                housinginnews.clear();
                getdata();
                break;
            case R.id.tv_fangwu_fenlei:
                tv_fangwu_new.setTextColor(Color.BLACK);
                tv_fangwu_fenlei.setTextColor(getResources().getColor(R.color.theme));
                fangwu_fenlei.setVisibility(View.VISIBLE);
                prlv_fangwu_list.setVisibility(View.GONE);
                code = 1;
                nowPage = 1;
                housinginnews.clear();
                viegroup_fangwu_fenlei.removeAllViews();
                getfenleidata();
                break;
        }
    }

    private void initPopuWindow(View parent) {
        if (popuWindow == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            contentView = mLayoutInflater.inflate(R.layout.popuwindow, null);
            popuWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        tv_pop_in = (TextView) contentView.findViewById(R.id.tv_pop_in);
        tv_pop_in.setOnClickListener(this);
        tv_pop_out = (TextView) contentView.findViewById(R.id.tv_pop_out);
        tv_pop_out.setOnClickListener(this);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popuWindow.setBackgroundDrawable(cd);
        //产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        popuWindow.setOutsideTouchable(true);
        popuWindow.setFocusable(true);
        popuWindow.showAtLocation((View) parent.getParent(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popuWindow.update();
        popuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void getdata() {
     /*   cmd:”housinginnewslist”
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
      /*  params.put("cmd", "housinginnewslist");
        params.put("nowPage", nowPage + "");*/
        String json="{\"cmd\":\"housinginnewslist\",\"nowPage\":\"" + nowPage + "\"}";
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
                        prlv_fangwu_list.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Fangwu_Bean fangwu_bean = gson.fromJson(response, Fangwu_Bean.class);
                        if (fangwu_bean.result.equals("1")) {
                            ToastUtil.showToast(context, fangwu_bean.resultNote);
                            prlv_fangwu_list.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        if (Integer.parseInt(fangwu_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            prlv_fangwu_list.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        List<Fangwu_Bean.Housinginnewslist> housinginnewslist = fangwu_bean.housinginnewslist;
                        housinginnews.addAll(housinginnewslist);
                        home_fangwu_adapter.setHousinginnews(housinginnews);
                        home_fangwu_adapter.notifyDataSetChanged();
                        prlv_fangwu_list.onRefreshComplete();
                        dialog.dismiss();
                    }
                });
    }

    private void getsearchdata() {
     /*  cmd:”searchhouslist”
    housesearch:"单间"    //搜索内容
    nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "searchhouslist");
        params.put("housesearch", "单间");
        params.put("nowPage", nowPage + "");*/
        String json="{\"cmd\":\"searchhouslist\",\"housesearch\":\"" + et_basesearch.getText().toString() + "\",\"nowPage\":\"" + nowPage + "\"}";
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
                        prlv_fangwu_list.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Fangwu_Bean fangwu_bean = gson.fromJson(response, Fangwu_Bean.class);
                        if (fangwu_bean.result.equals("1")) {
                            ToastUtil.showToast(context, fangwu_bean.resultNote);
                            prlv_fangwu_list.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        if (Integer.parseInt(fangwu_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            prlv_fangwu_list.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        List<Fangwu_Bean.Housinginnewslist> housinginnewslist = fangwu_bean.housinginnewslist;
                        housinginnews.addAll(housinginnewslist);
                        home_fangwu_adapter.setHousinginnews(housinginnews);
                        home_fangwu_adapter.notifyDataSetChanged();
                        prlv_fangwu_list.onRefreshComplete();
                        dialog.dismiss();
                    }
                });
    }

    private void getfenleidata() {
        /*cmd:”housclasslist”*/
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "housclasslist");*/
        String json="{\"cmd\":\"housclasslist\"}";
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
                        final Fangwu_fenlei_Bean fangwu_fenlei_bean = gson.fromJson(response, Fangwu_fenlei_Bean.class);
                        if (fangwu_fenlei_bean.result.equals("1")) {
                            ToastUtil.showToast(context, fangwu_fenlei_bean.resultNote);
                        }
                        for (int i = 0; i < fangwu_fenlei_bean.housclasslist.size(); i++) {
                            View inflate = View.inflate(context, R.layout.item_textview, null);
                            TextView tv_item_text = (TextView) inflate.findViewById(R.id.tv_item_text);
                            final Fangwu_fenlei_Bean.Housclasslist housclasslist = fangwu_fenlei_bean.housclasslist.get(i);
                            tv_item_text.setText(housclasslist.housclassname);
                            tv_item_text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    housclassid = housclasslist.housclassid;
                                    fangwu_fenlei.setVisibility(View.GONE);
                                    prlv_fangwu_list.setVisibility(View.VISIBLE);
                                    getfenleisearch();
                                }
                            });

                            viegroup_fangwu_fenlei.addView(inflate);
                        }
                    }
                });
    }

    private void getfenleisearch() {
           /* cmd:”classhouslist”
                                    housclassid:"12"   //房屋分类id
                                    nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "classhouslist");
        params.put("housclassid", "12");
        params.put("nowPage", nowPage1 + "");*/
        String json="{\"cmd\":\"classhouslist\",\"housclassid\":\"" + housclassid + "\",\"nowPage\":\"" + nowPage + "\"}";
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
                        prlv_fangwu_list.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Fangwu_Bean fangwu_bean = gson.fromJson(response, Fangwu_Bean.class);
                        if (fangwu_bean.result.equals("1")) {
                            ToastUtil.showToast(context, fangwu_bean.resultNote);
                            prlv_fangwu_list.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        if (Integer.parseInt(fangwu_bean.totalPage) < nowPage) {
                            prlv_fangwu_list.onRefreshComplete();
                            dialog.dismiss();
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        List<Fangwu_Bean.Housinginnewslist> housinginnewslist = fangwu_bean.housinginnewslist;
                        housinginnews.addAll(housinginnewslist);
                        home_fangwu_adapter.setHousinginnews(housinginnews);
                        home_fangwu_adapter.notifyDataSetChanged();
                        prlv_fangwu_list.onRefreshComplete();
                        dialog.dismiss();
                    }
                });
    }
}

