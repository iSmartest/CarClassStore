package com.lixin.carclassstore.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.utils.GlobalMethod;


public abstract class BottomDialog extends Dialog implements OnClickListener {
    protected Context mContext;

    public BottomDialog(Context context) {
        super(context, R.style.warn_window_dialog);
        mContext = context;
    }

    public BottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    public BottomDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    public void show() {
        windowDeploy(0, 0);
        super.show();
    }

    public void windowDeploy(int x, int y) {
        Window window = getWindow(); // 得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        window.setBackgroundDrawableResource(android.R.color.transparent); // 设置对话框背景为透明

        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = GlobalMethod.getWindowSize(getContext()).x;
        // 根据x，y坐标设置窗口需要显示的位置
        wl.x = x; // x小于0左移，大于0右移
        wl.y = y; // y小于0上移，大于0下移
        // wl.alpha = 0.6f; //设置透明度
        wl.gravity = Gravity.BOTTOM;//设置重力
        window.setAttributes(wl);
    }
}
