package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class Zhaopin_fabu_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private EditText tv_zhaopin_fabu_biaoti;
    private EditText iv_zhaopin_fabu_neirong;
    private EditText iv_zhaopin_fabu_yuexin;
    private EditText iv_zhaopin_fabu_gongsi;
    private EditText iv_zhaopin_fabu_xueli;
    private EditText iv_zhaopin_fabu_jingyan;
    private RadioButton iv_zhaopin_fabu_nan;
    private RadioButton iv_zhaopin_fabu_nv;
    private RadioButton iv_zhaopin_fabu_buxian;
    private RadioGroup iv_zhaopin_fabu_xingbie;
    private EditText iv_zhaopin_fabu_renshu;
    private EditText iv_zhaopin_fabu_lianxiren;
    private TextView iv_zhaopin_fabu_fabu;
    private LinearLayout activity_zhaopin_fabu_;
    private String sex;
    private EditText iv_zhaopin_fabu_phone;
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
    private EditText iv_zhaopin_fabu_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhaopin_fabu_);
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
        tv_zhaopin_fabu_biaoti = (EditText) findViewById(R.id.tv_zhaopin_fabu_biaoti);
        iv_zhaopin_fabu_neirong = (EditText) findViewById(R.id.iv_zhaopin_fabu_neirong);
        iv_zhaopin_fabu_yuexin = (EditText) findViewById(R.id.iv_zhaopin_fabu_yuexin);
        iv_zhaopin_fabu_gongsi = (EditText) findViewById(R.id.iv_zhaopin_fabu_gongsi);
        iv_zhaopin_fabu_xueli = (EditText) findViewById(R.id.iv_zhaopin_fabu_xueli);
        iv_zhaopin_fabu_jingyan = (EditText) findViewById(R.id.iv_zhaopin_fabu_jingyan);
        iv_zhaopin_fabu_nan = (RadioButton) findViewById(R.id.iv_zhaopin_fabu_nan);
        iv_zhaopin_fabu_nv = (RadioButton) findViewById(R.id.iv_zhaopin_fabu_nv);
        iv_zhaopin_fabu_buxian = (RadioButton) findViewById(R.id.iv_zhaopin_fabu_buxian);
        iv_zhaopin_fabu_xingbie = (RadioGroup) findViewById(R.id.iv_zhaopin_fabu_xingbie);
        iv_zhaopin_fabu_renshu = (EditText) findViewById(R.id.iv_zhaopin_fabu_renshu);
        iv_zhaopin_fabu_lianxiren = (EditText) findViewById(R.id.iv_zhaopin_fabu_lianxiren);
        iv_zhaopin_fabu_fabu = (TextView) findViewById(R.id.iv_zhaopin_fabu_fabu);
        iv_zhaopin_fabu_fabu.setOnClickListener(this);
        activity_zhaopin_fabu_ = (LinearLayout) findViewById(R.id.activity_zhaopin_fabu_);
        iv_zhaopin_fabu_xingbie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.iv_zhaopin_fabu_nan:
                        sex = "0";
                        break;
                    case R.id.iv_zhaopin_fabu_nv:
                        sex = "1";
                        break;
                    case R.id.iv_zhaopin_fabu_buxian:
                        sex = "2";
                        break;
                }
            }
        });
        iv_zhaopin_fabu_phone = (EditText) findViewById(R.id.iv_zhaopin_fabu_phone);
        iv_zhaopin_fabu_phone.setOnClickListener(this);
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
        iv_zhaopin_fabu_address = (EditText) findViewById(R.id.iv_zhaopin_fabu_address);
        iv_zhaopin_fabu_address.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recycler_view.onActivityResult(requestCode, resultCode, data);
        recycler_onlylook.showPics(recycler_view.getPhotos());
    }

    private void getdata() {
        /*cmd:”jobrealease”
        uid:"12"    //用户id
        jobimage::File[]   //   招聘图片
        jobitem:"单间"      //职业标题
        jobdetaile:"押一付三"    //职业描述
        jobsalary:"3000-4000"   //薪资
        companyname:"防盗门"   //公司名称
        jobhuntingschooling:"大专"   //学历
        jobexperience:"2年工作经验"  //工作经验
        jobhuntingsex:"男"    //性别
        jobhuntingnum:"5"   //招聘人数
        jobcontact:"张三"    //联系人姓名
        contactphone:"136542456987"     //联系人联系电话
        lat: 34.345345                 // 用户的纬度
        lon：120.23432                // 用户的经度
        companyaddress:"山西太原"   //地址
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
            params.put("cmd", "jobrealease");
            params.put("recruit.uid", uid);
            params.put("recruit.jobimage", file);
            params.put("recruit.jobitem", tv_zhaopin_fabu_biaoti.getText().toString());
            params.put("recruit.jobdetaile", iv_zhaopin_fabu_neirong.getText().toString());
            params.put("recruit.jobsalary", iv_zhaopin_fabu_yuexin.getText().toString());
            params.put("recruit.companyname", iv_zhaopin_fabu_gongsi.getText().toString());
            params.put("recruit.jobhuntingschooling", iv_zhaopin_fabu_xueli.getText().toString());
            params.put("recruit.jobexperience", iv_zhaopin_fabu_jingyan.getText().toString());
            params.put("recruit.jobhuntingsex", sex);
            params.put("recruit.jobhuntingnum", iv_zhaopin_fabu_renshu.getText().toString());
            params.put("recruit.jobcontact", iv_zhaopin_fabu_lianxiren.getText().toString());
            params.put("recruit.contactphone", iv_zhaopin_fabu_phone.getText().toString());
            params.put("recruit.lat", lat);
            params.put("recruit.lon", lon);
            params.put("recruit.companyaddress", iv_zhaopin_fabu_address.getText().toString());
            params.put("recruit.token", token);

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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_addphoto:
                PhotoPickUtils.startPick(this, null);
                break;
            case R.id.iv_zhaopin_fabu_fabu:
                getdata();
                break;
            case R.id.iv_turnback:
                finish();
                break;
        }
    }


}
