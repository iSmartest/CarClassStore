package com.lixin.carclassstore.activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lixin.carclassstore.R;
import com.lixin.carclassstore.fragment.ForumFragment;
import com.lixin.carclassstore.fragment.HomeFragment;
import com.lixin.carclassstore.fragment.MineFragment;
import com.lixin.carclassstore.fragment.StoreFragment;

public class MainActivity extends BaseActivity implements HomeFragment.CallBackValue{
    private ImageView[] mTextView;
    private Fragment[] mFragments;
    private FragmentTransaction transaction;
    private int current = 0;
    private String [] titles;
    private String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_new_main_layout_content, new HomeFragment())
                    .commit();
        }
        initTitle();
        initView();
        initFragment();
        refreshView();
    }
    private void initView() {
        mTextView = new ImageView[4];
        mTextView[0] = (ImageView) findViewById(R.id.iv_main_home);
        mTextView[1] = (ImageView) findViewById(R.id.iv_main_store);
        mTextView[2] = (ImageView) findViewById(R.id.iv_main_forum);
        mTextView[3] = (ImageView) findViewById(R.id.iv_main_mine);

    }
    private void initFragment() {
        mFragments = new Fragment[4];
        mFragments[0] = new HomeFragment();
        mFragments[1] = new StoreFragment();
        mFragments[2] = new ForumFragment();
        mFragments[3] = new MineFragment();
        setCurrent(0);
    }
    private void initTitle() {
        titles = new String[4];
        titles[0] = "山东广饶汽车城";
        titles[1] = "门店";
        titles[2] = "论坛";
        titles[3] = "我的";
    }
    private void setCurrent(int position) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_new_main_layout_content, mFragments[position]);
        transaction.commitAllowingStateLoss();
        mTextView[position].setSelected(true);
        for (int i = 0; i < mTextView.length; i++) {
            if (i != position) {
                mTextView[i].setSelected(false);
            }
        }
        if (position == 0){
            hideBack(true);
            setTitleText(titles[position]);
        }else if (position == 3){
            RelativeLayout lay_bg = (RelativeLayout) findViewById(R.id.lay_bg);
            setTitleText(titles[position]);
            lay_bg.setVisibility(View.GONE);
        }else {
            hideBack(false);
            ImageView Iv_base_back = (ImageView) findViewById(R.id.Iv_base_back);
            Iv_base_back.setVisibility(View.GONE);
            setTitleText(titles[position]);
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
            case 3:
                setCurrent(3);
                break;
            default:
                break;
        }
    }

    @Override
    public void SendMessageValue(String strValue) {
        temp = strValue;
        if (temp.equals("6")) setCurrent(1);
    }
}
