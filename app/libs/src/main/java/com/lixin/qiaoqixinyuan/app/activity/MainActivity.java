package com.lixin.qiaoqixinyuan.app.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.wxlib.util.SysUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.Im.YWSDKUtil;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.util.FragmentCheck;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.StatusBarUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.jpushdemo.ExampleUtil;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

import static com.lixin.qiaoqixinyuan.R.mipmap.my;
import static com.lixin.qiaoqixinyuan.app.base.MyApplication.CONTEXT;
import static com.lixin.qiaoqixinyuan.app.base.MyApplication.isLogined;
import static com.lixin.qiaoqixinyuan.app.base.MyApplication.mLocationClient;
import static com.umeng.socialize.utils.DeviceConfig.context;

public class MainActivity extends FragmentActivity {

    private FrameLayout frame_main_select;
    private RadioButton rb_main_shouye;
    private RadioButton rb_main_pinche;
    private RadioButton rb_main_fabu;
    private RadioButton rb_main_geren;
    private RadioGroup main_radiogroup;
    private LinearLayout activity_main;
    private FragmentManager mFragmentManager;
    private TextView tv_home_first;
    private ImageView tv_pin_first;
    private TextView tv_fragment_name;
    private ImageView iv_pin_second;
    private RelativeLayout rl_title;
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            StatusBarUtil.setColor(this, this.getResources().getColor(R.color.theme));
        }
        mLocationClient.stop();
        initView();
        init();
        select();
        registerMessageReceiver();
    }
    private void initView() {
        frame_main_select = (FrameLayout) findViewById(R.id.frame_main_select);
        rb_main_shouye = (RadioButton) findViewById(R.id.rb_main_shouye);
        rb_main_pinche = (RadioButton) findViewById(R.id.rb_main_pinche);
        rb_main_fabu = (RadioButton) findViewById(R.id.rb_main_fabu);
        rb_main_geren = (RadioButton) findViewById(R.id.rb_main_geren);
        main_radiogroup = (RadioGroup) findViewById(R.id.main_radiogroup);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        mFragmentManager = getSupportFragmentManager();
        tv_home_first = (TextView) findViewById(R.id.tv_home_first);
        tv_pin_first = (ImageView) findViewById(R.id.tv_pin_first);
        tv_fragment_name = (TextView) findViewById(R.id.tv_fragment_name);
        iv_pin_second = (ImageView) findViewById(R.id.iv_pin_second);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
    }
    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        isForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        isForeground = false;
    }
    private void select() {
        main_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            private Fragment fragmentById;

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                FragmentTransaction fragmentTransaction;
                switch (checkedId) {
                    case R.id.rb_main_shouye:
                        rl_title.setVisibility(View.VISIBLE);
                        tv_home_first.setVisibility(View.VISIBLE);
                        tv_pin_first.setVisibility(View.GONE);
                        tv_fragment_name.setText("首页");
                        iv_pin_second.setVisibility(View.GONE);
                        fragmentTransaction = mFragmentManager.beginTransaction();
                        rb_main_shouye.setChecked(true);
                        fragmentById = FragmentCheck.getFragmentById(R.id.rb_main_shouye);
                        fragmentTransaction.replace(R.id.frame_main_select, fragmentById);
                        fragmentTransaction.commit();
                        break;
                    case R.id.rb_main_pinche:
                        rl_title.setVisibility(View.VISIBLE);
                        tv_home_first.setVisibility(View.GONE);
                        tv_pin_first.setVisibility(View.VISIBLE);
                        tv_fragment_name.setText("拼车");
                        iv_pin_second.setVisibility(View.VISIBLE);
                        iv_pin_second.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, Pin_fabu_Activity.class));
                            }
                        });
                        tv_pin_first.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivityForResult(new Intent(MainActivity.this, Pin_search_Activity.class),100);
                            }
                        });
                        fragmentTransaction = mFragmentManager.beginTransaction();
                        rb_main_pinche.setChecked(true);
                        fragmentById = FragmentCheck.getFragmentById(R.id.rb_main_pinche);
                        fragmentTransaction.replace(R.id.frame_main_select, fragmentById);
                        fragmentTransaction.commit();
                        break;
                    case R.id.rb_main_fabu:
                        rl_title.setVisibility(View.VISIBLE);
                        tv_home_first.setVisibility(View.GONE);
                        tv_pin_first.setVisibility(View.GONE);
                        tv_fragment_name.setText("发布");
                        iv_pin_second.setVisibility(View.GONE);
                        fragmentTransaction = mFragmentManager.beginTransaction();
                        rb_main_fabu.setChecked(true);
                        fragmentById = FragmentCheck.getFragmentById(R.id.rb_main_fabu);
                        fragmentTransaction.replace(R.id.frame_main_select, fragmentById);
                        fragmentTransaction.commit();
                        break;
                    case R.id.rb_main_geren:
                        if(!isLogined()){
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }else {
                            rl_title.setVisibility(View.GONE);
//                        tv_home_first.setVisibility(View.GONE);
//                        tv_pin_first.setVisibility(View.GONE);
//                        tv_fragment_name.setText("");
//                        iv_pin_second.setVisibility(View.GONE);
                            fragmentTransaction = mFragmentManager.beginTransaction();
                            rb_main_geren.setChecked(true);
                            fragmentById = FragmentCheck.getFragmentById(R.id.rb_main_geren);
                            fragmentTransaction.replace(R.id.frame_main_select, fragmentById);
                            fragmentTransaction.commit();
                        }
                        break;
                }
            }
        });
    }

    private void init() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        rb_main_shouye.setChecked(true);
        Fragment fragment = FragmentCheck.getFragmentById(R.id.rb_main_shouye);
        fragmentTransaction.replace(R.id.frame_main_select, fragment);
        fragmentTransaction.commit();
    }
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
//                setCostomMsg(showMsg.toString());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }
}
