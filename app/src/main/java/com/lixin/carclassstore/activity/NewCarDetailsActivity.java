package com.lixin.carclassstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.ADVPAdapter;
import com.lixin.carclassstore.bean.AdInfo;
import com.lixin.carclassstore.bean.CarSeries;
import com.lixin.carclassstore.utils.GlobalMethod;
import com.lixin.carclassstore.utils.ToastUtils;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/3/27
 * My mailbox is 1403241630@qq.com
 * 新车详情
 */

public class NewCarDetailsActivity extends Activity implements View.OnClickListener{
    public static final String ARG = "NewCarDetailsActivity";
    private CarSeries mCarSeries;
    private View[] funcViews = new View[4];
    private String[] funcTxts;
    protected Context context;
    protected ViewPager mViewPager;
    protected ViewGroup mDianViewGroup;
    protected ADVPAdapter mADAdapter;
    private List<AdInfo> adList;
    private ListView lv_car_message;
    private LinearLayout linear_website_image,asking_for_the_lowest_price;
    private int[] bigBGs = new int[]{
            R.drawable.app_home,
            R.drawable.app_home,
            R.drawable.app_home,
            R.drawable.app_home
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_details);
        mCarSeries = getIntent().getParcelableExtra(ARG);
        initView();
        initListener();
    }

    private void initListener() {
        linear_website_image.setOnClickListener(this);
        asking_for_the_lowest_price.setOnClickListener(this);
        lv_car_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    startActivity(new Intent(NewCarDetailsActivity.this,NewCarMessageActivity.class));
            }
        });
    }

    private void initView() {
        funcTxts = this.getResources().getStringArray(R.array.car_message);
        linear_website_image = (LinearLayout) findViewById(R.id.linear_website_image);
        asking_for_the_lowest_price = (LinearLayout) findViewById(R.id.asking_for_the_lowest_price);
        lv_car_message = (ListView) findViewById(R.id.lv_car_message);
        funcViews[0] = findViewById(R.id.text_to_configure);
        funcViews[1] = findViewById(R.id.text_explain_in_detail);
        funcViews[2] = findViewById(R.id.text_consultation);
        funcViews[3] = findViewById(R.id.text_business_website);
        mViewPager = (ViewPager)findViewById(R.id.v_home_head_vp_vp);

        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = GlobalMethod.getMiddleADHeight(NewCarDetailsActivity.this);
        mViewPager.setLayoutParams(params);
        for (int i = 0; i < funcViews.length; i++) {
            ImageView imageView = (ImageView) funcViews[i]
                    .findViewById(R.id.include_imagetext_view_image);
            TextView textView = (TextView) funcViews[i]
                    .findViewById(R.id.include_imagetext_textview_text);
            textView.setText(funcTxts[i]);
            imageView.setImageResource(bigBGs[i]);
            funcViews[i].setOnClickListener(this);
            funcViews[i].setId(i);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /** 当ViewPager切换时,更新下部的点点 */
                PagerAdapter adapter = mViewPager.getAdapter();
                if (null == adapter) {
                    return;
                }
                int length = adapter.getCount() - 2;
                for (int i = 0; i < length; i++) {
                    ImageView imageView = (ImageView) mDianViewGroup
                            .getChildAt(i);
                    if (imageView != null) {
                        setImage(i, position, imageView);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setViewPagerAdapter();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_website_image:
                ToastUtils.showMessageLong(context,"你点击了显示口碑印象全部");
                break;
            case R.id.asking_for_the_lowest_price:
                ToastUtils.showMessageLong(context,"你点击了询底价");
                break;
            case 0:
                ToastUtils.showMessageLong(context,"你点击了配置");
                break;
            case 1:
                ToastUtils.showMessageLong(context,"你点击了详解");
                break;
            case 2:
                ToastUtils.showMessageLong(context,"你点击了资讯");
                break;
            case 3:
                ToastUtils.showMessageLong(context,"你点击了口碑");
                break;
        }

    }
    protected void setViewPagerAdapter() {
        mADAdapter = new ADVPAdapter(MyApplication.getApplication(),
                adList,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AdInfo mCurrentAd = (AdInfo) v.getTag();
                        if (mCurrentAd != null && mCurrentAd.getReqType() == 3 && !TextUtils.isEmpty(mCurrentAd.getUrl())) {
                            ToastUtils.showMessageLong(context,"你点击了图片");

                        }
                    }
                });
        mViewPager.setAdapter(mADAdapter);
        if (adList != null) {
            mViewPager.setCurrentItem(1);
        }
        initDian();
    }
    private void initDian() {
        mDianViewGroup.removeAllViews();

        PagerAdapter adapter = mViewPager.getAdapter();
        if (null == adapter) {
            return;
        }

        int length = adapter.getCount() - 2;
        if (length == 1)
            return;

        int height = GlobalMethod.dp2px(MyApplication.getApplication(), 10);
        int width = GlobalMethod.dp2px(MyApplication.getApplication(), 14);
        int padding = GlobalMethod.dp2px(MyApplication.getApplication(), 2);

        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(MyApplication.getApplication());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            imageView.setPadding(padding, 0, padding, 0);
            setImage(i, 0, imageView);
            mDianViewGroup.addView(imageView);
        }
    }
    private void setImage(int position, int flag, ImageView imageView) {
        if (position == flag) {
            imageView.setImageResource(R.drawable.app_selected_circle_icon);
        } else {
            imageView.setImageResource(R.drawable.app_unselected_circle_icon);
        }
    }
}
