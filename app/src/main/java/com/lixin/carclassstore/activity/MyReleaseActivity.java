package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.MyReleaseAdapter;
import com.lixin.carclassstore.adapter.ShoppingCartAdapter;
import com.lixin.carclassstore.bean.MyReleaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 */

public class MyReleaseActivity extends BaseActivity implements View.OnClickListener
        ,ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface{
    private ListView list_my_release;
    public TextView tv_title;
    private MyReleaseAdapter myReleaseAdapter;
    private List<MyReleaseBean> myReleaseBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setTitleText("我的发布");
        hideBack(false);
        initData();
        initView();
    }

    private void initView() {
        list_my_release = (ListView) findViewById(R.id.list_my_release);
        myReleaseAdapter = new MyReleaseAdapter(this);
        myReleaseAdapter.setModifyCountInterface(this);
        list_my_release.setAdapter(myReleaseAdapter);
        myReleaseAdapter.setMyReleaseBean(myReleaseBeanList);
    }

    @Override
    public void onClick(View v) {

    }




    protected void initData() {
        for (int i = 0; i < 6; i++) {
            MyReleaseBean MyReleaseBean = new MyReleaseBean();
            MyReleaseBean.setContent("百度翻译是百度发布的在线翻译服务，依托海量的互联网数据资源和领先的自然语言处理技术优势，致力于帮助用户跨越语言鸿沟，更加方便快捷地获取信息和服务。百度翻译支持全球28种热门语言互译，包括中文、英语、日语、韩语、西班牙语、泰语");
            MyReleaseBean.setNum("100");
            MyReleaseBean.setTime("2017-3-31 12：08");
            myReleaseBeanList.add(MyReleaseBean);
        }
    }

    @Override
    public void checkGroup(int position, boolean isChecked) {

    }

    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {

    }

    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        MyReleaseBean myReleaseBean = myReleaseBeanList.get(position);
        int currentCount = myReleaseBean.getCount();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        myReleaseBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        myReleaseAdapter.notifyDataSetChanged();

    }

    @Override
    public void childDelete(int position) {
        myReleaseBeanList.remove(position);
        myReleaseAdapter.notifyDataSetChanged();

    }

}
