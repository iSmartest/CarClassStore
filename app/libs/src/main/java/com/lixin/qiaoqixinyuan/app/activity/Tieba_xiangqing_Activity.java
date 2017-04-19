package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.Pinglun_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Pinglun_Bean;
import com.lixin.qiaoqixinyuan.app.bean.Tiaba_xiangqing_Bean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.LogUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.CircleImageView;
import com.lixin.qiaoqixinyuan.app.view.MyLinearLayout;
import com.lixin.qiaoqixinyuan.app.view.MyListview;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase.Mode;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class Tieba_xiangqing_Activity extends BaseActivity implements View.OnClickListener {

    private CircleImageView iv_tieba_icon;
    private TextView iv_tieba_name;
    private TextView iv_tieba_time;
    private MyLinearLayout layout_tieba_images;
    private TextView iv_tieba_pinglun;
    private TextView iv_tieba_fenxiang;
    private LinearLayout activity_tieba_xiangqing_;
    private int nowPage = 1;
    private TextView iv_tieba_pinglun_nub;
    private Pinglun_Adapter pinglun_adapter;
    private List<Pinglun_Bean.Messagelist> pinglun = new ArrayList<>();
    private Tiaba_xiangqing_Bean.Tiebadetail tiebadetail;
    private LinearLayout layout_image_tieba;
    private String pingluntype;
    private String messagetype;
    private String uid;
    private String token;
    private MyListview list_tieba_mylist;
    private String tiebaId;
    private TextView tv_tieba_message;
    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private CircleImageView lv_tieba_icon;
    private PullToRefreshScrollView sc_fresh;
    private    List<Tiaba_xiangqing_Bean.Tiebadetail.MyImageList> imagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tieba_xiangqing_);
        Intent intent = getIntent();
        tiebaId = intent.getStringExtra("tiebaId");
        pingluntype = intent.getStringExtra("pingluntype");
        messagetype = intent.getStringExtra("messagetype");
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        initView();
        getdata();
        getliuyan();
    }

    private void initView() {
        pinglun_adapter = new Pinglun_Adapter();
        pinglun_adapter.setTiebaId(tiebaId);
        iv_tieba_icon = (CircleImageView) findViewById(R.id.lv_tieba_icon);
        iv_tieba_icon.setOnClickListener(this);
        iv_tieba_name = (TextView) findViewById(R.id.iv_tieba_name);
        iv_tieba_time = (TextView) findViewById(R.id.iv_tieba_time);
        layout_tieba_images = (MyLinearLayout) findViewById(R.id.layout_tieba_images);
        iv_tieba_pinglun = (TextView) findViewById(R.id.iv_tieba_pinglun);
        iv_tieba_pinglun.setOnClickListener(this);
        iv_tieba_fenxiang = (TextView) findViewById(R.id.iv_tieba_fenxiang);
        iv_tieba_fenxiang.setOnClickListener(this);
        activity_tieba_xiangqing_ = (LinearLayout) findViewById(R.id.activity_tieba_xiangqing_);
        iv_tieba_pinglun_nub = (TextView) findViewById(R.id.iv_tieba_pinglun_nub);
        iv_tieba_pinglun_nub.setOnClickListener(this);
        layout_image_tieba = (LinearLayout) findViewById(R.id.layout_image_tieba);
        list_tieba_mylist = (MyListview) findViewById(R.id.list_tieba_mylist);
        list_tieba_mylist.setAdapter(pinglun_adapter);
        tv_tieba_message = (TextView) findViewById(R.id.tv_tieba_message);
        tv_tieba_message.setOnClickListener(this);
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        ll_title.setOnClickListener(this);
        tv_title.setText("帖子详情");
        lv_tieba_icon = (CircleImageView) findViewById(R.id.lv_tieba_icon);
        lv_tieba_icon.setOnClickListener(this);
        sc_fresh = (PullToRefreshScrollView) findViewById(R.id.sc_fresh);
        sc_fresh.setMode(Mode.BOTH);
        sc_fresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                nowPage=1;
                pinglun.clear();
                getliuyan();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                nowPage++;
                getliuyan();
            }
        });
    }

    private void getdata() {
      /*  cmd:”tiebadetail”
        tiebaId: '1'                 //贴吧id
      //当前页*/
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "tiebadetail");
        params.put("tiebaId", tiebaId);*/
        String json = "{\"cmd\":\"tiebadetail\",\"tiebaId\":\"" + tiebaId + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils//
                .post()//
                .url(Tieba_xiangqing_Activity.this.getString(R.string.url))//
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
                        Tiaba_xiangqing_Bean tiaba_xiangqing_bean = gson.fromJson(response, Tiaba_xiangqing_Bean.class);
                        if (tiaba_xiangqing_bean.result.equals("1")) {
                            ToastUtil.showToast(context, tiaba_xiangqing_bean.resultNote);
                            return;
                        }
                        tiebadetail = tiaba_xiangqing_bean.tiebadetail;
                        ImageLoader.getInstance().displayImage(tiebadetail.tiebaicon, iv_tieba_icon, ImageLoaderUtil.DIO());
                        iv_tieba_name.setText(tiebadetail.nickName);
                        iv_tieba_time.setText(tiebadetail.tiebatime);
                        iv_tieba_pinglun_nub.setText(tiebadetail.pinglunnum);
                        tv_tieba_message.setText(tiebadetail.tiebatext);
                        imagesList = tiaba_xiangqing_bean.tiebadetail.imagesList;
                        if (imagesList != null) {
                            for (int i = 0; i < imagesList.size(); i++) {
                                View inflate = View.inflate(context, R.layout.item_mypost_image, null);
                                ImageView iv_image = (ImageView) inflate.findViewById(R.id.iv_mypost_image);
                                ImageLoader.getInstance().displayImage(imagesList.get(i).imageUrl, iv_image, ImageLoaderUtil.DIO());
                                layout_tieba_images.addView(inflate);
                            }
                        }

                    }
                });
    }

    private void getliuyan() {
    /*    cmd:”shangjialiuyan”      //留言
        shangjiaid:"35"         //商家id
        messageid:"65"     //留言用户id
        pingluntype:"0"   // 0.帖子评论；1.房屋信息评论；2.二手信息评论；3.招聘求职信息评论 4：商家
        messagetype:"0"     // 0 普通用户留言   1 客户留言
        nowPage:"1"     //当前页 */
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"shangjialiuyan\",\"shangjiaid\":\"" + tiebaId + "\"" +
                ",\"messageid\":\"" + uid + "\",\"pingluntype\":\"" + pingluntype + "\"" +
                ",\"messagetype\":\"" + messagetype + "\",\"nowPage\":\"" + nowPage + "\"" +
                ",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.
                post().url(getString(R.string.url))
                .params(params).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context, e.getMessage());
                        dialog.dismiss();
                        sc_fresh.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        sc_fresh.onRefreshComplete();
                      Pinglun_Bean pinglun_bean = new Gson().fromJson(response, Pinglun_Bean.class);
                        if (pinglun_bean.result.equals("1")) {
                            ToastUtil.showToast(context, pinglun_bean.resultNote);
                            return;
                        }
                        if (Integer.parseInt(pinglun_bean.totalPage) < nowPage) {
                            ToastUtil.showToast(context, "没有更多了");
                            return;
                        }
                        List<Pinglun_Bean.Messagelist> messagelist = pinglun_bean.messagelist;
                        pinglun.addAll(messagelist);
                        pinglun_adapter.setMessage(pinglun);
                        list_tieba_mylist.setAdapter(pinglun_adapter);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tieba_pinglun:
                Intent intent = new Intent(context, Huifu_pinglun_Activity.class);
                intent.putExtra("messageid", "0");
                intent.putExtra("messagetype", "0");
                intent.putExtra("pingluntype", "0");
                intent.putExtra("orderNum", "0");
                intent.putExtra("shangjiaid", tiebaId);
                startActivity(intent);
                break;
            case R.id.lv_tieba_icon:
                Intent intent1 = new Intent(context, Geren_zhuye_Activity.class);
                intent1.putExtra("id", tiebadetail.tiebauid);
                startActivity(intent1);
                break;
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.iv_tieba_fenxiang:
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
        String json="{\"cmd\":\"sixshare\",\"type\":\"" + 0 + "\"," +
                "\"contentid\":\"" + tiebaId +"\"}";
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
                                new ShareAction(Tieba_xiangqing_Activity.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                        .withTitle("撬动沁源")
                                        .withMedia(new UMImage(context, imagesList.get(0).imageUrl))
                                        .withTargetUrl(sixshareone)
                                        .withText(tiebadetail.nickName)
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
