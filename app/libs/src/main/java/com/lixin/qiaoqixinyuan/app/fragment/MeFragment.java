package com.lixin.qiaoqixinyuan.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.BalanceActivity;
import com.lixin.qiaoqixinyuan.app.activity.ConcernActivity;
import com.lixin.qiaoqixinyuan.app.activity.LoginActivity;
import com.lixin.qiaoqixinyuan.app.activity.MyAddressActivity;
import com.lixin.qiaoqixinyuan.app.activity.MyBlacklistActivity;
import com.lixin.qiaoqixinyuan.app.activity.MyMessageActivity;
import com.lixin.qiaoqixinyuan.app.activity.MyOrderActivity;
import com.lixin.qiaoqixinyuan.app.activity.MyPostActivity;
import com.lixin.qiaoqixinyuan.app.activity.MyPublishActivity;
import com.lixin.qiaoqixinyuan.app.activity.NearByActivity;
import com.lixin.qiaoqixinyuan.app.activity.PersonalInformationActivity;
import com.lixin.qiaoqixinyuan.app.activity.SettingsActivity;
import com.lixin.qiaoqixinyuan.app.base.BaseFragment;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.bean.MyselfcenterBean;
import com.lixin.qiaoqixinyuan.app.util.ImageLoaderUtil;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView civ_user_icon;//用户头像
    private TextView tv_user_name;//用户昵称
    private TextView tv_check_userinformation;//查看用户信息
    private RelativeLayout rl_user_information;//用户信息
    private ImageView iv_message;//用户消息
    private TextView tv_news_num;//站内信数量
    private TextView tv_concernnum;//关注数量
    private TextView tv_funsnum;//粉丝数量
    private TextView tv_balance;//余额
    private TextView tv_myorder;//我的订单
    private TextView tv_mypublish;//我的发布
    private TextView tv_mymessage;//我的消息
    private TextView tv_myaddress;//收货地址
    private TextView tv_nearby;//附近的人
    private TextView tv_mypost;//我的帖子
    private TextView tv_myblacklist;//我的黑名单
    private TextView tv_setttings;//设置
    private String uid;
    private String nickName;
    private String token;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(context, R.layout.me_fragment, null);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        token=tm.getDeviceId();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        civ_user_icon = (CircleImageView) view.findViewById(R.id.civ_user_icon);
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        tv_check_userinformation = (TextView) view.findViewById(R.id.tv_check_userinformation);
        iv_message = (ImageView) view.findViewById(R.id.iv_message);
        rl_user_information= (RelativeLayout) view.findViewById(R.id.rl_user_information);
        tv_concernnum = (TextView) view.findViewById(R.id.tv_concernnum);
        tv_funsnum = (TextView) view.findViewById(R.id.tv_funsnum);
        tv_balance = (TextView) view.findViewById(R.id.tv_balance);
        tv_myorder = (TextView) view.findViewById(R.id.tv_myorder);
        tv_news_num = (TextView) view.findViewById(R.id.tv_news_num);
        tv_mypublish = (TextView) view.findViewById(R.id.tv_mypublish);
        tv_mymessage = (TextView) view.findViewById(R.id.tv_mymessage);
        tv_myaddress = (TextView) view.findViewById(R.id.tv_myaddress);
        tv_nearby = (TextView) view.findViewById(R.id.tv_nearby);
        tv_mypost = (TextView) view.findViewById(R.id.tv_mypost);
        tv_myblacklist = (TextView) view.findViewById(R.id.tv_myblacklist);
        tv_setttings = (TextView) view.findViewById(R.id.tv_setttings);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (MyApplication.isLogined()){
            uid = SharedPreferencesUtil.getSharePreStr(context,"uid");
            nickName=SharedPreferencesUtil.getSharePreStr(context,"nickName");
            tv_check_userinformation.setOnClickListener(this);
            iv_message.setOnClickListener(this);
            tv_news_num.setOnClickListener(this);
            tv_user_name.setText(nickName);
            myselfcenter();
        }else {
            civ_user_icon.setImageResource(R.mipmap.g_tx);
            tv_user_name.setText("未登录");
            tv_check_userinformation.setOnClickListener(null);
            iv_message.setOnClickListener(null);
            tv_news_num.setOnClickListener(null);
        }
    }

    private void initData() {

    }

    private void initListener() {
        rl_user_information.setOnClickListener(this);
        tv_concernnum.setOnClickListener(this);
        tv_funsnum.setOnClickListener(this);
        tv_balance.setOnClickListener(this);
        tv_myorder.setOnClickListener(this);
        tv_mypublish.setOnClickListener(this);
        tv_mymessage.setOnClickListener(this);
        tv_myaddress.setOnClickListener(this);
        tv_nearby.setOnClickListener(this);
        tv_mypost.setOnClickListener(this);
        tv_myblacklist.setOnClickListener(this);
        tv_setttings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_check_userinformation:
                MyApplication.openActivity(context, PersonalInformationActivity.class);
                break;
            case R.id.iv_message:
                MyApplication.openActivity(context, MyMessageActivity.class);
                break;
            case R.id.tv_news_num:
                MyApplication.openActivity(context, MyMessageActivity.class);
                break;
            case R.id.rl_user_information:
               if (MyApplication.isLogined()){
                   MyApplication.openActivity(context, SettingsActivity.class);
               }else {
                   MyApplication.openActivity(context, LoginActivity.class);
               }
                break;
            case R.id.tv_concernnum:
                Bundle bundle = new Bundle();
                bundle.putSerializable("code","0");
                MyApplication.openActivity(context, ConcernActivity.class,bundle);
                break;
            case R.id.tv_funsnum:
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("code","1");
                MyApplication.openActivity(context, ConcernActivity.class,bundle1);
                break;
            case R.id.tv_balance:
                MyApplication.openActivity(context, BalanceActivity.class);
                break;
            case R.id.tv_myorder:
                MyApplication.openActivity(context, MyOrderActivity.class);
                break;
            case R.id.tv_mypublish:
                MyApplication.openActivity(context, MyPublishActivity.class);
                break;
            case R.id.tv_mymessage:
                MyApplication.openActivity(context, MyMessageActivity.class);
                break;
            case R.id.tv_myaddress:
                MyApplication.openActivity(context, MyAddressActivity.class);
                break;
            case R.id.tv_nearby:
                MyApplication.openActivity(context, NearByActivity.class);
                break;
            case R.id.tv_mypost:
                MyApplication.openActivity(context, MyPostActivity.class);
                break;
            case R.id.tv_myblacklist:
                MyApplication.openActivity(context, MyBlacklistActivity.class);
                break;
            case R.id.tv_setttings:
                MyApplication.openActivity(context, SettingsActivity.class);
              break;
        }
    }
    /**
     * 个人中心
     */
    private void myselfcenter() {
        Map<String, String> params = new HashMap<>();
      /*  params.put("cmd", "myselfcenter");
        params.put("uid", uid);
        params.put("token", token);*/
        String json="{\"cmd\":\"myselfcenter\",\"uid\":\"" + uid + "\",\"token\":\"" + token + "\"}";
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
                        MyselfcenterBean bean = gson.fromJson(response, MyselfcenterBean.class);
                        if ("0".equals(bean.result)) {
                            SharedPreferencesUtil.putSharePre(context, "uidicon", bean.uidicon);
                            SharedPreferencesUtil.putSharePre(context, "nickName", bean.uidname);
                            SharedPreferencesUtil.putSharePre(context, "uidfocus", bean.uidfocus);
                            SharedPreferencesUtil.putSharePre(context, "uidfans", bean.uidfans);
                            SharedPreferencesUtil.putSharePre(context, "uidbalance", bean.uidbalance);
                            SharedPreferencesUtil.putSharePre(context, "uidnewsnum", bean.uidnewsnum);
                            ImageLoader.getInstance().displayImage(bean.uidicon,civ_user_icon,ImageLoaderUtil.DIO());
                            tv_user_name.setText(bean.uidname);
                            tv_concernnum.setText("关注  "+bean.uidfocus);
                            tv_funsnum.setText("粉丝  "+bean.uidfans);
                            tv_balance.setText("余额  "+bean.uidbalance);
                            if ("0".equals(bean.uidnewsnum)) {
                                tv_news_num.setVisibility(View.GONE);
                            }else {
                                tv_news_num.setVisibility(View.VISIBLE);
                                tv_news_num.setText(bean.uidnewsnum);
                            }
                        } else {
                            ToastUtil.showToast(context, bean.resultNote);
                        }
                    }
                });

    }

}
