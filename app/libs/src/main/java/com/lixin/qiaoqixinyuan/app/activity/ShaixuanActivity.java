package com.lixin.qiaoqixinyuan.app.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.bean.Shangpin_all_Bean;
import com.lixin.qiaoqixinyuan.app.util.ToastUtil;
import com.lixin.qiaoqixinyuan.app.view.MyListview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ShaixuanActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_turnback;
    private TextView tv_title;
    private LinearLayout ll_title;
    private TextView tv_shaixuan_fenlei;
    private TextView tv_shaixuan_fujin;
    private TextView tv_shaixuan_paixu;
    private MyListview shaixuan_list;
    private List<Shangpin_all_Bean.Classificationlist> classificationlist;
    private List<String>  mListType = new ArrayList<String>();  //类型列表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaixuan);
        initView();
    }
    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        iv_turnback.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("商家商品");
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        tv_shaixuan_fenlei = (TextView) findViewById(R.id.tv_shaixuan_fenlei);
        tv_shaixuan_fenlei.setOnClickListener(this);
        tv_shaixuan_fujin = (TextView) findViewById(R.id.tv_shaixuan_fujin);
        tv_shaixuan_fujin.setOnClickListener(this);
        tv_shaixuan_paixu = (TextView) findViewById(R.id.tv_shaixuan_paixu);
        tv_shaixuan_paixu.setOnClickListener(this);
        shaixuan_list = (MyListview) findViewById(R.id.shaixuan_list);
        for(int i = 0; i < 5; i++){
            mListType.add("弄好");
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_turnback:
                finish();
                break;
            case R.id.tv_shaixuan_fenlei:
                //showPopup();
                break;
        }
    }

    }
