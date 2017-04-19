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
import com.lixin.qiaoqixinyuan.app.bean.Zhaopin_zqiuzhi_Bean;
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

import static com.lixin.qiaoqixinyuan.R.id.ll_title;

public class Qiuzhi_qiuzhi_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private Banner zhaopin_qiuzhi_image;
    private CircleImageView zhaopin_qiuzhi_icon;
    private TextView zhaopin_qiuzhi_title;
    private ImageView zhaopin_qiuzhi_fenxiang;
    private TextView zhaopin_qiuzhi_lianxidianhua;
    private TextView zhaopin_qiuzhi_biaoti;
    private TextView zhaopin_qiuzhi_sex;
    private TextView zhaopin_qiuzhi_time;
    private TextView zhaopin_zhaopin_jieshao;
    private TextView zhaopin_zhaopin_jingli;
    private TextView textView3;
    private TextView zhaopin_zhaopin_liuyan;
    private LinearLayout activity_ershou_chushou_;
    private String jobhuntingid;
    private Zhaopin_zqiuzhi_Bean.Jobhuntingdetaile1 jobhuntingdetaile1;
    private Zhaopin_zqiuzhi_Bean zhaopin_zqiuzhi_bean;
    private List<Zhaopin_zqiuzhi_Bean.Jobhuntingdetaile1.ImageUrllist> imageUrllist;
    private List<String> images = new ArrayList<>();
    private RelativeLayout rl_liuyan_more;
    private TextView tv_jubao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiuzhi_xiangqing_);
        Intent intent = getIntent();
        jobhuntingid = intent.getStringExtra("jobhuntingid");
        initView();
        getdata();
    }

    private void initView() {

        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("求职详情");
        zhaopin_qiuzhi_image = (Banner) findViewById(R.id.zhaopin_qiuzhi_image);
        zhaopin_qiuzhi_image.setOnClickListener(this);
        zhaopin_qiuzhi_icon = (CircleImageView) findViewById(R.id.zhaopin_qiuzhi_icon);
        zhaopin_qiuzhi_icon.setOnClickListener(this);
        zhaopin_qiuzhi_title = (TextView) findViewById(R.id.zhaopin_qiuzhi_title);
        zhaopin_qiuzhi_title.setOnClickListener(this);
        zhaopin_qiuzhi_fenxiang = (ImageView) findViewById(R.id.zhaopin_qiuzhi_fenxiang);
        zhaopin_qiuzhi_fenxiang.setOnClickListener(this);
        zhaopin_qiuzhi_lianxidianhua = (TextView) findViewById(R.id.zhaopin_qiuzhi_lianxidianhua);
        zhaopin_qiuzhi_lianxidianhua.setOnClickListener(this);
        zhaopin_qiuzhi_biaoti = (TextView) findViewById(R.id.zhaopin_qiuzhi_biaoti);
        zhaopin_qiuzhi_biaoti.setOnClickListener(this);
        zhaopin_qiuzhi_sex = (TextView) findViewById(R.id.zhaopin_qiuzhi_sex);
        zhaopin_qiuzhi_sex.setOnClickListener(this);
        zhaopin_qiuzhi_time = (TextView) findViewById(R.id.zhaopin_qiuzhi_time);
        zhaopin_qiuzhi_time.setOnClickListener(this);
        zhaopin_zhaopin_jieshao = (TextView) findViewById(R.id.zhaopin_zhaopin_jieshao);
        zhaopin_zhaopin_jieshao.setOnClickListener(this);
        zhaopin_zhaopin_jingli = (TextView) findViewById(R.id.zhaopin_zhaopin_jingli);
        zhaopin_zhaopin_jingli.setOnClickListener(this);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setOnClickListener(this);
        zhaopin_zhaopin_liuyan = (TextView) findViewById(R.id.zhaopin_zhaopin_liuyan);
        zhaopin_zhaopin_liuyan.setOnClickListener(this);
        activity_ershou_chushou_ = (LinearLayout) findViewById(R.id.activity_ershou_chushou_);
        activity_ershou_chushou_.setOnClickListener(this);
        rl_liuyan_more = (RelativeLayout) findViewById(R.id.rl_liuyan_more);
        rl_liuyan_more.setOnClickListener(this);
        tv_jubao = (TextView) findViewById(R.id.tv_jubao);
        tv_jubao.setOnClickListener(this);
    }

    private void setbanner() {
        for (int i = 0; i < imageUrllist.size(); i++) {
            images.add(imageUrllist.get(i).imageurl);
        }
        DisplayMetrics displayMetrics = AppUtil.getDisplayMetrics(context);
        int widthPixels = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = zhaopin_qiuzhi_image.getLayoutParams();
        layoutParams.height = widthPixels / 2;
        zhaopin_qiuzhi_image.setLayoutParams(layoutParams);
        zhaopin_qiuzhi_image.setImageLoader(new GlideImageLoader());
        //设置图片集合
        zhaopin_qiuzhi_image.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        zhaopin_qiuzhi_image.start();
        zhaopin_qiuzhi_image.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(context, Dianpu_xiangqing_Activiyu.class);
            }
        });
    }

    private void getdata() {
       /* cmd:”jobhuntingdetail”
        jobhuntingtypeid:"0"   //0 招聘   1 求职   app写死
        jobhuntingid:"12"   //  招聘信息id*/
        Map<String, String> params = new HashMap<>();
       /* params.put("cmd", "jobhuntingdetail");
        params.put("jobhuntingtypeid", "0");
        params.put("jobhuntingid", jobhuntingid);*/
        String json = "{\"cmd\":\"jobhuntingdetail\",\"jobhuntingtypeid\":\"" + 1 + "\"" +
                ",\"jobhuntingid\":\"" + jobhuntingid + "\"}";
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
                        dialog.dismiss();
                        zhaopin_zqiuzhi_bean = gson.fromJson(response, Zhaopin_zqiuzhi_Bean.class);
                        if (zhaopin_zqiuzhi_bean.result.equals("1")) {
                            ToastUtil.showToast(context, zhaopin_zqiuzhi_bean.resultNote);
                            return;
                        }
                        jobhuntingdetaile1 = zhaopin_zqiuzhi_bean.jobhuntingdetaile1;
                        imageUrllist = jobhuntingdetaile1.imageUrllist;
                        ImageLoader.getInstance().displayImage(jobhuntingdetaile1.jobhuntingicon, zhaopin_qiuzhi_icon, ImageLoaderUtil.DIO());
                        zhaopin_qiuzhi_title.setText(jobhuntingdetaile1.jobhuntingnick);
                        zhaopin_qiuzhi_time.setText(jobhuntingdetaile1.jobhuntingtime);
                        zhaopin_qiuzhi_sex.setText(jobhuntingdetaile1.jobhuntingsex);
                        zhaopin_zhaopin_jieshao.setText(jobhuntingdetaile1.myselfdetail);
                        zhaopin_qiuzhi_lianxidianhua.setText(jobhuntingdetaile1.contactphone);
                        zhaopin_zhaopin_jingli.setText(jobhuntingdetaile1.jobdetail);
                        zhaopin_qiuzhi_biaoti.setText(jobhuntingdetaile1.jobhuntingname);
                        zhaopin_zhaopin_liuyan.setText(jobhuntingdetaile1.liuyannum);
                        setbanner();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_liuyan_more:
                Intent intent = new Intent(context, Pinglun_Activity.class);
                intent.putExtra("tiebaId", jobhuntingid);
                intent.putExtra("pingluntype", "3");
                intent.putExtra("messagetype", "0");
                startActivity(intent);
                break;
            case R.id.zhaopin_qiuzhi_icon:
                Intent intent1 = new Intent(context, Geren_zhuye_Activity.class);
                intent1.putExtra("id", jobhuntingdetaile1.newsuid);
                startActivity(intent1);
                break;
            case R.id.iv_basesearch_back:
                finish();
                break;
            case R.id.tv_jubao:
                Intent intent2 = new Intent(context, Ju_Activity.class);
                intent2.putExtra("newsid",jobhuntingdetaile1.newsuid);
                intent2.putExtra("type","3");
                startActivity(intent2);
                break;
            case R.id.zhaopin_qiuzhi_fenxiang:
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
        String json="{\"cmd\":\"sixshare\",\"type\":\"" + 4+ "\"," +
                "\"contentid\":\"" +jobhuntingid+"\"}";
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
                                new ShareAction(Qiuzhi_qiuzhi_Activity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                        .withTitle("撬动沁源")
                                        .withMedia(new UMImage(context, images.get(0)))
                                        .withTargetUrl(sixshareone)
                                        .withText(jobhuntingdetaile1.jobhuntingnick)
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
