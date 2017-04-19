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
import com.lixin.qiaoqixinyuan.app.bean.Fangwu_xiangqing_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Zhaopin_zqiuzhi_Bean;
import com.lixin.qiaoqixinyuan.app.util.AppUtil;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
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
import okhttp3.Call;
public class Fangwu_xiangqing_Activity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_turnback;
    private TextView tv_title;
    private ImageView fangwu_zushou_icon;
    private TextView fangwu_zushou_title;
    private ImageView fangwu_zushou_fenxiang;
    private TextView fangwu_zushou_time;
    private TextView fangwu_zushou_jiage;
    private TextView fangwu_zushou_xiangshu;
    private TextView fangwu_zushou_dizhi;
    private TextView fangwu_zushou_lianxiren;
    private TextView fangwu_zushou_phone;
    private TextView textView2;
    private TextView fangwu_zushou_nicheng;
    private TextView fangwu_zushou_liuyan;
    private LinearLayout activity_ershou_chushou_;
    private String housinginnewsid;
    private TextView tv_jubao;
    private Fangwu_xiangqing_Bean.Housdetail0 housdetail0;
    private List<Zhaopin_zqiuzhi_Bean.Jobhuntingdetaile1.ImageUrllist> imageUrllist;
    private Banner fangwu_zushou_banner;
    private List<String> images = new ArrayList<>();
    private RelativeLayout ll_title;
    private RelativeLayout rl_liuyan_more;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangwu_chushou_);
        Intent intent = getIntent();
        housinginnewsid = intent.getStringExtra("housinginnewsid");
        type = intent.getStringExtra("type");
        initView();
        getdata();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("房屋详情");
        fangwu_zushou_icon = (ImageView) findViewById(R.id.fangwu_zushou_icon);
        fangwu_zushou_icon.setOnClickListener(this);
        fangwu_zushou_title = (TextView) findViewById(R.id.fangwu_zushou_title);
        fangwu_zushou_fenxiang = (ImageView) findViewById(R.id.fangwu_zushou_fenxiang);
        fangwu_zushou_fenxiang.setOnClickListener(this);
        fangwu_zushou_time = (TextView) findViewById(R.id.fangwu_zushou_time);
        fangwu_zushou_jiage = (TextView) findViewById(R.id.fangwu_zushou_jiage);
        fangwu_zushou_xiangshu = (TextView) findViewById(R.id.fangwu_zushou_xiangshu);
        fangwu_zushou_dizhi = (TextView) findViewById(R.id.fangwu_zushou_dizhi);
        fangwu_zushou_lianxiren = (TextView) findViewById(R.id.fangwu_zushou_lianxiren);
        fangwu_zushou_phone = (TextView) findViewById(R.id.fangwu_zushou_phone);
        textView2 = (TextView) findViewById(R.id.textView2);
        fangwu_zushou_nicheng = (TextView) findViewById(R.id.fangwu_zushou_nicheng);
        fangwu_zushou_liuyan = (TextView) findViewById(R.id.fangwu_zushou_liuyan);
        activity_ershou_chushou_ = (LinearLayout) findViewById(R.id.activity_ershou_chushou_);
        tv_jubao = (TextView) findViewById(R.id.tv_jubao);
        tv_jubao.setOnClickListener(this);
        fangwu_zushou_banner = (Banner) findViewById(R.id.fangwu_zushou_banner);
        fangwu_zushou_banner.setOnClickListener(this);
        ll_title = (RelativeLayout) findViewById(R.id.ll_title);
        ll_title.setOnClickListener(this);
        rl_liuyan_more = (RelativeLayout) findViewById(R.id.rl_liuyan_more);
        rl_liuyan_more.setOnClickListener(this);
    }

    private void setbanner() {
        for (int i = 0; i < imageUrllist.size(); i++) {
            images.add(imageUrllist.get(i).imageurl);
        }
        DisplayMetrics displayMetrics = AppUtil.getDisplayMetrics(context);
        int widthPixels = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = fangwu_zushou_banner.getLayoutParams();
        layoutParams.height = widthPixels / 2;
        fangwu_zushou_banner.setLayoutParams(layoutParams);
        fangwu_zushou_banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        fangwu_zushou_banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        fangwu_zushou_banner.start();
        fangwu_zushou_banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(context, Dianpu_xiangqing_Activiyu.class);
            }
        });
    }

    private void getdata() {
        /*cmd:”housdetail”
                housinginnewsid:"12"   //房屋信息id
                housclassid:"12"   //房屋分类id
                type:"0"   //0 出售   1 求租*/
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "housdetail");
        params.put("housinginnewsid", housinginnewsid);*/
        String json = "{\"cmd\":\"housdetail\",\"housinginnewsid\":\"" + housinginnewsid + "\"}";
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
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Fangwu_xiangqing_Bean fangwu_xiangqing__bean = gson.fromJson(response, Fangwu_xiangqing_Bean.class);
                        if (fangwu_xiangqing__bean.result.equals("1")) {
                            ToastUtil.showToast(context, fangwu_xiangqing__bean.resultNote);
                            dialog.dismiss();
                            return;
                        }
                        housdetail0 = fangwu_xiangqing__bean.housdetail0;
                        ImageLoader.getInstance().displayImage(housdetail0.housusericon, fangwu_zushou_icon, ImageLoaderUtil.DIO());
                        fangwu_zushou_title.setText(housdetail0.housdetailname);
                        fangwu_zushou_lianxiren.setText(housdetail0.houscontact);
                        fangwu_zushou_nicheng.setText(housdetail0.housusername);
                        fangwu_zushou_time.setText(housdetail0.housusertime);
                        fangwu_zushou_jiage.setText(housdetail0.housprice);
                        fangwu_zushou_xiangshu.setText(housdetail0.housdetail);
                        fangwu_zushou_dizhi.setText(housdetail0.housaddress);
                        fangwu_zushou_phone.setText(housdetail0.contactphone);
                        fangwu_zushou_liuyan.setText(housdetail0.liuyannum);
                        imageUrllist = housdetail0.imageUrllist;
                        setbanner();
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jubao:
                Intent intent = new Intent(context, Ju_Activity.class);
                intent.putExtra("newsid", housinginnewsid);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.rl_liuyan_more:
                Intent intent1 = new Intent(context, Pinglun_Activity.class);
                intent1.putExtra("tiebaId", housinginnewsid);
                intent1.putExtra("pingluntype", "1");
                intent1.putExtra("messagetype", "0");
                intent1.putExtra("messageid", "0");
                startActivity(intent1);
                break;
            case R.id.fangwu_zushou_icon:
                Intent intent2 = new Intent(context, Geren_zhuye_Activity.class);
                intent2.putExtra("id", housdetail0.newsuid);
                startActivity(intent2);
                break;
            case R.id.fangwu_zushou_fenxiang:
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
        String type = housdetail0.type;

        int i = Integer.parseInt(housdetail0.type)+5;
        Map<String, String> params = new HashMap<>();
        String json="{\"cmd\":\"sixshare\",\"type\":\"" +"i"+ "\"," +
                "\"contentid\":\"" +housinginnewsid+"\"}";
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
                                new ShareAction(Fangwu_xiangqing_Activity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                        .withTitle("撬动沁源")
                                        .withMedia(new UMImage(context, images.get(0)))
                                        .withTargetUrl(sixshareone)
                                        .withText(housdetail0.housdetailname)
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
