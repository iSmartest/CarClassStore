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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.activity.BuyInsuranceActivity;
import com.lixin.carclassstore.activity.CarStoreActivity;
import com.lixin.carclassstore.activity.CheckViolationWebActivity;
import com.lixin.carclassstore.activity.CustomerServiceActivity;
import com.lixin.carclassstore.activity.NewCarActivity;
import com.lixin.carclassstore.activity.StoreActivity;
import com.lixin.carclassstore.bean.HoneBean;
import com.lixin.carclassstore.bean.ServerBean;
import com.lixin.carclassstore.homeBean.ServeTopBean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private TextView text_change_tire, text_do_maintenance, text_wash_car, text_customer_service;
    private View[] funcViews = new View[17];
    private String[] funcTxts;
    private View view;
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
            R.drawable.s_home2

    };
    String Url = "http://116.255.239.201:8080/carmallService/service.action";
    List<ServeTopBean> newsLists = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        initView();
        initListener();
        return view;
    }

    private void initView() {
        funcTxts = this.getResources().getStringArray(R.array.home_functions);
        text_change_tire = (TextView)view.findViewById(R.id.text_change_tire);
        text_do_maintenance = (TextView) view.findViewById(R.id.text_do_maintenance);
        text_wash_car = (TextView) view.findViewById(R.id.text_wash_car);
        text_customer_service = (TextView) view.findViewById(R.id.text_customer_service);
        funcViews[0] = view.findViewById(R.id.text_new_car);
        funcViews[1] = view.findViewById(R.id.text_used_car);
        funcViews[2] = view.findViewById(R.id.text_maintenance);
        funcViews[3] = view.findViewById(R.id.text_car_decoration);
        funcViews[4] = view.findViewById(R.id.text_check_violation);
        funcViews[5] = view.findViewById(R.id.text_buy_insurance);
        funcViews[6] = view.findViewById(R.id.text_small_maintenance);
        funcViews[7] = view.findViewById(R.id.text_give_oil);
        funcViews[8] = view.findViewById(R.id.text_reduction);
        funcViews[9] = view.findViewById(R.id.text_free_work);
        funcViews[10] = view.findViewById(R.id.iv_car);
        funcViews[11] = view.findViewById(R.id.text_plated_crystal_maintenance);
        funcViews[12] = view.findViewById(R.id.text_car_store);
        funcViews[13] = view.findViewById(R.id.text_special_offer);
        funcViews[14] = view.findViewById(R.id.text_automative_lighting);
        funcViews[15] = view.findViewById(R.id.text_refrigerator);
        funcViews[16] = view.findViewById(R.id.text_more);

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
    private void initListener() {
        text_change_tire.setOnClickListener(this);
        text_do_maintenance.setOnClickListener(this);
        text_wash_car.setOnClickListener(this);
        text_customer_service.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //跳转到门店
            case R.id.text_change_tire:
            case R.id.text_do_maintenance:
            case R.id.text_wash_car:
            case R.id.text_main_store:
            case 15:
            case 14:
            case 13:
            case 9:
            case 8:
            case 7:
            case 6:
            case 2:
            case 3:
                startActivity(new Intent(getActivity(),StoreActivity.class));
                break;
            //跳转到客服
            case R.id.text_customer_service:
                startActivity(new Intent(getActivity(),CustomerServiceActivity.class));
                break;
            //跳转到新车
            case 0:
                startActivity(new Intent(getActivity(),NewCarActivity.class).putExtra(NewCarActivity.TAG, 1));
                break;
            //跳转到二手车
            case 1:
                startActivity(new Intent(getActivity(),NewCarActivity.class).putExtra(NewCarActivity.TAG, 0));
                break;
            //跳转到查违章
            case 4:
                Intent intent = new Intent(getActivity(), CheckViolationWebActivity.class);
                intent.putExtra(CheckViolationWebActivity.URL, "http://www.baidu.com");
                startActivity(intent);
                break;
            //跳转到买保险
            case 5:
                startActivity(new Intent(getActivity(),BuyInsuranceActivity.class));
                break;
            //跳转到车品商店
            case 12:
                startActivity(new Intent(getActivity(),CarStoreActivity.class));
                break;
        }
    }
}