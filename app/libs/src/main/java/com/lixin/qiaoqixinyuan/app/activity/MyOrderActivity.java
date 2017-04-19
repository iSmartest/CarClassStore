package com.lixin.qiaoqixinyuan.app.activity;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.base.BaseActivity;
import com.lixin.qiaoqixinyuan.app.fragment.MyOrderCompleteFragment;
import com.lixin.qiaoqixinyuan.app.fragment.MyOrderOfflinePayFragment;
import com.lixin.qiaoqixinyuan.app.fragment.MyOrderOnlinePayFragment;
import com.lixin.qiaoqixinyuan.app.fragment.MyOrderRefundsFragment;
import com.lixin.qiaoqixinyuan.app.util.SharedPreferencesUtil;
import com.lixin.qiaoqixinyuan.app.view.LazyViewPager;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView iv_turnback;
    private TextView tv_title;
    private LazyViewPager viewPager;// 页卡内容
    private ImageView imageView;// 动画图片
    private TextView tv_offlinepay, tv_onlinepay, tv_complete,tv_refunds;// 选项名称
    private List<Fragment> fragments;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private int selectedColor, unSelectedColor;//是否选择显示的颜色
    private int type;//选择的订单状态
    private static final int pageSize = 4;//页卡总数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        initView();
        initData();
        initLiserner();
    }

    private void initView() {
        iv_turnback = (ImageView) findViewById(R.id.iv_turnback);
        tv_title = (TextView) findViewById(R.id.tv_title);
        selectedColor = getResources().getColor(R.color.red);
        unSelectedColor = getResources().getColor(R.color.black);

        InitImageView();
        InitTextView();
        InitViewPager();
    }
    private void initData() {
        tv_title.setText("我的订单");
    }
    private void initLiserner() {
        iv_turnback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_turnback:
                finish();
                break;
        }
    }
    /**
     * 初始化Viewpager页
     */
    private void InitViewPager() {
        viewPager = (LazyViewPager) findViewById(R.id.vPager);
        fragments = new ArrayList<Fragment>();
        fragments.add(new MyOrderOfflinePayFragment());
        fragments.add(new MyOrderOnlinePayFragment());
        fragments.add(new MyOrderCompleteFragment());
        fragments.add(new MyOrderRefundsFragment());
        viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),
                fragments));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化头标
     *
     */
    private void InitTextView() {
        tv_offlinepay = (TextView) findViewById(R.id.tv_offlinepay);
        tv_onlinepay = (TextView) findViewById(R.id.tv_onlinepay);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_refunds= (TextView) findViewById(R.id.tv_refunds);

        tv_offlinepay.setTextColor(selectedColor);
        tv_onlinepay.setTextColor(unSelectedColor);
        tv_complete.setTextColor(unSelectedColor);
        tv_refunds.setTextColor(unSelectedColor);

        tv_offlinepay.setText("见面付款");
        tv_onlinepay.setText("线上支付");
        tv_complete.setText("已完成");
        tv_refunds.setText("退款");

        tv_offlinepay.setOnClickListener(new MyOnClickListener(0));
        tv_onlinepay.setOnClickListener(new MyOnClickListener(1));
        tv_complete.setOnClickListener(new MyOnClickListener(2));
        tv_refunds.setOnClickListener(new MyOnClickListener(3));
    }

    /**
     * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     */

    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(),
                R.drawable.tab_selected_bg).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / pageSize - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
        // = 偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 头标点击监听
     */
    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {

            switch (index) {
                case 0:
                    tv_offlinepay.setTextColor(selectedColor);
                    tv_onlinepay.setTextColor(unSelectedColor);
                    tv_complete.setTextColor(unSelectedColor);
                    tv_refunds.setTextColor(unSelectedColor);
                    type=0;
                    break;
                case 1:
                    tv_offlinepay.setTextColor(unSelectedColor);
                    tv_onlinepay.setTextColor(selectedColor);
                    tv_complete.setTextColor(unSelectedColor);
                    tv_refunds.setTextColor(unSelectedColor);
                    type=1;
                    break;
                case 2:
                    tv_offlinepay.setTextColor(unSelectedColor);
                    tv_onlinepay.setTextColor(unSelectedColor);
                    tv_complete.setTextColor(selectedColor);
                    tv_refunds.setTextColor(unSelectedColor);
                    type=2;
                    break;
                case 3:
                    tv_offlinepay.setTextColor(unSelectedColor);
                    tv_onlinepay.setTextColor(unSelectedColor);
                    tv_complete.setTextColor(unSelectedColor);
                    tv_refunds.setTextColor(selectedColor);
                    type=3;
                    break;
            }
            viewPager.setCurrentItem(index);
        }

    }

    /**
     * 为选项卡绑定监听器
     */
    public class MyOnPageChangeListener implements LazyViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量
        int three = one * 3;// 页卡1 -> 页卡4 偏移量

        public void onPageScrollStateChanged(int index) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int index) {
            Animation animation = new TranslateAnimation(one * currIndex, one
                    * index, 0, 0);// 显然这个比较简洁，只有一行代码。
            currIndex = index;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);

            switch (index) {
                case 0:
                    tv_offlinepay.setTextColor(selectedColor);
                    tv_onlinepay.setTextColor(unSelectedColor);
                    tv_complete.setTextColor(unSelectedColor);
                    tv_refunds.setTextColor(unSelectedColor);
                    type=0;
                    break;
                case 1:
                    tv_offlinepay.setTextColor(unSelectedColor);
                    tv_onlinepay.setTextColor(selectedColor);
                    tv_complete.setTextColor(unSelectedColor);
                    tv_refunds.setTextColor(unSelectedColor);
                    type=1;
                    break;
                case 2:
                    tv_offlinepay.setTextColor(unSelectedColor);
                    tv_onlinepay.setTextColor(unSelectedColor);
                    tv_complete.setTextColor(selectedColor);
                    tv_refunds.setTextColor(unSelectedColor);
                    type=2;
                    break;
                case 3:
                    tv_offlinepay.setTextColor(unSelectedColor);
                    tv_onlinepay.setTextColor(unSelectedColor);
                    tv_complete.setTextColor(unSelectedColor);
                    tv_refunds.setTextColor(selectedColor);
                    type=3;
                    break;
            }
        }
    }
    /**
     * 定义适配器
     */
    class myPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        /**
         * 得到每个页面
         */
        @Override
        public Fragment getItem(int arg0) {
            return (fragmentList == null || fragmentList.size() == 0) ? null
                    : fragmentList.get(arg0);
        }

        /**
         * 每个页面的title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        /**
         * 页面的总个数
         */
        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }
}
