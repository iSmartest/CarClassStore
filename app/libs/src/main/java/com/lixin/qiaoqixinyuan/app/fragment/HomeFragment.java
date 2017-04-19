package com.lixin.qiaoqixinyuan.app.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.Dianpu_xiangqing_Activiyu;
import com.lixin.qiaoqixinyuan.app.activity.Ershou_Activity;
import com.lixin.qiaoqixinyuan.app.activity.Fangwu_Activity;
import com.lixin.qiaoqixinyuan.app.activity.Qianggou_Activity;
import com.lixin.qiaoqixinyuan.app.activity.Qiuzhi_Activity;
import com.lixin.qiaoqixinyuan.app.activity.SelectMapAddressActivity;
import com.lixin.qiaoqixinyuan.app.activity.Shangpin_Activity;
import com.lixin.qiaoqixinyuan.app.activity.Tieba_Activity;
import com.lixin.qiaoqixinyuan.app.activity.Tieba_xiangqing_Activity;
import com.lixin.qiaoqixinyuan.app.activity.qianggou_xiangqing_Activity;
import com.lixin.qiaoqixinyuan.app.adapter.Home_glid_adapter;
import com.lixin.qiaoqixinyuan.app.adapter.Home_list_qianggou_Adapter;
import com.lixin.qiaoqixinyuan.app.adapter.Home_list_shangjia_Adapter;
import com.lixin.qiaoqixinyuan.app.adapter.MyPostAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseFragment;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.Home_Bean;
import com.lixin.qiaoqixinyuan.app.util.AppUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.GlideImageLoader;
import com.lixin.qiaoqixinyuan.app.view.MyGridView;
import com.lixin.qiaoqixinyuan.app.view.MyListview;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private MyGridView grid_home;
    private Banner banner;
    private List<String> images = new ArrayList<>();
    private List<Integer> resorce = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private Home_glid_adapter home_glid_adapter;
    private TextView tv_home_qianggou_more;
    private MyListview list_home_qianggou;
    private TextView tv_home_shangpin_more;
    private MyListview list_home_shangpin;
    private TextView tv_home_tieba_more;
    private MyListview list_home_tieba;
    private List<Home_Bean.ImagesList> imagesList;
    private Home_Bean home_bean;
    private List<Home_Bean.Tiebamodel> tiebamodel;
    private List<Home_Bean.Shangjiamodel> shangjiamodel;
    private List<Home_Bean.Huodongmodel> huodongmodel;
    private Home_list_qianggou_Adapter home_list_qianggou_adapter;
    private Home_list_shangjia_Adapter home_list_shangjia_adapter;
    private int WRITE_RESULT_CODE=12;
    private int CAMERA_CODE=11;
    private String lat;
    private String lon;
    private MyPostAdapter myPostAdapter;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(context, R.layout.home_fragment, null);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                    WRITE_RESULT_CODE);
        } if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA},
                   CAMERA_CODE);
        }
        lat= SharedPreferencesUtil.getSharePreStr(context,"lat");
        lon= SharedPreferencesUtil.getSharePreStr(context,"lon");
        grid_home = (MyGridView) view.findViewById(R.id.grid_home);
        home_glid_adapter = new Home_glid_adapter();
        home_list_qianggou_adapter = new Home_list_qianggou_Adapter();
        home_list_shangjia_adapter = new Home_list_shangjia_Adapter();
        myPostAdapter = new MyPostAdapter(context,dialog,0);
        initView(view);
        getdata();
        getrecorct();
        list_home_qianggou.setFocusable(false);
        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == WRITE_RESULT_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                return;
            } else {
                // Permission Denied
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                        WRITE_RESULT_CODE);
            }
        }
    }
 private void setbanner(){
    /* images.add("http://www.pconline.com.cn/pcedu/sj/pm/photoshop/0412/pic/23psenlarge01.jpg");
     images.add("http://www.pconline.com.cn/pcedu/sj/pm/photoshop/0412/pic/23psenlarge01.jpg");
     images.add("http://www.pconline.com.cn/pcedu/sj/pm/photoshop/0412/pic/23psenlarge01.jpg");
     images.add("http://www.pconline.com.cn/pcedu/sj/pm/photoshop/0412/pic/23psenlarge01.jpg");*/
     for (int i = 0; i <imagesList.size() ; i++) {
         images.add(imagesList.get(i).imageUrl);
     }
     DisplayMetrics displayMetrics = AppUtil.getDisplayMetrics(context);
     int widthPixels = displayMetrics.widthPixels;
     ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
     layoutParams.height = widthPixels / 2;
     banner.setLayoutParams(layoutParams);
     banner.setImageLoader(new GlideImageLoader());
     //设置图片集合
     banner.setImages(images);
     //banner设置方法全部调用完毕时最后调用
     banner.start();
     banner.setOnBannerClickListener(new OnBannerClickListener() {
         @Override
         public void OnBannerClick(int position) {
             Intent intent = new Intent(context, Dianpu_xiangqing_Activiyu.class);
         }
     });
 }
    private void initView(View view) {
        banner = (Banner) view.findViewById(R.id.banner);
        tv_home_qianggou_more = (TextView) view.findViewById(R.id.tv_home_qianggou_more);
        tv_home_qianggou_more.setOnClickListener(this);
        list_home_qianggou = (MyListview) view.findViewById(R.id.list_home_qianggou);
        tv_home_shangpin_more = (TextView) view.findViewById(R.id.tv_home_shangpin_more);
        tv_home_shangpin_more.setOnClickListener(this);
        list_home_shangpin = (MyListview) view.findViewById(R.id.list_home_shangpin);
        tv_home_tieba_more = (TextView) view.findViewById(R.id.tv_home_tieba_more);
        tv_home_tieba_more.setOnClickListener(this);
        list_home_tieba = (MyListview) view.findViewById(R.id.list_home_tieba);
        list_home_qianggou.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, qianggou_xiangqing_Activity.class);
                intent.putExtra("shangjiaid",huodongmodel.get(i).shangjiaid);
                intent.putExtra("huodongid",huodongmodel.get(i).huodongid);
                startActivity(intent);
            }
        });
        list_home_shangpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, Dianpu_xiangqing_Activiyu.class);
                intent.putExtra("shangjiaid",shangjiamodel.get(i).shangjiaid);
                intent.putExtra("shangjianame",shangjiamodel.get(i).shangjianame);
                intent.putExtra("shangjiaicon",shangjiamodel.get(i).shangjiaicon);
                startActivity(intent);
            }
        });
        list_home_tieba.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, Tieba_xiangqing_Activity.class);
                intent.putExtra("tiebaId",tiebamodel.get(i).tiebaId);
                intent.putExtra("pingluntype","0");
                intent.putExtra("messagetype","0");
                startActivity(intent);
            }
        });
    }
    private void getrecorct() {
        resorce.add(R.mipmap.home_sj);
        resorce.add(R.mipmap.home_qg);
        resorce.add(R.mipmap.home_tb);
        resorce.add(R.mipmap.home_fw);
        resorce.add(R.mipmap.home_es);
        resorce.add(R.mipmap.home_qz);
        title.add("商家商品");
        title.add("活动抢购");
        title.add("贴吧");
        title.add("房屋信息");
        title.add("二手信息");
        title.add("招聘求职");
        home_glid_adapter.setResorce(resorce);
        home_glid_adapter.setTitle((ArrayList<String>) title);
        grid_home.setAdapter(home_glid_adapter);
        grid_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:

                        Intent intent = new Intent(context, Shangpin_Activity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        startActivity(new Intent(context, Qianggou_Activity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, Tieba_Activity.class));
                        break;
                    case 3:
                        startActivity(new Intent(context, Fangwu_Activity.class));
                        break;
                    case 4:
                        startActivity(new Intent(context, Ershou_Activity.class));
                        break;
                    case 5:
                        startActivity(new Intent(context, Qiuzhi_Activity.class));
                        break;
                }
            }
        });
    }
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "getGoods");
        params.put("cityId", "100100");
        params.put("lat", lat+"");
        params.put("lon", lon+"");*/
        String json="{\"cmd\":\"getGoods\",\"cityId\":\"" + 100100 + "\",\"lat\":\""
                + lat + "\",\"lon\":\"" + lon + "\"}";
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
                        ToastUtil.showToast(context, "网络异常");
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        home_bean = gson.fromJson(response, Home_Bean.class);
                        imagesList = home_bean.imagesList;//轮播图集合
                        huodongmodel = home_bean.huodongmodel;
                        shangjiamodel = home_bean.shangjiamodel;
                        tiebamodel = home_bean.tiebamodel;
                        home_list_qianggou_adapter.setHuodongmodel(huodongmodel);
                        list_home_qianggou.setAdapter(home_list_qianggou_adapter);
                        home_list_shangjia_adapter.setShangjiamodel(shangjiamodel);
                        list_home_shangpin.setAdapter(home_list_shangjia_adapter);
                        myPostAdapter.setTieba(tiebamodel);
                        list_home_tieba.setAdapter(myPostAdapter);
                        setbanner();

                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_home_qianggou_more:
                startActivity(new Intent(context,Qianggou_Activity.class));
                break;
            case R.id.tv_home_shangpin_more:
                startActivity(new Intent(context,Shangpin_Activity.class));
                break;
            case R.id.tv_home_tieba_more:
                startActivity(new Intent(context,Tieba_Activity.class));
                break;
        }
    }
}
