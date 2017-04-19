package com.lixin.qiaoqixinyuan.app.activity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Geren_zhuye_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean;
import com.lixin.qiaoqixinyuan.app.util.AppUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.GlideImageLoader;
import com.youth.banner.Banner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Geren_zhuye_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private Banner geren_image_binner;
    private TextView tv_geren_zhongxin_nub;
    private TextView tv_geren_zhongxin_guanzhu;
    private TextView tv_geren_zhongxin_liaotian;
    private TextView tv_geren_zhongxin_beizhu;
    private TextView tv_geren_zhongxin_baocun;
    private TextView tv_geren_zhongxin_tiezi;
    private TextView tv_geren_zhongxin_xinxi;
    private ImageView tv_geren_zhongxin_miandarao;
    private ImageView tv_geren_zhongxin_heimingdan;
    private LinearLayout activity_geren_zhuye_;
    private TextView tv_geren_zhongxin_guanzhu_nub;
    private TextView tv_geren_zhongxin_fensi;
    private EditText et_geren_zhongxin_beizhu;
    private EditText et_geren_zhongxin_qianming;
    private String token;
    private String uid;
    private String id;
    private Geren_zhuye_Bean geren_zhuye_bean;
    private List<Geren_zhuye_Bean.Personalimage> personalimage;
    private List<String> images=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geren_zhuye_);
        id = getIntent().getStringExtra("id");
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
        initView();
        getgerendata();
    }
  private void getguanzhudata(){
      Map<String, String> params = new HashMap<>();
      /*cmd:”guanzhupersonal”
    uid: “1”       //用户id
    guanzhuid:"3"    //被关注人id
    focustype:"0"      //0 已关注   1 取消关注
    token:    [JPUSHService registrationID]   //推送token*/
   /*   params.put("cmd", "guanzhupersonal");
      params.put("uid", uid);
      params.put("guanzhuid", id);
      params.put("focustype", geren_zhuye_bean.focustype);
      params.put("token", token);*/
      String json = "{\"cmd\":\"guanzhupersonal\",\"uid\":\"" + uid + "\",\"guanzhuid\":\"" + id + "\"" +
              ",\"focustype\":\"" + geren_zhuye_bean.focustype + "\",\"token\":\"" + token + "\"}";
      params.put("json", json);
      dialog.show();
      OkHttpUtils//
              .post()//
              .url(Geren_zhuye_Activity.this.getString(R.string.url))//
              .params(params)//
              .build()//
              .execute(new StringCallback() {
                  @Override
                  public void onError(Call call, Exception e, int id) {
                      ToastUtil.showToast(Geren_zhuye_Activity.this, e.getMessage());
                      dialog.dismiss();
                  }

                  @Override
                  public void onResponse(String response, int id) {
                      Gson gson = new Gson();
                      Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                      ToastUtil.showToast(Geren_zhuye_Activity.this,resout_bean.resultNote);
                      dialog.dismiss();
                  }
              });
  }
    private void getheimingdan(){
    /*    cmd:”blacklist”
        uid: “1”       //用户id
        blackid:"3"    //被黑名单的人id
        blacktype:"0"      //0 加入黑名单   1 取消黑名单*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "blacklist");
        params.put("uid", uid);
        params.put("blackid", id);
        params.put("blacktype", geren_zhuye_bean.blacktype);
        params.put("token", token);*/
        String json = "{\"cmd\":\"blacklist\",\"uid\":\"" + uid + "\",\"blackid\":\"" + id + "\"" +
                ",\"blacktype\":\"" + geren_zhuye_bean.blacktype + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()//
                .url(Geren_zhuye_Activity.this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Geren_zhuye_Activity.this, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        ToastUtil.showToast(Geren_zhuye_Activity.this,resout_bean.resultNote);
                        dialog.dismiss();
                    }
                });
    }
    private void getxiugai(){
    /*     cmd:”blacklist”
    uid: “1”       //用户id
    noteid:"3"    //被写备足的人id
    notetext:"如果的。。"      //备注信息*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "blacklist");
        params.put("uid", uid);
        params.put("noteid", id);
        params.put("notetext", "0");
        params.put("token", token);*/
        String json = "{\"cmd\":\"blacklist\",\"uid\":\"" + uid + "\",\"noteid\":\"" + id + "\"" +
                ",\"notetext\":\"" +0 + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
       dialog.show();
        OkHttpUtils.
                post()//
                .url(Geren_zhuye_Activity.this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Geren_zhuye_Activity.this, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        ToastUtil.showToast(Geren_zhuye_Activity.this,resout_bean.resultNote);
                        dialog.dismiss();
                    }
                });
    }
    private void getgerendata() {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "getpersonaldetail");
        params.put("uid", uid);
        params.put("aid", id);
        params.put("token", token);*/
        String json = "{\"cmd\":\"getpersonaldetail\",\"uid\":\"" + uid + "\",\"aid\":\"" + id + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()//
                .url(Geren_zhuye_Activity.this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Geren_zhuye_Activity.this, e.getMessage());
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        geren_zhuye_bean = gson.fromJson(response, Geren_zhuye_Bean.class);
                        if (geren_zhuye_bean.result.equals("1")) {
                            ToastUtil.showToast(Geren_zhuye_Activity.this, geren_zhuye_bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        personalimage = geren_zhuye_bean.personalimage;
                        for (int i = 0; i <personalimage.size() ; i++) {
                            images.add(personalimage.get(i).imageUrl);
                        }
                        setbanner();
                        tv_geren_zhongxin_nub.setText(geren_zhuye_bean.personalage);
                        et_geren_zhongxin_qianming.setText(geren_zhuye_bean.personalsignature);
                        if (geren_zhuye_bean.focustype.equals("0")) {
                            tv_geren_zhongxin_miandarao.setSelected(true);
                        } else {
                            tv_geren_zhongxin_miandarao.setSelected(false);
                        }
                        if (geren_zhuye_bean.blacktype.equals("1")) {
                            tv_geren_zhongxin_heimingdan.setSelected(true);
                        } else {
                            tv_geren_zhongxin_heimingdan.setSelected(false);
                        }
                        tv_geren_zhongxin_guanzhu_nub.setText(geren_zhuye_bean.personalfocus);
                        tv_geren_zhongxin_fensi.setText(geren_zhuye_bean.personalfensi);
                        //UILIImageLoader.getInstance().displayImage(geren_zhuye_bean.,vh.iv_home_list_image, ImageLoaderUtil.DIO());
                        dialog.dismiss();
                    }
                });
    }

    private void setbanner() {
        DisplayMetrics displayMetrics = AppUtil.getDisplayMetrics(context);
        int widthPixels = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = geren_image_binner.getLayoutParams();
        layoutParams.height = widthPixels / 2;
        geren_image_binner.setLayoutParams(layoutParams);
        geren_image_binner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        geren_image_binner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        geren_image_binner.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_geren_zhongxin_guanzhu:
                getguanzhudata();
                break;
            case R.id.tv_geren_zhongxin_heimingdan:
                getheimingdan();
                break;
            case R.id.tv_geren_zhongxin_baocun:
                getxiugai();
                break;
        }
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        geren_image_binner = (Banner) findViewById(R.id.geren_image_binner);
        tv_geren_zhongxin_nub = (TextView) findViewById(R.id.tv_geren_zhongxin_nub);
        tv_geren_zhongxin_guanzhu = (TextView) findViewById(R.id.tv_geren_zhongxin_guanzhu);
        tv_geren_zhongxin_guanzhu.setOnClickListener(this);
        tv_geren_zhongxin_liaotian = (TextView) findViewById(R.id.tv_geren_zhongxin_liaotian);
        tv_geren_zhongxin_beizhu = (TextView) findViewById(R.id.tv_geren_zhongxin_beizhu);
        tv_geren_zhongxin_baocun = (TextView) findViewById(R.id.tv_geren_zhongxin_baocun);
        tv_geren_zhongxin_baocun.setOnClickListener(this);
        tv_geren_zhongxin_tiezi = (TextView) findViewById(R.id.tv_geren_zhongxin_tiezi);
        tv_geren_zhongxin_xinxi = (TextView) findViewById(R.id.tv_geren_zhongxin_xinxi);
        tv_geren_zhongxin_miandarao = (ImageView) findViewById(R.id.tv_geren_zhongxin_miandarao);
        tv_geren_zhongxin_miandarao.setOnClickListener(this);
        tv_geren_zhongxin_heimingdan = (ImageView) findViewById(R.id.tv_geren_zhongxin_heimingdan);
        tv_geren_zhongxin_heimingdan.setOnClickListener(this);
        activity_geren_zhuye_ = (LinearLayout) findViewById(R.id.activity_geren_zhuye_);
        tv_geren_zhongxin_guanzhu_nub = (TextView) findViewById(R.id.tv_geren_zhongxin_guanzhu_nub);
        tv_geren_zhongxin_guanzhu_nub.setOnClickListener(this);
        tv_geren_zhongxin_fensi = (TextView) findViewById(R.id.tv_geren_zhongxin_fensi);
        tv_geren_zhongxin_fensi.setOnClickListener(this);
        et_geren_zhongxin_beizhu = (EditText) findViewById(R.id.et_geren_zhongxin_beizhu);
        et_geren_zhongxin_beizhu.setOnClickListener(this);
        et_geren_zhongxin_qianming = (EditText) findViewById(R.id.et_geren_zhongxin_qianming);
        et_geren_zhongxin_qianming.setOnClickListener(this);
    }

}
