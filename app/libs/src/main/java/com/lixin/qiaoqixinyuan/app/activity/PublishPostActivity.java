package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class PublishPostActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private EditText et_post_title;
    private EditText et_post_content;
    private TextView tv_addphoto;
    private Button btn_publish;
    private File[] files = new File[7];
    private String uid;
    private String token;
    private LinearLayout ll_title;
    private MultiPickResultView recycler_view;
    private MultiPickResultView recycler_onlylook;
    private LinearLayout activity_publish_post;
    private List<ObtainselfdataBean.Obtainselfdata.MydataImagesBean> mydataImages = new ArrayList<>();
    private PhotosAdapter photosAdapter;
    private ArrayList<String> pathslook = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishpost);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_post_title = (EditText) findViewById(R.id.et_post_title);
        et_post_content = (EditText) findViewById(R.id.et_post_content);
        tv_addphoto = (TextView) findViewById(R.id.tv_addphoto);
        btn_publish = (Button) findViewById(R.id.btn_publish);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        ll_title.setOnClickListener(this);
        recycler_view = (MultiPickResultView) findViewById(R.id.recycler_view);
        recycler_view.setOnClickListener(this);
        recycler_onlylook = (MultiPickResultView) findViewById(R.id.recycler_onlylook);
        recycler_onlylook.setOnClickListener(this);
        activity_publish_post = (LinearLayout) findViewById(R.id.activity_publish_post);
        activity_publish_post.setOnClickListener(this);
    }

    private void initData() {
        tv_title.setText("发布帖子");
        recycler_view.init(this, MultiPickResultView.ACTION_SELECT, null);
        recycler_onlylook.init(this, MultiPickResultView.ACTION_ONLY_SHOW, pathslook);
        recycler_view.setVisibility(View.GONE);
        photosAdapter = new PhotosAdapter(context, mydataImages);
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);
        tv_addphoto.setOnClickListener(this);
        btn_publish.setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recycler_view.onActivityResult(requestCode, resultCode, data);
        recycler_onlylook.showPics(recycler_view.getPhotos());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_addphoto:
                PhotoPickUtils.startPick(this, null);
                break;
            case R.id.btn_publish:
                submit();
                break;
        }
    }

    private void submit() {
        String title = et_post_title.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.showToast(context, "帖子标题不能为空");
            return;
        }

        String content = et_post_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(context, "帖子内容不能为空");
            return;
        }
        getdata();
    }

    private void getdata() {
  /*      cmd:”sendtieba”
        uid:"12"   //用户id
        itemText:"种植"      //标题
        tiebaImages:
        File[];  //贴吧图片
        tiebaText:"红薯生虫了"    //贴吧文本*//*
        Map<String, String> params = new HashMap<>();
       *//* params.put("cmd", "sendtieba");
        params.put("uid", uid);
        params.put("itemText", et_post_title.getText().toString());
        params.put("tiebaImages", "");
        params.put("tiebaText", et_post_content.getText().toString());
        params.put("token", token);*//*
        String json = "{\"cmd\":\"sendtieba\",\"uid\":\"" + uid + "\",\"itemText\":\"" + et_post_title.getText().toString() + "\"" +
                ",\"tiebaImages\":\"" +""+ "\",\"tiebaText\":\"" +et_post_content.getText().toString()+"\" ,\"token\":\"" + token + "\"}";
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
                    }
                });
    }*/
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<String> photos = new ArrayList<>();
        photos = recycler_view.getPhotos();
        if (photos.size() == 0) {
            ToastUtil.showToast(context, "请选择发布图片");
            return;
        }
        File[] file = new File[photos.size()];
        for (int i = 0; i < photos.size(); i++) {
            file[i] = new File(photos.get(i));
        }
        try {
           /* cmd:”sendtieba”
            uid:"12"   //用户id
            itemText:"种植"      //标题
            tiebaImages:File[];  //贴吧图片
            tiebaText:"红薯生虫了"    //贴吧文本
            token:    [JPUSHService registrationID]   //推送token*/
            params.put("cmd", "sendtieba");
            params.put("send.uid", uid);
            params.put("send.tiebaImages", file);
            params.put("send.itemText", et_post_title.getText().toString());
            params.put("send.tiebaText", et_post_content.getText().toString());
            params.put("send.token", token);
            /*String json="{\"cmd\":\"sendtieba\",\"send.uid\":\"" + uid + "\",\"send.tiebaImages\":\""
                    + file + "\",\"send.itemText\":\"" + et_post_title.getText().toString() + "\"" +
                    ",\"send.tiebaText\":\"" +et_post_content.getText().toString() + "\"" +
                    ",\"send.token\":\"" + token + "\"}";*/
            httpClient.post(context, getString(R.string.url), params, new MyAsyncHttpResponseHandler(context) {
                @Override
                public void success(int arg0, Header[] arg1, String s) {
                    try {
                        JSONObject object = new JSONObject(s);
                        String result = object.getString("result");
                        String resultNote = object.getString("resultNote");
                        if ("0".equals(result)) {
                            ToastUtil.showToast(context, "帖子发布成功");
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
}

