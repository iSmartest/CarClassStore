package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Fangwu_xiangqing_qiuzu_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class Fangwu_qiuzu_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private ImageView fangwu_qiuzu_image;
    private ImageView fangwu_qiuzu_icon;
    private TextView fangwu_qiuzu_title;
    private ImageView fangwu_qiuzu_fenxiang;
    private TextView fangwu_qiuzu_time;
    private TextView fangwu_qiuzu_xiangshu;
    private TextView fangwu_qiuzu_dizhi;
    private TextView fangwu_qiuzu_lianxiren;
    private TextView fangwu_qiuzu_phone;
    private TextView textView2;
    private TextView fangwu_qiuzu_nicheng;
    private TextView fangwu_qiuzu_liuyan;
    private LinearLayout activity_ershou_chushou_;
    private String housinginnewsid;
    private String housclassid;
    private String type;
    private TextView tv_jubao;
    private ImageView iv_fangwu_qiuzu_liuyan_more;
    private Fangwu_xiangqing_qiuzu_Bean fangwu_xiangqing_qiuzu_bean;
    private Fangwu_xiangqing_qiuzu_Bean.Housdetail1 housdetail1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangwu_qiuzu_);
        Intent intent = getIntent();
        housinginnewsid = intent.getStringExtra("housinginnewsid");
        housclassid = intent.getStringExtra("housclassid");
        type = intent.getStringExtra("type");
        initView();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("房屋求租");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        fangwu_qiuzu_image = (ImageView) findViewById(R.id.fangwu_qiuzu_image);
        fangwu_qiuzu_image.setOnClickListener(this);
        fangwu_qiuzu_icon = (ImageView) findViewById(R.id.fangwu_qiuzu_icon);
        fangwu_qiuzu_title = (TextView) findViewById(R.id.fangwu_qiuzu_title);
        fangwu_qiuzu_fenxiang = (ImageView) findViewById(R.id.fangwu_qiuzu_fenxiang);
        fangwu_qiuzu_time = (TextView) findViewById(R.id.fangwu_qiuzu_time);
        fangwu_qiuzu_xiangshu = (TextView) findViewById(R.id.fangwu_qiuzu_xiangshu);
        fangwu_qiuzu_dizhi = (TextView) findViewById(R.id.fangwu_qiuzu_dizhi);
        fangwu_qiuzu_lianxiren = (TextView) findViewById(R.id.fangwu_qiuzu_lianxiren);
        fangwu_qiuzu_phone = (TextView) findViewById(R.id.fangwu_qiuzu_phone);
        textView2 = (TextView) findViewById(R.id.textView2);
        fangwu_qiuzu_nicheng = (TextView) findViewById(R.id.fangwu_qiuzu_nicheng);
        fangwu_qiuzu_liuyan = (TextView) findViewById(R.id.fangwu_qiuzu_liuyan);
        activity_ershou_chushou_ = (LinearLayout) findViewById(R.id.activity_ershou_chushou_);
        tv_jubao = (TextView) findViewById(R.id.tv_jubao);
        tv_jubao.setOnClickListener(this);
        iv_fangwu_qiuzu_liuyan_more = (ImageView) findViewById(R.id.iv_fangwu_qiuzu_liuyan_more);
        iv_fangwu_qiuzu_liuyan_more.setOnClickListener(this);
    }

    private void getdata() {
        /*cmd:”housdetail”
                housinginnewsid:"12"   //房屋信息id
                housclassid:"12"   //房屋分类id
                type:"0"   //0 出售   1 求租*/
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "housdetail");
        params.put("housinginnewsid", housinginnewsid);
        params.put("housclassid", housclassid);
        params.put("type", type);
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        fangwu_xiangqing_qiuzu_bean = gson.fromJson(response, Fangwu_xiangqing_qiuzu_Bean.class);
                        if (fangwu_xiangqing_qiuzu_bean.result.equals("1")) {
                            ToastUtil.showToast(context, fangwu_xiangqing_qiuzu_bean.resultNote);
                            return;
                        }
                        housdetail1 = fangwu_xiangqing_qiuzu_bean.housdetail1;
                        ImageLoader.getInstance().displayImage(housdetail1.imageUrl, fangwu_qiuzu_image, ImageLoaderUtil.DIO());
                        ImageLoader.getInstance().displayImage(housdetail1.housusericon, fangwu_qiuzu_icon, ImageLoaderUtil.DIO());
                        fangwu_qiuzu_title.setText(housdetail1.housdetailname);
                        fangwu_qiuzu_lianxiren.setText(housdetail1.houscontact);
                        fangwu_qiuzu_nicheng.setText(housdetail1.housusername);
                        fangwu_qiuzu_time.setText(housdetail1.housusertime);
                        fangwu_qiuzu_xiangshu.setText(housdetail1.housdetail);
                        fangwu_qiuzu_phone.setText(housdetail1.contactphone);
                        fangwu_qiuzu_liuyan.setText(housdetail1.liuyannum);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jubao:
                Intent intent = new Intent(context, Huifu_pinglun_Activity.class);
                intent.putExtra("newsid", housinginnewsid);
                intent.putExtra("type", "0");
                intent.putExtra("code", 2);
                startActivity(intent);
                break;
            case R.id.iv_fangwu_qiuzu_liuyan_more:
                Intent intent1 = new Intent(context, Pinglun_Activity.class);
                intent1.putExtra("type","0");
                intent1.putExtra("newsid",housclassid);
                intent1.putExtra("code",2);
                startActivity(intent1);
                break;
            case R.id.fangwu_qiuzu_image:
                Intent intent2 = new Intent(context, Geren_zhuye_Activity.class);
                intent2.putExtra("id",housdetail1.newsuid);
                startActivity(intent2);
            case R.id.iv_turnback:
                    finish();
                    break;
        }
    }
}
