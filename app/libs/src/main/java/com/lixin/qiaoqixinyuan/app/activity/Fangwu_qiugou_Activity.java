package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.PhotosAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Fangwu_fenlei_Bean;
import com.lixin.qiaoqixinyuan.app.bean.ObtainselfdataBean;
import com.lixin.qiaoqixinyuan.app.bean.Resout_Bean;
import com.lixin.qiaoqixinyuan.app.util.MyAsyncHttpResponseHandler;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Call;import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Fangwu_qiugou_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private Spinner tv_fangwu_fabu_leixing;
    private EditText iv_fangwu_fabu_biaoti;
    private EditText iv_fangwu_fabu_neirong;
    private EditText iv_fangwu_fabu_lianxiren;
    private EditText iv_fangwu_fabu_dianhua;
    private EditText iv_fangwu_fabu_dizhi;
    private LinearLayout activity_fangwu_qiugou_;
    private String type;
    private List<String> fenlei = new ArrayList<>();
    private String housclassid;
    private EditText iv_fangwu_fabu_price;
    private TextView tv_fangwu_fabu_fabu;
    private MultiPickResultView recycler_view;
    private MultiPickResultView recycler_onlylook;
    private TextView tv_addphoto;
    private List<ObtainselfdataBean.Obtainselfdata.MydataImagesBean> mydataImages = new ArrayList<>();
    private PhotosAdapter photosAdapter;
    private ArrayList<String> pathslook = new ArrayList<>();
    private String uid;
    private String token;
    private List<Fangwu_fenlei_Bean.Housclasslist> housclasslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangwu_qiugou_);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        initView();
        getfenlei();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("房屋求购");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        tv_fangwu_fabu_leixing = (Spinner) findViewById(R.id.tv_fangwu_fabu_leixing);
        iv_fangwu_fabu_biaoti = (EditText) findViewById(R.id.iv_fangwu_fabu_biaoti);
        iv_fangwu_fabu_neirong = (EditText) findViewById(R.id.iv_fangwu_fabu_neirong);
        iv_fangwu_fabu_lianxiren = (EditText) findViewById(R.id.iv_fangwu_fabu_lianxiren);
        iv_fangwu_fabu_dianhua = (EditText) findViewById(R.id.iv_fangwu_fabu_dianhua);
        iv_fangwu_fabu_dizhi = (EditText) findViewById(R.id.iv_fangwu_fabu_dizhi);
        activity_fangwu_qiugou_ = (LinearLayout) findViewById(R.id.activity_fangwu_qiugou_);
        tv_fangwu_fabu_leixing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (fenlei.get(i).equals("chush")) {
                    housclassid = "0";
                }else {
                    housclassid = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelected(true);
            }
        });
        /*tv_fangwu_fabu_leixing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });*/
        iv_fangwu_fabu_price = (EditText) findViewById(R.id.iv_fangwu_fabu_price);
        iv_fangwu_fabu_price.setOnClickListener(this);
        tv_fangwu_fabu_fabu = (TextView) findViewById(R.id.tv_fangwu_fabu_fabu);
        tv_fangwu_fabu_fabu.setOnClickListener(this);
        recycler_view = (MultiPickResultView) findViewById(R.id.recycler_view);
        recycler_view.setOnClickListener(this);
        recycler_onlylook = (MultiPickResultView) findViewById(R.id.recycler_onlylook);
        recycler_onlylook.setOnClickListener(this);
        tv_addphoto = (TextView) findViewById(R.id.tv_addphoto);
        tv_addphoto.setOnClickListener(this);
        recycler_view.init(this, MultiPickResultView.ACTION_SELECT, null);
        recycler_onlylook.init(this, MultiPickResultView.ACTION_ONLY_SHOW, pathslook);
        recycler_view.setVisibility(View.GONE);
        photosAdapter = new PhotosAdapter(context, mydataImages);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recycler_view.onActivityResult(requestCode, resultCode, data);
        recycler_onlylook.showPics(recycler_view.getPhotos());
    }
    private void getfenlei() {
        Map<String, String> params = new HashMap<>();
        /*params.put("cmd", "housclasslist");*/
        String json="{\"cmd\":\"housclasslist\"}";
        params.put("json", json);
        dialog.show();
   OkHttpUtils.
                post()//
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
                        Fangwu_fenlei_Bean fangwu_fenlei_bean = gson.fromJson(response, Fangwu_fenlei_Bean.class);
                        if (fangwu_fenlei_bean.result.equals("1")) {
                            ToastUtil.showToast(context, fangwu_fenlei_bean.resultNote);
                            dialog.dismiss();
                        }
                      housclasslist = fangwu_fenlei_bean.housclasslist;
                        for (int i = 0; i < housclasslist.size(); i++) {
                            if (housclasslist.get(i).type.equals(type)) {
                                fenlei.add(housclasslist.get(i).housclassname);
                            }
                        }
                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(context, R.layout.item_textview, fenlei);
                        tv_fangwu_fabu_leixing.setAdapter(stringArrayAdapter);
                        dialog.dismiss();
                    }
                });
    }

    private void getdata() {
     /*  cmd:”realeasehous”
       uid:"12"    //用户id
       houseimage:"sadf"   //base64   房屋图片
       type:"0"           //0 出售   1 求租
       housclassid:"12"   //房屋分类id
       houseitem:"单间"      //房屋标题
       housedetaile:"押一付三"    //需求描述
       housuser:"张三"       //联系人名称
       housephone:"123652368954"   //联系人电话
       housaddress:"美林河畔"   // 地址      type = 0
       houseprice:"800"   //价格         type = 0*/
        AsyncHttpClient httpClient = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
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
            params.put("cmd", "realeasehous");
            params.put("hous.uid", uid);
            params.put("hous.houseimage", file);
            params.put("hous.type", type);
            params.put("hous.housclassid", housclassid);
            params.put("hous.houseitem", iv_fangwu_fabu_biaoti.getText().toString());
            params.put("hous.housedetaile", iv_fangwu_fabu_neirong.getText().toString());
            params.put("hous.housuser", iv_fangwu_fabu_lianxiren.getText().toString());
            params.put("hous.housephone", iv_fangwu_fabu_dianhua.getText().toString());
            params.put("hous.housaddress", iv_fangwu_fabu_dianhua.getText().toString());
            params.put("hous.houseprice", iv_fangwu_fabu_price.getText().toString());
            params.put("hous.token", token);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        httpClient.post(context,getString(R.string.url), params, new MyAsyncHttpResponseHandler(context) {
            @Override
            public void success(int arg0, Header[] arg1, String s) {
                Gson gson = new Gson();
                    params.toString();
                Resout_Bean resout_bean = gson.fromJson(s, Resout_Bean.class);
                ToastUtil.showToast(context, resout_bean.resultNote);
            }
        });
    }
private void submit(){
    if (iv_fangwu_fabu_biaoti.getText().toString()==null){
        ToastUtil.showToast(context,"标题不能为空");
        return;
    }if (iv_fangwu_fabu_lianxiren.getText().toString()==null){
        ToastUtil.showToast(context,"联系人不能为空");
        return;
    }if (iv_fangwu_fabu_dianhua.getText().toString()==null){
        ToastUtil.showToast(context,"联系电话不能为空");
        return;
    }
    getdata();
}
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fangwu_fabu_fabu:
                submit();
                break;
            case R.id.tv_addphoto:
                PhotoPickUtils.startPick(this, null);
                break;
            case R.id.iv_turnback:
                finish();
                break;

        }
    }

}
