package com.lixin.qiaoqixinyuan.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.activity.Ershou_fabu_Activity;
import com.lixin.qiaoqixinyuan.app.activity.Fangwu_Activity;
import com.lixin.qiaoqixinyuan.app.activity.Fangwu_qiugou_Activity;
import com.lixin.qiaoqixinyuan.app.activity.MainActivity;
import com.lixin.qiaoqixinyuan.app.activity.Pin_fabu_Activity;
import com.lixin.qiaoqixinyuan.app.activity.PublishPostActivity;
import com.lixin.qiaoqixinyuan.app.activity.Zhaopin_fabu_Activity;
import com.lixin.qiaoqixinyuan.app.base.BaseFragment;
import com.lixin.qiaoqixinyuan.app.base.MyApplication;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.wheel.pop.ClassifyPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class SendFragment extends BaseFragment implements View.OnClickListener, ClassifyPopWindow.PopInterface {
    private TextView tv_tieba;//贴吧
    private TextView tv_house;//房屋信息
    private TextView tv_used;//二手信息
    private TextView tv_recruitment;//招聘求职
    private TextView tv_dache;//搭车
    private LinearLayout ll_sendfragment;
    private List<String> list=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(context, R.layout.send_fragment, null);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        tv_tieba = (TextView) view.findViewById(R.id.tv_tieba);
        tv_house = (TextView) view.findViewById(R.id.tv_house);
        tv_used = (TextView) view.findViewById(R.id.tv_used);
        tv_recruitment = (TextView) view.findViewById(R.id.tv_recruitment);
        tv_dache = (TextView) view.findViewById(R.id.tv_dache);
        ll_sendfragment = (LinearLayout) view.findViewById(R.id.ll_sendfragment);
    }

    private void initData() {
        list.add("出售");
        list.add("求购");
        list.add("你猜");
    }

    private void initListener() {
        tv_tieba.setOnClickListener(this);
        tv_house.setOnClickListener(this);
        tv_used.setOnClickListener(this);
        tv_recruitment.setOnClickListener(this);
        tv_dache.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tieba://贴吧
                MyApplication.openActivity(context, PublishPostActivity.class);
                break;
            case R.id.tv_house://房屋信息
//                ClassifyPopWindow classifyPopWindow = null;
//                if (classifyPopWindow == null) {
//                    classifyPopWindow = new ClassifyPopWindow(context, list);
//                    classifyPopWindow.setOnCycleListener(this);
//                }
//                classifyPopWindow.showAtLocation(ll_sendfragment, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                MyApplication.openActivity(context, Fangwu_qiugou_Activity.class);
                break;
            case R.id.tv_used://二手信息
                Intent intent = new Intent(context, Ershou_fabu_Activity.class);
                intent.putExtra("secondtypeid","0");
                startActivity(intent);
                break;
            case R.id.tv_recruitment://招聘求职
                MyApplication.openActivity(context, Zhaopin_fabu_Activity.class);
                break;
            case R.id.tv_dache://搭车
                MyApplication.openActivity(context, Pin_fabu_Activity.class);
                break;
        }
    }

    @Override
    public void saveVycle(String birthday) {
        ToastUtil.showToast(context,birthday);
        String string=birthday;
    }
}
