package com.lixin.qiaoqixinyuan.app.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.lixin.qiaoqixinyuan.app.view.ProgressDialog;

import static com.umeng.socialize.Config.dialog;

/**
 * 项目名称：ProjectFramework
 * 类名称：BaseFragment
 * 类描述：Fragment基础类
 * 创建人：Tiramisu
 * 创建时间：2017/1/17 11:49
 */

public class BaseFragment extends Fragment {
    protected Context context;
    protected Dialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        dialog = ProgressDialog.createLoadingDialog(context, "加载中.....");
    }
}
