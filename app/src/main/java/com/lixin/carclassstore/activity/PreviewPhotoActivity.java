package com.lixin.carclassstore.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.SamplePagerAdapter;
import com.lixin.carclassstore.bean.Photo;

import java.util.ArrayList;


/**
 * 上传照片中的浏览照片页面
 */
public class PreviewPhotoActivity extends ImageBaseActivity implements View.OnClickListener{
    private ViewPager mViewPager;
    private TextView mNumTextView;
    private int mChoiceNum;

    private ArrayList<Photo> mCancelList;
    private SamplePagerAdapter mAdapter;

    private boolean mIsCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        setTitleText("预览");


        mViewPager = (ViewPager) findViewById(R.id.activity_preview_photo_vp_vp);
        mNumTextView = (TextView) findViewById(R.id.activity_preview_photo_tv_num);

        mChoiceNum = checkList.size();
        for (int i = 0; i < checkList.size(); i++) {
            checkList.get(i).path2 = checkList.get(i).path;
        }

        computeChoice();

        findViewById(R.id.activity_preview_photo_rl_accomplish).setOnClickListener(this);
        mAdapter = new SamplePagerAdapter(getApplicationContext(), checkList, false, null, null, null);
        mAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Photo photo = checkList.get(position);
                mIsCheck = !mCancelList.contains(photo);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mCancelList = new ArrayList<Photo>();
    }

    protected void menu() {
        Photo photo = checkList.get(mViewPager.getCurrentItem());
        if (mIsCheck) {
            mCancelList.add(photo);
        } else {
            mCancelList.remove(photo);
        }
        mChoiceNum = checkList.size() - mCancelList.size();

        mIsCheck = !mIsCheck;

        setResult(RESULT_CHANGE);
        computeChoice();

    }

    /**
     * 计算当前选择的个数，并设置按钮是否可点击
     */
    private void computeChoice() {
        if (mChoiceNum > 0) {
            mNumTextView.setVisibility(View.VISIBLE);
            mNumTextView.setText(String.valueOf(mChoiceNum));
        } else {
            mNumTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.activity_preview_photo_rl_accomplish:
                accomplish();
                break;
            case R.id.iv_conversation:
                menu();
                break;

            default:

                break;
        }

    }

    /**
     * 完成按钮的点击事件
     */
    private void accomplish() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void finish() {
        if (!mCancelList.isEmpty()) {
            for (Photo photo : mCancelList) {
                checkList.remove(photo);
            }
        }

        super.finish();
    }
}
