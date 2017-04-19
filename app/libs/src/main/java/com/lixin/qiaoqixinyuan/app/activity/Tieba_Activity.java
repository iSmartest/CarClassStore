package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.MyPostAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Geren_tiezi_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Home_Bean;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Tieba_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ImageView iv_basesearch_shuru;
    private LinearLayout activity_tieba_;
    private PullToRefreshListView prlv_tieba_list;
    private MyPostAdapter adapter;
    private List<Home_Bean.Tiebamodel> tieba=new ArrayList<>();
    private int nowPage=1;
    private int code=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tieba_);
        initView();
        getdata();
    }

    private void initView() {
        adapter=new MyPostAdapter(context,dialog,0);
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        iv_basesearch_back.setOnClickListener(this);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        et_basesearch.setOnClickListener(this);
        et_basesearch.setHint("搜索帖子信息");
        iv_basesearch_shuru = (ImageView) findViewById(R.id.iv_basesearch_shuru);
        iv_basesearch_shuru.setOnClickListener(this);
        activity_tieba_ = (LinearLayout) findViewById(R.id.activity_tieba_);
        iv_basesearch_shuru.setVisibility(View.VISIBLE);
        prlv_tieba_list = (PullToRefreshListView) findViewById(R.id.prlv_tieba_list);
        prlv_tieba_list.setMode(PullToRefreshBase.Mode.BOTH);
        prlv_tieba_list.setAdapter(adapter);
        prlv_tieba_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, Tieba_xiangqing_Activity.class);
                intent.putExtra("tiebaId",tieba.get(i-1).tiebaId);
                intent.putExtra("pingluntype","0");
                intent.putExtra("messagetype","0");
                startActivity(intent);
            }
        });
        prlv_tieba_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                tieba.clear();
                nowPage=1;
                if (code==0){
                   getdata(); 
                }else {
                    getsearchdata();
                }
                
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                if (code==0){
                    getdata();
                }else {
                    getsearchdata();
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
                startActivity(new Intent(this, PublishPostActivity.class));
                break;
            case R.id.et_basesearch:
                tieba.clear();
                getsearchdata();
                break;
        }
    }
    private void getdata(){
        code=0;
   /*     cmd:”gettiebalist”
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "gettiebalist");
        params.put("nowPage", nowPage+"");*/
        String json="{\"cmd\":\"gettiebalist\",\"nowPage\":\"" + nowPage + "\"}";
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
                        ToastUtil.showToast(context,e.getMessage());
                        prlv_tieba_list.onRefreshComplete();
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        prlv_tieba_list.onRefreshComplete();
                        dialog.dismiss();
                        Geren_tiezi_Bean geren_tiezi_bean = gson.fromJson(response, Geren_tiezi_Bean.class);
                        if (geren_tiezi_bean.result.equals("1")){
                            ToastUtil.showToast(context,geren_tiezi_bean.resultNote);
                            return;
                        }if (Integer.parseInt(geren_tiezi_bean.totalPage)<nowPage){
                            prlv_tieba_list.onRefreshComplete();
                            ToastUtil.showToast(context,"没有更多了");
                            return;
                        }
                        List<Home_Bean.Tiebamodel> tiebamodel = geren_tiezi_bean.tiebamodel;
                        tieba.addAll(tiebamodel);
                        adapter.setTieba(tieba);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    private void  getsearchdata(){
        code=1;
       /* cmd:”seachtiebalist”
        tiebasearchtext:"贴吧"             //贴吧搜索内容
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "seachtiebalist");
        params.put("nowPage", nowPage+"");
        params.put("tiebasearchtext", "贴吧");*/
        String json="{\"cmd\":\"seachtiebalist\",\"nowPage\":\"" + nowPage + "\",\"tiebasearchtext\":\"" + et_basesearch.getText().toString() +"\"}";
        params.put("json", json);
        dialog.dismiss();
       OkHttpUtils.
                post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context,e.getMessage());
                        prlv_tieba_list.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        prlv_tieba_list.onRefreshComplete();
                        dialog.dismiss();
                        Geren_tiezi_Bean geren_tiezi_bean = gson.fromJson(response, Geren_tiezi_Bean.class);
                        if (geren_tiezi_bean.result.equals("1")){
                            ToastUtil.showToast(context,geren_tiezi_bean.resultNote);
                            prlv_tieba_list.onRefreshComplete();
                            return;
                        }if (Integer.parseInt(geren_tiezi_bean.totalPage)<nowPage){
                            ToastUtil.showToast(context,"没有更多了");
                            return;
                        }
                        List<Home_Bean.Tiebamodel> tiebamodel = geren_tiezi_bean.tiebamodel;
                        tieba.addAll(tiebamodel);
                        adapter.setTieba(tieba);
                        adapter.notifyDataSetChanged();
                    }
                });
                }
}


