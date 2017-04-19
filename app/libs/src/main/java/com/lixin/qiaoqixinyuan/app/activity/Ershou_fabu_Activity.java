package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.PhotosAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.ObtainselfdataBean;
import com.lixin.qiaoqixinyuan.app.util.MyAsyncHttpResponseHandler;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.MyGridView;
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

import static com.lixin.qiaoqixinyuan.R.id.gv_photos;

public class Ershou_fabu_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private Spinner tv_ershou_fabu_leixing;
    private EditText iv_ershou_fabu_biaoti;
    private EditText iv_ershou_fabu_neirong;
    private EditText iv_ershou_fabu_lianxiren;
    private EditText iv_ershou_fabu_dianhua;
    private EditText iv_ershou_fabu_dizhi;
    private EditText iv_ershou_fabu_jiage;
    private LinearLayout activity_ershou_fabu_;
    private String secondtypeid;
    private MultiPickResultView recycler_view;
    private MultiPickResultView recycler_onlylook;
    private TextView tv_addphoto;
    private PhotosAdapter adapter;
    private List<String> fenlei = new ArrayList<>();
    private List<ObtainselfdataBean.Obtainselfdata.MydataImagesBean> mydataImages = new ArrayList<>();
    private ArrayList<String> pathslook = new ArrayList<>();
    private String uid;
    private String token;
    private String lat;
    private String lon;
    private TextView tv_ershou_fabu_fabu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ershou_fabu_);
        Intent intent = getIntent();
        secondtypeid = intent.getStringExtra("secondtypeid");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        lat = SharedPreferencesUtil.getSharePreStr(context, "lat");
        lon = SharedPreferencesUtil.getSharePreStr(context, "lon");
        initView();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("二手发布");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        tv_ershou_fabu_leixing = (Spinner) findViewById(R.id.tv_ershou_fabu_leixing);
        iv_ershou_fabu_biaoti = (EditText) findViewById(R.id.iv_ershou_fabu_biaoti);
        iv_ershou_fabu_neirong = (EditText) findViewById(R.id.iv_ershou_fabu_neirong);
        iv_ershou_fabu_lianxiren = (EditText) findViewById(R.id.iv_ershou_fabu_lianxiren);
        iv_ershou_fabu_dianhua = (EditText) findViewById(R.id.iv_ershou_fabu_dianhua);
        iv_ershou_fabu_dizhi = (EditText) findViewById(R.id.iv_ershou_fabu_dizhi);
        iv_ershou_fabu_jiage = (EditText) findViewById(R.id.iv_ershou_fabu_jiage);
        activity_ershou_fabu_ = (LinearLayout) findViewById(R.id.activity_ershou_fabu_);
        recycler_view = (MultiPickResultView) findViewById(R.id.recycler_view);
        recycler_view.setOnClickListener(this);
        recycler_onlylook = (MultiPickResultView) findViewById(R.id.recycler_onlylook);
        recycler_onlylook.setOnClickListener(this);
        tv_addphoto = (TextView) findViewById(R.id.tv_addphoto);
        tv_addphoto.setOnClickListener(this);
        recycler_view.init(this, MultiPickResultView.ACTION_SELECT, null);
        recycler_onlylook.init(this, MultiPickResultView.ACTION_ONLY_SHOW, pathslook);
        recycler_view.setVisibility(View.GONE);
        adapter = new PhotosAdapter(context, mydataImages);
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        if (secondtypeid.equals("0")) {
            fenlei.add("出售");
        } else {
            fenlei.add("求租");
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(context, R.layout.item_textview, fenlei);
        tv_ershou_fabu_leixing.setAdapter(stringArrayAdapter);
        tv_ershou_fabu_fabu = (TextView) findViewById(R.id.tv_ershou_fabu_fabu);
        tv_ershou_fabu_fabu.setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recycler_view.onActivityResult(requestCode, resultCode, data);
        recycler_onlylook.showPics(recycler_view.getPhotos());
    }
    private void getdata() {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<String> photos = new ArrayList<>();
        photos = recycler_view.getPhotos();
        if (photos.size() == 0) {
            ToastUtil.showToast(context, "请选择个人头像");
            return;
        }
        File[] file = new File[photos.size()];
        for (int i = 0; i < photos.size(); i++) {
            file[i] = new File(photos.get(i));
        }
   /*   cmd:”realeasesecondnews”
      uid:"12"    //用户id
      secondtypeid:"0"   //0 出售   1 求购   app写死
      secondImages:
      File[];  //图片
      seconditem:"单间"      //二手标题
      seconddetaile:"押一付三"    //需求描述
      seconduser:"张三"       //联系人名称
      secondphone:"123652368954"   //联系人电话
      secondaddress:"美林河畔"   // 地址      secondtype = 0  没有传0
      lat: 34.345345                 // 用户的纬度
      lon：120.23432                // 用户的经度
      secondprice:"800"   //价格          secondtype = 0   没有传0
      token:    [JPUSHService registrationID]   //推送token*/
        try {
            params.put("cmd", "realeasesecondnews");
            params.put("second.uid", uid);
            params.put("second.secondtypeid", secondtypeid);
            params.put("second.secondImages", file);
            params.put("second.seconditem", iv_ershou_fabu_biaoti.getText().toString());
            params.put("second.seconddetaile", iv_ershou_fabu_neirong.getText().toString());
            params.put("second.seconduser", iv_ershou_fabu_lianxiren.getText().toString());
            params.put("second.secondphone", iv_ershou_fabu_dianhua.getText().toString());
            params.put("second.secondaddress", iv_ershou_fabu_dizhi.getText().toString());
            params.put("second.lat", lat);
            params.put("second.lon", lon);
            params.put("second.secondprice", iv_ershou_fabu_jiage.getText().toString());
            params.put("second.token", token);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        httpClient.post(context, getString(R.string.url), params, new MyAsyncHttpResponseHandler(context) {
            @Override
            public void success(int arg0, Header[] arg1, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    String result = object.getString("result");
                    String resultNote = object.getString("resultNote");
                    if ("0".equals(result)) {
                        ToastUtil.showToast(context, "发布二手信息成功");
                        finish();
                    } else {
                        ToastUtil.showToast(context, resultNote);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
private void submit(){
    if (iv_ershou_fabu_biaoti.getText().toString()==null){
        ToastUtil.showToast(context,"标题不能为空");
        return;
    }if (iv_ershou_fabu_lianxiren.getText().toString()==null){
        ToastUtil.showToast(context,"联系人不能为空");
        return;
    }if (iv_ershou_fabu_dianhua.getText().toString()==null){
        ToastUtil.showToast(context,"电话不能为空");
        return;
    }
    getdata();
}
    @Override
    public void onClick(View view) {
   switch (view.getId()){
       case R.id.tv_ershou_fabu_fabu:
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


