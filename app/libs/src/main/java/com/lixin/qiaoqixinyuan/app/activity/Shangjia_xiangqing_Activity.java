package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Shangjia_xiangqing_Bean;
import com.lixin.qiaoqixinyuan.app.util.AppUtil;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.GlideImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class Shangjia_xiangqing_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private Banner shangjia_xiangqing_image;
    private ImageView shangjia_xiangqing_icon;
    private TextView shangjia_xiangqing_title;
    private ImageView shangjia_xiangqing_fenxiang;
    private TextView shangjia_xiangqing_time;
    private TextView shangjia_xiangqing_jieshao;
    private TextView shangjia_xiangqing_dizhi;
    private TextView shangjia_xiangqing_phone;
    private TextView shangjia_xiangqing_yunfei;
    private TextView shangjia_xiangqing_fanwei;
    private ImageView shangjia_xiangqing_liuyan;
    private ImageView shangjia_xiangqing_kehu;
    private LinearLayout activity_ershou_chushou_;
    private String shangjiaId;
    private List<String> images=new ArrayList<>();
    private Shangjia_xiangqing_Bean.Merchantsdetail detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangjia_xiangqing_);
        Intent intent = getIntent();
        shangjiaId = intent.getStringExtra("shangjiaId");
        initView();
        getdata();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        shangjia_xiangqing_image = (Banner) findViewById(R.id.shangjia_xiangqing_image);
        shangjia_xiangqing_icon = (ImageView) findViewById(R.id.shangjia_xiangqing_icon);
        shangjia_xiangqing_title = (TextView) findViewById(R.id.shangjia_xiangqing_title);
        shangjia_xiangqing_fenxiang = (ImageView) findViewById(R.id.shangjia_xiangqing_fenxiang);
        shangjia_xiangqing_fenxiang.setOnClickListener(this);
        shangjia_xiangqing_time = (TextView) findViewById(R.id.shangjia_xiangqing_time);
        shangjia_xiangqing_jieshao = (TextView) findViewById(R.id.shangjia_xiangqing_jieshao);
        shangjia_xiangqing_dizhi = (TextView) findViewById(R.id.shangjia_xiangqing_dizhi);
        shangjia_xiangqing_phone = (TextView) findViewById(R.id.shangjia_xiangqing_phone);
        shangjia_xiangqing_yunfei = (TextView) findViewById(R.id.shangjia_xiangqing_yunfei);
        shangjia_xiangqing_fanwei = (TextView) findViewById(R.id.shangjia_xiangqing_fanwei);
        shangjia_xiangqing_liuyan = (ImageView) findViewById(R.id.shangjia_xiangqing_liuyan);
        shangjia_xiangqing_liuyan.setOnClickListener(this);
        shangjia_xiangqing_kehu = (ImageView) findViewById(R.id.shangjia_xiangqing_kehu);
        shangjia_xiangqing_kehu.setOnClickListener(this);
        activity_ershou_chushou_ = (LinearLayout) findViewById(R.id.activity_ershou_chushou_);
        tv_title.setText("商家详情");
    }
    private void setbanner(){
        DisplayMetrics displayMetrics = AppUtil.getDisplayMetrics(Shangjia_xiangqing_Activity.this);
        int widthPixels = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = shangjia_xiangqing_image.getLayoutParams();
        layoutParams.height = widthPixels / 2;
        shangjia_xiangqing_image.setLayoutParams(layoutParams);
        shangjia_xiangqing_image.setImageLoader(new GlideImageLoader());
        //设置图片集合
        shangjia_xiangqing_image.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        shangjia_xiangqing_image.start();
    }
    private void getdata(){
         /* cmd:”getmerchantsdetail”
        shangjiaid: “1”       //商家id
        uid：“12”      //用户id
        token:    [JPUSHService registrationID]   //推送token*/
       Map<String, String> params = new HashMap<>();
        /* params.put("cmd", "getmerchantsdetail");
        params.put("shangjiaid", shangjiaId);
        params.put("uid", uid);
        params.put("token", token);*/
        String json="{\"cmd\":\"getmerchantsdetail\",\"shangjiaid\":\"" + shangjiaId + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {

                    private List<Shangjia_xiangqing_Bean.ImagesList> imagesList;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Shangjia_xiangqing_Activity.this,e.getLocalizedMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        dialog.dismiss();
                        Shangjia_xiangqing_Bean shangjia_xiangqing_bean = gson.fromJson(response, Shangjia_xiangqing_Bean.class);
                    if (shangjia_xiangqing_bean.result.equals("1")){
                        ToastUtil.showToast(Shangjia_xiangqing_Activity.this, shangjia_xiangqing_bean.resultNote);
                        return;
                    }
                        imagesList = shangjia_xiangqing_bean.imagesList;
                        for (int i = 0; i <imagesList.size() ; i++) {
                            images.add(imagesList.get(i).imageUrl);
                        }
                        setbanner();
                        detail = shangjia_xiangqing_bean.merchantsdetail;
                        ImageLoader.getInstance().displayImage(detail.shangjiaimage, shangjia_xiangqing_icon, ImageLoaderUtil.DIO());
                        shangjia_xiangqing_title.setText(detail.shangjiaName);
                        shangjia_xiangqing_time.setText("");//营业时间
                        shangjia_xiangqing_jieshao.setText(detail.shangjiadescribe);
                        shangjia_xiangqing_dizhi.setText(detail.shangjiaaddress);
                        shangjia_xiangqing_phone.setText(detail.shangjiaphone);
                        shangjia_xiangqing_yunfei.setText(detail.shangjiafreight);
                        shangjia_xiangqing_fanwei.setText(detail.shangjiadelivery);
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shangjia_xiangqing_liuyan:
                Intent intent = new Intent(Shangjia_xiangqing_Activity.this, Pinglun_Activity.class);
                intent.putExtra("code",0);
                startActivity(intent);
                break;
            case R.id.shangjia_xiangqing_kehu:
                Intent intent1 = new Intent(Shangjia_xiangqing_Activity.this, Pinglun_Activity.class);
                intent1.putExtra("code",1);
                startActivity(intent1);
                break;
            case R.id.iv_turnback:
                finish();;
                break;
            case R.id.shangjia_xiangqing_fenxiang:
                getshareuri();
                break;
        }
    }
    private void getshareuri(){
      /*  cmd:”sixshare”
        type:"0"  //0贴吧 1二手出租 2二手求购 3招聘 4求职 5房屋出租 6房屋求购 7活动 8商品 9商家
        contentid:"12"   //内容id
        nowPage:"1"		//当前页(分享活动使用)
        lat:""			//纬度(分享活动使用)
        lon:""			//经度(分享活动使用) */
        Map<String, String> params = new HashMap<>();
        String json="{\"cmd\":\"sixshare\",\"type\":\"" + 9+ "\"," +
                "\"contentid\":\"" +shangjiaId+"\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context,e.getMessage());
                        dialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            String resultNote = object.getString("resultNote");
                            String sixshareone = object.getString("sixshareone");
                            if ("0".equals(result)) {
                                new ShareAction(Shangjia_xiangqing_Activity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                        .withTitle("撬动沁源")
                                        .withMedia(new UMImage(context, images.get(0)))
                                        .withTargetUrl(sixshareone)
                                        .withText(detail.shangjiaName)
                                        .setCallback(umShareListener)
                                        .open();
                            } else {
                                ToastUtil.showToast(context, resultNote);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtil.d("plat","platform"+platform);
            ToastUtil.showToast(context,platform + " 分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast(context,platform + " 分享失败啦");
            if(t!=null){
                LogUtil.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast(context,platform + " 分享取消了");
        }
    };
}
