package com.lixin.qiaoqixinyuan.app.activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Shangpin_shangjia_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Huoqu_Juli_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_shangpin_search_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Shangjialiebiao_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Shangpin_all_Bean;
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
import static android.R.id.list;
import static android.R.string.no;
import static com.lixin.qiaoqixinyuan.R.id.tv_shaixuan_fenlei;
import static com.lixin.qiaoqixinyuan.R.id.view;
import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Shangpin_Activity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_basesearch_back;
    private EditText et_basesearch;
    private ImageView iv_basesearch_shuru;
    private TextView tv_shangpin_all;
    private TextView tv_shangpin_fujin;
    private TextView tv_shangpin_paixu;
    private int nowPage = 1;
    private PullToRefreshListView shangpin_list;
    private List<Shangjialiebiao_Bean.Merchantslist> merchants=new ArrayList<>();
    private Shangpin_shangjia_Adapter shangpin_shangjia_adapter;
    private List<Shangpin_all_Bean.Classificationlist> classificationlist;
    private List<String>  mListType = new ArrayList<String>();  //类型列表
    private List<Huoqu_Juli_Bean.Fujinlist> fujinlist;
    private String lat;
    private String lon;
    private PopupWindow popupWindow;
    private int code=0;
    private String classificationId;
    private String fujinId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangpin_);
        lat= SharedPreferencesUtil.getSharePreStr(context,"lat");
        lon= SharedPreferencesUtil.getSharePreStr(context,"lon");
        initView();
        getdata();
    }
    private void initView() {
        shangpin_shangjia_adapter = new Shangpin_shangjia_Adapter();
        iv_basesearch_back = (ImageView) findViewById(R.id.iv_basesearch_back);
        iv_basesearch_back.setOnClickListener(this);
        et_basesearch = (EditText) findViewById(R.id.et_basesearch);
        et_basesearch.setOnClickListener(this);
        et_basesearch.setHint("搜索商家或商品");
        iv_basesearch_shuru = (ImageView) findViewById(R.id.iv_basesearch_shuru);
        iv_basesearch_shuru.setOnClickListener(this);
        tv_shangpin_all = (TextView) findViewById(R.id.tv_shangpin_all);
        tv_shangpin_all.setOnClickListener(this);
        tv_shangpin_fujin = (TextView) findViewById(R.id.tv_shangpin_fujin);
        tv_shangpin_fujin.setOnClickListener(this);
        tv_shangpin_paixu = (TextView) findViewById(R.id.tv_shangpin_paixu);
        tv_shangpin_paixu.setOnClickListener(this);
        shangpin_list = (PullToRefreshListView) findViewById(R.id.shangpin_list);
        shangpin_list.setMode(PullToRefreshBase.Mode.BOTH);
        shangpin_list.setAdapter(shangpin_shangjia_adapter);
        shangpin_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage=1;
                merchants.clear();
                if (code==0) {
                    getdata();
                }else if (code==1){
                    getfeileidata(classificationId);
                }else if (code==2){
                    getjulidata(fujinId);
                }else if (code==3){
                    getpaixudata();
                }

            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                if (code==0) {
                    getdata();
                }else if (code==1){
                    getfeileidata(classificationId);
                }else if (code==2){
                    getjulidata(fujinId);
                }else if (code==3){
                    getpaixudata();
                }
            }
        });
        shangpin_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, Dianpu_xiangqing_Activiyu.class);
                intent.putExtra("shangjiaid",merchants.get(i-1).shangjiaid);
                intent.putExtra("shangjianame",merchants.get(i-1).shangjianame);
                intent.putExtra("shangjiaicon",merchants.get(i-1).shangjiaicon);
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
                getfenlei();
                break;
                //startActivity(new Intent(this, ShaixuanActivity.class));
            case R.id.tv_shangpin_fujin:
                getjuli();
                //startActivity(new Intent(this, ShaixuanActivity.class));
                break;
            case R.id.tv_shangpin_paixu:
                nowPage=1;
                merchants.clear();
                code=3;
                getpaixudata();
                //startActivity(new Intent(this, ShaixuanActivity.class));
                break;
            case R.id.et_basesearch:
                Intent intent = new Intent(Shangpin_Activity.this, Search_Activity.class);
                startActivity(intent);
                break;
        }
    }
    private void getdata() {
      /*  cmd:”getmerchantslist”
        lat: 34.345345                 // 用户的纬度
        lon：120.23432                // 用户的经度
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "getmerchantslist");
        params.put("lat", "34.345345");
        params.put("lon", "120.23432");
        params.put("nowPage",nowPage+"");*/
        String json="{\"cmd\":\"getmerchantslist\",\"lat\":\"" + lat + "\",\"lon\":\""
                + lon + "\",\"nowPage\":\"" + nowPage + "\"}";
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
                        ToastUtil.showToast(Shangpin_Activity.this, e.getLocalizedMessage());
                        shangpin_list.onRefreshComplete();
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        shangpin_list.onRefreshComplete();
                        dialog.dismiss();
                        Shangjialiebiao_Bean shangjialiebiao_bean = gson.fromJson(response, Shangjialiebiao_Bean.class);
                        if (shangjialiebiao_bean.result.equals("1")) {
                            ToastUtil.showToast(context, shangjialiebiao_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(shangjialiebiao_bean.totalPage)< nowPage){
                            ToastUtil.showToast(context,"没有更多了");
                            return;
                        }
                        List<Shangjialiebiao_Bean.Merchantslist> merchantslist = shangjialiebiao_bean.merchantslist;
                        merchants.addAll(merchantslist);
                        shangpin_shangjia_adapter.setMerchantslist(merchants);
                        shangpin_shangjia_adapter.notifyDataSetChanged();
                    }
                });
    }
    private void getfenlei(){//分类列表
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "getclassificationlist");*/
        String json="{\"cmd\":\"getclassificationlist\"}";
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
                        ToastUtil.showToast(Shangpin_Activity.this, e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        Shangpin_all_Bean shangpin_all_bean = gson.fromJson(response, Shangpin_all_Bean.class);
                        if (shangpin_all_bean.result.equals("1")) {
                            ToastUtil.showToast(context, shangpin_all_bean.resultNote);
                            return;
                        }
                        classificationlist = shangpin_all_bean.classificationlist;
                        mListType.clear();
                        for (int i = 0; i <classificationlist.size() ; i++) {
                            mListType.add(classificationlist.get(i).classificationame);
                        }
                        showPopup1();
                    }
                });
    }
    public void showPopup1() {
        ListView contentView = new ListView(context);
        if (popupWindow!=null){
            popupWindow.dismiss();
        }
        popupWindow = new PopupWindow(contentView,
                      getWindowManager().getDefaultDisplay().getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        contentView.setBackgroundColor(0XFFEEEEEE);
        contentView.setDivider(new ColorDrawable(getResources().getColor(R.color.gray)));
        contentView.setDividerHeight(getResources().getDimensionPixelSize(R.dimen.high));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                R.layout.item_main_popup_list, mListType);
        contentView.setAdapter(adapter);
        //tv_shangpin_all.setText(adapter.getItem(0));
        contentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                tv_shangpin_all.setText(item);
                popupWindow.dismiss();
                nowPage=1;
                merchants.clear();
                code=1;
                classificationId = classificationlist.get(position).classificationId;
                getfeileidata(classificationId);
            }
        });
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
    }
    public void showPopup2() {
        ListView contentView = new ListView(context);
        if (popupWindow!=null){
            popupWindow.dismiss();
        }
        popupWindow = new PopupWindow(contentView,
                getWindowManager().getDefaultDisplay().getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        contentView.setBackgroundColor(0XFFEEEEEE);
        contentView.setDivider(new ColorDrawable(getResources().getColor(R.color.gray)));
        contentView.setDividerHeight(getResources().getDimensionPixelSize(R.dimen.high));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                R.layout.item_main_popup_list, mListType);
        contentView.setAdapter(adapter);
        contentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                tv_shangpin_fujin.setText(item);
                popupWindow.dismiss();
                nowPage=1;
                merchants.clear();
                code=2;
                fujinId = fujinlist.get(position).fujinId;
                getjulidata(fujinId);
            }
        });
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
    }
private void getfeileidata(String id){
    popupWindow.dismiss();
    Map<String, String> params = new HashMap<>();
   /* params.put("cmd", "getshangpinfenlei");
    params.put("classificationId", id);
    params.put("lat", "34.345345");
    params.put("lon", "120.23432");
    params.put("nowPage",nowPage+"");*/
    String json="{\"cmd\":\"getshangpinfenlei\",\"lat\":\"" + lat + "\",\"lon\":\""
            + lon + "\",\"classificationId\":\"" + id + "\",\"nowPage\":\"" + nowPage + "\"}";
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
                    ToastUtil.showToast(Shangpin_Activity.this, e.getLocalizedMessage());
                    dialog.dismiss();
                }

                @Override
                public void onResponse(String response, int id) {
                    Gson gson = new Gson();
                    dialog.dismiss();
                    shangpin_list.onRefreshComplete();
                    Shangjialiebiao_Bean shangjialiebiao_bean = gson.fromJson(response, Shangjialiebiao_Bean.class);
                    if (shangjialiebiao_bean.result.equals("1")) {
                        ToastUtil.showToast(context, shangjialiebiao_bean.resultNote);
                        return;
                    }
                    if (Integer.parseInt(shangjialiebiao_bean.totalPage)< nowPage){
                        ToastUtil.showToast(context,"没有更多了");
                        return;
                    }
                    List<Shangjialiebiao_Bean.Merchantslist> merchantslist = shangjialiebiao_bean.merchantslist;
                    merchants.addAll(merchantslist);
                    shangpin_shangjia_adapter.setMerchantslist(merchants);
                    shangpin_shangjia_adapter.notifyDataSetChanged();
                }
            });
}
    private void getpaixudata() {//分类列表
        /*cmd:”getzhinengpaixu”
        zhinengid:“12”         //智能id  0送货上门,1到店消费,2上门+到店,3关注度高   app写死
        lat: 34.345345                 // 用户的纬度
        lon：120.23432                // 用户的经度
        nowPage:"1"     //当前页     */
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "getzhinengpaixu");
        params.put("zhinengid", "12");
        params.put("lat", "34.345345");
        params.put("lon", "120.23432");
        params.put("nowPage",nowPage+"");*/
        String json="{\"cmd\":\"getzhinengpaixu\",\"lat\":\"" + lat + "\",\"lon\":\""
                + lon + "\",\"zhinengid\":\"" + id + "\",\"nowPage\":\"" + nowPage + "\"}";
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
                        ToastUtil.showToast(Shangpin_Activity.this, e.getLocalizedMessage());
                        dialog.dismiss();
                        shangpin_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        shangpin_list.onRefreshComplete();
                        Shangjialiebiao_Bean shangjialiebiao_bean = gson.fromJson(response, Shangjialiebiao_Bean.class);
                        if (shangjialiebiao_bean.result.equals("1")) {
                            ToastUtil.showToast(context, shangjialiebiao_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(shangjialiebiao_bean.totalPage)< nowPage){
                            ToastUtil.showToast(context,"没有更多了");
                            return;
                        }
                        List<Shangjialiebiao_Bean.Merchantslist> merchantslist = shangjialiebiao_bean.merchantslist;
                        merchants.addAll(merchantslist);
                        shangpin_shangjia_adapter.setMerchantslist(merchants);
                        shangpin_shangjia_adapter.notifyDataSetChanged();
                    }
                });
    }
    private void getjuli(){
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "getfujinlist");*/
        String json="{\"cmd\":\"getfujinlist\"}";
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
                        ToastUtil.showToast(Shangpin_Activity.this, e.getLocalizedMessage());
                        dialog.dismiss();
                        shangpin_list.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        shangpin_list.onRefreshComplete();
                        Huoqu_Juli_Bean huoqu_juli_bean = gson.fromJson(response, Huoqu_Juli_Bean.class);
                        if (huoqu_juli_bean.result.equals("1")) {
                            ToastUtil.showToast(context, huoqu_juli_bean.resultNote);
                            return;
                        }
                        mListType.clear();
                        fujinlist = huoqu_juli_bean.fujinlist;
                        for (int i = 0; i <fujinlist.size() ; i++) {
                            mListType.add(fujinlist.get(i).fujinname);

                            Log.i("TAG", "附近列表" + fujinlist.get(i).fujinname);

                        }
                        showPopup2();
                    }

                });
    }
    private void getjulidata(String id){
        popupWindow.dismiss();
       /* cmd:”fujingoodslist”
        fujinId:“12”          //附近距离id
        lat: 34.345345                 // 用户的纬度
        lon：120.23432                // 用户的经度
        nowPage:"1"     //当前页    */
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "fujingoodslist");
        params.put("fujinId", id);
        params.put("lat", "34.345345");
        params.put("lon", "120.23432");
        params.put("nowPage",nowPage+"");*/
        String json="{\"cmd\":\"fujingoodslist\",\"lat\":\"" + lat + "\",\"lon\":\""
                + lon + "\",\"fujinId\":\"" + id + "\",\"nowPage\":\"" + nowPage + "\"}";
        params.put("json", json);
        dialog.show();
        Log.i("TAG", "请求结果" + json);
        OkHttpUtils.
                post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Shangpin_Activity.this, e.getLocalizedMessage());
                        shangpin_list.onRefreshComplete();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        Shangjialiebiao_Bean shangjialiebiao_bean = gson.fromJson(response, Shangjialiebiao_Bean.class);
                        if (shangjialiebiao_bean.result.equals("1")) {
                            ToastUtil.showToast(context, shangjialiebiao_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(shangjialiebiao_bean.totalPage)< nowPage){
                            ToastUtil.showToast(context,"没有更多了");
                            return;
                        }
                        List<Shangjialiebiao_Bean.Merchantslist> merchantslist = shangjialiebiao_bean.merchantslist;
                        merchants.addAll(merchantslist);
                        shangpin_shangjia_adapter.setMerchantslist(merchants);
                        shangpin_shangjia_adapter.notifyDataSetChanged();
                    }
                });
    }
}
