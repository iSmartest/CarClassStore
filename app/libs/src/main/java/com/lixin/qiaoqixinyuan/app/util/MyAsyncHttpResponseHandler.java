package com.lixin.qiaoqixinyuan.app.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.lixin.qiaoqixinyuan.app.view.ProgressDialog;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

public abstract class MyAsyncHttpResponseHandler extends TextHttpResponseHandler {

    private Context context;
    private Dialog progressDlg;

    public MyAsyncHttpResponseHandler(Context context) {
        super();
        this.context = context;
    }



    @Override
    public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
        progressDlg.dismiss();
        ToastUtil.showToast(context, "网络加载失败");
    }

    @Override
    public void onSuccess(int arg0, Header[] arg1, String arg2) {
        progressDlg.dismiss();
        success(arg0, arg1, arg2);
    }

    public abstract void success(int arg0, Header[] arg1, String s);

    @Override
    public void onFinish() {
        super.onFinish();
        progressDlg.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDlg = ProgressDialog.createLoadingDialog(context, "加载中...");
        progressDlg.show();
    }


}
