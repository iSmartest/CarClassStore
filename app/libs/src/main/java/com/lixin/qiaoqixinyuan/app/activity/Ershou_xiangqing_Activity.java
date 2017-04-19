package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Ershou_chushou_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Home_Bean;
import com.lixin.qiaoqixinyuan.app.util.AppUtil;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.CircleImageView;
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
import okhttp3.Call;
public class Ershou_xiangqing_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private TextView tv_jubao;
    private CircleImageView ershou_chushou_icon;
    private TextView ershou_chushou_title;
    private ImageView ershou_chushou_fenxiang;
    private TextView ershou_chushou_time;
    private TextView ershou_chushou_price;
    private TextView ershou_chushou_xiangqing;
    private TextView ershou_chushou_dizhi;
    private TextView ershou_chushou_lianxiren;
    private TextView ershou_chushou_phone;
    private TextView ershou_chushou_nicheng;
    private TextView ershou_chushou_liuyan;
    private LinearLayout activity_ershou_chushou_;
    private String secondnewsid;
    private Ershou_chushou_Bean.Secondnewsdetaile0 secondnewsdetaile0;
    private Banner ershou_chushou_image;
    private String secondtypeid;
    private List<Home_Bean.ImagesList> imagesList;
    private List<String> images = new ArrayList<>();
    private RelativeLayout ll_title;
    private RelativeLayout rl_liuyan_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ershou_chushou_);
        Intent intent = getIntent();
        secondnewsid = intent.getStringExtra("secondnewsid");
        secondtypeid = intent.getStringExtra("secondtypeid");
        initView();
        getdata();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("二手详情");
        tv_jubao = (TextView) findViewById(R.id.tv_jubao);
        tv_jubao.setOnClickListener(this);
        ershou_chushou_icon = (CircleImageView) findViewById(R.id.ershou_chushou_icon);
        ershou_chushou_icon.setOnClickListener(this);
        ershou_chushou_title = (TextView) findViewById(R.id.ershou_chushou_title);
        ershou_chushou_fenxiang = (ImageView) findViewById(R.id.ershou_chushou_fenxiang);
        ershou_chushou_fenxiang.setOnClickListener(this);
        ershou_chushou_time = (TextView) findViewById(R.id.ershou_chushou_time);
        ershou_chushou_price = (TextView) findViewById(R.id.ershou_chushou_price);
        ershou_chushou_xiangqing = (TextView) findViewById(R.id.ershou_chushou_xiangqing);
        ershou_chushou_dizhi = (TextView) findViewById(R.id.ershou_chushou_dizhi);
        ershou_chushou_lianxiren = (TextView) findViewById(R.id.ershou_chushou_lianxiren);
        ershou_chushou_phone = (TextView) findViewById(R.id.ershou_chushou_phone);
        ershou_chushou_nicheng = (TextView) findViewById(R.id.ershou_chushou_nicheng);
        ershou_chushou_liuyan = (TextView) findViewById(R.id.ershou_chushou_liuyan);
        activity_ershou_chushou_ = (LinearLayout) findViewById(R.id.activity_ershou_chushou_);
        ershou_chushou_image = (Banner) findViewById(R.id.ershou_chushou_image);
        ershou_chushou_image.setOnClickListener(this);
        ll_title = (RelativeLayout) findViewById(R.id.ll_title);
        ll_title.setOnClickListener(this);
        rl_liuyan_more = (RelativeLayout) findViewById(R.id.rl_liuyan_more);
        rl_liuyan_more.setOnClickListener(this);
    }

    private void setbanner() {
        DisplayMetrics displayMetrics = AppUtil.getDisplayMetrics(context);
        int widthPixels = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = ershou_chushou_image.getLayoutParams();
        layoutParams.height = widthPixels / 2;
        ershou_chushou_image.setLayoutParams(layoutParams);
        ershou_chushou_image.setImageLoader(new GlideImageLoader());
        //设置图片集合
        ershou_chushou_image.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        ershou_chushou_image.start();
        ershou_chushou_image.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(context, Dianpu_xiangqing_Activiyu.class);
            }
        });
    }

    private void getdata() {
      /*  cmd:”secondtypelist”
        secondtypeid:"0"   //0 出售   1 求购   app写死
        secondnewsid:"12"   //二手信息id
         //当前页*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "secondtypelist");
        params.put("secondtypeid", "0");
        params.put("secondnewsid", secondnewsid);*/
        String json = "{\"cmd\":\"secondtypedetailes\",\"secondnewsid\":\""
                + secondnewsid + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Ershou_chushou_Bean ershou_chushou_bean = gson.fromJson(response, Ershou_chushou_Bean.class);
                        if (ershou_chushou_bean.result.equals("1")) {
                            ToastUtil.showToast(context, ershou_chushou_bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        secondnewsdetaile0 = ershou_chushou_bean.secondnewsdetaile0;
                        ImageLoader.getInstance().displayImage(secondnewsdetaile0.secondusericon, ershou_chushou_icon, ImageLoaderUtil.DIO());
                        ershou_chushou_title.setText(secondnewsdetaile0.secondnewsname);
                        ershou_chushou_time.setText(secondnewsdetaile0.secondusertime);
                        ershou_chushou_price.setText(secondnewsdetaile0.secondprice);
                        ershou_chushou_xiangqing.setText(secondnewsdetaile0.seconddetail);
                        ershou_chushou_dizhi.setText(secondnewsdetaile0.secondaddress);
                        ershou_chushou_lianxiren.setText(secondnewsdetaile0.secondcontact);
                        ershou_chushou_phone.setText(secondnewsdetaile0.contactphone);
                        ershou_chushou_nicheng.setText(secondnewsdetaile0.secondusername);
                        ershou_chushou_liuyan.setText(secondnewsdetaile0.liuyannum);
                        imagesList = secondnewsdetaile0.imagesList;
                        for (int i = 0; i < imagesList.size(); i++) {
                            images.add(imagesList.get(i).imageUrl);
                        }
                        setbanner();
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_liuyan_more:
                Intent intent = new Intent(context, Pinglun_Activity.class);
                intent.putExtra("tiebaId", secondnewsdetaile0.newsuid);
                intent.putExtra("pingluntype", "2");
                intent.putExtra("messagetype", "0");
                intent.putExtra("messageid", "0");
                startActivity(intent);
                break;
            case R.id.ershou_chushou_icon:
                Intent intent1 = new Intent(context, Geren_zhuye_Activity.class);
                intent1.putExtra("id", secondnewsdetaile0.newsuid);
                startActivity(intent1);
                break;
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_jubao:
                Intent intent2 = new Intent(context, Ju_Activity.class);
                intent2.putExtra("newsid",secondnewsdetaile0.newsuid);
                intent2.putExtra("type","1");
                startActivity(intent2);
                break;
            case R.id.ershou_chushou_fenxiang:
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
        int i = Integer.parseInt(secondtypeid)+1;
        Map<String, String> params = new HashMap<>();
        String json="{\"cmd\":\"sixshare\",\"type\":\"" + i + "\"," +
                "\"contentid\":\"" +secondnewsid+"\"}";
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
                                new ShareAction(Ershou_xiangqing_Activity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                        .withTitle("撬动沁源")
                                        .withMedia(new UMImage(context, imagesList.get(0).imageUrl))
                                        .withTargetUrl(sixshareone)
                                        .withText(secondnewsdetaile0.secondnewsname)
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
