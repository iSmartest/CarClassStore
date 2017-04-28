package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.Constants;
import com.lixin.carclassstore.bean.Struct;
import com.lixin.carclassstore.bean.UserInfo;
import com.lixin.carclassstore.dialog.PhotoUploadDialog;
import com.lixin.carclassstore.tools.PicWorker;
import com.lixin.carclassstore.tools.UpdateUserInfoHttp;
import com.lixin.carclassstore.utils.CommonLog;
import com.lixin.carclassstore.utils.GlobalMethod;
import com.lixin.carclassstore.utils.ImageUtil;
import com.lixin.carclassstore.utils.SPUtils;
import com.lixin.carclassstore.utils.ToastUtils;

import java.io.File;

/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 * 设置页面
 */

public class SetUpActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout a_my_info_password_question,a_my_info_password,a_my_info_lay_username;
    private TextView text_sumit;
    private ImageView headImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        setTitleText("设置");
        hideBack(false);
        initView();
    }
    private void initView() {
        a_my_info_password_question = (LinearLayout) findViewById(R.id.a_my_info_password_question);
        a_my_info_password_question.setOnClickListener(this);
        a_my_info_lay_username = (LinearLayout) findViewById(R.id.a_my_info_lay_username);
        a_my_info_lay_username.setOnClickListener(this);
        a_my_info_password = (LinearLayout) findViewById(R.id.a_my_info_password);
        a_my_info_password.setOnClickListener(this);
        text_sumit = (TextView) findViewById(R.id.text_sumit);
        headImg = (ImageView) findViewById(R.id.a_my_info_iv_header);
        headImg.setOnClickListener(this);
    }
    private PhotoUploadDialog uploadDialog;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.a_my_info_password_question:
                startActivity(new Intent(SetUpActivity.this,SetPasswordQuestionActivity.class));
                break;
            case R.id.a_my_info_password:
                startActivity(new Intent(SetUpActivity.this,SetPasswordActivity.class));
                break;
            case R.id.a_my_info_iv_header:
                if (uploadDialog == null) {
                    uploadDialog = new PhotoUploadDialog(SetUpActivity.this);
                    uploadDialog.setCameraOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPhotoUri = GlobalMethod.getNewPhotoUri();
                            GlobalMethod.getPhotoFromCamera(SetUpActivity.this, mPhotoUri);
                        }
                    });
                    uploadDialog.setSDOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath())), "image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, Constants.UPLOAD_PHOTO_FROM_SD);
                        }
                    });
                }
                uploadDialog.show();
                break;
            case R.id.a_my_info_lay_username:
                SPUtils.put(context,"uid","");//用户ID
                SPUtils.put(context,"nickName","");//用户昵称
                SPUtils.put(context,"phoneNum","");//手机号码
                SPUtils.put(context,"userIcon","");//用户头像
                SPUtils.put(context,"myProfess","");//职位名称
                SPUtils.put(context,"positionType","");
                //0 专家已注册没有提交资质文件 1已注册且提交资质文件等待审核2未通过审核 3资质通过审核可以登录
                SPUtils.put(context,"isLogin",false);//登录状态
                ToastUtils.showMessageShort(context,"已安全退出账号");
                MyApplication.openActivity(context,LoginActivity.class);
                finish();
                break;
        }
    }
    private String photoPath64String;
    private Uri mPhotoUri;
    private String photoPath = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.UPLOAD_PHOTO_FROM_SD && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                mPhotoUri = data.getData();
                if (mPhotoUri != null) {
                    photoPath = ImageUtil.getPath(SetUpActivity.this, mPhotoUri);
                    updateHeadView();
                }
//                PicWorker.startPhotoZoom(MyInfoActivity.this, mPhotoUri);
            }

            return;
        }
        if (requestCode == Constants.UPLOAD_PHOTO_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
//            PicWorker.startPhotoZoom(MyInfoActivity.this, mPhotoUri);
            CommonLog.i("UPLOAD_PHOTO_FROM_CAMERA");
            if (mPhotoUri != null) {
                photoPath = mPhotoUri.getPath();
                updateHeadView();
            }
            return;
        }
    }
    private void updateHeadView() {
        if (TextUtils.isEmpty(photoPath))
            return;
        Bitmap bitmap = PicWorker.compressImage("file://" + photoPath);
        if (bitmap != null) {
            photoPath64String = PicWorker.bitmap2Str(bitmap);
        }

        new UpdateUserInfoHttp(SetUpActivity.this, 1, new UpdateUserInfoHttp.operateResult() {
            @Override
            public void result(Struct result) {
                if (result != null && result.what == 0) {
                    if (result.obj2 != null && !TextUtils.isEmpty(result.obj2.toString().trim())) {
                        Log.i("hh","新的头像地址：" + result.obj2.toString());
                        UserInfo.ImageUrl = result.obj2.toString().trim();
                    }
                }
            }
        }).update(photoPath64String, UserInfo.UserId);
    }

}
