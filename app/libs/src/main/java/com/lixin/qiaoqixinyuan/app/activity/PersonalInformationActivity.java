package com.lixin.qiaoqixinyuan.app.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.adapter.PhotosAdapter;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.ObtainselfdataBean;
import com.lixin.qiaoqixinyuan.app.bean.UserLoginBean;
import com.lixin.qiaoqixinyuan.app.util.MyAsyncHttpResponseHandler;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.MyGridView;
//import com.lzy.imagepicker.ImagePicker;
//import com.lzy.imagepicker.bean.ImageItem;
//import com.lzy.imagepicker.ui.ImageGridActivity;
//import com.lzy.imagepicker.ui.UILIImageLoader;
//import com.lzy.imagepicker.view.CropImageView;
//import com.nostra13.universalimageloader.core.ImageLoader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
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

public class PersonalInformationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private MyGridView gv_photos;
    private RadioButton rb_male;
    private RadioButton rb_female;
    private RadioGroup rg_user_sex;
    private TextView tv_addphoto;
    private EditText et_user_nickname;
    private EditText et_user_age;
    private EditText et_user_instruction;
    private Button btn_save;
    private String sex = "男";
    private PhotosAdapter adapter;
    private List<ObtainselfdataBean.Obtainselfdata.MydataImagesBean> mydataImages = new ArrayList<>();
    private MultiPickResultView recyclerView;
    private MultiPickResultView recyclerViewShowOnly;
    private ArrayList<String> pathslook = new ArrayList<>();
    private String uid;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinformation);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        gv_photos = (MyGridView) findViewById(R.id.gv_photos);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
        rg_user_sex = (RadioGroup) findViewById(R.id.rg_user_sex);
        et_user_nickname = (EditText) findViewById(R.id.et_user_nickname);
        et_user_age = (EditText) findViewById(R.id.et_user_age);
        tv_addphoto = (TextView) findViewById(R.id.tv_addphoto);
        et_user_instruction = (EditText) findViewById(R.id.et_user_instruction);
        recyclerView = (MultiPickResultView) findViewById(R.id.recycler_view);
        recyclerViewShowOnly = (MultiPickResultView) findViewById(R.id.recycler_onlylook);
        btn_save = (Button) findViewById(R.id.btn_save);
    }

    private void initData() {
        tv_title.setText("个人信息");
        rb_male.setChecked(true);
        recyclerView.init(this, MultiPickResultView.ACTION_SELECT, null);
        recyclerViewShowOnly.init(this, MultiPickResultView.ACTION_ONLY_SHOW, pathslook);
        recyclerView.setVisibility(View.GONE);
        adapter = new PhotosAdapter(context, mydataImages);
        gv_photos.setAdapter(adapter);
        uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
        obtainselfdata();
    }

    private void initListener() {
        iv_turnback.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        tv_addphoto.setOnClickListener(this);
        rg_user_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //获取变更后的选中项的ID
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) PersonalInformationActivity.this.findViewById(radioButtonId);
                rb.setChecked(true);
                sex = rb.getText().toString().trim();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_addphoto:
                gv_photos.setVisibility(View.GONE);
                PhotoPickUtils.startPick(this, null);
                break;
            case R.id.btn_save:
                submit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recyclerView.onActivityResult(requestCode, resultCode, data);
        recyclerViewShowOnly.showPics(recyclerView.getPhotos());
    }

    private void submit() {
        String nickname = et_user_nickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
            ToastUtil.showToast(context, "请输入昵称");
            return;
        }

        String age = et_user_age.getText().toString().trim();
        if (TextUtils.isEmpty(age)) {
            ToastUtil.showToast(context, "请输入年龄");
            return;
        }

        String instruction = et_user_instruction.getText().toString().trim();
        if (TextUtils.isEmpty(instruction)) {
            ToastUtil.showToast(context, "请输入个性签名");
            return;
        }
        myselfdata(nickname,sex,age,instruction);
    }

    /**
     * 获取个人资料
     */
    private void obtainselfdata() {
        Map<String, String> params = new HashMap<>();
    /*    params.put("cmd", "obtainselfdata");
        params.put("uid", uid);*/
        String json="{\"cmd\":\"obtainselfdata\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
        params.put("json", json);
        dialog.show();
        OkHttpUtils.post().url(getString(R.string.url)).params(params).build()
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
                        ObtainselfdataBean bean = gson.fromJson(response, ObtainselfdataBean.class);
                        if ("0".equals(bean.result)) {
                            ObtainselfdataBean.Obtainselfdata obtainselfdata = bean.obtainselfdata;
                            mydataImages = obtainselfdata.mydataImages;
                            et_user_nickname.setText(obtainselfdata.mynick);
                            et_user_age.setText(obtainselfdata.myage);
                            et_user_instruction.setText(obtainselfdata.mysignature);
                            if ("0".equals(obtainselfdata.mysex)) {
                                rb_male.setChecked(true);
                            } else {
                                rb_female.setChecked(true);
                            }
                            adapter=new PhotosAdapter(context,mydataImages);
                            gv_photos.setAdapter(adapter);
                        } else {
                            ToastUtil.showToast(context, bean.resultNote);
                            return;
                        }
                    }
                });

    }

    /**
     * @param mynick
     * @param mysex
     * @param myage
     * @param mysignature
     */
    private void myselfdata(String mynick, String mysex, String myage, String mysignature) {

        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<String> photos = new ArrayList<>();
        photos = recyclerView.getPhotos();
        if (photos.size() == 0) {
            ToastUtil.showToast(context, "请选择个人头像");
            return;
        }
        File[] file =new File[photos.size()];
        for (int i = 0; i < photos.size(); i++) {
            file[i] = new File(photos.get(i));
        }
        try {
            /*cmd:”myselfdata”
            uid:"13"   //用户id
            mydataImages:File[];  //个人图片
            mynick:"不错"    //昵称
            mysex:"女"   //性别
            myage:"13"  //年龄
            mysignature:"开朗"    //个性签名
            token:    [JPUSHService registrationID]   //推送token*/
            params.put("cmd", "myselfdata");
            params.put("myself.uid", uid);
            params.put("myself.mydataImages", file);
            params.put("myself.mynick", mynick);
            if (mysex.equals("男")) {
                params.put("myself.mysex", "0");
            }else {
                params.put("myself.mysex", "1");
            }
            params.put("myself.myage", myage);
            params.put("myself.mysignature", mysignature);
            params.put("myself.token", token);
            httpClient.post(context, getString(R.string.url), params, new MyAsyncHttpResponseHandler(context) {
                @Override
                public void success(int arg0, Header[] arg1, String s) {
                    try {
                        JSONObject object = new JSONObject(s);
                        String result = object.getString("result");
                        String resultNote = object.getString("resultNote");
                        if ("0".equals(result)) {
                            ToastUtil.showToast(context, "修改个人资料成功成功");
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
