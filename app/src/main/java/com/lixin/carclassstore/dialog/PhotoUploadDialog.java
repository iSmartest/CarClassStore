package com.lixin.carclassstore.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lixin.carclassstore.R;


/**
 * 上传照片对话框
 */
public class PhotoUploadDialog extends BottomDialog {
    private Context context;

    private android.view.View.OnClickListener mSDClickListener, mCameraClickListener;

    public PhotoUploadDialog(Context context) {
        super(context, R.style.warn_window_dialog);
        this.context = context;
        init();
    }

    private void init() {
        final View v = LayoutInflater.from(context).inflate(R.layout.dialog_photo_upload, null);

        setContentView(v);

        v.findViewById(R.id.btn_phone_gallery).setOnClickListener(this); // 手机相册按钮
        v.findViewById(R.id.btn_camera_phone).setOnClickListener(this);// 手机拍照按钮
        v.findViewById(R.id.btn_cancel).setOnClickListener(this); // 取消按钮
    }

    public void setSDOnClick(android.view.View.OnClickListener listener) {
        mSDClickListener = listener;
    }

    public void setCameraOnClick(android.view.View.OnClickListener listener) {
        mCameraClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_phone_gallery: // 手机相册
                if (mSDClickListener == null) {

                } else {
                    mSDClickListener.onClick(v);
                }
                break;
            case R.id.btn_camera_phone: // 手机拍照

                if (mCameraClickListener == null) {

                } else {
                    mCameraClickListener.onClick(v);
                }
                break;
            case R.id.btn_cancel: // 取消
                break;
        }

        dismiss();
    }
}
