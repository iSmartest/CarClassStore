package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.PhotosAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.ObtainselfdataBean;
import com.lixin.qiaoqixinyuan.app.util.MyAsyncHttpResponseHandler;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;

import static com.lixin.qiaoqixinyuan.R.id.et_post_content;
import static com.lixin.qiaoqixinyuan.R.id.et_post_title;

public class Huifu_pinglun_Activity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private EditText et_huifu_message;
    private TextView tv_huifu_huifu;
    private TextView tv_huifu_quxiao;
    private LinearLayout activity_huifu_pinglun_;
    private int code;
    private String uid;
    private String token;
    private String tieziid;
    private String tiezitype;
    private MultiPickResultView recycler_view;
    private MultiPickResultView recycler_onlylook;
    private TextView tv_addphoto;
    private List<ObtainselfdataBean.Obtainselfdata.MydataImagesBean> mydataImages = new ArrayList<>();
    private PhotosAdapter photosAdapter;
    private ArrayList<String> pathslook = new ArrayList<>();
    private String messageid;
    private String messagetype;
    private String pingluntype;
    private String orderNum;
    private String shangjiaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huifu_pinglun_);
        Intent intent = getIntent();
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        messageid = intent.getStringExtra("messageid");
        messagetype = intent.getStringExtra("messagetype");
        pingluntype = intent.getStringExtra("pingluntype");
        orderNum = intent.getStringExtra("orderNum");
        shangjiaid = intent.getStringExtra("shangjiaid");
        initView();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (messageid.equals("0")) {
            tv_title.setText("发表评论");
        }else {
            tv_title.setText("回复评论");
        }
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        et_huifu_message = (EditText) findViewById(R.id.et_huifu_message);
        tv_huifu_huifu = (TextView) findViewById(R.id.tv_huifu_huifu);
        tv_huifu_huifu.setOnClickListener(this);
        tv_huifu_quxiao = (TextView) findViewById(R.id.tv_huifu_quxiao);
        tv_huifu_quxiao.setOnClickListener(this);
        activity_huifu_pinglun_ = (LinearLayout) findViewById(R.id.activity_huifu_pinglun_);
        recycler_view = (MultiPickResultView) findViewById(R.id.recycler_view);
        recycler_onlylook = (MultiPickResultView) findViewById(R.id.recycler_onlylook);
        tv_addphoto = (TextView) findViewById(R.id.tv_addphoto);
        tv_addphoto.setOnClickListener(this);
        recycler_view.init(this, MultiPickResultView.ACTION_SELECT, null);
        recycler_onlylook.init(this, MultiPickResultView.ACTION_ONLY_SHOW, pathslook);
        recycler_view.setVisibility(View.GONE);
        photosAdapter = new PhotosAdapter(context, mydataImages);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recycler_view.onActivityResult(requestCode, resultCode, data);
        recycler_onlylook.showPics(recycler_view.getPhotos());
    }
    private void submit() {

        String content = et_huifu_message.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(context, "帖子内容不能为空");
            return;
        }
        getdata();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_addphoto:
                PhotoPickUtils.startPick(this, null);
                break;
            case R.id.tv_huifu_huifu:
                submit();
                break;
            case R.id.tv_huifu_quxiao:
                finish();
                break;
            case R.id.iv_turnback:
                finish();
                break;
        }
    }
    private void getdata(){
    AsyncHttpClient httpClient = new AsyncHttpClient();
    RequestParams params = new RequestParams();
    ArrayList<String> photos = new ArrayList<>();
    photos = recycler_view.getPhotos();
    File[] file = new File[photos.size()];
    for (int i = 0; i < photos.size(); i++) {
        file[i] = new File(photos.get(i));
    }
    try {
       /* cmd:”pinglunliuyan”
        uid:"12"   //用户id
        messageid:"13"     //评论id   0 评论  回复传id
        messagetype:"0"     // 0 普通用户留言   1 客户留言
        pingluntype:"0"   // 0.帖子评论；1.房屋信息评论；2.二手信息评论；3.招聘求职信息评论 4：商家
        orderNum:"1236523456"   //订单号    //非订单评价传0
        shangjiaid:"35"         //商家id
        pinglunText:"红薯生虫了"    //评论文本    //回复 内容（商家回复（昵称）：）
        pinglunImages:File[];  //图片
        token:    [JPUSHService registrationID]  //推送token*/
        params.put("cmd", "pinglunliuyan");
        params.put("release.messageid", messageid);
        params.put("release.uid", uid);
        params.put("release.mydataImages", file);
        params.put("release.messagetype",messagetype);
        params.put("release.pingluntype", pingluntype);
        params.put("release.orderNum",orderNum);
        params.put("release.shangjiaid", shangjiaid);
        params.put("release.pinglunText", et_huifu_message.getText().toString());
        params.put("release.token", token);
        httpClient.post(context, getString(R.string.url), params, new MyAsyncHttpResponseHandler(context) {
            @Override
            public void success(int arg0, Header[] arg1, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    String result = object.getString("result");
                    String resultNote = object.getString("resultNote");
                    if ("0".equals(result)) {
                        ToastUtil.showToast(context, "发布评论成功");
                        finish();
                    } else {
                        ToastUtil.showToast(context, resultNote);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}
   /* private void getdata(){
     *//*cmd:”pinglunliuyan”
        uid:"12"   //用户id
        messagetype:"0"     // 0 普通用户留言   1 客户留言
        shangjiaid:"35"         //商家id
        pinglunText:"红薯生虫了"    //评论文本*//*
        Map<String, String> params = new HashMap<>();
      *//*  params.put("cmd", "pinglunliuyan");
        params.put("uid", "12");
        params.put("messagetype", "12");
        params.put("shangjiaid", "12");
        params.put("pinglunText", "红薯生虫了");*//*
        String json = "{\"cmd\":\"pinglunliuyan\",\"uid\":\"" + uid + "\",\"messagetype\":\"" + messagetype +"\"" +
                ",\"shangjiaid\":\"" + shangjiaid + "\",\"pinglunText\":\"" + pinglunText + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context,e.getMessage());
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        ToastUtil.showToast(context,resout_bean.resultNote);
                        finish();
                    }
                });
    }*/
   /* private void gettieziliuyan(){
        Map<String, String> params = new HashMap<>();
      *//* cmd:”pingluntiezi”
    uid:"12"   //用户id
    tieziid:"13"      //  帖子id
    tiezitype:"0"   // 0 评论 1 回复
    pinglunText:"红薯生虫了"    //贴吧评论文本    //回复 内容
    pinglunImages:File[];  //图片
    token:    [JPUSHService registrationID]   //推送token*//*

     *//*   params.put("cmd", "pingluntiezi");
        params.put("uid", "12");
        params.put("tieziid", "12");
        params.put("pinglunText", "你好吗");*//*
        String json = "{\"cmd\":\"pingluntiezi\",\"uid\":\"" + uid + "\",\"tieziid\":\"" + tieziid +"\"" +
                ",\"tiezitype\":\"" + tiezitype + "\",\"pinglunText\":\"" + "你好" + "\",\"pinglunImages\":\"" + tiebaId + "\"" +
                ",\"token\":\"" + token + "\"}";
        params.put("json", json);
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(context,e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        ToastUtil.showToast(context,resout_bean.resultNote);
                        finish();
                    }
                });
    }
    private void getjubao(){
       *//* cmd:”housdetail”
        newsid:"12"   //房屋信息id 、二手信息id
        uid:"12"   //用户id
        type:"0"      // 0 房屋信息   1 二手信息  2 招聘求职 //app端写死
        reporttext:"错误"   //举报信息*//*
        Map<String, String> params = new HashMap<>();
 *//*       params.put("cmd", "housdetail");
        params.put("newsid", newsid);
        params.put("uid", "12");
        params.put("type", type);
        params.put("reporttext", et_huifu_message.getText().toString());*//*
        String json = "{\"cmd\":\"housdetail\",\"uid\":\"" + uid + "\",\"newsid\":\"" + newsid +"\"" +
                ",\"type\":\"" + type + "\",\"reporttext\":\"" + reporttext + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        OkHttpUtils//
                .post()//
                .url(context.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                       ToastUtil.showToast(context,e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Resout_Bean resout_bean = gson.fromJson(response, Resout_Bean.class);
                        ToastUtil.showToast(context,resout_bean.resultNote);
                        finish();
                    }
                });
    }*/
}
