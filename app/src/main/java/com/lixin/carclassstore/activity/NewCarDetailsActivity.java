package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.ADVPAdapter;
import com.lixin.carclassstore.adapter.NewCarAdapter;
import com.lixin.carclassstore.bean.AdInfo;
import com.lixin.carclassstore.bean.CarModel;
import com.lixin.carclassstore.bean.CarSeries;
import com.lixin.carclassstore.bean.NewCarDetails;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.GlobalMethod;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.lixin.carclassstore.view.ProgressDialog;
import com.lixin.carclassstore.view.viewPage.ImageSlideshow;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by 小火
 * Create time on  2017/3/27
 * My mailbox is 1403241630@qq.com
 * 新车详情
 */

public class NewCarDetailsActivity extends Activity{
    private ListView mListView;
    private Context context;
    private Dialog dialog1;
    private String carVersionId,carPriceZoen,mCarName;
    private View head;
    private ImageSlideshow mSlideshow;
    private TextView carName,carPrice,mKoubei;
    private ImageView mBack;
    private List<NewCarDetails.salesCars> mList = new ArrayList<>();
    private NewCarAdapter newCarAdapter;
    private LinearLayout lowestPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_details);
        context = this;
        dialog1 = ProgressDialog.createLoadingDialog(context, "加载中.....");
        Intent intent = getIntent();
        carVersionId = intent.getStringExtra("carVersionId");
        carPriceZoen = intent.getStringExtra("carPrice");
        mCarName = intent.getStringExtra("carName");
        initView();
        getdata();

    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_sales_cars);
        lowestPrice = (LinearLayout) findViewById(R.id.asking_for_the_lowest_price);
        head = LayoutInflater.from(this).inflate(R.layout.include_picture_view,null);
        mSlideshow = (ImageSlideshow) head.findViewById(R.id.img_salas_car_picture);
        carName = (TextView) head.findViewById(R.id.text_car_name);
        carPrice = (TextView) head.findViewById(R.id.text_car_price);
        mBack = (ImageView) head.findViewById(R.id.back);
        carName.setText(mCarName);
        carPrice.setText(carPriceZoen);
        mKoubei = (TextView) head.findViewById(R.id.text_koubei_yinxinag);
        if (head != null){
            mListView.addHeaderView(head);
        }
        newCarAdapter = new NewCarAdapter(this);
        mListView.setAdapter(newCarAdapter);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"getNewCarModelInfo\",\"carVersionId\":\"" + carVersionId +"\"}";
        params.put("json", json);
        Log.i("NewCarDetailsActivity", "onResponse: " + json.toString());
        dialog1.show();
        OkHttpUtils.post().url(context.getString(R.string.url)).params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showMessageShort(context, e.getMessage());
                dialog1.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("NewCarDetailsActivity", "onResponse: " + response.toString());
                Gson gson = new Gson();
                dialog1.dismiss();
                final NewCarDetails newCarDetails = gson.fromJson(response, NewCarDetails.class);
                if (newCarDetails.result.equals("1")) {
                    ToastUtils.showMessageShort(context, newCarDetails.resultNote);
                    return;
                }
                List<String> rotateCarPics = newCarDetails.rotateCarPics;
                Log.i("ShopFragment", "onResponse: " + rotateCarPics.toString());
                String[] relate = new String[rotateCarPics.size()];
                for (int i = 0; i <rotateCarPics.size() ; i++) {
                    relate[i] = rotateCarPics.get(i);
                }
                initData(relate);
                List<NewCarDetails.salesCars> salesCars = newCarDetails.salesCars;

                mList.addAll(salesCars);
                newCarAdapter.setNewCarList(context,mList);
                mListView.setAdapter(newCarAdapter);
                lowestPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + newCarDetails.getIphoneNum()));
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    private void initData(String[] relate) {
        for (int i = 0; i < relate.length; i++) {
            mSlideshow.addImageTitle(relate[i]);
            Log.i("ShopFragment", "onResponse: " + relate[i]);
        }
        mSlideshow.setDotSpace(12);
        mSlideshow.setDotSize(12);
        mSlideshow.setDelay(3000);
        mSlideshow.setOnItemClickListener(new ImageSlideshow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mSlideshow.commit();
        Log.i("jjjjjj", "initViewData: " + relate);
    }
}
