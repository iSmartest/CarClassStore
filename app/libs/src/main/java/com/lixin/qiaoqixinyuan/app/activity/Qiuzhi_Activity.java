package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
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
import com.lixin.qiaoqixinyuan.app.adapter.Home_qiuzhi_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Qiuzhi_Bean;
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

import okhttp3.Call;

import static com.lixin.qiaoqixinyuan.R.id.tv_ershou_fenlei;
import static com.lixin.qiaoqixinyuan.R.id.tv_ershou_new;
import static com.lixin.qiaoqixinyuan.R.id.tv_item_text;
import static com.lixin.qiaoqixinyuan.R.id.view;import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Qiuzhi_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ImageView iv_basesearch_shuru;
    private TextView tv_qiuzhi_new;
    private TextView tv_qiuzhi_fenlei;
    private LinearLayout activity_fangwu_;
    private PopupWindow popuWindow;
    private View contentView;
    private TextView tv_pop_in;
    private TextView tv_pop_out;
    private PullToRefreshListView prlv_qiuzhi_list;
    private int nowPage = 1;
    private int code = 0;
    private List<Qiuzhi_Bean.Jobhuntinglist> jobhunting = new ArrayList<>();
    private Home_qiuzhi_Adapter home_qiuzhi_adapter;
    private MyViewGroup viegroup_fangwu_fenlei;
    private TextView fangwu_fenlei_search;
    private LinearLayout fangwu_fenlei;
   private String jobhuntingtypeid=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiuzhi_);
        initView();
        getfenleidata();
        getdata();
    }

    private void initView() {
        home_qiuzhi_adapter = new Home_qiuzhi_Adapter(dialog,0);
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        iv_basesearch_back.setOnClickListener(this);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        et_basesearch.setHint("输入求职信息");
        iv_basesearch_shuru = (ImageView) findViewById(R.id.iv_basesearch_shuru);
        iv_basesearch_shuru.setOnClickListener(this);
        tv_qiuzhi_new = (TextView) findViewById(R.id.tv_qiuzhi_new);
        tv_qiuzhi_new.setOnClickListener(this);
        tv_qiuzhi_fenlei = (TextView) findViewById(R.id.tv_qiuzhi_fenlei);
        tv_qiuzhi_fenlei.setOnClickListener(this);
        activity_fangwu_ = (LinearLayout) findViewById(R.id.activity_fangwu_);
        iv_basesearch_shuru.setVisibility(View.VISIBLE);
        prlv_qiuzhi_list = (PullToRefreshListView) findViewById(R.id.prlv_qiuzhi_list);
        prlv_qiuzhi_list.setMode(PullToRefreshBase.Mode.BOTH);
        prlv_qiuzhi_list.setAdapter(home_qiuzhi_adapter);
        viegroup_fangwu_fenlei = (MyViewGroup) findViewById(R.id.viegroup_fangwu_fenlei);
        viegroup_fangwu_fenlei.setOnClickListener(this);
        fangwu_fenlei_search = (TextView) findViewById(R.id.fangwu_fenlei_search);
        fangwu_fenlei_search.setOnClickListener(this);
        fangwu_fenlei = (LinearLayout) findViewById(R.id.fangwu_fenlei);
        fangwu_fenlei.setOnClickListener(this);
        prlv_qiuzhi_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (jobhunting.get(i-1).jobhuntingtypeid.equals("0")) {
                    Intent intent = new Intent(context, Qiuzhi_zhaopin_Activity.class);
                    intent.putExtra("jobhuntingid",jobhunting.get(i-1).jobhuntingid);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(context, Qiuzhi_qiuzhi_Activity.class);
                    intent.putExtra("jobhuntingid",jobhunting.get(i-1).jobhuntingid);
                    startActivity(intent);
                }
            }
        });
        prlv_qiuzhi_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (code == 0) {
                    nowPage = 1;
                    jobhunting.clear();
                    getdata();
                } else if (code == 1) {
                    nowPage = 1;
                    jobhunting.clear();
                    getsearchdata();
                }else if (code == 2) {
                    nowPage = 1;
                    jobhunting.clear();
                    getfenleisearch();
                }
                prlv_qiuzhi_list.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (code == 0) {
                    nowPage++;
                    getdata();
                } else if (code == 1) {
                    nowPage++;
                    getsearchdata();
                }else if (code == 2) {
                    nowPage++;
                    getfenleisearch();
                }
                prlv_qiuzhi_list.onRefreshComplete();
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
                code=1;
                initPopuWindow(view);
                break;
            case R.id.tv_pop_in:
                Intent intent = new Intent(this, Zhaopin_fabu_Activity.class);
                startActivity(intent);
                break;
            case R.id.tv_pop_out:
                startActivity(new Intent(this, Qiuzhi_fabu_Activity.class));
                break;
            case R.id.tv_qiuzhi_new:
                code=0;
                nowPage=1;
                jobhunting.clear();
                prlv_qiuzhi_list.setVisibility(View.VISIBLE);
                fangwu_fenlei.setVisibility(View.GONE);
                tv_qiuzhi_new.setTextColor(getResources().getColor(R.color.theme));
                tv_qiuzhi_fenlei.setTextColor(Color.BLACK);
                break;
            case R.id.tv_qiuzhi_fenlei:
                code=2;
                prlv_qiuzhi_list.setVisibility(View.GONE);
                fangwu_fenlei.setVisibility(View.VISIBLE );
                tv_qiuzhi_fenlei.setTextColor(getResources().getColor(R.color.theme));
                tv_qiuzhi_new.setTextColor(Color.BLACK);
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
        tv_pop_in.setText("招聘发布");
        tv_pop_in.setOnClickListener(this);
        tv_pop_out = (TextView) contentView.findViewById(R.id.tv_pop_out);
        tv_pop_out.setText("求职发布");
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
       /* cmd:”jobhuntinglist”
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "jobhuntinglist");
        params.put("nowPage", nowPage + "");*/
        String json="{\"cmd\":\"jobhuntinglist\",\"nowPage\":\"" + nowPage + "\"}";
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
                        prlv_qiuzhi_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        prlv_qiuzhi_list.onRefreshComplete();
                        Qiuzhi_Bean qiuzhi_bean = gson.fromJson(response, Qiuzhi_Bean.class);
                        if (qiuzhi_bean.result.equals("1")) {
                            ToastUtil.showToast(context, qiuzhi_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(qiuzhi_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        List<Qiuzhi_Bean.Jobhuntinglist> jobhuntinglist = qiuzhi_bean.jobhuntinglist;
                        jobhunting.addAll(jobhuntinglist);
                        home_qiuzhi_adapter.setHousinginnews(jobhunting);
                        home_qiuzhi_adapter.notifyDataSetChanged();
                    }
                });
    }

    private void getsearchdata() {
      /*  cmd:”searchjobhunting”
        searchjobtext:"招聘"    //搜索内容
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
    /*    params.put("cmd", "searchjobhunting");
        params.put("searchjobtext", et_basesearch.getText().toString());
        params.put("nowPage", nowPage1 + "");*/
        String json="{\"cmd\":\"searchjobhunting\",\"searchjobtext\":\"" + et_basesearch.getText().toString() + "\",\"nowPage\":\"" + nowPage + "\"}";
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
                        prlv_qiuzhi_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        prlv_qiuzhi_list.onRefreshComplete();
                        Qiuzhi_Bean qiuzhi_bean = gson.fromJson(response, Qiuzhi_Bean.class);
                        if (qiuzhi_bean.result.equals("1")) {
                            ToastUtil.showToast(context, qiuzhi_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(qiuzhi_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        List<Qiuzhi_Bean.Jobhuntinglist> jobhuntinglist = qiuzhi_bean.jobhuntinglist;
                        jobhunting.addAll(jobhuntinglist);
                        home_qiuzhi_adapter.setHousinginnews(jobhunting);
                        home_qiuzhi_adapter.notifyDataSetChanged();
                    }
                });
    }

    private void getfenleisearch() {
       /* cmd:”searchjobhunting”
        jobhuntingtypeid:"0"   //0 招聘   1 求职   app写死
        jobhuntingid:"12"   //招聘求职信息id
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
  /*      params.put("cmd", "searchjobhunting");
        params.put("jobhuntingtypeid", jobhuntingtypeid);
        params.put("nowPage", nowPage2 + "");*/
        String json="{\"cmd\":\"searchjobhunting\",\"jobhuntingtypeid\":\"" + jobhuntingtypeid + "\",\"nowPage\":\"" + nowPage + "\"}";
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
                        prlv_qiuzhi_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        prlv_qiuzhi_list.onRefreshComplete();
                        Qiuzhi_Bean qiuzhi_bean = gson.fromJson(response, Qiuzhi_Bean.class);
                        if (qiuzhi_bean.result.equals("1")) {
                            ToastUtil.showToast(context, qiuzhi_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(qiuzhi_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        List<Qiuzhi_Bean.Jobhuntinglist> jobhuntinglist = qiuzhi_bean.jobhuntinglist;
                        jobhunting.addAll(jobhuntinglist);
                        home_qiuzhi_adapter.setHousinginnews(jobhunting);
                        home_qiuzhi_adapter.notifyDataSetChanged();
                    }
                });
    }
    private void getfenleidata() {
        code=3;
        String str[] = new String[]{
                "出售", "求购"
        };
        for (int i = 0; i < 2; i++) {
            View inflate = View.inflate(context, R.layout.item_textview, null);
            TextView tv_item_text = (TextView) inflate.findViewById(R.id.tv_item_text);
            tv_item_text.setText(str[i]);
            final int finalI = i;
            tv_item_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobhuntingtypeid=finalI+"";
                    nowPage=1;
                    jobhunting.clear();
                    prlv_qiuzhi_list.setVisibility(View.VISIBLE);
                    fangwu_fenlei.setVisibility(View.GONE);
                    getfenleisearch();
                }
            });
            viegroup_fangwu_fenlei.addView(tv_item_text);
        }
    }
}
