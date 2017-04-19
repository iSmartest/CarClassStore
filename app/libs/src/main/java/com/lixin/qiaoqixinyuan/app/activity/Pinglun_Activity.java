package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.PhotosAdapter;
import com.lixin.qiaoqixinyuan.app.adapter.Pinglun_Adapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.ObtainselfdataBean;
import com.lixin.qiaoqixinyuan.app.bean.Pinglun_Bean;
import com.lixin.qiaoqixinyuan.app.util.MyAsyncHttpResponseHandler;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Call;

import static com.lixin.qiaoqixinyuan.R.id.et_huifu_message;
import static com.lixin.qiaoqixinyuan.R.id.list_tieba_mylist;

public class Pinglun_Activity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView prlv_pinglun_list;
    private int code;
    private int nowPage = 1;
    private List<Pinglun_Bean.Messagelist> message = new ArrayList<>();
    private Pinglun_Adapter pinglun_adapter;
    private String pingluntype;
    private String messagetype;
    private String uid;
    private String token;
    private String shangjiaid;
    private List<Pinglun_Bean.Messagelist> pinglun = new ArrayList<>();
    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private EditText et_liuyan;
    private TextView tv_send_liuyan;
    private MultiPickResultView recycler_view;
    private MultiPickResultView recycler_onlylook;
    private TextView tv_addphoto;
    private List<ObtainselfdataBean.Obtainselfdata.MydataImagesBean> mydataImages = new ArrayList<>();
    private PhotosAdapter photosAdapter;
    private ArrayList<String> pathslook = new ArrayList<>();
    private String messageid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinglun_);
        Intent intent = getIntent();
        shangjiaid = intent.getStringExtra("tiebaId");
        pingluntype = intent.getStringExtra("pingluntype");
        messagetype = intent.getStringExtra("messagetype");
        messageid = intent.getStringExtra("messageid");
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        initView();
        getliuyan();
    }

    private void initView() {
        pinglun_adapter = new Pinglun_Adapter();
        pinglun_adapter.setTiebaId(shangjiaid);
        prlv_pinglun_list = (PullToRefreshListView) findViewById(R.id.prlv_pinglun_list);
        prlv_pinglun_list.setMode(PullToRefreshBase.Mode.BOTH);
        prlv_pinglun_list.setAdapter(pinglun_adapter);
        prlv_pinglun_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage = 1;
                pinglun.clear();
                getliuyan();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nowPage++;
                getliuyan();

            }
        });
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("留言列表");
        tv_title.setOnClickListener(this);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        ll_title.setOnClickListener(this);
        recycler_view = (MultiPickResultView) findViewById(R.id.recycler_view);
        recycler_view.setOnClickListener(this);
        recycler_onlylook = (MultiPickResultView) findViewById(R.id.recycler_onlylook);
        recycler_onlylook.setOnClickListener(this);
        tv_addphoto = (TextView) findViewById(R.id.tv_addphoto);
        tv_addphoto.setOnClickListener(this);
        et_liuyan = (EditText) findViewById(R.id.et_liuyan);
        et_liuyan.setOnClickListener(this);
        tv_send_liuyan = (TextView) findViewById(R.id.tv_send_liuyan);
        tv_send_liuyan.setOnClickListener(this);
        recycler_view = (MultiPickResultView) findViewById(R.id.recycler_view);
        recycler_onlylook = (MultiPickResultView) findViewById(R.id.recycler_onlylook);
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

    private void getliuyan() {
    /*    cmd:”shangjialiuyan”      //留言
        shangjiaid:"35"         //商家id
        messageid:"65"     //留言用户id
        pingluntype:"0"   // 0.帖子评论；1.房屋信息评论；2.二手信息评论；3.招聘求职信息评论 4：商家
        messagetype:"0"     // 0 普通用户留言   1 客户留言
        nowPage:"1"     //当前页 */
        Map<String, String> params = new HashMap<>();
        String json = "{\"cmd\":\"shangjialiuyan\",\"shangjiaid\":\"" + shangjiaid + "\"" +
                ",\"messageid\":\"" + uid + "\",\"pingluntype\":\"" + pingluntype + "\"" +
                ",\"messagetype\":\"" + messagetype + "\",\"nowPage\":\"" + nowPage + "\"" +
                ",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(context, e.getMessage());
                dialog.dismiss();
                prlv_pinglun_list.onRefreshComplete();
            }
            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                Pinglun_Bean pinglun_bean = new Gson().fromJson(response, Pinglun_Bean.class);
                if (pinglun_bean.result.equals("1")) {
                    ToastUtil.showToast(context, pinglun_bean.resultNote);
                    prlv_pinglun_list.onRefreshComplete();
                    return;
                }
                if (Integer.parseInt(pinglun_bean.totalPage) < nowPage) {
                    ToastUtil.showToast(context, "没有更多了");
                    prlv_pinglun_list.onRefreshComplete();
                    return;
                }
                List<Pinglun_Bean.Messagelist> messagelist = pinglun_bean.messagelist;
                pinglun.addAll(messagelist);
                pinglun_adapter.setMessage(pinglun);
                pinglun_adapter.notifyDataSetChanged();
                prlv_pinglun_list.onRefreshComplete();
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_send_liuyan:
                getdata();
                pinglun_adapter.notifyDataSetChanged();
                break;
            case R.id.tv_addphoto:
                PhotoPickUtils.startPick(this, null);
                break;

        }
    }

    private void getdata() {
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
            params.put("release.messagetype", messagetype);
            params.put("release.pingluntype", pingluntype);
            params.put("release.orderNum", "0");
            params.put("release.shangjiaid", shangjiaid);
            params.put("release.pinglunText", et_liuyan.getText().toString());
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

   /* private void getshangjialiuyan(){
    *//*    cmd:”shangjialiuyan”      //留言
        shangjiaid:"35"         //商家id
        messageid:"65"     //留言用户id
        messagetype:"0"     // 0 普通用户留言   1 客户留言
        nowPage:"1"     //当前页 *//*
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "shangjialiuyan");
        params.put("shangjiaId", "12");
        params.put("messageid", "12");
        params.put("messagetype", code+"");
        params.put("nowPage", nowPage+"");
        OkHttpUtils//
                .post()//
                .url(this.getString(R.string.url))//
                .params(params)//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(Pinglun_Activity.this,e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Pinglun_Bean pinglun_bean = gson.fromJson(response, Pinglun_Bean.class);
                        if (pinglun_bean.result.equals("1")){
                            ToastUtil.showToast(Pinglun_Activity.this,pinglun_bean.resultNote);
                        }if (Integer.getInteger(pinglun_bean.totalPage)<nowPage){
                            ToastUtil.showToast(Pinglun_Activity.this,"没有更多了");
                        }
                        List<Pinglun_Bean.Messagelist> messagelist = pinglun_bean.messagelist;
                        message.addAll(messagelist);
                        pinglun_adapter.setMessage(message);
                        pinglun_adapter.notifyDataSetChanged();
                    }
                });
    }
   private void getfangwuliuyan(){
   *//*    cmd:”newsliuyan”      //搜索活动
       uid:"12"    //用户id
       newsid:"35"         //信息id
       type:"0"      // 0 房屋信息   1 二手信息  2 招聘求职 //app端写死
       nowPage:"1"     //当前页*//*
       Map<String, String> params = new HashMap<>();
       params.put("cmd", "newsliuyan");
       params.put("uid", "12");
       params.put("newsid", newsid);
       params.put("type", type);
       params.put("nowPage", nowPage+"");
       OkHttpUtils//
               .post()//
               .url(this.getString(R.string.url))//
               .params(params)//
               .build()//
               .execute(new StringCallback() {
                   @Override
                   public void onError(Call call, Exception e, int id) {
                       ToastUtil.showToast(Pinglun_Activity.this,e.getMessage());
                   }

                   @Override
                   public void onResponse(String response, int id) {
                       Gson gson = new Gson();
                       Pinglun_Bean pinglun_bean = gson.fromJson(response, Pinglun_Bean.class);
                       if (pinglun_bean.result.equals("1")){
                           ToastUtil.showToast(Pinglun_Activity.this,pinglun_bean.resultNote);
                       }if (Integer.getInteger(pinglun_bean.totalPage)<nowPage){
                           ToastUtil.showToast(Pinglun_Activity.this,"没有更多了");
                       }
                       List<Pinglun_Bean.Messagelist> messagelist = pinglun_bean.messagelist;
                       message.addAll(messagelist);
                       pinglun_adapter.setMessage(message);
                       pinglun_adapter.notifyDataSetChanged();
                   }
               });
   }*/
    }
}
