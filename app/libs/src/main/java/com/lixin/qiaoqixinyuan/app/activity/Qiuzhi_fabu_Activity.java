package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import static com.lixin.qiaoqixinyuan.R.id.iv_zhaopin_fabu_fabu;

public class Qiuzhi_fabu_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private EditText tv_qiuzhin_fabu_biaoti;
    private RadioButton iv_qiuzhin_fabu_nan;
    private RadioButton iv_qiuzhin_fabu_nv;
    private RadioGroup iv_qiuzhin_fabu_xingbie;
    private EditText iv_qiuzhin_fabu_lianxiren;
    private EditText iv_qiuzhin_fabu_phone;
    private EditText iv_qiuzhin_fabu_jieshao;
    private EditText iv_qiuzhin_fabu_gongzuojingli;
    private TextView iv_qiuzhin_fabu_fabu;
    private LinearLayout activity_qiuzhi_fabu_;
    private String sex;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiuzhi_fabu_);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        token = tm.getDeviceId();
        lat = SharedPreferencesUtil.getSharePreStr(context, "lat");
        lon = SharedPreferencesUtil.getSharePreStr(context, "lon");
        uid = SharedPreferencesUtil.getSharePreStr(context, "uid");
        initView();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("招聘发布");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        tv_qiuzhin_fabu_biaoti = (EditText) findViewById(R.id.tv_qiuzhin_fabu_biaoti);
        iv_qiuzhin_fabu_nan = (RadioButton) findViewById(R.id.iv_qiuzhin_fabu_nan);
        iv_qiuzhin_fabu_nv = (RadioButton) findViewById(R.id.iv_qiuzhin_fabu_nv);
        iv_qiuzhin_fabu_xingbie = (RadioGroup) findViewById(R.id.iv_qiuzhin_fabu_xingbie);
        iv_qiuzhin_fabu_lianxiren = (EditText) findViewById(R.id.iv_qiuzhin_fabu_lianxiren);
        iv_qiuzhin_fabu_phone = (EditText) findViewById(R.id.iv_qiuzhin_fabu_phone);
        iv_qiuzhin_fabu_jieshao = (EditText) findViewById(R.id.iv_qiuzhin_fabu_jieshao);
        iv_qiuzhin_fabu_gongzuojingli = (EditText) findViewById(R.id.iv_qiuzhin_fabu_gongzuojingli);
        iv_qiuzhin_fabu_fabu = (TextView) findViewById(R.id.iv_qiuzhin_fabu_fabu);
        iv_qiuzhin_fabu_fabu.setOnClickListener(this);
        activity_qiuzhi_fabu_ = (LinearLayout) findViewById(R.id.activity_qiuzhi_fabu_);
        iv_qiuzhin_fabu_xingbie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.iv_qiuzhin_fabu_nan:
                        sex = "0";
                        break;
                    case R.id.iv_qiuzhin_fabu_nv:
                        sex = "1";
                        break;
                }
            }
        });
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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recycler_view.onActivityResult(requestCode, resultCode, data);
        recycler_onlylook.showPics(recycler_view.getPhotos());
    }
    private void getdata() {
     /*  cmd:”huntingrealease”
       uid:"12"    //用户id
       huntingimage::File[]  //   求职图片
       huntingitem:"单间"      //求职标题
       huntingsex:"男"    //性别
       huntingcontact:"张三"    //联系人姓名
       contactphone:"136542456987"     //联系人联系电话
       jobdetail:"交通方便"      //工作经验
       myselfdetail:"性格开朗"   //自我介绍
       token:    [JPUSHService registrationID]   //推送token*/
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
        try {
            params.put("cmd", "huntingrealease");
            params.put("wanted.uid", uid);
            params.put("wanted.huntingimage", file);
            params.put("wanted.huntingitem", tv_qiuzhin_fabu_biaoti.getText().toString());
            params.put("wanted.huntingsex", sex);
            params.put("wanted.huntingcontact", iv_qiuzhin_fabu_lianxiren.getText().toString());
            params.put("wanted.contactphone", iv_qiuzhin_fabu_phone.getText().toString());
            params.put("wanted.jobdetail", iv_qiuzhin_fabu_gongzuojingli.getText().toString());
            params.put("wanted.myselfdetail", iv_qiuzhin_fabu_jieshao.getText().toString());
            params.put("wanted.token", token);
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
                        ToastUtil.showToast(context, "发布招聘信息成功");
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
    if (tv_qiuzhin_fabu_biaoti.getText().toString()==null){
        ToastUtil.showToast(context,"标题不能为空");
        return;
    }if (iv_qiuzhin_fabu_lianxiren.getText().toString()==null){
        ToastUtil.showToast(context,"联系人不能为空");
        return;
    }if (iv_qiuzhin_fabu_phone.getText().toString()==null){
        ToastUtil.showToast(context,"联系电话不能为空");
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
           case R.id.iv_qiuzhin_fabu_fabu:
              submit();
               break;
           case R.id.iv_turnback:
               finish();
               break;
       }
    }
}
