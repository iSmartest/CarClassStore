package com.lixin.carclassstore.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.dialog.TipsDialog;
import com.lixin.carclassstore.utils.CommonLog;

public class BaseActivity extends FragmentActivity implements OnClickListener {
    private boolean override;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面启动 记录日志
        setContentView(R.layout.activity_base);
        CommonLog.i(getClass().getName());

    }

    public void hideBack(boolean show) {
        ImageView Iv_base_add = (ImageView) findViewById(R.id.Iv_base_add);
        ImageView Iv_base_back = (ImageView) findViewById(R.id.Iv_base_back);
        ImageView iv_conversation = (ImageView) findViewById(R.id.iv_conversation);
        ImageView iv_search = (ImageView) findViewById(R.id.iv_search);
        RelativeLayout lay_bg = (RelativeLayout) findViewById(R.id.lay_bg);
        if (show){
            Iv_base_back.setVisibility(View.GONE);
            Iv_base_add.setVisibility(View.VISIBLE);
            iv_conversation.setVisibility(View.VISIBLE);
            iv_search.setVisibility(View.VISIBLE);
            lay_bg.setVisibility(View.VISIBLE);
            iv_search.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BaseActivity.this, "点击了搜索", Toast.LENGTH_SHORT).show();
                }
            });
            iv_conversation.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BaseActivity.this, "点击了会话", Toast.LENGTH_SHORT).show();
                }
            });
            Iv_base_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BaseActivity.this, "点击了添加", Toast.LENGTH_SHORT).show();

                }
            });
        }else{
            lay_bg.setVisibility(View.VISIBLE);
            Iv_base_back.setVisibility(View.VISIBLE);
            Iv_base_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BaseActivity.this, "点击了后退", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            Iv_base_add.setVisibility(View.GONE);
            iv_search.setVisibility(View.GONE);
            iv_conversation.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

    }
    protected void overrideOnKeyDown(boolean override) {
        this.override = override;
    }
    protected void back() {
        finish();
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    public void startActivityFromChild(Activity child, Intent intent,
                                       int requestCode) {
        super.startActivityFromChild(child, intent, requestCode);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }
    private TipsDialog dialog;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (override)
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                if (dialog == null) {
                    dialog = new TipsDialog(this, R.string.ok_to_exit_it, new TipsDialog.OnSureBtnClickListener() {
                        @Override
                        public void sure() {
                            dialog.dismiss();
                            finish();
//                            MyApplication.getApplication().exit();
                        }
                    });
                }
                dialog.show();
            }
        return super.onKeyDown(keyCode, event);
    }
    public void setTitleText(String string) {
        TextView titleTv = (TextView) findViewById(R.id.tv_base_titleText);
        titleTv.setText(string);
    }
}
