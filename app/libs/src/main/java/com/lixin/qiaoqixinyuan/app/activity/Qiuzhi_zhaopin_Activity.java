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
import com.lixin.qiaoqixinyuan.app.bean.Zhaopin_zhaopin_Bean;
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

public class Qiuzhi_zhaopin_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private Banner zhaopin_zhaopin_image;
    private ImageView zhaopin_zhaopin_icon;
    private TextView zhaopin_zhaopin_title;
    private ImageView zhaopin_zhaopin_fenxiang;
    private TextView zhaopin_zhaopin_time;
    private TextView zhaopin_zhaopin_sex;
    private TextView zhaopin_zhaopin_lianxiren;
    private TextView zhaopin_zhaopin_lianxidianhua;
    private TextView zhaopin_zhaopin_dizhie;
    private TextView textView2;
    private TextView zhaopin_zhaopin_jingyan;
    private TextView zhaopin_zhaopin_liuyan;
    private LinearLayout activity_ershou_chushou_;
    private String jobhuntingid;
    private Zhaopin_zhaopin_Bean.Jobhuntingdetaile0 jobhuntingdetaile0;
    private TextView zhaopin_zhaopin_biaoti;
    private TextView zhaopin_zhaopin_gongsimingcheng;
    private TextView zhaopin_zhaopin_renshu;
    private TextView zhaopin_zhaopin_xueli;
    private TextView zhaopin_zhaopin_miaoshu;
    private String jobhuntingtypeid;
    private TextView textView3;
    private RelativeLayout rl_liuyan_more;
    private TextView tv_jubao;
    private List<Zhaopin_zqiuzhi_Bean.Jobhuntingdetaile1.ImageUrllist> imageUrllist;
    private List<String> images=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhaopin_xiangqing_);
        Intent intent = getIntent();
        jobhuntingid = intent.getStringExtra("jobhuntingid");
        jobhuntingtypeid = intent.getStringExtra("jobhuntingtypeid");
        initView();
        getdata();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("招聘详情");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        zhaopin_zhaopin_image = (Banner) findViewById(R.id.zhaopin_zhaopin_image);
        zhaopin_zhaopin_icon = (ImageView) findViewById(R.id.zhaopin_zhaopin_icon);
        zhaopin_zhaopin_icon.setOnClickListener(this);
        zhaopin_zhaopin_title = (TextView) findViewById(R.id.zhaopin_zhaopin_title);
        zhaopin_zhaopin_fenxiang = (ImageView) findViewById(R.id.zhaopin_zhaopin_fenxiang);
        zhaopin_zhaopin_time = (TextView) findViewById(R.id.zhaopin_zhaopin_time);
        zhaopin_zhaopin_sex = (TextView) findViewById(R.id.zhaopin_zhaopin_sex);
        zhaopin_zhaopin_lianxiren = (TextView) findViewById(R.id.zhaopin_zhaopin_lianxiren);
        zhaopin_zhaopin_lianxidianhua = (TextView) findViewById(R.id.zhaopin_zhaopin_lianxidianhua);
        zhaopin_zhaopin_dizhie = (TextView) findViewById(R.id.zhaopin_zhaopin_dizhie);
        textView2 = (TextView) findViewById(R.id.textView2);
        zhaopin_zhaopin_jingyan = (TextView) findViewById(R.id.zhaopin_zhaopin_jingyan);
        zhaopin_zhaopin_liuyan = (TextView) findViewById(R.id.zhaopin_zhaopin_liuyan);
        activity_ershou_chushou_ = (LinearLayout) findViewById(R.id.activity_ershou_chushou_);
        zhaopin_zhaopin_biaoti = (TextView) findViewById(R.id.zhaopin_zhaopin_biaoti);
        zhaopin_zhaopin_biaoti.setOnClickListener(this);
        zhaopin_zhaopin_gongsimingcheng = (TextView) findViewById(R.id.zhaopin_zhaopin_gongsimingcheng);
        zhaopin_zhaopin_gongsimingcheng.setOnClickListener(this);
        zhaopin_zhaopin_renshu = (TextView) findViewById(R.id.zhaopin_zhaopin_renshu);
        zhaopin_zhaopin_renshu.setOnClickListener(this);
        zhaopin_zhaopin_xueli = (TextView) findViewById(R.id.zhaopin_zhaopin_xueli);
        zhaopin_zhaopin_xueli.setOnClickListener(this);
        zhaopin_zhaopin_miaoshu = (TextView) findViewById(R.id.zhaopin_zhaopin_miaoshu);
        zhaopin_zhaopin_miaoshu.setOnClickListener(this);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setOnClickListener(this);
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
        ViewGroup.LayoutParams layoutParams = zhaopin_zhaopin_image.getLayoutParams();
        layoutParams.height = widthPixels / 2;
        zhaopin_zhaopin_image.setLayoutParams(layoutParams);
        zhaopin_zhaopin_image.setImageLoader(new GlideImageLoader());
        //设置图片集合
        zhaopin_zhaopin_image.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        zhaopin_zhaopin_image.start();
        zhaopin_zhaopin_image.setOnBannerClickListener(new OnBannerClickListener() {
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
        /*params.put("cmd", "jobhuntingdetail");
        params.put("jobhuntingtypeid", "0");
        params.put("jobhuntingid", jobhuntingid);*/
        String json = "{\"cmd\":\"jobhuntingdetail\",\"jobhuntingtypeid\":\"" + 0 + "\"" +
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
                        Zhaopin_zhaopin_Bean zhaopin_zhaopin_bean = gson.fromJson(response, Zhaopin_zhaopin_Bean.class);
                        if (zhaopin_zhaopin_bean.result.equals("1")) {
                            ToastUtil.showToast(context, zhaopin_zhaopin_bean.resultNote);
                            return;
                        }
                        jobhuntingdetaile0 = zhaopin_zhaopin_bean.jobhuntingdetaile0;
                        imageUrllist = jobhuntingdetaile0.imageUrllist;
                        ImageLoader.getInstance().displayImage(jobhuntingdetaile0.jobhuntingicon, zhaopin_zhaopin_icon, ImageLoaderUtil.DIO());
                        zhaopin_zhaopin_title.setText(jobhuntingdetaile0.jobhuntingnick);
                        zhaopin_zhaopin_time.setText(jobhuntingdetaile0.jobhuntingtime);
                        zhaopin_zhaopin_sex.setText(jobhuntingdetaile0.jobhuntingsex);
                        zhaopin_zhaopin_lianxiren.setText(jobhuntingdetaile0.jobcontact);
                        zhaopin_zhaopin_lianxidianhua.setText(jobhuntingdetaile0.contactphone);
                        zhaopin_zhaopin_dizhie.setText(jobhuntingdetaile0.companyaddress);
                        zhaopin_zhaopin_jingyan.setText(jobhuntingdetaile0.jobexperience);
                        zhaopin_zhaopin_biaoti.setText(jobhuntingdetaile0.jobhuntingname);
                        zhaopin_zhaopin_gongsimingcheng.setText(jobhuntingdetaile0.companyname);
                        zhaopin_zhaopin_renshu.setText(jobhuntingdetaile0.jobhuntingnum);
                        zhaopin_zhaopin_xueli.setText(jobhuntingdetaile0.jobhuntingschooling);
                        zhaopin_zhaopin_miaoshu.setText(jobhuntingdetaile0.jobdetail);
                        zhaopin_zhaopin_liuyan.setText(jobhuntingdetaile0.liuyannum);
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
                intent.putExtra("pingluntype", "1");
                intent.putExtra("messagetype", "0");
                startActivity(intent);
                break;
            case R.id.zhaopin_zhaopin_icon:
                Intent intent1 = new Intent(context, Geren_zhuye_Activity.class);
                intent1.putExtra("id", jobhuntingdetaile0.newsuid);
                startActivity(intent1);
                break;
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_jubao:
                Intent intent2 = new Intent(context, Ju_Activity.class);
                intent2.putExtra("newsid",jobhuntingdetaile0.newsuid);
                intent2.putExtra("type","3");
                startActivity(intent2);
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
        String json="{\"cmd\":\"sixshare\",\"type\":\"" + 3+ "\"," +
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
                                new ShareAction(Qiuzhi_zhaopin_Activity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                        .withTitle("撬动沁源")
                                        .withMedia(new UMImage(context, images.get(0)))
                                        .withTargetUrl(sixshareone)
                                        .withText(jobhuntingdetaile0.jobhuntingnick)
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
