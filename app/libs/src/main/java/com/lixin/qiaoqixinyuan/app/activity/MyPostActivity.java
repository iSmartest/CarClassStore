package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.MyPostAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.Geren_tiezi_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Home_Bean;
import com.lixin.qiaoqixinyuan.app.util.Md5Util;
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

public class MyPostActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private PullToRefreshListView prlv_mypost;
    private MyPostAdapter adapter;
    private List<Home_Bean.Tiebamodel> tieba=new ArrayList<>();
    private int nowPage=1;
    private String uid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        initView();
        initData();
        initLiserner();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        prlv_mypost = (PullToRefreshListView) findViewById(R.id.prlv_mypost);
        prlv_mypost.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initData() {
        tv_title.setText("我的帖子");
        uid= MyApplication.getuId();
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        adapter =new MyPostAdapter(context,dialog,1);
        prlv_mypost.setAdapter(adapter);
        getdata();
    }

    private void initLiserner() {
        iv_turnback.setOnClickListener(this);
        prlv_mypost.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage=1;
                tieba.clear();
                getdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getdata();
            }
        });
        prlv_mypost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle=new Bundle();
                bundle.putString("tiebaId",tieba.get(i).tiebaId);
                MyApplication.openActivity(context,Tieba_xiangqing_Activity.class,bundle);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
        }
    }
    private void getdata(){
       /* cmd:”releasehetieba”
        uid:"12"   ////用户id
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
       /* cmd:”releasehetieba”
        uid:"33"    //用户id
        releaseuid:"12"   //个人主页的个人id  / 个人中心 和uid相同
        nowPage:"1"     //当前页
        token:    [JPUSHService registrationID]   //推送token*/
        String json="{\"cmd\":\"releasehetieba\",\"uid\":\"" + uid + "\"," +
                "\"nowPage\":\"" + nowPage + "\",\"token\":\"" + token + "\"" +
                ",\"releaseuid\":\"" + uid + "\"}";
        params.put("json",json);
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
                        prlv_mypost.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Geren_tiezi_Bean geren_tiezi_bean = gson.fromJson(response, Geren_tiezi_Bean.class);
                        prlv_mypost.onRefreshComplete();
                        if (geren_tiezi_bean.result.equals("1")){
                            ToastUtil.showToast(context,geren_tiezi_bean.resultNote);
                            adapter.notifyDataSetChanged();
                            prlv_mypost.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }if (Integer.parseInt(geren_tiezi_bean.totalPage)<nowPage){
                            ToastUtil.showToast(context,"没有更多了");
                            adapter.notifyDataSetChanged();
                            prlv_mypost.onRefreshComplete();
                            dialog.dismiss();
                            return;
                        }
                        List<Home_Bean.Tiebamodel> tiebamodel = geren_tiezi_bean.tiebamodel;
                        tieba.addAll(tiebamodel);
                        adapter.setTieba(tieba);
                        adapter.notifyDataSetChanged();
                        prlv_mypost.onRefreshComplete();
                        dialog.dismiss();
                    }
                });
    }
}
