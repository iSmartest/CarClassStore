package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.MyReleaseBean;
import com.lixin.carclassstore.bean.RecommendCourtesy;
import com.lixin.carclassstore.http.StringCallback;
import com.lixin.carclassstore.utils.OkHttpUtils;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 小火
 * Create time on  2017/4/27
 * My mailbox is 1403241630@qq.com
 */

public class RecommendCourtesyActivity extends BaseActivity{
    private ImageView imRecommend;
    private TextView textRecommend;
    private String uid;
    private String apptype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_courtesy);
        hideBack(false);
        setTitleText("推荐有礼");
        initView();
        uid = (String) SPUtils.get(RecommendCourtesyActivity.this,"uid","");
        apptype = "1";
        getdata();
    }

    private void initView() {
        imRecommend = (ImageView) findViewById(R.id.im_recommend_courtesy);
        textRecommend = (TextView) findViewById(R.id.text_recommend);
    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
        final String json="{\"cmd\":\"shareApp\",\"uid\":\"" + uid +"\",\"apptype\":\"" + apptype + "\"}";
        params.put("json", json);
        dialog1.show();
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showMessageShort(context, e.getMessage());
                        dialog1.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog1.dismiss();
                        Log.i("response", "response: " + response.toString());
                        RecommendCourtesy recommendCourtesy = gson.fromJson(response, RecommendCourtesy.class);
                        if (recommendCourtesy.getResult().equals("1")) {
                            ToastUtils.showMessageShort(context, recommendCourtesy.getResultNote());
                            return;
                        }
                        if (TextUtils.isEmpty(recommendCourtesy.getShareIcon())){
                            imRecommend.setImageResource(R.drawable.image_fail_empty);
                        }else Picasso.with(RecommendCourtesyActivity.this).load(recommendCourtesy.getShareIcon()).into(imRecommend);
                        textRecommend.setText(recommendCourtesy.getShareCode());
                    }
                });
    }
}
