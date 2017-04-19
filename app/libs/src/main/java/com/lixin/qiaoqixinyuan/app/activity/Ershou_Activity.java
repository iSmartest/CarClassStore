package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Home_ershou_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Ershou_Bean;
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

import static com.lixin.qiaoqixinyuan.R.id.tv_fangwu_fenlei;
import static com.lixin.qiaoqixinyuan.R.id.tv_fangwu_new;
import static com.lixin.qiaoqixinyuan.R.id.view;import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Ershou_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ImageView iv_basesearch_shuru;
    private TextView tv_ershou_new;
    private TextView tv_ershou_fenlei;
    private LinearLayout activity_fangwu_;
    private PopupWindow popuWindow;
    private View contentView;
    private TextView tv_pop_in;
    private TextView tv_pop_out;
    private PullToRefreshListView prlv_ershou_list;
    private int nowPage = 1;
    private int code = 0;
    private List<Ershou_Bean.Secondnewslist> secondnews = new ArrayList<>();
    private Home_ershou_Adapter home_ershou_adapter;
    private MyViewGroup viegroup_fangwu_fenlei;
    private TextView fangwu_fenlei_search;
    private LinearLayout fangwu_fenlei;
   private String nub=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ershou_);
        initView();
        getdata();
        getfenleidata();
    }

    private void initView() {
        home_ershou_adapter = new Home_ershou_Adapter(dialog,0);
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        iv_basesearch_back.setOnClickListener(this);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        et_basesearch.setHint("搜索二手信息");
        iv_basesearch_shuru = (ImageView) findViewById(R.id.iv_basesearch_shuru);
        iv_basesearch_shuru.setOnClickListener(this);
        tv_ershou_new = (TextView) findViewById(R.id.tv_ershou_new);
        tv_ershou_new.setOnClickListener(this);
        tv_ershou_fenlei = (TextView) findViewById(R.id.tv_ershou_fenlei);
        tv_ershou_fenlei.setOnClickListener(this);
        activity_fangwu_ = (LinearLayout) findViewById(R.id.activity_fangwu_);
        iv_basesearch_shuru.setVisibility(View.VISIBLE);
        prlv_ershou_list = (PullToRefreshListView) findViewById(R.id.prlv_ershou_list);
        prlv_ershou_list.setMode(PullToRefreshBase.Mode.BOTH);
        prlv_ershou_list.setAdapter(home_ershou_adapter);
        et_basesearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_basesearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    String searchContext = et_basesearch.getText().toString().trim();
                    if (TextUtils.isEmpty(searchContext)) {
                        ToastUtil.showToast(context, "搜索内容为空，请输入");
                    } else {
                        // 调用搜索方法
                        code = 1;
                        secondnews.clear();
                        getsearchdata();
                    }
                }
                return false;
            }
        });
        prlv_ershou_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (code == 0) {
                    nowPage = 1;
                    secondnews.clear();
                    getdata();
                } else if (code == 1) {
                    nowPage = 1;
                    secondnews.clear();
                    getsearchdata();
                }else if (code == 2) {
                    nowPage = 1;
                    secondnews.clear();
                    getfenlei_searchdata();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                prlv_ershou_list.onRefreshComplete();
                if (code == 0) {
                    nowPage++;
                    getdata();
                } else if (code == 1) {
                    nowPage++;
                    getsearchdata();
                }else if (code == 1) {
                    nowPage++;
                }
            }
        });
        viegroup_fangwu_fenlei = (MyViewGroup) findViewById(R.id.viegroup_fangwu_fenlei);
        viegroup_fangwu_fenlei.setOnClickListener(this);
        fangwu_fenlei_search = (TextView) findViewById(R.id.fangwu_fenlei_search);
        fangwu_fenlei_search.setOnClickListener(this);
        fangwu_fenlei = (LinearLayout) findViewById(R.id.fangwu_fenlei);
        fangwu_fenlei.setOnClickListener(this);
        prlv_ershou_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(context, Ershou_xiangqing_Activity.class);
                    intent.putExtra("secondnewsid",secondnews.get(i-1).secondnewsid);
                    intent.putExtra("secondtypeid",secondnews.get(i-1).secondtypeid);
                    startActivity(intent);
                /*else {
                    Intent intent = new Intent(context, Ershou_qiugou_Activity.class);
                    intent.putExtra("secondnewsid",secondnews.get(i).secondnewsid);
                    startActivity(intent);
                }*/
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
                Intent intent = new Intent(this, Ershou_fabu_Activity.class);
                intent.putExtra("secondtypeid","1");
                startActivity(intent);
                break;
            case R.id.tv_pop_out:
                Intent intent1 = new Intent(this, Ershou_fabu_Activity.class);
                intent1.putExtra("secondtypeid","0");
                startActivity(intent1);
                break;
            case R.id.tv_ershou_new:
                code = 0;
                nowPage = 1;
                secondnews.clear();
                fangwu_fenlei.setVisibility(View.GONE);
                prlv_ershou_list.setVisibility(View.VISIBLE);
                tv_ershou_new.setTextColor(getResources().getColor(R.color.theme));
                tv_ershou_fenlei.setTextColor(Color.BLACK);
                getdata();
                break;
            case R.id.tv_ershou_fenlei:
                code = 2;
                fangwu_fenlei.setVisibility(View.VISIBLE);
                prlv_ershou_list.setVisibility(View.GONE);
                tv_ershou_new.setTextColor(Color.BLACK);
                tv_ershou_fenlei.setTextColor(getResources().getColor(R.color.theme));
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
        tv_pop_in.setText("二手求购");
        tv_pop_in.setOnClickListener(this);
        tv_pop_out = (TextView) contentView.findViewById(R.id.tv_pop_out);
        tv_pop_in.setText("二手出售");
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
      /*  cmd:”secondnewslist”
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "secondnewslist");
        params.put("nowPage", nowPage + "");*/
        String json="{\"cmd\":\"secondnewslist\",\"nowPage\":\"" + nowPage +"\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                        prlv_ershou_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Ershou_Bean ershou_bean = gson.fromJson(response, Ershou_Bean.class);
                        if (ershou_bean.result.equals("1")) {
                            ToastUtil.showToast(context, ershou_bean.resultNote);
                            prlv_ershou_list.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        if (Integer.parseInt(ershou_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            prlv_ershou_list.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        List<Ershou_Bean.Secondnewslist> secondnewslist = ershou_bean.secondnewslist;
                        secondnews.addAll(secondnewslist);
                        home_ershou_adapter.setHousinginnews(secondnews);
                        home_ershou_adapter.notifyDataSetChanged();
                        prlv_ershou_list.onRefreshComplete();
                        dialog.dismiss();
                    }
                });
    }

    private void getsearchdata() {
 /*       cmd:”searchsecondlist”
        housesearch:"单间"    //搜索内容
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
      /*  params.put("cmd", "searchsecondlist");
        params.put("housesearch", et_basesearch.getText().toString());
        params.put("nowPage", nowPage1 + "");*/
        String json="{\"cmd\":\"searchsecondlist\",\"housesearch\":\"" + et_basesearch.getText().toString() + "\",\"nowPage\":\"" + nowPage +"\"}";
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
                        prlv_ershou_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Ershou_Bean ershou_bean = gson.fromJson(response, Ershou_Bean.class);
                        if (ershou_bean.result.equals("1")) {
                            ToastUtil.showToast(context, ershou_bean.resultNote);
                            prlv_ershou_list.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        if (Integer.parseInt(ershou_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            prlv_ershou_list.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        List<Ershou_Bean.Secondnewslist> secondnewslist = ershou_bean.secondnewslist;
                        secondnews.addAll(secondnewslist);
                        home_ershou_adapter.setHousinginnews(secondnews);
                        home_ershou_adapter.notifyDataSetChanged();
                        prlv_ershou_list.onRefreshComplete();
                        dialog.dismiss();
                    }
                });
    }
 private void getfenlei_searchdata(){
     /*cmd:”secondtypelist”
     secondtypeid:"0"   //0 出售   1 求购   app写死
     nowPage:"1"     //当前页*/
     Map<String, String> params = new HashMap<>();
    /* params.put("cmd", "secondtypelist");
     params.put("secondtypeid", nub);
     params.put("nowPage", nowPage2+ "");*/
     String json="{\"cmd\":\"secondtypelist\",\"secondtypeid\":\"" + nub + "\",\"nowPage\":\"" + nowPage +"\"}";
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
                     prlv_ershou_list.onRefreshComplete();
                 }

                 @Override
                 public void onResponse(String response, int id) {
                     Gson gson = new Gson();
                     Ershou_Bean ershou_bean = gson.fromJson(response, Ershou_Bean.class);
                     if (ershou_bean.result.equals("1")) {
                         ToastUtil.showToast(context, ershou_bean.resultNote);
                         prlv_ershou_list.onRefreshComplete();
                         dialog.dismiss();
                         return;
                     }
                     if (Integer.parseInt(ershou_bean.totalPage) < nowPage) {
                         ToastUtil.showToast(context, "没有更多了");
                         prlv_ershou_list.onRefreshComplete();
                         dialog.dismiss();
                         return;
                     }
                     List<Ershou_Bean.Secondnewslist> secondnewslist = ershou_bean.secondnewslist;
                     secondnews.addAll(secondnewslist);
                     home_ershou_adapter.setHousinginnews(secondnews);
                     home_ershou_adapter.notifyDataSetChanged();
                     prlv_ershou_list.onRefreshComplete();
                     dialog.dismiss();
                 }
             });
 }
    private void getfenleidata() {
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
                    nub=finalI+"";
                    secondnews.clear();
                    fangwu_fenlei.setVisibility(View.GONE);
                    prlv_ershou_list.setVisibility(View.VISIBLE);
                    getfenlei_searchdata();
                }
            });
            viegroup_fangwu_fenlei.addView(tv_item_text);
        }
    }
}
