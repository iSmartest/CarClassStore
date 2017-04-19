package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Home_list_qianggou_Adapter;
import com.lixin.qiaoqixinyuan.app.adapter.Shangpin_list_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Home_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Search_huodong_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_shangpin_search_Bean;
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
import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Search_huodong_list extends BaseActivity implements View.OnClickListener {

    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ImageView iv_basesearch_shuru;
    private MyListview list_search_huodong;
    private PullToRefreshListView list_search_shangpin;
    private LinearLayout activity_search_huodong_list;
    private  List<Shangjia_shangpin_search_Bean.Goodslist> goods=new ArrayList<>();
    private Shangpin_list_Adapter shangpin_list_adapter;
    private Home_list_qianggou_Adapter home_list_qianggou_adapter;
    private int nowPage=1;
    private String lat;
    private String lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_huodong_list);
        lat= SharedPreferencesUtil.getSharePreStr(context,"lat");
        lon= SharedPreferencesUtil.getSharePreStr(context,"lon");
        initView();
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            //隐藏软键盘
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
           getdata();
        }
        return super.dispatchKeyEvent(event);
    }

    private void initView() {
        shangpin_list_adapter = new Shangpin_list_Adapter();
        home_list_qianggou_adapter = new Home_list_qianggou_Adapter();
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        iv_basesearch_back.setOnClickListener(this);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        iv_basesearch_shuru = (ImageView) findViewById(R.id.iv_basesearch_shuru);
        list_search_huodong = (MyListview) findViewById(R.id.list_search_huodong);
        list_search_shangpin = (PullToRefreshListView) findViewById(R.id.list_search_shangpin);
        activity_search_huodong_list = (LinearLayout) findViewById(R.id.activity_search_huodong_list);
        list_search_shangpin.setMode(PullToRefreshBase.Mode.BOTH);
        list_search_shangpin.setAdapter(shangpin_list_adapter);
        list_search_huodong.setAdapter(home_list_qianggou_adapter);
        list_search_shangpin.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage=1;
                goods.clear();
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
      switch (view.getId()){
          case R.id.iv_basesearch_back:
              finish();
              break;

      }
    }
    private void getdata(){
        /*cmd:”searhuodonglist”      //搜索活动
        searchtext:“蛋糕”         //搜索字段
        lat: 34.345345                 // 用户的纬度
        lon：120.23432                // 用户的经度
          shangjiaId: “1”       //商家id
        nowPage:"1"     //当前页 */


        Map<String, String> params = new HashMap<>();
/*        params.put("cmd", "searhuodonglist");
        params.put("searchtext", "蛋糕");
        params.put("lat", "345345");
        params.put("lon", "23432");
        params.put("nowPage", nowPage+"");*/
        String json="{\"cmd\":\"searhuodonglist\",\"searchtext\":\"" + et_basesearch.getText().toString() + "\",\"lat\":\"" + lat + "\"" +
                ",\"lon\":\"" + lon + "\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    private List<Home_Bean.Huodongmodel> huodongmodel;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context,e.getMessage());
                        list_search_shangpin.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        list_search_shangpin.onRefreshComplete();
                        dialog.dismiss();
                        Search_huodong_Bean search_huodong_bean = gson.fromJson(response, Search_huodong_Bean.class);
                        if (search_huodong_bean.result.equals("1")){
                            ToastUtil.showToast(context,search_huodong_bean.resultNote);
                            return;
                        }
                        List<Shangjia_shangpin_search_Bean.Goodslist> goodslist = search_huodong_bean.goodslist;
                        huodongmodel = search_huodong_bean.huodongmodel;
                        goods.addAll(goodslist);
                        shangpin_list_adapter.setGoodslist(goods);
                        shangpin_list_adapter.notifyDataSetChanged();
                        home_list_qianggou_adapter.setHuodongmodel(huodongmodel);
                        home_list_qianggou_adapter.notifyDataSetChanged();
                    }
                });
    }
}
