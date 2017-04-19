package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.StoreDetailBean;
import com.lixin.carclassstore.fragment.DecFragment;
import com.lixin.carclassstore.fragment.OpinionFragment;
import com.lixin.carclassstore.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/29
 * My mailbox is 1403241630@qq.com
 * 门店详情
 */

public class StoreDetailsActivity extends BaseActivity implements View.OnClickListener{
    private TextView[] mTextView;
    private Fragment[] mFragments;
    private FragmentTransaction transaction;
    private int current = 0;
    private List<StoreDetailBean> mList = new ArrayList<>();
    private ImageView imBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_store_details);
        initView();
        initFragment();
        refreshView();
    }
    protected void initView() {
        mTextView = new TextView[3];
        mTextView[0] = (TextView) findViewById(R.id.text_shop);
        mTextView[1] = (TextView) findViewById(R.id.text_dec);
        mTextView[2] = (TextView) findViewById(R.id.text_opinion);
        imBack = (ImageView) findViewById(R.id.img_back);
        imBack.setOnClickListener(this);
    }
    private void initFragment() {
        mFragments = new Fragment[3];
        mFragments[0] = new ShopFragment();
        mFragments[1] = new DecFragment();
        mFragments[2] = new OpinionFragment();
        setCurrent(0);
    }
    private void setCurrent(int position) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_shop_layout_content, mFragments[position]);
        transaction.commitAllowingStateLoss();
        mTextView[position].setSelected(true);
        for (int i = 0; i < mTextView.length; i++) {
            if (i != position) {
                mTextView[i].setSelected(false);
            }
        }
        current = position;
    }
    private void refreshView() {
        for (int i = 0; i < mTextView.length; i++) {
            mTextView[i].setId(i);
            mTextView[i].setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case 0:
                setCurrent(0);
                break;
            case 1:
                setCurrent(1);
                break;
            case 2:
                setCurrent(2);
                break;
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }
}
