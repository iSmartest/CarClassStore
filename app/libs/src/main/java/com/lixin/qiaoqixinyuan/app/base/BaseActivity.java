package com.lixin.qiaoqixinyuan.app.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.MainActivity;
import com.lixin.qiaoqixinyuan.app.bean.MyorderBean;
import com.lixin.qiaoqixinyuan.app.bean.UserLoginBean;
import com.lixin.qiaoqixinyuan.app.util.AppManager;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.StatusBarUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.ProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

import static com.lixin.qiaoqixinyuan.R.id.prlv_offlinepay;
import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 项目名称：ProjectFramework
 * 类名称：BaseActivity
 * 类描述：Activity基础类
 * 创建人：Tiramisu
 * 创建时间：2017/1/20 10:04
 */

public class BaseActivity extends Activity {
    protected Context context;
    private static final String TAG = "MainActivity";
    private Map<String, String> params;
    protected Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            StatusBarUtil.setColor(this, this.getResources().getColor(R.color.theme));
        }
        AppManager.getAppManager().addActivity(this);
        dialog = ProgressDialog.createLoadingDialog(context, "加载中.....");
    }
}
