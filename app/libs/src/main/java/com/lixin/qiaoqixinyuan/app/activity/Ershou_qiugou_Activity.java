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
import com.lixin.qiaoqixinyuan.app.bean.Ershou_qiugou_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.lixin.qiaoqixinyuan.R.id.ll_title;

public class Ershou_qiugou_Activity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_turnback;
    private TextView tv_title;
    private TextView tv_jubao;
    private ImageView ershou_qiugou_image;
    private ImageView ershou_qiugou_icon;
    private TextView ershou_qiugou_title;
    private ImageView ershou_qiugou_fenxiang;
    private TextView ershou_qiugou_time;
    private TextView ershou_qiugou_price;
    private TextView ershou_qiugou_xiangqing;
    private TextView ershou_qiugou_lianxiren;
    private TextView ershou_qiugou_phone;
    private TextView ershou_qiugou_nicheng;
    private TextView ershou_qiugou_liuyan;
    private LinearLayout activity_ershou_chushou_;
    private ImageView tiv_ershou_qiugou_liuyan_more;
    private Ershou_qiugou_Bean.Secondnewsdetaile1 secondnewsdetaile1;
    private String secondnewsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ershou_qiugou_);
        Intent intent = getIntent();
        secondnewsid = intent.getStringExtra("secondnewsid");
        initView();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_jubao = (TextView) findViewById(R.id.tv_jubao);
        ershou_qiugou_image = (ImageView) findViewById(R.id.ershou_qiugou_image);
        ershou_qiugou_image.setOnClickListener(this);
        ershou_qiugou_icon = (ImageView) findViewById(R.id.ershou_qiugou_icon);
        ershou_qiugou_title = (TextView) findViewById(R.id.ershou_qiugou_title);
        ershou_qiugou_fenxiang = (ImageView) findViewById(R.id.ershou_qiugou_fenxiang);
        ershou_qiugou_time = (TextView) findViewById(R.id.ershou_qiugou_time);
        ershou_qiugou_price = (TextView) findViewById(R.id.ershou_qiugou_price);
        ershou_qiugou_xiangqing = (TextView) findViewById(R.id.ershou_qiugou_xiangqing);
        ershou_qiugou_lianxiren = (TextView) findViewById(R.id.ershou_qiugou_lianxiren);
        ershou_qiugou_phone = (TextView) findViewById(R.id.ershou_qiugou_phone);
        ershou_qiugou_nicheng = (TextView) findViewById(R.id.ershou_qiugou_nicheng);
        ershou_qiugou_liuyan = (TextView) findViewById(R.id.ershou_qiugou_liuyan);
        activity_ershou_chushou_ = (LinearLayout) findViewById(R.id.activity_ershou_chushou_);
        tiv_ershou_qiugou_liuyan_more = (ImageView) findViewById(R.id.tiv_ershou_qiugou_liuyan_more);
        tiv_ershou_qiugou_liuyan_more.setOnClickListener(this);
    }

    private void getdata() {
      /*  cmd:”secondtypelist”
        secondtypeid:"0"   //0 出售   1 求购   app写死
        secondnewsid:"12"   //二手信息id
        nowPage:"1"     //当前页*/
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "secondtypelist");
        params.put("secondtypeid", "1");
        params.put("secondnewsid", "12");
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
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
                        Ershou_qiugou_Bean ershou_qiugou_bean = gson.fromJson(response, Ershou_qiugou_Bean.class);
                        if (ershou_qiugou_bean.result.equals("1")) {
                            ToastUtil.showToast(context, ershou_qiugou_bean.resultNote);
                            return;
                        }
                        secondnewsdetaile1 = ershou_qiugou_bean.secondnewsdetaile1;
                        ImageLoader.getInstance().displayImage(secondnewsdetaile1.imageUrl, ershou_qiugou_image, ImageLoaderUtil.DIO());
                        ImageLoader.getInstance().displayImage(secondnewsdetaile1.secondusericon, ershou_qiugou_icon, ImageLoaderUtil.DIO());
                        ershou_qiugou_title.setText(secondnewsdetaile1.secondnewsname);
                        ershou_qiugou_time.setText(secondnewsdetaile1.secondusertime);
                        ershou_qiugou_price.setText("");
                        ershou_qiugou_xiangqing.setText(secondnewsdetaile1.seconddetail);
                        ershou_qiugou_lianxiren.setText(secondnewsdetaile1.secondcontact);
                        ershou_qiugou_phone.setText(secondnewsdetaile1.contactphone);
                        ershou_qiugou_nicheng.setText(secondnewsdetaile1.secondusername);
                        ershou_qiugou_liuyan.setText(secondnewsdetaile1.liuyannum);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tiv_ershou_qiugou_liuyan_more:
                Intent intent1 = new Intent(context, Pinglun_Activity.class);
                intent1.putExtra("type","1");
                intent1.putExtra("newsid",secondnewsid);
                intent1.putExtra("code",2);
                startActivity(intent1);
                break;
            case R.id.ershou_qiugou_image:
                Intent intent = new Intent(context, Geren_zhuye_Activity.class);
                intent.putExtra("id",secondnewsdetaile1.newsuid);
                startActivity(intent);
                break;
        }
    }
}
