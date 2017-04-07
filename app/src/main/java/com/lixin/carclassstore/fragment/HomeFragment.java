package com.lixin.carclassstore.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.BuyInsuranceActivity;
import com.lixin.carclassstore.activity.CarStoreActivity;
import com.lixin.carclassstore.activity.CheckViolationWebActivity;
import com.lixin.carclassstore.activity.CustomerServiceActivity;
import com.lixin.carclassstore.activity.NewCarActivity;
import com.lixin.carclassstore.activity.StoreActivity;
import com.lixin.carclassstore.bean.JavaBean;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.sina.weibo.sdk.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{
    private View[] funcViews = new View[21];
    private String[] funcTxts;
    private String[] topTexts = new String[4];
    private JavaBean javaBean;
    private View view;
    private String result;
    private String resultNote;
    private String serve;
    private List<JavaBean.Serve.rotateAdvertisement> imageList = new ArrayList<>();
    private List<JavaBean.Serve.serveTop> topList = new ArrayList<>();
    private List<JavaBean.Serve.serveBottom> bottomList = new ArrayList<>();
    private List<JavaBean.Serve.CheckAdvertisement.checkServes> checkServesList = new ArrayList<>();
    private int[] bigBGs = new int[]{
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2,
            R.drawable.s_home2

    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        initData();
        initView();
        getdata();
        return view;
    }

    private void initData() {

    }

    private void initView() {
        funcTxts = this.getResources().getStringArray(R.array.home_functions);
        funcViews[0] = view.findViewById(R.id.text_change_tire);
        funcViews[1] = view.findViewById(R.id.text_do_maintenance);
        funcViews[2] = view.findViewById(R.id.text_wash_car);
        funcViews[3] = view.findViewById(R.id.text_customer_service);
        funcViews[4] = view.findViewById(R.id.text_new_car);
        funcViews[5] = view.findViewById(R.id.text_used_car);
        funcViews[6] = view.findViewById(R.id.text_maintenance);
        funcViews[7] = view.findViewById(R.id.text_car_decoration);
        funcViews[8] = view.findViewById(R.id.text_check_violation);
        funcViews[9] = view.findViewById(R.id.text_buy_insurance);
        funcViews[10] = view.findViewById(R.id.text_small_maintenance);
        funcViews[11] = view.findViewById(R.id.text_give_oil);
        funcViews[12] = view.findViewById(R.id.text_reduction);
        funcViews[13] = view.findViewById(R.id.text_free_work);
        funcViews[14] = view.findViewById(R.id.iv_car);
        funcViews[15] = view.findViewById(R.id.text_plated_crystal_maintenance);
        funcViews[16] = view.findViewById(R.id.text_car_store);
        funcViews[17] = view.findViewById(R.id.text_special_offer);
        funcViews[18] = view.findViewById(R.id.text_automative_lighting);
        funcViews[19] = view.findViewById(R.id.text_refrigerator);
        funcViews[20] = view.findViewById(R.id.text_more);

        for (int i = 0; i < funcViews.length; i++) {
            ImageView imageView = (ImageView) funcViews[i]
                    .findViewById(R.id.include_imagetext_view_image);
            TextView textView = (TextView) funcViews[i]
                    .findViewById(R.id.include_imagetext_textview_text);
            textView.setText(funcTxts[i]);
            imageView.setImageResource(bigBGs[i]);
            funcViews[i].setOnClickListener(this);
            funcViews[i].setId(i);

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case 0:
            case 1:
            case 2:
            case 3:
            case 19:
            case 18:
            case 14:
            case 13:
            case 12:
            case 11:
            case 10:
            case 6:
            case 7:
                startActivity(new Intent(getActivity(),StoreActivity.class));
                break;
            //跳转到客服
            case R.id.text_customer_service:
                startActivity(new Intent(getActivity(),CustomerServiceActivity.class));
                break;
            //跳转到新车
            case 4:
                startActivity(new Intent(getActivity(),NewCarActivity.class).putExtra(NewCarActivity.TAG, 1));
                break;
            //跳转到二手车
            case 5:
                startActivity(new Intent(getActivity(),NewCarActivity.class).putExtra(NewCarActivity.TAG, 0));
                break;
            //跳转到查违章
            case 8:
                Intent intent = new Intent(getActivity(), CheckViolationWebActivity.class);
                intent.putExtra(CheckViolationWebActivity.URL, "http://www.baidu.com");
                startActivity(intent);
                break;
            //跳转到买保险
            case 9:
                startActivity(new Intent(getActivity(),BuyInsuranceActivity.class));
                break;
            //跳转到车品商店
            case 16:
                startActivity(new Intent(getActivity(),CarStoreActivity.class));
                break;
        }
    }
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        String json="{\"cmd\":\"getAppLaunchInfo\"}";
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
                        ToastUtils.showMessageLong(context, "网络异常");
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        javaBean = gson.fromJson(response, JavaBean.class);
                        Log.i("wwww", "onResponse: " + javaBean.getServe());
                        JavaBean.Serve serve = gson.fromJson(response, JavaBean.Serve.class);
                        Log.i("qqqqq", "onResponse: " + serve);
                        imageList = serve.rotateAdvertisement;//轮播图集合
                        Log.i("qqqq", "imageList: " + imageList.get(0).getServeIcon());
                        topList = serve.serveTop;
                        Log.i("qqqq", "topList: " + topList.get(0).getServeIcon());
                        bottomList = serve.serveBottom;
                        Log.i("qqqq", "bottomList: " + bottomList.get(0).getServeIcon());
                        checkServesList = serve.checkAdvertisement.checkServes;
                        Log.i("qqqq", "checkServesList: " + checkServesList.get(0).getServeIcon());
//                        shopMeunList = javaBean.shopMeun;
//                        filtrateList = javaBean.filtrate;

                    }
                });
    }
}